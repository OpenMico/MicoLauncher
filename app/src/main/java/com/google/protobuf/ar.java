package com.google.protobuf;

import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnknownFieldSchema.java */
/* loaded from: classes2.dex */
public abstract class ar<T, B> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract B a();

    abstract T a(B b);

    abstract void a(B b, int i, int i2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(B b, int i, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(B b, int i, ByteString byteString);

    abstract void a(B b, int i, T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(T t, Writer writer) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(Object obj, T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a(ak akVar);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract T b(Object obj);

    abstract void b(B b, int i, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b(T t, Writer writer) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b(Object obj, B b);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract B c(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract T c(T t, T t2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void d(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int e(T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int f(T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a(B b, ak akVar) throws IOException {
        int b2 = akVar.b();
        int tagFieldNumber = WireFormat.getTagFieldNumber(b2);
        switch (WireFormat.getTagWireType(b2)) {
            case 0:
                a((ar<T, B>) b, tagFieldNumber, akVar.g());
                return true;
            case 1:
                b(b, tagFieldNumber, akVar.i());
                return true;
            case 2:
                a((ar<T, B>) b, tagFieldNumber, akVar.n());
                return true;
            case 3:
                B a = a();
                int a2 = WireFormat.a(tagFieldNumber, 4);
                b((ar<T, B>) a, akVar);
                if (a2 == akVar.b()) {
                    a((ar<T, B>) b, tagFieldNumber, (int) a((ar<T, B>) a));
                    return true;
                }
                throw InvalidProtocolBufferException.e();
            case 4:
                return false;
            case 5:
                a((ar<T, B>) b, tagFieldNumber, akVar.j());
                return true;
            default:
                throw InvalidProtocolBufferException.f();
        }
    }

    final void b(B b, ak akVar) throws IOException {
        while (akVar.a() != Integer.MAX_VALUE && a((ar<T, B>) b, akVar)) {
        }
    }
}
