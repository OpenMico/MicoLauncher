package com.zhangyue.we.x2c;

import android.content.Context;
import android.view.View;
import com.zhangyue.we.x2c.layouts.X2C127_Apps_Card_Alarm;

/* loaded from: classes4.dex */
public class X2C127_apps_card_alarm implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        return new X2C127_Apps_Card_Alarm().createView(context);
    }
}
