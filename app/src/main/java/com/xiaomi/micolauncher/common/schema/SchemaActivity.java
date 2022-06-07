package com.xiaomi.micolauncher.common.schema;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.mico.appstorelib.AppStoreUtil;
import com.xiaomi.mico.base.utils.FileUtils;
import com.xiaomi.mico.base.utils.HiddenWifiScanHelper;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.setup.afterlogin.QuickAppSetup;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.crash.DmesgReader;
import com.xiaomi.micolauncher.common.crash.DumpWifiHelper;
import com.xiaomi.micolauncher.common.crash.LogUploader;
import com.xiaomi.micolauncher.common.crash.SystemLogCapturer;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.ubus.UbusManager;
import com.xiaomi.micolauncher.module.NetworkMonitor;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreManager;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.miot.OtDefs;
import com.xiaomi.micolauncher.module.miot.OtInvokers;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeNotification;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SchemaActivity extends Activity {
    public static final String ACTION_CLEAR_LOG = "com.xiaomi.micolauncher.CLEAR_LOG";
    public static final String ACTION_DMESG_LOG = "com.xiaomi.micolauncher.DMESG";
    public static final String ACTION_DUMP_WIFI = "com.xiaomi.micolauncher.DUMP_WIFI";
    public static final String ACTION_GET_ENV = "com.xiaomi.micolauncher.GET_ENV";
    public static final String ACTION_MUTE = "com.xiaomi.micolauncher.MUTE";
    public static final String ACTION_PLAY_URL = "com.xiaomi.micolauncher.PLAY_URL";
    public static final String ACTION_PRINT_BATTERY = "com.xiaomi.micolauncher.PRINT_BATTERY";
    public static final String ACTION_SET_APPSTORE_PREFERENCE = "com.xiaomi.micolauncher.SET_APPSTORE_PREFERENCE";
    public static final String ACTION_SET_ENV = "com.xiaomi.micolauncher.SET_ENV";
    public static final String ACTION_SET_FLOW_PEAK_TIME = "com.xiaomi.micolauncher.SET_FLOW_PEAK_TIME";
    public static final String ACTION_START_CAPTURE_LOG = "com.xiaomi.micolauncher.START_CAPTURE_LOG";
    public static final String ACTION_START_THIRD_APP = "com.xiaomi.micolauncher.START_THIRD_APP";
    public static final String ACTION_START_VIDEO_APP = "com.xiaomi.micolauncher.START_VIDEO_APP";
    public static final String ACTION_STOP_CAPTURE_LOG = "com.xiaomi.micolauncher.STOP_CAPTURE_LOG";
    public static final String ACTION_STOP_PLAY_URL = "com.xiaomi.micolauncher.STOP_PLAY_URL";
    public static final String ACTION_TEST_APPSTORE_TIMER = "com.xiaomi.micolauncher.TEST_APPSTORE_TIMER";
    public static final String ACTION_TEST_MESH_UPGRADE = "com.xiaomi.micolauncher.TEST_MESH_UPGRADE";
    public static final String ACTION_TEST_NOTIFICATION = "com.xiaomi.micolauncher.TEST_NOTIFICATION";
    public static final String ACTION_TEST_NOTIFICATION_CANCEL = "com.xiaomi.micolauncher.TEST_NOTIFICATION_CANCEL";
    public static final String ACTION_TEST_NOTIFICATION_VIEW = "com.xiaomi.micolauncher.TEST_NOTIFICATION_VIEW";
    public static final String ACTION_TEST_OT = "com.xiaomi.micolauncher.TEST_OT";
    public static final String ACTION_TEST_PLAY_VIDEO = "com.xiaomi.micolauncher.TEST_PLAY_VIDEO";
    public static final String ACTION_TEST_QUICKAPP_UPGRADE = "com.xiaomi.micolauncher.TEST_QUICKAPP_UPGRADE";
    public static final String ACTION_UBUS = "com.xiaomi.micolauncher.UBUS";
    public static final String ACTION_UPLOAD_LOG = "com.xiaomi.micolauncher.UPLOAD_LOG";
    public static final String ACTION_WRITE_MICO_SERVICE_TOKEN_TO_FILE = "com.xiaomi.micolauncher.WRITE_MICO_SERVICE_TOKEN_TO_FILE";
    public static final String KEY_ENV = "env";
    public static final String KEY_MESSAGE_FILE = "messageFile";
    public static final String KEY_METHOD = "method";
    public static final String KEY_OT_ACTION = "otAction";
    public static final String KEY_PACKAGE = "package";
    public static final String KEY_PATH = "path";
    public static final String KEY_PAYLOAD = "payload";
    public static final String KEY_TIME = "time";
    public static final String KEY_URL = "url";
    public static final String KEY_VALUE = "keyValue";
    public static final String KEY_VOLUME = "volume";
    public static final double MIN_VOLUME = 1.0E-6d;
    public static final String VALUE_CONNECT_HIDDEN_WIFI = "connectHiddenWifi";
    public static final String VALUE_ENV_PREVIEW = "preview";
    public static final String VALUE_ENV_PREVIEW4TEST = "preview4test";
    public static final String VALUE_ENV_PRODUCTION = "production";
    public static final String VALUE_MOCK_NETWORK_LOST = "mockNetworkLost";
    public static final String VALUE_REGISTER_BIND = "bind";
    public static final String VALUE_REGISTER_METHODS = "registerMethods";
    public static final String VALUE_REGISTER_START = "start";
    public static final String VALUE_SCAN_HIDDEN_WIFI = "scanHiddenWifi";
    private MultiAudioPlayer a;

    @Override // android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        a(getIntent());
        if (getIntent() != null) {
            L.monitor.i("SchemaActivity.onCreate.intent=%s", getIntent().toString());
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
        if (intent != null) {
            L.monitor.i("SchemaActivity.onNewIntent.intent=%s", intent.toString());
        }
    }

    private void a(final Intent intent) {
        if (intent == null) {
            finish();
            return;
        }
        Uri data = intent.getData();
        if (data != null && !SystemSetting.isInitialized(this)) {
            ToastUtil.showToast((int) R.string.init_not_finish);
            finish();
        } else if (data != null) {
            L.base.i("SchemaActivity process schema: %s", data.toString());
            SchemaManager.handleSchema(getApplicationContext(), data);
            finish();
        } else {
            if (intent.getStringExtra("url") != null) {
                new ChildModeNotification(getApplicationContext()).show();
                finish();
            }
            L.ubus.i("SchemaActivity Schema action %s, data %s", intent.getAction(), data);
            Threads.getHeavyWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$SchemaActivity$XSNszOARyt8JBOMqhVBjgjO1_Js
                @Override // java.lang.Runnable
                public final void run() {
                    SchemaActivity.this.g(intent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(Intent intent) {
        if (b(intent)) {
            Threads.postInMainThread(new $$Lambda$ulGU3Ry7hXB73G1du7NGsaT6sk(this));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean b(final Intent intent) {
        char c;
        String action = intent.getAction();
        if (action == null) {
            return true;
        }
        if (ACTION_UBUS.equalsIgnoreCase(action)) {
            d(intent);
            return true;
        }
        int i = 3;
        char c2 = 65535;
        boolean z = false;
        switch (action.hashCode()) {
            case -2084366382:
                if (action.equals(ACTION_TEST_OT)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1946315578:
                if (action.equals(ACTION_UPLOAD_LOG)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -1808360323:
                if (action.equals(ACTION_TEST_PLAY_VIDEO)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1796012640:
                if (action.equals(ACTION_DUMP_WIFI)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -1791160658:
                if (action.equals(ACTION_STOP_CAPTURE_LOG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1740245710:
                if (action.equals(ACTION_CLEAR_LOG)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1617492071:
                if (action.equals(ACTION_WRITE_MICO_SERVICE_TOKEN_TO_FILE)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1610644884:
                if (action.equals(ACTION_START_THIRD_APP)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1460265308:
                if (action.equals(ACTION_PLAY_URL)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -735786140:
                if (action.equals(ACTION_GET_ENV)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -365323440:
                if (action.equals(ACTION_DMESG_LOG)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -174390018:
                if (action.equals(ACTION_TEST_QUICKAPP_UPGRADE)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -113194495:
                if (action.equals(ACTION_TEST_NOTIFICATION_CANCEL)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -111276052:
                if (action.equals(ACTION_TEST_NOTIFICATION_VIEW)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -71997763:
                if (action.equals(ACTION_SET_APPSTORE_PREFERENCE)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 241810072:
                if (action.equals(ACTION_TEST_NOTIFICATION)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 244089985:
                if (action.equals(ACTION_STOP_PLAY_URL)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 307652480:
                if (action.equals(ACTION_START_VIDEO_APP)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 370448371:
                if (action.equals(ACTION_TEST_APPSTORE_TIMER)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 404133625:
                if (action.equals(ACTION_MUTE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1324323440:
                if (action.equals(ACTION_SET_ENV)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1475484974:
                if (action.equals(ACTION_START_CAPTURE_LOG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1553319641:
                if (action.equals(ACTION_SET_FLOW_PEAK_TIME)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1669719895:
                if (action.equals(ACTION_TEST_MESH_UPGRADE)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1940385115:
                if (action.equals(ACTION_PRINT_BATTERY)) {
                    c = '\r';
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
                SystemLogCapturer.getInstance().startCaptureEndlessAsync();
                break;
            case 1:
                SystemLogCapturer.getInstance().stopCaptureLog();
                break;
            case 2:
                SystemLogCapturer.getInstance().clearOldLog();
                break;
            case 3:
                DmesgReader.fetchResultFile();
                break;
            case 4:
                Mic.getInstance().setMicMute(true);
                L.mic.d("SchemaActivity ACTION_MUTE set mute true");
                break;
            case 5:
                String stringExtra = intent.getStringExtra("url");
                if (!ContainerUtil.isEmpty(stringExtra)) {
                    MultiAudioPlayer multiAudioPlayer = this.a;
                    if (multiAudioPlayer != null) {
                        multiAudioPlayer.stop();
                    }
                    if (this.a == null) {
                        this.a = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_MUSIC, true);
                    }
                    this.a.playUrl(stringExtra);
                    float floatExtra = intent.getFloatExtra(KEY_VOLUME, 0.0f);
                    if (floatExtra > 1.0E-6d) {
                        this.a.setVolume(floatExtra);
                    }
                    this.a.playUrl(stringExtra);
                    break;
                } else {
                    L.debug.e("SchemaActivity url is empty");
                    return true;
                }
            case 6:
                MultiAudioPlayer multiAudioPlayer2 = this.a;
                if (multiAudioPlayer2 != null) {
                    multiAudioPlayer2.stop();
                    break;
                }
                break;
            case 7:
                String stringExtra2 = intent.getStringExtra(KEY_ENV);
                int hashCode = stringExtra2.hashCode();
                if (hashCode != -1402387458) {
                    if (hashCode != -318184504) {
                        if (hashCode == 1753018553 && stringExtra2.equals(VALUE_ENV_PRODUCTION)) {
                            c2 = 1;
                        }
                    } else if (stringExtra2.equals("preview")) {
                        c2 = 0;
                    }
                } else if (stringExtra2.equals("preview4test")) {
                    c2 = 2;
                }
                switch (c2) {
                    case 0:
                        i = 2;
                        break;
                    case 1:
                        break;
                    case 2:
                        i = 4;
                        break;
                    default:
                        i = 0;
                        break;
                }
                if (i != 0) {
                    ApiConstants.switchServerEnv(i);
                    ApiManager.getInstance().onEnvSwitched();
                }
                a();
                break;
            case '\b':
                a();
                break;
            case '\t':
                Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$SchemaActivity$n6M3Z9dIehX3frwxSBmZI6iBth8
                    @Override // java.lang.Runnable
                    public final void run() {
                        SchemaActivity.this.f(intent);
                    }
                });
                z = true;
                break;
            case '\n':
                String stringExtra3 = intent.getStringExtra("time");
                if (!TextUtils.isEmpty(stringExtra3)) {
                    if (stringExtra3.indexOf(Constants.ACCEPT_TIME_SEPARATOR_SP) <= 0) {
                        if (stringExtra3.indexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER) > 0) {
                            a(stringExtra3, Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                            break;
                        } else {
                            try {
                                int parseInt = Integer.parseInt(stringExtra3);
                                TimeUtils.setFlowPeakRange(parseInt, parseInt);
                                break;
                            } catch (NumberFormatException e) {
                                L.base.e("SchemaActivity set flow peak time parse error", e);
                                if (DebugHelper.isDebugVersion()) {
                                    ToastUtil.showToast("设置的时间段不合法");
                                    break;
                                }
                            }
                        }
                    } else {
                        a(stringExtra3, Constants.ACCEPT_TIME_SEPARATOR_SP);
                        break;
                    }
                } else {
                    L.base.e("SchemaActivity set flow peak time is empty");
                    if (DebugHelper.isDebugVersion()) {
                        ToastUtil.showToast("设置的时间段为空，将使用默认的早上6-9点时间段");
                        break;
                    }
                }
                break;
            case 11:
                TokenActionSchemaHandleHelper.a(intent);
                break;
            case '\f':
                String stringExtra4 = intent.getStringExtra("package");
                L.base.i("pkg name is %s", stringExtra4);
                ActivityLifeCycleManager.startActivityQuietly(getPackageManager().getLaunchIntentForPackage(stringExtra4));
                break;
            case '\r':
                boolean booleanExtra = intent.getBooleanExtra("stop", false);
                Intent intent2 = new Intent(this, BatteryPrintService.class);
                intent2.putExtra("stop", booleanExtra);
                startService(intent2);
                break;
            case 14:
                String stringExtra5 = intent.getStringExtra("url");
                if (ContainerUtil.isEmpty(stringExtra5)) {
                    L.debug.e("SchemeActivity url is empty");
                }
                ArrayList arrayList = new ArrayList(1);
                VideoItem videoItem = new VideoItem();
                videoItem.setVideoUrl(stringExtra5);
                arrayList.add(videoItem);
                VideoPlayerApi.play(getApplicationContext(), arrayList, 0, VideoMode.MV);
                break;
            case 15:
                String stringExtra6 = intent.getStringExtra("children");
                if (ContainerUtil.hasData(stringExtra6)) {
                    CameraDetectionController.getManager().showEnterChildModeNotification(this, Integer.parseInt(stringExtra6));
                    break;
                }
                break;
            case 16:
                int parseInt2 = Integer.parseInt(intent.getStringExtra("notify"));
                NotificationHelper.AlarmContentText alarmContentText = null;
                if (parseInt2 == 2 || parseInt2 == 4) {
                    alarmContentText = new NotificationHelper.AlarmContentText() { // from class: com.xiaomi.micolauncher.common.schema.SchemaActivity.1
                        @Override // com.xiaomi.micolauncher.common.push.NotificationHelper.AlarmContentText
                        public NotificationHelper.AlarmContent getAlarmContentText(Context context, boolean z2) {
                            return new NotificationHelper.AlarmContent(context.getString(R.string.lateral_sliding_look_more_content), context.getString(R.string.tips));
                        }
                    };
                }
                NotificationHelper.notify(getApplicationContext(), parseInt2, alarmContentText);
                break;
            case 17:
                switch (Integer.parseInt(intent.getStringExtra("notify"))) {
                    case 0:
                        NotificationHelper.cancelNotification(getApplicationContext(), R.string.notification_network_lost);
                        break;
                    case 1:
                        NotificationHelper.cancelNotification(getApplicationContext(), R.string.doorbell_notify);
                        break;
                }
            case 18:
                Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$SchemaActivity$lK4V9uWwLz__NB5L1df-jSi_2wc
                    @Override // java.lang.Runnable
                    public final void run() {
                        SchemaActivity.this.e(intent);
                    }
                });
                break;
            case 19:
                AppStoreUtil.runAppStoreTimer(getApplicationContext(), !AppStoreManager.getManager().isDisableInstallApp());
                break;
            case 20:
                AppStoreUtil.silentUpgradeApps(getApplicationContext(), "com.xiaomi.mesh.gateway");
                break;
            case 21:
                AppStoreUtil.silentUpgradeApps(getApplicationContext(), QuickAppSetup.QUICK_PKG_NAME);
                break;
            case 22:
                String stringExtra7 = intent.getStringExtra(KEY_VALUE);
                if (ContainerUtil.hasData(stringExtra7)) {
                    AppStoreUtil.setPreference(getApplicationContext(), stringExtra7);
                    break;
                }
                break;
            case 23:
                DumpWifiHelper.getInstance().dumpOnce();
                File[] wifiDumpFiles = DumpWifiHelper.getInstance().getWifiDumpFiles();
                if (ContainerUtil.hasData(wifiDumpFiles)) {
                    for (File file : wifiDumpFiles) {
                        L.debug.d("wifi dump log %s", file.getPath());
                    }
                    break;
                }
                break;
            case 24:
                new LogUploader().upload(getApplicationContext());
                break;
        }
        return !z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f(Intent intent) {
        String stringExtra = intent.getStringExtra("package");
        L.base.i("pkg name is %s", stringExtra);
        ThirdPartyAppProxy.getInstance().startApp(this, stringExtra);
        Threads.postInMainThread(new $$Lambda$ulGU3Ry7hXB73G1du7NGsaT6sk(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void e(Intent intent) {
        String stringExtra = intent.getStringExtra(KEY_OT_ACTION);
        if (stringExtra == null) {
            stringExtra = VALUE_REGISTER_METHODS;
        }
        char c = 65535;
        switch (stringExtra.hashCode()) {
            case -1791180152:
                if (stringExtra.equals(VALUE_MOCK_NETWORK_LOST)) {
                    c = 5;
                    break;
                }
                break;
            case -1447521175:
                if (stringExtra.equals(VALUE_CONNECT_HIDDEN_WIFI)) {
                    c = 4;
                    break;
                }
                break;
            case 3023933:
                if (stringExtra.equals(VALUE_REGISTER_BIND)) {
                    c = 1;
                    break;
                }
                break;
            case 109757538:
                if (stringExtra.equals("start")) {
                    c = 2;
                    break;
                }
                break;
            case 219170364:
                if (stringExtra.equals(VALUE_SCAN_HIDDEN_WIFI)) {
                    c = 3;
                    break;
                }
                break;
            case 809535375:
                if (stringExtra.equals(VALUE_REGISTER_METHODS)) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                OtInvokers.getInstance().registerMethodsToReceive(getApplicationContext());
                return;
            case 1:
                OtInvokers.getInstance().bind(getApplicationContext(), false, null);
                return;
            case 2:
                OtInvokers.getInstance().startDevice();
                return;
            case 3:
                OtDefs.WifiScanRequest wifiScanRequest = new OtDefs.WifiScanRequest();
                wifiScanRequest.params = new OtDefs.WifiScanRequestParams();
                wifiScanRequest.params.type = 1;
                wifiScanRequest.params.ssid = HiddenWifiScanHelper.getDefaultHiddenSsid();
                OtInvokers.getInstance().parseAndStartScan(getApplicationContext(), Gsons.getGson().toJson(wifiScanRequest));
                return;
            case 4:
                OtInvokers.getInstance().startConnectHiddenWifi(getApplicationContext(), HiddenWifiScanHelper.getDefaultHiddenSsid(), null);
                return;
            case 5:
                L.base.i("mock send reconnect failed event");
                NetworkMonitor.getInstance().mockSendReconnectFailedEvent();
                return;
            default:
                return;
        }
    }

    private void a(String str, String str2) {
        try {
            String[] split = str.split(str2);
            TimeUtils.setFlowPeakRange(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            L.base.e("SchemaActivity set flow peak time parse error", e);
            if (DebugHelper.isDebugVersion()) {
                ToastUtil.showToast("设置的时间段不合法");
            }
        }
    }

    private void a() {
        ToastUtil.showToast(ApiConstants.getServiceEnvName());
    }

    private void d(Intent intent) {
        String stringExtra = intent.getStringExtra("path");
        String stringExtra2 = intent.getStringExtra(KEY_METHOD);
        String stringExtra3 = intent.getStringExtra(KEY_MESSAGE_FILE);
        String stringExtra4 = intent.getStringExtra(KEY_PAYLOAD);
        if (ContainerUtil.isEmpty(stringExtra) || ContainerUtil.isEmpty(stringExtra2)) {
            finish();
            return;
        }
        if (TextUtils.isEmpty(stringExtra4)) {
            try {
                stringExtra4 = FileUtils.existsFile(stringExtra3) ? new String(Files.readAllBytes(Paths.get(stringExtra3, new String[0]))) : null;
            } catch (IOException e) {
                e.printStackTrace();
                stringExtra4 = null;
            }
        }
        if (stringExtra4 != null && stringExtra4.contains("\\")) {
            stringExtra4 = stringExtra4.replace("\\", "");
        }
        L.ubus.d("schema : path %s, method %s, message %s", stringExtra, stringExtra2, stringExtra4);
        UbusManager.getInstance().handle(getApplicationContext(), stringExtra, stringExtra2, stringExtra4);
        finish();
    }
}
