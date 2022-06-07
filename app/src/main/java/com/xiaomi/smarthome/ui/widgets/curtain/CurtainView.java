package com.xiaomi.smarthome.ui.widgets.curtain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4ControlDevice;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingCompleteActivity;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DevicePropValue;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.category.ValueConstrain;
import com.xiaomi.miot.support.core.ISendMsgCallback;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.CurtainOperation;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.UiUtils;
import com.xiaomi.smarthome.databinding.ViewCurtainBinding;
import com.xiaomi.smarthome.ui.SmartHomeModeCategoryFragment;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CurtainView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007¢\u0006\u0002\u0010\rJR\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00132\u0010\b\u0002\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%J\u001f\u0010&\u001a\u00020\u0019\"\b\b\u0000\u0010'*\u00020(2\u0006\u0010\u000e\u001a\u0002H'H\u0002¢\u0006\u0002\u0010)R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/curtain/CurtainView;", "Landroidx/constraintlayout/widget/ConstraintLayout;", c.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "attrs", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "binding", "Lcom/xiaomi/smarthome/databinding/ViewCurtainBinding;", IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO, "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "pageLocation", "Lcom/xiaomi/smarthome/ui/adapter/PageLocation;", "useAnim", "", OneTrack.Event.VIEW, "Landroid/view/View;", "bindData", "", "deviceInfoBean", "isEditMode", "location", "list", "", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "categoryName", "", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "startScaleLargeAnims", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", "(Landroidx/databinding/ViewDataBinding;)V", "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ClickableViewAccessibility"})
/* loaded from: classes4.dex */
public final class CurtainView extends ConstraintLayout {
    private DeviceInfoBean a;
    private ViewCurtainBinding b;
    private boolean c;
    private PageLocation d;
    private final View e;

    public static final /* synthetic */ DeviceInfoBean access$getDeviceInfo$p(CurtainView curtainView) {
        DeviceInfoBean deviceInfoBean = curtainView.a;
        if (deviceInfoBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException(IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO);
        }
        return deviceInfoBean;
    }

