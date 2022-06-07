package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeRealmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;

/* loaded from: classes3.dex */
public class HourlyChimeSetup implements ISetupRule {
    private HourlyChimeRealmHelper a;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.a = new HourlyChimeRealmHelper();
        if (this.a.queryAllHourseNum() <= 0) {
            int i = 0;
            while (i < 24) {
                int i2 = i + 1;
                this.a.addHourse(new HourlyChimeObject(i2, context.getResources().getStringArray(R.array.hourlyChime)[i], i >= 7 && i <= 21, i));
                i = i2;
            }
        }
        this.a.close();
    }
}
