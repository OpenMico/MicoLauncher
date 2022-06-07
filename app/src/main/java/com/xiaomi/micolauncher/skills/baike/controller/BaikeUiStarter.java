package com.xiaomi.micolauncher.skills.baike.controller;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.player.view.BaikeActivityX08;
import com.xiaomi.micolauncher.skills.baike.controller.uievent.BaikeQueryEvent;
import com.xiaomi.micolauncher.skills.baike.model.BaikeModel;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class BaikeUiStarter {
    private Context a;

    public BaikeUiStarter(Context context) {
        this.a = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaikeQueryEvent(BaikeQueryEvent baikeQueryEvent) {
        Intent intent = new Intent(this.a, BaikeActivityX08.class);
        intent.putExtra("KEY_TEXT_TITLE", baikeQueryEvent.getTitle());
        intent.putExtra("KEY_TEXT_CONTENT", baikeQueryEvent.getContent());
        intent.putExtra(BaikeModel.KEY_IMAGE_URL, baikeQueryEvent.getUrl());
        intent.putExtra("KEY_TO_SPEAK", baikeQueryEvent.getToSpeak());
        intent.putExtra(BaikeModel.KEY_ID, baikeQueryEvent.getDialogId());
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    public static void showBaikeView(String str, String str2, String str3, String str4, String str5) {
        EventBusRegistry.getEventBus().post(new BaikeQueryEvent(str, str2, str3, str4, str5));
    }
}
