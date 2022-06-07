package com.bumptech.glide.disklrucache;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class DiskLruCache implements Closeable {
    private final File b;
    private final File c;
    private final File d;
    private final File e;
    private final int f;
    private long g;
    private final int h;
    private Writer j;
    private int l;
    private long i = 0;
    private final LinkedHashMap<String, b> k = new LinkedHashMap<>(0, 0.75f, true);
    private long m = 0;
    final ThreadPoolExecutor a = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new a());
    private final Callable<Void> n = new Callable<Void>() { // from class: com.bumptech.glide.disklrucache.DiskLruCache.1
        /* renamed from: a */
        public Void call() throws Exception {
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.j == null) {
                    return null;
                }
                DiskLruCache.this.f();
                if (DiskLruCache.this.d()) {
                    DiskLruCache.this.c();
                    DiskLruCache.this.l = 0;
                }
                return null;
            }
        }
    };

    private DiskLruCache(File file, int i, int i2, long j) {
        this.b = file;
        this.f = i;
        this.c = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE);
        this.d = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE_TEMP);
        this.e = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE_BACKUP);
        this.h = i2;
        this.g = j;
    }

    public static DiskLruCache open(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 > 0) {
            File file2 = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE_BACKUP);
            if (file2.exists()) {
                File file3 = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE);
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            DiskLruCache diskLruCache = new DiskLruCache(file, i, i2, j);
            if (diskLruCache.c.exists()) {
                try {
                    diskLruCache.a();
                    diskLruCache.b();
                    return diskLruCache;
                } catch (IOException e) {
                    PrintStream printStream = System.out;
                    printStream.println("DiskLruCache " + file + " is corrupt: " + e.getMessage() + ", removing");
                    diskLruCache.delete();
                }
            }
            file.mkdirs();
            DiskLruCache diskLruCache2 = new DiskLruCache(file, i, i2, j);
            diskLruCache2.c();
            return diskLruCache2;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void a() throws IOException {
        a aVar = new a(new FileInputStream(this.c), b.a);
        try {
            String a2 = aVar.a();
            String a3 = aVar.a();
            String a4 = aVar.a();
            String a5 = aVar.a();
            String a6 = aVar.a();
            if (!okhttp3.internal.cache.DiskLruCache.MAGIC.equals(a2) || !"1".equals(a3) || !Integer.toString(this.f).equals(a4) || !Integer.toString(this.h).equals(a5) || !"".equals(a6)) {
                throw new IOException("unexpected journal header: [" + a2 + ", " + a3 + ", " + a5 + ", " + a6 + "]");
            }
            int i = 0;
            while (true) {
                try {
                    a(aVar.a());
                    i++;
                } catch (EOFException unused) {
                    this.l = i - this.k.size();
                    if (aVar.b()) {
                        c();
                    } else {
                        this.j = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.c, true), b.a));
                    }
                    b.a(aVar);
                    return;
                }
            }
        } catch (Throwable th) {
            b.a(aVar);
            throw th;
        }
    }

    private void a(String str) throws IOException {
        String str2;
        int indexOf = str.indexOf(32);
        if (indexOf != -1) {
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(32, i);
            if (indexOf2 == -1) {
                str2 = str.substring(i);
                if (indexOf == 6 && str.startsWith(okhttp3.internal.cache.DiskLruCache.REMOVE)) {
                    this.k.remove(str2);
                    return;
                }
            } else {
                str2 = str.substring(i, indexOf2);
            }
            b bVar = this.k.get(str2);
            if (bVar == null) {
                bVar = new b(str2);
                this.k.put(str2, bVar);
            }
            if (indexOf2 != -1 && indexOf == 5 && str.startsWith(okhttp3.internal.cache.DiskLruCache.CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(StringUtils.SPACE);
                bVar.f = true;
                bVar.g = null;
                bVar.a(split);
            } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(okhttp3.internal.cache.DiskLruCache.DIRTY)) {
                bVar.g = new Editor(bVar);
            } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith(okhttp3.internal.cache.DiskLruCache.READ)) {
                throw new IOException("unexpected journal line: " + str);
            }
        } else {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void b() throws IOException {
        a(this.d);
        Iterator<b> it = this.k.values().iterator();
        while (it.hasNext()) {
            b next = it.next();
            int i = 0;
            if (next.g == null) {
                while (i < this.h) {
                    this.i += next.e[i];
                    i++;
                }
            } else {
                next.g = null;
                while (i < this.h) {
                    a(next.a(i));
                    a(next.b(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void c() throws IOException {
        if (this.j != null) {
            a(this.j);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d), b.a));
        bufferedWriter.write(okhttp3.internal.cache.DiskLruCache.MAGIC);
        bufferedWriter.write("\n");
        bufferedWriter.write("1");
        bufferedWriter.write("\n");
        bufferedWriter.write(Integer.toString(this.f));
        bufferedWriter.write("\n");
        bufferedWriter.write(Integer.toString(this.h));
        bufferedWriter.write("\n");
        bufferedWriter.write("\n");
        for (b bVar : this.k.values()) {
            if (bVar.g != null) {
                bufferedWriter.write("DIRTY " + bVar.d + '\n');
            } else {
                bufferedWriter.write("CLEAN " + bVar.d + bVar.a() + '\n');
            }
        }
        a(bufferedWriter);
        if (this.c.exists()) {
            a(this.c, this.e, true);
        }
        a(this.d, this.c, false);
        this.e.delete();
        this.j = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.c, true), b.a));
    }

    private static void a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public synchronized Value get(String str) throws IOException {
        e();
        b bVar = this.k.get(str);
        if (bVar == null) {
            return null;
        }
        if (!bVar.f) {
            return null;
        }
        for (File file : bVar.a) {
            if (!file.exists()) {
                return null;
            }
        }
        this.l++;
        this.j.append((CharSequence) okhttp3.internal.cache.DiskLruCache.READ);
        this.j.append(' ');
        this.j.append((CharSequence) str);
        this.j.append('\n');
        if (d()) {
            this.a.submit(this.n);
        }
        return new Value(str, bVar.h, bVar.a, bVar.e);
    }

    public Editor edit(String str) throws IOException {
        return a(str, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Editor a(String str, long j) throws IOException {
        e();
        b bVar = this.k.get(str);
        if (j != -1 && (bVar == null || bVar.h != j)) {
            return null;
        }
        if (bVar == null) {
            bVar = new b(str);
            this.k.put(str, bVar);
        } else if (bVar.g != null) {
            return null;
        }
        Editor editor = new Editor(bVar);
        bVar.g = editor;
        this.j.append((CharSequence) okhttp3.internal.cache.DiskLruCache.DIRTY);
        this.j.append(' ');
        this.j.append((CharSequence) str);
        this.j.append('\n');
        b(this.j);
        return editor;
    }

    public File getDirectory() {
        return this.b;
    }

    public synchronized long getMaxSize() {
        return this.g;
    }

    public synchronized void setMaxSize(long j) {
        this.g = j;
        this.a.submit(this.n);
    }

    public synchronized long size() {
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(Editor editor, boolean z) throws IOException {
        b bVar = editor.b;
        if (bVar.g == editor) {
            if (z && !bVar.f) {
                for (int i = 0; i < this.h; i++) {
                    if (!editor.c[i]) {
                        editor.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    } else if (!bVar.b(i).exists()) {
                        editor.abort();
                        return;
                    }
                }
            }
            for (int i2 = 0; i2 < this.h; i2++) {
                File b2 = bVar.b(i2);
                if (!z) {
                    a(b2);
                } else if (b2.exists()) {
                    File a2 = bVar.a(i2);
                    b2.renameTo(a2);
                    long j = bVar.e[i2];
                    long length = a2.length();
                    bVar.e[i2] = length;
                    this.i = (this.i - j) + length;
                }
            }
            this.l++;
            bVar.g = null;
            if (bVar.f || z) {
                bVar.f = true;
                this.j.append((CharSequence) okhttp3.internal.cache.DiskLruCache.CLEAN);
                this.j.append(' ');
                this.j.append((CharSequence) bVar.d);
                this.j.append((CharSequence) bVar.a());
                this.j.append('\n');
                if (z) {
                    long j2 = this.m;
                    this.m = 1 + j2;
                    bVar.h = j2;
                }
            } else {
                this.k.remove(bVar.d);
                this.j.append((CharSequence) okhttp3.internal.cache.DiskLruCache.REMOVE);
                this.j.append(' ');
                this.j.append((CharSequence) bVar.d);
                this.j.append('\n');
            }
            b(this.j);
            if (this.i > this.g || d()) {
                this.a.submit(this.n);
            }
            return;
        }
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        int i = this.l;
        return i >= 2000 && i >= this.k.size();
    }

    public synchronized boolean remove(String str) throws IOException {
        e();
        b bVar = this.k.get(str);
        if (bVar != null && bVar.g == null) {
            for (int i = 0; i < this.h; i++) {
                File a2 = bVar.a(i);
                if (a2.exists() && !a2.delete()) {
                    throw new IOException("failed to delete " + a2);
                }
                this.i -= bVar.e[i];
                bVar.e[i] = 0;
            }
            this.l++;
            this.j.append((CharSequence) okhttp3.internal.cache.DiskLruCache.REMOVE);
            this.j.append(' ');
            this.j.append((CharSequence) str);
            this.j.append('\n');
            this.k.remove(str);
            if (d()) {
                this.a.submit(this.n);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean isClosed() {
        return this.j == null;
    }

    private void e() {
        if (this.j == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        e();
        f();
        b(this.j);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        if (this.j != null) {
            Iterator it = new ArrayList(this.k.values()).iterator();
            while (it.hasNext()) {
                b bVar = (b) it.next();
                if (bVar.g != null) {
                    bVar.g.abort();
                }
            }
            f();
            a(this.j);
            this.j = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() throws IOException {
        while (this.i > this.g) {
            remove(this.k.entrySet().iterator().next().getKey());
        }
    }

    public void delete() throws IOException {
        close();
        b.a(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(InputStream inputStream) throws IOException {
        return b.a((Reader) new InputStreamReader(inputStream, b.b));
    }

    @TargetApi(26)
    private static void a(Writer writer) throws IOException {
        if (Build.VERSION.SDK_INT < 26) {
            writer.close();
            return;
        }
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitUnbufferedIo().build());
        try {
            writer.close();
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    @TargetApi(26)
    private static void b(Writer writer) throws IOException {
        if (Build.VERSION.SDK_INT < 26) {
            writer.flush();
            return;
        }
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(threadPolicy).permitUnbufferedIo().build());
        try {
            writer.flush();
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    /* loaded from: classes.dex */
    public final class Value {
        private final String b;
        private final long c;
        private final long[] d;
        private final File[] e;

        private Value(String str, long j, File[] fileArr, long[] jArr) {
            this.b = str;
            this.c = j;
            this.e = fileArr;
            this.d = jArr;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.a(this.b, this.c);
        }

        public File getFile(int i) {
            return this.e[i];
        }

        public String getString(int i) throws IOException {
            return DiskLruCache.b(new FileInputStream(this.e[i]));
        }

        public long getLength(int i) {
            return this.d[i];
        }
    }

    /* loaded from: classes.dex */
    public final class Editor {
        private final b b;
        private final boolean[] c;
        private boolean d;

        private Editor(b bVar) {
            this.b = bVar;
            this.c = bVar.f ? null : new boolean[DiskLruCache.this.h];
        }

        private InputStream a(int i) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.b.g != this) {
                    throw new IllegalStateException();
                } else if (!this.b.f) {
                    return null;
                } else {
                    try {
                        return new FileInputStream(this.b.a(i));
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }
            }
        }

        public String getString(int i) throws IOException {
            InputStream a = a(i);
            if (a != null) {
                return DiskLruCache.b(a);
            }
            return null;
        }

        public File getFile(int i) throws IOException {
            File b;
            synchronized (DiskLruCache.this) {
                if (this.b.g == this) {
                    if (!this.b.f) {
                        this.c[i] = true;
                    }
                    b = this.b.b(i);
                    DiskLruCache.this.b.mkdirs();
                } else {
                    throw new IllegalStateException();
                }
            }
            return b;
        }

        public void set(int i, String str) throws IOException {
            Throwable th;
            OutputStreamWriter outputStreamWriter = null;
            try {
                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(getFile(i)), b.b);
                try {
                    outputStreamWriter2.write(str);
                    b.a(outputStreamWriter2);
                } catch (Throwable th2) {
                    th = th2;
                    outputStreamWriter = outputStreamWriter2;
                    b.a(outputStreamWriter);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }

        public void commit() throws IOException {
            DiskLruCache.this.a(this, true);
            this.d = true;
        }

        public void abort() throws IOException {
            DiskLruCache.this.a(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.d) {
                try {
                    abort();
                } catch (IOException unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class b {
        File[] a;
        File[] b;
        private final String d;
        private final long[] e;
        private boolean f;
        private Editor g;
        private long h;

        private b(String str) {
            this.d = str;
            this.e = new long[DiskLruCache.this.h];
            this.a = new File[DiskLruCache.this.h];
            this.b = new File[DiskLruCache.this.h];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i = 0; i < DiskLruCache.this.h; i++) {
                sb.append(i);
                this.a[i] = new File(DiskLruCache.this.b, sb.toString());
                sb.append(DiskFileUpload.postfix);
                this.b[i] = new File(DiskLruCache.this.b, sb.toString());
                sb.setLength(length);
            }
        }

        public String a() throws IOException {
            StringBuilder sb = new StringBuilder();
            long[] jArr = this.e;
            for (long j : jArr) {
                sb.append(' ');
                sb.append(j);
            }
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String[] strArr) throws IOException {
            if (strArr.length == DiskLruCache.this.h) {
                for (int i = 0; i < strArr.length; i++) {
                    try {
                        this.e[i] = Long.parseLong(strArr[i]);
                    } catch (NumberFormatException unused) {
                        throw b(strArr);
                    }
                }
                return;
            }
            throw b(strArr);
        }

        private IOException b(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File a(int i) {
            return this.a[i];
        }

        public File b(int i) {
            return this.b[i];
        }
    }

    /* loaded from: classes.dex */
    private static final class a implements ThreadFactory {
        private a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public synchronized Thread newThread(Runnable runnable) {
            Thread thread;
            thread = new Thread(runnable, "glide-disk-lru-cache-thread");
            thread.setPriority(1);
            return thread;
        }
    }
}
