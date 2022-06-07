package com.xiaomi.micolauncher.module.initialize;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.QuickSettingHelper;

/* loaded from: classes3.dex */
public class BootActivity extends BaseActivity {
    private String a = "";

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        if (Hardware.isBigScreen()) {
            setContentView(R.layout.activity_big_boot);
        } else {
            setContentView(R.layout.activity_boot);
        }
        nextStep(true, null);
        QuickSettingHelper.enable(this, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r8.equals(com.xiaomi.micolauncher.module.initialize.steps.StepRefreshTokenFailedFragment.STEP_NAME) != false) goto L_0x0065;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void nextStep(boolean r7, java.util.HashMap<java.lang.String, java.lang.String> r8) {
        /*
            r6 = this;
            com.elvishew.xlog.Logger r8 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r0 = "BootActivity login next step, result %b, current step %s"
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r7)
            r4 = 0
            r2[r4] = r3
            java.lang.String r3 = r6.a
            r5 = 1
            r2[r5] = r3
            r8.i(r0, r2)
            java.lang.String r8 = r6.a
            int r0 = r8.hashCode()
            r2 = -1899231592(0xffffffff8ecc0698, float:-5.0296232E-30)
            if (r0 == r2) goto L_0x005a
            r2 = -1468412680(0xffffffffa879ccf8, float:-1.3866722E-14)
            if (r0 == r2) goto L_0x0050
            if (r0 == 0) goto L_0x0046
            r2 = 495504695(0x1d88cd37, float:3.6211087E-21)
            if (r0 == r2) goto L_0x003d
            r1 = 657346906(0x272e515a, float:2.4191452E-15)
            if (r0 == r1) goto L_0x0033
            goto L_0x0064
        L_0x0033:
            java.lang.String r0 = "StepRefreshTokenFragment"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0064
            r1 = r5
            goto L_0x0065
        L_0x003d:
            java.lang.String r0 = "StepRefreshTokenFailedFragment"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0064
            goto L_0x0065
        L_0x0046:
            java.lang.String r0 = ""
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0064
            r1 = r4
            goto L_0x0065
        L_0x0050:
            java.lang.String r0 = "qr_relogin"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0064
            r1 = 3
            goto L_0x0065
        L_0x005a:
            java.lang.String r0 = "account_not_pair"
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0064
            r1 = 4
            goto L_0x0065
        L_0x0064:
            r1 = -1
        L_0x0065:
            switch(r1) {
                case 0: goto L_0x00af;
                case 1: goto L_0x009c;
                case 2: goto L_0x008f;
                case 3: goto L_0x007c;
                case 4: goto L_0x0069;
                default: goto L_0x0068;
            }
        L_0x0068:
            goto L_0x00bb
        L_0x0069:
            if (r7 == 0) goto L_0x006f
            r6.finish()
            return
        L_0x006f:
            com.xiaomi.micolauncher.module.initialize.steps.StepQRReloginFragment r7 = new com.xiaomi.micolauncher.module.initialize.steps.StepQRReloginFragment
            r7.<init>()
            r6.a(r7)
            java.lang.String r7 = "qr_relogin"
            r6.a = r7
            goto L_0x00bb
        L_0x007c:
            if (r7 == 0) goto L_0x0082
            r6.a()
            return
        L_0x0082:
            com.xiaomi.micolauncher.module.initialize.steps.StepAccountNotPairFragment r7 = new com.xiaomi.micolauncher.module.initialize.steps.StepAccountNotPairFragment
            r7.<init>()
            r6.a(r7)
            java.lang.String r7 = "account_not_pair"
            r6.a = r7
            goto L_0x00bb
        L_0x008f:
            com.xiaomi.micolauncher.module.initialize.steps.StepQRReloginFragment r7 = new com.xiaomi.micolauncher.module.initialize.steps.StepQRReloginFragment
            r7.<init>()
            r6.a(r7)
            java.lang.String r7 = "qr_relogin"
            r6.a = r7
            goto L_0x00bb
        L_0x009c:
            if (r7 == 0) goto L_0x00a2
            r6.a()
            return
        L_0x00a2:
            com.xiaomi.micolauncher.module.initialize.steps.StepRefreshTokenFailedFragment r7 = new com.xiaomi.micolauncher.module.initialize.steps.StepRefreshTokenFailedFragment
            r7.<init>()
            r6.a(r7)
            java.lang.String r7 = "StepRefreshTokenFailedFragment"
            r6.a = r7
            goto L_0x00bb
        L_0x00af:
            com.xiaomi.micolauncher.module.initialize.steps.StepRefreshTokenFragment r7 = new com.xiaomi.micolauncher.module.initialize.steps.StepRefreshTokenFragment
            r7.<init>()
            r6.a(r7)
            java.lang.String r7 = "StepRefreshTokenFragment"
            r6.a = r7
        L_0x00bb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.initialize.BootActivity.nextStep(boolean, java.util.HashMap):void");
    }

    private void a(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        beginTransaction.commitAllowingStateLoss();
    }

    private void a() {
        ActivityLifeCycleManager.getInstance().gotoMainActivity(this);
        finish();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        QuickSettingHelper.enable(this, true);
    }
}
