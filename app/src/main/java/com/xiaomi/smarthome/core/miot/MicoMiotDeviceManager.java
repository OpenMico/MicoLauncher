package com.xiaomi.smarthome.core.miot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.MicoSettingHome;
import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.miot.support.scene.GetSceneCallback;
import com.xiaomi.miot.support.scene.RunSceneCallback;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import com.xiaomi.smarthome.ui.widgets.MicoSceneCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class MicoMiotDeviceManager implements Handler.Callback, GetSceneCallback, MiotDeviceHelper.DevicesStatusCallback {
    public static final int MAX_MESSAGE_DELAY_TIME = 80000;
    public static final int REFRESH_DEVICE = 1001;
    @SuppressLint({"StaticFieldLeak"})
    private static final MicoMiotDeviceManager a = new MicoMiotDeviceManager();
    public static boolean isRegionFeatureOpen = Hardware.isX6A();
    private MiotDeviceHelper b;
    private MiotHome c;
    private String d;
    private String f;
    private Context h;
    private long i;
    private Map<String, List<DeviceInfo>> e = new HashMap();
    private final List<MicoDeviceChangeListener> g = new ArrayList();
    private final List<String> j = new ArrayList();
    private final Map<String, List<CommonUsedScene>> k = new HashMap();
    private final Map<String, Long> l = new HashMap();
    private final List<MicoSceneCallback> m = new ArrayList();
    private String n = null;
    private boolean o = false;
    private int q = 0;
    private final Runnable r = new Runnable() { // from class: com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.1
        @Override // java.lang.Runnable
        public void run() {
            MicoMiotDeviceManager.this.b.refreshDevices();
        }
    };
    private MicoHandler p = new MicoHandler(ThreadUtil.getWorkHandler().getLooper(), "MiotDeviceHandler", this) { // from class: com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager.2
        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return "MiotDeviceHandler";
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
        }
    };

    /* loaded from: classes4.dex */
    public interface MicoDeviceChangeListener {
        void onDeviceChanged();

        void onDeviceEvent();
    }

    public static /* synthetic */ Boolean lambda$8Xo6A66zVslC466JgYhgokTHmz8(MiotRoom miotRoom) {
        return true;
    }

    public static /* synthetic */ Boolean lambda$BWvYPM13Nq5cdqrVlztYdleJfQQ(MiotRoom miotRoom) {
        return true;
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesFailed() {
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 1001) {
            return false;
        }
        L.smarthome.i("MicoMiotDeviceManageron message  REFRESH_DEVICE");
        MiotManager.refreshDevices();
        return true;
    }

    public static MicoMiotDeviceManager getInstance() {
        return a;
    }

    private MicoMiotDeviceManager() {
    }

    public void init(Context context) {
        this.h = context.getApplicationContext();
        this.b = new MiotDeviceHelper(context);
        refresh(true);
        this.b.registerCallback(this);
    }

    public void unInit() {
        MiotDeviceHelper miotDeviceHelper = this.b;
        if (miotDeviceHelper != null) {
            miotDeviceHelper.unregisterCallback();
        }
        this.e.clear();
        Threads.removeCallbacksInMainThread(this.r);
        this.b = null;
        this.g.clear();
        this.k.clear();
        this.l.clear();
        this.m.clear();
        this.h = null;
    }

    public void addListeners(MicoDeviceChangeListener micoDeviceChangeListener) {
        this.g.add(micoDeviceChangeListener);
    }

    public void unregisterListener(MicoDeviceChangeListener micoDeviceChangeListener) {
        this.g.remove(micoDeviceChangeListener);
    }

    public boolean isSharedHome(String str) {
        for (String str2 : this.j) {
            if (TextUtils.equals(str2, str)) {
                return true;
            }
        }
        return false;
    }

    public void refreshDevice() {
        if (this.b != null) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.i;
            if (j == 0 || Math.abs(currentTimeMillis - j) > 45000) {
                this.i = currentTimeMillis;
                L.smarthome.i("refreshDevices...");
                Threads.postInMainThread(this.r);
                return;
            }
            MiotHome miotHome = this.c;
            if (miotHome == null || miotHome.roomList.isEmpty()) {
                refresh(true);
            }
        }
    }

    public void a() {
        for (MicoDeviceChangeListener micoDeviceChangeListener : this.g) {
            micoDeviceChangeListener.onDeviceChanged();
        }
    }

    private void b() {
        for (MicoDeviceChangeListener micoDeviceChangeListener : this.g) {
            micoDeviceChangeListener.onDeviceEvent();
        }
    }

    public MiotHome getCurrentHome() {
        return this.c;
    }

    public MiotRegionAdapter.MiotRegion getCurRegion() {
        Optional<MiotRegionAdapter.MiotRegion> findFirst = getRegionList().stream().filter($$Lambda$MicoMiotDeviceManager$r8rDG80XX965sR4rsUvPchYqVDc.INSTANCE).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        return null;
    }

    public MiotDeviceHelper getDeviceHelper() {
        return this.b;
    }

    public void setUserId(String str) {
        this.f = str;
    }

    public String getUserId() {
        return this.f;
    }

    public boolean isCurrentHome(String str) {
        if (str == null && this.c == null) {
            return true;
        }
        MiotHome miotHome = this.c;
        if (miotHome == null) {
            return false;
        }
        return TextUtils.equals(str, miotHome.homeId);
    }

    public List<DeviceInfo> getDeviceListByRoomId(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.e.get(str);
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesRefreshed() {
        L.smarthome.d("Manager onDevicesRefreshed");
        if (this.b != null) {
            refresh(true);
            if (c() || this.q > 80000) {
                this.q = 80001;
            } else {
                d();
            }
        }
    }

    private boolean c() {
        MiotDeviceHelper miotDeviceHelper = this.b;
        if (miotDeviceHelper == null) {
            return false;
        }
        boolean booleanValue = ((Boolean) ((List) Optional.ofNullable(miotDeviceHelper.getHomeList()).orElse(new ArrayList())).stream().flatMap($$Lambda$MicoMiotDeviceManager$JXYiIEEkbWxfyTRq7eWKNkLnFo.INSTANCE).filter($$Lambda$MicoMiotDeviceManager$KEOW3HiKxgm8isRx3bwdchtVwgU.INSTANCE).findFirst().map($$Lambda$MicoMiotDeviceManager$8Xo6A66zVslC466JgYhgokTHmz8.INSTANCE).orElse(false)).booleanValue();
        Logger logger = L.smarthome;
        logger.d("isSpeekerRoomSeted:" + booleanValue);
        return booleanValue;
    }

    public static /* synthetic */ Stream c(MiotHome miotHome) {
        return ((List) Optional.ofNullable(miotHome.roomList).orElse(new ArrayList())).stream();
    }

    public static /* synthetic */ boolean d(MiotRoom miotRoom) {
        return miotRoom.isCurrentSpeakerRoom && !"0".equals(miotRoom.roomId);
    }

    private void d() {
        this.p.sendEmptyMessageDelayed(1001, this.q);
        int i = this.q;
        if (i == 0) {
            this.q = 5000;
        } else {
            this.q = i * 2;
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onEventsUpdated() {
        L.smarthome.d("Manager onEventsUpdated");
        b();
    }

    @Override // com.xiaomi.miot.support.scene.GetSceneCallback
    public void onGetSceneSuccess(List<CommonUsedScene> list) {
        Logger logger = L.smarthome;
        logger.d("scene %s", "success getSceneList:" + list);
        if (list != null) {
            ArrayList arrayList = new ArrayList(list);
            String queryHomeId = getCurRegion().getQueryHomeId();
            this.k.put(queryHomeId, arrayList);
            this.l.put(queryHomeId, Long.valueOf(System.currentTimeMillis()));
            a(arrayList);
        }
    }

    @Override // com.xiaomi.miot.support.scene.GetSceneCallback
    public void onGetSceneFailed(int i, String str) {
        Logger logger = L.smarthome;
        logger.d("scene %s", "failed getSceneList:" + i + str);
        a(i, str);
    }

    private void a(List<CommonUsedScene> list) {
        for (MicoSceneCallback micoSceneCallback : this.m) {
            if (micoSceneCallback != null) {
                micoSceneCallback.onGetSceneSuccess(this.n, list);
            }
        }
        e();
    }

    private void a(int i, String str) {
        for (MicoSceneCallback micoSceneCallback : this.m) {
            micoSceneCallback.onGetSceneFailed(this.n, i, str);
        }
        e();
    }

    private void e() {
        L.smarthome.d("scene %s", "clear getSceneListCallback");
        this.m.clear();
        this.n = null;
    }

    public void runScene(CommonUsedScene commonUsedScene, RunSceneCallback runSceneCallback) {
        MiotDeviceHelper miotDeviceHelper = this.b;
        if (miotDeviceHelper != null && commonUsedScene != null) {
            miotDeviceHelper.runScene(commonUsedScene.sceneId, commonUsedScene.sceneType, commonUsedScene.sceneName, runSceneCallback);
        } else if (runSceneCallback != null) {
            runSceneCallback.onRunSceneFailed(commonUsedScene != null ? commonUsedScene.sceneName : "", -1, "");
        }
    }

    public void getScenesByHomeId(String str, MicoSceneCallback micoSceneCallback) {
        Logger logger = L.smarthome;
        logger.d("scene %s", "call getScenesByHomeId homeId=" + str);
        if (this.b == null || TextUtils.isEmpty(str)) {
            L.smarthome.i("scene %s", "getScenesByHomeId fail");
            return;
        }
        Long l = this.l.get(str);
        long currentTimeMillis = System.currentTimeMillis();
        if (l == null || Math.abs(currentTimeMillis - l.longValue()) > 30000) {
            this.m.add(micoSceneCallback);
            if (this.n == null) {
                this.n = str;
                this.b.getScenesByHomeId(str, this);
                return;
            }
            return;
        }
        L.smarthome.d("scene %s", "get scene too often");
        if (micoSceneCallback != null) {
            micoSceneCallback.onGetSceneSuccess(str, this.k.get(str));
        }
    }

    public void refresh(boolean z) {
        if (this.b != null) {
            MiotRegionAdapter.MiotRegion curRegion = getCurRegion();
            if (curRegion != null) {
                this.d = curRegion.getQueryHomeId();
            }
            this.j.clear();
            List<String> shareHomeIdList = this.b.getShareHomeIdList();
            if (shareHomeIdList != null) {
                this.j.addAll(shareHomeIdList);
            }
            MiotHome f = f();
            if (f == null) {
                if (!ContainerUtil.isEmpty(this.b.getHomeList())) {
                    f = g();
                } else {
                    return;
                }
            }
            handleHomeDevices(f);
            if (z) {
                a(f);
            }
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$kfQCJybb2siL5Y_YIeKTo-7ciNo
                @Override // java.lang.Runnable
                public final void run() {
                    MicoMiotDeviceManager.this.a();
                }
            });
        }
    }

    private void a(MiotHome miotHome) {
        String str;
        L.smarthomesetting.d("updateHomeListSetting");
        List<MiotHome> homeList = this.b.getHomeList();
        if (!ContainerUtil.isEmpty(homeList)) {
            if (homeList.size() != 1 || !homeList.get(0).homeId.equals("")) {
                ArrayList<MicoSettingHome> micoHomeList = MicoSettings.getMicoHomeList(this.h);
                HashMap hashMap = new HashMap();
                if (!ContainerUtil.isEmpty(micoHomeList)) {
                    for (int i = 0; i < micoHomeList.size(); i++) {
                        MicoSettingHome micoSettingHome = micoHomeList.get(i);
                        hashMap.put(micoSettingHome.getHomeId(), micoSettingHome);
                    }
                    str = a(miotHome, homeList, micoHomeList);
                } else {
                    str = miotHome.homeId;
                }
                for (int i2 = 0; i2 < homeList.size(); i2++) {
                    b(homeList.get(i2));
                }
                L.smarthomesetting.d("defaultSelectedHomeId:" + str);
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < homeList.size(); i3++) {
                    MiotHome miotHome2 = homeList.get(i3);
                    MicoSettingHome micoSettingHome2 = new MicoSettingHome();
                    micoSettingHome2.setHomeId(miotHome2.homeId);
                    micoSettingHome2.setHomeName(miotHome2.homeName);
                    MicoSettingHome micoSettingHome3 = (MicoSettingHome) hashMap.get(miotHome2.homeId);
                    ArrayList<MicoSettingRoom> arrayList2 = null;
                    if (micoSettingHome3 != null) {
                        micoSettingHome2.setSelected(micoSettingHome3.isSelected());
                        micoSettingHome2.setChildSelected(micoSettingHome3.isChildSelected());
                        arrayList2 = micoSettingHome3.getRoomList();
                    }
                    if (!a(micoSettingHome2, miotHome2, arrayList2) && str != null && str.equals(miotHome2.homeId)) {
                        L.smarthomesetting.d("老用户和其他一些select失效情况 选择默认家庭的全部房间:" + micoSettingHome2.getHomeName());
                        micoSettingHome2.setSelected(true);
                    }
                    arrayList.add(micoSettingHome2);
                }
                MicoSettings.setMicoHomeList(this.h, arrayList);
            }
        }
    }

    private boolean a(MicoSettingHome micoSettingHome, MiotHome miotHome, ArrayList<MicoSettingRoom> arrayList) {
        int i;
        HashMap hashMap = new HashMap();
        String str = null;
        if (!ContainerUtil.isEmpty(arrayList)) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                MicoSettingRoom micoSettingRoom = arrayList.get(i2);
                if (micoSettingRoom != null) {
                    hashMap.put(micoSettingRoom.getRoomId(), micoSettingRoom);
                }
                if (micoSettingRoom.isCurrentSpeakerRoom()) {
                    str = micoSettingRoom.getRoomId();
                }
            }
        }
        List<MiotRoom> list = miotHome.roomList;
        if (ContainerUtil.isEmpty(list)) {
            return false;
        }
        ArrayList<MicoSettingRoom> arrayList2 = new ArrayList<>();
        boolean z = false;
        int i3 = -1;
        for (int i4 = 0; i4 < list.size(); i4++) {
            MiotRoom miotRoom = list.get(i4);
            MicoSettingRoom micoSettingRoom2 = new MicoSettingRoom();
            micoSettingRoom2.setRoomId(miotRoom.roomId);
            micoSettingRoom2.setRoomName(miotRoom.roomName);
            micoSettingRoom2.setCurrentSpeakerRoom(miotRoom.isCurrentSpeakerRoom);
            if (miotRoom.isCurrentSpeakerRoom) {
                i3 = i4;
            }
            MicoSettingRoom micoSettingRoom3 = (MicoSettingRoom) hashMap.get(miotRoom.roomId);
            if (micoSettingRoom3 != null) {
                micoSettingRoom2.setSelected(micoSettingRoom3.isSelected());
                micoSettingRoom2.setConfigured(micoSettingRoom3.isConfigured());
            }
            int i5 = Settings.Global.getInt(this.h.getContentResolver(), "configure_network_device_room", 0);
            L.smarthomesetting.d("configureNetwork：" + i5);
            if ((str == null || str.equals("0")) && micoSettingRoom2.isCurrentSpeakerRoom() && i5 == 1 && isRegionFeatureOpen) {
                L.smarthomesetting.d("新用户（配网后用户+设置了所在房间）默认选择音箱所在房间:" + micoSettingRoom2.getRoomName());
                micoSettingHome.setChildSelected(true);
                micoSettingRoom2.setSelected(true);
                if (!"0".equals(micoSettingRoom2.getRoomId())) {
                    Settings.Global.putInt(this.h.getContentResolver(), "configure_network_device_room", 0);
                }
                z = true;
            }
            arrayList2.add(micoSettingRoom2);
        }
        MicoSettingRoom shareHome = getShareHome(miotHome);
        if (shareHome != null) {
            arrayList2.add(shareHome);
            i = -1;
        } else {
            i = -1;
        }
        if (i3 != i) {
            arrayList2.add(0, arrayList2.remove(i3));
        }
        micoSettingHome.setRoomList(arrayList2);
        return z;
    }

    private String a(final MiotHome miotHome, final List<MiotHome> list, ArrayList<MicoSettingHome> arrayList) {
        Optional findFirst = ((ArrayList) Optional.ofNullable(arrayList).orElse(new ArrayList())).stream().filter($$Lambda$MicoMiotDeviceManager$VMLWhKdUbILVkdY4Er20rdxnVJE.INSTANCE).findFirst();
        if (findFirst.isPresent()) {
            return (String) findFirst.map(new Function() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$wupC4gjZNawhOHOH3F2iFUm4Vo8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String a2;
                    a2 = MicoMiotDeviceManager.a(list, miotHome, (MicoSettingHome) obj);
                    return a2;
                }
            }).orElseGet($$Lambda$MicoMiotDeviceManager$S_9CG20MdyYEWEyv4dl0eUGbM4.INSTANCE);
        }
        L.smarthomesetting.e("oldMicoSettingHomes 无Select信息,使用默认");
        return miotHome.homeId;
    }

    public static /* synthetic */ boolean e(MicoSettingHome micoSettingHome) {
        return micoSettingHome.isSelected() || micoSettingHome.isChildSelected();
    }

    public static /* synthetic */ String a(List list, MiotHome miotHome, final MicoSettingHome micoSettingHome) {
        if (((Boolean) list.stream().filter(new Predicate() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$0aB5oL_fMvAbad43KwXUjcZtSx4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean b;
                b = MicoMiotDeviceManager.b(MicoSettingHome.this, (MiotHome) obj);
                return b;
            }
        }).findFirst().map(new Function() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$o8i2URcbFJIrMmSs9eYL-TFYk48
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean a2;
                a2 = MicoMiotDeviceManager.a(MicoSettingHome.this, (MiotHome) obj);
                return a2;
            }
        }).orElse(false)).booleanValue()) {
            return null;
        }
        micoSettingHome.setSelected(false);
        micoSettingHome.setChildSelected(false);
        L.smarthomesetting.e("oldMicoSettingHomes Select信息失效,使用默认");
        return miotHome.homeId;
    }

    public static /* synthetic */ boolean b(MicoSettingHome micoSettingHome, MiotHome miotHome) {
        return miotHome.homeId.equals(micoSettingHome.getHomeId());
    }

    public static /* synthetic */ Boolean a(MicoSettingHome micoSettingHome, final MiotHome miotHome) {
        if (micoSettingHome.isSelected()) {
            return true;
        }
        return (Boolean) ((ArrayList) Optional.ofNullable(micoSettingHome.getRoomList()).orElse(new ArrayList())).stream().filter($$Lambda$MicoMiotDeviceManager$ktuKv2Dj9YQWqNFH8Hm2WfTX4lQ.INSTANCE).findFirst().map(new Function() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$L034VTfmTiD_xOSFL5nv-m29PWw
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean a2;
                a2 = MicoMiotDeviceManager.a(MiotHome.this, (MicoSettingRoom) obj);
                return a2;
            }
        }).orElseGet($$Lambda$MicoMiotDeviceManager$xPCPLsm_Gnim4xY67NkPFF9Xepc.INSTANCE);
    }

    public static /* synthetic */ boolean a(MicoSettingRoom micoSettingRoom, MiotRoom miotRoom) {
        return miotRoom.roomId.equals(micoSettingRoom.getRoomId());
    }

    public static /* synthetic */ Boolean a(MiotHome miotHome, final MicoSettingRoom micoSettingRoom) {
        return (Boolean) ((List) Optional.ofNullable(miotHome.roomList).orElse(new ArrayList())).stream().filter(new Predicate() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$h9-zcnCrJ5KfMrUl22rJOs_qlRM
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean a2;
                a2 = MicoMiotDeviceManager.a(MicoSettingRoom.this, (MiotRoom) obj);
                return a2;
            }
        }).findFirst().map($$Lambda$MicoMiotDeviceManager$BWvYPM13Nq5cdqrVlztYdleJfQQ.INSTANCE).orElseGet($$Lambda$MicoMiotDeviceManager$Gx1NJWgJvixOnF2pyo4XNqsKso.INSTANCE);
    }

    public static /* synthetic */ Boolean j() {
        L.smarthomesetting.e("Home.isChildSelected()=true and micoSettingRoom.isSelected()=false but micoSettingRoom is removed");
        return false;
    }

    public static /* synthetic */ Boolean i() {
        L.smarthomesetting.e("Home.isChildSelected()=true but micoSettingRoom.isSelected()=false");
        return false;
    }

    public static /* synthetic */ String h() {
        L.smarthomesetting.d("oldMicoSettingHomes Select信息有效，不用使用默认");
        return null;
    }

    public void pickRegion(MiotRegionAdapter.MiotRegion miotRegion) {
        Logger logger = L.smarthome;
        logger.d("pickRegion=" + miotRegion.getRegionName());
        a(miotRegion.getQueryHomeId(), (!(miotRegion instanceof MiotRegionAdapter.MiotRegionRoom) || miotRegion.getQueryRoomIds().size() <= 0) ? null : miotRegion.getQueryRoomIds().get(0));
        refresh(false);
    }

    private void a(String str, String str2) {
        L.smarthomesetting.d("updateHomeListSelect homeId=" + str + " roomId=" + str);
        ArrayList<MicoSettingHome> micoHomeList = MicoSettings.getMicoHomeList(this.h);
        if (!ContainerUtil.isEmpty(micoHomeList)) {
            for (int i = 0; i < micoHomeList.size(); i++) {
                MicoSettingHome micoSettingHome = micoHomeList.get(i);
                if (str.equals(micoSettingHome.getHomeId())) {
                    if (!TextUtils.isEmpty(str2)) {
                        micoSettingHome.setSelected(false);
                        micoSettingHome.setChildSelected(true);
                    } else {
                        micoSettingHome.setSelected(true);
                        micoSettingHome.setChildSelected(false);
                    }
                    ArrayList<MicoSettingRoom> roomList = micoSettingHome.getRoomList();
                    if (!ContainerUtil.isEmpty(roomList)) {
                        for (int i2 = 0; i2 < roomList.size(); i2++) {
                            MicoSettingRoom micoSettingRoom = roomList.get(i2);
                            if (micoSettingRoom.getRoomId().equals(str2)) {
                                micoSettingRoom.setSelected(true);
                            } else {
                                micoSettingRoom.setSelected(false);
                            }
                        }
                    }
                } else {
                    micoSettingHome.setSelected(false);
                    micoSettingHome.setChildSelected(false);
                    ArrayList<MicoSettingRoom> roomList2 = micoSettingHome.getRoomList();
                    if (!ContainerUtil.isEmpty(roomList2)) {
                        for (int i3 = 0; i3 < roomList2.size(); i3++) {
                            roomList2.get(i3).setSelected(false);
                        }
                    }
                }
            }
            MicoSettings.setMicoHomeList(this.h, micoHomeList);
        }
    }

    public MicoSettingRoom getShareHome(MiotHome miotHome) {
        ArrayList<DeviceInfo> arrayList = new ArrayList(this.b.getDevices());
        for (String str : miotHome.homeDeviceList) {
            for (DeviceInfo deviceInfo : arrayList) {
                if (deviceInfo.did.equals(str) && TextUtils.isEmpty(deviceInfo.roomId) && MicoSupConstants.ROOM_SHARE.equals(deviceInfo.roomInfo)) {
                    MicoSettingRoom micoSettingRoom = new MicoSettingRoom();
                    micoSettingRoom.setRoomId(MicoSupConstants.SHARE_ROOM_ID);
                    micoSettingRoom.setRoomName(this.h.getResources().getString(R.string.mico_miot_share_room_name));
                    return micoSettingRoom;
                }
            }
        }
        return null;
    }

    public void handleHomeDevices(MiotHome miotHome) {
        L.smarthome.d("Manager refresh begin");
        long currentTimeMillis = System.currentTimeMillis();
        MiotHome cloneHome = MicoMiotUtils.cloneHome(miotHome);
        if (isRegionFeatureOpen) {
            if (Hardware.isX6A()) {
                b(cloneHome, getSettingHome());
            } else {
                a(cloneHome, getSettingHome());
            }
        }
        b(cloneHome);
        MiotRoom miotRoom = new MiotRoom();
        miotRoom.roomId = MicoMiotUtils.FREQ_ROOM_ID;
        miotRoom.roomName = this.h.getString(R.string.mico_miot_freq_device_room_name);
        cloneHome.roomList.add(0, miotRoom);
        ArrayList<DeviceInfo> arrayList = new ArrayList(this.b.getDevices());
        ArrayList<DeviceInfo> arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (String str : cloneHome.homeDeviceList) {
            for (DeviceInfo deviceInfo : arrayList) {
                if (deviceInfo.did.equals(str)) {
                    arrayList2.add(deviceInfo);
                    String str2 = deviceInfo.roomId;
                    if (!TextUtils.isEmpty(str2)) {
                        MicoMiotUtils.addToRoomMap(hashMap, str2, deviceInfo);
                    } else if (MicoSupConstants.ROOM_DEFAULT.equals(deviceInfo.roomInfo)) {
                        MicoMiotUtils.addToRoomMap(hashMap, "0", deviceInfo);
                    } else if (MicoSupConstants.ROOM_SHARE.equals(deviceInfo.roomInfo)) {
                        if (!z) {
                            MiotRoom miotRoom2 = new MiotRoom();
                            miotRoom2.roomId = MicoSupConstants.SHARE_ROOM_ID;
                            miotRoom2.roomName = this.h.getResources().getString(R.string.mico_miot_share_room_name);
                            cloneHome.roomList.add(miotRoom2);
                            z = true;
                        }
                        MicoMiotUtils.addToRoomMap(hashMap, MicoSupConstants.SHARE_ROOM_ID, deviceInfo);
                    }
                }
            }
        }
        if (this.b.getFreqDeviceIdList() != null) {
            for (String str3 : this.b.getFreqDeviceIdList()) {
                for (DeviceInfo deviceInfo2 : arrayList2) {
                    if (deviceInfo2.did.equals(str3)) {
                        MicoMiotUtils.addToRoomMap(hashMap, miotRoom.roomId, deviceInfo2);
                    }
                }
            }
        }
        if (isRegionFeatureOpen) {
            Optional<MiotRoom> findFirst = cloneHome.roomList.stream().filter($$Lambda$MicoMiotDeviceManager$On67J5QmAxzxwXI23BPMntXLgU.INSTANCE).findFirst();
            if (findFirst.isPresent()) {
                cloneHome.roomList.remove(findFirst.get());
                cloneHome.roomList.add(0, findFirst.get());
            }
            if (cloneHome.roomList.size() == 2) {
                cloneHome.roomList.remove(miotRoom);
            }
        }
        this.e = hashMap;
        this.c = cloneHome;
        this.c.selected = true;
        L.smarthome.d("pick home end cost:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    private void a(final MiotHome miotHome, final MicoSettingHome micoSettingHome) {
        if (miotHome != null && micoSettingHome != null) {
            final ArrayList arrayList = new ArrayList();
            miotHome.roomList.forEach(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$ZfOahkqFegl5TetYAFio435RRh0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MicoMiotDeviceManager.b(MicoSettingHome.this, miotHome, arrayList, (MiotRoom) obj);
                }
            });
            miotHome.roomList.removeAll(arrayList);
        }
    }

    public static /* synthetic */ void b(MicoSettingHome micoSettingHome, final MiotHome miotHome, final List list, final MiotRoom miotRoom) {
        micoSettingHome.getRoomList().forEach(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$tQ2lyv5arEqKKogt4MeL4Mp4VjI
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MicoMiotDeviceManager.b(MiotRoom.this, miotHome, list, (MicoSettingRoom) obj);
            }
        });
    }

    public static /* synthetic */ void b(MiotRoom miotRoom, MiotHome miotHome, List list, MicoSettingRoom micoSettingRoom) {
        if (TextUtils.equals(miotRoom.roomId, micoSettingRoom.getRoomId()) && !micoSettingRoom.isConfigured()) {
            miotHome.homeDeviceList.removeAll(miotRoom.roomDeviceList);
            list.add(miotRoom);
        }
    }

    private void b(final MiotHome miotHome, final MicoSettingHome micoSettingHome) {
        if (miotHome != null && micoSettingHome != null) {
            final ArrayList arrayList = new ArrayList();
            if (!micoSettingHome.isSelected()) {
                miotHome.roomList.forEach(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$cMWimk-amwi2cAp4qroSUQQGyDE
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MicoMiotDeviceManager.a(MicoSettingHome.this, miotHome, arrayList, (MiotRoom) obj);
                    }
                });
            }
            miotHome.roomList.removeAll(arrayList);
        }
    }

    public static /* synthetic */ void a(MicoSettingHome micoSettingHome, final MiotHome miotHome, final List list, final MiotRoom miotRoom) {
        micoSettingHome.getRoomList().forEach(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$e-55Jxhf_4xUDovscxeJ3INv-7g
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MicoMiotDeviceManager.a(MiotRoom.this, miotHome, list, (MicoSettingRoom) obj);
            }
        });
    }

    public static /* synthetic */ void a(MiotRoom miotRoom, MiotHome miotHome, List list, MicoSettingRoom micoSettingRoom) {
        if (TextUtils.equals(miotRoom.roomId, micoSettingRoom.getRoomId()) && !micoSettingRoom.isSelected()) {
            miotHome.homeDeviceList.removeAll(miotRoom.roomDeviceList);
            list.add(miotRoom);
        }
    }

    private void b(MiotHome miotHome) {
        String hostDeviceRoomId = this.b.getHostDeviceRoomId();
        List<MiotRoom> list = miotHome.roomList;
        MiotRoom miotRoom = null;
        MiotRoom miotRoom2 = null;
        for (MiotRoom miotRoom3 : list) {
            if ("0".equals(miotRoom3.roomId)) {
                miotRoom = miotRoom3;
            } else if (TextUtils.equals(hostDeviceRoomId, miotRoom3.roomId)) {
                miotRoom2 = miotRoom3;
            }
        }
        if (miotRoom != null) {
            list.remove(miotRoom);
            miotRoom.roomName = this.h.getResources().getString(R.string.mico_miot_default_room_name);
            list.add(miotRoom);
        }
        if (miotRoom2 != null) {
            list.remove(miotRoom2);
            list.add(0, miotRoom2);
        }
    }

    private MiotHome f() {
        List<MiotHome> homeList = this.b.getHomeList();
        if (ContainerUtil.isEmpty(homeList)) {
            MiotHome miotHome = new MiotHome();
            miotHome.homeName = this.h.getString(R.string.miot_home_name_empty_suffix, this.f);
            homeList.add(miotHome);
        }
        if (TextUtils.isEmpty(this.d)) {
            this.d = this.b.getHostDeviceHomeId();
        }
        if (TextUtils.isEmpty(this.d)) {
            MiotHome miotHome2 = homeList.get(0);
            this.d = miotHome2.homeId;
            return miotHome2;
        }
        for (MiotHome miotHome3 : homeList) {
            if (TextUtils.equals(this.d, miotHome3.homeId)) {
                return miotHome3;
            }
        }
        return null;
    }

    private MiotHome g() {
        List<MiotHome> homeList = this.b.getHomeList();
        this.d = this.b.getHostDeviceHomeId();
        if (!TextUtils.isEmpty(this.d)) {
            Iterator<MiotHome> it = homeList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MiotHome next = it.next();
                if (TextUtils.equals(this.d, next.homeId)) {
                    this.c = next;
                    break;
                }
            }
        } else {
            this.c = homeList.get(0);
            this.d = this.c.homeId;
        }
        return this.c;
    }

    public boolean isDeviceInfoReady() {
        return this.o;
    }

    public void setDeviceInfoReady(boolean z) {
        this.o = z;
    }

    public List<MiotRegionAdapter.MiotRegion> getRegionList() {
        ArrayList<MicoSettingHome> micoHomeList = MicoSettings.getMicoHomeList(this.h);
        if (micoHomeList == null) {
            return new ArrayList();
        }
        if (!isRegionFeatureOpen) {
            return (List) micoHomeList.stream().flatMap($$Lambda$MicoMiotDeviceManager$B6lJWww6FccuSVqGwTLW1koMDo.INSTANCE).collect(Collectors.toList());
        }
        return (List) micoHomeList.stream().filter($$Lambda$MicoMiotDeviceManager$HwOF98SuAEyLBmdX6uZcEw8HRM.INSTANCE).flatMap($$Lambda$MicoMiotDeviceManager$daTlqaWTmZ5lyfsIRIV9uTc7qQ.INSTANCE).sorted($$Lambda$MicoMiotDeviceManager$QB8LcLOsKMyvpZXmGG4RmOYqEs.INSTANCE).collect(Collectors.toList());
    }

    public static /* synthetic */ Stream d(MicoSettingHome micoSettingHome) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MiotRegionAdapter.MiotRegionHome(micoSettingHome));
        return arrayList.stream();
    }

    public static /* synthetic */ boolean c(MicoSettingHome micoSettingHome) {
        return micoSettingHome.isSelected() || micoSettingHome.isChildSelected();
    }

    public static /* synthetic */ Stream b(final MicoSettingHome micoSettingHome) {
        final ArrayList arrayList = new ArrayList();
        MiotRegionAdapter.MiotRegionHome miotRegionHome = new MiotRegionAdapter.MiotRegionHome(micoSettingHome);
        arrayList.add(miotRegionHome);
        micoSettingHome.getRoomList().forEach(new Consumer() { // from class: com.xiaomi.smarthome.core.miot.-$$Lambda$MicoMiotDeviceManager$7UjmPMnLCVn2Pvhe9dhhbpLAkBI
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MicoMiotDeviceManager.a(arrayList, micoSettingHome, (MicoSettingRoom) obj);
            }
        });
        if (arrayList.size() == 2) {
            arrayList.remove(miotRegionHome);
        }
        return arrayList.stream();
    }

    public static /* synthetic */ void a(List list, MicoSettingHome micoSettingHome, MicoSettingRoom micoSettingRoom) {
        if (micoSettingRoom.isConfigured()) {
            list.add(new MiotRegionAdapter.MiotRegionRoom(micoSettingHome, micoSettingRoom));
        }
    }

    public static /* synthetic */ int a(MiotRegionAdapter.MiotRegion miotRegion, MiotRegionAdapter.MiotRegion miotRegion2) {
        if (miotRegion.isCurrentSpeakerRoom()) {
            return -1;
        }
        if (miotRegion2.isCurrentSpeakerRoom()) {
            return 1;
        }
        if (miotRegion instanceof MiotRegionAdapter.MiotRegionHome) {
            return -1;
        }
        return miotRegion2 instanceof MiotRegionAdapter.MiotRegionHome ? 1 : 0;
    }

    public MicoSettingHome getSettingHome() {
        ArrayList<MicoSettingHome> micoHomeList = MicoSettings.getMicoHomeList(this.h);
        if (micoHomeList == null) {
            return null;
        }
        Optional<MicoSettingHome> findFirst = micoHomeList.stream().filter($$Lambda$MicoMiotDeviceManager$DMDT3L8CHs8nFzlhg8cIxXjBI.INSTANCE).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        return null;
    }

    public static /* synthetic */ boolean a(MicoSettingHome micoSettingHome) {
        return micoSettingHome.isSelected() || micoSettingHome.isChildSelected();
    }
}
