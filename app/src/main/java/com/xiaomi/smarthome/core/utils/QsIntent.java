package com.xiaomi.smarthome.core.utils;

import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;

/* loaded from: classes4.dex */
public class QsIntent {
    public static void onMusicPlay() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.Play());
    }

    public static void onMusicPause() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.Pause());
    }

    public static void onMusicNext() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.Next());
    }

    public static void onMusicPrev() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.Prev());
    }

    public static void onMusicJump() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.Jump());
    }

    public static void onMusicPlayWifi() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.PlayWifi());
    }

    public static void onMusicNextWifi() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.NextWifi());
    }

    public static void onMusicPrevWifi() {
        EventBusRegistry.getEventBus().post(new MediaControllerEvent.PrevWifi());
    }
}
