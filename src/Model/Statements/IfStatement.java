package Model.Statements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.ICustomStack;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class IfStatement implements IStatement{
    IExpression expression;
    IStatement thenBranch;
    IStatement elseBranch;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        thenBranch = thenStatement;
        elseBranch = elseStatement;
    }

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN (" + thenBranch.toString() + ") ELSE ( " +
                elseBranch.toString() + "))";
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression, thenBranch, elseBranch);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType expType = expression.typeCheck(typeEnvironment);

        if (!expType.equals(new BoolType())) throw new TypeMismatchException("If condition is not a boolean!");

        thenBranch.typeCheck(typeEnvironment.copy());
        elseBranch.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        ICustomStack<IStatement> stack = state.getExecutionStack();
        ICustomDictionary<String, IValue> symbolTable = state.getSymbolTable();

        IValue value = expression.eval(symbolTable, state.getHeap());
        BoolValue result = (BoolValue)value;
        if (result.getValue()) stack.push(thenBranch);
        else stack.push(elseBranch);
        return null;
    }
}
