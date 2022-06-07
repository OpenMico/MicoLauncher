package com.xiaomi.phonenum.obtain;

import com.xiaomi.phonenum.http.Response;
import java.io.IOException;
import org.json.JSONException;

/* loaded from: classes4.dex */
public abstract class Parser {
    protected Parser next;

    public abstract Response parse(int i, String str) throws IOException, JSONException;

    public void setNext(Parser parser) {
        Parser parser2 = this.next;
        if (parser2 == null) {
            this.next = parser;
        } else {
            parser2.setNext(parser);
        }
    }
}
