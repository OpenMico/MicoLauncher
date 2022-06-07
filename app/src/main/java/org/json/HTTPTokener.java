package org.json;

/* loaded from: classes3.dex */
public class HTTPTokener extends JSONTokener {
    public HTTPTokener(String str) {
        super(str);
    }

    public String nextToken() throws JSONException {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == '\"' || next == '\'') {
            while (true) {
                char next2 = next();
                if (next2 < ' ') {
                    throw syntaxError("Unterminated string.");
                } else if (next2 == next) {
                    return stringBuffer.toString();
                } else {
                    stringBuffer.append(next2);
                }
            }
        } else {
            while (next != 0 && !Character.isWhitespace(next)) {
                stringBuffer.append(next);
                next = next();
            }
            return stringBuffer.toString();
        }
    }
}
