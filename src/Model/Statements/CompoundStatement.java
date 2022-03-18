package Model.Statements;

import Model.Collections.ICustomDictionary;
import Model.Collections.ICustomStack;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;

public class CompoundStatement implements IStatement{
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first, second);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        ICustomStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
}