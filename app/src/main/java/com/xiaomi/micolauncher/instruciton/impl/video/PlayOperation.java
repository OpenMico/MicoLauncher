package com.xiaomi.micolauncher.instruciton.impl.video;

import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PlayOperation extends BaseVideoPlayerOperation {
    public PlayOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.impl.video.BaseVideoPlayerOperation
    protected BaseOperation.OpState doProcess(final boolean z) {
        final VideoItem a = a((VideoPlayer.Play) this.instruction.getPayload());
        if (a == null) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.video.-$$Lambda$PlayOperation$FX5lVJZw5rmthfjqjlANN6kv3LM
            @Override // java.lang.Runnable
            public final void run() {
                PlayOperation.this.a(a, z);
            }
        });
        return BaseOperation.OpState.STATE_SUCCESS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoItem videoItem, boolean z) {
        long currentCmdDuration = PersonalizeExecution.getInstance().getCurrentCmdDuration();
        ArrayList arrayList = new ArrayList();
        arrayList.add(videoItem);
        VideoPlayerApi.play(getContext(), arrayList, 0, VideoMode.VIDEO_PLAYER_PLAY, getDialogId(), z, currentCmdDuration);
    }
}
