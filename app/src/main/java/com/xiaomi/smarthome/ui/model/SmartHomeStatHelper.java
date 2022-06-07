package com.xiaomi.smarthome.ui.model;

import com.xiaomi.micolauncher.common.stat.StatUtils;

/* loaded from: classes4.dex */
public class SmartHomeStatHelper {
    public static final String PARAM_VALUE_DIS = "dis";
    public static final String PARAM_VALUE_EQUIPMENT = "equipment";
    public static final String PARAM_VALUE_EQUIPMENT_MORE = "equipmentMore";
    public static final String PARAM_VALUE_HOME = "home";
    public static final String PARAM_VALUE_MEDIA = "media";
    public static final String PARAM_VALUE_MODE = "mode";
    public static final String PARAM_VALUE_NO_SAME_WITH_SPEAKER = "noSameWithSpeaker";
    public static final String PARAM_VALUE_RANK = "rank";
    public static final String PARAM_VALUE_SAME_WITH_SPEAKER = "sameWithSpeaker";
    public static final String PARAM_VALUE_SCENE = "scene";
    public static final String PARAM_VALUE_TAB = "tab";

    public static void recordSmartTabControlModeShow() {
        StatUtils.recordCountEvent("SmartHome", "smartTab_controlMode_show");
    }

    public static void recordSmartTabControlModeClick(String str) {
        StatUtils.recordCountEvent("SmartHome", "smartTab_controlMode_click", "category", str);
    }

    public static void recordGuideCardShow() {
        StatUtils.recordCountEvent("SmartHome", "guideCard_show");
    }

    public static void recordGuideCardClick() {
        StatUtils.recordCountEvent("SmartHome", "guideCard_click");
    }

    public static void recordSmartTabRoomModeShow() {
        StatUtils.recordCountEvent("SmartHome", "smartTab_roomMode_show");
    }

    public static void recordSmartTabRoomModeClick(String str) {
        StatUtils.recordCountEvent("SmartHome", "smartTab_roomMode_click", "category", str);
    }

    public static void recordSmartTabDisChoose(String str) {
        StatUtils.recordCountEvent("SmartHome", "smartTab_disChoose", PARAM_VALUE_DIS, str);
    }
}
