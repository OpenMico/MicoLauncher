package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.LazyField;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: MessageSetSchema.java */
/* loaded from: classes2.dex */
final class aa<T> implements am<T> {
    private final MessageLite a;
    private final ar<?, ?> b;
    private final boolean c;
    private final j<?> d;

    private aa(ar<?, ?> arVar, j<?> jVar, MessageLite messageLite) {
        this.b = arVar;
        this.c = jVar.a(messageLite);
        this.d = jVar;
        this.a = messageLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> aa<T> a(ar<?, ?> arVar, j<?> jVar, MessageLite messageLite) {
        return new aa<>(arVar, jVar, messageLite);
    }

    @Override // com.google.protobuf.am
    public T a() {
        return (T) this.a.newBuilderForType().buildPartial();
    }

    @Override // com.google.protobuf.am
    public boolean a(T t, T t2) {
        if (!this.b.b(t).equals(this.b.b(t2))) {
            return false;
        }
        if (this.c) {
            return this.d.a(t).equals(this.d.a(t2));
        }
        return true;
    }

    @Override // com.google.protobuf.am
    public int a(T t) {
        int hashCode = this.b.b(t).hashCode();
        return this.c ? (hashCode * 53) + this.d.a(t).hashCode() : hashCode;
    }

    @Override // com.google.protobuf.am
    public void b(T t, T t2) {
        ao.a(this.b, t, t2);
        if (this.c) {
            ao.a(this.d, t, t2);
        }
    }

    @Override // com.google.protobuf.am
    public void a(T t, Writer writer) throws IOException {
        Iterator<Map.Entry<?, Object>> g = this.d.a(t).g();
        while (g.hasNext()) {
            Map.Entry<?, Object> next = g.next();
            FieldSet.FieldDescriptorLite fieldDescriptorLite = (FieldSet.FieldDescriptorLite) next.getKey();
            if (fieldDescriptorLite.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fieldDescriptorLite.isRepeated() || fieldDescriptorLite.isPacked()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof LazyField.a) {
                writer.a(fieldDescriptorLite.getNumber(), (Object) ((LazyField.a) next).a().toByteString());
            } else {
                writer.a(fieldDescriptorLite.getNumber(), next.getValue());
            }
        }
        a(this.b, (ar) t, writer);
    }

    private <UT, UB> void a(ar<UT, UB> arVar, T t, Writer writer) throws IOException {
        arVar.b((ar<UT, UB>) arVar.b(t), writer);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ca A[SYNTHETIC] */
    @Override // com.google.protobuf.am
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(T r10, byte[] r11, int r12, int r13, com.google.protobuf.c.a r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
            com.google.protobuf.UnknownFieldSetLite r1 = r0.unknownFields
            com.google.protobuf.UnknownFieldSetLite r2 = com.google.protobuf.UnknownFieldSetLite.getDefaultInstance()
            if (r1 != r2) goto L_0x0011
            com.google.protobuf.UnknownFieldSetLite r1 = com.google.protobuf.UnknownFieldSetLite.a()
            r0.unknownFields = r1
        L_0x0011:
            com.google.protobuf.GeneratedMessageLite$ExtendableMessage r10 = (com.google.protobuf.GeneratedMessageLite.ExtendableMessage) r10
            com.google.protobuf.FieldSet r10 = r10.c()
            r0 = 0
            r2 = r0
        L_0x0019:
            if (r12 >= r13) goto L_0x00d6
            int r4 = com.google.protobuf.c.a(r11, r12, r14)
            int r12 = r14.a
            int r3 = com.google.protobuf.WireFormat.a
            r5 = 2
            if (r12 == r3) goto L_0x006c
            int r3 = com.google.protobuf.WireFormat.getTagWireType(r12)
            if (r3 != r5) goto L_0x0067
            com.google.protobuf.j<?> r2 = r9.d
            com.google.protobuf.ExtensionRegistryLite r3 = r14.d
            com.google.protobuf.MessageLite r5 = r9.a
            int r6 = com.google.protobuf.WireFormat.getTagFieldNumber(r12)
            java.lang.Object r2 = r2.a(r3, r5, r6)
            r8 = r2
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r8 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r8
            if (r8 == 0) goto L_0x005c
            com.google.protobuf.ah r12 = com.google.protobuf.ah.a()
            com.google.protobuf.MessageLite r2 = r8.getMessageDefaultInstance()
            java.lang.Class r2 = r2.getClass()
            com.google.protobuf.am r12 = r12.a(r2)
            int r12 = com.google.protobuf.c.a(r12, r11, r4, r13, r14)
            com.google.protobuf.GeneratedMessageLite$a r2 = r8.d
            java.lang.Object r3 = r14.c
            r10.a(r2, r3)
            r2 = r8
            goto L_0x0019
        L_0x005c:
            r2 = r12
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.protobuf.c.a(r2, r3, r4, r5, r6, r7)
            r2 = r8
            goto L_0x0019
        L_0x0067:
            int r12 = com.google.protobuf.c.a(r12, r11, r4, r13, r14)
            goto L_0x0019
        L_0x006c:
            r12 = 0
            r3 = r0
        L_0x006e:
            if (r4 >= r13) goto L_0x00ca
            int r4 = com.google.protobuf.c.a(r11, r4, r14)
            int r6 = r14.a
            int r7 = com.google.protobuf.WireFormat.getTagFieldNumber(r6)
            int r8 = com.google.protobuf.WireFormat.getTagWireType(r6)
            switch(r7) {
                case 2: goto L_0x00ab;
                case 3: goto L_0x0082;
                default: goto L_0x0081;
            }
        L_0x0081:
            goto L_0x00c0
        L_0x0082:
            if (r2 == 0) goto L_0x00a0
            com.google.protobuf.ah r6 = com.google.protobuf.ah.a()
            com.google.protobuf.MessageLite r7 = r2.getMessageDefaultInstance()
            java.lang.Class r7 = r7.getClass()
            com.google.protobuf.am r6 = r6.a(r7)
            int r4 = com.google.protobuf.c.a(r6, r11, r4, r13, r14)
            com.google.protobuf.GeneratedMessageLite$a r6 = r2.d
            java.lang.Object r7 = r14.c
            r10.a(r6, r7)
            goto L_0x006e
        L_0x00a0:
            if (r8 != r5) goto L_0x00c0
            int r4 = com.google.protobuf.c.e(r11, r4, r14)
            java.lang.Object r3 = r14.c
            com.google.protobuf.ByteString r3 = (com.google.protobuf.ByteString) r3
            goto L_0x006e
        L_0x00ab:
            if (r8 != 0) goto L_0x00c0
            int r4 = com.google.protobuf.c.a(r11, r4, r14)
            int r12 = r14.a
            com.google.protobuf.j<?> r2 = r9.d
            com.google.protobuf.ExtensionRegistryLite r6 = r14.d
            com.google.protobuf.MessageLite r7 = r9.a
            java.lang.Object r2 = r2.a(r6, r7, r12)
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r2 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r2
            goto L_0x006e
        L_0x00c0:
            int r7 = com.google.protobuf.WireFormat.b
            if (r6 != r7) goto L_0x00c5
            goto L_0x00ca
        L_0x00c5:
            int r4 = com.google.protobuf.c.a(r6, r11, r4, r13, r14)
            goto L_0x006e
        L_0x00ca:
            if (r3 == 0) goto L_0x00d3
            int r12 = com.google.protobuf.WireFormat.a(r12, r5)
            r1.a(r12, r3)
        L_0x00d3:
            r12 = r4
            goto L_0x0019
        L_0x00d6:
            if (r12 != r13) goto L_0x00d9
            return
        L_0x00d9:
            com.google.protobuf.InvalidProtocolBufferException r10 = com.google.protobuf.InvalidProtocolBufferException.i()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.aa.a(java.lang.Object, byte[], int, int, com.google.protobuf.c$a):void");
    }

    @Override // com.google.protobuf.am
    public void a(T t, ak akVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        a(this.b, this.d, (j) t, akVar, extensionRegistryLite);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void a(ar<UT, UB> arVar, j<ET> jVar, T t, ak akVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        UB c = arVar.c(t);
        FieldSet<ET> b = jVar.b(t);
        do {
            try {
                if (akVar.a() == Integer.MAX_VALUE) {
                    return;
                }
            } finally {
                arVar.b((Object) t, (T) c);
            }
        } while (a(akVar, extensionRegistryLite, jVar, b, arVar, c));
    }

    @Override // com.google.protobuf.am
    public void d(T t) {
        this.b.d(t);
        this.d.c(t);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean a(ak akVar, ExtensionRegistryLite extensionRegistryLite, j<ET> jVar, FieldSet<ET> fieldSet, ar<UT, UB> arVar, UB ub) throws IOException {
        int b = akVar.b();
        if (b == WireFormat.a) {
            int i = 0;
            Object obj = null;
            ByteString byteString = null;
            while (akVar.a() != Integer.MAX_VALUE) {
                int b2 = akVar.b();
                if (b2 == WireFormat.c) {
                    i = akVar.o();
                    obj = jVar.a(extensionRegistryLite, this.a, i);
                } else if (b2 == WireFormat.d) {
                    if (obj != null) {
                        jVar.a(akVar, obj, extensionRegistryLite, fieldSet);
                    } else {
                        byteString = akVar.n();
                    }
                } else if (!akVar.c()) {
                    break;
                }
            }
            if (akVar.b() == WireFormat.b) {
                if (byteString != null) {
                    if (obj != null) {
                        jVar.a(byteString, obj, extensionRegistryLite, fieldSet);
                    } else {
                        arVar.a((ar<UT, UB>) ub, i, byteString);
                    }
                }
                return true;
            }
            throw InvalidProtocolBufferException.e();
        } else if (WireFormat.getTagWireType(b) != 2) {
            return akVar.c();
        } else {
            Object a = jVar.a(extensionRegistryLite, this.a, WireFormat.getTagFieldNumber(b));
            if (a == null) {
                return arVar.a((ar<UT, UB>) ub, akVar);
            }
            jVar.a(akVar, a, extensionRegistryLite, fieldSet);
            return true;
        }
    }

    @Override // com.google.protobuf.am
    public final boolean e(T t) {
        return this.d.a(t).i();
    }

    @Override // com.google.protobuf.am
    public int b(T t) {
        int a = a(this.b, (ar) t) + 0;
        return this.c ? a + this.d.a(t).k() : a;
    }

    private <UT, UB> int a(ar<UT, UB> arVar, T t) {
        return arVar.e(arVar.b(t));
    }
}
