package Model.Expressions;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.IType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;

public interface IExpression {
    IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException,
            HeapException;

    IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException;
}
