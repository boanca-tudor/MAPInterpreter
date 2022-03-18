package Model.Types;

import Model.Values.BoolValue;
import Model.Values.IValue;

public class BoolType implements IType {
    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
