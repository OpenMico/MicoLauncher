package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.common.utils.HolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.view.AppSkillHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.view.EntertainmentHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoViewHoldersCache;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager;
import io.reactivex.ObservableEmitter;

/* loaded from: classes3.dex */
public class ViewHoldSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        if (!ChildModeManager.getManager().isChildMode()) {
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$vK5Ea_BPzirSw84eHf7C3zOsGc.INSTANCE);
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$DNjVaz5_n1XPKT9lcG9GBHI9Es.INSTANCE);
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$jlJ10kYEaDHPHX3Y_og7aAdNM.INSTANCE);
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$UEOZaKno66jSuePuiIgNOpP47Jw.INSTANCE);
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$GdPEsajU0PI8pYIoRlbF5h1QpqQ.INSTANCE);
            HolderCacheManager.getManager().initViewHolder($$Lambda$ViewHoldSetup$63htouYBzOqzWvcUtKi_IJXkpus.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(ObservableEmitter observableEmitter) throws Exception {
        MiotViewCachedManager.getInstance().initViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(ObservableEmitter observableEmitter) throws Exception {
        AppSkillHolderCacheManager.getManager().initAppSkillViewHolder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        EntertainmentHolderCacheManager.getManager().initAudiobookView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        VideoViewHoldersCache.getInstance().initVideoHolder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        EntertainmentHolderCacheManager.getManager().initMusicView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        AppSkillHolderCacheManager.getManager().initSkillMyViewHolder();
    }
}
