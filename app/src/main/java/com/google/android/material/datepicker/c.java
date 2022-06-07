package com.google.android.material.datepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/* compiled from: DateFormatTextWatcher.java */
/* loaded from: classes2.dex */
public abstract class c extends TextWatcherAdapter {
    @NonNull
    private final TextInputLayout a;
    private final DateFormat b;
    private final CalendarConstraints c;
    private final String d;
    private final Runnable e;
    private Runnable f;

    void a() {
    }

    abstract void a(@Nullable Long l);

    public c(final String str, DateFormat dateFormat, @NonNull TextInputLayout textInputLayout, CalendarConstraints calendarConstraints) {
        this.b = dateFormat;
        this.a = textInputLayout;
        this.c = calendarConstraints;
        this.d = textInputLayout.getContext().getString(R.string.mtrl_picker_out_of_range);
        this.e = new Runnable() { // from class: com.google.android.material.datepicker.c.1
            @Override // java.lang.Runnable
            public void run() {
                TextInputLayout textInputLayout2 = c.this.a;
                DateFormat dateFormat2 = c.this.b;
                Context context = textInputLayout2.getContext();
                String string = context.getString(R.string.mtrl_picker_invalid_format);
                String format = String.format(context.getString(R.string.mtrl_picker_invalid_format_use), str);
                String format2 = String.format(context.getString(R.string.mtrl_picker_invalid_format_example), dateFormat2.format(new Date(k.b().getTimeInMillis())));
                textInputLayout2.setError(string + "\n" + format + "\n" + format2);
                c.this.a();
            }
        };
    }

    @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
    public void onTextChanged(@NonNull CharSequence charSequence, int i, int i2, int i3) {
        this.a.removeCallbacks(this.e);
        this.a.removeCallbacks(this.f);
        this.a.setError(null);
        a((Long) null);
        if (!TextUtils.isEmpty(charSequence)) {
            try {
                Date parse = this.b.parse(charSequence.toString());
                this.a.setError(null);
                long time = parse.getTime();
                if (!this.c.getDateValidator().isValid(time) || !this.c.a(time)) {
                    this.f = a(time);
                    a(this.a, this.f);
                    return;
                }
                a(Long.valueOf(parse.getTime()));
            } catch (ParseException unused) {
                a(this.a, this.e);
            }
        }
    }

    private Runnable a(final long j) {
        return new Runnable() { // from class: com.google.android.material.datepicker.c.2
            @Override // java.lang.Runnable
            public void run() {
                c.this.a.setError(String.format(c.this.d, d.e(j)));
                c.this.a();
            }
        };
    }

    public void a(View view, Runnable runnable) {
        view.postDelayed(runnable, 1000L);
    }
}
