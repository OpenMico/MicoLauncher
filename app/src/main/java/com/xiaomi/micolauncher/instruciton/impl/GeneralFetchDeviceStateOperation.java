package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.General;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;

/* loaded from: classes3.dex */
public class GeneralFetchDeviceStateOperation extends BaseOperation<Instruction<General.FetchDeviceState>> {
    public GeneralFetchDeviceStateOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        a();
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private void a() {
        General.DeviceStateReport deviceStateReport = new General.DeviceStateReport();
        AudioPlayer.PlaybackState audioPlaybackState = SpeechContextHelper.audioPlaybackState();
        deviceStateReport.setAudioPlayerState(audioPlaybackState);
        VideoPlayer.VideoPlaybackState videoPlaybackState = SpeechContextHelper.videoPlaybackState();
        deviceStateReport.setVideoPlayerState(videoPlaybackState);
        boolean z = audioPlaybackState.getStatus() != AudioPlayer.AudioPlayerStatus.PLAYING;
        if (videoPlaybackState.getStatus() == VideoPlayer.VideoPlayerStatus.PLAYING) {
            z = false;
        }
        deviceStateReport.setIsIdle(z);
        SpeechManager.getInstance().sendEventRequest(APIUtils.buildEvent(deviceStateReport));
    }
}
