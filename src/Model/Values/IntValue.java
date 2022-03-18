package Model.Values;

import Model.Types.IType;
import Model.Types.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue)) return false;
        return value == ((IntValue)other).getValue();
    }
}
