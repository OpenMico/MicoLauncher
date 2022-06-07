package org.eclipse.jetty.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

/* loaded from: classes5.dex */
public class MultiPartInputStream {
    public static final MultipartConfigElement __DEFAULT_MULTIPART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
    protected MultipartConfigElement _config;
    protected String _contentType;
    protected File _contextTmpDir;
    protected boolean _deleteOnExit;
    protected InputStream _in;
    protected MultiMap<String> _parts;
    protected File _tmpDir;

    /* loaded from: classes5.dex */
    public class MultiPart implements Part {
        protected ByteArrayOutputStream2 _bout;
        protected String _contentType;
        protected File _file;
        protected String _filename;
        protected MultiMap<String> _headers;
        protected String _name;
        protected OutputStream _out;
        protected long _size = 0;
        protected boolean _temporary = true;

        public MultiPart(String str, String str2) throws IOException {
            this._name = str;
            this._filename = str2;
        }

        protected void setContentType(String str) {
            this._contentType = str;
        }

        protected void open() throws IOException {
            String str = this._filename;
            if (str == null || str.trim().length() <= 0) {
                ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
                this._bout = byteArrayOutputStream2;
                this._out = byteArrayOutputStream2;
                return;
            }
            createFile();
        }

        protected void close() throws IOException {
            this._out.close();
        }

