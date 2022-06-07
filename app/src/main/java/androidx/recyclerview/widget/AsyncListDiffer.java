package androidx.recyclerview.widget;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class AsyncListDiffer<T> {
    private static final Executor e = new a();
    final AsyncDifferConfig<T> a;
    Executor b;
    int c;
    private final ListUpdateCallback d;
    private final List<ListListener<T>> f;
    @Nullable
    private List<T> g;
    @NonNull
    private List<T> h;

    /* loaded from: classes.dex */
    public interface ListListener<T> {
        void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2);
    }

    /* loaded from: classes.dex */
    private static class a implements Executor {
        final Handler a = new Handler(Looper.getMainLooper());

        a() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            this.a.post(runnable);
        }
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this(new AdapterListUpdateCallback(adapter), new AsyncDifferConfig.Builder(itemCallback).build());
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.f = new CopyOnWriteArrayList();
        this.h = Collections.emptyList();
        this.d = listUpdateCallback;
        this.a = asyncDifferConfig;
        if (asyncDifferConfig.getMainThreadExecutor() != null) {
            this.b = asyncDifferConfig.getMainThreadExecutor();
        } else {
            this.b = e;
        }
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.h;
    }

    public void submitList(@Nullable List<T> list) {
        submitList(list, null);
    }

    public void submitList(@Nullable final List<T> list, @Nullable final Runnable runnable) {
        final int i = this.c + 1;
        this.c = i;
        final List<T> list2 = this.g;
        if (list != list2) {
            List<T> list3 = this.h;
            if (list == null) {
                int size = list2.size();
                this.g = null;
                this.h = Collections.emptyList();
                this.d.onRemoved(0, size);
                a(list3, runnable);
            } else if (list2 == null) {
                this.g = list;
                this.h = Collections.unmodifiableList(list);
                this.d.onInserted(0, list.size());
                a(list3, runnable);
            } else {
                this.a.getBackgroundThreadExecutor().execute(new Runnable() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1
                    @Override // java.lang.Runnable
                    public void run() {
                        final DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1.1
                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public int getOldListSize() {
                                return list2.size();
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public int getNewListSize() {
                                return list.size();
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public boolean areItemsTheSame(int i2, int i3) {
                                Object obj = list2.get(i2);
                                Object obj2 = list.get(i3);
                                if (obj == null || obj2 == null) {
                                    return obj == null && obj2 == null;
                                }
                                return AsyncListDiffer.this.a.getDiffCallback().areItemsTheSame(obj, obj2);
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public boolean areContentsTheSame(int i2, int i3) {
                                Object obj = list2.get(i2);
                                Object obj2 = list.get(i3);
                                if (obj != null && obj2 != null) {
                                    return AsyncListDiffer.this.a.getDiffCallback().areContentsTheSame(obj, obj2);
                                }
                                if (obj == null && obj2 == null) {
                                    return true;
                                }
                                throw new AssertionError();
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            @Nullable
                            public Object getChangePayload(int i2, int i3) {
                                Object obj = list2.get(i2);
                                Object obj2 = list.get(i3);
                                if (obj != null && obj2 != null) {
                                    return AsyncListDiffer.this.a.getDiffCallback().getChangePayload(obj, obj2);
                                }
                                throw new AssertionError();
                            }
                        });
                        AsyncListDiffer.this.b.execute(new Runnable() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (AsyncListDiffer.this.c == i) {
                                    AsyncListDiffer.this.a(list, calculateDiff, runnable);
                                }
                            }
                        });
                    }
                });
            }
        } else if (runnable != null) {
            runnable.run();
        }
    }

    void a(@NonNull List<T> list, @NonNull DiffUtil.DiffResult diffResult, @Nullable Runnable runnable) {
        List<T> list2 = this.h;
        this.g = list;
        this.h = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.d);
        a(list2, runnable);
    }

    private void a(@NonNull List<T> list, @Nullable Runnable runnable) {
        for (ListListener<T> listListener : this.f) {
            listListener.onCurrentListChanged(list, this.h);
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public void addListListener(@NonNull ListListener<T> listListener) {
        this.f.add(listListener);
    }

    public void removeListListener(@NonNull ListListener<T> listListener) {
        this.f.remove(listListener);
    }
}
