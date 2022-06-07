package com.xiaomi.micolauncher.skills.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes3.dex */
public class AsrText extends AppCompatTextView implements AsrTextInterface {
    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z, boolean z2) {
    }

    public AsrText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str) {
        super.setText((CharSequence) String.valueOf(str));
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z) {
        super.setText((CharSequence) String.valueOf(str));
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public ViewPropertyAnimator animate() {
        return super.animate();
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setAlpha(float f) {
        super.setAlpha(f);
    }
}
