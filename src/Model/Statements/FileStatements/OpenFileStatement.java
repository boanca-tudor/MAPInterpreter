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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements IStatement {
    private IExpression expression;

    public OpenFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        IValue expressionResult = expression.eval(state.getSymbolTable(), state.getHeap());

        StringValue fileName = (StringValue)expressionResult;

        if (state.getFileTable().containsKey(fileName))
            throw new StatementException("File is already open!");

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName.getValue()));
            state.getFileTable().put(fileName, fileReader);
        }
        catch (FileNotFoundException ex) {
            throw new StatementException("The file with the given name does not exist!");
        }
        return null;
    }

    @Override
    public String toString() {
        return "openFile(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new OpenFileStatement(expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType type = expression.typeCheck(typeEnvironment);

        if (!type.equals(new StringType())) throw new TypeMismatchException("Expression is not a string type!");
        return typeEnvironment;
    }
}
