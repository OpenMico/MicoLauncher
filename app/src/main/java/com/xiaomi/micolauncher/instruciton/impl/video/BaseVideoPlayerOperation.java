package com.xiaomi.micolauncher.instruciton.impl.video;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseVideoPlayerOperation extends BaseOperation {
    protected abstract BaseOperation.OpState doProcess(boolean z);

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected boolean supportAsync() {
        return true;
    }

    public BaseVideoPlayerOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        BaseOperation.OpState doProcess = doProcess(z);
        if (!z || doProcess != BaseOperation.OpState.STATE_SUCCESS) {
            return doProcess;
        }
        register();
        return BaseOperation.OpState.STATE_PROCESSING;
    }

    VideoItem a(VideoPlayer.Play play) {
        VideoItem videoItem = new VideoItem();
        VideoPlayer.VideoStream stream = play.getStream();
        if (play.getMeta().isPresent()) {
            VideoPlayer.VideoMeta videoMeta = play.getMeta().get();
            if (videoMeta.getName().isPresent()) {
                videoItem.setTitle(videoMeta.getName().get());
            }
            if (videoMeta.getCategory().isPresent()) {
                videoItem.setCategory(videoMeta.getCategory().get());
            }
            if (videoMeta.getHorizontalPoster().isPresent()) {
                List<Template.ImageSource> sources = videoMeta.getHorizontalPoster().get().getSources();
                if (!ContainerUtil.isEmpty(sources)) {
                    videoItem.setImageUrl(sources.get(0).getUrl());
                }
            }
        }
        if (stream == null) {
            return null;
        }
        videoItem.videoContentProvider = new VideoContentProvider();
        videoItem.videoContentProvider.streamInfo = new StreamInfo(stream);
        videoItem.originDialogId = getDialogId();
        return videoItem;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVideoPlayEndEvent(VideoPlayerEvent videoPlayerEvent) {
        L.ope.i("onVideoPlayEndEvent");
        if (videoPlayerEvent.getEvent() == VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_LIST_COMPLETE) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }
}
