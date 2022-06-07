package com.xiaomi.smarthome.ui.widgets.lights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.asm.Opcodes;
import com.elvishew.xlog.Logger;
import com.jakewharton.rxbinding4.view.RxView;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.core.ISendMsgCallback;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.exts.DeviceExtsKt;
import com.xiaomi.smarthome.core.utils.AnimationUtils;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.OperationUtils;
import com.xiaomi.smarthome.databinding.LightController1Binding;
import com.xiaomi.smarthome.databinding.LightController2Binding;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LightView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0085\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n*\u0001)\b\u0007\u0018\u0000 E2\u00020\u0001:\u0001EB%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fJR\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u0006\u00107\u001a\u00020.2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00020'\u0018\u00010&2\u0006\u0010\u0017\u001a\u00020\u000e2\n\b\u0002\u0010/\u001a\u0004\u0018\u0001002\n\b\u0002\u00102\u001a\u0004\u0018\u000103J\b\u00108\u001a\u000205H\u0014J\b\u00109\u001a\u000205H\u0014J\u001f\u0010:\u001a\u000205\"\b\b\u0000\u0010;*\u00020<2\u0006\u0010=\u001a\u0002H;H\u0002¢\u0006\u0002\u0010>J1\u0010?\u001a\u000205\"\b\b\u0000\u0010;*\u00020<2\b\u0010@\u001a\u0004\u0018\u00010\u001b2\u0006\u0010A\u001a\u00020\u00192\u0006\u0010=\u001a\u0002H;H\u0002¢\u0006\u0002\u0010BJ3\u0010C\u001a\u000205\"\b\b\u0000\u0010;*\u00020<2\u0006\u0010A\u001a\u00020\u00192\u0006\u0010=\u001a\u0002H;2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\u001bH\u0002¢\u0006\u0002\u0010DR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\n\u0012\u0004\u0012\u00020'\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010(\u001a\u00020)8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u0016\u001a\u0004\b*\u0010+R\u000e\u0010-\u001a\u00020.X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/lights/LightView;", "Landroid/widget/FrameLayout;", com.umeng.analytics.pro.c.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "brightnessKey", "", "getBrightnessKey", "()Ljava/lang/String;", "callback", "Lcom/xiaomi/miot/support/IMiotMsgCallback;", "getCallback", "()Lcom/xiaomi/miot/support/IMiotMsgCallback;", "callback$delegate", "Lkotlin/Lazy;", "categoryName", "device", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "isEditMode", "", "isLight1Inflated", "isLight2Inflated", "light1", "Lcom/xiaomi/smarthome/databinding/LightController1Binding;", "light1ViewStub", "Landroid/view/ViewStub;", "light2", "Lcom/xiaomi/smarthome/databinding/LightController2Binding;", "light2ViewStub", "list", "", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "onClickListener", "com/xiaomi/smarthome/ui/widgets/lights/LightView$onClickListener$2$1", "getOnClickListener", "()Lcom/xiaomi/smarthome/ui/widgets/lights/LightView$onClickListener$2$1;", "onClickListener$delegate", "pageLocation", "Lcom/xiaomi/smarthome/ui/adapter/PageLocation;", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "useAnim", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "bindData", "", "data", "location", "onAttachedToWindow", "onDetachedFromWindow", "startScaleLargeAnims", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", "binding", "(Landroidx/databinding/ViewDataBinding;)V", "update", "turnStatus", "item", "(Ljava/lang/Boolean;Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;Landroidx/databinding/ViewDataBinding;)V", "updateUI", "(Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;Landroidx/databinding/ViewDataBinding;Ljava/lang/Boolean;)V", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ClickableViewAccessibility"})
/* loaded from: classes4.dex */
public final class LightView extends FrameLayout {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "Light:";
    private DeviceInfoBean a;
    private List<IDevice> b;
    private ViewStub c;
    private ViewStub d;
    private LightController1Binding e;
    private LightController2Binding f;
    private boolean g;
    private boolean h;
    private boolean i;
    private RecyclerView.ViewHolder j;
    private String k;
    private ItemTouchHelper l;
    private boolean m;
    private PageLocation n;
    private final Lazy o;
    private final Lazy p;

