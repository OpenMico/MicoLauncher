package com.google.protobuf;

/* compiled from: RawMessageInfo.java */
/* loaded from: classes2.dex */
final class aj implements w {
    private final MessageLite a;
    private final String b;
    private final Object[] c;
    private final int d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aj(MessageLite messageLite, String str, Object[] objArr) {
        this.a = messageLite;
        this.b = str;
        this.c = objArr;
        char charAt = str.charAt(0);
        if (charAt < 55296) {
            this.d = charAt;
            return;
        }
        int i = charAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            i3++;
            char charAt2 = str.charAt(i3);
            if (charAt2 >= 55296) {
                i |= (charAt2 & 8191) << i2;
                i2 += 13;
            } else {
                this.d = i | (charAt2 << i2);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String d() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object[] e() {
        return this.c;
    }

    @Override // com.google.protobuf.w
    public MessageLite c() {
        return this.a;
    }

    @Override // com.google.protobuf.w
    public ProtoSyntax a() {
        return (this.d & 1) == 1 ? ProtoSyntax.PROTO2 : ProtoSyntax.PROTO3;
    }

    @Override // com.google.protobuf.w
    public boolean b() {
        return (this.d & 2) == 2;
    }
}
