package Model.Collections;

import java.util.Map;

public interface ICustomDictionary<TKey, TValue> {
    TValue get(TKey key);
    boolean isEmpty();
    void put(TKey key, TValue value);
    void remove(TKey key);
    boolean containsKey(TKey key);
    String toLogString();
    Map<TKey, TValue> getContent();
    ICustomDictionary<TKey, TValue> copy();
}