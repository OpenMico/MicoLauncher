package com.xiaomi.micolauncher.skills.ancientpoem;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AncientPoemActivity extends BaseEventActivity {
    public static final String KEY_POEMS = "ANCIENT_POEMS";
    private static final long a = TimeUnit.SECONDS.toMillis(7);
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    private static final long c = TimeUnit.SECONDS.toMillis(1);
    private String d;
    private String e;
    private String f;
    private Runnable g;
    private TextView h;
    private TextView i;
    private TextView j;
    private int k;
    private ImageView l;
    private ScrollView n;
    private boolean o;
    private SparseIntArray p;
    private List<AncientPoemEntity> q;
    private Random m = new Random();
    private int r = 0;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ancientpoem);
        this.h = (TextView) findViewById(R.id.textViewAncientPoem);
        this.i = (TextView) findViewById(R.id.textViewAncientPoemTitle);
        this.j = (TextView) findViewById(R.id.textViewAncientPoemAuthor);
        this.l = (ImageView) findViewById(R.id.ancientPoembackground);
        this.n = (ScrollView) findViewById(R.id.scrollview);
        this.n.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.xiaomi.micolauncher.skills.ancientpoem.-$$Lambda$AncientPoemActivity$GuUnjwZ_N2aC3vxQ2NsS1aQ5EJo
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                AncientPoemActivity.this.a(view, i, i2, i3, i4);
            }
        });
        this.g = new Runnable() { // from class: com.xiaomi.micolauncher.skills.ancientpoem.-$$Lambda$fDEsrnGwf7xtmpEZN_VRNdmgtKE
            @Override // java.lang.Runnable
            public final void run() {
                AncientPoemActivity.this.finish();
            }
        };
        this.k = 0;
        initBackGroundArray();
        this.dialogId = getIntent().getStringExtra(BaseEventActivity.KEY_DIALOG_ID);
        this.q = getIntent().getParcelableArrayListExtra(KEY_POEMS);
        L.ancientPoem.i("ancientPoems dialogId=%s,ancientPoems=%s", this.dialogId, Integer.valueOf(this.q.size()));
        if (ContainerUtil.isEmpty(this.q) || TextUtils.isEmpty(this.dialogId)) {
            L.ancientPoem.i("ancientPoems is empty");
            a();
            return;
        }
        a((String) null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view, int i, int i2, int i3, int i4) {
        this.o = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MicoMultiAudioPlayer.getInstance() == null || !this.dialogId.equals(MicoMultiAudioPlayer.getInstance().getDialogId())) {
            a((String) null, 0);
        } else {
            a(MicoMultiAudioPlayer.getInstance().getAudioId(), MicoMultiAudioPlayer.getInstance().getPlayingIndex());
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (MicoMultiAudioPlayer.getInstance() != null) {
            OperationManager.getInstance().removeOperations(this.dialogId);
            SpeechManager.getInstance().stopTts();
            MicoMultiAudioPlayer.getInstance().release(this.dialogId);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        a(0L);
    }

    private void a(String str, int i) {
        String str2 = null;
        AncientPoemEntity ancientPoemById = !TextUtils.isEmpty(str) ? getAncientPoemById(str) : null;
        if (ancientPoemById == null && i >= 0) {
            ancientPoemById = getAncientPoemByIndex(i);
        }
        if (ancientPoemById == null && i == -1) {
            ancientPoemById = getAncientPoemByIndex(0);
        }
        if (ancientPoemById == null) {
            L.ancientPoem.i("item is null");
            a();
            return;
        }
        this.d = ancientPoemById.verse;
        this.e = ancientPoemById.name;
        this.f = ancientPoemById.a();
        L.ancientPoem.i("poemTitle:%s,poemAuthor:poemText:%s,poemUrl:%s ", this.e, this.f, this.d);
        if (!TextUtils.isEmpty(this.d)) {
            String string = !TextUtils.isEmpty(ancientPoemById.meaning) ? getString(R.string.ancient_poem_meaning, new Object[]{ancientPoemById.meaning}) : null;
            if (string != null) {
                this.d += string;
            }
            this.h.setText(this.d);
        }
        if (!TextUtils.isEmpty(this.e)) {
            this.i.setText(this.e);
        }
        if (!TextUtils.isEmpty(this.f)) {
            if (!TextUtils.isEmpty(ancientPoemById.dynasty)) {
                str2 = String.format("[%s]", ancientPoemById.dynasty);
            }
            if (str2 != null) {
                this.f = str2 + this.f;
            }
            this.j.setText(this.f);
        }
        b();
    }

    private void a(long j) {
        L.ancientPoem.i("release=%s", Long.valueOf(j));
        if (MicoMultiAudioPlayer.getInstance() != null) {
            MicoMultiAudioPlayer.getInstance().release(this.dialogId);
        }
        if (this.o) {
            scheduleToClose(DEFAULT_CLOSE_DURATION);
        } else {
            ThreadUtil.postDelayedInMainThread(this.g, j);
        }
    }

    private void a() {
        a(c);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayerStateChangeEvent(AudioPlayerStateChangeEvent audioPlayerStateChangeEvent) {
        L.base.i("event.audioState=%s", audioPlayerStateChangeEvent);
        MicoMultiAudioPlayer.AudioState audioState = audioPlayerStateChangeEvent.audioState;
        if (audioPlayerStateChangeEvent.dialogId == null || !audioPlayerStateChangeEvent.dialogId.equalsIgnoreCase(this.dialogId)) {
            L.base.i("dialogId not equals,finishDialogId=%s", this.dialogId);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.ERROR || audioState == MicoMultiAudioPlayer.AudioState.LIST_FINISH || audioState == MicoMultiAudioPlayer.AudioState.BACKGROUND) {
            a();
        } else if (audioState == MicoMultiAudioPlayer.AudioState.STARTED || audioState == MicoMultiAudioPlayer.AudioState.NEXT || audioState == MicoMultiAudioPlayer.AudioState.PREVIOUS) {
            a(audioPlayerStateChangeEvent.audioId, audioPlayerStateChangeEvent.index);
        }
    }

    private void b() {
        int nextInt;
        do {
            nextInt = this.m.nextInt(5);
        } while (nextInt == this.k);
        this.k = nextInt;
        GlideUtils.bindImageView((Activity) this, getBackGround(this.k), this.l);
        L.ancientPoem.d("set random background %s", Integer.valueOf(nextInt));
    }

    public void initBackGroundArray() {
        this.p = new SparseIntArray();
        this.p.put(0, R.drawable.ancientpoem_bg_a);
        this.p.put(1, R.drawable.ancientpoem_bg_b);
        this.p.put(2, R.drawable.ancientpoem_bg_c);
        this.p.put(3, R.drawable.ancientpoem_bg_d);
        this.p.put(4, R.drawable.ancientpoem_bg_e);
    }

    public int getBackGround(int i) {
        return i < this.p.size() ? this.p.get(i) : R.drawable.ancientpoem_bg_a;
    }

    public AncientPoemEntity getAncientPoemById(String str) {
        int i;
        if (ContainerUtil.isEmpty(this.q) || (i = this.r) > 10 || i >= this.q.size()) {
            return null;
        }
        for (AncientPoemEntity ancientPoemEntity : this.q) {
            if (str.equalsIgnoreCase(ancientPoemEntity.audioId)) {
                return ancientPoemEntity;
            }
        }
        return null;
    }

    public AncientPoemEntity getAncientPoemByIndex(int i) {
        if (!ContainerUtil.isEmpty(this.q) && i <= 10 && i < this.q.size()) {
            return this.q.get(i);
        }
        return null;
    }
}
