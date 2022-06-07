package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.Internal;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: SchemaUtil.java */
/* loaded from: classes2.dex */
public final class ao {
    private static final Class<?> a = d();
    private static final ar<?, ?> b = a(false);
    private static final ar<?, ?> c = a(true);
    private static final ar<?, ?> d = new as();

    public static void a(Class<?> cls) {
        Class<?> cls2;
        if (!GeneratedMessageLite.class.isAssignableFrom(cls) && (cls2 = a) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void a(int i, List<Double> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.g(i, list, z);
        }
    }

    public static void b(int i, List<Float> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.f(i, list, z);
        }
    }

    public static void c(int i, List<Long> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.c(i, list, z);
        }
    }

    public static void d(int i, List<Long> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.d(i, list, z);
        }
    }

    public static void e(int i, List<Long> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.n(i, list, z);
        }
    }

    public static void f(int i, List<Long> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.e(i, list, z);
        }
    }

    public static void g(int i, List<Long> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.l(i, list, z);
        }
    }

    public static void h(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.a(i, list, z);
        }
    }

    public static void i(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.j(i, list, z);
        }
    }

    public static void j(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.m(i, list, z);
        }
    }

    public static void k(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.b(i, list, z);
        }
    }

    public static void l(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.k(i, list, z);
        }
    }

    public static void m(int i, List<Integer> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.h(i, list, z);
        }
    }

    public static void n(int i, List<Boolean> list, Writer writer, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.i(i, list, z);
        }
    }

    public static void a(int i, List<String> list, Writer writer) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.a(i, list);
        }
    }

    public static void b(int i, List<ByteString> list, Writer writer) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.b(i, list);
        }
    }

    public static void a(int i, List<?> list, Writer writer, am amVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.a(i, list, amVar);
        }
    }

    public static void b(int i, List<?> list, Writer writer, am amVar) throws IOException {
        if (list != null && !list.isEmpty()) {
            writer.b(i, list, amVar);
        }
    }

    public static int a(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof r) {
            r rVar = (r) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt64SizeNoTag(rVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int a(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        int a2 = a(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(a2);
        }
        return a2 + (list.size() * CodedOutputStream.computeTagSize(i));
    }

    public static int b(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof r) {
            r rVar = (r) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt64SizeNoTag(rVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int b(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int b2 = b(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(b2);
        }
        return b2 + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int c(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof r) {
            r rVar = (r) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeSInt64SizeNoTag(rVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeSInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int c(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int c2 = c(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(c2);
        }
        return c2 + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int d(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeEnumSizeNoTag(oVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeEnumSizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int d(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int d2 = d(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(d2);
        }
        return d2 + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int e(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt32SizeNoTag(oVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int e(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int e = e(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(e);
        }
        return e + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int f(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt32SizeNoTag(oVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int f(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int f = f(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(f);
        }
        return f + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int g(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof o) {
            o oVar = (o) list;
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeSInt32SizeNoTag(oVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeSInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int g(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int g = g(list);
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(g);
        }
        return g + (size * CodedOutputStream.computeTagSize(i));
    }

    public static int h(List<?> list) {
        return list.size() * 4;
    }

    public static int h(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(size * 4);
        }
        return size * CodedOutputStream.computeFixed32Size(i, 0);
    }

    public static int i(List<?> list) {
        return list.size() * 8;
    }

    public static int i(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(size * 8);
        }
        return size * CodedOutputStream.computeFixed64Size(i, 0L);
    }

    public static int j(List<?> list) {
        return list.size();
    }

    public static int j(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return CodedOutputStream.computeTagSize(i) + CodedOutputStream.b(size);
        }
        return size * CodedOutputStream.computeBoolSize(i, true);
    }

    public static int a(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        if (list instanceof LazyStringList) {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i4 < size) {
                Object raw = lazyStringList.getRaw(i4);
                if (raw instanceof ByteString) {
                    i3 = CodedOutputStream.computeBytesSizeNoTag((ByteString) raw);
                } else {
                    i3 = CodedOutputStream.computeStringSizeNoTag((String) raw);
                }
                computeTagSize += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof ByteString) {
                    i2 = CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
                } else {
                    i2 = CodedOutputStream.computeStringSizeNoTag((String) obj);
                }
                computeTagSize += i2;
                i4++;
            }
        }
        return computeTagSize;
    }

    public static int a(int i, Object obj, am amVar) {
        if (obj instanceof LazyFieldLite) {
            return CodedOutputStream.computeLazyFieldSize(i, (LazyFieldLite) obj);
        }
        return CodedOutputStream.b(i, (MessageLite) obj, amVar);
    }

    public static int a(int i, List<?> list, am amVar) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof LazyFieldLite) {
                i2 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj);
            } else {
                i2 = CodedOutputStream.b((MessageLite) obj, amVar);
            }
            computeTagSize += i2;
        }
        return computeTagSize;
    }

    public static int b(int i, List<ByteString> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = size * CodedOutputStream.computeTagSize(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            computeTagSize += CodedOutputStream.computeBytesSizeNoTag(list.get(i2));
        }
        return computeTagSize;
    }

    public static int b(int i, List<MessageLite> list, am amVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += CodedOutputStream.d(i, list.get(i3), amVar);
        }
        return i2;
    }

    public static ar<?, ?> a() {
        return b;
    }

    public static ar<?, ?> b() {
        return c;
    }

    public static ar<?, ?> c() {
        return d;
    }

    private static ar<?, ?> a(boolean z) {
        try {
            Class<?> e = e();
            if (e == null) {
                return null;
            }
            return (ar) e.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> d() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessageV3");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> e() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static <T> void a(t tVar, T t, T t2, long j) {
        at.a(t, j, tVar.a(at.f(t, j), at.f(t2, j)));
    }

    public static <T, FT extends FieldSet.FieldDescriptorLite<FT>> void a(j<FT> jVar, T t, T t2) {
        FieldSet<FT> a2 = jVar.a(t2);
        if (!a2.c()) {
            jVar.b(t).a(a2);
        }
    }

    public static <T, UT, UB> void a(ar<UT, UB> arVar, T t, T t2) {
        arVar.a(t, arVar.c(arVar.b(t), arVar.b(t2)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <UT, UB> UB a(int i, List<Integer> list, Internal.EnumLiteMap<?> enumLiteMap, UB ub, ar<UT, UB> arVar) {
        UB ub2;
        if (enumLiteMap == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (enumLiteMap.findValueByNumber(intValue) != null) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub2 = (UB) a(i, intValue, ub2, arVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            ub2 = ub;
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (enumLiteMap.findValueByNumber(intValue2) == null) {
                    Object a2 = a(i, intValue2, ub2, arVar);
                    it.remove();
                    ub2 = a2;
                }
            }
        }
        return ub2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <UT, UB> UB a(int i, List<Integer> list, Internal.EnumVerifier enumVerifier, UB ub, ar<UT, UB> arVar) {
        UB ub2;
        if (enumVerifier == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (enumVerifier.isInRange(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub2 = (UB) a(i, intValue, ub2, arVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            ub2 = ub;
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!enumVerifier.isInRange(intValue2)) {
                    Object a2 = a(i, intValue2, ub2, arVar);
                    it.remove();
                    ub2 = a2;
                }
            }
        }
        return ub2;
    }

    public static <UT, UB> UB a(int i, int i2, UB ub, ar<UT, UB> arVar) {
        if (ub == null) {
            ub = arVar.a();
        }
        arVar.a((ar<UT, UB>) ub, i, i2);
        return ub;
    }
}
