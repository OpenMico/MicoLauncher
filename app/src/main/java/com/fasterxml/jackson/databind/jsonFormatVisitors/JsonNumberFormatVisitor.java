package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;

/* loaded from: classes.dex */
public interface JsonNumberFormatVisitor extends JsonValueFormatVisitor {

    /* loaded from: classes.dex */
    public static class Base extends JsonValueFormatVisitor.Base implements JsonNumberFormatVisitor {
        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor
        public void numberType(JsonParser.NumberType numberType) {
        }
    }

    void numberType(JsonParser.NumberType numberType);
}
