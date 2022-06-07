package com.google.android.material.transition.platform;

import android.graphics.RectF;
import androidx.annotation.RequiresApi;

/* compiled from: FitModeEvaluators.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
class e {
    private static final d a = new d() { // from class: com.google.android.material.transition.platform.e.1
        @Override // com.google.android.material.transition.platform.d
        public f a(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float a2 = j.a(f4, f6, f2, f3, f, true);
            float f8 = a2 / f4;
            float f9 = a2 / f6;
            return new f(f8, f9, a2, f5 * f8, a2, f7 * f9);
        }

        @Override // com.google.android.material.transition.platform.d
        public boolean a(f fVar) {
            return fVar.d > fVar.f;
        }

        @Override // com.google.android.material.transition.platform.d
        public void a(RectF rectF, float f, f fVar) {
            rectF.bottom -= Math.abs(fVar.f - fVar.d) * f;
        }
    };
    private static final d b = new d() { // from class: com.google.android.material.transition.platform.e.2
        @Override // com.google.android.material.transition.platform.d
        public f a(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float a2 = j.a(f5, f7, f2, f3, f, true);
            float f8 = a2 / f5;
            float f9 = a2 / f7;
            return new f(f8, f9, f4 * f8, a2, f6 * f9, a2);
        }

        @Override // com.google.android.material.transition.platform.d
        public boolean a(f fVar) {
            return fVar.c > fVar.e;
        }

        @Override // com.google.android.material.transition.platform.d
        public void a(RectF rectF, float f, f fVar) {
            float abs = (Math.abs(fVar.e - fVar.c) / 2.0f) * f;
            rectF.left += abs;
            rectF.right -= abs;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public static d a(int i, boolean z, RectF rectF, RectF rectF2) {
        switch (i) {
            case 0:
                return a(z, rectF, rectF2) ? a : b;
            case 1:
                return a;
            case 2:
                return b;
            default:
                throw new IllegalArgumentException("Invalid fit mode: " + i);
        }
    }

    private static boolean a(boolean z, RectF rectF, RectF rectF2) {
        float width = rectF.width();
        float height = rectF.height();
        float width2 = rectF2.width();
        float height2 = rectF2.height();
        float f = (height2 * width) / width2;
        float f2 = (width2 * height) / width;
        if (z) {
            if (f >= height) {
                return true;
            }
        } else if (f2 >= height2) {
            return true;
        }
        return false;
    }
}
