package com.xiaomi.micolauncher.application.activitypolicy;

import android.app.Activity;
import android.util.ArraySet;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import com.xiaomi.micolauncher.skills.video.player.VideoPlayerActivity;
import java.util.Set;

/* loaded from: classes3.dex */
public class ActivityPolicy {
    private static final ActivityPolicy b = new ActivityPolicy();
    final Set<Class<? extends Activity>> a = new ArraySet();

    private ActivityPolicy() {
        this.a.add(PlayerActivityV2.class);
        this.a.add(VideoPlayerActivity.class);
    }

    public static ActivityPolicy getInstance() {
        return b;
    }

    public boolean isLongMediaActivity(@NonNull Activity activity) {
        return this.a.contains(activity.getClass());
    }

    public boolean isHomeActivity(@NonNull Activity activity) {
        return activity instanceof Launcher;
    }
}
