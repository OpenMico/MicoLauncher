package com.google.zxing.datamatrix.encoder;

/* compiled from: C40Encoder.java */
/* loaded from: classes2.dex */
public class c implements f {
    public int a() {
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.f
    public void a(g gVar) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!gVar.g()) {
                break;
            }
            char b = gVar.b();
            gVar.a++;
            int a = a(b, sb);
            int d = gVar.d() + ((sb.length() / 3) << 1);
            gVar.c(d);
            int dataCapacity = gVar.i().getDataCapacity() - d;
            if (gVar.g()) {
                if (sb.length() % 3 == 0 && HighLevelEncoder.a(gVar.a(), gVar.a, a()) != a()) {
                    gVar.b(0);
                    break;
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (dataCapacity < 2 || dataCapacity > 2)) {
                    a = a(gVar, sb, sb2, a);
                }
                while (sb.length() % 3 == 1 && ((a <= 3 && dataCapacity != 1) || a > 3)) {
                    a = a(gVar, sb, sb2, a);
                }
            }
        }
        b(gVar, sb);
    }

    private int a(g gVar, StringBuilder sb, StringBuilder sb2, int i) {
        int length = sb.length();
        sb.delete(length - i, length);
        gVar.a--;
        int a = a(gVar.b(), sb2);
        gVar.k();
        return a;
    }

    static void a(g gVar, StringBuilder sb) {
        gVar.a(a(sb, 0));
        sb.delete(0, 3);
    }

    void b(g gVar, StringBuilder sb) {
        int length = sb.length() % 3;
        int d = gVar.d() + ((sb.length() / 3) << 1);
        gVar.c(d);
        int dataCapacity = gVar.i().getDataCapacity() - d;
        if (length == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                a(gVar, sb);
            }
            if (gVar.g()) {
                gVar.a((char) 254);
            }
        } else if (dataCapacity == 1 && length == 1) {
            while (sb.length() >= 3) {
                a(gVar, sb);
            }
            if (gVar.g()) {
                gVar.a((char) 254);
            }
            gVar.a--;
        } else if (length == 0) {
            while (sb.length() >= 3) {
                a(gVar, sb);
            }
            if (dataCapacity > 0 || gVar.g()) {
                gVar.a((char) 254);
            }
        } else {
            throw new IllegalStateException("Unexpected case. Please report!");
        }
        gVar.b(0);
    }

    int a(char c, StringBuilder sb) {
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        } else if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
            return 1;
        } else if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
            return 1;
        } else if (c >= 0 && c <= 31) {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        } else if (c >= '!' && c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        } else if (c >= ':' && c <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c - ':') + 15));
            return 2;
        } else if (c >= '[' && c <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c - '[') + 22));
            return 2;
        } else if (c >= '`' && c <= 127) {
            sb.append((char) 2);
            sb.append((char) (c - '`'));
            return 2;
        } else if (c >= 128) {
            sb.append("\u0001\u001e");
            return a((char) (c - 128), sb) + 2;
        } else {
            throw new IllegalArgumentException("Illegal character: " + c);
        }
    }

    private static String a(CharSequence charSequence, int i) {
        int charAt = (charSequence.charAt(i) * 1600) + (charSequence.charAt(i + 1) * '(') + charSequence.charAt(i + 2) + 1;
        return new String(new char[]{(char) (charAt / 256), (char) (charAt % 256)});
    }
}
