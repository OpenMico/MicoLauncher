package com.xiaomi.micolauncher.skills.music.controller;

import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public enum LoopType {
    SINGLE_LOOP(0, "SINGLE", R.string.loop_mode_single),
    LIST_LOOP(1, "LIST", R.string.loop_mode_list),
    ORDER(2, "SEQUENCE", R.string.loop_mode_sequence),
    SHUFFLE(3, "RANDOM", R.string.loop_mode_random),
    NONE(4, "NONE", -1);
    
    public static final String LIST = "list_loop";
    public static final String RANDOM = "random";
    public static final String SEQUENCE = "seq_loop";
    public static final String SINGLE = "single";
    private String id;
    private int index;
    private int name;

    public static int valueOfBrainType(String str) {
        if (SINGLE.equals(str)) {
            return SINGLE_LOOP.ordinal();
        }
        if (LIST.equals(str)) {
            return LIST_LOOP.ordinal();
        }
        if (RANDOM.equals(str)) {
            return SHUFFLE.ordinal();
        }
        if (SEQUENCE.equals(str)) {
            return LIST_LOOP.ordinal();
        }
        return NONE.ordinal();
    }

    LoopType(int i, String str, int i2) {
        this.id = str;
        this.index = i;
        this.name = i2;
    }

    public String getId() {
        return this.id;
    }

    public int getIndex() {
        return this.index;
    }

    public int getName() {
        return this.name;
    }

    public static LoopType valueOfType(String str) {
        if (SINGLE_LOOP.getId().equals(str)) {
            return SINGLE_LOOP;
        }
        if (LIST_LOOP.getId().equals(str)) {
            return LIST_LOOP;
        }
        if (SHUFFLE.getId().equals(str)) {
            return SHUFFLE;
        }
        if (ORDER.getId().equals(str)) {
            return ORDER;
        }
        return NONE;
    }

    public static LoopType valueOfIndex(int i) {
        if (SINGLE_LOOP.getIndex() == i) {
            return SINGLE_LOOP;
        }
        if (LIST_LOOP.getIndex() == i) {
            return LIST_LOOP;
        }
        if (SHUFFLE.getIndex() == i) {
            return SHUFFLE;
        }
        if (ORDER.getIndex() == i) {
            return ORDER;
        }
        return NONE;
    }

    public static boolean isShuffle(int i) {
        return i == SHUFFLE.ordinal();
    }

    public static boolean isListLoop(int i) {
        return i == LIST_LOOP.ordinal();
    }

    public static boolean isSingle(int i) {
        return i == SINGLE_LOOP.ordinal();
    }

    public static boolean isSequence(int i) {
        return i == ORDER.ordinal();
    }
}
