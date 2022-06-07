package com.xiaomi.micolauncher;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.application.SetupManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.BaseMainPage;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Set;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes3.dex */
public class Launcher extends BaseActivity {
    public static final String KEY_ENTER_HOMEPAGE = "home_page";
    private LauncherState a;
    private boolean b = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        L.startUpProfile.i("Launcher onCreate %s %s %s", intent, intent.getAction(), a(intent.getCategories()));
        a(bundle);
        setTheme(R.style.MainLauncher);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity
    public void onRestart() {
        super.onRestart();
        boolean z = this.a instanceof BaseMainPage;
        L.init.i("onRestart, currently main page state %s", Boolean.valueOf(z));
        if (!z) {
            a((Bundle) null);
        }
    }

    private void a(Bundle bundle) {
        boolean booleanExtra = getIntent().getBooleanExtra(KEY_ENTER_HOMEPAGE, false);
        L.init.i("enter home page ? %B", Boolean.valueOf(booleanExtra));
        if (booleanExtra) {
            d(bundle);
        } else {
            b(bundle);
        }
    }

    private void b(final Bundle bundle) {
        SplashLaunchingState splashLaunchingState = new SplashLaunchingState(this);
        this.a = splashLaunchingState;
        splashLaunchingState.a(new OnSwitchToHomeStateListener() { // from class: com.xiaomi.micolauncher.-$$Lambda$Launcher$4jeFDLgD76MbSvwyVeooC9OA7OE
            @Override // com.xiaomi.micolauncher.OnSwitchToHomeStateListener
            public final void switchToHomeState() {
                Launcher.this.d(bundle);
            }
        });
        this.a.onCreate(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!this.a.onNewIntent(intent)) {
            L.init.i("onNewIntent %s %s %s", intent, intent.getAction(), a(intent.getCategories()));
            if (SystemSetting.isInitialized(this)) {
                d(null);
                return;
            }
            L.init.w("not initialized, should be displaying login");
            b(null);
        }
    }

    private Object a(Set<String> set) {
        if (set == null) {
            return null;
        }
        return TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, set);
    }

    /* renamed from: c */
    public void d(Bundle bundle) {
        if (!(this.a instanceof BaseMainPage)) {
            if (!LoginModel.getInstance().hasValidAccount()) {
                L.init.w("illegal state : login failed ? valid account %B");
            }
            SetupManager.getInstance().setupAfterLoginEagerStage(this);
            this.a = BaseMainPage.getMainPage(this);
            this.a.onCreate(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.a != null) {
            if (!this.b) {
                this.b = true;
                L.startUpProfile.i("Launcher onResume");
            }
            EventBus eventBus = EventBusRegistry.getEventBus();
            if (this.a.needRegisterEventBus() && !eventBus.isRegistered(this.a)) {
                eventBus.register(this.a);
            }
            this.a.onResume();
            ThirdPartyAppProxy.getInstance().onLauncherResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LauncherState launcherState = this.a;
        if (launcherState != null) {
            launcherState.onPause();
        }
    }

    private void a() {
        if (this.a != null) {
            EventBus eventBus = EventBusRegistry.getEventBus();
            if (eventBus.isRegistered(this.a)) {
                eventBus.unregister(this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a.onDestroy();
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.a.onSaveInstanceState(bundle);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        LauncherState launcherState = this.a;
        if (launcherState != null) {
            launcherState.onDispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        L.base.w("Launcher super.onBackPressed called");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        L.init.d("onActivityResult requestCode %s", Integer.valueOf(i));
        LauncherState launcherState = this.a;
        if (launcherState != null) {
            launcherState.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        this.a.onTrimMemory(i);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }
}
