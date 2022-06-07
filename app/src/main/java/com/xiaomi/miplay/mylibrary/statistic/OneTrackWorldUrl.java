package com.xiaomi.miplay.mylibrary.statistic;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class OneTrackWorldUrl {
    public static final String ACTION_KEY_WORLD_URL = "world_url";
    public static final String ACTION_REF_CONTENT = "ref_content";
    public static final String ACTION_RESULT = "result";
    public static final String ACTION_TARGET = "target";
    public static final String ACTION_URL_CONNECT = "connect";
    public static final String ACTION_URL_PLAY = "play";
    public static final int CONNECT_TIME = 1;
    public static final int PLAY_TIME = 0;
    private static OneTrackWorldUrl a;
    private final Object c = new Object();
    private SparseArray<Long> b = new SparseArray<>();

    public static OneTrackWorldUrl getInstance() {
        if (a == null) {
            synchronized (StatsUtils.class) {
                if (a == null) {
                    a = new OneTrackWorldUrl();
                }
            }
        }
        return a;
    }

    OneTrackWorldUrl() {
    }

    public void startTimer(int i) {
        synchronized (this.c) {
            Log.i("OneTrackWorldUrl", "startTimer : " + i);
            this.b.put(i, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public void stopTimer(int i, String str) {
        synchronized (this.c) {
            long longValue = this.b.get(i, 0L).longValue();
            if (longValue != 0) {
                int i2 = 0;
                Logger.i("OneTrackWorldUrl", "stopTimer : " + i, new Object[0]);
                long currentTimeMillis = System.currentTimeMillis() - longValue;
                Logger.i("OneTrackWorldUrl", "stop = " + currentTimeMillis, new Object[0]);
                this.b.remove(i);
                try {
                    HashMap hashMap = new HashMap();
                    if (i == 1) {
                        hashMap.put("connect", Long.valueOf(currentTimeMillis));
                    } else if (i == 0) {
                        hashMap.put("play", Long.valueOf(currentTimeMillis));
                    }
                    if (!TextUtils.isEmpty(str)) {
                        i2 = -1;
                        if (!str.equals(Constant.PACKAGENAME_GALLERY)) {
                            if (str.equals(Constant.PACKAGENAME_XIAOMI_VIDEO)) {
                                i2 = 1;
                            } else if (str.equals(Constant.PACKAGENAME_QIYI_VIDEO)) {
                                i2 = 2;
                            }
                        }
                        hashMap.put(ACTION_REF_CONTENT, Integer.valueOf(i2));
                    }
                    OneTrackStatistics.track(ACTION_KEY_WORLD_URL, hashMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void trackTarget(int i, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(ACTION_TARGET, Integer.valueOf(i));
        if (!TextUtils.isEmpty(str)) {
            int i2 = -1;
            if (str.equals(Constant.PACKAGENAME_GALLERY)) {
                i2 = 0;
            } else if (str.equals(Constant.PACKAGENAME_XIAOMI_VIDEO)) {
                i2 = 1;
            } else if (str.equals(Constant.PACKAGENAME_QIYI_VIDEO)) {
                i2 = 2;
            }
            hashMap.put(ACTION_REF_CONTENT, Integer.valueOf(i2));
        }
        OneTrackStatistics.track(ACTION_KEY_WORLD_URL, hashMap);
    }

    public void trackResult(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("result", Boolean.valueOf(z));
        OneTrackStatistics.track(ACTION_KEY_WORLD_URL, hashMap);
    }
}
