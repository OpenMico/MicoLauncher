package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: DropdownMenuEndIconDelegate.java */
/* loaded from: classes2.dex */
class d extends e {
    private static final boolean d;
    private final TextWatcher e = new TextWatcherAdapter() { // from class: com.google.android.material.textfield.d.1
        @Override // com.google.android.material.internal.TextWatcherAdapter, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            final AutoCompleteTextView c = d.c(d.this.a.getEditText());
            if (d.this.o.isTouchExplorationEnabled() && d.d((EditText) c) && !d.this.c.hasFocus()) {
                c.dismissDropDown();
            }
            c.post(new Runnable() { // from class: com.google.android.material.textfield.d.1.1
                @Override // java.lang.Runnable
                public void run() {
                    boolean isPopupShowing = c.isPopupShowing();
                    d.this.b(isPopupShowing);
                    d.this.j = isPopupShowing;
                }
            });
        }
    };
    private final View.OnFocusChangeListener f = new View.OnFocusChangeListener() { // from class: com.google.android.material.textfield.d.3
        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            d.this.a.setEndIconActivated(z);
            if (!z) {
                d.this.b(false);
                d.this.j = false;
            }
        }
    };
    private final TextInputLayout.AccessibilityDelegate g = new TextInputLayout.AccessibilityDelegate(this.a) { // from class: com.google.android.material.textfield.d.4
        @Override // com.google.android.material.textfield.TextInputLayout.AccessibilityDelegate, androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (!d.d(d.this.a.getEditText())) {
                accessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
            }
            if (accessibilityNodeInfoCompat.isShowingHintText()) {
                accessibilityNodeInfoCompat.setHintText(null);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onPopulateAccessibilityEvent(View view, @NonNull AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            AutoCompleteTextView c = d.c(d.this.a.getEditText());
            if (accessibilityEvent.getEventType() == 1 && d.this.o.isTouchExplorationEnabled() && !d.d(d.this.a.getEditText())) {
                d.this.a(c);
            }
        }
    };
    private final TextInputLayout.OnEditTextAttachedListener h = new TextInputLayout.OnEditTextAttachedListener() { // from class: com.google.android.material.textfield.d.5
        @Override // com.google.android.material.textfield.TextInputLayout.OnEditTextAttachedListener
        public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
            AutoCompleteTextView c = d.c(textInputLayout.getEditText());
            d.this.b(c);
            d.this.c(c);
            d.this.d(c);
            c.setThreshold(0);
            c.removeTextChangedListener(d.this.e);
            c.addTextChangedListener(d.this.e);
            textInputLayout.setEndIconCheckable(true);
            textInputLayout.setErrorIconDrawable((Drawable) null);
            if (!d.d((EditText) c)) {
                ViewCompat.setImportantForAccessibility(d.this.c, 2);
            }
            textInputLayout.setTextInputAccessibilityDelegate(d.this.g);
            textInputLayout.setEndIconVisible(true);
        }
    };
    @SuppressLint({"ClickableViewAccessibility"})
    private final TextInputLayout.OnEndIconChangedListener i = new TextInputLayout.OnEndIconChangedListener() { // from class: com.google.android.material.textfield.d.6
        @Override // com.google.android.material.textfield.TextInputLayout.OnEndIconChangedListener
        public void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int i) {
            final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) textInputLayout.getEditText();
            if (autoCompleteTextView != null && i == 3) {
                autoCompleteTextView.post(new Runnable() { // from class: com.google.android.material.textfield.d.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        autoCompleteTextView.removeTextChangedListener(d.this.e);
                    }
                });
                if (autoCompleteTextView.getOnFocusChangeListener() == d.this.f) {
                    autoCompleteTextView.setOnFocusChangeListener(null);
                }
                autoCompleteTextView.setOnTouchListener(null);
                if (d.d) {
                    autoCompleteTextView.setOnDismissListener(null);
                }
            }
        }
    };
    private boolean j = false;
    private boolean k = false;
    private long l = Long.MAX_VALUE;
    private StateListDrawable m;
    private MaterialShapeDrawable n;
    @Nullable
    private AccessibilityManager o;
    private ValueAnimator p;
    private ValueAnimator q;

    @Override // com.google.android.material.textfield.e
    boolean a(int i) {
        return i != 0;
    }

    @Override // com.google.android.material.textfield.e
    boolean b() {
        return true;
    }

    static {
        d = Build.VERSION.SDK_INT >= 21;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(@NonNull TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    @Override // com.google.android.material.textfield.e
    void a() {
        float dimensionPixelOffset = this.b.getResources().getDimensionPixelOffset(R.dimen.mtrl_shape_corner_size_small_component);
        float dimensionPixelOffset2 = this.b.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        int dimensionPixelOffset3 = this.b.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        MaterialShapeDrawable a = a(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset3);
        MaterialShapeDrawable a2 = a(0.0f, dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset3);
        this.n = a;
        this.m = new StateListDrawable();
        this.m.addState(new int[]{16842922}, a);
        this.m.addState(new int[0], a2);
        this.a.setEndIconDrawable(AppCompatResources.getDrawable(this.b, d ? R.drawable.mtrl_dropdown_arrow : R.drawable.mtrl_ic_arrow_drop_down));
        this.a.setEndIconContentDescription(this.a.getResources().getText(R.string.exposed_dropdown_menu_content_description));
        this.a.setEndIconOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.textfield.d.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                d.this.a((AutoCompleteTextView) d.this.a.getEditText());
            }
        });
        this.a.addOnEditTextAttachedListener(this.h);
        this.a.addOnEndIconChangedListener(this.i);
        e();
        this.o = (AccessibilityManager) this.b.getSystemService("accessibility");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable AutoCompleteTextView autoCompleteTextView) {
        if (autoCompleteTextView != null) {
            if (d()) {
                this.j = false;
            }
            if (!this.j) {
                if (d) {
                    b(!this.k);
                } else {
                    this.k = !this.k;
                    this.c.toggle();
                }
                if (this.k) {
                    autoCompleteTextView.requestFocus();
                    autoCompleteTextView.showDropDown();
                    return;
                }
                autoCompleteTextView.dismissDropDown();
                return;
            }
            this.j = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(@NonNull AutoCompleteTextView autoCompleteTextView) {
        if (d) {
            int boxBackgroundMode = this.a.getBoxBackgroundMode();
            if (boxBackgroundMode == 2) {
                autoCompleteTextView.setDropDownBackgroundDrawable(this.n);
            } else if (boxBackgroundMode == 1) {
                autoCompleteTextView.setDropDownBackgroundDrawable(this.m);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(@NonNull AutoCompleteTextView autoCompleteTextView) {
        if (!d((EditText) autoCompleteTextView)) {
            int boxBackgroundMode = this.a.getBoxBackgroundMode();
            MaterialShapeDrawable boxBackground = this.a.getBoxBackground();
            int color = MaterialColors.getColor(autoCompleteTextView, R.attr.colorControlHighlight);
            int[][] iArr = {new int[]{16842919}, new int[0]};
            if (boxBackgroundMode == 2) {
                a(autoCompleteTextView, color, iArr, boxBackground);
            } else if (boxBackgroundMode == 1) {
                b(autoCompleteTextView, color, iArr, boxBackground);
            }
        }
    }

    private void a(@NonNull AutoCompleteTextView autoCompleteTextView, int i, int[][] iArr, @NonNull MaterialShapeDrawable materialShapeDrawable) {
        LayerDrawable layerDrawable;
        int color = MaterialColors.getColor(autoCompleteTextView, R.attr.colorSurface);
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        int layer = MaterialColors.layer(i, color, 0.1f);
        materialShapeDrawable2.setFillColor(new ColorStateList(iArr, new int[]{layer, 0}));
        if (d) {
            materialShapeDrawable2.setTint(color);
            ColorStateList colorStateList = new ColorStateList(iArr, new int[]{layer, color});
            MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
            materialShapeDrawable3.setTint(-1);
            layerDrawable = new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, materialShapeDrawable2, materialShapeDrawable3), materialShapeDrawable});
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable});
        }
        ViewCompat.setBackground(autoCompleteTextView, layerDrawable);
    }

    private void b(@NonNull AutoCompleteTextView autoCompleteTextView, int i, int[][] iArr, @NonNull MaterialShapeDrawable materialShapeDrawable) {
        int boxBackgroundColor = this.a.getBoxBackgroundColor();
        int[] iArr2 = {MaterialColors.layer(i, boxBackgroundColor, 0.1f), boxBackgroundColor};
        if (d) {
            ViewCompat.setBackground(autoCompleteTextView, new RippleDrawable(new ColorStateList(iArr, iArr2), materialShapeDrawable, materialShapeDrawable));
            return;
        }
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        materialShapeDrawable2.setFillColor(new ColorStateList(iArr, iArr2));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{materialShapeDrawable, materialShapeDrawable2});
        int paddingStart = ViewCompat.getPaddingStart(autoCompleteTextView);
        int paddingTop = autoCompleteTextView.getPaddingTop();
        int paddingEnd = ViewCompat.getPaddingEnd(autoCompleteTextView);
        int paddingBottom = autoCompleteTextView.getPaddingBottom();
        ViewCompat.setBackground(autoCompleteTextView, layerDrawable);
        ViewCompat.setPaddingRelative(autoCompleteTextView, paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"ClickableViewAccessibility"})
    public void d(@NonNull final AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.material.textfield.d.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(@NonNull View view, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    if (d.this.d()) {
                        d.this.j = false;
                    }
                    d.this.a(autoCompleteTextView);
                }
                return false;
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(this.f);
        if (d) {
            autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() { // from class: com.google.android.material.textfield.d.9
                @Override // android.widget.AutoCompleteTextView.OnDismissListener
                public void onDismiss() {
                    d.this.j = true;
                    d.this.l = System.currentTimeMillis();
                    d.this.b(false);
                }
            });
        }
    }

    private MaterialShapeDrawable a(float f, float f2, float f3, int i) {
        ShapeAppearanceModel build = ShapeAppearanceModel.builder().setTopLeftCornerSize(f).setTopRightCornerSize(f).setBottomLeftCornerSize(f2).setBottomRightCornerSize(f2).build();
        MaterialShapeDrawable createWithElevationOverlay = MaterialShapeDrawable.createWithElevationOverlay(this.b, f3);
        createWithElevationOverlay.setShapeAppearanceModel(build);
        createWithElevationOverlay.setPadding(0, i, 0, i);
        return createWithElevationOverlay;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        long currentTimeMillis = System.currentTimeMillis() - this.l;
        return currentTimeMillis < 0 || currentTimeMillis > 300;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public static AutoCompleteTextView c(EditText editText) {
        if (editText instanceof AutoCompleteTextView) {
            return (AutoCompleteTextView) editText;
        }
        throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(@NonNull EditText editText) {
        return editText.getKeyListener() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        if (this.k != z) {
            this.k = z;
            this.q.cancel();
            this.p.start();
        }
    }

    private void e() {
        this.q = a(67, 0.0f, 1.0f);
        this.p = a(50, 1.0f, 0.0f);
        this.p.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.d.10
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                d.this.c.setChecked(d.this.k);
                d.this.q.start();
            }
        });
    }

    private ValueAnimator a(int i, float... fArr) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        ofFloat.setDuration(i);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.d.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                d.this.c.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return ofFloat;
    }
}
