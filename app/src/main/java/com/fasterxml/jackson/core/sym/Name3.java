package com.fasterxml.jackson.core.sym;

/* loaded from: classes.dex */
public final class Name3 extends Name {
    private final int a;
    private final int b;
    private final int c;

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return this.a == i && this.b == i2 && this.c == i3;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        return i == 3 && iArr[0] == this.a && iArr[1] == this.b && iArr[2] == this.c;
    }
}
