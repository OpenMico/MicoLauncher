package com.xiaomi.micolauncher.module.intercom;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class IntercomSettings {
    @SerializedName("current_device")
    public CurrentDevice current_device;
    @SerializedName("family_device")
    public List<Object> family_device;
    @SerializedName("home_id")
    public String home_id;
    @SerializedName("night_mode_no_disturbing")
    public boolean night_mode_no_disturbing;
    @SerializedName("personal_device")
    public List<Object> personal_device;
    @SerializedName("user_id")
    public long user_id;

    public IntercomSettings(long j, String str, CurrentDevice currentDevice, boolean z) {
        this.user_id = j;
        this.home_id = str;
        this.current_device = currentDevice;
        this.night_mode_no_disturbing = z;
    }

    /* loaded from: classes3.dex */
    public static class CurrentDevice {
        @SerializedName("device_id")
        public String device_id;
        @SerializedName("support_intercom")
        public boolean support_intercom;

        public CurrentDevice(String str, boolean z) {
            this.device_id = str;
            this.support_intercom = z;
        }
    }
}
