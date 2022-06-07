package com.fasterxml.jackson.databind.util;

/* loaded from: classes.dex */
public final class LinkedNode<T> {
    private final T a;
    private LinkedNode<T> b;

    public LinkedNode(T t, LinkedNode<T> linkedNode) {
        this.a = t;
        this.b = linkedNode;
    }

    public void linkNext(LinkedNode<T> linkedNode) {
        if (this.b == null) {
            this.b = linkedNode;
            return;
        }
        throw new IllegalStateException();
    }

    public LinkedNode<T> next() {
        return this.b;
    }

    public T value() {
        return this.a;
    }

    public static <ST> boolean contains(LinkedNode<ST> linkedNode, ST st) {
        while (linkedNode != null) {
            if (linkedNode.value() == st) {
                return true;
            }
            linkedNode = linkedNode.next();
        }
        return false;
    }
}
