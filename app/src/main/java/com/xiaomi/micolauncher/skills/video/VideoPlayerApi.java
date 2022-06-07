package com.xiaomi.micolauncher.skills.video;

import android.content.Context;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoFastForwardEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPauseEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayNextEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoRestartEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoRewindEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoSeekEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoStopEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoPlayerApi {
    public static void play(Context context, List<VideoItem> list, int i, VideoMode videoMode, String str, boolean z, long j) {
        L.video.d("VideoPlayerApi play index=%d, handlePlayList=%s ", Integer.valueOf(i), list);
        if (!list.isEmpty()) {
            VideoModel.getInstance().setPlayList(videoMode, list);
            VideoModel.getInstance().setPlayIndex(i);
            VideoModel.getInstance().setDialogId(str);
            VideoModel.getInstance().setDependence(z);
            VideoModel.getInstance().setCmdVideoDuration(j);
            VideoProcessHelper.playVideo(context, list.get(0), i);
        }
    }

    public static void play(Context context, List<VideoItem> list, int i, VideoMode videoMode) {
        play(context, list, i, videoMode, null, false, 0L);
    }

    public static void playMiTv(Context context, String str, String str2, String str3, int i, ChildVideo.MiTvType miTvType) {
        L.video.d("VideoPlayerApi play serialId = %s,   index=%d,  ", str, Integer.valueOf(i));
        VideoItem videoItem = new VideoItem();
        videoItem.setCpType("mitv");
        videoItem.setCp("mitv");
        videoItem.setMediaId(str);
        videoItem.setTitle(str3);
        videoItem.setImageUrl(str2);
        videoItem.setpCode(miTvType.getpCode());
        videoItem.setCi(i + 1);
        VideoModel.getInstance().setDialogId(null);
        VideoModel.getInstance().setDependence(false);
        VideoModel.getInstance().setMiTvType(miTvType);
        VideoProcessHelper.playVideo(context, videoItem, i);
    }

    public static void play() {
        L.video.d("VideoPlayerApi play");
        EventBusRegistry.getEventBus().post(new VideoPlayEvent());
    }

    public static void pause() {
        L.video.d("VideoPlayerApi pause");
        EventBusRegistry.getEventBus().post(new VideoPauseEvent());
    }

    public static void restart() {
        L.video.d("VideoPlayerApi restart");
        EventBusRegistry.getEventBus().post(new VideoRestartEvent());
    }

    public static void prev() {
        L.video.d("VideoPlayerApi prev");
        EventBusRegistry.getEventBus().post(new VideoPlayNextEvent(-2));
    }

    public static void next() {
        L.video.d("VideoPlayerApi next");
        EventBusRegistry.getEventBus().post(new VideoPlayNextEvent(-1));
    }

    public static void stop() {
        L.video.d("VideoPlayerApi stop");
        EventBusRegistry.getEventBus().post(new VideoStopEvent());
    }

    public static void playByIndex(int i) {
        L.video.d("VideoPlayerApi index=%d", Integer.valueOf(i));
        EventBusRegistry.getEventBus().post(new VideoPlayNextEvent(i));
    }

    public static void seekTo(int i) {
        L.video.d("VideoPlayerApi seekTo=%d", Integer.valueOf(i));
        VideoSeekEvent videoSeekEvent = new VideoSeekEvent();
        videoSeekEvent.seconds = i;
        EventBusRegistry.getEventBus().post(videoSeekEvent);
    }

    public static void seekToPos(int i, boolean z) {
        L.video.d("VideoPlayerApi seekToPos=%d", Integer.valueOf(i));
        VideoSeekEvent videoSeekEvent = new VideoSeekEvent();
        videoSeekEvent.pos = i;
        videoSeekEvent.forward = z;
        EventBusRegistry.getEventBus().post(videoSeekEvent);
    }

    public static void forward(long j) {
        VideoFastForwardEvent videoFastForwardEvent = new VideoFastForwardEvent();
        videoFastForwardEvent.seconds = (int) TimeUnit.MILLISECONDS.toSeconds(j);
        EventBusRegistry.getEventBus().post(videoFastForwardEvent);
    }

    public static void backward(long j) {
        VideoRewindEvent videoRewindEvent = new VideoRewindEvent();
        videoRewindEvent.seconds = (int) TimeUnit.MILLISECONDS.toSeconds(j);
        EventBusRegistry.getEventBus().post(videoRewindEvent);
    }
}
