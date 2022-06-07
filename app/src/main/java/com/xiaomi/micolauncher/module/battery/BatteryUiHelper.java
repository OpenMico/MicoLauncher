package com.xiaomi.micolauncher.module.battery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.ArrayMap;
import androidx.annotation.GuardedBy;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import java.util.Map;

/* loaded from: classes3.dex */
public class BatteryUiHelper {
    public static final boolean APPLY_SIMPLE_FAST_BORDER_COLOR = true;
    @GuardedBy("pageToColor")
    private final Map<Class, Integer> a = new ArrayMap();
    private Rect b = new Rect();
    private int c;
    private int d;

    public boolean isReady() {
        return BatteryStatusMonitor.getInstance().isBatteryInfoValid();
    }

    public int getTextColor(Context context) {
        return getGraphicsBorderColor(context);
    }

    public int peekGraphicsBorderColor(Context context) {
        int a = a(context);
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        if (topActivity == null) {
            return a;
        }
        synchronized (this.a) {
            Integer num = this.a.get(topActivity.getClass());
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }
    }

    public int getGraphicsBorderColor(Context context) {
        int a = a(context);
        int i = this.d;
        return i == 0 ? a : i;
    }

    private int a(Context context) {
        return context.getColor(R.color.color_272727);
    }

    public int getGraphicsFillColor(Context context, int i) {
        if (BatteryStatusMonitor.getInstance().isCharging() && !BatteryStatusMonitor.getInstance().isFull()) {
            return context.getColor(R.color.color_25C865);
        }
        if (BatteryStatusMonitor.isBatteryLow(i)) {
            return context.getColor(R.color.color_FF004E);
        }
        if (this.c == 0) {
            L.battery.e("fill color not set");
            this.c = context.getColor(R.color.color_C5C7CD);
        }
        return this.c;
    }

    public void setFillColorForNormalStatus(int i) {
        this.c = i;
    }

    public void setBorderColor(int i) {
        this.d = i;
    }
}
