package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.UiEvent;
import com.xiaomi.micolauncher.common.model.ImageSource;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.event.RichMediaInterruptEvent;
import com.xiaomi.micolauncher.skills.personalize.event.RichMediaPlayEndEvent;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class TemplateRichPictureOperation extends BaseOperation<Instruction<Template.RichPicture>> {
    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected boolean supportAsync() {
        return true;
    }

    public TemplateRichPictureOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        AudioPlayer.Stream stream;
        Template.RichPicture richPicture = (Template.RichPicture) this.instruction.getPayload();
        Directive.RichMedia richMedia = new Directive.RichMedia();
        if (richPicture.getImage().isPresent()) {
            Template.Image image = richPicture.getImage().get();
            richMedia.text = image.getDescription();
            List<Template.ImageSource> sources = image.getSources();
            if (ContainerUtil.hasData(sources)) {
                richMedia.image = new ImageSource(sources.get(0));
            }
        }
        if (richPicture.getBgStream().isPresent() && (stream = richPicture.getBgStream().get()) != null) {
            richMedia.stream = new StreamInfo(stream);
        }
        if (richMedia.image == null) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        if (z) {
            register();
        }
        EventBusRegistry.getEventBus().post(new UiEvent.RichPictureEvent(getDialogId(), richMedia));
        return z ? BaseOperation.OpState.STATE_PROCESSING : BaseOperation.OpState.STATE_SUCCESS;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRichMediaPlayEnd(RichMediaPlayEndEvent richMediaPlayEndEvent) {
        L.capability.i("onRichMediaPlayEnd");
        notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRichMediaInterruptEnd(RichMediaInterruptEvent richMediaInterruptEvent) {
        L.capability.i("onRichMediaInterruptEnd");
        notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
    }
}
