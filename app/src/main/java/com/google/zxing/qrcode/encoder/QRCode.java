package com.google.zxing.qrcode.encoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

/* loaded from: classes2.dex */
public final class QRCode {
    public static final int NUM_MASK_PATTERNS = 8;
    private Mode a;
    private ErrorCorrectionLevel b;
    private Version c;
    private int d = -1;
    private ByteMatrix e;

    public static boolean isValidMaskPattern(int i) {
        return i >= 0 && i < 8;
    }

    public Mode getMode() {
        return this.a;
    }

    public ErrorCorrectionLevel getECLevel() {
        return this.b;
    }

    public Version getVersion() {
        return this.c;
    }

    public int getMaskPattern() {
        return this.d;
    }

    public ByteMatrix getMatrix() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append("<<\n");
        sb.append(" mode: ");
        sb.append(this.a);
        sb.append("\n ecLevel: ");
        sb.append(this.b);
        sb.append("\n version: ");
        sb.append(this.c);
        sb.append("\n maskPattern: ");
        sb.append(this.d);
        if (this.e == null) {
            sb.append("\n matrix: null\n");
        } else {
            sb.append("\n matrix:\n");
            sb.append(this.e);
        }
        sb.append(">>\n");
        return sb.toString();
    }

    public void setMode(Mode mode) {
        this.a = mode;
    }

    public void setECLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.b = errorCorrectionLevel;
    }

    public void setVersion(Version version) {
        this.c = version;
    }

    public void setMaskPattern(int i) {
        this.d = i;
    }

    public void setMatrix(ByteMatrix byteMatrix) {
        this.e = byteMatrix;
    }
}
