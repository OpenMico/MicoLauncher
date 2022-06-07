package androidx.room;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RoomDatabase.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001a9\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\u00020\b2\u001c\u0010\f\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\rH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"acquireTransactionThread", "Lkotlin/coroutines/ContinuationInterceptor;", "Ljava/util/concurrent/Executor;", "controlJob", "Lkotlinx/coroutines/Job;", "(Ljava/util/concurrent/Executor;Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createTransactionContext", "Lkotlin/coroutines/CoroutineContext;", "Landroidx/room/RoomDatabase;", "(Landroidx/room/RoomDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withTransaction", "R", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Landroidx/room/RoomDatabase;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "room-ktx_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes.dex */
public final class RoomDatabaseKt {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0082@"}, d2 = {"createTransactionContext", "", "Landroidx/room/RoomDatabase;", "continuation", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/CoroutineContext;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "androidx.room.RoomDatabaseKt", f = "RoomDatabase.kt", i = {0, 0}, l = {99}, m = "createTransactionContext", n = {"$this$createTransactionContext", "controlJob"}, s = {"L$0", "L$1"})
    /* loaded from: classes.dex */
    public static final class c extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        c(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RoomDatabaseKt.a(null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u001c\u0010\u0004\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0086@"}, d2 = {"withTransaction", "", "R", "Landroidx/room/RoomDatabase;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "continuation"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "androidx.room.RoomDatabaseKt", f = "RoomDatabase.kt", i = {0, 0}, l = {50, 51}, m = "withTransaction", n = {"$this$withTransaction", "block"}, s = {"L$0", "L$1"})
    /* loaded from: classes.dex */
    public static final class e extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        e(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RoomDatabaseKt.withTransaction(null, null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f A[PHI: r6 
      PHI: (r6v2 java.lang.Object) = (r6v4 java.lang.Object), (r6v1 java.lang.Object) binds: [B:24:0x007c, B:12:0x002c] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <R> java.lang.Object withTransaction(@org.jetbrains.annotations.NotNull androidx.room.RoomDatabase r4, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super R>, ? extends java.lang.Object> r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super R> r6) {
        /*
            boolean r0 = r6 instanceof androidx.room.RoomDatabaseKt.e
            if (r0 == 0) goto L_0x0014
            r0 = r6
            androidx.room.RoomDatabaseKt$e r0 = (androidx.room.RoomDatabaseKt.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            androidx.room.RoomDatabaseKt$e r0 = new androidx.room.RoomDatabaseKt$e
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x0030;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x007f
        L_0x0030:
            java.lang.Object r4 = r0.L$1
            r5 = r4
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
            java.lang.Object r4 = r0.L$0
            androidx.room.RoomDatabase r4 = (androidx.room.RoomDatabase) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x0067
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()
            androidx.room.TransactionElement$Key r2 = androidx.room.TransactionElement.Key
            kotlin.coroutines.CoroutineContext$Key r2 = (kotlin.coroutines.CoroutineContext.Key) r2
            kotlin.coroutines.CoroutineContext$Element r6 = r6.get(r2)
            androidx.room.TransactionElement r6 = (androidx.room.TransactionElement) r6
            if (r6 == 0) goto L_0x0059
            kotlin.coroutines.ContinuationInterceptor r6 = r6.getTransactionDispatcher$room_ktx_release()
            if (r6 == 0) goto L_0x0059
            kotlin.coroutines.CoroutineContext r6 = (kotlin.coroutines.CoroutineContext) r6
            goto L_0x0069
        L_0x0059:
            r0.L$0 = r4
            r0.L$1 = r5
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = a(r4, r0)
            if (r6 != r1) goto L_0x0067
            return r1
        L_0x0067:
            kotlin.coroutines.CoroutineContext r6 = (kotlin.coroutines.CoroutineContext) r6
        L_0x0069:
            androidx.room.RoomDatabaseKt$f r2 = new androidx.room.RoomDatabaseKt$f
            r3 = 0
            r2.<init>(r4, r5, r3)
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2
            r0.L$0 = r3
            r0.L$1 = r3
            r4 = 2
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r2, r0)
            if (r6 != r1) goto L_0x007f
            return r1
        L_0x007f:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomDatabaseKt.withTransaction(androidx.room.RoomDatabase, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "androidx.room.RoomDatabaseKt$withTransaction$2", f = "RoomDatabase.kt", i = {0}, l = {58}, m = "invokeSuspend", n = {"transactionElement"}, s = {"L$0"})
    /* loaded from: classes.dex */
    public static final class f extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super R>, Object> {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ RoomDatabase $this_withTransaction;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(RoomDatabase roomDatabase, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$this_withTransaction = roomDatabase;
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            f fVar = new f(this.$this_withTransaction, this.$block, completion);
            fVar.L$0 = obj;
            return fVar;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Object obj) {
            return ((f) create(coroutineScope, (Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            TransactionElement transactionElement;
            Throwable th;
            Throwable th2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    CoroutineContext.Element element = ((CoroutineScope) this.L$0).getCoroutineContext().get(TransactionElement.Key);
                    Intrinsics.checkNotNull(element);
                    transactionElement = (TransactionElement) element;
                    transactionElement.acquire();
                    try {
                        this.$this_withTransaction.beginTransaction();
                        try {
                            Function1 function1 = this.$block;
                            this.L$0 = transactionElement;
                            this.label = 1;
                            Object invoke = function1.invoke(this);
                            if (invoke == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            transactionElement = transactionElement;
                            obj = invoke;
                            this.$this_withTransaction.setTransactionSuccessful();
                            try {
                                this.$this_withTransaction.endTransaction();
                                transactionElement.release();
                                return obj;
                            } catch (Throwable th3) {
                                th = th3;
                                transactionElement = transactionElement;
                                transactionElement.release();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th2 = th4;
                            this.$this_withTransaction.endTransaction();
                            throw th2;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        transactionElement.release();
                        throw th;
                    }
                case 1:
                    transactionElement = (TransactionElement) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        this.$this_withTransaction.setTransactionSuccessful();
                        this.$this_withTransaction.endTransaction();
                        transactionElement.release();
                        return obj;
                    } catch (Throwable th6) {
                        th2 = th6;
                        this.$this_withTransaction.endTransaction();
                        throw th2;
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static final /* synthetic */ java.lang.Object a(androidx.room.RoomDatabase r5, kotlin.coroutines.Continuation<? super kotlin.coroutines.CoroutineContext> r6) {
        /*
            boolean r0 = r6 instanceof androidx.room.RoomDatabaseKt.c
            if (r0 == 0) goto L_0x0014
            r0 = r6
            androidx.room.RoomDatabaseKt$c r0 = (androidx.room.RoomDatabaseKt.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            androidx.room.RoomDatabaseKt$c r0 = new androidx.room.RoomDatabaseKt$c
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002c:
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.CompletableJob r5 = (kotlinx.coroutines.CompletableJob) r5
            java.lang.Object r0 = r0.L$0
            androidx.room.RoomDatabase r0 = (androidx.room.RoomDatabase) r0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x0076
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r6)
            r6 = 1
            r2 = 0
            kotlinx.coroutines.CompletableJob r2 = kotlinx.coroutines.JobKt.Job$default(r2, r6, r2)
            kotlin.coroutines.CoroutineContext r3 = r0.getContext()
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Key r4 = (kotlin.coroutines.CoroutineContext.Key) r4
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            if (r3 == 0) goto L_0x005b
            androidx.room.RoomDatabaseKt$d r4 = new androidx.room.RoomDatabaseKt$d
            r4.<init>(r2)
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            r3.invokeOnCompletion(r4)
        L_0x005b:
            java.util.concurrent.Executor r3 = r5.getTransactionExecutor()
            java.lang.String r4 = "transactionExecutor"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r4 = r2
            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4
            r0.L$0 = r5
            r0.L$1 = r2
            r0.label = r6
            java.lang.Object r6 = a(r3, r4, r0)
            if (r6 != r1) goto L_0x0074
            return r1
        L_0x0074:
            r0 = r5
            r5 = r2
        L_0x0076:
            kotlin.coroutines.ContinuationInterceptor r6 = (kotlin.coroutines.ContinuationInterceptor) r6
            androidx.room.TransactionElement r1 = new androidx.room.TransactionElement
            r2 = r5
            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2
            r1.<init>(r2, r6)
            java.lang.ThreadLocal r0 = r0.b()
            java.lang.String r2 = "suspendingTransactionId"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r2)
            int r5 = java.lang.System.identityHashCode(r5)
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
            kotlinx.coroutines.ThreadContextElement r5 = kotlinx.coroutines.ThreadContextElementKt.asContextElement(r0, r5)
            kotlin.coroutines.CoroutineContext r1 = (kotlin.coroutines.CoroutineContext) r1
            kotlin.coroutines.CoroutineContext r6 = r6.plus(r1)
            kotlin.coroutines.CoroutineContext r5 = (kotlin.coroutines.CoroutineContext) r5
            kotlin.coroutines.CoroutineContext r5 = r6.plus(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomDatabaseKt.a(androidx.room.RoomDatabase, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes.dex */
    public static final class d extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ CompletableJob $controlJob;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(CompletableJob completableJob) {
            super(1);
            this.$controlJob = completableJob;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }

        public final void a(@Nullable Throwable th) {
            Job.DefaultImpls.cancel$default((Job) this.$controlJob, (CancellationException) null, 1, (Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004¨\u0006\u0005"}, d2 = {"<anonymous>", "", "it", "", "invoke", "androidx/room/RoomDatabaseKt$acquireTransactionThread$2$1"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes.dex */
    public static final class a extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ Job $controlJob$inlined;
        final /* synthetic */ Executor $this_acquireTransactionThread$inlined;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Executor executor, Job job) {
            super(1);
            this.$this_acquireTransactionThread$inlined = executor;
            this.$controlJob$inlined = job;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }

        public final void a(@Nullable Throwable th) {
            Job.DefaultImpls.cancel$default(this.$controlJob$inlined, (CancellationException) null, 1, (Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RoomDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {"<anonymous>", "", "run", "androidx/room/RoomDatabaseKt$acquireTransactionThread$2$2"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes.dex */
    public static final class b implements Runnable {
        final /* synthetic */ CancellableContinuation a;
        final /* synthetic */ Executor b;
        final /* synthetic */ Job c;

        b(CancellableContinuation cancellableContinuation, Executor executor, Job job) {
            this.a = cancellableContinuation;
            this.b = executor;
            this.c = job;
        }

        /* compiled from: RoomDatabase.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "androidx/room/RoomDatabaseKt$acquireTransactionThread$2$2$1"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "androidx.room.RoomDatabaseKt$acquireTransactionThread$2$2$1", f = "RoomDatabase.kt", i = {}, l = {124}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: androidx.room.RoomDatabaseKt$b$1  reason: invalid class name */
        /* loaded from: classes.dex */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            private /* synthetic */ Object L$0;
            int label;

            AnonymousClass1(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                AnonymousClass1 r0 = new AnonymousClass1(completion);
                r0.L$0 = obj;
                return r0;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        CoroutineContext.Element element = ((CoroutineScope) this.L$0).getCoroutineContext().get(ContinuationInterceptor.Key);
                        Intrinsics.checkNotNull(element);
                        Result.Companion companion = Result.Companion;
                        b.this.a.resumeWith(Result.m1220constructorimpl(element));
                        Job job = b.this.c;
                        this.label = 1;
                        if (job.join(this) == coroutine_suspended) {
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

        @Override // java.lang.Runnable
        public final void run() {
            kotlinx.coroutines.d.a(null, new AnonymousClass1(null), 1, null);
        }
    }

    static final /* synthetic */ Object a(Executor executor, Job job, Continuation<? super ContinuationInterceptor> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        cancellableContinuationImpl2.invokeOnCancellation(new a(executor, job));
        try {
            executor.execute(new b(cancellableContinuationImpl2, executor, job));
        } catch (RejectedExecutionException e2) {
            cancellableContinuationImpl2.cancel(new IllegalStateException("Unable to acquire a thread to perform the database transaction.", e2));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
