package com.xiaomi.micolauncher.application.controllers;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.event.AccountEvent;
import com.xiaomi.micolauncher.common.event.QRLoginSuccessEvent;
import com.xiaomi.micolauncher.module.initialize.BootActivity;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AccountController extends ControllerBase {
    public AccountController(Context context) {
        super(context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountEvent(AccountEvent accountEvent) {
        if (accountEvent.getErrorCode() == ServiceTokenResult.ErrorCode.ERROR_USER_INTERACTION_NEEDED) {
            Intent intentOfActivity = getIntentOfActivity(BootActivity.class);
            intentOfActivity.addFlags(536870912);
            ActivityLifeCycleManager.startActivityQuietly(intentOfActivity);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountEvent(QRLoginSuccessEvent qRLoginSuccessEvent) {
        RecommendDataManager.getManager().loadData(getContext());
    }
}
