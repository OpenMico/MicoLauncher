package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class Miot {

    /* loaded from: classes3.dex */
    public static class Device implements Serializable {
        private static final long serialVersionUID = -337157334942626855L;
        public String deviceID;
        public String imageURL;
        public boolean isOnline;
        public String model;
        public String name;
        public String productID;
        public String productURN;
        public Map<String, String> properties;
        public boolean supported;
        public List<String> tips;
    }

    /* loaded from: classes3.dex */
    public static class Home {
        public List<String> dids;
        public String id;
        public String name;
        public List<Room> roomlist;
    }

    /* loaded from: classes3.dex */
    public static class HomeInfo {
        public List<Home> homelist;
    }

    /* loaded from: classes3.dex */
    public static class HomeResult {
        @SerializedName("code")
        public int code;
        @SerializedName("message")
        public String message;
        @SerializedName("result")
        public HomeInfo result;
    }

    /* loaded from: classes3.dex */
    public static class Room {
        public String bssid;
        public List<String> dids;
        public String id;
        public String name;
        public String parentid;
    }
}
