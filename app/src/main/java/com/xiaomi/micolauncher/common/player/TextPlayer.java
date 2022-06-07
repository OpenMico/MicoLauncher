package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.player.model.PlayerModel;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.view.PlayTextActivity;

/* loaded from: classes3.dex */
public class TextPlayer {
    public static void play(final Context context, final String str, final AudioSource audioSource) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.player.-$$Lambda$TextPlayer$rxx2FWQ6sJAxcbulCvk4MJdajCg
            @Override // java.lang.Runnable
            public final void run() {
                TextPlayer.a(context, str, audioSource);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, String str, AudioSource audioSource) {
        Intent intent = new Intent(context, PlayTextActivity.class);
        intent.putExtra("KEY_TEXT_CONTENT", str);
        intent.putExtra(PlayerModel.KEY_AUDIO_SOURCE, audioSource);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
