package io.netty.handler.codec.spdy;

import java.util.Set;

/* loaded from: classes4.dex */
public interface SpdySettingsFrame extends SpdyFrame {
    public static final int SETTINGS_CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
    public static final int SETTINGS_CURRENT_CWND = 5;
    public static final int SETTINGS_DOWNLOAD_BANDWIDTH = 2;
    public static final int SETTINGS_DOWNLOAD_RETRANS_RATE = 6;
    public static final int SETTINGS_INITIAL_WINDOW_SIZE = 7;
    public static final int SETTINGS_MAX_CONCURRENT_STREAMS = 4;
    public static final int SETTINGS_MINOR_VERSION = 0;
    public static final int SETTINGS_ROUND_TRIP_TIME = 3;
    public static final int SETTINGS_UPLOAD_BANDWIDTH = 1;

    boolean clearPreviouslyPersistedSettings();

    int getValue(int i);

    Set<Integer> ids();

    boolean isPersistValue(int i);

    boolean isPersisted(int i);

    boolean isSet(int i);

    SpdySettingsFrame removeValue(int i);

    SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean z);

    SpdySettingsFrame setPersistValue(int i, boolean z);

    SpdySettingsFrame setPersisted(int i, boolean z);

    SpdySettingsFrame setValue(int i, int i2);

    SpdySettingsFrame setValue(int i, int i2, boolean z, boolean z2);
}
