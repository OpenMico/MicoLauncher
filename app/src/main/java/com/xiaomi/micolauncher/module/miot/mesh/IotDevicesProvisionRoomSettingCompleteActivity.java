package com.xiaomi.micolauncher.module.miot.mesh;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.SmartHomeRc4Manager;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class IotDevicesProvisionRoomSettingCompleteActivity extends BaseActivity {
    public static final String DEVICE_INFO = "deviceInfo";
    private Context a;
    private ListView b;
    private a c;
    private MiotMeshDeviceItem e;
    private Runnable f;
    private final List<Map<String, String>> d = new ArrayList();
    private final Handler g = new Handler(Looper.getMainLooper());

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                d();
                break;
            case 1:
                c();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = this;
        this.e = (MiotMeshDeviceItem) getIntent().getParcelableExtra(DEVICE_INFO);
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        d();
        super.onDestroy();
    }

    private void a() {
        setContentView(R.layout.activity_iot_devices_provision_room_setting_complete);
        this.b = (ListView) findViewById(R.id.text_prompt_list);
        e();
        c();
        findViewById(R.id.do_devices_button).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesProvisionRoomSettingCompleteActivity$gVwD1nC9P_e3QF5gWCdOmxzL93c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IotDevicesProvisionRoomSettingCompleteActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        d();
        b();
    }

    private void b() {
        if (this.e != null) {
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.did = this.e.getDid() == null ? "" : this.e.getDid();
            deviceInfo.model = this.e.getModel() == null ? "" : this.e.getModel();
            new MiotDeviceHelper(this).onDevicePageShow(findViewById(R.id.do_devices_button), deviceInfo);
        } else {
            EventBusRegistry.getEventBus().post(new StartMiotUiEvent());
        }
        finish();
    }

    private void c() {
        L.mesh.d("setAutoExitActivity");
        if (this.f == null) {
            this.f = new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$IotDevicesProvisionRoomSettingCompleteActivity$JTtlEuR06yfOrjpvfE7sBn_CQCo
                @Override // java.lang.Runnable
                public final void run() {
                    IotDevicesProvisionRoomSettingCompleteActivity.this.f();
                }
            };
        }
        d();
        this.g.postDelayed(this.f, TimeUnit.SECONDS.toMillis(7L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        L.mesh.d("waitTimer exitActivity ");
        b();
    }

    private void d() {
        Runnable runnable = this.f;
        if (runnable != null) {
            this.g.removeCallbacks(runnable);
        }
    }

    private void e() {
        SmartHomeRc4Manager.getInstance().getVoiceCtrlTips(SystemSetting.getMiotDeviceId(), this.e.getDid(), 0, "name").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new DefaultObserver<String>() { // from class: com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingCompleteActivity.1
            /* renamed from: a */
            public void onSuccess(String str) {
                Logger logger = L.mesh;
                logger.i("getData:" + str);
                IotDevicesProvisionRoomSettingCompleteActivity.this.a(str);
                IotDevicesProvisionRoomSettingCompleteActivity iotDevicesProvisionRoomSettingCompleteActivity = IotDevicesProvisionRoomSettingCompleteActivity.this;
                iotDevicesProvisionRoomSettingCompleteActivity.c = new a(iotDevicesProvisionRoomSettingCompleteActivity.a, IotDevicesProvisionRoomSettingCompleteActivity.this.d, R.layout.activity_iot_devices_provision_room_list_item, new String[]{"text"}, new int[]{R.id.roomListViewItem});
                IotDevicesProvisionRoomSettingCompleteActivity.this.b.setAdapter((ListAdapter) IotDevicesProvisionRoomSettingCompleteActivity.this.c);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str) && str.contains(SkillStore.SkillDetailSection.TYPE_TIPS)) {
            Iterator<JsonElement> it = ((JsonObject) new JsonParser().parse(str)).get("result").getAsJsonObject().get("device").getAsJsonObject().get(SkillStore.SkillDetailSection.TYPE_TIPS).getAsJsonArray().iterator();
            while (it.hasNext()) {
                for (String str2 : ((MiotDeviceUsageTip) Gsons.getGson().fromJson(it.next(), (Class<Object>) MiotDeviceUsageTip.class)).contents) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("text", str2);
                    this.d.add(hashMap);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a extends SimpleAdapter {
        public a(Context context, List<? extends Map<String, ?>> list, int i, String[] strArr, int[] iArr) {
            super(context, list, i, strArr, iArr);
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            TextView textView = (TextView) view2.findViewById(R.id.roomListViewItem);
            textView.getPaint().setShader(new LinearGradient(0.0f, 0.0f, textView.getPaint().getTextSize() * textView.getText().length(), 0.0f, new int[]{view2.getContext().getColor(R.color.room_setting_color_begin), view2.getContext().getColor(R.color.room_setting_color_end)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
            textView.invalidate();
            return view2;
        }
    }
}
