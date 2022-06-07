package com.bumptech.glide.load.engine.bitmap_recycle;

/* compiled from: ArrayAdapterInterface.java */
/* loaded from: classes.dex */
interface a<T> {
    int getArrayLength(T t);

    int getElementSizeInBytes();

    String getTag();

    T newArray(int i);
}
