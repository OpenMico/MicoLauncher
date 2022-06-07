package com.xiaomi.micolauncher.instruciton.impl;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.JsonObject;
import com.xiaomi.ai.api.Launcher;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.quickapp.QuickAppHelper;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.personalize.event.ThirdPartAppOpeEvent;
import java.util.LinkedList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.hapjs.features.channel.ChannelMessage;

/* loaded from: classes3.dex */
public class LaunchGeneralQuickAppOperation extends BaseOperation<Instruction<Launcher.LaunchGeneralQuickApp>> implements QuickAppHelper.OnChannelStateChangedListener {
    private volatile LinkedList<String> b;

    public LaunchGeneralQuickAppOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        Launcher.LaunchGeneralQuickApp launchGeneralQuickApp = (Launcher.LaunchGeneralQuickApp) this.instruction.getPayload();
        String pkgName = launchGeneralQuickApp.getPkgName();
        String path = launchGeneralQuickApp.getPath();
        if (TextUtils.isEmpty(path)) {
            return BaseOperation.OpState.STATE_FAIL;
        }
        boolean z2 = false;
        if (QuickAppHelper.isDictionaryApp(pkgName)) {
            a();
            String decode = Uri.decode(path);
            String a = a(launchGeneralQuickApp);
            if (QuickAppHelper.isDicChannelOpened()) {
                ChannelMessage channelMessage = new ChannelMessage();
                L.quickapp.d("isDicChannelOpened:true ,assembleString=%s", a);
                channelMessage.setData(a);
                QuickAppHelper.getDicChannel().send(channelMessage, null);
            } else {
                this.b.add(a);
                L.quickapp.d("isDicChannelOpened:false ,assembleString=%s", a);
                QuickAppHelper.launchQuickAppActivity(decode, this);
                z2 = setOpenAppCmdCountDown();
            }
        } else {
            String decode2 = Uri.decode(path);
            L.quickapp.i("launchQuickApp uri=%s", decode2);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(decode2));
            ActivityLifeCycleManager.startActivityQuietly(intent);
            z2 = setOpenAppCmdCountDown();
        }
        if (!z2) {
            return BaseOperation.OpState.STATE_SUCCESS;
        }
        register();
        return BaseOperation.OpState.STATE_PROCESSING;
    }

    private void a() {
        if (this.b == null) {
            this.b = new LinkedList<>();
        }
    }

    @Override // com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.OnChannelStateChangedListener
    public void onChannelOpened() {
        String poll = this.b.poll();
        if (!TextUtils.isEmpty(poll)) {
            ChannelMessage channelMessage = new ChannelMessage();
            channelMessage.setData(poll);
            channelMessage.code = 0;
            QuickAppHelper.getDicChannel().send(channelMessage, null);
        }
    }

    private String a(Launcher.LaunchGeneralQuickApp launchGeneralQuickApp) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parameters", launchGeneralQuickApp.getParameters().get());
        jsonObject.addProperty("path", launchGeneralQuickApp.getPath());
        return jsonObject.toString();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThirdPartAppOpeTimeOutEvent(ThirdPartAppOpeEvent thirdPartAppOpeEvent) {
        L.capability.i("LaunchGeneralQuickAppOperation onThirdPartAppOpeTimeOutEvent");
        if (thirdPartAppOpeEvent.cmdTimeOut) {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }
}
