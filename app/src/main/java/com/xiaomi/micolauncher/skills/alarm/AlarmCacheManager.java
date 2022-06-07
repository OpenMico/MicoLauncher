package com.xiaomi.micolauncher.skills.alarm;

import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.utils.ThreadUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class AlarmCacheManager {
    private static volatile AlarmCacheManager a;
    private Map<String, List<Instruction>> b = new HashMap();

    private AlarmCacheManager() {
    }

    public static AlarmCacheManager getInstance() {
        if (a == null) {
            synchronized (AlarmCacheManager.class) {
                if (a == null) {
                    a = new AlarmCacheManager();
                }
            }
        }
        return a;
    }

    public List<Instruction> getMusicCacheById(String str) {
        return this.b.get(str);
    }

    public void setMusicCacheList(String str, List<Instruction> list) {
        this.b.put(str, list);
    }

    public void setMusicCache(String str, Instruction instruction) {
        ThreadUtil.verifyMainThread();
        List<Instruction> list = this.b.get(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(instruction);
        this.b.put(str, list);
    }

    public void clearMusicCache(String str) {
        this.b.remove(str);
    }
}
