package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* loaded from: classes5.dex */
public class ThreadUtils {
    public static final a ALWAYS_TRUE_PREDICATE = new a();

    /* loaded from: classes5.dex */
    public interface ThreadGroupPredicate {
        boolean test(ThreadGroup threadGroup);
    }

    /* loaded from: classes5.dex */
    public interface ThreadPredicate {
        boolean test(Thread thread);
    }

    public static Thread findThreadById(long j, ThreadGroup threadGroup) {
        if (threadGroup != null) {
            Thread findThreadById = findThreadById(j);
            if (findThreadById == null || !threadGroup.equals(findThreadById.getThreadGroup())) {
                return null;
            }
            return findThreadById;
        }
        throw new IllegalArgumentException("The thread group must not be null");
    }

    public static Thread findThreadById(long j, String str) {
        if (str != null) {
            Thread findThreadById = findThreadById(j);
            if (findThreadById == null || findThreadById.getThreadGroup() == null || !findThreadById.getThreadGroup().getName().equals(str)) {
                return null;
            }
            return findThreadById;
        }
        throw new IllegalArgumentException("The thread group name must not be null");
    }

    public static Collection<Thread> findThreadsByName(String str, ThreadGroup threadGroup) {
        return findThreads(threadGroup, false, new NamePredicate(str));
    }

    public static Collection<Thread> findThreadsByName(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("The thread name must not be null");
        } else if (str2 != null) {
            Collection<ThreadGroup> findThreadGroups = findThreadGroups(new NamePredicate(str2));
            if (findThreadGroups.isEmpty()) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            NamePredicate namePredicate = new NamePredicate(str);
            for (ThreadGroup threadGroup : findThreadGroups) {
                arrayList.addAll(findThreads(threadGroup, false, namePredicate));
            }
            return Collections.unmodifiableCollection(arrayList);
        } else {
            throw new IllegalArgumentException("The thread group name must not be null");
        }
    }

    public static Collection<ThreadGroup> findThreadGroupsByName(String str) {
        return findThreadGroups(new NamePredicate(str));
    }

    public static Collection<ThreadGroup> getAllThreadGroups() {
        return findThreadGroups(ALWAYS_TRUE_PREDICATE);
    }

    public static ThreadGroup getSystemThreadGroup() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
        }
        return threadGroup;
    }

    public static Collection<Thread> getAllThreads() {
        return findThreads(ALWAYS_TRUE_PREDICATE);
    }

    public static Collection<Thread> findThreadsByName(String str) {
        return findThreads(new NamePredicate(str));
    }

    public static Thread findThreadById(long j) {
        Collection<Thread> findThreads = findThreads(new ThreadIdPredicate(j));
        if (findThreads.isEmpty()) {
            return null;
        }
        return findThreads.iterator().next();
    }

    /* loaded from: classes5.dex */
    private static final class a implements ThreadGroupPredicate, ThreadPredicate {
        @Override // org.apache.commons.lang3.ThreadUtils.ThreadPredicate
        public boolean test(Thread thread) {
            return true;
        }

        @Override // org.apache.commons.lang3.ThreadUtils.ThreadGroupPredicate
        public boolean test(ThreadGroup threadGroup) {
            return true;
        }

        private a() {
        }
    }

    /* loaded from: classes5.dex */
    public static class NamePredicate implements ThreadGroupPredicate, ThreadPredicate {
        private final String a;

        public NamePredicate(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new IllegalArgumentException("The name must not be null");
        }

        @Override // org.apache.commons.lang3.ThreadUtils.ThreadGroupPredicate
        public boolean test(ThreadGroup threadGroup) {
            return threadGroup != null && threadGroup.getName().equals(this.a);
        }

        @Override // org.apache.commons.lang3.ThreadUtils.ThreadPredicate
        public boolean test(Thread thread) {
            return thread != null && thread.getName().equals(this.a);
        }
    }

    /* loaded from: classes5.dex */
    public static class ThreadIdPredicate implements ThreadPredicate {
        private final long a;

        public ThreadIdPredicate(long j) {
            if (j > 0) {
                this.a = j;
                return;
            }
            throw new IllegalArgumentException("The thread id must be greater than zero");
        }

        @Override // org.apache.commons.lang3.ThreadUtils.ThreadPredicate
        public boolean test(Thread thread) {
            return thread != null && thread.getId() == this.a;
        }
    }

    public static Collection<Thread> findThreads(ThreadPredicate threadPredicate) {
        return findThreads(getSystemThreadGroup(), true, threadPredicate);
    }

    public static Collection<ThreadGroup> findThreadGroups(ThreadGroupPredicate threadGroupPredicate) {
        return findThreadGroups(getSystemThreadGroup(), true, threadGroupPredicate);
    }

    public static Collection<Thread> findThreads(ThreadGroup threadGroup, boolean z, ThreadPredicate threadPredicate) {
        Thread[] threadArr;
        int enumerate;
        if (threadGroup == null) {
            throw new IllegalArgumentException("The group must not be null");
        } else if (threadPredicate != null) {
            int activeCount = threadGroup.activeCount();
            while (true) {
                threadArr = new Thread[activeCount + (activeCount / 2) + 1];
                enumerate = threadGroup.enumerate(threadArr, z);
                if (enumerate < threadArr.length) {
                    break;
                }
                activeCount = enumerate;
            }
            ArrayList arrayList = new ArrayList(enumerate);
            for (int i = 0; i < enumerate; i++) {
                if (threadPredicate.test(threadArr[i])) {
                    arrayList.add(threadArr[i]);
                }
            }
            return Collections.unmodifiableCollection(arrayList);
        } else {
            throw new IllegalArgumentException("The predicate must not be null");
        }
    }

    public static Collection<ThreadGroup> findThreadGroups(ThreadGroup threadGroup, boolean z, ThreadGroupPredicate threadGroupPredicate) {
        ThreadGroup[] threadGroupArr;
        int enumerate;
        if (threadGroup == null) {
            throw new IllegalArgumentException("The group must not be null");
        } else if (threadGroupPredicate != null) {
            int activeGroupCount = threadGroup.activeGroupCount();
            while (true) {
                threadGroupArr = new ThreadGroup[activeGroupCount + (activeGroupCount / 2) + 1];
                enumerate = threadGroup.enumerate(threadGroupArr, z);
                if (enumerate < threadGroupArr.length) {
                    break;
                }
                activeGroupCount = enumerate;
            }
            ArrayList arrayList = new ArrayList(enumerate);
            for (int i = 0; i < enumerate; i++) {
                if (threadGroupPredicate.test(threadGroupArr[i])) {
                    arrayList.add(threadGroupArr[i]);
                }
            }
            return Collections.unmodifiableCollection(arrayList);
        } else {
            throw new IllegalArgumentException("The predicate must not be null");
        }
    }
}
