package com.xiaomi.micolauncher.module;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.StatusBarHelper;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.module.child.view.MaskView;
import com.xiaomi.micolauncher.module.homepage.TabConfigParser;
import com.xiaomi.micolauncher.module.homepage.event.MainPageEffectEvent;
import com.xiaomi.micolauncher.module.homepage.event.MainPageGotoSetEvent;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskAnimationEvent;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskEvent;
import com.xiaomi.micolauncher.module.homepage.event.MainPageVisibilityEvent;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.ChildModeConfigChangedEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class MainPageBigScreen extends BaseMainPage {
    private TabViewPresenter c;
    private final SparseArray<TabConfigParser.ConfigInfo> d = new SparseArray<>();
    private boolean e = ChildModeManager.getManager().isChildMode();
    private View f;
    private MaskView g;
    private AnimatorSet h;
    private AnimatorSet i;
    private FrameLayout j;

    @Override // com.xiaomi.micolauncher.module.BaseMainPage
    public int getLayoutId() {
        return R.layout.activity_main_big_screen;
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MainPageBigScreen(BaseActivity baseActivity) {
        super(baseActivity);
        if (!baseActivity.isNightMode()) {
            StatusBarHelper.setStatusBarMode(baseActivity, !this.e);
        }
        a();
    }

    private void a() {
        this.d.clear();
        TabConfigParser tabConfigParser = TabConfigParser.get(this.activity);
        List<TabConfigParser.ConfigInfo> arrayList = new ArrayList<>();
        try {
            arrayList = tabConfigParser.loadLayout();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            this.d.put(i, arrayList.get(i));
        }
    }

    private void b() {
        int i;
        int i2;
        FrameLayout frameLayout = (FrameLayout) this.activity.findViewById(16908290);
        this.f = this.activity.findViewById(R.id.main_page_mask);
        this.g = (MaskView) this.activity.findViewById(R.id.main_page_animated_mask);
        this.j = (FrameLayout) this.activity.findViewById(R.id.effect_frame_layout);
        View findViewById = this.activity.findViewById(R.id.tabView);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
        if (this.e) {
            frameLayout.setBackgroundColor(this.activity.getColor(R.color.child_mode_bg_color));
            int dimensionPixelSize = this.activity.getResources().getDimensionPixelSize(R.dimen.home_app_tool_bar_width);
            i = this.activity.getResources().getDimensionPixelOffset(R.dimen.home_tab_width_child);
            i2 = dimensionPixelSize - i;
        } else {
            frameLayout.setBackgroundResource(R.drawable.home_page_bg);
            i = this.activity.getResources().getDimensionPixelOffset(R.dimen.home_tab_width);
            i2 = 0;
        }
        marginLayoutParams.width = i;
        marginLayoutParams.setMarginEnd(i2);
        findViewById.setLayoutParams(marginLayoutParams);
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage
    protected void onTimeTick() {
        this.c.timeTick();
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Logger logger = L.homepage;
        logger.d("MainPageBigScreen onCreate : " + this);
        if (!this.e) {
            setVisibility(false);
            Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.-$$Lambda$MainPageBigScreen$GKFrWN_JBOZ_DE2AiYGLikCElA8
                @Override // java.lang.Runnable
                public final void run() {
                    MainPageBigScreen.this.d();
                }
            }, 20000L);
        }
        Logger logger2 = L.homepage;
        logger2.d("MainPageBigScreen onCreate savedInstanceState : " + bundle);
        if (bundle != null) {
            this.c.loadCurrentPositionFromBundle(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        if (getRootView().getVisibility() != 0) {
            setVisibility(true);
            L.exception.e("注意！！！，确认首页显示逻辑是否有异常，导致首页隐藏后无法正常展示。MainPageVisibilityEvent");
        }
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onSaveInstanceState(Bundle bundle) {
        this.c.saveCurrentPositionToBundle(bundle);
    }

    @Override // com.xiaomi.micolauncher.LauncherState
    public boolean onNewIntent(Intent intent) {
        int intExtra = intent.getIntExtra(TabViewPresenter.LAUNCH_TAB_POSITION, -1);
        String stringExtra = intent.getStringExtra("LAUNCH_TAB_CHANGE_SCENE");
        if (intExtra == 0 || "VOIP_NOT_ANSWER_NOTIFICATION_CLICK".equals(stringExtra)) {
            this.c.switchSelectedTabByIntent(intent);
            this.c.onNewIntent(intent);
            return true;
        }
        this.c.onNewIntent(intent);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.BaseMainPage
    public void initViews(BaseActivity baseActivity) {
        super.initViews(baseActivity);
        L.homepage.d("MainPageBigScreen init Views !");
        this.c = new TabViewPresenter(baseActivity, baseActivity.findViewById(R.id.coordinator), this.d);
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendMusicDataChanged(ChildModeConfigChangedEvent childModeConfigChangedEvent) {
        L.homepage.i(" on receive ChildModeConfigChangedEvent");
        ChildModeManager.playChildModeChangedTts(this.activity);
        c();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainPageMaskEvent(MainPageMaskEvent mainPageMaskEvent) {
        L.homepage.i(" on receive MainPageMaskEvent");
        View view = this.f;
        if (view != null) {
            view.setVisibility(mainPageMaskEvent.show ? 0 : 8);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainPageAnimatedMaskEvent(MainPageMaskAnimationEvent mainPageMaskAnimationEvent) {
        if (this.g != null) {
            if (this.h == null) {
                this.h = new AnimatorSet();
                this.h.play(ObjectAnimator.ofFloat(this.g, "alpha", 0.7f));
                this.h.setDuration(300L);
            }
            if (this.i == null) {
                this.i = new AnimatorSet();
                this.i.play(ObjectAnimator.ofFloat(this.g, "alpha", 0.0f));
                this.i.setDuration(300L);
            }
            if (mainPageMaskAnimationEvent.show) {
                this.g.setHollowRect(mainPageMaskAnimationEvent.left, mainPageMaskAnimationEvent.top, mainPageMaskAnimationEvent.right, mainPageMaskAnimationEvent.bottom, mainPageMaskAnimationEvent.radius);
                this.h.start();
                return;
            }
            this.i.start();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMainPageVisibilityEvent(MainPageVisibilityEvent mainPageVisibilityEvent) {
        L.homepage.i(" on receive MainPageVisibilityEvent %d", Integer.valueOf(mainPageVisibilityEvent.state));
        MainPageVisibilityEvent.showMainPage |= mainPageVisibilityEvent.state;
        if (MainPageVisibilityEvent.showMainPage == 3 && getRootView().getVisibility() != 0) {
            setVisibility(true);
            this.activity.getWindow().getDecorView().setBackground(null);
        }
        EventBusRegistry.getEventBus().removeStickyEvent(mainPageVisibilityEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainPageEffectEvent(MainPageEffectEvent mainPageEffectEvent) {
        EffectHelper.effectSetListener(this.j, mainPageEffectEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainPageGotoSetEvent(MainPageGotoSetEvent mainPageGotoSetEvent) {
        SettingOpenManager.openSetting(this.activity, BigSettings.DEFAULT);
    }

    private void c() {
        this.e = ChildModeManager.getManager().isChildMode();
        if (!this.activity.isNightMode()) {
            StatusBarHelper.setStatusBarMode(this.activity, !this.e);
        }
        b();
        a();
        this.c.a();
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onResume() {
        super.onResume();
        if (this.e != ChildModeManager.getManager().isChildMode()) {
            c();
        }
        this.c.setSmartHomeVisibility(true);
        this.c.onResume();
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onPause() {
        super.onPause();
        this.c.setSmartHomeVisibility(false);
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onTrimMemory(int i) {
        this.c.clearCache();
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage, com.xiaomi.micolauncher.LauncherState
    public void onDestroy() {
        super.onDestroy();
        this.c.dispose();
    }

    @Override // com.xiaomi.micolauncher.module.BaseMainPage
    protected void backOutEditStatus() {
        this.c.backOutEditStatus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartMiotUiEvent(StartMiotUiEvent startMiotUiEvent) {
        this.c.showTab(0);
    }
}