        protected void write(int i) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() <= 0 || this._size + 1 <= MultiPartInputStream.this._config.getMaxFileSize()) {
                if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + 1 > MultiPartInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                    createFile();
                }
                this._out.write(i);
                this._size++;
                return;
            }
            throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
        }

        protected void write(byte[] bArr, int i, int i2) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() <= 0 || this._size + i2 <= MultiPartInputStream.this._config.getMaxFileSize()) {
                if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + i2 > MultiPartInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                    createFile();
                }
                this._out.write(bArr, i, i2);
                this._size += i2;
                return;
            }
            throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
        }

        protected void createFile() throws IOException {
            OutputStream outputStream;
            this._file = File.createTempFile("MultiPart", "", MultiPartInputStream.this._tmpDir);
            if (MultiPartInputStream.this._deleteOnExit) {
                this._file.deleteOnExit();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this._file));
            if (this._size > 0 && (outputStream = this._out) != null) {
                outputStream.flush();
                this._bout.writeTo(bufferedOutputStream);
                this._out.close();
                this._bout = null;
            }
            this._out = bufferedOutputStream;
        }

        protected void setHeaders(MultiMap<String> multiMap) {
            this._headers = multiMap;
        }

        @Override // javax.servlet.http.Part
        public String getContentType() {
            return this._contentType;
        }

        @Override // javax.servlet.http.Part
        public String getHeader(String str) {
            if (str == null) {
                return null;
            }
            return (String) this._headers.getValue(str.toLowerCase(Locale.ENGLISH), 0);
        }

        @Override // javax.servlet.http.Part
        public Collection<String> getHeaderNames() {
            return this._headers.keySet();
        }

        @Override // javax.servlet.http.Part
        public Collection<String> getHeaders(String str) {
            return this._headers.getValues(str);
        }

        @Override // javax.servlet.http.Part
        public InputStream getInputStream() throws IOException {
            File file = this._file;
            if (file != null) {
                return new BufferedInputStream(new FileInputStream(file));
            }
            return new ByteArrayInputStream(this._bout.getBuf(), 0, this._bout.size());
        }

        public byte[] getBytes() {
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bout;
            if (byteArrayOutputStream2 != null) {
                return byteArrayOutputStream2.toByteArray();
            }
            return null;
        }

        @Override // javax.servlet.http.Part
        public String getName() {
            return this._name;
        }

        @Override // javax.servlet.http.Part
        public long getSize() {
            return this._size;
        }

        @Override // javax.servlet.http.Part
        public void write(String str) throws IOException {
            Throwable th;
            BufferedOutputStream bufferedOutputStream;
            if (this._file == null) {
                this._temporary = false;
                this._file = new File(MultiPartInputStream.this._tmpDir, str);
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this._file));
                    try {
                        this._bout.writeTo(bufferedOutputStream);
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        this._bout = null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        this._bout = null;
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = null;
                }
            } else {
                this._temporary = false;
                File file = new File(MultiPartInputStream.this._tmpDir, str);
                if (this._file.renameTo(file)) {
                    this._file = file;
                }
            }
        }

        @Override // javax.servlet.http.Part
        public void delete() throws IOException {
            File file = this._file;
            if (file != null && file.exists()) {
                this._file.delete();
            }
        }

        public void cleanUp() throws IOException {
            File file;
            if (this._temporary && (file = this._file) != null && file.exists()) {
                this._file.delete();
            }
        }

        public File getFile() {
            return this._file;
        }

        public String getContentDispositionFilename() {
            return this._filename;
        }
    }

    public MultiPartInputStream(InputStream inputStream, String str, MultipartConfigElement multipartConfigElement, File file) {
        this._in = new BufferedInputStream(inputStream);
        this._contentType = str;
        this._config = multipartConfigElement;
        this._contextTmpDir = file;
        if (this._contextTmpDir == null) {
            this._contextTmpDir = new File(System.getProperty("java.io.tmpdir"));
        }
        if (this._config == null) {
            this._config = new MultipartConfigElement(this._contextTmpDir.getAbsolutePath());
        }
    }

    public Collection<Part> getParsedParts() {
        MultiMap<String> multiMap = this._parts;
        if (multiMap == null) {
            return Collections.emptyList();
        }
        Collection<Object> values = multiMap.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : values) {
            arrayList.addAll(LazyList.getList(obj, false));
        }
        return arrayList;
    }

    public void deleteParts() throws MultiException {
        Collection<Part> parsedParts = getParsedParts();
        MultiException multiException = new MultiException();
        Iterator<Part> it = parsedParts.iterator();
        while (it.hasNext()) {
            try {
                ((MultiPart) it.next()).cleanUp();
            } catch (Exception e) {
                multiException.add(e);
            }
        }
        this._parts.clear();
        multiException.ifExceptionThrowMulti();
    }

    public Collection<Part> getParts() throws IOException, ServletException {
        parse();
        Collection<Object> values = this._parts.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : values) {
            arrayList.addAll(LazyList.getList(obj, false));
        }
        return arrayList;
    }

    public Part getPart(String str) throws IOException, ServletException {
        parse();
        return (Part) this._parts.getValue(str, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0207, code lost:
        if (r7 == false) goto L_0x020e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0209, code lost:
        r3.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x020e, code lost:
        if (r14 == false) goto L_0x0215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0210, code lost:
        r3.write(10);
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0215, code lost:
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0216, code lost:
        r3.write(r2, r7, r6);
        r6 = -1;
        r7 = false;
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x021c, code lost:
        if (r6 > 0) goto L_0x0248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x021f, code lost:
        if (r15 != (-1)) goto L_0x0222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0222, code lost:
        if (r7 == false) goto L_0x0229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0224, code lost:
        r3.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0229, code lost:
        if (r14 == false) goto L_0x0231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x022b, code lost:
        r3.write(10);
        r6 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0231, code lost:
        r6 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0233, code lost:
        if (r15 != r6) goto L_0x0238;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0235, code lost:
        r6 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0238, code lost:
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0239, code lost:
        if (r15 == 10) goto L_0x0240;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x023b, code lost:
        if (r5 != 10) goto L_0x023e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x023e, code lost:
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0240, code lost:
        r7 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0242, code lost:
        if (r5 != 10) goto L_0x0245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0244, code lost:
        r5 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0245, code lost:
        r0 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0249, code lost:
        if (r6 != r2.length) goto L_0x024d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x024b, code lost:
        r4 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x024d, code lost:
        r3.close();
        r3 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0254, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0255, code lost:
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0258, code lost:
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0260, code lost:
        throw new java.io.IOException("Missing content-disposition");
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e0, code lost:
        if (r8 == null) goto L_0x0259;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e2, code lost:
        r14 = new org.eclipse.jetty.util.QuotedStringTokenizer(r8, ";");
        r5 = null;
        r6 = null;
        r15 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00f0, code lost:
        if (r14.hasMoreTokens() == false) goto L_0x0129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f2, code lost:
        r7 = r14.nextToken().trim();
        r0 = r7.toLowerCase(java.util.Locale.ENGLISH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0106, code lost:
        if (r7.startsWith("form-data") == false) goto L_0x010a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0108, code lost:
        r15 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0110, code lost:
        if (r0.startsWith("name=") == false) goto L_0x0119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0112, code lost:
        r5 = value(r7, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x011f, code lost:
        if (r0.startsWith("filename=") == false) goto L_0x0127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0121, code lost:
        r6 = value(r7, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0129, code lost:
        if (r15 != false) goto L_0x012c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x012c, code lost:
        if (r5 != null) goto L_0x0130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x012e, code lost:
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0136, code lost:
        if (io.netty.handler.codec.http.HttpHeaders.Values.BASE64.equalsIgnoreCase(r11) == false) goto L_0x0142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0138, code lost:
        r21._in = new org.eclipse.jetty.util.MultiPartInputStream.Base64InputStream(r21._in);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0148, code lost:
        if (io.netty.handler.codec.http.HttpHeaders.Values.QUOTED_PRINTABLE.equalsIgnoreCase(r11) == false) goto L_0x0153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x014a, code lost:
        r21._in = new org.eclipse.jetty.util.MultiPartInputStream.AnonymousClass1(r21, r21._in);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0153, code lost:
        r3 = new org.eclipse.jetty.util.MultiPartInputStream.MultiPart(r21, r5, r6);
        r3.setHeaders(r13);
        r3.setContentType(r12);
        r21._parts.add(r5, r3);
        r3.open();
        r0 = -2;
        r5 = -2;
        r6 = false;
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x016a, code lost:
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x016e, code lost:
        if (r5 == r0) goto L_0x0172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0170, code lost:
        r15 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0172, code lost:
        r15 = r21._in.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x017a, code lost:
        if (r15 == (-1)) goto L_0x01f5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x017c, code lost:
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x018a, code lost:
        if (r21._config.getMaxRequestSize() <= 0) goto L_0x01b9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0194, code lost:
        if (r9 > r21._config.getMaxRequestSize()) goto L_0x0197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01b8, code lost:
        throw new java.lang.IllegalStateException("Request exceeds maxRequestSize (" + r21._config.getMaxRequestSize() + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01b9, code lost:
        r5 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01bb, code lost:
        if (r15 == 13) goto L_0x01eb;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01bd, code lost:
        if (r15 != 10) goto L_0x01c2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01bf, code lost:
        r5 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01c2, code lost:
        if (r6 < 0) goto L_0x01d0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01c5, code lost:
        if (r6 >= r2.length) goto L_0x01d0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01c9, code lost:
        if (r15 != r2[r6]) goto L_0x01d0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01cb, code lost:
        r6 = r6 + 1;
        r0 = -2;
        r5 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01d0, code lost:
        if (r7 == false) goto L_0x01d7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01d2, code lost:
        r3.write(13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01d7, code lost:
        if (r14 == false) goto L_0x01dc;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01d9, code lost:
        r3.write(10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01dc, code lost:
        if (r6 <= 0) goto L_0x01e2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01de, code lost:
        r3.write(r2, 0, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01e2, code lost:
        r3.write(r15);
        r6 = -1;
        r0 = -2;
        r5 = -2;
        r7 = false;
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01eb, code lost:
        if (r15 != r5) goto L_0x01f4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ed, code lost:
        r5 = r21._in.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01f4, code lost:
        r5 = -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01f5, code lost:
        if (r6 <= 0) goto L_0x0200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01fa, code lost:
        if (r6 < (r2.length - 2)) goto L_0x01fd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01fd, code lost:
        r18 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0200, code lost:
        r18 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0205, code lost:
        if (r6 != (r2.length - 1)) goto L_0x021c;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void parse() throws java.io.IOException, javax.servlet.ServletException {
        /*
            Method dump skipped, instructions count: 769
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.MultiPartInputStream.parse():void");
    }

    public void setDeleteOnExit(boolean z) {
        this._deleteOnExit = z;
    }

    public boolean isDeleteOnExit() {
        return this._deleteOnExit;
    }

    private String value(String str, boolean z) {
        int indexOf;
        String trim = str.substring(str.indexOf(61) + 1).trim();
        int indexOf2 = trim.indexOf(59);
        if (indexOf2 > 0) {
            trim = trim.substring(0, indexOf2);
        }
        if (trim.startsWith("\"")) {
            return trim.substring(1, trim.indexOf(34, 1));
        }
        return (!z || (indexOf = trim.indexOf(32)) <= 0) ? trim : trim.substring(0, indexOf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class Base64InputStream extends InputStream {
        byte[] _buffer;
        BufferedReader _in;
        String _line;
        int _pos;

        public Base64InputStream(InputStream inputStream) {
            this._in = new BufferedReader(new InputStreamReader(inputStream));
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] bArr = this._buffer;
            if (bArr == null || this._pos >= bArr.length) {
                this._line = this._in.readLine();
                String str = this._line;
                if (str == null) {
                    return -1;
                }
                if (str.startsWith("--")) {
                    this._buffer = (this._line + "\r\n").getBytes();
                } else if (this._line.length() == 0) {
                    this._buffer = "\r\n".getBytes();
                } else {
                    this._buffer = B64Code.decode(this._line);
                }
                this._pos = 0;
            }
            byte[] bArr2 = this._buffer;
            int i = this._pos;
            this._pos = i + 1;
            return bArr2[i];
        }
    }
}
