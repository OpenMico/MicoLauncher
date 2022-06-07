package com.xiaomi.smarthome.ui.widgets.housework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4ControlDevice;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceActionBriefInfo;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.category.ValueConstrain;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.DeviceTypeDic;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.entity.SweepRobotStatus;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.UiUtils;
import com.xiaomi.smarthome.databinding.HouseWorkAirerBinding;
import com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBinding;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class HouseWorkView extends FrameLayout {
    private final Map<ViewStub, Boolean> a;
    private final Map<ViewStub, ViewDataBinding> b;
    private final Map<String, List<ViewStub>> c;
    private DeviceInfoBean d;
    private HouseWorkSweepRobotBinding e;
    private HouseWorkAirerBinding f;
    private boolean g;
    private PageLocation h;
    private boolean i;
    private List<IDevice> j;
    private String k;
    private ItemTouchHelper l;
    private RecyclerView.ViewHolder m;
    private final IMiotMsgCallback n;

    public HouseWorkView(@NonNull Context context) {
        this(context, null);
    }

    public HouseWorkView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HouseWorkView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public HouseWorkView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.a = new HashMap();
        this.b = new HashMap();
        this.c = new HashMap();
        this.g = false;
        this.h = PageLocation.PAGE_FIRST;
        this.i = true;
        this.n = new IMiotMsgCallback() { // from class: com.xiaomi.smarthome.ui.widgets.housework.HouseWorkView.1
            @Override // com.xiaomi.miot.support.IMiotMsgCallback
            public int getAction() {
                return 1;
            }

            @Override // com.xiaomi.miot.support.IMiotMsgCallback
            public void onMsgCallback(JSONObject jSONObject) {
                DevicePropertyBriefInfo prop;
                if (jSONObject == null) {
                    L.smarthome.d("%s onMsgCallback null", "HouseWorkView:");
                    return;
                }
                try {
                    if (!jSONObject.has("did") || !jSONObject.has("prop") || !jSONObject.has(b.p)) {
                        L.smarthome.e("%s onMsgCallback invalid data %s", "HouseWorkView:", jSONObject.toString());
                        return;
                    }
                    if (!TextUtils.isEmpty(jSONObject.getString("did")) && !TextUtils.isEmpty(jSONObject.getString("prop")) && !TextUtils.isEmpty(jSONObject.getString(b.p))) {
                        String string = jSONObject.getString("did");
                        if (!TextUtils.isEmpty(string)) {
                            String string2 = jSONObject.getString("prop");
                            if (!TextUtils.isEmpty(string2) && string2.startsWith("prop.") && TextUtils.equals(string, HouseWorkView.this.d.getDeviceInfoWrapper().getDeviceInfo().did)) {
                                L.smarthome.d("%s onMsgCallback %s", "HouseWorkView:", jSONObject);
                                if (TextUtils.equals(HouseWorkView.this.d.getDeviceInfoWrapper().getModelInfo().getDeviceType(), DeviceTypeDic.DT_HOUSE_WORK_VACUUM.getDeviceType()) && (prop = MiotDeviceUtils.getProp(HouseWorkView.this.d.getDeviceInfoWrapper(), PropDic.P_STATUS)) != null && TextUtils.equals(string2.replace("prop.", "pi:").replace(".", Constants.COLON_SEPARATOR), prop.getKey())) {
                                    if (prop.getPropValue() == null) {
                                        L.smarthome.e("%s status did=%s propValue is null", "HouseWorkView:", string);
                                        return;
                                    }
                                    prop.getPropValue().setValue(jSONObject.getString(b.p));
                                    HouseWorkView.this.a(HouseWorkView.this.d, HouseWorkView.this.e);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    L.smarthome.e("%s onMsgCallback invalid data %s", "HouseWorkView:", jSONObject.toString());
                } catch (JSONException e) {
                    L.smarthome.e("%s onMsgCallback error %s", "HouseWorkView:", e);
                }
            }
        };
        LayoutInflater.from(context).inflate(R.layout.view_house_work, (ViewGroup) this, true);
        final ViewStub viewStub = (ViewStub) findViewById(R.id.hwSweepRobot);
        final ViewStub viewStub2 = (ViewStub) findViewById(R.id.hwAirer);
        ViewStub.OnInflateListener onInflateListener = new ViewStub.OnInflateListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$nyo8LG0w_9-OMamjV_huZ_Zj5nQ
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub3, View view) {
                HouseWorkView.this.a(viewStub, viewStub2, viewStub3, view);
            }
        };
        viewStub.setOnInflateListener(onInflateListener);
        viewStub2.setOnInflateListener(onInflateListener);
        ArrayList arrayList = new ArrayList();
        arrayList.add(viewStub);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(viewStub2);
        this.c.put(DeviceTypeDic.DT_HOUSE_WORK_VACUUM.getDeviceType(), arrayList);
        this.c.put(DeviceTypeDic.DT_HOUSE_WORK_AIRER.getDeviceType(), arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewStub viewStub, ViewStub viewStub2, ViewStub viewStub3, View view) {
        ViewDataBinding viewDataBinding;
        final DeviceActionBriefInfo deviceActionBriefInfo;
        final DeviceActionBriefInfo deviceActionBriefInfo2;
        final DeviceActionBriefInfo deviceActionBriefInfo3 = null;
        final ValueConstrain valueConstrain = null;
        if (viewStub3 == viewStub) {
            this.e = HouseWorkSweepRobotBinding.bind(view);
            viewDataBinding = this.e;
            final String str = this.d.getDeviceInfoWrapper().getDeviceInfo().did;
            Map<String, List<DeviceActionBriefInfo>> actions = this.d.getDeviceInfoWrapper().getDevicePropInfo().getActions();
            if (actions != null) {
                deviceActionBriefInfo3 = a(actions, "start-charge");
                deviceActionBriefInfo2 = a(actions, "start-sweep");
                deviceActionBriefInfo = a(actions, "stop-sweeping");
            } else {
                deviceActionBriefInfo = null;
                deviceActionBriefInfo2 = null;
            }
            AnimLifecycleManager.repeatOnAttach(this.e.ivCharge, MicoAnimConfigurator4ControlDevice.get());
            this.e.ivCharge.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$M-q-DP2_I1oQZ6EZBOwm1jzMkYs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    HouseWorkView.c(DeviceActionBriefInfo.this, str, view2);
                }
            });
            AnimLifecycleManager.repeatOnAttach(this.e.ivSweep, MicoAnimConfigurator4ControlDevice.get());
            this.e.ivSweep.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$6idl9C2TPO8tHCjLixfxMab06_A
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    HouseWorkView.b(DeviceActionBriefInfo.this, str, view2);
                }
            });
            AnimLifecycleManager.repeatOnAttach(this.e.ivPause, MicoAnimConfigurator4ControlDevice.get());
            this.e.ivPause.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$PTzoIIC2z7Jy_RekcjsTYOUB63I
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    HouseWorkView.a(DeviceActionBriefInfo.this, str, view2);
                }
            });
        } else if (viewStub3 == viewStub2) {
            this.f = HouseWorkAirerBinding.bind(view);
            viewDataBinding = this.f;
            final String str2 = this.d.getDeviceInfoWrapper().getDeviceInfo().did;
            final String propKey = MiotDeviceUtils.getPropKey(this.d.getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
            try {
                valueConstrain = MiotDeviceUtils.getProps(this.d.getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL).get(0).getPropValue().getValueConstrain();
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                L.smarthome.e("HouseWorkView:", e);
            }
            AnimLifecycleManager.repeatOnAttach(this.f.ivUp, MicoAnimConfigurator4ControlDevice.get());
            AnimLifecycleManager.repeatOnAttach(this.f.ivDown, MicoAnimConfigurator4ControlDevice.get());
            AnimLifecycleManager.repeatOnAttach(this.f.ivPause, MicoAnimConfigurator4ControlDevice.get());
            if (valueConstrain != null) {
                this.f.ivUp.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$e8g8fe7DMwKG0OvMVRStkbWJML4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        HouseWorkView.c(str2, propKey, valueConstrain, view2);
                    }
                });
                this.f.ivDown.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$YGieoDC54HIpn94v4zrVKkk3X7w
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        HouseWorkView.b(str2, propKey, valueConstrain, view2);
                    }
                });
                this.f.ivPause.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$nzKsdIUL22mxzzl7VCJVdMq2m2Y
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        HouseWorkView.a(str2, propKey, valueConstrain, view2);
                    }
                });
            }
        } else {
            viewDataBinding = null;
        }
        if (viewDataBinding != null) {
            this.b.put(viewStub3, viewDataBinding);
            a(this.d, viewDataBinding);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(DeviceActionBriefInfo deviceActionBriefInfo, String str, View view) {
        if (deviceActionBriefInfo != null) {
            MiotManager.setDeviceAction(str, deviceActionBriefInfo.getSiid(), deviceActionBriefInfo.getAiid(), $$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no.INSTANCE);
        } else {
            L.smarthome.e("%s click charge button,has no such start-charge action ", "HouseWorkView:");
        }
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(String str) {
        L.smarthome.d("%s start-charge action %s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(DeviceActionBriefInfo deviceActionBriefInfo, String str, View view) {
        if (deviceActionBriefInfo != null) {
            MiotManager.setDeviceAction(str, deviceActionBriefInfo.getSiid(), deviceActionBriefInfo.getAiid(), $$Lambda$HouseWorkView$1DiY3aKOLfm1pZUJM6h0VyZsqys.INSTANCE);
        } else {
            L.smarthome.e("%s click charge button,has no such start-sweep action ", "HouseWorkView:");
        }
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(String str) {
        L.smarthome.d("%s start-sweep action %s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DeviceActionBriefInfo deviceActionBriefInfo, String str, View view) {
        if (deviceActionBriefInfo != null) {
            MiotManager.setDeviceAction(str, deviceActionBriefInfo.getSiid(), deviceActionBriefInfo.getAiid(), $$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA.INSTANCE);
        } else {
            L.smarthome.e("%s click charge button,has no such stop-sweep action ", "HouseWorkView:");
        }
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(String str) {
        L.smarthome.d("%s stop-sweep action %s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(String str) {
        L.smarthome.d("%s ivUp click result=%s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(String str, String str2, ValueConstrain valueConstrain, View view) {
        MiotManager.setDeviceProperty(str, str2, valueConstrain.getValueByDescription("Up"), "int", $$Lambda$HouseWorkView$eLblJAdARNGmS9BqAqTK1Lv_jEw.INSTANCE);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str) {
        L.smarthome.d("%s ivDown click result=%s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str, String str2, ValueConstrain valueConstrain, View view) {
        MiotManager.setDeviceProperty(str, str2, valueConstrain.getValueByDescription("Down"), "int", $$Lambda$HouseWorkView$gWQJU1AEJd5sQPJuo5vGfNCYg6w.INSTANCE);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
        L.smarthome.d("%s ivPause click result=%s", "HouseWorkView:", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, String str2, ValueConstrain valueConstrain, View view) {
        MiotManager.setDeviceProperty(str, str2, valueConstrain.getValueByDescription("Pause"), "int", $$Lambda$HouseWorkView$woPN0ZxkxJOf0lZNBYSaAUwdSQ.INSTANCE);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final DeviceInfoBean deviceInfoBean, final ViewDataBinding viewDataBinding) {
        if (!Threads.isMainThread()) {
            Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$WlJxNL5iUkrQg50fFo12S7qQgeo
                @Override // java.lang.Runnable
                public final void run() {
                    HouseWorkView.this.c(deviceInfoBean, viewDataBinding);
                }
            });
        } else {
            c(deviceInfoBean, viewDataBinding);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x016a  */
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c(final com.xiaomi.smarthome.core.entity.DeviceInfoBean r17, final androidx.databinding.ViewDataBinding r18) {
        /*
            Method dump skipped, instructions count: 971
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.housework.HouseWorkView.c(com.xiaomi.smarthome.core.entity.DeviceInfoBean, androidx.databinding.ViewDataBinding):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewDataBinding viewDataBinding, DeviceInfoBean deviceInfoBean, View view) {
        if (!UiUtils.isFastClick() && !this.g) {
            HashMap hashMap = new HashMap();
            hashMap.put("item", this.d.getDeviceInfoWrapper());
            hashMap.put(OneTrack.Event.VIEW, viewDataBinding.getRoot());
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_OPEN_CARD).post(hashMap);
            if (deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT_MORE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(View view, MotionEvent motionEvent) {
        this.l.startDrag(this.m);
        return false;
    }

    private void a(ViewDataBinding viewDataBinding) {
        if (this.h == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(viewDataBinding.getRoot());
        }
    }

    public void bindData(@Nullable List<IDevice> list, DeviceInfoBean deviceInfoBean, boolean z, PageLocation pageLocation, String str, ItemTouchHelper itemTouchHelper, RecyclerView.ViewHolder viewHolder) {
        this.d = deviceInfoBean;
        this.g = z;
        this.h = pageLocation;
        this.i = pageLocation == PageLocation.PAGE_SECOND;
        this.j = list;
        this.k = str;
        this.l = itemTouchHelper;
        this.m = viewHolder;
        String deviceType = this.d.getDeviceInfoWrapper().getModelInfo().getDeviceType();
        ViewStub viewStub = null;
        if (DeviceTypeDic.DT_HOUSE_WORK_VACUUM.getDeviceType().equals(deviceType)) {
            viewStub = this.c.get(deviceType).get(0);
        } else if (DeviceTypeDic.DT_HOUSE_WORK_AIRER.getDeviceType().equals(deviceType)) {
            viewStub = this.c.get(deviceType).get(0);
        }
        if (viewStub != null && !a(viewStub)) {
            viewStub.inflate();
            this.a.put(viewStub, true);
        }
        ViewDataBinding viewDataBinding = this.b.get(viewStub);
        if (viewDataBinding != null) {
            a(deviceInfoBean, viewDataBinding);
        }
        b(viewStub);
    }

    private boolean a(ViewStub viewStub) {
        Boolean bool = this.a.get(viewStub);
        return bool != null && bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ViewStub viewStub, ViewStub viewStub2, ViewDataBinding viewDataBinding) {
        viewDataBinding.getRoot().setVisibility(viewStub == viewStub2 ? 0 : 8);
    }

    private void b(final ViewStub viewStub) {
        if (viewStub != null) {
            this.b.forEach(new BiConsumer() { // from class: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$cjllkG4vkWnmaIh1Dqg6NKqROVM
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    HouseWorkView.a(viewStub, (ViewStub) obj, (ViewDataBinding) obj2);
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MiotManager.registerMiotMsgCallback(this.n);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MiotManager.unregisterMiotMsgCallback(this.n);
    }

    private DeviceActionBriefInfo a(Map<String, List<DeviceActionBriefInfo>> map, String str) {
        if (map.get(str) == null || map.get(str).isEmpty()) {
            return null;
        }
        return map.get(str).get(0);
    }

    private boolean a(boolean z, String str) {
        if (z) {
            return SweepRobotStatus.MODE_STANDBY.getDescription().equals(str);
        }
        return SweepRobotStatus.MODE_STANDBY.getStatus().equals(str);
    }

    private boolean b(boolean z, String str) {
        if (z) {
            return SweepRobotStatus.MODE_SWEEPING.getDescription().equals(str);
        }
        return SweepRobotStatus.MODE_SWEEPING.getStatus().equals(str);
    }

    private void a(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        view.setLayoutParams(marginLayoutParams);
    }
}
