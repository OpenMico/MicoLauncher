package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Logger;

/* loaded from: classes.dex */
public class PackageManagerHelper {
    private static final String a = Logger.tagWithPrefix("PackageManagerHelper");

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(@NonNull Context context, @NonNull Class<?> cls, boolean z) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, cls.getName()), z ? 1 : 2, 1);
            Logger logger = Logger.get();
            String str = a;
            Object[] objArr = new Object[2];
            objArr[0] = cls.getName();
            objArr[1] = z ? "enabled" : "disabled";
            logger.debug(str, String.format("%s %s", objArr), new Throwable[0]);
        } catch (Exception e) {
            Logger logger2 = Logger.get();
            String str2 = a;
            Object[] objArr2 = new Object[2];
            objArr2[0] = cls.getName();
            objArr2[1] = z ? "enabled" : "disabled";
            logger2.debug(str2, String.format("%s could not be %s", objArr2), e);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> cls) {
        return isComponentExplicitlyEnabled(context, cls.getName());
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String str) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, str)) == 1;
    }
}
