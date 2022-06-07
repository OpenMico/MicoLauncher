package com.xiaomi.micolauncher.skills.common.dlna;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.skills.common.dlna.DlnaDevice;
import com.xiaomi.micolauncher.skills.mitv.MiTvManager;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;
import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.model.Channel;
import org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class DlnaDevice {
    @SuppressLint({"StaticFieldLeak"})
    private static DlnaDevice c = new DlnaDevice();
    private Context a;
    private AndroidUpnpService b;
    private UDN d = new UDN(Constants.getSn());
    private RegistryListener e = new a(this, null);
    private final Object f = new Object();
    @GuardedBy("bindStateLock")
    private boolean g = false;
    private ServiceConnection h = new AnonymousClass1();

    public static DlnaDevice getInstance() {
        return c;
    }

    public void start(Context context) {
        if (this.a == null) {
            this.a = context;
        }
        if (!MiTvManager.getInstance().hasTvDeviceListInSp()) {
            L.dlna.i("DlnaDevice,no device list in sp,not start upnp service");
            return;
        }
        synchronized (this.f) {
            b();
            if (!EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().register(this);
            }
        }
    }

    public void stop() {
        a();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    private void a() {
        synchronized (this.f) {
            if (this.b != null && this.g) {
                L.dlna.i("handle dlna unbind Service !");
                this.b.getRegistry().removeListener(this.e);
                if (this.a != null) {
                    this.a.unbindService(this.h);
                }
                this.g = false;
            }
        }
    }

    @SuppressLint({"WrongConstant"})
    private void b() {
        L.dlna.i("handle dlna bind Service !");
        synchronized (this.f) {
            this.a.bindService(new Intent(this.a, MyUpnpServiceImpl.class), this.h, 1);
            this.g = true;
        }
    }

    private boolean c() {
        boolean z;
        synchronized (this.f) {
            z = this.g;
        }
        return z;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWifiConnectivityChangedEvent(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        synchronized (this.f) {
            Logger logger = L.dlna;
            logger.d("WifiConnectivityChangedEvent is received with wifi connected " + wifiConnectivityChangedEvent.connected);
            if (wifiConnectivityChangedEvent.connected) {
                if (!c()) {
                    b();
                } else {
                    a();
                    b();
                }
            } else if (c()) {
                a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.skills.common.dlna.DlnaDevice$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {
            DlnaDevice.this = r1;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.dlna.i("dlna onService Connected !");
            synchronized (DlnaDevice.this.f) {
                DlnaDevice.this.b = (AndroidUpnpService) iBinder;
                DlnaDevice.this.b.getRegistry().removeAllLocalDevices();
                DlnaDevice.this.b.getRegistry().removeAllRemoteDevices();
            }
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.dlna.-$$Lambda$DlnaDevice$1$XLY6x67_VnQmiyfEa5AxAJk-nr0
                @Override // java.lang.Runnable
                public final void run() {
                    DlnaDevice.AnonymousClass1.this.a();
                }
            });
            L.dlna.i("dlna onService Connected end !");
        }

        public /* synthetic */ void a() {
            try {
                L.dlna.i("add Device and Listener");
                DlnaDevice.this.b.getRegistry().addDevice(DlnaDevice.this.d());
                DlnaDevice.this.b.getRegistry().addListener(DlnaDevice.this.e);
            } catch (Exception e) {
                L.dlna.e("dlna register service failed:", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.dlna.i("dlna register onService Disconnected !");
            DlnaDevice.this.b = null;
        }
    }

    /* loaded from: classes3.dex */
    public static class MyUpnpServiceImpl extends AndroidUpnpServiceImpl {
        static final long a = TimeUnit.SECONDS.toMillis(30);

        @Override // org.fourthline.cling.android.AndroidUpnpServiceImpl
        public AndroidUpnpServiceConfiguration createConfiguration() {
            return new AndroidUpnpServiceConfiguration() { // from class: com.xiaomi.micolauncher.skills.common.dlna.DlnaDevice.MyUpnpServiceImpl.1
                @Override // org.fourthline.cling.DefaultUpnpServiceConfiguration, org.fourthline.cling.UpnpServiceConfiguration
                public ServiceType[] getExclusiveServiceTypes() {
                    return new ServiceType[]{new ServiceType("mi-com", "RController", 1)};
                }

                @Override // org.fourthline.cling.android.AndroidUpnpServiceConfiguration, org.fourthline.cling.DefaultUpnpServiceConfiguration, org.fourthline.cling.UpnpServiceConfiguration
                public int getRegistryMaintenanceIntervalMillis() {
                    return (int) MyUpnpServiceImpl.a;
                }
            };
        }
    }

    public LocalDevice d() {
        try {
            return new LocalDevice(new DeviceIdentity(this.d), new UDADeviceType("MediaRenderer"), new DeviceDetails("小爱带屏音箱", new ManufacturerDetails("xiaomi"), new ModelDetails(Hardware.current(this.a).getName(), "音箱.", "v1")), new LocalService[]{e()});
        } catch (ValidationException e) {
            L.dlna.e("new LocalDevice failed: %s", e);
            return null;
        }
    }

    private static LocalService<AudioRenderingControlService> e() {
        LocalService<AudioRenderingControlService> read = new AnnotationLocalServiceBinder().read(AudioRenderingControlService.class);
        read.setManager(new DefaultServiceManager(read, AudioRenderingControlService.class));
        return read;
    }

    public void destroy() {
        stop();
    }

    /* loaded from: classes3.dex */
    public static class AudioRenderingControlService extends AbstractAudioRenderingControl {
        @Override // org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl
        public boolean getMute(UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str) {
            return false;
        }

        @Override // org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl
        public UnsignedIntegerTwoBytes getVolume(UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str) {
            return null;
        }

        @Override // org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl
        public void setMute(UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str, boolean z) {
        }

        @Override // org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl
        public void setVolume(UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str, UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
        }

        @Override // org.fourthline.cling.support.renderingcontrol.AbstractAudioRenderingControl
        protected Channel[] getCurrentChannels() {
            return new Channel[0];
        }

        @Override // org.fourthline.cling.support.lastchange.LastChangeDelegator
        public UnsignedIntegerFourBytes[] getCurrentInstanceIds() {
            return new UnsignedIntegerFourBytes[0];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends DefaultRegistryListener {
        private a() {
            DlnaDevice.this = r1;
        }

        /* synthetic */ a(DlnaDevice dlnaDevice, AnonymousClass1 r2) {
            this();
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice remoteDevice) {
            L.dlna.i("BrowseRegistryListener remoteDeviceDiscoveryStarted !");
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice remoteDevice, Exception exc) {
            L.dlna.i("BrowseRegistryListener remoteDeviceDiscoveryFailed !");
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
            L.dlna.i("BrowseRegistryListener ====> remoteDeviceAdded: ");
            if (remoteDevice != null && !MiTvManager.getInstance().checkMiTvDevice(remoteDevice)) {
                L.dlna.i("Not MiTV Device !");
            }
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
            L.dlna.i("BrowseRegistryListener =====> remoteDeviceRemoved !");
            MiTvManager.getInstance().offLineMiTvDevice(remoteDevice);
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
            L.dlna.i("BrowseRegistryListener localDeviceAdded !");
        }

        @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
        public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
            L.dlna.i("BrowseRegistryListener localDeviceRemoved !");
        }
    }
}
