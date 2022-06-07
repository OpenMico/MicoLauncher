package com.xiaomi.micolauncher.instruciton.impl;

import android.app.Activity;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Selector;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.common.VideoPlayerEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.PlayingVideoSelectEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoSelectEvent;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SelectorSelectOperation extends BaseOperation<Instruction<Selector.Select>> {
    public SelectorSelectOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        String fullName = fullName();
        if (((fullName.hashCode() == -848412181 && fullName.equals(AIApiConstants.Selector.Select)) ? (char) 0 : (char) 65535) == 0) {
            Selector.Select select = (Selector.Select) this.instruction.getPayload();
            if (select.getIndex().isPresent()) {
                int intValue = select.getIndex().get().intValue();
                EventBusRegistry.getEventBus().post(new VideoSelectEvent(intValue, getDialogId()));
                if (!a()) {
                    EventBusRegistry.getEventBus().post(new PlayingVideoSelectEvent(intValue));
                }
                boolean openAppCmdCountDown = setOpenAppCmdCountDown();
                L.ope.i("SelectorSelectOperation 是否设置了时长 setAppCmdTimeOut=%s", Boolean.valueOf(openAppCmdCountDown));
                if (!openAppCmdCountDown) {
                    return BaseOperation.OpState.STATE_SUCCESS;
                }
                register();
                return BaseOperation.OpState.STATE_PROCESSING;
            }
        }
        return BaseOperation.OpState.STATE_FAIL;
    }

    boolean a() {
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        if (topActivity != null) {
            return (topActivity instanceof VideoDetailActivity) || (topActivity instanceof VideoRecommendationActivity);
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVideoPlayEndEvent(VideoPlayerEvent videoPlayerEvent) {
        L.ope.i("SelectorSelectOperation onVideoPlayEndEvent");
        if (videoPlayerEvent.getEvent() == VideoPlayerEvent.VideoPlayerStatus.VIDEO_PLAYER_LIST_COMPLETE) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }
}
