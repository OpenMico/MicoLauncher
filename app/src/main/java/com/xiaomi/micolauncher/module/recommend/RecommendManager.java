package com.xiaomi.micolauncher.module.recommend;

import android.os.Handler;
import android.os.Message;
import com.xiaomi.micolauncher.api.model.MainScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class RecommendManager {
    private static final RecommendManager a = new RecommendManager();
    private static final long b = TimeUnit.MINUTES.toMillis(10);
    private List<MainScreen.RecommendPage> d = new ArrayList();
    private List<MainScreen.RecommendPage> e = new ArrayList();
    private final Handler c = new Handler(new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.recommend.RecommendManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            RecommendManager.this.a();
            return false;
        }
    });

    public static RecommendManager getInstance() {
        return a;
    }

    private RecommendManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.c.removeMessages(1);
    }

    public List<MainScreen.RecommendPage> getRecommendMedias() {
        return this.e;
    }

    public List<MainScreen.RecommendPage> getRecommendSkills() {
        return this.d;
    }

    public void stopSync() {
        this.c.removeCallbacksAndMessages(null);
    }

    public void startSync() {
        a();
    }
}
