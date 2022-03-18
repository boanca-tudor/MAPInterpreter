package Model.Collections;

import java.util.List;

public interface ICustomStack<TElement> {
    TElement pop();
    void push(TElement newElement);
    boolean isEmpty();
    String toLogString();
    List<TElement> getContent();
}
