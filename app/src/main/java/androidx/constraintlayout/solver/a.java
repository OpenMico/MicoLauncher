package androidx.constraintlayout.solver;

/* compiled from: Pools.java */
/* loaded from: classes.dex */
final class a {

    /* compiled from: Pools.java */
    /* renamed from: androidx.constraintlayout.solver.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    interface AbstractC0006a<T> {
        T a();

        void a(T[] tArr, int i);

        boolean a(T t);
    }

    /* compiled from: Pools.java */
    /* loaded from: classes.dex */
    static class b<T> implements AbstractC0006a<T> {
        private final Object[] a;
        private int b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(int i) {
            if (i > 0) {
                this.a = new Object[i];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        @Override // androidx.constraintlayout.solver.a.AbstractC0006a
        public T a() {
            int i = this.b;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            Object[] objArr = this.a;
            T t = (T) objArr[i2];
            objArr[i2] = null;
            this.b = i - 1;
            return t;
        }

        @Override // androidx.constraintlayout.solver.a.AbstractC0006a
        public boolean a(T t) {
            int i = this.b;
            Object[] objArr = this.a;
            if (i >= objArr.length) {
                return false;
            }
            objArr[i] = t;
            this.b = i + 1;
            return true;
        }

        @Override // androidx.constraintlayout.solver.a.AbstractC0006a
        public void a(T[] tArr, int i) {
            if (i > tArr.length) {
                i = tArr.length;
            }
            for (int i2 = 0; i2 < i; i2++) {
                T t = tArr[i2];
                int i3 = this.b;
                Object[] objArr = this.a;
                if (i3 < objArr.length) {
                    objArr[i3] = t;
                    this.b = i3 + 1;
                }
            }
        }
    }
}
