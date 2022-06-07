package com.xiaomi.micolauncher.common.schema.control;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.StartAiqiyiUiEvent;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.common.event.UiEvent;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyService;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControl;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.player.view.ScrollTextActivity;
import com.xiaomi.micolauncher.module.miot.MiotMainActivity;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.ancientpoem.AncientPoemActivity;
import com.xiaomi.micolauncher.skills.personalize.view.PersonalizeRichMediaActivity;
import com.xiaomi.micolauncher.skills.video.controller.uievent.PlayingVideoSelectEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.voice.WhiteNoiseActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SchemaUiStarter {
    private Context a;

    public SchemaUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartAiqiyiUiEvent(StartAiqiyiUiEvent startAiqiyiUiEvent) {
        if (ChildModeManager.getManager().allowPlayingVideoOrShowActivity(this.a)) {
            ThirdPartyAppProxy.getInstance().startApp(this.a, "com.qiyi.video.speaker");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartMiotUiEvent(StartMiotUiEvent startMiotUiEvent) {
        if (!Hardware.isBigScreen()) {
            Context context = this.a;
            ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, MiotMainActivity.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartAncientPoemUiEvent(UiEvent.AncientPoemUiEvent ancientPoemUiEvent) {
        Intent intent = new Intent(this.a, AncientPoemActivity.class);
        intent.putExtra(BaseEventActivity.KEY_DIALOG_ID, ancientPoemUiEvent.dialogId);
        intent.putParcelableArrayListExtra(AncientPoemActivity.KEY_POEMS, ancientPoemUiEvent.ancientPoems);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartJokeUiEvent(UiEvent.JokeUiEvent jokeUiEvent) {
        Intent intent = new Intent(this.a, ScrollTextActivity.class);
        intent.putExtra(BaseEventActivity.KEY_DIALOG_ID, jokeUiEvent.dialogId);
        intent.putParcelableArrayListExtra(ScrollTextActivity.KEY_JOKES, jokeUiEvent.jokeItems);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartWhiteNoiseUiEvent(UiEvent.WhiteNoiseEvent whiteNoiseEvent) {
        ActivityLifeCycleManager.startActivityQuietly(WhiteNoiseActivity.getIntent(this.a, whiteNoiseEvent.getDialogId()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRichPictureUiEvent(UiEvent.RichPictureEvent richPictureEvent) {
        ActivityLifeCycleManager.startActivityQuietly(PersonalizeRichMediaActivity.openPersonalizeImageActivity(this.a, richPictureEvent.richMedia, richPictureEvent.dialogId));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoSelectEvent(PlayingVideoSelectEvent playingVideoSelectEvent) {
        PlaybackControllerInfo playbackControllerInfo = new PlaybackControllerInfo(PlaybackControl.SELECTOR);
        playbackControllerInfo.index = playingVideoSelectEvent.index;
        AudioPolicyService.getInstance().playbackController(playbackControllerInfo);
    }
}
