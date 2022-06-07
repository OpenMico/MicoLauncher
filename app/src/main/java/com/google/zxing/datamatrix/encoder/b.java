package com.google.zxing.datamatrix.encoder;

import androidx.core.view.InputDeviceCompat;
import com.alibaba.fastjson.asm.Opcodes;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Base256Encoder.java */
/* loaded from: classes2.dex */
public final class b implements f {
    public int a() {
        return 5;
    }

    @Override // com.google.zxing.datamatrix.encoder.f
    public void a(g gVar) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0);
        while (true) {
            if (!gVar.g()) {
                break;
            }
            sb.append(gVar.b());
            gVar.a++;
            if (HighLevelEncoder.a(gVar.a(), gVar.a, a()) != a()) {
                gVar.b(0);
                break;
            }
        }
        int length = sb.length() - 1;
        int d = gVar.d() + length + 1;
        gVar.c(d);
        boolean z = gVar.i().getDataCapacity() - d > 0;
        if (gVar.g() || z) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else if (length <= 1555) {
                sb.setCharAt(0, (char) ((length / 250) + 249));
                sb.insert(1, (char) (length % 250));
            } else {
                throw new IllegalStateException("Message length not in valid ranges: " + length);
            }
        }
        int length2 = sb.length();
        for (int i = 0; i < length2; i++) {
            gVar.a(a(sb.charAt(i), gVar.d() + 1));
        }
    }

    private static char a(char c, int i) {
        int i2 = c + ((i * Opcodes.FCMPL) % 255) + 1;
        return i2 <= 255 ? (char) i2 : (char) (i2 + InputDeviceCompat.SOURCE_ANY);
    }
}
