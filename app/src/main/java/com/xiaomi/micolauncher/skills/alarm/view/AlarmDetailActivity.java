package com.xiaomi.micolauncher.skills.alarm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.base.DialogActivity;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class AlarmDetailActivity extends BaseEventActivity {
    private AlarmRealmObjectBean a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alarm_detail);
        this.a = AlarmModel.getInstance().getAlarm(getIntent().getStringExtra(AlarmModel.KEY_ALARM_ID));
        if (this.a == null) {
            finish();
        } else {
            a();
        }
    }

    private void a() {
        findViewById(R.id.imageButtonBack).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmDetailActivity$2GIVD5a3ZPhXeqg-QhjSC7LjIuA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmDetailActivity.this.b(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.textviewContent);
        TextView textView2 = (TextView) findViewById(R.id.textviewAlarmTime);
        if (this.a.getEvent() != null) {
            textView.setText(this.a.getEvent());
        } else {
            textView.setText("");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(this.a.getLocalDate());
        textView2.setText(String.format(Locale.getDefault(), "%s:%s", TimeCalculator.completedString(instance.get(11)), TimeCalculator.completedString(instance.get(12))));
        TextView textView3 = (TextView) findViewById(R.id.textviewAlarmRepeatType);
        String circleExtra = this.a.getCircleExtra();
        if (circleExtra == null || circleExtra.length() <= 0) {
            textView3.setText(AlarmRealmObjectBean.getCircleCn(this.a.getCircle()));
        } else {
            textView3.setText(AlarmRealmObjectBean.getCircleExtraCn(circleExtra));
        }
        findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmDetailActivity$QiKI7iJcS-BdG_DzpmLeQ1DiANU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmDetailActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        onDeleteClicked();
    }

    protected void onDeleteClicked() {
        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra(DialogActivity.KEY_DIALOG_MESSAGE, getString(R.string.alarm_confirm_delete));
        intent.putExtra(DialogActivity.KEY_DIALOG_CONFIRM_BUTTON, getString(R.string.del));
        intent.putExtra(DialogActivity.KEY_DIALOG_CANCEL_BUTTON, getString(R.string.common_cancel));
        startActivityForResult(intent, 0);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0) {
            switch (i2) {
                case 1:
                    AlarmModel.getInstance().removeAlarm(this.a.getIdStr());
                    finish();
                    return;
                case 2:
                default:
                    return;
            }
        }
    }
}
