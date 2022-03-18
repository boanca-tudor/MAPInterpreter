package Model.Types;

import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
}
