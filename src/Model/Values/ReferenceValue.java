package Model.Values;

import Model.Types.IType;
import Model.Types.ReferenceType;

public class ReferenceValue implements IValue {
    int address;
    IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
