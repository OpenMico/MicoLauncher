package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemperatureChartViewV2 extends View {
    private Paint a;
    private Paint b;
    private int h;
    private List<Integer> k;
    private List<Integer> l;
    private List<Integer> m;
    private List<a> n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private String u;
    private int v;
    private final int c = -1;
    private final int d = Color.parseColor("#79FFFFFF");
    private final int e = DisplayUtils.dip2px(getContext(), 3.0f);
    private final int f = DisplayUtils.dip2px(getContext(), 2.0f);
    private final int g = DisplayUtils.dip2px(getContext(), 30.0f);
    private final int i = DisplayUtils.dip2px(getContext(), 10.0f);
    private final int j = DisplayUtils.dip2px(getContext(), 60.0f);

    public TemperatureChartViewV2(Context context) {
        super(context);
        a(context);
    }

    public TemperatureChartViewV2(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public TemperatureChartViewV2(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.s = DisplayUtils.getScreenHeightPixels(context) / 2;
        this.t = this.s / 2;
        this.h = DisplayUtils.getScreenWidthPixels(context) / 4;
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setTextSize(this.g);
        this.a.setStyle(Paint.Style.FILL);
        this.a.setColor(-1);
        this.a.setTypeface(Typeface.DEFAULT_BOLD);
        this.b = new Paint();
        this.b.setAntiAlias(true);
        this.b.setStrokeWidth(this.f);
        this.b.setColor(this.d);
        this.u = getContext().getString(R.string.weather_temperature_unit);
        this.k = new ArrayList();
        Rect rect = new Rect();
        this.a.getTextBounds(Constants.ACCEPT_TIME_SEPARATOR_SERVER, 0, 1, rect);
        this.v = rect.width();
    }

    public void setTempes(List<Integer> list, List<Integer> list2) {
        this.m = list2;
        this.l = list;
        if (list.size() > 0 && list2.size() > 0 && list.size() == list2.size()) {
            a();
            b();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas);
        b(canvas);
    }

    private void a(Canvas canvas) {
        if (this.n != null) {
            int i = 0;
            while (i <= this.n.size() - 2) {
                this.b.setColor(this.d);
                i++;
                canvas.drawLine(this.n.get(i).a, this.n.get(i).b, this.n.get(i).a, this.n.get(i).b, this.b);
            }
        }
    }

    private void b(Canvas canvas) {
        if (this.n != null) {
            for (int i = 0; i < this.n.size(); i++) {
                this.b.setColor(-1);
                canvas.drawCircle(this.n.get(i).a, this.n.get(i).b, this.e, this.b);
                a(canvas, this.n.get(i));
                b(canvas, this.n.get(i));
            }
        }
    }

    private void a(Canvas canvas, a aVar) {
        int intValue = this.l.get(aVar.d).intValue();
        String valueOf = String.valueOf(Math.abs(intValue));
        String str = intValue + this.u;
        Rect rect = new Rect();
        this.a.getTextBounds(valueOf, 0, valueOf.length(), rect);
        int width = aVar.a - (rect.width() / 2);
        if (intValue < 0) {
            width -= this.v;
        }
        canvas.drawText(str, width, aVar.b - (this.i + this.e), this.a);
    }

    private void b(Canvas canvas, a aVar) {
        int intValue = this.m.get(aVar.d).intValue();
        String valueOf = String.valueOf(Math.abs(intValue));
        String str = intValue + this.u;
        Rect rect = new Rect();
        this.a.getTextBounds(valueOf, 0, valueOf.length(), rect);
        int width = rect.width();
        int height = rect.height();
        int i = aVar.a - (width / 2);
        if (intValue < 0) {
            i -= this.v;
        }
        canvas.drawText(str, i, aVar.b + height + this.i + this.e, this.a);
    }

    private void a() {
        this.k.clear();
        this.o = this.l.get(0).intValue();
        this.p = this.l.get(0).intValue();
        for (int i = 0; i < this.l.size(); i++) {
            this.k.add(Integer.valueOf((this.l.get(i).intValue() + this.m.get(i).intValue()) / 2));
        }
        for (int i2 = 0; i2 < this.k.size(); i2++) {
            int intValue = this.k.get(i2).intValue();
            if (intValue > this.o) {
                this.o = intValue;
            }
            if (intValue < this.p) {
                this.p = intValue;
            }
        }
        int i3 = this.o;
        int i4 = this.p;
        this.q = (i3 + i4) / 2;
        int i5 = i3 - i4;
        if (i5 != 0) {
            this.r = (this.s - (this.j * 2)) / i5;
        }
        L.base.d("pichMaxAndMinTemperature");
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.l.size(); i++) {
            a aVar = new a();
            aVar.d = i;
            int i2 = this.h;
            aVar.a = (i * i2) + (i2 / 2);
            aVar.c = this.k.get(i).intValue();
            aVar.b = ((this.q - aVar.c) * this.r) + this.t;
            arrayList.add(aVar);
        }
        this.n = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a {
        int a;
        int b;
        int c;
        int d;

        a() {
        }
    }
}
