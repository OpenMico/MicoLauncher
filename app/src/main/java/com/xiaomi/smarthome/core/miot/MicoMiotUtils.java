package com.xiaomi.smarthome.core.miot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.core.MiotRoom;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class MicoMiotUtils {
    public static final String DEVICE_REFRESH_ACTION = "com.xiaomi.smarthome.refresh_device_json";
    public static final String FREQ_ROOM_ID = "mico_freq";
    public static final String ROOM_ALL = "全部";
    public static final String SCENE_DEVICE_DID = "scene_mico";
    public static final String SP_KEY_HOME_ID = "SP_KEY_ROOM_ID";

    /* loaded from: classes4.dex */
    public interface OnMicoDeviceChangedListener {
        void onDeviceStatusChanged(boolean z);
    }

    public static boolean addToRoomMap(Map<String, List<DeviceInfo>> map, String str, DeviceInfo deviceInfo) {
        boolean z;
        List<DeviceInfo> list = map.get(str);
        if (list == null) {
            list = new ArrayList<>();
            map.put(str, list);
            z = true;
        } else {
            z = false;
        }
        list.add(deviceInfo);
        return z;
    }

    public static MiotHome cloneHome(MiotHome miotHome) {
        MiotHome miotHome2 = new MiotHome();
        if (miotHome == null) {
            return miotHome2;
        }
        miotHome2.homeId = miotHome.homeId;
        miotHome2.homeName = miotHome.homeName;
        miotHome2.selected = miotHome.selected;
        miotHome2.roomList = new ArrayList();
        if (!ContainerUtil.isEmpty(miotHome.roomList)) {
            miotHome2.roomList.addAll(miotHome.roomList);
        }
        miotHome2.homeDeviceList = new ArrayList();
        if (!ContainerUtil.isEmpty(miotHome.homeDeviceList)) {
            miotHome2.homeDeviceList.addAll(miotHome.homeDeviceList);
        }
        return miotHome2;
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @Deprecated
    public static void switchDeviceStatus(ImageView imageView, final DeviceInfo deviceInfo, final OnMicoDeviceChangedListener onMicoDeviceChangedListener) {
        RxViewHelp.debounceClicksWithOneSeconds(imageView).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotUtils$QnMk-7cWgrSEAomTPKm44_KHSyI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MicoMiotUtils.a(DeviceInfo.this, onMicoDeviceChangedListener, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final DeviceInfo deviceInfo, final OnMicoDeviceChangedListener onMicoDeviceChangedListener, Object obj) throws Exception {
        final boolean z = !deviceInfo.currentStatus;
        MicoMiotDeviceManager.getInstance().getDeviceHelper().onDeviceSwitch(deviceInfo.did, deviceInfo.currentStatus, new ICallback.Stub() { // from class: com.xiaomi.smarthome.core.miot.MicoMiotUtils.1
            @Override // com.xiaomi.miot.service.ICallback
            public void onSuccess(Bundle bundle) {
                Logger logger = L.miot;
                StringBuilder sb = new StringBuilder();
                sb.append("turn ");
                sb.append(z ? "on" : "off");
                sb.append(" device Name=%s, Did=%s onSuccess");
                logger.d(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
                OnMicoDeviceChangedListener onMicoDeviceChangedListener2 = onMicoDeviceChangedListener;
                if (onMicoDeviceChangedListener2 != null) {
                    onMicoDeviceChangedListener2.onDeviceStatusChanged(z);
                }
            }

            @Override // com.xiaomi.miot.service.ICallback
            public void onFailure(Bundle bundle) {
                Logger logger = L.miot;
                StringBuilder sb = new StringBuilder();
                sb.append("turn ");
                sb.append(z ? "on" : "off");
                sb.append(" device Name=%s, Did=%s failure");
                logger.w(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
            }
        });
    }

    public static boolean equalHome(MiotHome miotHome, MiotHome miotHome2) {
        if (miotHome == null && miotHome2 == null) {
            return true;
        }
        if (miotHome == null && miotHome2 != null) {
            return false;
        }
        if (miotHome2 != null || miotHome == null) {
            return TextUtils.equals(miotHome.homeId, miotHome.homeId) && TextUtils.equals(miotHome.homeName, miotHome2.homeName);
        }
        return false;
    }

    public static boolean equalDeviceList(List<DeviceInfo> list, List<DeviceInfo> list2) {
        if (ContainerUtil.getSize(list) != ContainerUtil.getSize(list2)) {
            return false;
        }
        for (DeviceInfo deviceInfo : list) {
            for (DeviceInfo deviceInfo2 : list2) {
                if (deviceInfo.did.equals(deviceInfo2.did) && !(TextUtils.equals(deviceInfo.deviceName, deviceInfo2.deviceName) && TextUtils.equals(deviceInfo.subtitle, deviceInfo2.subtitle) && ((TextUtils.isEmpty(deviceInfo.roomInfo) || TextUtils.isEmpty(deviceInfo2.roomInfo) || MicoSupConstants.ROOM_SHARE.equals(deviceInfo.roomInfo) || MicoSupConstants.ROOM_SHARE.equals(deviceInfo2.roomInfo) || TextUtils.equals(deviceInfo.roomInfo, deviceInfo2.roomInfo)) && TextUtils.equals(deviceInfo.roomId, deviceInfo2.roomId) && deviceInfo.isOnline == deviceInfo2.isOnline))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCommonUsedRoom(MiotRoom miotRoom) {
        return miotRoom != null && FREQ_ROOM_ID.equals(miotRoom.roomId);
    }

    public static boolean isSceneDevice(DeviceInfo deviceInfo) {
        return TextUtils.equals(SCENE_DEVICE_DID, deviceInfo.did);
    }

    private MicoMiotUtils() {
    }
}
