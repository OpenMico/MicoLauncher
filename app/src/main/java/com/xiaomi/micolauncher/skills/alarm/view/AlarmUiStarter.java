package com.xiaomi.micolauncher.skills.alarm.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.common.reflect.TypeToken;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.activitypolicy.ActivityPolicy;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AlarmTtsEvent;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.statemodel.StateModelHelper;
import com.xiaomi.micolauncher.module.lockscreen.manager.WallPaperDataManager;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.StartCountDownEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.countdown.view.CountDownActivity;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.CameraEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AlarmUiStarter {
    private Context a;
    private boolean b = false;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public interface b {
        void a();

        void b();
    }

    public AlarmUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAlarmUi(AlarmBoomEvent alarmBoomEvent) {
        L.alarm.i("show alarm UI %s , top activity %s", alarmBoomEvent, ActivityLifeCycleManager.getInstance().getTopActivity());
        if (StateModelHelper.isStateWithUIGoingOn()) {
            a(alarmBoomEvent);
        } else if (!alarmBoomEvent.isAlarm()) {
            a(0);
        } else if (this.b) {
            a(alarmBoomEvent);
        } else {
            L.alarm.d("AlarmUiStarter : onManualStart, start AlarmBoomActivity.");
            a(new b() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmUiStarter.1
                @Override // com.xiaomi.micolauncher.skills.alarm.view.AlarmUiStarter.b
                public void a() {
                    L.alarm.d("AlarmUiStarter : onNoLastActivity, start AlarmBoomActivity.");
                    AlarmUiStarter.this.a();
                }

                @Override // com.xiaomi.micolauncher.skills.alarm.view.AlarmUiStarter.b
                public void b() {
                    L.alarm.d("AlarmUiStarter : onLastActivityDestroyed, start AlarmBoomActivity.");
                    AlarmUiStarter.this.a();
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmTtsEvent(AlarmTtsEvent alarmTtsEvent) {
        if (alarmTtsEvent.getSource().equals(AudioSource.AUDIO_SOURCE_ALARM_EARTHQUAKE)) {
            L.alarm.i("AlarmUiStarter onAlarmTtsEvent %s", Boolean.valueOf(alarmTtsEvent.isStart()));
            this.b = alarmTtsEvent.isStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        String find = ApiRealmHelper.getInstance().find(WallPaperDataManager.ALARM_AD_URL);
        Intent intent = new Intent(this.a, AlarmBoomActivity.class);
        if (!TextUtils.isEmpty(find)) {
            intent.putExtra(AlarmBoomActivity.KEY_AD_INFO, (Picture.Pictorial) Gsons.getGson().fromJson(find, new TypeToken<Picture.Pictorial>() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmUiStarter.2
            }.getType()));
        }
        ActivityLifeCycleManager.startActivityQuietly(this.a, intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmCreate(AlarmCreationPromptEvent alarmCreationPromptEvent) {
        Intent intent = new Intent(this.a, AlarmCreationPromptActivity.class);
        intent.putExtra(AlarmModel.KEY_ALARM_ID, alarmCreationPromptEvent.alarmItem.getIdStr());
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAlarmUi(StartCountDownEvent startCountDownEvent) {
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        L.alarm.i("StartCountDownEvent show alarm UI %s , top activity %s", startCountDownEvent, topActivity);
        if (topActivity != null && !ActivityPolicy.getInstance().isLongMediaActivity(topActivity) && !ActivityPolicy.getInstance().isHomeActivity(topActivity)) {
            topActivity.finish();
        }
        a(startCountDownEvent.getSeconds());
    }

    private void a(int i) {
        Intent intent = new Intent(this.a, CountDownActivity.class);
        intent.putExtra(CountDownModel.KEY_COUNTDOWN_SECONDS, i);
        ActivityLifeCycleManager.startActivityQuietly(this.a, intent);
        ScreenUtil.turnScreenOn(this.a);
    }

    private void a(b bVar) {
        Activity topActivity = ActivityLifeCycleManager.getInstance().getTopActivity();
        boolean isValidActivity = ActivityLifeCycleManager.isValidActivity(topActivity);
        if (!isValidActivity) {
            L.alarm.w("activity %s is not valid", topActivity);
        }
        if (isValidActivity && (topActivity instanceof CountDownActivity)) {
            topActivity.finish();
            ActivityLifeCycleManager.getInstance().addLifecycleCallbacks(new a(bVar));
        } else if (bVar != null) {
            bVar.a();
        }
    }

    private void a(AlarmBoomEvent alarmBoomEvent) {
        L.alarm.d("showAlarmNotification %s", alarmBoomEvent);
        NotificationHelper.notify(this.a, 2, alarmBoomEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a implements Application.ActivityLifecycleCallbacks {
        private final b a;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        public a(b bVar) {
            this.a = bVar;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            b bVar = this.a;
            if (bVar != null) {
                bVar.b();
            }
            ActivityLifeCycleManager.getInstance().removeLifecycleCallbacks(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmEvent(AlarmEvent alarmEvent) {
        if (VoipModel.getInstance().isVoipActive()) {
            L.voip.d("AlarmUiStarter : onAlarmEvent");
            NotificationHelper.notify(this.a, alarmEvent.notificationType(), alarmEvent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraEvent(CameraEvent cameraEvent) {
        if (VoipModel.getInstance().isVoipActive()) {
            L.voip.d("AlarmUiStarter : onCameraEvent");
            NotificationHelper.notify(this.a, 1);
        }
    }
}
