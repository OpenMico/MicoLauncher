package com.airbnb.lottie.parser.moshi;

/* loaded from: classes.dex */
final class JsonScope {
    static final int CLOSED = 8;
    static final int DANGLING_NAME = 4;
    static final int EMPTY_ARRAY = 1;
    static final int EMPTY_DOCUMENT = 6;
    static final int EMPTY_OBJECT = 3;
    static final int NONEMPTY_ARRAY = 2;
    static final int NONEMPTY_DOCUMENT = 7;
    static final int NONEMPTY_OBJECT = 5;

    private JsonScope() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getPath(int i, int[] iArr, String[] strArr, int[] iArr2) {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        for (int i2 = 0; i2 < i; i2++) {
            switch (iArr[i2]) {
                case 1:
                case 2:
                    sb.append('[');
                    sb.append(iArr2[i2]);
                    sb.append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    sb.append('.');
                    if (strArr[i2] != null) {
                        sb.append(strArr[i2]);
                        break;
                    } else {
                        break;
                    }
            }
        }
        return sb.toString();
    }
}
