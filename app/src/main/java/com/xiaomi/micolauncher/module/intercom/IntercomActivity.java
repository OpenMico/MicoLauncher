package com.xiaomi.micolauncher.module.intercom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.IntercomDeviceConfig;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class IntercomActivity extends BaseActivity {
    public static final String EXTRA_PAGE_TYPE = "extra_page_type";
    public static final String EXTRA_RECEIVE_MODEL = "extra_receive_model";
    public static final int INTERCOM_PAGE_MAIN = 0;
    public static final int INTERCOM_PAGE_NONE = -1;
    public static final int INTERCOM_PAGE_RECEIVE = 3;
    public static final int INTERCOM_PAGE_RECORD = 1;
    public static final int INTERCOM_PAGE_SEND = 2;
    private Fragment a;
    private CustomDialog b;
    private Disposable c;
    private boolean d;
    private int e = -1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface INTERCOM_PAGE_TYPE {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_intercom);
        removeCloseScheduler();
        this.c = DeviceConfigIntercom.getInstance().getIntercomSettings(this).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomActivity$Xa0C4G2Tz5X9NLn_iPzFw4ZJh9Q
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = IntercomActivity.this.a((BaseIntercomResponse) obj);
                return a;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomActivity$i49zB3q4sUHCTZPzDqyJOivXY2E
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IntercomActivity.this.a((IntercomSettings) obj);
            }
        }, $$Lambda$IntercomActivity$cEn0O2GSOmFguJRaf68toliiTTQ.INSTANCE);
        SendIntercom.getInstance().initRecordConfig(getApplication());
        a(getIntent());
    }

    public /* synthetic */ ObservableSource a(BaseIntercomResponse baseIntercomResponse) throws Exception {
        L.push.d("IntercomActivity getIntercomSettings from cloud success, and update to MicoSettings");
        IntercomSettings intercomSettings = (IntercomSettings) baseIntercomResponse.data;
        PreferenceUtils.setSettingString(this, DeviceConfigIntercom.SP_INTERCOM_SETTINGS, Gsons.getGson().toJson(intercomSettings));
        MicoSettings.setIntercomDeviceConfig(this, new IntercomDeviceConfig(intercomSettings.current_device.support_intercom, intercomSettings.night_mode_no_disturbing, -1, System.currentTimeMillis()));
        return Observable.just(intercomSettings);
    }

    public /* synthetic */ void a(IntercomSettings intercomSettings) throws Exception {
        PushIntercom.getInstance().setNightModeNoDisturbing(intercomSettings.night_mode_no_disturbing);
        if (!intercomSettings.current_device.support_intercom) {
            this.b = new CustomDialog.Builder().setTitleResId(R.string.intercom_main_title).setMessageResId(R.string.intercom_disable_dialog_msg).setPositiveResId(R.string.intercom_disable_dialog_positive).setNegativeResId(R.string.intercom_disable_dialog_negative).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomActivity$u1ybCiuzPv49_cl2y0GlPunqXb0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IntercomActivity.this.b(view);
                }
            }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomActivity$SCAI9enFfI-wZIeCsEDAFpH7NWQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IntercomActivity.this.a(view);
                }
            }).setHasNegativeButton(true).build();
            this.b.show();
        }
    }

    public /* synthetic */ void b(View view) {
        SettingOpenManager.openIntercomSetting(this);
    }

    public /* synthetic */ void a(View view) {
        onBackPressed();
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.push.e("IntercomActivity getIntercomSettings onError", th);
    }

    private void a(Intent intent) {
        int intExtra = intent.getIntExtra(EXTRA_PAGE_TYPE, -1);
        if (-1 == intExtra) {
            showFragment(0, IntercomMainFragment.newInstance(), false);
        } else if (3 == intExtra) {
            showFragment(3, IntercomReceiveFragment.newInstance((IntercomReceiveModel) intent.getParcelableExtra(EXTRA_RECEIVE_MODEL)), false);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PushIntercom.getInstance().setProcessIntercomPlay(true);
        a(intent);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CustomDialog customDialog = this.b;
        if (customDialog != null) {
            customDialog.dismiss();
            this.b = null;
        }
        Disposable disposable = this.c;
        if (disposable != null) {
            disposable.dispose();
            this.c = null;
        }
        PushIntercom.getInstance().stopPlay();
    }

    public void showFragment(int i, Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        if (this.a == null && z) {
            beginTransaction.addToBackStack(null);
        }
        if (this.e == 2 && i == 3) {
            beginTransaction.addToBackStack(null);
        }
        this.e = i;
        this.a = fragment;
        try {
            beginTransaction.commitNowAllowingStateLoss();
        } catch (IllegalStateException e) {
            beginTransaction.commitAllowingStateLoss();
            e.printStackTrace();
        }
    }

    public void detachSendFragment() {
        if (this.a instanceof IntercomSendFragment) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.detach(this.a);
            try {
                beginTransaction.commitNowAllowingStateLoss();
            } catch (IllegalStateException e) {
                beginTransaction.commitAllowingStateLoss();
                e.printStackTrace();
            }
        }
    }

    public boolean isMarkRecordAudioIsSending() {
        return this.d;
    }

    public void setMarkRecordAudioIsSending(boolean z) {
        this.d = z;
    }
}
