package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.baike.controller.BaikeUiStarter;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateImageCardOperation extends BaseOperation<Instruction<Template.ImageCard>> {
    public TemplateImageCardOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Title title;
        Template.ImageCard imageCard = (Template.ImageCard) this.instruction.getPayload();
        String str = "";
        String str2 = "";
        if (imageCard.getTitle().isPresent() && (title = imageCard.getTitle().get()) != null) {
            str = title.getMainTitle();
        }
        Template.Image image = imageCard.getImage();
        if (image != null) {
            List<Template.ImageSource> sources = image.getSources();
            if (!ContainerUtil.isEmpty(sources)) {
                str2 = sources.get(0).getUrl();
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        BaikeUiStarter.showBaikeView(getDialogId(), str, null, str2, "");
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
