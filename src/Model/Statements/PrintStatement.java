package Model.Statements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.ICustomList;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;

public class PrintStatement implements IStatement{
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment)
            throws TypeMismatchException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        ICustomList<IValue> output = state.getOutput();
        ICustomDictionary<String, IValue> symbolTable = state.getSymbolTable();
        output.add(expression.eval(symbolTable, state.getHeap()));
        return null;
    }
}
