package com.xiaomi.micolauncher.skills.weather;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class WeatherUiStarter {
    private Context a;

    public WeatherUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherQueryEvent(WeatherQueryEvent weatherQueryEvent) {
        Intent intent = new Intent(this.a, WeatherViewActivity.class);
        intent.putExtra(BaseEventActivity.KEY_DIALOG_ID, weatherQueryEvent.dialogId);
        ActivityLifeCycleManager.startActivityQuietly(this.a, intent);
    }
}