    private final IMiotMsgCallback getCallback() {
        return (IMiotMsgCallback) this.p.getValue();
    }

    private final LightView$onClickListener$2$1 getOnClickListener() {
        return (LightView$onClickListener$2$1) this.o.getValue();
    }

    public static final /* synthetic */ DeviceInfoBean access$getDevice$p(LightView lightView) {
        DeviceInfoBean deviceInfoBean = lightView.a;
        if (deviceInfoBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("device");
        }
        return deviceInfoBean;
    }

    public /* synthetic */ LightView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.m = true;
        this.n = PageLocation.PAGE_FIRST;
        this.o = LazyKt.lazy(new b());
        this.p = LazyKt.lazy(new a());
        LayoutInflater.from(context).inflate(R.layout.view_light, (ViewGroup) this, true);
        View findViewById = findViewById(R.id.light1);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(R.id.light1)");
        this.c = (ViewStub) findViewById;
        View findViewById2 = findViewById(R.id.light2);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(R.id.light2)");
        this.d = (ViewStub) findViewById2;
        this.c.setOnInflateListener(new AnonymousClass1());
        this.d.setOnInflateListener(new AnonymousClass2());
    }

    public final String getBrightnessKey() {
        DeviceInfoBean deviceInfoBean = this.a;
        if (deviceInfoBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("device");
        }
        DevicePropertyBriefInfo brightnessProp = DeviceExtsKt.brightnessProp(deviceInfoBean.getDeviceInfoWrapper());
        if (brightnessProp != null) {
            return brightnessProp.getKey();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "com/xiaomi/smarthome/ui/widgets/lights/LightView$onClickListener$2$1", "invoke", "()Lcom/xiaomi/smarthome/ui/widgets/lights/LightView$onClickListener$2$1;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b extends Lambda implements Function0<LightView$onClickListener$2$1> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b() {
            super(0);
            LightView.this = r1;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.smarthome.ui.widgets.lights.LightView$onClickListener$2$1] */
        @NotNull
        /* renamed from: a */
        public final LightView$onClickListener$2$1 invoke() {
            return new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.lights.LightView$onClickListener$2$1
                /* JADX WARN: Code restructure failed: missing block: B:4:0x000b, code lost:
                    r0 = com.xiaomi.smarthome.ui.widgets.lights.LightView.this.i;
                 */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onClick(@org.jetbrains.annotations.NotNull android.view.View r4) {
                    /*
                        r3 = this;
                        java.lang.String r0 = "v"
                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
                        boolean r0 = com.xiaomi.smarthome.core.utils.UiUtils.isFastClick()
                        if (r0 != 0) goto L_0x0056
                        com.xiaomi.smarthome.ui.widgets.lights.LightView$b r0 = com.xiaomi.smarthome.ui.widgets.lights.LightView.b.this
                        com.xiaomi.smarthome.ui.widgets.lights.LightView r0 = com.xiaomi.smarthome.ui.widgets.lights.LightView.this
                        boolean r0 = com.xiaomi.smarthome.ui.widgets.lights.LightView.access$isEditMode$p(r0)
                        if (r0 == 0) goto L_0x0016
                        goto L_0x0056
                    L_0x0016:
                        java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
                        r0.<init>()
                        java.util.Map r0 = (java.util.Map) r0
                        java.lang.String r1 = "item"
                        com.xiaomi.smarthome.ui.widgets.lights.LightView$b r2 = com.xiaomi.smarthome.ui.widgets.lights.LightView.b.this
                        com.xiaomi.smarthome.ui.widgets.lights.LightView r2 = com.xiaomi.smarthome.ui.widgets.lights.LightView.this
                        com.xiaomi.smarthome.core.entity.DeviceInfoBean r2 = com.xiaomi.smarthome.ui.widgets.lights.LightView.access$getDevice$p(r2)
                        com.xiaomi.miot.support.category.DeviceInfoWrapper r2 = r2.getDeviceInfoWrapper()
                        r0.put(r1, r2)
                        java.lang.String r1 = "view"
                        r0.put(r1, r4)
                        java.lang.String r4 = "event_open_card"
                        com.jeremyliao.liveeventbus.core.Observable r4 = com.jeremyliao.liveeventbus.LiveEventBus.get(r4)
                        r4.post(r0)
                        com.xiaomi.smarthome.ui.widgets.lights.LightView$b r4 = com.xiaomi.smarthome.ui.widgets.lights.LightView.b.this
                        com.xiaomi.smarthome.ui.widgets.lights.LightView r4 = com.xiaomi.smarthome.ui.widgets.lights.LightView.this
                        com.xiaomi.smarthome.core.entity.DeviceInfoBean r4 = com.xiaomi.smarthome.ui.widgets.lights.LightView.access$getDevice$p(r4)
                        com.xiaomi.miot.support.category.DeviceInfoWrapper r4 = r4.getDeviceInfoWrapper()
                        com.xiaomi.miot.DeviceInfo r4 = r4.getDeviceInfo()
                        boolean r4 = r4.isOnline
                        if (r4 == 0) goto L_0x0055
                        java.lang.String r4 = "equipmentMore"
                        com.xiaomi.smarthome.ui.model.SmartHomeStatHelper.recordSmartTabControlModeClick(r4)
                    L_0x0055:
                        return
                    L_0x0056:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.lights.LightView$onClickListener$2$1.onClick(android.view.View):void");
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "com/xiaomi/smarthome/ui/widgets/lights/LightView$callback$2$1", "invoke", "()Lcom/xiaomi/smarthome/ui/widgets/lights/LightView$callback$2$1;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a extends Lambda implements Function0<LightView$callback$2$1> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super(0);
            LightView.this = r1;
        }

        @NotNull
        /* renamed from: a */
        public final LightView$callback$2$1 invoke() {
            return new LightView$callback$2$1(this);
        }
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/lights/LightView$Companion;", "", "()V", "TAG", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ LightView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/ViewStub;", "kotlin.jvm.PlatformType", "inflated", "Landroid/view/View;", "onInflate"}, k = 3, mv = {1, 4, 2})
    /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$1 */
    /* loaded from: classes4.dex */
    public static final class AnonymousClass1 implements ViewStub.OnInflateListener {
        AnonymousClass1() {
            LightView.this = r1;
        }

        @Override // android.view.ViewStub.OnInflateListener
        public final void onInflate(ViewStub viewStub, View view) {
            LightView.this.e = LightController1Binding.bind(view);
            if (LightView.this.h) {
                LightController2Binding lightController2Binding = LightView.this.f;
                Intrinsics.checkNotNull(lightController2Binding);
                View root = lightController2Binding.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "light2!!.root");
                root.setVisibility(8);
            }
            LightController1Binding lightController1Binding = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding);
            View root2 = lightController1Binding.getRoot();
            Intrinsics.checkNotNullExpressionValue(root2, "light1!!.root");
            root2.setVisibility(0);
            LightController1Binding lightController1Binding2 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding2);
            TextView textView = lightController1Binding2.tvLocation;
            Intrinsics.checkNotNullExpressionValue(textView, "light1!!.tvLocation");
            textView.setPaintFlags(Opcodes.INSTANCEOF);
            LightController1Binding lightController1Binding3 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding3);
            TextView textView2 = lightController1Binding3.tvDeviceType;
            Intrinsics.checkNotNullExpressionValue(textView2, "light1!!.tvDeviceType");
            textView2.setPaintFlags(Opcodes.INSTANCEOF);
            LightController1Binding lightController1Binding4 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding4);
            AnimLifecycleManager.repeatOnAttach(lightController1Binding4.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            Double brightnessValue = DeviceExtsKt.brightnessValue(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper());
            double doubleValue = brightnessValue != null ? brightnessValue.doubleValue() / 100 : 0.0d;
            Logger logger = L.smarthome;
            logger.d("Light: brightness=" + brightnessValue + ",after div=" + doubleValue);
            LightController1Binding lightController1Binding5 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding5);
            lightController1Binding5.seekView.setVolume(doubleValue);
            LightController1Binding lightController1Binding6 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding6);
            lightController1Binding6.seekView.setVolumeChangeListener(new OnVolumeChangeListener() { // from class: com.xiaomi.smarthome.ui.widgets.lights.LightView.1.1
                @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                public void onVolumeChanged(double d) {
                }

                {
                    AnonymousClass1.this = this;
                }

                @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                public void onVolumeChangedBegin(double d) {
                    AnimationUtils.Companion companion = AnimationUtils.Companion;
                    LightController1Binding lightController1Binding7 = LightView.this.e;
                    Intrinsics.checkNotNull(lightController1Binding7);
                    VerticalSeekView verticalSeekView = lightController1Binding7.seekView;
                    Intrinsics.checkNotNullExpressionValue(verticalSeekView, "light1!!.seekView");
                    LightController1Binding lightController1Binding8 = LightView.this.e;
                    Intrinsics.checkNotNull(lightController1Binding8);
                    companion.startHotZoneAnimationForX6A(verticalSeekView, lightController1Binding8.seekView.getRadius());
                }

                @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                public void onVolumeChangedEnd(boolean z, double d) {
                    AnimationUtils.Companion companion = AnimationUtils.Companion;
                    LightController1Binding lightController1Binding7 = LightView.this.e;
                    Intrinsics.checkNotNull(lightController1Binding7);
                    VerticalSeekView verticalSeekView = lightController1Binding7.seekView;
                    Intrinsics.checkNotNullExpressionValue(verticalSeekView, "light1!!.seekView");
                    companion.endHotZoneAnimationForX6A(verticalSeekView);
                    MiotManager.setDeviceProperty(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo().did, LightView.this.getBrightnessKey(), MiotDeviceUtils.INSTANCE.seekViewVolume2Value(d, LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper(), PropDic.P_BRIGHTNESS), "int", a.a);
                    SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                }

                /* compiled from: LightView.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
                /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$1$1$a */
                /* loaded from: classes4.dex */
                static final class a implements ISendMsgCallback {
                    public static final a a = new a();

                    a() {
                    }

                    @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                    public final void onResult(String str) {
                        Logger logger = L.smarthome;
                        logger.d("Light: ,onResult=" + str);
                    }
                }
            });
            LightView lightView = LightView.this;
            DeviceInfoBean access$getDevice$p = LightView.access$getDevice$p(lightView);
            LightController1Binding lightController1Binding7 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding7);
            LightView.a(lightView, access$getDevice$p, lightController1Binding7, null, 4, null);
            LightController1Binding lightController1Binding8 = LightView.this.e;
            Intrinsics.checkNotNull(lightController1Binding8);
            ImageView imageView = lightController1Binding8.ivStatus;
            Intrinsics.checkNotNullExpressionValue(imageView, "light1!!.ivStatus");
            RxView.clicks(imageView).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.lights.LightView.1.2
                {
                    AnonymousClass1.this = this;
                }

                /* compiled from: LightView.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$1$2$1 */
                /* loaded from: classes4.dex */
                public static final class C01931 extends Lambda implements Function1<Boolean, Unit> {
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C01931() {
                        super(1);
                        AnonymousClass2.this = r1;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* synthetic */ Unit invoke(Boolean bool) {
                        a(bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void a(boolean z) {
                        LightView lightView = LightView.this;
                        DeviceInfoBean access$getDevice$p = LightView.access$getDevice$p(LightView.this);
                        LightController1Binding lightController1Binding = LightView.this.e;
                        Intrinsics.checkNotNull(lightController1Binding);
                        lightView.a(access$getDevice$p, (DeviceInfoBean) lightController1Binding, Boolean.valueOf(z));
                    }
                }

                /* renamed from: a */
                public final void accept(Unit unit) {
                    OperationUtils.Companion companion = OperationUtils.Companion;
                    DeviceInfo deviceInfo = LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo();
                    Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                    companion.toggleStatus(deviceInfo, new C01931());
                    SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                }
            });
        }
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/view/ViewStub;", "kotlin.jvm.PlatformType", "inflated", "Landroid/view/View;", "onInflate"}, k = 3, mv = {1, 4, 2})
    /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$2 */
    /* loaded from: classes4.dex */
    public static final class AnonymousClass2 implements ViewStub.OnInflateListener {
        AnonymousClass2() {
            LightView.this = r1;
        }

        @Override // android.view.ViewStub.OnInflateListener
        public final void onInflate(ViewStub viewStub, View view) {
            LightView.this.f = LightController2Binding.bind(view);
            if (LightView.this.g) {
                LightController1Binding lightController1Binding = LightView.this.e;
                Intrinsics.checkNotNull(lightController1Binding);
                View root = lightController1Binding.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "light1!!.root");
                root.setVisibility(8);
            }
            LightController2Binding lightController2Binding = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding);
            View root2 = lightController2Binding.getRoot();
            Intrinsics.checkNotNullExpressionValue(root2, "light2!!.root");
            root2.setVisibility(0);
            LightController2Binding lightController2Binding2 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding2);
            TextView textView = lightController2Binding2.tvLocation;
            Intrinsics.checkNotNullExpressionValue(textView, "light2!!.tvLocation");
            textView.setPaintFlags(Opcodes.INSTANCEOF);
            LightController2Binding lightController2Binding3 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding3);
            TextView textView2 = lightController2Binding3.tvDeviceType;
            Intrinsics.checkNotNullExpressionValue(textView2, "light2!!.tvDeviceType");
            textView2.setPaintFlags(Opcodes.INSTANCEOF);
            LightController2Binding lightController2Binding4 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding4);
            AnimLifecycleManager.repeatOnAttach(lightController2Binding4.ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
            LightView lightView = LightView.this;
            DeviceInfoBean access$getDevice$p = LightView.access$getDevice$p(lightView);
            LightController2Binding lightController2Binding5 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding5);
            LightView.a(lightView, access$getDevice$p, lightController2Binding5, null, 4, null);
            LightController2Binding lightController2Binding6 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding6);
            ImageView imageView = lightController2Binding6.ivStatus;
            Intrinsics.checkNotNullExpressionValue(imageView, "light2!!.ivStatus");
            RxView.clicks(imageView).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.lights.LightView.2.1
                {
                    AnonymousClass2.this = this;
                }

                /* compiled from: LightView.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$2$1$1 */
                /* loaded from: classes4.dex */
                public static final class C01941 extends Lambda implements Function1<Boolean, Unit> {
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C01941() {
                        super(1);
                        AnonymousClass1.this = r1;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* synthetic */ Unit invoke(Boolean bool) {
                        a(bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void a(boolean z) {
                        LightView lightView = LightView.this;
                        DeviceInfoBean access$getDevice$p = LightView.access$getDevice$p(LightView.this);
                        LightController2Binding lightController2Binding = LightView.this.f;
                        Intrinsics.checkNotNull(lightController2Binding);
                        lightView.a(access$getDevice$p, (DeviceInfoBean) lightController2Binding, Boolean.valueOf(z));
                    }
                }

                /* renamed from: a */
                public final void accept(Unit unit) {
                    OperationUtils.Companion companion = OperationUtils.Companion;
                    DeviceInfo deviceInfo = LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo();
                    Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                    companion.toggleStatus(deviceInfo, new C01941());
                    SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                }
            });
            LightController2Binding lightController2Binding7 = LightView.this.f;
            Intrinsics.checkNotNull(lightController2Binding7);
            ImageView imageView2 = lightController2Binding7.ivDragHandle;
            if (imageView2 != null) {
                imageView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.smarthome.ui.widgets.lights.LightView.2.2
                    {
                        AnonymousClass2.this = this;
                    }

                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        ItemTouchHelper itemTouchHelper;
                        RecyclerView.ViewHolder viewHolder = LightView.this.j;
                        if (viewHolder == null || (itemTouchHelper = LightView.this.l) == null) {
                            return false;
                        }
                        itemTouchHelper.startDrag(viewHolder);
                        return false;
                    }
                });
            }
        }
    }

    public static /* synthetic */ void bindData$default(LightView lightView, DeviceInfoBean deviceInfoBean, boolean z, PageLocation pageLocation, List list, String str, ItemTouchHelper itemTouchHelper, RecyclerView.ViewHolder viewHolder, int i, Object obj) {
        lightView.bindData(deviceInfoBean, (i & 2) != 0 ? false : z, pageLocation, (i & 8) != 0 ? null : list, str, (i & 32) != 0 ? null : itemTouchHelper, (i & 64) != 0 ? null : viewHolder);
    }

    public final void bindData(@NotNull DeviceInfoBean data, boolean z, @NotNull PageLocation location, @Nullable List<IDevice> list, @NotNull String categoryName, @Nullable ItemTouchHelper itemTouchHelper, @Nullable RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        this.a = data;
        this.b = list;
        this.i = z;
        this.j = viewHolder;
        this.k = categoryName;
        this.l = itemTouchHelper;
        this.n = location;
        this.m = location == PageLocation.PAGE_SECOND;
        DeviceItemInfo devicePropInfo = data.getDeviceInfoWrapper().getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "data.deviceInfoWrapper.devicePropInfo");
        if (devicePropInfo.getProperties().get(PropDic.P_BRIGHTNESS.getProp()) != null) {
            LightController1Binding lightController1Binding = this.e;
            if (lightController1Binding != null) {
                Intrinsics.checkNotNull(lightController1Binding);
                a(this, data, lightController1Binding, null, 4, null);
            } else if (!this.g) {
                this.c.inflate();
                this.g = true;
            }
        } else {
            LightController2Binding lightController2Binding = this.f;
            if (lightController2Binding != null) {
                Intrinsics.checkNotNull(lightController2Binding);
                a(this, data, lightController2Binding, null, 4, null);
            } else if (!this.h) {
                this.d.inflate();
                this.h = true;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MiotManager.registerMiotMsgCallback(getCallback());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MiotManager.unregisterMiotMsgCallback(getCallback());
    }

    public static /* synthetic */ void a(LightView lightView, DeviceInfoBean deviceInfoBean, ViewDataBinding viewDataBinding, Boolean bool, int i, Object obj) {
        if ((i & 4) != 0) {
            bool = null;
        }
        lightView.a(deviceInfoBean, (DeviceInfoBean) viewDataBinding, bool);
    }

    public final <T extends ViewDataBinding> void a(DeviceInfoBean deviceInfoBean, T t, Boolean bool) {
        if (!Threads.isMainThread()) {
            Threads.postInMainThread(new d(bool, deviceInfoBean, t));
        } else {
            a(bool, deviceInfoBean, (DeviceInfoBean) t);
        }
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class d implements Runnable {
        final /* synthetic */ Boolean b;
        final /* synthetic */ DeviceInfoBean c;
        final /* synthetic */ ViewDataBinding d;

        d(Boolean bool, DeviceInfoBean deviceInfoBean, ViewDataBinding viewDataBinding) {
            LightView.this = r1;
            this.b = bool;
            this.c = deviceInfoBean;
            this.d = viewDataBinding;
        }

        @Override // java.lang.Runnable
        public final void run() {
            LightView.this.a(this.b, this.c, (DeviceInfoBean) this.d);
        }
    }

    public final <T extends ViewDataBinding> void a(Boolean bool, DeviceInfoBean deviceInfoBean, T t) {
        ImageView imageView;
        VerticalSeekView verticalSeekView;
        if (bool != null) {
            deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().currentStatus = bool.booleanValue();
        }
        if (!t.getRoot().hasOnClickListeners()) {
            t.getRoot().setOnClickListener(getOnClickListener());
        }
        if (t instanceof LightController1Binding) {
            LightController1Binding lightController1Binding = (LightController1Binding) t;
            imageView = lightController1Binding.ivDragHandle;
            lightController1Binding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            lightController1Binding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            lightController1Binding.setEditMode(this.i);
            ConstraintLayout constraintLayout = lightController1Binding.container;
            Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.container");
            Context context = getContext();
            List<IDevice> list = this.b;
            String str = this.k;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("categoryName");
            }
            constraintLayout.setBackground(ContextCompat.getDrawable(context, MiotDeviceUtils.getItemBackgroundRes(list, deviceInfoBean, str)));
            LightController1Binding lightController1Binding2 = this.e;
            Intrinsics.checkNotNull(lightController1Binding2);
            AnimLifecycleManager.repeatOnAttach(lightController1Binding2.container, MicoAnimConfigurator4BigButton.get());
            LightController1Binding lightController1Binding3 = this.e;
            if (!(lightController1Binding3 == null || (verticalSeekView = lightController1Binding3.seekView) == null)) {
                DeviceInfoBean deviceInfoBean2 = this.a;
                if (deviceInfoBean2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("device");
                }
                verticalSeekView.setVolume(MiotDeviceUtils.value2SeekViewVolume(deviceInfoBean2.getDeviceInfoWrapper(), PropDic.P_BRIGHTNESS));
            }
            if (!lightController1Binding.seekView.hasOnClickListeners()) {
                lightController1Binding.seekView.setOnClickListener(getOnClickListener());
            }
            if (this.i) {
                MicoAnimationManager.getManager().startShowAnims(lightController1Binding.ivDragHandle, lightController1Binding.ivDevicePic, lightController1Binding.tvDeviceStatus);
                MicoAnimationManager.getManager().startHideAnims(lightController1Binding.seekView, lightController1Binding.ivStatus);
                MicoAnimationManager.getManager().startScaleSmallAnims(lightController1Binding.container);
            } else {
                if (!deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                    MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.m), lightController1Binding.seekView);
                    MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.m), lightController1Binding.ivDevicePic, lightController1Binding.tvDeviceStatus);
                } else if (deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().currentStatus) {
                    MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.m), lightController1Binding.seekView);
                    MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.m), lightController1Binding.ivDevicePic, lightController1Binding.tvDeviceStatus);
                } else {
                    MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.m), lightController1Binding.seekView);
                    MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.m), lightController1Binding.ivDevicePic, lightController1Binding.tvDeviceStatus);
                }
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.m), lightController1Binding.ivStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.m), lightController1Binding.ivDragHandle);
                a(t);
            }
        } else if (t != null) {
            LightController2Binding lightController2Binding = (LightController2Binding) t;
            imageView = lightController2Binding.ivDragHandle;
            lightController2Binding.setItem(deviceInfoBean.getDeviceInfoWrapper());
            lightController2Binding.setIsLocationDeviceNameExchange(deviceInfoBean.isLocationDeviceNameExchange());
            lightController2Binding.setEditMode(this.i);
            ConstraintLayout constraintLayout2 = lightController2Binding.container;
            Intrinsics.checkNotNullExpressionValue(constraintLayout2, "binding.container");
            Context context2 = getContext();
            List<IDevice> list2 = this.b;
            String str2 = this.k;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("categoryName");
            }
            constraintLayout2.setBackground(ContextCompat.getDrawable(context2, MiotDeviceUtils.getItemBackgroundRes(list2, deviceInfoBean, str2)));
            LightController2Binding lightController2Binding2 = this.f;
            Intrinsics.checkNotNull(lightController2Binding2);
            AnimLifecycleManager.repeatOnAttach(lightController2Binding2.container, MicoAnimConfigurator4BigButton.get());
            if (this.i) {
                MicoAnimationManager.getManager().startScaleSmallAnims(lightController2Binding.container);
                MicoAnimationManager.getManager().startShowAnims(lightController2Binding.ivDragHandle);
                MicoAnimationManager.getManager().startHideAnims(lightController2Binding.ivStatus);
            } else {
                a(t);
                MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.m), lightController2Binding.ivStatus);
                MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.m), lightController2Binding.ivDragHandle);
            }
        } else {
            throw new NullPointerException("null cannot be cast to non-null type com.xiaomi.smarthome.databinding.LightController2Binding");
        }
        if (imageView != null) {
            imageView.setOnTouchListener(new c());
        }
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u000e\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\u000e\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\b0\bH\n¢\u0006\u0002\b\t"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", ai.aC, "Landroid/view/View;", "kotlin.jvm.PlatformType", "event", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c implements View.OnTouchListener {
        c() {
            LightView.this = r1;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            ItemTouchHelper itemTouchHelper;
            RecyclerView.ViewHolder viewHolder = LightView.this.j;
            if (viewHolder == null || (itemTouchHelper = LightView.this.l) == null) {
                return false;
            }
            itemTouchHelper.startDrag(viewHolder);
            return false;
        }
    }

    private final <T extends ViewDataBinding> void a(T t) {
        if (this.n == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(t.getRoot());
        }
    }
}
