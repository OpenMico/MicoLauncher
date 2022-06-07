package com.xiaomi.miplay.mylibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.miplay.audioclient.MiPlayDevice;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.ByteUtils;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.mylibrary.utils.MD5Utils;
import com.xiaomi.miplay.mylibrary.utils.SystemProperties;
import com.xiaomi.mipush.sdk.Constants;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class DeviceManager {
    private static final String a = "DeviceManager";
    private String b = "";
    private final Object c = new Object();
    private List<MiDevice> d = new ArrayList();
    private Map<String, Integer> e = new ConcurrentHashMap();
    private Map<String, Integer> f = new ConcurrentHashMap();
    private Map<String, String> g = new ConcurrentHashMap();
    private List<MiDevice> h = Collections.synchronizedList(new ArrayList());
    private List<MiDevice> i = Collections.synchronizedList(new ArrayList());
    private Map<String, List<MiDevice>> j = new ConcurrentHashMap();

    public List<MiDevice> getSessionErrorDeviceList() {
        return this.d;
    }

    public Map<String, Integer> getVolumeMap() {
        return this.e;
    }

    public List<MiDevice> getConnectedMiDeviceList() {
        return this.h;
    }

    public List<MiDevice> getMiDeviceList() {
        return this.i;
    }

    public Map<String, Integer> getPlayStatusMap() {
        return this.f;
    }

    public Map<String, List<MiDevice>> getMiDeviceMap() {
        return this.j;
    }

    public MiPlayDeviceControlCenter parseClientDeviceControlCenter(MiDevice miDevice) {
        MiPlayDeviceControlCenter miPlayDeviceControlCenter = new MiPlayDeviceControlCenter();
        miPlayDeviceControlCenter.setId(miDevice.getUuid());
        if (!TextUtils.isEmpty(miDevice.getGroupId())) {
            miPlayDeviceControlCenter.setName(miDevice.getGroupName());
        } else if (TextUtils.isEmpty(miDevice.getMiName())) {
            miPlayDeviceControlCenter.setName(miDevice.getName());
        } else {
            miPlayDeviceControlCenter.setName(miDevice.getMiName());
        }
        miPlayDeviceControlCenter.setIsGroup(!TextUtils.isEmpty(miDevice.getGroupId()) ? 1 : 0);
        miPlayDeviceControlCenter.setAccountId(miDevice.getAccountId());
        miPlayDeviceControlCenter.setBluetoothMac(miDevice.getBluetoothMac());
        miPlayDeviceControlCenter.setMac(miDevice.getMac());
        if (TextUtils.isEmpty(miDevice.getGroupId())) {
            miPlayDeviceControlCenter.setRoomName(miDevice.getRoomName());
        } else if (!isGroupIdExistTwoSame(miDevice.getGroupId())) {
            miPlayDeviceControlCenter.setRoomName(miDevice.getRoomName());
        } else if (TextUtils.equals(miDevice.getIsMaster(), "1")) {
            miPlayDeviceControlCenter.setRoomName(miDevice.getRoomName());
        } else {
            String stereoMasterRoomName = getStereoMasterRoomName(miDevice);
            String str = a;
            Logger.d(str, "roomName:" + stereoMasterRoomName, new Object[0]);
            miPlayDeviceControlCenter.setRoomName(stereoMasterRoomName);
        }
        String str2 = a;
        Logger.d(str2, "device roomName:" + miDevice.getRoomName(), new Object[0]);
        miPlayDeviceControlCenter.setMiotDid(miDevice.getMiotDid());
        miPlayDeviceControlCenter.setMiName(miDevice.getMiName());
        miPlayDeviceControlCenter.setMiAlias(miDevice.getMiAlias());
        miPlayDeviceControlCenter.setHouse_Id(miDevice.getHouse_Id());
        miPlayDeviceControlCenter.setRoom_Id(miDevice.getRoom_Id());
        miPlayDeviceControlCenter.setRoom_Alias(miDevice.getRoom_Alias());
        miPlayDeviceControlCenter.setStatus(miDevice.getStatus());
        miPlayDeviceControlCenter.setDeviceConnectState(miDevice.getDeviceConnectState());
        miPlayDeviceControlCenter.setMirrorMode(miDevice.getMirrorMode());
        miPlayDeviceControlCenter.setGroupId(miDevice.getGroupId());
        miPlayDeviceControlCenter.setDeviceType(miDevice.getDeviceType());
        miPlayDeviceControlCenter.setIdhash(miDevice.getIdhash());
        miPlayDeviceControlCenter.setAltitude(miDevice.getAltitude());
        miPlayDeviceControlCenter.setAzimuth(miDevice.getAzimuth());
        miPlayDeviceControlCenter.setDistance(miDevice.getDistance());
        miPlayDeviceControlCenter.setIp_address(miDevice.getIp());
        return miPlayDeviceControlCenter;
    }

    public MiPlayDevice parseClientDevice(MiDevice miDevice) {
        MiPlayDevice miPlayDevice = new MiPlayDevice();
        miPlayDevice.setId(miDevice.getUuid());
        if (!TextUtils.isEmpty(miDevice.getGroupId())) {
            miPlayDevice.setName(miDevice.getGroupName());
        } else if (TextUtils.isEmpty(miDevice.getMiName())) {
            miPlayDevice.setName(miDevice.getName());
        } else {
            miPlayDevice.setName(miDevice.getMiName());
        }
        miPlayDevice.setIsGroup(!TextUtils.isEmpty(miDevice.getGroupId()) ? 1 : 0);
        miPlayDevice.setAccountId(miDevice.getAccountId());
        miPlayDevice.setBluetoothMac(miDevice.getBluetoothMac());
        if (TextUtils.isEmpty(miDevice.getGroupId())) {
            miPlayDevice.setRoomName(miDevice.getRoomName());
        } else if (!isGroupIdExistTwoSame(miDevice.getGroupId())) {
            miPlayDevice.setRoomName(miDevice.getRoomName());
        } else if (TextUtils.equals(miDevice.getIsMaster(), "1")) {
            miPlayDevice.setRoomName(miDevice.getRoomName());
        } else {
            miPlayDevice.setRoomName(getStereoMasterRoomName(miDevice));
        }
        miPlayDevice.setMiotDid(miDevice.getMiotDid());
        miPlayDevice.setMiName(miDevice.getMiName());
        miPlayDevice.setMiAlias(miDevice.getMiAlias());
        miPlayDevice.setHouse_Id(miDevice.getHouse_Id());
        miPlayDevice.setRoom_Id(miDevice.getRoom_Id());
        miPlayDevice.setRoom_Alias(miDevice.getRoom_Alias());
        miPlayDevice.setStatus(miDevice.getStatus());
        miPlayDevice.setDeviceConnectState(miDevice.getDeviceConnectState());
        miPlayDevice.setMirrorMode(miDevice.getMirrorMode());
        miPlayDevice.setAltitude(miDevice.getAltitude());
        miPlayDevice.setAzimuth(miDevice.getAzimuth());
        miPlayDevice.setDistance(miDevice.getDistance());
        return miPlayDevice;
    }

    public void removeSessionErrorDeviceList(String str) {
        Logger.i(a, "removeSessionErrorDeviceList.", new Object[0]);
        for (MiDevice miDevice : this.d) {
            if (miDevice.getMac().equals(str)) {
                String str2 = a;
                Logger.d(str2, "delete " + miDevice.getName() + " mac:" + miDevice.getMac(), new Object[0]);
                this.d.remove(miDevice);
                return;
            }
        }
    }

    public List<MiDevice> removeDevice(List<MiDevice> list, String str) {
        Iterator<MiDevice> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MiDevice next = it.next();
            if (next.getMac().equals(str)) {
                String str2 = a;
                Logger.d(str2, "delete " + next.getName() + " mac:" + next.getMac(), new Object[0]);
                list.remove(next);
                break;
            }
        }
        return list;
    }

    public MiDevice updateMiDevice(MiDevice miDevice) {
        if (this.g.size() == 0) {
            Logger.i(a, "update device fail", new Object[0]);
            return miDevice;
        }
        for (String str : this.g.keySet()) {
            if (str.equals("roomName")) {
                miDevice.setRoomName(this.g.get(str));
            } else if (str.equals("accountId")) {
                miDevice.setAccountId(this.g.get(str));
            } else if (str.equals("bluetoothMac")) {
                miDevice.setBluetoothMac(this.g.get(str));
            } else if (str.equals("groupId")) {
                miDevice.setGroupId(this.g.get(str));
            } else if (str.equals("channel")) {
                miDevice.setChannel(this.g.get(str));
            } else if (str.equals("groupName")) {
                miDevice.setGroupName(this.g.get(str));
            } else if (str.equals("miotDid")) {
                miDevice.setMiotDid(this.g.get(str));
            } else if (str.equals("miName")) {
                miDevice.setMiName(this.g.get(str));
            } else if (str.equals("miAlias")) {
                miDevice.setMiAlias(this.g.get(str));
            } else if (str.equals("house_Id")) {
                miDevice.setHouse_Id(this.g.get(str));
            } else if (str.equals("room_Id")) {
                miDevice.setRoom_Id(this.g.get(str));
            } else if (str.equals("room_Alias")) {
                miDevice.setRoom_Alias(this.g.get(str));
            } else if (str.equals("isMaster")) {
                miDevice.setIsMaster(this.g.get(str));
            } else if (str.equals("deviceType")) {
                if (!TextUtils.isEmpty(this.g.get(str))) {
                    int parseInt = Integer.parseInt(this.g.get(str));
                    String str2 = a;
                    Logger.i(str2, "deviceType:" + parseInt, new Object[0]);
                    if (parseInt != -1) {
                        miDevice.setDeviceType(parseInt);
                    }
                }
            } else if (str.equals("needAblum")) {
                miDevice.setNeedAblum(this.g.get(str));
            } else if (str.equals("needLrc")) {
                miDevice.setNeedLrc(this.g.get(str));
            } else if (str.equals("needPos")) {
                miDevice.setNeedPos(this.g.get(str));
            } else if (str.equals("canRevCtrl")) {
                miDevice.setCanRevCtrl(this.g.get(str));
            } else if (str.equals("support")) {
                miDevice.setSupportMpAbility(this.g.get(str));
                String str3 = a;
                Logger.i(str3, "supportmpability:" + this.g.get(str), new Object[0]);
                miDevice.setHaveSupportField(true);
            } else {
                String str4 = a;
                Log.e(str4, "invalid field:" + str);
            }
        }
        this.g.clear();
        return miDevice;
    }

    public boolean deviceEquals(MiDevice miDevice) {
        if (this.g.size() == 0) {
            Logger.i(a, "update device fail", new Object[0]);
            return true;
        }
        for (String str : this.g.keySet()) {
            if (str.equals("roomName")) {
                if (!TextUtils.equals(miDevice.getRoomName(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("accountId")) {
                if (!TextUtils.equals(miDevice.getAccountId(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("bluetoothMac")) {
                if (!TextUtils.equals(miDevice.getBluetoothMac(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("groupId")) {
                if (!TextUtils.equals(miDevice.getGroupId(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("channel")) {
                if (!TextUtils.equals(miDevice.getChannel(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("groupName")) {
                if (!TextUtils.equals(miDevice.getGroupName(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("miotDid")) {
                if (!TextUtils.equals(miDevice.getMiotDid(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("miName")) {
                if (!TextUtils.equals(miDevice.getMiName(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("miAlias")) {
                if (!TextUtils.equals(miDevice.getMiAlias(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("house_Id")) {
                if (!TextUtils.equals(miDevice.getHouse_Id(), this.g.get(str))) {
                    return false;
                }
            } else if (str.equals("room_Id")) {
                if (!TextUtils.equals(miDevice.getRoom_Id(), this.g.get(str))) {
                    return false;
                }
            } else if (!str.equals("room_Alias")) {
                String str2 = a;
                Log.e(str2, "invalid field:" + str);
            } else if (!TextUtils.equals(miDevice.getRoom_Alias(), this.g.get(str))) {
                return false;
            }
        }
        return true;
    }

    public void analysisDeviceInfo(byte[] bArr) {
        int deviceKeyLen;
        Logger.i(a, "analysisDeviceInfo", new Object[0]);
        int i = 0;
        for (int i2 = 0; i2 < bArr.length && (deviceKeyLen = ByteUtils.getDeviceKeyLen(bArr, i)) != -1; i2++) {
            int i3 = i + 1;
            String deviceValue = ByteUtils.getDeviceValue(bArr, deviceKeyLen, i3);
            if (!TextUtils.isEmpty(deviceValue)) {
                int i4 = i3 + deviceKeyLen;
                Logger.d(a, "key:" + deviceValue, new Object[0]);
                int i5 = i4 + 1;
                int deviceValueInfoLen = ByteUtils.getDeviceValueInfoLen(bArr, i5, 2);
                if (deviceValueInfoLen != -1) {
                    int i6 = i5 + 2;
                    String deviceValue2 = ByteUtils.getDeviceValue(bArr, deviceValueInfoLen, i6);
                    i = i6 + deviceValueInfoLen;
                    Logger.d(a, "value :" + deviceValue2, new Object[0]);
                    if (!TextUtils.isEmpty(deviceValue)) {
                        this.g.put(deviceValue, deviceValue2);
                    }
                    if (i == bArr.length) {
                        Logger.i(a, "exit analysis", new Object[0]);
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void clearDeviceMap() {
        Logger.i(a, "clearDeviceMap.", new Object[0]);
        this.g.clear();
    }

    public boolean isDevicesChangeGroupIdNull() {
        Logger.i(a, "isDevicesChangeGroupIdNull", new Object[0]);
        for (String str : this.g.keySet()) {
            if (str.equals("groupId")) {
                return TextUtils.isEmpty(this.g.get(str));
            }
        }
        return false;
    }

    public boolean deviceEquals(MiDevice miDevice, MiDevice miDevice2) {
        if (!TextUtils.equals(miDevice.getRoomName(), miDevice2.getRoomName()) || !TextUtils.equals(miDevice.getAccountId(), miDevice2.getAccountId()) || !TextUtils.equals(miDevice.getGroupId(), miDevice2.getGroupId()) || !TextUtils.equals(miDevice.getChannel(), miDevice2.getChannel()) || !TextUtils.equals(miDevice.getGroupName(), miDevice2.getGroupName()) || !TextUtils.equals(miDevice.getMiotDid(), miDevice2.getMiotDid()) || !TextUtils.equals(miDevice.getMiName(), miDevice2.getMiName()) || !TextUtils.equals(miDevice.getMiAlias(), miDevice2.getMiAlias()) || !TextUtils.equals(miDevice.getHouse_Id(), miDevice2.getHouse_Id()) || !TextUtils.equals(miDevice.getRoom_Id(), miDevice2.getRoom_Id()) || !TextUtils.equals(miDevice.getRoom_Alias(), miDevice2.getRoom_Alias())) {
            return false;
        }
        Logger.i(a, "deviceinfo equals", new Object[0]);
        return true;
    }

    public void updateMiDeviceList(MiDevice miDevice) {
        Logger.i(a, "updateMiDeviceList.", new Object[0]);
        for (MiDevice miDevice2 : this.i) {
            if (TextUtils.equals(miDevice.getMac(), miDevice2.getMac())) {
                miDevice2.setRoomName(miDevice.getRoomName());
                miDevice2.setAccountId(miDevice.getAccountId());
                miDevice2.setGroupId(miDevice.getGroupId());
                miDevice2.setChannel(miDevice.getChannel());
                miDevice2.setGroupName(miDevice.getGroupName());
                miDevice2.setMiotDid(miDevice.getMiotDid());
                miDevice2.setMiName(miDevice.getMiName());
                miDevice2.setMiAlias(miDevice.getMiAlias());
                miDevice2.setHouse_Id(miDevice.getHouse_Id());
                miDevice2.setRoom_Id(miDevice.getRoom_Id());
                miDevice2.setRoom_Alias(miDevice.getRoom_Alias());
                miDevice2.setIsMaster(miDevice.getIsMaster());
                miDevice2.setBeReport(miDevice.isBeReport());
                miDevice2.setUuid(miDevice.getUuid());
                miDevice2.setMirrorMode(miDevice.getMirrorMode());
                miDevice2.setDeviceConnectState(miDevice.getDeviceConnectState());
                String str = a;
                Logger.d(str, "updateMiDeviceList end:" + miDevice2.toString(), new Object[0]);
                return;
            }
        }
    }

    public void updateConnectedMiDeviceList(MiDevice miDevice) {
        Logger.i(a, "updateConnectedMiDeviceList.", new Object[0]);
        for (MiDevice miDevice2 : this.h) {
            if (TextUtils.equals(miDevice.getMac(), miDevice2.getMac())) {
                miDevice2.setRoomName(miDevice.getRoomName());
                miDevice2.setAccountId(miDevice.getAccountId());
                miDevice2.setGroupId(miDevice.getGroupId());
                miDevice2.setChannel(miDevice.getChannel());
                miDevice2.setGroupName(miDevice.getGroupName());
                miDevice2.setMiotDid(miDevice.getMiotDid());
                miDevice2.setMiName(miDevice.getMiName());
                miDevice2.setMiAlias(miDevice.getMiAlias());
                miDevice2.setHouse_Id(miDevice.getHouse_Id());
                miDevice2.setRoom_Id(miDevice.getRoom_Id());
                miDevice2.setRoom_Alias(miDevice.getRoom_Alias());
                miDevice2.setIsMaster(miDevice.getIsMaster());
                miDevice2.setBeReport(miDevice.isBeReport());
                miDevice2.setUuid(miDevice.getUuid());
                miDevice2.setMirrorMode(miDevice.getMirrorMode());
                miDevice2.setDeviceConnectState(miDevice.getDeviceConnectState());
                String str = a;
                Logger.d(str, "updateConnectedMiDeviceList end:" + miDevice2.toString(), new Object[0]);
                return;
            }
        }
    }

    public boolean isUuidExists(List<MiDevice> list, String[] strArr) {
        for (MiDevice miDevice : list) {
            Logger.d(a, miDevice.toString(), new Object[0]);
            for (String str : strArr) {
                if (miDevice.getUuid().equals(str)) {
                    Logger.i(a, "uuid exists.", new Object[0]);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDeviceExists(List<MiDevice> list, String str) {
        synchronized (this.c) {
            for (MiDevice miDevice : list) {
                if (miDevice.getMac().equals(str)) {
                    String str2 = a;
                    Logger.d(str2, "device already exists:" + miDevice.getMac(), new Object[0]);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isDeviceExists(String str) {
        synchronized (this.c) {
            for (MiDevice miDevice : getMiDeviceList()) {
                if (TextUtils.equals(miDevice.getMac(), str)) {
                    String str2 = a;
                    Logger.d(str2, "device already exists:" + str, new Object[0]);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isIdhashExists(String str) {
        synchronized (this.c) {
            for (MiDevice miDevice : getMiDeviceList()) {
                if (TextUtils.equals(miDevice.getIdhash(), str)) {
                    Logger.i(a, "idhash already exists:", new Object[0]);
                    return true;
                }
            }
            Logger.i(a, "idhash no exists:", new Object[0]);
            return false;
        }
    }

    public void removeMiDevice(String str) {
        for (MiDevice miDevice : getMiDeviceList()) {
            if (TextUtils.equals(miDevice.getMac(), str)) {
                String str2 = a;
                Logger.d(str2, "removeMiDevice:" + miDevice.getMac(), new Object[0]);
                getMiDeviceList().remove(miDevice);
                return;
            }
        }
    }

    public boolean isDataPipGroupIdExists(MiDevice miDevice) {
        if (getMiDeviceMap().get(miDevice.getGroupId()) == null) {
            Logger.i(a, "isGroupIdExists groupId invalid", new Object[0]);
            return false;
        }
        for (String str : getMiDeviceMap().keySet()) {
            if (str.equals(miDevice.getGroupId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isGroupIdExists(List<MiDevice> list, String str) {
        for (MiDevice miDevice : list) {
            if (TextUtils.equals(miDevice.getGroupId(), str)) {
                Logger.i(a, "groupid exists", new Object[0]);
                return true;
            }
        }
        Logger.i(a, "groupid no-exists", new Object[0]);
        return false;
    }

    public boolean isGroupIdExistTwoSame(String str) {
        Logger.i(a, "isGroupIdExistTwoSame.", new Object[0]);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int i = 0;
        for (MiDevice miDevice : getMiDeviceList()) {
            Logger.d(a, "deviceinfo:" + miDevice.toString(), new Object[0]);
            if (TextUtils.equals(miDevice.getGroupId(), str)) {
                i++;
                Logger.i(a, "ret:" + i, new Object[0]);
                if (i == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getStereoMasterRoomName(MiDevice miDevice) {
        for (MiDevice miDevice2 : getMiDeviceList()) {
            if (TextUtils.equals(miDevice2.getGroupId(), miDevice.getGroupId()) && miDevice2.getIsMaster().equals("1")) {
                return miDevice2.getRoomName();
            }
        }
        return miDevice.getRoomName();
    }

    public int queryGroupIdNum(List<MiDevice> list, String str) {
        Logger.i(a, "queryGroupIdNum.", new Object[0]);
        int i = 0;
        for (MiDevice miDevice : list) {
            Logger.d(a, "deviceinfo:" + miDevice.toString(), new Object[0]);
            if (TextUtils.equals(miDevice.getGroupId(), str)) {
                i++;
            }
        }
        Logger.i(a, "groupidnum:" + i, new Object[0]);
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005b, code lost:
        r7 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int queryOtherConnectStateFromGroupId(java.util.List<com.xiaomi.miplay.mylibrary.MiDevice> r7, java.lang.String r8, java.lang.String r9) {
        /*
            r6 = this;
            java.lang.String r0 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.String r1 = "queryOtherConnectStateFromGroupId."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            java.util.Iterator r7 = r7.iterator()
        L_0x000e:
            boolean r0 = r7.hasNext()
            r1 = 1
            if (r0 == 0) goto L_0x005b
            java.lang.Object r0 = r7.next()
            com.xiaomi.miplay.mylibrary.MiDevice r0 = (com.xiaomi.miplay.mylibrary.MiDevice) r0
            java.lang.String r3 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "deviceinfo:"
            r4.append(r5)
            java.lang.String r5 = r0.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.d(r3, r4, r5)
            java.lang.String r3 = r0.getGroupId()
            boolean r3 = android.text.TextUtils.equals(r3, r8)
            if (r3 == 0) goto L_0x000e
            java.lang.String r3 = r0.getMac()
            boolean r3 = android.text.TextUtils.equals(r3, r9)
            if (r3 != 0) goto L_0x000e
            int r7 = r0.getDeviceConnectState()
            if (r7 != r1) goto L_0x0053
            r7 = r1
            goto L_0x005c
        L_0x0053:
            int r7 = r0.getDeviceConnectState()
            if (r7 != 0) goto L_0x005b
            r7 = r2
            goto L_0x005c
        L_0x005b:
            r7 = -1
        L_0x005c:
            java.lang.String r8 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "ret:"
            r9.append(r0)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r8, r9, r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.DeviceManager.queryOtherConnectStateFromGroupId(java.util.List, java.lang.String, java.lang.String):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005b, code lost:
        r7 = -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int queryCurrentConnectStateFromGroupId(java.util.List<com.xiaomi.miplay.mylibrary.MiDevice> r7, java.lang.String r8, java.lang.String r9) {
        /*
            r6 = this;
            java.lang.String r0 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.String r1 = "queryCurrentConnectStateFromGroupId."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            java.util.Iterator r7 = r7.iterator()
        L_0x000e:
            boolean r0 = r7.hasNext()
            r1 = 1
            if (r0 == 0) goto L_0x005b
            java.lang.Object r0 = r7.next()
            com.xiaomi.miplay.mylibrary.MiDevice r0 = (com.xiaomi.miplay.mylibrary.MiDevice) r0
            java.lang.String r3 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "deviceinfo:"
            r4.append(r5)
            java.lang.String r5 = r0.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.d(r3, r4, r5)
            java.lang.String r3 = r0.getGroupId()
            boolean r3 = android.text.TextUtils.equals(r3, r8)
            if (r3 == 0) goto L_0x000e
            java.lang.String r3 = r0.getMac()
            boolean r3 = android.text.TextUtils.equals(r3, r9)
            if (r3 == 0) goto L_0x000e
            int r7 = r0.getDeviceConnectState()
            if (r7 != r1) goto L_0x0053
            r7 = r1
            goto L_0x005c
        L_0x0053:
            int r7 = r0.getDeviceConnectState()
            if (r7 != 0) goto L_0x005b
            r7 = r2
            goto L_0x005c
        L_0x005b:
            r7 = -1
        L_0x005c:
            java.lang.String r8 = com.xiaomi.miplay.mylibrary.DeviceManager.a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "ret:"
            r9.append(r0)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r8, r9, r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.DeviceManager.queryCurrentConnectStateFromGroupId(java.util.List, java.lang.String, java.lang.String):int");
    }

    public String getFirstStereoUuid(String str, String str2) {
        Logger.i(a, "getFirstStereoUuid.", new Object[0]);
        for (MiDevice miDevice : getMiDeviceList()) {
            Logger.d(a, miDevice.toString(), new Object[0]);
            if (TextUtils.equals(miDevice.getGroupId(), str) && !TextUtils.equals(miDevice.getMac(), str2)) {
                return miDevice.getUuid();
            }
        }
        Logger.i(a, "not found other stereo", new Object[0]);
        return "";
    }

    public List<MiDevice> queryDeviceFromGroupId(String str) {
        Logger.i(a, "queryDeviceFromGroupId.", new Object[0]);
        ArrayList arrayList = new ArrayList();
        for (MiDevice miDevice : getMiDeviceList()) {
            Logger.d(a, miDevice.toString(), new Object[0]);
            if (TextUtils.equals(miDevice.getGroupId(), str)) {
                arrayList.add(miDevice);
            }
        }
        return arrayList;
    }

    public MiDevice queryDeviceFromDeviceMac(List<MiDevice> list, String str) {
        Logger.i(a, "queryDeviceFromDeviceMac.", new Object[0]);
        for (MiDevice miDevice : list) {
            Logger.d(a, miDevice.toString(), new Object[0]);
            if (TextUtils.equals(miDevice.getMac(), str)) {
                return new MiDevice(miDevice);
            }
        }
        return null;
    }

    public void updateConnected(List<MiDevice> list, int i, String str) {
        Logger.i(a, "updateConnected.", new Object[0]);
        for (MiDevice miDevice : list) {
            if (TextUtils.equals(miDevice.getMac(), str)) {
                miDevice.setDeviceConnectState(i);
            }
        }
    }

    public String getLocalPhoneName() {
        String string = SystemProperties.getString("persist.sys.device_name", "");
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String string2 = SystemProperties.getString("ro.product.marketname", "");
        if (TextUtils.isEmpty(string2)) {
            string2 = SystemProperties.getString("ro.product.model", "");
        }
        return TextUtils.isEmpty(string2) ? "手机" : string2;
    }

    public String getLocalDeviceModel() {
        String string = SystemProperties.getString("ro.product.marketname", "");
        return TextUtils.isEmpty(string) ? SystemProperties.getString("ro.product.model", "") : string;
    }

    public synchronized byte[] sourceNameToJson(String str, Context context) {
        Logger.i(a, "sourceNameToJson.", new Object[0]);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String bluetoothMac = Constant.getBluetoothMac(context);
        String str2 = "";
        if (!TextUtils.isEmpty(bluetoothMac)) {
            String replaceAll = bluetoothMac.replaceAll(Constants.COLON_SEPARATOR, "");
            Logger.d(a, replaceAll, new Object[0]);
            str2 = MD5Utils.md5EncryptTo32(replaceAll);
            Logger.d(a, str2, new Object[0]);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("sourceName", str);
            jSONObject.putOpt("mSourceBtMac", str2);
            String jSONObject2 = jSONObject.toString();
            String str3 = a;
            Logger.i(str3, "sourceNameToJson:" + jSONObject2, new Object[0]);
            return jSONObject2.getBytes(StandardCharsets.UTF_8);
        } catch (JSONException e) {
            Logger.e(a, "sourceNameToJson", e);
            return null;
        }
    }

    public synchronized byte[] isSameAccountToJson(int i) {
        String jSONObject;
        Logger.i(a, "isSameAccountToJson.", new Object[0]);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.putOpt("isSameAccount", Integer.valueOf(i));
            jSONObject = jSONObject2.toString();
            String str = a;
            Logger.i(str, "isSameAccountToJson:" + jSONObject, new Object[0]);
        } catch (JSONException e) {
            Logger.e(a, "isSameAccountToJson", e);
            return null;
        }
        return jSONObject.getBytes(StandardCharsets.UTF_8);
    }

    public void updateSourceName(byte[] bArr) {
        analysisDeviceInfo(bArr);
        if (this.g.size() == 0) {
            Logger.i(a, "updateSourceName  fail", new Object[0]);
            return;
        }
        for (String str : this.g.keySet()) {
            if (str.equals("sourceName")) {
                this.b = this.g.get(str);
            } else {
                String str2 = a;
                Log.e(str2, "invalid field:" + str);
            }
        }
        this.g.clear();
    }

    public String getSourceName() {
        String str = a;
        Logger.i(str, "getSourceName:" + this.b, new Object[0]);
        return this.b;
    }

    public synchronized byte[] setLocalDeviceInfo(String str, String str2) {
        String jSONObject;
        Logger.i(a, "setLocalDeviceInfo.", new Object[0]);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.putOpt("isLocalSession", str);
            jSONObject2.putOpt("sessionType", str2);
            jSONObject = jSONObject2.toString();
            String str3 = a;
            Logger.i(str3, "setLocalDeviceInfo:" + jSONObject, new Object[0]);
        } catch (JSONException e) {
            Logger.e(a, "setLocalDeviceInfo", e);
            return null;
        }
        return jSONObject.getBytes(StandardCharsets.UTF_8);
    }

    public String getMacById(String str) {
        synchronized (this.c) {
            for (MiDevice miDevice : getMiDeviceList()) {
                if (TextUtils.equals(miDevice.getUuid(), str)) {
                    String str2 = a;
                    Logger.i(str2, "find device id=" + str, new Object[0]);
                    return miDevice.getMac();
                }
            }
            return "";
        }
    }
}
