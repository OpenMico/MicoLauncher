package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Collection.kt */
@Metadata(d1 = {"\u0000(\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010#\n\u0002\b\u0002\u001a;\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0010\b\u0001\u0010\u0001*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u0001H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a7\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a7\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\rH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, d2 = {"toCollection", HomePageRecordHelper.AREA_C, ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlinx/coroutines/flow/Flow;", RtspHeaders.Values.DESTINATION, "(Lkotlinx/coroutines/flow/Flow;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "", "(Lkotlinx/coroutines/flow/Flow;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toSet", "", "", "(Lkotlinx/coroutines/flow/Flow;Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class i {

    /* compiled from: Collection.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__CollectionKt", f = "Collection.kt", i = {0}, l = {32}, m = "toCollection", n = {RtspHeaders.Values.DESTINATION}, s = {"L$0"})
    /* loaded from: classes5.dex */
    static final class a<T, C extends Collection<? super T>> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        a(Continuation<? super a> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt.toCollection(null, null, this);
        }
    }

    @Nullable
    public static final <T> Object a(@NotNull Flow<? extends T> flow, @NotNull List<T> list, @NotNull Continuation<? super List<? extends T>> continuation) {
        return FlowKt.toCollection(flow, list, continuation);
    }

    public static /* synthetic */ Object a(Flow flow, List list, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            list = new ArrayList();
        }
        return FlowKt.toList(flow, list, continuation);
    }

    @Nullable
    public static final <T> Object a(@NotNull Flow<? extends T> flow, @NotNull Set<T> set, @NotNull Continuation<? super Set<? extends T>> continuation) {
        return FlowKt.toCollection(flow, set, continuation);
    }

    public static /* synthetic */ Object a(Flow flow, Set set, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            set = new LinkedHashSet();
        }
        return FlowKt.toSet(flow, set, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0035  */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T, C extends java.util.Collection<? super T>> java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.Flow<? extends T> r3, @org.jetbrains.annotations.NotNull final C r4, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super C> r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.i.a
            if (r0 == 0) goto L_0x0014
            r0 = r5
            kotlinx.coroutines.flow.i$a r0 = (kotlinx.coroutines.flow.i.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.i$a r0 = new kotlinx.coroutines.flow.i$a
            r0.<init>(r5)
        L_0x0019:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0035;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x002c:
            java.lang.Object r3 = r0.L$0
            r4 = r3
            java.util.Collection r4 = (java.util.Collection) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x004b
        L_0x0035:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$$inlined$collect$1 r5 = new kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$$inlined$collect$1
            r5.<init>()
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            r0.L$0 = r4
            r2 = 1
            r0.label = r2
            java.lang.Object r3 = r3.collect(r5, r0)
            if (r3 != r1) goto L_0x004b
            return r1
        L_0x004b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.i.a(kotlinx.coroutines.flow.Flow, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
