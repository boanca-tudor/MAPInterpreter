package Model.Statements.FileStatements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Statements.StatementException;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        if (!state.getSymbolTable().containsKey(variableName)) {
            throw new StatementException("Variable not declared");
        }

        IValue expressionResult = expression.eval(state.getSymbolTable(), state.getHeap());
        StringValue resultString = (StringValue)expressionResult;
        if (!state.getFileTable().containsKey(resultString))
            throw new StatementException("There is no file with the given name!");

        try {
            String line = state.getFileTable().get(resultString).readLine();

            if (line == null)
                state.getSymbolTable().put(variableName, new IntType().defaultValue());
            else
                state.getSymbolTable().put(variableName, new IntValue(Integer.parseInt(line)));
        }
        catch (IOException ex) {
            throw new StatementException("An error occurred while reading from the file!");
        }

        return null;
    }

    @Override
    public String toString() {
        return "ReadFile(" + expression.toString() + ", " + variableName + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression, variableName);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType varType = typeEnvironment.get(variableName);
        IType expType = expression.typeCheck(typeEnvironment);

        if (!expType.equals(new StringType())) throw new TypeMismatchException("Expression does not evaluate to a string!");
        if (!varType.equals(new IntType())) throw new TypeMismatchException("Variable does not evaluate to int!");

        return typeEnvironment;
    }
}
