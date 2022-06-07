package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.resources.MaterialResources;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IndicatorViewController.java */
/* loaded from: classes2.dex */
public final class f {
    private final Context a;
    @NonNull
    private final TextInputLayout b;
    private LinearLayout c;
    private int d;
    private FrameLayout e;
    @Nullable
    private Animator f;
    private final float g;
    private int h;
    private int i;
    @Nullable
    private CharSequence j;
    private boolean k;
    @Nullable
    private TextView l;
    @Nullable
    private CharSequence m;
    private int n;
    @Nullable
    private ColorStateList o;
    private CharSequence p;
    private boolean q;
    @Nullable
    private TextView r;
    private int s;
    @Nullable
    private ColorStateList t;
    private Typeface u;

    boolean a(int i) {
        return i == 0 || i == 1;
    }

    public f(@NonNull TextInputLayout textInputLayout) {
        this.a = textInputLayout.getContext();
        this.b = textInputLayout;
        this.g = this.a.getResources().getDimensionPixelSize(R.dimen.design_textinput_caption_translate_y);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(CharSequence charSequence) {
        c();
        this.p = charSequence;
        this.r.setText(charSequence);
        if (this.h != 2) {
            this.i = 2;
        }
        a(this.h, this.i, a(this.r, charSequence));
    }

    void a() {
        c();
        if (this.h == 2) {
            this.i = 0;
        }
        a(this.h, this.i, a(this.r, (CharSequence) null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(CharSequence charSequence) {
        c();
        this.j = charSequence;
        this.l.setText(charSequence);
        if (this.h != 1) {
            this.i = 1;
        }
        a(this.h, this.i, a(this.l, charSequence));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.j = null;
        c();
        if (this.h == 1) {
            if (!this.q || TextUtils.isEmpty(this.p)) {
                this.i = 0;
            } else {
                this.i = 2;
            }
        }
        a(this.h, this.i, a(this.l, (CharSequence) null));
    }

    private boolean a(@Nullable TextView textView, @Nullable CharSequence charSequence) {
        return ViewCompat.isLaidOut(this.b) && this.b.isEnabled() && (this.i != this.h || textView == null || !TextUtils.equals(textView.getText(), charSequence));
    }

    private void a(final int i, final int i2, boolean z) {
        if (i != i2) {
            if (z) {
                AnimatorSet animatorSet = new AnimatorSet();
                this.f = animatorSet;
                ArrayList arrayList = new ArrayList();
                a(arrayList, this.q, this.r, 2, i, i2);
                a(arrayList, this.k, this.l, 1, i, i2);
                AnimatorSetCompat.playTogether(animatorSet, arrayList);
                final TextView d = d(i);
                final TextView d2 = d(i2);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.f.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        f.this.h = i2;
                        f.this.f = null;
                        TextView textView = d;
                        if (textView != null) {
                            textView.setVisibility(4);
                            if (i == 1 && f.this.l != null) {
                                f.this.l.setText((CharSequence) null);
                            }
                        }
                        TextView textView2 = d2;
                        if (textView2 != null) {
                            textView2.setTranslationY(0.0f);
                            d2.setAlpha(1.0f);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        TextView textView = d2;
                        if (textView != null) {
                            textView.setVisibility(0);
                        }
                    }
                });
                animatorSet.start();
            } else {
                a(i, i2);
            }
            this.b.a();
            this.b.a(z);
            this.b.b();
        }
    }

    private void a(int i, int i2) {
        TextView d;
        TextView d2;
        if (i != i2) {
            if (!(i2 == 0 || (d2 = d(i2)) == null)) {
                d2.setVisibility(0);
                d2.setAlpha(1.0f);
            }
            if (!(i == 0 || (d = d(i)) == null)) {
                d.setVisibility(4);
                if (i == 1) {
                    d.setText((CharSequence) null);
                }
            }
            this.h = i2;
        }
    }

    private void a(@NonNull List<Animator> list, boolean z, @Nullable TextView textView, int i, int i2, int i3) {
        if (textView != null && z) {
            if (i == i3 || i == i2) {
                list.add(a(textView, i3 == i));
                if (i3 == i) {
                    list.add(a(textView));
                }
            }
        }
    }

    private ObjectAnimator a(TextView textView, boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.ALPHA, z ? 1.0f : 0.0f);
        ofFloat.setDuration(167L);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        return ofFloat;
    }

    private ObjectAnimator a(TextView textView) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, -this.g, 0.0f);
        ofFloat.setDuration(217L);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        return ofFloat;
    }

    void c() {
        Animator animator = this.f;
        if (animator != null) {
            animator.cancel();
        }
    }

    @Nullable
    private TextView d(int i) {
        switch (i) {
            case 1:
                return this.l;
            case 2:
                return this.r;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        if (n()) {
            EditText editText = this.b.getEditText();
            boolean isFontScaleAtLeast1_3 = MaterialResources.isFontScaleAtLeast1_3(this.a);
            ViewCompat.setPaddingRelative(this.c, a(isFontScaleAtLeast1_3, R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingStart(editText)), a(isFontScaleAtLeast1_3, R.dimen.material_helper_text_font_1_3_padding_top, this.a.getResources().getDimensionPixelSize(R.dimen.material_helper_text_default_padding_top)), a(isFontScaleAtLeast1_3, R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingEnd(editText)), 0);
        }
    }

    private boolean n() {
        return (this.c == null || this.b.getEditText() == null) ? false : true;
    }

    private int a(boolean z, @DimenRes int i, int i2) {
        return z ? this.a.getResources().getDimensionPixelSize(i) : i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(TextView textView, int i) {
        if (this.c == null && this.e == null) {
            this.c = new LinearLayout(this.a);
            this.c.setOrientation(0);
            this.b.addView(this.c, -1, -2);
            this.e = new FrameLayout(this.a);
            this.c.addView(this.e, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (this.b.getEditText() != null) {
                d();
            }
        }
        if (a(i)) {
            this.e.setVisibility(0);
            this.e.addView(textView);
        } else {
            this.c.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        }
        this.c.setVisibility(0);
        this.d++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(TextView textView, int i) {
        FrameLayout frameLayout;
        if (this.c != null) {
            if (!a(i) || (frameLayout = this.e) == null) {
                this.c.removeView(textView);
            } else {
                frameLayout.removeView(textView);
            }
            this.d--;
            a(this.c, this.d);
        }
    }

    private void a(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            viewGroup.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        if (this.k != z) {
            c();
            if (z) {
                this.l = new AppCompatTextView(this.a);
                this.l.setId(R.id.textinput_error);
                if (Build.VERSION.SDK_INT >= 17) {
                    this.l.setTextAlignment(5);
                }
                Typeface typeface = this.u;
                if (typeface != null) {
                    this.l.setTypeface(typeface);
                }
                b(this.n);
                a(this.o);
                c(this.m);
                this.l.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion(this.l, 1);
                a(this.l, 0);
            } else {
                b();
                b(this.l, 0);
                this.l = null;
                this.b.a();
                this.b.b();
            }
            this.k = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean e() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean f() {
        return this.q;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(boolean z) {
        if (this.q != z) {
            c();
            if (z) {
                this.r = new AppCompatTextView(this.a);
                this.r.setId(R.id.textinput_helper_text);
                if (Build.VERSION.SDK_INT >= 17) {
                    this.r.setTextAlignment(5);
                }
                Typeface typeface = this.u;
                if (typeface != null) {
                    this.r.setTypeface(typeface);
                }
                this.r.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion(this.r, 1);
                c(this.s);
                b(this.t);
                a(this.r, 1);
            } else {
                a();
                b(this.r, 1);
                this.r = null;
                this.b.a();
                this.b.b();
            }
            this.q = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean g() {
        return e(this.i);
    }

    private boolean e(int i) {
        return i == 1 && this.l != null && !TextUtils.isEmpty(this.j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public CharSequence h() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CharSequence i() {
        return this.p;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Typeface typeface) {
        if (typeface != this.u) {
            this.u = typeface;
            a(this.l, typeface);
            a(this.r, typeface);
        }
    }

    private void a(@Nullable TextView textView, Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public int j() {
        TextView textView = this.l;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList k() {
        TextView textView = this.l;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable ColorStateList colorStateList) {
        this.o = colorStateList;
        TextView textView = this.l;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@StyleRes int i) {
        this.n = i;
        TextView textView = this.l;
        if (textView != null) {
            this.b.a(textView, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@Nullable CharSequence charSequence) {
        this.m = charSequence;
        TextView textView = this.l;
        if (textView != null) {
            textView.setContentDescription(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public CharSequence l() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public int m() {
        TextView textView = this.r;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@Nullable ColorStateList colorStateList) {
        this.t = colorStateList;
        TextView textView = this.r;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@StyleRes int i) {
        this.s = i;
        TextView textView = this.r;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }
}
