package com.xiaomi.ai.core;

import android.support.v4.media.session.PlaybackStateCompat;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.ai.api.Common;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class AivsConfig {
    public static final String ENV = "aivs.env";
    public static final int ENV_PREVIEW = 1;
    public static final int ENV_PREVIEW4TEST = 3;
    public static final int ENV_PRODUCTION = 0;
    public static final int ENV_STAGING = 2;
    private Map<String, Object> a = new HashMap();

    /* loaded from: classes3.dex */
    public static final class Asr {
        public static final String BITRATE = "asr.bitrate";
        public static final String BITS = "asr.bits";
        public static final String CHANNEL = "asr.channel";
        public static final String CODEC = "asr.codec";
        public static final String CODEC_BV32_FLOAT = "BV32_FLOAT";
        public static final String CODEC_OPUS = "OPUS";
        public static final String CODEC_PCM = "PCM";
        public static final String CODEC_SOUNDAI = "PCM_SOUNDAI";
        public static final String ENABLE_NEW_VAD = "asr.enable_new_vad";
        public static final String ENABLE_PARTIAL_RESULT = "asr.enable_partial_result";
        public static final String ENABLE_SMART_VOLUME = "asr.enable_smart_volume";
        public static final String ENABLE_TIMEOUT = "asr.enable_timeout";
        public static final String ENCODED_BY_CLIENT = "asr.encoded_by_client";
        public static final String LANG = "asr.lang";
        public static final String MAX_AUDIO_SECONDS = "asr.max_audio_seconds";
        public static final String MAX_LENGTH_RESET = "asr.max_length_reset";
        public static final String MAX_VOICE = "asr.maxvoice";
        public static final String MIN_SIL = "asr.minsil";
        public static final String MIN_VOICE = "asr.minvoice";
        public static final String OPUS_BITRATE = "asr.opus.bitrate";
        public static final int OPUS_BITRATE_32K = 32000;
        public static final int OPUS_BITRATE_64K = 64000;
        public static final String RECV_TIMEOUT = "asr.recv_timeout";
        public static final String REMOVE_END_PUNCTUATION = "asr.remove_end_punctuation";
        public static final String VAD_TYPE = "asr.vad_type";
        public static final int VAD_TYPE_CLOUD = 0;
        public static final int VAD_TYPE_LOCAL = 1;
        public static final int VAD_TYPE_NONE = 2;
        public static final String VENDOR = "asr.vendor";

        private Asr() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Auth {
        public static final String CLIENT_ID = "auth.client_id";
        public static final String DEVICE_ID_USE_IMEI = "auth.device.id.use.imei";
        public static final int REQ_TOKEN_BY_CLIENT = 1;
        public static final int REQ_TOKEN_BY_SDK = 0;
        public static final int REQ_TOKEN_HYBRID = 2;
        public static final String REQ_TOKEN_MODE = "auth.req_token_mode";
        public static final String SUPPORT_MULTIPLY_CLIENT_ID = "auth.support_multiply_client_id";
        public static final String USER_ID = "user_id";

        /* loaded from: classes3.dex */
        public static final class Anonymous {
            public static final String API_KEY = "auth.anonymous.api_key";
            public static final String DEVICE_NAME = "auth.anonymous.device_name";
            public static final String SIGN_SECRET = "auth.anonymous.sign_secret";

            private Anonymous() {
            }
        }

        /* loaded from: classes3.dex */
        public static final class DeviceToken {
            public static final String SIGN = "auth.device_token.sign";

            private DeviceToken() {
            }
        }

        /* loaded from: classes3.dex */
        public static final class OAuth {
            public static final String CLIENT_SECRET = "auth.oauth.client_secret";
            public static final String ENABLE_UPLOAD_MIOT_DID = "auth.oauth.upload_miot_did";
            public static final String REDIRECT_URL = "auth.oauth.redirect_url";

            private OAuth() {
            }
        }

        /* loaded from: classes3.dex */
        public static final class ServerAuth {
            public static final String KEY = "auth.server_auth.key";
            public static final String SECRET = "auth.server_auth.secret";

            private ServerAuth() {
            }
        }

        private Auth() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Connection {
        public static final int CHANNEL_HYBRID = 2;
        public static final String CHANNEL_TYPE = "connection.channel_type";
        public static final int CHANNEL_WEB_SOCKET = 0;
        public static final int CHANNEL_XMD = 1;
        public static final String CONNECT_TIMEOUT = "connection.connect_timeout";
        public static final String DNS_FAIL_COUNT = "connection.dns_fail_count";
        public static final String DNS_FAIL_TIME = "connection.dns_fail_time";
        public static final int DO_NOT_KEEP_ALIVE = 2;
        public static final String ENABLE_ABROAD_URL = "connection.enable_abroad_url";
        public static final String ENABLE_CLOUD_CONTROL = "connection.enable_cloud_control";
        public static final String ENABLE_HORSE_RACE = "connection.enable_horse_race";
        public static final String ENABLE_HTTP_DNS = "connection.enable_http_dns";
        public static final String ENABLE_INSTRUCTION_ACK = "connection.enable_instruction_ack";
        public static final String ENABLE_IPV6_HTTP_DNS = "connection.enable_ipv6_http_dns";
        public static final String ENABLE_LITE_CRYPT = "connection.enable_lite_crypt";
        public static final String ENABLE_REFRESH_TOKEN_AHEAD = "connection.enable_refresh_token_ahead";
        public static final String ENABLE_REFRESH_TOKEN_LIMIT = "connection.enable_refresh_token_limit";
        public static final String EXTERNAL_CONNECT_URL = "connection.external_connect_url";
        public static final String HORSE_RACE_INTERVAL = "connection.horse_race_interval";
        public static final String HORSE_RACE_TIMEOUT = "connection.horse_race_timeout";
        public static final String HTTP_DNS_EXPIRE_IN = "connection.http_dns_expire_in";
        public static final int KEEP_ALIVE_FOREVER = 0;
        public static final int KEEP_ALIVE_SHORTLY = 1;
        public static final String KEEP_ALIVE_TYPE = "connection.keep_alive_type";
        public static final String MAX_KEEP_ALIVE_TIME = "connection.max_keep_alive_time";
        public static final String MAX_RECONNECT_INTERVAL = "connection.max_reconnect_interval";
        public static final String MAX_REFRESH_TIMES_DURING_LIMIT = "connection.max_refresh_times_during_limit";
        public static final String NET_AVAILABLE_WAIT_TIME = "connection.net_available_wait_time";
        public static final String PING_INTERVAL = "connection.ping_interval";
        public static final String QUIT_IF_NEW_TOKEN_INVALID = "connection.quit_if_new_token_invalid";
        public static final String REFRESH_HTTP_DNS_INTERVAL = "connection.refresh_http_dns_interval";
        public static final String REFRESH_TOKEN_MIN_INTERVAL = "connection.refresh_token_min_interval";
        public static final String TCP_HORSE_NUM = "connection.tcp_horse_num";
        public static final String TRY_AGAIN_THRESHOLD = "connection.try_again_threshold";
        public static final String USER_AGENT = "connection.user_agent";
        public static final String XMD_BINARY_RESEND_COUNT = "connection.xmd_binary_resend_count";
        public static final String XMD_CONN_RESEND_COUNT = "connection.xmd_conn_resend_count";
        public static final String XMD_CONN_RESEND_DELAY = "connection.xmd_conn_resend_delay";
        public static final String XMD_ENABLE_MTU_DETECT = "connection.xmd_enable_mtu_detect";
        public static final String XMD_EVENT_RESEND_COUNT = "connection.xmd_event_resend_count";
        public static final String XMD_PING_INTERVAL = "connection.xmd_ping_interval";
        public static final String XMD_RESEND_DELAY = "connection.xmd_resend_delay";
        public static final String XMD_SLICE_SIZE = "connection.xmd_slice_size";
        public static final String XMD_STREAM_WAIT_TIME = "connection.xmd_stream_wait_time";
        public static final String XMD_WS_EXPIRE_IN = "connection.xmd_ws_expire_in";

        private Connection() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class ContinuousDialog {
        public static final String ENABLE_TIMEOUT = "continuousdialog.enable_timeout";
        public static final String HEAD_TIMEOUT = "continuousdialog.head_timeout";
        public static final String MAX_CACHE_SIZE = "continuousdialog.max_cache_size";
        public static final String MAX_SEGMENT_NUM = "continuousdialog.max_segment_num";
        public static final String PAUSE_TIMEOUT = "continuousdialog.pause_timeout";

        private ContinuousDialog() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class GeneralTrack {
        public static final String CACHE_PERIOD_CHECK_INTERVAL = "general_track.period_check_interval";
        public static final String DISK_PERIOD_CHECK_INTERVAL = "general_track.disk_period_check_interval";
        public static final String MAX_LOCAL_TRACK_LENGTH = "general_track.max_local_track_length";
        public static final String MAX_TRACK_DATA_SIZE = "general_track.max_track_data_size";
        public static final String MAX_TRACK_TIMES = "general_track.max_track_times";

        private GeneralTrack() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class InternalLogUpload {
        public static final String CACHE_PERIOD_CHECK_INTERVAL = "logupload.period_check_interval";
        public static final String DISK_PERIOD_CHECK_INTERVAL = "logupload.disk_period_check_interval";
        public static final String ENABLE = "logupload.enable";
        public static final String MAX_DATA_TRACK_TIMES = "logupload.max_data_track_times";
        public static final String MAX_ENTRANCE_TRACK_TIMES = "logupload.max_entrance_track_times";
        public static final String MAX_LOCAL_TRACK_LENGTH = "logupload.max_local_track_length";
        public static final String MAX_TRACK_DATA_SIZE = "logupload.max_track_data_size";

        private InternalLogUpload() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class LimitedDiskCache {
        public static final String ENABLE = "LimitedDiskCache.enable";
        public static final String MAX_DISK_SAVE_TIMES = "LimitedDiskCache.max_disk_save_times";

        private LimitedDiskCache() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Locale {
        public static final String LANGS = "locale.langs";
        public static final String LOCATION = "locale.location";
        public static final String REGION = "locale.region";

        private Locale() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class PreAsr {
        public static final String PRE_ASR_TRACK = "pre.asr.track";

        private PreAsr() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Push {
        public static final String MI_PUSH_REGID = "push.mi_push_regid";
        public static final String UMENG_PUSH_DEVICE_TOKEN = "push.umeng_push_device_token";

        private Push() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Track {
        public static final String CACHE_PERIOD_CHECK_INTERVAL = "track.cache_period_check_interval";
        public static final String DEVICE = "track.device";
        public static final String DISK_PERIOD_CHECK_INTERVAL = "track.disk_period_check_interval";
        public static final String ENABLE = "track.enable";
        public static final String MAX_LOCAL_TRACK_LENGTH = "track.max_local_track_length";
        public static final String MAX_TRACK_DATA_SIZE = "track.max_track_data_size";
        public static final String MAX_TRACK_INTERNAL_DATA_SIZE = "track.max_track_internal_data_size";
        public static final String MAX_TRACK_TIMES = "track.max_track_times";
        public static final String MAX_WAIT_TIME = "track.max_wait_time";

        private Track() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class Tts {
        public static final String AUDIO_SPEAKER = "tts.audio_speaker";
        public static final String AUDIO_TYPE = "tts.audio_type";
        public static final String AUDIO_TYPE_STREAM = "stream";
        public static final String AUDIO_TYPE_URL = "url";
        public static final String AUDIO_VENDOR = "tts.audio_vendor";
        public static final String CODEC = "tts.codec";
        public static final String CODEC_MP3 = "MP3";
        public static final String ENABLE_INTERNAL_PLAYER = "tts.enable_internal_player";
        public static final String ENABLE_PLAY_DIALOG_ID = "tts.enable_play_dialog_id";
        public static final String ENABLE_PLAY_FINISH_DIALOG_ID = "tts.enable_play_finish_dialog_id";
        public static final String LANG = "tts.lang";
        public static final String RATE = "tts.rate";
        public static final String RECV_TIMEOUT = "tts.recv_timeout";
        public static final String SPEED = "tts.speed";
        public static final String TONE = "tts.tone";
        public static final String VENDOR = "tts.vendor";
        public static final String VOLUME = "tts.volume";

        private Tts() {
        }
    }

    public AivsConfig() {
        putInt(ENV, 0);
        putInt(Auth.REQ_TOKEN_MODE, 0);
        putBoolean(Auth.SUPPORT_MULTIPLY_CLIENT_ID, false);
        putInt(Connection.CONNECT_TIMEOUT, 15);
        putInt(Connection.MAX_RECONNECT_INTERVAL, 1800);
        putInt(Connection.HTTP_DNS_EXPIRE_IN, 604800);
        putInt(Connection.REFRESH_HTTP_DNS_INTERVAL, 30);
        putInt(Connection.KEEP_ALIVE_TYPE, 1);
        putInt(Connection.MAX_KEEP_ALIVE_TIME, 900);
        putInt(Connection.PING_INTERVAL, 90);
        putInt(Connection.XMD_PING_INTERVAL, 30);
        putBoolean(Connection.QUIT_IF_NEW_TOKEN_INVALID, false);
        putBoolean(Connection.ENABLE_HTTP_DNS, true);
        putBoolean(Connection.ENABLE_ABROAD_URL, false);
        putBoolean(Connection.ENABLE_INSTRUCTION_ACK, true);
        putBoolean(Connection.ENABLE_REFRESH_TOKEN_LIMIT, true);
        putInt(Connection.REFRESH_TOKEN_MIN_INTERVAL, 10);
        putInt(Connection.MAX_REFRESH_TIMES_DURING_LIMIT, 3);
        putBoolean(Connection.ENABLE_REFRESH_TOKEN_AHEAD, true);
        putBoolean(Connection.ENABLE_IPV6_HTTP_DNS, false);
        putBoolean(Connection.ENABLE_CLOUD_CONTROL, true);
        putBoolean(Connection.ENABLE_HORSE_RACE, true);
        putInt(Connection.TCP_HORSE_NUM, 3);
        putInt(Connection.HORSE_RACE_TIMEOUT, 5000);
        putInt(Connection.HORSE_RACE_INTERVAL, 300);
        putInt(Connection.XMD_EVENT_RESEND_COUNT, 10);
        putInt(Connection.XMD_BINARY_RESEND_COUNT, 8);
        putInt(Connection.XMD_RESEND_DELAY, 300);
        putInt(Connection.XMD_STREAM_WAIT_TIME, 5000);
        putInt(Connection.XMD_CONN_RESEND_COUNT, 10);
        putInt(Connection.XMD_CONN_RESEND_DELAY, 200);
        putBoolean(Connection.ENABLE_LITE_CRYPT, true);
        putInt(Connection.XMD_WS_EXPIRE_IN, CacheConstants.DAY);
        putInt(Connection.NET_AVAILABLE_WAIT_TIME, 3000);
        putInt(Connection.DNS_FAIL_COUNT, 4);
        putInt(Connection.DNS_FAIL_TIME, 2000);
        putBoolean(Connection.XMD_ENABLE_MTU_DETECT, true);
        putInt(Connection.XMD_SLICE_SIZE, 1320);
        putInt(Connection.TRY_AGAIN_THRESHOLD, 3000);
        putBoolean(Auth.DEVICE_ID_USE_IMEI, true);
        putString(Asr.CODEC, Asr.CODEC_OPUS);
        putInt(Asr.BITS, 16);
        putInt(Asr.BITRATE, 16000);
        putInt(Asr.CHANNEL, 1);
        putInt(Asr.VAD_TYPE, 1);
        putBoolean(Asr.ENABLE_NEW_VAD, false);
        putInt(Asr.RECV_TIMEOUT, 6);
        putInt(Asr.MIN_VOICE, 25);
        putInt(Asr.MIN_SIL, 50);
        putInt(Asr.MAX_VOICE, 1500);
        putInt(Asr.MAX_LENGTH_RESET, 6000);
        putString(Asr.LANG, "zh-CN");
        putBoolean(Asr.ENABLE_PARTIAL_RESULT, true);
        putBoolean(Asr.REMOVE_END_PUNCTUATION, true);
        putBoolean(Asr.ENABLE_SMART_VOLUME, false);
        putString(Tts.CODEC, Tts.CODEC_MP3);
        putString(Tts.LANG, "zh-CN");
        putString(Tts.AUDIO_TYPE, Tts.AUDIO_TYPE_STREAM);
        putBoolean(Tts.ENABLE_INTERNAL_PLAYER, true);
        putInt(Tts.RECV_TIMEOUT, 5);
        putBoolean(Track.ENABLE, true);
        putInt(Track.MAX_TRACK_DATA_SIZE, 95);
        putInt(Track.MAX_TRACK_INTERNAL_DATA_SIZE, 10);
        putLong(Track.MAX_LOCAL_TRACK_LENGTH, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE);
        putInt(Track.MAX_TRACK_TIMES, 100);
        putInt(Track.MAX_WAIT_TIME, 10);
        putInt(Track.CACHE_PERIOD_CHECK_INTERVAL, 10);
        putInt(Track.DISK_PERIOD_CHECK_INTERVAL, 1200);
        putInt(ContinuousDialog.HEAD_TIMEOUT, 3);
        putInt(ContinuousDialog.PAUSE_TIMEOUT, 3);
        putInt(ContinuousDialog.MAX_CACHE_SIZE, 9600);
        putInt(ContinuousDialog.MAX_SEGMENT_NUM, 2);
        putBoolean(ContinuousDialog.ENABLE_TIMEOUT, true);
        putBoolean(InternalLogUpload.ENABLE, false);
        putInt(InternalLogUpload.MAX_TRACK_DATA_SIZE, 1000);
        putInt(InternalLogUpload.MAX_DATA_TRACK_TIMES, 100);
        putInt(InternalLogUpload.MAX_ENTRANCE_TRACK_TIMES, 300);
        putInt(InternalLogUpload.CACHE_PERIOD_CHECK_INTERVAL, 10);
        putInt(InternalLogUpload.DISK_PERIOD_CHECK_INTERVAL, 1200);
        putLong(InternalLogUpload.MAX_LOCAL_TRACK_LENGTH, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE);
        putInt(GeneralTrack.MAX_TRACK_DATA_SIZE, 45);
        putInt(GeneralTrack.MAX_TRACK_TIMES, 100);
        putInt(GeneralTrack.CACHE_PERIOD_CHECK_INTERVAL, 10);
        putInt(GeneralTrack.DISK_PERIOD_CHECK_INTERVAL, 120);
        putLong(GeneralTrack.MAX_LOCAL_TRACK_LENGTH, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE);
        putBoolean(LimitedDiskCache.ENABLE, false);
        putInt(LimitedDiskCache.MAX_DISK_SAVE_TIMES, 500);
    }

    public boolean containsKey(String str) {
        return this.a.containsKey(str);
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException unused) {
            return z;
        }
    }

    public double getDouble(String str) {
        return getDouble(str, 0.0d);
    }

    public double getDouble(String str, double d) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return d;
        }
        try {
            return ((Double) obj).doubleValue();
        } catch (ClassCastException unused) {
            return d;
        }
    }

    public Common.LocaleRegion getEnum(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Common.LocaleRegion) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public float getFloat(String str, float f) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return f;
        }
        try {
            return ((Float) obj).floatValue();
        } catch (ClassCastException unused) {
            return f;
        }
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException unused) {
            return i;
        }
    }

    public long getLong(String str) {
        return getLong(str, 0L);
    }

    public long getLong(String str, long j) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException unused) {
            return j;
        }
    }

    public String getString(String str) {
        try {
            return (String) this.a.get(str);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public String getString(String str, String str2) {
        String string = getString(str);
        return string == null ? str2 : string;
    }

    public List<String> getStringList(String str) {
        Object obj = this.a.get(str);
        if (obj == null) {
            return Collections.emptyList();
        }
        try {
            return (List) obj;
        } catch (ClassCastException unused) {
            return Collections.emptyList();
        }
    }

    public void putBoolean(String str, boolean z) {
        this.a.put(str, Boolean.valueOf(z));
    }

    public void putDouble(String str, double d) {
        this.a.put(str, Double.valueOf(d));
    }

    public void putEnum(String str, Common.LocaleRegion localeRegion) {
        this.a.put(str, localeRegion);
    }

    public void putFloat(String str, float f) {
        this.a.put(str, Float.valueOf(f));
    }

    public void putInt(String str, int i) {
        this.a.put(str, Integer.valueOf(i));
    }

    public void putLong(String str, long j) {
        this.a.put(str, Long.valueOf(j));
    }

    public void putString(String str, String str2) {
        this.a.put(str, str2);
    }

    public void putStringList(String str, List<String> list) {
        this.a.put(str, list);
    }
}
