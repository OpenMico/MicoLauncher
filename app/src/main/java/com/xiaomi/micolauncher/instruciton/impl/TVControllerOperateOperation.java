package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.TVController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.mitv.MiTvManager;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes3.dex */
public class TVControllerOperateOperation extends BaseOperation<Instruction<TVController.Operate>> {
    public TVControllerOperateOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        TVController.Operate operate = (TVController.Operate) this.instruction.getPayload();
        if (operate.getBluetoothMac().isPresent()) {
            String str = operate.getBluetoothMac().get();
            if (operate.getBleAction().isPresent() && TVController.BLEActionType.BOOT == operate.getBleAction().get() && !TextUtils.isEmpty(str)) {
                MiTvManager.getInstance().openMiTv(str);
                return BaseOperation.OpState.STATE_SUCCESS;
            }
        }
        if (!operate.getAction().isPresent()) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        String str2 = operate.getAction().get();
        if (AbstractCircuitBreaker.PROPERTY_NAME.equals(str2)) {
            MiTvManager.getInstance().wakeUpMiTv(null);
        } else if ("close".equals(str2) || "query".equals(str2)) {
            String str3 = operate.getQuery().isPresent() ? operate.getQuery().get() : "";
            if (!TextUtils.isEmpty(str3)) {
                MiTvManager.getInstance().queryMiTv(str3, str2);
            }
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
