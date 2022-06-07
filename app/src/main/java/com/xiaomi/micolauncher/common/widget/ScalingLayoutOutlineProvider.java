package com.xiaomi.micolauncher.common.widget;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: classes3.dex */
public class ScalingLayoutOutlineProvider extends ViewOutlineProvider {
    private int height;
    private float radius;
    private int width;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScalingLayoutOutlineProvider(int i, int i2, float f) {
        this.width = i;
        this.height = i2;
        this.radius = f;
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        if (view.getBackground() != null) {
            view.getBackground().getOutline(outline);
        }
        outline.setAlpha(0.4f);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float f) {
        this.radius = f;
    }
}
