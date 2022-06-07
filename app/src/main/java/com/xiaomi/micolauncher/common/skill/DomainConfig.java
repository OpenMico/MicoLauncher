package com.xiaomi.micolauncher.common.skill;

import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.elvishew.xlog.Logger;
import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.onetrack.api.b;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DomainConfig {

    /* loaded from: classes3.dex */
    public static class ClassScheduleSkill {
        public static final String DOMAIN_NAME = "classSchedule";
    }

    /* loaded from: classes3.dex */
    public static class OpenPlatform {
        public static final String DOMAIN_NAME = "openplatform";

        /* loaded from: classes3.dex */
        public enum Action {
            kid_mode_reply
        }
    }

    /* loaded from: classes3.dex */
    public static class PersonalizeSkill {
        public static final String DOMAIN_NAME = "personalize";
    }

    /* loaded from: classes3.dex */
    public static class ScenesSkill {
        public static final String DOMAIN_NAME = "scenes";
    }

    /* loaded from: classes3.dex */
    public static class Shopping {
        public static final String DOMAIN_NAME = "shopping";
    }

    /* loaded from: classes3.dex */
    public static class SmartApp {
        public static final String DOMAIN_NAME = "smartApp";
    }

    /* loaded from: classes3.dex */
    public static class Music {
        public static final String DOMAIN_NAME = "music";

        /* loaded from: classes3.dex */
        public enum Action {
            unknown,
            query,
            next,
            negative
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SongList {
        public static final String DOMAIN_NAME = "songList";

        /* loaded from: classes3.dex */
        public enum Action {
            unknown,
            playList,
            collectSong,
            cancel,
            playRadio,
            collectRadio,
            changeRadio
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SoundboxControl {
        public static final String DOMAIN_NAME = "soundboxControl";

        /* loaded from: classes3.dex */
        public enum Action {
            action_unknown,
            action_resolution,
            action_prev,
            action_pause,
            action_next,
            action_prevList,
            action_nextList,
            action_skipStart,
            action_up_volume,
            action_down_volume,
            action_stop,
            action_play,
            action_play_mv,
            action_repeat,
            action_set_volume,
            action_fastForward,
            action_rewind,
            action_select,
            action_back,
            action_update,
            action_close_microphone,
            action_returnHome,
            action_open,
            action_close,
            action_up,
            action_down,
            action_set,
            action_open_device,
            action_close_device,
            action_query_volume,
            action_switch,
            action_query_info,
            action_query_artist,
            action_query_just_said,
            action_query_last_query,
            action_open_volume,
            action_close_volume,
            action_incomplete_set_volume,
            action_open_fullduplex,
            action_close_fullduplex,
            action_discover_device,
            action_confirm_connect,
            action_pair_devices,
            action_cancel_connect,
            action_exit,
            action_query_mode,
            action_set_mode,
            action_prevPage,
            action_nextPage,
            action_not_support,
            action_pause_later,
            action_cancel_ending,
            action_pause_after_finish,
            action_lock_screen,
            action_mistake_wakeup,
            action_boot,
            action_reboot,
            action_shutdown,
            action_shutdownNow,
            action_query_brightness,
            action_seek,
            action_tone_switch,
            action_tone_switch_next,
            action_tone_switch_gender
        }

        /* loaded from: classes3.dex */
        public enum SwitcherType {
            type_unknown,
            type_bluetooth
        }

        public static String getResolution(String str) {
            return DomainConfig.b(str, "resolution");
        }

        public static long getSeekPosition(String str) {
            String b = DomainConfig.b(str, "hour");
            String b2 = DomainConfig.b(str, "minute");
            String b3 = DomainConfig.b(str, "second");
            long j = 0;
            if (b.length() > 0) {
                j = 0 + TimeUnit.HOURS.toMillis(Integer.parseInt(b));
            }
            if (b2.length() > 0) {
                j += TimeUnit.MINUTES.toMillis(Integer.parseInt(b2));
            }
            return b3.length() > 0 ? j + TimeUnit.SECONDS.toMillis(Integer.parseInt(b3)) : j;
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, "action_" + str).or((Optional) Action.action_unknown);
        }

        public static int getVolume(String str) {
            try {
                String replace = new JSONObject(str).getString(SchemaActivity.KEY_VOLUME).replace("%", "");
                if (replace.length() > 0) {
                    return Integer.parseInt(replace);
                }
                return -1;
            } catch (NumberFormatException e) {
                L.base.e("getVolume.exception=%s", e);
                return -1;
            } catch (JSONException e2) {
                L.base.e("getVolume.exception=%s", e2);
                return -1;
            }
        }

        public static int getMuteMicDuration(String str) {
            int i = 0;
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.getString("action").equals("close_microphone")) {
                    return 0;
                }
                String optString = jSONObject.optString("hour");
                String optString2 = jSONObject.optString("minute");
                if (optString != null && !optString.isEmpty()) {
                    i = Integer.parseInt(optString) * CacheConstants.HOUR;
                }
                return (optString2 == null || optString2.isEmpty()) ? i : i + (Integer.parseInt(optString2) * 60);
            } catch (JSONException e) {
                e.printStackTrace();
                return i;
            }
        }

        public static int getSwitchId(String str) {
            String string;
            String string2;
            String string3;
            try {
                JSONObject jSONObject = new JSONObject(str);
                string = jSONObject.getString("action");
                string2 = jSONObject.getString("object_type");
                string3 = jSONObject.getString("object_id");
            } catch (Exception unused) {
            }
            if (!(string == null || string2 == null || string3 == null)) {
                if ((string.equals(AbstractCircuitBreaker.PROPERTY_NAME) || string.equals("close") || string.equals("switch")) && string2.equals("SWITCH")) {
                    return Integer.parseInt(string3);
                }
                return -1;
            }
            return -1;
        }

        public static SwitcherType getSwitcherType(String str) {
            String str2 = "unknown";
            try {
                str2 = new JSONObject(str).getString("device");
                if (TextUtils.isEmpty(str2)) {
                    str2 = "unknown";
                }
            } catch (JSONException unused) {
            }
            return (SwitcherType) Enums.getIfPresent(SwitcherType.class, "type_" + str2).or((Optional) SwitcherType.type_unknown);
        }

        public static String getSwitchName(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("action");
                String string2 = jSONObject.getString("object_type");
                String string3 = jSONObject.getString("object_name");
                if (!(string == null || string2 == null || string3 == null)) {
                    return (string.equals(AbstractCircuitBreaker.PROPERTY_NAME) || string.equals("close") || string.equals("switch")) ? string2.equals("SWITCH") ? string3 : "" : "";
                }
                return "";
            } catch (JSONException unused) {
                return "";
            }
        }

        public static String getAdjustName(Context context, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("object_type");
                String string2 = jSONObject.getString("object_name");
                if (!(string == null || string2 == null)) {
                    return context.getString(R.string.brain_adjust).equals(string) ? string2 : "";
                }
                return "";
            } catch (JSONException e) {
                L.base.e("getAdjustName.exception=%s", e);
                return "";
            }
        }

        public static int getAdjustValue(String str) {
            try {
                String replace = new JSONObject(str).getString(b.p).replace("%", "");
                if (replace.length() <= 0) {
                    return -1;
                }
                if ("MAX".equals(replace)) {
                    return 100;
                }
                if ("MIN".equals(replace)) {
                    return 0;
                }
                return Integer.parseInt(replace);
            } catch (NumberFormatException e) {
                L.base.e("getAdjustValue.exception=%s", e);
                return -1;
            } catch (JSONException e2) {
                L.base.e("getAdjustValue.exception=%s", e2);
                return -1;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v6, types: [boolean] */
        public static String getLoopMode(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("action");
                String string2 = jSONObject.getString("mode");
                if (!(string == null || string2 == null)) {
                    str = "set_mode".equals(string);
                    return str != 0 ? string2 : "";
                }
                return "";
            } catch (JSONException e) {
                Logger logger = L.skill;
                logger.w("failed to get loop mode in [" + str + "]", e);
                return "";
            }
        }
    }

    /* loaded from: classes3.dex */
    public class SmartMIOT {
        public static final String DOMAIN_NAME = "smartMiot";

        public SmartMIOT() {
        }
    }

    /* loaded from: classes3.dex */
    public class Weather {
        public static final String DOMAIN_NAME = "weather";

        public Weather() {
        }
    }

    /* loaded from: classes3.dex */
    public class Alarm {
        public static final String DOMAIN_NAME = "alarm";

        public Alarm() {
        }
    }

    /* loaded from: classes3.dex */
    public class Memo {
        public static final String DOMAIN_NAME = "todolist";

        public Memo() {
        }
    }

    /* loaded from: classes3.dex */
    public class Baike {
        public static final String DOMAIN_NAME = "baike";

        public Baike() {
        }
    }

    /* loaded from: classes3.dex */
    public class Person {
        public static final String DOMAIN_NAME = "person";

        public Person() {
        }
    }

    /* loaded from: classes3.dex */
    public class Recipe {
        public static final String DOMAIN_NAME = "recipe";

        public Recipe() {
        }
    }

    /* loaded from: classes3.dex */
    public class Qabot {
        public static final String DOMAIN_NAME = "qabot";

        public Qabot() {
        }
    }

    /* loaded from: classes3.dex */
    public class AncientPoem {
        public static final String DOMAIN_NAME = "ancientPoem";

        public AncientPoem() {
        }
    }

    /* loaded from: classes3.dex */
    public class Translation {
        public static final String DOMAIN_NAME = "translation";

        public Translation() {
        }
    }

    /* loaded from: classes3.dex */
    public class MiChat {
        public static final String DOMAIN_NAME = "michat";

        public MiChat() {
        }
    }

    /* loaded from: classes3.dex */
    public class Video {
        public static final String DOMAIN_NAME = "mobileVideo";

        public Video() {
        }
    }

    /* loaded from: classes3.dex */
    public class Voice {
        public static final String DOMAIN_NAME = "voice";

        public Voice() {
        }
    }

    /* loaded from: classes3.dex */
    public class Picture {
        public static final String DOMAIN_NAME = "picture";

        public Picture() {
        }
    }

    /* loaded from: classes3.dex */
    public class Joke {
        public static final String DOMAIN_NAME = "joke";

        public Joke() {
        }
    }

    /* loaded from: classes3.dex */
    public class TouristSpot {
        public static final String DOMAIN_NAME = "touristSpot";

        public TouristSpot() {
        }
    }

    /* loaded from: classes3.dex */
    public class Mesh {
        public static final String DOMAIN_DEVICE_BINDING = "DeviceBinding";
        public static final String DOMAIN_NAME = "mesh";

        public Mesh() {
        }
    }

    /* loaded from: classes3.dex */
    public static class InternalPlatform {
        public static final String DOMAIN_NAME = "internal-platform";
        public static final String QUICKAPP_SCHEMA = "hap://";

        /* loaded from: classes3.dex */
        public enum Action {
            runApp
        }

        public static String getAppUri(String str) {
            JSONObject jSONObject;
            String string;
            String str2 = "";
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2.getString("action").equals("runApp") && (jSONObject = jSONObject2.getJSONObject("intent_info")) != null && (str2 = jSONObject.getString(MiSoundBoxCommandExtras.URI)) != null && str2.length() >= 6 && str2.substring(0, 6).equals(QUICKAPP_SCHEMA) && (string = jSONObject2.getString("query")) != null) {
                    if (str2.contains("?")) {
                        str2 = str2 + "&query=" + string;
                    } else {
                        str2 = str2 + "/?query=" + string;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return str2;
        }
    }

    /* loaded from: classes3.dex */
    public static class News {
        public static final String DOMAIN_NAME = "soundboxnews";

        /* loaded from: classes3.dex */
        public enum Action {
            query,
            next,
            unknown
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class Station {
        public static final String DOMAIN_NAME = "station";
        public static final String SUBTYPE_RADIO = "RADIO";

        /* loaded from: classes3.dex */
        public enum Action {
            query,
            next,
            again,
            unknown
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, String str2) {
        try {
            return new JSONObject(str).getString(str2);
        } catch (JSONException e) {
            Logger logger = L.base;
            logger.e(str + ",key" + str2 + ".exception=", e);
            return "";
        }
    }

    /* loaded from: classes3.dex */
    public static class VoicePrint {
        public static final String DOMAIN_NAME = "voiceprint";

        /* loaded from: classes3.dex */
        public enum Action {
            UNKNOWN,
            REGISTER_RETRY,
            REGISTER_START,
            REGISTER_SECOND,
            REGISTER_PROCESS,
            REGISTER_SUCCESS,
            REGISTER_END,
            REGISTER_FAIL
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.UNKNOWN);
        }

        public static String getNickName(String str) {
            return DomainConfig.b(str, "nickname");
        }

        public static int getRegisterCnt(String str) {
            try {
                return new JSONObject(str).optInt("registerProcessCount");
            } catch (JSONException e) {
                L.base.e("getNickName.exception=", e);
                return 0;
            }
        }

        public static int getRegisterTotalCnt(String str) {
            try {
                int optInt = new JSONObject(str).optInt("registerTotalCount");
                if (optInt <= 0) {
                    return 5;
                }
                return optInt;
            } catch (JSONException e) {
                L.base.e("registerTotalCount.exception=", e);
                return 5;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Voip {
        public static final String DOMAIN_NAME = "phonecall";

        /* loaded from: classes3.dex */
        public enum Action {
            unknown,
            PICK_UP,
            HANG_UP,
            QUERY,
            PLAY,
            STOP,
            EMPTY
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }

    /* loaded from: classes3.dex */
    public static class SoundboxControlTV {
        public static final String DOMAIN_NAME = "soundboxControlTV";

        /* loaded from: classes3.dex */
        public enum Action {
            unknown,
            open,
            close,
            query
        }

        public static Action getAction(String str) {
            return (Action) Enums.getIfPresent(Action.class, str).or((Optional) Action.unknown);
        }
    }
}
