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
import Model.Values.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private RelationalOperator operator;

    public RelationalExpression(IExpression expression1, IExpression expression2, String operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;

        switch(operator) {
            case "<" -> this.operator = RelationalOperator.LESSER;
            case "<=" -> this.operator = RelationalOperator.LESSER_OR_EQUAL;
            case "==" -> this.operator = RelationalOperator.EQUAL;
            case "!=" -> this.operator = RelationalOperator.NOT_EQUAL;
            case ">=" -> this.operator = RelationalOperator.GREATER_OR_EQUAL;
            case ">" -> this.operator = RelationalOperator.GREATER;
        }
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException, HeapException {
        IValue value1, value2;
        value1 = expression1.eval(table, heap);
        value2 = expression2.eval(table, heap);

        IntValue int1 = (IntValue)value1;
        IntValue int2 = (IntValue)value2;

        switch (operator) {
            case LESSER -> {
                return new BoolValue(int1.getValue() < int2.getValue());
            }
            case LESSER_OR_EQUAL -> {
                return new BoolValue(int1.getValue() <= int2.getValue());
            }
            case EQUAL -> {
                return new BoolValue(int1.getValue() == int2.getValue());
            }
            case NOT_EQUAL -> {
                return new BoolValue(int1.getValue() != int2.getValue());
            }
            case GREATER -> {
                return new BoolValue(int1.getValue() > int2.getValue());
            }
            case GREATER_OR_EQUAL -> {
                return new BoolValue(int1.getValue() >= int2.getValue());
            }
        }

        return null;
    }

    @Override
    public IType typeCheck(ICustomDictionary<String, IType> typeEnvironment) throws TypeMismatchException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (!type1.equals(new IntType()))
            throw new TypeMismatchException("First expression must be an integer!");
        if (!type2.equals(new IntType()))
            throw new TypeMismatchException("Second expression must be an integer!");
        return new BoolType();
    }

    @Override
    public String toString()
    {
        switch(operator)
        {
            case LESSER -> {
                return expression1.toString() + " < " + expression2.toString();
            }
            case LESSER_OR_EQUAL -> {
                return expression1.toString() + " <= " + expression2.toString();
            }
            case EQUAL -> {
                return expression1.toString() + " == " + expression2.toString();
            }
            case NOT_EQUAL -> {
                return expression1.toString() + " != " + expression2.toString();
            }
            case GREATER -> {
                return expression1.toString() + " > " + expression2.toString();
            }
            case GREATER_OR_EQUAL -> {
                return expression1.toString() + " >= " + expression2.toString();
            }
        }
        return null;
    }
}

enum RelationalOperator {
    LESSER,
    LESSER_OR_EQUAL,
    EQUAL,
    NOT_EQUAL,
    GREATER,
    GREATER_OR_EQUAL
}
