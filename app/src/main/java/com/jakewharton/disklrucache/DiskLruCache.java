package com.jakewharton.disklrucache;

import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class DiskLruCache implements Closeable {
    static final Pattern a = Pattern.compile("[a-z0-9_-]{1,64}");
    private static final OutputStream p = new OutputStream() { // from class: com.jakewharton.disklrucache.DiskLruCache.2
        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
        }
    };
    private final File c;
    private final File d;
    private final File e;
    private final File f;
    private final int g;
    private long h;
    private final int i;
    private Writer k;
    private int m;
    private long j = 0;
    private final LinkedHashMap<String, a> l = new LinkedHashMap<>(0, 0.75f, true);
    private long n = 0;
    final ThreadPoolExecutor b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final Callable<Void> o = new Callable<Void>() { // from class: com.jakewharton.disklrucache.DiskLruCache.1
        /* renamed from: a */
        public Void call() throws Exception {
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.k == null) {
                    return null;
                }
                DiskLruCache.this.g();
                if (DiskLruCache.this.e()) {
                    DiskLruCache.this.d();
                    DiskLruCache.this.m = 0;
                }
                return null;
            }
        }
    };

    private DiskLruCache(File file, int i, int i2, long j) {
        this.c = file;
        this.g = i;
        this.d = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE);
        this.e = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE_TEMP);
        this.f = new File(file, okhttp3.internal.cache.DiskLruCache.JOURNAL_FILE_BACKUP);
        this.i = i2;
        this.h = j;
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
            if (diskLruCache.d.exists()) {
                try {
                    diskLruCache.b();
                    diskLruCache.c();
                    diskLruCache.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(diskLruCache.d, true), Util.a));
                    return diskLruCache;
                } catch (IOException e) {
                    PrintStream printStream = System.out;
                    printStream.println("DiskLruCache " + file + " is corrupt: " + e.getMessage() + ", removing");
                    diskLruCache.delete();
                }
            }
            file.mkdirs();
            DiskLruCache diskLruCache2 = new DiskLruCache(file, i, i2, j);
            diskLruCache2.d();
            return diskLruCache2;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void b() throws IOException {
        a aVar = new a(new FileInputStream(this.d), Util.a);
        try {
            String a2 = aVar.a();
            String a3 = aVar.a();
            String a4 = aVar.a();
            String a5 = aVar.a();
            String a6 = aVar.a();
            if (!okhttp3.internal.cache.DiskLruCache.MAGIC.equals(a2) || !"1".equals(a3) || !Integer.toString(this.g).equals(a4) || !Integer.toString(this.i).equals(a5) || !"".equals(a6)) {
                throw new IOException("unexpected journal header: [" + a2 + ", " + a3 + ", " + a5 + ", " + a6 + "]");
            }
            int i = 0;
            while (true) {
                try {
                    a(aVar.a());
                    i++;
                } catch (EOFException unused) {
                    this.m = i - this.l.size();
                    Util.a(aVar);
                    return;
                }
            }
        } catch (Throwable th) {
            Util.a(aVar);
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
                    this.l.remove(str2);
                    return;
                }
            } else {
                str2 = str.substring(i, indexOf2);
            }
            a aVar = this.l.get(str2);
            if (aVar == null) {
                aVar = new a(str2);
                this.l.put(str2, aVar);
            }
            if (indexOf2 != -1 && indexOf == 5 && str.startsWith(okhttp3.internal.cache.DiskLruCache.CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(StringUtils.SPACE);
                aVar.d = true;
                aVar.e = null;
                aVar.a(split);
            } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(okhttp3.internal.cache.DiskLruCache.DIRTY)) {
                aVar.e = new Editor(aVar);
            } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith(okhttp3.internal.cache.DiskLruCache.READ)) {
                throw new IOException("unexpected journal line: " + str);
            }
        } else {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void c() throws IOException {
        a(this.e);
        Iterator<a> it = this.l.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            int i = 0;
            if (next.e == null) {
                while (i < this.i) {
                    this.j += next.c[i];
                    i++;
                }
            } else {
                next.e = null;
                while (i < this.i) {
                    a(next.a(i));
                    a(next.b(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void d() throws IOException {
        if (this.k != null) {
            this.k.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.e), Util.a));
        bufferedWriter.write(okhttp3.internal.cache.DiskLruCache.MAGIC);
        bufferedWriter.write("\n");
        bufferedWriter.write("1");
        bufferedWriter.write("\n");
        bufferedWriter.write(Integer.toString(this.g));
        bufferedWriter.write("\n");
        bufferedWriter.write(Integer.toString(this.i));
        bufferedWriter.write("\n");
        bufferedWriter.write("\n");
        for (a aVar : this.l.values()) {
            if (aVar.e != null) {
                bufferedWriter.write("DIRTY " + aVar.b + '\n');
            } else {
                bufferedWriter.write("CLEAN " + aVar.b + aVar.a() + '\n');
            }
        }
        bufferedWriter.close();
        if (this.d.exists()) {
            a(this.d, this.f, true);
        }
        a(this.e, this.d, false);
        this.f.delete();
        this.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), Util.a));
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

    public synchronized Snapshot get(String str) throws IOException {
        f();
        b(str);
        a aVar = this.l.get(str);
        if (aVar == null) {
            return null;
        }
        if (!aVar.d) {
            return null;
        }
        InputStream[] inputStreamArr = new InputStream[this.i];
        for (int i = 0; i < this.i; i++) {
            try {
                inputStreamArr[i] = new FileInputStream(aVar.a(i));
            } catch (FileNotFoundException unused) {
                for (int i2 = 0; i2 < this.i && inputStreamArr[i2] != null; i2++) {
                    Util.a(inputStreamArr[i2]);
                }
                return null;
            }
        }
        this.m++;
        this.k.append((CharSequence) ("READ " + str + '\n'));
        if (e()) {
            this.b.submit(this.o);
        }
        return new Snapshot(str, aVar.f, inputStreamArr, aVar.c);
    }

    public Editor edit(String str) throws IOException {
        return a(str, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Editor a(String str, long j) throws IOException {
        f();
        b(str);
        a aVar = this.l.get(str);
        if (j != -1 && (aVar == null || aVar.f != j)) {
            return null;
        }
        if (aVar == null) {
            aVar = new a(str);
            this.l.put(str, aVar);
        } else if (aVar.e != null) {
            return null;
        }
        Editor editor = new Editor(aVar);
        aVar.e = editor;
        Writer writer = this.k;
        writer.write("DIRTY " + str + '\n');
        this.k.flush();
        return editor;
    }

    public File getDirectory() {
        return this.c;
    }

    public synchronized long getMaxSize() {
        return this.h;
    }

    public synchronized void setMaxSize(long j) {
        this.h = j;
        this.b.submit(this.o);
    }

    public synchronized long size() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(Editor editor, boolean z) throws IOException {
        a aVar = editor.b;
        if (aVar.e == editor) {
            if (z && !aVar.d) {
                for (int i = 0; i < this.i; i++) {
                    if (!editor.c[i]) {
                        editor.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    } else if (!aVar.b(i).exists()) {
                        editor.abort();
                        return;
                    }
                }
            }
            for (int i2 = 0; i2 < this.i; i2++) {
                File b = aVar.b(i2);
                if (!z) {
                    a(b);
                } else if (b.exists()) {
                    File a2 = aVar.a(i2);
                    b.renameTo(a2);
                    long j = aVar.c[i2];
                    long length = a2.length();
                    aVar.c[i2] = length;
                    this.j = (this.j - j) + length;
                }
            }
            this.m++;
            aVar.e = null;
            if (aVar.d || z) {
                aVar.d = true;
                this.k.write("CLEAN " + aVar.b + aVar.a() + '\n');
                if (z) {
                    long j2 = this.n;
                    this.n = 1 + j2;
                    aVar.f = j2;
                }
            } else {
                this.l.remove(aVar.b);
                this.k.write("REMOVE " + aVar.b + '\n');
            }
            this.k.flush();
            if (this.j > this.h || e()) {
                this.b.submit(this.o);
            }
            return;
        }
        throw new IllegalStateException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        int i = this.m;
        return i >= 2000 && i >= this.l.size();
    }

    public synchronized boolean remove(String str) throws IOException {
        f();
        b(str);
        a aVar = this.l.get(str);
        if (aVar != null && aVar.e == null) {
            for (int i = 0; i < this.i; i++) {
                File a2 = aVar.a(i);
                if (a2.exists() && !a2.delete()) {
                    throw new IOException("failed to delete " + a2);
                }
                this.j -= aVar.c[i];
                aVar.c[i] = 0;
            }
            this.m++;
            this.k.append((CharSequence) ("REMOVE " + str + '\n'));
            this.l.remove(str);
            if (e()) {
                this.b.submit(this.o);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean isClosed() {
        return this.k == null;
    }

    private void f() {
        if (this.k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        f();
        g();
        this.k.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        if (this.k != null) {
            Iterator it = new ArrayList(this.l.values()).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.e != null) {
                    aVar.e.abort();
                }
            }
            g();
            this.k.close();
            this.k = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() throws IOException {
        while (this.j > this.h) {
            remove(this.l.entrySet().iterator().next().getKey());
        }
    }

    public void delete() throws IOException {
        close();
        Util.a(this.c);
    }

    private void b(String str) {
        if (!a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + str + "\"");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(InputStream inputStream) throws IOException {
        return Util.a((Reader) new InputStreamReader(inputStream, Util.b));
    }

    /* loaded from: classes2.dex */
    public final class Snapshot implements Closeable {
        private final String b;
        private final long c;
        private final InputStream[] d;
        private final long[] e;

        private Snapshot(String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.b = str;
            this.c = j;
            this.d = inputStreamArr;
            this.e = jArr;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.a(this.b, this.c);
        }

        public InputStream getInputStream(int i) {
            return this.d[i];
        }

        public String getString(int i) throws IOException {
            return DiskLruCache.b(getInputStream(i));
        }

        public long getLength(int i) {
            return this.e[i];
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            for (InputStream inputStream : this.d) {
                Util.a(inputStream);
            }
        }
    }

    /* loaded from: classes2.dex */
    public final class Editor {
        private final a b;
        private final boolean[] c;
        private boolean d;
        private boolean e;

        private Editor(a aVar) {
            this.b = aVar;
            this.c = aVar.d ? null : new boolean[DiskLruCache.this.i];
        }

        public InputStream newInputStream(int i) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.b.e != this) {
                    throw new IllegalStateException();
                } else if (!this.b.d) {
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
            InputStream newInputStream = newInputStream(i);
            if (newInputStream != null) {
                return DiskLruCache.b(newInputStream);
            }
            return null;
        }

        public OutputStream newOutputStream(int i) throws IOException {
            FileOutputStream fileOutputStream;
            a aVar;
            synchronized (DiskLruCache.this) {
                if (this.b.e == this) {
                    if (!this.b.d) {
                        this.c[i] = true;
                    }
                    File b = this.b.b(i);
                    try {
                        fileOutputStream = new FileOutputStream(b);
                    } catch (FileNotFoundException unused) {
                        DiskLruCache.this.c.mkdirs();
                        try {
                            fileOutputStream = new FileOutputStream(b);
                        } catch (FileNotFoundException unused2) {
                            return DiskLruCache.p;
                        }
                    }
                    aVar = new a(fileOutputStream);
                } else {
                    throw new IllegalStateException();
                }
            }
            return aVar;
        }

        public void set(int i, String str) throws IOException {
            Throwable th;
            OutputStreamWriter outputStreamWriter = null;
            try {
                OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(newOutputStream(i), Util.b);
                try {
                    outputStreamWriter2.write(str);
                    Util.a(outputStreamWriter2);
                } catch (Throwable th2) {
                    th = th2;
                    outputStreamWriter = outputStreamWriter2;
                    Util.a(outputStreamWriter);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }

        public void commit() throws IOException {
            if (this.d) {
                DiskLruCache.this.a(this, false);
                DiskLruCache.this.remove(this.b.b);
            } else {
                DiskLruCache.this.a(this, true);
            }
            this.e = true;
        }

        public void abort() throws IOException {
            DiskLruCache.this.a(this, false);
        }

        public void abortUnlessCommitted() {
            if (!this.e) {
                try {
                    abort();
                } catch (IOException unused) {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public class a extends FilterOutputStream {
            private a(OutputStream outputStream) {
                super(outputStream);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException unused) {
                    Editor.this.d = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException unused) {
                    Editor.this.d = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                try {
                    this.out.close();
                } catch (IOException unused) {
                    Editor.this.d = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException unused) {
                    Editor.this.d = true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a {
        private final String b;
        private final long[] c;
        private boolean d;
        private Editor e;
        private long f;

        private a(String str) {
            this.b = str;
            this.c = new long[DiskLruCache.this.i];
        }

        public String a() throws IOException {
            StringBuilder sb = new StringBuilder();
            long[] jArr = this.c;
            for (long j : jArr) {
                sb.append(' ');
                sb.append(j);
            }
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String[] strArr) throws IOException {
            if (strArr.length == DiskLruCache.this.i) {
                for (int i = 0; i < strArr.length; i++) {
                    try {
                        this.c[i] = Long.parseLong(strArr[i]);
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
            File file = DiskLruCache.this.c;
            return new File(file, this.b + "." + i);
        }

        public File b(int i) {
            File file = DiskLruCache.this.c;
            return new File(file, this.b + "." + i + DiskFileUpload.postfix);
        }
    }
}
