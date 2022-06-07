package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ListFieldSchema.java */
/* loaded from: classes2.dex */
public abstract class q {
    private static final q a = new a();
    private static final q b = new b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <L> List<L> a(Object obj, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <L> void a(Object obj, Object obj2, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b(Object obj, long j);

    private q() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static q a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static q b() {
        return b;
    }

    /* compiled from: ListFieldSchema.java */
    /* loaded from: classes2.dex */
    private static final class a extends q {
        private static final Class<?> a = Collections.unmodifiableList(Collections.emptyList()).getClass();

        private a() {
            super();
        }

        @Override // com.google.protobuf.q
        <L> List<L> a(Object obj, long j) {
            return a(obj, j, 10);
        }

        @Override // com.google.protobuf.q
        void b(Object obj, long j) {
            Object obj2;
            List list = (List) at.f(obj, j);
            if (list instanceof LazyStringList) {
                obj2 = ((LazyStringList) list).getUnmodifiableView();
            } else if (!a.isAssignableFrom(list.getClass())) {
                if (!(list instanceof ag) || !(list instanceof Internal.ProtobufList)) {
                    obj2 = Collections.unmodifiableList(list);
                } else {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                    if (protobufList.isModifiable()) {
                        protobufList.makeImmutable();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
            at.a(obj, j, obj2);
        }

        private static <L> List<L> a(Object obj, long j, int i) {
            List<L> list;
            List<L> c = c(obj, j);
            if (c.isEmpty()) {
                if (c instanceof LazyStringList) {
                    list = new LazyStringArrayList(i);
                } else if (!(c instanceof ag) || !(c instanceof Internal.ProtobufList)) {
                    list = new ArrayList<>(i);
                } else {
                    list = ((Internal.ProtobufList) c).mutableCopyWithCapacity(i);
                }
                at.a(obj, j, list);
                return list;
            } else if (a.isAssignableFrom(c.getClass())) {
                ArrayList arrayList = new ArrayList(c.size() + i);
                arrayList.addAll(c);
                at.a(obj, j, arrayList);
                return arrayList;
            } else if (c instanceof UnmodifiableLazyStringList) {
                LazyStringArrayList lazyStringArrayList = new LazyStringArrayList(c.size() + i);
                lazyStringArrayList.addAll((UnmodifiableLazyStringList) c);
                at.a(obj, j, lazyStringArrayList);
                return lazyStringArrayList;
            } else if (!(c instanceof ag) || !(c instanceof Internal.ProtobufList)) {
                return c;
            } else {
                Internal.ProtobufList protobufList = (Internal.ProtobufList) c;
                if (protobufList.isModifiable()) {
                    return c;
                }
                Internal.ProtobufList mutableCopyWithCapacity = protobufList.mutableCopyWithCapacity(c.size() + i);
                at.a(obj, j, mutableCopyWithCapacity);
                return mutableCopyWithCapacity;
            }
        }

        @Override // com.google.protobuf.q
        <E> void a(Object obj, Object obj2, long j) {
            List c = c(obj2, j);
            List a2 = a(obj, j, c.size());
            int size = a2.size();
            int size2 = c.size();
            if (size > 0 && size2 > 0) {
                a2.addAll(c);
            }
            if (size > 0) {
                c = a2;
            }
            at.a(obj, j, c);
        }

        static <E> List<E> c(Object obj, long j) {
            return (List) at.f(obj, j);
        }
    }

    /* compiled from: ListFieldSchema.java */
    /* loaded from: classes2.dex */
    private static final class b extends q {
        private b() {
            super();
        }

        @Override // com.google.protobuf.q
        <L> List<L> a(Object obj, long j) {
            Internal.ProtobufList c = c(obj, j);
            if (c.isModifiable()) {
                return c;
            }
            int size = c.size();
            Internal.ProtobufList mutableCopyWithCapacity = c.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            at.a(obj, j, mutableCopyWithCapacity);
            return mutableCopyWithCapacity;
        }

        @Override // com.google.protobuf.q
        void b(Object obj, long j) {
            c(obj, j).makeImmutable();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.google.protobuf.Internal$ProtobufList] */
        @Override // com.google.protobuf.q
        <E> void a(Object obj, Object obj2, long j) {
            Internal.ProtobufList<E> c = c(obj, j);
            Internal.ProtobufList<E> c2 = c(obj2, j);
            int size = c.size();
            int size2 = c2.size();
            c2 = c;
            c2 = c;
            if (size > 0 && size2 > 0) {
                boolean isModifiable = c.isModifiable();
                Internal.ProtobufList<E> protobufList = c;
                if (!isModifiable) {
                    protobufList = c.mutableCopyWithCapacity(size2 + size);
                }
                protobufList.addAll(c2);
                c2 = protobufList;
            }
            if (size > 0) {
            }
            at.a(obj, j, c2);
        }

        static <E> Internal.ProtobufList<E> c(Object obj, long j) {
            return (Internal.ProtobufList) at.f(obj, j);
        }
    }
}
