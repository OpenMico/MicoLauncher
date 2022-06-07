package com.google.zxing.datamatrix.encoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: X12Encoder.java */
/* loaded from: classes2.dex */
public final class i extends c {
    @Override // com.google.zxing.datamatrix.encoder.c
    public int a() {
        return 3;
    }

    @Override // com.google.zxing.datamatrix.encoder.c, com.google.zxing.datamatrix.encoder.f
    public void a(g gVar) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!gVar.g()) {
                break;
            }
            char b = gVar.b();
            gVar.a++;
            a(b, sb);
            if (sb.length() % 3 == 0) {
                a(gVar, sb);
                if (HighLevelEncoder.a(gVar.a(), gVar.a, a()) != a()) {
                    gVar.b(0);
                    break;
                }
            }
        }
        b(gVar, sb);
    }

    @Override // com.google.zxing.datamatrix.encoder.c
    int a(char c, StringBuilder sb) {
        if (c == '\r') {
            sb.append((char) 0);
        } else if (c == ' ') {
            sb.append((char) 3);
        } else if (c == '*') {
            sb.append((char) 1);
        } else if (c == '>') {
            sb.append((char) 2);
        } else if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
        } else if (c < 'A' || c > 'Z') {
            HighLevelEncoder.c(c);
        } else {
            sb.append((char) ((c - 'A') + 14));
        }
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.c
    void b(g gVar, StringBuilder sb) {
        gVar.j();
        int dataCapacity = gVar.i().getDataCapacity() - gVar.d();
        gVar.a -= sb.length();
        if (gVar.h() > 1 || dataCapacity > 1 || gVar.h() != dataCapacity) {
            gVar.a((char) 254);
        }
        if (gVar.e() < 0) {
            gVar.b(0);
        }
    }
}
