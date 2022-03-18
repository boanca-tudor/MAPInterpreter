package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StringValue)) return false;
        return Objects.equals(value, ((StringValue) other).getValue());
    }
}
