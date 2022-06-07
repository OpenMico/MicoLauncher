package androidx.core.app;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FrameMetricsAggregator {
    public static final int ANIMATION_DURATION = 256;
    public static final int ANIMATION_INDEX = 8;
    public static final int COMMAND_DURATION = 32;
    public static final int COMMAND_INDEX = 5;
    public static final int DELAY_DURATION = 128;
    public static final int DELAY_INDEX = 7;
    public static final int DRAW_DURATION = 8;
    public static final int DRAW_INDEX = 3;
    public static final int EVERY_DURATION = 511;
    public static final int INPUT_DURATION = 2;
    public static final int INPUT_INDEX = 1;
    public static final int LAYOUT_MEASURE_DURATION = 4;
    public static final int LAYOUT_MEASURE_INDEX = 2;
    public static final int SWAP_DURATION = 64;
    public static final int SWAP_INDEX = 6;
    public static final int SYNC_DURATION = 16;
    public static final int SYNC_INDEX = 4;
    public static final int TOTAL_DURATION = 1;
    public static final int TOTAL_INDEX = 0;
    private b a;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface MetricType {
    }

    public FrameMetricsAggregator() {
        this(1);
    }

    public FrameMetricsAggregator(int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.a = new a(i);
        } else {
            this.a = new b();
        }
    }

    public void add(@NonNull Activity activity) {
        this.a.a(activity);
    }

    @Nullable
    public SparseIntArray[] remove(@NonNull Activity activity) {
        return this.a.b(activity);
    }

    @Nullable
    public SparseIntArray[] stop() {
        return this.a.a();
    }

    @Nullable
    public SparseIntArray[] reset() {
        return this.a.c();
    }

    @Nullable
    public SparseIntArray[] getMetrics() {
        return this.a.b();
    }

    /* loaded from: classes.dex */
    private static class b {
        public void a(Activity activity) {
        }

        public SparseIntArray[] a() {
            return null;
        }

        public SparseIntArray[] b() {
            return null;
        }

        public SparseIntArray[] b(Activity activity) {
            return null;
        }

        public SparseIntArray[] c() {
            return null;
        }

        b() {
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static class a extends b {
        private static HandlerThread e;
        private static Handler f;
        int a;
        SparseIntArray[] b = new SparseIntArray[9];
        private ArrayList<WeakReference<Activity>> d = new ArrayList<>();
        Window.OnFrameMetricsAvailableListener c = new Window.OnFrameMetricsAvailableListener() { // from class: androidx.core.app.FrameMetricsAggregator.a.1
            @Override // android.view.Window.OnFrameMetricsAvailableListener
            public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int i) {
                if ((a.this.a & 1) != 0) {
                    a aVar = a.this;
                    aVar.a(aVar.b[0], frameMetrics.getMetric(8));
                }
                if ((a.this.a & 2) != 0) {
                    a aVar2 = a.this;
                    aVar2.a(aVar2.b[1], frameMetrics.getMetric(1));
                }
                if ((a.this.a & 4) != 0) {
                    a aVar3 = a.this;
                    aVar3.a(aVar3.b[2], frameMetrics.getMetric(3));
                }
                if ((a.this.a & 8) != 0) {
                    a aVar4 = a.this;
                    aVar4.a(aVar4.b[3], frameMetrics.getMetric(4));
                }
                if ((a.this.a & 16) != 0) {
                    a aVar5 = a.this;
                    aVar5.a(aVar5.b[4], frameMetrics.getMetric(5));
                }
                if ((a.this.a & 64) != 0) {
                    a aVar6 = a.this;
                    aVar6.a(aVar6.b[6], frameMetrics.getMetric(7));
                }
                if ((a.this.a & 32) != 0) {
                    a aVar7 = a.this;
                    aVar7.a(aVar7.b[5], frameMetrics.getMetric(6));
                }
                if ((a.this.a & 128) != 0) {
                    a aVar8 = a.this;
                    aVar8.a(aVar8.b[7], frameMetrics.getMetric(0));
                }
                if ((a.this.a & 256) != 0) {
                    a aVar9 = a.this;
                    aVar9.a(aVar9.b[8], frameMetrics.getMetric(2));
                }
            }
        };

        a(int i) {
            this.a = i;
        }

        void a(SparseIntArray sparseIntArray, long j) {
            if (sparseIntArray != null) {
                int i = (int) ((500000 + j) / 1000000);
                if (j >= 0) {
                    sparseIntArray.put(i, sparseIntArray.get(i) + 1);
                }
            }
        }

        @Override // androidx.core.app.FrameMetricsAggregator.b
        public void a(Activity activity) {
            if (e == null) {
                e = new HandlerThread("FrameMetricsAggregator");
                e.start();
                f = new Handler(e.getLooper());
            }
            for (int i = 0; i <= 8; i++) {
                SparseIntArray[] sparseIntArrayArr = this.b;
                if (sparseIntArrayArr[i] == null && (this.a & (1 << i)) != 0) {
                    sparseIntArrayArr[i] = new SparseIntArray();
                }
            }
            activity.getWindow().addOnFrameMetricsAvailableListener(this.c, f);
            this.d.add(new WeakReference<>(activity));
        }

        @Override // androidx.core.app.FrameMetricsAggregator.b
        public SparseIntArray[] b(Activity activity) {
            Iterator<WeakReference<Activity>> it = this.d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeakReference<Activity> next = it.next();
                if (next.get() == activity) {
                    this.d.remove(next);
                    break;
                }
            }
            activity.getWindow().removeOnFrameMetricsAvailableListener(this.c);
            return this.b;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.b
        public SparseIntArray[] a() {
            for (int size = this.d.size() - 1; size >= 0; size--) {
                WeakReference<Activity> weakReference = this.d.get(size);
                Activity activity = weakReference.get();
                if (weakReference.get() != null) {
                    activity.getWindow().removeOnFrameMetricsAvailableListener(this.c);
                    this.d.remove(size);
                }
            }
            return this.b;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.b
        public SparseIntArray[] b() {
            return this.b;
        }

        @Override // androidx.core.app.FrameMetricsAggregator.b
        public SparseIntArray[] c() {
            SparseIntArray[] sparseIntArrayArr = this.b;
            this.b = new SparseIntArray[9];
            return sparseIntArrayArr;
        }
    }
}
