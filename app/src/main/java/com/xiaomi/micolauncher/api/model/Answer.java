package com.xiaomi.micolauncher.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes3.dex */
public class Answer {

    /* loaded from: classes3.dex */
    public static class App {
        public String name;
        @SerializedName("pkg_name")
        public String pkgName;
    }

    /* loaded from: classes3.dex */
    public static class Content {
        public long list_id;
        public int needStopForLX04;
        public String play_mode;
    }

    /* loaded from: classes3.dex */
    public static class Header {
        @SerializedName("dialog_id")
        public String dialogId;
        public String id;
        public String name;
        public String namespace;
    }

    /* loaded from: classes3.dex */
    public static class Intent {
        @SerializedName("min_version")
        public long minVersion;
        @SerializedName("pkg_name")
        public String pkgName;
        public String type;
        public String uri;
    }

    /* loaded from: classes3.dex */
    public static class Payload {
        public List<App> apps;
        public String identifier;
        public Intent intent;
        public String keyword;
        public String name;
        public String operation;
        public String type;
        @SerializedName("use_local_app")
        public boolean useLocalApp;
    }

    /* loaded from: classes3.dex */
    public static class SmartAppContent {
        public Header header;
        public Payload payload;
    }
}
