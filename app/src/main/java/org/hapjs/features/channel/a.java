package org.hapjs.features.channel;

import android.os.HandlerThread;
import android.os.Message;
import android.os.Messenger;
import org.hapjs.features.channel.HapChannelManager;
import org.hapjs.features.channel.appinfo.AndroidApplication;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.ChannelEventListener;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes5.dex */
class a extends ChannelBase implements IHapChannel {
    private boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(String str, AndroidApplication androidApplication, HapApplication hapApplication, HandlerThread handlerThread, boolean z, String str2) {
        super(androidApplication, hapApplication, handlerThread, str2);
        this.a = z;
        doSetIdAtClient(str);
        doSetIdAtServer(nextChannelId());
        addEventListener(new ChannelEventListener() { // from class: org.hapjs.features.channel.a.1
            @Override // org.hapjs.features.channel.listener.ChannelEventListener
            public void onOpen(IChannel iChannel) {
                HapChannelManager.ChannelHandler channelHandler = HapChannelManager.get().getChannelHandler(iChannel.getType());
                if (channelHandler != null) {
                    channelHandler.onOpen(a.this);
                }
            }

            @Override // org.hapjs.features.channel.listener.ChannelEventListener
            public void onReceiveMessage(IChannel iChannel, ChannelMessage channelMessage) {
                HapChannelManager.ChannelHandler channelHandler = HapChannelManager.get().getChannelHandler(iChannel.getType());
                if (channelHandler != null) {
                    channelHandler.onReceiveMessage(a.this, channelMessage);
                }
            }

            @Override // org.hapjs.features.channel.listener.ChannelEventListener
            public void onClose(IChannel iChannel, int i, String str3) {
                HapChannelManager.ChannelHandler channelHandler = HapChannelManager.get().getChannelHandler(iChannel.getType());
                if (channelHandler != null) {
                    channelHandler.onClose(a.this, i, str3);
                }
            }

            @Override // org.hapjs.features.channel.listener.ChannelEventListener
            public void onError(IChannel iChannel, int i, String str3) {
                HapChannelManager.ChannelHandler channelHandler = HapChannelManager.get().getChannelHandler(iChannel.getType());
                if (channelHandler != null) {
                    channelHandler.onError(a.this, i, str3);
                }
            }
        });
    }

    @Override // org.hapjs.features.channel.ChannelBase
    protected void doOpen(Message message) {
        int status = getStatus();
        if (status != 0) {
            doNotifyError(2, "Fail to open channel, invalid status:" + status);
            return;
        }
        doSetStatus(1);
        doSetMessenger((Messenger) message.obj);
        doSetStatus(2);
    }

    @Override // org.hapjs.features.channel.IHapChannel
    public void close(String str, EventCallBack eventCallBack) {
        close(0, str, true, eventCallBack);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Message message) {
        open(message.replyTo);
    }

    @Override // org.hapjs.features.channel.ChannelBase
    protected String getIdAtReceiver() {
        return getIdAtClient();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        int status = getStatus();
        if (status == 1 || status == 2) {
            close(3, "Remote app died.", false);
        }
    }

    @Override // org.hapjs.features.channel.ChannelBase
    protected boolean isSameProcess() {
        return this.a;
    }
}
