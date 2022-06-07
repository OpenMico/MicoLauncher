package com.xiaomi.smarthome.ui.widgets.os;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.asm.Opcodes;
import com.elvishew.xlog.Logger;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.DeviceTypeDic;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.OperationUtils;
import com.xiaomi.smarthome.core.utils.UiUtils;
import com.xiaomi.smarthome.databinding.OsOutletMultiControllerBinding;
import com.xiaomi.smarthome.databinding.OsOutletMultiInDeviceListBinding;
import com.xiaomi.smarthome.databinding.OsOutletSingleControllerBinding;
import com.xiaomi.smarthome.databinding.OsOutletSingleInDeviceListBinding;
import com.xiaomi.smarthome.databinding.OsSwitchMultiControllerBinding;
import com.xiaomi.smarthome.databinding.OsSwitchMultiInDeviceListBinding;
import com.xiaomi.smarthome.databinding.OsSwitchSingleControllerBinding;
import com.xiaomi.smarthome.databinding.OsSwitchSingleInDeviceListBinding;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.adapter.DeviceStatusIndicatorAdapter;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.widgets.os.OSView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class OSView extends FrameLayout {
    private final Map<ViewStub, Boolean> a;
    private final Map<ViewStub, ViewDataBinding> b;
    private final Map<String, List<ViewStub>> c;
    private DeviceInfoBean d;
    private boolean e;
    private List<IDevice> f;
    private String g;
    private ItemTouchHelper h;
    private RecyclerView.ViewHolder i;
    private PageLocation j;
    private boolean k;
    private final DeviceStatusIndicatorAdapter l;
    private GridLayoutManager m;
    private OsOutletSingleControllerBinding n;
    private OsOutletMultiControllerBinding o;
    private OsSwitchSingleControllerBinding p;
    private OsSwitchMultiControllerBinding q;
    private OsOutletSingleInDeviceListBinding r;
    private OsOutletMultiInDeviceListBinding s;
    private OsSwitchSingleInDeviceListBinding t;
    private OsSwitchMultiInDeviceListBinding u;
    private final IMiotMsgCallback v;

    /* renamed from: com.xiaomi.smarthome.ui.widgets.os.OSView$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements IMiotMsgCallback {
        @Override // com.xiaomi.miot.support.IMiotMsgCallback
        public int getAction() {
            return 1;
        }

        AnonymousClass1() {
        }

        @Override // com.xiaomi.miot.support.IMiotMsgCallback
        public void onMsgCallback(JSONObject jSONObject) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("prop") && jSONObject.has("did") && jSONObject.has(b.p)) {
                        if (TextUtils.equals(OSView.this.d.getDeviceInfoWrapper().getDeviceInfo().did, jSONObject.getString("did"))) {
                            L.smarthome.d("OSView:onMsgCallback %s", jSONObject.toString());
                            String string = jSONObject.getString("prop");
                            if (string.startsWith("prop.")) {
                                string = string.replace("prop.", "pi:").replace(".", Constants.COLON_SEPARATOR);
                            }
                            String deviceType = OSView.this.d.getDeviceInfoWrapper().getModelInfo().getDeviceType();
                            List<DevicePropertyBriefInfo> props = MiotDeviceUtils.getProps(OSView.this.d.getDeviceInfoWrapper(), PropDic.P_ON);
                            if (DeviceTypeDic.DT_OS_OUTLET.getDeviceType().equals(deviceType)) {
                                boolean z = jSONObject.getBoolean(b.p);
                                if (props != null) {
                                    int i = (props.stream().filter($$Lambda$OSView$1$Cy0oMLl3AxRTGpThI1mQepQAHKA.INSTANCE).count() > 1L ? 1 : (props.stream().filter($$Lambda$OSView$1$Cy0oMLl3AxRTGpThI1mQepQAHKA.INSTANCE).count() == 1L ? 0 : -1));
                                    if (i > 0) {
                                        int size = props.size() - 1;
                                        while (true) {
                                            if (size < 0) {
                                                break;
                                            } else if (props.get(size).getKey().equals(string)) {
                                                props.get(size).getPropValue().setValueStr(String.valueOf(z));
                                                props.get(size).getPropValue().setValue(Boolean.valueOf(z));
                                                break;
                                            } else {
                                                size--;
                                            }
                                        }
                                        Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$1$tycakNDTBaeQZeeBsynEnT4RFJY
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                OSView.AnonymousClass1.this.d();
                                            }
                                        });
                                        return;
                                    } else if (i == 0 && OSView.this.d.getDeviceInfoWrapper().getDeviceInfo().currentStatus != z) {
                                        OSView.this.d.getDeviceInfoWrapper().getDeviceInfo().currentStatus = z;
                                        Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$1$x27GoFzQu8hf81OuQW_rmIxFetY
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                OSView.AnonymousClass1.this.c();
                                            }
                                        });
                                        return;
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else if (DeviceTypeDic.DT_OS_SWITCH.getDeviceType().equals(deviceType) && props != null) {
                                long count = props.stream().filter($$Lambda$OSView$1$oNVUixXs84ah0zTTD_CEIa8wmA8.INSTANCE).count();
                                boolean z2 = jSONObject.getBoolean(b.p);
                                int i2 = (count > 1L ? 1 : (count == 1L ? 0 : -1));
                                if (i2 > 0) {
                                    int size2 = props.size() - 1;
                                    while (true) {
                                        if (size2 < 0) {
                                            break;
                                        } else if (props.get(size2).getKey().equals(string)) {
                                            props.get(size2).getPropValue().setValueStr(String.valueOf(z2));
                                            props.get(size2).getPropValue().setValue(Boolean.valueOf(z2));
                                            break;
                                        } else {
                                            size2--;
                                        }
                                    }
                                    Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$1$DB6UczvZgx704DYSeEDDQLn8Flg
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OSView.AnonymousClass1.this.b();
                                        }
                                    });
                                    return;
                                } else if (i2 == 0 && OSView.this.d.getDeviceInfoWrapper().getDeviceInfo().currentStatus != z2) {
                                    OSView.this.d.getDeviceInfoWrapper().getDeviceInfo().currentStatus = z2;
                                    Threads.postInMainThread(new Runnable() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$1$uyCgn0ZBKA5Hro3zhPJKX_0JMvA
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            OSView.AnonymousClass1.this.a();
                                        }
                                    });
                                    return;
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            Logger logger = L.smarthome;
            Object[] objArr = new Object[1];
            objArr[0] = jSONObject != null ? jSONObject.toString() : "null";
            logger.d("OSView:onMsgCallback %s", objArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean b(DevicePropertyBriefInfo devicePropertyBriefInfo) {
            return "switch".equals(devicePropertyBriefInfo.getServiceTypeName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d() {
            if (OSView.this.j == PageLocation.PAGE_FIRST) {
                OSView oSView = OSView.this;
                oSView.a(oSView.d, OSView.this.o, (Boolean) null);
                return;
            }
            OSView oSView2 = OSView.this;
            oSView2.a(oSView2.d, OSView.this.s, (Boolean) null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c() {
            if (OSView.this.j == PageLocation.PAGE_FIRST) {
                OSView oSView = OSView.this;
                oSView.a(oSView.d, OSView.this.n, (Boolean) null);
                return;
            }
            OSView oSView2 = OSView.this;
            oSView2.a(oSView2.d, OSView.this.r, (Boolean) null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean a(DevicePropertyBriefInfo devicePropertyBriefInfo) {
            return "switch".equals(devicePropertyBriefInfo.getServiceTypeName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b() {
            if (OSView.this.j == PageLocation.PAGE_FIRST) {
                OSView oSView = OSView.this;
                oSView.a(oSView.d, OSView.this.q, (Boolean) null);
                return;
            }
            OSView oSView2 = OSView.this;
            oSView2.a(oSView2.d, OSView.this.u, (Boolean) null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            if (OSView.this.j == PageLocation.PAGE_FIRST) {
                OSView oSView = OSView.this;
                oSView.a(oSView.d, OSView.this.p, (Boolean) null);
                return;
            }
            OSView oSView2 = OSView.this;
            oSView2.a(oSView2.d, OSView.this.t, (Boolean) null);
        }
    }

    public OSView(@NonNull Context context) {
        this(context, null);
    }

    public OSView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OSView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public OSView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.a = new HashMap();
        this.b = new HashMap();
        this.c = new HashMap();
        this.e = false;
        this.k = true;
        this.l = new DeviceStatusIndicatorAdapter();
        this.v = new AnonymousClass1();
        LayoutInflater.from(context).inflate(R.layout.view_outlet_switch, (ViewGroup) this, true);
        final ViewStub viewStub = (ViewStub) findViewById(R.id.osOutletSingle);
        final ViewStub viewStub2 = (ViewStub) findViewById(R.id.osOutletMulti);
        final ViewStub viewStub3 = (ViewStub) findViewById(R.id.osSwitchSingle);
        final ViewStub viewStub4 = (ViewStub) findViewById(R.id.osSwitchMulti);
        final ViewStub viewStub5 = (ViewStub) findViewById(R.id.osOutletSingle2);
        final ViewStub viewStub6 = (ViewStub) findViewById(R.id.osOutletMulti2);
        final ViewStub viewStub7 = (ViewStub) findViewById(R.id.osSwitchSingle2);
        ViewStub viewStub8 = (ViewStub) findViewById(R.id.osSwitchMulti2);
        ViewStub.OnInflateListener onInflateListener = new ViewStub.OnInflateListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$BMu0FP0l0NABKE43nub8-o01VIE
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub9, View view) {
                OSView.this.a(viewStub, viewStub2, viewStub3, viewStub4, viewStub5, viewStub6, viewStub7, viewStub9, view);
            }
        };
        viewStub.setOnInflateListener(onInflateListener);
        viewStub2.setOnInflateListener(onInflateListener);
        viewStub3.setOnInflateListener(onInflateListener);
        viewStub4.setOnInflateListener(onInflateListener);
        viewStub5.setOnInflateListener(onInflateListener);
        viewStub6.setOnInflateListener(onInflateListener);
        viewStub7.setOnInflateListener(onInflateListener);
        viewStub8.setOnInflateListener(onInflateListener);
        ArrayList arrayList = new ArrayList();
        arrayList.add(viewStub);
        arrayList.add(viewStub2);
        arrayList.add(viewStub5);
        arrayList.add(viewStub6);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(viewStub3);
        arrayList2.add(viewStub4);
        arrayList2.add(viewStub7);
        arrayList2.add(viewStub8);
        this.c.put(DeviceTypeDic.DT_OS_OUTLET.getDeviceType(), arrayList);
        this.c.put(DeviceTypeDic.DT_OS_SWITCH.getDeviceType(), arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void a(ViewStub viewStub, ViewStub viewStub2, ViewStub viewStub3, ViewStub viewStub4, ViewStub viewStub5, ViewStub viewStub6, ViewStub viewStub7, ViewStub viewStub8, View view) {
        OsSwitchMultiInDeviceListBinding osSwitchMultiInDeviceListBinding;
        if (viewStub8 == viewStub) {
            this.n = OsOutletSingleControllerBinding.bind(view);
            OsOutletSingleControllerBinding osOutletSingleControllerBinding = this.n;
            osOutletSingleControllerBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.n.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.n.container, MicoAnimConfigurator4BigButton.get());
            AnimLifecycleManager.repeatOnAttach(this.n.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            this.n.ivStatus.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$Nu8Zcmt3rNUhyFkLu-Upe0t5_z8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OSView.this.d(view2);
                }
            });
            osSwitchMultiInDeviceListBinding = osOutletSingleControllerBinding;
        } else if (viewStub8 == viewStub2) {
            this.o = OsOutletMultiControllerBinding.bind(view);
            OsOutletMultiControllerBinding osOutletMultiControllerBinding = this.o;
            osOutletMultiControllerBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.o.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.o.container, MicoAnimConfigurator4BigButton.get());
            setUpOsStatus(this.o.rvStatus);
            osSwitchMultiInDeviceListBinding = osOutletMultiControllerBinding;
        } else if (viewStub8 == viewStub3) {
            this.p = OsSwitchSingleControllerBinding.bind(view);
            OsSwitchSingleControllerBinding osSwitchSingleControllerBinding = this.p;
            osSwitchSingleControllerBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.p.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.p.container, MicoAnimConfigurator4BigButton.get());
            AnimLifecycleManager.repeatOnAttach(this.p.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            this.p.ivStatus.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$l1JdJYHLZR_A5AW8c6eQ_VbYxGo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OSView.this.c(view2);
                }
            });
            osSwitchMultiInDeviceListBinding = osSwitchSingleControllerBinding;
        } else if (viewStub8 == viewStub4) {
            this.q = OsSwitchMultiControllerBinding.bind(view);
            OsSwitchMultiControllerBinding osSwitchMultiControllerBinding = this.q;
            osSwitchMultiControllerBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.q.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.q.container, MicoAnimConfigurator4BigButton.get());
            setUpOsStatus(this.q.rvStatus);
            osSwitchMultiInDeviceListBinding = osSwitchMultiControllerBinding;
        } else if (viewStub8 == viewStub5) {
            this.r = OsOutletSingleInDeviceListBinding.bind(view);
            OsOutletSingleInDeviceListBinding osOutletSingleInDeviceListBinding = this.r;
            osOutletSingleInDeviceListBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.r.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.r.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            this.r.ivStatus.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$uN2zE8r1I6cdrITKqfac0EN1v4Q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OSView.this.b(view2);
                }
            });
            osSwitchMultiInDeviceListBinding = osOutletSingleInDeviceListBinding;
        } else if (viewStub8 == viewStub6) {
            this.s = OsOutletMultiInDeviceListBinding.bind(view);
            OsOutletMultiInDeviceListBinding osOutletMultiInDeviceListBinding = this.s;
            osOutletMultiInDeviceListBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.s.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            setUpOsStatus(this.s.rvStatus);
            osSwitchMultiInDeviceListBinding = osOutletMultiInDeviceListBinding;
        } else if (viewStub8 == viewStub7) {
            this.t = OsSwitchSingleInDeviceListBinding.bind(view);
            OsSwitchSingleInDeviceListBinding osSwitchSingleInDeviceListBinding = this.t;
            osSwitchSingleInDeviceListBinding.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.t.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            AnimLifecycleManager.repeatOnAttach(this.t.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            this.t.ivStatus.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$dCUbNp7_Y0Sf8sF6MvT7dSFKT1U
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OSView.this.a(view2);
                }
            });
            osSwitchMultiInDeviceListBinding = osSwitchSingleInDeviceListBinding;
        } else {
            this.u = OsSwitchMultiInDeviceListBinding.bind(view);
            OsSwitchMultiInDeviceListBinding osSwitchMultiInDeviceListBinding2 = this.u;
            osSwitchMultiInDeviceListBinding2.tvLocation.setPaintFlags(Opcodes.INSTANCEOF);
            this.u.tvDeviceType.setPaintFlags(Opcodes.INSTANCEOF);
            setUpOsStatus(this.u.rvStatus);
            osSwitchMultiInDeviceListBinding = osSwitchMultiInDeviceListBinding2;
        }
        this.b.put(viewStub8, osSwitchMultiInDeviceListBinding);
        a(this.d, osSwitchMultiInDeviceListBinding, (Boolean) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        OperationUtils.toggleStatus(this.d.component2().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$czS2_6jIP2LTlWWKQ3XlB2RJw2I
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit d;
                d = OSView.this.d((Boolean) obj);
                return d;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit d(Boolean bool) {
        L.smarthome.d("%s turnStatus=%s", "OutletSwitch:", bool);
        a(this.d, this.n, bool);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        OperationUtils.toggleStatus(this.d.component2().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$HBnolMtQl-_0mTXdhTjzl1_zCa4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit c;
                c = OSView.this.c((Boolean) obj);
                return c;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit c(Boolean bool) {
        L.smarthome.d("%s turnStatus=%s", "OutletSwitch:", bool);
        a(this.d, this.p, bool);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        if (!UiUtils.isFastClick()) {
            OperationUtils.toggleStatus(this.d.component2().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$NWznGWkcQhfO3GeDmIRegrx_rSQ
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit b;
                    b = OSView.this.b((Boolean) obj);
                    return b;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit b(Boolean bool) {
        L.smarthome.d("%s turnStatus=%s", "OutletSwitch:", bool);
        a(this.d, this.r, bool);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        OperationUtils.toggleStatus(this.d.component2().getDeviceInfo(), new Function1() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$ZhWmqQpd_IKdRXoCKQ0Xry-pucI
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit a;
                a = OSView.this.a((Boolean) obj);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit a(Boolean bool) {
        L.smarthome.d("%s turnStatus=%s", "OutletSwitch:", bool);
        a(this.d, this.t, bool);
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
        return null;
    }

    private void setUpOsStatus(RecyclerView recyclerView) {
        List<Boolean> switchStatusList = MiotDeviceUtils.getSwitchStatusList(this.d.getDeviceInfoWrapper());
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(this.l);
        }
        if (switchStatusList.size() != 0) {
            if (this.m == null) {
                this.m = new GridLayoutManager(getContext(), switchStatusList.size());
            }
            recyclerView.setLayoutManager(this.m);
            this.l.updateDeviceStatus(switchStatusList);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void bindData(@androidx.annotation.Nullable java.util.List<com.xiaomi.smarthome.core.entity.IDevice> r5, com.xiaomi.smarthome.core.entity.DeviceInfoBean r6, boolean r7, com.xiaomi.smarthome.ui.adapter.PageLocation r8, java.lang.String r9, androidx.recyclerview.widget.ItemTouchHelper r10, androidx.recyclerview.widget.RecyclerView.ViewHolder r11) {
        /*
            r4 = this;
            r4.d = r6
            r4.j = r8
            com.xiaomi.smarthome.ui.adapter.PageLocation r0 = r4.j
            com.xiaomi.smarthome.ui.adapter.PageLocation r1 = com.xiaomi.smarthome.ui.adapter.PageLocation.PAGE_SECOND
            r2 = 0
            r3 = 1
            if (r0 != r1) goto L_0x000e
            r0 = r3
            goto L_0x000f
        L_0x000e:
            r0 = r2
        L_0x000f:
            r4.k = r0
            r4.e = r7
            r4.f = r5
            r4.h = r10
            r4.i = r11
            r4.g = r9
            com.xiaomi.smarthome.core.entity.DeviceInfoBean r5 = r4.d
            com.xiaomi.miot.support.category.DeviceInfoWrapper r5 = r5.getDeviceInfoWrapper()
            com.xiaomi.miot.support.category.ModelInfo r5 = r5.getModelInfo()
            java.lang.String r5 = r5.getDeviceType()
            com.xiaomi.smarthome.core.entity.DeviceTypeDic r7 = com.xiaomi.smarthome.core.entity.DeviceTypeDic.DT_OS_OUTLET
            java.lang.String r7 = r7.getDeviceType()
            boolean r7 = r7.equals(r5)
            r9 = 0
            if (r7 != 0) goto L_0x0042
            com.xiaomi.smarthome.core.entity.DeviceTypeDic r7 = com.xiaomi.smarthome.core.entity.DeviceTypeDic.DT_OS_SWITCH
            java.lang.String r7 = r7.getDeviceType()
            boolean r7 = r7.equals(r5)
            if (r7 == 0) goto L_0x00cf
        L_0x0042:
            com.xiaomi.miot.support.category.DeviceInfoWrapper r7 = r6.getDeviceInfoWrapper()
            com.xiaomi.miot.support.category.DeviceItemInfo r7 = r7.getDevicePropInfo()
            java.util.Map r7 = r7.getProperties()
            com.xiaomi.smarthome.core.entity.PropDic r10 = com.xiaomi.smarthome.core.entity.PropDic.P_ON
            java.lang.String r10 = r10.getProp()
            boolean r10 = r7.containsKey(r10)
            if (r10 == 0) goto L_0x00cf
            com.xiaomi.smarthome.core.entity.PropDic r10 = com.xiaomi.smarthome.core.entity.PropDic.P_ON
            java.lang.String r10 = r10.getProp()
            java.lang.Object r7 = r7.get(r10)
            java.util.List r7 = (java.util.List) r7
            if (r7 == 0) goto L_0x00cf
            java.util.stream.Stream r7 = r7.stream()
            com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$NSIgmmeXZEkbd6xigmsvmxrIvG0 r10 = com.xiaomi.smarthome.ui.widgets.os.$$Lambda$OSView$NSIgmmeXZEkbd6xigmsvmxrIvG0.INSTANCE
            java.util.stream.Stream r7 = r7.filter(r10)
            long r10 = r7.count()
            r0 = 1
            int r7 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r7 <= 0) goto L_0x009f
            com.xiaomi.smarthome.ui.adapter.PageLocation r7 = com.xiaomi.smarthome.ui.adapter.PageLocation.PAGE_FIRST
            if (r8 != r7) goto L_0x008f
            java.util.Map<java.lang.String, java.util.List<android.view.ViewStub>> r7 = r4.c
            java.lang.Object r5 = r7.get(r5)
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r5 = r5.get(r3)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            goto L_0x00d0
        L_0x008f:
            java.util.Map<java.lang.String, java.util.List<android.view.ViewStub>> r7 = r4.c
            java.lang.Object r5 = r7.get(r5)
            java.util.List r5 = (java.util.List) r5
            r7 = 3
            java.lang.Object r5 = r5.get(r7)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            goto L_0x00d0
        L_0x009f:
            com.elvishew.xlog.Logger r7 = com.xiaomi.micolauncher.common.L.smarthome
            java.lang.String r10 = "%s current outlet has no switch service,data may be wrong"
            java.lang.Object[] r11 = new java.lang.Object[r3]
            java.lang.String r0 = "OutletSwitch:"
            r11[r2] = r0
            r7.e(r10, r11)
            com.xiaomi.smarthome.ui.adapter.PageLocation r7 = com.xiaomi.smarthome.ui.adapter.PageLocation.PAGE_FIRST
            if (r8 != r7) goto L_0x00bf
            java.util.Map<java.lang.String, java.util.List<android.view.ViewStub>> r7 = r4.c
            java.lang.Object r5 = r7.get(r5)
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r5 = r5.get(r2)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            goto L_0x00d0
        L_0x00bf:
            java.util.Map<java.lang.String, java.util.List<android.view.ViewStub>> r7 = r4.c
            java.lang.Object r5 = r7.get(r5)
            java.util.List r5 = (java.util.List) r5
            r7 = 2
            java.lang.Object r5 = r5.get(r7)
            android.view.ViewStub r5 = (android.view.ViewStub) r5
            goto L_0x00d0
        L_0x00cf:
            r5 = r9
        L_0x00d0:
            if (r5 == 0) goto L_0x00e4
            boolean r7 = r4.a(r5)
            if (r7 != 0) goto L_0x00e4
            r5.inflate()
            java.util.Map<android.view.ViewStub, java.lang.Boolean> r7 = r4.a
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r3)
            r7.put(r5, r8)
        L_0x00e4:
            java.util.Map<android.view.ViewStub, androidx.databinding.ViewDataBinding> r7 = r4.b
            java.lang.Object r7 = r7.get(r5)
            androidx.databinding.ViewDataBinding r7 = (androidx.databinding.ViewDataBinding) r7
            if (r7 == 0) goto L_0x00f1
            r4.a(r6, r7, r9)
        L_0x00f1:
            r4.b(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.os.OSView.bindData(java.util.List, com.xiaomi.smarthome.core.entity.DeviceInfoBean, boolean, com.xiaomi.smarthome.ui.adapter.PageLocation, java.lang.String, androidx.recyclerview.widget.ItemTouchHelper, androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(DevicePropertyBriefInfo devicePropertyBriefInfo) {
        return "switch".equals(devicePropertyBriefInfo.getServiceTypeName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(DeviceInfoBean deviceInfoBean, final ViewDataBinding viewDataBinding, Boolean bool) {
        if (bool != null) {
            deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().currentStatus = bool.booleanValue();
        }
        if (!viewDataBinding.getRoot().hasOnClickListeners()) {
            viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$aEbdbHl3-2rmvk0eskKH-sdmkJQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    OSView.this.a(viewDataBinding, view);
                }
            });
        }
        ImageView imageView = null;
        if (viewDataBinding instanceof OsOutletSingleControllerBinding) {
            OsOutletSingleControllerBinding osOutletSingleControllerBinding = (OsOutletSingleControllerBinding) viewDataBinding;
            osOutletSingleControllerBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osOutletSingleControllerBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osOutletSingleControllerBinding.setEditMode(this.e);
            osOutletSingleControllerBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
        } else if (viewDataBinding instanceof OsOutletMultiControllerBinding) {
            OsOutletMultiControllerBinding osOutletMultiControllerBinding = (OsOutletMultiControllerBinding) viewDataBinding;
            imageView = osOutletMultiControllerBinding.ivDragHandle;
            osOutletMultiControllerBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osOutletMultiControllerBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osOutletMultiControllerBinding.setEditMode(this.e);
            osOutletMultiControllerBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            setUpOsStatus(osOutletMultiControllerBinding.rvStatus);
        } else if (viewDataBinding instanceof OsSwitchSingleControllerBinding) {
            OsSwitchSingleControllerBinding osSwitchSingleControllerBinding = (OsSwitchSingleControllerBinding) viewDataBinding;
            imageView = osSwitchSingleControllerBinding.ivDragHandle;
            osSwitchSingleControllerBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osSwitchSingleControllerBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osSwitchSingleControllerBinding.setEditMode(this.e);
            osSwitchSingleControllerBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
        } else if (viewDataBinding instanceof OsSwitchMultiControllerBinding) {
            OsSwitchMultiControllerBinding osSwitchMultiControllerBinding = (OsSwitchMultiControllerBinding) viewDataBinding;
            imageView = osSwitchMultiControllerBinding.ivDragHandle;
            osSwitchMultiControllerBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osSwitchMultiControllerBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osSwitchMultiControllerBinding.setEditMode(this.e);
            osSwitchMultiControllerBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            osSwitchMultiControllerBinding.ivDeviceTypePic.setImageResource(MiotDeviceUtils.getMultiSwitchRes(this.d.getDeviceInfoWrapper()));
            setUpOsStatus(osSwitchMultiControllerBinding.rvStatus);
        } else if (viewDataBinding instanceof OsOutletSingleInDeviceListBinding) {
            OsOutletSingleInDeviceListBinding osOutletSingleInDeviceListBinding = (OsOutletSingleInDeviceListBinding) viewDataBinding;
            ImageView imageView2 = osOutletSingleInDeviceListBinding.ivDragHandle;
            osOutletSingleInDeviceListBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osOutletSingleInDeviceListBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osOutletSingleInDeviceListBinding.setEditMode(this.e);
            osOutletSingleInDeviceListBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            AnimLifecycleManager.repeatOnAttach(osOutletSingleInDeviceListBinding.container, MicoAnimConfigurator4BigButton.get());
            if (this.e) {
                MicoAnimationManager.getManager().startScaleSmallAnims(osOutletSingleInDeviceListBinding.container);
                MicoAnimationManager.getManager().startShowAnims(osOutletSingleInDeviceListBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(osOutletSingleInDeviceListBinding.ivStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.k), osOutletSingleInDeviceListBinding.ivStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.k), osOutletSingleInDeviceListBinding.ivDragHandle);
            }
            imageView = imageView2;
        } else if (viewDataBinding instanceof OsOutletMultiInDeviceListBinding) {
            OsOutletMultiInDeviceListBinding osOutletMultiInDeviceListBinding = (OsOutletMultiInDeviceListBinding) viewDataBinding;
            ImageView imageView3 = osOutletMultiInDeviceListBinding.ivDragHandle;
            osOutletMultiInDeviceListBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osOutletMultiInDeviceListBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osOutletMultiInDeviceListBinding.setEditMode(this.e);
            osOutletMultiInDeviceListBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            AnimLifecycleManager.repeatOnAttach(osOutletMultiInDeviceListBinding.container, MicoAnimConfigurator4BigButton.get());
            setUpOsStatus(osOutletMultiInDeviceListBinding.rvStatus);
            if (this.e) {
                MicoAnimationManager.getManager().startScaleSmallAnims(osOutletMultiInDeviceListBinding.container);
                MicoAnimationManager.getManager().startShowAnims(osOutletMultiInDeviceListBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(osOutletMultiInDeviceListBinding.rvStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.k), osOutletMultiInDeviceListBinding.rvStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.k), osOutletMultiInDeviceListBinding.ivDragHandle);
            }
            imageView = imageView3;
        } else if (viewDataBinding instanceof OsSwitchSingleInDeviceListBinding) {
            OsSwitchSingleInDeviceListBinding osSwitchSingleInDeviceListBinding = (OsSwitchSingleInDeviceListBinding) viewDataBinding;
            ImageView imageView4 = osSwitchSingleInDeviceListBinding.ivDragHandle;
            osSwitchSingleInDeviceListBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osSwitchSingleInDeviceListBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osSwitchSingleInDeviceListBinding.setEditMode(this.e);
            osSwitchSingleInDeviceListBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            AnimLifecycleManager.repeatOnAttach(osSwitchSingleInDeviceListBinding.container, MicoAnimConfigurator4BigButton.get());
            if (this.e) {
                MicoAnimationManager.getManager().startScaleSmallAnims(osSwitchSingleInDeviceListBinding.container);
                MicoAnimationManager.getManager().startShowAnims(osSwitchSingleInDeviceListBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(osSwitchSingleInDeviceListBinding.ivStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.k), osSwitchSingleInDeviceListBinding.ivStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.k), osSwitchSingleInDeviceListBinding.ivDragHandle);
            }
            imageView = imageView4;
        } else if (viewDataBinding instanceof OsSwitchMultiInDeviceListBinding) {
            OsSwitchMultiInDeviceListBinding osSwitchMultiInDeviceListBinding = (OsSwitchMultiInDeviceListBinding) viewDataBinding;
            ImageView imageView5 = osSwitchMultiInDeviceListBinding.ivDragHandle;
            osSwitchMultiInDeviceListBinding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            osSwitchMultiInDeviceListBinding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            osSwitchMultiInDeviceListBinding.setEditMode(this.e);
            osSwitchMultiInDeviceListBinding.container.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(this.f, deviceInfoBean, this.g)));
            AnimLifecycleManager.repeatOnAttach(osSwitchMultiInDeviceListBinding.container, MicoAnimConfigurator4BigButton.get());
            osSwitchMultiInDeviceListBinding.ivDeviceTypePic.setImageResource(MiotDeviceUtils.getMultiSwitchRes(this.d.getDeviceInfoWrapper()));
            setUpOsStatus(osSwitchMultiInDeviceListBinding.rvStatus);
            if (this.e) {
                MicoAnimationManager.getManager().startScaleSmallAnims(osSwitchMultiInDeviceListBinding.container);
                MicoAnimationManager.getManager().startShowAnims(osSwitchMultiInDeviceListBinding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(osSwitchMultiInDeviceListBinding.rvStatus);
            } else {
                a(viewDataBinding);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.k), osSwitchMultiInDeviceListBinding.rvStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.k), osSwitchMultiInDeviceListBinding.ivDragHandle);
            }
            imageView = imageView5;
        }
        if (imageView != null && this.e) {
            imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$siznB7TTujaSLnPosm9xAzaz8oE
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean a;
                    a = OSView.this.a(view, motionEvent);
                    return a;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ViewDataBinding viewDataBinding, View view) {
        if (!UiUtils.isFastClick() && !this.e) {
            HashMap hashMap = new HashMap();
            hashMap.put("item", this.d.getDeviceInfoWrapper());
            hashMap.put(OneTrack.Event.VIEW, viewDataBinding.getRoot());
            LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_OPEN_CARD).post(hashMap);
            if (this.d.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT_MORE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(View view, MotionEvent motionEvent) {
        this.h.startDrag(this.i);
        return false;
    }

    private void a(ViewDataBinding viewDataBinding) {
        if (this.j == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(viewDataBinding.getRoot());
        }
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
            this.b.forEach(new BiConsumer() { // from class: com.xiaomi.smarthome.ui.widgets.os.-$$Lambda$OSView$aewvfW_pNGW-_UKpTz_FmmSRqpc
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    OSView.a(viewStub, (ViewStub) obj, (ViewDataBinding) obj2);
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MiotManager.registerMiotMsgCallback(this.v);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MiotManager.unregisterMiotMsgCallback(this.v);
    }
}
