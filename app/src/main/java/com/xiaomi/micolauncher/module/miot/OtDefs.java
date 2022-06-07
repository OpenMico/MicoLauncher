package com.xiaomi.micolauncher.module.miot;

import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.onetrack.OneTrack;
import java.util.List;

/* loaded from: classes3.dex */
public class OtDefs {
    public static final String CONNECT_ERROR = "error";
    public static final String CONNECT_OK = "ok";
    public static final String INTERNAL_AUTH_LOGIN = "device_auth_login";
    public static final String INTERNAL_HELLO = "_internal.hello";
    public static final String INTERNAL_REQ_WIFI_CONF_STATUS = "_internal.req_wifi_conf_status";
    public static final String INTERNAL_RES_WIFI_CONF_STATUS = "_internal.res_wifi_conf_status";
    public static final String INTERNAL_WIFI_CONNECTED = "_internal.wifi_connected";
    public static final String INTERNAL_WIFI_CONNECT_FAILED = "_internal.wifi_connect_failed";
    public static final String INTERNAL_WIFI_DISCONNECT_EVENT = "_internal.wifi_disconnected_event";
    public static final String INTERNAL_WIFI_DISCONNECT_REQ = "_internal.wifi_disconnect_req";
    public static final String INTERNAL_WIFI_DISCONNECT_RESP = "_internal.wifi_disconnect_resp";
    public static final String INTERNAL_WIFI_RECONNECTED_FAILED_EVENT = "_internal.wifi_reconnected_failed_event";
    public static final String INTERNAL_WIFI_SCAN_REQ = "_internal.wifi_scan_req";
    public static final String INTERNAL_WIFI_SCAN_RESP = "_internal.wifi_scan_resp";
    public static final String INTERNAL_WIFI_START = "_internal.wifi_start";
    public static final String LOCAL_MSC_SIGN_REQ = "local.msc_sign_req";
    public static final String LOCAL_MSC_SIGN_RESP = "local.msc_sign_resp";
    public static final String METHOD_PREFIX_INTERNAL = "_internal";
    public static final String MIIO_IR_PLAY = "miIO.ir_play";
    public static final String MIIO_IR_PLAY_BATCH = "miIO.ir_play_batch";
    public static final String MIIO_IR_READ = "miIO.ir_read";
    public static final String MIIO_IR_STARTER = "miIO.ir";
    public static final int TYPE_SCAN_ALL_WIFI_CHANNELS = 1;

    /* loaded from: classes3.dex */
    public static class IRParams {
        @SerializedName("async")
        public int async;
        @SerializedName("code")
        public String code;
        @SerializedName("codes")
        public String codes;
        @SerializedName("freq")
        public int freq;
        @SerializedName("head")
        public String head;
        @SerializedName(ai.aR)
        public int interval;
        @SerializedName("length")
        public int length;
    }

    /* loaded from: classes3.dex */
    public static class IRStatus {
        @SerializedName("id")
        public int id;
        @SerializedName("result")
        public String result = "ok";
    }

    /* loaded from: classes3.dex */
    public static class OtLocalStatus extends d {
        public static final String STATUS_CLOUD_CONNECTED = "cloud_connected";
        @SerializedName("params")
        String a;
    }

    /* loaded from: classes3.dex */
    public static class WifiScanRequestParams {
        @SerializedName("channels")
        public List<Integer> channels;
        @SerializedName("ssid")
        public String ssid;
        @SerializedName("type")
        public int type;
    }

    /* loaded from: classes3.dex */
    static class b {
        @SerializedName("loginurl")
        String a;
    }

    /* loaded from: classes3.dex */
    static class c {
        @SerializedName("id")
        int a;
        @SerializedName("code")
        int b;
    }

    /* loaded from: classes3.dex */
    static class d {
        @SerializedName(SchemaActivity.KEY_METHOD)
        public String method;
    }

    /* loaded from: classes3.dex */
    static class f {
        @SerializedName("hash_type")
        String a;
        @SerializedName("hash")
        String b;
    }

    /* loaded from: classes3.dex */
    static class j {
        @SerializedName("ssid")
        String a;
        @SerializedName("item_num")
        int b;
        @SerializedName("item")
        List<q> c;
    }

