package com.xiaomi.miot.support.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.RectF;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.MiotDeviceCore;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.scene.GetSceneCallback;
import com.xiaomi.miot.support.scene.RunSceneCallback;
import com.xiaomi.miot.support.ui.header.MiotHeaderData;
import com.xiaomi.miot.support.util.MiotUtil;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class MiotDeviceHelper implements DeviceOperator {
    private static final String ACTION_DEVICE_UPDATED = "com.xiaomi.smarthome.refresh_device";
    private static final String ACTION_DEVICE_UPDATED_JSON = "com.xiaomi.smarthome.refresh_device_json";
    private DevicesStatusCallback mCallback;
    private final Context mContext;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaomi.miot.support.ui.MiotDeviceHelper.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String str = (String) Objects.requireNonNull(intent.getAction());
            switch (str.hashCode()) {
                case -1927085530:
                    if (str.equals(MiotDeviceHelper.ACTION_DEVICE_UPDATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -525137055:
                    if (str.equals("com.xiaomi.smarthome.refresh_device_json")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1144143038:
                    if (str.equals(MiotDeviceCore.ACTION_EVENTS_UPDATED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1270469135:
                    if (str.equals(MiotDeviceCore.ACTION_DEVICES_REFRESHED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1374560008:
                    if (str.equals(MiotDeviceCore.ACTION_DEVICES_FAILED)) {
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
                    MiotDeviceCore.with(MiotDeviceHelper.this.mContext).processDeviceUpdated(intent);
                    return;
                case 1:
                    MiotDeviceCore.with(MiotDeviceHelper.this.mContext).processDeviceUpdatedJson(intent);
                    return;
                case 2:
                    if (MiotDeviceHelper.this.mCallback != null) {
                        MiotDeviceHelper.this.mCallback.onDevicesRefreshed();
                        return;
                    }
                    return;
                case 3:
                    if (MiotDeviceHelper.this.mCallback != null) {
                        MiotDeviceHelper.this.mCallback.onDevicesFailed();
                        return;
                    }
                    return;
                case 4:
                    if (MiotDeviceHelper.this.mCallback != null) {
                        MiotDeviceHelper.this.mCallback.onEventsUpdated();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    /* loaded from: classes3.dex */
    public interface DevicesStatusCallback {
        void onDevicesFailed();

        void onDevicesRefreshed();

        void onEventsUpdated();
    }

    public MiotDeviceHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void registerCallback(DevicesStatusCallback devicesStatusCallback) {
        this.mCallback = devicesStatusCallback;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_DEVICE_UPDATED);
        intentFilter.addAction("com.xiaomi.smarthome.refresh_device_json");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(MiotDeviceCore.ACTION_DEVICES_REFRESHED);
        intentFilter2.addAction(MiotDeviceCore.ACTION_DEVICES_FAILED);
        intentFilter2.addAction(MiotDeviceCore.ACTION_EVENTS_UPDATED);
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.mReceiver, intentFilter2);
    }

    public void unregisterCallback() {
        this.mContext.unregisterReceiver(this.mReceiver);
        LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.mReceiver);
    }

    public void refreshDevices() {
        MiotLog.i("Info: refresh device in MiotDeviceHelper");
        MiotDeviceCore.with(this.mContext).refreshDevices();
    }

    public List<DeviceInfo> getDevices() {
        return MiotDeviceCore.with(this.mContext).getDevices();
    }

    public List<MiotHome> getHomeList() {
        return MiotDeviceCore.with(this.mContext).getHomeList();
    }

    public List<String> getSupportCameraDidList() {
        return MiotDeviceCore.with(this.mContext).getSupportCameraDidList();
    }

    public int getOriginDeviceCount() {
        return MiotDeviceCore.with(this.mContext).getOriginDevicesCount();
    }

    public List<MiotDeviceEvent> getEvents() {
        return MiotDeviceCore.with(this.mContext).getEvents();
    }

    public String getHostDeviceHomeId() {
        return MiotDeviceCore.with(this.mContext).getHostDeviceHomeId();
    }

    public String getHostDeviceRoomId() {
        return MiotDeviceCore.with(this.mContext).getHostDeviceRoomId();
    }

    public String getRoomInfoByRoomId(String str) {
        return MiotDeviceCore.with(this.mContext).getRoomInfoByRoomId(str);
    }

    public List<String> getShareHomeIdList() {
        return MiotDeviceCore.with(this.mContext).getShareHomeIdList();
    }

    public List<String> getFreqDeviceIdList() {
        return MiotDeviceCore.with(this.mContext).getFreqDeviceIdList();
    }

    public void getScenesByHomeId(String str, GetSceneCallback getSceneCallback) {
        MiotDeviceCore.with(this.mContext).getSceneByHomeId(str, getSceneCallback);
    }

    public void runScene(long j, int i, String str, RunSceneCallback runSceneCallback) {
        MiotDeviceCore.with(this.mContext).runScene(j, i, str, runSceneCallback);
    }

    public MiotHeaderData parseEvents(String str, List<MiotDeviceEvent> list) {
        return MiotUtil.parseEvents(str, list, MiotDeviceCore.with(this.mContext));
    }

    @Override // com.xiaomi.miot.support.ui.DeviceOperator
    public void onDevicePageShow(View view, DeviceInfo deviceInfo) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        MiotManager.showDevicePage(view.getContext(), deviceInfo, new RectF(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight()));
    }

    @Override // com.xiaomi.miot.support.ui.DeviceOperator
    public void onDeviceSwitch(String str, boolean z, ICallback iCallback) {
        MiotDeviceCore.with(this.mContext).setDeviceOn(str, z, iCallback);
    }
}
