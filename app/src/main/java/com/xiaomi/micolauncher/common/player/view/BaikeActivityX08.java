package com.xiaomi.micolauncher.common.player.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.model.PlayerModel;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.view.BaikeActivityX08;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.RoundImageView;
import com.xiaomi.micolauncher.databinding.ActivityBaikeX08Binding;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.baike.view.BaikeTextView;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BaikeActivityX08 extends BaseEventActivity implements BaikeTextView.CompleteListener {
    private static final int g = (int) TimeUnit.SECONDS.toMillis(3);
    private String a;
    private String b;
    private String c;
    private String d;
    private Runnable e;
    private boolean f;
    private FakePlayer h;
    private int i;
    private int j;
    private ActivityBaikeX08Binding k;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        this.k = (ActivityBaikeX08Binding) DataBindingUtil.setContentView(this, R.layout.activity_baike_x08);
        a(getIntent());
        this.e = new Runnable() { // from class: com.xiaomi.micolauncher.common.player.view.-$$Lambda$xZ6ycO4fNOFY2gfWdn2rSIgwFwQ
            @Override // java.lang.Runnable
            public final void run() {
                BaikeActivityX08.this.finish();
            }
        };
        AudioSource audioSource = (AudioSource) getIntent().getSerializableExtra(PlayerModel.KEY_AUDIO_SOURCE);
        if (audioSource == null) {
            audioSource = AudioSource.AUDIO_SOURCE_UI;
        }
        a(audioSource);
        setPlayFinish(false);
        this.h.start();
        SpeechManager.getInstance().uiShow(QueryLatency.MultiLine);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ThreadUtil.removeCallbacksInMainThread(this.e);
        a(intent);
        SpeechManager.getInstance().uiShow(QueryLatency.MultiLine);
    }

    @Override // com.xiaomi.micolauncher.skills.baike.view.BaikeTextView.CompleteListener
    public void onComplete() {
        if (TextUtils.isEmpty(this.b)) {
            L.base.d("BaikeTextActivity.onComplete");
            finishDelayed(7000);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ThreadUtil.removeCallbacksInMainThread(this.e);
        if (this.k.normal.getRoot().getVisibility() == 0) {
            this.k.normal.baikeTextView.stopUpdate();
        }
        FakePlayer fakePlayer = this.h;
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
        if (!isPlayFinish()) {
            SpeechManager.getInstance().stopTts();
        }
    }

    public void finishDelayed(int i) {
        ThreadUtil.removeCallbacksInMainThread(this.e);
        ThreadUtil.postDelayedInMainThread(this.e, i);
    }

    public void stopPlayerAndFinishActivity() {
        this.b = null;
        finishDelayed(g);
    }

    void a() {
        ThreadUtil.removeCallbacksInMainThread(this.e);
    }

    public void onBackgroundPlayer() {
        setPlayFinish(true);
    }

    void b() {
        this.b = null;
        finishDelayed(g);
    }

    void c() {
        this.b = null;
        finishDelayed(g);
    }

    private void a(AudioSource audioSource) {
        this.h = new FakePlayer(audioSource) { // from class: com.xiaomi.micolauncher.common.player.view.BaikeActivityX08.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void forceStop(AudioSource audioSource2) {
                L.base.i("general text activity, force stop %s", audioSource2);
                BaikeActivityX08.this.stopPlayerAndFinishActivity();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStop(AudioSource audioSource2) {
                L.base.i("general text activity, post stop %s", audioSource2);
                if (audioSource2 == AudioSource.AUDIO_SOURCE_MUSIC_MI_PLAY) {
                    BaikeActivityX08.this.h.start();
                } else {
                    BaikeActivityX08.this.stopPlayerAndFinishActivity();
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void suspend(AudioSource audioSource2) {
                L.base.i("general text activity, suspend %s", audioSource2);
                BaikeActivityX08.this.a();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void background(AudioSource audioSource2) {
                L.base.i("general text activity, background %s", audioSource2);
                BaikeActivityX08.this.onBackgroundPlayer();
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            protected void foreground(AudioSource audioSource2) {
                L.base.i("general text activity, foreground");
                BaikeActivityX08.this.c();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void resume(AudioSource audioSource2) {
                L.base.i("general text activity, resume");
                BaikeActivityX08.this.b();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStart() {
                super.postStart();
            }
        };
    }

    public boolean isPlayFinish() {
        return this.f;
    }

    public void setPlayFinish(boolean z) {
        this.f = z;
    }

    private void a(Intent intent) {
        this.c = intent.getStringExtra("KEY_TEXT_CONTENT");
        this.b = intent.getStringExtra("KEY_TO_SPEAK");
        this.d = intent.getStringExtra(BaikeModel.KEY_IMAGE_URL);
        this.a = intent.getStringExtra("KEY_TEXT_TITLE");
        String str = this.c;
        int i = 0;
        boolean z = str != null && str.length() > 0;
        this.k.normal.getRoot().setVisibility(z ? 0 : 8);
        View root = this.k.singleImage.getRoot();
        if (z) {
            i = 8;
        }
        root.setVisibility(i);
        this.k.normal.baikeTextView.setRowInterval(getResources().getInteger(R.integer.row_interval));
        this.k.normal.baikeTextView.setFontSize(getResources().getInteger(R.integer.baike_content_font_size));
        a(z);
        b(z);
        c(z);
    }

    private void a(boolean z) {
        ConstraintLayout constraintLayout = z ? this.k.normal.iconContainer : this.k.singleImage.iconContainer;
        RoundImageView roundImageView = z ? this.k.normal.baikeIcon : this.k.singleImage.baikeIcon;
        String str = this.d;
        if (str == null || str.length() <= 0) {
            constraintLayout.setVisibility(8);
        } else {
            GlideUtils.bindImageView(roundImageView, this.d, 0, (Transformation<Bitmap>) null, new AnonymousClass2(roundImageView, constraintLayout, roundImageView));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.common.player.view.BaikeActivityX08$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends ImageViewTarget<Bitmap> {
        final /* synthetic */ ConstraintLayout a;
        final /* synthetic */ ImageView b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ImageView imageView, ConstraintLayout constraintLayout, ImageView imageView2) {
            super(imageView);
            this.a = constraintLayout;
            this.b = imageView2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void setResource(@Nullable final Bitmap bitmap) {
            if (bitmap != null) {
                final ConstraintLayout constraintLayout = this.a;
                final ImageView imageView = this.b;
                UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.common.player.view.-$$Lambda$BaikeActivityX08$2$NC3Jw7RqZqHLjilkqAK6Zci6yAY
                    @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
                    public final void onLayoutComplete(boolean z) {
                        BaikeActivityX08.AnonymousClass2.this.a(constraintLayout, bitmap, imageView, z);
                    }
                }, constraintLayout);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(ConstraintLayout constraintLayout, @Nullable Bitmap bitmap, ImageView imageView, boolean z) {
            int i;
            int i2;
            BaikeActivityX08.this.i = constraintLayout.getWidth();
            BaikeActivityX08.this.j = constraintLayout.getHeight();
            L.video.i("containerWidth : %s  containerHeight : %s", Integer.valueOf(BaikeActivityX08.this.i), Integer.valueOf(BaikeActivityX08.this.j));
            int integer = BaikeActivityX08.this.getResources().getInteger(R.integer.baike_image_adjust);
            if (BaikeActivityX08.this.c == null || BaikeActivityX08.this.c.length() == 0) {
                BaikeActivityX08 baikeActivityX08 = BaikeActivityX08.this;
                baikeActivityX08.j = baikeActivityX08.getResources().getInteger(R.integer.baike_container_height);
                integer = 0;
            }
            float width = bitmap.getWidth() / bitmap.getHeight();
            if (BaikeActivityX08.this.i / BaikeActivityX08.this.j > width) {
                i = BaikeActivityX08.this.j - integer;
                i2 = (int) (i * width);
            } else {
                i2 = BaikeActivityX08.this.i - integer;
                i = (int) (i2 / width);
            }
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = i;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(0);
            L.video.i("width : %s  height : %s", Integer.valueOf(i2), Integer.valueOf(i));
        }
    }

    private void b(boolean z) {
        TextView textView = z ? this.k.normal.baikeTitle : this.k.singleImage.baikeTitle;
        String str = this.a;
        if (str == null || str.length() <= 0) {
            textView.setVisibility(8);
        } else {
            textView.setText(this.a);
        }
    }

    private void c(boolean z) {
        if (z) {
            this.k.normal.baikeTextView.updateText(this.c);
            this.k.normal.baikeTextView.setCompleteListener(this);
        }
    }
}