    /* loaded from: classes3.dex */
    static class l {
        @SerializedName("ssid")
        String a;
        @SerializedName("bssid")
        String b;
        @SerializedName("result")
        String c;
    }

    /* loaded from: classes3.dex */
    static class n {
        @SerializedName("ssid")
        String a;
        @SerializedName("bssid")
        String b;
    }

    /* loaded from: classes3.dex */
    static class q {
        @SerializedName("bssid")
        String a;
        @SerializedName("rssi")
        int b;
        @SerializedName("channel")
        int c;
    }

    /* loaded from: classes3.dex */
    static class s extends d {
        @SerializedName("params")
        t a;
    }

    /* loaded from: classes3.dex */
    public static class WifiConfStatus extends d {
        @SerializedName("params")
        public int params;

        public WifiConfStatus() {
            this.method = OtDefs.INTERNAL_RES_WIFI_CONF_STATUS;
        }
    }

    /* loaded from: classes3.dex */
    public static class WifiScanRequest extends d {
        @SerializedName("params")
        public WifiScanRequestParams params;

        public WifiScanRequest() {
            this.method = "_internal.wifi_scan_req";
        }
    }

    /* loaded from: classes3.dex */
    static class i extends d {
        @SerializedName("params")
        j a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public i() {
            this.method = "_internal.wifi_scan_resp";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class t {
        @SerializedName("router_configured")
        int a;
        @SerializedName("ssid")
        String b;
        @SerializedName("passwd")
        String c;
        @SerializedName("bssid")
        String d;
        @SerializedName("ssid_5g")
        String e;
        @SerializedName("passwd_5g")
        String f;
        @SerializedName(OneTrack.Param.UID)
        String g;

        public String toString() {
            return "WifiStartConnectParams{routerConfigured=" + this.a + ", uid='" + this.g + "', ssid='" + this.b + "', password='" + this.c + "', bssid='" + this.d + "', ssid5G='" + this.e + "', password5G='" + this.f + "'}";
        }
    }

    /* loaded from: classes3.dex */
    static class k extends d {
        @SerializedName("params")
        l a;

        private k() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public k(boolean z) {
            this.method = z ? "_internal.wifi_connected" : "_internal.wifi_connect_failed";
        }
    }

    /* loaded from: classes3.dex */
    static class m extends d {
        @SerializedName("params")
        n a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public m() {
            this.method = "_internal.wifi_disconnected_event";
        }
    }

    /* loaded from: classes3.dex */
    static class o extends d {
        @SerializedName("params")
        n a;

        o() {
            this.method = OtDefs.INTERNAL_WIFI_DISCONNECT_REQ;
        }
    }

    /* loaded from: classes3.dex */
    static class p extends d {
        @SerializedName("params")
        n a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public p() {
            this.method = OtDefs.INTERNAL_WIFI_DISCONNECT_RESP;
        }
    }

    /* loaded from: classes3.dex */
    static class a extends d {
        @SerializedName("id")
        int a;
        @SerializedName("params")
        b b;

        a() {
            this.method = OtDefs.INTERNAL_AUTH_LOGIN;
        }
    }

    /* loaded from: classes3.dex */
    static class r extends d {
        /* JADX INFO: Access modifiers changed from: package-private */
        public r() {
            this.method = OtDefs.INTERNAL_WIFI_RECONNECTED_FAILED_EVENT;
        }
    }

    /* loaded from: classes3.dex */
    static class e extends d {
        @SerializedName("params")
        f a;

        e() {
            this.method = OtDefs.LOCAL_MSC_SIGN_REQ;
        }
    }

    /* loaded from: classes3.dex */
    static class g extends d {
        @SerializedName("params")
        h a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public g() {
            this.method = OtDefs.LOCAL_MSC_SIGN_RESP;
        }
    }

    /* loaded from: classes3.dex */
    static class h {
        @SerializedName("sign_type")
        String a;
        @SerializedName("sign")
        String b;

        public h(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    /* loaded from: classes3.dex */
    public static class IRData extends d {
        @SerializedName("id")
        int a;
        @SerializedName("params")
        public IRParams params;

        IRData() {
            this.method = OtDefs.MIIO_IR_PLAY;
        }
    }
}
