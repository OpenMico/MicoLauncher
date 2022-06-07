package com.xiaomi.push.service;

import android.content.Context;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class al {
    private static al a;
    private ConcurrentHashMap<String, HashMap<String, b>> b = new ConcurrentHashMap<>();
    private List<a> c = new ArrayList();

    /* loaded from: classes4.dex */
    public interface a {
        void a();
    }

    /* loaded from: classes4.dex */
    public static class b {
        public Context a;

        /* renamed from: a */
        public d f187a;

        /* renamed from: a */
        public String f188a;

        /* renamed from: a */
        public boolean f189a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        Messenger l;
        private XMPushService q;
        c j = c.unbind;
        private int o = 0;
        private List<a> p = new ArrayList();
        c k = null;
        private boolean r = false;
        private XMPushService.b s = new XMPushService.b(this);
        IBinder.DeathRecipient m = null;
        final C0188b n = new C0188b();

        /* loaded from: classes4.dex */
        public interface a {
            void a(c cVar, c cVar2, int i);
        }

        /* renamed from: com.xiaomi.push.service.al$b$b */
        /* loaded from: classes4.dex */
        public class C0188b extends XMPushService.i {
            int b;
            int c;
            String d;
            String e;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C0188b() {
                super(0);
                b.this = r1;
            }

            public XMPushService.i a(int i, int i2, String str, String str2) {
                this.b = i;
                this.c = i2;
                this.e = str2;
                this.d = str;
                return this;
            }

            @Override // com.xiaomi.push.service.XMPushService.i
            /* renamed from: a */
            public String mo1167a() {
                return "notify job";
            }

            @Override // com.xiaomi.push.service.XMPushService.i
            /* renamed from: a */
            public void mo1167a() {
                if (b.this.a(this.b, this.c, this.e)) {
                    b.this.a(this.b, this.c, this.d, this.e);
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.b(" ignore notify client :" + b.this.g);
            }
        }

        /* loaded from: classes4.dex */
        public class c implements IBinder.DeathRecipient {
            final b a;
            final Messenger b;

            c(b bVar, Messenger messenger) {
                b.this = r1;
                this.a = bVar;
                this.b = messenger;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.xiaomi.channel.commonutils.logger.b.b("peer died, chid = " + this.a.g);
                b.this.q.a(new r(this, 0), 0L);
                if (Commands.ResolutionValues.BITSTREAM_4K.equals(this.a.g) && "com.xiaomi.xmsf".equals(b.this.q.getPackageName())) {
                    b.this.q.a(new s(this, 0), 60000L);
                }
            }
        }

        public b() {
        }

        public b(XMPushService xMPushService) {
            this.q = xMPushService;
            a(new q(this));
        }

        public static String a(String str) {
            int lastIndexOf;
            return (!TextUtils.isEmpty(str) && (lastIndexOf = str.lastIndexOf("/")) != -1) ? str.substring(lastIndexOf + 1) : "";
        }

        public void a(int i, int i2, String str, String str2) {
            c cVar = this.j;
            this.k = cVar;
            if (i == 2) {
                this.f187a.a(this.a, this, i2);
            } else if (i == 3) {
                this.f187a.a(this.a, this, str2, str);
            } else if (i == 1) {
                boolean z = cVar == c.binded;
                if (!z && "wait".equals(str2)) {
                    this.o++;
                } else if (z) {
                    this.o = 0;
                    if (this.l != null) {
                        try {
                            this.l.send(Message.obtain(null, 16, this.q.b));
                        } catch (RemoteException unused) {
                        }
                    }
                }
                this.f187a.a(this.q, this, z, i2, str);
            }
        }

        public boolean a(int i, int i2, String str) {
            boolean z;
            StringBuilder sb;
            String str2;
            c cVar = this.k;
            if (cVar == null || !(z = this.r)) {
                return true;
            }
            if (cVar == this.j) {
                sb = new StringBuilder();
                str2 = " status recovered, don't notify client:";
            } else if (this.l == null || !z) {
                sb = new StringBuilder();
                str2 = "peer died, ignore notify ";
            } else {
                com.xiaomi.channel.commonutils.logger.b.b("Peer alive notify status to client:" + this.g);
                return true;
            }
            sb.append(str2);
            sb.append(this.g);
            com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
            return false;
        }

        private boolean b(int i, int i2, String str) {
            switch (i) {
                case 1:
                    return this.j != c.binded && this.q.c() && i2 != 21 && (i2 != 7 || !"wait".equals(str));
                case 2:
                    return this.q.c();
                case 3:
                    return !"wait".equals(str);
                default:
                    return false;
            }
        }

        public long a() {
            return (((long) ((Math.random() * 20.0d) - 10.0d)) + ((this.o + 1) * 15)) * 1000;
        }

        public String a(int i) {
            switch (i) {
                case 1:
                    return "OPEN";
                case 2:
                    return "CLOSE";
                case 3:
                    return "KICK";
                default:
                    return "unknown";
            }
        }

        public void a(Messenger messenger) {
            b();
            try {
                if (messenger != null) {
                    this.l = messenger;
                    this.r = true;
                    this.m = new c(this, messenger);
                    messenger.getBinder().linkToDeath(this.m, 0);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.b("peer linked with old sdk chid = " + this.g);
                }
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.b("peer linkToDeath err: " + e.getMessage());
                this.l = null;
                this.r = false;
            }
        }

        public void a(a aVar) {
            synchronized (this.p) {
                this.p.add(aVar);
            }
        }

        public void a(c cVar, int i, int i2, String str, String str2) {
            boolean z;
            synchronized (this.p) {
                for (a aVar : this.p) {
                    aVar.a(this.j, cVar, i2);
                }
            }
            c cVar2 = this.j;
            int i3 = 0;
            if (cVar2 != cVar) {
                com.xiaomi.channel.commonutils.logger.b.m149a(String.format("update the client %7$s status. %1$s->%2$s %3$s %4$s %5$s %6$s", cVar2, cVar, a(i), ap.a(i2), str, str2, this.g));
                this.j = cVar;
            }
            if (this.f187a == null) {
                com.xiaomi.channel.commonutils.logger.b.d("status changed while the client dispatcher is missing");
            } else if (cVar != c.binding) {
                i3 = 10100;
                if (this.k != null && (z = this.r)) {
                    if (this.l != null && z) {
                        i3 = 1000;
                    }
                }
                this.q.b(this.n);
                if (b(i, i2, str2)) {
                    a(i, i2, str, str2);
                } else {
                    this.q.a(this.n.a(i, i2, str, str2), i3);
                }
            }
        }

        void b() {
            try {
                Messenger messenger = this.l;
                if (!(messenger == null || this.m == null)) {
                    messenger.getBinder().unlinkToDeath(this.m, 0);
                }
            } catch (Exception unused) {
            }
            this.k = null;
        }

        public void b(a aVar) {
            synchronized (this.p) {
                this.p.remove(aVar);
            }
        }
    }

    /* loaded from: classes4.dex */
    public enum c {
        unbind,
        binding,
        binded
    }

    private al() {
    }

    public static synchronized al a() {
        al alVar;
        synchronized (al.class) {
            if (a == null) {
                a = new al();
            }
            alVar = a;
        }
        return alVar;
    }

    private String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf("@");
        return indexOf > 0 ? str.substring(0, indexOf) : str;
    }

    /* renamed from: a */
    public synchronized int m1135a() {
        return this.b.size();
    }

    public synchronized b a(String str, String str2) {
        HashMap<String, b> hashMap = this.b.get(str);
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(b(str2));
    }

    /* renamed from: a */
    public synchronized ArrayList<b> m1136a() {
        ArrayList<b> arrayList;
        arrayList = new ArrayList<>();
        for (HashMap<String, b> hashMap : this.b.values()) {
            arrayList.addAll(hashMap.values());
        }
        return arrayList;
    }

    public synchronized Collection<b> a(String str) {
        if (!this.b.containsKey(str)) {
            return new ArrayList();
        }
        return ((HashMap) this.b.get(str).clone()).values();
    }

    /* renamed from: a */
    public synchronized List<String> m1137a(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (HashMap<String, b> hashMap : this.b.values()) {
            for (b bVar : hashMap.values()) {
                if (str.equals(bVar.f188a)) {
                    arrayList.add(bVar.g);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public synchronized void m1138a() {
        Iterator<b> it = m1136a().iterator();
        while (it.hasNext()) {
            it.next().b();
        }
        this.b.clear();
    }

    public synchronized void a(Context context) {
        for (HashMap<String, b> hashMap : this.b.values()) {
            for (b bVar : hashMap.values()) {
                bVar.a(c.unbind, 1, 3, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(Context context, int i) {
        for (HashMap<String, b> hashMap : this.b.values()) {
            for (b bVar : hashMap.values()) {
                bVar.a(c.unbind, 2, i, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(a aVar) {
        this.c.add(aVar);
    }

    public synchronized void a(b bVar) {
        HashMap<String, b> hashMap = this.b.get(bVar.g);
        if (hashMap == null) {
            hashMap = new HashMap<>();
            this.b.put(bVar.g, hashMap);
        }
        hashMap.put(b(bVar.b), bVar);
        for (a aVar : this.c) {
            aVar.a();
        }
    }

    /* renamed from: a */
    public synchronized void m1139a(String str) {
        HashMap<String, b> hashMap = this.b.get(str);
        if (hashMap != null) {
            for (b bVar : hashMap.values()) {
                bVar.b();
            }
            hashMap.clear();
            this.b.remove(str);
        }
        for (a aVar : this.c) {
            aVar.a();
        }
    }

    /* renamed from: a */
    public synchronized void m1140a(String str, String str2) {
        HashMap<String, b> hashMap = this.b.get(str);
        if (hashMap != null) {
            b bVar = hashMap.get(b(str2));
            if (bVar != null) {
                bVar.b();
            }
            hashMap.remove(b(str2));
            if (hashMap.isEmpty()) {
                this.b.remove(str);
            }
        }
        for (a aVar : this.c) {
            aVar.a();
        }
    }

    public synchronized void b() {
        this.c.clear();
    }
}
