package Model.Statements;

import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;

public class NOPStatement implements IStatement {

    public NOPStatement() {

    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NOPStatement();
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        return typeEnvironment;
    }
}
