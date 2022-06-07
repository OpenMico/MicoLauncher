package com.bumptech.glide;

import android.graphics.drawable.Drawable;
import android.widget.AbsListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.util.List;
import java.util.Queue;

/* loaded from: classes.dex */
public class ListPreloader<T> implements AbsListView.OnScrollListener {
    private final int a;
    private final b b;
    private final RequestManager c;
    private final PreloadModelProvider<T> d;
    private final PreloadSizeProvider<T> e;
    private int f;
    private int g;
    private int i;
    private int h = -1;
    private boolean j = true;

    /* loaded from: classes.dex */
    public interface PreloadModelProvider<U> {
        @NonNull
        List<U> getPreloadItems(int i);

        @Nullable
        RequestBuilder<?> getPreloadRequestBuilder(@NonNull U u);
    }

    /* loaded from: classes.dex */
    public interface PreloadSizeProvider<T> {
        @Nullable
        int[] getPreloadSize(@NonNull T t, int i, int i2);
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public ListPreloader(@NonNull RequestManager requestManager, @NonNull PreloadModelProvider<T> preloadModelProvider, @NonNull PreloadSizeProvider<T> preloadSizeProvider, int i) {
        this.c = requestManager;
        this.d = preloadModelProvider;
        this.e = preloadSizeProvider;
        this.a = i;
        this.b = new b(i + 1);
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        this.i = i3;
        int i4 = this.h;
        if (i > i4) {
            a(i2 + i, true);
        } else if (i < i4) {
            a(i, false);
        }
        this.h = i;
    }

    private void a(int i, boolean z) {
        if (this.j != z) {
            this.j = z;
            a();
        }
        a(i, (z ? this.a : -this.a) + i);
    }

    private void a(int i, int i2) {
        int i3;
        int i4;
        if (i < i2) {
            i3 = Math.max(this.f, i);
            i4 = i2;
        } else {
            i4 = Math.min(this.g, i);
            i3 = i2;
        }
        int min = Math.min(this.i, i4);
        int min2 = Math.min(this.i, Math.max(0, i3));
        if (i < i2) {
            for (int i5 = min2; i5 < min; i5++) {
                a((List) this.d.getPreloadItems(i5), i5, true);
            }
        } else {
            for (int i6 = min - 1; i6 >= min2; i6--) {
                a((List) this.d.getPreloadItems(i6), i6, false);
            }
        }
        this.g = min2;
        this.f = min;
    }

    private void a(List<T> list, int i, boolean z) {
        int size = list.size();
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                a((ListPreloader<T>) list.get(i2), i, i2);
            }
            return;
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            a((ListPreloader<T>) list.get(i3), i, i3);
        }
    }

    private void a(@Nullable T t, int i, int i2) {
        int[] preloadSize;
        RequestBuilder<?> preloadRequestBuilder;
        if (t != null && (preloadSize = this.e.getPreloadSize(t, i, i2)) != null && (preloadRequestBuilder = this.d.getPreloadRequestBuilder(t)) != null) {
            preloadRequestBuilder.into((RequestBuilder<?>) this.b.a(preloadSize[0], preloadSize[1]));
        }
    }

    private void a() {
        for (int i = 0; i < this.b.a.size(); i++) {
            this.c.clear(this.b.a(0, 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        final Queue<a> a;

        b(int i) {
            this.a = Util.createQueue(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.a.offer(new a());
            }
        }

        public a a(int i, int i2) {
            a poll = this.a.poll();
            this.a.offer(poll);
            poll.b = i;
            poll.a = i2;
            return poll;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a implements Target<Object> {
        int a;
        int b;
        @Nullable
        private Request c;

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onDestroy() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadCleared(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadFailed(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onLoadStarted(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onResourceReady(@NonNull Object obj, @Nullable Transition<? super Object> transition) {
        }

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onStart() {
        }

        @Override // com.bumptech.glide.manager.LifecycleListener
        public void onStop() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
        }

        a() {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
            sizeReadyCallback.onSizeReady(this.b, this.a);
        }

        @Override // com.bumptech.glide.request.target.Target
        public void setRequest(@Nullable Request request) {
            this.c = request;
        }

        @Override // com.bumptech.glide.request.target.Target
        @Nullable
        public Request getRequest() {
            return this.c;
        }
    }
}
