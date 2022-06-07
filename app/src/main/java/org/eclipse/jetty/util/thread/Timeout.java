package org.eclipse.jetty.util.thread;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class Timeout {
    private static final Logger LOG = Log.getLogger(Timeout.class);
    private long _duration;
    private Task _head;
    private Object _lock;
    private volatile long _now;

    public Timeout() {
        this._now = System.currentTimeMillis();
        this._head = new Task();
        this._lock = new Object();
        this._head._timeout = this;
    }

    public Timeout(Object obj) {
        this._now = System.currentTimeMillis();
        this._head = new Task();
        this._lock = obj;
        this._head._timeout = this;
    }

    public long getDuration() {
        return this._duration;
    }

    public void setDuration(long j) {
        this._duration = j;
    }

    public long setNow() {
        long currentTimeMillis = System.currentTimeMillis();
        this._now = currentTimeMillis;
        return currentTimeMillis;
    }

    public long getNow() {
        return this._now;
    }

    public void setNow(long j) {
        this._now = j;
    }

    public Task expired() {
        synchronized (this._lock) {
            long j = this._now - this._duration;
            if (this._head._next == this._head) {
                return null;
            }
            Task task = this._head._next;
            if (task._timestamp > j) {
                return null;
            }
            task.unlink();
            task._expired = true;
            return task;
        }
    }

    public void tick() {
        Task task;
        long j = this._now - this._duration;
        while (true) {
            try {
                synchronized (this._lock) {
                    task = this._head._next;
                    if (task != this._head && task._timestamp <= j) {
                        task.unlink();
                        task._expired = true;
                        task.expire();
                    }
                    return;
                }
                task.expired();
            } catch (Throwable th) {
                LOG.warn(Log.EXCEPTION, th);
            }
        }
    }

    public void tick(long j) {
        this._now = j;
        tick();
    }

    public void schedule(Task task) {
        schedule(task, 0L);
    }

    public void schedule(Task task, long j) {
        synchronized (this._lock) {
            if (task._timestamp != 0) {
                task.unlink();
                task._timestamp = 0L;
            }
            task._timeout = this;
            task._expired = false;
            task._delay = j;
            task._timestamp = this._now + j;
            Task task2 = this._head._prev;
            while (task2 != this._head && task2._timestamp > task._timestamp) {
                task2 = task2._prev;
            }
            task2.link(task);
        }
    }

    public void cancelAll() {
        synchronized (this._lock) {
            Task task = this._head;
            Task task2 = this._head;
            Task task3 = this._head;
            task2._prev = task3;
            task._next = task3;
        }
    }

    public boolean isEmpty() {
        boolean z;
        synchronized (this._lock) {
            z = this._head._next == this._head;
        }
        return z;
    }

    public long getTimeToNext() {
        synchronized (this._lock) {
            if (this._head._next == this._head) {
                return -1L;
            }
            long j = (this._duration + this._head._next._timestamp) - this._now;
            if (j < 0) {
                j = 0;
            }
            return j;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        for (Task task = this._head._next; task != this._head; task = task._next) {
            stringBuffer.append("-->");
            stringBuffer.append(task);
        }
        return stringBuffer.toString();
    }

    /* loaded from: classes5.dex */
    public static class Task {
        long _delay;
        Timeout _timeout;
        long _timestamp = 0;
        boolean _expired = false;
        Task _prev = this;
        Task _next = this;

        protected void expire() {
        }

        public void expired() {
        }

        public long getTimestamp() {
            return this._timestamp;
        }

        public long getAge() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                long j = timeout._now;
                if (j != 0) {
                    long j2 = this._timestamp;
                    if (j2 != 0) {
                        return j - j2;
                    }
                }
            }
            return 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unlink() {
            Task task = this._next;
            task._prev = this._prev;
            this._prev._next = task;
            this._prev = this;
            this._next = this;
            this._expired = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void link(Task task) {
            Task task2 = this._next;
            task2._prev = task;
            this._next = task;
            this._next._next = task2;
            this._next._prev = this;
        }

        public void schedule(Timeout timeout) {
            timeout.schedule(this);
        }

        public void schedule(Timeout timeout, long j) {
            timeout.schedule(this, j);
        }

        public void reschedule() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                timeout.schedule(this, this._delay);
            }
        }

        public void cancel() {
            Timeout timeout = this._timeout;
            if (timeout != null) {
                synchronized (timeout._lock) {
                    unlink();
                    this._timestamp = 0L;
                }
            }
        }

        public boolean isExpired() {
            return this._expired;
        }

        public boolean isScheduled() {
            return this._next != this;
        }
    }
}
