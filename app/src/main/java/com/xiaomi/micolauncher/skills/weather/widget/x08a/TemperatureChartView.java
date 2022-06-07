package com.xiaomi.micolauncher.skills.weather.widget.x08a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
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
public class TemperatureChartView extends View {
    private Paint a;
    private Paint b;
    private int g;
    private List<Integer> j;
    private List<Integer> k;
    private List<Integer> l;
    private List<a> m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private String t;
    private String u;
    private int v;
    private final int c = -1;
    private final int d = -1;
    private final int e = DisplayUtils.dip2px(getContext(), getContext().getResources().getDimension(R.dimen.weather_multiday_line_width));
    private final int f = DisplayUtils.dip2px(getContext(), getContext().getResources().getDimension(R.dimen.weather_multiday_temperature_chart_text_size));
    private final int h = DisplayUtils.dip2px(getContext(), 20.0f);
    private final int i = DisplayUtils.dip2px(getContext(), getContext().getResources().getDimension(R.dimen.weather_multiday_line_to_view_margin));

    public TemperatureChartView(Context context) {
        super(context);
        a(context);
    }

    public TemperatureChartView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public TemperatureChartView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        this.s = DisplayUtils.dip2px(getContext(), getContext().getResources().getDimension(R.dimen.weather_multidays_cell_margin_line));
        this.g = (DisplayUtils.getScreenWidthPixels(context) - DisplayUtils.dip2px(context, getContext().getResources().getDimension(R.dimen.weather_multidays_cell_width))) / 7;
        this.r = (this.s / 2) + this.i;
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setTextSize(this.f);
        this.a.setStyle(Paint.Style.FILL);
        this.a.setColor(-1);
        this.a.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/typefaceby.ttf"));
        this.b = new Paint();
        this.b.setAntiAlias(true);
        this.b.setStrokeWidth(this.e);
        this.b.setStyle(Paint.Style.STROKE);
        this.b.setStrokeCap(Paint.Cap.ROUND);
        this.b.setColor(-1);
        this.t = getContext().getString(R.string.weather_temperature_unit);
        this.j = new ArrayList();
        this.u = "/";
        Rect rect = new Rect();
        this.a.getTextBounds(Constants.ACCEPT_TIME_SEPARATOR_SERVER, 0, 1, rect);
        this.v = rect.width();
    }

    public void setTempes(List<Integer> list, List<Integer> list2) {
        this.l = list2;
        this.k = list;
        if (list.size() > 0 && list2.size() > 0 && list.size() == list2.size()) {
            a();
            b();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        b(canvas);
        a(canvas);
    }

    private void a(Canvas canvas) {
        if (this.m != null) {
            this.b.setPathEffect(new CornerPathEffect(200.0f));
            Path path = new Path();
            for (int i = 0; i <= this.m.size() - 1; i++) {
                if (i == 0) {
                    path.moveTo(this.m.get(i).a, this.m.get(i).b);
                } else {
                    path.lineTo(this.m.get(i).a, this.m.get(i).b);
                }
            }
            canvas.drawPath(path, this.b);
        }
    }

    private void b(Canvas canvas) {
        if (this.m != null) {
            for (int i = 0; i < this.m.size(); i++) {
                a(canvas, this.m.get(i));
            }
        }
    }

    private void a(Canvas canvas, a aVar) {
        String str = this.l.get(aVar.d).intValue() + this.u + this.k.get(aVar.d).intValue() + this.t;
        Rect rect = new Rect();
        this.a.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, aVar.a - (rect.width() / 2), aVar.b - this.h, this.a);
    }

    private void a() {
        this.j.clear();
        this.n = this.k.get(0).intValue();
        this.o = this.k.get(0).intValue();
        for (int i = 0; i < this.k.size(); i++) {
            this.j.add(Integer.valueOf((this.k.get(i).intValue() + this.l.get(i).intValue()) / 2));
        }
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            int intValue = this.j.get(i2).intValue();
            if (intValue > this.n) {
                this.n = intValue;
            }
            if (intValue < this.o) {
                this.o = intValue;
            }
        }
        int i3 = this.n;
        int i4 = this.o;
        this.p = (i3 + i4) / 2;
        int i5 = i3 - i4;
        if (i5 != 0) {
            this.q = this.s / i5;
        }
        L.base.d("pichMaxAndMinTemperature");
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.k.size(); i++) {
            a aVar = new a();
            aVar.d = i;
            int i2 = this.g;
            aVar.a = (i * i2) + (i2 / 2);
            aVar.c = this.j.get(i).intValue();
            aVar.b = ((this.p - aVar.c) * this.q) + this.r;
            arrayList.add(aVar);
        }
        this.m = arrayList;
    }

    public int getMaxTemp() {
        return this.n;
    }

    public int getMinTemp() {
        return this.o;
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
