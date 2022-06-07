package com.xiaomi.micolauncher.common.speech.utils;

import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public class WavFileWriter {
    private int a;
    private int b;
    private String c;
    private FileOutputStream d = null;

    public WavFileWriter(int i, int i2, String str) {
        this.a = i;
        this.b = i2;
        this.c = str;
    }

    private void a(FileOutputStream fileOutputStream, int i) throws IOException {
        int i2 = i + 36;
        int i3 = this.b;
        int i4 = this.a;
        int i5 = i3 * i4 * 2;
        byte b = (byte) (i & 255);
        fileOutputStream.write(new byte[]{82, 73, 70, 70, b, (byte) ((i2 >> 8) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i4, 0, (byte) (i3 & 255), (byte) ((i3 >> 8) & 255), (byte) ((i3 >> 16) & 255), (byte) ((i3 >> 24) & 255), (byte) (i5 & 255), (byte) ((i5 >> 8) & 255), (byte) ((i5 >> 16) & 255), (byte) ((i5 >> 24) & 255), 2, 0, 16, 0, 100, 97, 116, 97, b, (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)}, 0, 44);
    }

    public int writeAllData(final byte[] bArr, final int i, final int i2) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$WavFileWriter$dsL7etXbbA3ajqGSclSrZdTd7uQ
            @Override // java.lang.Runnable
            public final void run() {
                WavFileWriter.this.b(i2, bArr, i);
            }
        });
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i, byte[] bArr, int i2) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(this.c);
        } catch (FileNotFoundException e) {
            L.speech.e("%s new FileOutputStream.exception=%s", "WavFileWriter", e);
            fileOutputStream = null;
        }
        if (fileOutputStream != null && i > 0 && bArr != null) {
            try {
                a(fileOutputStream, i);
                fileOutputStream.write(bArr, i2, i);
                fileOutputStream.close();
            } catch (IOException e2) {
                L.speech.e("%s write.exception=%s", "WavFileWriter", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(final byte[] bArr, final int i, final int i2) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$WavFileWriter$28B2sOsh9GFcVdsL9RqATSPwPZY
            @Override // java.lang.Runnable
            public final void run() {
                WavFileWriter.this.a(i2, bArr, i);
            }
        });
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, byte[] bArr, int i2) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(this.c);
        } catch (FileNotFoundException e) {
            L.speech.e("%s new FileOutputStream.exception=%s", "WavFileWriter", e);
            fileOutputStream = null;
        }
        if (fileOutputStream != null && i > 0 && bArr != null) {
            try {
                fileOutputStream.write(bArr, i2, i);
                fileOutputStream.close();
            } catch (IOException e2) {
                L.speech.e("%s write.exception=%s", "WavFileWriter", e2);
            }
        }
    }

    public void open() {
        if (this.d == null) {
            try {
                this.d = new FileOutputStream(this.c);
            } catch (FileNotFoundException e) {
                L.speech.e("%s new FileOutputStream.exception=%s", "WavFileWriter", e);
            }
        }
    }

    public void writePcmPacket(byte[] bArr, int i, int i2) {
        FileOutputStream fileOutputStream = this.d;
        if (fileOutputStream != null && i2 > 0 && bArr != null) {
            try {
                fileOutputStream.write(bArr, i, i2);
            } catch (IOException e) {
                L.speech.e("%s write.exception=%s", "WavFileWriter", e);
            }
        }
    }

    public void close() {
        FileOutputStream fileOutputStream = this.d;
        if (fileOutputStream != null) {
            try {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    L.speech.e("%s close.exception=%s", "WavFileWriter", e);
                }
            } finally {
                this.d = null;
            }
        }
    }
}
