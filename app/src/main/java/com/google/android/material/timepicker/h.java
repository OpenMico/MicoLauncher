package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.timepicker.TimePickerView;
import java.lang.reflect.Field;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TimePickerTextInputPresenter.java */
/* loaded from: classes2.dex */
public class h implements TimePickerView.c, f {
    private final LinearLayout a;
    private final d b;
    private final TextWatcher c = new TextWatcherAdapter() { // from class: com.google.android.material.timepicker.h.1
        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            try {
                if (TextUtils.isEmpty(editable)) {
                    h.this.b.c(0);
                    return;
                }
                h.this.b.c(Integer.parseInt(editable.toString()));
            } catch (NumberFormatException unused) {
            }
        }
    };
    private final TextWatcher d = new TextWatcherAdapter() { // from class: com.google.android.material.timepicker.h.2
        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            try {
                if (TextUtils.isEmpty(editable)) {
                    h.this.b.b(0);
                    return;
                }
                h.this.b.b(Integer.parseInt(editable.toString()));
            } catch (NumberFormatException unused) {
            }
        }
    };
    private final ChipTextInputComboView e;
    private final ChipTextInputComboView f;
    private final g g;
    private final EditText h;
    private final EditText i;
    private MaterialButtonToggleGroup j;

    public h(LinearLayout linearLayout, d dVar) {
        this.a = linearLayout;
        this.b = dVar;
        Resources resources = linearLayout.getResources();
        this.e = (ChipTextInputComboView) linearLayout.findViewById(R.id.material_minute_text_input);
        this.f = (ChipTextInputComboView) linearLayout.findViewById(R.id.material_hour_text_input);
        ((TextView) this.e.findViewById(R.id.material_label)).setText(resources.getString(R.string.material_timepicker_minute));
        ((TextView) this.f.findViewById(R.id.material_label)).setText(resources.getString(R.string.material_timepicker_hour));
        this.e.setTag(R.id.selection_type, 12);
        this.f.setTag(R.id.selection_type, 10);
        if (dVar.a == 0) {
            i();
        }
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.google.android.material.timepicker.h.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                h.this.a(((Integer) view.getTag(R.id.selection_type)).intValue());
            }
        };
        this.f.setOnClickListener(onClickListener);
        this.e.setOnClickListener(onClickListener);
        this.f.a(dVar.c());
        this.e.a(dVar.b());
        this.h = this.f.a().getEditText();
        this.i = this.e.a().getEditText();
        if (Build.VERSION.SDK_INT < 21) {
            int color = MaterialColors.getColor(linearLayout, R.attr.colorPrimary);
            a(this.h, color);
            a(this.i, color);
        }
        this.g = new g(this.f, this.e, dVar);
        this.f.a(new a(linearLayout.getContext(), R.string.material_hour_selection));
        this.e.a(new a(linearLayout.getContext(), R.string.material_minute_selection));
        a();
    }

    public void a() {
        g();
        a(this.b);
        this.g.a();
    }

    private void g() {
        this.h.addTextChangedListener(this.d);
        this.i.addTextChangedListener(this.c);
    }

    private void h() {
        this.h.removeTextChangedListener(this.d);
        this.i.removeTextChangedListener(this.c);
    }

    private void a(d dVar) {
        h();
        Locale locale = this.a.getResources().getConfiguration().locale;
        String format = String.format(locale, "%02d", Integer.valueOf(dVar.c));
        String format2 = String.format(locale, "%02d", Integer.valueOf(dVar.a()));
        this.e.a(format);
        this.f.a(format2);
        g();
        j();
    }

    private void i() {
        this.j = (MaterialButtonToggleGroup) this.a.findViewById(R.id.material_clock_period_toggle);
        this.j.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() { // from class: com.google.android.material.timepicker.h.4
            @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean z) {
                h.this.b.d(i == R.id.material_clock_period_pm_button ? 1 : 0);
            }
        });
        this.j.setVisibility(0);
        j();
    }

    private void j() {
        MaterialButtonToggleGroup materialButtonToggleGroup = this.j;
        if (materialButtonToggleGroup != null) {
            materialButtonToggleGroup.check(this.b.e == 0 ? R.id.material_clock_period_am_button : R.id.material_clock_period_pm_button);
        }
    }

    @Override // com.google.android.material.timepicker.TimePickerView.c
    public void a(int i) {
        this.b.d = i;
        boolean z = true;
        this.e.setChecked(i == 12);
        ChipTextInputComboView chipTextInputComboView = this.f;
        if (i != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        j();
    }

    @Override // com.google.android.material.timepicker.f
    public void c() {
        this.a.setVisibility(0);
    }

    @Override // com.google.android.material.timepicker.f
    public void d() {
        View focusedChild = this.a.getFocusedChild();
        if (focusedChild == null) {
            this.a.setVisibility(8);
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(this.a.getContext(), InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedChild.getWindowToken(), 0);
        }
        this.a.setVisibility(8);
    }

    @Override // com.google.android.material.timepicker.f
    public void b() {
        a(this.b);
    }

    private static void a(EditText editText, @ColorInt int i) {
        try {
            Context context = editText.getContext();
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            int i2 = declaredField.getInt(editText);
            Field declaredField2 = TextView.class.getDeclaredField("mEditor");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(editText);
            Field declaredField3 = obj.getClass().getDeclaredField("mCursorDrawable");
            declaredField3.setAccessible(true);
            Drawable drawable = AppCompatResources.getDrawable(context, i2);
            drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
            declaredField3.set(obj, new Drawable[]{drawable, drawable});
        } catch (Throwable unused) {
        }
    }

    public void e() {
        boolean z = true;
        this.e.setChecked(this.b.d == 12);
        ChipTextInputComboView chipTextInputComboView = this.f;
        if (this.b.d != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
    }

    public void f() {
        this.e.setChecked(false);
        this.f.setChecked(false);
    }
}
