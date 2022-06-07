package com.xiaomi.miplay.mylibrary.mirror;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class KeyComposition {
    private static final Map<Character, String> a = a();

    private KeyComposition() {
    }

    public static String decompose(char c) {
        return a.get(Character.valueOf(c));
    }

    private static String a(char c) {
        return "̀" + c;
    }

    private static String b(char c) {
        return "́" + c;
    }

    private static String c(char c) {
        return "̂" + c;
    }

    private static String d(char c) {
        return "̃" + c;
    }

    private static String e(char c) {
        return "̈" + c;
    }

    private static Map<Character, String> a() {
        HashMap hashMap = new HashMap();
        hashMap.put((char) 192, a('A'));
        hashMap.put((char) 200, a('E'));
        hashMap.put((char) 204, a('I'));
        hashMap.put((char) 210, a('O'));
        hashMap.put((char) 217, a('U'));
        hashMap.put((char) 224, a('a'));
        hashMap.put((char) 232, a('e'));
        hashMap.put((char) 236, a('i'));
        hashMap.put((char) 242, a('o'));
        hashMap.put((char) 249, a('u'));
        hashMap.put((char) 504, a('N'));
        hashMap.put((char) 505, a('n'));
        hashMap.put((char) 7808, a('W'));
        hashMap.put((char) 7809, a('w'));
        hashMap.put((char) 7922, a('Y'));
        hashMap.put((char) 7923, a('y'));
        hashMap.put((char) 193, b('A'));
        hashMap.put((char) 201, b('E'));
        hashMap.put((char) 205, b('I'));
        hashMap.put((char) 211, b('O'));
        hashMap.put((char) 218, b('U'));
        hashMap.put((char) 221, b('Y'));
        hashMap.put((char) 225, b('a'));
        hashMap.put((char) 233, b('e'));
        hashMap.put((char) 237, b('i'));
        hashMap.put((char) 243, b('o'));
        hashMap.put((char) 250, b('u'));
        hashMap.put((char) 253, b('y'));
        hashMap.put((char) 262, b('C'));
        hashMap.put((char) 263, b('c'));
        hashMap.put((char) 313, b('L'));
        hashMap.put((char) 314, b('l'));
        hashMap.put((char) 323, b('N'));
        hashMap.put((char) 324, b('n'));
        hashMap.put((char) 340, b('R'));
        hashMap.put((char) 341, b('r'));
        hashMap.put((char) 346, b('S'));
        hashMap.put((char) 347, b('s'));
        hashMap.put((char) 377, b('Z'));
        hashMap.put((char) 378, b('z'));
        hashMap.put((char) 500, b('G'));
        hashMap.put((char) 501, b('g'));
        hashMap.put((char) 7688, b((char) 199));
        hashMap.put((char) 7689, b((char) 231));
        hashMap.put((char) 7728, b('K'));
        hashMap.put((char) 7729, b('k'));
        hashMap.put((char) 7742, b('M'));
        hashMap.put((char) 7743, b('m'));
        hashMap.put((char) 7764, b('P'));
        hashMap.put((char) 7765, b('p'));
        hashMap.put((char) 7810, b('W'));
        hashMap.put((char) 7811, b('w'));
        hashMap.put((char) 194, c('A'));
        hashMap.put((char) 202, c('E'));
        hashMap.put((char) 206, c('I'));
        hashMap.put((char) 212, c('O'));
        hashMap.put((char) 219, c('U'));
        hashMap.put((char) 226, c('a'));
        hashMap.put((char) 234, c('e'));
        hashMap.put((char) 238, c('i'));
        hashMap.put((char) 244, c('o'));
        hashMap.put((char) 251, c('u'));
        hashMap.put((char) 264, c('C'));
        hashMap.put((char) 265, c('c'));
        hashMap.put((char) 284, c('G'));
        hashMap.put((char) 285, c('g'));
        hashMap.put((char) 292, c('H'));
        hashMap.put((char) 293, c('h'));
        hashMap.put((char) 308, c('J'));
        hashMap.put((char) 309, c('j'));
        hashMap.put((char) 348, c('S'));
        hashMap.put((char) 349, c('s'));
        hashMap.put((char) 372, c('W'));
        hashMap.put((char) 373, c('w'));
        hashMap.put((char) 374, c('Y'));
        hashMap.put((char) 375, c('y'));
        hashMap.put((char) 7824, c('Z'));
        hashMap.put((char) 7825, c('z'));
        hashMap.put((char) 195, d('A'));
        hashMap.put((char) 209, d('N'));
        hashMap.put((char) 213, d('O'));
        hashMap.put((char) 227, d('a'));
        hashMap.put((char) 241, d('n'));
        hashMap.put((char) 245, d('o'));
        hashMap.put((char) 296, d('I'));
        hashMap.put((char) 297, d('i'));
        hashMap.put((char) 360, d('U'));
        hashMap.put((char) 361, d('u'));
        hashMap.put((char) 7868, d('E'));
        hashMap.put((char) 7869, d('e'));
        hashMap.put((char) 7928, d('Y'));
        hashMap.put((char) 7929, d('y'));
        hashMap.put((char) 196, e('A'));
        hashMap.put((char) 203, e('E'));
        hashMap.put((char) 207, e('I'));
        hashMap.put((char) 214, e('O'));
        hashMap.put((char) 220, e('U'));
        hashMap.put((char) 228, e('a'));
        hashMap.put((char) 235, e('e'));
        hashMap.put((char) 239, e('i'));
        hashMap.put((char) 246, e('o'));
        hashMap.put((char) 252, e('u'));
        hashMap.put((char) 255, e('y'));
        hashMap.put((char) 376, e('Y'));
        hashMap.put((char) 7718, e('H'));
        hashMap.put((char) 7719, e('h'));
        hashMap.put((char) 7812, e('W'));
        hashMap.put((char) 7813, e('w'));
        hashMap.put((char) 7820, e('X'));
        hashMap.put((char) 7821, e('x'));
        hashMap.put((char) 7831, e('t'));
        return hashMap;
    }
}
