package Model.Collections;

import java.util.Map;

public interface IHeap<Integer, IValue> {
    int getFreeLocation();
    IValue get(Integer key) throws HeapException;
    void put(Integer key, IValue value);
    void remove(Integer key);
    boolean containsKey(Integer key);
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> newContent);
    String toLogString();
}