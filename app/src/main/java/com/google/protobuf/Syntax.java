package com.google.protobuf;

import com.google.protobuf.Internal;

/* loaded from: classes2.dex */
public enum Syntax implements Internal.EnumLite {
    SYNTAX_PROTO2(0),
    SYNTAX_PROTO3(1),
    UNRECOGNIZED(-1);
    
    public static final int SYNTAX_PROTO2_VALUE = 0;
    public static final int SYNTAX_PROTO3_VALUE = 1;
    private static final Internal.EnumLiteMap<Syntax> a = new Internal.EnumLiteMap<Syntax>() { // from class: com.google.protobuf.Syntax.1
        /* renamed from: a */
        public Syntax findValueByNumber(int i) {
            return Syntax.forNumber(i);
        }
    };
    private final int value;

    @Override // com.google.protobuf.Internal.EnumLite
    public final int getNumber() {
        if (this != UNRECOGNIZED) {
            return this.value;
        }
        throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
    }

    @Deprecated
    public static Syntax valueOf(int i) {
        return forNumber(i);
    }

    public static Syntax forNumber(int i) {
        switch (i) {
            case 0:
                return SYNTAX_PROTO2;
            case 1:
                return SYNTAX_PROTO3;
            default:
                return null;
        }
    }

    public static Internal.EnumLiteMap<Syntax> internalGetValueMap() {
        return a;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return a.a;
    }

    /* loaded from: classes2.dex */
    private static final class a implements Internal.EnumVerifier {
        static final Internal.EnumVerifier a = new a();

        private a() {
        }

        @Override // com.google.protobuf.Internal.EnumVerifier
        public boolean isInRange(int i) {
            return Syntax.forNumber(i) != null;
        }
    }

    Syntax(int i) {
        this.value = i;
    }
}
