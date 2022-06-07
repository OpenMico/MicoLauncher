package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class ShadowLayout extends FrameLayout {
    private int a;
    private int b;
    private int c;
    private float d;
    private float e;
    private float f;
    private float g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    private Paint l;
    private Paint m;
    private int n;
    private int o;
    private int p;
    private int q;
    private RectF r;
    private int s;
    private boolean t;
    private boolean u;
    private float v;
    private float w;
    private float x;
    private float y;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShadowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.r = new RectF();
        this.s = 3;
        this.t = true;
        a(context, attributeSet);
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        int i = this.s;
        if (i == 3 || i == 2) {
            if (z) {
                this.m.setColor(this.b);
            } else {
                this.m.setColor(this.a);
            }
            postInvalidate();
        }
    }

    public void setMDx(float f) {
        float abs = Math.abs(f);
        float f2 = this.d;
        if (abs <= f2) {
            this.f = f;
        } else if (f > 0.0f) {
            this.f = f2;
        } else {
            this.f = -f2;
        }
        setPading();
    }

    public void setMDy(float f) {
        float abs = Math.abs(f);
        float f2 = this.d;
        if (abs <= f2) {
            this.g = f;
        } else if (f > 0.0f) {
            this.g = f2;
        } else {
            this.g = -f2;
        }
        setPading();
    }

    public float getmCornerRadius() {
        return this.e;
    }

    public void setmCornerRadius(int i) {
        this.e = i;
        if (getWidth() != 0 && getHeight() != 0) {
            a(getWidth(), getHeight());
        }
    }

    public float getmShadowLimit() {
        return this.d;
    }

    public void setmShadowLimit(int i) {
        this.d = i;
        setPading();
    }

    public void setmShadowColor(int i) {
        this.c = i;
        if (getWidth() != 0 && getHeight() != 0) {
            a(getWidth(), getHeight());
        }
    }

    public void setLeftShow(boolean z) {
        this.h = z;
        setPading();
    }

    public void setRightShow(boolean z) {
        this.i = z;
        setPading();
    }

    public void setTopShow(boolean z) {
        this.j = z;
        setPading();
    }

    public void setBottomShow(boolean z) {
        this.k = z;
        setPading();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i > 0 && i2 > 0) {
            a(i, i2);
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        a(attributeSet);
        this.l = new Paint();
        this.l.setAntiAlias(true);
        this.l.setStyle(Paint.Style.FILL);
        this.m = new Paint(1);
        this.m.setStyle(Paint.Style.FILL);
        this.m.setColor(this.a);
        setPading();
    }

    public void setPading() {
        if (this.u) {
            int abs = (int) (this.d + Math.abs(this.f));
            int abs2 = (int) (this.d + Math.abs(this.g));
            if (this.h) {
                this.n = abs;
            } else {
                this.n = 0;
            }
            if (this.j) {
                this.o = abs2;
            } else {
                this.o = 0;
            }
            if (this.i) {
                this.p = abs;
            } else {
                this.p = 0;
            }
            if (this.k) {
                this.q = abs2;
            } else {
                this.q = 0;
            }
        } else {
            float abs3 = Math.abs(this.g);
            float f = this.d;
            if (abs3 > f) {
                if (this.g > 0.0f) {
                    this.g = f;
                } else {
                    this.g = 0.0f - f;
                }
            }
            float abs4 = Math.abs(this.f);
            float f2 = this.d;
            if (abs4 > f2) {
                if (this.f > 0.0f) {
                    this.f = f2;
                } else {
                    this.f = 0.0f - f2;
                }
            }
            if (this.j) {
                this.o = (int) (this.d - this.g);
            } else {
                this.o = 0;
            }
            if (this.k) {
                this.q = (int) (this.d + this.g);
            } else {
                this.q = 0;
            }
            if (this.i) {
                this.p = (int) (this.d - this.f);
            } else {
                this.p = 0;
            }
            if (this.h) {
                this.n = (int) (this.d + this.f);
            } else {
                this.n = 0;
            }
        }
        setPadding(this.n, this.o, this.p, this.q);
    }

    private void a(int i, int i2) {
        if (this.t) {
            isAddAlpha(this.c);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(a(i, i2, this.e, this.d, this.f, this.g, this.c, 0));
            if (Build.VERSION.SDK_INT <= 16) {
                setBackgroundDrawable(bitmapDrawable);
            } else {
                setBackground(bitmapDrawable);
            }
        } else {
            setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    private void a(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ShadowLayout);
        if (obtainStyledAttributes != null) {
            try {
                this.t = obtainStyledAttributes.getBoolean(8, true);
                this.h = obtainStyledAttributes.getBoolean(10, true);
                this.i = obtainStyledAttributes.getBoolean(11, true);
                this.k = obtainStyledAttributes.getBoolean(0, true);
                this.j = obtainStyledAttributes.getBoolean(17, true);
                this.e = obtainStyledAttributes.getDimension(1, getResources().getDimension(R.dimen.corner_radius));
                this.v = obtainStyledAttributes.getDimension(3, -1.0f);
                this.x = obtainStyledAttributes.getDimension(2, -1.0f);
                this.w = obtainStyledAttributes.getDimension(5, -1.0f);
                this.y = obtainStyledAttributes.getDimension(4, -1.0f);
                this.d = obtainStyledAttributes.getDimension(16, getResources().getDimension(R.dimen.dp_5));
                this.f = obtainStyledAttributes.getDimension(6, 0.0f);
                this.g = obtainStyledAttributes.getDimension(7, 0.0f);
                this.c = obtainStyledAttributes.getColor(15, getResources().getColor(R.color.color_B9BEC7));
                this.a = obtainStyledAttributes.getColor(13, getResources().getColor(R.color.color_B9BEC7));
                this.b = obtainStyledAttributes.getColor(14, getResources().getColor(R.color.color_B9BEC7));
                if (this.b != -1) {
                    setClickable(true);
                }
                this.s = obtainStyledAttributes.getInt(12, 3);
                this.u = obtainStyledAttributes.getBoolean(9, true);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    private Bitmap a(int i, int i2, float f, float f2, float f3, float f4, int i3, int i4) {
        float f5 = f3 / 4.0f;
        float f6 = f4 / 4.0f;
        int i5 = i / 4;
        int i6 = i2 / 4;
        float f7 = f / 4.0f;
        float f8 = f2 / 4.0f;
        Bitmap createBitmap = Bitmap.createBitmap(i5, i6, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        RectF rectF = new RectF(f8, f8, i5 - f8, i6 - f8);
        if (this.u) {
            if (f6 > 0.0f) {
                rectF.top += f6;
                rectF.bottom -= f6;
            } else if (f6 < 0.0f) {
                rectF.top += Math.abs(f6);
                rectF.bottom -= Math.abs(f6);
            }
            if (f5 > 0.0f) {
                rectF.left += f5;
                rectF.right -= f5;
            } else if (f5 < 0.0f) {
                rectF.left += Math.abs(f5);
                rectF.right -= Math.abs(f5);
            }
        } else {
            rectF.top -= f6;
            rectF.bottom -= f6;
            rectF.right -= f5;
            rectF.left -= f5;
        }
        this.l.setColor(i4);
        if (!isInEditMode()) {
            this.l.setShadowLayer(f8, f5, f6, i3);
        }
        if (this.x == -1.0f && this.v == -1.0f && this.w == -1.0f && this.y == -1.0f) {
            canvas.drawRoundRect(rectF, f7, f7, this.l);
        } else {
            RectF rectF2 = this.r;
            rectF2.left = this.n;
            rectF2.top = this.o;
            rectF2.right = getWidth() - this.p;
            this.r.bottom = getHeight() - this.q;
            int height = (getHeight() - this.q) - this.o;
            int width = (getWidth() - this.p) - this.n;
            if (width <= height) {
                height = width;
            }
            float f9 = height / 2;
            canvas.drawRoundRect(rectF, f9, f9, this.l);
            float f10 = this.v;
            if (f10 != -1.0f) {
                if (f10 / f9 <= 0.62f) {
                    float f11 = height / 8;
                    RectF rectF3 = new RectF(rectF.left, rectF.top, rectF.left + f11, rectF.top + f11);
                    float f12 = this.v;
                    canvas.drawRoundRect(rectF3, f12 / 4.0f, f12 / 4.0f, this.l);
                }
            } else if (this.e / f9 <= 0.62f) {
                float f13 = height / 8;
                RectF rectF4 = new RectF(rectF.left, rectF.top, rectF.left + f13, rectF.top + f13);
                float f14 = this.e;
                canvas.drawRoundRect(rectF4, f14 / 4.0f, f14 / 4.0f, this.l);
            }
            float f15 = this.x;
            if (f15 != -1.0f) {
                if (f15 / f9 <= 0.62f) {
                    float f16 = height / 8;
                    RectF rectF5 = new RectF(rectF.left, rectF.bottom - f16, rectF.left + f16, rectF.bottom);
                    float f17 = this.x;
                    canvas.drawRoundRect(rectF5, f17 / 4.0f, f17 / 4.0f, this.l);
                }
            } else if (this.e / f9 <= 0.62f) {
                float f18 = height / 8;
                RectF rectF6 = new RectF(rectF.left, rectF.bottom - f18, rectF.left + f18, rectF.bottom);
                float f19 = this.e;
                canvas.drawRoundRect(rectF6, f19 / 4.0f, f19 / 4.0f, this.l);
            }
            float f20 = this.w;
            if (f20 != -1.0f) {
                if (f20 / f9 <= 0.62f) {
                    float f21 = height / 8;
                    RectF rectF7 = new RectF(rectF.right - f21, rectF.top, rectF.right, rectF.top + f21);
                    float f22 = this.w;
                    canvas.drawRoundRect(rectF7, f22 / 4.0f, f22 / 4.0f, this.l);
                }
            } else if (this.e / f9 <= 0.62f) {
                float f23 = height / 8;
                RectF rectF8 = new RectF(rectF.right - f23, rectF.top, rectF.right, rectF.top + f23);
                float f24 = this.e;
                canvas.drawRoundRect(rectF8, f24 / 4.0f, f24 / 4.0f, this.l);
            }
            float f25 = this.y;
            if (f25 != -1.0f) {
                if (f25 / f9 <= 0.62f) {
                    float f26 = height / 8;
                    RectF rectF9 = new RectF(rectF.right - f26, rectF.bottom - f26, rectF.right, rectF.bottom);
                    float f27 = this.y;
                    canvas.drawRoundRect(rectF9, f27 / 4.0f, f27 / 4.0f, this.l);
                }
            } else if (this.e / f9 <= 0.62f) {
                float f28 = height / 8;
                RectF rectF10 = new RectF(rectF.right - f28, rectF.bottom - f28, rectF.right, rectF.bottom);
                float f29 = this.e;
                canvas.drawRoundRect(rectF10, f29 / 4.0f, f29 / 4.0f, this.l);
            }
        }
        return createBitmap;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.r;
        rectF.left = this.n;
        rectF.top = this.o;
        rectF.right = getWidth() - this.p;
        this.r.bottom = getHeight() - this.q;
        int i = (int) (this.r.bottom - this.r.top);
        if (this.v == 0.0f && this.x == 0.0f && this.w == 0.0f && this.y == 0.0f) {
            float f = this.e;
            float f2 = i / 2;
            if (f > f2) {
                canvas.drawRoundRect(this.r, f2, f2, this.m);
            } else {
                canvas.drawRoundRect(this.r, f, f, this.m);
            }
        } else {
            a(canvas, i);
        }
    }

    private void a(Canvas canvas, int i) {
        float f = this.v;
        int i2 = f == -1.0f ? (int) this.e : (int) f;
        int i3 = i / 2;
        if (i2 > i3) {
            i2 = i3;
        }
        float f2 = this.w;
        int i4 = f2 == -1.0f ? (int) this.e : (int) f2;
        if (i4 > i3) {
            i4 = i3;
        }
        float f3 = this.y;
        int i5 = f3 == -1.0f ? (int) this.e : (int) f3;
        if (i5 > i3) {
            i5 = i3;
        }
        float f4 = this.x;
        int i6 = f4 == -1.0f ? (int) this.e : (int) f4;
        if (i6 <= i3) {
            i3 = i6;
        }
        float f5 = i2;
        float f6 = i4;
        float f7 = i5;
        float f8 = i3;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{f5, f5, f6, f6, f7, f7, f8, f8}, null, null));
        shapeDrawable.getPaint().setColor(this.m.getColor());
        shapeDrawable.setBounds(this.n, this.o, getWidth() - this.p, getHeight() - this.q);
        shapeDrawable.draw(canvas);
    }

    public void isAddAlpha(int i) {
        if (Color.alpha(i) == 255) {
            String hexString = Integer.toHexString(Color.red(i));
            String hexString2 = Integer.toHexString(Color.green(i));
            String hexString3 = Integer.toHexString(Color.blue(i));
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            if (hexString2.length() == 1) {
                hexString2 = "0" + hexString2;
            }
            if (hexString3.length() == 1) {
                hexString3 = "0" + hexString3;
            }
            this.c = convertToColorInt("#2a" + hexString + hexString2 + hexString3);
        }
    }

    public static int convertToColorInt(String str) throws IllegalArgumentException {
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        return Color.parseColor(str);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.b != -1) {
            int action = motionEvent.getAction();
            if (action != 3) {
                switch (action) {
                    case 0:
                        if (!isSelected() && this.s != 2) {
                            this.m.setColor(this.b);
                            postInvalidate();
                            break;
                        }
                        break;
                }
            }
            if (!isSelected() && this.s != 2) {
                this.m.setColor(this.a);
                postInvalidate();
            }
        }
        return super.onTouchEvent(motionEvent);
    }
}
