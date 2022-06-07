package com.xiaomi.micolauncher.module.homepage.event;

/* loaded from: classes3.dex */
public class MainPageEffectEvent {
    public static String EFFECT_BEGIN = "effect_begin";
    public static String EFFECT_EDN = "effect_end";
    public static String LIGHT_BRIGHTNESS = "brightness";
    public static String LIGHT_EFFECT = "LIGHT_EFFECT";
    public String effect;
    public String key;
    public float value;

    public MainPageEffectEvent(String str, String str2, float f) {
        this.effect = str;
        this.key = str2;
        this.value = f;
    }
}
