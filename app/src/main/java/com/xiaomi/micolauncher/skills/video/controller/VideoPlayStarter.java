package com.xiaomi.micolauncher.skills.video.controller;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeDetailEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokePlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeRecommendationEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity;
import java.util.HashMap;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class VideoPlayStarter {
    public static final String ACTION = "xiaomi.intent.action.VIDEO_PLAY";
    private Context a;

    public VideoPlayStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void start(VideoInvokePlayEvent videoInvokePlayEvent) {
        HashMap<String, Object> hashMap = videoInvokePlayEvent.params;
        Intent intent = new Intent(ACTION);
        if (hashMap != null) {
            String str = (String) hashMap.get("KEY_TO_SPEAK");
            if (str != null) {
                intent.putExtra("KEY_TO_SPEAK", str);
            }
            Boolean bool = (Boolean) hashMap.get(BaikeModel.KEY_HIDE_RETURN_BUTTON);
            if (bool != null) {
                intent.putExtra(BaikeModel.KEY_HIDE_RETURN_BUTTON, bool.booleanValue());
            }
            Boolean bool2 = (Boolean) hashMap.get(BaikeModel.KEY_HIDE_TITLE);
            if (bool2 != null) {
                intent.putExtra(BaikeModel.KEY_HIDE_TITLE, bool2.booleanValue());
            }
            Boolean bool3 = (Boolean) hashMap.get(BaikeModel.KEY_RESIZE_VIDEO);
            if (bool3 != null) {
                intent.putExtra(BaikeModel.KEY_RESIZE_VIDEO, bool3.booleanValue());
            }
        }
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVideoDetail(VideoInvokeDetailEvent videoInvokeDetailEvent) {
        if (Hardware.isBigScreen()) {
            VideoItem recommendationVideoItem = VideoModel.getInstance().getRecommendationVideoItem(videoInvokeDetailEvent.getVideoId());
            VideoModel.getInstance().setPlayIndex(videoInvokeDetailEvent.getIndex());
            VideoProcessHelper.playVideo(this.a, recommendationVideoItem, videoInvokeDetailEvent.getIndex());
            return;
        }
        Intent intent = new Intent(this.a, VideoDetailActivity.class);
        if (!ActivityUtil.isLauncherInForeground(this.a)) {
            intent.addFlags(402653184);
            L.player.i("added new task and multiple task flag");
        }
        intent.putExtra(VideoModel.KEY_VIDEO_ITEM_ID, videoInvokeDetailEvent.getVideoId());
        int index = videoInvokeDetailEvent.getIndex();
        if (index > 0) {
            intent.putExtra(VideoModel.KEY_VIDEO_INDEX, index);
        }
        L.player.i("ShowVideoDetail index %s, video id %s", Integer.valueOf(videoInvokeDetailEvent.getIndex()), videoInvokeDetailEvent.getVideoId());
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVideoInvokeRecommendationEvent(VideoInvokeRecommendationEvent videoInvokeRecommendationEvent) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.a, VideoRecommendationActivity.class));
    }
}
