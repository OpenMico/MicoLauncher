package com.xiaomi.smarthome.core.miot;

/* loaded from: classes4.dex */
public class MediaControllerEvent {

    /* loaded from: classes4.dex */
    public static class Jump {
    }

    /* loaded from: classes4.dex */
    public static class Next {
    }

    /* loaded from: classes4.dex */
    public static class NextWifi {
    }

    /* loaded from: classes4.dex */
    public static class Pause {
    }

    /* loaded from: classes4.dex */
    public static class Play {
    }

    /* loaded from: classes4.dex */
    public static class PlayWifi {
    }

    /* loaded from: classes4.dex */
    public static class Prev {
    }

    /* loaded from: classes4.dex */
    public static class PrevWifi {
    }

    /* loaded from: classes4.dex */
    public static class MusicState {
        private int a;

        public MusicState(int i) {
            this.a = i;
        }

        public int getState() {
            return this.a;
        }
    }
}
