package androidx.room;

import android.os.Build;
import android.os.CancellationSignal;
import androidx.annotation.RestrictTo;
import androidx.room.InvalidationTracker;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.e;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutinesRoom.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/room/CoroutinesRoom;", "", "()V", "Companion", "room-ktx_release"}, k = 1, mv = {1, 4, 2})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class CoroutinesRoom {
    @NotNull
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    @NotNull
    public static final <R> Flow<R> createFlow(@NotNull RoomDatabase roomDatabase, boolean z, @NotNull String[] strArr, @NotNull Callable<R> callable) {
        return Companion.createFlow(roomDatabase, z, strArr, callable);
    }

    @JvmStatic
    @Nullable
    public static final <R> Object execute(@NotNull RoomDatabase roomDatabase, boolean z, @NotNull CancellationSignal cancellationSignal, @NotNull Callable<R> callable, @NotNull Continuation<? super R> continuation) {
        return Companion.execute(roomDatabase, z, cancellationSignal, callable, continuation);
    }

    @JvmStatic
    @Nullable
    public static final <R> Object execute(@NotNull RoomDatabase roomDatabase, boolean z, @NotNull Callable<R> callable, @NotNull Continuation<? super R> continuation) {
        return Companion.execute(roomDatabase, z, callable, continuation);
    }

    /* compiled from: CoroutinesRoom.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\r\u0012\t\u0012\u0007H\u0005¢\u0006\u0002\b\u00060\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000fH\u0007¢\u0006\u0002\u0010\u0010J=\u0010\u0011\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000fH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0014J5\u0010\u0011\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000fH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, d2 = {"Landroidx/room/CoroutinesRoom$Companion;", "", "()V", "createFlow", "Lkotlinx/coroutines/flow/Flow;", "R", "Lkotlin/jvm/JvmSuppressWildcards;", "db", "Landroidx/room/RoomDatabase;", "inTransaction", "", "tableNames", "", "", "callable", "Ljava/util/concurrent/Callable;", "(Landroidx/room/RoomDatabase;Z[Ljava/lang/String;Ljava/util/concurrent/Callable;)Lkotlinx/coroutines/flow/Flow;", "execute", "cancellationSignal", "Landroid/os/CancellationSignal;", "(Landroidx/room/RoomDatabase;ZLandroid/os/CancellationSignal;Ljava/util/concurrent/Callable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Landroidx/room/RoomDatabase;ZLjava/util/concurrent/Callable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "room-ktx_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes.dex */
    public static final class Companion {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CoroutinesRoom.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0002\b\u0005¨\u0006\u0006"}, d2 = {"<anonymous>", "", "R", "it", "", "invoke", "androidx/room/CoroutinesRoom$Companion$execute$4$1"}, k = 3, mv = {1, 4, 2})
        /* loaded from: classes.dex */
        public static final class c extends Lambda implements Function1<Throwable, Unit> {
            final /* synthetic */ Callable $callable$inlined;
            final /* synthetic */ CancellationSignal $cancellationSignal$inlined;
            final /* synthetic */ ContinuationInterceptor $context$inlined;
            final /* synthetic */ Job $job;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            c(Job job, ContinuationInterceptor continuationInterceptor, Callable callable, CancellationSignal cancellationSignal) {
                super(1);
                this.$job = job;
                this.$context$inlined = continuationInterceptor;
                this.$callable$inlined = callable;
                this.$cancellationSignal$inlined = cancellationSignal;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(Throwable th) {
                a(th);
                return Unit.INSTANCE;
            }

            public final void a(@Nullable Throwable th) {
                if (Build.VERSION.SDK_INT >= 16) {
                    this.$cancellationSignal$inlined.cancel();
                }
                Job.DefaultImpls.cancel$default(this.$job, (CancellationException) null, 1, (Object) null);
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @Nullable
        public final <R> Object execute(@NotNull RoomDatabase roomDatabase, boolean z, @NotNull Callable<R> callable, @NotNull Continuation<? super R> continuation) {
            CoroutineDispatcher coroutineDispatcher;
            if (roomDatabase.isOpen() && roomDatabase.inTransaction()) {
                return callable.call();
            }
            TransactionElement transactionElement = (TransactionElement) continuation.getContext().get(TransactionElement.Key);
            if (transactionElement == null || (coroutineDispatcher = transactionElement.getTransactionDispatcher$room_ktx_release()) == null) {
                coroutineDispatcher = z ? CoroutinesRoomKt.getTransactionDispatcher(roomDatabase) : CoroutinesRoomKt.getQueryDispatcher(roomDatabase);
            }
            return BuildersKt.withContext(coroutineDispatcher, new d(callable, null), continuation);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CoroutinesRoom.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "R", "kotlin.jvm.PlatformType", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$execute$2", f = "CoroutinesRoom.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes.dex */
        public static final class d extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super R>, Object> {
            final /* synthetic */ Callable $callable;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            d(Callable callable, Continuation continuation) {
                super(2, continuation);
                this.$callable = callable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return new d(this.$callable, completion);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Object obj) {
                return ((d) create(coroutineScope, (Continuation) obj)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    return this.$callable.call();
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        @JvmStatic
        @Nullable
        public final <R> Object execute(@NotNull RoomDatabase roomDatabase, boolean z, @NotNull CancellationSignal cancellationSignal, @NotNull Callable<R> callable, @NotNull Continuation<? super R> continuation) {
            CoroutineDispatcher coroutineDispatcher;
            Job a2;
            if (roomDatabase.isOpen() && roomDatabase.inTransaction()) {
                return callable.call();
            }
            TransactionElement transactionElement = (TransactionElement) continuation.getContext().get(TransactionElement.Key);
            if (transactionElement == null || (coroutineDispatcher = transactionElement.getTransactionDispatcher$room_ktx_release()) == null) {
                coroutineDispatcher = z ? CoroutinesRoomKt.getTransactionDispatcher(roomDatabase) : CoroutinesRoomKt.getQueryDispatcher(roomDatabase);
            }
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
            a2 = e.a(GlobalScope.INSTANCE, coroutineDispatcher, null, new b(cancellableContinuationImpl2, null, coroutineDispatcher, callable, cancellationSignal), 2, null);
            cancellableContinuationImpl2.invokeOnCancellation(new c(a2, coroutineDispatcher, callable, cancellationSignal));
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CoroutinesRoom.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "androidx/room/CoroutinesRoom$Companion$execute$4$job$1"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$execute$4$job$1", f = "CoroutinesRoom.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes.dex */
        public static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Callable $callable$inlined;
            final /* synthetic */ CancellationSignal $cancellationSignal$inlined;
            final /* synthetic */ ContinuationInterceptor $context$inlined;
            final /* synthetic */ CancellableContinuation $continuation;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            b(CancellableContinuation cancellableContinuation, Continuation continuation, ContinuationInterceptor continuationInterceptor, Callable callable, CancellationSignal cancellationSignal) {
                super(2, continuation);
                this.$continuation = cancellableContinuation;
                this.$context$inlined = continuationInterceptor;
                this.$callable$inlined = callable;
                this.$cancellationSignal$inlined = cancellationSignal;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return new b(this.$continuation, completion, this.$context$inlined, this.$callable$inlined, this.$cancellationSignal$inlined);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    try {
                        Object call = this.$callable$inlined.call();
                        Result.Companion companion = Result.Companion;
                        this.$continuation.resumeWith(Result.m1220constructorimpl(call));
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        this.$continuation.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(th)));
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: CoroutinesRoom.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0010\u0012\f\u0012\n \u0004*\u0004\u0018\u0001H\u0002H\u00020\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1", f = "CoroutinesRoom.kt", i = {}, l = {116}, m = "invokeSuspend", n = {}, s = {})
        /* loaded from: classes.dex */
        public static final class a extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
            final /* synthetic */ Callable $callable;
            final /* synthetic */ RoomDatabase $db;
            final /* synthetic */ boolean $inTransaction;
            final /* synthetic */ String[] $tableNames;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(String[] strArr, boolean z, RoomDatabase roomDatabase, Callable callable, Continuation continuation) {
                super(2, continuation);
                this.$tableNames = strArr;
                this.$inTransaction = z;
                this.$db = roomDatabase;
                this.$callable = callable;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                a aVar = new a(this.$tableNames, this.$inTransaction, this.$db, this.$callable, completion);
                aVar.L$0 = obj;
                return aVar;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Continuation<? super Unit> continuation) {
                return ((a) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r10v4, types: [T, androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1] */
            /* JADX WARN: Type inference failed for: r10v6, types: [T, kotlin.coroutines.CoroutineContext] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                CoroutineDispatcher coroutineDispatcher;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        final Channel Channel$default = ChannelKt.Channel$default(-1, null, null, 6, null);
                        Ref.ObjectRef objectRef = new Ref.ObjectRef();
                        final String[] strArr = this.$tableNames;
                        objectRef.element = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1
                            @Override // androidx.room.InvalidationTracker.Observer
                            public void onInvalidated(@NotNull Set<String> tables) {
                                Intrinsics.checkNotNullParameter(tables, "tables");
                                Channel$default.offer(Unit.INSTANCE);
                            }
                        };
                        Channel$default.offer(Unit.INSTANCE);
                        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                        objectRef2.element = getContext();
                        TransactionElement transactionElement = (TransactionElement) getContext().get(TransactionElement.Key);
                        if (transactionElement == null || (coroutineDispatcher = transactionElement.getTransactionDispatcher$room_ktx_release()) == null) {
                            coroutineDispatcher = this.$inTransaction ? CoroutinesRoomKt.getTransactionDispatcher(this.$db) : CoroutinesRoomKt.getQueryDispatcher(this.$db);
                        }
                        this.label = 1;
                        if (BuildersKt.withContext(coroutineDispatcher, new AnonymousClass1(flowCollector, objectRef, Channel$default, objectRef2, null), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        break;
                    case 1:
                        ResultKt.throwOnFailure(obj);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return Unit.INSTANCE;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* compiled from: CoroutinesRoom.kt */
            @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
            @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1$1", f = "CoroutinesRoom.kt", i = {}, l = {121, 123}, m = "invokeSuspend", n = {}, s = {})
            /* renamed from: androidx.room.CoroutinesRoom$Companion$a$1  reason: invalid class name */
            /* loaded from: classes.dex */
            public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Ref.ObjectRef $flowContext;
                final /* synthetic */ Ref.ObjectRef $observer;
                final /* synthetic */ Channel $observerChannel;
                final /* synthetic */ FlowCollector $this_flow;
                Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(FlowCollector flowCollector, Ref.ObjectRef objectRef, Channel channel, Ref.ObjectRef objectRef2, Continuation continuation) {
                    super(2, continuation);
                    this.$this_flow = flowCollector;
                    this.$observer = objectRef;
                    this.$observerChannel = channel;
                    this.$flowContext = objectRef2;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @NotNull
                public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                    Intrinsics.checkNotNullParameter(completion, "completion");
                    return new AnonymousClass1(this.$this_flow, this.$observer, this.$observerChannel, this.$flowContext, completion);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:17:0x004f A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x005c A[Catch: all -> 0x00a4, TRY_LEAVE, TryCatch #0 {all -> 0x00a4, blocks: (B:15:0x0044, B:19:0x0054, B:21:0x005c), top: B:32:0x0044 }] */
                /* JADX WARN: Removed duplicated region for block: B:25:0x008e  */
                /* JADX WARN: Type inference failed for: r3v2, types: [T, java.lang.Object] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
                    /*
                        r7 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r1 = r7.label
                        switch(r1) {
                            case 0: goto L_0x0026;
                            case 1: goto L_0x001c;
                            case 2: goto L_0x0011;
                            default: goto L_0x0009;
                        }
                    L_0x0009:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r0)
                        throw r8
                    L_0x0011:
                        java.lang.Object r1 = r7.L$0
                        kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                        kotlin.ResultKt.throwOnFailure(r8)     // Catch: all -> 0x00a6
                        r8 = r1
                        r1 = r0
                        r0 = r7
                        goto L_0x0044
                    L_0x001c:
                        java.lang.Object r1 = r7.L$0
                        kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                        kotlin.ResultKt.throwOnFailure(r8)     // Catch: all -> 0x00a6
                        r2 = r0
                        r0 = r7
                        goto L_0x0054
                    L_0x0026:
                        kotlin.ResultKt.throwOnFailure(r8)
                        androidx.room.CoroutinesRoom$Companion$a r8 = androidx.room.CoroutinesRoom.Companion.a.this
                        androidx.room.RoomDatabase r8 = r8.$db
                        androidx.room.InvalidationTracker r8 = r8.getInvalidationTracker()
                        kotlin.jvm.internal.Ref$ObjectRef r1 = r7.$observer
                        T r1 = r1.element
                        androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r1 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r1
                        androidx.room.InvalidationTracker$Observer r1 = (androidx.room.InvalidationTracker.Observer) r1
                        r8.addObserver(r1)
                        kotlinx.coroutines.channels.Channel r8 = r7.$observerChannel     // Catch: all -> 0x00a6
                        kotlinx.coroutines.channels.ChannelIterator r8 = r8.iterator()     // Catch: all -> 0x00a6
                        r1 = r0
                        r0 = r7
                    L_0x0044:
                        r0.L$0 = r8     // Catch: all -> 0x00a4
                        r2 = 1
                        r0.label = r2     // Catch: all -> 0x00a4
                        java.lang.Object r2 = r8.hasNext(r0)     // Catch: all -> 0x00a4
                        if (r2 != r1) goto L_0x0050
                        return r1
                    L_0x0050:
                        r6 = r1
                        r1 = r8
                        r8 = r2
                        r2 = r6
                    L_0x0054:
                        java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: all -> 0x00a4
                        boolean r8 = r8.booleanValue()     // Catch: all -> 0x00a4
                        if (r8 == 0) goto L_0x008e
                        java.lang.Object r8 = r1.next()     // Catch: all -> 0x00a4
                        kotlin.Unit r8 = (kotlin.Unit) r8     // Catch: all -> 0x00a4
                        kotlin.jvm.internal.Ref$ObjectRef r8 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: all -> 0x00a4
                        r8.<init>()     // Catch: all -> 0x00a4
                        androidx.room.CoroutinesRoom$Companion$a r3 = androidx.room.CoroutinesRoom.Companion.a.this     // Catch: all -> 0x00a4
                        java.util.concurrent.Callable r3 = r3.$callable     // Catch: all -> 0x00a4
                        java.lang.Object r3 = r3.call()     // Catch: all -> 0x00a4
                        r8.element = r3     // Catch: all -> 0x00a4
                        kotlin.jvm.internal.Ref$ObjectRef r3 = r0.$flowContext     // Catch: all -> 0x00a4
                        T r3 = r3.element     // Catch: all -> 0x00a4
                        kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3     // Catch: all -> 0x00a4
                        androidx.room.CoroutinesRoom$Companion$a$1$1 r4 = new androidx.room.CoroutinesRoom$Companion$a$1$1     // Catch: all -> 0x00a4
                        r5 = 0
                        r4.<init>(r8, r5)     // Catch: all -> 0x00a4
                        kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4     // Catch: all -> 0x00a4
                        r0.L$0 = r1     // Catch: all -> 0x00a4
                        r8 = 2
                        r0.label = r8     // Catch: all -> 0x00a4
                        java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r0)     // Catch: all -> 0x00a4
                        if (r8 != r2) goto L_0x008b
                        return r2
                    L_0x008b:
                        r8 = r1
                        r1 = r2
                        goto L_0x0044
                    L_0x008e:
                        androidx.room.CoroutinesRoom$Companion$a r8 = androidx.room.CoroutinesRoom.Companion.a.this
                        androidx.room.RoomDatabase r8 = r8.$db
                        androidx.room.InvalidationTracker r8 = r8.getInvalidationTracker()
                        kotlin.jvm.internal.Ref$ObjectRef r0 = r0.$observer
                        T r0 = r0.element
                        androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r0 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r0
                        androidx.room.InvalidationTracker$Observer r0 = (androidx.room.InvalidationTracker.Observer) r0
                        r8.removeObserver(r0)
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    L_0x00a4:
                        r8 = move-exception
                        goto L_0x00a8
                    L_0x00a6:
                        r8 = move-exception
                        r0 = r7
                    L_0x00a8:
                        androidx.room.CoroutinesRoom$Companion$a r1 = androidx.room.CoroutinesRoom.Companion.a.this
                        androidx.room.RoomDatabase r1 = r1.$db
                        androidx.room.InvalidationTracker r1 = r1.getInvalidationTracker()
                        kotlin.jvm.internal.Ref$ObjectRef r0 = r0.$observer
                        T r0 = r0.element
                        androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r0 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r0
                        androidx.room.InvalidationTracker$Observer r0 = (androidx.room.InvalidationTracker.Observer) r0
                        r1.removeObserver(r0)
                        throw r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.room.CoroutinesRoom.Companion.a.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                /* compiled from: CoroutinesRoom.kt */
                @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
                @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1$1$1", f = "CoroutinesRoom.kt", i = {}, l = {123}, m = "invokeSuspend", n = {}, s = {})
                /* renamed from: androidx.room.CoroutinesRoom$Companion$a$1$1  reason: invalid class name and collision with other inner class name */
                /* loaded from: classes.dex */
                public static final class C00261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    final /* synthetic */ Ref.ObjectRef $result;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    C00261(Ref.ObjectRef objectRef, Continuation continuation) {
                        super(2, continuation);
                        this.$result = objectRef;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @NotNull
                    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                        Intrinsics.checkNotNullParameter(completion, "completion");
                        return new C00261(this.$result, completion);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((C00261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        switch (this.label) {
                            case 0:
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = AnonymousClass1.this.$this_flow;
                                T t = this.$result.element;
                                this.label = 1;
                                if (flowCollector.emit(t, this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                break;
                            case 1:
                                ResultKt.throwOnFailure(obj);
                                break;
                            default:
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        return Unit.INSTANCE;
                    }
                }
            }
        }

        @JvmStatic
        @NotNull
        public final <R> Flow<R> createFlow(@NotNull RoomDatabase db, boolean z, @NotNull String[] tableNames, @NotNull Callable<R> callable) {
            Intrinsics.checkNotNullParameter(db, "db");
            Intrinsics.checkNotNullParameter(tableNames, "tableNames");
            Intrinsics.checkNotNullParameter(callable, "callable");
            return FlowKt.flow(new a(tableNames, z, db, callable, null));
        }
    }

    private CoroutinesRoom() {
    }
}
