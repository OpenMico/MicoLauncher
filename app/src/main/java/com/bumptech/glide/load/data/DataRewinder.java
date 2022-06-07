package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import java.io.IOException;

/* loaded from: classes.dex */
public interface DataRewinder<T> {

    /* loaded from: classes.dex */
    public interface Factory<T> {
        @NonNull
        DataRewinder<T> build(@NonNull T t);

        @NonNull
        Class<T> getDataClass();
    }

    void cleanup();

    @NonNull
    T rewindAndGet() throws IOException;
}
