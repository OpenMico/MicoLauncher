package com.xiaomi.mico.settingslib.core;

import com.xiaomi.mico.settingslib.R;

/* loaded from: classes3.dex */
public enum LockScreen {
    WALL_PAPER(0, R.drawable.wallpaper_pictorial_default_bg, R.drawable.thumbnail_wall_paper, R.string.lock_screen_wall_paper, R.string.lock_screen_wall_paper_subtitle, R.drawable.dialmark_dongtaihuabao),
    CLOCK_DIAL(1, R.drawable.wallwrapper_dial, R.drawable.thumbnail_clock_dial, R.string.lock_screen_clock_dial, R.string.lock_screen_clock_dial_subtitle, R.drawable.dialmark_zhizhenshizhong),
    CLOCK_FLIP(2, R.drawable.wallwrapper_flip, R.drawable.thumbnail_clock_flip, R.string.lock_screen_clock_flip, R.string.lock_screen_clock_flip_subtitle, R.drawable.dialmark_fanyeshizhong),
    EMPTY(3, 0, R.drawable.thumbnail_empty, R.string.lock_screen_clock_empty, R.string.lock_screen_empty_subtitle, R.drawable.dialmark_empty),
    ALBUM(4, R.drawable.wallwrapper_album, R.drawable.thumbnail_album, R.string.lock_screen_picture, R.string.lock_screen_picture_subtitle, R.drawable.dialmark_album),
    MAIN(5, R.drawable.wallpaper_time_number_default_bg, R.drawable.thumbnail_clock_0, R.string.lock_screen_main, R.string.lock_screen_main_subtitle, R.drawable.dialmark_dongtaishuzi),
    TIME_WALL_PAPER(6, 0, R.drawable.thumbnail_time_wall_paper, R.string.lock_screen_time_wall_paper, R.string.lock_screen_time_wall_paper_subtitle, R.drawable.dialmark_tianse_shiguang),
    TIME_DOMESTIC(7, 0, R.drawable.thumbnail_time_domestic, R.string.lock_screen_time_domestic, R.string.lock_screen_time_domestic_subtitle),
    SUMMER_NIGHT(9, 0, R.drawable.thumbnail_clock_summer_night, R.string.lock_screen_summer_night, R.string.lock_screen_empty_subtitle, R.drawable.dialmark_summer_night),
    FOREST(10, 0, R.drawable.thumbnail_dial_forest, R.string.lock_screen_forest, R.string.lock_screen_empty_subtitle, R.drawable.dialmark_fantasy_forest),
    SPACEMAN(11, 0, R.drawable.thumbnail_dial_space_roaming, R.string.lock_screen_space_roaming, R.string.lock_screen_empty_subtitle, R.drawable.dialmark_space_roaming),
    FINGER_NUMBER(13, 0, R.drawable.thumbnail_finger_number, R.string.lock_screen_finger_number, R.string.lock_screen_empty_subtitle, R.drawable.dialmark_finger_number);
    
    private int backgroundRes;
    private int dialmarkRes;
    private int id;
    private int nameRes;
    private int settingRes;
    private int subtitleRes;

    LockScreen(int i, int i2, int i3, int i4, int i5, int i6) {
        this.id = i;
        this.backgroundRes = i2;
        this.settingRes = i3;
        this.nameRes = i4;
        this.subtitleRes = i5;
        this.dialmarkRes = i6;
    }

    LockScreen(int i, int i2, int i3, int i4, int i5) {
        this.id = i;
        this.backgroundRes = i2;
        this.settingRes = i3;
        this.nameRes = i4;
        this.subtitleRes = i5;
    }

    public static LockScreen valueOf(int i) {
        LockScreen[] values = values();
        for (LockScreen lockScreen : values) {
            if (lockScreen.getId() == i) {
                return lockScreen;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    public int getBackgroundRes() {
        return this.backgroundRes;
    }

    public int getSettingRes() {
        return this.settingRes;
    }

    public int getNameRes() {
        return this.nameRes;
    }

    public int getSubtitleRes() {
        return this.subtitleRes;
    }

    public boolean isUserType() {
        return this.id < 100;
    }

    public int getDialmarkRes() {
        return this.dialmarkRes;
    }

    public static String getPlaceholderUrl(LockScreen lockScreen, boolean z) {
        switch (lockScreen) {
            case WALL_PAPER:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/2618ca3d-813d-4194-8889-36f85890873d";
            case CLOCK_DIAL:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/ce5f774e-5ece-4f91-a98f-65ccf0f0c132";
            case CLOCK_FLIP:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/4a29c5b0-50d2-4063-b6c3-6f34a828bad2";
            case EMPTY:
                return z ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/185def67-7d37-416a-bf56-4ac2e980e3e6" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/53d6ae9a-5112-4f7e-9f26-27c429750bfc";
            case ALBUM:
                return z ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/465e2cfb-d472-4051-b4de-2683f60aa509" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/634d7ab0-b67d-4eb7-b59e-152cfc97c752";
            case MAIN:
                return z ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/87d736b6-7148-4238-8595-b952de22dc3d" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/64a559d3-fe54-4310-b096-f4c9eb63c008";
            case TIME_WALL_PAPER:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/30256a99-3a85-45e8-8422-d7aee5cb79be";
            case TIME_DOMESTIC:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/84d49baa-5f59-4999-87a5-882bc6a9ca8a";
            case SUMMER_NIGHT:
                return z ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/afccf411-de84-4b8c-9975-59bb29cdc06f" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/359df616-1ca8-48a8-a935-6d480db18c00";
            case FOREST:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/d4f64f90-4fb1-475a-80f0-a1657baf272e";
            case SPACEMAN:
                return z ? "https://cdn.cnbj1.fds.api.mi-img.com/mico/d33dbdc4-b1aa-4072-aa4b-651e21fcb69b" : "https://cdn.cnbj1.fds.api.mi-img.com/mico/bdcab992-c73a-4bd8-bc0e-cf33b1cd7801";
            case FINGER_NUMBER:
                return "https://cdn.cnbj1.fds.api.mi-img.com/mico/5295f449-0929-4181-8ecc-5a1b18b10ab9";
            default:
                return null;
        }
    }
}
