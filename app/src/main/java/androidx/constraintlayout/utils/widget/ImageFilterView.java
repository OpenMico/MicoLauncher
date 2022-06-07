package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class ImageFilterView extends AppCompatImageView {
    ViewOutlineProvider a;
    RectF b;
    Drawable[] c;
    LayerDrawable d;
    private a e = new a();
    private boolean f = true;
    private float g = 0.0f;
    private float h = 0.0f;
    private float i = Float.NaN;
    private Path j;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        float[] a = new float[20];
        ColorMatrix b = new ColorMatrix();
        ColorMatrix c = new ColorMatrix();
        float d = 1.0f;
        float e = 1.0f;
        float f = 1.0f;
        float g = 1.0f;

        private void a(float f) {
            float f2 = 1.0f - f;
            float f3 = 0.2999f * f2;
            float f4 = 0.587f * f2;
            float f5 = f2 * 0.114f;
            float[] fArr = this.a;
            fArr[0] = f3 + f;
            fArr[1] = f4;
            fArr[2] = f5;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = f3;
            fArr[6] = f4 + f;
            fArr[7] = f5;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = f3;
            fArr[11] = f4;
            fArr[12] = f5 + f;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void b(float f) {
            float f2;
            float f3;
            float f4;
            if (f <= 0.0f) {
                f = 0.01f;
            }
            float f5 = (5000.0f / f) / 100.0f;
            if (f5 > 66.0f) {
                double d = f5 - 60.0f;
                f3 = ((float) Math.pow(d, -0.13320475816726685d)) * 329.69873f;
                f2 = ((float) Math.pow(d, 0.07551484555006027d)) * 288.12216f;
            } else {
                f2 = (((float) Math.log(f5)) * 99.4708f) - 161.11957f;
                f3 = 255.0f;
            }
            if (f5 >= 66.0f) {
                f4 = 255.0f;
            } else if (f5 > 19.0f) {
                f4 = (((float) Math.log(f5 - 10.0f)) * 138.51773f) - 305.0448f;
            } else {
                f4 = 0.0f;
            }
            float min = Math.min(255.0f, Math.max(f3, 0.0f));
            float min2 = Math.min(255.0f, Math.max(f2, 0.0f));
            float min3 = Math.min(255.0f, Math.max(f4, 0.0f));
            float min4 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            float min5 = Math.min(255.0f, Math.max((((float) Math.log(50.0f)) * 99.4708f) - 161.11957f, 0.0f));
            float min6 = min3 / Math.min(255.0f, Math.max((((float) Math.log(40.0f)) * 138.51773f) - 305.0448f, 0.0f));
            float[] fArr = this.a;
            fArr[0] = min / min4;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = min2 / min5;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = min6;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void c(float f) {
            float[] fArr = this.a;
            fArr[0] = f;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = f;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = f;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(ImageView imageView) {
            boolean z;
            this.b.reset();
            float f = this.e;
            if (f != 1.0f) {
                a(f);
                this.b.set(this.a);
                z = true;
            } else {
                z = false;
            }
            float f2 = this.f;
            if (f2 != 1.0f) {
                this.c.setScale(f2, f2, f2, 1.0f);
                this.b.postConcat(this.c);
                z = true;
            }
            float f3 = this.g;
            if (f3 != 1.0f) {
                b(f3);
                this.c.set(this.a);
                this.b.postConcat(this.c);
                z = true;
            }
            float f4 = this.d;
            if (f4 != 1.0f) {
                c(f4);
                this.c.set(this.a);
                this.b.postConcat(this.c);
                z = true;
            }
            if (z) {
                imageView.setColorFilter(new ColorMatrixColorFilter(this.b));
            } else {
                imageView.clearColorFilter();
            }
        }
    }

    public ImageFilterView(Context context) {
        super(context);
        a(context, null);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ImageFilterView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.ImageFilterView_altSrc);
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ImageFilterView_crossfade) {
                    this.g = obtainStyledAttributes.getFloat(index, 0.0f);
                } else if (index == R.styleable.ImageFilterView_warmth) {
                    setWarmth(obtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_saturation) {
                    setSaturation(obtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_contrast) {
                    setContrast(obtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_round) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        setRound(obtainStyledAttributes.getDimension(index, 0.0f));
                    }
                } else if (index == R.styleable.ImageFilterView_roundPercent) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        setRoundPercent(obtainStyledAttributes.getFloat(index, 0.0f));
                    }
                } else if (index == R.styleable.ImageFilterView_overlay) {
                    setOverlay(obtainStyledAttributes.getBoolean(index, this.f));
                }
            }
            obtainStyledAttributes.recycle();
            if (drawable != null) {
                this.c = new Drawable[2];
                this.c[0] = getDrawable();
                Drawable[] drawableArr = this.c;
                drawableArr[1] = drawable;
                this.d = new LayerDrawable(drawableArr);
                this.d.getDrawable(1).setAlpha((int) (this.g * 255.0f));
                super.setImageDrawable(this.d);
            }
        }
    }

    private void setOverlay(boolean z) {
        this.f = z;
    }

    public void setSaturation(float f) {
        a aVar = this.e;
        aVar.e = f;
        aVar.a(this);
    }

    public float getSaturation() {
        return this.e.e;
    }

    public void setContrast(float f) {
        a aVar = this.e;
        aVar.f = f;
        aVar.a(this);
    }

    public float getContrast() {
        return this.e.f;
    }

    public void setWarmth(float f) {
        a aVar = this.e;
        aVar.g = f;
        aVar.a(this);
    }

    public float getWarmth() {
        return this.e.g;
    }

    public void setCrossfade(float f) {
        this.g = f;
        if (this.c != null) {
            if (!this.f) {
                this.d.getDrawable(0).setAlpha((int) ((1.0f - this.g) * 255.0f));
            }
            this.d.getDrawable(1).setAlpha((int) (this.g * 255.0f));
            super.setImageDrawable(this.d);
        }
    }

    public float getCrossfade() {
        return this.g;
    }

    public void setBrightness(float f) {
        a aVar = this.e;
        aVar.d = f;
        aVar.a(this);
    }

    public float getBrightness() {
        return this.e.d;
    }

    @RequiresApi(21)
    public void setRoundPercent(float f) {
        boolean z = this.h != f;
        this.h = f;
        if (this.h != 0.0f) {
            if (this.j == null) {
                this.j = new Path();
            }
            if (this.b == null) {
                this.b = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.a == null) {
                    this.a = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.1
                        @Override // android.view.ViewOutlineProvider
                        public void getOutline(View view, Outline outline) {
                            int width = ImageFilterView.this.getWidth();
                            int height = ImageFilterView.this.getHeight();
                            outline.setRoundRect(0, 0, width, height, (Math.min(width, height) * ImageFilterView.this.h) / 2.0f);
                        }
                    };
                    setOutlineProvider(this.a);
                }
                setClipToOutline(true);
            }
            int width = getWidth();
            int height = getHeight();
            float min = (Math.min(width, height) * this.h) / 2.0f;
            this.b.set(0.0f, 0.0f, width, height);
            this.j.reset();
            this.j.addRoundRect(this.b, min, min, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(false);
        }
        if (z && Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @RequiresApi(21)
    public void setRound(float f) {
        if (Float.isNaN(f)) {
            this.i = f;
            float f2 = this.h;
            this.h = -1.0f;
            setRoundPercent(f2);
            return;
        }
        boolean z = this.i != f;
        this.i = f;
        if (this.i != 0.0f) {
            if (this.j == null) {
                this.j = new Path();
            }
            if (this.b == null) {
                this.b = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.a == null) {
                    this.a = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.2
                        @Override // android.view.ViewOutlineProvider
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.i);
                        }
                    };
                    setOutlineProvider(this.a);
                }
                setClipToOutline(true);
            }
            this.b.set(0.0f, 0.0f, getWidth(), getHeight());
            this.j.reset();
            Path path = this.j;
            RectF rectF = this.b;
            float f3 = this.i;
            path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(false);
        }
        if (z && Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public float getRoundPercent() {
        return this.h;
    }

    public float getRound() {
        return this.i;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z;
        if (Build.VERSION.SDK_INT >= 21 || this.h == 0.0f || this.j == null) {
            z = false;
        } else {
            z = true;
            canvas.save();
            canvas.clipPath(this.j);
        }
        super.draw(canvas);
        if (z) {
            canvas.restore();
        }
    }
}
