package com.xiaomi.micolauncher.skills.video.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Trace;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeDetailEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.BiliAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.QiyiAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.TencentVideoAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.YoukuAppCommandProcessor;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoProcessHelper {
    public static final int INVALID_EPISODE_INDEX = 0;

    public static void playVideo(Context context, VideoItem videoItem) {
        playVideo(context, videoItem, 0);
    }

    public static void playVideo(Context context, VideoItem videoItem, int i) {
        L.video.i("playVideo episodeIndex %s", Integer.valueOf(i));
        if (videoItem != null) {
            L.video.i("playVideo cp_type %s", videoItem.getCp());
            if (videoItem.getSerialId() == 0) {
                videoItem.saveSerialId();
            }
            if (DebugHelper.isDebugVersion()) {
                Trace.beginSection("micoBeginVideo");
            }
            ThirdPartyAppProxy.StartArgs a = a(videoItem, i);
            if (a != null) {
                VideoModel.getInstance().setCurrentPlayingVideoItem(videoItem);
                a.setVideoItem(videoItem);
                ThirdPartyAppProxy.getInstance().startVideo(context, a);
            } else {
                L.video.i("playVideo args is null");
            }
            if (DebugHelper.isDebugVersion()) {
                Trace.endSection();
            }
        }
    }

    private static ThirdPartyAppProxy.StartArgs a(@NonNull final VideoItem videoItem, final int i) {
        String mediaId = videoItem.getMediaId();
        L.video.i("get processor start args, media id = %s, episode index=%s", mediaId, Integer.valueOf(i));
        if (videoItem.isBili()) {
            String b = b(videoItem, i);
            if (ContainerUtil.hasData(mediaId)) {
                Uri buildBiliAppUrl = BiliAppCommandProcessor.buildBiliAppUrl(false, mediaId, b);
                L.video.d("CpType=%s bili uri=%s", videoItem.getCpType(), buildBiliAppUrl);
                return ThirdPartyAppProxy.StartArgs.from("tv.danmaku.bili", buildBiliAppUrl);
            }
            L.video.e("bili wrong arg, no mediaId, %s", videoItem);
            return null;
        } else if (videoItem.isYouku()) {
            return ThirdPartyAppProxy.StartArgs.from("com.youku.iot", VideoInvokeDetailEvent.isValidEpisodeIndex(i) ? YoukuAppCommandProcessor.getUrl(mediaId, i) : YoukuAppCommandProcessor.getUrl(mediaId));
        } else if (videoItem.isIqiyi()) {
            final String b2 = b(videoItem, i);
            return ThirdPartyAppProxy.StartArgs.from("com.qiyi.video.speaker", new ThirdPartyAppProxy.OnCustomStartVideoCallback() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$VideoProcessHelper$YvjEqDXvX2x9Uz8RP9R6qEUt8LI
                @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.OnCustomStartVideoCallback
                public final Intent createIntent() {
                    Intent a;
                    a = VideoProcessHelper.a(VideoItem.this, b2, i);
                    return a;
                }
            });
        } else if (videoItem.isMiTV() || videoItem.isVipKid()) {
            return ThirdPartyAppProxy.StartArgs.from("com.xiaomi.micolauncher", videoItem, i);
        } else {
            if (videoItem.isTencentVideo()) {
                return ThirdPartyAppProxy.StartArgs.from("com.tencent.qqlive.audiobox", VideoInvokeDetailEvent.isValidEpisodeIndex(i) ? TencentVideoAppCommandProcessor.getUrl(mediaId, b(videoItem, i)) : TencentVideoAppCommandProcessor.getUrl(mediaId));
            } else if (videoItem.isMangguoTv()) {
                return ThirdPartyAppProxy.StartArgs.from(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO, VideoInvokeDetailEvent.isValidEpisodeIndex(i) ? MangguoAppCommandProcessor.getUrl(mediaId, b(videoItem, i)) : MangguoAppCommandProcessor.getUrl(mediaId));
            } else {
                L.video.e("Unknown cp %s : video item %s", videoItem.getCp(), videoItem);
                return ThirdPartyAppProxy.StartArgs.from("com.xiaomi.micolauncher", videoItem, i);
            }
        }
    }

    public static /* synthetic */ Intent a(@NonNull VideoItem videoItem, String str, int i) {
        return QiyiAppCommandProcessor.createStartVideoIntent(videoItem.getMediaId(), str, i, 0L);
    }

    private static String b(VideoItem videoItem, int i) {
        List<String> episodeIds = videoItem.getEpisodeIds();
        int i2 = i - 1;
        if (!ContainerUtil.isOutOfBound(i2, episodeIds)) {
            return episodeIds.get(i2);
        }
        JSONObject episodesIds = videoItem.getEpisodesIds();
        if (episodesIds != null) {
            return episodesIds.getString(String.valueOf(Integer.max(1, i)));
        }
        return null;
    }

    public static void processOnActivityResume(BaseActivity baseActivity, boolean z, boolean z2) {
        L.video.i("process activity %s, is ephemeral %s, doNotOpenCameraService=%s", baseActivity, Boolean.valueOf(z), Boolean.valueOf(z2));
        if (!z) {
            if (!z2) {
                a();
            }
            a(baseActivity);
        }
        ThirdPartyAppProxy.getInstance().removeThirdPartyAppProcessor();
    }

    private static void a(final BaseActivity baseActivity) {
        final ThirdPartyAppProxy instance = ThirdPartyAppProxy.getInstance();
        if (!instance.isThirdPartyAppInForeground(baseActivity.getApplication())) {
            L.video.i("no third party app is in foreground, do nothing");
        } else if (!instance.isEverStarted()) {
            L.video.i("third party app never started");
        } else {
            L.video.i("app %s quit right now", instance.getCurrentProcessor());
            Threads.getLightWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.model.-$$Lambda$VideoProcessHelper$dcTnccJgcSvXLQwfIpBTqma_z_I
                @Override // java.lang.Runnable
                public final void run() {
                    VideoProcessHelper.a(ThirdPartyAppProxy.this, baseActivity);
                }
            });
        }
    }

    public static /* synthetic */ void a(ThirdPartyAppProxy thirdPartyAppProxy, BaseActivity baseActivity) {
        thirdPartyAppProxy.stopFromUI(baseActivity);
        thirdPartyAppProxy.quitFromUI(baseActivity);
    }

    private static void a() {
        ThirdPartyAppProxy instance = ThirdPartyAppProxy.getInstance();
        if (instance.isCurrentAppNeedCamera()) {
            L.camera2.i("current processor need camera %s", instance.getCurrentProcessor());
            ThirdPartyAppProxy.getInstance().setCurrentAppNeedCamera(false);
            EventBusRegistry.getEventBus().post(new CameraRelatedSwitchEvent(CameraRelatedSwitchEvent.event.OPEN));
        }
    }
}
