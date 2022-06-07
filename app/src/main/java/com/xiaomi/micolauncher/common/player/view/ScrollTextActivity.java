package com.xiaomi.micolauncher.common.player.view;

import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.skills.baike.view.BaikeTextView;
import com.xiaomi.micolauncher.skills.joke.JokeItem;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class ScrollTextActivity extends BaseEventActivity {
    public static final String KEY_JOKES = "JOKES";
    private static final long a = TimeUnit.SECONDS.toMillis(7);
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    private Runnable c;
    private BaikeTextView d;
    private boolean e;
    private int f = 0;
    private List<JokeItem> g;
    private String h;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_baike_text);
        this.d = (BaikeTextView) findViewById(R.id.baikeTextView);
        this.c = new Runnable() { // from class: com.xiaomi.micolauncher.common.player.view.-$$Lambda$bWTRC8Xjv4SswO8tGM8fgJFcFHs
            @Override // java.lang.Runnable
            public final void run() {
                ScrollTextActivity.this.finish();
            }
        };
        this.h = getIntent().getStringExtra(BaseEventActivity.KEY_DIALOG_ID);
        this.g = getIntent().getParcelableArrayListExtra(KEY_JOKES);
        if (ContainerUtil.isEmpty(this.g) || TextUtils.isEmpty(this.h)) {
            L.skill.i("jokes is empty");
            a(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MicoMultiAudioPlayer.getInstance() != null) {
            a(MicoMultiAudioPlayer.getInstance().getAudioId(), MicoMultiAudioPlayer.getInstance().getPlayingIndex());
        }
    }

    private void a(String str) {
        a(str, -1);
    }

    private void a(String str, int i) {
        JokeItem jokeItemById = !TextUtils.isEmpty(str) ? getJokeItemById(str) : null;
        if (jokeItemById == null && i >= 0) {
            jokeItemById = getJokeItemByIndex(i);
        }
        if (jokeItemById == null) {
            L.skill.i("item is illegal");
        } else {
            this.d.updateText(jokeItemById.text);
        }
    }

    private void a(long j) {
        L.base.i("release=%s", Long.valueOf(j));
        if (!this.e) {
            if (MicoMultiAudioPlayer.getInstance() != null) {
                MicoMultiAudioPlayer.getInstance().release(this.h);
            }
            ThreadUtil.postDelayedInMainThread(this.c, j);
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        a(b);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayerStateChangeEvent(AudioPlayerStateChangeEvent audioPlayerStateChangeEvent) {
        List<JokeItem> list;
        L.player.i("ScrollTextActivity onAudioPlayerStateChangeEvent.audioState=%s", audioPlayerStateChangeEvent);
        MicoMultiAudioPlayer.AudioState audioState = audioPlayerStateChangeEvent.audioState;
        if (audioState == MicoMultiAudioPlayer.AudioState.LOAD_MORE) {
            this.h = audioPlayerStateChangeEvent.dialogId;
            if (audioPlayerStateChangeEvent.jokeItems != null && (list = this.g) != null) {
                list.addAll(audioPlayerStateChangeEvent.jokeItems);
            }
        } else if (audioPlayerStateChangeEvent.dialogId == null || !audioPlayerStateChangeEvent.dialogId.equalsIgnoreCase(this.h)) {
            L.player.i("ScrollTextActivity dialogId not equals,finishDialogId=%s", this.h);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.LIST_FINISH) {
            a(a);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.NEXT || audioState == MicoMultiAudioPlayer.AudioState.PREVIOUS) {
            a(audioPlayerStateChangeEvent.audioId);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.SUSPEND || audioState == MicoMultiAudioPlayer.AudioState.BACKGROUND) {
            a(b);
        }
    }

    public JokeItem getJokeItemById(String str) {
        if (ContainerUtil.isEmpty(this.g) || this.f >= this.g.size()) {
            return null;
        }
        for (JokeItem jokeItem : this.g) {
            if (str.equalsIgnoreCase(jokeItem.id)) {
                return jokeItem;
            }
        }
        return null;
    }

    public JokeItem getJokeItemByIndex(int i) {
        if (ContainerUtil.isOutOfBound(i, this.g)) {
            return null;
        }
        return this.g.get(i);
    }
}
