package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.R;
import java.util.Collection;

/* compiled from: MonthAdapter.java */
/* loaded from: classes2.dex */
public class g extends BaseAdapter {
    static final int a = k.c().getMaximum(4);
    final f b;
    final DateSelector<?> c;
    b d;
    final CalendarConstraints e;
    private Collection<Long> f;

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public g(f fVar, DateSelector<?> dateSelector, CalendarConstraints calendarConstraints) {
        this.b = fVar;
        this.c = dateSelector;
        this.e = calendarConstraints;
        this.f = dateSelector.getSelectedDays();
    }

    @Nullable
    /* renamed from: a */
    public Long getItem(int i) {
        if (i < this.b.b() || i > b()) {
            return null;
        }
        return Long.valueOf(this.b.a(b(i)));
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i / this.b.c;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.b.d + a();
    }

    @NonNull
    /* renamed from: a */
    public TextView getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        a(viewGroup.getContext());
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_day, viewGroup, false);
        }
        int a2 = i - a();
        if (a2 < 0 || a2 >= this.b.d) {
            textView.setVisibility(8);
            textView.setEnabled(false);
        } else {
            int i2 = a2 + 1;
            textView.setTag(this.b);
            textView.setText(String.format(textView.getResources().getConfiguration().locale, "%d", Integer.valueOf(i2)));
            long a3 = this.b.a(i2);
            if (this.b.b == f.a().b) {
                textView.setContentDescription(d.c(a3));
            } else {
                textView.setContentDescription(d.d(a3));
            }
            textView.setVisibility(0);
            textView.setEnabled(true);
        }
        Long a4 = getItem(i);
        if (a4 == null) {
            return textView;
        }
        a(textView, a4.longValue());
        return textView;
    }

    public void a(MaterialCalendarGridView materialCalendarGridView) {
        for (Long l : this.f) {
            a(materialCalendarGridView, l.longValue());
        }
        DateSelector<?> dateSelector = this.c;
        if (dateSelector != null) {
            for (Long l2 : dateSelector.getSelectedDays()) {
                a(materialCalendarGridView, l2.longValue());
            }
            this.f = this.c.getSelectedDays();
        }
    }

    private void a(MaterialCalendarGridView materialCalendarGridView, long j) {
        if (f.a(j).equals(this.b)) {
            a((TextView) materialCalendarGridView.getChildAt(materialCalendarGridView.getAdapter2().c(this.b.b(j)) - materialCalendarGridView.getFirstVisiblePosition()), j);
        }
    }

    private void a(@Nullable TextView textView, long j) {
        a aVar;
        if (textView != null) {
            if (this.e.getDateValidator().isValid(j)) {
                textView.setEnabled(true);
                if (a(j)) {
                    aVar = this.d.b;
                } else if (k.b().getTimeInMillis() == j) {
                    aVar = this.d.c;
                } else {
                    aVar = this.d.a;
                }
            } else {
                textView.setEnabled(false);
                aVar = this.d.g;
            }
            aVar.a(textView);
        }
    }

    private boolean a(long j) {
        for (Long l : this.c.getSelectedDays()) {
            if (k.a(j) == k.a(l.longValue())) {
                return true;
            }
        }
        return false;
    }

    private void a(Context context) {
        if (this.d == null) {
            this.d = new b(context);
        }
    }

    public int a() {
        return this.b.b();
    }

    public int b() {
        return (this.b.b() + this.b.d) - 1;
    }

    int b(int i) {
        return (i - this.b.b()) + 1;
    }

    public int c(int i) {
        return a() + (i - 1);
    }

    public boolean d(int i) {
        return i >= a() && i <= b();
    }

    public boolean e(int i) {
        return i % this.b.c == 0;
    }

    public boolean f(int i) {
        return (i + 1) % this.b.c == 0;
    }
}
