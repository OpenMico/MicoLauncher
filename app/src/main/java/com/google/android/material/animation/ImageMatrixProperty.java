package com.google.android.material.animation;

import android.graphics.Matrix;
import android.util.Property;
import android.widget.ImageView;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public class ImageMatrixProperty extends Property<ImageView, Matrix> {
    private final Matrix a = new Matrix();

    public ImageMatrixProperty() {
        super(Matrix.class, "imageMatrixProperty");
    }

    public void set(@NonNull ImageView imageView, @NonNull Matrix matrix) {
        imageView.setImageMatrix(matrix);
    }

    @NonNull
    public Matrix get(@NonNull ImageView imageView) {
        this.a.set(imageView.getImageMatrix());
        return this.a;
    }
}
