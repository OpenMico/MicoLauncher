package com.xiaomi.mi_soundbox_command_sdk;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public interface SoundBoxCommandInterface {
    void destroy();

    UserCommandCallback getUserCommandCallback();

    void initialize(@NonNull Context context, @Nullable Handler handler);

    boolean isInitialized();

    boolean isServiceConnected();

    void setPaused();

    void setPlayInfo(PlayInfoArg playInfoArg);

    void setResumed();

    void setStarted();

    void setStopped();

    void setUserCommandCallback(UserCommandCallback userCommandCallback);

    /* loaded from: classes3.dex */
    public static class PlayInfoArg {
        public String episodeId;
        public int episodeIndex;
        public String extras;
        public String id;
        public String title;

        public boolean isValid() {
            String str = this.id;
            return (str == null || str.length() == 0) ? false : true;
        }
    }
}
