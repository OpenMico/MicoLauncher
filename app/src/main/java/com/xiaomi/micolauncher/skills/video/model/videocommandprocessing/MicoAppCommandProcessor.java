package com.xiaomi.micolauncher.skills.video.model.videocommandprocessing;

import android.annotation.SuppressLint;
import android.content.Context;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoFastForwardEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokePlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoRewindEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MicoAppCommandProcessor extends BaseAppCommandProcessor {
    public static final String PACKAGE_NAME_LAUNCHER = "com.xiaomi.micolauncher";
    private final boolean a;

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public String getPackage() {
        return "com.xiaomi.micolauncher";
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void quit(Context context) {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportBackward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportForward() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean supportGesture() {
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

    public MicoAppCommandProcessor(boolean z) {
        this.a = z;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    @SuppressLint({"CheckResult"})
    public void startVideo(Context context, ThirdPartyAppProxy.StartArgs startArgs) {
        VideoItem videoItem = startArgs.getVideoItem();
        VideoModel.getInstance().setLog(videoItem.log);
        int episodeIndex = startArgs.getEpisodeIndex();
        if (ChildModeManager.getManager().allowPlayingVideoOrShowActivity(context) && videoItem.isVipKid()) {
            HashMap<String, Object> hashMap = new HashMap<>(ContainerUtil.getSize(videoItem.getSerialList()) * 2);
            hashMap.put(BaikeModel.KEY_HIDE_RETURN_BUTTON, true);
            VideoModel.getInstance().setPlayList(VideoMode.SERIAL, videoItem.getSerialList());
            VideoModel.getInstance().setPlayIndex(episodeIndex - 1);
            a(hashMap);
        } else if (videoItem.isMiTV()) {
            final HashMap<String, Object> hashMap2 = new HashMap<>(8);
            hashMap2.put(BaikeModel.KEY_HIDE_RETURN_BUTTON, true);
            VideoModel.getInstance().clearPlayList();
            VideoModel.getInstance().setMode(VideoMode.MITV_SERIAL);
            VideoModel.getInstance().setSerialId(videoItem.getMediaId());
            VideoModel.getInstance().setMiTvType(ChildVideo.parseCodeToType(videoItem.getpCode()));
            VideoModel.getInstance().setSerialImageUrl(videoItem.getHorizontalImage());
            if (episodeIndex <= 0) {
                ChildPlayUtil.getPlayIndexFromDb(videoItem.getMediaId(), new ChildPlayUtil.OnPlayIndexLoadListener() { // from class: com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.-$$Lambda$MicoAppCommandProcessor$V7IJlWWRn2yULkmfDmUYtAxDN0Q
                    @Override // com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil.OnPlayIndexLoadListener
                    public final void onPlayIndexLoad(int i) {
                        MicoAppCommandProcessor.this.a(hashMap2, i);
                    }
                });
                return;
            }
            VideoModel.getInstance().setPlayIndex(episodeIndex);
            a(hashMap2);
        } else {
            HashMap<String, Object> hashMap3 = new HashMap<>(ContainerUtil.getSize(videoItem.getSerialList()) * 2);
            hashMap3.put(BaikeModel.KEY_HIDE_RETURN_BUTTON, true);
            a(hashMap3);
        }
    }

    public /* synthetic */ void a(HashMap hashMap, int i) {
        VideoModel.getInstance().setPlayIndex(i);
        a(hashMap);
    }

    private void a(HashMap<String, Object> hashMap) {
        L.video.d("Start video play");
        VideoInvokePlayEvent videoInvokePlayEvent = new VideoInvokePlayEvent();
        videoInvokePlayEvent.params = hashMap;
        EventBusRegistry.getEventBus().post(videoInvokePlayEvent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void play(Context context) {
        VideoPlayerApi.play();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void pause(Context context) {
        VideoPlayerApi.pause();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void forward(Context context, long j) {
        VideoFastForwardEvent videoFastForwardEvent = new VideoFastForwardEvent();
        videoFastForwardEvent.seconds = (int) TimeUnit.MILLISECONDS.toSeconds(j);
        EventBusRegistry.getEventBus().post(videoFastForwardEvent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void backward(Context context, long j) {
        VideoRewindEvent videoRewindEvent = new VideoRewindEvent();
        videoRewindEvent.seconds = (int) TimeUnit.MILLISECONDS.toSeconds(j);
        EventBusRegistry.getEventBus().post(videoRewindEvent);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void stop(Context context) {
        VideoPlayerApi.stop();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void previous(Context context) {
        VideoPlayerApi.prev();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void next(Context context) {
        VideoPlayerApi.next();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public boolean shouldCountTimeOnChildMode() {
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void repeat(Context context) {
        VideoPlayerApi.restart();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BaseAppCommandProcessor, com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor
    public void seek(Context context, long j) {
        VideoPlayerApi.seekTo((int) TimeUnit.MILLISECONDS.toSeconds(j));
    }
}
