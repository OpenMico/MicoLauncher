package com.xiaomi.micolauncher.skills.baike.view;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class BaikeImageActivity extends BaseEventActivity {
    private Handler a;
    private String b;
    private boolean c;
    private boolean d;
    private long e;
    private int f;
    private boolean g;
    private Runnable h;
    private ImageView i;
    private TextView j;
    private ImageButton k;
    private View l;
    private ImageButton m;
    private TextView n;
    private TextView o;
    private String p;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = new Handler();
        this.b = getIntent().getStringExtra("KEY_TO_SPEAK");
        this.p = getIntent().getStringExtra(BaikeModel.KEY_ID);
        this.h = new Runnable() { // from class: com.xiaomi.micolauncher.skills.baike.view.-$$Lambda$BaikeImageActivity$SipvUSsDbgWIabExtqjyEVaHZ0s
            @Override // java.lang.Runnable
            public final void run() {
                BaikeImageActivity.this.c();
            }
        };
        setContentView(R.layout.activity_baike_image);
        String stringExtra = getIntent().getStringExtra("KEY_TEXT_TITLE");
        String stringExtra2 = getIntent().getStringExtra("KEY_TEXT_CONTENT");
        String stringExtra3 = getIntent().getStringExtra(BaikeModel.KEY_IMAGE_URL);
        a();
        this.j.setText(stringExtra);
        this.n.setText(stringExtra);
        this.o.setText(stringExtra2);
        this.o.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (!TextUtils.isEmpty(stringExtra3)) {
            ImageView imageView = this.i;
            GlideUtils.bindImageView(imageView, stringExtra3, 0, new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.skills.baike.view.BaikeImageActivity.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* renamed from: a */
                public void setResource(@Nullable Bitmap bitmap) {
                    if (bitmap != null) {
                        ((ImageView) BaikeImageActivity.this.findViewById(R.id.imageViewBackground)).setImageBitmap(bitmap);
                        BaikeImageActivity baikeImageActivity = BaikeImageActivity.this;
                        baikeImageActivity.a(new BitmapDrawable(baikeImageActivity.getResources(), bitmap));
                    }
                }
            });
        }
        if (MediaSessionController.getInstance() != null) {
            this.f = MediaSessionController.getInstance().getPlayStatus();
        }
        switch (this.f) {
            case 1:
                MediaSessionController.getInstance().pause();
                break;
        }
        this.g = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        finish();
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.d = true;
        if (this.b == null) {
            a(60000L);
        } else if (this.c) {
            a(60000L);
        }
        return super.onTouchEvent(motionEvent);
    }

    private void a(long j) {
        this.a.removeCallbacks(this.h);
        this.a.postDelayed(this.h, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Drawable drawable) {
        int screenWidthPixels = DisplayUtils.getScreenWidthPixels(this);
        int screenHeightPixels = DisplayUtils.getScreenHeightPixels(this);
        float f = screenWidthPixels;
        float f2 = screenHeightPixels;
        float f3 = (f * 1.0f) / f2;
        this.i.setImageDrawable(drawable);
        Rect bounds = drawable.getBounds();
        float width = (bounds.width() * 1.0f) / bounds.height();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.i.getLayoutParams();
        this.i.setScaleType(ImageView.ScaleType.FIT_XY);
        if (!Float.isNaN(width)) {
            if (width >= f3) {
                layoutParams.height = screenHeightPixels;
                layoutParams.width = (int) (width * f2);
                this.i.setLayoutParams(layoutParams);
                int i = (layoutParams.width - screenWidthPixels) / 2;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.i, "translationX", i, -i);
                ofFloat.setDuration(15000L);
                ofFloat.setRepeatCount(-1);
                ofFloat.setRepeatMode(2);
                ofFloat.start();
                this.e = System.currentTimeMillis() + 15000;
                if (this.b == null) {
                    a(22000L);
                }
            } else if (width >= 1.0f) {
                layoutParams.width = screenWidthPixels;
                layoutParams.height = (int) (f / width);
                this.i.setLayoutParams(layoutParams);
                int i2 = (layoutParams.height - screenHeightPixels) / 2;
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.i, "translationY", i2, -i2);
                ofFloat2.setDuration(15000L);
                ofFloat2.setRepeatCount(-1);
                ofFloat2.setRepeatMode(2);
                ofFloat2.start();
                this.e = System.currentTimeMillis() + 15000;
                if (this.b == null) {
                    a(22000L);
                }
            } else {
                layoutParams.height = (int) (screenHeightPixels * 0.7d);
                layoutParams.width = (int) (width * layoutParams.height);
                this.i.setLayoutParams(layoutParams);
                this.i.setY(-40.0f);
                this.e = System.currentTimeMillis();
                if (this.b == null) {
                    a(7000L);
                }
            }
        }
    }

    private void a() {
        this.i = (ImageView) findViewById(R.id.imageViewBaikeImage);
        this.j = (TextView) findViewById(R.id.textViewBaikeTitle);
        this.k = (ImageButton) findViewById(R.id.imageButtonText);
        this.k.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.baike.view.BaikeImageActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaikeImageActivity.this.onBaikeTextClicked(null);
            }
        });
        this.l = findViewById(R.id.viewMask);
        this.m = (ImageButton) findViewById(R.id.imageButtonClose);
        this.m.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.baike.view.BaikeImageActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaikeImageActivity.this.onBaikeTextClose(null);
            }
        });
        this.n = (TextView) findViewById(R.id.textviewBaikeImageTitle);
        this.o = (TextView) findViewById(R.id.textViewBaikeContent);
        this.l.setVisibility(4);
        this.m.setVisibility(4);
        this.n.setVisibility(4);
        this.o.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Logger logger = L.base;
        logger.d("BaikeImageActivity onDestroy, toSpeak:" + this.b + " isToSpeakEnd:" + this.c);
        if (this.b != null && !this.c) {
            new Thread($$Lambda$BaikeImageActivity$OPwPd8Tyw1EYHFm_60u1aMoe1jw.INSTANCE).start();
        }
        this.a.removeCallbacks(this.h);
        if (!this.g) {
            switch (this.f) {
                case 0:
                default:
                    return;
                case 1:
                    MediaSessionController.getInstance().play();
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b() {
        SpeechManager.getInstance().stopTts();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayEndEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        Logger logger = L.base;
        logger.d("BaikeImageActivity onTtsPlayEndEvent, dialogId:" + this.p + " isToSpeakEnd:" + this.c + " dialogId:" + ttsPlayEndEvent.dialogId);
        String str = this.p;
        if (str != null && str.equals(ttsPlayEndEvent.dialogId)) {
            this.c = true;
            long currentTimeMillis = this.e - System.currentTimeMillis();
            int i = this.d ? 60000 : 7000;
            if (currentTimeMillis > 0) {
                a(currentTimeMillis + i);
            } else {
                a(i);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayErrorEvent(TtsPlayErrorEvent ttsPlayErrorEvent) {
        Logger logger = L.base;
        logger.d("BaikeImageActivity onTtsPlayErrorEvent, dialogId:" + this.p + " isToSpeakEnd:" + this.c + " dialogId:" + ttsPlayErrorEvent.dialogId);
        String str = this.p;
        if (str != null && str.equals(ttsPlayErrorEvent.dialogId)) {
            this.c = true;
            long currentTimeMillis = this.e - System.currentTimeMillis();
            int i = this.d ? 60000 : 7000;
            if (currentTimeMillis > 0) {
                a(currentTimeMillis + i);
            } else {
                a(i);
            }
        }
    }

    public void onBaikeTextClicked(View view) {
        this.l.setVisibility(0);
        this.m.setVisibility(0);
        this.n.setVisibility(0);
        this.o.setVisibility(0);
    }

    public void onBaikeTextClose(View view) {
        this.l.setVisibility(4);
        this.m.setVisibility(4);
        this.n.setVisibility(4);
        this.o.setVisibility(4);
    }
}
