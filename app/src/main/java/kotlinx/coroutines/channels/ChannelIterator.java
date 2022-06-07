package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Channel.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u0011\u0010\u0003\u001a\u00020\u0004H¦Bø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0007J\u0013\u0010\b\u001a\u00028\u0000H\u0097@ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/channels/ChannelIterator;", "E", "", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "next", "()Ljava/lang/Object;", "next0", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public interface ChannelIterator<E> {
    @Nullable
    Object hasNext(@NotNull Continuation<? super Boolean> continuation);

    E next();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
    @JvmName(name = "next")
    /* synthetic */ Object next(Continuation continuation);

    /* compiled from: Channel.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class DefaultImpls {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: Channel.kt */
        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelIterator$DefaultImpls", f = "Channel.kt", i = {0}, l = {579}, m = "next", n = {"this"}, s = {"L$0"})
        /* loaded from: classes5.dex */
        public static final class a<E> extends ContinuationImpl {
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
                return DefaultImpls.next(null, this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
        /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0050  */
        @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        @kotlin.jvm.JvmName(name = "next")
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static /* synthetic */ java.lang.Object next(kotlinx.coroutines.channels.ChannelIterator r3, kotlin.coroutines.Continuation r4) {
            /*
                boolean r0 = r4 instanceof kotlinx.coroutines.channels.ChannelIterator.DefaultImpls.a
                if (r0 == 0) goto L_0x0014
                r0 = r4
                kotlinx.coroutines.channels.ChannelIterator$DefaultImpls$a r0 = (kotlinx.coroutines.channels.ChannelIterator.DefaultImpls.a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r4 = r0.label
                int r4 = r4 - r2
                r0.label = r4
                goto L_0x0019
            L_0x0014:
                kotlinx.coroutines.channels.ChannelIterator$DefaultImpls$a r0 = new kotlinx.coroutines.channels.ChannelIterator$DefaultImpls$a
                r0.<init>(r4)
            L_0x0019:
                java.lang.Object r4 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                switch(r2) {
                    case 0: goto L_0x0034;
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
                kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
                kotlin.ResultKt.throwOnFailure(r4)
                goto L_0x0043
            L_0x0034:
                kotlin.ResultKt.throwOnFailure(r4)
                r0.L$0 = r3
                r4 = 1
                r0.label = r4
                java.lang.Object r4 = r3.hasNext(r0)
                if (r4 != r1) goto L_0x0043
                return r1
            L_0x0043:
                java.lang.Boolean r4 = (java.lang.Boolean) r4
                boolean r4 = r4.booleanValue()
                if (r4 == 0) goto L_0x0050
                java.lang.Object r3 = r3.next()
                return r3
            L_0x0050:
                kotlinx.coroutines.channels.ClosedReceiveChannelException r3 = new kotlinx.coroutines.channels.ClosedReceiveChannelException
                java.lang.String r4 = "Channel was closed"
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelIterator.DefaultImpls.next(kotlinx.coroutines.channels.ChannelIterator, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
