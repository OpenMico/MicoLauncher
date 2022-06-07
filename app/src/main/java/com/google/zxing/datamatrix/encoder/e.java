package com.google.zxing.datamatrix.encoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EdifactEncoder.java */
/* loaded from: classes2.dex */
public final class e implements f {
    public int a() {
        return 4;
    }

    @Override // com.google.zxing.datamatrix.encoder.f
    public void a(g gVar) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!gVar.g()) {
                break;
            }
            a(gVar.b(), sb);
            gVar.a++;
            if (sb.length() >= 4) {
                gVar.a(a(sb, 0));
                sb.delete(0, 4);
                if (HighLevelEncoder.a(gVar.a(), gVar.a, a()) != a()) {
                    gVar.b(0);
                    break;
                }
            }
        }
        sb.append((char) 31);
        a(gVar, sb);
    }

    private static void a(g gVar, CharSequence charSequence) {
        try {
            int length = charSequence.length();
            if (length != 0) {
                boolean z = true;
                if (length == 1) {
                    gVar.j();
                    int dataCapacity = gVar.i().getDataCapacity() - gVar.d();
                    if (gVar.h() <= dataCapacity && dataCapacity <= 2) {
                        return;
                    }
                }
                if (length <= 4) {
                    int i = length - 1;
                    String a = a(charSequence, 0);
                    if (!(!gVar.g()) || i > 2) {
                        z = false;
                    }
                    if (i <= 2) {
                        gVar.c(gVar.d() + i);
                        if (gVar.i().getDataCapacity() - gVar.d() >= 3) {
                            gVar.c(gVar.d() + a.length());
                            z = false;
                        }
                    }
                    if (z) {
                        gVar.k();
                        gVar.a -= i;
                    } else {
                        gVar.a(a);
                    }
                    return;
                }
                throw new IllegalStateException("Count must not exceed 4");
            }
        } finally {
            gVar.b(0);
        }
    }

    private static void a(char c, StringBuilder sb) {
        if (c >= ' ' && c <= '?') {
            sb.append(c);
        } else if (c < '@' || c > '^') {
            HighLevelEncoder.c(c);
        } else {
            sb.append((char) (c - '@'));
        }
    }

    private static String a(CharSequence charSequence, int i) {
        int length = charSequence.length() - i;
        if (length != 0) {
            char charAt = charSequence.charAt(i);
            char c = 0;
            char charAt2 = length >= 2 ? charSequence.charAt(i + 1) : (char) 0;
            char charAt3 = length >= 3 ? charSequence.charAt(i + 2) : (char) 0;
            if (length >= 4) {
                c = charSequence.charAt(i + 3);
            }
            int i2 = (charAt << 18) + (charAt2 << '\f') + (charAt3 << 6) + c;
            char c2 = (char) ((i2 >> 8) & 255);
            char c3 = (char) (i2 & 255);
            StringBuilder sb = new StringBuilder(3);
            sb.append((char) ((i2 >> 16) & 255));
            if (length >= 2) {
                sb.append(c2);
            }
            if (length >= 3) {
                sb.append(c3);
            }
            return sb.toString();
        }
        throw new IllegalStateException("StringBuilder must not be empty");
    }
}
