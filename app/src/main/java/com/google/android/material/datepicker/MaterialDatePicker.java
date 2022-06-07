package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes2.dex */
public final class MaterialDatePicker<S> extends DialogFragment {
    public static final int INPUT_MODE_CALENDAR = 0;
    public static final int INPUT_MODE_TEXT = 1;
    static final Object a = "CONFIRM_BUTTON_TAG";
    static final Object b = "CANCEL_BUTTON_TAG";
    static final Object c = "TOGGLE_BUTTON_TAG";
    private final LinkedHashSet<MaterialPickerOnPositiveButtonClickListener<? super S>> d = new LinkedHashSet<>();
    private final LinkedHashSet<View.OnClickListener> e = new LinkedHashSet<>();
    private final LinkedHashSet<DialogInterface.OnCancelListener> f = new LinkedHashSet<>();
    private final LinkedHashSet<DialogInterface.OnDismissListener> g = new LinkedHashSet<>();
    @StyleRes
    private int h;
    @Nullable
    private DateSelector<S> i;
    private h<S> j;
    @Nullable
    private CalendarConstraints k;
    private MaterialCalendar<S> l;
    @StringRes
    private int m;
    private CharSequence n;
    private boolean o;
    private int p;
    private TextView q;
    private CheckableImageButton r;
    @Nullable
    private MaterialShapeDrawable s;
    private Button t;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface InputMode {
    }

    public static long todayInUtcMilliseconds() {
        return k.b().getTimeInMillis();
    }

    public static long thisMonthInUtcMilliseconds() {
        return f.a().e;
    }

    public String getHeaderText() {
        return this.i.getSelectionDisplayString(getContext());
    }

