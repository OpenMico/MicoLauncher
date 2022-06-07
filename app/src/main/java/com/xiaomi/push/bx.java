package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.push.service.ag;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class bx {
    private static volatile bx a;
    private Context b;
    private bw c;
    private final HashMap<String, bv> d = new HashMap<>();
    private ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue(), new m("dm"));
    private final ArrayList<a> f = new ArrayList<>();

    /* loaded from: classes4.dex */
    public static abstract class a implements Runnable {
        protected String b;
        private String c;
        private WeakReference<Context> d;
        private a g;
        protected bv a = null;
        private Random e = new Random();
        private int f = 0;

        public a(String str) {
            this.c = str;
        }

        public SQLiteDatabase a() {
            return this.a.getWritableDatabase();
        }

        /* renamed from: a */
        public Object m783a() {
            return null;
        }

        /* renamed from: a */
        public String m784a() {
            return this.c;
        }

        public void a(Context context) {
            a aVar = this.g;
            if (aVar != null) {
                aVar.a(context, m783a());
            }
            b(context);
        }

        public abstract void a(Context context, SQLiteDatabase sQLiteDatabase);

        public void a(Context context, Object obj) {
            bx.a(context).a(this);
        }

        void a(bv bvVar, Context context) {
            this.a = bvVar;
            this.b = this.a.a();
            this.d = new WeakReference<>(context);
        }

        public void a(a aVar) {
            this.g = aVar;
        }

        /* renamed from: a */
        public boolean m785a() {
            return this.a == null || TextUtils.isEmpty(this.b) || this.d == null;
        }

        public void b(Context context) {
        }

        @Override // java.lang.Runnable
        public final void run() {
            Context context;
            WeakReference<Context> weakReference = this.d;
            if (weakReference != null && (context = weakReference.get()) != null && context.getFilesDir() != null && this.a != null && !TextUtils.isEmpty(this.c)) {
                File file = new File(this.c);
                w.a(context, new File(file.getParentFile(), ay.b(file.getAbsolutePath())), new bm(this, context));
            }
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class b<T> extends a {
        private List<String> c;
        private String d;
        private String[] e;
        private String f;
        private String g;
        private String h;
        private int i;
        private List<T> j = new ArrayList();

        public b(String str, List<String> list, String str2, String[] strArr, String str3, String str4, String str5, int i) {
            super(str);
            this.c = list;
            this.d = str2;
            this.e = strArr;
            this.f = str3;
            this.g = str4;
            this.h = str5;
            this.i = i;
        }

        @Override // com.xiaomi.push.bx.a
        public SQLiteDatabase a() {
            return this.a.getReadableDatabase();
        }

        public abstract T a(Context context, Cursor cursor);

        @Override // com.xiaomi.push.bx.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            String[] strArr;
            this.j.clear();
            List<String> list = this.c;
            String str = null;
            if (list == null || list.size() <= 0) {
                strArr = null;
            } else {
                String[] strArr2 = new String[this.c.size()];
                this.c.toArray(strArr2);
                strArr = strArr2;
            }
            int i = this.i;
            if (i > 0) {
                str = String.valueOf(i);
            }
            Cursor query = sQLiteDatabase.query(this.b, strArr, this.d, this.e, this.f, this.g, this.h, str);
            if (query != null && query.moveToFirst()) {
                do {
                    T a = a(context, query);
                    if (a != null) {
                        this.j.add(a);
                    }
                } while (query.moveToNext());
                query.close();
            }
            a(context, (List) this.j);
        }

        public abstract void a(Context context, List<T> list);
    }

    /* loaded from: classes4.dex */
    public static class c extends a {
        private ArrayList<a> c = new ArrayList<>();

        public c(String str, ArrayList<a> arrayList) {
            super(str);
            this.c.addAll(arrayList);
        }

        @Override // com.xiaomi.push.bx.a
        public final void a(Context context) {
            super.a(context);
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next != null) {
                    next.a(context);
                }
            }
        }

        @Override // com.xiaomi.push.bx.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            Iterator<a> it = this.c.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next != null) {
                    next.a(context, sQLiteDatabase);
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public static class d extends a {
        protected String[] a;
        private String c;

        public d(String str, String str2, String[] strArr) {
            super(str);
            this.c = str2;
            this.a = strArr;
        }

        @Override // com.xiaomi.push.bx.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.delete(this.b, this.c, this.a);
        }
    }

    /* loaded from: classes4.dex */
    public static class e extends a {
        private ContentValues c;

        public e(String str, ContentValues contentValues) {
            super(str);
            this.c = contentValues;
        }

        @Override // com.xiaomi.push.bx.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.insert(this.b, null, this.c);
        }
    }

    private bx(Context context) {
        this.b = context;
    }

    public static bx a(Context context) {
        if (a == null) {
            synchronized (bx.class) {
                if (a == null) {
                    a = new bx(context);
                }
            }
        }
        return a;
    }

    private void a() {
        aj.a(this.b).b(new bk(this), ag.a(this.b).a(hm.StatDataProcessFrequency.a(), 5));
    }

    private bv b(String str) {
        bv bvVar = this.d.get(str);
        if (bvVar == null) {
            synchronized (this.d) {
                if (bvVar == null) {
                    bvVar = this.c.a(this.b, str);
                    this.d.put(str, bvVar);
                }
            }
        }
        return bvVar;
    }

    public String a(String str) {
        return b(str).a();
    }

    public void a(a aVar) {
        bv bvVar;
        if (aVar != null) {
            if (this.c != null) {
                String a2 = aVar.m784a();
                synchronized (this.d) {
                    bvVar = this.d.get(a2);
                    if (bvVar == null) {
                        bvVar = this.c.a(this.b, a2);
                        this.d.put(a2, bvVar);
                    }
                }
                if (!this.e.isShutdown()) {
                    aVar.a(bvVar, this.b);
                    synchronized (this.f) {
                        this.f.add(aVar);
                        a();
                    }
                    return;
                }
                return;
            }
            throw new IllegalStateException("should exec init method first!");
        }
    }

    public void a(Runnable runnable) {
        if (!this.e.isShutdown()) {
            this.e.execute(runnable);
        }
    }

    public void a(ArrayList<a> arrayList) {
        if (this.c != null) {
            HashMap hashMap = new HashMap();
            if (!this.e.isShutdown()) {
                Iterator<a> it = arrayList.iterator();
                while (it.hasNext()) {
                    a next = it.next();
                    if (next.m785a()) {
                        next.a(b(next.m784a()), this.b);
                    }
                    ArrayList arrayList2 = (ArrayList) hashMap.get(next.m784a());
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                        hashMap.put(next.m784a(), arrayList2);
                    }
                    arrayList2.add(next);
                }
                for (String str : hashMap.keySet()) {
                    ArrayList arrayList3 = (ArrayList) hashMap.get(str);
                    if (arrayList3 != null && arrayList3.size() > 0) {
                        c cVar = new c(str, arrayList3);
                        cVar.a(((a) arrayList3.get(0)).a, this.b);
                        this.e.execute(cVar);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("should exec setDbHelperFactory method first!");
    }

    public void b(a aVar) {
        bv bvVar;
        if (aVar != null) {
            if (this.c != null) {
                String a2 = aVar.m784a();
                synchronized (this.d) {
                    bvVar = this.d.get(a2);
                    if (bvVar == null) {
                        bvVar = this.c.a(this.b, a2);
                        this.d.put(a2, bvVar);
                    }
                }
                if (!this.e.isShutdown()) {
                    aVar.a(bvVar, this.b);
                    a((Runnable) aVar);
                    return;
                }
                return;
            }
            throw new IllegalStateException("should exec init method first!");
        }
    }
}
