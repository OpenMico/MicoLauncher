package com.google.android.material.datepicker;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.R;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: DaysOfWeekAdapter.java */
/* loaded from: classes2.dex */
class e extends BaseAdapter {
    private static final int d;
    @NonNull
    private final Calendar a = k.c();
    private final int b = this.a.getMaximum(7);
    private final int c = this.a.getFirstDayOfWeek();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    static {
        d = Build.VERSION.SDK_INT >= 26 ? 4 : 1;
    }

    @Nullable
    /* renamed from: a */
    public Integer getItem(int i) {
        if (i >= this.b) {
            return null;
        }
        return Integer.valueOf(b(i));
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.b;
    }

    @Override // android.widget.Adapter
    @Nullable
    @SuppressLint({"WrongConstant"})
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_day_of_week, viewGroup, false);
        }
        this.a.set(7, b(i));
        textView.setText(this.a.getDisplayName(7, d, textView.getResources().getConfiguration().locale));
        textView.setContentDescription(String.format(viewGroup.getContext().getString(R.string.mtrl_picker_day_of_week_column_header), this.a.getDisplayName(7, 2, Locale.getDefault())));
        return textView;
    }

    private int b(int i) {
        int i2 = i + this.c;
        int i3 = this.b;
        return i2 > i3 ? i2 - i3 : i2;
    }
}
