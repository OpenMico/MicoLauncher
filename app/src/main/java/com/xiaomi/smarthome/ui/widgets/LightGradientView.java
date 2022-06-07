package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class LightGradientView extends View {
    private int a = getResources().getColor(R.color.white_0_transparent, null);
    private int b = getResources().getColor(R.color.white_0_transparent, null);
    private int c = getResources().getColor(R.color.white_0_transparent, null);
    private int d = getResources().getColor(R.color.white_0_transparent, null);
    private float e = 0.0f;
    private float f = 0.5f;
    private float g = 0.1f;
    private float h = 0.0f;
    private float i = 0.5f;
    private float j = 0.1f;
    private float k = 0.0f;
    private float l = 1.0f;
    private float m = 1.0f;
    private float n = 1.0f;
    private boolean o = false;
    private Paint p;
    private Paint q;

    public LightGradientView(Context context) {
        super(context);
    }

    public LightGradientView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        this.p = new Paint();
        this.q = new Paint();
    }

    @Override // android.view.View
    @RequiresApi(api = 26)
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.q.setShader(new RadialGradient(DisplayUtils.getScreenWidthPixels(getContext()) / 2, -80.0f, (DisplayUtils.getScreenWidthPixels(getContext()) / 4) + 2, new int[]{this.a, this.b, this.c, this.d}, new float[]{0.3f, 0.6f, 0.9f, 1.0f}, Shader.TileMode.REPEAT));
        canvas.drawCircle(DisplayUtils.getScreenWidthPixels(getContext()) / 2, -80.0f, DisplayUtils.getScreenWidthPixels(getContext()) / 4, this.q);
        if (this.o) {
            this.p.setColor(this.a);
            canvas.drawArc(new RectF((DisplayUtils.getScreenWidthPixels(getContext()) / 2) - 120, -20.0f, (DisplayUtils.getScreenWidthPixels(getContext()) / 2) + 120, 10.0f), 0.0f, 180.0f, true, this.p);
        }
    }

    @RequiresApi(api = 26)
    public void setProgressBrightness(float f) {
        double d = f;
        if (d > 0.9d) {
            this.o = true;
        } else {
            this.o = false;
        }
        L.smarthome.d("LightGradientView setProgressBrightness() tempProgressTemper: " + f);
        this.h = ((1.0f - f) * 10.0f * 0.05f) + f;
        if (d < 0.4d) {
            this.i = 0.2f;
        } else {
            this.i = f * this.f;
        }
        this.j = 0.1f;
        L.smarthome.d("LightGradientView  argb1: " + this.h + " argb2: " + this.i + " argb3: " + this.j);
        this.a = Color.argb(this.h, this.l, this.m, this.n);
        this.b = Color.argb(this.i, this.l, this.m, this.n);
        this.c = Color.argb(this.j, this.l, this.m, this.n);
        postInvalidate();
    }

    @RequiresApi(api = 26)
    public void setProgressTemper(float f) {
        float f2 = (float) (100.0f * f * 2.55d);
        Logger logger = L.smarthome;
        logger.d("LightGradientView setProgressTemper() :tempProgressTemper  " + f + " progressTemper: " + f2);
        this.l = 1.0f;
        float f3 = (f2 / 4.0f) + 183.0f;
        this.m = f3 / 255.0f;
        this.n = f2 / 255.0f;
        Logger logger2 = L.smarthome;
        logger2.d("LightGradientView setProgressTemper() :green  " + f3 + " blue: " + f2);
        Logger logger3 = L.smarthome;
        logger3.d("LightGradientView etProgressTemper() :green  " + this.m + " blue: " + this.n);
        Logger logger4 = L.smarthome;
        logger4.d("LightGradientView  argb1: " + this.h + " argb2: " + this.i + " argb3: " + this.j);
        this.a = Color.argb(this.h, this.l, this.m, this.n);
        this.b = Color.argb(this.i, this.l, this.m, this.n);
        this.c = Color.argb(this.j, this.l, this.m, this.n);
        this.d = Color.argb(this.k, this.l, this.m, this.n);
        postInvalidate();
    }
}
