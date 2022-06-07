package com.alibaba.fastjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes.dex */
public abstract class JSONValidator implements Cloneable {
    protected char ch;
    protected boolean eof;
    protected Type type;
    protected int pos = -1;
    protected int count = 0;
    protected boolean supportMultiValue = true;

    /* loaded from: classes.dex */
    public enum Type {
        Object,
        Array,
        Value
    }

    static final boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f' || c == '\b';
    }

    public void close() throws IOException {
    }

    abstract void next();

    public static JSONValidator fromUtf8(byte[] bArr) {
        return new UTF8Validator(bArr);
    }

    public static JSONValidator fromUtf8(InputStream inputStream) {
        return new UTF8InputStreamValidator(inputStream);
    }

    public static JSONValidator from(String str) {
        return new UTF16Validator(str);
    }

    public static JSONValidator from(Reader reader) {
        return new ReaderValidator(reader);
    }

    public Type getType() {
        return this.type;
    }

    public boolean validate() {
        do {
            any();
            this.count++;
            if (!this.supportMultiValue || this.eof) {
                break;
            }
            skipWhiteSpace();
        } while (!this.eof);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void any() {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONValidator.any():void");
    }

    protected void fieldName() {
        next();
        while (true) {
            char c = this.ch;
            if (c == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else if (c == '\"') {
                next();
                return;
            } else {
                next();
            }
        }
    }

    void error() {
        throw new JSONException("error : " + this.pos);
    }

    void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    /* loaded from: classes.dex */
    static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bArr) {
            this.bytes = bArr;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            int i = this.pos;
            byte[] bArr = this.bytes;
            if (i >= bArr.length) {
                this.ch = (char) 0;
                this.eof = true;
                return;
            }
            this.ch = (char) bArr[this.pos];
        }
    }

    /* loaded from: classes.dex */
    static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private final InputStream is;
        private int end = -1;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream inputStream) {
            this.is = inputStream;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            if (this.pos < this.end) {
                byte[] bArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = (char) bArr[i];
            } else if (!this.eof) {
                try {
                    int read = this.is.read(this.buf, 0, this.buf.length);
                    this.readCount++;
                    if (read > 0) {
                        this.ch = (char) this.buf[0];
                        this.pos = 0;
                        this.end = read - 1;
                    } else if (read == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                    } else {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException unused) {
                    throw new JSONException("read error");
                }
            }
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.is.close();
        }
    }

    /* loaded from: classes.dex */
    static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str) {
            this.str = str;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            if (this.pos >= this.str.length()) {
                this.ch = (char) 0;
                this.eof = true;
                return;
            }
            this.ch = this.str.charAt(this.pos);
        }
    }

    /* loaded from: classes.dex */
    static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;
        final Reader r;
        private int end = -1;
        private int readCount = 0;

        ReaderValidator(Reader reader) {
            this.r = reader;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            if (this.pos < this.end) {
                char[] cArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = cArr[i];
            } else if (!this.eof) {
                try {
                    int read = this.r.read(this.buf, 0, this.buf.length);
                    this.readCount++;
                    if (read > 0) {
                        this.ch = this.buf[0];
                        this.pos = 0;
                        this.end = read - 1;
                    } else if (read == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                    } else {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException unused) {
                    throw new JSONException("read error");
                }
            }
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void error() {
            throw new JSONException("error, readCount " + this.readCount + ", valueCount : " + this.count + ", pos " + this.pos);
        }

        @Override // com.alibaba.fastjson.JSONValidator
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.r.close();
        }
    }
}
