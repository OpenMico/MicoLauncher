package com.xiaomi.micolauncher.skills.weather;

import android.content.Intent;
import android.os.Bundle;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.skills.weather.fragment.WeatherBaseFragment;
import com.xiaomi.micolauncher.skills.weather.fragment.WeatherX04Fragment;
import com.xiaomi.micolauncher.skills.weather.fragment.WeatherX08Fragment;

/* loaded from: classes3.dex */
public class WeatherViewActivity extends BaseActivity {
    private WeatherBaseFragment a;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_weather_view);
        a(getIntent().getStringExtra(BaseEventActivity.KEY_DIALOG_ID));
        SpeechManager.getInstance().uiShow(QueryLatency.Weather);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent.getStringExtra(BaseEventActivity.KEY_DIALOG_ID));
        SpeechManager.getInstance().uiShow(QueryLatency.Weather);
    }

    private void a(String str) {
        if (Hardware.isLx04()) {
            this.a = new WeatherX04Fragment();
        } else {
            this.a = new WeatherX08Fragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(BaseEventActivity.KEY_DIALOG_ID, str);
        this.a.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.a).commitAllowingStateLoss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a = null;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        WeatherBaseFragment weatherBaseFragment = this.a;
        if (weatherBaseFragment != null) {
            weatherBaseFragment.onBackPressed();
        }
    }
}
