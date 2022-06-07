package com.xiaomi.passport.v2.utils;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.PhoneNumKeeperFactory;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import com.xiaomi.phonenum.obtain.PhoneLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActivatorPhoneController {
    private static final String APP_ID = "2882303761517565051";
    private static final long MILLI_TIME_OUT_GET_ACTIVATOR_PHONE = 180000;
    private static final String TAG = "ActivatorPhoneController";
    private SimpleFutureTask<List<ActivatorPhoneInfo>> mGetActivatorPhoneTask;
    PhoneNumKeeper mPhoneNumKeeper;

    public ActivatorPhoneController(Context context) {
        this.mPhoneNumKeeper = new PhoneNumKeeperFactory().createPhoneNumKeeper(context, APP_ID);
        this.mPhoneNumKeeper.setUp(new PhoneNumKeeper.SetupFinishedListener() { // from class: com.xiaomi.passport.v2.utils.ActivatorPhoneController.1
            @Override // com.xiaomi.phonenum.PhoneNumKeeper.SetupFinishedListener
            public void onSetupFinished(Error error) {
                AccountLog.i(ActivatorPhoneController.TAG, SkillStatHelper.SKILL_STAT_SETUP + error.toString());
            }
        });
    }

    public List<ActivatorPhoneInfo> peekPhoneNumLevelData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPhoneNumKeeper.getSlotCount(); i++) {
            this.mPhoneNumKeeper.obtainPhoneNum(i, PhoneLevel.DATA);
            PhoneNum peekPhoneNum = this.mPhoneNumKeeper.peekPhoneNum(i, PhoneLevel.DATA);
            if (!(peekPhoneNum == null || peekPhoneNum.token == null)) {
                arrayList.add(new ActivatorPhoneInfo.Builder().phone(peekPhoneNum.number).phoneHash(peekPhoneNum.numberHash).activatorToken(peekPhoneNum.token).slotId(i).copyWriter(peekPhoneNum.copywriter).operatorLink(peekPhoneNum.operatorLink).build());
            }
        }
        return arrayList;
    }

    public SimpleFutureTask<List<ActivatorPhoneInfo>> getLocalActivatorPhone(final ActivatorPhoneInfoCallback activatorPhoneInfoCallback, final boolean z) {
        if (activatorPhoneInfoCallback != null) {
            this.mGetActivatorPhoneTask = new SimpleFutureTask<>(new Callable<List<ActivatorPhoneInfo>>() { // from class: com.xiaomi.passport.v2.utils.ActivatorPhoneController.3
                @Override // java.util.concurrent.Callable
                public List<ActivatorPhoneInfo> call() throws Exception {
                    int slotCount = ActivatorPhoneController.this.mPhoneNumKeeper.getSlotCount();
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < slotCount; i++) {
                        if (!z) {
                            ActivatorPhoneController.this.mPhoneNumKeeper.invalidatePhoneNum(i);
                        }
                        PhoneNum phoneNum = ActivatorPhoneController.this.mPhoneNumKeeper.obtainPhoneNum(i).get(ActivatorPhoneController.MILLI_TIME_OUT_GET_ACTIVATOR_PHONE, TimeUnit.MILLISECONDS);
                        if (phoneNum.errorCode == 0) {
                            arrayList.add(new ActivatorPhoneInfo.Builder().phone(phoneNum.number).phoneHash(phoneNum.numberHash).activatorToken(phoneNum.token).slotId(i).copyWriter(phoneNum.copywriter).operatorLink(phoneNum.operatorLink).build());
                        } else {
                            AccountLog.w(ActivatorPhoneController.TAG, "getLocalActivatorPhone, slotId=" + i + ", result=" + phoneNum);
                        }
                    }
                    return arrayList;
                }
            }, new SimpleFutureTask.Callback<List<ActivatorPhoneInfo>>() { // from class: com.xiaomi.passport.v2.utils.ActivatorPhoneController.2
                @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
                public void call(SimpleFutureTask<List<ActivatorPhoneInfo>> simpleFutureTask) {
                    try {
                        ActivatorPhoneController.this.handleResult(simpleFutureTask.get(), activatorPhoneInfoCallback);
                    } catch (InterruptedException | ExecutionException e) {
                        AccountLog.e(ActivatorPhoneController.TAG, "getLocalActivatorPhone", e);
                        activatorPhoneInfoCallback.onNone();
                    }
                }
            });
            XiaomiPassportExecutor.getSingleton().execute(this.mGetActivatorPhoneTask);
            return this.mGetActivatorPhoneTask;
        }
        throw new IllegalArgumentException("get phone num callback should not be null");
    }

    public void handleResult(List<ActivatorPhoneInfo> list, ActivatorPhoneInfoCallback activatorPhoneInfoCallback) {
        if (list == null || list.size() == 0) {
            AccountLog.i(TAG, "no inserted phone");
            activatorPhoneInfoCallback.onNone();
            return;
        }
        switch (list.size()) {
            case 0:
                AccountLog.i(TAG, "no activator phone");
                activatorPhoneInfoCallback.onNone();
                return;
            case 1:
                AccountLog.i(TAG, "one activator phone");
                activatorPhoneInfoCallback.onSingleSIM(list.get(0));
                return;
            case 2:
                AccountLog.i(TAG, "two activator phone");
                activatorPhoneInfoCallback.onDualSIM(list.get(0), list.get(1));
                return;
            default:
                throw new RuntimeException("should not happen");
        }
    }

    public void invalidateCachePhoneNum(int i) {
        this.mPhoneNumKeeper.invalidatePhoneNum(i);
    }

    public void cancel() {
        SimpleFutureTask<List<ActivatorPhoneInfo>> simpleFutureTask = this.mGetActivatorPhoneTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mGetActivatorPhoneTask = null;
        }
        this.mPhoneNumKeeper.dispose();
    }
}
