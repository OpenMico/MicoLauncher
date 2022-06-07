package com.xiaomi.micolauncher.skills.alarm.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.alarm.AlarmPreviewAudioPlayer;
import com.xiaomi.micolauncher.skills.alarm.VolumeChangeListener;
import com.xiaomi.micolauncher.skills.alarm.adapter.AlarmListAdapter;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmCreationPromptEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmDeleteEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmModifyEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.MidnightEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.TimeSyncEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import java.util.LinkedList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class AlarmListBigScreenActivity extends BaseEventActivity implements VolumeChangeListener {
    RecyclerView a;
    View b;
    private ImageView c;
    private AlarmListAdapter d;
    private a e;
    private AlarmPreviewAudioPlayer f;
    private boolean g;

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alarm_list);
        this.a = (RecyclerView) findViewById(R.id.recyclerViewAlarmList);
        this.b = findViewById(R.id.empty_view);
        TextView textView = (TextView) findViewById(R.id.alarm_list_empty_tip2_view);
        textView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, textView.getPaint().getTextSize() * textView.getText().length(), 0.0f, getColor(R.color.alarm_empty_view_tip2_start_color), getColor(R.color.alarm_empty_view_tip2_end_color), Shader.TileMode.CLAMP));
        a();
        setDefaultScheduleDuration();
        this.f = new AlarmPreviewAudioPlayer();
        this.g = true;
        registerReceiver();
        this.c = (ImageView) findViewById(R.id.back_iv);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmListBigScreenActivity$1uVVI_bbv3AkKE4vI8UEuPZ4RD4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmListBigScreenActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(View view) {
        finish();
    }

    private void a() {
        this.a.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.a.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmListBigScreenActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    AlarmListBigScreenActivity.this.scheduleToClose(BaseActivity.DEFAULT_CLOSE_DURATION);
                } else if (i == 1) {
                    AlarmListBigScreenActivity.this.removeCloseScheduler();
                    if (AlarmListBigScreenActivity.this.d.isEditing()) {
                        AlarmListBigScreenActivity.this.d.update();
                    }
                }
            }
        });
        b();
    }

    private void b() {
        LinkedList<AlarmRealmObjectBean> alarmWillRing = AlarmModel.getInstance().getAlarmWillRing();
        this.d = new AlarmListAdapter(this, alarmWillRing);
        this.a.setAdapter(this.d);
        int i = 0;
        if (alarmWillRing == null || alarmWillRing.size() <= 0) {
            this.b.setVisibility(0);
            return;
        }
        while (true) {
            if (i >= alarmWillRing.size()) {
                break;
            } else if (!alarmWillRing.get(i).isBoom()) {
                this.a.scrollToPosition(i);
                break;
            } else {
                i++;
            }
        }
        this.b.setVisibility(8);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmCreate(AlarmCreationPromptEvent alarmCreationPromptEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmModifyEvent(AlarmModifyEvent alarmModifyEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmDelete(AlarmDeleteEvent alarmDeleteEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmBoomEvent(AlarmBoomEvent alarmBoomEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMidnightEvent(MidnightEvent midnightEvent) {
        L.alarm.d("onMidnightEvent");
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeSyncEvent(TimeSyncEvent timeSyncEvent) {
        L.alarm.d("onTimeSyncEvent");
        b();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayFinishEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        onTtsPlayEnd();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayErrorEvent(TtsPlayErrorEvent ttsPlayErrorEvent) {
        onTtsPlayEnd();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.f.stopPreveiw();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        unregisterReceiver();
        this.f.relasePlayer();
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (!this.onTtsPlayEnd && this.dialogId != null) {
            SpeechManager.getInstance().stopTts();
        }
    }

    public void flushView(boolean z) {
        L.alarm.d("flushView() flag: %b", Boolean.valueOf(z));
        this.g = z;
    }

    public void startPreview() {
        this.f.startPreview(this);
    }

    public void registerReceiver() {
        this.e = new a(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(this.e, intentFilter);
    }

    public void unregisterReceiver() {
        try {
            unregisterReceiver(this.e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.VolumeChangeListener
    public void onVolumeChanged() {
        if (this.g) {
            this.d.notifyDataSetChanged();
        }
    }

    /* loaded from: classes3.dex */
    public static class a extends BroadcastReceiver {
        private final VolumeChangeListener a;

        public a(VolumeChangeListener volumeChangeListener) {
            this.a = volumeChangeListener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            VolumeChangeListener volumeChangeListener;
            L.alarm.d("AlarmListBigScreenActivity onReceive");
            if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction()) && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 4 && (volumeChangeListener = this.a) != null) {
                volumeChangeListener.onVolumeChanged();
            }
        }
    }
}
