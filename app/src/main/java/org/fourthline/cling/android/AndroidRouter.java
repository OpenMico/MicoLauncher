package org.fourthline.cling.android;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.RouterException;
import org.fourthline.cling.transport.RouterImpl;
import org.fourthline.cling.transport.spi.InitializationException;

/* loaded from: classes5.dex */
public class AndroidRouter extends RouterImpl {
    private static final long GET_NETWORK_INFO_TIME_DELAY = 1000;
    private static final int MAX_GET_NETWORK_INFO_TIME = 3;
    private static final Logger log = Logger.getLogger(Router.class.getName());
    private final Context context;
    private Handler handler = new Handler();
    protected WifiManager.MulticastLock multicastLock;
    protected NetworkInfo networkInfo;
    protected WifiManager.WifiLock wifiLock;
    private final WifiManager wifiManager;

    @Override // org.fourthline.cling.transport.RouterImpl
    protected int getLockTimeoutMillis() {
        return 15000;
    }

    public AndroidRouter(UpnpServiceConfiguration upnpServiceConfiguration, ProtocolFactory protocolFactory, Context context) throws InitializationException {
        super(upnpServiceConfiguration, protocolFactory);
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
        this.networkInfo = NetworkUtils.getConnectedNetworkInfo(context);
    }

    @Override // org.fourthline.cling.transport.RouterImpl, org.fourthline.cling.transport.Router
    public void shutdown() throws RouterException {
        super.shutdown();
    }

    @Override // org.fourthline.cling.transport.RouterImpl, org.fourthline.cling.transport.Router
    public boolean enable() throws RouterException {
        lock(this.writeLock);
        try {
            boolean enable = super.enable();
            if (enable && isWifi()) {
                setWiFiMulticastLock(true);
                setWifiLock(true);
            }
            return enable;
        } finally {
            unlock(this.writeLock);
        }
    }

    @Override // org.fourthline.cling.transport.RouterImpl, org.fourthline.cling.transport.Router
    public boolean disable() throws RouterException {
        lock(this.writeLock);
        try {
            if (isWifi()) {
                setWiFiMulticastLock(false);
                setWifiLock(false);
            }
            return super.disable();
        } finally {
            unlock(this.writeLock);
        }
    }

    public NetworkInfo getNetworkInfo() {
        return this.networkInfo;
    }

    public boolean isMobile() {
        return NetworkUtils.isMobile(this.networkInfo);
    }

    public boolean isWifi() {
        return NetworkUtils.isWifi(this.networkInfo);
    }

    public boolean isEthernet() {
        return NetworkUtils.isEthernet(this.networkInfo);
    }

    public boolean enableWiFi() {
        log.info("Enabling WiFi...");
        try {
            return this.wifiManager.setWifiEnabled(true);
        } catch (Throwable th) {
            log.log(Level.WARNING, "SetWifiEnabled failed", th);
            return false;
        }
    }

    protected void setWiFiMulticastLock(boolean z) {
        if (this.multicastLock == null) {
            this.multicastLock = this.wifiManager.createMulticastLock(getClass().getSimpleName());
        }
        if (z) {
            if (this.multicastLock.isHeld()) {
                log.warning("WiFi multicast lock already acquired");
                return;
            }
            log.info("WiFi multicast lock acquired");
            this.multicastLock.acquire();
        } else if (this.multicastLock.isHeld()) {
            log.info("WiFi multicast lock released");
            this.multicastLock.release();
        } else {
            log.warning("WiFi multicast lock already released");
        }
    }

    protected void setWifiLock(boolean z) {
        if (this.wifiLock == null) {
            this.wifiLock = this.wifiManager.createWifiLock(3, getClass().getSimpleName());
        }
        if (z) {
            if (this.wifiLock.isHeld()) {
                log.warning("WiFi lock already acquired");
                return;
            }
            log.info("WiFi lock acquired");
            this.wifiLock.acquire();
        } else if (this.wifiLock.isHeld()) {
            log.info("WiFi lock released");
            this.wifiLock.release();
        } else {
            log.warning("WiFi lock already released");
        }
    }
}
