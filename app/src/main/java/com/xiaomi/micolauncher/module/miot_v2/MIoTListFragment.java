package com.xiaomi.micolauncher.module.miot_v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import com.xiaomi.smarthome.core.miot.MicoMiotUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class MIoTListFragment extends BaseFragment {
    public static final String EXTRA_CAMERA_IDS = "EXTRA_CAMERA_IDS";
    public static final String EXTRA_DEVICES = "EXTRA_DEVICES";
    public static final String EXTRA_ROOM_DEVICE_IDS = "EXTRA_ROOM_DEVICE_IDS";
    public static final String EXTRA_ROOM_ID = "EXTRA_ROOM_ID";
    public static final String EXTRA_ROOM_INFO = "EXTRA_ROOM_INFO";
    private static int a;
    private RecyclerView b;
    private ImageView c;
    private MIoTListAdapter d;
    private String e;
    private String f;
    private List<DeviceInfo> g;
    private List<DeviceInfo> h;
    private MiotDeviceHelper i;
    private BroadcastReceiver j;

    public void setOperator(MiotDeviceHelper miotDeviceHelper) {
        this.i = miotDeviceHelper;
    }

    public static MIoTListFragment newInstance(Bundle bundle) {
        MIoTListFragment mIoTListFragment = new MIoTListFragment();
        if (bundle != null) {
            mIoTListFragment.setArguments(bundle);
        }
        return mIoTListFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        getArguments().remove(EXTRA_DEVICES);
        L.miot.w("MIoTListFragment onSaveInstanceState remove all deviceInfo from Arguments");
        super.onSaveInstanceState(bundle);
    }

    private void b() {
        IntentFilter intentFilter = new IntentFilter(MicoMiotUtils.DEVICE_REFRESH_ACTION);
        this.j = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.miot_v2.MIoTListFragment.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String stringExtra = intent.getStringExtra("device_info_json");
                    if (TextUtils.isEmpty(stringExtra)) {
                        L.miot.e("MIoTListFragment onDeviceRefresh device_info_json is null");
                        return;
                    }
                    DeviceInfo deviceInfo = (DeviceInfo) Gsons.getGson().fromJson(stringExtra, (Class<Object>) DeviceInfo.class);
                    if (deviceInfo != null && ContainerUtil.hasData(MIoTListFragment.this.h)) {
                        int i = 0;
                        while (true) {
                            if (i >= MIoTListFragment.this.h.size()) {
                                i = -1;
                                break;
                            } else if (deviceInfo.did.equals(((DeviceInfo) MIoTListFragment.this.h.get(i)).did)) {
                                MIoTListFragment.this.h.remove(i);
                                MIoTListFragment.this.h.add(i, deviceInfo);
                                L.miot.d("device=%s, did=%s status change, need refresh adapter", deviceInfo.deviceName, deviceInfo.did);
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (MIoTListFragment.this.d == null) {
                            return;
                        }
                        if (i != -1) {
                            MIoTListFragment.this.d.notifyItemChanged(i);
                        } else {
                            MIoTListFragment.this.d.notifyDataSetChanged();
                        }
                    }
                }
            }
        };
        getActivity().registerReceiver(this.j, intentFilter);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        CommonUtils.unregisterReceiver(getActivity(), this.j);
        this.b.setAdapter(null);
        this.d = null;
        this.g = null;
        this.c = null;
        this.j = null;
        this.b = null;
        this.h = null;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        List<DeviceInfo> list;
        MiotHome currentHome;
        a = (int) getResources().getDimension(R.dimen.size_14dp);
        View inflate = layoutInflater.inflate(R.layout.fragment_miot_list, viewGroup, false);
        if (getArguments() != null) {
            this.e = getArguments().getString(EXTRA_ROOM_ID);
            this.f = getArguments().getString(EXTRA_ROOM_INFO);
            ArrayList<String> stringArrayList = getArguments().getStringArrayList(EXTRA_ROOM_DEVICE_IDS);
            this.g = getArguments().getParcelableArrayList(EXTRA_DEVICES);
            if (!(this.g != null || getActivity() == null || (currentHome = ((MIoTMainActivityV2) getActivity()).getCurrentHome()) == null)) {
                ArrayList arrayList = new ArrayList();
                for (String str : currentHome.homeDeviceList) {
                    for (DeviceInfo deviceInfo : this.i.getDevices()) {
                        if (deviceInfo.did.equals(str)) {
                            arrayList.add(deviceInfo);
                        }
                    }
                }
                this.g = arrayList;
            }
            this.h = new ArrayList();
            if (!TextUtils.isEmpty(this.e) && this.g != null && stringArrayList != null) {
                Iterator<String> it = stringArrayList.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    Iterator<DeviceInfo> it2 = this.g.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            DeviceInfo next2 = it2.next();
                            if (next.equals(next2.did)) {
                                if (this.e.equals("0") && MicoSupConstants.ROOM_DEFAULT.equals(next2.roomInfo)) {
                                    this.h.add(next2);
                                } else if (next2.roomId.equals(this.e)) {
                                    this.h.add(next2);
                                }
                            }
                        }
                    }
                }
            } else if (TextUtils.isEmpty(this.f) || !this.f.equals(MIoTMainActivityV2.ROOM_CAMERA)) {
                this.h = this.g;
            } else {
                ArrayList<String> stringArrayList2 = getArguments().getStringArrayList(EXTRA_CAMERA_IDS);
                if (!(stringArrayList2 == null || (list = this.g) == null)) {
                    for (DeviceInfo deviceInfo2 : list) {
                        Iterator<String> it3 = stringArrayList2.iterator();
                        while (it3.hasNext()) {
                            if (deviceInfo2.did.equals(it3.next())) {
                                this.h.add(deviceInfo2);
                            }
                        }
                    }
                }
            }
        }
        a(inflate);
        b();
        return inflate;
    }

    private void a(View view) {
        this.b = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.c = (ImageView) view.findViewById(R.id.empty_view);
        List<DeviceInfo> list = this.h;
        if (list == null || list.isEmpty()) {
            GlideUtils.bindImageView(this, (int) R.drawable.miot_room_devices_empty, this.c);
            this.c.setVisibility(0);
            this.b.setVisibility(8);
            return;
        }
        this.c.setVisibility(8);
        this.b.setLayoutManager(new BaseGridLayoutManager(getContext(), 2, 0, false));
        this.d = new MIoTListAdapter(getContext());
        this.d.setDeviceOperator(this.i);
        this.d.setDeviceInfoList(this.h);
        this.d.setTabRoomName(this.f);
        this.b.setAdapter(this.d);
        this.b.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.miot_v2.MIoTListFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) < r2) {
                    rect.left = MIoTListFragment.a;
                }
                rect.right = MIoTListFragment.a * 3;
                rect.bottom = MIoTListFragment.a;
            }
        });
    }
}
