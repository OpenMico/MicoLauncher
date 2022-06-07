package okhttp3.internal.connection;

import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.lang.ref.Reference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealConnectionPool.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u000e\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0012J\u0006\u0010\"\u001a\u00020\u0005J\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u0005J\u0018\u0010&\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\u000e\u0010'\u001a\u00020$2\u0006\u0010!\u001a\u00020\u0012R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Lokhttp3/internal/concurrent/TaskRunner;IJLjava/util/concurrent/TimeUnit;)V", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/connection/RealConnectionPool$cleanupTask$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupTask$1;", "connections", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealConnection;", "keepAliveDurationNs", "callAcquirePooledConnection", "", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "routes", "", "Lokhttp3/Route;", "requireMultiplexed", "cleanup", "now", "connectionBecameIdle", "connection", "connectionCount", "evictAll", "", "idleConnectionCount", "pruneAndGetAllocationCount", "put", "Companion", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealConnectionPool {
    public static final Companion Companion = new Companion(null);
    private final TaskQueue cleanupQueue;
    private final RealConnectionPool$cleanupTask$1 cleanupTask = new Task(Util.okHttpName + " ConnectionPool") { // from class: okhttp3.internal.connection.RealConnectionPool$cleanupTask$1
        @Override // okhttp3.internal.concurrent.Task
        public long runOnce() {
            return RealConnectionPool.this.cleanup(System.nanoTime());
        }
    };
    private final ArrayDeque<RealConnection> connections = new ArrayDeque<>();
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    /* JADX WARN: Type inference failed for: r3v2, types: [okhttp3.internal.connection.RealConnectionPool$cleanupTask$1] */
    public RealConnectionPool(@NotNull TaskRunner taskRunner, int i, long j, @NotNull TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
        Intrinsics.checkParameterIsNotNull(timeUnit, "timeUnit");
        this.maxIdleConnections = i;
        this.keepAliveDurationNs = timeUnit.toNanos(j);
        this.cleanupQueue = taskRunner.newQueue();
        if (!(j > 0)) {
            throw new IllegalArgumentException(("keepAliveDuration <= 0: " + j).toString());
        }
    }

    public final synchronized int idleConnectionCount() {
        int i;
        ArrayDeque<RealConnection> arrayDeque = this.connections;
        i = 0;
        if (!(arrayDeque instanceof Collection) || !arrayDeque.isEmpty()) {
            for (RealConnection realConnection : arrayDeque) {
                if (realConnection.getCalls().isEmpty() && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    public final synchronized int connectionCount() {
        return this.connections.size();
    }

    public final void evictAll() {
        ArrayList<RealConnection> arrayList = new ArrayList();
        synchronized (this) {
            Iterator<RealConnection> it = this.connections.iterator();
            Intrinsics.checkExpressionValueIsNotNull(it, "connections.iterator()");
            while (it.hasNext()) {
                RealConnection connection = it.next();
                if (connection.getCalls().isEmpty()) {
                    connection.setNoNewExchanges(true);
                    Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                    arrayList.add(connection);
                    it.remove();
                }
            }
            if (this.connections.isEmpty()) {
                this.cleanupQueue.cancelAll();
            }
            Unit unit = Unit.INSTANCE;
        }
        for (RealConnection realConnection : arrayList) {
            Util.closeQuietly(realConnection.socket());
        }
    }

    public final long cleanup(long j) {
        RealConnection realConnection = null;
        synchronized (this) {
            Iterator<RealConnection> it = this.connections.iterator();
            int i = 0;
            long j2 = Long.MIN_VALUE;
            int i2 = 0;
            while (it.hasNext()) {
                RealConnection connection = it.next();
                Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                if (pruneAndGetAllocationCount(connection, j) > 0) {
                    i2++;
                } else {
                    i++;
                    long idleAtNs$okhttp = j - connection.getIdleAtNs$okhttp();
                    if (idleAtNs$okhttp > j2) {
                        realConnection = connection;
                        j2 = idleAtNs$okhttp;
                    }
                }
            }
            if (j2 < this.keepAliveDurationNs && i <= this.maxIdleConnections) {
                if (i > 0) {
                    return this.keepAliveDurationNs - j2;
                } else if (i2 <= 0) {
                    return -1L;
                } else {
                    return this.keepAliveDurationNs;
                }
            }
            this.connections.remove(realConnection);
            if (this.connections.isEmpty()) {
                this.cleanupQueue.cancelAll();
            }
            Unit unit = Unit.INSTANCE;
            if (realConnection == null) {
                Intrinsics.throwNpe();
            }
            Util.closeQuietly(realConnection.socket());
            return 0L;
        }
    }

    private final int pruneAndGetAllocationCount(RealConnection realConnection, long j) {
        List<Reference<RealCall>> calls = realConnection.getCalls();
        int i = 0;
        while (i < calls.size()) {
            Reference<RealCall> reference = calls.get(i);
            if (reference.get() != null) {
                i++;
            } else if (reference != null) {
                Platform.Companion.get().logCloseableLeak("A connection to " + realConnection.route().address().url() + " was leaked. Did you forget to close a response body?", ((RealCall.CallReference) reference).getCallStackTrace());
                calls.remove(i);
                realConnection.setNoNewExchanges(true);
                if (calls.isEmpty()) {
                    realConnection.setIdleAtNs$okhttp(j - this.keepAliveDurationNs);
                    return 0;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.connection.RealCall.CallReference");
            }
        }
        return calls.size();
    }

    /* compiled from: RealConnectionPool.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", "()V", BluetoothConstants.GET, "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final RealConnectionPool get(@NotNull ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
            return connectionPool.getDelegate$okhttp();
        }
    }

    public final boolean callAcquirePooledConnection(@NotNull Address address, @NotNull RealCall call, @Nullable List<Route> list, boolean z) {
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(call, "call");
        if (!Util.assertionsEnabled || Thread.holdsLock(this)) {
            Iterator<RealConnection> it = this.connections.iterator();
            while (it.hasNext()) {
                RealConnection connection = it.next();
                if (!z || connection.isMultiplexed()) {
                    if (connection.isEligible$okhttp(address, list)) {
                        Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                        call.acquireConnectionNoEvents(connection);
                        return true;
                    }
                }
            }
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final void put(@NotNull RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        if (!Util.assertionsEnabled || Thread.holdsLock(this)) {
            this.connections.add(connection);
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final boolean connectionBecameIdle(@NotNull RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST hold lock on ");
            sb.append(this);
            throw new AssertionError(sb.toString());
        } else if (connection.getNoNewExchanges() || this.maxIdleConnections == 0) {
            this.connections.remove(connection);
            if (this.connections.isEmpty()) {
                this.cleanupQueue.cancelAll();
            }
            return true;
        } else {
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return false;
        }
    }
}
