package com.xiaomi.micolauncher.skills.mitv;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.onetrack.api.b;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class MiTVDevices {
    private int a;
    private int b;
    private CopyOnWriteArrayList<DeviceListBean> c = new CopyOnWriteArrayList<>();

    public int getEnable() {
        return this.a;
    }

    public void setEnable(int i) {
        this.a = i;
    }

    public int getTv_push() {
        return this.b;
    }

    public void setTv_push(int i) {
        this.b = i;
    }

    public List<DeviceListBean> getDevice_list() {
        return this.c;
    }

    public void setDevice_list(CopyOnWriteArrayList<DeviceListBean> copyOnWriteArrayList) {
        this.c = copyOnWriteArrayList;
    }

    /* loaded from: classes3.dex */
    public static class DeviceListBean {
        @SerializedName("udn")
        private String a;
        @SerializedName("name")
        private String b;
        @SerializedName(b.E)
        private String c;
        @SerializedName("selected")
        private int d;
        @SerializedName("voice")
        private int e;
        @SerializedName("bluetooth_mac")
        private String f;
        @SerializedName("online")
        private int g;

        public String getUdn() {
            return this.a;
        }

        public void setUdn(String str) {
            this.a = str;
        }

        public String getName() {
            return this.b;
        }

        public void setName(String str) {
            this.b = str;
        }

        public String getHost() {
            return this.c;
        }

        public void setHost(String str) {
            this.c = str;
        }

        public int getSelected() {
            return this.d;
        }

        public void setSelected(int i) {
            this.d = i;
        }

        public int getVoice() {
            return this.e;
        }

        public void setVoice(int i) {
            this.e = i;
        }

        public String getBluetooth_mac() {
            return this.f;
        }

        public void setBluetooth_mac(String str) {
            this.f = str;
        }

        public int getOnline() {
            return this.g;
        }

        public void setOnline(int i) {
            this.g = i;
        }

        public String displayToString() {
            return Gsons.getGson().toJson(this);
        }
    }
}
