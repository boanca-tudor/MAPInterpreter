package Model.Types;

import Model.Values.IValue;
import Model.Values.ReferenceValue;

public class ReferenceType implements IType {
    IType inner;

    public ReferenceType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ReferenceType) return inner.equals(((ReferenceType)other).getInner());
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
