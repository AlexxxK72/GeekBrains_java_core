package lesson4;

import java.util.Objects;

public class DoubleRelatedList<T> implements MyIterator<T> {
    @Override
    public void reset() {
        iterator = head;
    }

    @Override
    public boolean atEnd() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        return iterator.next == null;
    }

    @Override
    public boolean atBegin() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        return iterator.prev == null;
    }

    private boolean listExists() {
        return iterator != null;
    }

    @Override
    public T deleteCurrent() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        T temp = iterator.c;
        if (atBegin() && atEnd()) {
            head = null;
            tail = null;
            reset();
        } else if (atBegin()) {
            head = iterator.next;
            head.prev = null;
            reset();
        } else if (atEnd()) {
            tail = iterator.prev;
            tail.next = null;
            iterator = iterator.next;
        } else {
            iterator.prev.next = iterator.next;
            iterator.next.prev = iterator.prev;
        }
        return temp;

    }

    @Override
    public void insertAfter(T c) {
        if (!listExists()) throw new RuntimeException("iterator is null");
        Node<T> temp = new Node<>(c);
        if (!atEnd()) {
            temp.next = iterator.next;
            iterator.next.prev = temp;
        } else {
            tail = temp;
        }
        iterator.next = temp;
        temp.prev = iterator;
    }

    @Override
    public void insertBefore(T c) {
        if (!listExists()) throw new RuntimeException("iterator is null");
        Node<T> temp = new Node<>(c);
        if (!atBegin()) {
            temp.prev = iterator.prev;
            iterator.prev.next = temp;
        } else {
            head = temp;
        }
        iterator.prev = temp;
        temp.next = iterator;
    }

    @Override
    public T getCurrent() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        return iterator.c;
    }

    @Override
    public boolean hasNext() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        return iterator.next != null;
    }

    @Override
    public T next() {
        if (!listExists()) throw new RuntimeException("iterator is null");
        iterator = iterator.next;
        return iterator.prev.c;
    }

    private class Node<T> {
        T c;
        Node<T> next;
        Node<T> prev;
        Node(T c) {
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(c, node.c);
        }

        @Override
        public int hashCode() {

            return Objects.hash(c);
        }

        @Override public String toString() {
            return c.toString();
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private Node<T> iterator;

    public DoubleRelatedList() {
        head = null;
        tail = null;
        iterator = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void push(Cat c) {
        Node n = new Node<>(c);
        n.next = head; // if (head == null) n.next = null;
        if (head == null) {
            tail = n;
        } else {
            head.prev = n;
        }
        reset();
        head = n;
    }

    public T pop() {
        if (isEmpty()) return null;
        T c = tail.c;
        if (tail.prev != null) {
            tail.prev.next = null;
            tail = tail.prev;
            iterator = head;
        } else {
            tail = null;
            head = null;
            iterator = null;
        }
        reset();
        return c;
    }

    public boolean contains(T c) {
        Node<T> n = new Node<>(c);
        Node<T> current = head;
        while (!current.equals(n)) {
            if (current.next == null) return false;
            else current = current.next;
        }
        return true;
    }

    public T delete(T c) {
        Node<T> n = new Node<>(c);
        Node<T> current = head;
        Node<T> previous = head;

        while (!current.equals(n)) {
            if (current.next == null) return null;
            else {
                previous = current;
                current = current.next;
            }
        }
        if (current == head && current == tail) {
            head = null;
            tail = null;
            iterator = null;
        } else if (current == head) {
            head = head.next;
            head.next.prev = null;
        } else if (current == tail) {
            tail = tail.prev;
            tail.prev.next = null;
        } else {
            previous.next = current.next;
            current.next.prev = previous;
        }

        return current.c;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        Node current = head;
        StringBuilder sb = new StringBuilder("[ ");
        while (current != null) {
            sb.append(current);
            current = current.next;
            sb.append((current == null) ? " ]" : ", ");
        }
        return sb.toString();
    }

}
