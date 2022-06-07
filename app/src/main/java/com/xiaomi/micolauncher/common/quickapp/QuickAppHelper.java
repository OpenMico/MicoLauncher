package com.xiaomi.micolauncher.common.quickapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonParser;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.hapjs.features.channel.ChannelMessage;
import org.hapjs.features.channel.HapChannelManager;
import org.hapjs.features.channel.IHapChannel;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.features.channel.listener.EventCallBack;

/* loaded from: classes3.dex */
public class QuickAppHelper {
    public static final String DEEP_LINK_FORMAT = "%s?querySeq=%s";
    private static WeakReference<OnChannelStateChangedListener> b;
    private static final Map<String, IHapChannel> a = new ConcurrentHashMap();
    public static final AtomicInteger seq = new AtomicInteger(0);
    private static final JsonParser c = new JsonParser();

    /* loaded from: classes3.dex */
    public interface OnChannelStateChangedListener {
        void onChannelOpened();
    }

    public static void initChannels(Context context) {
        HapChannelManager.DefaultChannelHandler defaultChannelHandler = new HapChannelManager.DefaultChannelHandler("com.xiaomi.aiot.soundbox_dictionary.mi", "6d3e6a3dcbdadb0aa94f64e33a36b01254cb6ef7e14be282d885cd95aeb952e0", "80166f59d0a983e6ffd122c383c7a9e97ceb10ee629cbc29d796a489c1810317") { // from class: com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.1
            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onOpen(IHapChannel iHapChannel) {
                L.quickapp.d("QuickAppHelper New channel opened, from %s thread: %s", iHapChannel.getHapApplication().mPkgName, Thread.currentThread().getName());
                QuickAppHelper.a.put(iHapChannel.getHapApplication().mPkgName, new ChannelImpl(iHapChannel));
                synchronized (QuickAppHelper.class) {
                    if (QuickAppHelper.b == null || QuickAppHelper.b.get() == null) {
                        L.quickapp.d("ChannelStateChangedListener not initialized or be null!");
                    } else {
                        ((OnChannelStateChangedListener) QuickAppHelper.b.get()).onChannelOpened();
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:20:0x007b  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x00a0  */
            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceiveMessage(org.hapjs.features.channel.IHapChannel r9, org.hapjs.features.channel.ChannelMessage r10) {
                /*
                    r8 = this;
                    org.hapjs.features.channel.appinfo.HapApplication r0 = r9.getHapApplication()
                    java.lang.String r0 = r0.mPkgName
                    int r1 = r10.code
                    java.lang.Object r10 = r10.getData()
                    boolean r2 = r10 instanceof byte[]
                    r3 = 2
                    if (r2 == 0) goto L_0x0018
                    byte[] r10 = (byte[]) r10
                    java.lang.String r10 = android.util.Base64.encodeToString(r10, r3)
                    goto L_0x001c
                L_0x0018:
                    java.lang.String r10 = java.lang.String.valueOf(r10)
                L_0x001c:
                    boolean r0 = com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.isDictionaryApp(r0)
                    if (r0 == 0) goto L_0x0072
                    r0 = -10
                    if (r0 != r1) goto L_0x0072
                    com.xiaomi.ai.api.Template$ResolveWords r0 = new com.xiaomi.ai.api.Template$ResolveWords
                    r0.<init>()
                    r2 = 0
                    com.google.gson.JsonParser r4 = com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.c()     // Catch: JsonSyntaxException -> 0x0050
                    com.google.gson.JsonElement r4 = r4.parse(r10)     // Catch: JsonSyntaxException -> 0x0050
                    com.google.gson.JsonObject r4 = r4.getAsJsonObject()     // Catch: JsonSyntaxException -> 0x0050
                    java.lang.String r5 = "id"
                    com.google.gson.JsonElement r5 = r4.get(r5)     // Catch: JsonSyntaxException -> 0x0050
                    java.lang.String r5 = r5.getAsString()     // Catch: JsonSyntaxException -> 0x0050
                    java.lang.String r6 = "words"
                    com.google.gson.JsonElement r4 = r4.get(r6)     // Catch: JsonSyntaxException -> 0x004e
                    java.lang.String r2 = r4.getAsString()     // Catch: JsonSyntaxException -> 0x004e
                    goto L_0x0057
                L_0x004e:
                    r4 = move-exception
                    goto L_0x0052
                L_0x0050:
                    r4 = move-exception
                    r5 = r2
                L_0x0052:
                    com.elvishew.xlog.Logger r6 = com.xiaomi.micolauncher.common.L.quickapp
                    r6.d(r4)
                L_0x0057:
                    r0.setWords(r2)
                    r0.setId(r5)
                    com.xiaomi.ai.api.Template$ResolveWordsType r2 = com.xiaomi.ai.api.Template.ResolveWordsType.DETAILS
                    r0.setType(r2)
                    com.xiaomi.ai.api.common.Event r0 = com.xiaomi.ai.api.common.APIUtils.buildEvent(r0)
                    com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil r2 = com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.getInstance()
                    com.xiaomi.micolauncher.common.quickapp.QuickAppHelper$1$1 r4 = new com.xiaomi.micolauncher.common.quickapp.QuickAppHelper$1$1
                    r4.<init>()
                    r2.eventRequest(r0, r4)
                L_0x0072:
                    boolean r0 = com.xiaomi.micolauncher.common.DebugHelper.isDebugVersion()
                    r2 = 1
                    r4 = 0
                    r5 = 3
                    if (r0 == 0) goto L_0x00a0
                    com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.quickapp
                    java.lang.String r6 = "QuickAppHelper Receive msg from hap app, pkgName: %s code: %s data: %s thread: %s"
                    r7 = 4
                    java.lang.Object[] r7 = new java.lang.Object[r7]
                    org.hapjs.features.channel.appinfo.HapApplication r9 = r9.getHapApplication()
                    java.lang.String r9 = r9.mPkgName
                    r7[r4] = r9
                    java.lang.Integer r9 = java.lang.Integer.valueOf(r1)
                    r7[r2] = r9
                    r7[r3] = r10
                    java.lang.Thread r9 = java.lang.Thread.currentThread()
                    java.lang.String r9 = r9.getName()
                    r7[r5] = r9
                    r0.d(r6, r7)
                    goto L_0x00c1
                L_0x00a0:
                    com.elvishew.xlog.Logger r10 = com.xiaomi.micolauncher.common.L.quickapp
                    java.lang.String r0 = "QuickAppHelper Receive msg from hap app, pkgName:%s, code:%s thread:%s "
                    java.lang.Object[] r5 = new java.lang.Object[r5]
                    org.hapjs.features.channel.appinfo.HapApplication r9 = r9.getHapApplication()
                    java.lang.String r9 = r9.mPkgName
                    r5[r4] = r9
                    java.lang.Integer r9 = java.lang.Integer.valueOf(r1)
                    r5[r2] = r9
                    java.lang.Thread r9 = java.lang.Thread.currentThread()
                    java.lang.String r9 = r9.getName()
                    r5[r3] = r9
                    r10.d(r0, r5)
                L_0x00c1:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.AnonymousClass1.onReceiveMessage(org.hapjs.features.channel.IHapChannel, org.hapjs.features.channel.ChannelMessage):void");
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onClose(IHapChannel iHapChannel, int i, String str) {
                L.quickapp.d("QuickAppHelper Channel opened by %s closed, code %s, reason: %s thread: %s", iHapChannel.getHapApplication().mPkgName, Integer.valueOf(i), str, Thread.currentThread().getName());
                QuickAppHelper.a.remove(iHapChannel.getHapApplication().mPkgName);
            }

            @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
            public void onError(IHapChannel iHapChannel, int i, String str) {
                L.quickapp.d("QuickAppHelper Channel opened by %s error, errorCode %s, errorMessage:%s thread: %s", iHapChannel.getHapApplication().mPkgName, Integer.valueOf(i), str, Thread.currentThread().getName());
                if (i == 4 || i == 2) {
                    QuickAppHelper.a.remove(iHapChannel.getHapApplication().mPkgName);
                }
            }
        };
        HapChannelManager.get().initialize(context);
        HapChannelManager.get().setDefaultChannelHandler(defaultChannelHandler);
    }

    public static boolean isDictionaryApp(String str) {
        return str.contains("com.xiaomi.aiot.soundbox_dictionary.mi");
    }

    public static boolean isDicChannelOpened() {
        return isChannelOpened("com.xiaomi.aiot.soundbox_dictionary.mi");
    }

    public static IHapChannel getDicChannel() {
        return getChannel("com.xiaomi.aiot.soundbox_dictionary.mi");
    }

    public static void clearChannels() {
        a.clear();
    }

    public static boolean isChannelOpened(String str) {
        Logger logger = L.quickapp;
        logger.d("channel status " + a.get(str));
        return a.containsKey(str) && a.get(str) != null;
    }

    public static IHapChannel getChannel(String str) {
        return a.get(str);
    }

    public static void notifyExit() {
        if (getDicChannel() != null) {
            ChannelMessage channelMessage = new ChannelMessage();
            channelMessage.code = -100;
            channelMessage.setData("dictionary should exit right now!");
            getDicChannel().send(channelMessage, null);
            a.remove("com.xiaomi.aiot.soundbox_dictionary.mi");
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static void launchQuickAppActivity(String str, OnChannelStateChangedListener onChannelStateChangedListener) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(String.format(DEEP_LINK_FORMAT, str, 0)));
        ActivityLifeCycleManager.startActivityQuietly(intent);
        b = new WeakReference<>(onChannelStateChangedListener);
    }

    @SuppressLint({"DefaultLocale"})
    public static void launchQuickAppActivity(String str, int i) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(String.format(DEEP_LINK_FORMAT, str, Integer.valueOf(i))));
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* loaded from: classes3.dex */
    public static class ChannelImpl implements IHapChannel {
        private IHapChannel a;
        private EventCallBack b;

        public ChannelImpl(IHapChannel iHapChannel) {
            this.a = iHapChannel;
        }

        @Override // org.hapjs.features.channel.IHapChannel
        public int getStatus() {
            IHapChannel iHapChannel = this.a;
            if (iHapChannel != null) {
                return iHapChannel.getStatus();
            }
            return 0;
        }

        @Override // org.hapjs.features.channel.IHapChannel
        public HapApplication getHapApplication() {
            IHapChannel iHapChannel = this.a;
            if (iHapChannel != null) {
                return iHapChannel.getHapApplication();
            }
            return null;
        }

        @Override // org.hapjs.features.channel.IHapChannel
        public void send(final ChannelMessage channelMessage, EventCallBack eventCallBack) {
            IHapChannel iHapChannel = this.a;
            if (iHapChannel == null) {
                L.quickapp.e("send: channel null");
            } else if (eventCallBack != null) {
                iHapChannel.send(channelMessage, eventCallBack);
            } else {
                if (this.b == null) {
                    this.b = new EventCallBack() { // from class: com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.ChannelImpl.1
                        @Override // org.hapjs.features.channel.listener.EventCallBack
                        public void onSuccess() {
                            L.quickapp.d("send message success: code is %s", Integer.valueOf(channelMessage.code));
                        }

                        @Override // org.hapjs.features.channel.listener.EventCallBack
                        public void onFail() {
                            L.quickapp.d("send message fail:code is %s", Integer.valueOf(channelMessage.code));
                        }
                    };
                }
                this.a.send(channelMessage, this.b);
            }
        }

        @Override // org.hapjs.features.channel.IHapChannel
        public void close(final String str, EventCallBack eventCallBack) {
            IHapChannel iHapChannel = this.a;
            if (iHapChannel == null) {
                L.quickapp.e("close: channel null");
            } else if (eventCallBack != null) {
                iHapChannel.close(str, eventCallBack);
            } else {
                if (this.b == null) {
                    this.b = new EventCallBack() { // from class: com.xiaomi.micolauncher.common.quickapp.QuickAppHelper.ChannelImpl.2
                        @Override // org.hapjs.features.channel.listener.EventCallBack
                        public void onSuccess() {
                            L.quickapp.d("close message success:close reason is %s", str);
                        }

                        @Override // org.hapjs.features.channel.listener.EventCallBack
                        public void onFail() {
                            L.quickapp.d("close message fail:close reason is %s", str);
                        }
                    };
                }
                this.a.close(str, this.b);
            }
        }
    }
}
