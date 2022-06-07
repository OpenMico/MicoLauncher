package org.hapjs.features.channel.vui;

import org.hapjs.features.channel.vui.data.Location;

/* loaded from: classes5.dex */
public interface IWorker {
    void closeMic(VuiCallback vuiCallback);

    void connect(int i, VuiCallback vuiCallback);

    boolean onAccept(String str, String str2);

    void openMic(VuiCallback vuiCallback);

    void pause(String str, VuiCallback vuiCallback);

    void query(String str, VuiCallback vuiCallback);

    void registerLocationUpdate(long j, VuiCallback<Location> vuiCallback);

    void resume(String str, VuiCallback vuiCallback);

    void stopTts(VuiCallback vuiCallback);

    void tts(String str, VuiCallback vuiCallback);

    void unregisterLocationUpdate(long j, VuiCallback vuiCallback);
}
