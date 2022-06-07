package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class RangeDateSelector implements DateSelector<Pair<Long, Long>> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator<RangeDateSelector>() { // from class: com.google.android.material.datepicker.RangeDateSelector.3
        @NonNull
        /* renamed from: a */
        public RangeDateSelector createFromParcel(@NonNull Parcel parcel) {
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            rangeDateSelector.c = (Long) parcel.readValue(Long.class.getClassLoader());
            rangeDateSelector.d = (Long) parcel.readValue(Long.class.getClassLoader());
            return rangeDateSelector;
        }

        @NonNull
        /* renamed from: a */
        public RangeDateSelector[] newArray(int i) {
            return new RangeDateSelector[i];
        }
    };
    private String a;
    private final String b = StringUtils.SPACE;
    @Nullable
    private Long c = null;
    @Nullable
    private Long d = null;
    @Nullable
    private Long e = null;
    @Nullable
    private Long f = null;

    private boolean a(long j, long j2) {
        return j <= j2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public void select(long j) {
        Long l = this.c;
        if (l == null) {
            this.c = Long.valueOf(j);
        } else if (this.d != null || !a(l.longValue(), j)) {
            this.d = null;
            this.c = Long.valueOf(j);
        } else {
            this.d = Long.valueOf(j);
        }
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public boolean isSelectionComplete() {
        Long l = this.c;
        return (l == null || this.d == null || !a(l.longValue(), this.d.longValue())) ? false : true;
    }

    public void setSelection(@NonNull Pair<Long, Long> pair) {
        if (!(pair.first == null || pair.second == null)) {
            Preconditions.checkArgument(a(pair.first.longValue(), pair.second.longValue()));
        }
        Long l = null;
        this.c = pair.first == null ? null : Long.valueOf(k.a(pair.first.longValue()));
        if (pair.second != null) {
            l = Long.valueOf(k.a(pair.second.longValue()));
        }
        this.d = l;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Pair<Long, Long> getSelection() {
        return new Pair<>(this.c, this.d);
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        if (this.c == null || this.d == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Pair(this.c, this.d));
        return arrayList;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public Collection<Long> getSelectedDays() {
        ArrayList arrayList = new ArrayList();
        Long l = this.c;
        if (l != null) {
            arrayList.add(l);
        }
        Long l2 = this.d;
        if (l2 != null) {
            arrayList.add(l2);
        }
        return arrayList;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public int getDefaultThemeResId(@NonNull Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return MaterialAttributes.resolveOrThrow(context, Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) > resources.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis) ? R.attr.materialCalendarTheme : R.attr.materialCalendarFullscreenTheme, MaterialDatePicker.class.getCanonicalName());
    }

    @Override // com.google.android.material.datepicker.DateSelector
    @NonNull
    public String getSelectionDisplayString(@NonNull Context context) {
        Resources resources = context.getResources();
        if (this.c == null && this.d == null) {
            return resources.getString(R.string.mtrl_picker_range_header_unselected);
        }
        Long l = this.d;
        if (l == null) {
            return resources.getString(R.string.mtrl_picker_range_header_only_start_selected, d.e(this.c.longValue()));
        }
        Long l2 = this.c;
        if (l2 == null) {
            return resources.getString(R.string.mtrl_picker_range_header_only_end_selected, d.e(this.d.longValue()));
        }
        Pair<String, String> a = d.a(l2, l);
        return resources.getString(R.string.mtrl_picker_range_header_selected, a.first, a.second);
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_range_header_title;
    }

    @Override // com.google.android.material.datepicker.DateSelector
    public View onCreateTextInputView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle, CalendarConstraints calendarConstraints, @NonNull final OnSelectionChangedListener<Pair<Long, Long>> onSelectionChangedListener) {
        View inflate = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, viewGroup, false);
        final TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.mtrl_picker_text_input_range_start);
        final TextInputLayout textInputLayout2 = (TextInputLayout) inflate.findViewById(R.id.mtrl_picker_text_input_range_end);
        EditText editText = textInputLayout.getEditText();
        EditText editText2 = textInputLayout2.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            editText.setInputType(17);
            editText2.setInputType(17);
        }
        this.a = inflate.getResources().getString(R.string.mtrl_picker_invalid_range);
        SimpleDateFormat d = k.d();
        Long l = this.c;
        if (l != null) {
            editText.setText(d.format(l));
            this.e = this.c;
        }
        Long l2 = this.d;
        if (l2 != null) {
            editText2.setText(d.format(l2));
            this.f = this.d;
        }
        String a = k.a(inflate.getResources(), d);
        textInputLayout.setPlaceholderText(a);
        textInputLayout2.setPlaceholderText(a);
        editText.addTextChangedListener(new c(a, d, textInputLayout, calendarConstraints) { // from class: com.google.android.material.datepicker.RangeDateSelector.1
            @Override // com.google.android.material.datepicker.c
            void a(@Nullable Long l3) {
                RangeDateSelector.this.e = l3;
                RangeDateSelector.this.a(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }

            @Override // com.google.android.material.datepicker.c
            void a() {
                RangeDateSelector.this.e = null;
                RangeDateSelector.this.a(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }
        });
        editText2.addTextChangedListener(new c(a, d, textInputLayout2, calendarConstraints) { // from class: com.google.android.material.datepicker.RangeDateSelector.2
            @Override // com.google.android.material.datepicker.c
            void a(@Nullable Long l3) {
                RangeDateSelector.this.f = l3;
                RangeDateSelector.this.a(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }

            @Override // com.google.android.material.datepicker.c
            void a() {
                RangeDateSelector.this.f = null;
                RangeDateSelector.this.a(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }
        });
        ViewUtils.requestFocusAndShowKeyboard(editText);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull TextInputLayout textInputLayout, @NonNull TextInputLayout textInputLayout2, @NonNull OnSelectionChangedListener<Pair<Long, Long>> onSelectionChangedListener) {
        Long l = this.e;
        if (l == null || this.f == null) {
            a(textInputLayout, textInputLayout2);
            onSelectionChangedListener.onIncompleteSelectionChanged();
        } else if (a(l.longValue(), this.f.longValue())) {
            this.c = this.e;
            this.d = this.f;
            onSelectionChangedListener.onSelectionChanged(getSelection());
        } else {
            b(textInputLayout, textInputLayout2);
            onSelectionChangedListener.onIncompleteSelectionChanged();
        }
    }

    private void a(@NonNull TextInputLayout textInputLayout, @NonNull TextInputLayout textInputLayout2) {
        if (textInputLayout.getError() != null && this.a.contentEquals(textInputLayout.getError())) {
            textInputLayout.setError(null);
        }
        if (textInputLayout2.getError() != null && StringUtils.SPACE.contentEquals(textInputLayout2.getError())) {
            textInputLayout2.setError(null);
        }
    }

    private void b(@NonNull TextInputLayout textInputLayout, @NonNull TextInputLayout textInputLayout2) {
        textInputLayout.setError(this.a);
        textInputLayout2.setError(StringUtils.SPACE);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeValue(this.c);
        parcel.writeValue(this.d);
    }
}
