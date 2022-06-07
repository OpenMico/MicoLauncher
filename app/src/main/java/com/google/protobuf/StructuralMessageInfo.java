package com.google.protobuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
final class StructuralMessageInfo implements w {
    private final ProtoSyntax a;
    private final boolean b;
    private final int[] c;
    private final FieldInfo[] d;
    private final MessageLite e;

    StructuralMessageInfo(ProtoSyntax protoSyntax, boolean z, int[] iArr, FieldInfo[] fieldInfoArr, Object obj) {
        this.a = protoSyntax;
        this.b = z;
        this.c = iArr;
        this.d = fieldInfoArr;
        this.e = (MessageLite) Internal.a(obj, "defaultInstance");
    }

    @Override // com.google.protobuf.w
    public ProtoSyntax a() {
        return this.a;
    }

    @Override // com.google.protobuf.w
    public boolean b() {
        return this.b;
    }

    public int[] d() {
        return this.c;
    }

    public FieldInfo[] e() {
        return this.d;
    }

    @Override // com.google.protobuf.w
    public MessageLite c() {
        return this.e;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final List<FieldInfo> a;
        private ProtoSyntax b;
        private boolean c;
        private boolean d;
        private int[] e;
        private Object f;

        public Builder() {
            this.e = null;
            this.a = new ArrayList();
        }

        public Builder(int i) {
            this.e = null;
            this.a = new ArrayList(i);
        }

        public void withDefaultInstance(Object obj) {
            this.f = obj;
        }

        public void withSyntax(ProtoSyntax protoSyntax) {
            this.b = (ProtoSyntax) Internal.a(protoSyntax, "syntax");
        }

        public void withMessageSetWireFormat(boolean z) {
            this.d = z;
        }

        public void withCheckInitialized(int[] iArr) {
            this.e = iArr;
        }

        public void withField(FieldInfo fieldInfo) {
            if (!this.c) {
                this.a.add(fieldInfo);
                return;
            }
            throw new IllegalStateException("Builder can only build once");
        }

        public StructuralMessageInfo build() {
            if (this.c) {
                throw new IllegalStateException("Builder can only build once");
            } else if (this.b != null) {
                this.c = true;
                Collections.sort(this.a);
                return new StructuralMessageInfo(this.b, this.d, this.e, (FieldInfo[]) this.a.toArray(new FieldInfo[0]), this.f);
            } else {
                throw new IllegalStateException("Must specify a proto syntax");
            }
        }
    }
}