    @NonNull
    static <S> MaterialDatePicker<S> a(@NonNull Builder<S> builder) {
        MaterialDatePicker<S> materialDatePicker = new MaterialDatePicker<>();
        Bundle bundle = new Bundle();
        bundle.putInt("OVERRIDE_THEME_RES_ID", builder.b);
        bundle.putParcelable("DATE_SELECTOR_KEY", builder.a);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", builder.c);
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", builder.d);
        bundle.putCharSequence("TITLE_TEXT_KEY", builder.e);
        bundle.putInt("INPUT_MODE_KEY", builder.g);
        materialDatePicker.setArguments(bundle);
        return materialDatePicker;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.h);
        bundle.putParcelable("DATE_SELECTOR_KEY", this.i);
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder(this.k);
        if (this.l.a() != null) {
            builder.setOpenAt(this.l.a().e);
        }
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", builder.build());
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.m);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.n);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.h = bundle.getInt("OVERRIDE_THEME_RES_ID");
        this.i = (DateSelector) bundle.getParcelable("DATE_SELECTOR_KEY");
        this.k = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.m = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.n = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.p = bundle.getInt("INPUT_MODE_KEY");
    }

    private int c(Context context) {
        int i = this.h;
        return i != 0 ? i : this.i.getDefaultThemeResId(context);
    }

    @Override // androidx.fragment.app.DialogFragment
    @NonNull
    public final Dialog onCreateDialog(@Nullable Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), c(requireContext()));
        Context context = dialog.getContext();
        this.o = a(context);
        int resolveOrThrow = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
        this.s = new MaterialShapeDrawable(context, null, R.attr.materialCalendarStyle, R.style.Widget_MaterialComponents_MaterialCalendar);
        this.s.initializeElevationOverlay(context);
        this.s.setFillColor(ColorStateList.valueOf(resolveOrThrow));
        this.s.setElevation(ViewCompat.getElevation(dialog.getWindow().getDecorView()));
        return dialog;
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public final View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(this.o ? R.layout.mtrl_picker_fullscreen : R.layout.mtrl_picker_dialog, viewGroup);
        Context context = inflate.getContext();
        if (this.o) {
            inflate.findViewById(R.id.mtrl_calendar_frame).setLayoutParams(new LinearLayout.LayoutParams(g(context), -2));
        } else {
            View findViewById = inflate.findViewById(R.id.mtrl_calendar_main_pane);
            View findViewById2 = inflate.findViewById(R.id.mtrl_calendar_frame);
            findViewById.setLayoutParams(new LinearLayout.LayoutParams(g(context), -1));
            findViewById2.setMinimumHeight(f(requireContext()));
        }
        this.q = (TextView) inflate.findViewById(R.id.mtrl_picker_header_selection_text);
        ViewCompat.setAccessibilityLiveRegion(this.q, 1);
        this.r = (CheckableImageButton) inflate.findViewById(R.id.mtrl_picker_header_toggle);
        TextView textView = (TextView) inflate.findViewById(R.id.mtrl_picker_title_text);
        CharSequence charSequence = this.n;
        if (charSequence != null) {
            textView.setText(charSequence);
        } else {
            textView.setText(this.m);
        }
        d(context);
        this.t = (Button) inflate.findViewById(R.id.confirm_button);
        if (this.i.isSelectionComplete()) {
            this.t.setEnabled(true);
        } else {
            this.t.setEnabled(false);
        }
        this.t.setTag(a);
        this.t.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.d.iterator();
                while (it.hasNext()) {
                    ((MaterialPickerOnPositiveButtonClickListener) it.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        Button button = (Button) inflate.findViewById(R.id.cancel_button);
        button.setTag(b);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Iterator it = MaterialDatePicker.this.e.iterator();
                while (it.hasNext()) {
                    ((View.OnClickListener) it.next()).onClick(view);
                }
                MaterialDatePicker.this.dismiss();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.o) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.s);
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(new InsetDrawable((Drawable) this.s, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset));
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        c();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        this.j.f();
        super.onStop();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(@NonNull DialogInterface dialogInterface) {
        Iterator<DialogInterface.OnCancelListener> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(@NonNull DialogInterface dialogInterface) {
        Iterator<DialogInterface.OnDismissListener> it = this.g.iterator();
        while (it.hasNext()) {
            it.next().onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Nullable
    public final S getSelection() {
        return this.i.getSelection();
    }

    public void b() {
        String headerText = getHeaderText();
        this.q.setContentDescription(String.format(getString(R.string.mtrl_picker_announce_current_selection), headerText));
        this.q.setText(headerText);
    }

    public void c() {
        int c2 = c(requireContext());
        this.l = MaterialCalendar.newInstance(this.i, c2, this.k);
        this.j = this.r.isChecked() ? MaterialTextInputPicker.a(this.i, c2, this.k) : this.l;
        b();
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.mtrl_calendar_frame, this.j);
        beginTransaction.commitNow();
        this.j.addOnSelectionChangedListener(new OnSelectionChangedListener<S>() { // from class: com.google.android.material.datepicker.MaterialDatePicker.3
            @Override // com.google.android.material.datepicker.OnSelectionChangedListener
            public void onSelectionChanged(S s) {
                MaterialDatePicker.this.b();
                MaterialDatePicker.this.t.setEnabled(MaterialDatePicker.this.i.isSelectionComplete());
            }

            @Override // com.google.android.material.datepicker.OnSelectionChangedListener
            public void onIncompleteSelectionChanged() {
                MaterialDatePicker.this.t.setEnabled(false);
            }
        });
    }

    private void d(Context context) {
        this.r.setTag(c);
        this.r.setImageDrawable(e(context));
        this.r.setChecked(this.p != 0);
        ViewCompat.setAccessibilityDelegate(this.r, null);
        a(this.r);
        this.r.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.datepicker.MaterialDatePicker.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MaterialDatePicker.this.t.setEnabled(MaterialDatePicker.this.i.isSelectionComplete());
                MaterialDatePicker.this.r.toggle();
                MaterialDatePicker materialDatePicker = MaterialDatePicker.this;
                materialDatePicker.a(materialDatePicker.r);
                MaterialDatePicker.this.c();
            }
        });
    }

    public void a(@NonNull CheckableImageButton checkableImageButton) {
        String str;
        if (this.r.isChecked()) {
            str = checkableImageButton.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode);
        } else {
            str = checkableImageButton.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode);
        }
        this.r.setContentDescription(str);
    }

    @NonNull
    private static Drawable e(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842912}, AppCompatResources.getDrawable(context, R.drawable.material_ic_calendar_black_24dp));
        stateListDrawable.addState(new int[0], AppCompatResources.getDrawable(context, R.drawable.material_ic_edit_black_24dp));
        return stateListDrawable;
    }

    public static boolean a(@NonNull Context context) {
        return a(context, 16843277);
    }

    public static boolean b(@NonNull Context context) {
        return a(context, R.attr.nestedScrollable);
    }

    static boolean a(@NonNull Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{i});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    private static int f(@NonNull Context context) {
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height);
        return dimensionPixelSize + dimensionPixelSize2 + (g.a * resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height)) + ((g.a - 1) * resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding)) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding);
    }

    private static int g(@NonNull Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
        int i = f.a().c;
        return (dimensionPixelOffset * 2) + (resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width) * i) + ((i - 1) * resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding));
    }

    public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.d.add(materialPickerOnPositiveButtonClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener<? super S> materialPickerOnPositiveButtonClickListener) {
        return this.d.remove(materialPickerOnPositiveButtonClickListener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.d.clear();
    }

    public boolean addOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.e.add(onClickListener);
    }

    public boolean removeOnNegativeButtonClickListener(View.OnClickListener onClickListener) {
        return this.e.remove(onClickListener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.e.clear();
    }

    public boolean addOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.f.add(onCancelListener);
    }

    public boolean removeOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.f.remove(onCancelListener);
    }

    public void clearOnCancelListeners() {
        this.f.clear();
    }

    public boolean addOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.g.add(onDismissListener);
    }

    public boolean removeOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.g.remove(onDismissListener);
    }

    public void clearOnDismissListeners() {
        this.g.clear();
    }

    /* loaded from: classes2.dex */
    public static final class Builder<S> {
        final DateSelector<S> a;
        CalendarConstraints c;
        int b = 0;
        int d = 0;
        CharSequence e = null;
        @Nullable
        S f = null;
        int g = 0;

        private Builder(DateSelector<S> dateSelector) {
            this.a = dateSelector;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static <S> Builder<S> customDatePicker(@NonNull DateSelector<S> dateSelector) {
            return new Builder<>(dateSelector);
        }

        @NonNull
        public static Builder<Long> datePicker() {
            return new Builder<>(new SingleDateSelector());
        }

        @NonNull
        public static Builder<Pair<Long, Long>> dateRangePicker() {
            return new Builder<>(new RangeDateSelector());
        }

        @NonNull
        public Builder<S> setSelection(S s) {
            this.f = s;
            return this;
        }

        @NonNull
        public Builder<S> setTheme(@StyleRes int i) {
            this.b = i;
            return this;
        }

        @NonNull
        public Builder<S> setCalendarConstraints(CalendarConstraints calendarConstraints) {
            this.c = calendarConstraints;
            return this;
        }

        @NonNull
        public Builder<S> setTitleText(@StringRes int i) {
            this.d = i;
            this.e = null;
            return this;
        }

        @NonNull
        public Builder<S> setTitleText(@Nullable CharSequence charSequence) {
            this.e = charSequence;
            this.d = 0;
            return this;
        }

        @NonNull
        public Builder<S> setInputMode(int i) {
            this.g = i;
            return this;
        }

        @NonNull
        public MaterialDatePicker<S> build() {
            if (this.c == null) {
                this.c = new CalendarConstraints.Builder().build();
            }
            if (this.d == 0) {
                this.d = this.a.getDefaultTitleResId();
            }
            S s = this.f;
            if (s != null) {
                this.a.setSelection(s);
            }
            if (this.c.c() == null) {
                this.c.a(a());
            }
            return MaterialDatePicker.a(this);
        }

        private f a() {
            long j = this.c.a().e;
            long j2 = this.c.b().e;
            if (!this.a.getSelectedDays().isEmpty()) {
                long longValue = this.a.getSelectedDays().iterator().next().longValue();
                if (longValue >= j && longValue <= j2) {
                    return f.a(longValue);
                }
            }
            long thisMonthInUtcMilliseconds = MaterialDatePicker.thisMonthInUtcMilliseconds();
            if (j <= thisMonthInUtcMilliseconds && thisMonthInUtcMilliseconds <= j2) {
                j = thisMonthInUtcMilliseconds;
            }
            return f.a(j);
        }
    }
}
