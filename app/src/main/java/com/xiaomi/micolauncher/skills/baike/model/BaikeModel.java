package com.xiaomi.micolauncher.skills.baike.model;

/* loaded from: classes3.dex */
public class BaikeModel {
    public static final String KEY_HIDE_RETURN_BUTTON = "KEY_HIDE_RETURN_BUTTON";
    public static final String KEY_HIDE_TITLE = "KEY_HIDE_TITLE";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";
    public static final String KEY_RESIZE_VIDEO = "KEY_RESIZE_VIDEO";
    public static final String KEY_TEXT_CONTENT = "KEY_TEXT_CONTENT";
    public static final String KEY_TEXT_TITLE = "KEY_TEXT_TITLE";
    public static final String KEY_TO_SPEAK = "KEY_TO_SPEAK";
    private static BaikeModel a;

    private BaikeModel() {
    }

    public static BaikeModel getInstance() {
        if (a == null) {
            synchronized (BaikeModel.class) {
                a = new BaikeModel();
            }
        }
        return a;
    }
}
