package Model.Statements;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Expressions.ExpressionException;
import Model.Expressions.IExpression;
import Model.ProgramState;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;

public class AssignStatement implements IStatement{
    private String id;
    private IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return id + "=" + expression;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, expression);
    }

    @Override
    public ICustomDictionary<String, IType> typeCheck(ICustomDictionary<String, IType> typeEnvironment)
            throws TypeMismatchException {
        IType varType = typeEnvironment.get(id);
        IType expType = expression.typeCheck(typeEnvironment);

        if (varType.equals(expType))
            return typeEnvironment;
        throw new TypeMismatchException("Right hand side and left hand side operands have different types!");
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, HeapException {
        ICustomDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.containsKey(id)) {
            IValue value = expression.eval(symbolTable, state.getHeap());
            symbolTable.put(id, value);
        }
        else throw new StatementException("Variable" + id + " is not declared");
        return null;
    }
}
