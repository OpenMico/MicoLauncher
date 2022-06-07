package com.fasterxml.jackson.databind.jsonFormatVisitors;

import java.util.Set;

/* loaded from: classes.dex */
public interface JsonValueFormatVisitor {

    /* loaded from: classes.dex */
    public static class Base implements JsonValueFormatVisitor {
        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor
        public void enumTypes(Set<String> set) {
        }

        @Override // com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor
        public void format(JsonValueFormat jsonValueFormat) {
        }
    }

    void enumTypes(Set<String> set);

    void format(JsonValueFormat jsonValueFormat);
}
