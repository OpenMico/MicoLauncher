package com.google.android.material.timepicker;

import android.os.Build;
import android.view.accessibility.AccessibilityManager;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import com.google.android.material.timepicker.ClockHandView;
import com.google.android.material.timepicker.TimePickerView;
import com.xiaomi.mi_soundbox_command_sdk.Commands;

/* compiled from: TimePickerClockPresenter.java */
/* loaded from: classes2.dex */
class e implements ClockHandView.OnActionUpListener, ClockHandView.OnRotateListener, TimePickerView.b, TimePickerView.c, f {
    private static final String[] a = {"12", "1", "2", "3", Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "6", "7", "8", Commands.ResolutionValues.BITSTREAM_4K, "10", "11"};
    private static final String[] b = {"00", "2", Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, "6", "8", "10", "12", "14", "16", "18", "20", "22"};
    private static final String[] c = {"00", Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private TimePickerView d;
    private d e;
    private float f;
    private float g;
    private boolean h = false;

    public e(TimePickerView timePickerView, d dVar) {
        this.d = timePickerView;
        this.e = dVar;
        a();
    }

    public void a() {
        if (this.e.a == 0) {
            this.d.a();
        }
        this.d.a((ClockHandView.OnRotateListener) this);
        this.d.a((TimePickerView.c) this);
        this.d.a((TimePickerView.b) this);
        this.d.a((ClockHandView.OnActionUpListener) this);
        h();
        b();
    }

    @Override // com.google.android.material.timepicker.f
    public void b() {
        this.g = this.e.a() * f();
        this.f = this.e.c * 6;
        a(this.e.d, false);
        g();
    }

    @Override // com.google.android.material.timepicker.f
    public void c() {
        this.d.setVisibility(0);
    }

    @Override // com.google.android.material.timepicker.f
    public void d() {
        this.d.setVisibility(8);
    }

    private String[] e() {
        return this.e.a == 1 ? b : a;
    }

    private int f() {
        return this.e.a == 1 ? 15 : 30;
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnRotateListener
    public void onRotate(float f, boolean z) {
        if (!this.h) {
            int i = this.e.b;
            int i2 = this.e.c;
            int round = Math.round(f);
            if (this.e.d == 12) {
                this.e.c((round + 3) / 6);
                this.f = (float) Math.floor(this.e.c * 6);
            } else {
                this.e.b((round + (f() / 2)) / f());
                this.g = this.e.a() * f();
            }
            if (!z) {
                g();
                a(i, i2);
            }
        }
    }

    private void a(int i, int i2) {
        if (this.e.c != i2 || this.e.b != i) {
            this.d.performHapticFeedback(Build.VERSION.SDK_INT >= 21 ? 4 : 1);
        }
    }

    @Override // com.google.android.material.timepicker.TimePickerView.c
    public void a(int i) {
        a(i, true);
    }

    @Override // com.google.android.material.timepicker.TimePickerView.b
    public void b(int i) {
        this.e.d(i);
    }

    void a(int i, boolean z) {
        boolean z2 = i == 12;
        this.d.a(z2);
        this.e.d = i;
        this.d.a(z2 ? c : e(), z2 ? R.string.material_minute_suffix : R.string.material_hour_suffix);
        this.d.a(z2 ? this.f : this.g, z);
        this.d.a(i);
        TimePickerView timePickerView = this.d;
        timePickerView.a(new a(timePickerView.getContext(), R.string.material_hour_selection));
        TimePickerView timePickerView2 = this.d;
        timePickerView2.b(new a(timePickerView2.getContext(), R.string.material_minute_selection));
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnActionUpListener
    public void onActionUp(float f, boolean z) {
        this.h = true;
        int i = this.e.c;
        int i2 = this.e.b;
        if (this.e.d == 10) {
            this.d.a(this.g, false);
            if (!((AccessibilityManager) ContextCompat.getSystemService(this.d.getContext(), AccessibilityManager.class)).isTouchExplorationEnabled()) {
                a(12, true);
            }
        } else {
            int round = Math.round(f);
            if (!z) {
                this.e.c(((round + 15) / 30) * 5);
                this.f = this.e.c * 6;
            }
            this.d.a(this.f, z);
        }
        this.h = false;
        g();
        a(i2, i);
    }

    private void g() {
        this.d.a(this.e.e, this.e.a(), this.e.c);
    }

    private void h() {
        a(a, "%d");
        a(b, "%d");
        a(c, "%02d");
    }

    private void a(String[] strArr, String str) {
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = d.a(this.d.getResources(), strArr[i], str);
        }
    }
}
