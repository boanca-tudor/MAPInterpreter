package Model.Collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CustomStack<TElement> implements ICustomStack<TElement> {
    private Stack<TElement> stack;

    public CustomStack() {
        stack = new Stack<TElement>();
    }

    @Override
    public TElement pop() {
        return stack.pop();
    }

    @Override
    public void push(TElement newElement) {
        stack.push(newElement);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toLogString() {
        return getReversed().toString();
    }

    @Override
    public List<TElement> getContent() {
        return getReversed();
    }

    public List<TElement> getReversed() {
        List<TElement> list= Arrays.asList((TElement[])stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return getReversed().toString();
    }
}
