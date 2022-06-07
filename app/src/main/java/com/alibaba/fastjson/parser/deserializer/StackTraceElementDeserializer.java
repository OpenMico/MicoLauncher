package com.alibaba.fastjson.parser.deserializer;

/* loaded from: classes.dex */
public class StackTraceElementDeserializer implements ObjectDeserializer {
    public static final StackTraceElementDeserializer instance = new StackTraceElementDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x01dc, code lost:
        return (T) new java.lang.StackTraceElement(r2, r4, r6, r5);
     */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r12, java.lang.reflect.Type r13, java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 508
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
