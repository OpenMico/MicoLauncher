package com.xiaomi.smarthome.ui.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.adapter.AdapterUtils;
import com.xiaomi.micolauncher.module.homepage.adapter.SpanSizeCallback;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.scene.CommonUsedScene;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotUtils;
import com.xiaomi.smarthome.ui.model.MicoMode;
import com.xiaomi.smarthome.ui.widgets.MiotDeviceViewHolder;
import com.xiaomi.smarthome.ui.widgets.MiotViewCachedManager;
import com.xiaomi.smarthome.ui.widgets.RoomEmptyViewHolder;
import com.xiaomi.smarthome.ui.widgets.SceneListViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MiotDeviceAdapter extends BaseMiotListAdapter<DeviceInfo> {
    private MiotRoom a;
    private BroadcastReceiver b;
    private final Context c;
    private final List<CommonUsedScene> d = new ArrayList();
    private DeviceInfo e = null;
    private final LayoutInflater f;
    private final RecyclerView g;

    public MiotDeviceAdapter(Context context, RecyclerView recyclerView) {
        this.c = context;
        this.f = LayoutInflater.from(context);
        this.g = recyclerView;
    }

    public void setMiotRoom(MiotRoom miotRoom) {
        this.a = miotRoom;
    }

    public void updateDataList(List<CommonUsedScene> list, List<DeviceInfo> list2) {
        a(list);
        updateDataList(list2);
    }

    private void a(List<CommonUsedScene> list) {
        this.d.clear();
        if (!ContainerUtil.isEmpty(list)) {
            this.d.addAll(list);
            if (MicoMiotUtils.isCommonUsedRoom(this.a)) {
                if (this.e == null) {
                    this.e = new DeviceInfo();
                    this.e.did = MicoMiotUtils.SCENE_DEVICE_DID;
                    return;
                }
                return;
            }
        }
        this.e = null;
    }

    @Override // com.xiaomi.smarthome.ui.adapter.BaseMiotListAdapter
    public void updateDataList(List<DeviceInfo> list) {
        this.dataList.clear();
        if (list != null) {
            if (this.e != null) {
                this.dataList.add(this.e);
            }
            this.dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override // com.xiaomi.smarthome.ui.adapter.BaseMiotListAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (ContainerUtil.isEmpty(this.dataList)) {
            return 1;
        }
        return super.getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (ContainerUtil.isEmpty(this.dataList)) {
            return 2;
        }
        return MicoMiotUtils.isSceneDevice((DeviceInfo) this.dataList.get(i)) ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                return new SceneListViewHolder(this.f.inflate(R.layout.item_mico_room_scene_list_view, viewGroup, false), MicoMode.ROOM);
            case 2:
                return new RoomEmptyViewHolder(this.f.inflate(R.layout.item_room_empty_view, viewGroup, false));
            default:
                return MiotViewCachedManager.getInstance().fetchMiotDeviceHolder(this.c);
        }
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (baseViewHolder instanceof SceneListViewHolder) {
            ((SceneListViewHolder) baseViewHolder).bindData(this.d);
        } else if (baseViewHolder instanceof MiotDeviceViewHolder) {
            ((MiotDeviceViewHolder) baseViewHolder).bindData((DeviceInfo) this.dataList.get(i), this.a);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        AdapterUtils.onAttachedToRecyclerView(null, recyclerView, new SpanSizeCallback() { // from class: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotDeviceAdapter$aN0tiKeJGkYRLQvlnIwlpxQCn98
            @Override // com.xiaomi.micolauncher.module.homepage.adapter.SpanSizeCallback
            public final int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
                int a;
                a = MiotDeviceAdapter.this.a(gridLayoutManager, spanSizeLookup, i);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int a(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 1 || itemViewType == 2) {
            return gridLayoutManager.getSpanCount();
        }
        if (spanSizeLookup != null) {
            return spanSizeLookup.getSpanSize(i);
        }
        return 1;
    }

    public void register() {
        if (this.b == null) {
            this.b = new BroadcastReceiver() { // from class: com.xiaomi.smarthome.ui.adapter.MiotDeviceAdapter.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && MiotDeviceAdapter.this.a != null && MicoMiotUtils.DEVICE_REFRESH_ACTION.equals(intent.getAction())) {
                        String stringExtra = intent.getStringExtra("device_info_json");
                        if (TextUtils.isEmpty(stringExtra)) {
                            L.miot.e("MiotRoomViewHolder onDeviceRefresh device_info_json is null");
                            return;
                        }
                        DeviceInfo deviceInfo = (DeviceInfo) Gsons.getGson().fromJson(stringExtra, (Class<Object>) DeviceInfo.class);
                        if (deviceInfo != null && ContainerUtil.hasData(MiotDeviceAdapter.this.dataList)) {
                            int i = 0;
                            while (true) {
                                if (i >= MiotDeviceAdapter.this.dataList.size()) {
                                    i = -1;
                                    break;
                                } else if (deviceInfo.did.equals(((DeviceInfo) MiotDeviceAdapter.this.dataList.get(i)).did)) {
                                    MiotDeviceAdapter.this.dataList.remove(i);
                                    MiotDeviceAdapter.this.dataList.add(i, deviceInfo);
                                    L.miot.d("device=%s, did=%s status change, need refresh adapter", deviceInfo.deviceName, deviceInfo.did);
                                    break;
                                } else {
                                    i++;
                                }
                            }
                            if (i != -1) {
                                MiotDeviceAdapter.this.notifyItemChanged(i);
                            } else {
                                MiotDeviceAdapter.this.notifyDataSetChanged();
                            }
                        }
                    }
                }
            };
        }
        this.c.getApplicationContext().registerReceiver(this.b, new IntentFilter(MicoMiotUtils.DEVICE_REFRESH_ACTION));
    }

    public void unregister() {
        try {
            this.c.getApplicationContext().unregisterReceiver(this.b);
        } catch (IllegalArgumentException e) {
            L.miot.d(e.getMessage());
        }
    }
}
