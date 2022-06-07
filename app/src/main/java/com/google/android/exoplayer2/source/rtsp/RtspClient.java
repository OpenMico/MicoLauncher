package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.os.Handler;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspClient;
import com.google.android.exoplayer2.source.rtsp.RtspHeaders;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.source.rtsp.e;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.xiaomi.infra.galaxy.fds.Common;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.SocketFactory;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class RtspClient implements Closeable {
    private final SessionInfoListener a;
    private final PlaybackEventListener b;
    private final Uri c;
    @Nullable
    private final RtspMessageUtil.RtspAuthUserInfo d;
    private final String e;
    @Nullable
    private String j;
    @Nullable
    private a k;
    @Nullable
    private c l;
    private boolean m;
    private boolean n;
    private final ArrayDeque<e.c> f = new ArrayDeque<>();
    private final SparseArray<RtspRequest> g = new SparseArray<>();
    private final c h = new c();
    private long o = C.TIME_UNSET;
    private RtspMessageChannel i = new RtspMessageChannel(new b());

    /* loaded from: classes2.dex */
    public interface PlaybackEventListener {
        void onPlaybackError(RtspMediaSource.RtspPlaybackException rtspPlaybackException);

        void onPlaybackStarted(long j, ImmutableList<l> immutableList);

        void onRtspSetupCompleted();
    }

    /* loaded from: classes2.dex */
    public interface SessionInfoListener {
        void onSessionTimelineRequestFailed(String str, @Nullable Throwable th);

        void onSessionTimelineUpdated(j jVar, ImmutableList<f> immutableList);
    }

    public RtspClient(SessionInfoListener sessionInfoListener, PlaybackEventListener playbackEventListener, String str, Uri uri) {
        this.a = sessionInfoListener;
        this.b = playbackEventListener;
        this.c = RtspMessageUtil.a(uri);
        this.d = RtspMessageUtil.b(uri);
        this.e = str;
    }

    public void a() throws IOException {
        try {
            this.i.a(a(this.c));
            this.h.a(this.c, this.j);
        } catch (IOException e) {
            Util.closeQuietly(this.i);
            throw e;
        }
    }

    public void a(List<e.c> list) {
        this.f.addAll(list);
        c();
    }

    public void a(long j) {
        this.h.a(this.c, j, (String) Assertions.checkNotNull(this.j));
    }

    public void b(long j) {
        this.h.d(this.c, (String) Assertions.checkNotNull(this.j));
        this.o = j;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        a aVar = this.k;
        if (aVar != null) {
            aVar.close();
            this.k = null;
            this.h.c(this.c, (String) Assertions.checkNotNull(this.j));
        }
        this.i.close();
    }

    public void b() {
        try {
            close();
            this.i = new RtspMessageChannel(new b());
            this.i.a(a(this.c));
            this.j = null;
            this.n = false;
            this.l = null;
        } catch (IOException e) {
            this.b.onPlaybackError(new RtspMediaSource.RtspPlaybackException(e));
        }
    }

    public void a(int i, RtspMessageChannel.InterleavedBinaryDataListener interleavedBinaryDataListener) {
        this.i.a(i, interleavedBinaryDataListener);
    }

    public void c() {
        e.c pollFirst = this.f.pollFirst();
        if (pollFirst == null) {
            this.b.onRtspSetupCompleted();
        } else {
            this.h.a(pollFirst.c(), pollFirst.b(), this.j);
        }
    }

    private static Socket a(Uri uri) throws IOException {
        Assertions.checkArgument(uri.getHost() != null);
        return SocketFactory.getDefault().createSocket((String) Assertions.checkNotNull(uri.getHost()), uri.getPort() > 0 ? uri.getPort() : 554);
    }

    public void a(Throwable th) {
        RtspMediaSource.RtspPlaybackException rtspPlaybackException;
        if (th instanceof RtspMediaSource.RtspPlaybackException) {
            rtspPlaybackException = (RtspMediaSource.RtspPlaybackException) th;
        } else {
            rtspPlaybackException = new RtspMediaSource.RtspPlaybackException(th);
        }
        if (this.m) {
            this.b.onPlaybackError(rtspPlaybackException);
        } else {
            this.a.onSessionTimelineRequestFailed(Strings.nullToEmpty(th.getMessage()), th);
        }
    }

    public static boolean c(List<Integer> list) {
        return list.isEmpty() || list.contains(2);
    }

    public static ImmutableList<f> b(SessionDescription sessionDescription, Uri uri) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < sessionDescription.b.size(); i++) {
            MediaDescription mediaDescription = sessionDescription.b.get(i);
            if (RtpPayloadFormat.isFormatSupported(mediaDescription)) {
                builder.add((ImmutableList.Builder) new f(mediaDescription, uri));
            }
        }
        return builder.build();
    }

    /* loaded from: classes2.dex */
    public final class c {
        private int b;
        private RtspRequest c;

        private c() {
            RtspClient.this = r1;
        }

        public void a(Uri uri, @Nullable String str) {
            a(a(4, str, ImmutableMap.of(), uri));
        }

        public void b(Uri uri, @Nullable String str) {
            a(a(2, str, ImmutableMap.of(), uri));
        }

        public void a(Uri uri, String str, @Nullable String str2) {
            a(a(10, str2, ImmutableMap.of(RtspHeaders.Names.TRANSPORT, str), uri));
        }

        public void a(Uri uri, long j, String str) {
            a(a(6, str, ImmutableMap.of("Range", j.a(j)), uri));
        }

        public void c(Uri uri, String str) {
            a(a(12, str, ImmutableMap.of(), uri));
        }

        public void d(Uri uri, String str) {
            a(a(5, str, ImmutableMap.of(), uri));
        }

        public void a() {
            Assertions.checkStateNotNull(this.c);
            ImmutableListMultimap<String, String> a = this.c.c.a();
            HashMap hashMap = new HashMap();
            for (String str : a.keySet()) {
                if (!str.equals(RtspHeaders.Names.CSEQ) && !str.equals("User-Agent") && !str.equals(RtspHeaders.Names.SESSION) && !str.equals("Authorization")) {
                    hashMap.put(str, (String) Iterables.getLast(a.get((ImmutableListMultimap<String, String>) str)));
                }
            }
            a(a(this.c.b, RtspClient.this.j, hashMap, this.c.a));
        }

        private RtspRequest a(int i, @Nullable String str, Map<String, String> map, Uri uri) {
            RtspHeaders.Builder builder = new RtspHeaders.Builder();
            int i2 = this.b;
            this.b = i2 + 1;
            builder.add(RtspHeaders.Names.CSEQ, String.valueOf(i2));
            builder.add("User-Agent", RtspClient.this.e);
            if (str != null) {
                builder.add(RtspHeaders.Names.SESSION, str);
            }
            if (RtspClient.this.l != null) {
                Assertions.checkStateNotNull(RtspClient.this.d);
                try {
                    builder.add("Authorization", RtspClient.this.l.a(RtspClient.this.d, uri, i));
                } catch (ParserException e) {
                    RtspClient.this.a(new RtspMediaSource.RtspPlaybackException(e));
                }
            }
            builder.addAll(map);
            return new RtspRequest(uri, i, builder.build(), "");
        }

        private void a(RtspRequest rtspRequest) {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(rtspRequest.c.a(RtspHeaders.Names.CSEQ)));
            Assertions.checkState(RtspClient.this.g.get(parseInt) == null);
            RtspClient.this.g.append(parseInt, rtspRequest);
            RtspClient.this.i.a(RtspMessageUtil.a(rtspRequest));
            this.c = rtspRequest;
        }
    }

    /* loaded from: classes2.dex */
    public final class b implements RtspMessageChannel.MessageListener {
        private final Handler b = Util.createHandlerForCurrentLooper();

        public b() {
            RtspClient.this = r1;
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.MessageListener
        public void onRtspMessageReceived(final List<String> list) {
            this.b.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$RtspClient$b$9gGJR7zKzlQFb58x4VdKjaCOjI4
                @Override // java.lang.Runnable
                public final void run() {
                    RtspClient.b.this.b(list);
                }
            });
        }

        /* renamed from: a */
        public void b(List<String> list) {
            j jVar;
            ImmutableList<l> immutableList;
            i b = RtspMessageUtil.b(list);
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(b.b.a(RtspHeaders.Names.CSEQ)));
            RtspRequest rtspRequest = (RtspRequest) RtspClient.this.g.get(parseInt);
            if (rtspRequest != null) {
                RtspClient.this.g.remove(parseInt);
                int i = rtspRequest.b;
                try {
                    int i2 = b.a;
                    if (i2 != 200) {
                        if (i2 == 401 && RtspClient.this.d != null && !RtspClient.this.n) {
                            String a = b.b.a("WWW-Authenticate");
                            if (a != null) {
                                RtspClient.this.l = RtspMessageUtil.g(a);
                                RtspClient.this.h.a();
                                RtspClient.this.n = true;
                                return;
                            }
                            throw ParserException.createForMalformedManifest("Missing WWW-Authenticate header in a 401 response.", null);
                        }
                        RtspClient rtspClient = RtspClient.this;
                        String a2 = RtspMessageUtil.a(i);
                        int i3 = b.a;
                        StringBuilder sb = new StringBuilder(String.valueOf(a2).length() + 12);
                        sb.append(a2);
                        sb.append(StringUtils.SPACE);
                        sb.append(i3);
                        rtspClient.a(new RtspMediaSource.RtspPlaybackException(sb.toString()));
                        return;
                    }
                    switch (i) {
                        case 1:
                        case 3:
                        case 7:
                        case 8:
                        case 9:
                        case 11:
                        case 12:
                            return;
                        case 2:
                            a(new d(b.a, m.a(b.c)));
                            return;
                        case 4:
                            a(new g(b.a, RtspMessageUtil.e(b.b.a(RtspHeaders.Names.PUBLIC))));
                            return;
                        case 5:
                            a();
                            return;
                        case 6:
                            String a3 = b.b.a("Range");
                            if (a3 == null) {
                                jVar = j.a;
                            } else {
                                jVar = j.a(a3);
                            }
                            String a4 = b.b.a(RtspHeaders.Names.RTP_INFO);
                            if (a4 == null) {
                                immutableList = ImmutableList.of();
                            } else {
                                immutableList = l.a(a4, RtspClient.this.c);
                            }
                            a(new h(b.a, jVar, immutableList));
                            return;
                        case 10:
                            String a5 = b.b.a(RtspHeaders.Names.SESSION);
                            String a6 = b.b.a(RtspHeaders.Names.TRANSPORT);
                            if (a5 == null || a6 == null) {
                                throw ParserException.createForMalformedManifest("Missing mandatory session or transport header", null);
                            }
                            a(new k(b.a, RtspMessageUtil.f(a5), a6));
                            return;
                        default:
                            throw new IllegalStateException();
                    }
                } catch (ParserException e) {
                    RtspClient.this.a(new RtspMediaSource.RtspPlaybackException(e));
                }
            }
        }

        private void a(g gVar) {
            if (RtspClient.this.k == null) {
                if (RtspClient.c(gVar.b)) {
                    RtspClient.this.h.b(RtspClient.this.c, RtspClient.this.j);
                } else {
                    RtspClient.this.a.onSessionTimelineRequestFailed("DESCRIBE not supported.", null);
                }
            }
        }

        private void a(d dVar) {
            j jVar = j.a;
            String str = dVar.b.a.get(Common.RANGE);
            if (str != null) {
                try {
                    jVar = j.a(str);
                } catch (ParserException e) {
                    RtspClient.this.a.onSessionTimelineRequestFailed("SDP format error.", e);
                    return;
                }
            }
            ImmutableList<f> b = RtspClient.b(dVar.b, RtspClient.this.c);
            if (b.isEmpty()) {
                RtspClient.this.a.onSessionTimelineRequestFailed("No playable track.", null);
                return;
            }
            RtspClient.this.a.onSessionTimelineUpdated(jVar, b);
            RtspClient.this.m = true;
        }

        private void a(k kVar) {
            RtspClient.this.j = kVar.b.sessionId;
            RtspClient.this.c();
        }

        private void a(h hVar) {
            if (RtspClient.this.k == null) {
                RtspClient rtspClient = RtspClient.this;
                rtspClient.k = new a(30000L);
                RtspClient.this.k.a();
            }
            RtspClient.this.b.onPlaybackStarted(C.msToUs(hVar.b.b), hVar.c);
            RtspClient.this.o = C.TIME_UNSET;
        }

        private void a() {
            if (RtspClient.this.o != C.TIME_UNSET) {
                RtspClient rtspClient = RtspClient.this;
                rtspClient.a(C.usToMs(rtspClient.o));
            }
        }
    }

    /* loaded from: classes2.dex */
    public final class a implements Closeable, Runnable {
        private final Handler b = Util.createHandlerForCurrentLooper();
        private final long c;
        private boolean d;

        public a(long j) {
            RtspClient.this = r1;
            this.c = j;
        }

        public void a() {
            if (!this.d) {
                this.d = true;
                this.b.postDelayed(this, this.c);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            RtspClient.this.h.a(RtspClient.this.c, RtspClient.this.j);
            this.b.postDelayed(this, this.c);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.d = false;
            this.b.removeCallbacks(this);
        }
    }
}
