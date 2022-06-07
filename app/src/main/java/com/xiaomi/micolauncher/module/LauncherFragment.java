package com.xiaomi.micolauncher.module;

import android.view.View;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.base.BaseFragment;

/* loaded from: classes3.dex */
public class LauncherFragment extends BaseFragment implements PageTransformer {
    @Override // com.xiaomi.micolauncher.module.PageTransformer
    public void transformPage(int i, int i2, @NonNull View view, float f) {
        float f2;
        if (Math.abs(f) <= 1.0f) {
            if (getActivity() instanceof PageTransformer) {
                ((PageTransformer) getActivity()).transformPage(i, i2, view, f);
            }
            if (i == i2) {
                f2 = Math.max(0.0f, 1.0f - (Math.abs(f) / 0.5f));
            } else {
                f2 = Math.min(1.0f, (1.0f - Math.abs(f)) / 0.5f);
            }
            getView().setAlpha(f2);
        }
    }
}
