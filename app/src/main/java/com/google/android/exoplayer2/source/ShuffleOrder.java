package com.google.android.exoplayer2.source;

import java.util.Arrays;
import java.util.Random;

/* loaded from: classes2.dex */
public interface ShuffleOrder {
    ShuffleOrder cloneAndClear();

    ShuffleOrder cloneAndInsert(int i, int i2);

    ShuffleOrder cloneAndRemove(int i, int i2);

    int getFirstIndex();

    int getLastIndex();

    int getLength();

    int getNextIndex(int i);

    int getPreviousIndex(int i);

    /* loaded from: classes2.dex */
    public static class DefaultShuffleOrder implements ShuffleOrder {
        private final Random a;
        private final int[] b;
        private final int[] c;

        public DefaultShuffleOrder(int i) {
            this(i, new Random());
        }

        public DefaultShuffleOrder(int i, long j) {
            this(i, new Random(j));
        }

        public DefaultShuffleOrder(int[] iArr, long j) {
            this(Arrays.copyOf(iArr, iArr.length), new Random(j));
        }

        private DefaultShuffleOrder(int i, Random random) {
            this(a(i, random), random);
        }

        private DefaultShuffleOrder(int[] iArr, Random random) {
            this.b = iArr;
            this.a = random;
            this.c = new int[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                this.c[iArr[i]] = i;
            }
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLength() {
            return this.b.length;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getNextIndex(int i) {
            int i2 = this.c[i] + 1;
            int[] iArr = this.b;
            if (i2 < iArr.length) {
                return iArr[i2];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getPreviousIndex(int i) {
            int i2 = this.c[i] - 1;
            if (i2 >= 0) {
                return this.b[i2];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLastIndex() {
            int[] iArr = this.b;
            if (iArr.length > 0) {
                return iArr[iArr.length - 1];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getFirstIndex() {
            int[] iArr = this.b;
            if (iArr.length > 0) {
                return iArr[0];
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndInsert(int i, int i2) {
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            int i3 = 0;
            while (i3 < i2) {
                iArr[i3] = this.a.nextInt(this.b.length + 1);
                int i4 = i3 + 1;
                int nextInt = this.a.nextInt(i4);
                iArr2[i3] = iArr2[nextInt];
                iArr2[nextInt] = i3 + i;
                i3 = i4;
            }
            Arrays.sort(iArr);
            int[] iArr3 = new int[this.b.length + i2];
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < this.b.length + i2; i7++) {
                if (i5 >= i2 || i6 != iArr[i5]) {
                    i6++;
                    iArr3[i7] = this.b[i6];
                    if (iArr3[i7] >= i) {
                        iArr3[i7] = iArr3[i7] + i2;
                    }
                } else {
                    i5++;
                    iArr3[i7] = iArr2[i5];
                }
            }
            return new DefaultShuffleOrder(iArr3, new Random(this.a.nextLong()));
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndRemove(int i, int i2) {
            int i3 = i2 - i;
            int[] iArr = new int[this.b.length - i3];
            int i4 = 0;
            int i5 = 0;
            while (true) {
                int[] iArr2 = this.b;
                if (i4 >= iArr2.length) {
                    return new DefaultShuffleOrder(iArr, new Random(this.a.nextLong()));
                }
                if (iArr2[i4] < i || iArr2[i4] >= i2) {
                    int i6 = i4 - i5;
                    int[] iArr3 = this.b;
                    iArr[i6] = iArr3[i4] >= i ? iArr3[i4] - i3 : iArr3[i4];
                } else {
                    i5++;
                }
                i4++;
            }
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndClear() {
            return new DefaultShuffleOrder(0, new Random(this.a.nextLong()));
        }

        private static int[] a(int i, Random random) {
            int[] iArr = new int[i];
            int i2 = 0;
            while (i2 < i) {
                int i3 = i2 + 1;
                int nextInt = random.nextInt(i3);
                iArr[i2] = iArr[nextInt];
                iArr[nextInt] = i2;
                i2 = i3;
            }
            return iArr;
        }
    }

    /* loaded from: classes2.dex */
    public static final class UnshuffledShuffleOrder implements ShuffleOrder {
        private final int a;

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getPreviousIndex(int i) {
            int i2 = i - 1;
            if (i2 >= 0) {
                return i2;
            }
            return -1;
        }

        public UnshuffledShuffleOrder(int i) {
            this.a = i;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLength() {
            return this.a;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getNextIndex(int i) {
            int i2 = i + 1;
            if (i2 < this.a) {
                return i2;
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getLastIndex() {
            int i = this.a;
            if (i > 0) {
                return i - 1;
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public int getFirstIndex() {
            return this.a > 0 ? 0 : -1;
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndInsert(int i, int i2) {
            return new UnshuffledShuffleOrder(this.a + i2);
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndRemove(int i, int i2) {
            return new UnshuffledShuffleOrder((this.a - i2) + i);
        }

        @Override // com.google.android.exoplayer2.source.ShuffleOrder
        public ShuffleOrder cloneAndClear() {
            return new UnshuffledShuffleOrder(0);
        }
    }
}
