package kotlinx.coroutines.debug.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.debug.AgentPremain;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugProbesImpl.kt */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\bÀ\u0002\u0018\u00002\u00020\u0013:\u0002\u008f\u0001B\t\b\u0002¢\u0006\u0004\b\u0001\u0010\u0002J3\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0004\b\u0011\u0010\u0012J>\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\"\b\b\u0000\u0010\u0014*\u00020\u00132\u001c\u0010\u0018\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00028\u00000\u0015H\u0082\b¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001b\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u001b\u0010\u000eJ\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000f¢\u0006\u0004\b\u001d\u0010\u0012J)\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010\u001e\u001a\u00020\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f¢\u0006\u0004\b!\u0010\"J5\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010$\u001a\u00020#2\b\u0010&\u001a\u0004\u0018\u00010%2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b'\u0010(J?\u0010.\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020)0-2\u0006\u0010*\u001a\u00020)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001f0+2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b.\u0010/J3\u00101\u001a\u00020)2\u0006\u00100\u001a\u00020)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001f0+2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b1\u00102J\u001d\u00105\u001a\u0010\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020\f\u0018\u000103H\u0002¢\u0006\u0004\b5\u00106J\u0015\u00109\u001a\u00020#2\u0006\u00108\u001a\u000207¢\u0006\u0004\b9\u0010:J\r\u0010;\u001a\u00020\f¢\u0006\u0004\b;\u0010\u0002J%\u0010=\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b=\u0010>J\u001b\u0010@\u001a\u00020\f2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0002¢\u0006\u0004\b@\u0010AJ)\u0010D\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0000¢\u0006\u0004\bB\u0010CJ\u001b\u0010G\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bE\u0010FJ\u001b\u0010I\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bH\u0010FJ'\u0010L\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f\"\b\b\u0000\u0010\u0003*\u00020J2\u0006\u0010K\u001a\u00028\u0000H\u0002¢\u0006\u0004\bL\u0010MJ\u000f\u0010N\u001a\u00020\fH\u0002¢\u0006\u0004\bN\u0010\u0002J\u000f\u0010O\u001a\u00020\fH\u0002¢\u0006\u0004\bO\u0010\u0002J\r\u0010P\u001a\u00020\f¢\u0006\u0004\bP\u0010\u0002J\u001f\u0010R\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020Q2\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bR\u0010SJ#\u0010T\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bT\u0010UJ/\u0010T\u001a\u00020\f2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bT\u0010VJ;\u0010^\u001a\u00020\f*\u0002072\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020X0W2\n\u0010\\\u001a\u00060Zj\u0002`[2\u0006\u0010]\u001a\u00020#H\u0002¢\u0006\u0004\b^\u0010_J\u0017\u0010`\u001a\u000204*\u0006\u0012\u0002\b\u00030\u0016H\u0002¢\u0006\u0004\b`\u0010aJ\u001d\u0010?\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016*\u0006\u0012\u0002\b\u00030\u0004H\u0002¢\u0006\u0004\b?\u0010bJ\u001a\u0010?\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016*\u00020QH\u0082\u0010¢\u0006\u0004\b?\u0010cJ\u0016\u0010d\u001a\u0004\u0018\u00010Q*\u00020QH\u0082\u0010¢\u0006\u0004\bd\u0010eJ\u001b\u0010f\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\bf\u0010gR\u0016\u0010h\u001a\u00020#8\u0002@\u0002X\u0082T¢\u0006\u0006\n\u0004\bh\u0010iR\"\u0010k\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020X0j8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bk\u0010lR \u0010p\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00160m8B@\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bn\u0010oR&\u0010q\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u0002040j8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bq\u0010lR\u0016\u0010s\u001a\u00020r8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bs\u0010tR\u0016\u0010v\u001a\u00020u8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bv\u0010wR$\u0010x\u001a\u0010\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020\f\u0018\u0001038\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bx\u0010yR\"\u0010z\u001a\u0002048\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\bz\u0010{\u001a\u0004\b|\u0010}\"\u0004\b~\u0010\u007fR\u0019\u0010\u0080\u0001\u001a\u00020)8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0080\u0001\u0010\u0081\u0001R\u0018\u0010\u0083\u0001\u001a\u0002048@@\u0000X\u0080\u0004¢\u0006\u0007\u001a\u0005\b\u0082\u0001\u0010}R&\u0010\u0084\u0001\u001a\u0002048\u0006@\u0006X\u0086\u000e¢\u0006\u0015\n\u0005\b\u0084\u0001\u0010{\u001a\u0005\b\u0085\u0001\u0010}\"\u0005\b\u0086\u0001\u0010\u007fR\u001b\u0010\u0087\u0001\u001a\u0004\u0018\u00010%8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0087\u0001\u0010\u0088\u0001R$\u0010\u008c\u0001\u001a\u00020#*\u0002078B@\u0002X\u0082\u0004¢\u0006\u000f\u0012\u0006\b\u008a\u0001\u0010\u008b\u0001\u001a\u0005\b\u0089\u0001\u0010:R\u001d\u0010\u008d\u0001\u001a\u000204*\u00020\u001f8B@\u0002X\u0082\u0004¢\u0006\b\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001¨\u0006\u0090\u0001"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "<init>", "()V", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", TrackConstant.COMPLETION, "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "frame", "createOwner", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/StackTraceFrame;)Lkotlin/coroutines/Continuation;", "Ljava/io/PrintStream;", "out", "", "dumpCoroutines", "(Ljava/io/PrintStream;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "dumpCoroutinesInfo", "()Ljava/util/List;", "", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "Lkotlin/coroutines/CoroutineContext;", "create", "dumpCoroutinesInfoImpl", "(Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "dumpCoroutinesSynchronized", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpDebuggerInfo", "info", "Ljava/lang/StackTraceElement;", "coroutineTrace", "enhanceStackTraceWithThreadDump", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;Ljava/util/List;)Ljava/util/List;", "", XiaomiOAuthConstants.EXTRA_STATE_2, "Ljava/lang/Thread;", "thread", "enhanceStackTraceWithThreadDumpImpl", "(Ljava/lang/String;Ljava/lang/Thread;Ljava/util/List;)Ljava/util/List;", "", "indexOfResumeWith", "", "actualTrace", "Lkotlin/Pair;", "findContinuationStartIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "frameIndex", "findIndexOfFrame", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "Lkotlin/Function1;", "", "getDynamicAttach", "()Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/Job;", "job", "hierarchyToString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "install", "frames", "printStackTrace", "(Ljava/io/PrintStream;Ljava/util/List;)V", "owner", "probeCoroutineCompleted", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)V", "probeCoroutineCreated$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "probeCoroutineCreated", "probeCoroutineResumed$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)V", "probeCoroutineResumed", "probeCoroutineSuspended$kotlinx_coroutines_core", "probeCoroutineSuspended", "", "throwable", "sanitizeStackTrace", "(Ljava/lang/Throwable;)Ljava/util/List;", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "uninstall", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateRunningState", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/String;)V", "updateState", "(Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "map", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "builder", "indent", "build", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "isFinished", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Z", "(Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "realCaller", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "toStackTraceFrame", "(Ljava/util/List;)Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "ARTIFICIAL_FRAME_MESSAGE", "Ljava/lang/String;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "callerInfoCache", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "getCapturedCoroutines", "()Ljava/util/Set;", "capturedCoroutines", "capturedCoroutinesMap", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "coroutineStateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Ljava/text/SimpleDateFormat;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dynamicAttach", "Lkotlin/jvm/functions/Function1;", "enableCreationStackTraces", "Z", "getEnableCreationStackTraces", "()Z", "setEnableCreationStackTraces", "(Z)V", "installations", "I", "isInstalled$kotlinx_coroutines_core", "isInstalled", "sanitizeStackTraces", "getSanitizeStackTraces", "setSanitizeStackTraces", "weakRefCleanerThread", "Ljava/lang/Thread;", "getDebugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "debugString", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class DebugProbesImpl {
    @Nullable
    private static Thread b;
    private static volatile int installations;
    @NotNull
    public static final DebugProbesImpl INSTANCE = new DebugProbesImpl();
    @NotNull
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @NotNull
    private static final ConcurrentWeakMap<a<?>, Boolean> c = new ConcurrentWeakMap<>(false, 1, null);
    @NotNull
    private static final /* synthetic */ a d = new Object(0) { // from class: kotlinx.coroutines.debug.internal.a
        volatile long sequenceNumber;

        {
            this.sequenceNumber = r1;
        }
    };
    @NotNull
    private static final ReentrantReadWriteLock f = new ReentrantReadWriteLock();
    private static boolean g = true;
    private static boolean h = true;
    @Nullable
    private static final Function1<Boolean, Unit> i = INSTANCE.b();
    @NotNull
    private static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> j = new ConcurrentWeakMap<>(true);
    private static final /* synthetic */ AtomicLongFieldUpdater e = AtomicLongFieldUpdater.newUpdater(a.class, "sequenceNumber");

    private DebugProbesImpl() {
    }

    public final Set<a<?>> a() {
        return c.keySet();
    }

    public final boolean isInstalled$kotlinx_coroutines_core() {
        return installations > 0;
    }

    public final boolean getSanitizeStackTraces() {
        return g;
    }

    public final void setSanitizeStackTraces(boolean z) {
        g = z;
    }

    public final boolean getEnableCreationStackTraces() {
        return h;
    }

    public final void setEnableCreationStackTraces(boolean z) {
        h = z;
    }

    private final Function1<Boolean, Unit> b() {
        Object obj;
        Object newInstance;
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            newInstance = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(new Object[0]);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m1220constructorimpl(ResultKt.createFailure(th));
        }
        if (newInstance != null) {
            obj = Result.m1220constructorimpl((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(newInstance, 1));
            if (Result.m1225isFailureimpl(obj)) {
                obj = null;
            }
            return (Function1) obj;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>");
    }

    public final void install() {
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            installations++;
            if (installations <= 1) {
                INSTANCE.c();
                if (AgentPremain.INSTANCE.isInstalledStatically()) {
                    while (i2 < readHoldCount) {
                        readLock.lock();
                        i2++;
                    }
                    writeLock.unlock();
                    return;
                }
                Function1<Boolean, Unit> function1 = i;
                if (function1 != null) {
                    function1.invoke(true);
                }
                Unit unit = Unit.INSTANCE;
                while (i2 < readHoldCount) {
                    readLock.lock();
                    i2++;
                }
                writeLock.unlock();
            }
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    public final void uninstall() {
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                DebugProbesImpl debugProbesImpl = INSTANCE;
                installations--;
                if (installations == 0) {
                    INSTANCE.d();
                    c.clear();
                    j.clear();
                    if (AgentPremain.INSTANCE.isInstalledStatically()) {
                        while (i2 < readHoldCount) {
                            readLock.lock();
                            i2++;
                        }
                        writeLock.unlock();
                        return;
                    }
                    Function1<Boolean, Unit> function1 = i;
                    if (function1 != null) {
                        function1.invoke(false);
                    }
                    Unit unit = Unit.INSTANCE;
                    while (i2 < readHoldCount) {
                        readLock.lock();
                        i2++;
                    }
                    writeLock.unlock();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Agent was not installed".toString());
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    /* compiled from: DebugProbesImpl.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class c extends Lambda implements Function0<Unit> {
        public static final c a = new c();

        c() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* synthetic */ Unit invoke() {
            a();
            return Unit.INSTANCE;
        }

        public final void a() {
            DebugProbesImpl.j.runWeakRefQueueCleaningLoopUntilInterrupted();
        }
    }

    private final void c() {
        Thread thread;
        thread = ThreadsKt.thread((r12 & 1) != 0, (r12 & 2) != 0 ? false : true, (r12 & 4) != 0 ? null : null, (r12 & 8) != 0 ? null : "Coroutines Debugger Cleaner", (r12 & 16) != 0 ? -1 : 0, c.a);
        b = thread;
    }

    private final void d() {
        Thread thread = b;
        if (thread != null) {
            thread.interrupt();
        }
        b = null;
    }

    @NotNull
    public final String hierarchyToString(@NotNull Job job) {
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : INSTANCE.a()) {
                    if (((a) obj).a.getContext().get(Job.Key) != null) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
                for (Object obj2 : arrayList2) {
                    linkedHashMap.put(JobKt.getJob(((a) obj2).a.getContext()), ((a) obj2).b);
                }
                StringBuilder sb = new StringBuilder();
                INSTANCE.a(job, linkedHashMap, sb, "");
                String sb2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
                return sb2;
            }
            throw new IllegalStateException("Debug probes are not installed".toString());
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    private final void a(Job job, Map<Job, DebugCoroutineInfoImpl> map, StringBuilder sb, String str) {
        DebugCoroutineInfoImpl debugCoroutineInfoImpl = map.get(job);
        if (debugCoroutineInfoImpl != null) {
            String state = debugCoroutineInfoImpl.getState();
            sb.append(str + a(job) + ", continuation is " + state + " at line " + ((StackTraceElement) CollectionsKt.firstOrNull((List<? extends Object>) debugCoroutineInfoImpl.lastObservedStackTrace())) + '\n');
            str = Intrinsics.stringPlus(str, "\t");
        } else if (!(job instanceof ScopeCoroutine)) {
            sb.append(str + a(job) + '\n');
            str = Intrinsics.stringPlus(str, "\t");
        }
        for (Job job2 : job.getChildren()) {
            a(job2, map, sb, str);
        }
    }

    private final String a(Job job) {
        return job instanceof JobSupport ? ((JobSupport) job).toDebugString() : job.toString();
    }

    public final void dumpCoroutines(@NotNull PrintStream printStream) {
        synchronized (printStream) {
            INSTANCE.a(printStream);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean a(a<?> aVar) {
        CoroutineContext context = aVar.b.getContext();
        Job job = context == null ? null : (Job) context.get(Job.Key);
        if (job == null || !job.isCompleted()) {
            return false;
        }
        c.remove(aVar);
        return true;
    }

    private final void a(PrintStream printStream) {
        String str;
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                printStream.print(Intrinsics.stringPlus("Coroutines dump ", a.format(Long.valueOf(System.currentTimeMillis()))));
                for (a aVar : SequencesKt.sortedWith(SequencesKt.filter(CollectionsKt.asSequence(INSTANCE.a()), b.a), new Comparator<T>() { // from class: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesSynchronized$lambda-21$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(Long.valueOf(((DebugProbesImpl.a) t).b.sequenceNumber), Long.valueOf(((DebugProbesImpl.a) t2).b.sequenceNumber));
                    }
                })) {
                    DebugCoroutineInfoImpl debugCoroutineInfoImpl = aVar.b;
                    List<StackTraceElement> lastObservedStackTrace = debugCoroutineInfoImpl.lastObservedStackTrace();
                    List<StackTraceElement> a2 = INSTANCE.a(debugCoroutineInfoImpl.getState(), debugCoroutineInfoImpl.lastObservedThread, lastObservedStackTrace);
                    if (!Intrinsics.areEqual(debugCoroutineInfoImpl.getState(), "RUNNING") || a2 != lastObservedStackTrace) {
                        str = debugCoroutineInfoImpl.getState();
                    } else {
                        str = Intrinsics.stringPlus(debugCoroutineInfoImpl.getState(), " (Last suspension stacktrace, not an actual stacktrace)");
                    }
                    printStream.print("\n\nCoroutine " + aVar.a + ", state: " + str);
                    if (lastObservedStackTrace.isEmpty()) {
                        printStream.print(Intrinsics.stringPlus("\n\tat ", StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace")));
                        INSTANCE.a(printStream, debugCoroutineInfoImpl.getCreationStackTrace());
                    } else {
                        INSTANCE.a(printStream, a2);
                    }
                }
                Unit unit = Unit.INSTANCE;
                return;
            }
            throw new IllegalStateException("Debug probes are not installed".toString());
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    /* compiled from: DebugProbesImpl.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\n"}, d2 = {"<anonymous>", "", "it", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends Lambda implements Function1<a<?>, Boolean> {
        public static final b a = new b();

        b() {
            super(1);
        }

        public final boolean a(@NotNull a<?> aVar) {
            return !DebugProbesImpl.INSTANCE.a(aVar);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Boolean invoke(a<?> aVar) {
            return Boolean.valueOf(a(aVar));
        }
    }

    private final void a(PrintStream printStream, List<StackTraceElement> list) {
        for (StackTraceElement stackTraceElement : list) {
            printStream.print(Intrinsics.stringPlus("\n\tat ", stackTraceElement));
        }
    }

    @NotNull
    public final List<StackTraceElement> enhanceStackTraceWithThreadDump(@NotNull DebugCoroutineInfo debugCoroutineInfo, @NotNull List<StackTraceElement> list) {
        return a(debugCoroutineInfo.getState(), debugCoroutineInfo.getLastObservedThread(), list);
    }

    private final List<StackTraceElement> a(String str, Thread thread, List<StackTraceElement> list) {
        Object obj;
        if (!Intrinsics.areEqual(str, "RUNNING") || thread == null) {
            return list;
        }
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            obj = Result.m1220constructorimpl(thread.getStackTrace());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m1220constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1225isFailureimpl(obj)) {
            obj = null;
        }
        StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) obj;
        if (stackTraceElementArr == null) {
            return list;
        }
        int length = stackTraceElementArr.length;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                i3 = -1;
                break;
            }
            StackTraceElement stackTraceElement = stackTraceElementArr[i3];
            if (Intrinsics.areEqual(stackTraceElement.getClassName(), "kotlin.coroutines.jvm.internal.BaseContinuationImpl") && Intrinsics.areEqual(stackTraceElement.getMethodName(), "resumeWith") && Intrinsics.areEqual(stackTraceElement.getFileName(), "ContinuationImpl.kt")) {
                break;
            }
            i3++;
        }
        Pair<Integer, Integer> a2 = a(i3, stackTraceElementArr, list);
        int intValue = a2.component1().intValue();
        int intValue2 = a2.component2().intValue();
        if (intValue == -1) {
            return list;
        }
        ArrayList arrayList = new ArrayList((((list.size() + i3) - intValue) - 1) - intValue2);
        int i4 = i3 - intValue2;
        if (i4 > 0) {
            while (true) {
                int i5 = i2 + 1;
                arrayList.add(stackTraceElementArr[i2]);
                if (i5 >= i4) {
                    break;
                }
                i2 = i5;
            }
        }
        int i6 = intValue + 1;
        int size = list.size();
        if (i6 < size) {
            while (true) {
                int i7 = i6 + 1;
                arrayList.add(list.get(i6));
                if (i7 >= size) {
                    break;
                }
                i6 = i7;
            }
        }
        return arrayList;
    }

    private final Pair<Integer, Integer> a(int i2, StackTraceElement[] stackTraceElementArr, List<StackTraceElement> list) {
        for (int i3 = 0; i3 < 3; i3++) {
            int b2 = INSTANCE.b((i2 - 1) - i3, stackTraceElementArr, list);
            if (b2 != -1) {
                return TuplesKt.to(Integer.valueOf(b2), Integer.valueOf(i3));
            }
        }
        return TuplesKt.to(-1, 0);
    }

    private final int b(int i2, StackTraceElement[] stackTraceElementArr, List<StackTraceElement> list) {
        StackTraceElement stackTraceElement = (StackTraceElement) ArraysKt.getOrNull(stackTraceElementArr, i2);
        if (stackTraceElement == null) {
            return -1;
        }
        int i3 = 0;
        for (StackTraceElement stackTraceElement2 : list) {
            if (Intrinsics.areEqual(stackTraceElement2.getFileName(), stackTraceElement.getFileName()) && Intrinsics.areEqual(stackTraceElement2.getClassName(), stackTraceElement.getClassName()) && Intrinsics.areEqual(stackTraceElement2.getMethodName(), stackTraceElement.getMethodName())) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    public final void probeCoroutineResumed$kotlinx_coroutines_core(@NotNull Continuation<?> continuation) {
        a(continuation, "RUNNING");
    }

    public final void probeCoroutineSuspended$kotlinx_coroutines_core(@NotNull Continuation<?> continuation) {
        a(continuation, DebugCoroutineInfoImplKt.SUSPENDED);
    }

    private final void a(Continuation<?> continuation, String str) {
        if (isInstalled$kotlinx_coroutines_core()) {
            if (!Intrinsics.areEqual(str, "RUNNING") || !KotlinVersion.CURRENT.isAtLeast(1, 3, 30)) {
                a<?> a2 = a(continuation);
                if (a2 != null) {
                    a(a2, continuation, str);
                    return;
                }
                return;
            }
            CoroutineStackFrame coroutineStackFrame = continuation instanceof CoroutineStackFrame ? (CoroutineStackFrame) continuation : null;
            if (coroutineStackFrame != null) {
                a(coroutineStackFrame, str);
            }
        }
    }

    private final void a(CoroutineStackFrame coroutineStackFrame, String str) {
        ReentrantReadWriteLock.ReadLock readLock = f.readLock();
        readLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                DebugCoroutineInfoImpl remove = j.remove(coroutineStackFrame);
                if (remove == null) {
                    a<?> b2 = INSTANCE.b(coroutineStackFrame);
                    CoroutineStackFrame coroutineStackFrame2 = null;
                    remove = b2 == null ? null : b2.b;
                    if (remove != null) {
                        CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = remove.getLastObservedFrame$kotlinx_coroutines_core();
                        if (lastObservedFrame$kotlinx_coroutines_core != null) {
                            coroutineStackFrame2 = INSTANCE.a(lastObservedFrame$kotlinx_coroutines_core);
                        }
                        if (coroutineStackFrame2 != null) {
                            j.remove(coroutineStackFrame2);
                        }
                    } else {
                        return;
                    }
                }
                remove.updateState$kotlinx_coroutines_core(str, (Continuation) coroutineStackFrame);
                CoroutineStackFrame a2 = INSTANCE.a(coroutineStackFrame);
                if (a2 != null) {
                    j.put(a2, remove);
                    Unit unit = Unit.INSTANCE;
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    private final CoroutineStackFrame a(CoroutineStackFrame coroutineStackFrame) {
        do {
            coroutineStackFrame = coroutineStackFrame.getCallerFrame();
            if (coroutineStackFrame == null) {
                return null;
            }
        } while (coroutineStackFrame.getStackTraceElement() == null);
        return coroutineStackFrame;
    }

    private final void a(a<?> aVar, Continuation<?> continuation, String str) {
        ReentrantReadWriteLock.ReadLock readLock = f.readLock();
        readLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                aVar.b.updateState$kotlinx_coroutines_core(str, continuation);
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            readLock.unlock();
        }
    }

    private final a<?> a(Continuation<?> continuation) {
        CoroutineStackFrame coroutineStackFrame = continuation instanceof CoroutineStackFrame ? (CoroutineStackFrame) continuation : null;
        if (coroutineStackFrame == null) {
            return null;
        }
        return b(coroutineStackFrame);
    }

    private final a<?> b(CoroutineStackFrame coroutineStackFrame) {
        while (!(coroutineStackFrame instanceof a)) {
            coroutineStackFrame = coroutineStackFrame.getCallerFrame();
            if (coroutineStackFrame == null) {
                return null;
            }
        }
        return (a) coroutineStackFrame;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(@NotNull Continuation<? super T> continuation) {
        StackTraceFrame stackTraceFrame;
        if (!isInstalled$kotlinx_coroutines_core() || a(continuation) != null) {
            return continuation;
        }
        if (h) {
            stackTraceFrame = a(a((DebugProbesImpl) new Exception()));
        } else {
            stackTraceFrame = null;
        }
        return a(continuation, stackTraceFrame);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <T> Continuation<T> a(Continuation<? super T> continuation, StackTraceFrame stackTraceFrame) {
        if (!isInstalled$kotlinx_coroutines_core()) {
            return continuation;
        }
        a<?> aVar = new a<>(continuation, new DebugCoroutineInfoImpl(continuation.getContext(), stackTraceFrame, e.incrementAndGet(d)), stackTraceFrame);
        c.put(aVar, true);
        if (!isInstalled$kotlinx_coroutines_core()) {
            c.clear();
        }
        return aVar;
    }

    public final void b(a<?> aVar) {
        c.remove(aVar);
        CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = aVar.b.getLastObservedFrame$kotlinx_coroutines_core();
        CoroutineStackFrame a2 = lastObservedFrame$kotlinx_coroutines_core == null ? null : a(lastObservedFrame$kotlinx_coroutines_core);
        if (a2 != null) {
            j.remove(a2);
        }
    }

    /* compiled from: DebugProbesImpl.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001e\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "frame", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", com.umeng.analytics.pro.c.R, "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a<T> implements Continuation<T>, CoroutineStackFrame {
        @JvmField
        @NotNull
        public final Continuation<T> a;
        @JvmField
        @NotNull
        public final DebugCoroutineInfoImpl b;
        @Nullable
        private final CoroutineStackFrame c;

        @Override // kotlin.coroutines.Continuation
        @NotNull
        public CoroutineContext getContext() {
            return this.a.getContext();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public a(@NotNull Continuation<? super T> continuation, @NotNull DebugCoroutineInfoImpl debugCoroutineInfoImpl, @Nullable CoroutineStackFrame coroutineStackFrame) {
            this.a = continuation;
            this.b = debugCoroutineInfoImpl;
            this.c = coroutineStackFrame;
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        @Nullable
        public CoroutineStackFrame getCallerFrame() {
            CoroutineStackFrame coroutineStackFrame = this.c;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getCallerFrame();
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        @Nullable
        public StackTraceElement getStackTraceElement() {
            CoroutineStackFrame coroutineStackFrame = this.c;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getStackTraceElement();
        }

        @Override // kotlin.coroutines.Continuation
        public void resumeWith(@NotNull Object obj) {
            DebugProbesImpl.INSTANCE.b((a<?>) this);
            this.a.resumeWith(obj);
        }

        @NotNull
        public String toString() {
            return this.a.toString();
        }
    }

    private final <T extends Throwable> List<StackTraceElement> a(T t) {
        StackTraceElement[] stackTrace = t.getStackTrace();
        int length = stackTrace.length;
        int length2 = stackTrace.length - 1;
        while (true) {
            if (length2 < 0) {
                length2 = -1;
                break;
            } else if (Intrinsics.areEqual(stackTrace[length2].getClassName(), "kotlin.coroutines.jvm.internal.DebugProbesKt")) {
                break;
            } else {
                length2--;
            }
        }
        if (!g) {
            int i2 = length - length2;
            ArrayList arrayList = new ArrayList(i2);
            int i3 = 0;
            while (i3 < i2) {
                arrayList.add(i3 == 0 ? StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace") : stackTrace[i3 + length2]);
                i3++;
            }
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList((length - length2) + 1);
        ArrayList arrayList3 = arrayList2;
        arrayList3.add(StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace"));
        int i4 = length2 + 1;
        while (i4 < length) {
            if (a(stackTrace[i4])) {
                arrayList3.add(stackTrace[i4]);
                int i5 = i4 + 1;
                while (i5 < length && a(stackTrace[i5])) {
                    i5++;
                }
                int i6 = i5 - 1;
                int i7 = i6;
                while (i7 > i4 && stackTrace[i7].getFileName() == null) {
                    i7--;
                }
                if (i7 > i4 && i7 < i6) {
                    arrayList3.add(stackTrace[i7]);
                }
                arrayList3.add(stackTrace[i6]);
                i4 = i5;
            } else {
                arrayList3.add(stackTrace[i4]);
                i4++;
            }
        }
        return arrayList2;
    }

    private final boolean a(StackTraceElement stackTraceElement) {
        return StringsKt.startsWith$default(stackTraceElement.getClassName(), "kotlinx.coroutines", false, 2, (Object) null);
    }

    @NotNull
    public final List<DebugCoroutineInfo> dumpCoroutinesInfo() {
        CoroutineContext context;
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                ArrayList arrayList = new ArrayList();
                for (a aVar : CollectionsKt.sortedWith(INSTANCE.a(), new DebugProbesImpl$dumpCoroutinesInfoImpl$lambda14$$inlined$sortedBy$1())) {
                    DebugCoroutineInfo debugCoroutineInfo = null;
                    if (!INSTANCE.a((a<?>) aVar) && (context = aVar.b.getContext()) != null) {
                        debugCoroutineInfo = new DebugCoroutineInfo(aVar.b, context);
                    }
                    if (debugCoroutineInfo != null) {
                        arrayList.add(debugCoroutineInfo);
                    }
                }
                return arrayList;
            }
            throw new IllegalStateException("Debug probes are not installed".toString());
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    @NotNull
    public final List<DebuggerInfo> dumpDebuggerInfo() {
        CoroutineContext context;
        ReentrantReadWriteLock reentrantReadWriteLock = f;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int i2 = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i3 = 0; i3 < readHoldCount; i3++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                ArrayList arrayList = new ArrayList();
                for (a aVar : CollectionsKt.sortedWith(INSTANCE.a(), new DebugProbesImpl$dumpCoroutinesInfoImpl$lambda14$$inlined$sortedBy$1())) {
                    DebuggerInfo debuggerInfo = null;
                    if (!INSTANCE.a((a<?>) aVar) && (context = aVar.b.getContext()) != null) {
                        debuggerInfo = new DebuggerInfo(aVar.b, context);
                    }
                    if (debuggerInfo != null) {
                        arrayList.add(debuggerInfo);
                    }
                }
                return arrayList;
            }
            throw new IllegalStateException("Debug probes are not installed".toString());
        } finally {
            while (i2 < readHoldCount) {
                readLock.lock();
                i2++;
            }
            writeLock.unlock();
        }
    }

    private final StackTraceFrame a(List<StackTraceElement> list) {
        StackTraceFrame stackTraceFrame = null;
        if (!list.isEmpty()) {
            ListIterator<StackTraceElement> listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                stackTraceFrame = new StackTraceFrame(stackTraceFrame, listIterator.previous());
            }
        }
        return stackTraceFrame;
    }
}
