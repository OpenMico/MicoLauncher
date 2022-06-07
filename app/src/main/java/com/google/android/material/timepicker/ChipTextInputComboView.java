package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class ChipTextInputComboView extends FrameLayout implements Checkable {
    private final Chip a;
    private final TextInputLayout b;
    private final EditText c;
    private TextWatcher d;
    private TextView e;

    public ChipTextInputComboView(@NonNull Context context) {
        this(context, null);
    }

    public ChipTextInputComboView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChipTextInputComboView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater from = LayoutInflater.from(context);
        this.a = (Chip) from.inflate(R.layout.material_time_chip, (ViewGroup) this, false);
        this.b = (TextInputLayout) from.inflate(R.layout.material_time_input, (ViewGroup) this, false);
        this.c = this.b.getEditText();
        this.c.setVisibility(4);
        this.d = new a();
        this.c.addTextChangedListener(this.d);
        b();
        addView(this.a);
        addView(this.b);
        this.e = (TextView) findViewById(R.id.material_label);
        this.c.setSaveEnabled(false);
    }

    private void b() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.c.setImeHintLocales(getContext().getResources().getConfiguration().getLocales());
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.a.isChecked();
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        this.a.setChecked(z);
        int i = 0;
        this.c.setVisibility(z ? 0 : 4);
        Chip chip = this.a;
        if (z) {
            i = 8;
        }
        chip.setVisibility(i);
        if (isChecked()) {
            this.c.requestFocus();
            if (!TextUtils.isEmpty(this.c.getText())) {
                EditText editText = this.c;
                editText.setSelection(editText.getText().length());
            }
        }
    }

    @Override // android.widget.Checkable
    public void toggle() {
        this.a.toggle();
    }

    public void a(CharSequence charSequence) {
        this.a.setText(b(charSequence));
        if (!TextUtils.isEmpty(this.c.getText())) {
            this.c.removeTextChangedListener(this.d);
            this.c.setText((CharSequence) null);
            this.c.addTextChangedListener(this.d);
        }
    }

    public String b(CharSequence charSequence) {
        return d.a(getResources(), charSequence);
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
        this.a.setOnClickListener(onClickListener);
    }

    @Override // android.view.View
    public void setTag(int i, Object obj) {
        this.a.setTag(i, obj);
    }

    public void a(InputFilter inputFilter) {
        InputFilter[] filters = this.c.getFilters();
        InputFilter[] inputFilterArr = (InputFilter[]) Arrays.copyOf(filters, filters.length + 1);
        inputFilterArr[filters.length] = inputFilter;
        this.c.setFilters(inputFilterArr);
    }

    public TextInputLayout a() {
        return this.b;
    }

    public void a(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        ViewCompat.setAccessibilityDelegate(this.a, accessibilityDelegateCompat);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a extends TextWatcherAdapter {
        private a() {
            ChipTextInputComboView.this = r1;
        }

        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView.this.a.setText(ChipTextInputComboView.this.b("00"));
            } else {
                ChipTextInputComboView.this.a.setText(ChipTextInputComboView.this.b(editable));
            }
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        b();
    }
}
