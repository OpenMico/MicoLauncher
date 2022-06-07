package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.ResourceDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ResourceDecoderRegistry {
    private final List<String> a = new ArrayList();
    private final Map<String, List<a<?, ?>>> b = new HashMap();

    public synchronized void setBucketPriorityList(@NonNull List<String> list) {
        ArrayList<String> arrayList = new ArrayList(this.a);
        this.a.clear();
        for (String str : list) {
            this.a.add(str);
        }
        for (String str2 : arrayList) {
            if (!list.contains(str2)) {
                this.a.add(str2);
            }
        }
    }

    @NonNull
    public synchronized <T, R> List<ResourceDecoder<T, R>> getDecoders(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.a) {
            List<a<?, ?>> list = this.b.get(str);
            if (list != null) {
                for (a<?, ?> aVar : list) {
                    if (aVar.a(cls, cls2)) {
                        arrayList.add(aVar.b);
                    }
                }
            }
        }
        return arrayList;
    }

    @NonNull
    public synchronized <T, R> List<Class<R>> getResourceClasses(@NonNull Class<T> cls, @NonNull Class<R> cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.a) {
            List<a<?, ?>> list = this.b.get(str);
            if (list != null) {
                for (a<?, ?> aVar : list) {
                    if (aVar.a(cls, cls2) && !arrayList.contains(aVar.a)) {
                        arrayList.add(aVar.a);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized <T, R> void append(@NonNull String str, @NonNull ResourceDecoder<T, R> resourceDecoder, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        a(str).add(new a<>(cls, cls2, resourceDecoder));
    }

    public synchronized <T, R> void prepend(@NonNull String str, @NonNull ResourceDecoder<T, R> resourceDecoder, @NonNull Class<T> cls, @NonNull Class<R> cls2) {
        a(str).add(0, new a<>(cls, cls2, resourceDecoder));
    }

    @NonNull
    private synchronized List<a<?, ?>> a(@NonNull String str) {
        List<a<?, ?>> list;
        if (!this.a.contains(str)) {
            this.a.add(str);
        }
        list = this.b.get(str);
        if (list == null) {
            list = new ArrayList<>();
            this.b.put(str, list);
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a<T, R> {
        final Class<R> a;
        final ResourceDecoder<T, R> b;
        private final Class<T> c;

        public a(@NonNull Class<T> cls, @NonNull Class<R> cls2, ResourceDecoder<T, R> resourceDecoder) {
            this.c = cls;
            this.a = cls2;
            this.b = resourceDecoder;
        }

        public boolean a(@NonNull Class<?> cls, @NonNull Class<?> cls2) {
            return this.c.isAssignableFrom(cls) && cls2.isAssignableFrom(this.a);
        }
    }
}
