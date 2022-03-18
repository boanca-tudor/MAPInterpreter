package Model.Statements.HeapStatements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Statements.StatementException;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;
import Model.Values.ReferenceValue;

import java.lang.ref.Reference;

public class HeapWriteStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public HeapWriteStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        if (!state.getSymbolTable().containsKey(variableName))
            throw new StatementException("The variable is not in the symbol table!");
        if (!state.getHeap().containsKey(((ReferenceValue)state.getSymbolTable().get(variableName)).getAddress()))
            throw new StatementException("The address of the variable is not in the heap!");

        IValue expressionResult = expression.eval(state.getSymbolTable(), state.getHeap());

        state.getHeap().put(((ReferenceValue)state.getSymbolTable().get(variableName)).getAddress(),
                expressionResult);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWriteStatement(variableName, expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment)
            throws TypeMismatchException {
        IType varType = typeEnvironment.get(variableName);
        IType expType = expression.typeCheck(typeEnvironment);

        if (!(varType instanceof ReferenceType)) throw new TypeMismatchException("Variable is not a reference type!");
        IType inner = ((ReferenceType)varType).getInner();

        if (!inner.equals(expType)) throw new TypeMismatchException("Left and right hand side operands of" +
                "heap write have different types!");

        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return "HeapWrite(" + variableName + ", " + expression + ")";
    }
}
