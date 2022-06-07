package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class ImageFilterButton extends AppCompatImageButton {
    ViewOutlineProvider a;
    RectF b;
    Drawable[] c;
    LayerDrawable d;
    private Path i;
    private ImageFilterView.a e = new ImageFilterView.a();
    private float f = 0.0f;
    private float g = 0.0f;
    private float h = Float.NaN;
    private boolean j = true;

    public ImageFilterButton(Context context) {
        super(context);
        a(context, null);
    }

    public ImageFilterButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public ImageFilterButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        setPadding(0, 0, 0, 0);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ImageFilterView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.ImageFilterView_altSrc);
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ImageFilterView_crossfade) {
                    this.f = obtainStyledAttributes.getFloat(index, 0.0f);
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
                    setOverlay(obtainStyledAttributes.getBoolean(index, this.j));
                }
            }
            obtainStyledAttributes.recycle();
            if (drawable != null) {
                this.c = new Drawable[2];
                this.c[0] = getDrawable();
                Drawable[] drawableArr = this.c;
                drawableArr[1] = drawable;
                this.d = new LayerDrawable(drawableArr);
                this.d.getDrawable(1).setAlpha((int) (this.f * 255.0f));
                super.setImageDrawable(this.d);
            }
        }
    }

    private void setOverlay(boolean z) {
        this.j = z;
    }

    public void setSaturation(float f) {
        ImageFilterView.a aVar = this.e;
        aVar.e = f;
        aVar.a(this);
    }

    public float getSaturation() {
        return this.e.e;
    }

    public void setContrast(float f) {
        ImageFilterView.a aVar = this.e;
        aVar.f = f;
        aVar.a(this);
    }

    public float getContrast() {
        return this.e.f;
    }

    public void setWarmth(float f) {
        ImageFilterView.a aVar = this.e;
        aVar.g = f;
        aVar.a(this);
    }

    public float getWarmth() {
        return this.e.g;
    }

    public void setCrossfade(float f) {
        this.f = f;
        if (this.c != null) {
            if (!this.j) {
                this.d.getDrawable(0).setAlpha((int) ((1.0f - this.f) * 255.0f));
            }
            this.d.getDrawable(1).setAlpha((int) (this.f * 255.0f));
            super.setImageDrawable(this.d);
        }
    }

    public float getCrossfade() {
        return this.f;
    }

    public void setBrightness(float f) {
        ImageFilterView.a aVar = this.e;
        aVar.d = f;
        aVar.a(this);
    }

    @RequiresApi(21)
    public void setRoundPercent(float f) {
        boolean z = this.g != f;
        this.g = f;
        if (this.g != 0.0f) {
            if (this.i == null) {
                this.i = new Path();
            }
            if (this.b == null) {
                this.b = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.a == null) {
                    this.a = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterButton.1
                        @Override // android.view.ViewOutlineProvider
                        public void getOutline(View view, Outline outline) {
                            int width = ImageFilterButton.this.getWidth();
                            int height = ImageFilterButton.this.getHeight();
                            outline.setRoundRect(0, 0, width, height, (Math.min(width, height) * ImageFilterButton.this.g) / 2.0f);
                        }
                    };
                    setOutlineProvider(this.a);
                }
                setClipToOutline(true);
            }
            int width = getWidth();
            int height = getHeight();
            float min = (Math.min(width, height) * this.g) / 2.0f;
            this.b.set(0.0f, 0.0f, width, height);
            this.i.reset();
            this.i.addRoundRect(this.b, min, min, Path.Direction.CW);
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
            this.h = f;
            float f2 = this.g;
            this.g = -1.0f;
            setRoundPercent(f2);
            return;
        }
        boolean z = this.h != f;
        this.h = f;
        if (this.h != 0.0f) {
            if (this.i == null) {
                this.i = new Path();
            }
            if (this.b == null) {
                this.b = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.a == null) {
                    this.a = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterButton.2
                        @Override // android.view.ViewOutlineProvider
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0, 0, ImageFilterButton.this.getWidth(), ImageFilterButton.this.getHeight(), ImageFilterButton.this.h);
                        }
                    };
                    setOutlineProvider(this.a);
                }
                setClipToOutline(true);
            }
            this.b.set(0.0f, 0.0f, getWidth(), getHeight());
            this.i.reset();
            Path path = this.i;
            RectF rectF = this.b;
            float f3 = this.h;
            path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(false);
        }
        if (z && Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public float getRoundPercent() {
        return this.g;
    }

    public float getRound() {
        return this.h;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z;
        if (Build.VERSION.SDK_INT >= 21 || this.h == 0.0f || this.i == null) {
            z = false;
        } else {
            z = true;
            canvas.save();
            canvas.clipPath(this.i);
        }
        super.draw(canvas);
        if (z) {
            canvas.restore();
        }
    }
}
