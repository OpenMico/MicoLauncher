package com.google.protobuf;

import java.io.IOException;

/* compiled from: UnknownFieldSetLiteSchema.java */
/* loaded from: classes2.dex */
class as extends ar<UnknownFieldSetLite, UnknownFieldSetLite> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ar
    public boolean a(ak akVar) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public UnknownFieldSetLite a() {
        return UnknownFieldSetLite.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UnknownFieldSetLite unknownFieldSetLite, int i, long j) {
        unknownFieldSetLite.a(WireFormat.a(i, 0), Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UnknownFieldSetLite unknownFieldSetLite, int i, int i2) {
        unknownFieldSetLite.a(WireFormat.a(i, 5), Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(UnknownFieldSetLite unknownFieldSetLite, int i, long j) {
        unknownFieldSetLite.a(WireFormat.a(i, 1), Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UnknownFieldSetLite unknownFieldSetLite, int i, ByteString byteString) {
        unknownFieldSetLite.a(WireFormat.a(i, 2), (Object) byteString);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UnknownFieldSetLite unknownFieldSetLite, int i, UnknownFieldSetLite unknownFieldSetLite2) {
        unknownFieldSetLite.a(WireFormat.a(i, 3), unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UnknownFieldSetLite a(UnknownFieldSetLite unknownFieldSetLite) {
        unknownFieldSetLite.makeImmutable();
        return unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Object obj, UnknownFieldSetLite unknownFieldSetLite) {
        ((GeneratedMessageLite) obj).unknownFields = unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: g */
    public UnknownFieldSetLite b(Object obj) {
        return ((GeneratedMessageLite) obj).unknownFields;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: h */
    public UnknownFieldSetLite c(Object obj) {
        UnknownFieldSetLite g = b(obj);
        if (g != UnknownFieldSetLite.getDefaultInstance()) {
            return g;
        }
        UnknownFieldSetLite a = UnknownFieldSetLite.a();
        a(obj, a);
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Object obj, UnknownFieldSetLite unknownFieldSetLite) {
        a(obj, unknownFieldSetLite);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ar
    public void d(Object obj) {
        b(obj).makeImmutable();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UnknownFieldSetLite unknownFieldSetLite, Writer writer) throws IOException {
        unknownFieldSetLite.writeTo(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(UnknownFieldSetLite unknownFieldSetLite, Writer writer) throws IOException {
        unknownFieldSetLite.a(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public UnknownFieldSetLite c(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        return unknownFieldSetLite2.equals(UnknownFieldSetLite.getDefaultInstance()) ? unknownFieldSetLite : UnknownFieldSetLite.a(unknownFieldSetLite, unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public int f(UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public int e(UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSizeAsMessageSet();
    }
}
