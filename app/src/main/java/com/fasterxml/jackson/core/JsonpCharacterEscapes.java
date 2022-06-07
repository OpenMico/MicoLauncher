package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

/* loaded from: classes.dex */
public class JsonpCharacterEscapes extends CharacterEscapes {
    private static final int[] a = CharacterEscapes.standardAsciiEscapesForJSON();
    private static final SerializedString b = new SerializedString("\\u2028");
    private static final SerializedString c = new SerializedString("\\u2029");
    private static final JsonpCharacterEscapes d = new JsonpCharacterEscapes();
    private static final long serialVersionUID = 1;

    public static JsonpCharacterEscapes instance() {
        return d;
    }

    @Override // com.fasterxml.jackson.core.io.CharacterEscapes
    public SerializableString getEscapeSequence(int i) {
        switch (i) {
            case 8232:
                return b;
            case 8233:
                return c;
            default:
                return null;
        }
    }

    @Override // com.fasterxml.jackson.core.io.CharacterEscapes
    public int[] getEscapeCodesForAscii() {
        return a;
    }
}
