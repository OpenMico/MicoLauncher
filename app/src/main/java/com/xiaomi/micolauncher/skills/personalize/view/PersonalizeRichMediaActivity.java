package com.xiaomi.micolauncher.skills.personalize.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.personalize.event.RichMediaInterruptEvent;
import com.xiaomi.micolauncher.skills.personalize.event.RichMediaPlayEndEvent;
import com.xiaomi.micolauncher.skills.personalize.event.RichMediaWakeupEvent;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.voice.model.VoiceModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class PersonalizeRichMediaActivity extends BaseActivity {
    private static final long a = TimeUnit.SECONDS.toMillis(5);
    private ImageView b;
    private Directive.RichMedia c;
    private String d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private CountDownTimer i = new CountDownTimer(a, 1000) { // from class: com.xiaomi.micolauncher.skills.personalize.view.PersonalizeRichMediaActivity.1
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            long j2 = PersonalizeRichMediaActivity.a - j;
            XLog.i("PersonalizeSkill[RichMedia]: on tick " + j2);
            if (j2 > 500) {
                return;
            }
            if (!TextUtils.isEmpty(PersonalizeRichMediaActivity.this.c.text)) {
                XLog.v("PersonalizeSkill[RichMedia]: start tts " + PersonalizeRichMediaActivity.this.c.text);
                PersonalizeRichMediaActivity.this.e = true;
                SpeechManager.getInstance().ttsRequest(PersonalizeRichMediaActivity.this.c.text, false);
                return;
            }
            PersonalizeRichMediaActivity.this.c();
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            XLog.i("PersonalizeSkill[RichMedia]:on finish hasAudio:" + PersonalizeRichMediaActivity.this.e + " audioPlayEnd:" + PersonalizeRichMediaActivity.this.f);
            if (!PersonalizeRichMediaActivity.this.e || PersonalizeRichMediaActivity.this.f) {
                PersonalizeRichMediaActivity.this.d();
            } else {
                PersonalizeRichMediaActivity.this.g = true;
            }
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() != null) {
            this.c = (Directive.RichMedia) getIntent().getSerializableExtra("EXTRA_RICH_MEDIA");
        }
        setContentView(R.layout.activity_personalize_image);
        this.b = (ImageView) findViewById(R.id.image_view);
        Directive.RichMedia richMedia = this.c;
        if (richMedia == null || richMedia.image == null) {
            d();
        }
        b();
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.e = false;
        this.f = false;
        this.g = false;
    }

    private void b() {
        StreamHelper.buildUrl(this.c.image).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.personalize.view.-$$Lambda$PersonalizeRichMediaActivity$og23h9ilFY-Xy5PKVHG8gbHjsDA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PersonalizeRichMediaActivity.this.b((String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.personalize.view.-$$Lambda$PersonalizeRichMediaActivity$oVSz-cIJ5TTAE2MhdjAfa-IBfTg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PersonalizeRichMediaActivity.this.b((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str) throws Exception {
        XLog.v("PersonalizeSkill[RichMedia]:load image, url is " + str);
        ImageView imageView = this.b;
        GlideUtils.bindImageView(imageView, str, 0, new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.skills.personalize.view.PersonalizeRichMediaActivity.2
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void setResource(@Nullable Bitmap bitmap) {
                if (bitmap != null) {
                    if (bitmap.getWidth() / bitmap.getHeight() > 1.0f) {
                        PersonalizeRichMediaActivity.this.b.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        PersonalizeRichMediaActivity.this.b.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                    PersonalizeRichMediaActivity.this.b.setImageBitmap(bitmap);
                }
            }
        });
        this.i.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        XLog.e(th);
        XLog.i("PersonalizeSkill[RichMedia]: rich media catch token exception");
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.c.stream != null) {
            StreamHelper.buildUrl(this.c.stream).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.personalize.view.-$$Lambda$PersonalizeRichMediaActivity$d9j5zAnURviJ8AsiZFg7XBvdJ3c
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PersonalizeRichMediaActivity.this.a((String) obj);
                }
            }, $$Lambda$PersonalizeRichMediaActivity$NeT9XMoaUeIF100NUbi1qMNE1eA.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        XLog.v("PersonalizeSkill[RichMedia]:start audio, url is " + str);
        this.e = true;
        VoiceModel.getInstance().setSoundItem(str);
        VoiceModel.getInstance().notifyUpdateSound(new BasePlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.skills.personalize.view.PersonalizeRichMediaActivity.3
            @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
            public void onError(BasePlayer basePlayer, int i, int i2) {
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
            public void onPrepared(BasePlayer basePlayer) {
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
            public void onComplete(BasePlayer basePlayer) {
                XLog.i("PersonalizeSkill[RichMedia]: voice play completed timeout:" + PersonalizeRichMediaActivity.this.g);
                PersonalizeRichMediaActivity.this.f = true;
                if (PersonalizeRichMediaActivity.this.g) {
                    PersonalizeRichMediaActivity.this.d();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        XLog.e(th);
        XLog.i("PersonalizeSkill[RichMedia]: rich media catch token exception");
    }

    public static Object getValue(Object obj, String str) throws Exception {
        if (obj == null || TextUtils.isEmpty(str)) {
            return null;
        }
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField.get(obj);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.h = true;
        finish();
        EventBusRegistry.getEventBus().post(new RichMediaPlayEndEvent(this.d));
        this.i.cancel();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity
    public void finish() {
        super.finish();
        if (!this.h) {
            EventBusRegistry.getEventBus().post(new RichMediaInterruptEvent(this.d));
            this.i.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayEnd(TtsPlayEndEvent ttsPlayEndEvent) {
        XLog.i("PersonalizeSkill[RichMedia]: onTtsPlayEnd timeout:" + this.g);
        this.f = true;
        if (this.g) {
            d();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWakeup(RichMediaWakeupEvent richMediaWakeupEvent) {
        XLog.i("PersonalizeSkill[RichMedia]: onWakeup");
        d();
    }

    public static Intent openPersonalizeImageActivity(Context context, Directive.RichMedia richMedia, String str) {
        Intent intent = new Intent(context, PersonalizeRichMediaActivity.class);
        intent.putExtra("EXTRA_RICH_MEDIA", richMedia);
        intent.putExtra("EXTRA_DIALOG_ID", str);
        return intent;
    }
}
