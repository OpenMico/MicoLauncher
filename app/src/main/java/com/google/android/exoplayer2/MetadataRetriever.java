package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

/* loaded from: classes.dex */
public final class MetadataRetriever {
    private MetadataRetriever() {
    }

    public static ListenableFuture<TrackGroupArray> retrieveMetadata(Context context, MediaItem mediaItem) {
        return a(context, mediaItem, Clock.DEFAULT);
    }

    public static ListenableFuture<TrackGroupArray> retrieveMetadata(MediaSourceFactory mediaSourceFactory, MediaItem mediaItem) {
        return a(mediaSourceFactory, mediaItem, Clock.DEFAULT);
    }

    @VisibleForTesting
    static ListenableFuture<TrackGroupArray> a(Context context, MediaItem mediaItem, Clock clock) {
        return a(new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory().setMp4ExtractorFlags(6)), mediaItem, clock);
    }

    private static ListenableFuture<TrackGroupArray> a(MediaSourceFactory mediaSourceFactory, MediaItem mediaItem, Clock clock) {
        return new a(mediaSourceFactory, clock).a(mediaItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        private final MediaSourceFactory a;
        private final HandlerWrapper c;
        private final HandlerThread b = new HandlerThread("ExoPlayer:MetadataRetriever");
        private final SettableFuture<TrackGroupArray> d = SettableFuture.create();

        public a(MediaSourceFactory mediaSourceFactory, Clock clock) {
            this.a = mediaSourceFactory;
            this.b.start();
            this.c = clock.createHandler(this.b.getLooper(), new C0053a());
        }

        public ListenableFuture<TrackGroupArray> a(MediaItem mediaItem) {
            this.c.obtainMessage(0, mediaItem).sendToTarget();
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public final class C0053a implements Handler.Callback {
            private final C0054a b = new C0054a();
            private MediaSource c;
            private MediaPeriod d;

            public C0053a() {
            }

            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        this.c = a.this.a.createMediaSource((MediaItem) message.obj);
                        this.c.prepareSource(this.b, null);
                        a.this.c.sendEmptyMessage(1);
                        return true;
                    case 1:
                        try {
                            if (this.d == null) {
                                ((MediaSource) Assertions.checkNotNull(this.c)).maybeThrowSourceInfoRefreshError();
                            } else {
                                this.d.maybeThrowPrepareError();
                            }
                            a.this.c.sendEmptyMessageDelayed(1, 100);
                        } catch (Exception e) {
                            a.this.d.setException(e);
                            a.this.c.obtainMessage(3).sendToTarget();
                        }
                        return true;
                    case 2:
                        ((MediaPeriod) Assertions.checkNotNull(this.d)).continueLoading(0L);
                        return true;
                    case 3:
                        if (this.d != null) {
                            ((MediaSource) Assertions.checkNotNull(this.c)).releasePeriod(this.d);
                        }
                        ((MediaSource) Assertions.checkNotNull(this.c)).releaseSource(this.b);
                        a.this.c.removeCallbacksAndMessages(null);
                        a.this.b.quit();
                        return true;
                    default:
                        return false;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a$a  reason: collision with other inner class name */
            /* loaded from: classes.dex */
            public final class C0054a implements MediaSource.MediaSourceCaller {
                private final C0055a b = new C0055a();
                private final Allocator c = new DefaultAllocator(true, 65536);
                private boolean d;

                public C0054a() {
                }

                @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
                public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
                    if (!this.d) {
                        this.d = true;
                        C0053a.this.d = mediaSource.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0)), this.c, 0L);
                        C0053a.this.d.prepare(this.b, 0L);
                    }
                }

                /* renamed from: com.google.android.exoplayer2.MetadataRetriever$a$a$a$a  reason: collision with other inner class name */
                /* loaded from: classes.dex */
                private final class C0055a implements MediaPeriod.Callback {
                    private C0055a() {
                    }

                    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
                    public void onPrepared(MediaPeriod mediaPeriod) {
                        a.this.d.set(mediaPeriod.getTrackGroups());
                        a.this.c.obtainMessage(3).sendToTarget();
                    }

                    /* renamed from: a */
                    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
                        a.this.c.obtainMessage(2).sendToTarget();
                    }
                }
            }
        }
    }
}
