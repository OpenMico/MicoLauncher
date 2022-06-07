package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AudioPlayerStateChangeEvent;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudioPlayerPlayOperation extends BaseOperation<Instruction<AudioPlayer.Play>> {
    private PlaybackControllerInfo b;

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected boolean supportAsync() {
        return true;
    }

    public AudioPlayerPlayOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        if (!MicoMultiAudioPlayer.getInstance().hasCapability(this.instruction)) {
            return opState;
        }
        if (z) {
            register();
        }
        a();
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$AudioPlayerPlayOperation$UsJSIcv0rdzng-D09XABzC5T9sc
            @Override // java.lang.Runnable
            public final void run() {
                AudioPlayerPlayOperation.this.b();
            }
        });
        return z ? BaseOperation.OpState.STATE_PROCESSING : BaseOperation.OpState.STATE_SUCCESS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        MicoMultiAudioPlayer.getInstance().play(this.instruction);
        if (this.b != null) {
            MicoMultiAudioPlayer.getInstance().setProperty(this.b);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAudioPlayerStateChangeEvent(AudioPlayerStateChangeEvent audioPlayerStateChangeEvent) {
        L.capability.i("AudioPlayerPlayOperation event=%s", audioPlayerStateChangeEvent);
        if (audioPlayerStateChangeEvent.dialogId == null || !audioPlayerStateChangeEvent.dialogId.equalsIgnoreCase(getDialogId())) {
            L.base.i("not equals dialog id");
            return;
        }
        MicoMultiAudioPlayer.AudioState audioState = audioPlayerStateChangeEvent.audioState;
        if (audioState == MicoMultiAudioPlayer.AudioState.ERROR) {
            notifyProcessDone(BaseOperation.OpState.STATE_FAIL);
        } else if (audioState == MicoMultiAudioPlayer.AudioState.STOPPED || audioState == MicoMultiAudioPlayer.AudioState.LIST_FINISH) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }

    public void setPlaybackControllerInfo(PlaybackControllerInfo playbackControllerInfo) {
        this.b = playbackControllerInfo;
    }

    private void a() {
        boolean z;
        Iterator<Instruction> it = OperationManager.getInstance().getInstructions(getDialogId()).iterator();
        while (true) {
            if (it.hasNext()) {
                if (TextUtils.equals(AIApiConstants.Template.Translation, it.next().getFullName())) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            List<AudioPlayer.AudioItemV1> audioItems = ((AudioPlayer.Play) this.instruction.getPayload()).getAudioItems();
            if (ContainerUtil.isEmpty(audioItems)) {
                L.skill.i("AudioPlayerPlayOperation AudioItems is empty, ignore saveUrl4Translation");
                return;
            }
            AudioPlayer.AudioItemV1 audioItemV1 = audioItems.get(0);
            if (audioItemV1 != null) {
                String url = audioItemV1.getStream().getUrl();
                if (!TextUtils.isEmpty(url)) {
                    PreferenceUtils.setSettingString(getContext(), TranslationV2Fragment.KEY_TRANSLATION_SENTENCE_URL, url);
                }
            }
        }
    }
}
