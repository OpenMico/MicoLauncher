package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Iterator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public final class MaterialCalendar<S> extends h<S> {
    @VisibleForTesting
    static final Object a = "MONTHS_VIEW_GROUP_TAG";
    @VisibleForTesting
    static final Object b = "NAVIGATION_PREV_TAG";
    @VisibleForTesting
    static final Object c = "NAVIGATION_NEXT_TAG";
    @VisibleForTesting
    static final Object d = "SELECTOR_TOGGLE_TAG";
    @StyleRes
    private int e;
    @Nullable
    private DateSelector<S> f;
    @Nullable
    private CalendarConstraints g;
    @Nullable
    private f h;
    private a i;
    private b j;
    private RecyclerView k;
    private RecyclerView l;
    private View m;
    private View n;

    /* loaded from: classes2.dex */
    public enum a {
        DAY,
        YEAR
    }

    /* loaded from: classes2.dex */
    public interface b {
        void a(long j);
    }

    @NonNull
    public static <T> MaterialCalendar<T> newInstance(@NonNull DateSelector<T> dateSelector, @StyleRes int i, @NonNull CalendarConstraints calendarConstraints) {
        MaterialCalendar<T> materialCalendar = new MaterialCalendar<>();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", i);
        bundle.putParcelable("GRID_SELECTOR_KEY", dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints);
        bundle.putParcelable("CURRENT_MONTH_KEY", calendarConstraints.c());
        materialCalendar.setArguments(bundle);
        return materialCalendar;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("THEME_RES_ID_KEY", this.e);
        bundle.putParcelable("GRID_SELECTOR_KEY", this.f);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.g);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.h);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.e = bundle.getInt("THEME_RES_ID_KEY");
        this.f = (DateSelector) bundle.getParcelable("GRID_SELECTOR_KEY");
        this.g = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.h = (f) bundle.getParcelable("CURRENT_MONTH_KEY");
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        final int i;
        int i2;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), this.e);
        this.j = new b(contextThemeWrapper);
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(contextThemeWrapper);
        f a2 = this.g.a();
        if (MaterialDatePicker.a(contextThemeWrapper)) {
            i2 = R.layout.mtrl_calendar_vertical;
            i = 1;
        } else {
            i2 = R.layout.mtrl_calendar_horizontal;
            i = 0;
        }
        View inflate = cloneInContext.inflate(i2, viewGroup, false);
        GridView gridView = (GridView) inflate.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(gridView, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
        gridView.setAdapter((ListAdapter) new e());
        gridView.setNumColumns(a2.c);
        gridView.setEnabled(false);
        this.l = (RecyclerView) inflate.findViewById(R.id.mtrl_calendar_months);
        this.l.setLayoutManager(new i(getContext(), i, false) { // from class: com.google.android.material.datepicker.MaterialCalendar.3
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public void calculateExtraLayoutSpace(@NonNull RecyclerView.State state, @NonNull int[] iArr) {
                if (i == 0) {
                    iArr[0] = MaterialCalendar.this.l.getWidth();
                    iArr[1] = MaterialCalendar.this.l.getWidth();
                    return;
                }
                iArr[0] = MaterialCalendar.this.l.getHeight();
                iArr[1] = MaterialCalendar.this.l.getHeight();
            }
        });
        this.l.setTag(a);
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(contextThemeWrapper, this.f, this.g, new b() { // from class: com.google.android.material.datepicker.MaterialCalendar.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.material.datepicker.MaterialCalendar.b
            public void a(long j) {
                if (MaterialCalendar.this.g.getDateValidator().isValid(j)) {
                    MaterialCalendar.this.f.select(j);
                    Iterator it = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((OnSelectionChangedListener) it.next()).onSelectionChanged(MaterialCalendar.this.f.getSelection());
                    }
                    MaterialCalendar.this.l.getAdapter().notifyDataSetChanged();
                    if (MaterialCalendar.this.k != null) {
                        MaterialCalendar.this.k.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
        this.l.setAdapter(monthsPagerAdapter);
        int integer = contextThemeWrapper.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        this.k = (RecyclerView) inflate.findViewById(R.id.mtrl_calendar_year_selector_frame);
        RecyclerView recyclerView = this.k;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            this.k.setLayoutManager(new GridLayoutManager((Context) contextThemeWrapper, integer, 1, false));
            this.k.setAdapter(new YearGridAdapter(this));
            this.k.addItemDecoration(g());
        }
        if (inflate.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            a(inflate, monthsPagerAdapter);
        }
        if (!MaterialDatePicker.a(contextThemeWrapper)) {
            new PagerSnapHelper().attachToRecyclerView(this.l);
        }
        this.l.scrollToPosition(monthsPagerAdapter.a(this.h));
        return inflate;
    }

    @NonNull
    private RecyclerView.ItemDecoration g() {
        return new RecyclerView.ItemDecoration() { // from class: com.google.android.material.datepicker.MaterialCalendar.5
            private final Calendar b = k.c();
            private final Calendar c = k.c();

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                int i;
                if ((recyclerView.getAdapter() instanceof YearGridAdapter) && (recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                    YearGridAdapter yearGridAdapter = (YearGridAdapter) recyclerView.getAdapter();
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    for (Pair<Long, Long> pair : MaterialCalendar.this.f.getSelectedRanges()) {
                        if (!(pair.first == null || pair.second == null)) {
                            this.b.setTimeInMillis(pair.first.longValue());
                            this.c.setTimeInMillis(pair.second.longValue());
                            int a2 = yearGridAdapter.a(this.b.get(1));
                            int a3 = yearGridAdapter.a(this.c.get(1));
                            View findViewByPosition = gridLayoutManager.findViewByPosition(a2);
                            View findViewByPosition2 = gridLayoutManager.findViewByPosition(a3);
                            int spanCount = a2 / gridLayoutManager.getSpanCount();
                            int spanCount2 = a3 / gridLayoutManager.getSpanCount();
                            int i2 = spanCount;
                            while (i2 <= spanCount2) {
                                View findViewByPosition3 = gridLayoutManager.findViewByPosition(gridLayoutManager.getSpanCount() * i2);
                                if (findViewByPosition3 != null) {
                                    int top = findViewByPosition3.getTop() + MaterialCalendar.this.j.d.a();
                                    int bottom = findViewByPosition3.getBottom() - MaterialCalendar.this.j.d.b();
                                    int left = i2 == spanCount ? findViewByPosition.getLeft() + (findViewByPosition.getWidth() / 2) : 0;
                                    if (i2 == spanCount2) {
                                        i = findViewByPosition2.getLeft() + (findViewByPosition2.getWidth() / 2);
                                    } else {
                                        i = recyclerView.getWidth();
                                    }
                                    canvas.drawRect(left, top, i, bottom, MaterialCalendar.this.j.h);
                                }
                                i2++;
                            }
                        }
                    }
                }
            }
        };
    }

    @Nullable
    public f a() {
        return this.h;
    }

    @Nullable
    public CalendarConstraints b() {
        return this.g;
    }

    public void a(f fVar) {
        MonthsPagerAdapter monthsPagerAdapter = (MonthsPagerAdapter) this.l.getAdapter();
        int a2 = monthsPagerAdapter.a(fVar);
        int a3 = a2 - monthsPagerAdapter.a(this.h);
        boolean z = true;
        boolean z2 = Math.abs(a3) > 3;
        if (a3 <= 0) {
            z = false;
        }
        this.h = fVar;
        if (z2 && z) {
            this.l.scrollToPosition(a2 - 3);
            a(a2);
        } else if (z2) {
            this.l.scrollToPosition(a2 + 3);
            a(a2);
        } else {
            a(a2);
        }
    }

    @Nullable
    public DateSelector<S> getDateSelector() {
        return this.f;
    }

    public b c() {
        return this.j;
    }

    @Px
    public static int a(@NonNull Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
    }

    public void a(a aVar) {
        this.i = aVar;
        if (aVar == a.YEAR) {
            this.k.getLayoutManager().scrollToPosition(((YearGridAdapter) this.k.getAdapter()).a(this.h.b));
            this.m.setVisibility(0);
            this.n.setVisibility(8);
        } else if (aVar == a.DAY) {
            this.m.setVisibility(8);
            this.n.setVisibility(0);
            a(this.h);
        }
    }

    void d() {
        if (this.i == a.YEAR) {
            a(a.DAY);
        } else if (this.i == a.DAY) {
            a(a.YEAR);
        }
    }

    private void a(@NonNull View view, @NonNull final MonthsPagerAdapter monthsPagerAdapter) {
        final MaterialButton materialButton = (MaterialButton) view.findViewById(R.id.month_navigation_fragment_toggle);
        materialButton.setTag(d);
        ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendar.6
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                String str;
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                if (MaterialCalendar.this.n.getVisibility() == 0) {
                    str = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
                } else {
                    str = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
                }
                accessibilityNodeInfoCompat.setHintText(str);
            }
        });
        MaterialButton materialButton2 = (MaterialButton) view.findViewById(R.id.month_navigation_previous);
        materialButton2.setTag(b);
        MaterialButton materialButton3 = (MaterialButton) view.findViewById(R.id.month_navigation_next);
        materialButton3.setTag(c);
        this.m = view.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.n = view.findViewById(R.id.mtrl_calendar_day_selector_frame);
        a(a.DAY);
        materialButton.setText(this.h.a(view.getContext()));
        this.l.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.7
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                int i3;
                if (i < 0) {
                    i3 = MaterialCalendar.this.e().findFirstVisibleItemPosition();
                } else {
                    i3 = MaterialCalendar.this.e().findLastVisibleItemPosition();
                }
                MaterialCalendar.this.h = monthsPagerAdapter.b(i3);
                materialButton.setText(monthsPagerAdapter.a(i3));
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                if (i == 0) {
                    CharSequence text = materialButton.getText();
                    if (Build.VERSION.SDK_INT >= 16) {
                        recyclerView.announceForAccessibility(text);
                    } else {
                        recyclerView.sendAccessibilityEvent(2048);
                    }
                }
            }
        });
        materialButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                MaterialCalendar.this.d();
            }
        });
        materialButton3.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int findFirstVisibleItemPosition = MaterialCalendar.this.e().findFirstVisibleItemPosition() + 1;
                if (findFirstVisibleItemPosition < MaterialCalendar.this.l.getAdapter().getItemCount()) {
                    MaterialCalendar.this.a(monthsPagerAdapter.b(findFirstVisibleItemPosition));
                }
            }
        });
        materialButton2.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialCalendar.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int findLastVisibleItemPosition = MaterialCalendar.this.e().findLastVisibleItemPosition() - 1;
                if (findLastVisibleItemPosition >= 0) {
                    MaterialCalendar.this.a(monthsPagerAdapter.b(findLastVisibleItemPosition));
                }
            }
        });
    }

    private void a(final int i) {
        this.l.post(new Runnable() { // from class: com.google.android.material.datepicker.MaterialCalendar.2
            @Override // java.lang.Runnable
            public void run() {
                MaterialCalendar.this.l.smoothScrollToPosition(i);
            }
        });
    }

    @NonNull
    LinearLayoutManager e() {
        return (LinearLayoutManager) this.l.getLayoutManager();
    }

    @Override // com.google.android.material.datepicker.h
    public boolean addOnSelectionChangedListener(@NonNull OnSelectionChangedListener<S> onSelectionChangedListener) {
        return super.addOnSelectionChangedListener(onSelectionChangedListener);
    }
}
