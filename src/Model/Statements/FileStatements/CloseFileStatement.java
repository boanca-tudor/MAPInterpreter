package Model.Statements.FileStatements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Statements.StatementException;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.IOException;

public class CloseFileStatement implements IStatement {
    private IExpression expression;

    public CloseFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        IValue expressionResult = expression.eval(state.getSymbolTable(), state.getHeap());

        StringValue stringResult = (StringValue)expressionResult;
        if (!state.getFileTable().containsKey(stringResult))
            throw new StatementException("There is no open file with that name!");

        try {
            state.getFileTable().get(stringResult).close();
            state.getFileTable().remove(stringResult);
        }
        catch (IOException ex) {
            throw new StatementException("An error occurred while closing the file!");
        }

        return null;
    }

    @Override
    public String toString() {
        return "CloseFile(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileStatement(expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType type = expression.typeCheck(typeEnvironment);

        if (!type.equals(new StringType())) throw new TypeMismatchException("Expression is not a string type!");
        return typeEnvironment;
    }
}
