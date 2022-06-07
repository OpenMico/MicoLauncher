package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class TemplateGeneralOperation extends BaseOperation<Instruction<Template.General>> {
    public TemplateGeneralOperation(Instruction instruction) {
        super(instruction);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0053  */
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.xiaomi.micolauncher.instruciton.base.BaseOperation.OpState onProcess(boolean r5) {
        /*
            r4 = this;
            com.xiaomi.ai.api.common.Instruction r5 = r4.instruction
            java.lang.Object r5 = r5.getPayload()
            com.xiaomi.ai.api.Template$General r5 = (com.xiaomi.ai.api.Template.General) r5
            java.util.List r0 = r5.getImages()
            boolean r1 = com.xiaomi.mico.common.ContainerUtil.isEmpty(r0)
            if (r1 != 0) goto L_0x002e
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.xiaomi.ai.api.Template$Image r0 = (com.xiaomi.ai.api.Template.Image) r0
            java.util.List r0 = r0.getSources()
            boolean r2 = com.xiaomi.mico.common.ContainerUtil.isEmpty(r0)
            if (r2 != 0) goto L_0x002e
            java.lang.Object r0 = r0.get(r1)
            com.xiaomi.ai.api.Template$ImageSource r0 = (com.xiaomi.ai.api.Template.ImageSource) r0
            java.lang.String r0 = r0.getUrl()
            goto L_0x002f
        L_0x002e:
            r0 = 0
        L_0x002f:
            java.lang.String r1 = r5.getText()
            java.lang.String r2 = ""
            com.xiaomi.ai.api.Template$Title r3 = r5.getTitle()
            if (r3 == 0) goto L_0x0043
            com.xiaomi.ai.api.Template$Title r5 = r5.getTitle()
            java.lang.String r2 = r5.getMainTitle()
        L_0x0043:
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 != 0) goto L_0x0053
            java.lang.String r5 = r4.getDialogId()
            com.xiaomi.micolauncher.skills.baike.controller.BaikeUiStarter.showBaikeView(r5, r2, r1, r0, r1)
            com.xiaomi.micolauncher.instruciton.base.BaseOperation$OpState r5 = com.xiaomi.micolauncher.instruciton.base.BaseOperation.OpState.STATE_SUCCESS
            return r5
        L_0x0053:
            com.xiaomi.micolauncher.instruciton.base.BaseOperation$OpState r5 = com.xiaomi.micolauncher.instruciton.base.BaseOperation.OpState.STATE_FAIL
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.instruciton.impl.TemplateGeneralOperation.onProcess(boolean):com.xiaomi.micolauncher.instruciton.base.BaseOperation$OpState");
    }
}
