package com.xiaomi.mico.common;

import androidx.annotation.StringRes;

/* loaded from: classes3.dex */
public interface HardwareConfig {
    public static final String CHANNEL_NAME_LX04 = "LX04";
    public static final String CHANNEL_NAME_X08A = "X08";
    public static final String CHANNEL_NAME_X08C = "X08C";
    public static final String CHANNEL_NAME_X08E = "X08E";
    public static final String CHANNEL_NAME_X10 = "X10";
    public static final String CHANNEL_NAME_X10A = "X10A";
    public static final String CHANNEL_NAME_X10P = "X10P";
    public static final String CHANNEL_NAME_X6A = "X6A";
    public static final String CMIIT_ID_NO_UES = "";
    public static final String CMIIT_ID_X08A = "2019AP9323";
    public static final String CMIIT_ID_X08C = "2020DP1441";
    public static final String CMIIT_ID_X08E = "2020AP5549";
    public static final String CMIIT_ID_X10A = "2022AP0521";
    public static final String CMIIT_ID_X6A = "2022DP3977";
    public static final String HARDWARE_NAME_LX04 = "LX04";
    public static final String HARDWARE_NAME_X08A = "X08A";
    public static final String HARDWARE_NAME_X08C = "X08C";
    public static final String HARDWARE_NAME_X08E = "X08E";
    public static final String HARDWARE_NAME_X10A = "X10A";
    public static final String HARDWARE_NAME_X6A = "X6A";

    byte getBLEType();

    String getClientId();

    String getClientKey();

    String getCmiitId();

    String getIotFriendlyName();

    String getMiBrainDeviceModel();

    short getMiotProductId();

    String getName();

    @StringRes
    default int getProductName() {
        return 0;
    }

    String statChannel();

    @StringRes
    default int getDefaultAlias() {
        return R.string.mi_speaker_default_name;
    }
}
