package org.eclipse.jetty.http;

/* loaded from: classes5.dex */
public interface HttpTokens {
    public static final byte CARRIAGE_RETURN = 13;
    public static final int CHUNKED_CONTENT = -2;
    public static final byte COLON = 58;
    public static final byte[] CRLF = {13, 10};
    public static final int EOF_CONTENT = -1;
    public static final byte LINE_FEED = 10;
    public static final int NO_CONTENT = 0;
    public static final int SELF_DEFINING_CONTENT = -4;
    public static final byte SEMI_COLON = 59;
    public static final byte SPACE = 32;
    public static final byte TAB = 9;
    public static final int UNKNOWN_CONTENT = -3;
}
