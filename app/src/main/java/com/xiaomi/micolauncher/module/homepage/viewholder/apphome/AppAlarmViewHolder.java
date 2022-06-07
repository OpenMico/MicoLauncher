package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.GsonUtils;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmDeleteEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmModifyEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Consumer;
import java.util.LinkedList;
import java.util.Optional;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AppAlarmViewHolder extends BaseAppHolder {
    private final TextView a;
    private final TextView b;

    @SuppressLint({"CheckResult"})
    public AppAlarmViewHolder(@NonNull View view) {
        super(view, false);
        this.a = (TextView) view.findViewById(R.id.tvTime);
        this.b = (TextView) view.findViewById(R.id.tvMsg);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult"})
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppAlarmViewHolder$nsIqdIgQcBVO0rSWJJ5PbJkAZeQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppAlarmViewHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        executeAction();
        AppRecordHelper.recordAppClickByMi(this.tabName, this.appInfo.getAppName());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindAppInfo(AppInfo appInfo, String str) {
        super.bindAppInfo(appInfo, str);
        a();
    }

    public void register() {
        EventBusRegistry.getEventBus().register(this);
    }

    public void unRegister() {
        EventBusRegistry.getEventBus().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmDelete(AlarmDeleteEvent alarmDeleteEvent) {
        a();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmModifyEvent(AlarmModifyEvent alarmModifyEvent) {
        a();
    }

    @SuppressLint({"CheckResult"})
    private void a() {
        Observable.create($$Lambda$AppAlarmViewHolder$PUBXtJkymiqZ3OsSmbkpZWxj_1I.INSTANCE).subscribeOn(MicoSchedulers.computation()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppAlarmViewHolder$2aJKIC_T3kZ2AWzyYik7wZb_IT8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppAlarmViewHolder.this.a((Optional) obj);
            }
        }, $$Lambda$AppAlarmViewHolder$G4ZWe0_VWLYbWUga3kklDVv7n4.INSTANCE);
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        try {
            LinkedList<AlarmRealmObjectBean> alarmWillRing = AlarmModel.getInstance().getAlarmWillRing();
            if (ContainerUtil.hasData(alarmWillRing)) {
                observableEmitter.onNext(Optional.of(alarmWillRing.get(0)));
            } else {
                observableEmitter.onNext(Optional.empty());
            }
        } catch (Exception e) {
            e.printStackTrace();
            observableEmitter.onError(e);
        }
        observableEmitter.onComplete();
    }

    public /* synthetic */ void a(Optional optional) throws Exception {
        if (optional.isPresent()) {
            L.alarm.d("recent alarm is %s", GsonUtils.toJson(optional));
            AlarmRealmObjectBean alarmRealmObjectBean = (AlarmRealmObjectBean) optional.get();
            this.a.setText(alarmRealmObjectBean.getTimeStr());
            this.b.setText(alarmRealmObjectBean.getEvent());
            return;
        }
        this.a.setText(R.string.label_alarm);
        this.b.setText(R.string.alarm_tip_empty);
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.alarm.e(th);
    }
}
