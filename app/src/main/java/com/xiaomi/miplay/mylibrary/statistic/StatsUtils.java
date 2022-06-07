package com.xiaomi.miplay.mylibrary.statistic;

import android.text.TextUtils;
import android.util.SparseArray;
import com.xiaomi.miplay.mylibrary.DeviceManager;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.miplay.mylibrary.mirror.CmdSessionControl;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.mylibrary.utils.MiuiSdk;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class StatsUtils {
    public static final String ACTION_ACTIVE_MIPLAY = "active_miplay";
    public static final String ACTION_CONNECT = "connect";
    public static final String ACTION_CONNECT_TIME = "connect_time";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_SCAN_TIME = "scan_time";
    public static final String ACTION_SOUND_TIME = "sound_time";
    public static final int CONNECT_TIME = 1;
    public static final int SCAN_TIME = 0;
    public static final int SOUND_TIME = 2;
    private static StatsUtils b;
    private final Object a = new Object();
    private String d = "";
    private Map<String, String> e = new ConcurrentHashMap();
    private Map<String, String> f = new ConcurrentHashMap();
    private Map<String, String> g = new ConcurrentHashMap();
    private SparseArray<Long> c = new SparseArray<>();

    public static StatsUtils getInstance() {
        if (b == null) {
            synchronized (StatsUtils.class) {
                if (b == null) {
                    b = new StatsUtils();
                }
            }
        }
        return b;
    }

    StatsUtils() {
    }

    public void startTimer(int i) {
        synchronized (this.a) {
            Logger.i("MiPlayAudioStatUtils", "startTimer : " + i, new Object[0]);
            this.c.put(i, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public void stopTimer(int i, int i2, DeviceManager deviceManager) {
        synchronized (this.a) {
            long longValue = this.c.get(i, 0L).longValue();
            if (longValue != 0) {
                Logger.i("MiPlayAudioStatUtils", "stopTimer : " + i, new Object[0]);
                long currentTimeMillis = System.currentTimeMillis() - longValue;
                Logger.i("MiPlayAudioStatUtils", "stop = " + currentTimeMillis, new Object[0]);
                this.c.remove(i);
                try {
                    HashMap hashMap = new HashMap();
                    String str = "";
                    if (i == 0) {
                        str = ACTION_SCAN_TIME;
                        hashMap.put("duration", Long.valueOf(currentTimeMillis));
                        if (deviceManager != null) {
                            hashMap.put("device_model", deviceManager.getLocalDeviceModel());
                        }
                    } else if (i == 1) {
                        str = ACTION_CONNECT_TIME;
                        hashMap.put("duration", Long.valueOf(currentTimeMillis));
                    } else if (i == 2) {
                        str = ACTION_SOUND_TIME;
                        hashMap.put("ref_device", b(i2));
                        hashMap.put("duration", Long.valueOf(currentTimeMillis));
                        hashMap.put("connect_type", Integer.valueOf(a(i2)));
                        if (deviceManager != null) {
                            hashMap.put("device_model", deviceManager.getLocalDeviceModel());
                        }
                    }
                    OneTrackStatistics.track(str, hashMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, String> getRef_channelMap() {
        return this.e;
    }

    public Map<String, String> getRef_functionMap() {
        return this.f;
    }

    public Map<String, String> getRef_contentMap() {
        return this.g;
    }

    public byte[] ontrackDataToJson(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("ref_channel", str);
            jSONObject.putOpt("ref_function", str2);
            jSONObject.putOpt(OneTrackWorldUrl.ACTION_REF_CONTENT, str3);
            String jSONObject2 = jSONObject.toString();
            Logger.i("MiPlayAudioStatUtils", jSONObject2, new Object[0]);
            return jSONObject2.getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            Logger.e("MiPlayAudioStatUtils", "ontrackDataToJson", e);
            return null;
        }
    }

    public void setPlaySource(DeviceManager deviceManager, Map<String, CmdSessionControl> map) {
        Logger.i("MiPlayAudioStatUtils", "setPlaySource.", new Object[0]);
        for (MiDevice miDevice : deviceManager.getMiDeviceList()) {
            if (map.get(miDevice.getMac()) == null) {
                Logger.i("MiPlayAudioStatUtils", "cmdSessionControlMap on a null object", new Object[0]);
            } else {
                String str = "";
                String str2 = "";
                String str3 = "";
                if (getInstance().getRef_contentMap() != null) {
                    str = getInstance().getRef_contentMap().get(OneTrackWorldUrl.ACTION_REF_CONTENT);
                }
                if (getInstance().getRef_channelMap() != null) {
                    str2 = getInstance().getRef_channelMap().get("ref_channel");
                }
                if (getInstance().getRef_functionMap() != null) {
                    str3 = getInstance().getRef_functionMap().get("ref_function");
                }
                map.get(miDevice.getMac()).setPlaySource(getInstance().ontrackDataToJson(str2, str3, str));
            }
        }
    }

    public static boolean packageNameEquals(String str, String str2) {
        Logger.i("MiPlayAudioStatUtils", "packageNameEquals.", new Object[0]);
        return TextUtils.equals(str, str2);
    }

    public String getRecordPackageName() {
        Logger.i("MiPlayAudioStatUtils", "recordPackageName:" + this.d, new Object[0]);
        return this.d;
    }

    public void setRecordPackageName(String str) {
        Logger.i("MiPlayAudioStatUtils", "recordPackageName:" + str, new Object[0]);
        this.d = str;
    }

    public void activeMiplayStats(String str, int i, DeviceManager deviceManager) {
        Logger.i("MiPlayAudioStatUtils", "activeMiplayStats." + str + " ," + deviceManager.getConnectedMiDeviceList().size() + " ," + i, new Object[0]);
        HashMap hashMap = new HashMap();
        String str2 = "phone";
        if (MiuiSdk.isTablet()) {
            str2 = "pad";
        }
        hashMap.put("ref_device", str2);
        String str3 = "";
        if (getInstance().getRef_channelMap() != null) {
            str3 = getInstance().getRef_channelMap().get("ref_channel");
        }
        hashMap.put("ref_channel", str3);
        Logger.i("MiPlayAudioStatUtils", "ref_channel:" + str3, new Object[0]);
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("ref_function", "stereo");
            getInstance().getRef_functionMap().put("ref_function", "stereo");
            Logger.i("MiPlayAudioStatUtils", "ref_function:stereo", new Object[0]);
        } else if (deviceManager.getConnectedMiDeviceList().size() > 0) {
            hashMap.put("ref_function", "multi_room");
            Logger.i("MiPlayAudioStatUtils", "ref_function:multi_room", new Object[0]);
            getInstance().getRef_functionMap().put("ref_function", "multi_room");
        } else {
            Logger.i("MiPlayAudioStatUtils", "ref_function:single_room", new Object[0]);
            hashMap.put("ref_function", "single_room");
            getInstance().getRef_functionMap().put("ref_function", "single_room");
        }
        hashMap.put("connect_type", Integer.valueOf(a(i)));
        if (deviceManager != null) {
            hashMap.put("device_model", deviceManager.getLocalDeviceModel());
        }
        Logger.i("MiPlayAudioStatUtils", "active_miplay:" + hashMap.size(), new Object[0]);
        OneTrackStatistics.track(ACTION_ACTIVE_MIPLAY, hashMap);
    }

    private int a(int i) {
        if (MiuiSdk.isTablet()) {
            if (i == 4) {
                return 3;
            }
            if (i == 2) {
                return 4;
            }
            return i == 16 ? 5 : -1;
        } else if (i == 4) {
            return 0;
        } else {
            if (i == 2) {
                return 1;
            }
            return i == 16 ? 2 : -1;
        }
    }

    public void connectStats(String str, int i, boolean z, int i2, DeviceManager deviceManager) {
        String str2 = "phone";
        if (MiuiSdk.isTablet()) {
            str2 = "pad";
        }
        HashMap hashMap = new HashMap();
        hashMap.put("command", str);
        if (z) {
            hashMap.put("err_code", Integer.valueOf(i));
        }
        hashMap.put("ref_device", str2);
        hashMap.put("connect_type", Integer.valueOf(a(i2)));
        if (deviceManager != null) {
            hashMap.put("device_model", deviceManager.getLocalDeviceModel());
        }
        String str3 = "";
        if (getInstance().getRef_channelMap() != null) {
            str3 = getInstance().getRef_channelMap().get("ref_channel");
        }
        hashMap.put("ref_channel", str3);
        Logger.i("MiPlayAudioStatUtils", "ref_channel:" + str3, new Object[0]);
        OneTrackStatistics.track("connect", hashMap);
    }

    private String b(int i) {
        String str = "speaker";
        if (i == 2) {
            str = "tv";
        } else if (i == 4) {
            str = "speaker";
        } else if (i == 16) {
            str = "screensoundbox";
        }
        Logger.i("MiPlayAudioStatUtils", "getSoundDevice:" + str, new Object[0]);
        return str;
    }

    public void setRefChannel(int i) {
        if (i == 0) {
            getInstance().getRef_channelMap().put("ref_channel", "controlcenter");
        } else if (i == 1) {
            getInstance().getRef_channelMap().put("ref_channel", "nearfield");
        } else if (i == 2) {
            getInstance().getRef_channelMap().put("ref_channel", "xiaoai_phone");
        } else if (i == 3) {
            getInstance().getRef_channelMap().put("ref_channel", "farfield");
        } else if (i == 4) {
            getInstance().getRef_channelMap().put("ref_channel", "lockscreen");
        } else if (i == 5) {
            getInstance().getRef_channelMap().put("ref_channel", "notification");
        } else if (i == 6) {
            getInstance().getRef_channelMap().put("ref_channel", "playpage");
        } else if (i == 7) {
            getInstance().getRef_channelMap().put("ref_channel", "world");
        }
    }

    public void setRefContent(String str) {
        if (str.equals(Constant.MUSIC_MIUI)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "music_miui");
        } else if (str.equals(Constant.MUSIC_WANGYIYUN)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "music_wangyiyun");
        } else if (str.equals(Constant.MUSIC_QQ)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "music_qq");
        } else if (str.equals(Constant.MUSIC_KUGOU)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "music_kugou");
        } else if (str.equals(Constant.MUSIC_KUWO)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "music_kuwo");
        } else if (str.equals(Constant.FM_HIMALAYA)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "fm_himalaya");
        } else if (str.equals(Constant.FM_QINGTING)) {
            getInstance().getRef_contentMap().put(OneTrackWorldUrl.ACTION_REF_CONTENT, "fm_qingting");
        }
    }
}
