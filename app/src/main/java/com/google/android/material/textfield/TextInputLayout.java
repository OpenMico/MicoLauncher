package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes2.dex */
public class TextInputLayout extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    public static final int END_ICON_CLEAR_TEXT = 2;
    public static final int END_ICON_CUSTOM = -1;
    public static final int END_ICON_DROPDOWN_MENU = 3;
    public static final int END_ICON_NONE = 0;
    public static final int END_ICON_PASSWORD_TOGGLE = 1;
    private static final int d = R.style.Widget_Design_TextInputLayout;
    @Nullable
    private CharSequence A;
    @NonNull
    private final TextView B;
    private boolean C;
    private CharSequence D;
    private boolean E;
    @Nullable
    private MaterialShapeDrawable F;
    @Nullable
    private MaterialShapeDrawable G;
    @NonNull
    private ShapeAppearanceModel H;
    private final int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int O;
    @ColorInt
    private int P;
    @ColorInt
    private int Q;
    private final Rect R;
    private final Rect S;
    private final RectF T;
    private Typeface U;
    @NonNull
    private final CheckableImageButton V;
    private ColorStateList W;
    EditText a;
    @ColorInt
    private int aA;
    private ColorStateList aB;
    @ColorInt
    private int aC;
    @ColorInt
    private int aD;
    @ColorInt
    private int aE;
    @ColorInt
    private int aF;
    @ColorInt
    private int aG;
    private boolean aH;
    private boolean aI;
    private boolean aJ;
    private ValueAnimator aK;
    private boolean aL;
    private boolean aM;
    private boolean aa;
    private PorterDuff.Mode ab;
    private boolean ac;
    @Nullable
    private Drawable ad;
    private int ae;
    private View.OnLongClickListener af;
    private final LinkedHashSet<OnEditTextAttachedListener> ag;
    private int ah;
    private final SparseArray<e> ai;
    @NonNull
    private final CheckableImageButton aj;
    private final LinkedHashSet<OnEndIconChangedListener> ak;
    private ColorStateList al;
    private boolean am;
    private PorterDuff.Mode an;
    private boolean ao;
    @Nullable
    private Drawable ap;
    private int aq;
    private Drawable ar;
    private View.OnLongClickListener as;
    private View.OnLongClickListener at;
    @NonNull
    private final CheckableImageButton au;
    private ColorStateList av;
    private ColorStateList aw;
    private ColorStateList ax;
    @ColorInt
    private int ay;
    @ColorInt
    private int az;
    boolean b;
    final CollapsingTextHelper c;
    @NonNull
    private final FrameLayout e;
    @NonNull
    private final LinearLayout f;
    @NonNull
    private final LinearLayout g;
    @NonNull
    private final FrameLayout h;
    private CharSequence i;
    private int j;
    private int k;
    private final f l;
    private int m;
    private boolean n;
    @Nullable
    private TextView o;
    private int p;
    private int q;
    private CharSequence r;
    private boolean s;
    private TextView t;
    @Nullable
    private ColorStateList u;
    private int v;
    @Nullable
    private ColorStateList w;
    @Nullable
    private ColorStateList x;
    @Nullable
    private CharSequence y;
    @NonNull
    private final TextView z;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface BoxBackgroundMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface EndIconMode {
    }

    /* loaded from: classes2.dex */
    public interface OnEditTextAttachedListener {
        void onEditTextAttached(@NonNull TextInputLayout textInputLayout);
    }

    /* loaded from: classes2.dex */
    public interface OnEndIconChangedListener {
        void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int i);
    }

    public TextInputLayout(@NonNull Context context) {
        this(context, null);
    }

    public TextInputLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textInputStyle);
    }

    public TextInputLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, d), attributeSet, i);
        ActivityChooserView.b bVar;
        int i2;
        boolean z;
        this.j = -1;
        this.k = -1;
        this.l = new f(this);
        this.R = new Rect();
        this.S = new Rect();
        this.T = new RectF();
        this.ag = new LinkedHashSet<>();
        this.ah = 0;
        this.ai = new SparseArray<>();
        this.ak = new LinkedHashSet<>();
        this.c = new CollapsingTextHelper(this);
        Context context2 = getContext();
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.e = new FrameLayout(context2);
        this.e.setAddStatesFromChildren(true);
        addView(this.e);
        this.f = new LinearLayout(context2);
        this.f.setOrientation(0);
        this.f.setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.START));
        this.e.addView(this.f);
        this.g = new LinearLayout(context2);
        this.g.setOrientation(0);
        this.g.setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.END));
        this.e.addView(this.g);
        this.h = new FrameLayout(context2);
        this.h.setLayoutParams(new FrameLayout.LayoutParams(-2, -1));
        this.c.setTextSizeInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.c.setPositionInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.c.setCollapsedTextGravity(BadgeDrawable.TOP_START);
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.TextInputLayout, i, d, R.styleable.TextInputLayout_counterTextAppearance, R.styleable.TextInputLayout_counterOverflowTextAppearance, R.styleable.TextInputLayout_errorTextAppearance, R.styleable.TextInputLayout_helperTextTextAppearance, R.styleable.TextInputLayout_hintTextAppearance);
        this.C = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        setHint(obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_android_hint));
        this.aJ = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        this.aI = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_expandedHintEnabled, true);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_android_minWidth)) {
            setMinWidth(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.TextInputLayout_android_minWidth, -1));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_android_maxWidth)) {
            setMaxWidth(obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.TextInputLayout_android_maxWidth, -1));
        }
        this.H = ShapeAppearanceModel.builder(context2, attributeSet, i, d).build();
        this.I = context2.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
        this.L = obtainTintedStyledAttributes.getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
        this.N = obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidth, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default));
        this.O = obtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidthFocused, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused));
        this.M = this.N;
        float dimension = obtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, -1.0f);
        float dimension2 = obtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, -1.0f);
        float dimension3 = obtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, -1.0f);
        float dimension4 = obtainTintedStyledAttributes.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, -1.0f);
        ShapeAppearanceModel.Builder builder = this.H.toBuilder();
        if (dimension >= 0.0f) {
            builder.setTopLeftCornerSize(dimension);
        }
        if (dimension2 >= 0.0f) {
            builder.setTopRightCornerSize(dimension2);
        }
        if (dimension3 >= 0.0f) {
            builder.setBottomRightCornerSize(dimension3);
        }
        if (dimension4 >= 0.0f) {
            builder.setBottomLeftCornerSize(dimension4);
        }
        this.H = builder.build();
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_boxBackgroundColor);
        if (colorStateList != null) {
            this.aC = colorStateList.getDefaultColor();
            this.Q = this.aC;
            if (colorStateList.isStateful()) {
                this.aD = colorStateList.getColorForState(new int[]{-16842910}, -1);
                this.aE = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
                this.aF = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            } else {
                this.aE = this.aC;
                ColorStateList colorStateList2 = AppCompatResources.getColorStateList(context2, R.color.mtrl_filled_background_color);
                this.aD = colorStateList2.getColorForState(new int[]{-16842910}, -1);
                this.aF = colorStateList2.getColorForState(new int[]{16843623}, -1);
            }
        } else {
            this.Q = 0;
            this.aC = 0;
            this.aD = 0;
            this.aE = 0;
            this.aF = 0;
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList3 = obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.ax = colorStateList3;
            this.aw = colorStateList3;
        }
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_boxStrokeColor);
        this.aA = obtainTintedStyledAttributes.getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
        this.ay = ContextCompat.getColor(context2, R.color.mtrl_textinput_default_box_stroke_color);
        this.aG = ContextCompat.getColor(context2, R.color.mtrl_textinput_disabled_color);
        this.az = ContextCompat.getColor(context2, R.color.mtrl_textinput_hovered_box_stroke_color);
        if (colorStateList4 != null) {
            setBoxStrokeColorStateList(colorStateList4);
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_boxStrokeErrorColor)) {
            setBoxStrokeErrorColor(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_boxStrokeErrorColor));
        }
        if (obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        int resourceId = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        CharSequence text = obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_errorContentDescription);
        boolean z2 = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        this.au = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_end_icon, (ViewGroup) this.g, false);
        this.au.setId(R.id.text_input_error_icon);
        this.au.setVisibility(8);
        if (MaterialResources.isFontScaleAtLeast1_3(context2)) {
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.au.getLayoutParams(), 0);
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_errorIconDrawable)) {
            setErrorIconDrawable(obtainTintedStyledAttributes.getDrawable(R.styleable.TextInputLayout_errorIconDrawable));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_errorIconTint)) {
            setErrorIconTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_errorIconTint));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_errorIconTintMode)) {
            setErrorIconTintMode(ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_errorIconTintMode, -1), null));
        }
        this.au.setContentDescription(getResources().getText(R.string.error_icon_content_description));
        ViewCompat.setImportantForAccessibility(this.au, 2);
        this.au.setClickable(false);
        this.au.setPressable(false);
        this.au.setFocusable(false);
        int resourceId2 = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
        boolean z3 = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
        CharSequence text2 = obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_helperText);
        int resourceId3 = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_placeholderTextAppearance, 0);
        CharSequence text3 = obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_placeholderText);
        int resourceId4 = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0);
        CharSequence text4 = obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_prefixText);
        int resourceId5 = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_suffixTextAppearance, 0);
        CharSequence text5 = obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_suffixText);
        boolean z4 = obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.q = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.p = obtainTintedStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.V = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_start_icon, (ViewGroup) this.f, false);
        this.V.setVisibility(8);
        if (MaterialResources.isFontScaleAtLeast1_3(context2)) {
            MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams) this.V.getLayoutParams(), 0);
            bVar = null;
        } else {
            bVar = null;
        }
        setStartIconOnClickListener(bVar);
        setStartIconOnLongClickListener(bVar);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_startIconDrawable)) {
            setStartIconDrawable(obtainTintedStyledAttributes.getDrawable(R.styleable.TextInputLayout_startIconDrawable));
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_startIconContentDescription)) {
                setStartIconContentDescription(obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_startIconContentDescription));
            }
            setStartIconCheckable(obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_startIconTint)) {
            setStartIconTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_startIconTint));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_startIconTintMode)) {
            setStartIconTintMode(ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_startIconTintMode, -1), null));
        }
        setBoxBackgroundMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
        this.aj = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_end_icon, (ViewGroup) this.h, false);
        this.h.addView(this.aj);
        this.aj.setVisibility(8);
        if (MaterialResources.isFontScaleAtLeast1_3(context2)) {
            i2 = 0;
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.aj.getLayoutParams(), 0);
        } else {
            i2 = 0;
        }
        this.ai.append(-1, new b(this));
        this.ai.append(i2, new g(this));
        this.ai.append(1, new h(this));
        this.ai.append(2, new a(this));
        this.ai.append(3, new d(this));
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_endIconMode)) {
            setEndIconMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_endIconMode, 0));
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_endIconDrawable)) {
                setEndIconDrawable(obtainTintedStyledAttributes.getDrawable(R.styleable.TextInputLayout_endIconDrawable));
            }
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_endIconContentDescription)) {
                setEndIconContentDescription(obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_endIconContentDescription));
            }
            setEndIconCheckable(obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_endIconCheckable, true));
        } else if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
            setEndIconMode(obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false) ? 1 : 0);
            setEndIconDrawable(obtainTintedStyledAttributes.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable));
            setEndIconContentDescription(obtainTintedStyledAttributes.getText(R.styleable.TextInputLayout_passwordToggleContentDescription));
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
                setEndIconTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_passwordToggleTint));
            }
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
                setEndIconTintMode(ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null));
            }
        }
        if (!obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_endIconTint)) {
                setEndIconTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, R.styleable.TextInputLayout_endIconTint));
            }
            if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_endIconTintMode)) {
                setEndIconTintMode(ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(R.styleable.TextInputLayout_endIconTintMode, -1), null));
            }
        }
        this.z = new AppCompatTextView(context2);
        this.z.setId(R.id.textinput_prefix_text);
        this.z.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        ViewCompat.setAccessibilityLiveRegion(this.z, 1);
        this.f.addView(this.V);
        this.f.addView(this.z);
        this.B = new AppCompatTextView(context2);
        this.B.setId(R.id.textinput_suffix_text);
        this.B.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 80));
        ViewCompat.setAccessibilityLiveRegion(this.B, 1);
        this.g.addView(this.B);
        this.g.addView(this.au);
        this.g.addView(this.h);
        setHelperTextEnabled(z3);
        setHelperText(text2);
        setHelperTextTextAppearance(resourceId2);
        setErrorEnabled(z2);
        setErrorTextAppearance(resourceId);
        setErrorContentDescription(text);
        setCounterTextAppearance(this.q);
        setCounterOverflowTextAppearance(this.p);
        setPlaceholderText(text3);
        setPlaceholderTextAppearance(resourceId3);
        setPrefixText(text4);
        setPrefixTextAppearance(resourceId4);
        setSuffixText(text5);
        setSuffixTextAppearance(resourceId5);
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_errorTextColor)) {
            setErrorTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_errorTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_helperTextTextColor)) {
            setHelperTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_helperTextTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_hintTextColor)) {
            setHintTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_hintTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_counterTextColor)) {
            setCounterTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_counterTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_counterOverflowTextColor)) {
            setCounterOverflowTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_counterOverflowTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_placeholderTextColor)) {
            setPlaceholderTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_placeholderTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_prefixTextColor)) {
            setPrefixTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_prefixTextColor));
        }
        if (obtainTintedStyledAttributes.hasValue(R.styleable.TextInputLayout_suffixTextColor)) {
            setSuffixTextColor(obtainTintedStyledAttributes.getColorStateList(R.styleable.TextInputLayout_suffixTextColor));
            z = z4;
        } else {
            z = z4;
        }
        setCounterEnabled(z);
        setEnabled(obtainTintedStyledAttributes.getBoolean(R.styleable.TextInputLayout_android_enabled, true));
        obtainTintedStyledAttributes.recycle();
        ViewCompat.setImportantForAccessibility(this, 2);
        if (Build.VERSION.SDK_INT >= 26) {
            ViewCompat.setImportantForAutofill(this, 1);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(@NonNull View view, int i, @NonNull ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
            this.e.addView(view, layoutParams2);
            this.e.setLayoutParams(layoutParams);
            j();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    @NonNull
    public MaterialShapeDrawable getBoxBackground() {
        int i = this.K;
        if (i == 1 || i == 2) {
            return this.F;
        }
        throw new IllegalStateException();
    }

    public void setBoxBackgroundMode(int i) {
        if (i != this.K) {
            this.K = i;
            if (this.a != null) {
                d();
            }
        }
    }

    public int getBoxBackgroundMode() {
        return this.K;
    }

    private void d() {
        e();
        f();
        b();
        h();
        i();
        if (this.K != 0) {
            j();
        }
    }

    private void e() {
        switch (this.K) {
            case 0:
                this.F = null;
                this.G = null;
                return;
            case 1:
                this.F = new MaterialShapeDrawable(this.H);
                this.G = new MaterialShapeDrawable();
                return;
            case 2:
                if (!this.C || (this.F instanceof c)) {
                    this.F = new MaterialShapeDrawable(this.H);
                } else {
                    this.F = new c(this.H);
                }
                this.G = null;
                return;
            default:
                throw new IllegalArgumentException(this.K + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
    }

    private void f() {
        if (g()) {
            ViewCompat.setBackground(this.a, this.F);
        }
    }

    private boolean g() {
        EditText editText = this.a;
        return (editText == null || this.F == null || editText.getBackground() != null || this.K == 0) ? false : true;
    }

    private void h() {
        if (this.K != 1) {
            return;
        }
        if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
            this.L = getResources().getDimensionPixelSize(R.dimen.material_font_2_0_box_collapsed_padding_top);
        } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            this.L = getResources().getDimensionPixelSize(R.dimen.material_font_1_3_box_collapsed_padding_top);
        }
    }

    private void i() {
        if (this.a != null && this.K == 1) {
            if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
                EditText editText = this.a;
                ViewCompat.setPaddingRelative(editText, ViewCompat.getPaddingStart(editText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.getPaddingEnd(this.a), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText2 = this.a;
                ViewCompat.setPaddingRelative(editText2, ViewCompat.getPaddingStart(editText2), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.getPaddingEnd(this.a), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
    }

    public void setBoxStrokeWidthResource(@DimenRes int i) {
        setBoxStrokeWidth(getResources().getDimensionPixelSize(i));
    }

    public void setBoxStrokeWidth(int i) {
        this.N = i;
        b();
    }

    public int getBoxStrokeWidth() {
        return this.N;
    }

    public void setBoxStrokeWidthFocusedResource(@DimenRes int i) {
        setBoxStrokeWidthFocused(getResources().getDimensionPixelSize(i));
    }

    public void setBoxStrokeWidthFocused(int i) {
        this.O = i;
        b();
    }

    public int getBoxStrokeWidthFocused() {
        return this.O;
    }

    public void setBoxStrokeColor(@ColorInt int i) {
        if (this.aA != i) {
            this.aA = i;
            b();
        }
    }

    public int getBoxStrokeColor() {
        return this.aA;
    }

    public void setBoxStrokeColorStateList(@NonNull ColorStateList colorStateList) {
        if (colorStateList.isStateful()) {
            this.ay = colorStateList.getDefaultColor();
            this.aG = colorStateList.getColorForState(new int[]{-16842910}, -1);
            this.az = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.aA = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.aA != colorStateList.getDefaultColor()) {
            this.aA = colorStateList.getDefaultColor();
        }
        b();
    }

    public void setBoxStrokeErrorColor(@Nullable ColorStateList colorStateList) {
        if (this.aB != colorStateList) {
            this.aB = colorStateList;
            b();
        }
    }

    @Nullable
    public ColorStateList getBoxStrokeErrorColor() {
        return this.aB;
    }

    public void setBoxBackgroundColorResource(@ColorRes int i) {
        setBoxBackgroundColor(ContextCompat.getColor(getContext(), i));
    }

    public void setBoxBackgroundColor(@ColorInt int i) {
        if (this.Q != i) {
            this.Q = i;
            this.aC = i;
            this.aE = i;
            this.aF = i;
            y();
        }
    }

    public void setBoxBackgroundColorStateList(@NonNull ColorStateList colorStateList) {
        this.aC = colorStateList.getDefaultColor();
        this.Q = this.aC;
        this.aD = colorStateList.getColorForState(new int[]{-16842910}, -1);
        this.aE = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.aF = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        y();
    }

    public int getBoxBackgroundColor() {
        return this.Q;
    }

    public void setBoxCornerRadiiResources(@DimenRes int i, @DimenRes int i2, @DimenRes int i3, @DimenRes int i4) {
        setBoxCornerRadii(getContext().getResources().getDimension(i), getContext().getResources().getDimension(i2), getContext().getResources().getDimension(i4), getContext().getResources().getDimension(i3));
    }

    public void setBoxCornerRadii(float f, float f2, float f3, float f4) {
        MaterialShapeDrawable materialShapeDrawable = this.F;
        if (materialShapeDrawable == null || materialShapeDrawable.getTopLeftCornerResolvedSize() != f || this.F.getTopRightCornerResolvedSize() != f2 || this.F.getBottomRightCornerResolvedSize() != f4 || this.F.getBottomLeftCornerResolvedSize() != f3) {
            this.H = this.H.toBuilder().setTopLeftCornerSize(f).setTopRightCornerSize(f2).setBottomRightCornerSize(f4).setBottomLeftCornerSize(f3).build();
            y();
        }
    }

    public float getBoxCornerRadiusTopStart() {
        return this.F.getTopLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.F.getTopRightCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.F.getBottomLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.F.getBottomRightCornerResolvedSize();
    }

    public void setTypeface(@Nullable Typeface typeface) {
        if (typeface != this.U) {
            this.U = typeface;
            this.c.setTypefaces(typeface);
            this.l.a(typeface);
            TextView textView = this.o;
            if (textView != null) {
                textView.setTypeface(typeface);
            }
        }
    }

    @Nullable
    public Typeface getTypeface() {
        return this.U;
    }

    @Override // android.view.ViewGroup, android.view.View
    @TargetApi(26)
    public void dispatchProvideAutofillStructure(@NonNull ViewStructure viewStructure, int i) {
        EditText editText = this.a;
        if (editText == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        if (this.i != null) {
            boolean z = this.E;
            this.E = false;
            CharSequence hint = editText.getHint();
            this.a.setHint(this.i);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, i);
            } finally {
                this.a.setHint(hint);
                this.E = z;
            }
        } else {
            viewStructure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(viewStructure, i);
            onProvideAutofillVirtualStructure(viewStructure, i);
            viewStructure.setChildCount(this.e.getChildCount());
            for (int i2 = 0; i2 < this.e.getChildCount(); i2++) {
                View childAt = this.e.getChildAt(i2);
                ViewStructure newChild = viewStructure.newChild(i2);
                childAt.dispatchProvideAutofillStructure(newChild, i);
                if (childAt == this.a) {
                    newChild.setHint(getHint());
                }
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.a == null) {
            if (this.ah != 3 && !(editText instanceof TextInputEditText)) {
                Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.a = editText;
            setMinWidth(this.j);
            setMaxWidth(this.k);
            d();
            setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
            this.c.setTypefaces(this.a.getTypeface());
            this.c.setExpandedTextSize(this.a.getTextSize());
            int gravity = this.a.getGravity();
            this.c.setCollapsedTextGravity((gravity & (-113)) | 48);
            this.c.setExpandedTextGravity(gravity);
            this.a.addTextChangedListener(new TextWatcher() { // from class: com.google.android.material.textfield.TextInputLayout.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(@NonNull Editable editable) {
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.a(!textInputLayout.aM);
                    if (TextInputLayout.this.b) {
                        TextInputLayout.this.a(editable.length());
                    }
                    if (TextInputLayout.this.s) {
                        TextInputLayout.this.b(editable.length());
                    }
                }
            });
            if (this.aw == null) {
                this.aw = this.a.getHintTextColors();
            }
            if (this.C) {
                if (TextUtils.isEmpty(this.D)) {
                    this.i = this.a.getHint();
                    setHint(this.i);
                    this.a.setHint((CharSequence) null);
                }
                this.E = true;
            }
            if (this.o != null) {
                a(this.a.getText().length());
            }
            a();
            this.l.d();
            this.f.bringToFront();
            this.g.bringToFront();
            this.h.bringToFront();
            this.au.bringToFront();
            E();
            r();
            t();
            if (!isEnabled()) {
                editText.setEnabled(false);
            }
            a(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void j() {
        if (this.K != 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.e.getLayoutParams();
            int v = v();
            if (v != layoutParams.topMargin) {
                layoutParams.topMargin = v;
                this.e.requestLayout();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public int getBaseline() {
        EditText editText = this.a;
        if (editText != null) {
            return editText.getBaseline() + getPaddingTop() + v();
        }
        return super.getBaseline();
    }

    public void a(boolean z) {
        a(z, false);
    }

    private void a(boolean z, boolean z2) {
        ColorStateList colorStateList;
        TextView textView;
        boolean isEnabled = isEnabled();
        EditText editText = this.a;
        boolean z3 = editText != null && !TextUtils.isEmpty(editText.getText());
        EditText editText2 = this.a;
        boolean z4 = editText2 != null && editText2.hasFocus();
        boolean g = this.l.g();
        ColorStateList colorStateList2 = this.aw;
        if (colorStateList2 != null) {
            this.c.setCollapsedTextColor(colorStateList2);
            this.c.setExpandedTextColor(this.aw);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.aw;
            int colorForState = colorStateList3 != null ? colorStateList3.getColorForState(new int[]{-16842910}, this.aG) : this.aG;
            this.c.setCollapsedTextColor(ColorStateList.valueOf(colorForState));
            this.c.setExpandedTextColor(ColorStateList.valueOf(colorForState));
        } else if (g) {
            this.c.setCollapsedTextColor(this.l.k());
        } else if (this.n && (textView = this.o) != null) {
            this.c.setCollapsedTextColor(textView.getTextColors());
        } else if (z4 && (colorStateList = this.ax) != null) {
            this.c.setCollapsedTextColor(colorStateList);
        }
        if (z3 || !this.aI || (isEnabled() && z4)) {
            if (z2 || this.aH) {
                c(z);
            }
        } else if (z2 || !this.aH) {
            d(z);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.a;
    }

    public void setMinWidth(@Px int i) {
        this.j = i;
        EditText editText = this.a;
        if (editText != null && i != -1) {
            editText.setMinWidth(i);
        }
    }

    public void setMinWidthResource(@DimenRes int i) {
        setMinWidth(getContext().getResources().getDimensionPixelSize(i));
    }

    @Px
    public int getMinWidth() {
        return this.j;
    }

    public void setMaxWidth(@Px int i) {
        this.k = i;
        EditText editText = this.a;
        if (editText != null && i != -1) {
            editText.setMaxWidth(i);
        }
    }

    public void setMaxWidthResource(@DimenRes int i) {
        setMaxWidth(getContext().getResources().getDimensionPixelSize(i));
    }

    @Px
    public int getMaxWidth() {
        return this.k;
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.C) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHint(@StringRes int i) {
        setHint(i != 0 ? getResources().getText(i) : null);
    }

    private void setHintInternal(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.D)) {
            this.D = charSequence;
            this.c.setText(charSequence);
            if (!this.aH) {
                M();
            }
        }
    }

    @Nullable
    public CharSequence getHint() {
        if (this.C) {
            return this.D;
        }
        return null;
    }

    public void setHintEnabled(boolean z) {
        if (z != this.C) {
            this.C = z;
            if (!this.C) {
                this.E = false;
                if (!TextUtils.isEmpty(this.D) && TextUtils.isEmpty(this.a.getHint())) {
                    this.a.setHint(this.D);
                }
                setHintInternal(null);
            } else {
                CharSequence hint = this.a.getHint();
                if (!TextUtils.isEmpty(hint)) {
                    if (TextUtils.isEmpty(this.D)) {
                        setHint(hint);
                    }
                    this.a.setHint((CharSequence) null);
                }
                this.E = true;
            }
            if (this.a != null) {
                j();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.C;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isProvidingHint() {
        return this.E;
    }

    public void setHintTextAppearance(@StyleRes int i) {
        this.c.setCollapsedTextAppearance(i);
        this.ax = this.c.getCollapsedTextColor();
        if (this.a != null) {
            a(false);
            j();
        }
    }

    public void setHintTextColor(@Nullable ColorStateList colorStateList) {
        if (this.ax != colorStateList) {
            if (this.aw == null) {
                this.c.setCollapsedTextColor(colorStateList);
            }
            this.ax = colorStateList;
            if (this.a != null) {
                a(false);
            }
        }
    }

    @Nullable
    public ColorStateList getHintTextColor() {
        return this.ax;
    }

    public void setDefaultHintTextColor(@Nullable ColorStateList colorStateList) {
        this.aw = colorStateList;
        this.ax = colorStateList;
        if (this.a != null) {
            a(false);
        }
    }

    @Nullable
    public ColorStateList getDefaultHintTextColor() {
        return this.aw;
    }

    public void setErrorEnabled(boolean z) {
        this.l.a(z);
    }

    public void setErrorTextAppearance(@StyleRes int i) {
        this.l.b(i);
    }

    public void setErrorTextColor(@Nullable ColorStateList colorStateList) {
        this.l.a(colorStateList);
    }

    @ColorInt
    public int getErrorCurrentTextColors() {
        return this.l.j();
    }

    public void setHelperTextTextAppearance(@StyleRes int i) {
        this.l.c(i);
    }

    public void setHelperTextColor(@Nullable ColorStateList colorStateList) {
        this.l.b(colorStateList);
    }

    public boolean isErrorEnabled() {
        return this.l.e();
    }

    public void setHelperTextEnabled(boolean z) {
        this.l.b(z);
    }

    public void setHelperText(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.l.a(charSequence);
        } else if (isHelperTextEnabled()) {
            setHelperTextEnabled(false);
        }
    }

    public boolean isHelperTextEnabled() {
        return this.l.f();
    }

    @ColorInt
    public int getHelperTextCurrentTextColor() {
        return this.l.m();
    }

    public void setErrorContentDescription(@Nullable CharSequence charSequence) {
        this.l.c(charSequence);
    }

    @Nullable
    public CharSequence getErrorContentDescription() {
        return this.l.l();
    }

    public void setError(@Nullable CharSequence charSequence) {
        if (!this.l.e()) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.l.b(charSequence);
        } else {
            this.l.b();
        }
    }

    public void setErrorIconDrawable(@DrawableRes int i) {
        setErrorIconDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
        refreshErrorIconDrawableState();
    }

    public void setErrorIconDrawable(@Nullable Drawable drawable) {
        this.au.setImageDrawable(drawable);
        setErrorIconVisible(drawable != null && this.l.e());
    }

    @Nullable
    public Drawable getErrorIconDrawable() {
        return this.au.getDrawable();
    }

    public void setErrorIconTintList(@Nullable ColorStateList colorStateList) {
        this.av = colorStateList;
        Drawable drawable = this.au.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTintList(drawable, colorStateList);
        }
        if (this.au.getDrawable() != drawable) {
            this.au.setImageDrawable(drawable);
        }
    }

    public void setErrorIconTintMode(@Nullable PorterDuff.Mode mode) {
        Drawable drawable = this.au.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTintMode(drawable, mode);
        }
        if (this.au.getDrawable() != drawable) {
            this.au.setImageDrawable(drawable);
        }
    }

    public void setCounterEnabled(boolean z) {
        if (this.b != z) {
            if (z) {
                this.o = new AppCompatTextView(getContext());
                this.o.setId(R.id.textinput_counter);
                Typeface typeface = this.U;
                if (typeface != null) {
                    this.o.setTypeface(typeface);
                }
                this.o.setMaxLines(1);
                this.l.a(this.o, 2);
                MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.o.getLayoutParams(), getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_counter_margin_start));
                u();
                k();
            } else {
                this.l.b(this.o, 2);
                this.o = null;
            }
            this.b = z;
        }
    }

    public void setCounterTextAppearance(int i) {
        if (this.q != i) {
            this.q = i;
            u();
        }
    }

    public void setCounterTextColor(@Nullable ColorStateList colorStateList) {
        if (this.w != colorStateList) {
            this.w = colorStateList;
            u();
        }
    }

    @Nullable
    public ColorStateList getCounterTextColor() {
        return this.w;
    }

    public void setCounterOverflowTextAppearance(int i) {
        if (this.p != i) {
            this.p = i;
            u();
        }
    }

    public void setCounterOverflowTextColor(@Nullable ColorStateList colorStateList) {
        if (this.x != colorStateList) {
            this.x = colorStateList;
            u();
        }
    }

    @Nullable
    public ColorStateList getCounterOverflowTextColor() {
        return this.w;
    }

    public boolean isCounterEnabled() {
        return this.b;
    }

    public void setCounterMaxLength(int i) {
        if (this.m != i) {
            if (i > 0) {
                this.m = i;
            } else {
                this.m = -1;
            }
            if (this.b) {
                k();
            }
        }
    }

    private void k() {
        if (this.o != null) {
            EditText editText = this.a;
            a(editText == null ? 0 : editText.getText().length());
        }
    }

    void a(int i) {
        boolean z = this.n;
        int i2 = this.m;
        if (i2 == -1) {
            this.o.setText(String.valueOf(i));
            this.o.setContentDescription(null);
            this.n = false;
        } else {
            this.n = i > i2;
            a(getContext(), this.o, i, this.m, this.n);
            if (z != this.n) {
                u();
            }
            this.o.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(R.string.character_counter_pattern, Integer.valueOf(i), Integer.valueOf(this.m))));
        }
        if (this.a != null && z != this.n) {
            a(false);
            b();
            a();
        }
    }

    private static void a(@NonNull Context context, @NonNull TextView textView, int i, int i2, boolean z) {
        textView.setContentDescription(context.getString(z ? R.string.character_counter_overflowed_content_description : R.string.character_counter_content_description, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void setPlaceholderText(@Nullable CharSequence charSequence) {
        if (!this.s || !TextUtils.isEmpty(charSequence)) {
            if (!this.s) {
                setPlaceholderTextEnabled(true);
            }
            this.r = charSequence;
        } else {
            setPlaceholderTextEnabled(false);
        }
        l();
    }

    @Nullable
    public CharSequence getPlaceholderText() {
        if (this.s) {
            return this.r;
        }
        return null;
    }

    private void setPlaceholderTextEnabled(boolean z) {
        if (this.s != z) {
            if (z) {
                this.t = new AppCompatTextView(getContext());
                this.t.setId(R.id.textinput_placeholder);
                ViewCompat.setAccessibilityLiveRegion(this.t, 1);
                setPlaceholderTextAppearance(this.v);
                setPlaceholderTextColor(this.u);
                o();
            } else {
                p();
                this.t = null;
            }
            this.s = z;
        }
    }

    private void l() {
        EditText editText = this.a;
        b(editText == null ? 0 : editText.getText().length());
    }

    public void b(int i) {
        if (i != 0 || this.aH) {
            n();
        } else {
            m();
        }
    }

    private void m() {
        TextView textView = this.t;
        if (textView != null && this.s) {
            textView.setText(this.r);
            this.t.setVisibility(0);
            this.t.bringToFront();
        }
    }

    private void n() {
        TextView textView = this.t;
        if (textView != null && this.s) {
            textView.setText((CharSequence) null);
            this.t.setVisibility(4);
        }
    }

    private void o() {
        TextView textView = this.t;
        if (textView != null) {
            this.e.addView(textView);
            this.t.setVisibility(0);
        }
    }

    private void p() {
        TextView textView = this.t;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setPlaceholderTextColor(@Nullable ColorStateList colorStateList) {
        if (this.u != colorStateList) {
            this.u = colorStateList;
            TextView textView = this.t;
            if (textView != null && colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
        }
    }

    @Nullable
    public ColorStateList getPlaceholderTextColor() {
        return this.u;
    }

    public void setPlaceholderTextAppearance(@StyleRes int i) {
        this.v = i;
        TextView textView = this.t;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }

    @StyleRes
    public int getPlaceholderTextAppearance() {
        return this.v;
    }

    public void setPrefixText(@Nullable CharSequence charSequence) {
        this.y = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.z.setText(charSequence);
        q();
    }

    @Nullable
    public CharSequence getPrefixText() {
        return this.y;
    }

    @NonNull
    public TextView getPrefixTextView() {
        return this.z;
    }

    private void q() {
        this.z.setVisibility((this.y == null || c()) ? 8 : 0);
        I();
    }

    public void setPrefixTextColor(@NonNull ColorStateList colorStateList) {
        this.z.setTextColor(colorStateList);
    }

    @Nullable
    public ColorStateList getPrefixTextColor() {
        return this.z.getTextColors();
    }

    public void setPrefixTextAppearance(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.z, i);
    }

    private void r() {
        if (this.a != null) {
            ViewCompat.setPaddingRelative(this.z, isStartIconVisible() ? 0 : ViewCompat.getPaddingStart(this.a), this.a.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.a.getCompoundPaddingBottom());
        }
    }

    public void setSuffixText(@Nullable CharSequence charSequence) {
        this.A = TextUtils.isEmpty(charSequence) ? null : charSequence;
        this.B.setText(charSequence);
        s();
    }

    @Nullable
    public CharSequence getSuffixText() {
        return this.A;
    }

    @NonNull
    public TextView getSuffixTextView() {
        return this.B;
    }

    private void s() {
        int visibility = this.B.getVisibility();
        int i = 0;
        boolean z = this.A != null && !c();
        TextView textView = this.B;
        if (!z) {
            i = 8;
        }
        textView.setVisibility(i);
        if (visibility != this.B.getVisibility()) {
            getEndIconDelegate().a(z);
        }
        I();
    }

    public void setSuffixTextColor(@NonNull ColorStateList colorStateList) {
        this.B.setTextColor(colorStateList);
    }

    @Nullable
    public ColorStateList getSuffixTextColor() {
        return this.B.getTextColors();
    }

    public void setSuffixTextAppearance(@StyleRes int i) {
        TextViewCompat.setTextAppearance(this.B, i);
    }

    private void t() {
        if (this.a != null) {
            ViewCompat.setPaddingRelative(this.B, getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.a.getPaddingTop(), (isEndIconVisible() || P()) ? 0 : ViewCompat.getPaddingEnd(this.a), this.a.getPaddingBottom());
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        a(this, z);
        super.setEnabled(z);
    }

    private static void a(@NonNull ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                a((ViewGroup) childAt, z);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.m;
    }

    @Nullable
    CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.b || !this.n || (textView = this.o) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    private void u() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextView textView = this.o;
        if (textView != null) {
            a(textView, this.n ? this.p : this.q);
            if (!this.n && (colorStateList2 = this.w) != null) {
                this.o.setTextColor(colorStateList2);
            }
            if (this.n && (colorStateList = this.x) != null) {
                this.o.setTextColor(colorStateList);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0015, code lost:
        if (r3.getTextColors().getDefaultColor() == (-65281)) goto L_0x001a;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(@androidx.annotation.NonNull android.widget.TextView r3, @androidx.annotation.StyleRes int r4) {
        /*
            r2 = this;
            r0 = 1
            androidx.core.widget.TextViewCompat.setTextAppearance(r3, r4)     // Catch: Exception -> 0x001a
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch: Exception -> 0x001a
            r1 = 23
            if (r4 < r1) goto L_0x0018
            android.content.res.ColorStateList r4 = r3.getTextColors()     // Catch: Exception -> 0x001a
            int r4 = r4.getDefaultColor()     // Catch: Exception -> 0x001a
            r1 = -65281(0xffffffffffff00ff, float:NaN)
            if (r4 != r1) goto L_0x0018
            goto L_0x001a
        L_0x0018:
            r4 = 0
            r0 = r4
        L_0x001a:
            if (r0 == 0) goto L_0x002e
            int r4 = com.google.android.material.R.style.TextAppearance_AppCompat_Caption
            androidx.core.widget.TextViewCompat.setTextAppearance(r3, r4)
            android.content.Context r4 = r2.getContext()
            int r0 = com.google.android.material.R.color.design_error
            int r4 = androidx.core.content.ContextCompat.getColor(r4, r0)
            r3.setTextColor(r4)
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.a(android.widget.TextView, int):void");
    }

    private int v() {
        if (!this.C) {
            return 0;
        }
        switch (this.K) {
            case 0:
            case 1:
                return (int) this.c.getCollapsedTextHeight();
            case 2:
                return (int) (this.c.getCollapsedTextHeight() / 2.0f);
            default:
                return 0;
        }
    }

    @NonNull
    private Rect a(@NonNull Rect rect) {
        if (this.a != null) {
            Rect rect2 = this.S;
            boolean z = true;
            if (ViewCompat.getLayoutDirection(this) != 1) {
                z = false;
            }
            rect2.bottom = rect.bottom;
            switch (this.K) {
                case 1:
                    rect2.left = a(rect.left, z);
                    rect2.top = rect.top + this.L;
                    rect2.right = b(rect.right, z);
                    return rect2;
                case 2:
                    rect2.left = rect.left + this.a.getPaddingLeft();
                    rect2.top = rect.top - v();
                    rect2.right = rect.right - this.a.getPaddingRight();
                    return rect2;
                default:
                    rect2.left = a(rect.left, z);
                    rect2.top = getPaddingTop();
                    rect2.right = b(rect.right, z);
                    return rect2;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private int a(int i, boolean z) {
        int compoundPaddingLeft = i + this.a.getCompoundPaddingLeft();
        return (this.y == null || z) ? compoundPaddingLeft : (compoundPaddingLeft - this.z.getMeasuredWidth()) + this.z.getPaddingLeft();
    }

    private int b(int i, boolean z) {
        int compoundPaddingRight = i - this.a.getCompoundPaddingRight();
        return (this.y == null || !z) ? compoundPaddingRight : compoundPaddingRight + (this.z.getMeasuredWidth() - this.z.getPaddingRight());
    }

    @NonNull
    private Rect b(@NonNull Rect rect) {
        if (this.a != null) {
            Rect rect2 = this.S;
            float expandedTextHeight = this.c.getExpandedTextHeight();
            rect2.left = rect.left + this.a.getCompoundPaddingLeft();
            rect2.top = a(rect, expandedTextHeight);
            rect2.right = rect.right - this.a.getCompoundPaddingRight();
            rect2.bottom = a(rect, rect2, expandedTextHeight);
            return rect2;
        }
        throw new IllegalStateException();
    }

    private int a(@NonNull Rect rect, float f) {
        if (w()) {
            return (int) (rect.centerY() - (f / 2.0f));
        }
        return rect.top + this.a.getCompoundPaddingTop();
    }

    private int a(@NonNull Rect rect, @NonNull Rect rect2, float f) {
        if (w()) {
            return (int) (rect2.top + f);
        }
        return rect.bottom - this.a.getCompoundPaddingBottom();
    }

    private boolean w() {
        return this.K == 1 && (Build.VERSION.SDK_INT < 16 || this.a.getMinLines() <= 1);
    }

    private int x() {
        return this.K == 1 ? MaterialColors.layer(MaterialColors.getColor(this, R.attr.colorSurface, 0), this.Q) : this.Q;
    }

    private void y() {
        MaterialShapeDrawable materialShapeDrawable = this.F;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(this.H);
            if (A()) {
                this.F.setStroke(this.M, this.P);
            }
            this.Q = x();
            this.F.setFillColor(ColorStateList.valueOf(this.Q));
            if (this.ah == 3) {
                this.a.getBackground().invalidateSelf();
            }
            z();
            invalidate();
        }
    }

    private void z() {
        if (this.G != null) {
            if (B()) {
                this.G.setFillColor(ColorStateList.valueOf(this.P));
            }
            invalidate();
        }
    }

    private boolean A() {
        return this.K == 2 && B();
    }

    private boolean B() {
        return this.M > -1 && this.P != 0;
    }

    public void a() {
        Drawable background;
        TextView textView;
        EditText editText = this.a;
        if (editText != null && this.K == 0 && (background = editText.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(background)) {
                background = background.mutate();
            }
            if (this.l.g()) {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.l.j(), PorterDuff.Mode.SRC_IN));
            } else if (!this.n || (textView = this.o) == null) {
                DrawableCompat.clearColorFilter(background);
                this.a.refreshDrawableState();
            } else {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class a extends AbsSavedState {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.ClassLoaderCreator<a>() { // from class: com.google.android.material.textfield.TextInputLayout.a.1
            @NonNull
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new a(parcel, classLoader);
            }

            @Nullable
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel) {
                return new a(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public a[] newArray(int i) {
                return new a[i];
            }
        };
        @Nullable
        CharSequence a;
        boolean b;
        @Nullable
        CharSequence c;
        @Nullable
        CharSequence d;
        @Nullable
        CharSequence e;

        a(Parcelable parcelable) {
            super(parcelable);
        }

        a(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.b = parcel.readInt() != 1 ? false : true;
            this.c = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.d = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.e = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.a, parcel, i);
            parcel.writeInt(this.b ? 1 : 0);
            TextUtils.writeToParcel(this.c, parcel, i);
            TextUtils.writeToParcel(this.d, parcel, i);
            TextUtils.writeToParcel(this.e, parcel, i);
        }

        @NonNull
        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + ((Object) this.a) + " hint=" + ((Object) this.c) + " helperText=" + ((Object) this.d) + " placeholderText=" + ((Object) this.e) + "}";
        }
    }

    @Override // android.view.View
    @Nullable
    public Parcelable onSaveInstanceState() {
        a aVar = new a(super.onSaveInstanceState());
        if (this.l.g()) {
            aVar.a = getError();
        }
        aVar.b = G() && this.aj.isChecked();
        aVar.c = getHint();
        aVar.d = getHelperText();
        aVar.e = getPlaceholderText();
        return aVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (!(parcelable instanceof a)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        a aVar = (a) parcelable;
        super.onRestoreInstanceState(aVar.getSuperState());
        setError(aVar.a);
        if (aVar.b) {
            this.aj.post(new Runnable() { // from class: com.google.android.material.textfield.TextInputLayout.2
                @Override // java.lang.Runnable
                public void run() {
                    TextInputLayout.this.aj.performClick();
                    TextInputLayout.this.aj.jumpDrawablesToCurrentState();
                }
            });
        }
        setHint(aVar.c);
        setHelperText(aVar.d);
        setPlaceholderText(aVar.e);
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(@NonNull SparseArray<Parcelable> sparseArray) {
        this.aM = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.aM = false;
    }

    @Nullable
    public CharSequence getError() {
        if (this.l.e()) {
            return this.l.h();
        }
        return null;
    }

    @Nullable
    public CharSequence getHelperText() {
        if (this.l.f()) {
            return this.l.i();
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.aJ;
    }

    public void setHintAnimationEnabled(boolean z) {
        this.aJ = z;
    }

    public boolean isExpandedHintEnabled() {
        return this.aI;
    }

    public void setExpandedHintEnabled(boolean z) {
        if (this.aI != z) {
            this.aI = z;
            a(false);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        boolean C = C();
        boolean I = I();
        if (C || I) {
            this.a.post(new Runnable() { // from class: com.google.android.material.textfield.TextInputLayout.3
                @Override // java.lang.Runnable
                public void run() {
                    TextInputLayout.this.a.requestLayout();
                }
            });
        }
        D();
        r();
        t();
    }

    private boolean C() {
        int max;
        if (this.a == null || this.a.getMeasuredHeight() >= (max = Math.max(this.g.getMeasuredHeight(), this.f.getMeasuredHeight()))) {
            return false;
        }
        this.a.setMinimumHeight(max);
        return true;
    }

    private void D() {
        EditText editText;
        if (this.t != null && (editText = this.a) != null) {
            this.t.setGravity(editText.getGravity());
            this.t.setPadding(this.a.getCompoundPaddingLeft(), this.a.getCompoundPaddingTop(), this.a.getCompoundPaddingRight(), this.a.getCompoundPaddingBottom());
        }
    }

    public void setStartIconDrawable(@DrawableRes int i) {
        setStartIconDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public void setStartIconDrawable(@Nullable Drawable drawable) {
        this.V.setImageDrawable(drawable);
        if (drawable != null) {
            setStartIconVisible(true);
            refreshStartIconDrawableState();
            return;
        }
        setStartIconVisible(false);
        setStartIconOnClickListener(null);
        setStartIconOnLongClickListener(null);
        setStartIconContentDescription((CharSequence) null);
    }

    @Nullable
    public Drawable getStartIconDrawable() {
        return this.V.getDrawable();
    }

    public void setStartIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        a(this.V, onClickListener, this.af);
    }

    public void setStartIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.af = onLongClickListener;
        a(this.V, onLongClickListener);
    }

    public void setStartIconVisible(boolean z) {
        if (isStartIconVisible() != z) {
            this.V.setVisibility(z ? 0 : 8);
            r();
            I();
        }
    }

    public boolean isStartIconVisible() {
        return this.V.getVisibility() == 0;
    }

    public void refreshStartIconDrawableState() {
        a(this.V, this.W);
    }

    public void setStartIconCheckable(boolean z) {
        this.V.setCheckable(z);
    }

    public boolean isStartIconCheckable() {
        return this.V.isCheckable();
    }

    public void setStartIconContentDescription(@StringRes int i) {
        setStartIconContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public void setStartIconContentDescription(@Nullable CharSequence charSequence) {
        if (getStartIconContentDescription() != charSequence) {
            this.V.setContentDescription(charSequence);
        }
    }

    @Nullable
    public CharSequence getStartIconContentDescription() {
        return this.V.getContentDescription();
    }

    public void setStartIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.W != colorStateList) {
            this.W = colorStateList;
            this.aa = true;
            F();
        }
    }

    public void setStartIconTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.ab != mode) {
            this.ab = mode;
            this.ac = true;
            F();
        }
    }

    public void setEndIconMode(int i) {
        int i2 = this.ah;
        this.ah = i;
        c(i2);
        setEndIconVisible(i != 0);
        if (getEndIconDelegate().a(this.K)) {
            getEndIconDelegate().a();
            H();
            return;
        }
        throw new IllegalStateException("The current box background mode " + this.K + " is not supported by the end icon mode " + i);
    }

    public int getEndIconMode() {
        return this.ah;
    }

    public void setEndIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        a(this.aj, onClickListener, this.as);
    }

    public void setErrorIconOnClickListener(@Nullable View.OnClickListener onClickListener) {
        a(this.au, onClickListener, this.at);
    }

    public void setEndIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.as = onLongClickListener;
        a(this.aj, onLongClickListener);
    }

    public void setErrorIconOnLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.at = onLongClickListener;
        a(this.au, onLongClickListener);
    }

    public void refreshErrorIconDrawableState() {
        a(this.au, this.av);
    }

    public void setEndIconVisible(boolean z) {
        if (isEndIconVisible() != z) {
            this.aj.setVisibility(z ? 0 : 8);
            t();
            I();
        }
    }

    public boolean isEndIconVisible() {
        return this.h.getVisibility() == 0 && this.aj.getVisibility() == 0;
    }

    public void setEndIconActivated(boolean z) {
        this.aj.setActivated(z);
    }

    public void refreshEndIconDrawableState() {
        a(this.aj, this.al);
    }

    public void setEndIconCheckable(boolean z) {
        this.aj.setCheckable(z);
    }

    public boolean isEndIconCheckable() {
        return this.aj.isCheckable();
    }

    public void setEndIconDrawable(@DrawableRes int i) {
        setEndIconDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public void setEndIconDrawable(@Nullable Drawable drawable) {
        this.aj.setImageDrawable(drawable);
        refreshEndIconDrawableState();
    }

    @Nullable
    public Drawable getEndIconDrawable() {
        return this.aj.getDrawable();
    }

    public void setEndIconContentDescription(@StringRes int i) {
        setEndIconContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public void setEndIconContentDescription(@Nullable CharSequence charSequence) {
        if (getEndIconContentDescription() != charSequence) {
            this.aj.setContentDescription(charSequence);
        }
    }

    @Nullable
    public CharSequence getEndIconContentDescription() {
        return this.aj.getContentDescription();
    }

    public void setEndIconTintList(@Nullable ColorStateList colorStateList) {
        if (this.al != colorStateList) {
            this.al = colorStateList;
            this.am = true;
            H();
        }
    }

    public void setEndIconTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.an != mode) {
            this.an = mode;
            this.ao = true;
            H();
        }
    }

    public void addOnEndIconChangedListener(@NonNull OnEndIconChangedListener onEndIconChangedListener) {
        this.ak.add(onEndIconChangedListener);
    }

    public void removeOnEndIconChangedListener(@NonNull OnEndIconChangedListener onEndIconChangedListener) {
        this.ak.remove(onEndIconChangedListener);
    }

    public void clearOnEndIconChangedListeners() {
        this.ak.clear();
    }

    public void addOnEditTextAttachedListener(@NonNull OnEditTextAttachedListener onEditTextAttachedListener) {
        this.ag.add(onEditTextAttachedListener);
        if (this.a != null) {
            onEditTextAttachedListener.onEditTextAttached(this);
        }
    }

    public void removeOnEditTextAttachedListener(@NonNull OnEditTextAttachedListener onEditTextAttachedListener) {
        this.ag.remove(onEditTextAttachedListener);
    }

    public void clearOnEditTextAttachedListeners() {
        this.ag.clear();
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i) {
        setPasswordVisibilityToggleDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.aj.setImageDrawable(drawable);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(@StringRes int i) {
        setPasswordVisibilityToggleContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.aj.setContentDescription(charSequence);
    }

    @Nullable
    @Deprecated
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.aj.getDrawable();
    }

    @Nullable
    @Deprecated
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.aj.getContentDescription();
    }

    @Deprecated
    public boolean isPasswordVisibilityToggleEnabled() {
        return this.ah == 1;
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean z) {
        if (z && this.ah != 1) {
            setEndIconMode(1);
        } else if (!z) {
            setEndIconMode(0);
        }
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.al = colorStateList;
        this.am = true;
        H();
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.an = mode;
        this.ao = true;
        H();
    }

    @Deprecated
    public void passwordVisibilityToggleRequested(boolean z) {
        if (this.ah == 1) {
            this.aj.performClick();
            if (z) {
                this.aj.jumpDrawablesToCurrentState();
            }
        }
    }

    public void setTextInputAccessibilityDelegate(@Nullable AccessibilityDelegate accessibilityDelegate) {
        EditText editText = this.a;
        if (editText != null) {
            ViewCompat.setAccessibilityDelegate(editText, accessibilityDelegate);
        }
    }

    @NonNull
    public CheckableImageButton getEndIconView() {
        return this.aj;
    }

    private e getEndIconDelegate() {
        e eVar = this.ai.get(this.ah);
        return eVar != null ? eVar : this.ai.get(0);
    }

    private void E() {
        Iterator<OnEditTextAttachedListener> it = this.ag.iterator();
        while (it.hasNext()) {
            it.next().onEditTextAttached(this);
        }
    }

    private void F() {
        a(this.V, this.aa, this.W, this.ac, this.ab);
    }

    private boolean G() {
        return this.ah != 0;
    }

    private void c(int i) {
        Iterator<OnEndIconChangedListener> it = this.ak.iterator();
        while (it.hasNext()) {
            it.next().onEndIconChanged(this, i);
        }
    }

    private void b(boolean z) {
        if (!z || getEndIconDrawable() == null) {
            H();
            return;
        }
        Drawable mutate = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        DrawableCompat.setTint(mutate, this.l.j());
        this.aj.setImageDrawable(mutate);
    }

    private void H() {
        a(this.aj, this.am, this.al, this.ao, this.an);
    }

    private boolean I() {
        boolean z;
        if (this.a == null) {
            return false;
        }
        if (J()) {
            int measuredWidth = this.f.getMeasuredWidth() - this.a.getPaddingLeft();
            if (this.ad == null || this.ae != measuredWidth) {
                this.ad = new ColorDrawable();
                this.ae = measuredWidth;
                this.ad.setBounds(0, 0, this.ae, 1);
            }
            Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.a);
            Drawable drawable = compoundDrawablesRelative[0];
            Drawable drawable2 = this.ad;
            if (drawable != drawable2) {
                TextViewCompat.setCompoundDrawablesRelative(this.a, drawable2, compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
                z = true;
            } else {
                z = false;
            }
        } else if (this.ad != null) {
            Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.a);
            TextViewCompat.setCompoundDrawablesRelative(this.a, null, compoundDrawablesRelative2[1], compoundDrawablesRelative2[2], compoundDrawablesRelative2[3]);
            this.ad = null;
            z = true;
        } else {
            z = false;
        }
        if (K()) {
            int measuredWidth2 = this.B.getMeasuredWidth() - this.a.getPaddingRight();
            CheckableImageButton endIconToUpdateDummyDrawable = getEndIconToUpdateDummyDrawable();
            if (endIconToUpdateDummyDrawable != null) {
                measuredWidth2 = measuredWidth2 + endIconToUpdateDummyDrawable.getMeasuredWidth() + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) endIconToUpdateDummyDrawable.getLayoutParams());
            }
            Drawable[] compoundDrawablesRelative3 = TextViewCompat.getCompoundDrawablesRelative(this.a);
            Drawable drawable3 = this.ap;
            if (drawable3 == null || this.aq == measuredWidth2) {
                if (this.ap == null) {
                    this.ap = new ColorDrawable();
                    this.aq = measuredWidth2;
                    this.ap.setBounds(0, 0, this.aq, 1);
                }
                Drawable drawable4 = compoundDrawablesRelative3[2];
                Drawable drawable5 = this.ap;
                if (drawable4 == drawable5) {
                    return z;
                }
                this.ar = compoundDrawablesRelative3[2];
                TextViewCompat.setCompoundDrawablesRelative(this.a, compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], drawable5, compoundDrawablesRelative3[3]);
                return true;
            }
            this.aq = measuredWidth2;
            drawable3.setBounds(0, 0, this.aq, 1);
            TextViewCompat.setCompoundDrawablesRelative(this.a, compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], this.ap, compoundDrawablesRelative3[3]);
            return true;
        } else if (this.ap == null) {
            return z;
        } else {
            Drawable[] compoundDrawablesRelative4 = TextViewCompat.getCompoundDrawablesRelative(this.a);
            if (compoundDrawablesRelative4[2] == this.ap) {
                TextViewCompat.setCompoundDrawablesRelative(this.a, compoundDrawablesRelative4[0], compoundDrawablesRelative4[1], this.ar, compoundDrawablesRelative4[3]);
                z = true;
            }
            this.ap = null;
            return z;
        }
    }

    private boolean J() {
        return !(getStartIconDrawable() == null && this.y == null) && this.f.getMeasuredWidth() > 0;
    }

    private boolean K() {
        return (this.au.getVisibility() == 0 || ((G() && isEndIconVisible()) || this.A != null)) && this.g.getMeasuredWidth() > 0;
    }

    @Nullable
    private CheckableImageButton getEndIconToUpdateDummyDrawable() {
        if (this.au.getVisibility() == 0) {
            return this.au;
        }
        if (!G() || !isEndIconVisible()) {
            return null;
        }
        return this.aj;
    }

    private void a(@NonNull CheckableImageButton checkableImageButton, boolean z, ColorStateList colorStateList, boolean z2, PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null && (z || z2)) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (z) {
                DrawableCompat.setTintList(drawable, colorStateList);
            }
            if (z2) {
                DrawableCompat.setTintMode(drawable, mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    private static void a(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnClickListener onClickListener, @Nullable View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnClickListener(onClickListener);
        b(checkableImageButton, onLongClickListener);
    }

    private static void a(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnLongClickListener(onLongClickListener);
        b(checkableImageButton, onLongClickListener);
    }

    private static void b(@NonNull CheckableImageButton checkableImageButton, @Nullable View.OnLongClickListener onLongClickListener) {
        boolean hasOnClickListeners = ViewCompat.hasOnClickListeners(checkableImageButton);
        boolean z = false;
        int i = 1;
        boolean z2 = onLongClickListener != null;
        if (hasOnClickListeners || z2) {
            z = true;
        }
        checkableImageButton.setFocusable(z);
        checkableImageButton.setClickable(hasOnClickListeners);
        checkableImageButton.setPressable(hasOnClickListeners);
        checkableImageButton.setLongClickable(z2);
        if (!z) {
            i = 2;
        }
        ViewCompat.setImportantForAccessibility(checkableImageButton, i);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        EditText editText = this.a;
        if (editText != null) {
            Rect rect = this.R;
            DescendantOffsetUtils.getDescendantRect(this, editText, rect);
            c(rect);
            if (this.C) {
                this.c.setExpandedTextSize(this.a.getTextSize());
                int gravity = this.a.getGravity();
                this.c.setCollapsedTextGravity((gravity & (-113)) | 48);
                this.c.setExpandedTextGravity(gravity);
                this.c.setCollapsedBounds(a(rect));
                this.c.setExpandedBounds(b(rect));
                this.c.recalculate();
                if (L() && !this.aH) {
                    M();
                }
            }
        }
    }

    private void c(@NonNull Rect rect) {
        if (this.G != null) {
            this.G.setBounds(rect.left, rect.bottom - this.O, rect.right, rect.bottom);
        }
    }

    @Override // android.view.View
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        a(canvas);
        b(canvas);
    }

    private void a(@NonNull Canvas canvas) {
        if (this.C) {
            this.c.draw(canvas);
        }
    }

    private void b(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable = this.G;
        if (materialShapeDrawable != null) {
            Rect bounds = materialShapeDrawable.getBounds();
            bounds.top = bounds.bottom - this.M;
            this.G.draw(canvas);
        }
    }

    private void c(boolean z) {
        ValueAnimator valueAnimator = this.aK;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.aK.cancel();
        }
        if (!z || !this.aJ) {
            this.c.setExpansionFraction(1.0f);
        } else {
            a(1.0f);
        }
        this.aH = false;
        if (L()) {
            M();
        }
        l();
        q();
        s();
    }

    private boolean L() {
        return this.C && !TextUtils.isEmpty(this.D) && (this.F instanceof c);
    }

    private void M() {
        if (L()) {
            RectF rectF = this.T;
            this.c.getCollapsedTextActualBounds(rectF, this.a.getWidth(), this.a.getGravity());
            a(rectF);
            this.J = this.M;
            rectF.top = 0.0f;
            rectF.bottom = this.J;
            rectF.offset(-getPaddingLeft(), 0.0f);
            ((c) this.F).a(rectF);
        }
    }

    private void N() {
        if (L() && !this.aH && this.J != this.M) {
            O();
            M();
        }
    }

    private void O() {
        if (L()) {
            ((c) this.F).b();
        }
    }

    private void a(@NonNull RectF rectF) {
        rectF.left -= this.I;
        rectF.right += this.I;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (!this.aL) {
            boolean z = true;
            this.aL = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            CollapsingTextHelper collapsingTextHelper = this.c;
            boolean state = collapsingTextHelper != null ? collapsingTextHelper.setState(drawableState) | false : false;
            if (this.a != null) {
                if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                    z = false;
                }
                a(z);
            }
            a();
            b();
            if (state) {
                invalidate();
            }
            this.aL = false;
        }
    }

    public void b() {
        TextView textView;
        EditText editText;
        EditText editText2;
        if (this.F != null && this.K != 0) {
            boolean z = false;
            boolean z2 = isFocused() || ((editText2 = this.a) != null && editText2.hasFocus());
            boolean z3 = isHovered() || ((editText = this.a) != null && editText.isHovered());
            if (!isEnabled()) {
                this.P = this.aG;
            } else if (this.l.g()) {
                if (this.aB != null) {
                    b(z2, z3);
                } else {
                    this.P = this.l.j();
                }
            } else if (!this.n || (textView = this.o) == null) {
                if (z2) {
                    this.P = this.aA;
                } else if (z3) {
                    this.P = this.az;
                } else {
                    this.P = this.ay;
                }
            } else if (this.aB != null) {
                b(z2, z3);
            } else {
                this.P = textView.getCurrentTextColor();
            }
            if (getErrorIconDrawable() != null && this.l.e() && this.l.g()) {
                z = true;
            }
            setErrorIconVisible(z);
            refreshErrorIconDrawableState();
            refreshStartIconDrawableState();
            refreshEndIconDrawableState();
            if (getEndIconDelegate().b()) {
                b(this.l.g());
            }
            if (!z2 || !isEnabled()) {
                this.M = this.N;
            } else {
                this.M = this.O;
            }
            if (this.K == 2) {
                N();
            }
            if (this.K == 1) {
                if (!isEnabled()) {
                    this.Q = this.aD;
                } else if (z3 && !z2) {
                    this.Q = this.aF;
                } else if (z2) {
                    this.Q = this.aE;
                } else {
                    this.Q = this.aC;
                }
            }
            y();
        }
    }

    private void b(boolean z, boolean z2) {
        int defaultColor = this.aB.getDefaultColor();
        int colorForState = this.aB.getColorForState(new int[]{16843623, 16842910}, defaultColor);
        int colorForState2 = this.aB.getColorForState(new int[]{16843518, 16842910}, defaultColor);
        if (z) {
            this.P = colorForState2;
        } else if (z2) {
            this.P = colorForState;
        } else {
            this.P = defaultColor;
        }
    }

    private void setErrorIconVisible(boolean z) {
        int i = 0;
        this.au.setVisibility(z ? 0 : 8);
        FrameLayout frameLayout = this.h;
        if (z) {
            i = 8;
        }
        frameLayout.setVisibility(i);
        t();
        if (!G()) {
            I();
        }
    }

    private boolean P() {
        return this.au.getVisibility() == 0;
    }

    private void a(CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() != null && colorStateList != null && colorStateList.isStateful()) {
            int colorForState = colorStateList.getColorForState(a(checkableImageButton), colorStateList.getDefaultColor());
            Drawable mutate = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTintList(mutate, ColorStateList.valueOf(colorForState));
            checkableImageButton.setImageDrawable(mutate);
        }
    }

    private int[] a(CheckableImageButton checkableImageButton) {
        int[] drawableState = getDrawableState();
        int[] drawableState2 = checkableImageButton.getDrawableState();
        int length = drawableState.length;
        int[] copyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
        System.arraycopy(drawableState2, 0, copyOf, length, drawableState2.length);
        return copyOf;
    }

    private void d(boolean z) {
        ValueAnimator valueAnimator = this.aK;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.aK.cancel();
        }
        if (!z || !this.aJ) {
            this.c.setExpansionFraction(0.0f);
        } else {
            a(0.0f);
        }
        if (L() && ((c) this.F).a()) {
            O();
        }
        this.aH = true;
        n();
        q();
        s();
    }

    @VisibleForTesting
    void a(float f) {
        if (this.c.getExpansionFraction() != f) {
            if (this.aK == null) {
                this.aK = new ValueAnimator();
                this.aK.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                this.aK.setDuration(167L);
                this.aK.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.TextInputLayout.4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        TextInputLayout.this.c.setExpansionFraction(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.aK.setFloatValues(this.c.getExpansionFraction(), f);
            this.aK.start();
        }
    }

    @VisibleForTesting
    final boolean c() {
        return this.aH;
    }

    @VisibleForTesting
    final int getHintCurrentCollapsedTextColor() {
        return this.c.getCurrentCollapsedTextColor();
    }

    @VisibleForTesting
    final float getHintCollapsedTextHeight() {
        return this.c.getCollapsedTextHeight();
    }

    @VisibleForTesting
    final int getErrorTextCurrentColor() {
        return this.l.j();
    }

    /* loaded from: classes2.dex */
    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout a;

        public AccessibilityDelegate(@NonNull TextInputLayout textInputLayout) {
            this.a = textInputLayout;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(@NonNull View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            EditText editText = this.a.getEditText();
            CharSequence text = editText != null ? editText.getText() : null;
            CharSequence hint = this.a.getHint();
            CharSequence error = this.a.getError();
            CharSequence placeholderText = this.a.getPlaceholderText();
            int counterMaxLength = this.a.getCounterMaxLength();
            CharSequence counterOverflowDescription = this.a.getCounterOverflowDescription();
            boolean z = true;
            boolean z2 = !TextUtils.isEmpty(text);
            boolean z3 = !TextUtils.isEmpty(hint);
            boolean z4 = !this.a.c();
            boolean z5 = !TextUtils.isEmpty(error);
            boolean z6 = z5 || !TextUtils.isEmpty(counterOverflowDescription);
            String charSequence = z3 ? hint.toString() : "";
            if (z2) {
                accessibilityNodeInfoCompat.setText(text);
            } else if (!TextUtils.isEmpty(charSequence)) {
                accessibilityNodeInfoCompat.setText(charSequence);
                if (z4 && placeholderText != null) {
                    accessibilityNodeInfoCompat.setText(charSequence + ", " + ((Object) placeholderText));
                }
            } else if (placeholderText != null) {
                accessibilityNodeInfoCompat.setText(placeholderText);
            }
            if (!TextUtils.isEmpty(charSequence)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    accessibilityNodeInfoCompat.setHintText(charSequence);
                } else {
                    if (z2) {
                        charSequence = ((Object) text) + ", " + charSequence;
                    }
                    accessibilityNodeInfoCompat.setText(charSequence);
                }
                if (z2) {
                    z = false;
                }
                accessibilityNodeInfoCompat.setShowingHintText(z);
            }
            if (text == null || text.length() != counterMaxLength) {
                counterMaxLength = -1;
            }
            accessibilityNodeInfoCompat.setMaxTextLength(counterMaxLength);
            if (z6) {
                if (!z5) {
                    error = counterOverflowDescription;
                }
                accessibilityNodeInfoCompat.setError(error);
            }
            if (Build.VERSION.SDK_INT >= 17 && editText != null) {
                editText.setLabelFor(R.id.textinput_helper_text);
            }
        }
    }
}
