package com.xiaomi.micolauncher.instruciton.impl;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.ai.api.Launcher;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.skills.personalize.event.ThirdPartAppOpeEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class LaunchAppOperation extends BaseOperation<Instruction<Launcher.LaunchApp>> {
    public LaunchAppOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        boolean z2;
        Launcher.LaunchApp launchApp = (Launcher.LaunchApp) this.instruction.getPayload();
        if (!launchApp.getType().isPresent()) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        Launcher.LaunchTargetType launchTargetType = launchApp.getType().get();
        if (launchTargetType == Launcher.LaunchTargetType.ANDROID_INTENT) {
            if (!launchApp.getIntent().isPresent()) {
                return BaseOperation.OpState.STATE_FAIL;
            }
            final Template.AndroidIntent androidIntent = launchApp.getIntent().get();
            String trim = androidIntent.getUri().trim();
            if (ContainerUtil.hasData(trim)) {
                L.capability.i("%s processLaunchApp uri=%s", "[LaunchAppOperation]", trim);
                final Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(trim));
                register();
                try {
                    z2 = ActivityLifeCycleManager.startActivityWithThrowException(intent);
                } catch (ActivityNotFoundException unused) {
                    L.base.e("%s caught ActivityNotFoundException", "[LaunchAppOperation]");
                    if (ContainerUtil.hasData(androidIntent.getPkgName())) {
                        AppStoreApi.handleAppWithPkgName(getContext(), androidIntent.getPkgName(), new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$LaunchAppOperation$3bgXQ1UvtNsn_g-f_DiNciiX1cQ
                            @Override // java.lang.Runnable
                            public final void run() {
                                LaunchAppOperation.a(Template.AndroidIntent.this, intent);
                            }
                        });
                    }
                    z2 = false;
                }
                int indexOf = trim.indexOf("?");
                int i = indexOf - 1;
                if (trim.lastIndexOf("/") == i) {
                    trim = trim.substring(0, i);
                    L.base.i("%s processLaunchApp startResult: %b, index -1 uri is : %s", "[LaunchAppOperation]", Boolean.valueOf(z2), trim);
                } else if (indexOf > 0) {
                    trim = trim.substring(0, indexOf);
                    L.base.i("%s processLaunchApp startResult: %b, index uri is : %s", "[LaunchAppOperation]", Boolean.valueOf(z2), trim);
                }
                SkillDataManager.getManager().addRecordByAction(trim);
                return z2 ? BaseOperation.OpState.STATE_PROCESSING : BaseOperation.OpState.STATE_FAIL;
            }
            final String pkgName = androidIntent.getPkgName();
            boolean isPackageInstalled = CommonUtils.isPackageInstalled(getContext(), pkgName);
            L.capability.i("%s processLaunchApp pkgName=%s, isInstalled=%s", "[LaunchAppOperation]", pkgName, Boolean.valueOf(isPackageInstalled));
            if (isPackageInstalled) {
                return a(pkgName);
            }
            AppStoreApi.handleAppWithPkgName(getContext(), pkgName, new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$LaunchAppOperation$u-T0uy1y_-uclDLP5dnBTv_ujwM
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchAppOperation.this.a(pkgName);
                }
            });
            return BaseOperation.OpState.STATE_PROCESSING;
        } else if (launchTargetType != Launcher.LaunchTargetType.URL || !launchApp.getUrl().isPresent()) {
            return BaseOperation.OpState.STATE_FAIL;
        } else {
            SchemaManager.handleSchema(getContext(), launchApp.getUrl().get());
            QueryLatency.getInstance().setSpeechLaunchAppIntentMs();
            return BaseOperation.OpState.STATE_SUCCESS;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Template.AndroidIntent androidIntent, Intent intent) {
        L.base.i("%s %s installed successfully, try to restart", "[LaunchAppOperation]", androidIntent.getPkgName());
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* renamed from: startInstalledActivity */
    public BaseOperation.OpState a(String str) {
        ActivityLifeCycleManager.startActivityQuietly(getContext().getPackageManager().getLaunchIntentForPackage(str));
        if (setOpenAppCmdCountDown()) {
            register();
            return BaseOperation.OpState.STATE_PROCESSING;
        }
        QueryLatency.getInstance().setSpeechLaunchAppIntentMs();
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThirdPartAppOpeTimeOutEvent(ThirdPartAppOpeEvent thirdPartAppOpeEvent) {
        L.capability.i("%s onThirdPartAppOpeTimeOutEvent", "[LaunchAppOperation]");
        if (thirdPartAppOpeEvent.cmdTimeOut) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }
}
