package Model.Statements;

import Model.Collections.*;
import Model.Expressions.ExpressionException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        ICustomStack<IStatement> newStack = new CustomStack<>();
        ICustomDictionary<String, IValue> newSymbolTable = state.getSymbolTable().copy();

        return new ProgramState(newStack, newSymbolTable, state.getOutput(), state.getFileTable(),
                state.getHeap(), ProgramState.generateNewID(), statement);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        statement.typeCheck(typeEnvironment.copy());
        return typeEnvironment;

    }

    @Override
    public String toString() {
        return "Fork(" + statement + ")";
    }
}
