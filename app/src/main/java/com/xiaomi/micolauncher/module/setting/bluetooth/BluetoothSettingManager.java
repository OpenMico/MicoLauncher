package com.xiaomi.micolauncher.module.setting.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAvrcpController;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.BluetoothUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.settingslib.core.MicoSettingsAction;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.BluetoothControlAction;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BluetoothSettingManager {
    public static final String LAST_CONNECTED_DEVICE = "lastConnectedDevice";
    @SuppressLint({"StaticFieldLeak"})
    private static volatile BluetoothSettingManager f;
    private final Context d;
    private Handler e;
    private BluetoothA2dp n;
    private BluetoothA2dpSink o;
    private BluetoothAvrcpController p;
    private volatile boolean q;
    private Runnable r;
    private Runnable s;
    private Runnable t;
    private static final long a = TimeUnit.SECONDS.toMillis(3);
    private static int b = 3;
    private static volatile boolean g = false;
    private static final long i = TimeUnit.MINUTES.toSeconds(15);
    private static final long j = TimeUnit.MINUTES.toMillis(15);
    private static final long k = TimeUnit.SECONDS.toMillis(5);
    private static final long l = TimeUnit.SECONDS.toMillis(5);
    private static volatile boolean y = false;
    private static volatile boolean z = false;
    private int c = 0;
    private BluetoothAdapter h = BluetoothAdapter.getDefaultAdapter();
    private b m = new b();
    private BluetoothControlAction.EnumAction u = BluetoothControlAction.EnumAction.ACTION_IDLE;
    private CurrentBluetooth v = new CurrentBluetooth();
    private volatile boolean w = false;
    private BluetoothDevice x = null;
    private volatile boolean A = false;
    private final BroadcastReceiver B = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                L.bluetooth.i("%s On received action ----> %s", "[BtSetManager]:", action);
                switch (action.hashCode()) {
                    case -1780914469:
                        if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1530327060:
                        if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1492944353:
                        if (action.equals("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -301431627:
                        if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -223687943:
                        if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -206700896:
                        if (action.equals("android.bluetooth.adapter.action.SCAN_MODE_CHANGED")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 6759640:
                        if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 40146574:
                        if (action.equals("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1123270207:
                        if (action.equals("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1167529923:
                        if (action.equals("android.bluetooth.device.action.FOUND")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1244161670:
                        if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1260591598:
                        if (action.equals("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1347806984:
                        if (action.equals("android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1506612347:
                        if (action.equals(BluetoothConstants.BLUETOOTH_ACTION_SYSTEMUI)) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1821585647:
                        if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2116862345:
                        if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        L.bluetooth.d("[BtSetManager]:", "开始扫描...");
                        return;
                    case 1:
                        L.bluetooth.d("[BtSetManager]:", "扫描结束");
                        return;
                    case 2:
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice != null) {
                            L.bluetooth.d("%s 设备名: %s, 地址: %s, type: %s, uuid: %s", "[BtSetManager]:", bluetoothDevice.getName(), bluetoothDevice.getAddress(), Integer.valueOf(bluetoothDevice.getType()), bluetoothDevice.getUuids());
                            return;
                        }
                        return;
                    case 3:
                        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        L.bluetooth.i("%s Bond State: %s", "[BtSetManager]:", BluetoothSettingManager.this.d(bluetoothDevice2.getBondState()));
                        intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
                        intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        switch (bluetoothDevice2.getBondState()) {
                            case 10:
                                L.bluetooth.i("%s 取消配对", "[BtSetManager]:");
                                String a2 = BluetoothSettingManager.this.m.a();
                                String b2 = BluetoothSettingManager.this.m.b();
                                if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(b2) && a2.equals(bluetoothDevice2.getName()) && b2.equals(bluetoothDevice2.getAddress())) {
                                    L.bluetooth.i("%s Clear device name: %s Address: %s", "[BtSetManager]:", bluetoothDevice2.getName(), bluetoothDevice2.getAddress());
                                    BluetoothSettingManager.this.m.d();
                                }
                                if (!BluetoothSettingManager.this.p()) {
                                    if (BluetoothSettingManager.this.a().b() != 0) {
                                        BluetoothSettingManager bluetoothSettingManager = BluetoothSettingManager.this;
                                        bluetoothSettingManager.c(bluetoothSettingManager.a(BluetoothControlAction.EnumAction.ACTION_REQUEST_REMOVE_BONDED_BY_ITEM_CLICK));
                                        BluetoothSettingManager.this.c();
                                        break;
                                    } else {
                                        return;
                                    }
                                }
                                break;
                            case 11:
                                L.bluetooth.i("%s 正在配对......", "[BtSetManager]:");
                                break;
                            case 12:
                                L.bluetooth.i("%s 完成配对", "[BtSetManager]:");
                                BluetoothSettingManager.this.a(bluetoothDevice2, bluetoothDevice2.getName());
                                break;
                        }
                        BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_VIEW);
                        return;
                    case 4:
                        L.bluetooth.d("%s BluetoothDevice.ACTION_ACL_CONNECTED", "[BtSetManager]:");
                        BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice3 != null) {
                            L.bluetooth.i("%s device name: %s, device address: %s", "[BtSetManager]:", bluetoothDevice3.getName(), bluetoothDevice3.getAddress());
                        }
                        BluetoothSettingManager.this.b("android.bluetooth.device.action.ACL_CONNECTED");
                        return;
                    case 5:
                        BluetoothSettingManager.this.b("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
                        return;
                    case 6:
                        BluetoothSettingManager.this.b("android.bluetooth.device.action.ACL_DISCONNECTED");
                        BluetoothSettingManager.this.c = 0;
                        if (BluetoothSettingManager.this.x != null) {
                            BluetoothSettingManager.this.b();
                            return;
                        }
                        return;
                    case 7:
                    default:
                        return;
                    case '\b':
                        BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_BT_NAME);
                        return;
                    case '\t':
                        L.bluetooth.i("%s Current scan mode: %s", "[BtSetManager]:", BluetoothSettingManager.this.c(BluetoothSettingManager.this.h.getScanMode()));
                        return;
                    case '\n':
                        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 10);
                        int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                        L.bluetooth.i("%s preState: %s, state: %s", "[BtSetManager]:", BluetoothSettingManager.this.a(intExtra), BluetoothSettingManager.this.a(intExtra2));
                        switch (intExtra2) {
                            case 10:
                                BluetoothSettingManager.this.m();
                                break;
                            case 12:
                                BluetoothSettingManager.this.i();
                                if (BluetoothSettingManager.this.u != BluetoothControlAction.EnumAction.ACTION_IDLE) {
                                    BluetoothSettingManager.this.k();
                                    if (!BluetoothSettingManager.this.m.c()) {
                                        L.bluetooth.i("%s no need connecting!", "[BtSetManager]:");
                                        break;
                                    } else {
                                        L.bluetooth.i("%s try to connecting!", "[BtSetManager]:");
                                        String a3 = BluetoothSettingManager.this.m.a();
                                        String b3 = BluetoothSettingManager.this.m.b();
                                        if (TextUtils.isEmpty(a3)) {
                                            L.bluetooth.i("%s lastConnectedDevice is null", "[BtSetManager]:");
                                            break;
                                        } else {
                                            L.bluetooth.i("%s last connected Device Name: %s address: %s", "[BtSetManager]:", a3, b3);
                                            BluetoothSettingManager.this.connectBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_CODE, BluetoothSettingManager.this.getRemoteDeviceByMac(b3));
                                            break;
                                        }
                                    }
                                } else {
                                    BluetoothSettingManager.this.b(21);
                                    BluetoothSettingManager.this.a(false);
                                    break;
                                }
                        }
                        BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_VIEW);
                        return;
                    case 11:
                        L.bluetooth.i("%s state: %s", "[BtSetManager]:", BluetoothSettingManager.this.a(intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 10)));
                        BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_VIEW);
                        return;
                    case '\f':
                    case '\r':
                        int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", 0);
                        int intExtra4 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        BluetoothDevice bluetoothDevice4 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice4 != null) {
                            L.bluetooth.i("%s A2dpSink preState: %s, state: %s", "[BtSetManager]:", BluetoothSettingManager.this.a(intExtra3), BluetoothSettingManager.this.a(intExtra4));
                            L.bluetooth.i("%s device name: %s, device address: %s", "[BtSetManager]:", bluetoothDevice4.getName(), bluetoothDevice4.getAddress());
                            switch (intExtra4) {
                                case 0:
                                    int intExtra5 = intent.getIntExtra("android.bluetooth.profile.extra.DISCONNECT_REASON", 0);
                                    L.bluetooth.w("%s !! disconnect reason: 0x%s", "[BtSetManager]:", Integer.toHexString(intExtra5));
                                    BluetoothSettingManager.this.a().setReason(intExtra5);
                                    BluetoothSettingManager.this.A = MediaSessionController.getInstance().isBtPlayingWhenDisconnect();
                                    L.bluetooth.d("%s !! isBtPlayingWhenDisconnect: %s", "[BtSetManager]:", Boolean.valueOf(BluetoothSettingManager.this.A));
                                    BluetoothSettingManager.this.a(intExtra3, intExtra4, bluetoothDevice4.getName(), bluetoothDevice4.getAddress());
                                    BluetoothSettingManager.this.c();
                                    return;
                                case 1:
                                    BluetoothSettingManager.this.a(intExtra3, intExtra4, bluetoothDevice4.getName(), bluetoothDevice4.getAddress());
                                    BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_NOTIFY_CHANGED);
                                    return;
                                case 2:
                                    BluetoothSettingManager.this.a(intExtra3, intExtra4, bluetoothDevice4.getName(), bluetoothDevice4.getAddress());
                                    BluetoothSettingManager.this.e();
                                    BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_VIEW);
                                    return;
                                case 3:
                                    BluetoothSettingManager.this.a(intExtra3, intExtra4, bluetoothDevice4.getName(), bluetoothDevice4.getAddress());
                                    BluetoothSettingManager.this.d(BluetoothControlAction.EnumAction.ACTION_POST_NOTIFY_CHANGED);
                                    return;
                                default:
                                    return;
                            }
                        } else {
                            return;
                        }
                    case 14:
                        int intExtra6 = intent.getIntExtra("android.bluetooth.profile.extra.PREVIOUS_STATE", -1);
                        int intExtra7 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                        BluetoothDevice bluetoothDevice5 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                        if (bluetoothDevice5 != null) {
                            L.bluetooth.i("%s Avrcp Controller preState: %s, state: %s", "[BtSetManager]:", BluetoothSettingManager.this.a(intExtra6), BluetoothSettingManager.this.a(intExtra7));
                            BluetoothSettingManager.this.a(intExtra6, intExtra7, bluetoothDevice5.getName(), bluetoothDevice5.getAddress());
                            BluetoothSettingManager.this.m.a(BluetoothSettingManager.this.a().getName());
                            BluetoothSettingManager.this.m.b(BluetoothSettingManager.this.a().a());
                            return;
                        }
                        return;
                    case 15:
                        String stringExtra = intent.getStringExtra(BluetoothConstants.EXTRA_BLUETOOTH_ACTION);
                        L.bluetooth.i("on receive bluetooth action from systemui" + stringExtra);
                        if (!BluetoothConstants.SET.equals(stringExtra)) {
                            BluetoothSettingManager.this.d();
                            return;
                        } else if (intent.getBooleanExtra(BluetoothConstants.EXTRA_BLUETOOTH_STATUS, false)) {
                            BluetoothSettingManager.openBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_BUTTON);
                            return;
                        } else {
                            BluetoothSettingManager.closeBluetooth(BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_BT_BUTTON);
                            return;
                        }
                }
            }
        }
    };
    private BluetoothProfile.ServiceListener C = new BluetoothProfile.ServiceListener() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager.2
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            if (i2 == 11) {
                L.bluetooth.i("%s A2DP_SINK onServiceConnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.o = (BluetoothA2dpSink) bluetoothProfile;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i2) {
            if (i2 == 11) {
                L.bluetooth.i("%s A2DP_SINK onServiceDisconnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.o = null;
            }
        }
    };
    private BluetoothProfile.ServiceListener D = new BluetoothProfile.ServiceListener() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager.3
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            if (i2 == 2) {
                L.bluetooth.i("%s A2DP onServiceConnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.n = (BluetoothA2dp) bluetoothProfile;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i2) {
            if (i2 == 2) {
                L.bluetooth.i("%s A2DP onServiceDisconnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.n = null;
            }
        }
    };
    private BluetoothProfile.ServiceListener E = new BluetoothProfile.ServiceListener() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothSettingManager.4
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
            if (i2 == 12) {
                L.bluetooth.i("%s AVRCP_CONTROLLER onServiceConnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.p = (BluetoothAvrcpController) bluetoothProfile;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i2) {
            if (i2 == 12) {
                L.bluetooth.i("%s AVRCP_CONTROLLER onServiceDisconnected !", "[BtSetManager]:");
                BluetoothSettingManager.this.p = null;
            }
        }
    };

    private BluetoothSettingManager(Context context) {
        this.d = context;
    }

    public static BluetoothSettingManager getInstance() {
        if (f == null) {
            L.bluetooth.e("%s instance is null !!", "[BtSetManager]:");
            init(MicoApplication.getGlobalContext());
        }
        return f;
    }

    public static void init(Context context) {
        if (f == null && context != null) {
            synchronized (BluetoothSettingManager.class) {
                if (f == null) {
                    f = new BluetoothSettingManager(context);
                }
            }
        }
    }

    public void start() {
        if (!this.q) {
            L.bluetooth.i("%s BluetoothSettingManager start !", "[BtSetManager]:");
            BluetoothAdapter bluetoothAdapter = this.h;
            if (bluetoothAdapter == null) {
                L.bluetooth.e("%s btAdapter is null", "[BtSetManager]:");
                return;
            }
            if (bluetoothAdapter.isEnabled()) {
                b(21);
            } else {
                L.bluetooth.d("%s Trying to enable btAdapt !!", "[BtSetManager]:");
                this.h.enable();
            }
            this.h.getProfileProxy(this.d, this.C, 11);
            this.h.getProfileProxy(this.d, this.E, 12);
            this.h.getProfileProxy(this.d, this.D, 2);
            h();
            i();
            this.r = new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.-$$Lambda$BluetoothSettingManager$ORW2-ytoCIGxl6Gv-YCBpjDUTJs
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothSettingManager.this.u();
                }
            };
            this.t = new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.-$$Lambda$BluetoothSettingManager$LMNhp0G-Sbw5xo69VOETTRDh2YU
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothSettingManager.this.t();
                }
            };
            this.s = new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.-$$Lambda$BluetoothSettingManager$QWUQLJQIcCYfogaqoHT37eUwlKU
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothSettingManager.this.s();
                }
            };
            this.e = new a(ThreadUtil.getWorkHandler().getLooper());
            this.q = true;
            d();
            String macAddress = BluetoothUtil.getMacAddress(this.d);
            if (!TextUtils.isEmpty(macAddress)) {
                L.bluetooth.i("%s bt physics address: %s !", "[BtSetManager]:", macAddress);
            } else {
                L.bluetooth.e("%s bt physics address is null , Please checked !!", "[BtSetManager]:");
            }
            b(21);
        }
    }

    public /* synthetic */ void u() {
        L.bluetooth.i("waitPairTimer timeout !", "[BtSetManager]:");
        if (this.h.isEnabled() && !isDeviceConnected()) {
            L.bluetooth.i("Disable btAdapter!", "[BtSetManager]:");
            closeBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_WAIT_TIMEOUT);
        }
        y = false;
    }

    public /* synthetic */ void t() {
        L.bluetooth.i("reopen timeout !", "[BtSetManager]:");
        if (!this.h.isEnabled()) {
            L.bluetooth.i("enable btAdapter!", "[BtSetManager]:");
            openBT(BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_MESH);
        }
        z = false;
    }

    public /* synthetic */ void s() {
        L.bluetooth.i("%s Reconnect runnable !", "[BtSetManager]:");
        if (this.h.getProfileConnectionState(2) != 2) {
            L.bluetooth.i("%s try to reconnect device name: %s", "[BtSetManager]:", this.v.getName());
            g(this.v.e());
            a(l);
            return;
        }
        L.bluetooth.i("%s A2DP is connected, don't reconnect !!", "[BtSetManager]:");
    }

    public void b() {
        if (this.x != null) {
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.-$$Lambda$BluetoothSettingManager$aIIOzaWngNnUYW6jzBV7M7RhWlU
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothSettingManager.this.r();
                }
            }, a);
        }
    }

    public /* synthetic */ void r() {
        BluetoothDevice bluetoothDevice = this.x;
        if (bluetoothDevice != null) {
            boolean a2 = a(bluetoothDevice, bluetoothDevice.getName());
            this.c++;
            if (!a2 && this.c <= b) {
                b();
            }
            if (a2 || this.c > b) {
                this.x = null;
            }
            L.bluetooth.i("%s doConnectIfneedWhenReceiveDisConnect connect result:%s", "[BtSetManager]:", Boolean.valueOf(a2));
        }
    }

    public synchronized void c() {
        int c = a().c();
        int b2 = a().b();
        L.bluetooth.i("%s doDisconnectHandle Current Acl State: %s, A2dp Prev State: %s, A2dp State: %s", "[BtSetManager]:", a().d(), a(c), a(b2));
        if (c == 1 && b2 == 0) {
            f();
        } else if (c == 0 && b2 == 0) {
            f();
        } else {
            if (b2 == 0) {
                L.bluetooth.i("%s STATE_DISCONNECTED, btControlAction: %s", "[BtSetManager]:", this.u.name());
                g();
                a(0, 0);
                d(BluetoothControlAction.EnumAction.ACTION_POST_UPDATE_VIEW);
            }
        }
    }

    public synchronized void d() {
        L.bluetooth.i("%s post bt status changed to systemui: %s", "[BtSetManager]:", Boolean.valueOf(MicoSettings.isBluetoothEnable(this.d)));
        Intent intent = new Intent(BluetoothConstants.BLUETOOTH_ACTION_LAUNCHER);
        intent.putExtra(BluetoothConstants.EXTRA_BLUETOOTH_STATUS, MicoSettings.isBluetoothEnable(this.d));
        this.d.sendBroadcast(intent);
    }

    public void e() {
        L.bluetooth.i("%s handleConnectedChange !", "[BtSetManager]:");
        m();
        b(20);
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_BUTTON || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_VOICE_QUERY || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_ITEM_CLICK || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY) {
            b(this.u);
        }
        this.m.a(a().getName());
        this.m.b(a().a());
        L.bluetooth.i("%s btControlAction: %s", "[BtSetManager]:", this.u.name());
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT) {
            j();
            return;
        }
        a(true);
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.-$$Lambda$BluetoothSettingManager$eWBZ0PSN-0a74w21GNmoFpJkcMw
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothSettingManager.this.q();
            }
        }, 1500L);
    }

    public /* synthetic */ void q() {
        BluetoothA2dpSink bluetoothA2dpSink;
        BluetoothA2dp bluetoothA2dp;
        BluetoothDevice connectedBluetoothDevice = getConnectedBluetoothDevice();
        if (connectedBluetoothDevice == null || (((bluetoothA2dpSink = this.o) == null || !bluetoothA2dpSink.isA2dpPlaying(connectedBluetoothDevice)) && ((bluetoothA2dp = this.n) == null || !bluetoothA2dp.isA2dpPlaying(connectedBluetoothDevice)))) {
            L.bluetooth.d("%s getConnectedBluetoothDevice is null", "[BtSetManager]:");
            a(a().getName());
            g(R.string.setting_bluetooth_connect_success);
            L.bluetooth.d("%s isA2dp not Playing", "[BtSetManager]:");
            return;
        }
        L.bluetooth.d("%s mac: %s", "[BtSetManager]:", ((BluetoothDevice) Objects.requireNonNull(connectedBluetoothDevice)).getAddress());
        L.bluetooth.d("%s isA2dpPlaying", "[BtSetManager]:");
    }

    private void f() {
        L.bluetooth.i("%s handleConnectFailed Change !", "[BtSetManager]:");
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_BUTTON || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_VOICE_QUERY || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_ITEM_CLICK || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY) {
            b(this.u);
        }
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_VOICE_QUERY || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_ITEM_CLICK) {
            L.bluetooth.e("%s not Come to here: %s, Please Checked !!", "[BtSetManager]:", this.u.name());
            b(this.u);
        }
        if (this.u != BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT) {
            g(R.string.setting_bluetooth_connect_failed);
            f(R.raw.bluetooth_connect_failed);
            a(23, i);
        }
    }

    private void g() {
        L.bluetooth.i("%s handleDisconnect Change !", "[BtSetManager]:");
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_BT_BUTTON || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_VOICE_QUERY || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_CODE) {
            m();
            b(21);
            j();
            b(this.u);
        } else if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_ITEM_CLICK) {
            if (this.w) {
                this.w = false;
                BluetoothDevice bluetoothDevice = this.x;
                if (bluetoothDevice != null) {
                    f(bluetoothDevice);
                } else {
                    L.bluetooth.e("%s needConnectDevice should not be null !!", "[BtSetManager]:");
                }
            } else {
                L.bluetooth.e("%s needDisconnectFlag should not be false !!", "[BtSetManager]:");
            }
        } else if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY) {
            L.bluetooth.e("%s Should not come to here !!", "[BtSetManager]:");
            b(this.u);
        } else if (this.v.getReason() != 8 || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT) {
            b(this.u);
            this.m.a(false);
            if (!this.A) {
                g(R.string.setting_bluetooth_connect_disconnect);
                if (!BluetoothTTSPlayUtils.isInTTSPlayBlackList(a().f, a().e)) {
                    f(R.raw.bluetooth_disconnect);
                }
            }
            k();
        } else {
            c(BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT);
            a(0L);
            k();
        }
    }

    private void a(Object obj, int i2, int i3) {
        L.bluetooth.i("%s sendBtMessage: %s", "[BtSetManager]:", e(i2));
        Message obtain = Message.obtain();
        obtain.obj = obj;
        obtain.what = i2;
        if (i3 == 0) {
            this.e.sendMessage(obtain);
        } else {
            this.e.sendMessageDelayed(obtain, i3);
        }
    }

    /* loaded from: classes3.dex */
    public final class a extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Looper looper) {
            super(looper);
            BluetoothSettingManager.this = r1;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            L.bluetooth.i("%s handle Message: %s, btControlAction: %s", "[BtSetManager]:", BluetoothSettingManager.this.e(message.what), BluetoothSettingManager.this.u.name());
            switch (message.what) {
                case 1:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                    switch (BluetoothSettingManager.this.u) {
                        case ACTION_REQUEST_CONNECT_BY_CODE:
                        case ACTION_REQUEST_CONNECT_BY_ITEM_CLICK:
                        case ACTION_REQUEST_CONNECT_BY_VOICE_QUERY:
                            if (!BluetoothSettingManager.this.isDeviceConnected()) {
                                if (bluetoothDevice == null) {
                                    L.bluetooth.e("%s device is null !!", "[BtSetManager]:");
                                    break;
                                } else {
                                    BluetoothSettingManager.this.e(bluetoothDevice);
                                    break;
                                }
                            } else {
                                L.bluetooth.e("%s shouldn't has connected device when request connect action !", "[BtSetManager]:");
                                break;
                            }
                        case ACTION_REQUEST_OPEN_BY_BT_BUTTON:
                        case ACTION_REQUEST_OPEN_BY_VOICE_QUERY:
                        case ACTION_REQUEST_OPEN_BY_CODE:
                            if (bluetoothDevice == null) {
                                L.bluetooth.e("%s device is null !!", "[BtSetManager]:");
                                break;
                            } else {
                                BluetoothSettingManager.this.e(bluetoothDevice);
                                break;
                            }
                        default:
                            L.bluetooth.e("%s not handle this action: %s", "[BtSetManager]:", BluetoothSettingManager.this.u.name());
                            break;
                    }
                case 2:
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
                    switch (BluetoothSettingManager.this.u) {
                        case ACTION_REQUEST_CLOSE_BY_BT_BUTTON:
                        case ACTION_REQUEST_CLOSE_BY_CODE:
                        case ACTION_REQUEST_CLOSE_BY_VOICE_QUERY:
                        case ACTION_REQUEST_DISCONNECT_BY_CODE:
                        case ACTION_REQUEST_DISCONNECT_BY_ITEM_CLICK:
                        case ACTION_REQUEST_DISCONNECT_BY_VOICE_QUERY:
                        case ACTION_REQUEST_CONNECT_BY_CODE:
                        case ACTION_REQUEST_CONNECT_BY_ITEM_CLICK:
                            if (BluetoothSettingManager.this.isDeviceConnected()) {
                                if (bluetoothDevice2 == null) {
                                    L.bluetooth.e("%s device is null !!", "[BtSetManager]:");
                                    break;
                                } else {
                                    BluetoothSettingManager.this.a(bluetoothDevice2);
                                    break;
                                }
                            } else {
                                L.bluetooth.e("%s no connected device !", "[BtSetManager]:");
                                break;
                            }
                        case ACTION_REQUEST_CONNECT_BY_VOICE_QUERY:
                            L.bluetooth.e("%s Not come to here, Please checked !!", "[BtSetManager]:");
                            break;
                        default:
                            L.bluetooth.e("%s not handle this action: %s", "[BtSetManager]:", BluetoothSettingManager.this.u.name());
                            break;
                    }
                case 3:
                    if (!BluetoothSettingManager.this.isDeviceConnected()) {
                        BluetoothSettingManager bluetoothSettingManager = BluetoothSettingManager.this;
                        bluetoothSettingManager.e(bluetoothSettingManager.v.e());
                        break;
                    } else {
                        L.bluetooth.e("%s shouldn't has connected device when request Reconnect action !", "[BtSetManager]:");
                        break;
                    }
            }
            super.handleMessage(message);
        }
    }

    /* loaded from: classes3.dex */
    public class b {
        private b() {
            BluetoothSettingManager.this = r1;
        }

        public String a() {
            return BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).getString("name", null);
        }

        public void a(String str) {
            SharedPreferences.Editor edit = BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).edit();
            edit.putString("name", str);
            edit.apply();
        }

        public String b() {
            return BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).getString("address", null);
        }

        public void b(String str) {
            SharedPreferences.Editor edit = BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).edit();
            edit.putString("address", str);
            edit.apply();
        }

        public boolean c() {
            return BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).getBoolean("Need_Reconnect_Flag", false);
        }

        public void a(boolean z) {
            SharedPreferences.Editor edit = BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).edit();
            edit.putBoolean("Need_Reconnect_Flag", z);
            edit.apply();
        }

        public void d() {
            SharedPreferences.Editor edit = BluetoothSettingManager.this.d.getSharedPreferences(BluetoothSettingManager.LAST_CONNECTED_DEVICE, 0).edit();
            edit.clear();
            edit.apply();
        }

        public boolean e() {
            if (!TextUtils.isEmpty(BluetoothSettingManager.this.m.b())) {
                BluetoothSettingManager bluetoothSettingManager = BluetoothSettingManager.this;
                BluetoothDevice remoteDeviceByMac = bluetoothSettingManager.getRemoteDeviceByMac(bluetoothSettingManager.m.b());
                if (remoteDeviceByMac != null) {
                    return remoteDeviceByMac.isConnected() || remoteDeviceByMac.getBondState() == 12;
                }
                return false;
            }
            L.bluetooth.i("%s get address is null!", "[BtSetManager]:");
            return false;
        }
    }

    public synchronized boolean isNeedReconnect() {
        return this.m.c();
    }

    public synchronized boolean isDeviceConnected() {
        Set<BluetoothDevice> bondedDevices = this.h.getBondedDevices();
        if (bondedDevices.size() > 0) {
            L.bluetooth.i("%s isDeviceConnected 已配对的设备:", "[BtSetManager]:");
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                Logger logger = L.bluetooth;
                Object[] objArr = new Object[4];
                objArr[0] = "[BtSetManager]:";
                objArr[1] = bluetoothDevice.getName();
                objArr[2] = bluetoothDevice.getAddress();
                objArr[3] = bluetoothDevice.isConnected() ? "connected!" : "disconnected!";
                logger.i("%s device name: %s, address: %s, state: %s", objArr);
                if (bluetoothDevice.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public BluetoothDevice getConnectedBluetoothDevice() {
        Set<BluetoothDevice> bondedDevices = this.h.getBondedDevices();
        if (bondedDevices.size() <= 0) {
            return null;
        }
        L.bluetooth.i("%s getConnectedBluetoothDevice 已配对的设备:", "[BtSetManager]:");
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            Logger logger = L.bluetooth;
            Object[] objArr = new Object[5];
            objArr[0] = "[BtSetManager]:";
            objArr[1] = bluetoothDevice.getName();
            objArr[2] = bluetoothDevice.getAddress();
            objArr[3] = Integer.valueOf(bluetoothDevice.getBondState());
            objArr[4] = bluetoothDevice.isConnected() ? "connected!" : "disconnected!";
            logger.i("%s device name: %s, address: %s,isBonded:%s, state: %s", objArr);
            if (bluetoothDevice.isConnected()) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    private void h() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction(BluetoothConstants.BLUETOOTH_ACTION_SYSTEMUI);
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.d.registerReceiver(this.B, intentFilter);
    }

    public String i() {
        String deviceName = SystemSetting.getDeviceName();
        String name = this.h.getName();
        if (TextUtils.isEmpty(deviceName)) {
            String sn = Constants.getSn();
            if (!TextUtils.isEmpty(sn)) {
                String substring = sn.substring(sn.length() - 4);
                L.bluetooth.i("%s serialNumber: %s, sn_sub: %s", "[BtSetManager]:", sn, substring);
                deviceName = "小爱触屏音箱-" + substring;
            } else {
                deviceName = "小爱触屏音箱-";
            }
            L.bluetooth.i("%s sys_name is null, set name to: %s", "[BtSetManager]:", deviceName);
        }
        if (TextUtils.isEmpty(name) || !name.equals(deviceName)) {
            L.bluetooth.i("%s update bt name: %s", "[BtSetManager]:", deviceName);
            this.h.setName(deviceName);
        }
        return deviceName;
    }

    private void a(long j2) {
        L.bluetooth.i("%s post ReconnectThread !", "[BtSetManager]:");
        ThreadUtil.postDelayedInMainThread(this.s, j2);
    }

    private void j() {
        L.bluetooth.i("%s remove ReconnectThread !", "[BtSetManager]:");
        Runnable runnable = this.s;
        if (runnable != null) {
            ThreadUtil.removeCallbacksInMainThread(runnable);
        }
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT) {
            b(this.u);
        }
    }

    public void k() {
        L.bluetooth.i("%s enable WaitPairTimer", "[BtSetManager]:");
        if (!y) {
            y = true;
        } else {
            Runnable runnable = this.r;
            if (runnable != null) {
                ThreadUtil.removeCallbacksInMainThread(runnable);
            }
        }
        a(23, i);
        ThreadUtil.postDelayedInMainThread(this.r, j);
    }

    private void l() {
        L.bluetooth.i("%s enable WaitReopenTimer", "[BtSetManager]:");
        if (!z) {
            z = true;
        } else {
            Runnable runnable = this.t;
            if (runnable != null) {
                ThreadUtil.removeCallbacksInMainThread(runnable);
            }
        }
        ThreadUtil.postDelayedInMainThread(this.t, k);
    }

    public void m() {
        L.bluetooth.i("%s disable WaitPairTimer", "[BtSetManager]:");
        if (y) {
            y = false;
            Runnable runnable = this.r;
            if (runnable != null) {
                ThreadUtil.removeCallbacksInMainThread(runnable);
            }
        }
    }

    private void n() {
        L.bluetooth.i("%s disable WaitReopenTimer", "[BtSetManager]:");
        if (z) {
            z = false;
            Runnable runnable = this.t;
            if (runnable != null) {
                ThreadUtil.removeCallbacksInMainThread(runnable);
            }
        }
    }

    public static boolean isSettingActivityOpened() {
        return g;
    }

    public synchronized void a(boolean z2) {
        g = z2;
        d();
        if (MicoSettings.isBluetoothEnable(this.d) != z2) {
            MicoSettings.setBluetoothEnable(this.d, z2);
        }
    }

    public static void openBluetooth(BluetoothControlAction.EnumAction enumAction) {
        if (getInstance() != null) {
            getInstance().openBT(enumAction);
        } else {
            L.bluetooth.i("BluetoothSettingManager is null for openBT");
        }
    }

    public void openBT(BluetoothControlAction.EnumAction enumAction) {
        L.bluetooth.i("%s openBT --->action: %s !", "[BtSetManager]:", enumAction.name());
        if (!this.q) {
            start();
        }
        if (enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_MESH) {
            if (!this.h.isEnabled()) {
                this.h.enable();
            }
            this.u = BluetoothControlAction.EnumAction.ACTION_IDLE;
            n();
        } else if (!this.h.isEnabled()) {
            this.h.enable();
        } else if (this.n == null && this.o == null) {
            L.bluetooth.e("%s btA2dp,btA2dpSink is null , Please checked !!", "[BtSetManager]:");
        } else {
            c(enumAction);
            k();
            if (this.m.c()) {
                L.bluetooth.i("%s try to reconnecting last disconnect bt device!", "[BtSetManager]:");
                String a2 = this.m.a();
                String b2 = this.m.b();
                BluetoothDevice remoteDeviceByMac = getRemoteDeviceByMac(b2);
                boolean z2 = remoteDeviceByMac.isConnected() || remoteDeviceByMac.getBondState() == 12;
                if (TextUtils.isEmpty(a2) || TextUtils.isEmpty(b2)) {
                    c(a(enumAction));
                    L.bluetooth.e("%s lastConnectedDevice is null", "[BtSetManager]:");
                } else {
                    Logger logger = L.bluetooth;
                    Object[] objArr = new Object[4];
                    objArr[0] = "[BtSetManager]:";
                    objArr[1] = a2;
                    objArr[2] = b2;
                    objArr[3] = z2 ? "connected" : "disconnected";
                    logger.i("%s last disconnect Device Name: %s, address: %s, state: %s", objArr);
                    if (z2) {
                        L.bluetooth.e("%s reconnecting device is connected !", "[BtSetManager]:");
                        c(a(enumAction));
                        a(remoteDeviceByMac, a2);
                    } else {
                        f(getRemoteDeviceByMac(b2));
                    }
                }
            } else {
                c(a(enumAction));
                L.bluetooth.i("%s NeedReconnectFlag is false, no need connecting!", "[BtSetManager]:");
            }
            a(true);
        }
    }

    public static void closeBluetooth(BluetoothControlAction.EnumAction enumAction) {
        if (getInstance() != null) {
            getInstance().closeBT(enumAction);
        } else {
            L.bluetooth.i("BluetoothSettingManager is null for closeBT");
        }
    }

    public void closeBluetoothReal() {
        BluetoothAdapter bluetoothAdapter = this.h;
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            this.h.disable();
            l();
        }
    }

    public void closeBT(BluetoothControlAction.EnumAction enumAction) {
        L.bluetooth.i("%s closeBT --->action: %s !", "[BtSetManager]:", enumAction.name());
        if (this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_BT_BUTTON || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_CODE || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_WAIT_TIMEOUT || this.u == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_VOICE_QUERY) {
            L.bluetooth.i("%s closeBT ---> action: %s ,is in closing!", "[BtSetManager]:", enumAction.name());
        } else if (!this.h.isEnabled()) {
            this.h.enable();
        } else {
            c(enumAction);
            m();
            b(21);
            j();
            if (this.m.e()) {
                this.m.a(true);
                h(getRemoteDeviceByMac(this.m.b()));
            } else {
                c(a(enumAction));
                this.m.a(false);
            }
            a(false);
        }
    }

    public void removeBonded(BluetoothControlAction.EnumAction enumAction, BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            L.bluetooth.d("%s removeBonded device is null ", "[BtSetManager]:");
            return;
        }
        int bondState = bluetoothDevice.getBondState();
        if (bondState == 11) {
            bluetoothDevice.cancelBondProcess();
        }
        if (bondState != 10) {
            if (bluetoothDevice.removeBond()) {
                L.bluetooth.d("%s Command sent successfully:REMOVE_BOND ", "[BtSetManager]:");
            } else {
                L.bluetooth.d("%s Framework rejected command immediately:REMOVE_BOND ", "[BtSetManager]:");
            }
        }
        c(enumAction);
    }

    public void connectBT(BluetoothControlAction.EnumAction enumAction, BluetoothDevice bluetoothDevice) {
        L.bluetooth.i("%s connectBT --->action: %s", "[BtSetManager]:", enumAction);
        BluetoothDevice connectedBluetoothDevice = getConnectedBluetoothDevice();
        if (connectedBluetoothDevice != null) {
            connectedBluetoothDevice.getName();
        }
        Logger logger = L.bluetooth;
        Object[] objArr = new Object[3];
        objArr[0] = "[BtSetManager]:";
        BluetoothDevice bluetoothDevice2 = null;
        objArr[1] = bluetoothDevice != null ? bluetoothDevice.getName() : null;
        if (isDeviceConnected()) {
            bluetoothDevice2 = connectedBluetoothDevice;
        }
        objArr[2] = bluetoothDevice2;
        logger.i("%s try to connect device name: %s, current connected device name: %s", objArr);
        if (!this.h.isEnabled()) {
            this.h.enable();
        } else if (this.n == null && this.o == null) {
            L.bluetooth.e("%s btA2dp,btA2dpSink is null, Please checked !!", "[BtSetManager]:");
            return;
        } else {
            c(enumAction);
            k();
            a(true);
            if (this.h.getBondedDevices().size() <= 0 && enumAction.ordinal() == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY.ordinal()) {
                L.bluetooth.i("%s bluetooth_nophone", "[BtSetManager]:");
                f(R.raw.bluetooth_nophone);
                c(a(enumAction));
                return;
            } else if (bluetoothDevice == null) {
                String b2 = this.m.b();
                if (!TextUtils.isEmpty(b2)) {
                    BluetoothDevice remoteDeviceByMac = getRemoteDeviceByMac(b2);
                    if (remoteDeviceByMac.isConnected()) {
                        L.bluetooth.i("%s bluetooth_already_connected", "[BtSetManager]:");
                        f(R.raw.bluetooth_already_connected);
                        m();
                        b(20);
                        c(a(enumAction));
                        return;
                    }
                    f(remoteDeviceByMac);
                } else {
                    c(a(enumAction));
                    L.bluetooth.e("%s address is null, Please Checked !!", "[BtSetManager]:");
                    return;
                }
            } else if (bluetoothDevice.isConnected()) {
                f(R.raw.bluetooth_already_connected);
                m();
                b(20);
                c(a(enumAction));
                return;
            } else if (isDeviceConnected()) {
                this.w = true;
                this.x = bluetoothDevice;
                h(connectedBluetoothDevice);
            } else {
                f(bluetoothDevice);
            }
        }
        L.bluetooth.d("%s connectBT is over !", "[BtSetManager]:");
    }

    public void disconnectBT(BluetoothControlAction.EnumAction enumAction) {
        L.bluetooth.i("%s disconnectBT --->action: %s ", "[BtSetManager]:", enumAction);
        if (this.m.e()) {
            c(enumAction);
            this.m.a(false);
            o();
        } else if (MiotProvisionManagerWrapper.getInstance().isMeshState()) {
            f(R.raw.bluetooth_noconnect);
        }
    }

    public BluetoothControlAction.EnumAction a(BluetoothControlAction.EnumAction enumAction) {
        String str = enumAction.name() + "_DONE";
        L.bluetooth.i("%s getActionDone action: %s, actionDone: %s", "[BtSetManager]:", enumAction.name(), str);
        return BluetoothControlAction.EnumAction.valueOf(str);
    }

    private void o() {
        boolean z2;
        Set<BluetoothDevice> bondedDevices = this.h.getBondedDevices();
        if (bondedDevices.size() > 0) {
            L.bluetooth.i("%s disconnectAllConnectDevices 已配对的设备:", "[BtSetManager]:");
            z2 = false;
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                Logger logger = L.bluetooth;
                Object[] objArr = new Object[4];
                objArr[0] = "[BtSetManager]:";
                objArr[1] = bluetoothDevice.getName();
                objArr[2] = bluetoothDevice.getAddress();
                objArr[3] = bluetoothDevice.isConnected() ? "connected!" : "disconnected!";
                logger.i("%s device name: %s, address: %s, state: %s", objArr);
                if (bluetoothDevice.isConnected()) {
                    L.bluetooth.i("%s trying to disconnect!", "[BtSetManager]:");
                    a(bluetoothDevice);
                    z2 = true;
                }
            }
        } else {
            z2 = false;
        }
        if (!z2 && MiotProvisionManagerWrapper.getInstance().isMeshState()) {
            f(R.raw.bluetooth_noconnect);
        }
    }

    public synchronized void a(BluetoothDevice bluetoothDevice) {
        if (this.n == null && this.o == null) {
            L.bluetooth.e("%s onA2dpSinkDisconnect a2dpSink is null !!", "[BtSetManager]:");
        }
        if ((this.o != null && this.o.getConnectionState(bluetoothDevice) == 2) || (this.n != null && this.n.getConnectionState(bluetoothDevice) == 2)) {
            d(bluetoothDevice);
        }
    }

    private boolean b(BluetoothDevice bluetoothDevice) {
        if (!Hardware.isX10() || this.n == null) {
            return false;
        }
        try {
            return ((Boolean) BluetoothA2dp.class.getMethod("connect", BluetoothDevice.class).invoke(this.n, bluetoothDevice)).booleanValue();
        } catch (IllegalAccessException e) {
            L.bluetooth.e("%s performConnectUseSource error:%s", "[BtSetManager]:", e);
            return false;
        } catch (NoSuchMethodException e2) {
            L.bluetooth.e("%s performConnectUseSource error:%s", "[BtSetManager]:", e2);
            return false;
        } catch (InvocationTargetException e3) {
            L.bluetooth.e("%s performConnectUseSource error:%s", "[BtSetManager]:", e3);
            return false;
        }
    }

    private boolean c(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.o;
        if (bluetoothA2dpSink != null) {
            return bluetoothA2dpSink.connect(bluetoothDevice);
        }
        return false;
    }

    public boolean a(BluetoothDevice bluetoothDevice, String str) {
        if (BluetoothSettingManagerHelper.isInWhiteList(bluetoothDevice.getAddress(), str)) {
            boolean b2 = b(bluetoothDevice);
            L.bluetooth.d("%s performConnect withSource inWhiteList result onConnect %s:", "[BtSetManager]:", Boolean.valueOf(b2));
            return b2;
        }
        boolean c = c(bluetoothDevice);
        L.bluetooth.d("%s performConnect use Sink %s:", "[BtSetManager]:", Boolean.valueOf(c));
        return c;
    }

    private void d(BluetoothDevice bluetoothDevice) {
        try {
            if (this.o != null) {
                this.o.disconnect(bluetoothDevice);
            }
            if (Hardware.isX10() && this.n != null) {
                L.bluetooth.e("%s disConnect result %s:", "[BtSetManager]:", Boolean.valueOf(((Boolean) BluetoothA2dp.class.getMethod(MicoSettingsAction.BLUETOOTH_DISCONNECT, BluetoothDevice.class).invoke(this.n, bluetoothDevice)).booleanValue()));
            }
        } catch (Exception e) {
            L.bluetooth.e("%s,error performDisConnect %s:", "[BtSetManager]:", e);
        }
    }

    public void e(BluetoothDevice bluetoothDevice) {
        if (this.n == null && this.o == null) {
            L.bluetooth.e("%s a2dpSink is null", "[BtSetManager]:");
        } else if (bluetoothDevice == null) {
            L.bluetooth.e("%s onA2dpSinkConnect Device is null", "[BtSetManager]:");
        } else {
            BluetoothAdapter bluetoothAdapter = this.h;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.cancelDiscovery();
            }
            if (bluetoothDevice.getBondState() == 12) {
                a(bluetoothDevice, bluetoothDevice.getName());
            } else {
                bluetoothDevice.createBond();
            }
            L.bluetooth.i("%s trying to Connect device name: %s, address: %s", "[BtSetManager]:", bluetoothDevice.getName(), bluetoothDevice.getAddress());
        }
    }

    private void f(BluetoothDevice bluetoothDevice) {
        L.bluetooth.i("%s connect device: %s", "[BtSetManager]:", bluetoothDevice.getName());
        a(bluetoothDevice, 1, 0);
    }

    private void g(BluetoothDevice bluetoothDevice) {
        L.bluetooth.i("%s Reconnect device: %s", "[BtSetManager]:", bluetoothDevice.getName());
        a(bluetoothDevice, 3, 0);
    }

    private void h(BluetoothDevice bluetoothDevice) {
        L.bluetooth.i("%s connect device: %s", "[BtSetManager]:", bluetoothDevice.getName());
        a(bluetoothDevice, 2, 0);
    }

    public BluetoothDevice getRemoteDeviceByMac(String str) {
        return this.h.getRemoteDevice(str);
    }

    public void b(int i2) {
        if (this.h != null) {
            L.bluetooth.i("%s set ScanMode: %s", "[BtSetManager]:", c(i2));
            this.h.setScanMode(i2);
        }
    }

    private void a(int i2, long j2) {
        if (this.h != null) {
            L.bluetooth.i("%s set ScanMode: %s", "[BtSetManager]:", c(i2));
            this.h.setScanMode(i2, (int) j2);
        }
    }

    public String c(int i2) {
        switch (i2) {
            case 20:
                return "SCAN_MODE_NONE";
            case 21:
                return "SCAN_MODE_CONNECTABLE";
            case 22:
            default:
                L.bluetooth.e("%s not support this Scan Mode: %s", "[BtSetManager]:", Integer.valueOf(i2));
                return "????" + i2;
            case 23:
                return "SCAN_MODE_CONNECTABLE_DISCOVERABLE";
        }
    }

    public String d(int i2) {
        switch (i2) {
            case 10:
                return "BOND_NONE";
            case 11:
                return "BOND_BONDING";
            case 12:
                return "BOND_BONDED";
            default:
                L.bluetooth.e("%s not support this Bond State: %s", "[BtSetManager]:", Integer.valueOf(i2));
                return "???" + i2;
        }
    }

    String a(int i2) {
        switch (i2) {
            case 0:
                return "STATE_DISCONNECTED";
            case 1:
                return "STATE_CONNECTING";
            case 2:
                return "STATE_CONNECTED";
            case 3:
                return "STATE_DISCONNECTING";
            default:
                L.bluetooth.e("%s not support this Connection State: %s", "[BtSetManager]:", Integer.valueOf(i2));
                return "???" + i2;
        }
    }

    public String e(int i2) {
        switch (i2) {
            case 1:
                return "HANDLE_CONNECT";
            case 2:
                return "HANDLE_DISCONNECT";
            case 3:
                return "HANDLE_RECONNECT";
            default:
                L.bluetooth.e("%s not handle this Message: %s", Integer.valueOf(i2));
                return "????" + i2;
        }
    }

    private synchronized void b(BluetoothControlAction.EnumAction enumAction) {
        if ((enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_BT_BUTTON || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_CODE || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_OPEN_BY_VOICE_QUERY || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_BT_BUTTON || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_CODE || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_VOICE_QUERY || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CLOSE_BY_WAIT_TIMEOUT || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_ITEM_CLICK || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_CODE || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_CONNECT_BY_VOICE_QUERY || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_ITEM_CLICK || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_CODE || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_DISCONNECT_BY_VOICE_QUERY || enumAction == BluetoothControlAction.EnumAction.ACTION_REQUEST_RECONNECT_BY_CONNECTION_TIMEOUT) && this.u == enumAction) {
            c(a(enumAction));
        }
    }

    public boolean p() {
        return BluetoothControlAction.EnumAction.ACTION_REQUEST_REMOVE_BONDED_BY_ITEM_CLICK == this.u;
    }

    public synchronized void c(BluetoothControlAction.EnumAction enumAction) {
        L.bluetooth.d("%s Set BT ControlAction: %s", "[BtSetManager]:", enumAction.name());
        this.u = enumAction;
        d(enumAction);
    }

    public synchronized void d(BluetoothControlAction.EnumAction enumAction) {
        L.bluetooth.i("%s post BTControlAction: %s", "[BtSetManager]:", enumAction.name());
        EventBusRegistry.getEventBus().post(new BluetoothControlAction(enumAction));
    }

    private void a(String str) {
        if (BluetoothTTSPlayUtils.isInTTSPlayBlackList(a().f, str)) {
            L.bluetooth.i("%s name:%s is in tts play black list ,address:%s", "[BtSetManager]:", str, a().f);
        } else if (!WiFiUtil.isWifiConnected(this.d) || !SpeechManager.getInstance().isAvailable()) {
            if (!WiFiUtil.isWifiConnected(this.d)) {
                L.bluetooth.i("%s Wifi is Disconnected", "[BtSetManager]:");
            } else if (!SpeechManager.getInstance().isAvailable()) {
                L.bluetooth.i("%s Token is null", "[BtSetManager]:");
            }
            f(R.raw.bluetooth_connect);
        } else {
            L.bluetooth.i("%s Wifi and Token is Connected", "[BtSetManager]:");
            String str2 = "蓝牙已连接至" + str;
            L.bluetooth.i("%s nameContent: %s", "[BtSetManager]:", str2);
            SpeechManager.getInstance().ttsRequest(str2);
        }
    }

    private void f(int i2) {
        L.bluetooth.d("%s playTts", "[BtSetManager]:");
        PromptSoundPlayer.getInstance().play(this.d, i2);
    }

    private void g(int i2) {
        ToastUtil.showCustomToast(i2, 2);
    }

    CurrentBluetooth a() {
        return this.v;
    }

    private void a(int i2, int i3) {
        this.v.b(i2);
        this.v.a(i3);
    }

    public void b(String str) {
        this.v.b(str);
    }

    public synchronized void a(int i2, int i3, String str, String str2) {
        this.v.b(i2);
        this.v.a(i3);
        this.v.setName(str);
        this.v.a(str2);
    }

    /* loaded from: classes3.dex */
    public class CurrentBluetooth {
        private int g;
        private int c = 0;
        private int d = 0;
        private String b = "android.bluetooth.device.action.ACL_DISCONNECTED";
        private String e = null;
        private String f = null;

        CurrentBluetooth() {
            BluetoothSettingManager.this = r1;
        }

        public String getName() {
            return this.e;
        }

        public void setName(String str) {
            this.e = str;
        }

        String a() {
            return this.f;
        }

        void a(String str) {
            this.f = str;
        }

        int b() {
            return this.d;
        }

        void a(int i) {
            this.d = i;
        }

        int c() {
            return this.c;
        }

        void b(int i) {
            this.c = i;
        }

        String d() {
            return this.b;
        }

        void b(String str) {
            this.b = str;
        }

        public int getReason() {
            return this.g;
        }

        public void setReason(int i) {
            this.g = i;
        }

        BluetoothDevice e() {
            if (!TextUtils.isEmpty(this.f)) {
                return BluetoothSettingManager.this.getRemoteDeviceByMac(this.f);
            }
            return null;
        }
    }

    public void destroy() {
        if (this.q) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            defaultAdapter.closeProfileProxy(11, this.o);
            defaultAdapter.closeProfileProxy(2, this.n);
            defaultAdapter.closeProfileProxy(12, this.p);
            Runnable runnable = this.r;
            if (runnable != null) {
                this.e.removeCallbacks(runnable);
            }
            this.d.unregisterReceiver(this.B);
            this.q = false;
        }
    }

    public boolean isEnabled() {
        return this.h.isEnabled();
    }

    public String getBluetoothMacAddress() {
        return Settings.Secure.getString(this.d.getContentResolver(), "bluetooth_address");
    }
}
