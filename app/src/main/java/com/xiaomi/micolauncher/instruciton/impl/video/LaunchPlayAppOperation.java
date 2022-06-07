package com.xiaomi.micolauncher.instruciton.impl.video;

import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LaunchPlayAppOperation extends BaseVideoPlayerOperation {
    public LaunchPlayAppOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.impl.video.BaseVideoPlayerOperation
    protected BaseOperation.OpState doProcess(final boolean z) {
        L.capability.i("processLaunchPlayApp");
        final VideoPlayer.LaunchPlayApp launchPlayApp = (VideoPlayer.LaunchPlayApp) this.instruction.getPayload();
        List<VideoPlayer.VideoMeta> items = launchPlayApp.getItems();
        if (!ContainerUtil.isEmpty(items)) {
            final String dialogId = getDialogId();
            String asrQuery = getAsrQuery();
            try {
                final int intValue = launchPlayApp.getEpisode().isPresent() ? launchPlayApp.getEpisode().get().intValue() : 0;
                final List<VideoItem> parseVideos = parseVideos(items, asrQuery, getDialogId());
                if (!ContainerUtil.isEmpty(parseVideos)) {
                    ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.video.-$$Lambda$LaunchPlayAppOperation$6ITMPv96SeVpdu94Y0w4zyLU0O8
                        @Override // java.lang.Runnable
                        public final void run() {
                            LaunchPlayAppOperation.this.a(parseVideos, launchPlayApp, intValue, dialogId, z);
                        }
                    });
                    return BaseOperation.OpState.STATE_SUCCESS;
                }
            } catch (Exception e) {
                L.capability.e("processLaunchPlayApp", e);
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    public /* synthetic */ void a(List list, VideoPlayer.LaunchPlayApp launchPlayApp, int i, String str, boolean z) {
        long currentCmdDuration = PersonalizeExecution.getInstance().getCurrentCmdDuration();
        if ("LONG".equalsIgnoreCase(((VideoItem) list.get(0)).lengthType)) {
            VideoModel.getInstance().setMultiCpRecommendationList(list, getDialogId());
            VideoModel.getInstance().isAutoPlay = true;
            VideoModel.getInstance().setCurrentPlayingVideoItem((VideoItem) list.get(0));
            a(VideoModel.getInstance().getPlayIndex());
        } else {
            VideoModel.getInstance().setMultiCpRecommendationList(null, null);
            VideoModel.getInstance().isAutoPlay = false;
        }
        loadLoadMoreInfo(launchPlayApp);
        VideoPlayerApi.play(getContext(), list, i, VideoMode.VIDEO_PLAYER_LAUNCH_PLAY_APP, str, z, currentCmdDuration);
    }

    public static List<VideoItem> parseVideos(List<VideoPlayer.VideoMeta> list, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        for (VideoPlayer.VideoMeta videoMeta : list) {
            VideoItem translate = new VideoItem().translate(videoMeta, str, null);
            if (translate != null) {
                translate.originDialogId = str2;
                arrayList.add(translate);
            }
        }
        return arrayList;
    }

    public static void loadLoadMoreInfo(VideoPlayer.LaunchPlayApp launchPlayApp) {
        if (launchPlayApp.getLoadmoreToken().isPresent()) {
            VideoModel.getInstance().setLoadMoreToken(launchPlayApp.getLoadmoreToken().get());
        } else {
            VideoModel.getInstance().setLoadMoreToken("");
        }
        if (launchPlayApp.isNeedsLoadmore().isPresent()) {
            VideoModel.getInstance().setNeedLoadMore(launchPlayApp.isNeedsLoadmore().get().booleanValue());
        } else {
            VideoModel.getInstance().setNeedLoadMore(false);
        }
    }

    private void a(int i) {
        VideoTrackHelper.postLongVideoPlayTrack(VideoModel.getInstance().getCurrentPlayingVideoItem(), i, VideoTrackHelper.SWITCH_TYPE_DIRECT_PLAY);
    }
}
