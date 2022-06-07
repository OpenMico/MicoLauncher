package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.Education;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.module.child.util.ChildHelper;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeDetailEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeRecommendationEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class EductionOperation extends BaseOperation<Instruction<Education.EduShowSearchPage>> {
    public EductionOperation(Instruction<Education.EduShowSearchPage> instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Education.EduShowSearchPage eduShowSearchPage = (Education.EduShowSearchPage) this.instruction.getPayload();
        List<Video.VideoSearchItem> items = eduShowSearchPage.getItems();
        List<Education.EduSearchTag> list = eduShowSearchPage.getTags().get();
        List<Education.EduVideoSearchItem> list2 = eduShowSearchPage.getItemsMoreInfo().get();
        if (ContainerUtil.isEmpty(items)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        List<VideoItem> parseVideos = parseVideos(items, list2);
        if (ContainerUtil.isEmpty(parseVideos)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        if (ContainerUtil.isEmpty(list2)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        L.ope.i("processEduction playList=%s", parseVideos.toString());
        VideoModel.getInstance().setRecommendationList(parseVideos, getDialogId());
        if (parseVideos.size() == 1) {
            EventBusRegistry.getEventBus().post(new VideoInvokeDetailEvent(parseVideos.get(0).getId(), 0));
        } else {
            VideoModel.getInstance().setSearchTagList(list);
            EventBusRegistry.getEventBus().post(new VideoInvokeRecommendationEvent(eduShowSearchPage.getLoadmoreInfo().get()));
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    public static List<VideoItem> parseVideos(List<Video.VideoSearchItem> list, List<Education.EduVideoSearchItem> list2) {
        if (ContainerUtil.isEmpty(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Video.VideoSearchItem videoSearchItem = list.get(i);
            Education.EduVideoSearchItem eduVideoSearchItem = list2.get(i);
            VideoItem videoItem = new VideoItem();
            if (eduVideoSearchItem.getIndex() == i) {
                videoItem.setpCode(ChildHelper.parseVipType(eduVideoSearchItem.getVipType().get()));
            }
            VideoItem translate = videoItem.translate(videoSearchItem);
            if (translate != null) {
                if (!"unknown".equals(translate.getpCode()) || !translate.isVip()) {
                    arrayList.add(translate);
                } else {
                    L.video.i("unknown pcode but is vip ");
                }
            }
        }
        return arrayList;
    }
}
