package com.xiaomi.micolauncher.module.multicp;

import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiCpRepository {
    private static MultiCpRepository a;
    private final List<VideoItem> b = new ArrayList();

    private MultiCpRepository() {
    }

    public static MultiCpRepository getSingleton() {
        if (a == null) {
            synchronized (MultiCpRepository.class) {
                if (a == null) {
                    a = new MultiCpRepository();
                }
            }
        }
        return a;
    }

    public List<VideoItem> getRecommendations() {
        return this.b;
    }

    public void setRecommendations(List<VideoItem> list) {
        this.b.clear();
        this.b.addAll(list);
        L.accessibility.d("setRecommendations():\n %s", Gsons.getGson().toJson(list));
    }

    public void clear() {
        L.accessibility.d("clear()");
        this.b.clear();
    }
}
