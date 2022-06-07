package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.MaterialCalendar;

/* loaded from: classes2.dex */
class MonthsPagerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context a;
    @NonNull
    private final CalendarConstraints b;
    private final DateSelector<?> c;
    private final MaterialCalendar.b d;
    private final int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MonthsPagerAdapter(@NonNull Context context, DateSelector<?> dateSelector, @NonNull CalendarConstraints calendarConstraints, MaterialCalendar.b bVar) {
        f a = calendarConstraints.a();
        f b = calendarConstraints.b();
        f c = calendarConstraints.c();
        if (a.compareTo(c) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        } else if (c.compareTo(b) <= 0) {
            int a2 = g.a * MaterialCalendar.a(context);
            int a3 = MaterialDatePicker.a(context) ? MaterialCalendar.a(context) : 0;
            this.a = context;
            this.e = a2 + a3;
            this.b = calendarConstraints;
            this.c = dateSelector;
            this.d = bVar;
            setHasStableIds(true);
        } else {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView a;
        final MaterialCalendarGridView b;

        ViewHolder(@NonNull LinearLayout linearLayout, boolean z) {
            super(linearLayout);
            this.a = (TextView) linearLayout.findViewById(R.id.month_title);
            ViewCompat.setAccessibilityHeading(this.a, true);
            this.b = (MaterialCalendarGridView) linearLayout.findViewById(R.id.month_grid);
            if (!z) {
                this.a.setVisibility(8);
            }
        }
    }

    @NonNull
    /* renamed from: a */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (!MaterialDatePicker.a(viewGroup.getContext())) {
            return new ViewHolder(linearLayout, false);
        }
        linearLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, this.e));
        return new ViewHolder(linearLayout, true);
    }

    /* renamed from: a */
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        f b = this.b.a().b(i);
        viewHolder.a.setText(b.a(viewHolder.itemView.getContext()));
        final MaterialCalendarGridView materialCalendarGridView = (MaterialCalendarGridView) viewHolder.b.findViewById(R.id.month_grid);
        if (materialCalendarGridView.getAdapter2() == null || !b.equals(materialCalendarGridView.getAdapter2().b)) {
            g gVar = new g(b, this.c, this.b);
            materialCalendarGridView.setNumColumns(b.c);
            materialCalendarGridView.setAdapter((ListAdapter) gVar);
        } else {
            materialCalendarGridView.invalidate();
            materialCalendarGridView.getAdapter2().a(materialCalendarGridView);
        }
        materialCalendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.google.android.material.datepicker.MonthsPagerAdapter.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                if (materialCalendarGridView.getAdapter2().d(i2)) {
                    MonthsPagerAdapter.this.d.a(materialCalendarGridView.getAdapter2().getItem(i2).longValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.b.a().b(i).c();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public CharSequence a(int i) {
        return b(i).a(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public f b(int i) {
        return this.b.a().b(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(@NonNull f fVar) {
        return this.b.a().b(fVar);
    }
}
