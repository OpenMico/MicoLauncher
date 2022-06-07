package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.module.commandsdk.MiSoundBoxCommandSdkService;

/* loaded from: classes3.dex */
public abstract class SdkBasedAppCommandProcessor extends BaseAppCommandProcessor {
    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportBackward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportCommandSdk() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportForward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportNext() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportPrevious() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportRepeat() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSeek() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSelect() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSetResolution() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportSkipStart() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportStartApp() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void forward(Context context, long j) {
        MiSoundBoxCommandSdkService.getInstance().fastForward(context, j);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void backward(Context context, long j) {
        MiSoundBoxCommandSdkService.getInstance().fastBackward(context, j);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
        MiSoundBoxCommandSdkService.getInstance().pause(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
        MiSoundBoxCommandSdkService.getInstance().resume(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
        MiSoundBoxCommandSdkService.getInstance().stop(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void setResolution(Context context, String str) {
        String a = a(str);
        L.video.d("getPackage=%s,setResolution=%s,resolutionIntStr=%s", getPackage(), str, a);
        MiSoundBoxCommandSdkService.getInstance().setResolution(context, a);
    }

    public String convertResolutionToIntString(Context context, String str) {
        return TextUtils.isEmpty(str) ? "" : str.equals(context.getResources().getString(R.string.video_standard)) ? "1" : str.equals(context.getResources().getString(R.string.video_topspeed)) ? "0" : str.equals(context.getResources().getString(R.string.video_high)) ? "2" : str.equals(context.getResources().getString(R.string.video_very_high)) ? "3" : str.equals(context.getResources().getString(R.string.video_blue_high)) ? Commands.ResolutionValues.BITSTREAM_BLUE_HIGH : str.equalsIgnoreCase(context.getResources().getString(R.string.video_4k)) ? Commands.ResolutionValues.BITSTREAM_4K : str.equals(context.getResources().getString(R.string.video_panoramic_sound)) ? Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND : "";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void next(Context context) {
        MiSoundBoxCommandSdkService.getInstance().next(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void previous(Context context) {
        MiSoundBoxCommandSdkService.getInstance().previous(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void repeat(Context context) {
        MiSoundBoxCommandSdkService.getInstance().repeat(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void select(Context context, int i) {
        MiSoundBoxCommandSdkService.getInstance().select(context, i);
    }

    private String a(String str) {
        return PlaybackControllerInfo.RESOLUTION_SD.equalsIgnoreCase(str) ? "1" : PlaybackControllerInfo.RESOLUTION_FD.equalsIgnoreCase(str) ? "0" : PlaybackControllerInfo.RESOLUTION_HD.equalsIgnoreCase(str) ? "2" : PlaybackControllerInfo.RESOLUTION_FULL_HD.equalsIgnoreCase(str) ? "3" : PlaybackControllerInfo.RESOLUTION_BD.equalsIgnoreCase(str) ? Commands.ResolutionValues.BITSTREAM_BLUE_HIGH : PlaybackControllerInfo.RESOLUTION_4K.equalsIgnoreCase(str) ? Commands.ResolutionValues.BITSTREAM_4K : "";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void skipStart(Context context) {
        MiSoundBoxCommandSdkService.getInstance().skipStart(context);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void seek(Context context, long j) {
        MiSoundBoxCommandSdkService.getInstance().seek(context, j);
    }
}
