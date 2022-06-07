package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/* loaded from: classes.dex */
public final class DrawableDecoderCompat {
    private static volatile boolean a = true;

    private DrawableDecoderCompat() {
    }

    public static Drawable getDrawable(Context context, Context context2, @DrawableRes int i) {
        return a(context, context2, i, null);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        return a(context, context, i, theme);
    }

    private static Drawable a(Context context, Context context2, @DrawableRes int i, @Nullable Resources.Theme theme) {
        try {
            if (a) {
                return a(context2, i, theme);
            }
        } catch (Resources.NotFoundException unused) {
        } catch (IllegalStateException e) {
            if (!context.getPackageName().equals(context2.getPackageName())) {
                return ContextCompat.getDrawable(context2, i);
            }
            throw e;
        } catch (NoClassDefFoundError unused2) {
            a = false;
        }
        if (theme == null) {
            theme = context2.getTheme();
        }
        return b(context2, i, theme);
    }

    private static Drawable a(Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        if (theme != null) {
            context = new ContextThemeWrapper(context, theme);
        }
        return AppCompatResources.getDrawable(context, i);
    }

    private static Drawable b(Context context, @DrawableRes int i, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(context.getResources(), i, theme);
    }
}
