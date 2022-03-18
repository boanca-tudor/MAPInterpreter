package Model.Expressions;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.TypeMismatchException;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class LogicExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private LogicSymbol operator;

    public LogicExpression(IExpression expression1, IExpression expression2, char symbol) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        switch(symbol)
        {
            case '|' -> operator = LogicSymbol.OR;
            case '&' -> operator = LogicSymbol.AND;
        }
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException, HeapException {
        IValue value1, value2;
        value1 = expression1.eval(table, heap);
        value2 = expression2.eval(table, heap);

        BoolValue bool1 = (BoolValue)value1;
        BoolValue bool2 = (BoolValue)value2;

        switch (operator) {
            case AND -> {
                return new BoolValue(bool1.getValue() && bool2.getValue());
            }
            case OR -> {
                return new BoolValue(bool1.getValue() || bool2.getValue());
            }
        }

        return null;
    }

    @Override
    public IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (!type1.equals(new BoolType()))
            throw new TypeMismatchException("First expression must be a boolean!");
        if (!type2.equals(new BoolType()))
            throw new TypeMismatchException("Second expression must be a boolean!");
        return new BoolType();
    }

    @Override
    public String toString()
    {
        switch(operator)
        {
            case OR -> {
                return expression1.toString() + " || " + expression2.toString();
            }
            case AND -> {
                return expression1.toString() + " && " + expression2.toString();
            }
        }
        return null;
    }

}

enum LogicSymbol {
    AND,
    OR
}
