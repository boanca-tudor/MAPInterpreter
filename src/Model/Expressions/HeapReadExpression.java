package Model.Expressions;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.IType;
import Model.Types.ReferenceType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;
import Model.Values.ReferenceValue;

import java.lang.ref.Reference;

public class HeapReadExpression implements IExpression {
    private IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException,
            HeapException {
        IValue expressionResult = expression.eval(table, heap);
        ReferenceValue value = (ReferenceValue)expressionResult;

        if (!heap.containsKey(value.getAddress()))
            throw new ExpressionException("The heap does not contain the address of the expression!");

        return heap.get(value.getAddress());
    }

    @Override
    public IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType type = expression.typeCheck(typeEnvironment);

        if (!(type instanceof ReferenceType referenceType))
            throw new TypeMismatchException("The operand is not a reference type!");

        return referenceType.getInner();
    }

    @Override
    public String toString() {
        return "HeapRead(" + expression + ")";
    }
}
