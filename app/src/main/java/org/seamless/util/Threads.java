package org.seamless.util;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class Threads {
    public static ThreadGroup getRootThreadGroup() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (true) {
            ThreadGroup parent = threadGroup.getParent();
            if (parent == null) {
                return threadGroup;
            }
            threadGroup = parent;
        }
    }

    public static Thread[] getAllThreads() {
        Thread[] threadArr;
        int enumerate;
        ThreadGroup rootThreadGroup = getRootThreadGroup();
        int threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
        do {
            threadCount *= 2;
            threadArr = new Thread[threadCount];
            enumerate = rootThreadGroup.enumerate(threadArr, true);
        } while (enumerate == threadCount);
        return (Thread[]) Arrays.copyOf(threadArr, enumerate);
    }

    public static Thread getThread(long j) {
        Thread[] allThreads = getAllThreads();
        for (Thread thread : allThreads) {
            if (thread.getId() == j) {
                return thread;
            }
        }
        return null;
    }
}
