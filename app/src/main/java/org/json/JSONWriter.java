package org.json;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes3.dex */
public class JSONWriter {
    protected Writer writer;
    private boolean a = false;
    protected char mode = 'i';
    private JSONObject[] b = new JSONObject[20];
    private int c = 0;

    public JSONWriter(Writer writer) {
        this.writer = writer;
    }

    private JSONWriter a(String str) throws JSONException {
        if (str != null) {
            char c = this.mode;
            if (c == 'o' || c == 'a') {
                try {
                    if (this.a && this.mode == 'a') {
                        this.writer.write(44);
                    }
                    this.writer.write(str);
                    if (this.mode == 'o') {
                        this.mode = 'k';
                    }
                    this.a = true;
                    return this;
                } catch (IOException e) {
                    throw new JSONException(e);
                }
            } else {
                throw new JSONException("Value out of sequence.");
            }
        } else {
            throw new JSONException("Null pointer");
        }
    }

    public JSONWriter array() throws JSONException {
        char c = this.mode;
        if (c == 'i' || c == 'o' || c == 'a') {
            a((JSONObject) null);
            a("[");
            this.a = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    private JSONWriter a(char c, char c2) throws JSONException {
        if (this.mode != c) {
            throw new JSONException(c == 'o' ? "Misplaced endObject." : "Misplaced endArray.");
        }
        a(c);
        try {
            this.writer.write(c2);
            this.a = true;
            return this;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONWriter endArray() throws JSONException {
        return a('a', ']');
    }

    public JSONWriter endObject() throws JSONException {
        return a('k', '}');
    }

    public JSONWriter key(String str) throws JSONException {
        if (str == null) {
            throw new JSONException("Null key.");
        } else if (this.mode == 'k') {
            try {
                if (this.a) {
                    this.writer.write(44);
                }
                this.b[this.c - 1].putOnce(str, Boolean.TRUE);
                this.writer.write(JSONObject.quote(str));
                this.writer.write(58);
                this.a = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        } else {
            throw new JSONException("Misplaced key.");
        }
    }

    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        char c = this.mode;
        if (c == 'o' || c == 'a') {
            a("{");
            a(new JSONObject());
            this.a = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    private void a(char c) throws JSONException {
        int i = this.c;
        if (i > 0) {
            char c2 = 'a';
            if ((this.b[i + (-1)] == null ? 'a' : 'k') == c) {
                this.c--;
                int i2 = this.c;
                if (i2 == 0) {
                    c2 = 'd';
                } else if (this.b[i2 - 1] != null) {
                    c2 = 'k';
                }
                this.mode = c2;
                return;
            }
            throw new JSONException("Nesting error.");
        }
        throw new JSONException("Nesting error.");
    }

    private void a(JSONObject jSONObject) throws JSONException {
        int i = this.c;
        if (i < 20) {
            this.b[i] = jSONObject;
            this.mode = jSONObject == null ? 'a' : 'k';
            this.c++;
            return;
        }
        throw new JSONException("Nesting too deep.");
    }

    public JSONWriter value(boolean z) throws JSONException {
        return a(z ? "true" : "false");
    }

    public JSONWriter value(double d) throws JSONException {
        return value(new Double(d));
    }

    public JSONWriter value(long j) throws JSONException {
        return a(Long.toString(j));
    }

    public JSONWriter value(Object obj) throws JSONException {
        return a(JSONObject.b(obj));
    }
}
