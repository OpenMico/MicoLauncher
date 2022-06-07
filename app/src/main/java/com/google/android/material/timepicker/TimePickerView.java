package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class TimePickerView extends ConstraintLayout implements TimePickerControls {
    private final Chip a;
    private final Chip b;
    private final ClockHandView c;
    private final ClockFaceView d;
    private final MaterialButtonToggleGroup e;
    private final View.OnClickListener f;
    private b g;
    private c h;
    private a i;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface a {
        void a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface b {
        void b(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface c {
        void a(int i);
    }

    public TimePickerView(Context context) {
        this(context, null);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = new View.OnClickListener() { // from class: com.google.android.material.timepicker.TimePickerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TimePickerView.this.h != null) {
                    TimePickerView.this.h.a(((Integer) view.getTag(R.id.selection_type)).intValue());
                }
            }
        };
        LayoutInflater.from(context).inflate(R.layout.material_timepicker, this);
        this.d = (ClockFaceView) findViewById(R.id.material_clock_face);
        this.e = (MaterialButtonToggleGroup) findViewById(R.id.material_clock_period_toggle);
        this.e.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() { // from class: com.google.android.material.timepicker.TimePickerView.2
            @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i2, boolean z) {
                int i3 = i2 == R.id.material_clock_period_pm_button ? 1 : 0;
                if (TimePickerView.this.g != null && z) {
                    TimePickerView.this.g.b(i3);
                }
            }
        });
        this.a = (Chip) findViewById(R.id.material_minute_tv);
        this.b = (Chip) findViewById(R.id.material_hour_tv);
        this.c = (ClockHandView) findViewById(R.id.material_clock_hand);
        b();
        c();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void b() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.google.android.material.timepicker.TimePickerView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                boolean onDoubleTap = super.onDoubleTap(motionEvent);
                if (TimePickerView.this.i != null) {
                    TimePickerView.this.i.a();
                }
                return onDoubleTap;
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: com.google.android.material.timepicker.TimePickerView.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (((Checkable) view).isChecked()) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
                return false;
            }
        };
        this.a.setOnTouchListener(onTouchListener);
        this.b.setOnTouchListener(onTouchListener);
    }

    public void a(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        ViewCompat.setAccessibilityDelegate(this.b, accessibilityDelegateCompat);
    }

    public void b(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        ViewCompat.setAccessibilityDelegate(this.a, accessibilityDelegateCompat);
    }

    private void c() {
        this.a.setTag(R.id.selection_type, 12);
        this.b.setTag(R.id.selection_type, 10);
        this.a.setOnClickListener(this.f);
        this.b.setOnClickListener(this.f);
    }

    public void a(String[] strArr, @StringRes int i) {
        this.d.a(strArr, i);
    }

    public void a(float f, boolean z) {
        this.c.a(f, z);
    }

    public void a(boolean z) {
        this.c.a(z);
    }

    @SuppressLint({"DefaultLocale"})
    public void a(int i, int i2, int i3) {
        this.e.check(i == 1 ? R.id.material_clock_period_pm_button : R.id.material_clock_period_am_button);
        Locale locale = getResources().getConfiguration().locale;
        String format = String.format(locale, "%02d", Integer.valueOf(i3));
        String format2 = String.format(locale, "%02d", Integer.valueOf(i2));
        this.a.setText(format);
        this.b.setText(format2);
    }

    public void a(int i) {
        boolean z = true;
        this.a.setChecked(i == 12);
        Chip chip = this.b;
        if (i != 10) {
            z = false;
        }
        chip.setChecked(z);
    }

    public void a(ClockHandView.OnRotateListener onRotateListener) {
        this.c.a(onRotateListener);
    }

    public void a(ClockHandView.OnActionUpListener onActionUpListener) {
        this.c.a(onActionUpListener);
    }

    public void a(b bVar) {
        this.g = bVar;
    }

    public void a(c cVar) {
        this.h = cVar;
    }

    public void a(@Nullable a aVar) {
        this.i = aVar;
    }

    public void a() {
        this.e.setVisibility(0);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(@NonNull View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            d();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        d();
    }

    private void d() {
        if (this.e.getVisibility() == 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            int i = 1;
            if (ViewCompat.getLayoutDirection(this) == 0) {
                i = 2;
            }
            constraintSet.clear(R.id.material_clock_display, i);
            constraintSet.applyTo(this);
        }
    }
}
