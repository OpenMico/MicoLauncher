package com.xiaomi.micolauncher.skills.alarm.view;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;

/* loaded from: classes3.dex */
public final class AlarmCreationPromptActivity extends BaseActivity {
    TextView a;
    TextView b;
    TextView c;
    ImageView d;
    ImageView e;
    private Handler f;
    private Runnable g;
    private AlarmRealmObjectBean h;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alarm_creation_prompt);
        this.a = (TextView) findViewById(R.id.textviewContent);
        this.b = (TextView) findViewById(R.id.textViewTime);
        this.c = (TextView) findViewById(R.id.textviewCircle);
        this.d = (ImageView) findViewById(R.id.bg_cover);
        this.e = (ImageView) findViewById(R.id.back_iv);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmCreationPromptActivity$oxkJ_sG6QLh5aUzBpN3XU6_5zWM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmCreationPromptActivity.this.a(view);
            }
        });
        this.f = new Handler();
        this.g = new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$lgIiXB48Qnqf0hHKhimq0YyQi0I
            @Override // java.lang.Runnable
            public final void run() {
                AlarmCreationPromptActivity.this.finish();
            }
        };
        this.h = AlarmModel.getInstance().getAlarm(getIntent().getStringExtra(AlarmModel.KEY_ALARM_ID));
        a();
        SpeechManager.getInstance().uiShow(QueryLatency.AlarmCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    private void a() {
        AlarmRealmObjectBean alarmRealmObjectBean = this.h;
        if (alarmRealmObjectBean != null) {
            this.c.setText(AlarmHelper.getCircleString(this, alarmRealmObjectBean));
            this.b.setText(TimeUtils.getDateTimeInHourAndMinute(this.h.getLocalDate()));
            if (!TextUtils.isEmpty(this.h.getEvent())) {
                this.a.setVisibility(0);
                this.a.setText(this.h.getEvent());
            }
        }
        this.f.postDelayed(this.g, 7000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.f.removeCallbacks(this.g);
    }
}
