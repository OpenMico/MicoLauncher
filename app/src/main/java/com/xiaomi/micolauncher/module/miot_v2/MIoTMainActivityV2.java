package com.xiaomi.micolauncher.module.miot_v2;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.view.LoadingDialog;
import com.xiaomi.micolauncher.module.miot_v2.MIoTHomePopupWindow;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import com.xiaomi.smarthome.core.miot.MicoMiotUtils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MIoTMainActivityV2 extends BaseActivity implements MiotDeviceHelper.DevicesStatusCallback {
    public static final String ROOM_CAMERA = "摄像头";
    private View a;
    private MIoTLeftTabHost b;
    private View c;
    private LoadingDialog d;
    private MIoTHomePopupWindow e;
    private CompositeDisposable f;
    private List<DeviceInfo> g;
    private MiotHome h;
    private MiotDeviceHelper i;
    private String j;
    private long k;
    private int m;
    private boolean l = true;
    private int n;
    private int o = this.n;
    private Runnable p = new Runnable() { // from class: com.xiaomi.micolauncher.module.miot_v2.MIoTMainActivityV2.1
        @Override // java.lang.Runnable
        public void run() {
            MIoTMainActivityV2.this.i.refreshDevices();
        }
    };

    private void a(List<MiotDeviceEvent> list, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        this.o = this.b.currentTab;
        L.miot.d("MIoTMainActivityV2 onSaveInstanceState currentIndex=%s", Integer.valueOf(this.o));
        bundle.putInt("extra_current_index", this.o);
        super.onSaveInstanceState(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.o = bundle.getInt("extra_current_index", -1);
            L.miot.w("MIoTMainActivityV2 savedInstanceState remove FRAGMENTS_TAG");
            bundle.remove("android:support:fragments");
        }
        super.onCreate(bundle);
        setContentView(R.layout.activity_miot_v2);
        this.b = (MIoTLeftTabHost) findViewById(R.id.tab_host);
        this.c = findViewById(R.id.miot_mask_view);
        this.a = findViewById(R.id.miot_support_device_empty);
        this.b.setFragmentManager(getSupportFragmentManager());
        this.b.getViewPager().setScrollable(false);
        this.b.getTabWidget().setFocusable(false);
        this.b.getTabWidget().setFocusableInTouchMode(false);
        this.i = new MiotDeviceHelper(this);
        this.i.registerCallback(this);
        this.d = new LoadingDialog(this, new LoadingDialog.LoadingListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$lst0oM_CdNTWx77eWsYJUBBaP9A
            @Override // com.xiaomi.micolauncher.common.view.LoadingDialog.LoadingListener
            public final void onRetry() {
                MIoTMainActivityV2.this.f();
            }
        });
        f();
        setDefaultScheduleDuration();
        MiotManager.enterDeviceListPage();
        L.miot.i("MiotManager.enterDeviceListPage");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.k;
        if (j == 0 || uptimeMillis - j > 45000) {
            this.k = uptimeMillis;
            L.miot.i("MIoTMainActivityV2.onResume refreshDevices...");
            Threads.postInMainThread(this.p);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: loadData */
    public void f() {
        c();
        if (!this.i.getDevices().isEmpty() && !this.i.getEvents().isEmpty()) {
            a(this.i.getEvents(), true);
        }
        a(this.i.getHomeList(), this.i);
    }

    private void a(List<MiotHome> list, MiotDeviceHelper miotDeviceHelper) {
        if (this.i.getDevices().isEmpty()) {
            b();
            return;
        }
        if (list.isEmpty()) {
            MiotHome miotHome = new MiotHome();
            miotHome.homeName = getString(R.string.miot_home_name_empty_suffix, new Object[]{TokenManager.getInstance().getUserId()});
            miotHome.selected = true;
            list.add(miotHome);
        }
        this.b.clear();
        this.j = PreferenceUtils.getSettingString(this, MicoMiotUtils.SP_KEY_HOME_ID, "");
        if (TextUtils.isEmpty(this.j)) {
            this.j = miotDeviceHelper.getHostDeviceHomeId();
        }
        if (TextUtils.isEmpty(this.j)) {
            this.h = list.get(0);
        } else {
            Iterator<MiotHome> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MiotHome next = it.next();
                if (this.j.equals(next.homeId)) {
                    this.h = next;
                    break;
                }
            }
            if (this.h == null) {
                this.h = list.get(0);
            }
        }
        this.g = new ArrayList(miotDeviceHelper.getDevices());
        L.miot.i("MIoTMainActivityV2 setupAdapter DeviceList size=%d", Integer.valueOf(this.g.size()));
        b(list, miotDeviceHelper);
    }

    private void b(final List<MiotHome> list, final MiotDeviceHelper miotDeviceHelper) {
        boolean z;
        final int i;
        String str = this.h.homeName;
        if (TextUtils.isEmpty(str)) {
            str = getString(R.string.miot_home_name_empty_suffix, new Object[]{TokenManager.getInstance().getUserId()});
        }
        String str2 = str;
        int i2 = 0;
        for (MiotHome miotHome : list) {
            miotHome.selected = miotHome.homeId.equals(this.h.homeId);
            String str3 = miotHome.homeName;
            if (TextUtils.isEmpty(str3)) {
                str3 = getString(R.string.miot_home_name_empty_suffix, new Object[]{TokenManager.getInstance().getUserId()});
            }
            int length = str3.length();
            if (i2 == 0) {
                str2 = str3;
                i2 = length;
            }
            if (length > i2) {
                str2 = str3;
                i2 = length;
            }
        }
        TextView textView = new TextView(this);
        textView.setTextSize(getResources().getDimension(R.dimen.miot_room_pop_text_size));
        textView.setText(str2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        textView.measure(makeMeasureSpec, makeMeasureSpec);
        final int measuredWidth = textView.getMeasuredWidth() + (getResources().getDimensionPixelOffset(R.dimen.miot_room_pop_text_margin) * 2);
        this.b.addMIoTHomeTabToFirst(str, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$H07j8Hd9ShkLXClsHigxpd6q89M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MIoTMainActivityV2.this.a(list, measuredWidth, miotDeviceHelper, view);
            }
        });
        List<MiotRoom> list2 = this.h.roomList;
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (String str4 : this.h.homeDeviceList) {
            for (DeviceInfo deviceInfo : miotDeviceHelper.getDevices()) {
                if (deviceInfo.did.equals(str4)) {
                    arrayList.add(deviceInfo);
                }
            }
        }
        List<String> supportCameraDidList = miotDeviceHelper.getSupportCameraDidList();
        Iterator<? extends Parcelable> it = arrayList.iterator();
        loop3: while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            DeviceInfo deviceInfo2 = (DeviceInfo) it.next();
            for (String str5 : supportCameraDidList) {
                if (deviceInfo2.did.equals(str5)) {
                    z = true;
                    break loop3;
                }
            }
        }
        if (z && !supportCameraDidList.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString(MIoTListFragment.EXTRA_ROOM_INFO, ROOM_CAMERA);
            bundle.putStringArrayList(MIoTListFragment.EXTRA_CAMERA_IDS, (ArrayList) supportCameraDidList);
            bundle.putParcelableArrayList(MIoTListFragment.EXTRA_DEVICES, arrayList);
            this.m = 1;
            this.b.addMIoTRoomTab(ROOM_CAMERA, miotDeviceHelper, bundle, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$Kxcu0yO6bElWgDCI-g0K-6dJOlM
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MIoTMainActivityV2.this.b(view);
                }
            });
        }
        if (this.l) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MIoTListFragment.EXTRA_ROOM_INFO, "全部");
            bundle2.putParcelableArrayList(MIoTListFragment.EXTRA_DEVICES, arrayList);
            i = this.m > 0 ? 2 : 1;
            this.n = i;
            this.b.addMIoTRoomTab("全部", miotDeviceHelper, bundle2, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$kR0CqhM7H8BiozJcikTBS6FbggA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MIoTMainActivityV2.this.a(view);
                }
            });
        } else {
            i = 1;
        }
        MiotRoom miotRoom = null;
        for (MiotRoom miotRoom2 : list2) {
            if (miotRoom2.roomId.equals("0")) {
                miotRoom = miotRoom2;
            } else {
                i++;
                Bundle bundle3 = new Bundle();
                bundle3.putString(MIoTListFragment.EXTRA_ROOM_ID, miotRoom2.roomId);
                bundle3.putString(MIoTListFragment.EXTRA_ROOM_INFO, miotRoom2.roomName);
                bundle3.putStringArrayList(MIoTListFragment.EXTRA_ROOM_DEVICE_IDS, (ArrayList) miotRoom2.roomDeviceList);
                bundle3.putParcelableArrayList(MIoTListFragment.EXTRA_DEVICES, arrayList);
                this.b.addMIoTRoomTab(miotRoom2.roomName, miotDeviceHelper, bundle3, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$ZZ_69TZfwpytWnEB2RKakaJyQzA
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MIoTMainActivityV2.this.b(i, view);
                    }
                });
            }
        }
        if (miotRoom != null) {
            final int i3 = i + 1;
            Bundle bundle4 = new Bundle();
            bundle4.putString(MIoTListFragment.EXTRA_ROOM_ID, miotRoom.roomId);
            bundle4.putString(MIoTListFragment.EXTRA_ROOM_INFO, miotRoom.roomName);
            bundle4.putStringArrayList(MIoTListFragment.EXTRA_ROOM_DEVICE_IDS, (ArrayList) miotRoom.roomDeviceList);
            bundle4.putParcelableArrayList(MIoTListFragment.EXTRA_DEVICES, arrayList);
            this.b.addMIoTRoomTab(miotRoom.roomName, miotDeviceHelper, bundle4, new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$ESuEBTtG9nf-_-f52Rw0J09srTY
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MIoTMainActivityV2.this.a(i3, view);
                }
            });
        }
        a();
        L.miot.i("MIoTMainActivityV2 addMIoTData currentIndex=%d, homeDeviceList Size=%d, allDevice Size=%d", Integer.valueOf(this.o), Integer.valueOf(arrayList.size()), Integer.valueOf(miotDeviceHelper.getDevices().size()));
        if (this.o <= 0) {
            this.o = this.n;
        }
        this.b.setCurrentTab(this.o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, int i, final MiotDeviceHelper miotDeviceHelper, View view) {
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.miot_room_pop_location_margin);
        MIoTHomePopupWindow mIoTHomePopupWindow = this.e;
        if (mIoTHomePopupWindow == null) {
            this.e = new MIoTHomePopupWindow(this);
            this.e.setHomeList(list);
            this.e.setPopWindowWidth(i);
            this.e.setHomeSelectListener(new MIoTHomePopupWindow.HomeSelectListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$PjdM9is0sZl0PL9O9NMo0tbqU5o
                @Override // com.xiaomi.micolauncher.module.miot_v2.MIoTHomePopupWindow.HomeSelectListener
                public final void onHomeChanged(MiotHome miotHome, List list2) {
                    MIoTMainActivityV2.this.a(miotDeviceHelper, miotHome, list2);
                }
            });
            this.e.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$YWZTi7Kl2beUydv4WsRv8JOhHoU
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    MIoTMainActivityV2.this.e();
                }
            });
            this.e.show(view, dimensionPixelOffset, dimensionPixelOffset * 2);
            this.c.setVisibility(0);
        } else if (mIoTHomePopupWindow.isShowing()) {
            this.e.dismiss();
        } else {
            this.e.show(view, dimensionPixelOffset, dimensionPixelOffset * 2);
            this.c.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MiotDeviceHelper miotDeviceHelper, MiotHome miotHome, List list) {
        this.e.setHomeList(list);
        a(miotHome, list, miotDeviceHelper);
        this.e.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        this.c.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        this.b.setCurrentTab(this.m, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.b.setCurrentTab(this.n, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i, View view) {
        this.b.setCurrentTab(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, View view) {
        this.b.setCurrentTab(i, true);
    }

    private void a(MiotHome miotHome, List<MiotHome> list, MiotDeviceHelper miotDeviceHelper) {
        if (miotHome != null && !miotHome.homeId.equals(this.h.homeId)) {
            PreferenceUtils.setSettingString(this, MicoMiotUtils.SP_KEY_HOME_ID, miotHome.homeId);
            this.h = miotHome;
            this.m = 0;
            this.b.clear();
            b(list, miotDeviceHelper);
        }
    }

    private void c(final List<DeviceInfo> list, final MiotDeviceHelper miotDeviceHelper) {
        if (this.g != null && list != null) {
            L.miot.d("MIoTMainActivityV2 updateDeviceList cachedSize=%d,  newSize=%d", Integer.valueOf(this.g.size()), Integer.valueOf(list.size()));
            if (this.f == null) {
                this.f = new CompositeDisposable();
            }
            this.f.add(Observable.just(list).subscribeOn(MicoSchedulers.computation()).map(new Function() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$S0MQnElD5gsAzAs6Ug-IbHLipU4
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    Boolean a;
                    a = MIoTMainActivityV2.this.a(list, (List) obj);
                    return a;
                }
            }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTMainActivityV2$AwUgW2jIgiEZRxaUZRmPsFwXJVY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MIoTMainActivityV2.this.a(list, miotDeviceHelper, (Boolean) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a(List list, List list2) throws Exception {
        return Boolean.valueOf(MicoMiotUtils.equalDeviceList(this.g, list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, MiotDeviceHelper miotDeviceHelper, Boolean bool) throws Exception {
        Logger logger = L.miot;
        logger.i("MIoTMainActivityV2 updateDeviceList same as before:" + bool);
        if (!bool.booleanValue()) {
            this.o = this.b.currentTab;
            Logger logger2 = L.miot;
            logger2.i("MIoTMainActivityV2 updateDeviceList currentIndex:" + this.o);
            this.b.clear();
            this.g = new ArrayList(list);
            List<MiotHome> homeList = miotDeviceHelper.getHomeList();
            if (homeList.isEmpty()) {
                MiotHome miotHome = new MiotHome();
                miotHome.homeName = getString(R.string.miot_home_name_empty_suffix, new Object[]{TokenManager.getInstance().getUserId()});
                miotHome.selected = true;
                homeList.add(miotHome);
            }
            if (TextUtils.isEmpty(this.j)) {
                this.j = miotDeviceHelper.getHostDeviceHomeId();
            }
            Iterator<MiotHome> it = homeList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MiotHome next = it.next();
                if (this.j.equals(next.homeId)) {
                    this.h = next;
                    break;
                }
            }
            b(homeList, miotDeviceHelper);
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesRefreshed() {
        L.miot.i("MIoTMainActivityV2 onDevicesRefreshed devices size=%d", Integer.valueOf(this.i.getDevices().size()));
        if (!this.i.getEvents().isEmpty()) {
            a(this.i.getEvents(), false);
        }
        if (!this.i.getDevices().isEmpty()) {
            c(this.i.getDevices(), this.i);
        } else if (this.i.getOriginDeviceCount() > 0) {
            b();
        } else {
            d();
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onDevicesFailed() {
        MiotLog.w("MIoTMainActivityV2 onDevicesFailed");
        if (!this.i.getDevices().isEmpty()) {
            return;
        }
        if (this.i.getOriginDeviceCount() > 0) {
            b();
        } else {
            d();
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public void onEventsUpdated() {
        if (!this.i.getDevices().isEmpty()) {
            a(this.i.getEvents(), true);
        }
    }

    private void a() {
        this.b.setVisibility(0);
        this.a.setVisibility(8);
        LoadingDialog loadingDialog = this.d;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            this.d.dismiss();
        }
    }

    private void b() {
        this.b.setVisibility(8);
        this.a.setVisibility(0);
        LoadingDialog loadingDialog = this.d;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            this.d.dismiss();
        }
    }

    private void c() {
        LoadingDialog loadingDialog = this.d;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    private void d() {
        LoadingDialog loadingDialog = this.d;
        if (loadingDialog != null) {
            loadingDialog.showError();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        MIoTHomePopupWindow mIoTHomePopupWindow = this.e;
        if (mIoTHomePopupWindow != null && mIoTHomePopupWindow.isShowing()) {
            this.e.dismiss();
        }
        Threads.removeCallbacksInMainThread(this.p);
        try {
            this.i.unregisterCallback();
        } catch (IllegalArgumentException e) {
            L.miot.e("MIoTMainActivityV2 onDestroy miotDeviceHelper unregisterCallback ", e);
        }
        CompositeDisposable compositeDisposable = this.f;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            this.f.dispose();
            this.f.clear();
        }
        this.a = null;
        MiotManager.exitDeviceListPage();
        L.miot.i("MiotManager.exitDeviceListPage");
    }

    public MiotHome getCurrentHome() {
        return this.h;
    }
}
