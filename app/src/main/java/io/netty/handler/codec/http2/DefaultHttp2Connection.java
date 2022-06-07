package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.collection.IntCollections;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.UnaryPromiseNotifier;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/* loaded from: classes4.dex */
public class DefaultHttp2Connection implements Http2Connection {
    private static final InternalLogger i = InternalLoggerFactory.getInstance(DefaultHttp2Connection.class);
    private static final int j = Math.max(1, SystemPropertyUtil.getInt("io.netty.http2.childrenMapSize", 4));
    final c<Http2LocalFlowController> d;
    final c<Http2RemoteFlowController> e;
    Promise<Void> h;
    final IntObjectMap<Http2Stream> a = new IntObjectHashMap();
    final h b = new h();
    final b c = new b();
    final List<Http2Connection.Listener> f = new ArrayList(4);
    final a g = new a(this.f);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface f {
        void a();
    }

    public DefaultHttp2Connection(boolean z) {
        this.d = new c<>(z);
        this.e = new c<>(!z);
        this.a.put(this.c.id(), (int) this.c);
    }

    final boolean a() {
        return this.h != null;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Future<Void> close(Promise<Void> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        Promise<Void> promise2 = this.h;
        if (promise2 == null) {
            this.h = promise;
        } else if (promise2 != promise) {
            if (!(promise instanceof ChannelPromise) || !((ChannelPromise) promise2).isVoid()) {
                this.h.addListener((GenericFutureListener<? extends Future<? super Void>>) new UnaryPromiseNotifier(promise));
            } else {
                this.h = promise;
            }
        }
        if (d()) {
            promise.trySuccess(null);
            return promise;
        }
        Iterator<IntObjectMap.PrimitiveEntry<Http2Stream>> it = this.a.entries().iterator();
        if (this.g.b()) {
            this.g.c();
            while (it.hasNext()) {
                try {
                    e eVar = (e) it.next().value();
                    if (eVar.id() != 0) {
                        eVar.a(it);
                    }
                } finally {
                    this.g.d();
                }
            }
        } else {
            while (it.hasNext()) {
                Http2Stream value = it.next().value();
                if (value.id() != 0) {
                    value.close();
                }
            }
        }
        return this.h;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public void addListener(Http2Connection.Listener listener) {
        this.f.add(listener);
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public void removeListener(Http2Connection.Listener listener) {
        this.f.remove(listener);
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public boolean isServer() {
        return this.d.isServer();
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Stream connectionStream() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Stream stream(int i2) {
        return this.a.get(i2);
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public boolean streamMayHaveExisted(int i2) {
        return this.e.mayHaveCreatedStream(i2) || this.d.mayHaveCreatedStream(i2);
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public int numActiveStreams() {
        return this.g.a();
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Stream forEachActiveStream(Http2StreamVisitor http2StreamVisitor) throws Http2Exception {
        return this.g.a(http2StreamVisitor);
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Connection.Endpoint<Http2LocalFlowController> local() {
        return this.d;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Connection.Endpoint<Http2RemoteFlowController> remote() {
        return this.e;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public boolean goAwayReceived() {
        return ((c) this.d).f >= 0;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public void goAwayReceived(final int i2, long j2, ByteBuf byteBuf) {
        this.d.c(i2);
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            try {
                this.f.get(i3).onGoAwayReceived(i2, j2, byteBuf);
            } catch (Throwable th) {
                i.error("Caught Throwable from listener onGoAwayReceived.", th);
            }
        }
        try {
            forEachActiveStream(new Http2StreamVisitor() { // from class: io.netty.handler.codec.http2.DefaultHttp2Connection.1
                @Override // io.netty.handler.codec.http2.Http2StreamVisitor
                public boolean visit(Http2Stream http2Stream) {
                    if (http2Stream.id() <= i2 || !DefaultHttp2Connection.this.d.isValidStreamId(http2Stream.id())) {
                        return true;
                    }
                    http2Stream.close();
                    return true;
                }
            });
        } catch (Http2Exception e2) {
            PlatformDependent.throwException(e2);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public boolean goAwaySent() {
        return ((c) this.e).f >= 0;
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public void goAwaySent(final int i2, long j2, ByteBuf byteBuf) {
        this.e.c(i2);
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            try {
                this.f.get(i3).onGoAwaySent(i2, j2, byteBuf);
            } catch (Throwable th) {
                i.error("Caught Throwable from listener onGoAwaySent.", th);
            }
        }
        try {
            forEachActiveStream(new Http2StreamVisitor() { // from class: io.netty.handler.codec.http2.DefaultHttp2Connection.2
                @Override // io.netty.handler.codec.http2.Http2StreamVisitor
                public boolean visit(Http2Stream http2Stream) {
                    if (http2Stream.id() <= i2 || !DefaultHttp2Connection.this.e.isValidStreamId(http2Stream.id())) {
                        return true;
                    }
                    http2Stream.close();
                    return true;
                }
            });
        } catch (Http2Exception e2) {
            PlatformDependent.throwException(e2);
        }
    }

    private boolean d() {
        return this.a.size() == 1;
    }

    void a(e eVar, Iterator<?> it) {
        if (eVar.parent().a(eVar)) {
            if (it == null) {
                this.a.remove(eVar.id());
            } else {
                it.remove();
            }
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                try {
                    this.f.get(i2).onStreamRemoved(eVar);
                } catch (Throwable th) {
                    i.error("Caught Throwable from listener onStreamRemoved.", th);
                }
            }
            if (this.h != null && d()) {
                this.h.trySuccess(null);
            }
        }
    }

    static Http2Stream.State a(int i2, Http2Stream.State state, boolean z, boolean z2) throws Http2Exception {
        switch (state) {
            case IDLE:
                return z2 ? z ? Http2Stream.State.HALF_CLOSED_LOCAL : Http2Stream.State.HALF_CLOSED_REMOTE : Http2Stream.State.OPEN;
            case RESERVED_LOCAL:
                return Http2Stream.State.HALF_CLOSED_REMOTE;
            case RESERVED_REMOTE:
                return Http2Stream.State.HALF_CLOSED_LOCAL;
            default:
                Http2Error http2Error = Http2Error.PROTOCOL_ERROR;
                throw Http2Exception.streamError(i2, http2Error, "Attempting to open a stream in an invalid state: " + state, new Object[0]);
        }
    }

    void a(Http2Stream http2Stream) {
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            try {
                this.f.get(i2).onStreamHalfClosed(http2Stream);
            } catch (Throwable th) {
                i.error("Caught Throwable from listener onStreamHalfClosed.", th);
            }
        }
    }

    void b(Http2Stream http2Stream) {
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            try {
                this.f.get(i2).onStreamClosed(http2Stream);
            } catch (Throwable th) {
                i.error("Caught Throwable from listener onStreamClosed.", th);
            }
        }
    }

    @Override // io.netty.handler.codec.http2.Http2Connection
    public Http2Connection.PropertyKey newKey() {
        return this.b.a();
    }

    final d a(Http2Connection.PropertyKey propertyKey) {
        return ((d) ObjectUtil.checkNotNull((d) propertyKey, "key")).a(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class e implements Http2Stream {
        static final /* synthetic */ boolean b = !DefaultHttp2Connection.class.desiredAssertionStatus();
        private final int a;
        private Http2Stream.State e;
        private e g;
        private boolean i;
        private final a d = new a();
        private short f = 16;
        private IntObjectMap<e> h = IntCollections.emptyMap();

        e(int i, Http2Stream.State state) {
            this.a = i;
            this.e = state;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final int id() {
            return this.a;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final Http2Stream.State state() {
            return this.e;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public boolean isResetSent() {
            return this.i;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream resetSent() {
            this.i = true;
            return this;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final <V> V setProperty(Http2Connection.PropertyKey propertyKey, V v) {
            return (V) this.d.a(DefaultHttp2Connection.this.a(propertyKey), v);
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final <V> V getProperty(Http2Connection.PropertyKey propertyKey) {
            return (V) this.d.a(DefaultHttp2Connection.this.a(propertyKey));
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final <V> V removeProperty(Http2Connection.PropertyKey propertyKey) {
            return (V) this.d.b(DefaultHttp2Connection.this.a(propertyKey));
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final boolean isRoot() {
            return this.g == null;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final short weight() {
            return this.f;
        }

        /* renamed from: b */
        public final e parent() {
            return this.g;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final boolean isDescendantOf(Http2Stream http2Stream) {
            for (Http2Stream b2 = parent(); b2 != null; b2 = b2.parent()) {
                if (b2 == http2Stream) {
                    return true;
                }
            }
            return false;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final boolean isLeaf() {
            return numChildren() == 0;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public final int numChildren() {
            return this.h.size();
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream forEachChild(Http2StreamVisitor http2StreamVisitor) throws Http2Exception {
            for (e eVar : this.h.values()) {
                if (!http2StreamVisitor.visit(eVar)) {
                    return eVar;
                }
            }
            return null;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream setPriority(int i, short s, boolean z) throws Http2Exception {
            ArrayList arrayList;
            int i2 = 0;
            if (s < 1 || s > 256) {
                throw new IllegalArgumentException(String.format("Invalid weight: %d.  Must be between %d and %d (inclusive).", Short.valueOf(s), (short) 1, Short.valueOf((short) Http2CodecUtil.MAX_WEIGHT)));
            }
            e eVar = (e) DefaultHttp2Connection.this.stream(i);
            if (eVar == null) {
                eVar = a().createIdleStream(i);
            } else if (this == eVar) {
                throw new IllegalArgumentException("A stream cannot depend on itself");
            }
            a(s);
            if (eVar != parent() || (z && eVar.numChildren() != 1)) {
                if (eVar.isDescendantOf(this)) {
                    arrayList = new ArrayList((z ? eVar.numChildren() : 0) + 2);
                    this.g.a(eVar, false, arrayList);
                } else {
                    if (z) {
                        i2 = eVar.numChildren();
                    }
                    arrayList = new ArrayList(i2 + 1);
                }
                eVar.a(this, z, arrayList);
                DefaultHttp2Connection.this.a(arrayList);
            }
            return this;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream open(boolean z) throws Http2Exception {
            this.e = DefaultHttp2Connection.a(this.a, this.e, d(), z);
            if (a().canOpenStream()) {
                c();
                return this;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Maximum active streams violated for this endpoint.", new Object[0]);
        }

        void c() {
            DefaultHttp2Connection.this.g.a(this);
        }

        Http2Stream a(Iterator<?> it) {
            if (this.e == Http2Stream.State.CLOSED) {
                return this;
            }
            this.e = Http2Stream.State.CLOSED;
            DefaultHttp2Connection.this.g.a(this, it);
            return this;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream close() {
            return a((Iterator<?>) null);
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream closeLocalSide() {
            switch (this.e) {
                case OPEN:
                    this.e = Http2Stream.State.HALF_CLOSED_LOCAL;
                    DefaultHttp2Connection.this.a(this);
                    break;
                case HALF_CLOSED_LOCAL:
                    break;
                default:
                    close();
                    break;
            }
            return this;
        }

        @Override // io.netty.handler.codec.http2.Http2Stream
        public Http2Stream closeRemoteSide() {
            int i = AnonymousClass3.a[this.e.ordinal()];
            if (i == 4) {
                this.e = Http2Stream.State.HALF_CLOSED_REMOTE;
                DefaultHttp2Connection.this.a(this);
            } else if (i != 6) {
                close();
            }
            return this;
        }

        private void e() {
            if (this.h == IntCollections.emptyMap()) {
                f();
            }
        }

        private void f() {
            this.h = new IntObjectHashMap(DefaultHttp2Connection.j);
        }

        c<? extends Http2FlowController> a() {
            return DefaultHttp2Connection.this.d.isValidStreamId(this.a) ? DefaultHttp2Connection.this.d : DefaultHttp2Connection.this.e;
        }

        final boolean d() {
            return DefaultHttp2Connection.this.d.isValidStreamId(this.a);
        }

        final void a(short s) {
            short s2 = this.f;
            if (s != s2) {
                this.f = s;
                for (int i = 0; i < DefaultHttp2Connection.this.f.size(); i++) {
                    try {
                        DefaultHttp2Connection.this.f.get(i).onWeightChanged(this, s2);
                    } catch (Throwable th) {
                        DefaultHttp2Connection.i.error("Caught Throwable from listener onWeightChanged.", th);
                    }
                }
            }
        }

        private IntObjectMap<e> b(e eVar) {
            e remove = this.h.remove(eVar.id());
            IntObjectMap<e> intObjectMap = this.h;
            f();
            if (remove != null) {
                this.h.put(remove.id(), (int) remove);
            }
            return intObjectMap;
        }

        final void a(e eVar, boolean z, List<g> list) {
            e b2 = eVar.parent();
            if (b2 != this) {
                list.add(new g(eVar, b2));
                DefaultHttp2Connection.this.a(eVar, this);
                eVar.g = this;
                if (b2 != null) {
                    b2.h.remove(eVar.id());
                }
                e();
                e put = this.h.put(eVar.id(), (int) eVar);
                if (!b && put != null) {
                    throw new AssertionError("A stream with the same stream ID was already in the child map.");
                }
            }
            if (z && !this.h.isEmpty()) {
                for (e eVar2 : b(eVar).values()) {
                    eVar.a(eVar2, false, list);
                }
            }
        }

        final boolean a(e eVar) {
            if (this.h.remove(eVar.id()) == null) {
                return false;
            }
            ArrayList arrayList = new ArrayList(eVar.numChildren() + 1);
            arrayList.add(new g(eVar, eVar.parent()));
            DefaultHttp2Connection.this.a(eVar, (Http2Stream) null);
            eVar.g = null;
            for (e eVar2 : eVar.h.values()) {
                a(eVar2, false, arrayList);
            }
            DefaultHttp2Connection.this.a(arrayList);
            return true;
        }

        /* loaded from: classes4.dex */
        private class a {
            Object[] a;

            private a() {
                this.a = EmptyArrays.EMPTY_OBJECTS;
            }

            <V> V a(d dVar, V v) {
                a(dVar.a);
                V v2 = (V) this.a[dVar.a];
                this.a[dVar.a] = v;
                return v2;
            }

            <V> V a(d dVar) {
                int i = dVar.a;
                Object[] objArr = this.a;
                if (i >= objArr.length) {
                    return null;
                }
                return (V) objArr[dVar.a];
            }

            <V> V b(d dVar) {
                int i = dVar.a;
                Object[] objArr = this.a;
                if (i >= objArr.length) {
                    return null;
                }
                V v = (V) objArr[dVar.a];
                this.a[dVar.a] = null;
                return v;
            }

            void a(int i) {
                Object[] objArr = this.a;
                if (i >= objArr.length) {
                    this.a = Arrays.copyOf(objArr, DefaultHttp2Connection.this.b.b());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class g {
        private final Http2Stream a;
        private final Http2Stream b;

        g(Http2Stream http2Stream, Http2Stream http2Stream2) {
            this.a = http2Stream;
            this.b = http2Stream2;
        }

        public void a(Http2Connection.Listener listener) {
            try {
                listener.onPriorityTreeParentChanged(this.a, this.b);
            } catch (Throwable th) {
                DefaultHttp2Connection.i.error("Caught Throwable from listener onPriorityTreeParentChanged.", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<g> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            g gVar = list.get(i2);
            for (int i3 = 0; i3 < this.f.size(); i3++) {
                gVar.a(this.f.get(i3));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Http2Stream http2Stream, Http2Stream http2Stream2) {
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            try {
                this.f.get(i2).onPriorityTreeParentChanging(http2Stream, http2Stream2);
            } catch (Throwable th) {
                i.error("Caught Throwable from listener onPriorityTreeParentChanging.", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends e {
        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e
        c<? extends Http2FlowController> a() {
            return null;
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public boolean isResetSent() {
            return false;
        }

        b() {
            super(0, Http2Stream.State.IDLE);
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream resetSent() {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream setPriority(int i, short s, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream open(boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream close() {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream closeLocalSide() {
            throw new UnsupportedOperationException();
        }

        @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.e, io.netty.handler.codec.http2.Http2Stream
        public Http2Stream closeRemoteSide() {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class c<F extends Http2FlowController> implements Http2Connection.Endpoint<F> {
        int a;
        private final boolean c;
        private int d;
        private int e;
        private int f = -1;
        private boolean g;
        private F h;
        private int i;

        c(boolean z) {
            this.g = true;
            this.c = z;
            if (z) {
                this.d = 2;
                this.e = 0;
            } else {
                this.d = 1;
                this.e = 1;
            }
            this.g = true ^ z;
            this.i = Integer.MAX_VALUE;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public int incrementAndGetNextStreamId() {
            int i = this.e;
            if (i < 0) {
                return i;
            }
            int i2 = i + 2;
            this.e = i2;
            return i2;
        }

        private void b(int i) {
            int i2 = this.e;
            if (i > i2 && i2 >= 0) {
                this.e = i;
            }
            this.d = i + 2;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean isValidStreamId(int i) {
            return i > 0 && this.c == ((i & 1) == 0);
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean mayHaveCreatedStream(int i) {
            return isValidStreamId(i) && i <= lastStreamCreated();
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean canOpenStream() {
            return this.a + 1 <= this.i;
        }

        private e a(int i, Http2Stream.State state) throws Http2Exception {
            b(i, state);
            e eVar = new e(i, state);
            b(i);
            a(eVar);
            return eVar;
        }

        /* renamed from: a */
        public e createIdleStream(int i) throws Http2Exception {
            return a(i, Http2Stream.State.IDLE);
        }

        /* renamed from: a */
        public e createStream(int i, boolean z) throws Http2Exception {
            e a = a(i, DefaultHttp2Connection.a(i, Http2Stream.State.IDLE, a(), z));
            a.c();
            return a;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean created(Http2Stream http2Stream) {
            return (http2Stream instanceof e) && ((e) http2Stream).a() == this;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean isServer() {
            return this.c;
        }

        /* renamed from: a */
        public e reservePushStream(int i, Http2Stream http2Stream) throws Http2Exception {
            if (http2Stream == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Parent stream missing", new Object[0]);
            } else if (!a() ? !http2Stream.state().remoteSideOpen() : !http2Stream.state().localSideOpen()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d is not open for sending push promise", Integer.valueOf(http2Stream.id()));
            } else if (opposite().allowPushTo()) {
                Http2Stream.State state = a() ? Http2Stream.State.RESERVED_LOCAL : Http2Stream.State.RESERVED_REMOTE;
                b(i, state);
                e eVar = new e(i, state);
                b(i);
                a(eVar);
                return eVar;
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server push not allowed to opposite endpoint.", new Object[0]);
            }
        }

        private void a(e eVar) {
            DefaultHttp2Connection.this.a.put(eVar.id(), (int) eVar);
            ArrayList arrayList = new ArrayList(1);
            DefaultHttp2Connection.this.c.a(eVar, false, arrayList);
            for (int i = 0; i < DefaultHttp2Connection.this.f.size(); i++) {
                try {
                    DefaultHttp2Connection.this.f.get(i).onStreamAdded(eVar);
                } catch (Throwable th) {
                    DefaultHttp2Connection.i.error("Caught Throwable from listener onStreamAdded.", th);
                }
            }
            DefaultHttp2Connection.this.a(arrayList);
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public void allowPushTo(boolean z) {
            if (!z || !this.c) {
                this.g = z;
                return;
            }
            throw new IllegalArgumentException("Servers do not allow push");
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public boolean allowPushTo() {
            return this.g;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public int numActiveStreams() {
            return this.a;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public int maxActiveStreams() {
            return this.i;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public void maxActiveStreams(int i) {
            this.i = i;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public int lastStreamCreated() {
            int i = this.d;
            if (i > 1) {
                return i - 2;
            }
            return 0;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public int lastStreamKnownByPeer() {
            return this.f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.f = i;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public F flowController() {
            return this.h;
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public void flowController(F f) {
            this.h = (F) ((Http2FlowController) ObjectUtil.checkNotNull(f, "flowController"));
        }

        @Override // io.netty.handler.codec.http2.Http2Connection.Endpoint
        public Http2Connection.Endpoint<? extends Http2FlowController> opposite() {
            return a() ? DefaultHttp2Connection.this.e : DefaultHttp2Connection.this.d;
        }

        private void b(int i, Http2Stream.State state) throws Http2Exception {
            if (DefaultHttp2Connection.this.goAwayReceived() && i > DefaultHttp2Connection.this.d.lastStreamKnownByPeer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Cannot create stream %d since this endpoint has received a GOAWAY frame with last stream id %d.", Integer.valueOf(i), Integer.valueOf(DefaultHttp2Connection.this.d.lastStreamKnownByPeer()));
            } else if (i < 0) {
                throw new Http2NoMoreStreamIdsException();
            } else if (!isValidStreamId(i)) {
                Http2Error http2Error = Http2Error.PROTOCOL_ERROR;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(i);
                objArr[1] = this.c ? "server" : "client";
                throw Http2Exception.connectionError(http2Error, "Request stream %d is not correct for %s connection", objArr);
            } else {
                int i2 = this.d;
                if (i < i2) {
                    throw Http2Exception.closedStreamError(Http2Error.PROTOCOL_ERROR, "Request stream %d is behind the next expected stream %d", Integer.valueOf(i), Integer.valueOf(this.d));
                } else if (i2 <= 0) {
                    throw Http2Exception.connectionError(Http2Error.REFUSED_STREAM, "Stream IDs are exhausted for this endpoint.", new Object[0]);
                } else if ((state.localSideOpen() || state.remoteSideOpen()) && !canOpenStream()) {
                    throw Http2Exception.connectionError(Http2Error.REFUSED_STREAM, "Maximum active streams violated for this endpoint.", new Object[0]);
                } else if (DefaultHttp2Connection.this.a()) {
                    throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Attempted to create stream id %d after connection was closed", Integer.valueOf(i));
                }
            }
        }

        private boolean a() {
            return this == DefaultHttp2Connection.this.d;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a {
        private final List<Http2Connection.Listener> b;
        private final Queue<f> c = new ArrayDeque(4);
        private final Set<Http2Stream> d = new LinkedHashSet();
        private int e;

        public a(List<Http2Connection.Listener> list) {
            this.b = list;
        }

        public int a() {
            return this.d.size();
        }

        public void a(final e eVar) {
            if (b()) {
                b(eVar);
            } else {
                this.c.add(new f() { // from class: io.netty.handler.codec.http2.DefaultHttp2Connection.a.1
                    @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.f
                    public void a() {
                        a.this.b(eVar);
                    }
                });
            }
        }

        public void a(final e eVar, final Iterator<?> it) {
            if (b() || it != null) {
                b(eVar, it);
            } else {
                this.c.add(new f() { // from class: io.netty.handler.codec.http2.DefaultHttp2Connection.a.2
                    @Override // io.netty.handler.codec.http2.DefaultHttp2Connection.f
                    public void a() {
                        if (eVar.parent() != null) {
                            a.this.b(eVar, it);
                        }
                    }
                });
            }
        }

        public Http2Stream a(Http2StreamVisitor http2StreamVisitor) throws Http2Exception {
            c();
            try {
                for (Http2Stream http2Stream : this.d) {
                    if (!http2StreamVisitor.visit(http2Stream)) {
                        return http2Stream;
                    }
                }
                return null;
            } finally {
                d();
            }
        }

        void b(e eVar) {
            if (this.d.add(eVar)) {
                eVar.a().a++;
                for (int i = 0; i < this.b.size(); i++) {
                    try {
                        this.b.get(i).onStreamActive(eVar);
                    } catch (Throwable th) {
                        DefaultHttp2Connection.i.error("Caught Throwable from listener onStreamActive.", th);
                    }
                }
            }
        }

        void b(e eVar, Iterator<?> it) {
            if (this.d.remove(eVar)) {
                c<? extends Http2FlowController> a = eVar.a();
                a.a--;
                DefaultHttp2Connection.this.b(eVar);
            }
            DefaultHttp2Connection.this.a(eVar, it);
        }

        boolean b() {
            return this.e == 0;
        }

        void c() {
            this.e++;
        }

        void d() {
            this.e--;
            if (b()) {
                while (true) {
                    f poll = this.c.poll();
                    if (poll != null) {
                        try {
                            poll.a();
                        } catch (Throwable th) {
                            DefaultHttp2Connection.i.error("Caught Throwable while processing pending ActiveStreams$Event.", th);
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class d implements Http2Connection.PropertyKey {
        final int a;

        d(int i) {
            this.a = i;
        }

        d a(Http2Connection http2Connection) {
            if (http2Connection == DefaultHttp2Connection.this) {
                return this;
            }
            throw new IllegalArgumentException("Using a key that was not created by this connection");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class h {
        final List<d> a;

        private h() {
            this.a = new ArrayList(4);
        }

        d a() {
            d dVar = new d(this.a.size());
            this.a.add(dVar);
            return dVar;
        }

        int b() {
            return this.a.size();
        }
    }
}
