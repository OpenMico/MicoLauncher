package com.xiaomi.mi_soundbox_command_sdk;

/* loaded from: classes3.dex */
public interface UserCommandCallback {
    void onCommand(String str, MiSoundBoxCommandExtras miSoundBoxCommandExtras);

    void onRemoteServiceBind();
}
