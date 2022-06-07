package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public final class DownloadHelper {
    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS;
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT = DefaultTrackSelector.Parameters.DEFAULT_WITHOUT_CONTEXT.buildUpon().setForceHighestSupportedBitrate(true).build();
    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT;
    private final MediaItem.PlaybackProperties a;
    @Nullable
    private final MediaSource b;
    private final DefaultTrackSelector c;
    private final RendererCapabilities[] d;
    private final SparseIntArray e = new SparseIntArray();
    private final Handler f = Util.createHandlerForCurrentOrMainLooper();
    private final Timeline.Window g = new Timeline.Window();
    private boolean h;
    private Callback i;
    private c j;
    private TrackGroupArray[] k;
    private MappingTrackSelector.MappedTrackInfo[] l;
    private List<ExoTrackSelection>[][] m;
    private List<ExoTrackSelection>[][] n;

    /* loaded from: classes2.dex */
    public interface Callback {
        void onPrepareError(DownloadHelper downloadHelper, IOException iOException);

        void onPrepared(DownloadHelper downloadHelper);
    }

    /* loaded from: classes2.dex */
    public static class LiveContentUnsupportedException extends IOException {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Metadata metadata) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e() {
    }

    static {
        DefaultTrackSelector.Parameters parameters = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT;
        DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT = parameters;
        DEFAULT_TRACK_SELECTOR_PARAMETERS = parameters;
    }

    public static DefaultTrackSelector.Parameters getDefaultTrackSelectorParameters(Context context) {
        return DefaultTrackSelector.Parameters.getDefaults(context).buildUpon().setForceHighestSupportedBitrate(true).build();
    }

    public static RendererCapabilities[] getRendererCapabilities(RenderersFactory renderersFactory) {
        Renderer[] createRenderers = renderersFactory.createRenderers(Util.createHandlerForCurrentOrMainLooper(), new VideoRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.1
        }, new AudioRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.2
        }, $$Lambda$DownloadHelper$OXcrFq4ALBDsyFH4sA3rUOgc1ko.INSTANCE, $$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog.INSTANCE);
        RendererCapabilities[] rendererCapabilitiesArr = new RendererCapabilities[createRenderers.length];
        for (int i = 0; i < createRenderers.length; i++) {
            rendererCapabilitiesArr[i] = createRenderers[i].getCapabilities();
        }
        return rendererCapabilitiesArr;
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).build());
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri, @Nullable String str) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).setCustomCacheKey(str).build());
    }

    @Deprecated
    public static DownloadHelper forDash(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forDash(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forDash(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_MPD).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forHls(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forHls(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forHls(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_SS).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem) {
        Assertions.checkArgument(a((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties)));
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), null, null, null);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), renderersFactory, factory, null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, parameters, renderersFactory, factory, null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        RendererCapabilities[] rendererCapabilitiesArr;
        boolean a2 = a((MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties));
        Assertions.checkArgument(a2 || factory != null);
        MediaSource a3 = a2 ? null : a(mediaItem, (DataSource.Factory) Util.castNonNull(factory), drmSessionManager);
        if (renderersFactory != null) {
            rendererCapabilitiesArr = getRendererCapabilities(renderersFactory);
        } else {
            rendererCapabilitiesArr = new RendererCapabilities[0];
        }
        return new DownloadHelper(mediaItem, a3, parameters, rendererCapabilitiesArr);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory) {
        return createMediaSource(downloadRequest, factory, null);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        return a(downloadRequest.toMediaItem(), factory, drmSessionManager);
    }

    public DownloadHelper(MediaItem mediaItem, @Nullable MediaSource mediaSource, DefaultTrackSelector.Parameters parameters, RendererCapabilities[] rendererCapabilitiesArr) {
        this.a = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.playbackProperties);
        this.b = mediaSource;
        this.c = new DefaultTrackSelector(parameters, new a.C0065a());
        this.d = rendererCapabilitiesArr;
        this.c.init($$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs.INSTANCE, new b());
    }

    public void prepare(final Callback callback) {
        Assertions.checkState(this.i == null);
        this.i = callback;
        MediaSource mediaSource = this.b;
        if (mediaSource != null) {
            this.j = new c(mediaSource, this);
        } else {
            this.f.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$Qdxi1ow8SVYn12Y1lTR4gj1M9o0
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadHelper.this.a(callback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Callback callback) {
        callback.onPrepared(this);
    }

    public void release() {
        c cVar = this.j;
        if (cVar != null) {
            cVar.a();
        }
    }

    @Nullable
    public Object getManifest() {
        if (this.b == null) {
            return null;
        }
        c();
        if (this.j.a.getWindowCount() > 0) {
            return this.j.a.getWindow(0, this.g).manifest;
        }
        return null;
    }

    public int getPeriodCount() {
        if (this.b == null) {
            return 0;
        }
        c();
        return this.k.length;
    }

    public TrackGroupArray getTrackGroups(int i) {
        c();
        return this.k[i];
    }

    public MappingTrackSelector.MappedTrackInfo getMappedTrackInfo(int i) {
        c();
        return this.l[i];
    }

    public List<ExoTrackSelection> getTrackSelections(int i, int i2) {
        c();
        return this.n[i][i2];
    }

    public void clearTrackSelections(int i) {
        c();
        for (int i2 = 0; i2 < this.d.length; i2++) {
            this.m[i][i2].clear();
        }
    }

    public void replaceTrackSelections(int i, DefaultTrackSelector.Parameters parameters) {
        clearTrackSelections(i);
        addTrackSelection(i, parameters);
    }

    public void addTrackSelection(int i, DefaultTrackSelector.Parameters parameters) {
        c();
        this.c.setParameters(parameters);
        a(i);
    }

    public void addAudioLanguagesToSelection(String... strArr) {
        c();
        for (int i = 0; i < this.l.length; i++) {
            DefaultTrackSelector.ParametersBuilder buildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.l[i];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i2 = 0; i2 < rendererCount; i2++) {
                if (mappedTrackInfo.getRendererType(i2) != 1) {
                    buildUpon.setRendererDisabled(i2, true);
                }
            }
            for (String str : strArr) {
                buildUpon.setPreferredAudioLanguage(str);
                addTrackSelection(i, buildUpon.build());
            }
        }
    }

    public void addTextLanguagesToSelection(boolean z, String... strArr) {
        c();
        for (int i = 0; i < this.l.length; i++) {
            DefaultTrackSelector.ParametersBuilder buildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.l[i];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i2 = 0; i2 < rendererCount; i2++) {
                if (mappedTrackInfo.getRendererType(i2) != 3) {
                    buildUpon.setRendererDisabled(i2, true);
                }
            }
            buildUpon.setSelectUndeterminedTextLanguage(z);
            for (String str : strArr) {
                buildUpon.setPreferredTextLanguage(str);
                addTrackSelection(i, buildUpon.build());
            }
        }
    }

    public void addTrackSelectionForSingleRenderer(int i, int i2, DefaultTrackSelector.Parameters parameters, List<DefaultTrackSelector.SelectionOverride> list) {
        c();
        DefaultTrackSelector.ParametersBuilder buildUpon = parameters.buildUpon();
        int i3 = 0;
        while (i3 < this.l[i].getRendererCount()) {
            buildUpon.setRendererDisabled(i3, i3 != i2);
            i3++;
        }
        if (list.isEmpty()) {
            addTrackSelection(i, buildUpon.build());
            return;
        }
        TrackGroupArray trackGroups = this.l[i].getTrackGroups(i2);
        for (int i4 = 0; i4 < list.size(); i4++) {
            buildUpon.setSelectionOverride(i2, trackGroups, list.get(i4));
            addTrackSelection(i, buildUpon.build());
        }
    }

    public DownloadRequest getDownloadRequest(@Nullable byte[] bArr) {
        return getDownloadRequest(this.a.uri.toString(), bArr);
    }

    public DownloadRequest getDownloadRequest(String str, @Nullable byte[] bArr) {
        DownloadRequest.Builder data = new DownloadRequest.Builder(str, this.a.uri).setMimeType(this.a.mimeType).setKeySetId(this.a.drmConfiguration != null ? this.a.drmConfiguration.getKeySetId() : null).setCustomCacheKey(this.a.customCacheKey).setData(bArr);
        if (this.b == null) {
            return data.build();
        }
        c();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = this.m.length;
        for (int i = 0; i < length; i++) {
            arrayList2.clear();
            int length2 = this.m[i].length;
            for (int i2 = 0; i2 < length2; i2++) {
                arrayList2.addAll(this.m[i][i2]);
            }
            arrayList.addAll(this.j.b[i].getStreamKeys(arrayList2));
        }
        return data.setStreamKeys(arrayList).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        Assertions.checkNotNull(this.j);
        Assertions.checkNotNull(this.j.b);
        Assertions.checkNotNull(this.j.a);
        int length = this.j.b.length;
        int length2 = this.d.length;
        this.m = (List[][]) Array.newInstance(List.class, length, length2);
        this.n = (List[][]) Array.newInstance(List.class, length, length2);
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length2; i2++) {
                this.m[i][i2] = new ArrayList();
                this.n[i][i2] = Collections.unmodifiableList(this.m[i][i2]);
            }
        }
        this.k = new TrackGroupArray[length];
        this.l = new MappingTrackSelector.MappedTrackInfo[length];
        for (int i3 = 0; i3 < length; i3++) {
            this.k[i3] = this.j.b[i3].getTrackGroups();
            this.c.onSelectionActivated(a(i3).info);
            this.l[i3] = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.c.getCurrentMappedTrackInfo());
        }
        b();
        ((Handler) Assertions.checkNotNull(this.f)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$5KqORLcRNYIzUx8od9G067MK-B0
            @Override // java.lang.Runnable
            public final void run() {
                DownloadHelper.this.d();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        ((Callback) Assertions.checkNotNull(this.i)).onPrepared(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final IOException iOException) {
        ((Handler) Assertions.checkNotNull(this.f)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$1jd7UYew9wcEkmeOHL6OsZKyjYo
            @Override // java.lang.Runnable
            public final void run() {
                DownloadHelper.this.b(iOException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(IOException iOException) {
        ((Callback) Assertions.checkNotNull(this.i)).onPrepareError(this, iOException);
    }

    @RequiresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void b() {
        this.h = true;
    }

    @EnsuresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void c() {
        Assertions.checkState(this.h);
    }

    @RequiresNonNull({"trackGroupArrays", "trackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline"})
    private TrackSelectorResult a(int i) {
        boolean z;
        try {
            TrackSelectorResult selectTracks = this.c.selectTracks(this.d, this.k[i], new MediaSource.MediaPeriodId(this.j.a.getUidOfPeriod(i)), this.j.a);
            for (int i2 = 0; i2 < selectTracks.length; i2++) {
                ExoTrackSelection exoTrackSelection = selectTracks.selections[i2];
                if (exoTrackSelection != null) {
                    List<ExoTrackSelection> list = this.m[i][i2];
                    int i3 = 0;
                    while (true) {
                        if (i3 >= list.size()) {
                            z = false;
                            break;
                        }
                        ExoTrackSelection exoTrackSelection2 = list.get(i3);
                        if (exoTrackSelection2.getTrackGroup() == exoTrackSelection.getTrackGroup()) {
                            this.e.clear();
                            for (int i4 = 0; i4 < exoTrackSelection2.length(); i4++) {
                                this.e.put(exoTrackSelection2.getIndexInTrackGroup(i4), 0);
                            }
                            for (int i5 = 0; i5 < exoTrackSelection.length(); i5++) {
                                this.e.put(exoTrackSelection.getIndexInTrackGroup(i5), 0);
                            }
                            int[] iArr = new int[this.e.size()];
                            for (int i6 = 0; i6 < this.e.size(); i6++) {
                                iArr[i6] = this.e.keyAt(i6);
                            }
                            list.set(i3, new a(exoTrackSelection2.getTrackGroup(), iArr));
                            z = true;
                        } else {
                            i3++;
                        }
                    }
                    if (!z) {
                        list.add(exoTrackSelection);
                    }
                }
            }
            return selectTracks;
        } catch (ExoPlaybackException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static MediaSource a(MediaItem mediaItem, DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        return new DefaultMediaSourceFactory(factory, ExtractorsFactory.EMPTY).setDrmSessionManager(drmSessionManager).createMediaSource(mediaItem);
    }

    private static boolean a(MediaItem.PlaybackProperties playbackProperties) {
        return Util.inferContentTypeForUriAndMimeType(playbackProperties.uri, playbackProperties.mimeType) == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c implements Handler.Callback, MediaPeriod.Callback, MediaSource.MediaSourceCaller {
        public Timeline a;
        public MediaPeriod[] b;
        private final MediaSource c;
        private final DownloadHelper d;
        private final Allocator e = new DefaultAllocator(true, 65536);
        private final ArrayList<MediaPeriod> f = new ArrayList<>();
        private final Handler g = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$c$EpMzyprq0SafN09KOpFp-w3K2HM
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a;
                a = DownloadHelper.c.this.a(message);
                return a;
            }
        });
        private final HandlerThread h = new HandlerThread("ExoPlayer:DownloadHelper");
        private final Handler i = Util.createHandler(this.h.getLooper(), this);
        private boolean j;

        public c(MediaSource mediaSource, DownloadHelper downloadHelper) {
            this.c = mediaSource;
            this.d = downloadHelper;
            this.h.start();
            this.i.sendEmptyMessage(0);
        }

        public void a() {
            if (!this.j) {
                this.j = true;
                this.i.sendEmptyMessage(3);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = 0;
            switch (message.what) {
                case 0:
                    this.c.prepareSource(this, null);
                    this.i.sendEmptyMessage(1);
                    return true;
                case 1:
                    try {
                        if (this.b == null) {
                            this.c.maybeThrowSourceInfoRefreshError();
                        } else {
                            while (i < this.f.size()) {
                                this.f.get(i).maybeThrowPrepareError();
                                i++;
                            }
                        }
                        this.i.sendEmptyMessageDelayed(1, 100L);
                    } catch (IOException e) {
                        this.g.obtainMessage(1, e).sendToTarget();
                    }
                    return true;
                case 2:
                    MediaPeriod mediaPeriod = (MediaPeriod) message.obj;
                    if (this.f.contains(mediaPeriod)) {
                        mediaPeriod.continueLoading(0L);
                    }
                    return true;
                case 3:
                    MediaPeriod[] mediaPeriodArr = this.b;
                    if (mediaPeriodArr != null) {
                        int length = mediaPeriodArr.length;
                        while (i < length) {
                            this.c.releasePeriod(mediaPeriodArr[i]);
                            i++;
                        }
                    }
                    this.c.releaseSource(this);
                    this.i.removeCallbacksAndMessages(null);
                    this.h.quit();
                    return true;
                default:
                    return false;
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
        public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
            MediaPeriod[] mediaPeriodArr;
            if (this.a == null) {
                if (timeline.getWindow(0, new Timeline.Window()).isLive()) {
                    this.g.obtainMessage(1, new LiveContentUnsupportedException()).sendToTarget();
                    return;
                }
                this.a = timeline;
                this.b = new MediaPeriod[timeline.getPeriodCount()];
                int i = 0;
                while (true) {
                    mediaPeriodArr = this.b;
                    if (i >= mediaPeriodArr.length) {
                        break;
                    }
                    MediaPeriod createPeriod = this.c.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(i)), this.e, 0L);
                    this.b[i] = createPeriod;
                    this.f.add(createPeriod);
                    i++;
                }
                for (MediaPeriod mediaPeriod : mediaPeriodArr) {
                    mediaPeriod.prepare(this, 0L);
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            this.f.remove(mediaPeriod);
            if (this.f.isEmpty()) {
                this.i.removeMessages(1);
                this.g.sendEmptyMessage(0);
            }
        }

        /* renamed from: a */
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            if (this.f.contains(mediaPeriod)) {
                this.i.obtainMessage(2, mediaPeriod).sendToTarget();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a(Message message) {
            if (this.j) {
                return false;
            }
            switch (message.what) {
                case 0:
                    this.d.a();
                    return true;
                case 1:
                    a();
                    this.d.a((IOException) Util.castNonNull(message.obj));
                    return true;
                default:
                    return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends BaseTrackSelection {
        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        @Nullable
        public Object getSelectionData() {
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long j, long j2, long j3, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        }

        /* renamed from: com.google.android.exoplayer2.offline.DownloadHelper$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private static final class C0065a implements ExoTrackSelection.Factory {
            private C0065a() {
            }

            @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection.Factory
            public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
                ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
                for (int i = 0; i < definitionArr.length; i++) {
                    exoTrackSelectionArr[i] = definitionArr[i] == null ? null : new a(definitionArr[i].group, definitionArr[i].tracks);
                }
                return exoTrackSelectionArr;
            }
        }

        public a(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements BandwidthMeter {
        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public long getBitrateEstimate() {
            return 0L;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        @Nullable
        public TransferListener getTransferListener() {
            return null;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        }

        private b() {
        }
    }
}
