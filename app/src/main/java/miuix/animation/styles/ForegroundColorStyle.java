package miuix.animation.styles;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import miuix.animation.IAnimTarget;
import miuix.animation.R;
import miuix.animation.ViewTarget;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.DeviceUtils;

/* loaded from: classes5.dex */
public class ForegroundColorStyle extends PropertyStyle {
    public static void start(IAnimTarget iAnimTarget, UpdateInfo updateInfo) {
        View a = a(iAnimTarget);
        if (!a(a)) {
            int i = updateInfo.animInfo.tintMode;
            TintDrawable a2 = TintDrawable.a(a);
            Object tag = a.getTag(R.id.miuix_animation_tag_view_corner);
            if (tag != null && ((tag instanceof Float) || (tag instanceof Integer))) {
                a2.a(((Float) tag).floatValue());
            }
            i = 1;
            if (!(DeviceUtils.getDeviceLevel() == 0 && i == -1) && i == -1) {
                i = 0;
            }
            a2.a(i & 1);
        }
    }

    public static void end(IAnimTarget iAnimTarget, UpdateInfo updateInfo) {
        View a = a(iAnimTarget);
        if (!a(a)) {
            TintDrawable tintDrawable = TintDrawable.get(a);
            int i = (int) updateInfo.animInfo.value;
            if (tintDrawable != null && Color.alpha(i) == 0) {
                tintDrawable.a();
            }
        }
    }

    private static View a(IAnimTarget iAnimTarget) {
        if (iAnimTarget instanceof ViewTarget) {
            return ((ViewTarget) iAnimTarget).getTargetObject();
        }
        return null;
    }

    private static boolean a(View view) {
        return view == null || Build.VERSION.SDK_INT < 23;
    }
}
