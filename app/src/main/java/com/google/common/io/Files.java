package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class Files {
    private static final TreeTraverser<File> a = new TreeTraverser<File>() { // from class: com.google.common.io.Files.2
        public String toString() {
            return "Files.fileTreeTraverser()";
        }

        /* renamed from: a */
        public Iterable<File> children(File file) {
            return Files.b(file);
        }
    };
    private static final SuccessorsFunction<File> b = new SuccessorsFunction<File>() { // from class: com.google.common.io.Files.3
        /* renamed from: a */
        public Iterable<File> successors(File file) {
            return Files.b(file);
        }
    };

    /* loaded from: classes2.dex */
    private enum c implements Predicate<File> {
        IS_DIRECTORY {
            @Override // java.lang.Enum
            public String toString() {
                return "Files.isDirectory()";
            }

            /* renamed from: a */
            public boolean apply(File file) {
                return file.isDirectory();
            }
        },
        IS_FILE {
            @Override // java.lang.Enum
            public String toString() {
                return "Files.isFile()";
            }

            /* renamed from: a */
            public boolean apply(File file) {
                return file.isFile();
            }
        }
    }

    private Files() {
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    public static ByteSource asByteSource(File file) {
        return new b(file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends ByteSource {
        private final File a;

        private b(File file) {
            this.a = (File) Preconditions.checkNotNull(file);
        }

        /* renamed from: a */
        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.a);
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            if (this.a.isFile()) {
                return Optional.of(Long.valueOf(this.a.length()));
            }
            return Optional.absent();
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            if (this.a.isFile()) {
                return this.a.length();
            }
            throw new FileNotFoundException(this.a.toString());
        }

        @Override // com.google.common.io.ByteSource
        public byte[] read() throws IOException {
            Closer create = Closer.create();
            try {
                FileInputStream fileInputStream = (FileInputStream) create.register(openStream());
                return ByteStreams.a(fileInputStream, fileInputStream.getChannel().size());
            } finally {
                create.close();
            }
        }

        public String toString() {
            return "Files.asByteSource(" + this.a + ")";
        }
    }

    public static ByteSink asByteSink(File file, FileWriteMode... fileWriteModeArr) {
        return new a(file, fileWriteModeArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a extends ByteSink {
        private final File a;
        private final ImmutableSet<FileWriteMode> b;

        private a(File file, FileWriteMode... fileWriteModeArr) {
            this.a = (File) Preconditions.checkNotNull(file);
            this.b = ImmutableSet.copyOf(fileWriteModeArr);
        }

        /* renamed from: a */
        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.a, this.b.contains(FileWriteMode.APPEND));
        }

        public String toString() {
            return "Files.asByteSink(" + this.a + ", " + this.b + ")";
        }
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode... fileWriteModeArr) {
        return asByteSink(file, fileWriteModeArr).asCharSink(charset);
    }

    public static byte[] toByteArray(File file) throws IOException {
        return asByteSource(file).read();
    }

    @Deprecated
    public static String toString(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).read();
    }

    public static void write(byte[] bArr, File file) throws IOException {
        asByteSink(file, new FileWriteMode[0]).write(bArr);
    }

    @Deprecated
    public static void write(CharSequence charSequence, File file, Charset charset) throws IOException {
        asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    public static void copy(File file, OutputStream outputStream) throws IOException {
        asByteSource(file).copyTo(outputStream);
    }

    public static void copy(File file, File file2) throws IOException {
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        asByteSource(file).copyTo(asByteSink(file2, new FileWriteMode[0]));
    }

    @Deprecated
    public static void copy(File file, Charset charset, Appendable appendable) throws IOException {
        asCharSource(file, charset).copyTo(appendable);
    }

    @Deprecated
    public static void append(CharSequence charSequence, File file, Charset charset) throws IOException {
        asCharSink(file, charset, FileWriteMode.APPEND).write(charSequence);
    }

    public static boolean equal(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return true;
        }
        long length = file.length();
        long length2 = file2.length();
        if (length == 0 || length2 == 0 || length == length2) {
            return asByteSource(file).contentEquals(asByteSource(file2));
        }
        return false;
    }

    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        String str = System.currentTimeMillis() + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        for (int i = 0; i < 10000; i++) {
            File file2 = new File(file, str + i);
            if (file2.mkdir()) {
                return file2;
            }
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + str + "0 to " + str + com.xiaomi.passport.ui.internal.Constants.RESULT_RESTART_BINDING_EMAIL + ')');
    }

    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to update modification time of " + file);
        }
    }

    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        File parentFile = file.getCanonicalFile().getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
            if (!parentFile.isDirectory()) {
                throw new IOException("Unable to create parent directories of " + file);
            }
        }
    }

    public static void move(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        if (!file.renameTo(file2)) {
            copy(file, file2);
            if (file.delete()) {
                return;
            }
            if (!file2.delete()) {
                throw new IOException("Unable to delete " + file2);
            }
            throw new IOException("Unable to delete " + file);
        }
    }

    @Deprecated
    public static String readFirstLine(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).readFirstLine();
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        return (List) asCharSource(file, charset).readLines(new LineProcessor<List<String>>() { // from class: com.google.common.io.Files.1
            final List<String> a = Lists.newArrayList();

            @Override // com.google.common.io.LineProcessor
            public boolean processLine(String str) {
                this.a.add(str);
                return true;
            }

            /* renamed from: a */
            public List<String> getResult() {
                return this.a;
            }
        });
    }

    @CanIgnoreReturnValue
    @Deprecated
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return (T) asCharSource(file, charset).readLines(lineProcessor);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) throws IOException {
        return (T) asByteSource(file).read(byteProcessor);
    }

    @Deprecated
    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return asByteSource(file).hash(hashFunction);
    }

    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return map(file, FileChannel.MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        if (file.exists()) {
            return map(file, mapMode, file.length());
        }
        throw new FileNotFoundException(file.toString());
    }

    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode, long j) throws FileNotFoundException, IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        Closer create = Closer.create();
        try {
            return a((RandomAccessFile) create.register(new RandomAccessFile(file, mapMode == FileChannel.MapMode.READ_ONLY ? "r" : "rw")), mapMode, j);
        } finally {
            create.close();
        }
    }

    private static MappedByteBuffer a(RandomAccessFile randomAccessFile, FileChannel.MapMode mapMode, long j) throws IOException {
        Closer create = Closer.create();
        try {
            return ((FileChannel) create.register(randomAccessFile.getChannel())).map(mapMode, 0L, j);
        } finally {
            create.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004f, code lost:
        if (r3.equals(".") != false) goto L_0x0053;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0056 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x005a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0023 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String simplifyPath(java.lang.String r9) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r9)
            int r0 = r9.length()
            if (r0 != 0) goto L_0x000c
            java.lang.String r9 = "."
            return r9
        L_0x000c:
            r0 = 47
            com.google.common.base.Splitter r1 = com.google.common.base.Splitter.on(r0)
            com.google.common.base.Splitter r1 = r1.omitEmptyStrings()
            java.lang.Iterable r1 = r1.split(r9)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x0023:
            boolean r3 = r1.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x0082
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            r5 = -1
            int r6 = r3.hashCode()
            r7 = 46
            r8 = 1
            if (r6 == r7) goto L_0x0049
            r4 = 1472(0x5c0, float:2.063E-42)
            if (r6 == r4) goto L_0x003f
            goto L_0x0052
        L_0x003f:
            java.lang.String r4 = ".."
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L_0x0052
            r4 = r8
            goto L_0x0053
        L_0x0049:
            java.lang.String r6 = "."
            boolean r6 = r3.equals(r6)
            if (r6 == 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r4 = r5
        L_0x0053:
            switch(r4) {
                case 0: goto L_0x0023;
                case 1: goto L_0x005a;
                default: goto L_0x0056;
            }
        L_0x0056:
            r2.add(r3)
            goto L_0x0023
        L_0x005a:
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x007c
            int r3 = r2.size()
            int r3 = r3 - r8
            java.lang.Object r3 = r2.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = ".."
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x007c
            int r3 = r2.size()
            int r3 = r3 - r8
            r2.remove(r3)
            goto L_0x0023
        L_0x007c:
            java.lang.String r3 = ".."
            r2.add(r3)
            goto L_0x0023
        L_0x0082:
            com.google.common.base.Joiner r1 = com.google.common.base.Joiner.on(r0)
            java.lang.String r1 = r1.join(r2)
            char r9 = r9.charAt(r4)
            if (r9 != r0) goto L_0x00a1
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r0 = "/"
            r9.append(r0)
            r9.append(r1)
            java.lang.String r1 = r9.toString()
        L_0x00a1:
            java.lang.String r9 = "/../"
            boolean r9 = r1.startsWith(r9)
            if (r9 == 0) goto L_0x00af
            r9 = 3
            java.lang.String r1 = r1.substring(r9)
            goto L_0x00a1
        L_0x00af:
            java.lang.String r9 = "/.."
            boolean r9 = r1.equals(r9)
            if (r9 == 0) goto L_0x00ba
            java.lang.String r1 = "/"
            goto L_0x00c4
        L_0x00ba:
            java.lang.String r9 = ""
            boolean r9 = r9.equals(r1)
            if (r9 == 0) goto L_0x00c4
            java.lang.String r1 = "."
        L_0x00c4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.Files.simplifyPath(java.lang.String):java.lang.String");
    }

    public static String getFileExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int lastIndexOf = name.lastIndexOf(46);
        return lastIndexOf == -1 ? "" : name.substring(lastIndexOf + 1);
    }

    public static String getNameWithoutExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int lastIndexOf = name.lastIndexOf(46);
        return lastIndexOf == -1 ? name : name.substring(0, lastIndexOf);
    }

    public static Traverser<File> fileTraverser() {
        return Traverser.forTree(b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Iterable<File> b(File file) {
        File[] listFiles;
        if (!file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(listFiles));
    }

    public static Predicate<File> isDirectory() {
        return c.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return c.IS_FILE;
    }
}
