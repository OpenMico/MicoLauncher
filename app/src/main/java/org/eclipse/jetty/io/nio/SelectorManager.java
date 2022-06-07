package org.eclipse.jetty.io.nio;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public abstract class SelectorManager extends AbstractLifeCycle implements Dumpable {
    private long _lowResourcesConnections;
    private int _lowResourcesMaxIdleTime;
    private int _maxIdleTime;
    private SelectSet[] _selectSet;
    public static final Logger LOG = Log.getLogger("org.eclipse.jetty.io.nio");
    private static final int __MONITOR_PERIOD = Integer.getInteger("org.eclipse.jetty.io.nio.MONITOR_PERIOD", 1000).intValue();
    private static final int __MAX_SELECTS = Integer.getInteger("org.eclipse.jetty.io.nio.MAX_SELECTS", 100000).intValue();
    private static final int __BUSY_PAUSE = Integer.getInteger("org.eclipse.jetty.io.nio.BUSY_PAUSE", 50).intValue();
    private static final int __IDLE_TICK = Integer.getInteger("org.eclipse.jetty.io.nio.IDLE_TICK", 400).intValue();
    private int _selectSets = 1;
    private volatile int _set = 0;
    private boolean _deferringInterestedOps0 = true;
    private int _selectorPriorityDelta = 0;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface ChangeTask extends Runnable {
    }

    public abstract boolean dispatch(Runnable runnable);

    protected abstract void endPointClosed(SelectChannelEndPoint selectChannelEndPoint);

    protected abstract void endPointOpened(SelectChannelEndPoint selectChannelEndPoint);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection);

    public abstract AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj);

    protected abstract SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectSet selectSet, SelectionKey selectionKey) throws IOException;

    public void setMaxIdleTime(long j) {
        this._maxIdleTime = (int) j;
    }

    public void setSelectSets(int i) {
        this._selectSets = i;
        this._lowResourcesConnections = (this._lowResourcesConnections * this._selectSets) / this._selectSets;
    }

    public long getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public int getSelectSets() {
        return this._selectSets;
    }

    public SelectSet getSelectSet(int i) {
        return this._selectSet[i];
    }

    public void register(SocketChannel socketChannel, Object obj) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        int i2 = i % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i2];
            selectSet.addChange(socketChannel, obj);
            selectSet.wakeup();
        }
    }

    public void register(SocketChannel socketChannel) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        int i2 = i % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i2];
            selectSet.addChange(socketChannel);
            selectSet.wakeup();
        }
    }

    public void register(ServerSocketChannel serverSocketChannel) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        SelectSet selectSet = this._selectSet[i % this._selectSets];
        selectSet.addChange(serverSocketChannel);
        selectSet.wakeup();
    }

    public int getSelectorPriorityDelta() {
        return this._selectorPriorityDelta;
    }

    public void setSelectorPriorityDelta(int i) {
        this._selectorPriorityDelta = i;
    }

    public long getLowResourcesConnections() {
        return this._lowResourcesConnections * this._selectSets;
    }

    public void setLowResourcesConnections(long j) {
        int i = this._selectSets;
        this._lowResourcesConnections = ((j + i) - 1) / i;
    }

    public long getLowResourcesMaxIdleTime() {
        return this._lowResourcesMaxIdleTime;
    }

    public void setLowResourcesMaxIdleTime(long j) {
        this._lowResourcesMaxIdleTime = (int) j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._selectSet = new SelectSet[this._selectSets];
        int i = 0;
        while (true) {
            SelectSet[] selectSetArr = this._selectSet;
            if (i >= selectSetArr.length) {
                break;
            }
            selectSetArr[i] = new SelectSet(i);
            i++;
        }
        super.doStart();
        for (final int i2 = 0; i2 < getSelectSets(); i2++) {
            if (!dispatch(new Runnable() { // from class: org.eclipse.jetty.io.nio.SelectorManager.1
                @Override // java.lang.Runnable
                public void run() {
                    String name = Thread.currentThread().getName();
                    int priority = Thread.currentThread().getPriority();
                    try {
                        SelectSet[] selectSetArr2 = SelectorManager.this._selectSet;
                        if (selectSetArr2 == null) {
                            SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                            Thread.currentThread().setName(name);
                            if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                                Thread.currentThread().setPriority(priority);
                                return;
                            }
                            return;
                        }
                        SelectSet selectSet = selectSetArr2[i2];
                        Thread currentThread = Thread.currentThread();
                        currentThread.setName(name + " Selector" + i2);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(Thread.currentThread().getPriority() + SelectorManager.this.getSelectorPriorityDelta());
                        }
                        SelectorManager.LOG.debug("Starting {} on {}", Thread.currentThread(), this);
                        while (SelectorManager.this.isRunning()) {
                            try {
                                selectSet.doSelect();
                            } catch (IOException e) {
                                SelectorManager.LOG.ignore(e);
                            } catch (Exception e2) {
                                SelectorManager.LOG.warn(e2);
                            }
                        }
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                    } catch (Throwable th) {
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                        throw th;
                    }
                }
            })) {
                throw new IllegalStateException("!Selecting");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        SelectSet[] selectSetArr = this._selectSet;
        this._selectSet = null;
        if (selectSetArr != null) {
            for (SelectSet selectSet : selectSetArr) {
                if (selectSet != null) {
                    selectSet.stop();
                }
            }
        }
        super.doStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void connectionFailed(SocketChannel socketChannel, Throwable th, Object obj) {
        Logger logger = LOG;
        logger.warn(th + Constants.ACCEPT_TIME_SEPARATOR_SP + socketChannel + Constants.ACCEPT_TIME_SEPARATOR_SP + obj, new Object[0]);
        LOG.debug(th);
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        AggregateLifeCycle.dumpObject(appendable, this);
        AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(this._selectSet));
    }

    /* loaded from: classes5.dex */
    public class SelectSet implements Dumpable {
        private int _busySelects;
        private boolean _paused;
        private boolean _pausing;
        private volatile Thread _selecting;
        private final int _setID;
        private final ConcurrentLinkedQueue<Object> _changes = new ConcurrentLinkedQueue<>();
        private ConcurrentMap<SelectChannelEndPoint, Object> _endPoints = new ConcurrentHashMap();
        private volatile long _idleTick = System.currentTimeMillis();
        private final Timeout _timeout = new Timeout(this);
        private volatile Selector _selector = Selector.open();
        private long _monitorNext = System.currentTimeMillis() + SelectorManager.__MONITOR_PERIOD;

        SelectSet(int i) throws Exception {
            this._setID = i;
            this._timeout.setDuration(0L);
        }

        public void addChange(Object obj) {
            this._changes.add(obj);
        }

        public void addChange(SelectableChannel selectableChannel, Object obj) {
            if (obj == null) {
                addChange(selectableChannel);
            } else if (obj instanceof EndPoint) {
                addChange(obj);
            } else {
                addChange(new ChannelAndAttachment(selectableChannel, obj));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:57:0x00d1, code lost:
            r2 = r1.selectNow();
            r5 = java.lang.System.currentTimeMillis();
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00dc, code lost:
            if (r2 != 0) goto L_0x0162;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x00e6, code lost:
            if (r1.selectedKeys().isEmpty() == false) goto L_0x0162;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00ea, code lost:
            if (r14._pausing == false) goto L_0x00ff;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00ec, code lost:
            java.lang.Thread.sleep(org.eclipse.jetty.io.nio.SelectorManager.__BUSY_PAUSE);
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00f5, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00f6, code lost:
            org.eclipse.jetty.io.nio.SelectorManager.LOG.ignore(r2);
         */
        /* JADX WARN: Removed duplicated region for block: B:134:0x020b A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:135:0x0211 A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TRY_LEAVE, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:150:0x0257 A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:156:0x0276 A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:204:0x00bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:206:0x0218 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:216:0x00ce A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00b0 A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x00b6 A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TRY_LEAVE, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:98:0x017c A[Catch: ClosedSelectorException -> 0x02d8, CancelledKeyException -> 0x02cf, all -> 0x02cd, TRY_LEAVE, TryCatch #1 {ClosedSelectorException -> 0x02d8, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:12:0x0021, B:14:0x0025, B:15:0x002b, B:18:0x0034, B:20:0x0038, B:21:0x003c, B:23:0x0042, B:25:0x004b, B:26:0x005e, B:28:0x0064, B:30:0x006d, B:32:0x0071, B:33:0x0073, B:36:0x0086, B:38:0x008a, B:39:0x0090, B:41:0x0094, B:42:0x009c, B:43:0x00a5, B:46:0x00a8, B:48:0x00b0, B:49:0x00b6, B:51:0x00bd, B:53:0x00c2, B:55:0x00c9, B:57:0x00d1, B:59:0x00de, B:61:0x00e8, B:63:0x00ec, B:65:0x00f6, B:66:0x00fb, B:67:0x00ff, B:69:0x0112, B:81:0x012b, B:83:0x013d, B:85:0x0144, B:87:0x014f, B:89:0x0155, B:90:0x0162, B:92:0x0166, B:95:0x016e, B:96:0x0176, B:98:0x017c, B:99:0x0182, B:101:0x0188, B:103:0x0193, B:104:0x0197, B:106:0x019f, B:108:0x01a5, B:110:0x01ab, B:111:0x01b1, B:113:0x01b7, B:116:0x01c3, B:117:0x01d1, B:122:0x01e0, B:123:0x01e3, B:124:0x01e4, B:125:0x01ea, B:127:0x01f7, B:132:0x0203, B:134:0x020b, B:135:0x0211, B:137:0x0218, B:139:0x021d, B:141:0x0224, B:143:0x022c, B:145:0x0232, B:147:0x0238, B:148:0x023f, B:150:0x0257, B:152:0x025b, B:153:0x0262, B:154:0x0269, B:156:0x0276, B:158:0x0282, B:160:0x0295, B:162:0x02a7, B:163:0x02b1, B:165:0x02b7, B:167:0x02bd), top: B:184:0x0001, outer: #6 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doSelect() throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 753
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.doSelect():void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void renewSelector() {
            try {
                synchronized (this) {
                    Selector selector = this._selector;
                    if (selector != null) {
                        Selector open = Selector.open();
                        for (SelectionKey selectionKey : selector.keys()) {
                            if (selectionKey.isValid() && selectionKey.interestOps() != 0) {
                                SelectableChannel channel = selectionKey.channel();
                                Object attachment = selectionKey.attachment();
                                if (attachment == null) {
                                    addChange(channel);
                                } else {
                                    addChange(channel, attachment);
                                }
                            }
                        }
                        this._selector.close();
                        this._selector = open;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("recreating selector", e);
            }
        }

        public SelectorManager getManager() {
            return SelectorManager.this;
        }

        public long getNow() {
            return this._timeout.getNow();
        }

        public void scheduleTimeout(Timeout.Task task, long j) {
            if (task instanceof Runnable) {
                this._timeout.schedule(task, j);
                return;
            }
            throw new IllegalArgumentException("!Runnable");
        }

        public void cancelTimeout(Timeout.Task task) {
            task.cancel();
        }

        public void wakeup() {
            try {
                Selector selector = this._selector;
                if (selector != null) {
                    selector.wakeup();
                }
            } catch (Exception unused) {
                addChange(new ChangeTask() { // from class: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SelectSet.this.renewSelector();
                    }
                });
                renewSelector();
            }
        }

        private SelectChannelEndPoint createEndPoint(SocketChannel socketChannel, SelectionKey selectionKey) throws IOException {
            SelectChannelEndPoint newEndPoint = SelectorManager.this.newEndPoint(socketChannel, this, selectionKey);
            SelectorManager.LOG.debug("created {}", newEndPoint);
            SelectorManager.this.endPointOpened(newEndPoint);
            this._endPoints.put(newEndPoint, this);
            return newEndPoint;
        }

        public void destroyEndPoint(SelectChannelEndPoint selectChannelEndPoint) {
            SelectorManager.LOG.debug("destroyEndPoint {}", selectChannelEndPoint);
            this._endPoints.remove(selectChannelEndPoint);
            SelectorManager.this.endPointClosed(selectChannelEndPoint);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Selector getSelector() {
            return this._selector;
        }

        void stop() throws Exception {
            for (int i = 0; i < 100; i++) {
                try {
                    if (this._selecting == null) {
                        break;
                    }
                    wakeup();
                    Thread.sleep(10L);
                } catch (Exception e) {
                    SelectorManager.LOG.ignore(e);
                }
            }
            synchronized (this) {
                for (SelectionKey selectionKey : this._selector.keys()) {
                    if (selectionKey != null) {
                        Object attachment = selectionKey.attachment();
                        if (attachment instanceof EndPoint) {
                            try {
                                ((EndPoint) attachment).close();
                            } catch (IOException e2) {
                                SelectorManager.LOG.ignore(e2);
                            }
                        }
                    }
                }
                this._timeout.cancelAll();
                try {
                    Selector selector = this._selector;
                    if (selector != null) {
                        selector.close();
                    }
                } catch (IOException e3) {
                    SelectorManager.LOG.ignore(e3);
                }
                this._selector = null;
            }
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public String dump() {
            return AggregateLifeCycle.dump(this);
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public void dump(Appendable appendable, String str) throws IOException {
            appendable.append(String.valueOf(this)).append(" id=").append(String.valueOf(this._setID)).append("\n");
            Thread thread = this._selecting;
            Object obj = "not selecting";
            StackTraceElement[] stackTrace = thread == null ? null : thread.getStackTrace();
            if (stackTrace != null) {
                int length = stackTrace.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTrace[i];
                    if (stackTraceElement.getClassName().startsWith("org.eclipse.jetty.")) {
                        obj = stackTraceElement;
                        break;
                    }
                    i++;
                }
            }
            Selector selector = this._selector;
            if (selector != null) {
                final ArrayList arrayList = new ArrayList(selector.keys().size() * 2);
                arrayList.add(obj);
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                addChange(new ChangeTask() { // from class: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SelectSet.this.dumpKeyState(arrayList);
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await(5L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    SelectorManager.LOG.ignore(e);
                }
                AggregateLifeCycle.dump(appendable, str, arrayList);
            }
        }

        public void dumpKeyState(List<Object> list) {
            Selector selector = this._selector;
            Set<SelectionKey> keys = selector.keys();
            list.add(selector + " keys=" + keys.size());
            for (SelectionKey selectionKey : keys) {
                if (selectionKey.isValid()) {
                    list.add(selectionKey.attachment() + " iOps=" + selectionKey.interestOps() + " rOps=" + selectionKey.readyOps());
                } else {
                    list.add(selectionKey.attachment() + " iOps=-1 rOps=-1");
                }
            }
        }

        public String toString() {
            Selector selector = this._selector;
            Object[] objArr = new Object[3];
            objArr[0] = super.toString();
            int i = -1;
            objArr[1] = Integer.valueOf((selector == null || !selector.isOpen()) ? -1 : selector.keys().size());
            if (selector != null && selector.isOpen()) {
                i = selector.selectedKeys().size();
            }
            objArr[2] = Integer.valueOf(i);
            return String.format("%s keys=%d selected=%d", objArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class ChannelAndAttachment {
        final Object _attachment;
        final SelectableChannel _channel;

        public ChannelAndAttachment(SelectableChannel selectableChannel, Object obj) {
            this._channel = selectableChannel;
            this._attachment = obj;
        }
    }

    public boolean isDeferringInterestedOps0() {
        return this._deferringInterestedOps0;
    }

    public void setDeferringInterestedOps0(boolean z) {
        this._deferringInterestedOps0 = z;
    }
}
