package com.fasterxml.jackson.core.sym;

/* loaded from: classes.dex */
public final class Name1 extends Name {
    private static final Name1 a = new Name1("", 0, 0);
    private final int b;

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return false;
    }

    Name1(String str, int i, int i2) {
        super(str, i);
        this.b = i2;
    }

    public static Name1 getEmptyName() {
        return a;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return i == this.b;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return i == this.b && i2 == 0;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        return i == 1 && iArr[0] == this.b;
    }
}
