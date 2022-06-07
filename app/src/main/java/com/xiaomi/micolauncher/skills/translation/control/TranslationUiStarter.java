package com.xiaomi.micolauncher.skills.translation.control;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.CommonSkillFragmentActivity;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StartTranslationUiEvent;
import com.xiaomi.micolauncher.common.event.StartTranslationV2UiEvent;
import com.xiaomi.micolauncher.skills.translation.view.TranslationFragment;
import com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class TranslationUiStarter {
    private Context a;

    public TranslationUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartTranslationEvent(StartTranslationUiEvent startTranslationUiEvent) {
        if (ContainerUtil.isEmpty(startTranslationUiEvent.getSourceText()) || ContainerUtil.isEmpty(startTranslationUiEvent.getTargetText())) {
            L.base.w("invalid translation, do not show");
            return;
        }
        Intent intent = new Intent(this.a, CommonSkillFragmentActivity.class);
        intent.putExtra("fragment", TranslationFragment.class.getName());
        intent.putExtra(TranslationFragment.KEY_SOURCE_TEXT, startTranslationUiEvent.getSourceText());
        intent.putExtra(TranslationFragment.KEY_TARGET_TEXT, startTranslationUiEvent.getTargetText());
        intent.putExtra(TranslationFragment.KEY_DIALOG_ID, startTranslationUiEvent.getDialogId());
        intent.putExtra(TranslationFragment.KEY_AUTO_FINISH, startTranslationUiEvent.shouldAutoFinish());
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartTranslationV2Event(StartTranslationV2UiEvent startTranslationV2UiEvent) {
        Intent intent = new Intent(this.a, CommonSkillFragmentActivity.class);
        intent.putExtra("fragment", TranslationV2Fragment.class.getName());
        intent.putExtra(TranslationV2Fragment.KEY_TRANSLATION_DATA, startTranslationV2UiEvent.translation);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
