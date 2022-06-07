package com.xiaomi.micolauncher.skills.music.controller.playlist;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicIndexHelper {
    public static final int LOAD_MORE_MIN_COUNT = 1;
    private int a;
    private int c;
    private volatile boolean f;
    private int g;
    protected int loopType = LoopType.NONE.ordinal();
    private int b = 0;
    private List<Integer> d = new ArrayList();
    private int e = -1;
    private int h = -2;

    public void setLoadingMoreSuccess() {
    }

    public void setListCount(int i) {
        L.player.d("MusicIndexHelper setListCount=%d loopType=%s", Integer.valueOf(i), Integer.valueOf(this.loopType));
        this.c = i;
        if (this.loopType == LoopType.SHUFFLE.ordinal()) {
            updateShuffleIndexList(i);
        }
    }

    public List<Integer> getRandomIndexList() {
        return this.d;
    }

    public int getLoopType() {
        return this.loopType;
    }

    public boolean setLoopType(int i, boolean z) {
        Threads.verifyMainThread();
        if (i == LoopType.SHUFFLE.ordinal() && (z || ContainerUtil.isEmpty(this.d))) {
            if (this.c > 0) {
                this.d.clear();
                L.player.i(" MusicIndexHelper setLoopType shuffleIndexList.clear");
            }
            updateShuffleIndexList(this.c);
        }
        if (this.loopType == i || i < LoopType.SINGLE_LOOP.ordinal() || i > LoopType.NONE.ordinal()) {
            return false;
        }
        this.loopType = i;
        return true;
    }

    public boolean isSingleLoop() {
        Threads.verifyMainThread();
        return this.loopType == LoopType.SINGLE_LOOP.ordinal();
    }

    public boolean isShuffleLoop() {
        Threads.verifyMainThread();
        return this.loopType == LoopType.SHUFFLE.ordinal();
    }

    public int getRandomPlayIndexByOriginalIndex(int i) {
        Threads.verifyMainThread();
        if (isShuffleLoop()) {
            for (int i2 = 0; i2 < this.d.size(); i2++) {
                if (i == this.d.get(i2).intValue()) {
                    this.e = i2;
                    return i2;
                }
            }
        }
        return i;
    }

    public int getPlayingIndexInCurrentList() {
        Threads.verifyMainThread();
        if (!isShuffleLoop()) {
            return this.a;
        }
        if (this.e < 0) {
            this.e = 0;
        }
        return this.e;
    }

    public int getPlayingIndexInOriginalList() {
        Threads.verifyMainThread();
        return this.a;
    }

    public int getPlayingIndexInOriginalList(int i) {
        Threads.verifyMainThread();
        if (!isShuffleLoop()) {
            return this.a;
        }
        if (!ContainerUtil.isOutOfBound(i, this.d)) {
            return this.d.get(i).intValue();
        }
        return -1;
    }

    public void setPlayIndex(int i) {
        Threads.verifyMainThread();
        L.player.d("MusicIndexHelper setPlayIndex=%d", Integer.valueOf(i));
        if (i < 0) {
            i = 0;
        }
        if (isShuffleLoop()) {
            if (this.e < 0) {
                this.e = 0;
            }
            this.e = getRandomPlayIndexByOriginalIndex(i);
        }
        this.a = i;
    }

    public int shouldLoadMore(int i) {
        Threads.verifyMainThread();
        if (isShuffleLoop()) {
            if (i < 1 || this.e + 2 < i - 1) {
                return -1;
            }
            return getNextIndex(i);
        } else if (i < 1 || this.a + 2 < i - 1) {
            return -1;
        } else {
            return getNextIndex(i);
        }
    }

    public boolean shouldLoadMoreInShuffleLoop(int i) {
        Threads.verifyMainThread();
        return isShuffleLoop() && i >= 1 && this.e + 2 >= i - 1;
    }

    public int getPrevIndex(int i) {
        Threads.verifyMainThread();
        if (isShuffleLoop()) {
            this.e--;
            if (this.e < 0) {
                this.e = i - 1;
            }
            this.a = a(i);
        } else {
            this.a--;
            if (this.a < 0) {
                this.a = i - 1;
            }
        }
        if (this.a < 0) {
            this.a = 0;
        }
        return this.a;
    }

    public int getNextIndex(int i) {
        Threads.verifyMainThread();
        if (isShuffleLoop()) {
            this.e++;
            this.a = a(i);
        } else {
            this.a++;
        }
        return this.a;
    }

    private int a(int i) {
        if (i <= 0) {
            L.player.e("getRandomIndex : input index is illegal %s", Integer.valueOf(i));
            return 0;
        }
        int i2 = this.e;
        if (i2 < 0 || i2 >= this.d.size()) {
            this.e = 0;
        }
        Integer num = ContainerUtil.isOutOfBound((long) this.e, this.d) ? 0 : this.d.get(this.e);
        L.player.i("MusicIndexHelper getRandomIndex targetIndex=%s, randomPlayIndex=%s", num, Integer.valueOf(this.e));
        return num.intValue();
    }

    public void updateShuffleIndexList(int i) {
        Threads.verifyMainThread();
        if (i > 0) {
            boolean z = false;
            L.player.i("MusicIndexHelper getRandomIndex playIndex=%s lastPlayerListSize=%s，listCount=%s", Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(i));
            if (this.d.isEmpty()) {
                L.player.d("MusicIndexHelper getRandomIndex lastPlayerListSize is Empty");
                for (int i2 = 0; i2 < i; i2++) {
                    if (i2 != this.a) {
                        this.d.add(Integer.valueOf(i2));
                    }
                }
                a(this.d, false);
                if (!ContainerUtil.isOutOfBound(this.a, this.d)) {
                    List<Integer> list = this.d;
                    int i3 = this.a;
                    list.add(i3, Integer.valueOf(i3));
                }
            } else if (this.b < i) {
                ArrayList arrayList = new ArrayList();
                for (int i4 = this.b; i4 < i; i4++) {
                    if (i4 == this.a) {
                        z = true;
                    } else {
                        arrayList.add(Integer.valueOf(i4));
                    }
                }
                a(arrayList, true);
                if (z) {
                    if (!ContainerUtil.isOutOfBound(this.a, this.d)) {
                        List<Integer> list2 = this.d;
                        int i5 = this.a;
                        list2.add(i5, Integer.valueOf(i5));
                    } else {
                        this.d.add(Integer.valueOf(this.a));
                    }
                }
            }
            this.b = i;
        }
    }

    private void a(List<Integer> list, boolean z) {
        Collections.shuffle(list);
        if (z) {
            this.d.addAll(list);
        } else {
            this.d = list;
        }
    }

    public boolean isPlaying(int i) {
        Threads.verifyMainThread();
        return this.a == i && this.loopType != LoopType.SINGLE_LOOP.ordinal() && i > 0;
    }

    public void reset() {
        Threads.verifyMainThread();
        this.b = 0;
        this.c = 0;
        this.a = 0;
        this.e = 0;
        this.f = false;
        this.d.clear();
        L.player.i("MusicIndexHelper reset shuffleIndexList.clear");
    }

    public boolean checkPlayCompletePause() {
        int i = this.h;
        if (i < 0) {
            return false;
        }
        this.h = i - 1;
        return this.h <= 0;
    }

    public void pausePlayerByFinish(int i) {
        this.h = i;
    }

    public boolean isFinishOfPlayList() {
        return this.c == this.a + 1 && this.loopType == LoopType.ORDER.ordinal();
    }

    public boolean isFinishOfPlayList(int i) {
        return this.c == i + 1;
    }

    public boolean needLoadMore() {
        if (isShuffleLoop()) {
            return needLoadMore(this.e);
        }
        return needLoadMore(this.a);
    }

    public boolean needLoadMore(int i) {
        int i2;
        return !isLoadingMore() && (i2 = this.c) >= 0 && i > i2 + (-3);
    }

    public boolean isLoadingMore() {
        return this.f;
    }

    public void setLoadingMore(boolean z) {
        this.f = z;
        if (z) {
            this.g = this.c;
        }
    }
}
