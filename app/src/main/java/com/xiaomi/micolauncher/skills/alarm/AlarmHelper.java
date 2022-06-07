package com.xiaomi.micolauncher.skills.alarm;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRing;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmListActivity;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmListBigScreenActivity;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class AlarmHelper {
    public static final String RING_TYPE_FUN = "趣味";
    public static final String RING_TYPE_MUSIC = "音乐";
    public static final String RING_TYPE_NATURE = "自然";
    public static final String RING_TYPE_SYSTEM = "系统铃声";
    public static final String RING_TYPE_VIDEO = "视频";
    private static List<AlarmRing> b;
    private static HashMap<String, String> a = new HashMap<>();
    private static final Map<String, String> c = new HashMap<String, String>() { // from class: com.xiaomi.micolauncher.skills.alarm.AlarmHelper.1
        {
            put("music.default", AlarmHelper.RING_TYPE_MUSIC);
            put("nature.default", AlarmHelper.RING_TYPE_NATURE);
            put("fun.default", AlarmHelper.RING_TYPE_FUN);
            put("video.default", AlarmHelper.RING_TYPE_VIDEO);
            put("songlist.specific", AlarmHelper.RING_TYPE_MUSIC);
            put("songlist.general", AlarmHelper.RING_TYPE_MUSIC);
            put("songlist.album", AlarmHelper.RING_TYPE_MUSIC);
            put("songlist.artist", AlarmHelper.RING_TYPE_MUSIC);
            put("songlist.combination", AlarmHelper.RING_TYPE_MUSIC);
            put("music.general", AlarmHelper.RING_TYPE_MUSIC);
            put("music.song", AlarmHelper.RING_TYPE_MUSIC);
            put("music.tag", AlarmHelper.RING_TYPE_MUSIC);
            put("voice.nature", AlarmHelper.RING_TYPE_NATURE);
            put("voice.white_noise", AlarmHelper.RING_TYPE_NATURE);
            put("voice.animal", AlarmHelper.RING_TYPE_FUN);
            put("voice.character_voice", AlarmHelper.RING_TYPE_FUN);
            put("voice.simulator", AlarmHelper.RING_TYPE_FUN);
            put("voice.type", AlarmHelper.RING_TYPE_FUN);
            put("voice.action", AlarmHelper.RING_TYPE_FUN);
            put("voice.language", AlarmHelper.RING_TYPE_FUN);
            put("voice.voice_action", AlarmHelper.RING_TYPE_FUN);
            put("voice.foreign_language", AlarmHelper.RING_TYPE_FUN);
            put("voice.other", AlarmHelper.RING_TYPE_FUN);
        }
    };

    public static String getCircleString(Context context, AlarmRealmObjectBean alarmRealmObjectBean) {
        if (alarmRealmObjectBean == null || context == null) {
            return "";
        }
        String circle = alarmRealmObjectBean.getCircle();
        char c2 = 65535;
        switch (circle.hashCode()) {
            case -734561654:
                if (circle.equals(Circle.YEARLY)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -318797514:
                if (circle.equals(Circle.MONTOFRI)) {
                    c2 = 3;
                    break;
                }
                break;
            case 3415681:
                if (circle.equals(Circle.ONCE)) {
                    c2 = 0;
                    break;
                }
                break;
            case 151588239:
                if (circle.equals(Circle.EVERYWEEK)) {
                    c2 = 6;
                    break;
                }
                break;
            case 281966241:
                if (circle.equals(Circle.EVERYDAY)) {
                    c2 = 1;
                    break;
                }
                break;
            case 1091905624:
                if (circle.equals(Circle.HOLIDAY)) {
                    c2 = 4;
                    break;
                }
                break;
            case 1226863719:
                if (circle.equals(Circle.WEEKEND)) {
                    c2 = 5;
                    break;
                }
                break;
            case 1236635661:
                if (circle.equals(Circle.MONTHLY)) {
                    c2 = 7;
                    break;
                }
                break;
            case 1525159659:
                if (circle.equals(Circle.WORKDAY)) {
                    c2 = 2;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return context.getResources().getString(R.string.alarm_circle_type_once);
            case 1:
                return context.getResources().getString(R.string.alarm_circle_type_everyday);
            case 2:
                return context.getResources().getString(R.string.alarm_circle_type_workday);
            case 3:
                return context.getResources().getString(R.string.alarm_circle_type_montofri);
            case 4:
                return context.getResources().getString(R.string.alarm_circle_type_holiday);
            case 5:
                return context.getResources().getString(R.string.alarm_circle_type_weekend);
            case 6:
                String circleExtra = alarmRealmObjectBean.getCircleExtra();
                if (circleExtra == null || circleExtra.length() <= 0) {
                    return context.getResources().getString(R.string.alarm_circle_type_everyweek);
                }
                return AlarmRealmObjectBean.getCircleExtraCn(circleExtra);
            case 7:
                return context.getResources().getString(R.string.alarm_circle_type_monthly);
            case '\b':
                return context.getResources().getString(R.string.alarm_circle_type_yearly);
            default:
                return "";
        }
    }

    private static String a(String str) {
        String str2;
        if (!TextUtils.isEmpty(str)) {
            str2 = a.get(str);
            if (TextUtils.isEmpty(str2)) {
                str2 = c.get(str);
            }
        } else {
            str2 = null;
        }
        return TextUtils.isEmpty(str2) ? RING_TYPE_MUSIC : str2;
    }

    public static void loadConfig() {
        if (a.isEmpty()) {
            ApiManager.minaService.loadConfig("alarm_name_id_map").subscribe(new Consumer<String>() { // from class: com.xiaomi.micolauncher.skills.alarm.AlarmHelper.2
                /* renamed from: a */
                public void accept(String str) throws Exception {
                    HashMap unused = AlarmHelper.a = (HashMap) JSONObject.parseObject(str, HashMap.class);
                }
            }, $$Lambda$AlarmHelper$hjzrBqBhqXDQz5_BDu9PSXAOzk.INSTANCE);
        }
        if (!ContainerUtil.isEmpty(b)) {
            ApiManager.minaService.loadConfig("alarm_query_map").subscribe(new Consumer<String>() { // from class: com.xiaomi.micolauncher.skills.alarm.AlarmHelper.3
                /* renamed from: a */
                public void accept(String str) throws Exception {
                    if (!TextUtils.isEmpty(str)) {
                        List unused = AlarmHelper.b = (List) Gsons.getGson().fromJson(str, new TypeToken<List<AlarmRing>>() { // from class: com.xiaomi.micolauncher.skills.alarm.AlarmHelper.3.1
                        }.getType());
                    }
                }
            }, new Consumer<Throwable>() { // from class: com.xiaomi.micolauncher.skills.alarm.AlarmHelper.4
                /* renamed from: a */
                public void accept(Throwable th) throws Exception {
                    L.base.d("load alarm_name_id_map config error");
                }
            });
        }
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.base.d("load alarm_name_id_map config error");
    }

    public static void showAlarmList(Context context, String str) {
        Intent intent;
        if (Hardware.isBigScreen()) {
            intent = new Intent(context, AlarmListBigScreenActivity.class);
        } else {
            intent = new Intent(context, AlarmListActivity.class);
        }
        intent.putExtra(BaseEventActivity.KEY_DIALOG_ID, str);
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }

    public static boolean isFunRingtone(String str) {
        return RING_TYPE_FUN.equals(a(str));
    }

    public static boolean isNatureRingtone(String str) {
        return RING_TYPE_NATURE.equals(a(str));
    }

    public static boolean isVideoRingtone(String str) {
        return !ContainerUtil.isEmpty(str) && str.startsWith("video");
    }

    public static boolean isMusicRingtone(String str) {
        return !ContainerUtil.isEmpty(str) && str.startsWith("music");
    }

    public static boolean isSongListRingtone(String str) {
        return !ContainerUtil.isEmpty(str) && str.startsWith("songlist");
    }
}
