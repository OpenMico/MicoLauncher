package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeDetailEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeRecommendationEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TemplateVideosOperation extends BaseOperation<Instruction<Template.Videos>> {
    public TemplateVideosOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Template.Videos videos = (Template.Videos) this.instruction.getPayload();
        List<Template.VideoItem> items = videos.getItems();
        if (ContainerUtil.isEmpty(items)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        List<VideoItem> a = a(items, getAsrQuery());
        if (ContainerUtil.isEmpty(a)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        VideoModel.getInstance().setDialogId(getDialogId());
        int i = 0;
        if (!videos.isAutoPlay().isPresent() || !videos.isAutoPlay().get().booleanValue()) {
            VideoModel.getInstance().setRecommendationList(a, getDialogId());
            VideoModel.getInstance().setMultiCpRecommendationList(null, getDialogId());
            VideoModel.getInstance().isAutoPlay = false;
            EventBusRegistry.getEventBus().post(new VideoInvokeRecommendationEvent());
        } else {
            VideoModel.getInstance().setRecommendationList(a, getDialogId());
            VideoModel.getInstance().setMultiCpRecommendationList(a, getDialogId());
            VideoModel.getInstance().isAutoPlay = true;
            if (videos.getEpisode().isPresent()) {
                i = videos.getEpisode().get().intValue();
            }
            EventBusRegistry.getEventBus().post(new VideoInvokeDetailEvent(a.get(i).getId(), i));
            VideoModel.getInstance().setCurrentPlayingVideoItem(a.get(i));
            a(i);
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    private void a(int i) {
        VideoTrackHelper.postLongVideoPlayTrack(VideoModel.getInstance().getCurrentPlayingVideoItem(), i, VideoTrackHelper.SWITCH_TYPE_DIRECT_PLAY);
    }

    private List<VideoItem> a(List<Template.VideoItem> list, String str) {
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Template.VideoItem videoItem : list) {
            VideoItem translate = new VideoItem().translate(videoItem, str);
            if (translate != null) {
                translate.originDialogId = getDialogId();
                arrayList.add(translate);
            }
        }
        return arrayList;
    }
}
