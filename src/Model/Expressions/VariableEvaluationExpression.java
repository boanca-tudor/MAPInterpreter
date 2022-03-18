package Model.Expressions;

import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.IType;
import Model.Values.IValue;

public class VariableEvaluationExpression implements IExpression {
    private String id;

    public VariableEvaluationExpression(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException {
        if (!table.containsKey(id)) throw new ExpressionException("Variable " + id + " is not defined");
        return table.get(id);
    }

    @Override
    public IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) {
        return typeEnvironment.get(id);
    }

    @Override
    public String toString()
    {
        return id;
    }
}
