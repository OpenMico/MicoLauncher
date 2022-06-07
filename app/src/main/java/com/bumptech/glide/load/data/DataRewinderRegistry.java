package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class DataRewinderRegistry {
    private static final DataRewinder.Factory<?> b = new DataRewinder.Factory<Object>() { // from class: com.bumptech.glide.load.data.DataRewinderRegistry.1
        @Override // com.bumptech.glide.load.data.DataRewinder.Factory
        @NonNull
        public DataRewinder<Object> build(@NonNull Object obj) {
            return new a(obj);
        }

        @Override // com.bumptech.glide.load.data.DataRewinder.Factory
        @NonNull
        public Class<Object> getDataClass() {
            throw new UnsupportedOperationException("Not implemented");
        }
    };
    private final Map<Class<?>, DataRewinder.Factory<?>> a = new HashMap();

    public synchronized void register(@NonNull DataRewinder.Factory<?> factory) {
        this.a.put(factory.getDataClass(), factory);
    }

    @NonNull
    public synchronized <T> DataRewinder<T> build(@NonNull T t) {
        DataRewinder.Factory<?> factory;
        Preconditions.checkNotNull(t);
        factory = this.a.get(t.getClass());
        if (factory == null) {
            Iterator<DataRewinder.Factory<?>> it = this.a.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DataRewinder.Factory<?> next = it.next();
                if (next.getDataClass().isAssignableFrom(t.getClass())) {
                    factory = next;
                    break;
                }
            }
        }
        if (factory == null) {
            factory = b;
        }
        return (DataRewinder<T>) factory.build(t);
    }

    /* loaded from: classes.dex */
    private static final class a implements DataRewinder<Object> {
        private final Object a;

        @Override // com.bumptech.glide.load.data.DataRewinder
        public void cleanup() {
        }

        a(@NonNull Object obj) {
            this.a = obj;
        }

        @Override // com.bumptech.glide.load.data.DataRewinder
        @NonNull
        public Object rewindAndGet() {
            return this.a;
        }
    }
}
