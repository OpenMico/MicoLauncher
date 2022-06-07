package org.fourthline.cling.support.model;

/* loaded from: classes5.dex */
public enum TransportState {
    STOPPED,
    PLAYING,
    TRANSITIONING,
    PAUSED_PLAYBACK,
    PAUSED_RECORDING,
    RECORDING,
    NO_MEDIA_PRESENT,
    CUSTOM;
    
    String value = name();

    TransportState() {
    }

    public String getValue() {
        return this.value;
    }

    public TransportState setValue(String str) {
        this.value = str;
        return this;
    }

    public static TransportState valueOrCustomOf(String str) {
        try {
            return valueOf(str);
        } catch (IllegalArgumentException unused) {
            return CUSTOM.setValue(str);
        }
    }
}
