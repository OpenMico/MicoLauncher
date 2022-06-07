package com.xiaomi.micolauncher.skills.alarm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.Alerts;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeAudioPlayer;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeMode;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.util.List;

/* loaded from: classes3.dex */
public class HourlyChimeService extends IntentService {
    public static final String BUNDLE = "bundle";
    public static final String DATA = "data";
    public static final int PLAY_TYPE = 2;
    public static final int REQUEST_TYPE = 1;
    public static final String TYPE = "type";
    private int a;
    private HourlyChimeObject b;
    private HourlyChimeAudioPlayer c;

    public HourlyChimeService() {
        super("HourlyChimeService");
    }

    @Override // android.app.IntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
        if (this.c == null) {
            this.c = new HourlyChimeAudioPlayer(MicoApplication.getGlobalContext(), new MultiAudioPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.HourlyChimeService.1
                @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
                public void onPrepared() {
                    L.hourlychime.d("HourlyChimeService onTtsPrepared()");
                }

                @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
                public void onComplete() {
                    L.hourlychime.d("HourlyChimeService onTtsPlayEnd()");
                    HourlyChimeCacheManager.getInstance().clearMusicCache();
                    HourlyChimeMode.getInstance().requestHourlyResource();
                }

                @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
                public void onError(int i, Exception exc) {
                    L.hourlychime.d("HourlyChimeService onTtsError()");
                    HourlyChimeMode.getInstance().requestHourlyResource();
                }
            });
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        HourlyChimeAudioPlayer hourlyChimeAudioPlayer = this.c;
        if (hourlyChimeAudioPlayer != null) {
            hourlyChimeAudioPlayer.release();
            this.c = null;
        }
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra(BUNDLE);
        this.a = bundleExtra.getInt("type");
        this.b = (HourlyChimeObject) bundleExtra.getSerializable("data");
        L.hourlychime.d("HourlyChimeService  type: %d ", Integer.valueOf(this.a));
        if (this.b != null) {
            Logger logger = L.hourlychime;
            logger.d("HourlyChimeService  hourlyChimeObject: %d " + this.b);
        }
        int i = this.a;
        if (i == 1) {
            Event buildEvent = APIUtils.buildEvent(new Alerts.ChimeHourly());
            if (SpeechEventUtil.peekInstance() != null) {
                SpeechEventUtil.getInstance().eventRequest(buildEvent, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.alarm.HourlyChimeService.2
                    @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                    public void onEventResult(List<Instruction> list, Event event) {
                        L.hourlychime.i("HourlyChimeService onEventResult");
                        HourlyChimeCacheManager.getInstance().setHourlyChimeCacheList(list);
                        HourlyChimeMode.getInstance().playHourlyResource();
                    }

                    @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                    public void onEventFail(Event event, boolean z) {
                        L.hourlychime.i("HourlyChimeService onEventFail====");
                        HourlyChimeMode.getInstance().playHourlyResource();
                    }
                });
            }
        } else if (i == 2) {
            ScreenUtil.turnScreenOn(MicoApplication.getGlobalContext());
            List<Integer> hourlyAlarmClock = AlarmModel.getInstance().getHourlyAlarmClock();
            Logger logger2 = L.hourlychime;
            logger2.w("HourlyChimeService hourly: " + hourlyAlarmClock);
            if (VoipModel.getInstance().isVoipActive() || hourlyAlarmClock.contains(Integer.valueOf(this.b.getPosition()))) {
                L.hourlychime.i("HourlyChimeService  notification");
                AlarmEvent alarmEvent = new AlarmEvent();
                alarmEvent.type = AlarmEvent.AlarmType.HOURLY_CHIME;
                alarmEvent.time = String.format(MicoApplication.getGlobalContext().getResources().getString(R.string.hourly_chime_notification), Integer.valueOf(this.b.getPosition()));
                NotificationHelper.notify(MicoApplication.getGlobalContext(), alarmEvent.notificationType(), alarmEvent);
                HourlyChimeMode.getInstance().requestHourlyResource();
                return;
            }
            L.hourlychime.w("HourlyChimeService  hourly chime start play tts");
            this.c.playTTS();
        }
    }
}
