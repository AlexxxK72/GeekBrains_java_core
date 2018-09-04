package lesson3;

import lesson2.Array;

public class Stack {
    private Array stack;
    private int head;

    public Stack(int size) {
        this.stack = new Array(size);
        this.head = -1;
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public void push(int value) {
        //stack[++head] = value;
        stack.append(value);
        head++;
    }

    public int pop() {
        //if (!isEmpty()) return stack[head--];
        int val;
        try {
            val = stack.get(head--);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Stack is Empty");
        }
        stack.remove();
        return val;
    }

    public int peek() {
        //if (!isEmpty()) return stack[head];
        int val;
        try {
            val = stack.get(head);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Stack is Empty");
        }
        return val;
    }
}