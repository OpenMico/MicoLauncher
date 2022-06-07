package org.hapjs.features.channel.vui;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.HapChannelManager;
import org.hapjs.features.channel.IHapChannel;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes5.dex */
class a {
    private Map<String, IHapChannel> a = new ConcurrentHashMap();
    private Context b;
    private VuiBridgeManager c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.hapjs.features.channel.vui.a$a  reason: collision with other inner class name */
    /* loaded from: classes5.dex */
    public interface AbstractC0385a {
        void a(ChannelMessage channelMessage);
    }

    public a(VuiBridgeManager vuiBridgeManager) {
        this.c = vuiBridgeManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Context context) {
        this.b = context;
        HapChannelManager.ChannelHandler channelHandler = new HapChannelManager.ChannelHandler() { // from class: org.hapjs.features.channel.vui.a.1
            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public boolean accept(HapApplication hapApplication) {
                String str = hapApplication.mPkgName;
                String str2 = hapApplication.mSignature;
                Log.d("VUI:ChannelImpl", "Check hap app, pkgName:" + str + ", signature:" + str2 + " thread: " + Thread.currentThread().getName());
                return a.this.c.a(str, str2);
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onOpen(IHapChannel iHapChannel) {
                Log.d("VUI:ChannelImpl", "New channel opened, from " + iHapChannel.getHapApplication().mPkgName + " thread: " + Thread.currentThread().getName());
                a.this.a.put(iHapChannel.getHapApplication().mPkgName, iHapChannel);
                a.this.c.a(iHapChannel.getHapApplication().mPkgName);
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onReceiveMessage(final IHapChannel iHapChannel, ChannelMessage channelMessage) {
                String str;
                String str2 = iHapChannel.getHapApplication().mPkgName;
                int i = channelMessage.code;
                Object data = channelMessage.getData();
                if (data instanceof byte[]) {
                    str = Base64.encodeToString((byte[]) data, 2);
                } else {
                    str = String.valueOf(data);
                }
                Log.d("VUI:ChannelImpl", "Receive msg from hap app, pkgName:" + str2 + ", code:" + i + " thread: " + Thread.currentThread().getName());
                a.this.c.a(iHapChannel.getHapApplication().mPkgName, str, new AbstractC0385a() { // from class: org.hapjs.features.channel.vui.a.1.1
                    @Override // org.hapjs.features.channel.vui.a.AbstractC0385a
                    public void a(ChannelMessage channelMessage2) {
                        iHapChannel.send(channelMessage2, null);
                    }
                });
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onClose(IHapChannel iHapChannel, int i, String str) {
                Log.d("VUI:ChannelImpl", "Channel opened by " + iHapChannel.getHapApplication().mPkgName + " closed, code " + i + ", reason:" + str + " thread: " + Thread.currentThread().getName());
                a.this.a.remove(iHapChannel);
                a.this.c.b(iHapChannel.getHapApplication().mPkgName);
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onError(IHapChannel iHapChannel, int i, String str) {
                Log.d("VUI:ChannelImpl", "Channel opened by " + iHapChannel.getHapApplication().mPkgName + " error, errorCode " + i + ", errorMessage:" + str + " thread: " + Thread.currentThread().getName());
            }
        };
        HapChannelManager.get().initialize(this.b);
        HapChannelManager.get().setChannelHandler("vui", channelHandler);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str, ChannelMessage channelMessage, final b bVar) {
        this.a.get(str).send(channelMessage, new EventCallBack() { // from class: org.hapjs.features.channel.vui.a.2
            @Override // org.hapjs.features.channel.listener.EventCallBack
            public void onSuccess() {
                b bVar2 = bVar;
                if (bVar2 != null) {
                    bVar2.a();
                }
            }

            @Override // org.hapjs.features.channel.listener.EventCallBack
            public void onFail() {
                b bVar2 = bVar;
                if (bVar2 != null) {
                    bVar2.b();
                }
            }
        });
    }
}
