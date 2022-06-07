package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Context;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.CustomDirective;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.crash.LogUploader;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.cmcc.ims.controller.uievent.ImsUploadLogEvent;

/* loaded from: classes3.dex */
public class CustomDirectiveOperation extends BaseOperation<Instruction<CustomDirective.ExecuteDeviceSkill>> {
    public CustomDirectiveOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        ObjectNode directive = ((CustomDirective.ExecuteDeviceSkill) this.instruction.getPayload()).getDirective();
        String asText = directive.has("action") ? directive.get("action").asText() : "";
        if ("upload_log".equals(asText)) {
            uploadLog(getContext());
        } else if ("cmcc_upload_log".equals(asText)) {
            uploadCmccLog();
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    public void uploadLog(final Context context) {
        if (context == null) {
            L.base.w("CustomDirectiveOperation uploadLog failed, reason: context is null");
        } else {
            new LogUploader().upload(context, new LogUploader.LogUploadListener() { // from class: com.xiaomi.micolauncher.instruciton.impl.CustomDirectiveOperation.1
                @Override // com.xiaomi.micolauncher.common.crash.LogUploader.LogUploadListener
                public void onSuccess() {
                    SpeechManager.getInstance().ttsRequest(context.getResources().getString(R.string.voip_upload_log_success));
                }

                @Override // com.xiaomi.micolauncher.common.crash.LogUploader.LogUploadListener
                public void onFailed(String str) {
                    L.base.w("CustomDirectiveOperation uploadLog failed, reason:%s", str);
                    SpeechManager.getInstance().ttsRequest(context.getResources().getString(R.string.voip_upload_log_failed));
                }
            });
        }
    }

    public void uploadCmccLog() {
        EventBusRegistry.getEventBus().post(new ImsUploadLogEvent());
    }
}
