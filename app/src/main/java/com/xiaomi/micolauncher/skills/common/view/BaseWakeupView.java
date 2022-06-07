package com.xiaomi.micolauncher.skills.common.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.Priority;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.api.model.WakeGuide;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.player.WakeupPlayer;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.widget.itemdecoration.LinearItemDecoration;
import com.xiaomi.micolauncher.common.widget.itemdecoration.StaggeredGridItemDecoration;
import com.xiaomi.micolauncher.module.battery.EnergySaver;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.lockscreen.manager.WallPaperDataManager;
import com.xiaomi.micolauncher.module.lockscreen.utils.AdHelper;
import com.xiaomi.micolauncher.skills.common.WakeupHelper;
import com.xiaomi.micolauncher.skills.common.WakeupUiEvent;
import com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.micolauncher.skills.wakeup.WakeDataHelper;
import com.xiaomi.micolauncher.skills.wakeup.WakeGuideAdapter;
import com.xiaomi.micolauncher.skills.wakeup.WakeStatUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public abstract class BaseWakeupView extends Dialog implements WakeupHelper {
    protected static String LOG_TAG;
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    protected ImageView adImg;
    private View c;
    private View d;
    private Disposable f;
    private WakeupPlayer g;
    private TextView h;
    private TextView i;
    protected boolean isAdShow;
    private List<WakeGuide.AnswerBean.SuggestionsBean> j;
    private AutoScrollRecyclerView k;
    private WakeGuideAdapter l;
    private RecyclerView.ItemDecoration m;
    protected View mWakeupView;
    private int n;
    private RecyclerView.LayoutManager o;
    private boolean p;
    private WakeGuide.AnswerBean r;
    private long s;
    private boolean t;
    protected WakeupAnimalHelper wakeupAnimalView;
    private boolean q = true;
    LinearSmoothScroller a = new LinearSmoothScroller(getContext()) { // from class: com.xiaomi.micolauncher.skills.common.view.BaseWakeupView.2
        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return Hardware.isBigScreen() ? 20.0f : 25.0f;
        }
    };
    private Handler e = new ViewHandler();

    protected abstract boolean showNlpText(String str);

    protected abstract void textViewReset();

    public BaseWakeupView(@NonNull Context context) {
        super(context, R.style.DialogFullScreen);
        this.mWakeupView = LayoutInflater.from(context).inflate(R.layout.view_wakeup_new, (ViewGroup) null);
        this.g = WakeupPlayer.getInstance(context);
        this.h = (TextView) this.mWakeupView.findViewById(R.id.wake_guide_tv);
        this.i = (TextView) this.mWakeupView.findViewById(R.id.wake_guide_end);
        this.k = (AutoScrollRecyclerView) this.mWakeupView.findViewById(R.id.wake_guide_list);
        this.k.setClipToPadding(false);
        this.n = context.getResources().getDimensionPixelOffset(R.dimen.wake_guide_item_margin);
        this.adImg = (ImageView) this.mWakeupView.findViewById(R.id.ad_img);
        this.c = this.mWakeupView.findViewById(R.id.wake_bg_top1);
        this.d = this.mWakeupView.findViewById(R.id.wake_bg_top2);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger logger = L.speech;
        logger.d(LOG_TAG + "onCreate");
        requestWindowFeature(1);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.animWakeupFade);
            window.setType(2038);
        }
        setCanceledOnTouchOutside(false);
        setContentView(this.mWakeupView);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        Logger logger = L.speech;
        logger.d(LOG_TAG + "onStart");
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 17;
            attributes.width = -1;
            attributes.height = -1;
            window.setAttributes(attributes);
        }
        this.s = e();
        L.speech.i("msg.what=WakeupView.onStart");
        QueryLatency.getInstance().setWakeupViewShowMs();
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void show(boolean z) {
        show(z, false);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void show(boolean z, boolean z2) {
        if (EnergySaver.getInstance(getContext()).isBatteryMode()) {
            getWindow().addFlags(128);
        }
        this.p = z2;
        this.t = false;
        Logger logger = L.speech;
        logger.d(LOG_TAG + "show");
        if (!isShowing()) {
            try {
                super.show();
            } catch (Exception unused) {
                L.wakeup.e("WakeupView show exception");
            }
        }
        WakeupAnimalHelper wakeupAnimalHelper = this.wakeupAnimalView;
        if (wakeupAnimalHelper != null) {
            wakeupAnimalHelper.stepInit();
        }
        showAd();
        if (this.q) {
            initWakeGuide();
        }
        showWakeGuide();
        textViewReset();
        if (z) {
            autoDismiss(10000);
        }
        EventBusRegistry.getEventBus().post(new WakeupUiEvent(0));
        LockScreenSendBroadcast.sendWakeupUiEventBroadcast(MicoApplication.getGlobalContext(), 0);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "dismiss");
        if (isShowing()) {
            this.q = true;
            this.adImg.setVisibility(8);
            WakeupAnimalHelper wakeupAnimalHelper = this.wakeupAnimalView;
            if (wakeupAnimalHelper != null) {
                wakeupAnimalHelper.stepStop();
            }
            finishWakeup();
            dismissWakeGuide();
            this.o = null;
            this.j = null;
            this.k.removeItemDecoration(this.m);
            EventBusRegistry.getEventBus().post(new WakeupUiEvent(3));
            LockScreenSendBroadcast.sendWakeupUiEventBroadcast(MicoApplication.getGlobalContext(), 3);
            WakeStatUtil.recordWakeGuideExit(this.r, e() - this.s, WakeStatUtil.ExitType.AUTO_DISMISS);
            super.dismiss();
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "onWindowFocusChanged");
        if (!z) {
            dismiss();
        }
    }

    protected void cancelAutoDismiss() {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "cancelAutoDismiss");
        this.e.removeMessages(0);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void showAsr(String str, boolean z, boolean z2) {
        if (!isShowing()) {
            Logger logger = L.speech;
            logger.d(LOG_TAG + "setTextContent: ----- has dismissed");
            return;
        }
        L.speech.d("%s showAsr() .isFade=%s .text=%s", LOG_TAG, Boolean.valueOf(z2), str);
        if (z2 || !TextUtils.isEmpty(str)) {
            autoDismiss(10000);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void waitNlp() {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "waitNlp");
        WakeStatUtil.recordWakeGuideExit(this.r, e() - this.s, WakeStatUtil.ExitType.QUERY);
        dismissWakeGuide();
        autoDismiss(8000);
        WakeupAnimalHelper wakeupAnimalHelper = this.wakeupAnimalView;
        if (wakeupAnimalHelper != null) {
            wakeupAnimalHelper.stepSpeakingTransToLoading();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void ttsStart() {
        cancelAutoDismiss();
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void ttsEnd() {
        autoDismiss(300);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void autoDismiss(int i) {
        Logger logger = L.speech;
        logger.d(LOG_TAG + "autoDismiss, ms=" + i);
        if (isShowing()) {
            this.e.removeMessages(0);
            if (i > 0) {
                Handler handler = this.e;
                handler.sendMessageDelayed(handler.obtainMessage(0), i);
                return;
            }
            dismiss();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void stepToTtsAnimation() {
        dismissWakeGuide();
        autoDismiss(8000);
        WakeupAnimalHelper wakeupAnimalHelper = this.wakeupAnimalView;
        if (wakeupAnimalHelper != null) {
            wakeupAnimalHelper.stepLoadingTransToSpeaking();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public boolean showNlp(String str) {
        if (!isShowing()) {
            Logger logger = L.speech;
            logger.d(LOG_TAG + "showNlp: ----- has dismissed");
            return false;
        }
        Logger logger2 = L.speech;
        logger2.d(LOG_TAG + "showNlp: content=" + str);
        autoDismiss(10000);
        if (str.length() > 0) {
            WakeupAnimalHelper wakeupAnimalHelper = this.wakeupAnimalView;
            if (wakeupAnimalHelper != null) {
                wakeupAnimalHelper.stepLoadingTransToSpeaking();
            }
            return showNlpText(str);
        }
        EventBusRegistry.getEventBus().post(new WakeupUiEvent(2));
        LockScreenSendBroadcast.sendWakeupUiEventBroadcast(MicoApplication.getGlobalContext(), 2);
        return false;
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public boolean aiSpeakerTts(String str) {
        if (!isShowing()) {
            super.show();
        }
        textViewReset();
        autoDismiss(10000);
        this.wakeupAnimalView.stepTts();
        return showNlpText(str);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void playWakeup(boolean z, boolean z2) {
        this.g.requestPlayAsync(z, z2);
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void finishWakeup() {
        this.g.requestFinish();
    }

    protected void jump(View view, Picture.Pictorial pictorial) {
        SpeechManager.getInstance().cancelSpeech();
        dismiss();
        AdHelper.jump(view.getContext(), pictorial.adInfo, AdHelper.AdCategory.WAKEUP_AD_CLICK, AdHelper.AdKey.THEME);
    }

    @SuppressLint({"CheckResult"})
    protected void showAd() {
        final Picture.Pictorial wakeAd = WallPaperDataManager.getManager().getWakeAd();
        long settingLong = PreferenceUtils.getSettingLong(getContext(), LOG_TAG, 0L);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(settingLong);
        this.isAdShow = wakeAd != null && !TimeUtils.checkSameDay(Calendar.getInstance(), instance);
        L.wakeguide.d("isAdShow %b", Boolean.valueOf(this.isAdShow));
        if (this.isAdShow) {
            this.adImg.setVisibility(0);
            L.wakeguide.d("ad url %s", wakeAd.url);
            GlideUtils.bindImageViewWithPriority(this.adImg, wakeAd.url, Priority.IMMEDIATE);
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$SnOaYoFPOJXunHxg8Tt7690PK7Y
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    BaseWakeupView.this.a(wakeAd, observableEmitter);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
            RxViewHelp.debounceClicksWithOneSeconds(this.adImg).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$-tZAmg81rh1DmgRTP4F9wLbgPok
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BaseWakeupView.this.a(wakeAd, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Picture.Pictorial pictorial, ObservableEmitter observableEmitter) throws Exception {
        PreferenceUtils.setSettingLong(getContext(), LOG_TAG, System.currentTimeMillis());
        AdHelper.recordAdShow(getContext(), pictorial.adInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Picture.Pictorial pictorial, Object obj) throws Exception {
        jump(this.adImg, pictorial);
    }

    private void a() {
        if (this.o == null) {
            if (!Hardware.isBigScreen() || this.isAdShow) {
                this.o = new LinearLayoutManager(getContext(), 0, false);
                this.m = new LinearItemDecoration(this.n);
            } else {
                this.o = new StaggeredGridLayoutManager(2, 0);
                this.m = new StaggeredGridItemDecoration(this.n);
                ((StaggeredGridLayoutManager) this.o).setGapStrategy(2);
            }
            if (this.k.getItemDecorationCount() == 0) {
                this.k.addItemDecoration(this.m);
            }
            this.k.setLayoutManager(this.o);
        }
        WakeGuideAdapter wakeGuideAdapter = this.l;
        if (wakeGuideAdapter != null && this.q) {
            wakeGuideAdapter.notifyDataSetChanged();
        }
    }

    public void initWakeGuide() {
        L.wakeguide.i("init wake guide");
        a();
        this.k.setHasFixedSize(true);
        this.l = new WakeGuideAdapter(new ArrayList());
        this.k.setAdapter(this.l);
        this.k.setItemAnimator(null);
        this.k.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.micolauncher.skills.common.view.BaseWakeupView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 2) {
                    return false;
                }
                BaseWakeupView.this.autoDismiss(10000);
                return false;
            }
        });
        this.l.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$YVcxKfR_iztujP48X7ci9M7MNvk
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseWakeupView.this.a(baseQuickAdapter, view, i);
            }
        });
        this.l.setNewData(this.j);
        this.f = WakeDataHelper.getHelper().getSuggest(getContext()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$rNDicr82MVeH3BgW3O8dwO1lIrA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseWakeupView.this.a(obj);
            }
        }, $$Lambda$BaseWakeupView$tDyern7OGlr4Tg0x0Kb6_CjHsAI.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        List data = baseQuickAdapter.getData();
        if (!ContainerUtil.isOutOfBound(i, data)) {
            autoDismiss(8000);
            SpeechManager.getInstance().asrFinalResultRequest(((WakeGuide.AnswerBean.SuggestionsBean) data.get(i)).getQuery());
            WakeStatUtil.recordWakeGuideResourceClick(this.r, (WakeGuide.AnswerBean.SuggestionsBean) data.get(i), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        this.r = (WakeGuide.AnswerBean) obj;
        if (!TextUtils.isEmpty(this.r.getModule_title())) {
            this.h.setText(this.r.getModule_title());
        }
        this.j = this.r.getSuggestions();
        L.wakeguide.i("wake guide size %d", Integer.valueOf(ContainerUtil.getSize(this.j)));
        this.l.setNewData(this.j);
        this.l.setAnswerBean(this.r);
        showWakeGuide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.wakeguide.e(th);
    }

    public void showWakeGuide() {
        L.wakeguide.i("show wake guide");
        if (this.p) {
            L.wakeguide.e("isDialogWakeUp set wakeGuide gone");
            b();
        } else if (VoipModel.getInstance().isVoipActive()) {
            L.wakeguide.e("isVoipActive set wakeGuide gone");
            b();
        } else if (ContainerUtil.isEmpty(this.l.getData())) {
            L.wakeguide.e("data is null set wakeGuide gone");
            b();
        } else {
            a();
            d();
            if (!this.isAdShow || !Hardware.isSmallScreen()) {
                ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$FOPCz34sn1dY_hSWhbS2tq9MVAg
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseWakeupView.this.g();
                    }
                }, 500L);
            } else {
                b();
            }
            this.q = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        if (this.t) {
            L.wakeguide.i("wakeupCancel, not show");
        } else {
            c();
        }
    }

    private void b() {
        L.wakeguide.d("hideWakeGuideView");
        this.k.setVisibility(8);
        this.h.setVisibility(8);
        this.i.setVisibility(8);
        this.c.setVisibility(8);
        this.d.setVisibility(8);
    }

    private void c() {
        this.k.setAlpha(0.0f);
        this.h.setAlpha(0.0f);
        this.i.setAlpha(0.0f);
        this.c.setAlpha(0.0f);
        this.d.setAlpha(0.0f);
        this.k.setVisibility(0);
        this.h.setVisibility(0);
        this.i.setVisibility(0);
        this.c.setVisibility(0);
        this.d.setVisibility(0);
        if (this.q) {
            L.wakeguide.d("is first show");
            this.k.animate().alpha(1.0f).setDuration(600L).start();
            this.h.animate().alpha(1.0f).setDuration(600L).start();
            this.i.animate().alpha(1.0f).setDuration(600L).start();
            this.c.animate().alpha(1.0f).setDuration(600L).start();
            this.d.animate().alpha(1.0f).setDuration(600L).start();
        } else {
            L.wakeguide.d("not first show");
            this.k.setAlpha(1.0f);
            this.h.setAlpha(1.0f);
            this.i.setAlpha(1.0f);
            this.c.setAlpha(1.0f);
            this.d.setAlpha(1.0f);
        }
        WakeStatUtil.recordWakeGuideExpose(this.r);
    }

    private void d() {
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.view.-$$Lambda$BaseWakeupView$wqh4cW2tbECwSVppTxYwky-gYEc
            @Override // java.lang.Runnable
            public final void run() {
                BaseWakeupView.this.f();
            }
        }, this.q ? b : 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        int size = ContainerUtil.getSize(this.j) - 1;
        if (size >= 0) {
            this.a.setTargetPosition(size);
            this.o.startSmoothScroll(this.a);
        }
    }

    protected void dismissWakeGuide() {
        L.wakeguide.i("dismiss wake guide");
        Disposable disposable = this.f;
        if (disposable != null && !disposable.isDisposed()) {
            this.f.dispose();
        }
        this.c.animate().cancel();
        this.d.animate().cancel();
        this.k.animate().cancel();
        this.h.animate().cancel();
        this.i.animate().cancel();
        this.c.animate().alpha(0.0f).setDuration(500L).start();
        this.d.animate().alpha(0.0f).setDuration(500L).start();
        this.k.animate().alpha(0.0f).setDuration(500L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.skills.common.view.BaseWakeupView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (BaseWakeupView.this.k.getAlpha() == 0.0f) {
                    BaseWakeupView.this.k.setVisibility(8);
                }
            }
        }).start();
        this.h.animate().alpha(0.0f).setDuration(500L).start();
        this.i.animate().alpha(0.0f).setDuration(500L).start();
    }

    @SuppressLint({"HandlerLeak"})
    /* loaded from: classes3.dex */
    protected class ViewHandler extends Handler {
        private ViewHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                BaseWakeupView.this.dismiss();
            }
            super.handleMessage(message);
        }
    }

    private long e() {
        return SystemClock.uptimeMillis();
    }

    @Override // com.xiaomi.micolauncher.skills.common.WakeupHelper
    public void wakeUpCancel() {
        this.t = true;
    }
}
