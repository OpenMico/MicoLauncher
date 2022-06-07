package com.milink.base.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public final class TransactionLockProvider {
    public static final int FLAG_NOT_REQUIRE_IF_DUPLICATE_VISITOR_TAG = 1;
    private final Map<String, a> a = new ArrayMap();
    private final Map<String, List<String>> b = new ArrayMap();

    /* loaded from: classes2.dex */
    public interface ITransactionLock {
        void endTransaction();

        void startTransaction() throws InterruptedException;
    }

    @NonNull
    public ITransactionLock require(@NonNull String str, @NonNull String str2) {
        return (ITransactionLock) Objects.requireNonNull(require(str, str2, 0));
    }

    @Nullable
    public ITransactionLock require(@NonNull String str, @NonNull String str2, int i) {
        if ((i & 1) == 0 || !hasTransactionVisitor(str, str2)) {
            return new b(str, str2);
        }
        return null;
    }

    public boolean hasTransactionVisitor(@NonNull String str, @NonNull String str2) {
        boolean z;
        synchronized (this.b) {
            List<String> list = this.b.get(str);
            z = list != null && list.contains(str2);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2) {
        synchronized (this.b) {
            List<String> list = this.b.get(str);
            if (list == null) {
                list = new ArrayList<>();
                this.b.put(str, list);
            }
            list.add(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, String str2) {
        synchronized (this.b) {
            List<String> list = this.b.get(str);
            if (list != null) {
                list.remove(str2);
                if (list.isEmpty()) {
                    this.b.remove(str);
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    static class a {
        private final AtomicReference<ITransactionLock> a = new AtomicReference<>();

        a() {
        }

        void a(ITransactionLock iTransactionLock) throws InterruptedException {
            if (!this.a.compareAndSet(iTransactionLock, iTransactionLock)) {
                synchronized (this.a) {
                    while (!this.a.compareAndSet(null, iTransactionLock)) {
                        this.a.wait();
                    }
                }
            }
        }

        void b(ITransactionLock iTransactionLock) {
            if (this.a.get() == iTransactionLock) {
                synchronized (this.a) {
                    this.a.compareAndSet(iTransactionLock, null);
                    this.a.notifyAll();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class b implements ITransactionLock {
        private final String b;
        private final String c;

        b(String str, @NonNull String str2) {
            this.c = (String) Objects.requireNonNull(str);
            this.b = (String) Objects.requireNonNull(str2);
        }

        @Override // com.milink.base.utils.TransactionLockProvider.ITransactionLock
        public void startTransaction() throws InterruptedException {
            a aVar;
            synchronized (TransactionLockProvider.this.a) {
                aVar = (a) TransactionLockProvider.this.a.get(Objects.requireNonNull(this.c));
                if (aVar == null) {
                    aVar = new a();
                    TransactionLockProvider.this.a.put(this.c, aVar);
                }
            }
            TransactionLockProvider.this.a(this.c, this.b);
            aVar.a(this);
        }

        @Override // com.milink.base.utils.TransactionLockProvider.ITransactionLock
        public void endTransaction() {
            a aVar;
            synchronized (TransactionLockProvider.this.a) {
                aVar = (a) TransactionLockProvider.this.a.remove(Objects.requireNonNull(this.c));
            }
            if (aVar == null) {
                Logger.w("TransactionLock", "NOT found lock, ignore end transaction.", new Object[0]);
                return;
            }
            TransactionLockProvider.this.b(this.c, this.b);
            aVar.b(this);
        }
    }
}
