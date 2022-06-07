package com.google.zxing.datamatrix.encoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ASCIIEncoder.java */
/* loaded from: classes2.dex */
public final class a implements f {
    public int a() {
        return 0;
    }

    @Override // com.google.zxing.datamatrix.encoder.f
    public void a(g gVar) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(gVar.a(), gVar.a) >= 2) {
            gVar.a(a(gVar.a().charAt(gVar.a), gVar.a().charAt(gVar.a + 1)));
            gVar.a += 2;
            return;
        }
        char b = gVar.b();
        int a = HighLevelEncoder.a(gVar.a(), gVar.a, a());
        if (a != a()) {
            switch (a) {
                case 1:
                    gVar.a((char) 230);
                    gVar.b(1);
                    return;
                case 2:
                    gVar.a((char) 239);
                    gVar.b(2);
                    return;
                case 3:
                    gVar.a((char) 238);
                    gVar.b(3);
                    return;
                case 4:
                    gVar.a((char) 240);
                    gVar.b(4);
                    return;
                case 5:
                    gVar.a((char) 231);
                    gVar.b(5);
                    return;
                default:
                    throw new IllegalStateException("Illegal mode: " + a);
            }
        } else if (HighLevelEncoder.b(b)) {
            gVar.a((char) 235);
            gVar.a((char) ((b - 128) + 1));
            gVar.a++;
        } else {
            gVar.a((char) (b + 1));
            gVar.a++;
        }
    }

    private static char a(char c, char c2) {
        if (HighLevelEncoder.a(c) && HighLevelEncoder.a(c2)) {
            return (char) (((c - '0') * 10) + (c2 - '0') + 130);
        }
        throw new IllegalArgumentException("not digits: " + c + c2);
    }
}
