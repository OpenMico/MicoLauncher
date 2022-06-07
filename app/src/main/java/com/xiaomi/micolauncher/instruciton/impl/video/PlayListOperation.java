package com.xiaomi.micolauncher.instruciton.impl.video;

import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayListOperation extends BaseVideoPlayerOperation {
    public PlayListOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.impl.video.BaseVideoPlayerOperation
    protected BaseOperation.OpState doProcess(final boolean z) {
        final VideoPlayer.PlayList playList = (VideoPlayer.PlayList) this.instruction.getPayload();
        List<VideoPlayer.Play> items = playList.getItems();
        if (ContainerUtil.hasData(items)) {
            final String dialogId = getDialogId();
            final List<VideoItem> parsePlayList = parsePlayList(items, getAsrQuery(), dialogId);
            if (ContainerUtil.hasData(parsePlayList)) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.video.-$$Lambda$PlayListOperation$DDbu_K4Te5VEWIdKJK481sTdaYs
                    @Override // java.lang.Runnable
                    public final void run() {
                        PlayListOperation.this.a(playList, parsePlayList, dialogId, z);
                    }
                });
                return BaseOperation.OpState.STATE_SUCCESS;
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoPlayer.PlayList playList, List list, String str, boolean z) {
        long currentCmdDuration = PersonalizeExecution.getInstance().getCurrentCmdDuration();
        loadLoadMoreInfo(playList);
        VideoPlayerApi.play(getContext(), list, 0, VideoMode.VIDEO_PLAYER_PLAY_LIST, str, z, currentCmdDuration);
    }

    public static List<VideoItem> parsePlayList(List<VideoPlayer.Play> list, String str, String str2) {
        VideoItem videoItem;
        ArrayList arrayList = new ArrayList();
        for (VideoPlayer.Play play : list) {
            VideoPlayer.VideoStream stream = play.getStream();
            VideoItem videoItem2 = new VideoItem();
            if (play.getLog().isPresent()) {
                videoItem2.log = play.getLog().get();
            }
            if (play.getMeta().isPresent()) {
                videoItem = videoItem2.translate(play.getMeta().get(), str, stream);
            } else {
                videoItem = videoItem2.translate(stream);
            }
            if (videoItem != null) {
                videoItem.originDialogId = str2;
                arrayList.add(videoItem);
            }
        }
        return arrayList;
    }

    public static void loadLoadMoreInfo(VideoPlayer.PlayList playList) {
        if (playList.getLoadmoreToken().isPresent()) {
            VideoModel.getInstance().setLoadMoreToken(playList.getLoadmoreToken().get());
        } else {
            VideoModel.getInstance().setLoadMoreToken("");
        }
        if (playList.isNeedsLoadmore().isPresent()) {
            VideoModel.getInstance().setNeedLoadMore(playList.isNeedsLoadmore().get().booleanValue());
        } else {
            VideoModel.getInstance().setNeedLoadMore(false);
        }
    }
}
