package com.xiaomi.micolauncher.module;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.module.homepage.event.MainPageEffectEvent;
import com.xiaomi.smarthome.ui.widgets.LightGradientView;

/* loaded from: classes3.dex */
public class EffectHelper {
    public static void effectSetListener(ViewGroup viewGroup, MainPageEffectEvent mainPageEffectEvent) {
        FrameLayout frameLayout = (FrameLayout) viewGroup;
        if (!mainPageEffectEvent.effect.equals(MainPageEffectEvent.LIGHT_EFFECT)) {
            return;
        }
        if (mainPageEffectEvent.key.equals(MainPageEffectEvent.EFFECT_EDN)) {
            frameLayout.removeAllViews();
        } else if (mainPageEffectEvent.key.equals(MainPageEffectEvent.EFFECT_BEGIN)) {
            frameLayout.removeAllViews();
            frameLayout.addView(LayoutInflater.from(MicoApplication.getGlobalContext()).inflate(R.layout.main_page_view_light_effect, (ViewGroup) null));
        } else if (mainPageEffectEvent.key.equals(MainPageEffectEvent.LIGHT_BRIGHTNESS) && frameLayout.getChildCount() > 0) {
            ((LightGradientView) ((LightGradientView) frameLayout.getChildAt(0)).findViewById(R.id.lightGradientView)).setProgressBrightness(mainPageEffectEvent.value);
        }
    }
}
