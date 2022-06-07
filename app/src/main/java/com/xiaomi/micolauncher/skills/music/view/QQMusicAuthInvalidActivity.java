package com.xiaomi.micolauncher.skills.music.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.MusicProfile;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class QQMusicAuthInvalidActivity extends BaseActivity {
    TextView a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_qq_music_auth_invalid);
        this.a = (TextView) findViewById(R.id.auth_status_desc);
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$QQMusicAuthInvalidActivity$_keziwuSAGKzpMzG3t_phxzbNcE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QQMusicAuthInvalidActivity.this.a(view);
            }
        });
        a();
        EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError(22));
        PlayerApi.pause();
        PlayerApi.clearPlayerStatus();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
        a(MusicProfile.shareInstance().getQQMusicAuthStatus());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MIBrain.CPBindStatus cPBindStatus) {
        if (cPBindStatus == null) {
            return;
        }
        if (cPBindStatus.isNotBinded()) {
            this.a.setText(R.string.qq_music_auth_unbind);
        } else if (cPBindStatus.isExpired()) {
            this.a.setText(R.string.qq_music_auth_expired);
        } else if (cPBindStatus.isExpireSoon()) {
            this.a.setText(R.string.qq_music_auth_will_expired);
        }
    }

    private void a() {
        ApiManager.minaService.qqMusicAuthStatus("").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<MIBrain.CPBindStatus>() { // from class: com.xiaomi.micolauncher.skills.music.view.QQMusicAuthInvalidActivity.1
            /* renamed from: a */
            public void onSuccess(MIBrain.CPBindStatus cPBindStatus) {
                MusicProfile.shareInstance().setQQMusicAuthStatus(cPBindStatus);
                if (!QQMusicAuthInvalidActivity.this.isFinishing() && !QQMusicAuthInvalidActivity.this.isDestroyed()) {
                    if (cPBindStatus.isExpired() || cPBindStatus.isNotBinded()) {
                        QQMusicAuthInvalidActivity.this.a(cPBindStatus);
                    } else {
                        QQMusicAuthInvalidActivity.this.finish();
                    }
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
            }
        });
    }
}
