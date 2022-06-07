package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;
import com.xiaomi.micolauncher.skills.openplatform.model.SkillChatItem;
import com.xiaomi.micolauncher.skills.openplatform.view.OpenPlatformChatListActivity;
import java.util.List;

/* loaded from: classes3.dex */
public class FullScreenTemplateDialogueOperation extends BaseOperation<Instruction<FullScreenTemplate.Dialogue>> {
    public FullScreenTemplateDialogueOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Instruction a = a();
        Instruction d = d();
        if (a != null && d == null) {
            b();
            OpenPlatformModel.getInstance().setIsFarewell(false);
        }
        String asrQuery = getAsrQuery();
        if (!TextUtils.isEmpty(asrQuery)) {
            OpenPlatformModel.getInstance().addChatItem(new SkillChatItem(SkillChatItem.Type.SEND, asrQuery));
            OpenPlatformModel.getInstance().notifyUpdateList();
        }
        if (d != null) {
            OpenPlatformModel.getInstance().setIsFarewell(true);
        }
        e();
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private Instruction a() {
        return InstructionUtil.getIntention(OperationManager.getInstance().getInstructions(getDialogId()), AIApiConstants.Dialog.MultipleTurnInProgress);
    }

    private void b() {
        if (!OpenPlatformModel.getInstance().isStarted()) {
            OpenPlatformModel.getInstance().clear();
            OpenPlatformModel.getInstance().setQuitByVoice(true);
            OpenPlatformModel.getInstance().onStart();
            OpenPlatformModel.getInstance().setDefaultText(getContext(), R.string.openplatform_audio);
            c();
        }
    }

    private void c() {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(getContext(), OpenPlatformChatListActivity.class));
    }

    private Instruction d() {
        return InstructionUtil.getIntention(OperationManager.getInstance().getInstructions(getDialogId()), AIApiConstants.Dialog.ExitMultipleTurn);
    }

    private void e() {
        FullScreenTemplate.Dialog data = ((FullScreenTemplate.Dialogue) this.instruction.getPayload()).getData();
        if (!ContainerUtil.isEmpty(data.getMessages())) {
            boolean z = false;
            if (data.getMessages().size() > 1) {
                OpenPlatformModel.getInstance().addMessage(data.getMessages());
                if (data.isOpenMic().isPresent()) {
                    z = data.isOpenMic().get().booleanValue();
                }
                OpenPlatformModel.getInstance().openMic(z);
                if (!OperationManager.getInstance().hasSpeakInstruction(getDialogId())) {
                    OpenPlatformModel.getInstance().processNext();
                    return;
                }
                return;
            }
            FullScreenTemplate.Message message = data.getMessages().get(0);
            if (message.getType() == FullScreenTemplate.MessageType.TTS && message.getTts().isPresent()) {
                FullScreenTemplate.TTS tts = message.getTts().get();
                String str = tts.getUrl().isPresent() ? tts.getUrl().get() : "";
                String str2 = tts.getText().isPresent() ? tts.getText().get() : "";
                if (!TextUtils.isEmpty(str2)) {
                    SpeechManager.getInstance().setSpeakingTts(str2);
                }
                if (!TextUtils.isEmpty(str)) {
                    OpenPlatformModel.getInstance().playTtsUrl(str);
                }
                if (OpenPlatformModel.getInstance().isFarewellPending()) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = OpenPlatformModel.getInstance().getDefaultText();
                    }
                    OpenPlatformModel.getInstance().addChatItem(new SkillChatItem(SkillChatItem.Type.RECV, str2, message.getAvatar()));
                    boolean isQuitByVoice = OpenPlatformModel.getInstance().isQuitByVoice();
                    L.openplatform.i("FullScreenTemplateDialogueOperation quit by voice=%s", Boolean.valueOf(isQuitByVoice));
                    if (isQuitByVoice) {
                        OpenPlatformModel.getInstance().clearMessageList();
                        List<SkillChatItem> chatList = OpenPlatformModel.getInstance().getChatList();
                        if (!chatList.isEmpty() && TextUtils.isEmpty(str)) {
                            OpenPlatformModel.getInstance().prepareQuitSkill(chatList.get(chatList.size() - 1).getContent());
                        }
                    } else {
                        OpenPlatformModel.getInstance().reset();
                    }
                    OpenPlatformModel.getInstance().openMic(false);
                    return;
                }
                if (data.isOpenMic().isPresent()) {
                    z = data.isOpenMic().get().booleanValue();
                }
                OpenPlatformModel.getInstance().openMic(z);
                if (TextUtils.isEmpty(str2)) {
                    str2 = OpenPlatformModel.getInstance().getDefaultText();
                }
                OpenPlatformModel.getInstance().addChatItem(new SkillChatItem(SkillChatItem.Type.RECV, str2, message.getAvatar()));
            }
        }
    }
}
