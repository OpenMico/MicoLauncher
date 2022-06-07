package org.apache.commons.lang.time;

/* loaded from: classes5.dex */
public class StopWatch {
    private int a = 0;
    private int b = 10;
    private long c = -1;
    private long d = -1;

    public void start() {
        int i = this.a;
        if (i == 2) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
        } else if (i == 0) {
            this.d = -1L;
            this.c = System.currentTimeMillis();
            this.a = 1;
        } else {
            throw new IllegalStateException("Stopwatch already started. ");
        }
    }

    public void stop() {
        int i = this.a;
        if (i == 1 || i == 3) {
            if (this.a == 1) {
                this.d = System.currentTimeMillis();
            }
            this.a = 2;
            return;
        }
        throw new IllegalStateException("Stopwatch is not running. ");
    }

    public void reset() {
        this.a = 0;
        this.b = 10;
        this.c = -1L;
        this.d = -1L;
    }

    public void split() {
        if (this.a == 1) {
            this.d = System.currentTimeMillis();
            this.b = 11;
            return;
        }
        throw new IllegalStateException("Stopwatch is not running. ");
    }

    public void unsplit() {
        if (this.b == 11) {
            this.d = -1L;
            this.b = 10;
            return;
        }
        throw new IllegalStateException("Stopwatch has not been split. ");
    }

    public void suspend() {
        if (this.a == 1) {
            this.d = System.currentTimeMillis();
            this.a = 3;
            return;
        }
        throw new IllegalStateException("Stopwatch must be running to suspend. ");
    }

    public void resume() {
        if (this.a == 3) {
            this.c += System.currentTimeMillis() - this.d;
            this.d = -1L;
            this.a = 1;
            return;
        }
        throw new IllegalStateException("Stopwatch must be suspended to resume. ");
    }

    public long getTime() {
        int i = this.a;
        if (i == 2 || i == 3) {
            return this.d - this.c;
        }
        if (i == 0) {
            return 0L;
        }
        if (i == 1) {
            return System.currentTimeMillis() - this.c;
        }
        throw new RuntimeException("Illegal running state has occured. ");
    }

    public long getSplitTime() {
        if (this.b == 11) {
            return this.d - this.c;
        }
        throw new IllegalStateException("Stopwatch must be split to get the split time. ");
    }

    public long getStartTime() {
        if (this.a != 0) {
            return this.c;
        }
        throw new IllegalStateException("Stopwatch has not been started");
    }

    public String toString() {
        return DurationFormatUtils.formatDurationHMS(getTime());
    }

    public String toSplitString() {
        return DurationFormatUtils.formatDurationHMS(getSplitTime());
    }
}
