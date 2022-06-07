package com.xiaomi.micolauncher.common.player.policy;

import java.io.Serializable;

/* loaded from: classes3.dex */
public enum AudioSource implements Serializable {
    AUDIO_SOURCE_NULL,
    AUDIO_SOURCE_WAKEUP,
    AUDIO_SOURCE_TTS,
    AUDIO_SOURCE_PROMPT_VOICE,
    AUDIO_SOURCE_LOCAL_PLAYER,
    AUDIO_SOURCE_URL_PLAYER,
    AUDIO_SOURCE_MUSIC,
    AUDIO_SOURCE_MUSIC_BT,
    AUDIO_SOURCE_MUSIC_MI_PLAY,
    AUDIO_SOURCE_ALARM,
    AUDIO_SOURCE_ALARM_EARTHQUAKE,
    AUDIO_SOURCE_RINGTONE,
    AUDIO_SOURCE_RINGTONE_VOIP,
    AUDIO_SOURCE_RINGTONE_IMS,
    AUDIO_SOURCE_RINGTONE_DOORBELL,
    AUDIO_SOURCE_RINGTONE_CAMERA,
    AUDIO_SOURCE_CALL,
    AUDIO_SOURCE_CALL_VOIP,
    AUDIO_SOURCE_CALL_IMS,
    AUDIO_SOURCE_CALL_DOORBELL,
    AUDIO_SOURCE_CALL_CAMERA,
    AUDIO_SOURCE_UI,
    AUDIO_SOURCE_MESH,
    AUDIO_SOURCE_PUSH,
    AUDIO_SOURCE_OPEN_PLATFORM,
    AUDIO_SOURCE_SHORT_VIDEO,
    AUDIO_SOURCE_LONG_VIDEO,
    AUDIO_SOURCE_FAKE,
    AUDIO_SOURCE_POWER_KEY,
    AUDIO_SOURCE_SHUT_DOWN,
    AUDIO_SOURCE_FACTORY_RESET;

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        switch (this) {
            case AUDIO_SOURCE_WAKEUP:
                return 4;
            case AUDIO_SOURCE_TTS:
            case AUDIO_SOURCE_LOCAL_PLAYER:
                return 2;
            case AUDIO_SOURCE_PROMPT_VOICE:
                return 64;
            case AUDIO_SOURCE_CALL:
            case AUDIO_SOURCE_CALL_CAMERA:
            case AUDIO_SOURCE_CALL_IMS:
            case AUDIO_SOURCE_CALL_VOIP:
            case AUDIO_SOURCE_CALL_DOORBELL:
                return 32;
            case AUDIO_SOURCE_ALARM:
            case AUDIO_SOURCE_ALARM_EARTHQUAKE:
                return 8;
            case AUDIO_SOURCE_MUSIC:
            case AUDIO_SOURCE_MUSIC_BT:
            case AUDIO_SOURCE_MUSIC_MI_PLAY:
            case AUDIO_SOURCE_LONG_VIDEO:
                return 1;
            case AUDIO_SOURCE_SHORT_VIDEO:
                return 128;
            case AUDIO_SOURCE_PUSH:
            case AUDIO_SOURCE_RINGTONE:
            case AUDIO_SOURCE_RINGTONE_IMS:
            case AUDIO_SOURCE_RINGTONE_VOIP:
            case AUDIO_SOURCE_RINGTONE_CAMERA:
            case AUDIO_SOURCE_RINGTONE_DOORBELL:
                return 16;
            case AUDIO_SOURCE_UI:
            case AUDIO_SOURCE_MESH:
            case AUDIO_SOURCE_OPEN_PLATFORM:
                return 512;
            case AUDIO_SOURCE_FAKE:
                return 256;
            case AUDIO_SOURCE_URL_PLAYER:
                return 1024;
            default:
                return 0;
        }
    }
}
