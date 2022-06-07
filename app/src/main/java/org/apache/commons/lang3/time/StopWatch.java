package org.apache.commons.lang3.time;

import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class StopWatch {
    private b a = b.UNSTARTED;
    private a b = a.UNSPLIT;
    private long c;
    private long d;
    private long e;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum a {
        SPLIT,
        UNSPLIT
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum b {
        UNSTARTED {
            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean a() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean b() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean c() {
                return false;
            }
        },
        RUNNING {
            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean a() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean b() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean c() {
                return false;
            }
        },
        STOPPED {
            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean a() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean b() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean c() {
                return false;
            }
        },
        SUSPENDED {
            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean a() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean b() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.b
            boolean c() {
                return true;
            }
        };

        abstract boolean a();

        abstract boolean b();

        abstract boolean c();
    }

    public static StopWatch createStarted() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    public void start() {
        if (this.a == b.STOPPED) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
        } else if (this.a == b.UNSTARTED) {
            this.c = System.nanoTime();
            this.d = System.currentTimeMillis();
            this.a = b.RUNNING;
        } else {
            throw new IllegalStateException("Stopwatch already started. ");
        }
    }

    public void stop() {
        if (this.a == b.RUNNING || this.a == b.SUSPENDED) {
            if (this.a == b.RUNNING) {
                this.e = System.nanoTime();
            }
            this.a = b.STOPPED;
            return;
        }
        throw new IllegalStateException("Stopwatch is not running. ");
    }

    public void reset() {
        this.a = b.UNSTARTED;
        this.b = a.UNSPLIT;
    }

    public void split() {
        if (this.a == b.RUNNING) {
            this.e = System.nanoTime();
            this.b = a.SPLIT;
            return;
        }
        throw new IllegalStateException("Stopwatch is not running. ");
    }

    public void unsplit() {
        if (this.b == a.SPLIT) {
            this.b = a.UNSPLIT;
            return;
        }
        throw new IllegalStateException("Stopwatch has not been split. ");
    }

    public void suspend() {
        if (this.a == b.RUNNING) {
            this.e = System.nanoTime();
            this.a = b.SUSPENDED;
            return;
        }
        throw new IllegalStateException("Stopwatch must be running to suspend. ");
    }

    public void resume() {
        if (this.a == b.SUSPENDED) {
            this.c += System.nanoTime() - this.e;
            this.a = b.RUNNING;
            return;
        }
        throw new IllegalStateException("Stopwatch must be suspended to resume. ");
    }

    public long getTime() {
        return getNanoTime() / 1000000;
    }

    public long getTime(TimeUnit timeUnit) {
        return timeUnit.convert(getNanoTime(), TimeUnit.NANOSECONDS);
    }

    public long getNanoTime() {
        if (this.a == b.STOPPED || this.a == b.SUSPENDED) {
            return this.e - this.c;
        }
        if (this.a == b.UNSTARTED) {
            return 0L;
        }
        if (this.a == b.RUNNING) {
            return System.nanoTime() - this.c;
        }
        throw new RuntimeException("Illegal running state has occurred.");
    }

    public long getSplitTime() {
        return getSplitNanoTime() / 1000000;
    }

    public long getSplitNanoTime() {
        if (this.b == a.SPLIT) {
            return this.e - this.c;
        }
        throw new IllegalStateException("Stopwatch must be split to get the split time. ");
    }

    public long getStartTime() {
        if (this.a != b.UNSTARTED) {
            return this.d;
        }
        throw new IllegalStateException("Stopwatch has not been started");
    }

    public String toString() {
        return DurationFormatUtils.formatDurationHMS(getTime());
    }

    public String toSplitString() {
        return DurationFormatUtils.formatDurationHMS(getSplitTime());
    }

    public boolean isStarted() {
        return this.a.a();
    }

    public boolean isSuspended() {
        return this.a.c();
    }

    public boolean isStopped() {
        return this.a.b();
    }
}
