package org.seamless.xml;

import com.xiaomi.mipush.sdk.Constants;
import org.xml.sax.SAXParseException;

/* loaded from: classes4.dex */
public class ParserException extends Exception {
    public ParserException() {
    }

    public ParserException(String str) {
        super(str);
    }

    public ParserException(String str, Throwable th) {
        super(str, th);
    }

    public ParserException(Throwable th) {
        super(th);
    }

    public ParserException(SAXParseException sAXParseException) {
        super("(Line/Column: " + sAXParseException.getLineNumber() + Constants.COLON_SEPARATOR + sAXParseException.getColumnNumber() + ") " + sAXParseException.getMessage());
    }
}
