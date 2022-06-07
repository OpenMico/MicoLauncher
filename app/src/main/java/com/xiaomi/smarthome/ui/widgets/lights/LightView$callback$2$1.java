package com.xiaomi.smarthome.ui.widgets.lights;

import androidx.databinding.ViewDataBinding;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.category.DevicePropValue;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.exts.ViewExtsKt;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import com.xiaomi.smarthome.databinding.LightController1Binding;
import com.xiaomi.smarthome.ui.widgets.VerticalSeekView;
import com.xiaomi.smarthome.ui.widgets.lights.LightView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: LightView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\b"}, d2 = {"com/xiaomi/smarthome/ui/widgets/lights/LightView$callback$2$1", "Lcom/xiaomi/miot/support/IMiotMsgCallback;", "getAction", "", "onMsgCallback", "", "data", "Lorg/json/JSONObject;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class LightView$callback$2$1 implements IMiotMsgCallback {
    final /* synthetic */ LightView.a a;

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public int getAction() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LightView$callback$2$1(LightView.a aVar) {
        this.a = aVar;
    }

    /* compiled from: LightView.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.ui.widgets.lights.LightView$callback$2$1$onMsgCallback$1", f = "LightView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
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
            boolean parseBoolean;
            VerticalSeekView verticalSeekView;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                JSONObject jSONObject = this.$data;
                if (jSONObject != null) {
                    if (!jSONObject.has("did") || !jSONObject.has("prop") || !jSONObject.has(b.p)) {
                        Logger logger = L.smarthome;
                        logger.e("Light: onMsgCallback " + this.$data);
                    } else {
                        String string = jSONObject.getString("did");
                        if (Intrinsics.areEqual(string, LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo().did)) {
                            Logger logger2 = L.smarthome;
                            logger2.d("Light: onMsgCallback " + this.$data);
                            String string2 = jSONObject.getString("prop");
                            boolean z = false;
                            String str = null;
                            if (string2 != null) {
                                str = StringsKt.startsWith$default(string2, "prop.", false, 2, (Object) null) ? StringsKt.replace$default(StringsKt.replace$default(string2, "prop.", "pi:", false, 4, (Object) null), ".", Constants.COLON_SEPARATOR, false, 4, (Object) null) : string2;
                            }
                            String string3 = jSONObject.getString(b.p);
                            DevicePropertyBriefInfo prop = MiotDeviceUtils.getProp(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper(), PropDic.P_BRIGHTNESS);
                            if (prop != null && Intrinsics.areEqual(str, prop.getKey())) {
                                if (prop.getPropValue() == null) {
                                    Logger logger3 = L.smarthome;
                                    logger3.e("Light: onMsgCallback light(did=" + string + ") brightness propValue is null");
                                } else {
                                    DevicePropValue propValue = prop.getPropValue();
                                    Intrinsics.checkNotNullExpressionValue(propValue, "propValue");
                                    propValue.setValue(string3);
                                    if (!Threads.isMainThread()) {
                                        Threads.postInMainThread(new RunnableC0196a(str, string, string3, this));
                                    } else {
                                        LightController1Binding lightController1Binding = LightView.this.e;
                                        if (!(lightController1Binding == null || (verticalSeekView = lightController1Binding.seekView) == null)) {
                                            verticalSeekView.updateVolume(MiotDeviceUtils.value2SeekViewVolume(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper(), PropDic.P_BRIGHTNESS));
                                        }
                                    }
                                }
                            }
                            DevicePropertyBriefInfo prop2 = MiotDeviceUtils.getProp(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper(), PropDic.P_ON);
                            if (!(prop2 == null || !Intrinsics.areEqual(str, prop2.getKey()) || LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus == (parseBoolean = Boolean.parseBoolean(string3)))) {
                                LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper().getDeviceInfo().currentStatus = parseBoolean;
                                if (prop != null) {
                                    z = true;
                                }
                                LightView lightView = LightView.this;
                                DeviceInfoBean access$getDevice$p = LightView.access$getDevice$p(LightView.this);
                                ViewDataBinding viewDataBinding = z ? LightView.this.e : LightView.this.f;
                                Intrinsics.checkNotNull(viewDataBinding);
                                LightView.a(lightView, access$getDevice$p, viewDataBinding, null, 4, null);
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: LightView.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0004"}, d2 = {"<anonymous>", "", "run", "com/xiaomi/smarthome/ui/widgets/lights/LightView$callback$2$1$onMsgCallback$1$1$1$1", "com/xiaomi/smarthome/ui/widgets/lights/LightView$callback$2$1$onMsgCallback$1$$special$$inlined$apply$lambda$1"}, k = 3, mv = {1, 4, 2})
        /* renamed from: com.xiaomi.smarthome.ui.widgets.lights.LightView$callback$2$1$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public static final class RunnableC0196a implements Runnable {
            final /* synthetic */ String a;
            final /* synthetic */ String b;
            final /* synthetic */ String c;
            final /* synthetic */ a d;

            RunnableC0196a(String str, String str2, String str3, a aVar) {
                this.a = str;
                this.b = str2;
                this.c = str3;
                this.d = aVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                VerticalSeekView verticalSeekView;
                LightController1Binding lightController1Binding = LightView.this.e;
                if (lightController1Binding != null && (verticalSeekView = lightController1Binding.seekView) != null) {
                    verticalSeekView.updateVolume(MiotDeviceUtils.value2SeekViewVolume(LightView.access$getDevice$p(LightView.this).getDeviceInfoWrapper(), PropDic.P_BRIGHTNESS));
                }
            }
        }
    }

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public void onMsgCallback(@Nullable JSONObject jSONObject) {
        e.a(ViewExtsKt.getViewScope(LightView.this), null, null, new a(jSONObject, null), 3, null);
    }
}
