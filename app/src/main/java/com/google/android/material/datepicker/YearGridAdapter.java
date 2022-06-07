package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class YearGridAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final MaterialCalendar<?> a;

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView a;

        ViewHolder(TextView textView) {
            super(textView);
            this.a = textView;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public YearGridAdapter(MaterialCalendar<?> materialCalendar) {
        this.a = materialCalendar;
    }

    @NonNull
    /* renamed from: a */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder((TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_year, viewGroup, false));
    }

    /* renamed from: a */
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int b = b(i);
        String string = viewHolder.a.getContext().getString(R.string.mtrl_picker_navigate_to_year_description);
        viewHolder.a.setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(b)));
        viewHolder.a.setContentDescription(String.format(string, Integer.valueOf(b)));
        b c = this.a.c();
        Calendar b2 = k.b();
        a aVar = b2.get(1) == b ? c.f : c.d;
        for (Long l : this.a.getDateSelector().getSelectedDays()) {
            b2.setTimeInMillis(l.longValue());
            if (b2.get(1) == b) {
                aVar = c.e;
            }
        }
        aVar.a(viewHolder.a);
        viewHolder.a.setOnClickListener(c(b));
    }

    @NonNull
    private View.OnClickListener c(final int i) {
        return new View.OnClickListener() { // from class: com.google.android.material.datepicker.YearGridAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                YearGridAdapter.this.a.a(YearGridAdapter.this.a.b().b(f.a(i, YearGridAdapter.this.a.a().a)));
                YearGridAdapter.this.a.a(MaterialCalendar.a.DAY);
            }
        };
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.b().e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i) {
        return i - this.a.b().a().b;
    }

    int b(int i) {
        return this.a.b().a().b + i;
    }
}
