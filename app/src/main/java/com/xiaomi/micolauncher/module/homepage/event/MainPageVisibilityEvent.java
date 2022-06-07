package com.xiaomi.micolauncher.module.homepage.event;

/* loaded from: classes3.dex */
public class MainPageVisibilityEvent {
    public static final int INIT_MIOT_UPDATE_DEVICELIST = 2;
    public static final int INIT_VIEWHOLDER_FINISHED = 1;
    public static final int SHOW_MAIN_PAGE = 3;
    public static int showMainPage;
    public int state;

    public MainPageVisibilityEvent(int i) {
        this.state = i;
    }

    public static boolean miotDevicelistUpdated() {
        return (showMainPage & 2) != 0;
    }
}
