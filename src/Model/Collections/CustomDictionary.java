package Model.Collections;

import java.util.HashMap;
import java.util.Map;

public class CustomDictionary<TKey, TValue> implements ICustomDictionary<TKey, TValue>{
    private Map<TKey, TValue> dictionary;

    public CustomDictionary() {
        dictionary = new HashMap<TKey, TValue>();
    }

    @Override
    public TValue get(TKey key) {
        return dictionary.get(key);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void put(TKey key, TValue value) {
         dictionary.put(key, value);
    }

    @Override
    public void remove(TKey key) {
        dictionary.remove(key);
    }

    @Override
    public boolean containsKey(TKey key) {
        return dictionary.containsKey(key);
    }

    @Override
    public String toLogString() {
            StringBuilder str = new StringBuilder();
            for (TKey key : dictionary.keySet())
                str.append("\n").append(key.toString()).append(" -> ").append(dictionary.get(key).toString());
            return str.toString();
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return dictionary;
    }

    @Override
    public ICustomDictionary<TKey, TValue> copy() {
        ICustomDictionary<TKey, TValue> newDictionary = new CustomDictionary<>();

        for (TKey key : dictionary.keySet())
            newDictionary.put(key, dictionary.get(key));

        return newDictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}
