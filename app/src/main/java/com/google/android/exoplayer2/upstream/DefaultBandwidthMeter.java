package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.NetworkTypeObserver;
import com.google.android.exoplayer2.util.SlidingPercentile;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    @Nullable
    private static DefaultBandwidthMeter a;
    private final ImmutableMap<Integer, Long> b;
    private final BandwidthMeter.EventListener.EventDispatcher c;
    private final SlidingPercentile d;
    private final Clock e;
    private final boolean f;
    private int g;
    private long h;
    private long i;
    private int j;
    private long k;
    private long l;
    private long m;
    private long n;
    private boolean o;
    private int p;
    public static final ImmutableListMultimap<String, Integer> DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS = a();
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI = ImmutableList.of(6200000L, 3900000L, 2300000L, 1300000L, 620000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_2G = ImmutableList.of(248000L, 160000L, 142000L, 127000L, 113000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_3G = ImmutableList.of(2200000L, 1300000L, 950000L, 760000L, 520000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_4G = ImmutableList.of(4400000L, 2300000L, 1500000L, 1100000L, 640000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA = ImmutableList.of(10000000L, 7200000L, 5000000L, 2700000L, 1600000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA = ImmutableList.of(2600000L, 2200000L, 2000000L, 1500000L, 470000L);

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public TransferListener getTransferListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z) {
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        @Nullable
        private final Context a;
        private Map<Integer, Long> b;
        private int c;
        private Clock d;
        private boolean e;

        public Builder(Context context) {
            this.a = context == null ? null : context.getApplicationContext();
            this.b = a(Util.getCountryCode(context));
            this.c = 2000;
            this.d = Clock.DEFAULT;
            this.e = true;
        }

        public Builder setSlidingWindowMaxWeight(int i) {
            this.c = i;
            return this;
        }

        public Builder setInitialBitrateEstimate(long j) {
            for (Integer num : this.b.keySet()) {
                setInitialBitrateEstimate(num.intValue(), j);
            }
            return this;
        }

        public Builder setInitialBitrateEstimate(int i, long j) {
            this.b.put(Integer.valueOf(i), Long.valueOf(j));
            return this;
        }

        public Builder setInitialBitrateEstimate(String str) {
            this.b = a(Ascii.toUpperCase(str));
            return this;
        }

        public Builder setClock(Clock clock) {
            this.d = clock;
            return this;
        }

        public Builder setResetOnNetworkTypeChange(boolean z) {
            this.e = z;
            return this;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.a, this.b, this.c, this.d, this.e);
        }

        private static Map<Integer, Long> a(String str) {
            ImmutableList<Integer> b = b(str);
            HashMap hashMap = new HashMap(8);
            hashMap.put(0, 1000000L);
            hashMap.put(2, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI.get(b.get(0).intValue()));
            hashMap.put(3, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_2G.get(b.get(1).intValue()));
            hashMap.put(4, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_3G.get(b.get(2).intValue()));
            hashMap.put(5, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_4G.get(b.get(3).intValue()));
            hashMap.put(10, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA.get(b.get(4).intValue()));
            hashMap.put(9, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA.get(b.get(5).intValue()));
            hashMap.put(7, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI.get(b.get(0).intValue()));
            return hashMap;
        }

        private static ImmutableList<Integer> b(String str) {
            ImmutableList<Integer> immutableList = DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS.get((ImmutableListMultimap<String, Integer>) str);
            return immutableList.isEmpty() ? ImmutableList.of(2, 2, 2, 2, 2, 2) : immutableList;
        }
    }

    public static synchronized DefaultBandwidthMeter getSingletonInstance(Context context) {
        DefaultBandwidthMeter defaultBandwidthMeter;
        synchronized (DefaultBandwidthMeter.class) {
            if (a == null) {
                a = new Builder(context).build();
            }
            defaultBandwidthMeter = a;
        }
        return defaultBandwidthMeter;
    }

    @Deprecated
    public DefaultBandwidthMeter() {
        this(null, ImmutableMap.of(), 2000, Clock.DEFAULT, false);
    }

    private DefaultBandwidthMeter(@Nullable Context context, Map<Integer, Long> map, int i, Clock clock, boolean z) {
        this.b = ImmutableMap.copyOf(map);
        this.c = new BandwidthMeter.EventListener.EventDispatcher();
        this.d = new SlidingPercentile(i);
        this.e = clock;
        this.f = z;
        if (context != null) {
            NetworkTypeObserver instance = NetworkTypeObserver.getInstance(context);
            this.j = instance.getNetworkType();
            this.m = b(this.j);
            instance.register(new NetworkTypeObserver.Listener() { // from class: com.google.android.exoplayer2.upstream.-$$Lambda$DefaultBandwidthMeter$xUAGSLQ7YfQ7h3oYsHw8x3HE0NQ
                @Override // com.google.android.exoplayer2.util.NetworkTypeObserver.Listener
                public final void onNetworkTypeChanged(int i2) {
                    DefaultBandwidthMeter.this.a(i2);
                }
            });
            return;
        }
        this.j = 0;
        this.m = b(0);
    }

    public synchronized void setNetworkTypeOverride(int i) {
        this.p = i;
        this.o = true;
        a(i);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public synchronized long getBitrateEstimate() {
        return this.m;
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.c.addListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.c.removeListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (a(dataSpec, z)) {
            if (this.g == 0) {
                this.h = this.e.elapsedRealtime();
            }
            this.g++;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z, int i) {
        if (a(dataSpec, z)) {
            this.i += i;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (a(dataSpec, z)) {
            Assertions.checkState(this.g > 0);
            long elapsedRealtime = this.e.elapsedRealtime();
            int i = (int) (elapsedRealtime - this.h);
            this.k += i;
            this.l += this.i;
            if (i > 0) {
                this.d.addSample((int) Math.sqrt(this.i), (((float) this.i) * 8000.0f) / i);
                if (this.k >= SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS || this.l >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    this.m = this.d.getPercentile(0.5f);
                }
                a(i, this.i, this.m);
                this.h = elapsedRealtime;
                this.i = 0L;
            }
            this.g--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(int i) {
        if (this.j == 0 || this.f) {
            if (this.o) {
                i = this.p;
            }
            if (this.j != i) {
                this.j = i;
                if (!(i == 1 || i == 0 || i == 8)) {
                    this.m = b(i);
                    long elapsedRealtime = this.e.elapsedRealtime();
                    a(this.g > 0 ? (int) (elapsedRealtime - this.h) : 0, this.i, this.m);
                    this.h = elapsedRealtime;
                    this.i = 0L;
                    this.l = 0L;
                    this.k = 0L;
                    this.d.reset();
                }
            }
        }
    }

    private void a(int i, long j, long j2) {
        if (i != 0 || j != 0 || j2 != this.n) {
            this.n = j2;
            this.c.bandwidthSample(i, j, j2);
        }
    }

    private long b(int i) {
        Long l = this.b.get(Integer.valueOf(i));
        if (l == null) {
            l = this.b.get(0);
        }
        if (l == null) {
            l = 1000000L;
        }
        return l.longValue();
    }

    private static boolean a(DataSpec dataSpec, boolean z) {
        return z && !dataSpec.isFlagSet(8);
    }

    private static ImmutableListMultimap<String, Integer> a() {
        return ImmutableListMultimap.builder().putAll((ImmutableListMultimap.Builder) "AD", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "AE", (Object[]) new Integer[]{1, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "AF", (Object[]) new Integer[]{4, 4, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "AG", (Object[]) new Integer[]{4, 2, 1, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "AI", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "AL", (Object[]) new Integer[]{1, 1, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "AM", (Object[]) new Integer[]{2, 2, 1, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "AO", (Object[]) new Integer[]{3, 4, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "AR", (Object[]) new Integer[]{2, 4, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "AS", (Object[]) new Integer[]{2, 2, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "AT", (Object[]) new Integer[]{0, 1, 0, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "AU", (Object[]) new Integer[]{0, 2, 0, 1, 1, 2}).putAll((ImmutableListMultimap.Builder) "AW", (Object[]) new Integer[]{1, 2, 0, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "AX", (Object[]) new Integer[]{0, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "AZ", (Object[]) new Integer[]{3, 3, 3, 4, 4, 2}).putAll((ImmutableListMultimap.Builder) "BA", (Object[]) new Integer[]{1, 1, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "BB", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) PlaybackControllerInfo.RESOLUTION_BD, (Object[]) new Integer[]{2, 0, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "BE", (Object[]) new Integer[]{0, 0, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "BF", (Object[]) new Integer[]{4, 4, 4, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BG", (Object[]) new Integer[]{0, 1, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "BH", (Object[]) new Integer[]{1, 0, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "BI", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "BJ", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "BL", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BM", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "BN", (Object[]) new Integer[]{3, 2, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "BO", (Object[]) new Integer[]{1, 2, 4, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BQ", (Object[]) new Integer[]{1, 2, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BR", (Object[]) new Integer[]{2, 4, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BS", (Object[]) new Integer[]{2, 2, 1, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) PlayerEvent.MUSIC_SOURCE_BT, (Object[]) new Integer[]{3, 0, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BW", (Object[]) new Integer[]{3, 4, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "BY", (Object[]) new Integer[]{1, 1, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "BZ", (Object[]) new Integer[]{2, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CA", (Object[]) new Integer[]{0, 3, 1, 2, 4, 2}).putAll((ImmutableListMultimap.Builder) "CD", (Object[]) new Integer[]{4, 2, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "CF", (Object[]) new Integer[]{4, 2, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CG", (Object[]) new Integer[]{3, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CH", (Object[]) new Integer[]{0, 0, 0, 0, 1, 2}).putAll((ImmutableListMultimap.Builder) "CI", (Object[]) new Integer[]{3, 3, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "CK", (Object[]) new Integer[]{2, 2, 3, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "CL", (Object[]) new Integer[]{1, 1, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CM", (Object[]) new Integer[]{3, 4, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CN", (Object[]) new Integer[]{2, 2, 2, 1, 3, 2}).putAll((ImmutableListMultimap.Builder) "CO", (Object[]) new Integer[]{2, 3, 4, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CR", (Object[]) new Integer[]{2, 3, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "CU", (Object[]) new Integer[]{4, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "CV", (Object[]) new Integer[]{2, 3, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "CW", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "CY", (Object[]) new Integer[]{1, 1, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "CZ", (Object[]) new Integer[]{0, 1, 0, 0, 1, 2}).putAll((ImmutableListMultimap.Builder) "DE", (Object[]) new Integer[]{0, 0, 1, 1, 0, 2}).putAll((ImmutableListMultimap.Builder) "DJ", (Object[]) new Integer[]{4, 0, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "DK", (Object[]) new Integer[]{0, 0, 1, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "DM", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "DO", (Object[]) new Integer[]{3, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "DZ", (Object[]) new Integer[]{3, 3, 4, 4, 2, 4}).putAll((ImmutableListMultimap.Builder) "EC", (Object[]) new Integer[]{2, 4, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "EE", (Object[]) new Integer[]{0, 1, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "EG", (Object[]) new Integer[]{3, 4, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "EH", (Object[]) new Integer[]{2, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "ER", (Object[]) new Integer[]{4, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "ES", (Object[]) new Integer[]{0, 1, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "ET", (Object[]) new Integer[]{4, 4, 4, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "FI", (Object[]) new Integer[]{0, 0, 0, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "FJ", (Object[]) new Integer[]{3, 0, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "FK", (Object[]) new Integer[]{4, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "FM", (Object[]) new Integer[]{3, 2, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "FO", (Object[]) new Integer[]{1, 2, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "FR", (Object[]) new Integer[]{1, 1, 2, 0, 1, 2}).putAll((ImmutableListMultimap.Builder) "GA", (Object[]) new Integer[]{3, 4, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "GB", (Object[]) new Integer[]{0, 0, 1, 1, 1, 2}).putAll((ImmutableListMultimap.Builder) "GD", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "GE", (Object[]) new Integer[]{1, 1, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "GF", (Object[]) new Integer[]{2, 2, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "GG", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "GH", (Object[]) new Integer[]{3, 1, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "GI", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "GL", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "GM", (Object[]) new Integer[]{4, 3, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "GN", (Object[]) new Integer[]{4, 3, 4, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "GP", (Object[]) new Integer[]{2, 1, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "GQ", (Object[]) new Integer[]{4, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "GR", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "GT", (Object[]) new Integer[]{3, 2, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "GU", (Object[]) new Integer[]{1, 2, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "GW", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "GY", (Object[]) new Integer[]{3, 3, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "HK", (Object[]) new Integer[]{0, 1, 2, 3, 2, 0}).putAll((ImmutableListMultimap.Builder) "HN", (Object[]) new Integer[]{3, 1, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "HR", (Object[]) new Integer[]{1, 1, 0, 0, 3, 2}).putAll((ImmutableListMultimap.Builder) "HT", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "HU", (Object[]) new Integer[]{0, 0, 0, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "ID", (Object[]) new Integer[]{3, 2, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "IE", (Object[]) new Integer[]{0, 0, 1, 1, 3, 2}).putAll((ImmutableListMultimap.Builder) "IL", (Object[]) new Integer[]{1, 0, 2, 3, 4, 2}).putAll((ImmutableListMultimap.Builder) "IM", (Object[]) new Integer[]{0, 2, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "IN", (Object[]) new Integer[]{2, 1, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "IO", (Object[]) new Integer[]{4, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "IQ", (Object[]) new Integer[]{3, 3, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "IR", (Object[]) new Integer[]{3, 2, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "IS", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "IT", (Object[]) new Integer[]{0, 4, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "JE", (Object[]) new Integer[]{2, 2, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "JM", (Object[]) new Integer[]{3, 3, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "JO", (Object[]) new Integer[]{2, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "JP", (Object[]) new Integer[]{0, 0, 0, 0, 2, 1}).putAll((ImmutableListMultimap.Builder) "KE", (Object[]) new Integer[]{3, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) ExpandedProductParsedResult.KILOGRAM, (Object[]) new Integer[]{2, 0, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "KH", (Object[]) new Integer[]{1, 0, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "KI", (Object[]) new Integer[]{4, 2, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "KM", (Object[]) new Integer[]{4, 3, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "KN", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "KP", (Object[]) new Integer[]{4, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "KR", (Object[]) new Integer[]{0, 0, 1, 3, 1, 2}).putAll((ImmutableListMultimap.Builder) "KW", (Object[]) new Integer[]{1, 3, 1, 1, 1, 2}).putAll((ImmutableListMultimap.Builder) "KY", (Object[]) new Integer[]{1, 2, 0, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "KZ", (Object[]) new Integer[]{2, 2, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "LA", (Object[]) new Integer[]{1, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) ExpandedProductParsedResult.POUND, (Object[]) new Integer[]{3, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "LC", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "LI", (Object[]) new Integer[]{0, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "LK", (Object[]) new Integer[]{2, 0, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "LR", (Object[]) new Integer[]{3, 4, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "LS", (Object[]) new Integer[]{3, 3, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "LT", (Object[]) new Integer[]{0, 0, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "LU", (Object[]) new Integer[]{1, 0, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "LV", (Object[]) new Integer[]{0, 0, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "LY", (Object[]) new Integer[]{4, 2, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "MA", (Object[]) new Integer[]{3, 2, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "MC", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "MD", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "ME", (Object[]) new Integer[]{1, 2, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "MF", (Object[]) new Integer[]{2, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "MG", (Object[]) new Integer[]{3, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MH", (Object[]) new Integer[]{4, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "MK", (Object[]) new Integer[]{1, 1, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "ML", (Object[]) new Integer[]{4, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MM", (Object[]) new Integer[]{2, 3, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "MN", (Object[]) new Integer[]{2, 4, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MO", (Object[]) new Integer[]{0, 2, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "MP", (Object[]) new Integer[]{0, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MQ", (Object[]) new Integer[]{2, 2, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "MR", (Object[]) new Integer[]{3, 0, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "MS", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MT", (Object[]) new Integer[]{0, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "MU", (Object[]) new Integer[]{2, 1, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) PlaybackTrackHelper.TAG_MV, (Object[]) new Integer[]{4, 3, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "MW", (Object[]) new Integer[]{4, 2, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "MX", (Object[]) new Integer[]{2, 4, 4, 4, 4, 2}).putAll((ImmutableListMultimap.Builder) "MY", (Object[]) new Integer[]{1, 0, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "MZ", (Object[]) new Integer[]{3, 3, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "NA", (Object[]) new Integer[]{4, 3, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "NC", (Object[]) new Integer[]{3, 0, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "NE", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "NF", (Object[]) new Integer[]{2, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "NG", (Object[]) new Integer[]{3, 3, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "NI", (Object[]) new Integer[]{2, 1, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "NL", (Object[]) new Integer[]{0, 2, 3, 2, 0, 2}).putAll((ImmutableListMultimap.Builder) "NO", (Object[]) new Integer[]{0, 1, 2, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "NP", (Object[]) new Integer[]{2, 0, 4, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "NR", (Object[]) new Integer[]{3, 2, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "NU", (Object[]) new Integer[]{4, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "NZ", (Object[]) new Integer[]{0, 2, 1, 2, 4, 2}).putAll((ImmutableListMultimap.Builder) "OM", (Object[]) new Integer[]{2, 2, 1, 3, 3, 2}).putAll((ImmutableListMultimap.Builder) "PA", (Object[]) new Integer[]{1, 3, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "PE", (Object[]) new Integer[]{2, 3, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "PF", (Object[]) new Integer[]{2, 2, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "PG", (Object[]) new Integer[]{4, 4, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "PH", (Object[]) new Integer[]{2, 1, 3, 3, 3, 2}).putAll((ImmutableListMultimap.Builder) "PK", (Object[]) new Integer[]{3, 2, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "PL", (Object[]) new Integer[]{1, 0, 1, 2, 3, 2}).putAll((ImmutableListMultimap.Builder) "PM", (Object[]) new Integer[]{0, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "PR", (Object[]) new Integer[]{2, 1, 2, 2, 4, 3}).putAll((ImmutableListMultimap.Builder) "PS", (Object[]) new Integer[]{3, 3, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "PT", (Object[]) new Integer[]{0, 1, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "PW", (Object[]) new Integer[]{1, 2, 4, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "PY", (Object[]) new Integer[]{2, 0, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "QA", (Object[]) new Integer[]{2, 3, 1, 2, 3, 2}).putAll((ImmutableListMultimap.Builder) "RE", (Object[]) new Integer[]{1, 0, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "RO", (Object[]) new Integer[]{0, 1, 0, 1, 0, 2}).putAll((ImmutableListMultimap.Builder) "RS", (Object[]) new Integer[]{1, 2, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "RU", (Object[]) new Integer[]{0, 1, 0, 1, 4, 2}).putAll((ImmutableListMultimap.Builder) "RW", (Object[]) new Integer[]{3, 3, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "SA", (Object[]) new Integer[]{2, 2, 2, 1, 1, 2}).putAll((ImmutableListMultimap.Builder) "SB", (Object[]) new Integer[]{4, 2, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SC", (Object[]) new Integer[]{4, 2, 1, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) PlaybackControllerInfo.RESOLUTION_SD, (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "SE", (Object[]) new Integer[]{0, 0, 0, 0, 0, 2}).putAll((ImmutableListMultimap.Builder) "SG", (Object[]) new Integer[]{1, 0, 1, 2, 3, 2}).putAll((ImmutableListMultimap.Builder) "SH", (Object[]) new Integer[]{4, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SI", (Object[]) new Integer[]{0, 0, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "SJ", (Object[]) new Integer[]{2, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SK", (Object[]) new Integer[]{0, 1, 0, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "SL", (Object[]) new Integer[]{4, 3, 4, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "SM", (Object[]) new Integer[]{0, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SN", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "SO", (Object[]) new Integer[]{3, 3, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "SR", (Object[]) new Integer[]{3, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SS", (Object[]) new Integer[]{4, 4, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "ST", (Object[]) new Integer[]{2, 2, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SV", (Object[]) new Integer[]{2, 1, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "SX", (Object[]) new Integer[]{2, 2, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "SY", (Object[]) new Integer[]{4, 3, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "SZ", (Object[]) new Integer[]{3, 3, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TC", (Object[]) new Integer[]{2, 2, 2, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "TD", (Object[]) new Integer[]{4, 3, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TG", (Object[]) new Integer[]{3, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TH", (Object[]) new Integer[]{0, 3, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "TJ", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TL", (Object[]) new Integer[]{4, 0, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TM", (Object[]) new Integer[]{4, 2, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "TN", (Object[]) new Integer[]{2, 1, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "TO", (Object[]) new Integer[]{3, 3, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "TR", (Object[]) new Integer[]{1, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "TT", (Object[]) new Integer[]{1, 4, 0, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) XiaomiOAuthConstants.EXTRA_DISPLAY_TV, (Object[]) new Integer[]{3, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "TW", (Object[]) new Integer[]{0, 0, 0, 0, 1, 0}).putAll((ImmutableListMultimap.Builder) "TZ", (Object[]) new Integer[]{3, 3, 3, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "UA", (Object[]) new Integer[]{0, 3, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "UG", (Object[]) new Integer[]{3, 2, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "US", (Object[]) new Integer[]{1, 1, 2, 2, 4, 2}).putAll((ImmutableListMultimap.Builder) "UY", (Object[]) new Integer[]{2, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "UZ", (Object[]) new Integer[]{2, 1, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "VC", (Object[]) new Integer[]{1, 2, 2, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "VE", (Object[]) new Integer[]{4, 4, 4, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "VG", (Object[]) new Integer[]{2, 2, 1, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "VI", (Object[]) new Integer[]{1, 2, 1, 2, 2, 2}).putAll((ImmutableListMultimap.Builder) "VN", (Object[]) new Integer[]{0, 1, 3, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "VU", (Object[]) new Integer[]{4, 0, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "WF", (Object[]) new Integer[]{4, 2, 2, 4, 2, 2}).putAll((ImmutableListMultimap.Builder) "WS", (Object[]) new Integer[]{3, 1, 3, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "XK", (Object[]) new Integer[]{0, 1, 1, 0, 2, 2}).putAll((ImmutableListMultimap.Builder) "YE", (Object[]) new Integer[]{4, 4, 4, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "YT", (Object[]) new Integer[]{4, 2, 2, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "ZA", (Object[]) new Integer[]{3, 3, 2, 1, 2, 2}).putAll((ImmutableListMultimap.Builder) "ZM", (Object[]) new Integer[]{3, 2, 3, 3, 2, 2}).putAll((ImmutableListMultimap.Builder) "ZW", (Object[]) new Integer[]{3, 2, 4, 3, 2, 2}).build();
    }
}
