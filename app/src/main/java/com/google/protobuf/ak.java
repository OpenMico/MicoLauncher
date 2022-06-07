package com.google.protobuf;

import com.google.protobuf.MapEntryLite;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Reader.java */
/* loaded from: classes2.dex */
public interface ak {
    int a() throws IOException;

    <T> T a(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    <T> T a(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    void a(List<Double> list) throws IOException;

    <T> void a(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    <K, V> void a(Map<K, V> map, MapEntryLite.a<K, V> aVar, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    int b();

    @Deprecated
    <T> T b(am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    @Deprecated
    <T> T b(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    void b(List<Float> list) throws IOException;

    @Deprecated
    <T> void b(List<T> list, am<T> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    void c(List<Long> list) throws IOException;

    boolean c() throws IOException;

    double d() throws IOException;

    void d(List<Long> list) throws IOException;

    float e() throws IOException;

    void e(List<Integer> list) throws IOException;

    long f() throws IOException;

    void f(List<Long> list) throws IOException;

    long g() throws IOException;

    void g(List<Integer> list) throws IOException;

    int h() throws IOException;

    void h(List<Boolean> list) throws IOException;

    long i() throws IOException;

    void i(List<String> list) throws IOException;

    int j() throws IOException;

    void j(List<String> list) throws IOException;

    void k(List<ByteString> list) throws IOException;

    boolean k() throws IOException;

    String l() throws IOException;

    void l(List<Integer> list) throws IOException;

    String m() throws IOException;

    void m(List<Integer> list) throws IOException;

    ByteString n() throws IOException;

    void n(List<Integer> list) throws IOException;

    int o() throws IOException;

    void o(List<Long> list) throws IOException;

    int p() throws IOException;

    void p(List<Integer> list) throws IOException;

    int q() throws IOException;

    void q(List<Long> list) throws IOException;

    long r() throws IOException;

    int s() throws IOException;

    long t() throws IOException;
}
