package Model.Statements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException;
    String toString();
    IStatement deepCopy();

    ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException;
}
