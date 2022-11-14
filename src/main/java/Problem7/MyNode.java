package Problem7;

public class MyNode<T> {
    public MyNode<T> prev, next;
    public T value;

    public MyNode() {}
    public MyNode(T value) { this.value = value; }
    public MyNode(MyNode<T> prev, MyNode<T> next, T value) {
        this.prev = prev;
        this.next = next;
        this.value = value;
    }

    public MyNode<T> getPrev() { return prev; }
    public MyNode<T> getNext() { return next; }
    public void setValue(T value) { this.value = value; }
    public T getValue() { return value; }
}
