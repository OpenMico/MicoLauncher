package com.squareup.javapoet.x2c;

import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LineWrapper.java */
/* loaded from: classes2.dex */
public final class b {
    private final Appendable a;
    private final String b;
    private final int c;
    private boolean d;
    private final StringBuilder e = new StringBuilder();
    private int f = 0;
    private int g = -1;
    private a h;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: LineWrapper.java */
    /* loaded from: classes2.dex */
    public enum a {
        WRAP,
        SPACE,
        EMPTY
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Appendable appendable, String str, int i) {
        c.a(appendable, "out == null", new Object[0]);
        this.a = appendable;
        this.b = str;
        this.c = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str) throws IOException {
        int i;
        if (!this.d) {
            if (this.h != null) {
                int indexOf = str.indexOf(10);
                if (indexOf != -1 || this.f + str.length() > this.c) {
                    a(indexOf == -1 || this.f + indexOf > this.c ? a.WRAP : this.h);
                } else {
                    this.e.append(str);
                    this.f += str.length();
                    return;
                }
            }
            this.a.append(str);
            int lastIndexOf = str.lastIndexOf(10);
            if (lastIndexOf != -1) {
                i = (str.length() - lastIndexOf) - 1;
            } else {
                i = str.length() + this.f;
            }
            this.f = i;
            return;
        }
        throw new IllegalStateException("closed");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) throws IOException {
        if (!this.d) {
            a aVar = this.h;
            if (aVar != null) {
                a(aVar);
            }
            this.f++;
            this.h = a.SPACE;
            this.g = i;
            return;
        }
        throw new IllegalStateException("closed");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i) throws IOException {
        if (this.d) {
            throw new IllegalStateException("closed");
        } else if (this.f != 0) {
            a aVar = this.h;
            if (aVar != null) {
                a(aVar);
            }
            this.h = a.EMPTY;
            this.g = i;
        }
    }

    private void a(a aVar) throws IOException {
        switch (aVar) {
            case WRAP:
                this.a.append('\n');
                int i = 0;
                while (true) {
                    int i2 = this.g;
                    if (i >= i2) {
                        this.f = i2 * this.b.length();
                        this.f += this.e.length();
                        break;
                    } else {
                        this.a.append(this.b);
                        i++;
                    }
                }
            case SPACE:
                this.a.append(' ');
                break;
            case EMPTY:
                break;
            default:
                throw new IllegalArgumentException("Unknown FlushType: " + aVar);
        }
        this.a.append(this.e);
        StringBuilder sb = this.e;
        sb.delete(0, sb.length());
        this.g = -1;
        this.h = null;
    }
}
