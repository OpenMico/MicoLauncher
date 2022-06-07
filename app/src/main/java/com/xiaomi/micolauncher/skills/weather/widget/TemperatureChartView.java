package com.xiaomi.micolauncher.skills.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemperatureChartView extends View {
    private Paint a;
    private int i;
    private List<Integer> l;
    private List<Integer> m;
    private List<a> n;
    private List<a> o;
    private int p;
    private int q;
    private int r;
    private int s;
    private String t;
    private final int b = Color.parseColor("#FBAC18");
    private final int c = Color.parseColor("#6083FF");
    private final int d = Color.parseColor("#404042");
    private final int e = DisplayUtils.dip2px(getContext(), 3.0f);
    private final int f = DisplayUtils.dip2px(getContext(), 4.0f);
    private final int g = DisplayUtils.dip2px(getContext(), 2.0f);
    private final int h = DisplayUtils.dip2px(getContext(), 22.0f);
    private final int j = DisplayUtils.dip2px(getContext(), 7.0f);
    private final int k = DisplayUtils.dip2px(getContext(), 40.0f);

    public TemperatureChartView(Context context) {
        super(context);
        a(context);
    }

    public TemperatureChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public TemperatureChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.s = (int) context.getResources().getDimension(R.dimen.weather_chart_height);
        this.i = DisplayUtils.getScreenWidthPixels(context) / 5;
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setTextSize(this.h);
        this.a.setStyle(Paint.Style.FILL);
        this.a.setColor(this.d);
        this.a.setTypeface(Typeface.DEFAULT_BOLD);
        this.t = getContext().getString(R.string.weather_temperature_unit);
    }

    public void setTempes(List<Integer> list, List<Integer> list2) {
        this.m = list2;
        this.l = list;
        if (list2.size() > 0 && list2.size() > 0) {
            a();
            b();
            c();
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
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.g);
            int i = 0;
            while (i <= this.n.size() - 2) {
                paint.setColor(this.b);
                int i2 = i + 1;
                canvas.drawLine(this.n.get(i).a, this.n.get(i).b, this.n.get(i2).a, this.n.get(i2).b, paint);
                paint.setColor(-1);
                canvas.drawCircle(this.n.get(i).a, this.n.get(i).b, this.f, paint);
                paint.setColor(this.b);
                canvas.drawCircle(this.n.get(i).a, this.n.get(i).b, this.e, paint);
                a(canvas, this.n.get(i));
                if (i == this.n.size() - 2) {
                    paint.setColor(-1);
                    canvas.drawCircle(this.n.get(i2).a, this.n.get(i2).b, this.f, paint);
                    paint.setColor(this.b);
                    canvas.drawCircle(this.n.get(i2).a, this.n.get(i2).b, this.e, paint);
                    a(canvas, this.n.get(i2));
                }
                i = i2;
            }
        }
    }

    private void b(Canvas canvas) {
        if (this.o != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.g);
            int i = 0;
            while (i <= this.o.size() - 2) {
                paint.setColor(this.c);
                int i2 = i + 1;
                canvas.drawLine(this.o.get(i).a, this.o.get(i).b, this.o.get(i2).a, this.o.get(i2).b, paint);
                paint.setColor(-1);
                canvas.drawCircle(this.o.get(i).a, this.o.get(i).b, this.f, paint);
                paint.setColor(this.c);
                canvas.drawCircle(this.o.get(i).a, this.o.get(i).b, this.e, paint);
                b(canvas, this.o.get(i));
                if (i == this.n.size() - 2) {
                    paint.setColor(-1);
                    canvas.drawCircle(this.o.get(i2).a, this.o.get(i2).b, this.f, paint);
                    paint.setColor(this.c);
                    canvas.drawCircle(this.o.get(i2).a, this.o.get(i2).b, this.e, paint);
                    b(canvas, this.o.get(i2));
                }
                i = i2;
            }
        }
    }

    private void a(Canvas canvas, a aVar) {
        String str = aVar.c + this.t;
        Rect rect = new Rect();
        this.a.getTextBounds(str, 0, str.length() - 1, rect);
        int width = rect.width();
        rect.height();
        canvas.drawText(str, aVar.a - (width / 2), (aVar.b - this.j) - this.f, this.a);
    }

    private void b(Canvas canvas, a aVar) {
        String str = aVar.c + this.t;
        Rect rect = new Rect();
        this.a.getTextBounds(str, 0, str.length() - 1, rect);
        int width = rect.width();
        int height = rect.height();
        canvas.drawText(str, aVar.a - (width / 2), aVar.b + height + this.j + this.f, this.a);
    }

    private void a() {
        this.p = this.l.get(0).intValue();
        for (Integer num : this.l) {
            if (num.intValue() > this.p) {
                this.p = num.intValue();
            }
        }
        this.q = this.m.get(0).intValue();
        for (Integer num2 : this.m) {
            if (num2.intValue() < this.q) {
                this.q = num2.intValue();
            }
        }
        this.r = (this.s - (this.k * 2)) / (this.p - this.q);
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.l.size(); i++) {
            a aVar = new a();
            aVar.d = i;
            int i2 = this.i;
            aVar.a = (i * i2) + (i2 / 2);
            aVar.c = this.l.get(i).intValue();
            aVar.b = ((this.p - this.l.get(i).intValue()) * this.r) + this.k;
            arrayList.add(aVar);
        }
        this.n = arrayList;
    }

    private void c() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.m.size(); i++) {
            a aVar = new a();
            aVar.d = i;
            int i2 = this.i;
            aVar.a = (i * i2) + (i2 / 2);
            aVar.c = this.m.get(i).intValue();
            aVar.b = (this.s - ((this.m.get(i).intValue() - this.q) * this.r)) - this.k;
            arrayList.add(aVar);
        }
        this.o = arrayList;
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
