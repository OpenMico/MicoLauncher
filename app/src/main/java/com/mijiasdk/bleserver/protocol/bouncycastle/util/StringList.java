package com.mijiasdk.bleserver.protocol.bouncycastle.util;

/* loaded from: classes2.dex */
public interface StringList extends Iterable<String> {
    boolean add(String str);

    String get(int i);

    int size();

    String[] toStringArray();

    String[] toStringArray(int i, int i2);
}
