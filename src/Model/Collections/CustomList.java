package Model.Collections;

import java.util.ArrayList;
import java.util.List;

public class CustomList<TElement> implements ICustomList<TElement> {
    private List<TElement> list;

    public CustomList() {
        list = new ArrayList<TElement>();
    }

    @Override
    public void add(TElement newElem) {
        list.add(newElem);
    }

    @Override
    public void insert(int position, TElement newElem) {
        list.add(position, newElem);
    }

    @Override
    public TElement get(int position) {
        return list.get(position);
    }

    @Override
    public TElement remove(int position) {
        return list.remove(position);
    }

    @Override
    public void set(int position, TElement element) {
        list.set(position, element);
    }

    @Override
    public List<TElement> getContent() {
        return list;
    }

    @Override
    public String toLogString() {
        StringBuilder str = new StringBuilder();
        for (TElement elem : list)
            str.append("\n").append(elem.toString());
        return str.toString();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
