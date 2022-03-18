package Model.Collections;

import Model.Values.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap<Integer, IValue> {
    private Map<Integer, IValue> heap;
    int freeLocation;

    public Heap() {
        heap = new HashMap<>();
        freeLocation = 1;
    }

    @Override
    public int getFreeLocation() {
        int copy = freeLocation;
        changeFreeLocation();
        return copy;
    }

    private void changeFreeLocation() {
        freeLocation += 1;
    }

    @Override
    public IValue get(Integer key) throws HeapException {
        if (key == 0) throw new HeapException("Address 0 is not a valid address!");

        return heap.get(key);
    }

    @Override
    public void put(Integer key, IValue value) {
        heap.put(key, value);
    }

    @Override
    public void remove(Integer key) {
        heap.remove(key);
    }

    @Override
    public boolean containsKey(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> newContent) {
        heap = newContent;
    }

    @Override
    public String toLogString() {
        return heap.toString();
    }
}
