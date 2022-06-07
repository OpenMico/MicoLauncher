package com.alibaba.fastjson.parser.deserializer;

/* loaded from: classes.dex */
public class JSONPDeserializer implements ObjectDeserializer {
    public static final JSONPDeserializer instance = new JSONPDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.alibaba.fastjson.JSONPObject, T] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r3, java.lang.reflect.Type r4, java.lang.Object r5) {
        /*
            r2 = this;
            com.alibaba.fastjson.parser.JSONLexer r4 = r3.getLexer()
            com.alibaba.fastjson.parser.JSONLexerBase r4 = (com.alibaba.fastjson.parser.JSONLexerBase) r4
            com.alibaba.fastjson.parser.SymbolTable r5 = r3.getSymbolTable()
            java.lang.String r5 = r4.scanSymbolUnQuoted(r5)
            r4.nextToken()
            int r0 = r4.token()
            r1 = 25
            if (r0 != r1) goto L_0x0048
            com.alibaba.fastjson.parser.SymbolTable r0 = r3.getSymbolTable()
            java.lang.String r0 = r4.scanSymbolUnQuoted(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            java.lang.String r5 = "."
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            r1.append(r0)
            java.lang.String r5 = r1.toString()
            r4.nextToken()
            int r0 = r4.token()
        L_0x0048:
            com.alibaba.fastjson.JSONPObject r1 = new com.alibaba.fastjson.JSONPObject
            r1.<init>(r5)
            r5 = 10
            if (r0 != r5) goto L_0x0095
            r4.nextToken()
        L_0x0054:
            java.lang.Object r5 = r3.parse()
            r1.addParameter(r5)
            int r5 = r4.token()
            r0 = 16
            if (r5 != r0) goto L_0x0067
            r4.nextToken()
            goto L_0x0054
        L_0x0067:
            r3 = 11
            if (r5 != r3) goto L_0x007a
            r4.nextToken()
            int r3 = r4.token()
            r5 = 24
            if (r3 != r5) goto L_0x0079
            r4.nextToken()
        L_0x0079:
            return r1
        L_0x007a:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "illegal jsonp : "
            r5.append(r0)
            java.lang.String r4 = r4.info()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            r3.<init>(r4)
            throw r3
        L_0x0095:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "illegal jsonp : "
            r5.append(r0)
            java.lang.String r4 = r4.info()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JSONPDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
