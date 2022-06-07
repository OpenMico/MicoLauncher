package org.json;

import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.Iterator;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public class XML {
    public static final Character AMP = new Character(Typography.amp);
    public static final Character APOS = new Character('\'');
    public static final Character BANG = new Character('!');
    public static final Character EQ = new Character('=');
    public static final Character GT = new Character(Typography.greater);
    public static final Character LT = new Character(Typography.less);
    public static final Character QUEST = new Character('?');
    public static final Character QUOT = new Character('\"');
    public static final Character SLASH = new Character(JsonPointer.SEPARATOR);

    public static String escape(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '\"') {
                stringBuffer.append("&quot;");
            } else if (charAt == '&') {
                stringBuffer.append("&amp;");
            } else if (charAt == '<') {
                stringBuffer.append("&lt;");
            } else if (charAt != '>') {
                stringBuffer.append(charAt);
            } else {
                stringBuffer.append("&gt;");
            }
        }
        return stringBuffer.toString();
    }

    public static void noSpace(String str) throws JSONException {
        int length = str.length();
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                if (Character.isWhitespace(str.charAt(i))) {
                    throw new JSONException(LrcRow.SINGLE_QUOTE + str + "' contains a space character.");
                }
            }
            return;
        }
        throw new JSONException("Empty string.");
    }

    private static boolean a(XMLTokener xMLTokener, JSONObject jSONObject, String str) throws JSONException {
        Object nextToken = xMLTokener.nextToken();
        int i = 1;
        if (nextToken == BANG) {
            char next = xMLTokener.next();
            if (next == '-') {
                if (xMLTokener.next() == '-') {
                    xMLTokener.skipPast("-->");
                    return false;
                }
                xMLTokener.back();
            } else if (next == '[') {
                if (!xMLTokener.nextToken().equals("CDATA") || xMLTokener.next() != '[') {
                    throw xMLTokener.syntaxError("Expected 'CDATA['");
                }
                String nextCDATA = xMLTokener.nextCDATA();
                if (nextCDATA.length() > 0) {
                    jSONObject.accumulate("content", nextCDATA);
                }
                return false;
            }
            do {
                Object nextMeta = xMLTokener.nextMeta();
                if (nextMeta == null) {
                    throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                } else if (nextMeta == LT) {
                    i++;
                    continue;
                } else if (nextMeta == GT) {
                    i--;
                    continue;
                } else {
                    continue;
                }
            } while (i > 0);
            return false;
        } else if (nextToken == QUEST) {
            xMLTokener.skipPast("?>");
            return false;
        } else if (nextToken == SLASH) {
            Object nextToken2 = xMLTokener.nextToken();
            if (str == null) {
                throw xMLTokener.syntaxError("Mismatched close tag" + nextToken2);
            } else if (!nextToken2.equals(str)) {
                throw xMLTokener.syntaxError("Mismatched " + str + " and " + nextToken2);
            } else if (xMLTokener.nextToken() == GT) {
                return true;
            } else {
                throw xMLTokener.syntaxError("Misshaped close tag");
            }
        } else if (!(nextToken instanceof Character)) {
            String str2 = (String) nextToken;
            JSONObject jSONObject2 = new JSONObject();
            Object obj = null;
            while (true) {
                if (obj == null) {
                    obj = xMLTokener.nextToken();
                }
                if (obj instanceof String) {
                    String str3 = (String) obj;
                    Object nextToken3 = xMLTokener.nextToken();
                    if (nextToken3 == EQ) {
                        Object nextToken4 = xMLTokener.nextToken();
                        if (nextToken4 instanceof String) {
                            jSONObject2.accumulate(str3, JSONObject.stringToValue((String) nextToken4));
                            obj = null;
                        } else {
                            throw xMLTokener.syntaxError("Missing value");
                        }
                    } else {
                        jSONObject2.accumulate(str3, "");
                        obj = nextToken3;
                    }
                } else if (obj == SLASH) {
                    if (xMLTokener.nextToken() == GT) {
                        jSONObject.accumulate(str2, jSONObject2);
                        return false;
                    }
                    throw xMLTokener.syntaxError("Misshaped tag");
                } else if (obj == GT) {
                    while (true) {
                        Object nextContent = xMLTokener.nextContent();
                        if (nextContent == null) {
                            if (str2 == null) {
                                return false;
                            }
                            throw xMLTokener.syntaxError("Unclosed tag " + str2);
                        } else if (nextContent instanceof String) {
                            String str4 = (String) nextContent;
                            if (str4.length() > 0) {
                                jSONObject2.accumulate("content", JSONObject.stringToValue(str4));
                            }
                        } else if (nextContent == LT && a(xMLTokener, jSONObject2, str2)) {
                            if (jSONObject2.length() == 0) {
                                jSONObject.accumulate(str2, "");
                            } else if (jSONObject2.length() != 1 || jSONObject2.opt("content") == null) {
                                jSONObject.accumulate(str2, jSONObject2);
                            } else {
                                jSONObject.accumulate(str2, jSONObject2.opt("content"));
                            }
                            return false;
                        }
                    }
                } else {
                    throw xMLTokener.syntaxError("Misshaped tag");
                }
            }
        } else {
            throw xMLTokener.syntaxError("Misshaped tag");
        }
    }

    public static JSONObject toJSONObject(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(str);
        while (xMLTokener.more() && xMLTokener.skipPast("<")) {
            a(xMLTokener, jSONObject, null);
        }
        return jSONObject;
    }

    public static String toString(Object obj) throws JSONException {
        return toString(obj, null);
    }

    public static String toString(Object obj, String str) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        if (obj instanceof JSONObject) {
            if (str != null) {
                stringBuffer.append(Typography.less);
                stringBuffer.append(str);
                stringBuffer.append(Typography.greater);
            }
            JSONObject jSONObject = (JSONObject) obj;
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String obj2 = keys.next().toString();
                Object opt = jSONObject.opt(obj2);
                if (opt == null) {
                    opt = "";
                }
                if (opt instanceof String) {
                    String str2 = (String) opt;
                }
                if (obj2.equals("content")) {
                    if (opt instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) opt;
                        int length = jSONArray.length();
                        for (int i = 0; i < length; i++) {
                            if (i > 0) {
                                stringBuffer.append('\n');
                            }
                            stringBuffer.append(escape(jSONArray.get(i).toString()));
                        }
                    } else {
                        stringBuffer.append(escape(opt.toString()));
                    }
                } else if (opt instanceof JSONArray) {
                    JSONArray jSONArray2 = (JSONArray) opt;
                    int length2 = jSONArray2.length();
                    for (int i2 = 0; i2 < length2; i2++) {
                        Object obj3 = jSONArray2.get(i2);
                        if (obj3 instanceof JSONArray) {
                            stringBuffer.append(Typography.less);
                            stringBuffer.append(obj2);
                            stringBuffer.append(Typography.greater);
                            stringBuffer.append(toString(obj3));
                            stringBuffer.append("</");
                            stringBuffer.append(obj2);
                            stringBuffer.append(Typography.greater);
                        } else {
                            stringBuffer.append(toString(obj3, obj2));
                        }
                    }
                } else if (opt.equals("")) {
                    stringBuffer.append(Typography.less);
                    stringBuffer.append(obj2);
                    stringBuffer.append("/>");
                } else {
                    stringBuffer.append(toString(opt, obj2));
                }
            }
            if (str != null) {
                stringBuffer.append("</");
                stringBuffer.append(str);
                stringBuffer.append(Typography.greater);
            }
            return stringBuffer.toString();
        } else if (obj instanceof JSONArray) {
            JSONArray jSONArray3 = (JSONArray) obj;
            int length3 = jSONArray3.length();
            for (int i3 = 0; i3 < length3; i3++) {
                stringBuffer.append(toString(jSONArray3.opt(i3), str == null ? "array" : str));
            }
            return stringBuffer.toString();
        } else {
            String escape = obj == null ? "null" : escape(obj.toString());
            if (str == null) {
                return "\"" + escape + "\"";
            } else if (escape.length() == 0) {
                return "<" + str + "/>";
            } else {
                return "<" + str + ">" + escape + "</" + str + ">";
            }
        }
    }
}