    public /* synthetic */ CurtainView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurtainView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.c = true;
        this.d = PageLocation.PAGE_FIRST;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_curtain, (ViewGroup) this, true);
        Intrinsics.checkNotNullExpressionValue(inflate, "LayoutInflater.from(cont…view_curtain, this, true)");
        this.e = inflate;
        this.e.setTag("layout/view_curtain_0");
        ViewCurtainBinding bind = ViewCurtainBinding.bind(this.e);
        Intrinsics.checkNotNullExpressionValue(bind, "ViewCurtainBinding.bind(view)");
        this.b = bind;
        AnimLifecycleManager.repeatOnAttach(this.b.ivOpen, MicoAnimConfigurator4ControlDevice.get());
        ImageView imageView = this.b.ivOpen;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.ivOpen");
        RxView.clicks(imageView).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView.1
            {
                CurtainView.this = this;
            }

            /* renamed from: a */
            public final void accept(Unit unit) {
                String propKey = MiotDeviceUtils.getPropKey(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                if (propKey != null) {
                    ValueConstrain valueConstrain = null;
                    try {
                        List<DevicePropertyBriefInfo> props = MiotDeviceUtils.getProps(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                        Intrinsics.checkNotNull(props);
                        DevicePropValue propValue = props.get(0).getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue, "getProps(\n              …         )!![0].propValue");
                        valueConstrain = propValue.getValueConstrain();
                    } catch (IndexOutOfBoundsException e) {
                        L.smarthome.e("tmpValueConstrain IndexOutOfBoundsException:", e);
                    } catch (NullPointerException e2) {
                        L.smarthome.e("tmpValueConstrain NullPointerException:", e2);
                    }
                    MiotManager.setDeviceProperty(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper().getDeviceInfo().did, propKey, valueConstrain == null ? CurtainOperation.OPEN.getValue() : valueConstrain.getValueByDescription(CurtainOperation.OPEN.getDescription()), CurtainOperation.OPEN.getFormat(), a.a);
                }
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
            }

            /* compiled from: CurtainView.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "result", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
            /* renamed from: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView$1$a */
            /* loaded from: classes4.dex */
            public static final class a implements ISendMsgCallback {
                public static final a a = new a();

                a() {
                }

                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                public final void onResult(String str) {
                    Logger logger = L.smarthome;
                    logger.d("Open Curtain callback result=" + str);
                }
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.b.ivClose, MicoAnimConfigurator4ControlDevice.get());
        ImageView imageView2 = this.b.ivClose;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.ivClose");
        RxView.clicks(imageView2).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView.2
            {
                CurtainView.this = this;
            }

            /* renamed from: a */
            public final void accept(Unit unit) {
                String propKey = MiotDeviceUtils.getPropKey(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                if (propKey != null) {
                    ValueConstrain valueConstrain = null;
                    try {
                        List<DevicePropertyBriefInfo> props = MiotDeviceUtils.getProps(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                        Intrinsics.checkNotNull(props);
                        DevicePropValue propValue = props.get(0).getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue, "getProps(\n              …         )!![0].propValue");
                        valueConstrain = propValue.getValueConstrain();
                    } catch (IndexOutOfBoundsException e) {
                        L.smarthome.e("tmpValueConstrain IndexOutOfBoundsException:", e);
                    } catch (NullPointerException e2) {
                        L.smarthome.e("tmpValueConstrain NullPointerException:", e2);
                    }
                    MiotManager.setDeviceProperty(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper().getDeviceInfo().did, propKey, valueConstrain == null ? CurtainOperation.CLOSE.getValue() : valueConstrain.getValueByDescription(CurtainOperation.CLOSE.getDescription()), CurtainOperation.CLOSE.getFormat(), a.a);
                }
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
            }

            /* compiled from: CurtainView.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "result", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
            /* renamed from: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView$2$a */
            /* loaded from: classes4.dex */
            public static final class a implements ISendMsgCallback {
                public static final a a = new a();

                a() {
                }

                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                public final void onResult(String str) {
                    Logger logger = L.smarthome;
                    logger.d("Close Curtain callback result=" + str);
                }
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.b.ivPause, MicoAnimConfigurator4ControlDevice.get());
        ImageView imageView3 = this.b.ivPause;
        Intrinsics.checkNotNullExpressionValue(imageView3, "binding.ivPause");
        RxView.clicks(imageView3).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView.3
            {
                CurtainView.this = this;
            }

            /* renamed from: a */
            public final void accept(Unit unit) {
                String propKey = MiotDeviceUtils.getPropKey(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                if (propKey != null) {
                    ValueConstrain valueConstrain = null;
                    try {
                        List<DevicePropertyBriefInfo> props = MiotDeviceUtils.getProps(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper(), PropDic.P_MONITOR_CONTROL);
                        Intrinsics.checkNotNull(props);
                        DevicePropValue propValue = props.get(0).getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue, "getProps(\n              …         )!![0].propValue");
                        valueConstrain = propValue.getValueConstrain();
                    } catch (IndexOutOfBoundsException e) {
                        L.smarthome.e("tmpValueConstrain IndexOutOfBoundsException:", e);
                    } catch (NullPointerException e2) {
                        L.smarthome.e("tmpValueConstrain NullPointerException:", e2);
                    }
                    MiotManager.setDeviceProperty(CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper().getDeviceInfo().did, propKey, valueConstrain == null ? CurtainOperation.PAUSE.getValue() : valueConstrain.getValueByDescription(CurtainOperation.PAUSE.getDescription()), CurtainOperation.PAUSE.getFormat(), a.a);
                }
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
            }

            /* compiled from: CurtainView.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "result", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
            /* renamed from: com.xiaomi.smarthome.ui.widgets.curtain.CurtainView$3$a */
            /* loaded from: classes4.dex */
            public static final class a implements ISendMsgCallback {
                public static final a a = new a();

                a() {
                }

                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                public final void onResult(String str) {
                    Logger logger = L.smarthome;
                    logger.d("Pause Curtain callback result=" + str);
                }
            }
        });
        TextView textView = this.b.tvLocation;
        Intrinsics.checkNotNullExpressionValue(textView, "binding.tvLocation");
        textView.setPaintFlags(Opcodes.INSTANCEOF);
        TextView textView2 = this.b.tvDeviceType;
        Intrinsics.checkNotNullExpressionValue(textView2, "binding.tvDeviceType");
        textView2.setPaintFlags(Opcodes.INSTANCEOF);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CurtainView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ CurtainView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CurtainView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CurtainView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public static /* synthetic */ void bindData$default(CurtainView curtainView, DeviceInfoBean deviceInfoBean, boolean z, PageLocation pageLocation, List list, String str, ItemTouchHelper itemTouchHelper, RecyclerView.ViewHolder viewHolder, int i, Object obj) {
        curtainView.bindData(deviceInfoBean, (i & 2) != 0 ? false : z, pageLocation, (i & 8) != 0 ? null : list, str, (i & 32) != 0 ? null : itemTouchHelper, (i & 64) != 0 ? null : viewHolder);
    }

    public final void bindData(@NotNull DeviceInfoBean deviceInfoBean, boolean z, @NotNull PageLocation location, @Nullable List<IDevice> list, @NotNull String categoryName, @Nullable ItemTouchHelper itemTouchHelper, @Nullable RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(deviceInfoBean, "deviceInfoBean");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        this.a = deviceInfoBean;
        this.d = location;
        this.c = location == PageLocation.PAGE_SECOND;
        this.b.setItem(deviceInfoBean.getDeviceInfoWrapper());
        ImageView imageView = this.b.ivDragHandle;
        if (imageView != null) {
            imageView.setOnTouchListener(new a(viewHolder, itemTouchHelper));
        }
        this.b.setEditMode(z);
        ConstraintLayout constraintLayout = this.b.container;
        Intrinsics.checkNotNullExpressionValue(constraintLayout, "binding.container");
        constraintLayout.setBackground(ContextCompat.getDrawable(getContext(), MiotDeviceUtils.getItemBackgroundRes(list, deviceInfoBean, categoryName)));
        AnimLifecycleManager.repeatOnAttach(this.b.container, MicoAnimConfigurator4BigButton.get());
        if (!this.b.getRoot().hasOnClickListeners()) {
            this.b.getRoot().setOnClickListener(new b(z, deviceInfoBean));
        }
        if (z) {
            MicoAnimationManager.getManager().startScaleSmallAnims(this.b.container);
            MicoAnimationManager.getManager().startShowAnims(this.b.ivOfflinePic, this.b.ivDragHandle, this.b.tvOffline);
            MicoAnimationManager.getManager().startHideAnims(this.b.ivPause, this.b.ivClose, this.b.ivOpen, this.b.ivStatus);
            return;
        }
        a(this.b);
        MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.c), this.b.ivDragHandle);
        if (deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
            MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.c), this.b.ivOfflinePic, this.b.tvOffline, this.b.ivStatus);
            MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.c), this.b.ivPause, this.b.ivClose, this.b.ivOpen);
            return;
        }
        MicoAnimationManager.getManager().startShowAnims(Boolean.valueOf(this.c), this.b.ivOfflinePic, this.b.tvOffline, this.b.ivStatus);
        MicoAnimationManager.getManager().startHideAnims(Boolean.valueOf(this.c), this.b.ivPause, this.b.ivClose, this.b.ivOpen);
    }

    /* compiled from: CurtainView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", ai.aC, "Landroid/view/View;", "kotlin.jvm.PlatformType", "event", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a implements View.OnTouchListener {
        final /* synthetic */ RecyclerView.ViewHolder a;
        final /* synthetic */ ItemTouchHelper b;

        a(RecyclerView.ViewHolder viewHolder, ItemTouchHelper itemTouchHelper) {
            this.a = viewHolder;
            this.b = itemTouchHelper;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            ItemTouchHelper itemTouchHelper;
            RecyclerView.ViewHolder viewHolder = this.a;
            if (viewHolder == null || (itemTouchHelper = this.b) == null) {
                return false;
            }
            itemTouchHelper.startDrag(viewHolder);
            return false;
        }
    }

    /* compiled from: CurtainView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b implements View.OnClickListener {
        final /* synthetic */ boolean b;
        final /* synthetic */ DeviceInfoBean c;

        b(boolean z, DeviceInfoBean deviceInfoBean) {
            CurtainView.this = r1;
            this.b = z;
            this.c = deviceInfoBean;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (!UiUtils.isFastClick() && !this.b) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("item", CurtainView.access$getDeviceInfo$p(CurtainView.this).getDeviceInfoWrapper());
                View root = CurtainView.this.b.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "binding.root");
                linkedHashMap.put(OneTrack.Event.VIEW, root);
                LiveEventBus.get(SmartHomeModeCategoryFragment.EVENT_OPEN_CARD).post(linkedHashMap);
            }
            if (this.c.getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT_MORE);
            }
        }
    }

    private final <T extends ViewDataBinding> void a(T t) {
        if (this.d == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(t.getRoot());
        }
    }
}
