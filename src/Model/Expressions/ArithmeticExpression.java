package Model.Expressions;

import Model.Collections.HeapException;
import Model.Collections.ICustomDictionary;
import Model.Collections.IHeap;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.TypeMismatchException;
import Model.Values.IValue;
import Model.Values.IntValue;

public class ArithmeticExpression implements IExpression {
    private IExpression expression1;
    private IExpression expression2;
    private ArithmeticSymbol operator;

    public ArithmeticExpression(IExpression expression1, IExpression expression2, char symbol) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        switch (symbol) {
            case '+' -> operator = ArithmeticSymbol.PLUS;
            case '-' -> operator = ArithmeticSymbol.MINUS;
            case '*' -> operator = ArithmeticSymbol.MULTIPLY;
            case '/' -> operator = ArithmeticSymbol.DIVIDE;
        }
    }

    @Override
    public IValue eval(ICustomDictionary<String, IValue> table, IHeap<Integer, IValue> heap) throws ExpressionException, HeapException {
        IValue value1, value2;
        value1 = expression1.eval(table, heap);
        value2 = expression2.eval(table, heap);

        IntValue integer1 = (IntValue)value1;
        IntValue integer2 = (IntValue)value2;

        switch (operator) {
            case PLUS -> {
                return new IntValue(integer1.getValue() + integer2.getValue());
            }
            case MINUS -> {
                return new IntValue(integer1.getValue() - integer2.getValue());
            }
            case MULTIPLY -> {
                return new IntValue(integer1.getValue() * integer2.getValue());
            }
            case DIVIDE -> {
                if (integer2.getValue() == 0) throw new ExpressionException("Division by zero");
                return new IntValue(integer1.getValue() / integer2.getValue());
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
        return new IntType();
    }

    @Override
    public String toString()
    {
        switch(operator) {
            case PLUS -> {
                return expression1.toString() + " + " + expression2.toString();
            }
            case MINUS -> {
                return expression1.toString() + " - " + expression2.toString();
            }
            case MULTIPLY -> {
                return expression1.toString() + " * " + expression2.toString();
            }
            case DIVIDE -> {
                return expression1.toString() + " / " + expression2.toString();
            }
        }
        return null;
    }
}

enum ArithmeticSymbol {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE
}