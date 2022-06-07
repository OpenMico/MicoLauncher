package com.xiaomi.micolauncher.instruciton.impl;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.JsonSyntaxException;
import com.xiaomi.ai.api.MiotController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.miot.model.MiotModel;
import com.xiaomi.micolauncher.skills.miot.multipledevices.MiotMultipleDevicesSelectActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class MiotControllerOperation extends BaseOperation<Instruction<MiotController.Operate>> {
    public MiotControllerOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        MiotController.Operate operate = (MiotController.Operate) this.instruction.getPayload();
        if (MiotController.DeviceType.CAMERA == operate.getDevice()) {
            MiotController.OpType op = operate.getOp();
            try {
                List<String> ids = ((MiotController.CameraParam) Gsons.getGson().fromJson(operate.getParams().toString(), (Class<Object>) MiotController.CameraParam.class)).getIds();
                if (ids != null) {
                    if (ids.size() >= 1) {
                        a(op, ids.get(0));
                    }
                    return BaseOperation.OpState.STATE_SUCCESS;
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    private void a(final MiotController.OpType opType, final String str) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$MiotControllerOperation$o-VYq6dWp7EOiork-03FH6HRzR0
            @Override // java.lang.Runnable
            public final void run() {
                MiotControllerOperation.this.b(opType, str);
            }
        });
    }

    public /* synthetic */ void b(MiotController.OpType opType, String str) {
        destroyTopMultipleDevicesSelectActivity();
        if (MiotController.OpType.OPEN == opType) {
            MiotModel.getInstance().showCameraActivity(str);
        } else if (MiotController.OpType.CLOSE == opType) {
            Intent intent = new Intent("com.xiaomi.mico.action.smartmiot.camera.close");
            intent.setPackage("com.xiaomi.smarthome");
            getContext().sendBroadcast(intent);
        }
    }

    public static void destroyTopMultipleDevicesSelectActivity() {
        Activity topActivity = MicoApplication.getTopActivity();
        if (topActivity instanceof MiotMultipleDevicesSelectActivity) {
            topActivity.finish();
        } else if (topActivity != null) {
            L.miot.d("activity: %s", topActivity.getLocalClassName());
        }
    }
}
