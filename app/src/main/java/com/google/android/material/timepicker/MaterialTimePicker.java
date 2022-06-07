package com.google.android.material.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.timepicker.TimePickerView;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class MaterialTimePicker extends DialogFragment {
    public static final int INPUT_MODE_CLOCK = 0;
    public static final int INPUT_MODE_KEYBOARD = 1;
    private TimePickerView e;
    private ViewStub f;
    @Nullable
    private e g;
    @Nullable
    private h h;
    @Nullable
    private f i;
    @DrawableRes
    private int j;
    @DrawableRes
    private int k;
    private String m;
    private MaterialButton n;
    private d p;
    private final Set<View.OnClickListener> a = new LinkedHashSet();
    private final Set<View.OnClickListener> b = new LinkedHashSet();
    private final Set<DialogInterface.OnCancelListener> c = new LinkedHashSet();
    private final Set<DialogInterface.OnDismissListener> d = new LinkedHashSet();
    private int l = 0;
    private int o = 0;
    private int q = 0;

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public static MaterialTimePicker b(@NonNull Builder builder) {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker();
        Bundle bundle = new Bundle();
        bundle.putParcelable("TIME_PICKER_TIME_MODEL", builder.a);
        bundle.putInt("TIME_PICKER_INPUT_MODE", builder.b);
        bundle.putInt("TIME_PICKER_TITLE_RES", builder.c);
        bundle.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", builder.e);
        if (builder.d != null) {
            bundle.putString("TIME_PICKER_TITLE_TEXT", builder.d.toString());
        }
        materialTimePicker.setArguments(bundle);
        return materialTimePicker;
    }

    @IntRange(from = 0, to = 60)
    public int getMinute() {
        return this.p.c;
    }

    @IntRange(from = 0, to = 23)
    public int getHour() {
        return this.p.b % 24;
    }

    public int getInputMode() {
        return this.o;
    }

    @Override // androidx.fragment.app.DialogFragment
    @NonNull
    public final Dialog onCreateDialog(@Nullable Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), b());
        Context context = dialog.getContext();
        int resolveOrThrow = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialTimePicker.class.getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context, null, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.MaterialTimePicker, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
        this.k = obtainStyledAttributes.getResourceId(R.styleable.MaterialTimePicker_clockIcon, 0);
        this.j = obtainStyledAttributes.getResourceId(R.styleable.MaterialTimePicker_keyboardIcon, 0);
        obtainStyledAttributes.recycle();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(resolveOrThrow));
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(materialShapeDrawable);
        window.requestFeature(1);
        window.setLayout(-2, -2);
        return dialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        a(bundle);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("TIME_PICKER_TIME_MODEL", this.p);
        bundle.putInt("TIME_PICKER_INPUT_MODE", this.o);
        bundle.putInt("TIME_PICKER_TITLE_RES", this.l);
        bundle.putString("TIME_PICKER_TITLE_TEXT", this.m);
        bundle.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", this.q);
    }

    private void a(@Nullable Bundle bundle) {
        if (bundle != null) {
            this.p = (d) bundle.getParcelable("TIME_PICKER_TIME_MODEL");
            if (this.p == null) {
                this.p = new d();
            }
            this.o = bundle.getInt("TIME_PICKER_INPUT_MODE", 0);
            this.l = bundle.getInt("TIME_PICKER_TITLE_RES", 0);
            this.m = bundle.getString("TIME_PICKER_TITLE_TEXT");
            this.q = bundle.getInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", 0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public final View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.material_timepicker_dialog, viewGroup);
        this.e = (TimePickerView) viewGroup2.findViewById(R.id.material_timepicker_view);
        this.e.a(new TimePickerView.a() { // from class: com.google.android.material.timepicker.MaterialTimePicker.1
            @Override // com.google.android.material.timepicker.TimePickerView.a
            public void a() {
                MaterialTimePicker.this.o = 1;
                MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
                materialTimePicker.a(materialTimePicker.n);
                MaterialTimePicker.this.h.e();
            }
        });
        this.f = (ViewStub) viewGroup2.findViewById(R.id.material_textinput_timepicker);
        this.n = (MaterialButton) viewGroup2.findViewById(R.id.material_timepicker_mode_button);
        TextView textView = (TextView) viewGroup2.findViewById(R.id.header_title);
        if (!TextUtils.isEmpty(this.m)) {
            textView.setText(this.m);
        }
        int i = this.l;
        if (i != 0) {
            textView.setText(i);
        }
        a(this.n);
        ((Button) viewGroup2.findViewById(R.id.material_timepicker_ok_button)).setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.timepicker.MaterialTimePicker.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                for (View.OnClickListener onClickListener : MaterialTimePicker.this.a) {
                    onClickListener.onClick(view);
                }
                MaterialTimePicker.this.dismiss();
            }
        });
        ((Button) viewGroup2.findViewById(R.id.material_timepicker_cancel_button)).setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.timepicker.MaterialTimePicker.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                for (View.OnClickListener onClickListener : MaterialTimePicker.this.b) {
                    onClickListener.onClick(view);
                }
                MaterialTimePicker.this.dismiss();
            }
        });
        this.n.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.timepicker.MaterialTimePicker.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
                materialTimePicker.o = materialTimePicker.o == 0 ? 1 : 0;
                MaterialTimePicker materialTimePicker2 = MaterialTimePicker.this;
                materialTimePicker2.a(materialTimePicker2.n);
            }
        });
        return viewGroup2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.i = null;
        this.g = null;
        this.h = null;
        this.e = null;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(@NonNull DialogInterface dialogInterface) {
        for (DialogInterface.OnCancelListener onCancelListener : this.c) {
            onCancelListener.onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(@NonNull DialogInterface dialogInterface) {
        for (DialogInterface.OnDismissListener onDismissListener : this.d) {
            onDismissListener.onDismiss(dialogInterface);
        }
        super.onDismiss(dialogInterface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MaterialButton materialButton) {
        f fVar = this.i;
        if (fVar != null) {
            fVar.d();
        }
        this.i = b(this.o);
        this.i.c();
        this.i.b();
        Pair<Integer, Integer> c = c(this.o);
        materialButton.setIconResource(((Integer) c.first).intValue());
        materialButton.setContentDescription(getResources().getString(((Integer) c.second).intValue()));
    }

    private f b(int i) {
        if (i == 0) {
            e eVar = this.g;
            if (eVar == null) {
                eVar = new e(this.e, this.p);
            }
            this.g = eVar;
            return this.g;
        }
        if (this.h == null) {
            this.h = new h((LinearLayout) this.f.inflate(), this.p);
        }
        this.h.f();
        return this.h;
    }

    private Pair<Integer, Integer> c(int i) {
        switch (i) {
            case 0:
                return new Pair<>(Integer.valueOf(this.j), Integer.valueOf(R.string.material_timepicker_text_input_mode_description));
            case 1:
                return new Pair<>(Integer.valueOf(this.k), Integer.valueOf(R.string.material_timepicker_clock_mode_description));
            default:
                throw new IllegalArgumentException("no icon for mode: " + i);
        }
    }

    public boolean addOnPositiveButtonClickListener(@NonNull View.OnClickListener onClickListener) {
        return this.a.add(onClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(@NonNull View.OnClickListener onClickListener) {
        return this.a.remove(onClickListener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.a.clear();
    }

    public boolean addOnNegativeButtonClickListener(@NonNull View.OnClickListener onClickListener) {
        return this.b.add(onClickListener);
    }

    public boolean removeOnNegativeButtonClickListener(@NonNull View.OnClickListener onClickListener) {
        return this.b.remove(onClickListener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.b.clear();
    }

    public boolean addOnCancelListener(@NonNull DialogInterface.OnCancelListener onCancelListener) {
        return this.c.add(onCancelListener);
    }

    public boolean removeOnCancelListener(@NonNull DialogInterface.OnCancelListener onCancelListener) {
        return this.c.remove(onCancelListener);
    }

    public void clearOnCancelListeners() {
        this.c.clear();
    }

    public boolean addOnDismissListener(@NonNull DialogInterface.OnDismissListener onDismissListener) {
        return this.d.add(onDismissListener);
    }

    public boolean removeOnDismissListener(@NonNull DialogInterface.OnDismissListener onDismissListener) {
        return this.d.remove(onDismissListener);
    }

    public void clearOnDismissListeners() {
        this.d.clear();
    }

    private int b() {
        int i = this.q;
        if (i != 0) {
            return i;
        }
        TypedValue resolve = MaterialAttributes.resolve(requireContext(), R.attr.materialTimePickerTheme);
        if (resolve == null) {
            return 0;
        }
        return resolve.data;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private int b;
        private CharSequence d;
        private d a = new d();
        private int c = 0;
        private int e = 0;

        @NonNull
        public Builder setInputMode(int i) {
            this.b = i;
            return this;
        }

        @NonNull
        public Builder setHour(@IntRange(from = 0, to = 23) int i) {
            this.a.a(i);
            return this;
        }

        @NonNull
        public Builder setMinute(@IntRange(from = 0, to = 60) int i) {
            this.a.c(i);
            return this;
        }

        @NonNull
        public Builder setTimeFormat(int i) {
            int i2 = this.a.b;
            int i3 = this.a.c;
            this.a = new d(i);
            this.a.c(i3);
            this.a.a(i2);
            return this;
        }

        @NonNull
        public Builder setTitleText(@StringRes int i) {
            this.c = i;
            return this;
        }

        @NonNull
        public Builder setTitleText(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        @NonNull
        public Builder setTheme(@StyleRes int i) {
            this.e = i;
            return this;
        }

        @NonNull
        public MaterialTimePicker build() {
            return MaterialTimePicker.b(this);
        }
    }
}
