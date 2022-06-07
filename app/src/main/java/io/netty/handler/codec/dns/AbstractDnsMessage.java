package io.netty.handler.codec.dns;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.UShort;

/* loaded from: classes4.dex */
public abstract class AbstractDnsMessage extends AbstractReferenceCounted implements DnsMessage {
    private static final ResourceLeakDetector<DnsMessage> a = new ResourceLeakDetector<>(DnsMessage.class);
    private static final int b = DnsSection.QUESTION.ordinal();
    private final ResourceLeak c;
    private short d;
    private DnsOpCode e;
    private boolean f;
    private byte g;
    private Object h;
    private Object i;
    private Object j;
    private Object k;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractDnsMessage(int i) {
        this(i, DnsOpCode.QUERY);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractDnsMessage(int i, DnsOpCode dnsOpCode) {
        this.c = a.open(this);
        setId(i);
        setOpCode(dnsOpCode);
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public int id() {
        return this.d & UShort.MAX_VALUE;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage setId(int i) {
        this.d = (short) i;
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsOpCode opCode() {
        return this.e;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage setOpCode(DnsOpCode dnsOpCode) {
        this.e = (DnsOpCode) ObjectUtil.checkNotNull(dnsOpCode, "opCode");
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public boolean isRecursionDesired() {
        return this.f;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage setRecursionDesired(boolean z) {
        this.f = z;
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public int z() {
        return this.g;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage setZ(int i) {
        this.g = (byte) (i & 7);
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public int count(DnsSection dnsSection) {
        return a(a(dnsSection));
    }

    private int a(int i) {
        Object d = d(i);
        if (d == null) {
            return 0;
        }
        if (d instanceof DnsRecord) {
            return 1;
        }
        return ((List) d).size();
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public int count() {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += a(i2);
        }
        return i;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public <T extends DnsRecord> T recordAt(DnsSection dnsSection) {
        return (T) b(a(dnsSection));
    }

    private <T extends DnsRecord> T b(int i) {
        Object d = d(i);
        if (d == null) {
            return null;
        }
        if (d instanceof DnsRecord) {
            return (T) a(d);
        }
        List list = (List) d;
        if (list.isEmpty()) {
            return null;
        }
        return (T) a(list.get(0));
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public <T extends DnsRecord> T recordAt(DnsSection dnsSection, int i) {
        return (T) a(a(dnsSection), i);
    }

    private <T extends DnsRecord> T a(int i, int i2) {
        Object d = d(i);
        if (d == null) {
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: none)");
        } else if (!(d instanceof DnsRecord)) {
            return (T) a(((List) d).get(i2));
        } else {
            if (i2 == 0) {
                return (T) a(d);
            }
            throw new IndexOutOfBoundsException("index: " + i2 + "' (expected: 0)");
        }
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        a(a(dnsSection), dnsRecord);
        return this;
    }

    private void a(int i, DnsRecord dnsRecord) {
        c(i);
        a(i, (Object) c(i, dnsRecord));
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public <T extends DnsRecord> T setRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        return (T) a(a(dnsSection), i, dnsRecord);
    }

    private <T extends DnsRecord> T a(int i, int i2, DnsRecord dnsRecord) {
        c(i, dnsRecord);
        Object d = d(i);
        if (d == null) {
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: none)");
        } else if (!(d instanceof DnsRecord)) {
            return (T) a(((List) d).set(i2, dnsRecord));
        } else {
            if (i2 == 0) {
                a(i, (Object) dnsRecord);
                return (T) a(d);
            }
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: 0)");
        }
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        b(a(dnsSection), dnsRecord);
        return this;
    }

    private void b(int i, DnsRecord dnsRecord) {
        c(i, dnsRecord);
        Object d = d(i);
        if (d == null) {
            a(i, (Object) dnsRecord);
        } else if (d instanceof DnsRecord) {
            ArrayList<DnsRecord> a2 = a();
            a2.add(a(d));
            a2.add(dnsRecord);
            a(i, a2);
        } else {
            ((List) d).add(dnsRecord);
        }
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord) {
        b(a(dnsSection), i, dnsRecord);
        return this;
    }

    private void b(int i, int i2, DnsRecord dnsRecord) {
        ArrayList<DnsRecord> arrayList;
        c(i, dnsRecord);
        Object d = d(i);
        if (d == null) {
            if (i2 == 0) {
                a(i, (Object) dnsRecord);
                return;
            }
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: 0)");
        } else if (d instanceof DnsRecord) {
            if (i2 == 0) {
                arrayList = a();
                arrayList.add(dnsRecord);
                arrayList.add(a(d));
            } else if (i2 == 1) {
                arrayList = a();
                arrayList.add(a(d));
                arrayList.add(dnsRecord);
            } else {
                throw new IndexOutOfBoundsException("index: " + i2 + " (expected: 0 or 1)");
            }
            a(i, arrayList);
        } else {
            ((List) d).add(i2, dnsRecord);
        }
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public <T extends DnsRecord> T removeRecord(DnsSection dnsSection, int i) {
        return (T) b(a(dnsSection), i);
    }

    private <T extends DnsRecord> T b(int i, int i2) {
        Object d = d(i);
        if (d == null) {
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: none)");
        } else if (!(d instanceof DnsRecord)) {
            return (T) a(((List) d).remove(i2));
        } else {
            if (i2 == 0) {
                T t = (T) a(d);
                a(i, (Object) null);
                return t;
            }
            throw new IndexOutOfBoundsException("index: " + i2 + " (expected: 0)");
        }
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage clear(DnsSection dnsSection) {
        c(a(dnsSection));
        return this;
    }

    @Override // io.netty.handler.codec.dns.DnsMessage
    public DnsMessage clear() {
        for (int i = 0; i < 4; i++) {
            c(i);
        }
        return this;
    }

    private void c(int i) {
        Object d = d(i);
        a(i, (Object) null);
        if (d instanceof ReferenceCounted) {
            ((ReferenceCounted) d).release();
        } else if (d instanceof List) {
            List<Object> list = (List) d;
            if (!list.isEmpty()) {
                for (Object obj : list) {
                    ReferenceCountUtil.release(obj);
                }
            }
        }
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsMessage touch() {
        return (DnsMessage) super.touch();
    }

    @Override // io.netty.util.ReferenceCounted
    public DnsMessage touch(Object obj) {
        ResourceLeak resourceLeak = this.c;
        if (resourceLeak != null) {
            resourceLeak.record(obj);
        }
        return this;
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsMessage retain() {
        return (DnsMessage) super.retain();
    }

    @Override // io.netty.util.AbstractReferenceCounted, io.netty.util.ReferenceCounted
    public DnsMessage retain(int i) {
        return (DnsMessage) super.retain(i);
    }

    @Override // io.netty.util.AbstractReferenceCounted
    protected void deallocate() {
        clear();
        ResourceLeak resourceLeak = this.c;
        if (resourceLeak != null) {
            resourceLeak.close();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DnsMessage)) {
            return false;
        }
        DnsMessage dnsMessage = (DnsMessage) obj;
        if (id() != dnsMessage.id()) {
            return false;
        }
        if (this instanceof DnsQuery) {
            if (!(dnsMessage instanceof DnsQuery)) {
                return false;
            }
        } else if (dnsMessage instanceof DnsQuery) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (id() * 31) + (this instanceof DnsQuery ? 0 : 1);
    }

    private Object d(int i) {
        switch (i) {
            case 0:
                return this.h;
            case 1:
                return this.i;
            case 2:
                return this.j;
            case 3:
                return this.k;
            default:
                throw new Error();
        }
    }

    private void a(int i, Object obj) {
        switch (i) {
            case 0:
                this.h = obj;
                return;
            case 1:
                this.i = obj;
                return;
            case 2:
                this.j = obj;
                return;
            case 3:
                this.k = obj;
                return;
            default:
                throw new Error();
        }
    }

    private static int a(DnsSection dnsSection) {
        return ((DnsSection) ObjectUtil.checkNotNull(dnsSection, "section")).ordinal();
    }

    private static DnsRecord c(int i, DnsRecord dnsRecord) {
        if (i != b || (ObjectUtil.checkNotNull(dnsRecord, "record") instanceof DnsQuestion)) {
            return dnsRecord;
        }
        throw new IllegalArgumentException("record: " + dnsRecord + " (expected: " + StringUtil.simpleClassName((Class<?>) DnsQuestion.class) + ')');
    }

    private static <T extends DnsRecord> T a(Object obj) {
        return (T) ((DnsRecord) obj);
    }

    private static ArrayList<DnsRecord> a() {
        return new ArrayList<>(2);
    }
}
