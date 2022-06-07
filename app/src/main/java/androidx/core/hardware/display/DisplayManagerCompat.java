package androidx.core.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> a = new WeakHashMap<>();
    private final Context b;

    private DisplayManagerCompat(Context context) {
        this.b = context;
    }

    @NonNull
    public static DisplayManagerCompat getInstance(@NonNull Context context) {
        DisplayManagerCompat displayManagerCompat;
        synchronized (a) {
            displayManagerCompat = a.get(context);
            if (displayManagerCompat == null) {
                displayManagerCompat = new DisplayManagerCompat(context);
                a.put(context, displayManagerCompat);
            }
        }
        return displayManagerCompat;
    }

    @Nullable
    public Display getDisplay(int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            return ((DisplayManager) this.b.getSystemService("display")).getDisplay(i);
        }
        Display defaultDisplay = ((WindowManager) this.b.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay.getDisplayId() == i) {
            return defaultDisplay;
        }
        return null;
    }

    @NonNull
    public Display[] getDisplays() {
        return Build.VERSION.SDK_INT >= 17 ? ((DisplayManager) this.b.getSystemService("display")).getDisplays() : new Display[]{((WindowManager) this.b.getSystemService("window")).getDefaultDisplay()};
    }

    @NonNull
    public Display[] getDisplays(@Nullable String str) {
        if (Build.VERSION.SDK_INT >= 17) {
            return ((DisplayManager) this.b.getSystemService("display")).getDisplays(str);
        }
        return str == null ? new Display[0] : new Display[]{((WindowManager) this.b.getSystemService("window")).getDefaultDisplay()};
    }
}
