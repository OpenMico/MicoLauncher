package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    static final int nonDirectFeatures;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;

    static {
        int parseInt;
        BUFFER_THRESHOLD = 131072;
        try {
            String stringProperty = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (stringProperty != null && stringProperty.length() > 0 && (parseInt = Integer.parseInt(stringProperty)) >= 64 && parseInt <= 65536) {
                BUFFER_THRESHOLD = parseInt * 1024;
            }
        } catch (Throwable unused) {
        }
        nonDirectFeatures = SerializerFeature.UseSingleQuotes.mask | 0 | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    }

    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(SerializerFeature... serializerFeatureArr) {
        this((Writer) null, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, SerializerFeature... serializerFeatureArr) {
        this(writer, 0, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, int i, SerializerFeature... serializerFeatureArr) {
        this.maxBufSize = -1;
        this.writer = writer;
        this.buf = bufLocal.get();
        if (this.buf != null) {
            bufLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        for (SerializerFeature serializerFeature : serializerFeatureArr) {
            i |= serializerFeature.getMask();
        }
        this.features = i;
        computeFeatures();
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public void setMaxBufSize(int i) {
        if (i >= this.buf.length) {
            this.maxBufSize = i;
            return;
        }
        throw new JSONException("must > " + this.buf.length);
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int i) {
        this((Writer) null, i);
    }

    public SerializeWriter(Writer writer, int i) {
        this.maxBufSize = -1;
        this.writer = writer;
        if (i > 0) {
            this.buf = new char[i];
            computeFeatures();
            return;
        }
        throw new IllegalArgumentException("Negative initial size: " + i);
    }

    public void config(SerializerFeature serializerFeature, boolean z) {
        if (z) {
            this.features |= serializerFeature.getMask();
            if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                this.features &= ~SerializerFeature.WriteEnumUsingName.getMask();
            } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                this.features &= ~SerializerFeature.WriteEnumUsingToString.getMask();
            }
        } else {
            this.features = (~serializerFeature.getMask()) & this.features;
        }
        computeFeatures();
    }

    protected void computeFeatures() {
        boolean z = true;
        this.quoteFieldNames = (this.features & SerializerFeature.QuoteFieldNames.mask) != 0;
        this.useSingleQuotes = (this.features & SerializerFeature.UseSingleQuotes.mask) != 0;
        this.sortField = (this.features & SerializerFeature.SortField.mask) != 0;
        this.disableCircularReferenceDetect = (this.features & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
        this.beanToArray = (this.features & SerializerFeature.BeanToArray.mask) != 0;
        this.writeNonStringValueAsString = (this.features & SerializerFeature.WriteNonStringValueAsString.mask) != 0;
        this.notWriteDefaultValue = (this.features & SerializerFeature.NotWriteDefaultValue.mask) != 0;
        this.writeEnumUsingName = (this.features & SerializerFeature.WriteEnumUsingName.mask) != 0;
        this.writeEnumUsingToString = (this.features & SerializerFeature.WriteEnumUsingToString.mask) != 0;
        this.writeDirect = this.quoteFieldNames && (this.features & nonDirectFeatures) == 0 && (this.beanToArray || this.writeEnumUsingName);
        this.keySeperator = this.useSingleQuotes ? '\'' : '\"';
        if ((this.features & SerializerFeature.BrowserSecure.mask) == 0) {
            z = false;
        }
        this.browserSecure = z;
        this.sepcialBits = this.browserSecure ? 5764610843043954687L : (this.features & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return (serializerFeature.mask & this.features) != 0;
    }

    public boolean isEnabled(int i) {
        return (i & this.features) != 0;
    }

    @Override // java.io.Writer
    public void write(int i) {
        int i2 = this.count + 1;
        if (i2 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i2);
            } else {
                flush();
                i2 = 1;
            }
        }
        this.buf[this.count] = (char) i;
        this.count = i2;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        int i3;
        if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) > cArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 != 0) {
            int i4 = this.count + i2;
            if (i4 > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(i4);
                } else {
                    do {
                        char[] cArr2 = this.buf;
                        int length = cArr2.length;
                        int i5 = this.count;
                        int i6 = length - i5;
                        System.arraycopy(cArr, i, cArr2, i5, i6);
                        this.count = this.buf.length;
                        flush();
                        i2 -= i6;
                        i += i6;
                    } while (i2 > this.buf.length);
                    i4 = i2;
                }
            }
            System.arraycopy(cArr, i, this.buf, this.count, i2);
            this.count = i4;
        }
    }

    public void expandCapacity(int i) {
        char[] cArr;
        int i2 = this.maxBufSize;
        if (i2 == -1 || i < i2) {
            char[] cArr2 = this.buf;
            int length = cArr2.length + (cArr2.length >> 1) + 1;
            if (length >= i) {
                i = length;
            }
            char[] cArr3 = new char[i];
            System.arraycopy(this.buf, 0, cArr3, 0, this.count);
            if (this.buf.length < BUFFER_THRESHOLD && ((cArr = bufLocal.get()) == null || cArr.length < this.buf.length)) {
                bufLocal.set(this.buf);
            }
            this.buf = cArr3;
            return;
        }
        throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + i);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? "null" : charSequence.toString();
        write(charSequence2, 0, charSequence2.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        String charSequence2 = charSequence.subSequence(i, i2).toString();
        write(charSequence2, 0, charSequence2.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) {
        int i3;
        int i4 = this.count + i2;
        if (i4 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i4);
            } else {
                while (true) {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i5 = this.count;
                    int i6 = length - i5;
                    i3 = i + i6;
                    str.getChars(i, i3, cArr, i5);
                    this.count = this.buf.length;
                    flush();
                    i2 -= i6;
                    if (i2 <= this.buf.length) {
                        break;
                    }
                    i = i3;
                }
                i4 = i2;
                i = i3;
            }
        }
        str.getChars(i, i2 + i, this.buf, this.count);
        this.count = i4;
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.writer == null) {
            writer.write(this.buf, 0, this.count);
            return;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public void writeTo(OutputStream outputStream, String str) throws IOException {
        writeTo(outputStream, Charset.forName(str));
    }

    public void writeTo(OutputStream outputStream, Charset charset) throws IOException {
        writeToEx(outputStream, charset);
    }

    public int writeToEx(OutputStream outputStream, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        } else if (charset == IOUtils.UTF8) {
            return encodeToUTF8(outputStream);
        } else {
            byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
            outputStream.write(bytes);
            return bytes.length;
        }
    }

    public char[] toCharArray() {
        if (this.writer == null) {
            int i = this.count;
            char[] cArr = new char[i];
            System.arraycopy(this.buf, 0, cArr, 0, i);
            return cArr;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer == null) {
            int i = this.count;
            char[] cArr = new char[i - 2];
            System.arraycopy(this.buf, 1, cArr, 0, i - 2);
            return cArr;
        }
        throw new UnsupportedOperationException("writer not null");
    }

    public byte[] toBytes(String str) {
        Charset charset;
        if (str == null || "UTF-8".equals(str)) {
            charset = IOUtils.UTF8;
        } else {
            charset = Charset.forName(str);
        }
        return toBytes(charset);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        } else if (charset == IOUtils.UTF8) {
            return encodeToUTF8Bytes();
        } else {
            return new String(this.buf, 0, this.count).getBytes(charset);
        }
    }

    private int encodeToUTF8(OutputStream outputStream) throws IOException {
        int i = (int) (this.count * 3.0d);
        byte[] bArr = bytesBufLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            bytesBufLocal.set(bArr);
        }
        if (bArr.length < i) {
            bArr = new byte[i];
        }
        int encodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr);
        outputStream.write(bArr, 0, encodeUTF8);
        return encodeUTF8;
    }

    private byte[] encodeToUTF8Bytes() {
        int i = (int) (this.count * 3.0d);
        byte[] bArr = bytesBufLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            bytesBufLocal.set(bArr);
        }
        if (bArr.length < i) {
            bArr = new byte[i];
        }
        int encodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr);
        byte[] bArr2 = new byte[encodeUTF8];
        System.arraycopy(bArr, 0, bArr2, 0, encodeUTF8);
        return bArr2;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= BUFFER_THRESHOLD) {
            bufLocal.set(cArr);
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str == null) {
            writeNull();
        } else {
            write(str, 0, str.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int stringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int i2 = this.count + stringSize;
        if (i2 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i2);
            } else {
                char[] cArr = new char[stringSize];
                IOUtils.getChars(i, stringSize, cArr);
                write(cArr, 0, cArr.length);
                return;
            }
        }
        IOUtils.getChars(i, i2, this.buf);
        this.count = i2;
    }

    public void writeByteArray(byte[] bArr) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bArr);
            return;
        }
        int length = bArr.length;
        char c = this.useSingleQuotes ? '\'' : '\"';
        if (length == 0) {
            write(this.useSingleQuotes ? "''" : "\"\"");
            return;
        }
        char[] cArr = IOUtils.CA;
        int i = (length / 3) * 3;
        int i2 = length - 1;
        int i3 = this.count;
        int i4 = (((i2 / 3) + 1) << 2) + i3 + 2;
        int i5 = 0;
        if (i4 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                int i6 = 0;
                while (i6 < i) {
                    int i7 = i6 + 1;
                    int i8 = i7 + 1;
                    int i9 = ((bArr[i6] & 255) << 16) | ((bArr[i7] & 255) << 8);
                    i6 = i8 + 1;
                    int i10 = i9 | (bArr[i8] & 255);
                    write(cArr[(i10 >>> 18) & 63]);
                    write(cArr[(i10 >>> 12) & 63]);
                    write(cArr[(i10 >>> 6) & 63]);
                    write(cArr[i10 & 63]);
                }
                int i11 = length - i;
                if (i11 > 0) {
                    int i12 = (bArr[i] & 255) << 10;
                    if (i11 == 2) {
                        i5 = (bArr[i2] & 255) << 2;
                    }
                    int i13 = i12 | i5;
                    write(cArr[i13 >> 12]);
                    write(cArr[(i13 >>> 6) & 63]);
                    write(i11 == 2 ? cArr[i13 & 63] : '=');
                    write(61);
                }
                write(c);
                return;
            }
            expandCapacity(i4);
        }
        this.count = i4;
        int i14 = i3 + 1;
        this.buf[i3] = c;
        int i15 = 0;
        while (i15 < i) {
            int i16 = i15 + 1;
            int i17 = i16 + 1;
            int i18 = ((bArr[i15] & 255) << 16) | ((bArr[i16] & 255) << 8);
            i15 = i17 + 1;
            int i19 = i18 | (bArr[i17] & 255);
            char[] cArr2 = this.buf;
            int i20 = i14 + 1;
            cArr2[i14] = cArr[(i19 >>> 18) & 63];
            int i21 = i20 + 1;
            cArr2[i20] = cArr[(i19 >>> 12) & 63];
            int i22 = i21 + 1;
            cArr2[i21] = cArr[(i19 >>> 6) & 63];
            i14 = i22 + 1;
            cArr2[i22] = cArr[i19 & 63];
        }
        int i23 = length - i;
        if (i23 > 0) {
            int i24 = (bArr[i] & 255) << 10;
            if (i23 == 2) {
                i5 = (bArr[i2] & 255) << 2;
            }
            int i25 = i24 | i5;
            char[] cArr3 = this.buf;
            cArr3[i4 - 5] = cArr[i25 >> 12];
            cArr3[i4 - 4] = cArr[(i25 >>> 6) & 63];
            cArr3[i4 - 3] = i23 == 2 ? cArr[i25 & 63] : '=';
            this.buf[i4 - 2] = '=';
        }
        this.buf[i4 - 1] = c;
    }

    public void writeHex(byte[] bArr) {
        int length = this.count + (bArr.length * 2) + 3;
        if (length > this.buf.length) {
            expandCapacity(length);
        }
        char[] cArr = this.buf;
        int i = this.count;
        this.count = i + 1;
        cArr[i] = 'x';
        int i2 = this.count;
        this.count = i2 + 1;
        cArr[i2] = '\'';
        for (byte b : bArr) {
            int i3 = b & 255;
            int i4 = i3 >> 4;
            int i5 = i3 & 15;
            char[] cArr2 = this.buf;
            int i6 = this.count;
            this.count = i6 + 1;
            int i7 = 48;
            cArr2[i6] = (char) (i4 + (i4 < 10 ? 48 : 55));
            char[] cArr3 = this.buf;
            int i8 = this.count;
            this.count = i8 + 1;
            if (i5 >= 10) {
                i7 = 55;
            }
            cArr3[i8] = (char) (i5 + i7);
        }
        char[] cArr4 = this.buf;
        int i9 = this.count;
        this.count = i9 + 1;
        cArr4[i9] = '\'';
    }

    public void writeFloat(float f, boolean z) {
        if (f != f || f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int i = this.count + 15;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String ryuFloat = RyuFloat.toString(f);
                write(ryuFloat, 0, ryuFloat.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
        }
        this.count += RyuFloat.toString(f, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double d, boolean z) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            writeNull();
            return;
        }
        int i = this.count + 24;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String ryuDouble = RyuDouble.toString(d);
                write(ryuDouble, 0, ryuDouble.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
        }
        this.count += RyuDouble.toString(d, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> r3) {
        if (r3 == null) {
            writeNull();
            return;
        }
        String str = null;
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            str = r3.name();
        } else if (this.writeEnumUsingToString) {
            str = r3.toString();
        }
        if (str != null) {
            int i = isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
            write(i);
            write(str);
            write(i);
            return;
        }
        writeInt(r3.ordinal());
    }

    public void writeLongAndChar(long j, char c) throws IOException {
        writeLong(j);
        write(c);
    }

    public void writeLong(long j) {
        boolean z = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (j > 9007199254740991L || j < -9007199254740991L);
        if (j != Long.MIN_VALUE) {
            int stringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
            int i = this.count + stringSize;
            if (z) {
                i += 2;
            }
            if (i > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(i);
                } else {
                    char[] cArr = new char[stringSize];
                    IOUtils.getChars(j, stringSize, cArr);
                    if (z) {
                        write(34);
                        write(cArr, 0, cArr.length);
                        write(34);
                        return;
                    }
                    write(cArr, 0, cArr.length);
                    return;
                }
            }
            if (z) {
                char[] cArr2 = this.buf;
                cArr2[this.count] = '\"';
                int i2 = i - 1;
                IOUtils.getChars(j, i2, cArr2);
                this.buf[i2] = '\"';
            } else {
                IOUtils.getChars(j, i, this.buf);
            }
            this.count = i;
        } else if (z) {
            write("\"-9223372036854775808\"");
        } else {
            write("-9223372036854775808");
        }
    }

    public void writeNull() {
        write("null");
    }

    public void writeNull(SerializerFeature serializerFeature) {
        writeNull(0, serializerFeature.mask);
    }

    public void writeNull(int i, int i2) {
        if ((i & i2) == 0 && (this.features & i2) == 0) {
            writeNull();
        } else if ((SerializerFeature.WriteMapNullValue.mask & i) != 0 && (i & (~SerializerFeature.WriteMapNullValue.mask) & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
            writeNull();
        } else if (i2 == SerializerFeature.WriteNullListAsEmpty.mask) {
            write(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        } else if (i2 == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
        } else if (i2 == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write("false");
        } else if (i2 == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:217:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x053f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeStringWithDoubleQuote(java.lang.String r20, char r21) {
        /*
            Method dump skipped, instructions count: 1474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(java.lang.String, char):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:141:0x02fa, code lost:
        if ((r21.sepcialBits & (1 << r9)) == 0) goto L_0x02ff;
     */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04f9  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0539  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0330 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeStringWithDoubleQuote(char[] r22, char r23) {
        /*
            Method dump skipped, instructions count: 1468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeWriter.writeStringWithDoubleQuote(char[], char):void");
    }

    public void writeFieldNameDirect(String str) {
        int length = str.length();
        int i = this.count + length + 3;
        if (i > this.buf.length) {
            expandCapacity(i);
        }
        int i2 = this.count;
        char[] cArr = this.buf;
        cArr[i2] = '\"';
        str.getChars(0, length, cArr, i2 + 1);
        this.count = i;
        char[] cArr2 = this.buf;
        int i3 = this.count;
        cArr2[i3 - 2] = '\"';
        cArr2[i3 - 1] = ':';
    }

    public void write(List<String> list) {
        boolean z;
        int i;
        if (list.isEmpty()) {
            write(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        int i2 = this.count;
        int size = list.size();
        int i3 = i2;
        for (int i4 = 0; i4 < size; i4++) {
            String str = list.get(i4);
            if (str == null) {
                z = true;
            } else {
                int length = str.length();
                z = false;
                for (int i5 = 0; i5 < length; i5++) {
                    char charAt = str.charAt(i5);
                    z = charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\\';
                    if (z) {
                        break;
                    }
                }
            }
            if (z) {
                this.count = i2;
                write(91);
                for (int i6 = 0; i6 < list.size(); i6++) {
                    String str2 = list.get(i6);
                    if (i6 != 0) {
                        write(44);
                    }
                    if (str2 == null) {
                        write("null");
                    } else {
                        writeStringWithDoubleQuote(str2, (char) 0);
                    }
                }
                write(93);
                return;
            }
            int length2 = str.length() + i3 + 3;
            if (i4 == list.size() - 1) {
                length2++;
            }
            if (length2 > this.buf.length) {
                this.count = i3;
                expandCapacity(length2);
            }
            if (i4 == 0) {
                i = i3 + 1;
                this.buf[i3] = '[';
            } else {
                i = i3 + 1;
                this.buf[i3] = StringUtil.COMMA;
            }
            int i7 = i + 1;
            this.buf[i] = '\"';
            str.getChars(0, str.length(), this.buf, i7);
            int length3 = i7 + str.length();
            i3 = length3 + 1;
            this.buf[length3] = '\"';
        }
        this.buf[i3] = ']';
        this.count = i3 + 1;
    }

    public void writeFieldValue(char c, String str, char c2) {
        write(c);
        writeFieldName(str);
        if (c2 == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(c2));
        }
    }

    public void writeFieldValue(char c, String str, boolean z) {
        if (!this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            write(z);
            return;
        }
        int i = z ? 4 : 5;
        int length = str.length();
        int i2 = this.count + length + 4 + i;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeString(str);
                write(58);
                write(z);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length + 1;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        this.buf[i4 + 1] = this.keySeperator;
        if (z) {
            System.arraycopy(":true".toCharArray(), 0, this.buf, i4 + 2, 5);
        } else {
            System.arraycopy(":false".toCharArray(), 0, this.buf, i4 + 2, 6);
        }
    }

    public void write(boolean z) {
        if (z) {
            write("true");
        } else {
            write("false");
        }
    }

    public void writeFieldValue(char c, String str, int i) {
        if (i == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            writeInt(i);
            return;
        }
        int stringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int length = str.length();
        int i2 = this.count + length + 4 + stringSize;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeInt(i);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length + 1;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        char[] cArr2 = this.buf;
        cArr2[i4 + 1] = this.keySeperator;
        cArr2[i4 + 2] = ':';
        IOUtils.getChars(i, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, long j) {
        if (j == Long.MIN_VALUE || !this.quoteFieldNames || isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            write(c);
            writeFieldName(str);
            writeLong(j);
            return;
        }
        int stringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
        int length = str.length();
        int i = this.count + length + 4 + stringSize;
        if (i > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeLong(j);
                return;
            }
            expandCapacity(i);
        }
        int i2 = this.count;
        this.count = i;
        char[] cArr = this.buf;
        cArr[i2] = c;
        int i3 = i2 + length + 1;
        cArr[i2 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i2 + 2);
        char[] cArr2 = this.buf;
        cArr2[i3 + 1] = this.keySeperator;
        cArr2[i3 + 2] = ':';
        IOUtils.getChars(j, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, float f) {
        write(c);
        writeFieldName(str);
        writeFloat(f, false);
    }

    public void writeFieldValue(char c, String str, double d) {
        write(c);
        writeFieldName(str);
        writeDouble(d, false);
    }

    public void writeFieldValue(char c, String str, String str2) {
        if (!this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            if (str2 == null) {
                writeNull();
            } else {
                writeString(str2);
            }
        } else if (this.useSingleQuotes) {
            write(c);
            writeFieldName(str);
            if (str2 == null) {
                writeNull();
            } else {
                writeString(str2);
            }
        } else if (isEnabled(SerializerFeature.BrowserCompatible)) {
            write(c);
            writeStringWithDoubleQuote(str, ':');
            writeStringWithDoubleQuote(str2, (char) 0);
        } else {
            writeFieldValueStringWithDoubleQuoteCheck(c, str, str2);
        }
    }

    public void writeFieldValueStringWithDoubleQuoteCheck(char c, String str, String str2) {
        int i;
        int i2;
        int length = str.length();
        int i3 = this.count;
        if (str2 == null) {
            i2 = i3 + length + 8;
            i = 4;
        } else {
            i = str2.length();
            i2 = i3 + length + i + 6;
        }
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i2);
        }
        char[] cArr = this.buf;
        int i4 = this.count;
        cArr[i4] = c;
        int i5 = i4 + 2;
        int i6 = i5 + length;
        cArr[i4 + 1] = '\"';
        str.getChars(0, length, cArr, i5);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i6] = '\"';
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        cArr2[i7] = ':';
        if (str2 == null) {
            int i9 = i8 + 1;
            cArr2[i8] = 'n';
            int i10 = i9 + 1;
            cArr2[i9] = 'u';
            cArr2[i10] = 'l';
            cArr2[i10 + 1] = 'l';
            return;
        }
        int i11 = i8 + 1;
        cArr2[i8] = '\"';
        int i12 = i11 + i;
        str2.getChars(0, i, cArr2, i11);
        int i13 = -1;
        int i14 = -1;
        int i15 = i2;
        int i16 = 0;
        char c2 = 0;
        for (int i17 = i11; i17 < i12; i17++) {
            char c3 = this.buf[i17];
            if (c3 < ']') {
                if ((c3 < '@' && (this.sepcialBits & (1 << c3)) != 0) || c3 == '\\') {
                    i16++;
                    if (c3 == '(' || c3 == ')' || c3 == '<' || c3 == '>' || (c3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c3] == 4)) {
                        i15 += 4;
                    }
                    if (i13 == -1) {
                        i13 = i17;
                        i14 = i13;
                        c2 = c3;
                    } else {
                        i14 = i17;
                        c2 = c3;
                    }
                }
            } else if (c3 >= 127 && (c3 == 8232 || c3 == 8233 || c3 < 160)) {
                if (i13 == -1) {
                    i13 = i17;
                }
                i16++;
                i15 += 4;
                i14 = i17;
                c2 = c3;
            }
        }
        if (i16 > 0) {
            int i18 = i15 + i16;
            if (i18 > this.buf.length) {
                expandCapacity(i18);
            }
            this.count = i18;
            if (i16 == 1) {
                if (c2 == 8232) {
                    int i19 = i14 + 1;
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i19, cArr3, i14 + 6, (i12 - i14) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i14] = '\\';
                    cArr4[i19] = 'u';
                    int i20 = i19 + 1;
                    cArr4[i20] = '2';
                    int i21 = i20 + 1;
                    cArr4[i21] = '0';
                    int i22 = i21 + 1;
                    cArr4[i22] = '2';
                    cArr4[i22 + 1] = '8';
                } else if (c2 == 8233) {
                    int i23 = i14 + 1;
                    char[] cArr5 = this.buf;
                    System.arraycopy(cArr5, i23, cArr5, i14 + 6, (i12 - i14) - 1);
                    char[] cArr6 = this.buf;
                    cArr6[i14] = '\\';
                    cArr6[i23] = 'u';
                    int i24 = i23 + 1;
                    cArr6[i24] = '2';
                    int i25 = i24 + 1;
                    cArr6[i25] = '0';
                    int i26 = i25 + 1;
                    cArr6[i26] = '2';
                    cArr6[i26 + 1] = '9';
                } else if (c2 == '(' || c2 == ')' || c2 == '<' || c2 == '>') {
                    int i27 = i14 + 1;
                    char[] cArr7 = this.buf;
                    System.arraycopy(cArr7, i27, cArr7, i14 + 6, (i12 - i14) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i14] = '\\';
                    int i28 = i27 + 1;
                    cArr8[i27] = 'u';
                    int i29 = i28 + 1;
                    cArr8[i28] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    int i30 = i29 + 1;
                    this.buf[i29] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i30] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i30 + 1] = IOUtils.DIGITS[c2 & 15];
                } else if (c2 >= IOUtils.specicalFlags_doubleQuotes.length || IOUtils.specicalFlags_doubleQuotes[c2] != 4) {
                    int i31 = i14 + 1;
                    char[] cArr9 = this.buf;
                    System.arraycopy(cArr9, i31, cArr9, i14 + 2, (i12 - i14) - 1);
                    char[] cArr10 = this.buf;
                    cArr10[i14] = '\\';
                    cArr10[i31] = IOUtils.replaceChars[c2];
                } else {
                    int i32 = i14 + 1;
                    char[] cArr11 = this.buf;
                    System.arraycopy(cArr11, i32, cArr11, i14 + 6, (i12 - i14) - 1);
                    char[] cArr12 = this.buf;
                    cArr12[i14] = '\\';
                    int i33 = i32 + 1;
                    cArr12[i32] = 'u';
                    int i34 = i33 + 1;
                    cArr12[i33] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    int i35 = i34 + 1;
                    this.buf[i34] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i35] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i35 + 1] = IOUtils.DIGITS[c2 & 15];
                }
            } else if (i16 > 1) {
                for (int i36 = i13 - i11; i36 < str2.length(); i36++) {
                    char charAt = str2.charAt(i36);
                    if (this.browserSecure && (charAt == '(' || charAt == ')' || charAt == '<' || charAt == '>')) {
                        char[] cArr13 = this.buf;
                        int i37 = i13 + 1;
                        cArr13[i13] = '\\';
                        int i38 = i37 + 1;
                        cArr13[i37] = 'u';
                        int i39 = i38 + 1;
                        cArr13[i38] = IOUtils.DIGITS[(charAt >>> '\f') & 15];
                        int i40 = i39 + 1;
                        this.buf[i39] = IOUtils.DIGITS[(charAt >>> '\b') & 15];
                        int i41 = i40 + 1;
                        this.buf[i40] = IOUtils.DIGITS[(charAt >>> 4) & 15];
                        i13 = i41 + 1;
                        this.buf[i41] = IOUtils.DIGITS[charAt & 15];
                    }
                    if ((charAt < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[charAt] != 0) || (charAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i42 = i13 + 1;
                        this.buf[i13] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[charAt] == 4) {
                            char[] cArr14 = this.buf;
                            int i43 = i42 + 1;
                            cArr14[i42] = 'u';
                            int i44 = i43 + 1;
                            cArr14[i43] = IOUtils.DIGITS[(charAt >>> '\f') & 15];
                            int i45 = i44 + 1;
                            this.buf[i44] = IOUtils.DIGITS[(charAt >>> '\b') & 15];
                            int i46 = i45 + 1;
                            this.buf[i45] = IOUtils.DIGITS[(charAt >>> 4) & 15];
                            i13 = i46 + 1;
                            this.buf[i46] = IOUtils.DIGITS[charAt & 15];
                        } else {
                            i13 = i42 + 1;
                            this.buf[i42] = IOUtils.replaceChars[charAt];
                        }
                    }
                    if (charAt == 8232 || charAt == 8233) {
                        char[] cArr15 = this.buf;
                        int i47 = i13 + 1;
                        cArr15[i13] = '\\';
                        int i48 = i47 + 1;
                        cArr15[i47] = 'u';
                        int i49 = i48 + 1;
                        cArr15[i48] = IOUtils.DIGITS[(charAt >>> '\f') & 15];
                        int i50 = i49 + 1;
                        this.buf[i49] = IOUtils.DIGITS[(charAt >>> '\b') & 15];
                        int i51 = i50 + 1;
                        this.buf[i50] = IOUtils.DIGITS[(charAt >>> 4) & 15];
                        i13 = i51 + 1;
                        this.buf[i51] = IOUtils.DIGITS[charAt & 15];
                    } else {
                        i13++;
                        this.buf[i13] = charAt;
                    }
                }
            }
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldValueStringWithDoubleQuote(char c, String str, String str2) {
        int length = str.length();
        int i = this.count;
        int length2 = str2.length();
        int i2 = i + length + length2 + 6;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i2);
        }
        char[] cArr = this.buf;
        int i3 = this.count;
        cArr[i3] = c;
        int i4 = i3 + 2;
        int i5 = i4 + length;
        cArr[i3 + 1] = '\"';
        str.getChars(0, length, cArr, i4);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i5] = '\"';
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        cArr2[i6] = ':';
        cArr2[i7] = '\"';
        str2.getChars(0, length2, cArr2, i7 + 1);
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldValue(char c, String str, Enum<?> r4) {
        if (r4 == null) {
            write(c);
            writeFieldName(str);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.toString());
        } else {
            writeFieldValue(c, str, r4.ordinal());
        }
    }

    private void writeEnumFieldValue(char c, String str, String str2) {
        if (this.useSingleQuotes) {
            writeFieldValue(c, str, str2);
        } else {
            writeFieldValueStringWithDoubleQuote(c, str, str2);
        }
    }

    public void writeFieldValue(char c, String str, BigDecimal bigDecimal) {
        String str2;
        write(c);
        writeFieldName(str);
        if (bigDecimal == null) {
            writeNull();
            return;
        }
        int scale = bigDecimal.scale();
        if (!isEnabled(SerializerFeature.WriteBigDecimalAsPlain) || scale < -100 || scale >= 100) {
            str2 = bigDecimal.toString();
        } else {
            str2 = bigDecimal.toPlainString();
        }
        write(str2);
    }

    public void writeString(String str, char c) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
            write(c);
            return;
        }
        writeStringWithDoubleQuote(str, c);
    }

    public void writeString(String str) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
        } else {
            writeStringWithDoubleQuote(str, (char) 0);
        }
    }

    public void writeString(char[] cArr) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(cArr);
        } else {
            writeStringWithDoubleQuote(new String(cArr), (char) 0);
        }
    }

    public void writeStringWithSingleQuote(String str) {
        int i = 0;
        if (str == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = str.length();
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < str.length()) {
                    char charAt = str.charAt(i);
                    if (charAt <= '\r' || charAt == '\\' || charAt == '\'' || (charAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[charAt]);
                    } else {
                        write(charAt);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = i5 + length;
        char[] cArr = this.buf;
        cArr[i4] = '\'';
        str.getChars(0, length, cArr, i5);
        this.count = i3;
        int i7 = -1;
        char c = 0;
        for (int i8 = i5; i8 < i6; i8++) {
            char c2 = this.buf[i8];
            if (c2 <= '\r' || c2 == '\\' || c2 == '\'' || (c2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c = c2;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr2 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr2, i10, cArr2, i7 + 2, (i6 - i7) - 1);
            char[] cArr3 = this.buf;
            cArr3[i7] = '\\';
            cArr3[i10] = IOUtils.replaceChars[c];
        } else if (i > 1) {
            char[] cArr4 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr4, i11, cArr4, i7 + 2, (i6 - i7) - 1);
            char[] cArr5 = this.buf;
            cArr5[i7] = '\\';
            cArr5[i11] = IOUtils.replaceChars[c];
            int i12 = i6 + 1;
            for (int i13 = i11 - 2; i13 >= i5; i13--) {
                char c3 = this.buf[i13];
                if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr6 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr6, i14, cArr6, i13 + 2, (i12 - i13) - 1);
                    char[] cArr7 = this.buf;
                    cArr7[i13] = '\\';
                    cArr7[i14] = IOUtils.replaceChars[c3];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    protected void writeStringWithSingleQuote(char[] cArr) {
        int i = 0;
        if (cArr == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = cArr.length;
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < cArr.length) {
                    char c = cArr[i];
                    if (c <= '\r' || c == '\\' || c == '\'' || (c == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[c]);
                    } else {
                        write(c);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = length + i5;
        char[] cArr2 = this.buf;
        cArr2[i4] = '\'';
        System.arraycopy(cArr, 0, cArr2, i5, cArr.length);
        this.count = i3;
        int i7 = -1;
        char c2 = 0;
        for (int i8 = i5; i8 < i6; i8++) {
            char c3 = this.buf[i8];
            if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c2 = c3;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr3 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr3, i10, cArr3, i7 + 2, (i6 - i7) - 1);
            char[] cArr4 = this.buf;
            cArr4[i7] = '\\';
            cArr4[i10] = IOUtils.replaceChars[c2];
        } else if (i > 1) {
            char[] cArr5 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr5, i11, cArr5, i7 + 2, (i6 - i7) - 1);
            char[] cArr6 = this.buf;
            cArr6[i7] = '\\';
            cArr6[i11] = IOUtils.replaceChars[c2];
            int i12 = i6 + 1;
            for (int i13 = i11 - 2; i13 >= i5; i13--) {
                char c4 = this.buf[i13];
                if (c4 <= '\r' || c4 == '\\' || c4 == '\'' || (c4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr7 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr7, i14, cArr7, i13 + 2, (i12 - i13) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i13] = '\\';
                    cArr8[i14] = IOUtils.replaceChars[c4];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String str) {
        writeFieldName(str, false);
    }

    public void writeFieldName(String str, boolean z) {
        if (str == null) {
            write("null:");
        } else if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(str);
                write(58);
                return;
            }
            writeKeyWithSingleQuoteIfHasSpecial(str);
        } else if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(str, ':');
        } else {
            boolean z2 = str.length() == 0;
            int i = 0;
            while (true) {
                if (i >= str.length()) {
                    break;
                }
                char charAt = str.charAt(i);
                if ((charAt < '@' && (this.sepcialBits & (1 << charAt)) != 0) || charAt == '\\') {
                    z2 = true;
                    break;
                }
                i++;
            }
            if (z2) {
                writeStringWithDoubleQuote(str, ':');
                return;
            }
            write(str);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String str) {
        int i;
        byte[] bArr = IOUtils.specicalFlags_singleQuotes;
        int length = str.length();
        boolean z = true;
        int i2 = this.count + length + 1;
        int i3 = 0;
        if (i2 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i2);
            } else if (length == 0) {
                write(39);
                write(39);
                write(58);
                return;
            } else {
                int i4 = 0;
                while (true) {
                    if (i4 < length) {
                        char charAt = str.charAt(i4);
                        if (charAt < bArr.length && bArr[charAt] != 0) {
                            break;
                        }
                        i4++;
                    } else {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    write(39);
                }
                while (i3 < length) {
                    char charAt2 = str.charAt(i3);
                    if (charAt2 >= bArr.length || bArr[charAt2] == 0) {
                        write(charAt2);
                    } else {
                        write(92);
                        write(IOUtils.replaceChars[charAt2]);
                    }
                    i3++;
                }
                if (z) {
                    write(39);
                }
                write(58);
                return;
            }
        }
        if (length == 0) {
            int i5 = this.count;
            if (i5 + 3 > this.buf.length) {
                expandCapacity(i5 + 3);
            }
            char[] cArr = this.buf;
            int i6 = this.count;
            this.count = i6 + 1;
            cArr[i6] = '\'';
            int i7 = this.count;
            this.count = i7 + 1;
            cArr[i7] = '\'';
            int i8 = this.count;
            this.count = i8 + 1;
            cArr[i8] = ':';
            return;
        }
        int i9 = this.count;
        int i10 = i9 + length;
        str.getChars(0, length, this.buf, i9);
        this.count = i2;
        int i11 = i9;
        boolean z2 = false;
        while (i11 < i10) {
            char[] cArr2 = this.buf;
            char c = cArr2[i11];
            if (c >= bArr.length || bArr[c] == 0) {
                i = i11;
            } else if (!z2) {
                i2 += 3;
                if (i2 > cArr2.length) {
                    expandCapacity(i2);
                }
                this.count = i2;
                char[] cArr3 = this.buf;
                int i12 = i11 + 1;
                System.arraycopy(cArr3, i12, cArr3, i11 + 3, (i10 - i11) - 1);
                char[] cArr4 = this.buf;
                System.arraycopy(cArr4, i3, cArr4, 1, i11);
                char[] cArr5 = this.buf;
                cArr5[i9] = '\'';
                cArr5[i12] = '\\';
                int i13 = i12 + 1;
                cArr5[i13] = IOUtils.replaceChars[c];
                i10 += 2;
                this.buf[this.count - 2] = '\'';
                i = i13;
                z2 = true;
            } else {
                i2++;
                if (i2 > cArr2.length) {
                    expandCapacity(i2);
                }
                this.count = i2;
                char[] cArr6 = this.buf;
                i = i11 + 1;
                System.arraycopy(cArr6, i, cArr6, i11 + 2, i10 - i11);
                char[] cArr7 = this.buf;
                cArr7[i11] = '\\';
                cArr7[i] = IOUtils.replaceChars[c];
                i10++;
            }
            i11 = i + 1;
            i3 = 0;
        }
        this.buf[i2 - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        Writer writer = this.writer;
        if (writer != null) {
            try {
                writer.write(this.buf, 0, this.count);
                this.writer.flush();
                this.count = 0;
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
    }

    public void reset() {
        this.count = 0;
    }
}
