package org.hapjs.features.channel;

import android.content.Context;
import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hapjs.features.channel.appinfo.HapApplication;
import org.hapjs.sdk.platform.PlatformUtils;

/* loaded from: classes5.dex */
public class HapChannelManager {
    private Map<String, ChannelHandler> a = new ConcurrentHashMap();
    private volatile boolean b;

    /* loaded from: classes5.dex */
    public interface ChannelHandler {
        boolean accept(HapApplication hapApplication);

        void onClose(IHapChannel iHapChannel, int i, String str);

        void onError(IHapChannel iHapChannel, int i, String str);

        void onOpen(IHapChannel iHapChannel);

        void onReceiveMessage(IHapChannel iHapChannel, ChannelMessage channelMessage);
    }

    /* loaded from: classes5.dex */
    public static class a {
        static HapChannelManager a = new HapChannelManager();
    }

    public static HapChannelManager get() {
        return a.a;
    }

    public synchronized void initialize(Context context) {
        if (!this.b) {
            PlatformUtils.init(context);
            this.b = true;
        }
    }

    public boolean isInitialized() {
        return this.b;
    }

    public void setDefaultChannelHandler(ChannelHandler channelHandler) {
        this.a.put("default", channelHandler);
    }

    public void setChannelHandler(String str, ChannelHandler channelHandler) {
        this.a.put(str, channelHandler);
    }

    public ChannelHandler getChannelHandler(String str) {
        return this.a.get(str);
    }

    public void setNetworkAvailable(Context context, boolean z) {
        PlatformUtils.setNetworkAvailable(context, z);
    }

    /* loaded from: classes5.dex */
    public static abstract class DefaultChannelHandler implements ChannelHandler {
        private String a;
        private String[] b;

        public DefaultChannelHandler(String str, String... strArr) {
            this.a = str;
            this.b = strArr;
        }

        @Override // org.hapjs.features.channel.HapChannelManager.ChannelHandler
        public boolean accept(HapApplication hapApplication) {
            if (hapApplication == null || !TextUtils.equals(this.a, hapApplication.mPkgName)) {
                return false;
            }
            String[] strArr = this.b;
            if (strArr == null || strArr.length == 0) {
                return true;
            }
            for (String str : strArr) {
                if (TextUtils.equals(hapApplication.mSignature, str)) {
                    return true;
                }
            }
            return false;
        }
    }
}
