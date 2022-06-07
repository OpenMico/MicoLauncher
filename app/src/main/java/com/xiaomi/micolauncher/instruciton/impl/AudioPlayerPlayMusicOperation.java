package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Personalize;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.view_v2.AudioBookPlayListActivity;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudioPlayerPlayMusicOperation extends BaseOperation<Instruction<AudioPlayer.Play>> {
    private AudioPlayer.Play b;
    private Template.PlayInfo c;
    private volatile PlaybackControllerInfo d;
    private boolean e;

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected boolean supportAsync() {
        return true;
    }

    public AudioPlayerPlayMusicOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        this.b = (AudioPlayer.Play) this.instruction.getPayload();
        this.e = z;
        Instruction loadDependentInstrByName = loadDependentInstrByName(AIApiConstants.Template.PlayInfo);
        if (loadDependentInstrByName == null) {
            loadDependentInstrByName = b();
        }
        if (loadDependentInstrByName != null) {
            this.c = (Template.PlayInfo) loadDependentInstrByName.getPayload();
        }
        if (this.b == null || this.c == null) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        Long l = 0L;
        if (z) {
            register();
            Personalize.PersonalCmd currentCmd = PersonalizeExecution.getInstance().getCurrentCmd();
            if (currentCmd != null && currentCmd.getDuration().isPresent()) {
                l = currentCmd.getDuration().get();
            }
            if (l.longValue() > 0 && l.longValue() < 3) {
                return BaseOperation.OpState.STATE_SUCCESS;
            }
        }
        String a = a();
        if (MediaSessionController.getInstance() != null) {
            MediaSessionController.getInstance().playAudioList(getDialogId(), this.b, this.c, a, z, l.longValue());
            if (this.b.getPlayBehavior() != AudioPlayer.PlayBehavior.APPEND && !AudioBookPlayListActivity.isClickRadioList) {
                PlayerApi.showPlayerView(getContext(), false);
            }
        }
        this.b = null;
        this.c = null;
        AudioBookPlayListActivity.isClickRadioList = false;
        return z ? BaseOperation.OpState.STATE_PROCESSING : BaseOperation.OpState.STATE_SUCCESS;
    }

    private String a() {
        L.base.d("playbackControllerInfo1=%s", this.d);
        if (this.d != null && this.d.isSettingLoopMode()) {
            return this.d.loopMode.getId();
        }
        L.base.d("playbackControllerInfo121=%s", this.d);
        return LoopType.LIST_LOOP.getId();
    }

    private Instruction b() {
        return InstructionUtil.getIntention(OperationManager.getInstance().getInstructions(getDialogId()), AIApiConstants.Template.PlayInfo);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected List<String> dependenceInstruction() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AIApiConstants.Template.PlayInfo);
        return arrayList;
    }

    public void setPlaybackControllerInfo(PlaybackControllerInfo playbackControllerInfo) {
        L.base.d("playbackControllerInfo123=%s", playbackControllerInfo);
        this.d = playbackControllerInfo;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onResourcePlayEndEvent(PlayerEvent.OnPlayFinish onPlayFinish) {
        L.ope.i("onResourcePlayEndEvent");
        if (this.e) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onResourcePlayErrorEvent(PlayerEvent.OnPlayError onPlayError) {
        L.ope.i("onResourcePlayErrorEvent");
        if (this.e) {
            notifyProcessDone(BaseOperation.OpState.STATE_FAIL);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAuthInvalidEvent(PlayerEvent.OnMusicAuthInvalid onMusicAuthInvalid) {
        L.ope.i("onAuthInvalidEvent");
        if (this.e) {
            notifyProcessDone(BaseOperation.OpState.STATE_FAIL);
        }
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected void onCancel() {
        PlayerApi.pause();
    }
}
