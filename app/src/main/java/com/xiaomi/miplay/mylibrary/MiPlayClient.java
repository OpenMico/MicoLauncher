package com.xiaomi.miplay.mylibrary;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMProcessCallback;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.IIDMServiceFactory;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.identify.IdentifyParam;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.ByteUtils;
import com.xiaomi.miplay.mylibrary.utils.Constant;

/* loaded from: classes4.dex */
public class MiPlayClient implements MiPlayClientAPI {
    private final Context a;
    private volatile boolean b;
    private IDMClient c;
    private MiPlayClientCallback d;
    private boolean e;
    private DeviceManager f;
    private String g;
    private final Object h = new Object();
    private IDMClient.ServiceFilter i = new IDMClient.ServiceFilter().addType(DataModel.SERVICE_TYPE);
    private IDMProcessCallback j = new IDMProcessCallback() { // from class: com.xiaomi.miplay.mylibrary.MiPlayClient.1
        @Override // com.xiaomi.idm.api.IDMProcessCallback
        public void onProcessConnected() {
            Logger.i("MiPlayAudioClient", "onProcessConnected", new Object[0]);
            if (MiPlayClient.this.c == null) {
                Logger.i("MiPlayAudioClient", "mMiConnect == null", new Object[0]);
                if (MiPlayClient.this.d != null) {
                    MiPlayClient.this.d.onInitError();
                }
            } else if (MiPlayClient.this.c.registerIDM(MiPlayClient.this.k, (IdentifyParam) null) != -1) {
                if (MiPlayClient.this.d != null) {
                    MiPlayClient.this.d.onInitSuccess();
                }
                LogUtil.setDebug(false);
                Logger.i("MiPlayAudioClient", "isStartDiscoveryInit:" + Constant.getInstance().isStartDiscoveryInit(), new Object[0]);
                if (Constant.getInstance().isStartDiscoveryInit()) {
                    MiPlayClient.this.stopDiscovery();
                    MiPlayClient.this.startDiscovery(Constant.getInstance().getDisctype());
                    Constant.getInstance().setStartDiscoveryInit(false);
                    Constant.getInstance().setDisctype(2);
                }
            } else {
                Logger.i("MiPlayAudioClient", "registerIDM_Error", new Object[0]);
                if (MiPlayClient.this.d != null) {
                    MiPlayClient.this.d.onInitError();
                }
            }
        }

        @Override // com.xiaomi.idm.api.IDMProcessCallback
        public void onProcessDisconnected() {
            Logger.i("MiPlayAudioClient", "onProcessDisconnected", new Object[0]);
            MiPlayClient.this.b = false;
        }
    };
    private IDMClient.IDMClientCallback k = new IDMClient.IDMClientCallback() { // from class: com.xiaomi.miplay.mylibrary.MiPlayClient.2
        @Override // com.xiaomi.idm.api.IDMClient.IDMClientCallback
        protected void onServiceFound(IDMService iDMService) {
            int i;
            Logger.i("MiPlayAudioClient", "onServiceFound:" + iDMService.getEndpoint().getName(), new Object[0]);
            Logger.d("MiPlayAudioClient", Constant.getInstance().convertMac(iDMService.getEndpoint().getMac()), new Object[0]);
            if ("00:00:00:00:00:00".equals(iDMService.getEndpoint().getMac())) {
                Logger.i("MiPlayAudioClient", "onServiceFound illegal mac abandon", new Object[0]);
                return;
            }
            Logger.d("MiPlayAudioClient", "+----------------------------\n--CLIENT onServiceFound: ServicedeviceType: " + iDMService.getEndpoint().getDeviceType() + "\nName: " + iDMService.getEndpoint().getName() + "\nip: " + iDMService.getEndpoint().getIp() + "\nmac: " + iDMService.getEndpoint().getMac() + "\nidhash: " + iDMService.getEndpoint().getIdhash() + "\naltitude: " + iDMService.getEndpoint().getAltitude() + "\nazimuth: " + iDMService.getEndpoint().getAzimuth() + "\ndistance: " + iDMService.getEndpoint().getDistance() + "\nverifyStatus: " + iDMService.getEndpoint().getVerifyStatus() + "\n+----------------------------\n", new Object[0]);
            byte[] appData = iDMService.getAppData();
            if (Build.VERSION.SDK_INT >= 9) {
                i = ByteUtils.getPort(appData);
                Logger.d("MiPlayAudioClient", "port:" + i, new Object[0]);
            } else {
                i = -1;
            }
            String ip = iDMService.getEndpoint().getIp();
            String mac = iDMService.getEndpoint().getMac();
            String idhash = iDMService.getEndpoint().getIdhash();
            String name = iDMService.getEndpoint().getName();
            float altitude = iDMService.getEndpoint().getAltitude();
            float azimuth = iDMService.getEndpoint().getAzimuth();
            int distance = iDMService.getEndpoint().getDistance();
            int deviceType = iDMService.getEndpoint().getDeviceType();
            MiDevice miDevice = new MiDevice();
            miDevice.setIp(ip);
            miDevice.setIdhash(idhash);
            miDevice.setMac(mac);
            miDevice.setName(name);
            miDevice.setDeviceType(deviceType);
            if (i == -1) {
                miDevice.setPort(8899);
            } else {
                miDevice.setPort(i);
            }
            miDevice.setAltitude(altitude);
            miDevice.setAzimuth(azimuth);
            miDevice.setDistance(distance);
            miDevice.setDistance(distance);
            miDevice.setVerifyStatus(iDMService.getEndpoint().getVerifyStatus());
            miDevice.setServiceProto(iDMService.getIDMServiceProto());
            if (MiPlayClient.this.d != null) {
                MiPlayClient.this.d.onDeviceFound(miDevice);
            }
        }

        @Override // com.xiaomi.idm.api.IDMClient.IDMClientCallback
        protected void onServiceLost(IDMService iDMService) {
            Logger.d("MiPlayAudioClient", "-----onServiceLost-----:" + Constant.getInstance().convertMac(iDMService.getEndpoint().getMac()), new Object[0]);
            if (MiPlayClient.this.d != null) {
                MiPlayClient.this.d.onDeviceLost(iDMService.getEndpoint().getMac());
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public MiPlayClient(Context context, DeviceManager deviceManager) {
        this.a = context;
        this.f = deviceManager;
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public void init(MiPlayClientCallback miPlayClientCallback, String str) {
        synchronized (this.h) {
            if (!this.b) {
                this.b = true;
                this.d = miPlayClientCallback;
                this.g = str;
                if (TextUtils.isEmpty(this.g)) {
                    this.g = DataModel.CLIENT_ID;
                }
                this.c = new IDMClient(this.a, this.g, (IIDMServiceFactory) new IDMAudioFactory(), this.j);
                this.c.init();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public boolean isInited() {
        return this.b;
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public void unInit(boolean z) {
        synchronized (this.h) {
            Logger.i("MiPlayAudioClient", "unInit", new Object[0]);
            if (!z) {
                if (this.c != null) {
                    this.c = null;
                }
                Logger.i("MiPlayAudioClient", "no execution destroyIdm", new Object[0]);
            } else if (this.c != null) {
                this.c.destroy();
                this.c = null;
            }
            this.b = false;
            Log.d("MiPlayAudioClient", "unInit end .");
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public void startDiscovery(int i) {
        Logger.i("MiPlayAudioClient", "startDiscovery.", new Object[0]);
        if (this.c == null) {
            Logger.i("MiPlayAudioClient", "mMiConnect == null", new Object[0]);
            return;
        }
        IDMClient.SDParamBuilder sDParamBuilder = new IDMClient.SDParamBuilder(this.i);
        sDParamBuilder.discType(i);
        sDParamBuilder.serviceSecurityType(1);
        if (!this.e) {
            this.e = true;
            Logger.i("MiPlayAudioClient", "startDiscovery disctype:" + i, new Object[0]);
            this.c.startDiscovery(sDParamBuilder);
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public void stopDiscovery() {
        Logger.i("MiPlayAudioClient", "stopDiscovery.", new Object[0]);
        IDMClient iDMClient = this.c;
        if (iDMClient == null) {
            Logger.i("MiPlayAudioClient", "mMiConnect == null", new Object[0]);
        } else if (this.e) {
            this.e = false;
            try {
                iDMClient.stopDiscovery();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public boolean isDiscovering() {
        return this.e;
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public boolean isServiceAvailable() {
        Logger.i("MiPlayAudioClient", "isServiceAvailable.", new Object[0]);
        return false;
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientAPI
    public void VerifySameAccount(IDMServiceProto.IDMService iDMService) {
        Logger.d("MiPlayAudioClient", "VerifySameAccount.", new Object[0]);
        IDMClient.CSParamBuilder cSParamBuilder = new IDMClient.CSParamBuilder(iDMService);
        cSParamBuilder.serviceSecurityType(1);
        cSParamBuilder.commType(2);
        this.c.connectService(cSParamBuilder);
    }
}
