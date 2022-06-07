package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.PlaybackController;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor;

/* loaded from: classes3.dex */
public class PlaybackControllerOperation extends BaseOperation {
    public PlaybackControllerOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        String fullName = this.instruction.getFullName();
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        PlaybackControllerInfo a = a(fullName, this.instruction);
        if (a == null) {
            L.ope.i("not support Playback Controller instruction");
            return opState;
        } else if (!a(a)) {
            return b(a);
        } else {
            L.ope.i("ThirdPartAppConsume");
            return BaseOperation.OpState.STATE_SUCCESS;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private PlaybackControllerInfo a(String str, Instruction instruction) {
        char c;
        String dialogId = InstructionUtil.getDialogId(instruction);
        switch (str.hashCode()) {
            case -1990497024:
                if (str.equals(AIApiConstants.PlaybackController.SetProperty)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1954408854:
                if (str.equals(AIApiConstants.PlaybackController.Next)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1954343253:
                if (str.equals(AIApiConstants.PlaybackController.Play)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1954337366:
                if (str.equals(AIApiConstants.PlaybackController.Prev)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1954260497:
                if (str.equals(AIApiConstants.PlaybackController.Seek)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1954245767:
                if (str.equals(AIApiConstants.PlaybackController.Stop)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1315060253:
                if (str.equals(AIApiConstants.PlaybackController.StopAfter)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1234733648:
                if (str.equals(AIApiConstants.PlaybackController.ContinuePlaying)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1171720590:
                if (str.equals(AIApiConstants.PlaybackController.Rewind)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1072757624:
                if (str.equals(AIApiConstants.PlaybackController.DeletePlayingHistory)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -917081083:
                if (str.equals(AIApiConstants.PlaybackController.SkipEnd)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -833516276:
                if (str.equals(AIApiConstants.PlaybackController.SkipStart)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -751535297:
                if (str.equals(AIApiConstants.PlaybackController.StartOver)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -455407265:
                if (str.equals(AIApiConstants.PlaybackController.Pause)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 412115826:
                if (str.equals(AIApiConstants.PlaybackController.FastForward)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 796906630:
                if (str.equals(AIApiConstants.PlaybackController.SetAudioSource)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1868090825:
                if (str.equals(AIApiConstants.PlaybackController.CancelStopAfter)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new PlaybackControllerInfo(PlaybackControl.PLAY, dialogId);
            case 1:
                return new PlaybackControllerInfo(PlaybackControl.CONTINUE_PLAYING, dialogId);
            case 2:
                return new PlaybackControllerInfo(PlaybackControl.PAUSE, dialogId);
            case 3:
                return new PlaybackControllerInfo(PlaybackControl.STOP, dialogId);
            case 4:
                return new PlaybackControllerInfo(PlaybackControl.PREV, dialogId);
            case 5:
                return new PlaybackControllerInfo(PlaybackControl.NEXT, dialogId);
            case 6:
                return new PlaybackControllerInfo(PlaybackControl.START_OVER, dialogId);
            case 7:
                return new PlaybackControllerInfo(PlaybackControl.FAST_FORWARD, dialogId);
            case '\b':
                return new PlaybackControllerInfo(PlaybackControl.REWIND, dialogId);
            case '\t':
                return a(instruction);
            case '\n':
                return b(instruction);
            case 11:
                return new PlaybackControllerInfo(PlaybackControl.CANCEL_STOP_AFTER);
            case '\f':
                return buildSetPropertyPlayback(instruction);
            case '\r':
            case 14:
            case 15:
            case 16:
            default:
                return null;
        }
    }

    private boolean a(PlaybackControllerInfo playbackControllerInfo) {
        IAppCommandProcessor currentProcessor = ThirdPartyAppProxy.getInstance().getCurrentProcessor();
        if (currentProcessor == null || !currentProcessor.supportCommunicate() || !currentProcessor.supportSoundboxControlAction(playbackControllerInfo) || !ThirdPartyAppProxy.getInstance().isThirdPartyAppInForeground(getContext())) {
            return false;
        }
        String asrQuery = getAsrQuery();
        L.video.i("PlaybackControllerOperation communicate,asrQuery:%s", asrQuery);
        currentProcessor.communicate(getContext(), asrQuery);
        return true;
    }

    private BaseOperation.OpState b(PlaybackControllerInfo playbackControllerInfo) {
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        switch (playbackControllerInfo.playbackControl) {
            case SET_PROPERTY:
                if (a()) {
                    BaseOperation nextOperation = OperationManager.getInstance().nextOperation();
                    if (nextOperation instanceof AudioPlayerPlayMusicOperation) {
                        ((AudioPlayerPlayMusicOperation) nextOperation).setPlaybackControllerInfo(playbackControllerInfo);
                    } else if (nextOperation instanceof AudioPlayerPlayOperation) {
                        ((AudioPlayerPlayOperation) nextOperation).setPlaybackControllerInfo(playbackControllerInfo);
                    } else {
                        L.ope.i("set property operation is error");
                    }
                } else {
                    AudioPolicyService.getInstance().playbackController(playbackControllerInfo);
                }
                return BaseOperation.OpState.STATE_SUCCESS;
            case PLAY:
            case CONTINUE_PLAYING:
            case PAUSE:
            case NEXT:
            case PREV:
            case SEEK:
                return c(playbackControllerInfo);
            case STOP:
            case START_OVER:
            case STOP_AFTER:
            case CANCEL_STOP_AFTER:
            case REWIND:
            case FAST_FORWARD:
            case SKIP_START:
            case SKIP_END:
            case SET_AUDIO_SOURCE:
            case DELETE_PLAYING_HISTORY:
                AudioPolicyService.getInstance().playbackController(playbackControllerInfo);
                return BaseOperation.OpState.STATE_SUCCESS;
            default:
                return opState;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.micolauncher.instruciton.base.BaseOperation.OpState c(com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo r5) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.instruciton.impl.PlaybackControllerOperation.c(com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo):com.xiaomi.micolauncher.instruciton.base.BaseOperation$OpState");
    }

    private PlaybackControllerInfo a(Instruction instruction) {
        PlaybackController.Seek seek = (PlaybackController.Seek) instruction.getPayload();
        PlaybackControllerInfo playbackControllerInfo = new PlaybackControllerInfo(PlaybackControl.SEEK);
        playbackControllerInfo.deltaInMs = seek.getDeltaInMs();
        playbackControllerInfo.referenceDef = PlaybackControllerInfo.ReferenceDef.valueOf(seek.getReference().getId());
        playbackControllerInfo.dialogId = InstructionUtil.getDialogId(instruction);
        return playbackControllerInfo;
    }

    private PlaybackControllerInfo b(Instruction instruction) {
        PlaybackController.StopAfter stopAfter = (PlaybackController.StopAfter) instruction.getPayload();
        PlaybackControllerInfo playbackControllerInfo = new PlaybackControllerInfo(PlaybackControl.STOP_AFTER);
        if (stopAfter.getCount().isPresent()) {
            playbackControllerInfo.countOfEnd = stopAfter.getCount().get().intValue();
        }
        if (stopAfter.getCountType().isPresent()) {
            playbackControllerInfo.countType = PlaybackControllerInfo.CountType.valueOf(stopAfter.getCountType().get().getId());
        }
        if (stopAfter.getTimeoutInMs().isPresent()) {
            playbackControllerInfo.timeoutInMs = stopAfter.getTimeoutInMs().get().intValue();
        }
        playbackControllerInfo.dialogId = InstructionUtil.getDialogId(instruction);
        return playbackControllerInfo;
    }

    public static PlaybackControllerInfo buildSetPropertyPlayback(Instruction instruction) {
        PlaybackController.SetProperty setProperty = (PlaybackController.SetProperty) instruction.getPayload();
        PlaybackControllerInfo playbackControllerInfo = new PlaybackControllerInfo(PlaybackControl.SET_PROPERTY);
        playbackControllerInfo.setPropertyName = PlaybackControllerInfo.SetProperty.value(setProperty.getName());
        if (playbackControllerInfo.setPropertyName == PlaybackControllerInfo.SetProperty.LOOP_MODE) {
            playbackControllerInfo.loopMode = LoopType.valueOfType(setProperty.getValue());
        }
        playbackControllerInfo.propertyValue = setProperty.getValue();
        playbackControllerInfo.dialogId = InstructionUtil.getDialogId(instruction);
        return playbackControllerInfo;
    }

    private boolean a() {
        BaseOperation nextOperation = OperationManager.getInstance().nextOperation();
        return nextOperation != null && AIApiConstants.AudioPlayer.Play.equals(nextOperation.fullName());
    }
}
