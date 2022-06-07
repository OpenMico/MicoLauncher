package com.xiaomi.micolauncher.skills.alarm.view;

import android.content.Context;
import com.xiaomi.micolauncher.common.event.HourlyChimeQueryEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HourlyChimeUiStarter {
    private Context a;

    public HourlyChimeUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHourlyChimeQueryEvent(HourlyChimeQueryEvent hourlyChimeQueryEvent) {
        HourlyChimeActivity.startActivity(this.a);
    }
}
