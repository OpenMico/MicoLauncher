package com.alibaba.fastjson.asm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okio.Utf8;

/* loaded from: classes.dex */
public class ClassReader {
    public final byte[] b;
    public final int header;
    private final int[] items;
    private final int maxStringLength;
    private boolean readAnnotations;
    private final String[] strings;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ClassReader(InputStream inputStream, boolean z) throws IOException {
        int i;
        this.readAnnotations = z;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            i = 0;
            if (read == -1) {
                break;
            } else if (read > 0) {
                byteArrayOutputStream.write(bArr, 0, read);
            }
        }
        inputStream.close();
        this.b = byteArrayOutputStream.toByteArray();
        this.items = new int[readUnsignedShort(8)];
        int length = this.items.length;
        this.strings = new String[length];
        int i2 = 10;
        int i3 = 1;
        while (i3 < length) {
            int i4 = i2 + 1;
            this.items[i3] = i4;
            byte b = this.b[i2];
            int i5 = 3;
            if (b == 1) {
                i5 = 3 + readUnsignedShort(i4);
                if (i5 > i) {
                    i = i5;
                }
            } else if (b != 15) {
                if (b != 18) {
                    switch (b) {
                        case 3:
                        case 4:
                            break;
                        case 5:
                        case 6:
                            i5 = 9;
                            i3++;
                            break;
                        default:
                            switch (b) {
                            }
                    }
                }
                i5 = 5;
            } else {
                i5 = 4;
            }
            i2 += i5;
            i3++;
        }
        this.maxStringLength = i;
        this.header = i2;
    }

    public void accept(TypeCollector typeCollector) {
        int i;
        char[] cArr = new char[this.maxStringLength];
        if (this.readAnnotations) {
            int attributes = getAttributes();
            for (int readUnsignedShort = readUnsignedShort(attributes); readUnsignedShort > 0; readUnsignedShort--) {
                if ("RuntimeVisibleAnnotations".equals(readUTF8(attributes + 2, cArr))) {
                    i = attributes + 8;
                    break;
                } else {
                    attributes += readInt(attributes + 4) + 6;
                }
            }
        }
        i = 0;
        int i2 = this.header;
        int i3 = this.items[readUnsignedShort(i2 + 4)];
        int readUnsignedShort2 = readUnsignedShort(i2 + 6);
        int i4 = i2 + 8;
        for (int i5 = 0; i5 < readUnsignedShort2; i5++) {
            i4 += 2;
        }
        int i6 = i4 + 2;
        int i7 = i6;
        for (int readUnsignedShort3 = readUnsignedShort(i4); readUnsignedShort3 > 0; readUnsignedShort3--) {
            i7 += 8;
            for (int readUnsignedShort4 = readUnsignedShort(i7 + 6); readUnsignedShort4 > 0; readUnsignedShort4--) {
                i7 += readInt(i7 + 2) + 6;
            }
        }
        int i8 = i7 + 2;
        for (int readUnsignedShort5 = readUnsignedShort(i7); readUnsignedShort5 > 0; readUnsignedShort5--) {
            i8 += 8;
            for (int readUnsignedShort6 = readUnsignedShort(i8 + 6); readUnsignedShort6 > 0; readUnsignedShort6--) {
                i8 += readInt(i8 + 2) + 6;
            }
        }
        int i9 = i8 + 2;
        for (int readUnsignedShort7 = readUnsignedShort(i8); readUnsignedShort7 > 0; readUnsignedShort7--) {
            i9 += readInt(i9 + 2) + 6;
        }
        if (i != 0) {
            int i10 = i + 2;
            for (int readUnsignedShort8 = readUnsignedShort(i); readUnsignedShort8 > 0; readUnsignedShort8--) {
                typeCollector.visitAnnotation(readUTF8(i10, cArr));
            }
        }
        for (int readUnsignedShort9 = readUnsignedShort(i4); readUnsignedShort9 > 0; readUnsignedShort9--) {
            i6 += 8;
            for (int readUnsignedShort10 = readUnsignedShort(i6 + 6); readUnsignedShort10 > 0; readUnsignedShort10--) {
                i6 += readInt(i6 + 2) + 6;
            }
        }
        int i11 = i6 + 2;
        for (int readUnsignedShort11 = readUnsignedShort(i6); readUnsignedShort11 > 0; readUnsignedShort11--) {
            i11 = readMethod(typeCollector, cArr, i11);
        }
    }

    private int getAttributes() {
        int i = this.header;
        int readUnsignedShort = i + 8 + (readUnsignedShort(i + 6) * 2);
        for (int readUnsignedShort2 = readUnsignedShort(readUnsignedShort); readUnsignedShort2 > 0; readUnsignedShort2--) {
            for (int readUnsignedShort3 = readUnsignedShort(readUnsignedShort + 8); readUnsignedShort3 > 0; readUnsignedShort3--) {
                readUnsignedShort += readInt(readUnsignedShort + 12) + 6;
            }
            readUnsignedShort += 8;
        }
        int i2 = readUnsignedShort + 2;
        for (int readUnsignedShort4 = readUnsignedShort(i2); readUnsignedShort4 > 0; readUnsignedShort4--) {
            for (int readUnsignedShort5 = readUnsignedShort(i2 + 8); readUnsignedShort5 > 0; readUnsignedShort5--) {
                i2 += readInt(i2 + 12) + 6;
            }
            i2 += 8;
        }
        return i2 + 2;
    }

    private int readMethod(TypeCollector typeCollector, char[] cArr, int i) {
        int readUnsignedShort = readUnsignedShort(i);
        String readUTF8 = readUTF8(i + 2, cArr);
        String readUTF82 = readUTF8(i + 4, cArr);
        int i2 = i + 8;
        int i3 = 0;
        int i4 = 0;
        for (int readUnsignedShort2 = readUnsignedShort(i + 6); readUnsignedShort2 > 0; readUnsignedShort2--) {
            String readUTF83 = readUTF8(i2, cArr);
            int readInt = readInt(i2 + 2);
            int i5 = i2 + 6;
            if (readUTF83.equals("Code")) {
                i4 = i5;
            }
            i2 = i5 + readInt;
        }
        MethodCollector visitMethod = typeCollector.visitMethod(readUnsignedShort, readUTF8, readUTF82);
        if (!(visitMethod == null || i4 == 0)) {
            int readInt2 = i4 + 8 + readInt(i4 + 4);
            int i6 = readInt2 + 2;
            for (int readUnsignedShort3 = readUnsignedShort(readInt2); readUnsignedShort3 > 0; readUnsignedShort3--) {
                i6 += 8;
            }
            int i7 = i6 + 2;
            int i8 = 0;
            for (int readUnsignedShort4 = readUnsignedShort(i6); readUnsignedShort4 > 0; readUnsignedShort4--) {
                String readUTF84 = readUTF8(i7, cArr);
                if (readUTF84.equals("LocalVariableTable")) {
                    i3 = i7 + 6;
                } else if (readUTF84.equals("LocalVariableTypeTable")) {
                    i8 = i7 + 6;
                }
                i7 += readInt(i7 + 2) + 6;
            }
            if (i3 != 0) {
                if (i8 != 0) {
                    int readUnsignedShort5 = readUnsignedShort(i8) * 3;
                    int i9 = i8 + 2;
                    int[] iArr = new int[readUnsignedShort5];
                    while (readUnsignedShort5 > 0) {
                        int i10 = readUnsignedShort5 - 1;
                        iArr[i10] = i9 + 6;
                        int i11 = i10 - 1;
                        iArr[i11] = readUnsignedShort(i9 + 8);
                        readUnsignedShort5 = i11 - 1;
                        iArr[readUnsignedShort5] = readUnsignedShort(i9);
                        i9 += 10;
                    }
                }
                int i12 = i3 + 2;
                for (int readUnsignedShort6 = readUnsignedShort(i3); readUnsignedShort6 > 0; readUnsignedShort6--) {
                    visitMethod.visitLocalVariable(readUTF8(i12 + 4, cArr), readUnsignedShort(i12 + 8));
                    i12 += 10;
                }
            }
        }
        return i2;
    }

    private int readUnsignedShort(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    private int readInt(int i) {
        byte[] bArr = this.b;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    private String readUTF8(int i, char[] cArr) {
        int readUnsignedShort = readUnsignedShort(i);
        String[] strArr = this.strings;
        String str = strArr[readUnsignedShort];
        if (str != null) {
            return str;
        }
        int i2 = this.items[readUnsignedShort];
        String readUTF = readUTF(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[readUnsignedShort] = readUTF;
        return readUTF;
    }

    private String readUTF(int i, int i2, char[] cArr) {
        int i3 = i2 + i;
        byte[] bArr = this.b;
        int i4 = 0;
        char c = 0;
        char c2 = 0;
        while (i < i3) {
            i++;
            byte b = bArr[i];
            switch (c) {
                case 0:
                    int i5 = b & 255;
                    if (i5 >= 128) {
                        if (i5 < 224 && i5 > 191) {
                            c2 = (char) (i5 & 31);
                            c = 1;
                            break;
                        } else {
                            c2 = (char) (i5 & 15);
                            c = 2;
                            break;
                        }
                    } else {
                        i4++;
                        cArr[i4] = (char) i5;
                        break;
                    }
                    break;
                case 1:
                    i4++;
                    cArr[i4] = (char) ((b & Utf8.REPLACEMENT_BYTE) | (c2 << 6));
                    c = 0;
                    break;
                case 2:
                    c2 = (char) ((b & Utf8.REPLACEMENT_BYTE) | (c2 << 6));
                    c = 1;
                    break;
            }
        }
        return new String(cArr, 0, i4);
    }
}
