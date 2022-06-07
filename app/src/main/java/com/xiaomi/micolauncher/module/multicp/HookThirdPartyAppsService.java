package com.xiaomi.micolauncher.module.multicp;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;
import com.xiaomi.micolauncher.module.multicp.events.LockViewVisibilityEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.voip.common.Rx;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HookThirdPartyAppsService extends AccessibilityService {
    private String a;
    private FloatingContentProviderView b;
    private volatile boolean c = false;

    @Override // android.accessibilityservice.AccessibilityService
    protected void onServiceConnected() {
        super.onServiceConnected();
        L.accessibility.d("onServiceConnected");
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        a();
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        String charSequence = accessibilityEvent.getPackageName().toString();
        if (!TextUtils.equals(charSequence, this.a)) {
            this.a = charSequence;
            L.accessibility.d("current pkg is %s", this.a);
        }
        if (this.b == null) {
            this.b = new FloatingContentProviderView(MicoApplication.getApp());
        }
        final String findTargetViewId = MultiCpUtils.findTargetViewId(charSequence);
        final String findAnchorViewId = MultiCpUtils.findAnchorViewId(charSequence);
        if (findTargetViewId != null) {
            Logger logger = L.accessibility;
            logger.d("found targetId successfully:" + findTargetViewId);
        }
        if (findTargetViewId == null) {
            if (this.b.isShowing() && MultiCpUtils.isVideoPlayingQuited(true)) {
                this.b.dismiss();
                if (!this.c) {
                    VideoModel.getInstance().setMultiCpRecommendationList(null, null);
                }
            }
            if (MultiCpUtils.isVideoPlayingQuited(false) && !this.c) {
                VideoModel.getInstance().setMultiCpRecommendationList(null, null);
                return;
            }
            return;
        }
        Rx.Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.multicp.-$$Lambda$HookThirdPartyAppsService$548tg3WFA5u-64KrhzANByvye5s
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HookThirdPartyAppsService.this.a(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AccessibilityNodeInfo>() { // from class: com.xiaomi.micolauncher.module.multicp.HookThirdPartyAppsService.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            /* renamed from: a */
            public void onNext(AccessibilityNodeInfo accessibilityNodeInfo) {
                if (accessibilityNodeInfo == null) {
                    L.accessibility.w("rootInActiveWindow is null");
                    return;
                }
                List<AccessibilityNodeInfo> list = null;
                if (findAnchorViewId != null) {
                    Logger logger2 = L.accessibility;
                    logger2.d("found anchorViewId successfully:" + findAnchorViewId);
                    list = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(findAnchorViewId);
                }
                HookThirdPartyAppsService.this.b.setMoreBtnShouldBeVisible(ContainerUtil.hasData(list) && list.get(0).isVisibleToUser());
                if (ContainerUtil.hasData(accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(findTargetViewId))) {
                    if (HookThirdPartyAppsService.this.b.isShowing() || !MultiCpUtils.needShow()) {
                        HookThirdPartyAppsService.this.b.update(false);
                        return;
                    }
                    HookThirdPartyAppsService.this.b.show();
                    HookThirdPartyAppsService.this.b.update(true);
                } else if (HookThirdPartyAppsService.this.b.isShowing()) {
                    HookThirdPartyAppsService.this.b.dismiss();
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                Logger logger2 = L.accessibility;
                logger2.e("getRootInActiveWindow Error:" + th.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(getRootInActiveWindow());
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
        L.accessibility.d("onInterrupt");
        FloatingContentProviderView floatingContentProviderView = this.b;
        if (floatingContentProviderView != null && floatingContentProviderView.isShowing()) {
            this.b.dismiss();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        L.accessibility.d("onDestroy");
        b();
        FloatingContentProviderView floatingContentProviderView = this.b;
        if (floatingContentProviderView != null) {
            if (floatingContentProviderView.isShowing()) {
                this.b.dismiss();
            }
            this.b = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onCollapsedChangedEvent(CollapseEvent collapseEvent) {
        FloatingContentProviderView floatingContentProviderView = this.b;
        if (floatingContentProviderView != null && floatingContentProviderView.isShowing()) {
            this.b.clearDelayedMsg();
            this.b.toggleCollapse(collapseEvent.isCollapsed);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLockedViewChangedEvent(LockViewVisibilityEvent lockViewVisibilityEvent) {
        this.c = lockViewVisibilityEvent.isShowing();
        Logger logger = L.accessibility;
        logger.d("onLockedViewChangedEvent isShowing:" + lockViewVisibilityEvent.isShowing());
    }

    private void a() {
        try {
            if (!EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().register(this);
            }
        } catch (EventBusException e) {
            L.accessibility.w(e);
        }
    }

    private void b() {
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }
}
