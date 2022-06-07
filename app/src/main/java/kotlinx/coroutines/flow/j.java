package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Context.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\u0004\u001a(\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a0\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a$\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a[\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0006\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\t2#\u0010\u0013\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\u00060\u0014¢\u0006\u0002\b\u0015H\u0007¨\u0006\u0016"}, d2 = {"checkFlowContext", "", c.R, "Lkotlin/coroutines/CoroutineContext;", "checkFlowContext$FlowKt__ContextKt", "buffer", "Lkotlinx/coroutines/flow/Flow;", ExifInterface.GPS_DIRECTION_TRUE, "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "cancellable", "conflate", "flowOn", "flowWith", "R", "flowContext", "bufferSize", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class j {
    public static /* synthetic */ Flow a(Flow flow, int i, BufferOverflow bufferOverflow, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        return FlowKt.buffer(flow, i, bufferOverflow);
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow, int i, @NotNull BufferOverflow bufferOverflow) {
        BufferOverflow bufferOverflow2;
        int i2;
        boolean z = true;
        if (i >= 0 || i == -2 || i == -1) {
            if (i == -1 && bufferOverflow != BufferOverflow.SUSPEND) {
                z = false;
            }
            if (z) {
                if (i == -1) {
                    bufferOverflow2 = BufferOverflow.DROP_OLDEST;
                    i2 = 0;
                } else {
                    i2 = i;
                    bufferOverflow2 = bufferOverflow;
                }
                return flow instanceof FusibleFlow ? FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, null, i2, bufferOverflow2, 1, null) : new ChannelFlowOperatorImpl(flow, null, i2, bufferOverflow2, 2, null);
            }
            throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ", Integer.valueOf(i)).toString());
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.4.0, binary compatibility with earlier versions")
    public static final /* synthetic */ Flow a(Flow flow, int i) {
        Flow a;
        a = a(flow, i, null, 2, null);
        return a;
    }

    public static /* synthetic */ Flow a(Flow flow, int i, int i2, Object obj) {
        Flow a;
        if ((i2 & 1) != 0) {
            i = -2;
        }
        a = a(flow, i);
        return a;
    }

    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow) {
        Flow<T> a;
        a = a(flow, -1, null, 2, null);
        return a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Flow<T> a(@NotNull Flow<? extends T> flow, @NotNull CoroutineContext coroutineContext) {
        a(coroutineContext);
        return Intrinsics.areEqual(coroutineContext, EmptyCoroutineContext.INSTANCE) ? flow : flow instanceof FusibleFlow ? FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, coroutineContext, 0, null, 6, null) : new ChannelFlowOperatorImpl(flow, coroutineContext, 0, null, 12, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> Flow<T> b(@NotNull Flow<? extends T> flow) {
        return flow instanceof CancellableFlow ? flow : new b(flow);
    }

    public static /* synthetic */ Flow a(Flow flow, CoroutineContext coroutineContext, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -2;
        }
        return FlowKt.flowWith(flow, coroutineContext, i, function1);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "flowWith is deprecated without replacement, please refer to its KDoc for an explanation")
    @FlowPreview
    @NotNull
    public static final <T, R> Flow<R> a(@NotNull final Flow<? extends T> flow, @NotNull final CoroutineContext coroutineContext, final int i, @NotNull final Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> function1) {
        a(coroutineContext);
        return new Flow<R>() { // from class: kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull final FlowCollector<? super R> flowCollector, @NotNull Continuation<? super Unit> continuation) {
                Flow a;
                Flow a2;
                a = j.a(FlowKt.flowOn(Flow.this, continuation.getContext().minusKey(Job.Key)), i, null, 2, null);
                a2 = j.a(FlowKt.flowOn((Flow) function1.invoke(a), coroutineContext), i, null, 2, null);
                Object collect = a2.collect(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0035: INVOKE  (r5v1 'collect' java.lang.Object) = 
                      (r0v7 'a2' kotlinx.coroutines.flow.Flow)
                      (wrap: kotlinx.coroutines.flow.FlowCollector : 0x0030: CONSTRUCTOR  (r1v7 kotlinx.coroutines.flow.FlowCollector A[REMOVE]) = (r5v0 'flowCollector' kotlinx.coroutines.flow.FlowCollector<? super R> A[DONT_INLINE]) call: kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$lambda-3$$inlined$collect$1.<init>(kotlinx.coroutines.flow.FlowCollector):void type: CONSTRUCTOR)
                      (r6v0 'continuation' kotlin.coroutines.Continuation<? super kotlin.Unit>)
                     type: INTERFACE call: kotlinx.coroutines.flow.Flow.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object in method: kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector<? super R>, kotlin.coroutines.Continuation<? super kotlin.Unit>):java.lang.Object, file: classes5.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:278)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:241)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:90)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:266)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:259)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                    	at java.base/java.util.ArrayList.forEach(Unknown Source)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$lambda-3$$inlined$collect$1, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:297)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:676)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:386)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:140)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:116)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:103)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:996)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:807)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:390)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:271)
                    	... 15 more
                    */
                /*
                    this = this;
                    kotlin.coroutines.CoroutineContext r0 = r6.getContext()
                    kotlinx.coroutines.Job$Key r1 = kotlinx.coroutines.Job.Key
                    kotlin.coroutines.CoroutineContext$Key r1 = (kotlin.coroutines.CoroutineContext.Key) r1
                    kotlin.coroutines.CoroutineContext r0 = r0.minusKey(r1)
                    kotlinx.coroutines.flow.Flow r1 = kotlinx.coroutines.flow.Flow.this
                    kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.flowOn(r1, r0)
                    int r1 = r2
                    r2 = 2
                    r3 = 0
                    kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.buffer$default(r0, r1, r3, r2, r3)
                    kotlin.jvm.functions.Function1 r1 = r3
                    java.lang.Object r0 = r1.invoke(r0)
                    kotlinx.coroutines.flow.Flow r0 = (kotlinx.coroutines.flow.Flow) r0
                    kotlin.coroutines.CoroutineContext r1 = r4
                    kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.flowOn(r0, r1)
                    int r1 = r2
                    kotlinx.coroutines.flow.Flow r0 = kotlinx.coroutines.flow.FlowKt.buffer$default(r0, r1, r3, r2, r3)
                    kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$lambda-3$$inlined$collect$1 r1 = new kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$lambda-3$$inlined$collect$1
                    r1.<init>(r5)
                    kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                    java.lang.Object r5 = r0.collect(r1, r6)
                    java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    if (r5 != r6) goto L_0x0040
                    return r5
                L_0x0040:
                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ContextKt$flowWith$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
            }
        };
    }

    private static final void a(CoroutineContext coroutineContext) {
        if (!(coroutineContext.get(Job.Key) == null)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Flow context cannot contain job in it. Had ", coroutineContext).toString());
        }
    }
}
