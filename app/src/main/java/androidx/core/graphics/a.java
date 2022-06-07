package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* compiled from: BlendModeUtils.java */
/* loaded from: classes.dex */
class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    @RequiresApi(29)
    public static BlendMode a(@NonNull BlendModeCompat blendModeCompat) {
        switch (blendModeCompat) {
            case CLEAR:
                return BlendMode.CLEAR;
            case SRC:
                return BlendMode.SRC;
            case DST:
                return BlendMode.DST;
            case SRC_OVER:
                return BlendMode.SRC_OVER;
            case DST_OVER:
                return BlendMode.DST_OVER;
            case SRC_IN:
                return BlendMode.SRC_IN;
            case DST_IN:
                return BlendMode.DST_IN;
            case SRC_OUT:
                return BlendMode.SRC_OUT;
            case DST_OUT:
                return BlendMode.DST_OUT;
            case SRC_ATOP:
                return BlendMode.SRC_ATOP;
            case DST_ATOP:
                return BlendMode.DST_ATOP;
            case XOR:
                return BlendMode.XOR;
            case PLUS:
                return BlendMode.PLUS;
            case MODULATE:
                return BlendMode.MODULATE;
            case SCREEN:
                return BlendMode.SCREEN;
            case OVERLAY:
                return BlendMode.OVERLAY;
            case DARKEN:
                return BlendMode.DARKEN;
            case LIGHTEN:
                return BlendMode.LIGHTEN;
            case COLOR_DODGE:
                return BlendMode.COLOR_DODGE;
            case COLOR_BURN:
                return BlendMode.COLOR_BURN;
            case HARD_LIGHT:
                return BlendMode.HARD_LIGHT;
            case SOFT_LIGHT:
                return BlendMode.SOFT_LIGHT;
            case DIFFERENCE:
                return BlendMode.DIFFERENCE;
            case EXCLUSION:
                return BlendMode.EXCLUSION;
            case MULTIPLY:
                return BlendMode.MULTIPLY;
            case HUE:
                return BlendMode.HUE;
            case SATURATION:
                return BlendMode.SATURATION;
            case COLOR:
                return BlendMode.COLOR;
            case LUMINOSITY:
                return BlendMode.LUMINOSITY;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static PorterDuff.Mode b(@Nullable BlendModeCompat blendModeCompat) {
        if (blendModeCompat == null) {
            return null;
        }
        switch (blendModeCompat) {
            case CLEAR:
                return PorterDuff.Mode.CLEAR;
            case SRC:
                return PorterDuff.Mode.SRC;
            case DST:
                return PorterDuff.Mode.DST;
            case SRC_OVER:
                return PorterDuff.Mode.SRC_OVER;
            case DST_OVER:
                return PorterDuff.Mode.DST_OVER;
            case SRC_IN:
                return PorterDuff.Mode.SRC_IN;
            case DST_IN:
                return PorterDuff.Mode.DST_IN;
            case SRC_OUT:
                return PorterDuff.Mode.SRC_OUT;
            case DST_OUT:
                return PorterDuff.Mode.DST_OUT;
            case SRC_ATOP:
                return PorterDuff.Mode.SRC_ATOP;
            case DST_ATOP:
                return PorterDuff.Mode.DST_ATOP;
            case XOR:
                return PorterDuff.Mode.XOR;
            case PLUS:
                return PorterDuff.Mode.ADD;
            case MODULATE:
                return PorterDuff.Mode.MULTIPLY;
            case SCREEN:
                return PorterDuff.Mode.SCREEN;
            case OVERLAY:
                return PorterDuff.Mode.OVERLAY;
            case DARKEN:
                return PorterDuff.Mode.DARKEN;
            case LIGHTEN:
                return PorterDuff.Mode.LIGHTEN;
            default:
                return null;
        }
    }
}
