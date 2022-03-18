package Model.Expressions;

import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.IType;
import Model.Values.IValue;

public class ValueExpression implements IExpression {
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException {
        return value;
    }

    @Override
    public IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
