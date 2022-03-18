package Model.Statements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        IValue expressionResult = expression.eval(state.getSymbolTable(), state.getHeap());

        BoolValue value = (BoolValue)expressionResult;

        if (value.getValue()) {
            state.getExecutionStack().push(deepCopy());
            state.getExecutionStack().push(statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression, statement);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType expType = expression.typeCheck(typeEnvironment);

        if (!expType.equals(new BoolType())) throw new TypeMismatchException("While condition not a boolean!");

        statement.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "while (" + expression + ") " + statement;
    }
}
