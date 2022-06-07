package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: ClearTextEndIconDelegate.java */
/* loaded from: classes2.dex */
class a extends e {
    private final TextWatcher d = new TextWatcher() { // from class: com.google.android.material.textfield.a.1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(@NonNull Editable editable) {
            if (a.this.a.getSuffixText() == null) {
                a aVar = a.this;
                aVar.b(aVar.a.hasFocus() && a.b(editable));
            }
        }
    };
    private final View.OnFocusChangeListener e = new View.OnFocusChangeListener() { // from class: com.google.android.material.textfield.a.2
        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            boolean z2 = true;
            boolean z3 = !TextUtils.isEmpty(((EditText) view).getText());
            a aVar = a.this;
            if (!z3 || !z) {
                z2 = false;
            }
            aVar.b(z2);
        }
    };
    private final TextInputLayout.OnEditTextAttachedListener f = new TextInputLayout.OnEditTextAttachedListener() { // from class: com.google.android.material.textfield.a.3
        @Override // com.google.android.material.textfield.TextInputLayout.OnEditTextAttachedListener
        public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            textInputLayout.setEndIconVisible(editText.hasFocus() && a.b(editText.getText()));
            textInputLayout.setEndIconCheckable(false);
            editText.setOnFocusChangeListener(a.this.e);
            editText.removeTextChangedListener(a.this.d);
            editText.addTextChangedListener(a.this.d);
        }
    };
    private final TextInputLayout.OnEndIconChangedListener g = new TextInputLayout.OnEndIconChangedListener() { // from class: com.google.android.material.textfield.a.4
        @Override // com.google.android.material.textfield.TextInputLayout.OnEndIconChangedListener
        public void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int i) {
            final EditText editText = textInputLayout.getEditText();
            if (editText != null && i == 2) {
                editText.post(new Runnable() { // from class: com.google.android.material.textfield.a.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        editText.removeTextChangedListener(a.this.d);
                    }
                });
                if (editText.getOnFocusChangeListener() == a.this.e) {
                    editText.setOnFocusChangeListener(null);
                }
            }
        }
    };
    private AnimatorSet h;
    private ValueAnimator i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    @Override // com.google.android.material.textfield.e
    void a() {
        this.a.setEndIconDrawable(AppCompatResources.getDrawable(this.b, R.drawable.mtrl_ic_cancel));
        this.a.setEndIconContentDescription(this.a.getResources().getText(R.string.clear_text_end_icon_content_description));
        this.a.setEndIconOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.textfield.a.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Editable text = a.this.a.getEditText().getText();
                if (text != null) {
                    text.clear();
                }
                a.this.a.refreshEndIconDrawableState();
            }
        });
        this.a.addOnEditTextAttachedListener(this.f);
        this.a.addOnEndIconChangedListener(this.g);
        c();
    }

    @Override // com.google.android.material.textfield.e
    void a(boolean z) {
        if (this.a.getSuffixText() != null) {
            b(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        boolean z2 = this.a.isEndIconVisible() == z;
        if (z && !this.h.isRunning()) {
            this.i.cancel();
            this.h.start();
            if (z2) {
                this.h.end();
            }
        } else if (!z) {
            this.h.cancel();
            this.i.start();
            if (z2) {
                this.i.end();
            }
        }
    }

    private void c() {
        ValueAnimator d = d();
        ValueAnimator a = a(0.0f, 1.0f);
        this.h = new AnimatorSet();
        this.h.playTogether(d, a);
        this.h.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.a.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                a.this.a.setEndIconVisible(true);
            }
        });
        this.i = a(1.0f, 0.0f);
        this.i.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.a.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                a.this.a.setEndIconVisible(false);
            }
        });
    }

    private ValueAnimator a(float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        ofFloat.setDuration(100L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.a.8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                a.this.c.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return ofFloat;
    }

    private ValueAnimator d() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.8f, 1.0f);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        ofFloat.setDuration(150L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.a.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                a.this.c.setScaleX(floatValue);
                a.this.c.setScaleY(floatValue);
            }
        });
        return ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(@NonNull Editable editable) {
        return editable.length() > 0;
    }
}
