package com.google.android.material.transition.platform;

import androidx.annotation.RequiresApi;

/* compiled from: FadeModeEvaluators.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
class b {
    private static final a a = new a() { // from class: com.google.android.material.transition.platform.b.1
        @Override // com.google.android.material.transition.platform.a
        public c a(float f, float f2, float f3, float f4) {
            return c.b(255, j.a(0, 255, f2, f3, f));
        }
    };
    private static final a b = new a() { // from class: com.google.android.material.transition.platform.b.2
        @Override // com.google.android.material.transition.platform.a
        public c a(float f, float f2, float f3, float f4) {
            return c.a(j.a(255, 0, f2, f3, f), 255);
        }
    };
    private static final a c = new a() { // from class: com.google.android.material.transition.platform.b.3
        @Override // com.google.android.material.transition.platform.a
        public c a(float f, float f2, float f3, float f4) {
            return c.a(j.a(255, 0, f2, f3, f), j.a(0, 255, f2, f3, f));
        }
    };
    private static final a d = new a() { // from class: com.google.android.material.transition.platform.b.4
        @Override // com.google.android.material.transition.platform.a
        public c a(float f, float f2, float f3, float f4) {
            float f5 = ((f3 - f2) * f4) + f2;
            return c.a(j.a(255, 0, f2, f5, f), j.a(0, 255, f5, f3, f));
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a(int i, boolean z) {
        switch (i) {
            case 0:
                return z ? a : b;
            case 1:
                return z ? b : a;
            case 2:
                return c;
            case 3:
                return d;
            default:
                throw new IllegalArgumentException("Invalid fade mode: " + i);
        }
    }
}
