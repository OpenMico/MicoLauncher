package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.Application;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.skills.personalize.event.ThirdPartAppOpeEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ApplicationOperateOperation extends BaseOperation<Instruction<Application.Operate>> {
    private boolean b;

    /* loaded from: classes3.dex */
    public enum OperateTag {
        UNKNOWN(-1),
        EXECUTE(0),
        TOO_MANY(1),
        NOT_INSTALLED(2),
        NOT_SUPPORTED(3);
        
        private int id;

        OperateTag(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    public ApplicationOperateOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Application.Operate operate = (Application.Operate) this.instruction.getPayload();
        Application.ApplicationOp operation = operate.getOperation();
        if (operation == Application.ApplicationOp.OPEN) {
            return a(operate);
        }
        if (operation == Application.ApplicationOp.CLOSE) {
            return b(operate);
        }
        String a = a(operate, OperateTag.NOT_SUPPORTED);
        if (!TextUtils.isEmpty(a)) {
            SpeechManager.getInstance().ttsRequest(a);
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private BaseOperation.OpState a(Application.Operate operate) {
        if (!operate.getAppExtras().isPresent()) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        List<Application.AppExtra> list = operate.getAppExtras().get();
        if (ContainerUtil.isEmpty(list)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        OperateTag a = a(list);
        if (a != OperateTag.EXECUTE) {
            if (a == OperateTag.NOT_INSTALLED || a == OperateTag.NOT_SUPPORTED) {
                String a2 = a(operate, a);
                if (!TextUtils.isEmpty(a2)) {
                    SpeechManager.getInstance().ttsRequest(a2);
                }
            }
            return BaseOperation.OpState.STATE_FAIL;
        } else if (!this.b) {
            return BaseOperation.OpState.STATE_SUCCESS;
        } else {
            register();
            return BaseOperation.OpState.STATE_PROCESSING;
        }
    }

    private BaseOperation.OpState b(Application.Operate operate) {
        List<Template.AndroidApp> apps = operate.getApps();
        if (ContainerUtil.isEmpty(apps)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        OperateTag b = b(apps);
        if (b == OperateTag.EXECUTE) {
            return BaseOperation.OpState.STATE_SUCCESS;
        }
        if (b == OperateTag.NOT_INSTALLED || b == OperateTag.NOT_SUPPORTED) {
            String a = a(operate, b);
            if (!TextUtils.isEmpty(a)) {
                SpeechManager.getInstance().ttsRequest(a);
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private String a(Application.Operate operate, OperateTag operateTag) {
        for (Application.Hint hint : operate.getHints()) {
            if (hint.getTag().getId() == operateTag.getId()) {
                return hint.getDescription();
            }
        }
        return "";
    }

    private OperateTag a(List<Application.AppExtra> list) {
        final String pkgName = list.get(0).getPkgName();
        if (TextUtils.isEmpty(pkgName)) {
            L.base.i("openApp pkgName is empty");
            return OperateTag.NOT_SUPPORTED;
        }
        boolean handleAppWithPkgName = AppStoreApi.handleAppWithPkgName(getContext(), pkgName, new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$ApplicationOperateOperation$a8GvVql9SgJDyTOQHGa6NKlL3fQ
            @Override // java.lang.Runnable
            public final void run() {
                ApplicationOperateOperation.this.a(pkgName);
            }
        });
        if (!handleAppWithPkgName) {
            L.base.i("%s app is not install for openApp", pkgName);
        }
        return handleAppWithPkgName ? OperateTag.EXECUTE : OperateTag.NOT_INSTALLED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) {
        this.b = setOpenAppCmdCountDown();
        ThirdPartyAppProxy.getInstance().startApp(getContext(), str);
    }

    private OperateTag b(List<Template.AndroidApp> list) {
        for (Template.AndroidApp androidApp : list) {
            String pkgName = androidApp.getPkgName();
            if (TextUtils.isEmpty(pkgName)) {
                L.base.i("closeApp ,pkgName is empty");
                return OperateTag.NOT_SUPPORTED;
            } else if (!CommonUtils.isPackageInstalled(getContext(), pkgName)) {
                L.base.i("%s,%s app is not install for closeApp", androidApp.getName(), pkgName);
                return OperateTag.NOT_INSTALLED;
            } else {
                L.base.i("close app name is %s", pkgName);
                ActivityUtil.forceStopPackage(getContext(), pkgName);
            }
        }
        return OperateTag.EXECUTE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThirdPartAppOpeTimeOutEvent(ThirdPartAppOpeEvent thirdPartAppOpeEvent) {
        L.capability.i("ApplicationOperateOperation onThirdPartAppOpeTimeOutEvent");
        if (thirdPartAppOpeEvent.cmdTimeOut) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }
}
