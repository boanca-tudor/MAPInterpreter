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

public class NewStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public NewStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        if (!state.getSymbolTable().containsKey(variableName))
            throw new ExpressionException("Symbol table does not contain the variable " + variableName);

        if (!(state.getSymbolTable().get(variableName).getType() instanceof ReferenceType))
            throw new ExpressionException("Variable is not a reference type");

        IValue expressionValue = expression.eval(state.getSymbolTable(), state.getHeap());
        ReferenceValue variableValue = (ReferenceValue)state.getSymbolTable().get(variableName);
        IType type = ((ReferenceType)variableValue.getType()).getInner();

        if (!expressionValue.getType().equals(type))
            throw new ExpressionException("Expression and variable do not evaluate to the same type!");

        int location = state.getHeap().getFreeLocation();
        state.getHeap().put(location, expressionValue);

        state.getSymbolTable().put(variableName, new ReferenceValue(location, type));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(variableName, expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType varType = typeEnvironment.get(variableName);
        IType expType = expression.typeCheck(typeEnvironment);

        if (!varType.equals(new ReferenceType(expType))) throw new TypeMismatchException("Left and right hand side" +
                "operands of new have different types!");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ")";
    }
}
