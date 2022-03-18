package Model.Collections;

import java.util.List;

public interface ICustomList<TElement> {
    void add(TElement newElem);
    void insert(int position, TElement newElem);
    TElement get(int position);
    TElement remove(int position);
    void set(int position, TElement element);
    List<TElement> getContent();
    String toLogString();
}
