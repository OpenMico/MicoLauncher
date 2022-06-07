package com.xiaomi.smarthome.ui.widgets.environments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.databinding.ViewDataBinding;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.asm.Opcodes;
import com.elvishew.xlog.Logger;
import com.jakewharton.rxbinding4.view.RxView;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mico.base.exts.ViewExtKt;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4ControlDevice;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.ui.MicoAnimationManager;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.DevicePropValue;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.miot.support.category.ValueRange;
import com.xiaomi.miot.support.core.ISendMsgCallback;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.AirConditionMode;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.DeviceTypeDic;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.exts.DeviceExtsKt;
import com.xiaomi.smarthome.core.exts.ViewExtsKt;
import com.xiaomi.smarthome.core.utils.AnimationUtils;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.core.utils.OperationUtils;
import com.xiaomi.smarthome.databinding.EnvAirConditionControllerBinding;
import com.xiaomi.smarthome.databinding.EnvAirPurifierController1Binding;
import com.xiaomi.smarthome.databinding.EnvFanController1Binding;
import com.xiaomi.smarthome.databinding.EnvFanController2Binding;
import com.xiaomi.smarthome.databinding.EnvHeaterController1Binding;
import com.xiaomi.smarthome.databinding.EnvHeaterController2Binding;
import com.xiaomi.smarthome.databinding.EnvHumidityController1Binding;
import com.xiaomi.smarthome.databinding.EnvHumidityController2Binding;
import com.xiaomi.smarthome.ui.adapter.PageLocation;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener;
import com.xiaomi.smarthome.ui.widgets.ShiftChooser;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;
import com.xiaomi.smarthome.ui.widgets.VerticalShiftChooser;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: EnvView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Ç\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013*\u0001<\b\u0007\u0018\u0000 h2\u00020\u0001:\u0001hB%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\nB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fJR\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u00162\b\b\u0002\u00107\u001a\u0002042\u0006\u0010M\u001a\u00020A2\u0010\b\u0002\u00108\u001a\n\u0012\u0004\u0012\u00020:\u0018\u0001092\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010B\u001a\u0004\u0018\u00010C2\n\b\u0002\u0010E\u001a\u0004\u0018\u00010FJ(\u0010N\u001a\u00020K2\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u00142\u0006\u0010R\u001a\u00020\u00072\u0006\u0010S\u001a\u00020\u0007H\u0002J \u0010T\u001a\u00020K2\b\u0010U\u001a\u0004\u0018\u00010V2\u0006\u0010W\u001a\u00020\u00072\u0006\u0010X\u001a\u000204J\u0010\u0010Y\u001a\u0002042\u0006\u0010Z\u001a\u00020\u001aH\u0002J\b\u0010[\u001a\u00020KH\u0014J\b\u0010\\\u001a\u00020KH\u0014J\u001f\u0010]\u001a\u00020K\"\b\b\u0000\u0010^*\u0002062\u0006\u0010_\u001a\u0002H^H\u0002¢\u0006\u0002\u0010`J\u0012\u0010a\u001a\u00020K2\b\u0010Z\u001a\u0004\u0018\u00010\u001aH\u0002J1\u0010b\u001a\u00020K\"\b\b\u0000\u0010^*\u0002062\b\u0010c\u001a\u0004\u0018\u0001042\u0006\u0010d\u001a\u00020\u00162\u0006\u0010_\u001a\u0002H^H\u0002¢\u0006\u0002\u0010eJ3\u0010f\u001a\u00020K\"\b\b\u0000\u0010^*\u0002062\u0006\u0010d\u001a\u00020\u00162\u0006\u0010_\u001a\u0002H^2\n\b\u0002\u0010c\u001a\u0004\u0018\u000104H\u0002¢\u0006\u0002\u0010gR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0006\u0012\u0004\u0018\u00010403X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u00105\u001a\u0010\u0012\u0004\u0012\u00020\u001a\u0012\u0006\u0012\u0004\u0018\u00010603X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u00108\u001a\n\u0012\u0004\u0012\u00020:\u0018\u000109X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010;\u001a\u00020<8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\u0012\u001a\u0004\b=\u0010>R\u000e\u0010@\u001a\u00020AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u00010CX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u000204X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u00010FX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010G\u001a\u0016\u0012\u0004\u0012\u00020\u0014\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0I0HX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/environments/EnvView;", "Landroid/widget/FrameLayout;", com.umeng.analytics.pro.c.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "(Landroid/content/Context;)V", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "callback", "Lcom/xiaomi/miot/support/IMiotMsgCallback;", "getCallback", "()Lcom/xiaomi/miot/support/IMiotMsgCallback;", "callback$delegate", "Lkotlin/Lazy;", "categoryName", "", "device", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "envAirConditionControllerBinding", "Lcom/xiaomi/smarthome/databinding/EnvAirConditionControllerBinding;", "envAirConditionViewStub", "Landroid/view/ViewStub;", "envAirFreshController1Binding", "Lcom/xiaomi/smarthome/databinding/EnvAirPurifierController1Binding;", "envAirFreshViewStub", "envAirPurifierController1Binding", "envAirPurifierViewStub", "envFan1ViewStub", "envFan2ViewStub", "envFanController1Binding", "Lcom/xiaomi/smarthome/databinding/EnvFanController1Binding;", "envFanController2Binding", "Lcom/xiaomi/smarthome/databinding/EnvFanController2Binding;", "envHeater1ViewStub", "envHeater2ViewStub", "envHeaterController1Binding", "Lcom/xiaomi/smarthome/databinding/EnvHeaterController1Binding;", "envHeaterController2Binding", "Lcom/xiaomi/smarthome/databinding/EnvHeaterController2Binding;", "envHumidityController1Binding", "Lcom/xiaomi/smarthome/databinding/EnvHumidityController1Binding;", "envHumidityController2Binding", "Lcom/xiaomi/smarthome/databinding/EnvHumidityController2Binding;", "envHumidityViewStub", "envHumidityViewStub2", "inflatedRecords", "", "", "inflatedViews", "Landroidx/databinding/ViewDataBinding;", "isEditMode", "list", "", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "onClickListener", "com/xiaomi/smarthome/ui/widgets/environments/EnvView$onClickListener$2$1", "getOnClickListener", "()Lcom/xiaomi/smarthome/ui/widgets/environments/EnvView$onClickListener$2$1;", "onClickListener$delegate", "pageLocation", "Lcom/xiaomi/smarthome/ui/adapter/PageLocation;", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "useAnim", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "viewStubMap", "", "", "bindData", "", "data", "location", "bindGradientText", OneTrack.Event.VIEW, "Landroid/widget/TextView;", "text", "startColor", "endColor", "handleStopTrackingTouch", ai.aC, "Lcom/xiaomi/smarthome/ui/widgets/ShiftChooser;", "shift", "isShiftChanged", "isViewStubInflated", "viewStub", "onAttachedToWindow", "onDetachedFromWindow", "startScaleLargeAnims", ExifInterface.GPS_DIRECTION_TRUE, "binding", "(Landroidx/databinding/ViewDataBinding;)V", "toggleShow", "update", "turnStatus", "item", "(Ljava/lang/Boolean;Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;Landroidx/databinding/ViewDataBinding;)V", "updateUI", "(Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;Landroidx/databinding/ViewDataBinding;Ljava/lang/Boolean;)V", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
@SuppressLint({"ClickableViewAccessibility"})
/* loaded from: classes4.dex */
public final class EnvView extends FrameLayout {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String TAG = "Env:";
    private RecyclerView.ViewHolder A;
    private String B;
    private final Lazy C;
    private ItemTouchHelper D;
    private final Lazy E;
    private volatile DeviceInfoBean a;
    private List<IDevice> b;
    private ViewStub c;
    private ViewStub d;
    private ViewStub e;
    private ViewStub f;
    private ViewStub g;
    private ViewStub h;
    private ViewStub i;
    private ViewStub j;
    private ViewStub k;
    private EnvAirConditionControllerBinding l;
    private EnvHeaterController1Binding m;
    private EnvHeaterController2Binding n;
    private EnvAirPurifierController1Binding o;
    private EnvAirPurifierController1Binding p;
    private EnvHumidityController1Binding q;
    private EnvHumidityController2Binding r;
    private EnvFanController1Binding s;
    private EnvFanController2Binding t;
    private final Map<ViewStub, Boolean> u;
    private final Map<ViewStub, ViewDataBinding> v;
    private final Map<String, List<ViewStub>> w;
    private boolean x;
    private boolean y;
    private PageLocation z;

    private final IMiotMsgCallback getCallback() {
        return (IMiotMsgCallback) this.E.getValue();
    }

    private final EnvView$onClickListener$2$1 getOnClickListener() {
        return (EnvView$onClickListener$2$1) this.C.getValue();
    }

    public static final /* synthetic */ DeviceInfoBean access$getDevice$p(EnvView envView) {
        DeviceInfoBean deviceInfoBean = envView.a;
        if (deviceInfoBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("device");
        }
        return deviceInfoBean;
    }

    public static final /* synthetic */ EnvAirConditionControllerBinding access$getEnvAirConditionControllerBinding$p(EnvView envView) {
        EnvAirConditionControllerBinding envAirConditionControllerBinding = envView.l;
        if (envAirConditionControllerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envAirConditionControllerBinding");
        }
        return envAirConditionControllerBinding;
    }

    public static final /* synthetic */ EnvAirPurifierController1Binding access$getEnvAirFreshController1Binding$p(EnvView envView) {
        EnvAirPurifierController1Binding envAirPurifierController1Binding = envView.p;
        if (envAirPurifierController1Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envAirFreshController1Binding");
        }
        return envAirPurifierController1Binding;
    }

    public static final /* synthetic */ EnvAirPurifierController1Binding access$getEnvAirPurifierController1Binding$p(EnvView envView) {
        EnvAirPurifierController1Binding envAirPurifierController1Binding = envView.o;
        if (envAirPurifierController1Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envAirPurifierController1Binding");
        }
        return envAirPurifierController1Binding;
    }

    public static final /* synthetic */ EnvFanController1Binding access$getEnvFanController1Binding$p(EnvView envView) {
        EnvFanController1Binding envFanController1Binding = envView.s;
        if (envFanController1Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envFanController1Binding");
        }
        return envFanController1Binding;
    }

    public static final /* synthetic */ EnvFanController2Binding access$getEnvFanController2Binding$p(EnvView envView) {
        EnvFanController2Binding envFanController2Binding = envView.t;
        if (envFanController2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envFanController2Binding");
        }
        return envFanController2Binding;
    }

    public static final /* synthetic */ EnvHeaterController1Binding access$getEnvHeaterController1Binding$p(EnvView envView) {
        EnvHeaterController1Binding envHeaterController1Binding = envView.m;
        if (envHeaterController1Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envHeaterController1Binding");
        }
        return envHeaterController1Binding;
    }

    public static final /* synthetic */ EnvHeaterController2Binding access$getEnvHeaterController2Binding$p(EnvView envView) {
        EnvHeaterController2Binding envHeaterController2Binding = envView.n;
        if (envHeaterController2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envHeaterController2Binding");
        }
        return envHeaterController2Binding;
    }

    public static final /* synthetic */ EnvHumidityController1Binding access$getEnvHumidityController1Binding$p(EnvView envView) {
        EnvHumidityController1Binding envHumidityController1Binding = envView.q;
        if (envHumidityController1Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envHumidityController1Binding");
        }
        return envHumidityController1Binding;
    }

    public static final /* synthetic */ EnvHumidityController2Binding access$getEnvHumidityController2Binding$p(EnvView envView) {
        EnvHumidityController2Binding envHumidityController2Binding = envView.r;
        if (envHumidityController2Binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("envHumidityController2Binding");
        }
        return envHumidityController2Binding;
    }

    public /* synthetic */ EnvView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnvView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.u = new LinkedHashMap();
        this.v = new LinkedHashMap();
        this.y = true;
        this.z = PageLocation.PAGE_FIRST;
        this.C = LazyKt.lazy(new c());
        this.E = LazyKt.lazy(new a());
        LayoutInflater.from(context).inflate(R.layout.view_env, (ViewGroup) this, true);
        View findViewById = findViewById(R.id.envAirCondition);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(R.id.envAirCondition)");
        this.c = (ViewStub) findViewById;
        View findViewById2 = findViewById(R.id.envAirPurifier);
        Intrinsics.checkNotNullExpressionValue(findViewById2, "findViewById(R.id.envAirPurifier)");
        this.f = (ViewStub) findViewById2;
        View findViewById3 = findViewById(R.id.envAirFresh);
        Intrinsics.checkNotNullExpressionValue(findViewById3, "findViewById(R.id.envAirFresh)");
        this.g = (ViewStub) findViewById3;
        View findViewById4 = findViewById(R.id.envHeater1);
        Intrinsics.checkNotNullExpressionValue(findViewById4, "findViewById(R.id.envHeater1)");
        this.d = (ViewStub) findViewById4;
        View findViewById5 = findViewById(R.id.envHeater2);
        Intrinsics.checkNotNullExpressionValue(findViewById5, "findViewById(R.id.envHeater2)");
        this.e = (ViewStub) findViewById5;
        View findViewById6 = findViewById(R.id.envHumidity);
        Intrinsics.checkNotNullExpressionValue(findViewById6, "findViewById(R.id.envHumidity)");
        this.h = (ViewStub) findViewById6;
        View findViewById7 = findViewById(R.id.envHumidity2);
        Intrinsics.checkNotNullExpressionValue(findViewById7, "findViewById(R.id.envHumidity2)");
        this.i = (ViewStub) findViewById7;
        View findViewById8 = findViewById(R.id.envFan1);
        Intrinsics.checkNotNullExpressionValue(findViewById8, "findViewById(R.id.envFan1)");
        this.j = (ViewStub) findViewById8;
        View findViewById9 = findViewById(R.id.envFan2);
        Intrinsics.checkNotNullExpressionValue(findViewById9, "findViewById(R.id.envFan2)");
        this.k = (ViewStub) findViewById9;
        d dVar = new d(context);
        this.c.setOnInflateListener(dVar);
        this.g.setOnInflateListener(dVar);
        this.f.setOnInflateListener(dVar);
        this.h.setOnInflateListener(dVar);
        this.i.setOnInflateListener(dVar);
        this.d.setOnInflateListener(dVar);
        this.e.setOnInflateListener(dVar);
        this.j.setOnInflateListener(dVar);
        this.k.setOnInflateListener(dVar);
        this.w = MapsKt.mapOf(TuplesKt.to(DeviceTypeDic.DT_ENV_AIR_CONDITIONER.getDeviceType(), CollectionsKt.listOf(this.c)), TuplesKt.to(DeviceTypeDic.DT_ENV_HUMIDIFIER.getDeviceType(), CollectionsKt.listOf((Object[]) new ViewStub[]{this.h, this.i})), TuplesKt.to(DeviceTypeDic.DT_ENV_AIR_FRESH.getDeviceType(), CollectionsKt.listOf(this.g)), TuplesKt.to(DeviceTypeDic.DT_ENV_AIR_PURIFIER.getDeviceType(), CollectionsKt.listOf(this.f)), TuplesKt.to(DeviceTypeDic.DT_ENV_FAN.getDeviceType(), CollectionsKt.listOf((Object[]) new ViewStub[]{this.j, this.k})), TuplesKt.to(DeviceTypeDic.DT_ENV_HEATER.getDeviceType(), CollectionsKt.listOf((Object[]) new ViewStub[]{this.d, this.e})));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "com/xiaomi/smarthome/ui/widgets/environments/EnvView$onClickListener$2$1", "invoke", "()Lcom/xiaomi/smarthome/ui/widgets/environments/EnvView$onClickListener$2$1;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c extends Lambda implements Function0<EnvView$onClickListener$2$1> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c() {
            super(0);
            EnvView.this = r1;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.smarthome.ui.widgets.environments.EnvView$onClickListener$2$1] */
        @NotNull
        /* renamed from: a */
        public final EnvView$onClickListener$2$1 invoke() {
            return new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView$onClickListener$2$1
                /* JADX WARN: Code restructure failed: missing block: B:4:0x0006, code lost:
                    r0 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.this.x;
                 */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void onClick(@org.jetbrains.annotations.Nullable android.view.View r4) {
                    /*
                        r3 = this;
                        boolean r0 = com.xiaomi.smarthome.core.utils.UiUtils.isFastClick()
                        if (r0 != 0) goto L_0x0054
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView$c r0 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.c.this
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView r0 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.this
                        boolean r0 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.access$isEditMode$p(r0)
                        if (r0 == 0) goto L_0x0011
                        goto L_0x0054
                    L_0x0011:
                        java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
                        r0.<init>()
                        java.util.Map r0 = (java.util.Map) r0
                        java.lang.String r1 = "item"
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView$c r2 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.c.this
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView r2 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.this
                        com.xiaomi.smarthome.core.entity.DeviceInfoBean r2 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.access$getDevice$p(r2)
                        com.xiaomi.miot.support.category.DeviceInfoWrapper r2 = r2.getDeviceInfoWrapper()
                        r0.put(r1, r2)
                        java.lang.String r1 = "view"
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
                        r0.put(r1, r4)
                        java.lang.String r4 = "event_open_card"
                        com.jeremyliao.liveeventbus.core.Observable r4 = com.jeremyliao.liveeventbus.LiveEventBus.get(r4)
                        r4.post(r0)
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView$c r4 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.c.this
                        com.xiaomi.smarthome.ui.widgets.environments.EnvView r4 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.this
                        com.xiaomi.smarthome.core.entity.DeviceInfoBean r4 = com.xiaomi.smarthome.ui.widgets.environments.EnvView.access$getDevice$p(r4)
                        com.xiaomi.miot.support.category.DeviceInfoWrapper r4 = r4.getDeviceInfoWrapper()
                        com.xiaomi.miot.DeviceInfo r4 = r4.getDeviceInfo()
                        boolean r4 = r4.isOnline
                        if (r4 == 0) goto L_0x0053
                        java.lang.String r4 = "equipmentMore"
                        com.xiaomi.smarthome.ui.model.SmartHomeStatHelper.recordSmartTabControlModeClick(r4)
                    L_0x0053:
                        return
                    L_0x0054:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.environments.EnvView$onClickListener$2$1.onClick(android.view.View):void");
                }
            };
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EnvView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ EnvView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EnvView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EnvView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/smarthome/ui/widgets/environments/EnvView$Companion;", "", "()V", "TAG", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", "com/xiaomi/smarthome/ui/widgets/environments/EnvView$callback$2$1", "invoke", "()Lcom/xiaomi/smarthome/ui/widgets/environments/EnvView$callback$2$1;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a extends Lambda implements Function0<EnvView$callback$2$1> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super(0);
            EnvView.this = r1;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.xiaomi.smarthome.ui.widgets.environments.EnvView$callback$2$1] */
        @NotNull
        /* renamed from: a */
        public final EnvView$callback$2$1 invoke() {
            return new IMiotMsgCallback() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView$callback$2$1
                @Override // com.xiaomi.miot.support.IMiotMsgCallback
                public int getAction() {
                    return 1;
                }

                /* compiled from: EnvView.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
                @DebugMetadata(c = "com.xiaomi.smarthome.ui.widgets.environments.EnvView$callback$2$1$onMsgCallback$1", f = "EnvView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
                /* loaded from: classes4.dex */
                static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ JSONObject $data;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    a(JSONObject jSONObject, Continuation continuation) {
                        super(2, continuation);
                        this.$data = jSONObject;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                        Intrinsics.checkNotNullParameter(completion, "completion");
                        return new a(this.$data, completion);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        DevicePropertyBriefInfo prop;
                        boolean parseBoolean;
                        boolean parseBoolean2;
                        boolean parseBoolean3;
                        String obj2;
                        boolean parseBoolean4;
                        ViewDataBinding viewDataBinding;
                        Integer intOrNull;
                        boolean parseBoolean5;
                        ViewDataBinding viewDataBinding2;
                        Integer intOrNull2;
                        ViewDataBinding viewDataBinding3;
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label == 0) {
                            ResultKt.throwOnFailure(obj);
                            JSONObject jSONObject = this.$data;
                            if (jSONObject != null) {
                                if (!jSONObject.has("did") || !jSONObject.has("prop") || !jSONObject.has(b.p)) {
                                    L.smarthome.e("Env: onMsgCallback " + this.$data);
                                } else {
                                    String string = jSONObject.getString("did");
                                    if (Intrinsics.areEqual(string, EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().did)) {
                                        L.smarthome.d("Env: onMsgCallback " + this.$data);
                                        String string2 = jSONObject.getString("prop");
                                        if (string2 == null) {
                                            string2 = null;
                                        } else if (StringsKt.startsWith$default(string2, "prop.", false, 2, (Object) null)) {
                                            string2 = StringsKt.replace$default(StringsKt.replace$default(string2, "prop.", "pi:", false, 4, (Object) null), ".", Constants.COLON_SEPARATOR, false, 4, (Object) null);
                                        }
                                        String string3 = jSONObject.getString(b.p);
                                        if (string3 == null) {
                                            string3 = "";
                                        }
                                        ModelInfo modelInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getModelInfo();
                                        Intrinsics.checkNotNullExpressionValue(modelInfo, "device.deviceInfoWrapper.modelInfo");
                                        String deviceType = modelInfo.getDeviceType();
                                        boolean z = true;
                                        if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_HEATER.getDeviceType())) {
                                            DevicePropertyBriefInfo prop2 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_TARGET_TEMP);
                                            if (prop2 != null && Intrinsics.areEqual(string2, prop2.getKey())) {
                                                if (prop2.getPropValue() == null) {
                                                    L.smarthome.e("Env: onMsgCallback heater(did=" + string + ") targetTemp propValue is null");
                                                } else {
                                                    DevicePropValue propValue = prop2.getPropValue();
                                                    Intrinsics.checkNotNullExpressionValue(propValue, "this.propValue");
                                                    propValue.setValue(string3);
                                                    EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView.updateVolume(MiotDeviceUtils.value2SeekViewVolume(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_TARGET_TEMP));
                                                }
                                            }
                                            DevicePropertyBriefInfo prop3 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                                            if (prop3 != null && Intrinsics.areEqual(string2, prop3.getKey())) {
                                                boolean parseBoolean6 = Boolean.parseBoolean(string3);
                                                if (EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus != parseBoolean6) {
                                                    EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean6;
                                                }
                                                if (prop2 == null) {
                                                    z = false;
                                                }
                                                EnvView envView = EnvView.this;
                                                DeviceInfoBean access$getDevice$p = EnvView.access$getDevice$p(EnvView.this);
                                                if (z) {
                                                    viewDataBinding3 = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this);
                                                } else {
                                                    viewDataBinding3 = EnvView.access$getEnvHeaterController2Binding$p(EnvView.this);
                                                }
                                                EnvView.a(envView, access$getDevice$p, viewDataBinding3, null, 4, null);
                                            }
                                        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_FAN.getDeviceType())) {
                                            DevicePropertyBriefInfo prop4 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                                            if (prop4 != null && Intrinsics.areEqual(string2, prop4.getKey())) {
                                                if (prop4.getPropValue() == null) {
                                                    L.smarthome.e("Env: onMsgCallback fan(did=" + string + ") fanlevel propValue is null");
                                                } else {
                                                    DevicePropValue propValue2 = prop4.getPropValue();
                                                    Intrinsics.checkNotNullExpressionValue(propValue2, "this.propValue");
                                                    propValue2.setValue(string3);
                                                    String propValue3 = MiotDeviceUtils.getPropValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                                                    EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.scrollToShift(((propValue3 == null || (intOrNull2 = StringsKt.toIntOrNull(propValue3)) == null) ? 1 : intOrNull2.intValue()) - 1, false);
                                                }
                                            }
                                            DevicePropertyBriefInfo prop5 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                                            if (!(prop5 == null || !Intrinsics.areEqual(string2, prop5.getKey()) || EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus == (parseBoolean5 = Boolean.parseBoolean(string3)))) {
                                                EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean5;
                                                if (prop4 == null) {
                                                    z = false;
                                                }
                                                EnvView envView2 = EnvView.this;
                                                DeviceInfoBean access$getDevice$p2 = EnvView.access$getDevice$p(EnvView.this);
                                                if (z) {
                                                    viewDataBinding2 = EnvView.access$getEnvFanController1Binding$p(EnvView.this);
                                                } else {
                                                    viewDataBinding2 = EnvView.access$getEnvFanController2Binding$p(EnvView.this);
                                                }
                                                EnvView.a(envView2, access$getDevice$p2, viewDataBinding2, null, 4, null);
                                            }
                                        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_HUMIDIFIER.getDeviceType())) {
                                            DevicePropertyBriefInfo prop6 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                                            if (prop6 != null && Intrinsics.areEqual(string2, prop6.getKey())) {
                                                if (prop6.getPropValue() == null) {
                                                    L.smarthome.e("Env: onMsgCallback humidity(did=" + string + ") fanlevel propValue is null");
                                                } else {
                                                    DevicePropValue propValue4 = prop6.getPropValue();
                                                    Intrinsics.checkNotNullExpressionValue(propValue4, "this.propValue");
                                                    propValue4.setValue(string3);
                                                    String propValue5 = MiotDeviceUtils.getPropValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                                                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.scrollToShift(((propValue5 == null || (intOrNull = StringsKt.toIntOrNull(propValue5)) == null) ? 1 : intOrNull.intValue()) - 1, false);
                                                }
                                            }
                                            DevicePropertyBriefInfo prop7 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                                            if (!(prop7 == null || !Intrinsics.areEqual(string2, prop7.getKey()) || EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus == (parseBoolean4 = Boolean.parseBoolean(string3)))) {
                                                EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean4;
                                                EnvView envView3 = EnvView.this;
                                                DeviceInfoBean access$getDevice$p3 = EnvView.access$getDevice$p(EnvView.this);
                                                if (prop6 != null) {
                                                    viewDataBinding = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this);
                                                } else {
                                                    viewDataBinding = EnvView.access$getEnvHumidityController2Binding$p(EnvView.this);
                                                }
                                                EnvView.a(envView3, access$getDevice$p3, viewDataBinding, null, 4, null);
                                            }
                                        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_CONDITIONER.getDeviceType())) {
                                            DevicePropertyBriefInfo prop8 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_TARGET_TEMP);
                                            if (prop8 != null && Intrinsics.areEqual(string2, prop8.getKey())) {
                                                if (prop8.getPropValue() == null) {
                                                    L.smarthome.e("Env: onMsgCallback air-condition(did=" + string + ") targetTemp propValue is null");
                                                } else {
                                                    DevicePropValue propValue6 = prop8.getPropValue();
                                                    Intrinsics.checkNotNullExpressionValue(propValue6, "this.propValue");
                                                    Object value = propValue6.getValue();
                                                    if (!Intrinsics.areEqual((value == null || (obj2 = value.toString()) == null) ? null : StringsKt.toDoubleOrNull(obj2), Double.parseDouble(string3))) {
                                                        DevicePropValue propValue7 = prop8.getPropValue();
                                                        Intrinsics.checkNotNullExpressionValue(propValue7, "this.propValue");
                                                        propValue7.setValue(string3);
                                                        EnvView envView4 = EnvView.this;
                                                        TextView textView = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).tvTemperature;
                                                        Intrinsics.checkNotNullExpressionValue(textView, "envAirConditionControllerBinding.tvTemperature");
                                                        envView4.a(textView, string3, EnvView.this.getResources().getColor(R.color.x08l_ac_temperature_start_color, null), EnvView.this.getResources().getColor(R.color.x08l_ac_temperature_end_color, null));
                                                    }
                                                }
                                            }
                                            DevicePropertyBriefInfo prop9 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_MODE);
                                            if (prop9 != null && Intrinsics.areEqual(string2, prop9.getKey())) {
                                                if (prop9.getPropValue() == null) {
                                                    L.smarthome.e("Env: onMsgCallback " + this.$data + " mode propValue is null");
                                                } else {
                                                    DevicePropValue propValue8 = prop9.getPropValue();
                                                    Intrinsics.checkNotNullExpressionValue(propValue8, "this.propValue");
                                                    propValue8.setValue(string3);
                                                    String mode = DeviceExtsKt.getMode(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                                                    if (Intrinsics.areEqual(mode, AirConditionMode.MODE_COLD.getMode())) {
                                                        ImageView imageView = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode;
                                                        Intrinsics.checkNotNullExpressionValue(imageView, "envAirConditionControllerBinding.ivConditionMode");
                                                        imageView.setVisibility(0);
                                                        EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode.setImageResource(R.drawable.sh_ic_air_condition_mode_cold);
                                                    } else if (Intrinsics.areEqual(mode, AirConditionMode.MODE_HOT.getMode())) {
                                                        ImageView imageView2 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode;
                                                        Intrinsics.checkNotNullExpressionValue(imageView2, "envAirConditionControllerBinding.ivConditionMode");
                                                        imageView2.setVisibility(0);
                                                        EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode.setImageResource(R.drawable.sh_ic_air_condition_mode_hot);
                                                    } else if (Intrinsics.areEqual(mode, AirConditionMode.MODE_WIND.getMode())) {
                                                        ImageView imageView3 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode;
                                                        Intrinsics.checkNotNullExpressionValue(imageView3, "envAirConditionControllerBinding.ivConditionMode");
                                                        imageView3.setVisibility(0);
                                                        EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode.setImageResource(R.drawable.sh_ic_air_condition_mode_wind);
                                                    } else {
                                                        L.smarthome.e("Env: ,unkonwn mode ,air-condition did=" + string);
                                                        ImageView imageView4 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivConditionMode;
                                                        Intrinsics.checkNotNullExpressionValue(imageView4, "envAirConditionControllerBinding.ivConditionMode");
                                                        imageView4.setVisibility(8);
                                                    }
                                                }
                                            }
                                            DevicePropertyBriefInfo prop10 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                                            if (!(prop10 == null || !Intrinsics.areEqual(string2, prop10.getKey()) || EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus == (parseBoolean3 = Boolean.parseBoolean(string3)))) {
                                                EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean3;
                                                EnvView.a(EnvView.this, EnvView.access$getDevice$p(EnvView.this), EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this), null, 4, null);
                                            }
                                        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_PURIFIER.getDeviceType())) {
                                            DevicePropertyBriefInfo prop11 = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                                            if (!(prop11 == null || !Intrinsics.areEqual(string2, prop11.getKey()) || EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus == (parseBoolean2 = Boolean.parseBoolean(string3)))) {
                                                EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean2;
                                                EnvView.a(EnvView.this, EnvView.access$getDevice$p(EnvView.this), EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this), null, 4, null);
                                            }
                                        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_FRESH.getDeviceType()) && (prop = MiotDeviceUtils.getProp(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_ON)) != null && Intrinsics.areEqual(string2, prop.getKey()) && EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus != (parseBoolean = Boolean.parseBoolean(string3))) {
                                            EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean;
                                            EnvView.a(EnvView.this, EnvView.access$getDevice$p(EnvView.this), EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this), null, 4, null);
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }

                @Override // com.xiaomi.miot.support.IMiotMsgCallback
                public void onMsgCallback(@Nullable JSONObject jSONObject) {
                    e.a(ViewExtsKt.getViewScope(EnvView.this), null, null, new a(jSONObject, null), 3, null);
                }
            };
        }
    }

    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "viewStub", "Landroid/view/ViewStub;", "kotlin.jvm.PlatformType", "inflated", "Landroid/view/View;", "onInflate"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class d implements ViewStub.OnInflateListener {
        final /* synthetic */ Context b;

        d(Context context) {
            EnvView.this = r1;
            this.b = context;
        }

        @Override // android.view.ViewStub.OnInflateListener
        public final void onInflate(ViewStub viewStub, View view) {
            EnvFanController2Binding envFanController2Binding;
            Bitmap bitmap;
            int i;
            Integer intOrNull;
            Bitmap bitmap2;
            int i2;
            Integer intOrNull2;
            String str;
            ViewDataBinding viewDataBinding = null;
            if (Intrinsics.areEqual(viewStub, EnvView.this.c)) {
                EnvView envView = EnvView.this;
                EnvAirConditionControllerBinding bind = EnvAirConditionControllerBinding.bind(view);
                Intrinsics.checkNotNullExpressionValue(bind, "EnvAirConditionControllerBinding.bind(inflated)");
                envView.l = bind;
                envFanController2Binding = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this);
                TextView textView = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).tvLocation;
                Intrinsics.checkNotNullExpressionValue(textView, "envAirConditionControllerBinding.tvLocation");
                textView.setPaintFlags(Opcodes.INSTANCEOF);
                TextView textView2 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).tvDeviceType;
                Intrinsics.checkNotNullExpressionValue(textView2, "envAirConditionControllerBinding.tvDeviceType");
                textView2.setPaintFlags(Opcodes.INSTANCEOF);
                AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                ImageView imageView = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivStatus;
                Intrinsics.checkNotNullExpressionValue(imageView, "envAirConditionControllerBinding.ivStatus");
                RxView.clicks(imageView).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.1
                    {
                        d.this = this;
                    }

                    /* compiled from: EnvView.kt */
                    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                    /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$1$1 */
                    /* loaded from: classes4.dex */
                    public static final class C01911 extends Lambda implements Function1<Boolean, Unit> {
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        C01911() {
                            super(1);
                            AnonymousClass1.this = r1;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* synthetic */ Unit invoke(Boolean bool) {
                            a(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void a(boolean z) {
                            EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this), Boolean.valueOf(z));
                        }
                    }

                    /* renamed from: a */
                    public final void accept(Unit unit) {
                        OperationUtils.Companion companion = OperationUtils.Companion;
                        DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                        Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                        companion.toggleStatus(deviceInfo, new C01911());
                        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                    }
                });
                EnvView envView2 = EnvView.this;
                TextView textView3 = EnvView.access$getEnvAirConditionControllerBinding$p(envView2).tvTemperature;
                Intrinsics.checkNotNullExpressionValue(textView3, "envAirConditionControllerBinding.tvTemperature");
                Double temperatureValue = DeviceExtsKt.getTemperatureValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                if (temperatureValue == null || (str = String.valueOf(temperatureValue.doubleValue())) == null) {
                    str = "";
                }
                envView2.a(textView3, str, EnvView.this.getResources().getColor(R.color.x08l_ac_temperature_start_color, null), EnvView.this.getResources().getColor(R.color.x08l_ac_temperature_end_color, null));
                AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivIncrease, MicoAnimConfigurator4ControlDevice.get());
                ImageView imageView2 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivIncrease;
                Intrinsics.checkNotNullExpressionValue(imageView2, "envAirConditionControllerBinding.ivIncrease");
                RxView.clicks(imageView2).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.7
                    {
                        d.this = this;
                    }

                    /* renamed from: a */
                    public final void accept(Unit unit) {
                        if (!(!Intrinsics.areEqual(DeviceExtsKt.getMode(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper()), AirConditionMode.MODE_COLD.getMode())) || !(!Intrinsics.areEqual(DeviceExtsKt.getMode(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper()), AirConditionMode.MODE_HOT.getMode()))) {
                            Double temperatureValue2 = DeviceExtsKt.getTemperatureValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            double doubleValue = temperatureValue2 != null ? temperatureValue2.doubleValue() : 0.0d;
                            boolean z = false;
                            ValueRange temperatureRange = DeviceExtsKt.getTemperatureRange(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            if (temperatureRange != null) {
                                String maxValue = temperatureRange.getMaxValue();
                                Intrinsics.checkNotNullExpressionValue(maxValue, "maxValue");
                                if (doubleValue < Double.parseDouble(maxValue)) {
                                    String step = temperatureRange.getStep();
                                    Intrinsics.checkNotNullExpressionValue(step, "step");
                                    doubleValue += Double.parseDouble(step);
                                } else {
                                    z = true;
                                }
                            }
                            if (z) {
                                ToastUtil.showToast(d.this.b.getString(R.string.tip_highest_temperature));
                                return;
                            }
                            String temperatureFormat = DeviceExtsKt.getTemperatureFormat(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            final String exactValue = MiotDeviceUtils.getExactValue(String.valueOf(doubleValue), temperatureFormat);
                            String did = DeviceExtsKt.getDid(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            DevicePropertyBriefInfo targetTempe = DeviceExtsKt.getTargetTempe(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            MiotManager.setDeviceProperty(did, targetTempe != null ? targetTempe.getKey() : null, exactValue, temperatureFormat, new ISendMsgCallback() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.7.1
                                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                                public final void onResult(String str2) {
                                    Logger logger = L.smarthome;
                                    logger.d("Env: ivIncrease temperature change " + PropDic.P_TARGET_TEMP.getProp() + ' ' + exactValue + ' ' + str2);
                                }
                            });
                        } else {
                            ToastUtil.showToast(R.string.air_contidion_can_not_control_tip);
                        }
                        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                    }
                });
                AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivDecrease, MicoAnimConfigurator4ControlDevice.get());
                ImageView imageView3 = EnvView.access$getEnvAirConditionControllerBinding$p(EnvView.this).ivDecrease;
                Intrinsics.checkNotNullExpressionValue(imageView3, "envAirConditionControllerBinding.ivDecrease");
                RxView.clicks(imageView3).throttleFirst(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.8
                    {
                        d.this = this;
                    }

                    /* renamed from: a */
                    public final void accept(Unit unit) {
                        if (!(!Intrinsics.areEqual(DeviceExtsKt.getMode(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper()), AirConditionMode.MODE_COLD.getMode())) || !(!Intrinsics.areEqual(DeviceExtsKt.getMode(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper()), AirConditionMode.MODE_HOT.getMode()))) {
                            Double temperatureValue2 = DeviceExtsKt.getTemperatureValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            double doubleValue = temperatureValue2 != null ? temperatureValue2.doubleValue() : 0.0d;
                            boolean z = false;
                            ValueRange temperatureRange = DeviceExtsKt.getTemperatureRange(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            if (temperatureRange != null) {
                                String minValue = temperatureRange.getMinValue();
                                Intrinsics.checkNotNullExpressionValue(minValue, "minValue");
                                if (doubleValue > Double.parseDouble(minValue)) {
                                    String step = temperatureRange.getStep();
                                    Intrinsics.checkNotNullExpressionValue(step, "step");
                                    doubleValue -= Double.parseDouble(step);
                                } else {
                                    z = true;
                                }
                            }
                            if (z) {
                                ToastUtil.showToast(d.this.b.getString(R.string.tip_lowest_temperature));
                                return;
                            }
                            String temperatureFormat = DeviceExtsKt.getTemperatureFormat(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            final String exactValue = MiotDeviceUtils.getExactValue(String.valueOf(doubleValue), temperatureFormat);
                            String did = DeviceExtsKt.getDid(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            DevicePropertyBriefInfo targetTempe = DeviceExtsKt.getTargetTempe(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            MiotManager.setDeviceProperty(did, targetTempe != null ? targetTempe.getKey() : null, exactValue, temperatureFormat, new ISendMsgCallback() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.8.1
                                @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                                public final void onResult(String str2) {
                                    Logger logger = L.smarthome;
                                    logger.d("Env: ivDecrease temperature change " + PropDic.P_TARGET_TEMP.getProp() + ",targetV=" + exactValue + ' ' + str2);
                                }
                            });
                        } else {
                            ToastUtil.showToast(R.string.air_contidion_can_not_control_tip);
                        }
                        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                    }
                });
            } else if (Intrinsics.areEqual(viewStub, EnvView.this.g)) {
                EnvView envView3 = EnvView.this;
                EnvAirPurifierController1Binding bind2 = EnvAirPurifierController1Binding.bind(view);
                Intrinsics.checkNotNullExpressionValue(bind2, "EnvAirPurifierController1Binding.bind(inflated)");
                envView3.p = bind2;
                envFanController2Binding = EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this);
                TextView textView4 = EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this).tvLocation;
                Intrinsics.checkNotNullExpressionValue(textView4, "envAirFreshController1Binding.tvLocation");
                textView4.setPaintFlags(Opcodes.INSTANCEOF);
                TextView textView5 = EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this).tvDeviceType;
                Intrinsics.checkNotNullExpressionValue(textView5, "envAirFreshController1Binding.tvDeviceType");
                textView5.setPaintFlags(Opcodes.INSTANCEOF);
                AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                ImageView imageView4 = EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this).ivStatus;
                Intrinsics.checkNotNullExpressionValue(imageView4, "envAirFreshController1Binding.ivStatus");
                RxView.clicks(imageView4).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.9
                    {
                        d.this = this;
                    }

                    /* compiled from: EnvView.kt */
                    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                    /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$9$1 */
                    /* loaded from: classes4.dex */
                    public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        AnonymousClass1() {
                            super(1);
                            AnonymousClass9.this = r1;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* synthetic */ Unit invoke(Boolean bool) {
                            a(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void a(boolean z) {
                            EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvAirFreshController1Binding$p(EnvView.this), Boolean.valueOf(z));
                        }
                    }

                    /* renamed from: a */
                    public final void accept(Unit unit) {
                        OperationUtils.Companion companion = OperationUtils.Companion;
                        DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                        Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                        companion.toggleStatus(deviceInfo, new AnonymousClass1());
                        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                    }
                });
            } else if (Intrinsics.areEqual(viewStub, EnvView.this.f)) {
                EnvView envView4 = EnvView.this;
                EnvAirPurifierController1Binding bind3 = EnvAirPurifierController1Binding.bind(view);
                Intrinsics.checkNotNullExpressionValue(bind3, "EnvAirPurifierController1Binding.bind(inflated)");
                envView4.o = bind3;
                envFanController2Binding = EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this);
                TextView textView6 = EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this).tvLocation;
                Intrinsics.checkNotNullExpressionValue(textView6, "envAirPurifierController1Binding.tvLocation");
                textView6.setPaintFlags(Opcodes.INSTANCEOF);
                TextView textView7 = EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this).tvDeviceType;
                Intrinsics.checkNotNullExpressionValue(textView7, "envAirPurifierController1Binding.tvDeviceType");
                textView7.setPaintFlags(Opcodes.INSTANCEOF);
                AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                ImageView imageView5 = EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this).ivStatus;
                Intrinsics.checkNotNullExpressionValue(imageView5, "envAirPurifierController1Binding.ivStatus");
                RxView.clicks(imageView5).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.10
                    {
                        d.this = this;
                    }

                    /* compiled from: EnvView.kt */
                    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                    /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$10$1 */
                    /* loaded from: classes4.dex */
                    public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        AnonymousClass1() {
                            super(1);
                            AnonymousClass10.this = r1;
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* synthetic */ Unit invoke(Boolean bool) {
                            a(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void a(boolean z) {
                            EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvAirPurifierController1Binding$p(EnvView.this), Boolean.valueOf(z));
                        }
                    }

                    /* renamed from: a */
                    public final void accept(Unit unit) {
                        OperationUtils.Companion companion = OperationUtils.Companion;
                        DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                        Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                        companion.toggleStatus(deviceInfo, new AnonymousClass1());
                        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                    }
                });
            } else {
                int i3 = 1;
                if (Intrinsics.areEqual(viewStub, EnvView.this.h)) {
                    EnvView envView5 = EnvView.this;
                    EnvHumidityController1Binding bind4 = EnvHumidityController1Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind4, "EnvHumidityController1Binding.bind(inflated)");
                    envView5.q = bind4;
                    EnvHumidityController1Binding access$getEnvHumidityController1Binding$p = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this);
                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.setCanChoose(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().isOnline && EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus);
                    if (EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().isOnline) {
                        Drawable drawable = ContextCompat.getDrawable(this.b, R.drawable.sh_ic_humidity_on);
                        Intrinsics.checkNotNull(drawable);
                        Intrinsics.checkNotNullExpressionValue(drawable, "ContextCompat.getDrawabl…able.sh_ic_humidity_on)!!");
                        bitmap2 = DrawableKt.toBitmap$default(drawable, FrameLayout.resolveSize(EnvView.this.getResources().getDimensionPixelSize(R.dimen.shift_chooser_icon_size), EnvView.this.getResources().getDimensionPixelSize(R.dimen.shift_chooser_icon_size)), 0, null, 6, null);
                    } else {
                        Drawable drawable2 = ContextCompat.getDrawable(this.b, R.drawable.sh_ic_humidity_off);
                        Intrinsics.checkNotNull(drawable2);
                        Intrinsics.checkNotNullExpressionValue(drawable2, "ContextCompat.getDrawabl…ble.sh_ic_humidity_off)!!");
                        bitmap2 = DrawableKt.toBitmap$default(drawable2, FrameLayout.resolveSize(EnvView.this.getResources().getDimensionPixelSize(R.dimen.shift_chooser_icon_size), EnvView.this.getResources().getDimensionPixelSize(R.dimen.shift_chooser_icon_size)), 0, null, 6, null);
                    }
                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.setImageIcon(bitmap2);
                    if (EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus) {
                        i2 = ContextCompat.getColor(this.b, R.color.humidity_shiftbar_fg);
                    } else {
                        i2 = ContextCompat.getColor(this.b, R.color.humidity_shiftbar_fg_disabled);
                    }
                    EnvHumidityController1Binding envHumidityController1Binding = access$getEnvHumidityController1Binding$p;
                    envHumidityController1Binding.seekView.setTextPaint(R.color.humidifier_text_color, R.dimen.shift_bar_text_size);
                    envHumidityController1Binding.seekView.setShowShiftText(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus);
                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.setForegroundColor(i2);
                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.setShowShiftText(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus);
                    TextView textView8 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView8, "envHumidityController1Binding.tvLocation");
                    textView8.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView9 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView9, "envHumidityController1Binding.tvDeviceType");
                    textView9.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView6 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView6, "envHumidityController1Binding.ivStatus");
                    RxView.clicks(imageView6).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.11
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$11$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass11.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvHumidityController1Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                    Integer totalShifts = MiotDeviceUtils.INSTANCE.getTotalShifts(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                    if (totalShifts != null) {
                        int intValue = totalShifts.intValue();
                        VerticalShiftChooser verticalShiftChooser = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView;
                        Intrinsics.checkNotNullExpressionValue(verticalShiftChooser, "envHumidityController1Binding.seekView");
                        verticalShiftChooser.setTotalShifts(intValue);
                        ArrayList arrayList = new ArrayList();
                        if (1 <= intValue) {
                            while (true) {
                                if (i3 == intValue) {
                                    String string = this.b.getString(R.string.coonstant_humidity);
                                    Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.coonstant_humidity)");
                                    arrayList.add(string);
                                } else {
                                    arrayList.add(i3 + " 档");
                                }
                                if (i3 == intValue) {
                                    break;
                                }
                                i3++;
                            }
                        }
                        VerticalShiftChooser verticalShiftChooser2 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView;
                        Object[] array = arrayList.toArray(new String[0]);
                        if (array != null) {
                            verticalShiftChooser2.setShiftsTitles((String[]) array);
                            Unit unit = Unit.INSTANCE;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                        }
                    }
                    String propValue = MiotDeviceUtils.getPropValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                    if (!(propValue == null || (intOrNull2 = StringsKt.toIntOrNull(propValue)) == null)) {
                        int intValue2 = intOrNull2.intValue();
                        List<Integer> fanLevelArray = MiotDeviceUtils.INSTANCE.getFanLevelArray(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                        if (fanLevelArray.contains(Integer.valueOf(intValue2))) {
                            EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.scrollToShift(fanLevelArray.indexOf(Integer.valueOf(intValue2)), false);
                        }
                        Unit unit2 = Unit.INSTANCE;
                    }
                    EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView.setOnShiftChangedListener(new ShiftChooser.OnShiftChangedListener() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView$onInflateListener$1$9
                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onShiftChanged(@Nullable ShiftChooser shiftChooser, int i4) {
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onStartTrackingTouch() {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalShiftChooser verticalShiftChooser3 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalShiftChooser3, "envHumidityController1Binding.seekView");
                            companion.startHotZoneAnimationForX6A(verticalShiftChooser3, EnvView.this.getResources().getDimensionPixelSize(R.dimen.x08l_vertical_shift_chooser_radius));
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onStopTrackingTouch(@Nullable ShiftChooser shiftChooser, int i4, boolean z) {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalShiftChooser verticalShiftChooser3 = EnvView.access$getEnvHumidityController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalShiftChooser3, "envHumidityController1Binding.seekView");
                            companion.endHotZoneAnimationForX6A(verticalShiftChooser3);
                            EnvView.this.handleStopTrackingTouch(shiftChooser, i4, z);
                        }
                    });
                    envFanController2Binding = access$getEnvHumidityController1Binding$p;
                } else if (Intrinsics.areEqual(viewStub, EnvView.this.i)) {
                    EnvView envView6 = EnvView.this;
                    EnvHumidityController2Binding bind5 = EnvHumidityController2Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind5, "EnvHumidityController2Binding.bind(inflated)");
                    envView6.r = bind5;
                    envFanController2Binding = EnvView.access$getEnvHumidityController2Binding$p(EnvView.this);
                    TextView textView10 = EnvView.access$getEnvHumidityController2Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView10, "envHumidityController2Binding.tvLocation");
                    textView10.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView11 = EnvView.access$getEnvHumidityController2Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView11, "envHumidityController2Binding.tvDeviceType");
                    textView11.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvHumidityController2Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView7 = EnvView.access$getEnvHumidityController2Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView7, "envHumidityController2Binding.ivStatus");
                    RxView.clicks(imageView7).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.2
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$2$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass2.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvHumidityController2Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit3) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                } else if (Intrinsics.areEqual(viewStub, EnvView.this.d)) {
                    EnvView envView7 = EnvView.this;
                    EnvHeaterController1Binding bind6 = EnvHeaterController1Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind6, "EnvHeaterController1Binding.bind(inflated)");
                    envView7.m = bind6;
                    envFanController2Binding = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this);
                    EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView.setVolume(MiotDeviceUtils.value2SeekViewVolume(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_TARGET_TEMP));
                    EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView.setVolumeChangeListener(new OnVolumeChangeListener() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView$onInflateListener$1$11
                        @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                        public void onVolumeChanged(double d) {
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                        public void onVolumeChangedBegin(double d) {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalSeekView verticalSeekView = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalSeekView, "envHeaterController1Binding.seekView");
                            companion.startHotZoneAnimationForX6A(verticalSeekView, EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView.getRadius());
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.OnVolumeChangeListener
                        public void onVolumeChangedEnd(boolean z, double d) {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalSeekView verticalSeekView = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalSeekView, "envHeaterController1Binding.seekView");
                            companion.endHotZoneAnimationForX6A(verticalSeekView);
                            String seekViewVolume2Value = MiotDeviceUtils.INSTANCE.seekViewVolume2Value(d, EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_TARGET_TEMP);
                            String str2 = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().did;
                            DevicePropertyBriefInfo targetTempe = DeviceExtsKt.getTargetTempe(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                            MiotManager.setDeviceProperty(str2, targetTempe != null ? targetTempe.getKey() : null, seekViewVolume2Value, "int", a.a);
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
                        /* loaded from: classes4.dex */
                        static final class a implements ISendMsgCallback {
                            public static final a a = new a();

                            a() {
                            }

                            @Override // com.xiaomi.miot.support.core.ISendMsgCallback
                            public final void onResult(String str) {
                                Logger logger = L.smarthome;
                                logger.d("Env: heater change " + PropDic.P_TARGET_TEMP.getProp() + ' ' + str);
                            }
                        }
                    });
                    TextView textView12 = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView12, "envHeaterController1Binding.tvLocation");
                    textView12.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView13 = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView13, "envHeaterController1Binding.tvDeviceType");
                    textView13.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView8 = EnvView.access$getEnvHeaterController1Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView8, "envHeaterController1Binding.ivStatus");
                    RxView.clicks(imageView8).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.3
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$3$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass3.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvHeaterController1Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit3) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                } else if (Intrinsics.areEqual(viewStub, EnvView.this.e)) {
                    EnvView envView8 = EnvView.this;
                    EnvHeaterController2Binding bind7 = EnvHeaterController2Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind7, "EnvHeaterController2Binding.bind(inflated)");
                    envView8.n = bind7;
                    envFanController2Binding = EnvView.access$getEnvHeaterController2Binding$p(EnvView.this);
                    TextView textView14 = EnvView.access$getEnvHeaterController2Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView14, "envHeaterController2Binding.tvLocation");
                    textView14.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView15 = EnvView.access$getEnvHeaterController2Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView15, "envHeaterController2Binding.tvDeviceType");
                    textView15.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvHeaterController2Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView9 = EnvView.access$getEnvHeaterController2Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView9, "envHeaterController2Binding.ivStatus");
                    RxView.clicks(imageView9).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.4
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$4$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass4.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvHeaterController2Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit3) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                } else if (Intrinsics.areEqual(viewStub, EnvView.this.j)) {
                    EnvView envView9 = EnvView.this;
                    EnvFanController1Binding bind8 = EnvFanController1Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind8, "EnvFanController1Binding.bind(inflated)");
                    envView9.s = bind8;
                    EnvFanController1Binding access$getEnvFanController1Binding$p = EnvView.access$getEnvFanController1Binding$p(EnvView.this);
                    EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.setCanChoose(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().isOnline && EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus);
                    if (EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus) {
                        bitmap = BitmapFactory.decodeResource(EnvView.this.getResources(), R.drawable.sh_ic_fan_on);
                    } else {
                        bitmap = BitmapFactory.decodeResource(EnvView.this.getResources(), R.drawable.sh_ic_fan_off);
                    }
                    EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.setImageIcon(bitmap);
                    if (EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus) {
                        i = ContextCompat.getColor(this.b, R.color.fan_foreground_color_online);
                    } else {
                        i = ContextCompat.getColor(this.b, R.color.fan_foreground_color_offline);
                    }
                    EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.setForegroundColor(i);
                    EnvFanController1Binding envFanController1Binding = access$getEnvFanController1Binding$p;
                    envFanController1Binding.seekView.setTextPaint(R.color.fan_text_color, R.dimen.shift_bar_text_size);
                    envFanController1Binding.seekView.setShowShiftText(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus);
                    Integer totalShifts2 = MiotDeviceUtils.INSTANCE.getTotalShifts(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                    if (totalShifts2 != null) {
                        int intValue3 = totalShifts2.intValue();
                        VerticalShiftChooser verticalShiftChooser3 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView;
                        Intrinsics.checkNotNullExpressionValue(verticalShiftChooser3, "envFanController1Binding.seekView");
                        verticalShiftChooser3.setTotalShifts(intValue3);
                        ArrayList arrayList2 = new ArrayList();
                        if (1 <= intValue3) {
                            while (true) {
                                arrayList2.add(i3 + " 档");
                                if (i3 == intValue3) {
                                    break;
                                }
                                i3++;
                            }
                        }
                        VerticalShiftChooser verticalShiftChooser4 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView;
                        Object[] array2 = arrayList2.toArray(new String[0]);
                        if (array2 != null) {
                            verticalShiftChooser4.setShiftsTitles((String[]) array2);
                            Unit unit3 = Unit.INSTANCE;
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                        }
                    }
                    String propValue2 = MiotDeviceUtils.getPropValue(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL);
                    if (!(propValue2 == null || (intOrNull = StringsKt.toIntOrNull(propValue2)) == null)) {
                        int intValue4 = intOrNull.intValue();
                        List<Integer> fanLevelArray2 = MiotDeviceUtils.INSTANCE.getFanLevelArray(EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper());
                        if (fanLevelArray2.contains(Integer.valueOf(intValue4))) {
                            EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.scrollToShift(fanLevelArray2.indexOf(Integer.valueOf(intValue4)), false);
                        }
                        Unit unit4 = Unit.INSTANCE;
                    }
                    EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView.setOnShiftChangedListener(new ShiftChooser.OnShiftChangedListener() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView$onInflateListener$1$16
                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onShiftChanged(@NotNull ShiftChooser v, int i4) {
                            Intrinsics.checkNotNullParameter(v, "v");
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onStartTrackingTouch() {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalShiftChooser verticalShiftChooser5 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalShiftChooser5, "envFanController1Binding.seekView");
                            companion.startHotZoneAnimationForX6A(verticalShiftChooser5, EnvView.this.getResources().getDimensionPixelSize(R.dimen.x08l_vertical_shift_chooser_radius));
                        }

                        @Override // com.xiaomi.smarthome.ui.widgets.ShiftChooser.OnShiftChangedListener
                        public void onStopTrackingTouch(@Nullable ShiftChooser shiftChooser, int i4, boolean z) {
                            AnimationUtils.Companion companion = AnimationUtils.Companion;
                            VerticalShiftChooser verticalShiftChooser5 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).seekView;
                            Intrinsics.checkNotNullExpressionValue(verticalShiftChooser5, "envFanController1Binding.seekView");
                            companion.endHotZoneAnimationForX6A(verticalShiftChooser5);
                            EnvView.this.handleStopTrackingTouch(shiftChooser, i4, z);
                        }
                    });
                    TextView textView16 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView16, "envFanController1Binding.tvLocation");
                    textView16.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView17 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView17, "envFanController1Binding.tvDeviceType");
                    textView17.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvFanController1Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView10 = EnvView.access$getEnvFanController1Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView10, "envFanController1Binding.ivStatus");
                    RxView.clicks(imageView10).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.5
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$5$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass5.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvFanController1Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit5) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                    envFanController2Binding = access$getEnvFanController1Binding$p;
                } else if (Intrinsics.areEqual(viewStub, EnvView.this.k)) {
                    EnvView envView10 = EnvView.this;
                    EnvFanController2Binding bind9 = EnvFanController2Binding.bind(view);
                    Intrinsics.checkNotNullExpressionValue(bind9, "EnvFanController2Binding.bind(inflated)");
                    envView10.t = bind9;
                    envFanController2Binding = EnvView.access$getEnvFanController2Binding$p(EnvView.this);
                    TextView textView18 = EnvView.access$getEnvFanController2Binding$p(EnvView.this).tvLocation;
                    Intrinsics.checkNotNullExpressionValue(textView18, "envFanController2Binding.tvLocation");
                    textView18.setPaintFlags(Opcodes.INSTANCEOF);
                    TextView textView19 = EnvView.access$getEnvFanController2Binding$p(EnvView.this).tvDeviceType;
                    Intrinsics.checkNotNullExpressionValue(textView19, "envFanController2Binding.tvDeviceType");
                    textView19.setPaintFlags(Opcodes.INSTANCEOF);
                    AnimLifecycleManager.repeatOnAttach(EnvView.access$getEnvFanController2Binding$p(EnvView.this).ivStatus, MicoAnimConfigurator4TabAndSwitch.get());
                    ImageView imageView11 = EnvView.access$getEnvFanController2Binding$p(EnvView.this).ivStatus;
                    Intrinsics.checkNotNullExpressionValue(imageView11, "envFanController2Binding.ivStatus");
                    RxView.clicks(imageView11).throttleFirst(1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Unit>() { // from class: com.xiaomi.smarthome.ui.widgets.environments.EnvView.d.6
                        {
                            d.this = this;
                        }

                        /* compiled from: EnvView.kt */
                        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
                        /* renamed from: com.xiaomi.smarthome.ui.widgets.environments.EnvView$d$6$1 */
                        /* loaded from: classes4.dex */
                        public static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1() {
                                super(1);
                                AnonymousClass6.this = r1;
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* synthetic */ Unit invoke(Boolean bool) {
                                a(bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void a(boolean z) {
                                EnvView.this.a(EnvView.access$getDevice$p(EnvView.this), (DeviceInfoBean) EnvView.access$getEnvFanController2Binding$p(EnvView.this), Boolean.valueOf(z));
                            }
                        }

                        /* renamed from: a */
                        public final void accept(Unit unit5) {
                            OperationUtils.Companion companion = OperationUtils.Companion;
                            DeviceInfo deviceInfo = EnvView.access$getDevice$p(EnvView.this).getDeviceInfoWrapper().getDeviceInfo();
                            Intrinsics.checkNotNullExpressionValue(deviceInfo, "device.deviceInfoWrapper.deviceInfo");
                            companion.toggleStatus(deviceInfo, new AnonymousClass1());
                            SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
                        }
                    });
                } else {
                    envFanController2Binding = viewDataBinding;
                }
            }
            Map map = EnvView.this.v;
            Intrinsics.checkNotNullExpressionValue(viewStub, "viewStub");
            map.put(viewStub, envFanController2Binding);
            EnvView.this.a(viewStub);
            if (envFanController2Binding != null) {
                EnvView envView11 = EnvView.this;
                EnvView.a(envView11, EnvView.access$getDevice$p(envView11), envFanController2Binding, null, 4, null);
            }
        }
    }

    public final void handleStopTrackingTouch(@Nullable ShiftChooser shiftChooser, int i, boolean z) {
        if (z) {
            MiotDeviceUtils miotDeviceUtils = MiotDeviceUtils.INSTANCE;
            DeviceInfoBean deviceInfoBean = this.a;
            if (deviceInfoBean == null) {
                Intrinsics.throwUninitializedPropertyAccessException("device");
            }
            List<Integer> fanLevelArray = miotDeviceUtils.getFanLevelArray(deviceInfoBean.getDeviceInfoWrapper());
            if (i <= fanLevelArray.size() - 1 && i >= 0) {
                DeviceInfoBean deviceInfoBean2 = this.a;
                if (deviceInfoBean2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("device");
                }
                String str = deviceInfoBean2.getDeviceInfoWrapper().getDeviceInfo().did;
                DeviceInfoBean deviceInfoBean3 = this.a;
                if (deviceInfoBean3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("device");
                }
                DevicePropertyBriefInfo fanLevel = DeviceExtsKt.getFanLevel(deviceInfoBean3.getDeviceInfoWrapper());
                MiotManager.setDeviceProperty(str, fanLevel != null ? fanLevel.getKey() : null, String.valueOf(fanLevelArray.get(i).intValue()), "int", new b(i));
            }
        }
        SmartHomeStatHelper.recordSmartTabControlModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "onResult"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b implements ISendMsgCallback {
        final /* synthetic */ int a;

        b(int i) {
            this.a = i;
        }

        @Override // com.xiaomi.miot.support.core.ISendMsgCallback
        public final void onResult(String str) {
            Logger logger = L.smarthome;
            logger.d("Env: fan change " + PropDic.P_FAN_LEVEL.getProp() + ' ' + this.a);
        }
    }

    public static /* synthetic */ void bindData$default(EnvView envView, DeviceInfoBean deviceInfoBean, boolean z, PageLocation pageLocation, List list, String str, ItemTouchHelper itemTouchHelper, RecyclerView.ViewHolder viewHolder, int i, Object obj) {
        envView.bindData(deviceInfoBean, (i & 2) != 0 ? false : z, pageLocation, (i & 8) != 0 ? null : list, str, (i & 32) != 0 ? null : itemTouchHelper, (i & 64) != 0 ? null : viewHolder);
    }

    public final void bindData(@NotNull DeviceInfoBean data, boolean z, @NotNull PageLocation location, @Nullable List<IDevice> list, @NotNull String categoryName, @Nullable ItemTouchHelper itemTouchHelper, @Nullable RecyclerView.ViewHolder viewHolder) {
        ViewStub viewStub;
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        this.a = data;
        this.x = z;
        this.z = location;
        this.y = location == PageLocation.PAGE_SECOND;
        this.b = list;
        this.A = viewHolder;
        this.B = categoryName;
        this.D = itemTouchHelper;
        DeviceInfoBean deviceInfoBean = this.a;
        if (deviceInfoBean == null) {
            Intrinsics.throwUninitializedPropertyAccessException("device");
        }
        ModelInfo modelInfo = deviceInfoBean.getDeviceInfoWrapper().getModelInfo();
        Intrinsics.checkNotNullExpressionValue(modelInfo, "device.deviceInfoWrapper.modelInfo");
        String deviceType = modelInfo.getDeviceType();
        ViewStub viewStub2 = null;
        if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_FRESH.getDeviceType()) || Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_CONDITIONER.getDeviceType()) || Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_AIR_PURIFIER.getDeviceType())) {
            List<ViewStub> list2 = this.w.get(deviceType);
            Intrinsics.checkNotNull(list2);
            viewStub2 = list2.get(0);
        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_HUMIDIFIER.getDeviceType())) {
            DeviceInfoBean deviceInfoBean2 = this.a;
            if (deviceInfoBean2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("device");
            }
            if (MiotDeviceUtils.getProp(deviceInfoBean2.getDeviceInfoWrapper(), PropDic.P_FAN_LEVEL) != null) {
                List<ViewStub> list3 = this.w.get(deviceType);
                Intrinsics.checkNotNull(list3);
                viewStub = list3.get(0);
            } else {
                List<ViewStub> list4 = this.w.get(deviceType);
                Intrinsics.checkNotNull(list4);
                viewStub = list4.get(1);
            }
            viewStub2 = viewStub;
        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_HEATER.getDeviceType())) {
            DeviceItemInfo devicePropInfo = data.getDeviceInfoWrapper().getDevicePropInfo();
            Intrinsics.checkNotNullExpressionValue(devicePropInfo, "data.deviceInfoWrapper.devicePropInfo");
            if (devicePropInfo.getProperties().get(PropDic.P_TARGET_TEMP.getProp()) != null) {
                List<ViewStub> list5 = this.w.get(deviceType);
                Intrinsics.checkNotNull(list5);
                viewStub2 = list5.get(0);
            } else {
                List<ViewStub> list6 = this.w.get(deviceType);
                Intrinsics.checkNotNull(list6);
                viewStub2 = list6.get(1);
            }
        } else if (Intrinsics.areEqual(deviceType, DeviceTypeDic.DT_ENV_FAN.getDeviceType())) {
            DeviceItemInfo devicePropInfo2 = data.getDeviceInfoWrapper().getDevicePropInfo();
            Intrinsics.checkNotNullExpressionValue(devicePropInfo2, "data.deviceInfoWrapper.devicePropInfo");
            if (devicePropInfo2.getProperties().get(PropDic.P_FAN_LEVEL.getProp()) != null) {
                MiotDeviceUtils miotDeviceUtils = MiotDeviceUtils.INSTANCE;
                DeviceInfoBean deviceInfoBean3 = this.a;
                if (deviceInfoBean3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("device");
                }
                Integer totalShifts = miotDeviceUtils.getTotalShifts(deviceInfoBean3.getDeviceInfoWrapper());
                DeviceInfoBean deviceInfoBean4 = this.a;
                if (deviceInfoBean4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("device");
                }
                if (!deviceInfoBean4.getDeviceInfoWrapper().getDeviceInfo().currentStatus || MiotDeviceUtils.INSTANCE.isValidTotalShifts(totalShifts)) {
                    List<ViewStub> list7 = this.w.get(deviceType);
                    Intrinsics.checkNotNull(list7);
                    viewStub2 = list7.get(0);
                } else {
                    List<ViewStub> list8 = this.w.get(deviceType);
                    Intrinsics.checkNotNull(list8);
                    viewStub2 = list8.get(1);
                }
            } else {
                List<ViewStub> list9 = this.w.get(deviceType);
                Intrinsics.checkNotNull(list9);
                viewStub2 = list9.get(1);
            }
        } else {
            L.smarthome.e("Env: 未找到该环境设备对应的布局!!!");
        }
        if (viewStub2 != null && !b(viewStub2)) {
            viewStub2.inflate();
            this.u.put(viewStub2, true);
        }
        ViewDataBinding viewDataBinding = this.v.get(viewStub2);
        if (viewDataBinding != null) {
            a(this, data, viewDataBinding, null, 4, null);
        }
        a(viewStub2);
    }

    public static /* synthetic */ void a(EnvView envView, DeviceInfoBean deviceInfoBean, ViewDataBinding viewDataBinding, Boolean bool, int i, Object obj) {
        if ((i & 4) != 0) {
            bool = null;
        }
        envView.a(deviceInfoBean, (DeviceInfoBean) viewDataBinding, bool);
    }

    public final <T extends ViewDataBinding> void a(DeviceInfoBean deviceInfoBean, T t, Boolean bool) {
        if (!Threads.isMainThread()) {
            Threads.postInMainThread(new f(bool, deviceInfoBean, t));
        } else {
            a(bool, deviceInfoBean, (DeviceInfoBean) t);
        }
    }

    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", "run"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class f implements Runnable {
        final /* synthetic */ Boolean b;
        final /* synthetic */ DeviceInfoBean c;
        final /* synthetic */ ViewDataBinding d;

        f(Boolean bool, DeviceInfoBean deviceInfoBean, ViewDataBinding viewDataBinding) {
            EnvView.this = r1;
            this.b = bool;
            this.c = deviceInfoBean;
            this.d = viewDataBinding;
        }

        @Override // java.lang.Runnable
        public final void run() {
            EnvView.this.a(this.b, this.c, (DeviceInfoBean) this.d);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:188:0x0c2c  */
    /* JADX WARN: Removed duplicated region for block: B:190:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <T extends androidx.databinding.ViewDataBinding> void a(java.lang.Boolean r19, com.xiaomi.smarthome.core.entity.DeviceInfoBean r20, T r21) {
        /*
            Method dump skipped, instructions count: 3129
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.environments.EnvView.a(java.lang.Boolean, com.xiaomi.smarthome.core.entity.DeviceInfoBean, androidx.databinding.ViewDataBinding):void");
    }

    /* compiled from: EnvView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u000e\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u00052\u000e\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\b0\bH\n¢\u0006\u0002\b\t"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/databinding/ViewDataBinding;", ai.aC, "Landroid/view/View;", "kotlin.jvm.PlatformType", "event", "Landroid/view/MotionEvent;", "onTouch"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class e implements View.OnTouchListener {
        e() {
            EnvView.this = r1;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            ItemTouchHelper itemTouchHelper;
            RecyclerView.ViewHolder viewHolder = EnvView.this.A;
            if (viewHolder == null || (itemTouchHelper = EnvView.this.D) == null) {
                return false;
            }
            itemTouchHelper.startDrag(viewHolder);
            return false;
        }
    }

    private final <T extends ViewDataBinding> void a(T t) {
        if (this.z == PageLocation.PAGE_SECOND) {
            MicoAnimationManager.getManager().startScaleLargeAnims(t.getRoot());
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

    public final void a(ViewStub viewStub) {
        View root;
        if (viewStub != null) {
            for (Map.Entry<ViewStub, ViewDataBinding> entry : this.v.entrySet()) {
                ViewDataBinding value = entry.getValue();
                if (!(value == null || (root = value.getRoot()) == null)) {
                    ViewExtKt.visibility(root, Intrinsics.areEqual(entry.getKey(), viewStub));
                }
            }
        }
    }

    private final boolean b(ViewStub viewStub) {
        return this.u.get(viewStub) != null;
    }

    public final void a(TextView textView, String str, int i, int i2) {
        textView.setText(str);
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, textView.getMeasuredHeight(), i, i2, Shader.TileMode.CLAMP);
        TextPaint paint = textView.getPaint();
        Intrinsics.checkNotNullExpressionValue(paint, "view.paint");
        paint.setShader(linearGradient);
    }
}
