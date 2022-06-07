package com.xiaomi.micolauncher.module.miot.mesh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import com.elvishew.xlog.Logger;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.SmartHomeRc4Manager;
import com.xiaomi.micolauncher.api.model.Miot;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class IotDevicesProvisionRoomSettingActivity extends BaseActivity {
    private Context a;
    private GridView b;
    private List<HashMap<String, String>> c;
    private SimpleAdapter d;
    private final Miot.Room e = new Miot.Room();
    private View f;
    private MiotMeshDeviceItem g;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = this;
        setContentView(R.layout.activity_iot_devices_provision_room_setting);
        this.b = (GridView) findViewById(R.id.room_setting_room_list);
        b();
        this.g = (MiotMeshDeviceItem) getIntent().getParcelableExtra(MeshDevicesShowActivity.SINGLE_BIND_DEVICE_KEY);
        this.b.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesProvisionRoomSettingActivity$ieTHjdB90jNucekgeDqIrHPOON4
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                IotDevicesProvisionRoomSettingActivity.this.a(adapterView, view, i, j);
            }
        });
        findViewById(R.id.room_setting_next_button).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesProvisionRoomSettingActivity$f2Ig3GWoJp_cDfwy0wzjeBNjreU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IotDevicesProvisionRoomSettingActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AdapterView adapterView, View view, int i, long j) {
        HashMap hashMap = (HashMap) this.d.getItem(i);
        L.mesh.i("select roomId=%s, roomName=%s", hashMap.get("roomId"), hashMap.get("text"));
        this.e.name = (String) hashMap.get("text");
        this.e.id = (String) hashMap.get("roomId");
        View view2 = this.f;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.f = view.findViewById(R.id.roomGridViewItemSelected);
        this.f.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        Logger logger = L.mesh;
        logger.i("add to room:" + this.e.name + ", " + this.e.id + ", bindDeviceId:" + this.g.getDid());
        if (TextUtils.isEmpty(this.e.id)) {
            L.mesh.e("need select room");
        } else {
            a();
        }
    }

    private void a() {
        SmartHomeRc4Manager.getInstance().bindDeviceToRome(this.e.id, this.g.getDid()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingActivity.1
            /* renamed from: a */
            public void onSuccess(String str) {
                L.mesh.i("bind to room success");
                if (MiotMeshUtils.isXiaomiWifiSpeaker(IotDevicesProvisionRoomSettingActivity.this.g)) {
                    EventBusRegistry.getEventBus().post(new StartMiotUiEvent());
                } else {
                    Intent intent = new Intent(IotDevicesProvisionRoomSettingActivity.this.getApplicationContext(), IotDevicesProvisionRoomSettingCompleteActivity.class);
                    intent.putExtra(IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO, IotDevicesProvisionRoomSettingActivity.this.g);
                    ActivityLifeCycleManager.startActivityQuietly(intent);
                }
                IotDevicesProvisionRoomSettingActivity.this.finish();
            }
        });
    }

    private void b() {
        SmartHomeRc4Manager.getInstance().getCurrentDeviceHomeInfo().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<Miot.Home>() { // from class: com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingActivity.2
            /* renamed from: a */
            public void onSuccess(Miot.Home home) {
                IotDevicesProvisionRoomSettingActivity iotDevicesProvisionRoomSettingActivity = IotDevicesProvisionRoomSettingActivity.this;
                iotDevicesProvisionRoomSettingActivity.c = iotDevicesProvisionRoomSettingActivity.a(home);
                IotDevicesProvisionRoomSettingActivity iotDevicesProvisionRoomSettingActivity2 = IotDevicesProvisionRoomSettingActivity.this;
                iotDevicesProvisionRoomSettingActivity2.d = new SimpleAdapter(iotDevicesProvisionRoomSettingActivity2.a, IotDevicesProvisionRoomSettingActivity.this.c, R.layout.activity_iot_devices_provision_room_grid_item, new String[]{"text", "roomId"}, new int[]{R.id.roomGridViewItem, R.id.roomGridViewRoomId});
                IotDevicesProvisionRoomSettingActivity.this.b.setAdapter((ListAdapter) IotDevicesProvisionRoomSettingActivity.this.d);
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                L.mesh.e("get RoomInfo error", th);
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<HashMap<String, String>> a(Miot.Home home) {
        if (home == null) {
            L.mesh.e("home is empty");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Miot.Room room : home.roomlist) {
            HashMap hashMap = new HashMap();
            hashMap.put("text", room.name);
            hashMap.put("roomId", room.id);
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
