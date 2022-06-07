package com.xiaomi.micolauncher.skills.voice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.skills.voice.widget.VoiceTimePicker;

/* loaded from: classes3.dex */
public class WhiteNoiseActivity extends BaseActivity implements IWhiteNoiseView, VoiceTimePicker.OnTimePickedListener {
    View a;
    ImageView b;
    TextView c;
    TextView d;
    ImageView e;
    VoiceTimePicker f;
    ImageView g;
    GestureDetector.SimpleOnGestureListener h = new GestureDetector.SimpleOnGestureListener() { // from class: com.xiaomi.micolauncher.skills.voice.WhiteNoiseActivity.1
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!WhiteNoiseActivity.this.i.a()) {
                return false;
            }
            float rawX = motionEvent.getRawX() - motionEvent2.getRawX();
            if (rawX > 200.0f || f < -1500.0f) {
                WhiteNoiseActivity.this.i.b();
                return true;
            } else if (rawX >= -200.0f && f <= 1500.0f) {
                return false;
            } else {
                WhiteNoiseActivity.this.i.c();
                return true;
            }
        }
    };
    private WhiteNoisePresenter i;
    private GestureDetector j;
    private Runnable k;

    public static Intent getIntent(Context context, String str) {
        Intent intent = new Intent(context, WhiteNoiseActivity.class);
        intent.putExtra("KEY_DIALOG_ID", str);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_white_noise);
        this.a = findViewById(R.id.root_view);
        this.b = (ImageView) findViewById(R.id.image_iv);
        this.c = (TextView) findViewById(R.id.title);
        this.d = (TextView) findViewById(R.id.cut_down_time);
        this.e = (ImageView) findViewById(R.id.pause_button);
        this.f = (VoiceTimePicker) findViewById(R.id.voice_time_picker);
        this.g = (ImageView) findViewById(R.id.button_time);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.-$$Lambda$WhiteNoiseActivity$9WGjuxE0mhKcPW0D-RML1EtTXtg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WhiteNoiseActivity.this.c(view);
            }
        });
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.-$$Lambda$WhiteNoiseActivity$jwTRCe0EMOQR3b5Lb71T0EfOvfw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WhiteNoiseActivity.this.b(view);
            }
        });
        this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.voice.-$$Lambda$WhiteNoiseActivity$6b8Ae1pXEKGUE2uHHbyBbcC8BRY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WhiteNoiseActivity.this.a(view);
            }
        });
        this.f.setTimePickerListener(this);
        this.i = new WhiteNoisePresenter(this);
        this.j = new GestureDetector(this, this.h);
        this.a.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.micolauncher.skills.voice.-$$Lambda$WhiteNoiseActivity$AX_HUWR6MGtQx1V7ce6YGFjohwQ
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean a;
                a = WhiteNoiseActivity.this.a(view, motionEvent);
                return a;
            }
        });
        this.i.setDialogId(getIntent().getStringExtra("KEY_DIALOG_ID"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        this.i.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        this.i.play();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.f.setVisibility(0);
        this.g.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(View view, MotionEvent motionEvent) {
        return this.j.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.i.e();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.i.f();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.i.setDialogId(intent.getStringExtra("KEY_DIALOG_ID"));
    }

    @Override // com.xiaomi.micolauncher.skills.voice.widget.VoiceTimePicker.OnTimePickedListener
    public void onTimePicked(long j) {
        WhiteNoisePresenter whiteNoisePresenter = this.i;
        if (whiteNoisePresenter != null) {
            whiteNoisePresenter.a(j);
        }
        a();
    }

    @Override // com.xiaomi.micolauncher.skills.voice.widget.VoiceTimePicker.OnTimePickedListener
    public void onCancel() {
        a();
    }

    private void a() {
        ImageView imageView = this.g;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Threads.removeCallbacksInMainThread(this.k);
        this.k = null;
        WhiteNoisePresenter whiteNoisePresenter = this.i;
        if (whiteNoisePresenter != null) {
            whiteNoisePresenter.onDestroy();
            this.i = null;
        }
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void updateTitle(String str) {
        TextView textView = this.c;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void updateTime(String str) {
        TextView textView = this.d;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void updateImage(String str) {
        if (this.b != null && !TextUtils.isEmpty(str)) {
            GlideUtils.bindImageView(this, str, this.b);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void updateImageLocal(String str) {
        this.b.setImageResource(getResources().getIdentifier(str, "drawable", getPackageName()));
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void finish(String str) {
        L.base.d("voice finish,tag=%s", str);
        if (!isFinishing() && !isDestroyed()) {
            finish();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.voice.IWhiteNoiseView
    public void onPlayStateChange(final MicoMultiAudioPlayer.AudioState audioState) {
        this.k = new Runnable() { // from class: com.xiaomi.micolauncher.skills.voice.-$$Lambda$WhiteNoiseActivity$WgSZc3-dBy0pQSnwjLO7cwS8UwM
            @Override // java.lang.Runnable
            public final void run() {
                WhiteNoiseActivity.this.a(audioState);
            }
        };
        runOnUiThread(this.k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MicoMultiAudioPlayer.AudioState audioState) {
        switch (audioState) {
            case STARTED:
            case RESUME:
                ImageView imageView = this.e;
                if (imageView != null) {
                    imageView.setVisibility(8);
                }
                this.i.onResumeCutDown();
                return;
            case STOPPED:
            case PAUSED:
                ImageView imageView2 = this.e;
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                this.i.stopCutDownTime();
                return;
            default:
                return;
        }
    }
}
