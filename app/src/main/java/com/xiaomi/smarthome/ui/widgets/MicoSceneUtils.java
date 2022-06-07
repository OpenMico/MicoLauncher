package com.xiaomi.smarthome.ui.widgets;

import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class MicoSceneUtils {
    public static final String SCENE_NAME_GOOD_NIGHT = "晚安";
    public static final String SCENE_NAME_LEFT_HOME = "我出门了";

    public static int getSceneImage(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 835695) {
            if (hashCode == 769280359 && str.equals(SCENE_NAME_LEFT_HOME)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(SCENE_NAME_GOOD_NIGHT)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return R.drawable.scene_toast_good_night;
            case 1:
                return R.drawable.scene_toast_left_home;
            default:
                return R.drawable.scene_toast_custom;
        }
    }

    private MicoSceneUtils() {
    }
}
