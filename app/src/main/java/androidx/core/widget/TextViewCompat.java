package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build;
import android.text.Editable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static Field a;
    private static boolean b;
    private static Field c;
    private static boolean d;
    private static Field e;
    private static boolean f;
    private static Field g;
    private static boolean h;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface AutoSizeTextType {
    }

    private TextViewCompat() {
    }

    private static Field a(String str) {
        Field field = null;
        try {
            field = TextView.class.getDeclaredField(str);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException unused) {
            Log.e("TextViewCompat", "Could not retrieve " + str + " field.");
            return field;
        }
    }

    private static int a(Field field, TextView textView) {
        try {
            return field.getInt(textView);
        } catch (IllegalAccessException unused) {
            Log.d("TextViewCompat", "Could not retrieve value of " + field.getName() + " field.");
            return -1;
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        } else if (Build.VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        } else {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else if (Build.VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        } else if (Build.VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            int i5 = z ? i3 : i;
            if (!z) {
                i = i3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(i5, i2, i, i4);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        }
    }

    public static int getMaxLines(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMaxLines();
        }
        if (!d) {
            c = a("mMaxMode");
            d = true;
        }
        Field field = c;
        if (field == null || a(field, textView) != 1) {
            return -1;
        }
        if (!b) {
            a = a("mMaximum");
            b = true;
        }
        Field field2 = a;
        if (field2 != null) {
            return a(field2, textView);
        }
        return -1;
    }

    public static int getMinLines(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return textView.getMinLines();
        }
        if (!h) {
            g = a("mMinMode");
            h = true;
        }
        Field field = g;
        if (field == null || a(field, textView) != 1) {
            return -1;
        }
        if (!f) {
            e = a("mMinimum");
            f = true;
        }
        Field field2 = e;
        if (field2 != null) {
            return a(field2, textView);
        }
        return -1;
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(i);
        } else {
            textView.setTextAppearance(textView.getContext(), i);
        }
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 18) {
            return textView.getCompoundDrawablesRelative();
        }
        if (Build.VERSION.SDK_INT < 17) {
            return textView.getCompoundDrawables();
        }
        boolean z = true;
        if (textView.getLayoutDirection() != 1) {
            z = false;
        }
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        if (z) {
            Drawable drawable = compoundDrawables[2];
            Drawable drawable2 = compoundDrawables[0];
            compoundDrawables[0] = drawable;
            compoundDrawables[2] = drawable2;
        }
        return compoundDrawables;
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int i) {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeWithDefaults(i);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i);
        }
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextType();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextType();
        }
        return 0;
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeStepGranularity();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
        }
        return -1;
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMinTextSize();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
        }
        return -1;
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMaxTextSize();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
        }
        return -1;
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextAvailableSizes();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
        }
        return new int[0];
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull ActionMode.Callback callback) {
        textView.setCustomSelectionActionModeCallback(wrapCustomSelectionActionModeCallback(textView, callback));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static ActionMode.Callback wrapCustomSelectionActionModeCallback(@NonNull TextView textView, @Nullable ActionMode.Callback callback) {
        return (Build.VERSION.SDK_INT < 26 || Build.VERSION.SDK_INT > 27 || (callback instanceof a) || callback == null) ? callback : new a(callback, textView);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static ActionMode.Callback unwrapCustomSelectionActionModeCallback(@Nullable ActionMode.Callback callback) {
        return (!(callback instanceof a) || Build.VERSION.SDK_INT < 26) ? callback : ((a) callback).a();
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    public static class a implements ActionMode.Callback {
        private final ActionMode.Callback a;
        private final TextView b;
        private Class<?> c;
        private Method d;
        private boolean e;
        private boolean f = false;

        a(ActionMode.Callback callback, TextView textView) {
            this.a = callback;
            this.b = textView;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            a(menu);
            return this.a.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.a.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.a.onDestroyActionMode(actionMode);
        }

        @NonNull
        ActionMode.Callback a() {
            return this.a;
        }

        private void a(Menu menu) {
            Method method;
            Context context = this.b.getContext();
            PackageManager packageManager = context.getPackageManager();
            if (!this.f) {
                this.f = true;
                try {
                    this.c = Class.forName("com.android.internal.view.menu.MenuBuilder");
                    this.d = this.c.getDeclaredMethod("removeItemAt", Integer.TYPE);
                    this.e = true;
                } catch (ClassNotFoundException | NoSuchMethodException unused) {
                    this.c = null;
                    this.d = null;
                    this.e = false;
                }
            }
            try {
                if (!this.e || !this.c.isInstance(menu)) {
                    method = menu.getClass().getDeclaredMethod("removeItemAt", Integer.TYPE);
                } else {
                    method = this.d;
                }
                for (int size = menu.size() - 1; size >= 0; size--) {
                    MenuItem item = menu.getItem(size);
                    if (item.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(item.getIntent().getAction())) {
                        method.invoke(menu, Integer.valueOf(size));
                    }
                }
                List<ResolveInfo> a = a(context, packageManager);
                for (int i = 0; i < a.size(); i++) {
                    ResolveInfo resolveInfo = a.get(i);
                    menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(a(resolveInfo, this.b)).setShowAsAction(1);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
            }
        }

        private List<ResolveInfo> a(Context context, PackageManager packageManager) {
            ArrayList arrayList = new ArrayList();
            if (!(context instanceof Activity)) {
                return arrayList;
            }
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(b(), 0)) {
                if (a(resolveInfo, context)) {
                    arrayList.add(resolveInfo);
                }
            }
            return arrayList;
        }

        private boolean a(ResolveInfo resolveInfo, Context context) {
            if (context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                return true;
            }
            if (!resolveInfo.activityInfo.exported) {
                return false;
            }
            return resolveInfo.activityInfo.permission == null || context.checkSelfPermission(resolveInfo.activityInfo.permission) == 0;
        }

        private Intent a(ResolveInfo resolveInfo, TextView textView) {
            return b().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", !a(textView)).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        }

        private boolean a(TextView textView) {
            return (textView instanceof Editable) && textView.onCheckIsTextEditor() && textView.isEnabled();
        }

        private Intent b() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }
    }

    public static void setFirstBaselineToTopHeight(@NonNull TextView textView, @IntRange(from = 0) @Px int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        if (Build.VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(i);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (Build.VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            i2 = fontMetricsInt.top;
        } else {
            i2 = fontMetricsInt.ascent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i + i2, textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void setLastBaselineToBottomHeight(@NonNull TextView textView, @IntRange(from = 0) @Px int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (Build.VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            i2 = fontMetricsInt.bottom;
        } else {
            i2 = fontMetricsInt.descent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2);
        }
    }

    public static int getFirstBaselineToTopHeight(@NonNull TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int getLastBaselineToBottomHeight(@NonNull TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static void setLineHeight(@NonNull TextView textView, @IntRange(from = 0) @Px int i) {
        Preconditions.checkArgumentNonnegative(i);
        int fontMetricsInt = textView.getPaint().getFontMetricsInt(null);
        if (i != fontMetricsInt) {
            textView.setLineSpacing(i - fontMetricsInt, 1.0f);
        }
    }

    @NonNull
    public static PrecomputedTextCompat.Params getTextMetricsParams(@NonNull TextView textView) {
        if (Build.VERSION.SDK_INT >= 28) {
            return new PrecomputedTextCompat.Params(textView.getTextMetricsParams());
        }
        PrecomputedTextCompat.Params.Builder builder = new PrecomputedTextCompat.Params.Builder(new TextPaint(textView.getPaint()));
        if (Build.VERSION.SDK_INT >= 23) {
            builder.setBreakStrategy(textView.getBreakStrategy());
            builder.setHyphenationFrequency(textView.getHyphenationFrequency());
        }
        if (Build.VERSION.SDK_INT >= 18) {
            builder.setTextDirection(a(textView));
        }
        return builder.build();
    }

    public static void setTextMetricsParams(@NonNull TextView textView, @NonNull PrecomputedTextCompat.Params params) {
        if (Build.VERSION.SDK_INT >= 18) {
            textView.setTextDirection(a(params.getTextDirection()));
        }
        if (Build.VERSION.SDK_INT < 23) {
            float textScaleX = params.getTextPaint().getTextScaleX();
            textView.getPaint().set(params.getTextPaint());
            if (textScaleX == textView.getTextScaleX()) {
                textView.setTextScaleX((textScaleX / 2.0f) + 1.0f);
            }
            textView.setTextScaleX(textScaleX);
            return;
        }
        textView.getPaint().set(params.getTextPaint());
        textView.setBreakStrategy(params.getBreakStrategy());
        textView.setHyphenationFrequency(params.getHyphenationFrequency());
    }

    public static void setPrecomputedText(@NonNull TextView textView, @NonNull PrecomputedTextCompat precomputedTextCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            textView.setText(precomputedTextCompat.getPrecomputedText());
        } else if (getTextMetricsParams(textView).equalsWithoutTextDirection(precomputedTextCompat.getParams())) {
            textView.setText(precomputedTextCompat);
        } else {
            throw new IllegalArgumentException("Given text can not be applied to TextView.");
        }
    }

    @RequiresApi(18)
    private static TextDirectionHeuristic a(@NonNull TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        boolean z = false;
        if (Build.VERSION.SDK_INT < 28 || (textView.getInputType() & 15) != 3) {
            if (textView.getLayoutDirection() == 1) {
                z = true;
            }
            switch (textView.getTextDirection()) {
                case 2:
                    return TextDirectionHeuristics.ANYRTL_LTR;
                case 3:
                    return TextDirectionHeuristics.LTR;
                case 4:
                    return TextDirectionHeuristics.RTL;
                case 5:
                    return TextDirectionHeuristics.LOCALE;
                case 6:
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
                case 7:
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                default:
                    if (z) {
                        return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                    }
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
        } else {
            byte directionality = Character.getDirectionality(DecimalFormatSymbols.getInstance(textView.getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (directionality == 1 || directionality == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
    }

    @RequiresApi(18)
    private static int a(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6;
        }
        return textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL ? 7 : 1;
    }

    public static void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList colorStateList) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setCompoundDrawableTintList(colorStateList);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintList(colorStateList);
        }
    }

    @Nullable
    public static ColorStateList getCompoundDrawableTintList(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            return textView.getCompoundDrawableTintList();
        }
        if (textView instanceof TintableCompoundDrawablesView) {
            return ((TintableCompoundDrawablesView) textView).getSupportCompoundDrawablesTintList();
        }
        return null;
    }

    public static void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable PorterDuff.Mode mode) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setCompoundDrawableTintMode(mode);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintMode(mode);
        }
    }

    @Nullable
    public static PorterDuff.Mode getCompoundDrawableTintMode(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            return textView.getCompoundDrawableTintMode();
        }
        if (textView instanceof TintableCompoundDrawablesView) {
            return ((TintableCompoundDrawablesView) textView).getSupportCompoundDrawablesTintMode();
        }
        return null;
    }
}
