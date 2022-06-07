package com.fasterxml.jackson.core.sym;

/* loaded from: classes.dex */
public final class Name2 extends Name {
    private final int a;
    private final int b;

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return i == this.a && i2 == this.b;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] iArr, int i) {
        return i == 2 && iArr[0] == this.a && iArr[1] == this.b;
    }
}
