package com.google.protobuf;

import com.google.protobuf.MapEntryLite;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface Writer {

    /* loaded from: classes2.dex */
    public enum FieldOrder {
        ASCENDING,
        DESCENDING
    }

    FieldOrder a();

    @Deprecated
    void a(int i) throws IOException;

    void a(int i, double d) throws IOException;

    void a(int i, float f) throws IOException;

    void a(int i, int i2) throws IOException;

    void a(int i, long j) throws IOException;

    void a(int i, ByteString byteString) throws IOException;

    <K, V> void a(int i, MapEntryLite.a<K, V> aVar, Map<K, V> map) throws IOException;

    void a(int i, Object obj) throws IOException;

    void a(int i, Object obj, am amVar) throws IOException;

    void a(int i, String str) throws IOException;

    void a(int i, List<String> list) throws IOException;

    void a(int i, List<?> list, am amVar) throws IOException;

    void a(int i, List<Integer> list, boolean z) throws IOException;

    void a(int i, boolean z) throws IOException;

    @Deprecated
    void b(int i) throws IOException;

    void b(int i, int i2) throws IOException;

    void b(int i, long j) throws IOException;

    @Deprecated
    void b(int i, Object obj, am amVar) throws IOException;

    void b(int i, List<ByteString> list) throws IOException;

    @Deprecated
    void b(int i, List<?> list, am amVar) throws IOException;

    void b(int i, List<Integer> list, boolean z) throws IOException;

    void c(int i, int i2) throws IOException;

    void c(int i, long j) throws IOException;

    void c(int i, List<Long> list, boolean z) throws IOException;

    void d(int i, int i2) throws IOException;

    void d(int i, long j) throws IOException;

    void d(int i, List<Long> list, boolean z) throws IOException;

    void e(int i, int i2) throws IOException;

    void e(int i, long j) throws IOException;

    void e(int i, List<Long> list, boolean z) throws IOException;

    void f(int i, int i2) throws IOException;

    void f(int i, List<Float> list, boolean z) throws IOException;

    void g(int i, List<Double> list, boolean z) throws IOException;

    void h(int i, List<Integer> list, boolean z) throws IOException;

    void i(int i, List<Boolean> list, boolean z) throws IOException;

    void j(int i, List<Integer> list, boolean z) throws IOException;

    void k(int i, List<Integer> list, boolean z) throws IOException;

    void l(int i, List<Long> list, boolean z) throws IOException;

    void m(int i, List<Integer> list, boolean z) throws IOException;

    void n(int i, List<Long> list, boolean z) throws IOException;
}
