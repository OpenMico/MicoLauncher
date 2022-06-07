package org.hapjs.features.channel.vui;

import android.util.SparseArray;

/* loaded from: classes5.dex */
public class VuiErrorCode {
    public static final int CODE_CHANNEL_SEND_ERROR = 2001;
    public static final int CODE_NO_ERROR = 0;
    public static final int CODE_TTS_IS_INTERRUPTED = 3003;
    public static final int CODE_VERSION_NOT_COMPATIBLE = 3002;
    public static final int CODE_VERSION_NOT_MATCH = 3001;
    private static SparseArray<String> a = new SparseArray<>();

    static {
        a.put(2001, "channel error");
        a.put(3001, "version not match");
        a.put(3002, "version not compatible");
        a.put(3003, "tts is interrupted");
    }

    public static String getErrorMessage(int i) {
        return a.get(i, "");
    }
}
