package com.xiaomi.miplay.mylibrary.session.utils;

import android.media.session.MediaController;
import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes4.dex */
public class MediaControllerDiffUtils {
    public static void diffExistLocked(@Nullable List<MediaController> list, List<MediaController> list2, List<MediaController> list3, List<MediaController> list4) {
        if (list != null) {
            for (MediaController mediaController : list) {
                if (!a(list2, mediaController)) {
                    list4.add(mediaController);
                }
            }
        }
        for (MediaController mediaController2 : list2) {
            if (!a(list, mediaController2)) {
                list3.add(mediaController2);
            }
        }
    }

    private static boolean a(@Nullable List<MediaController> list, MediaController mediaController) {
        if (list == null) {
            return false;
        }
        for (MediaController mediaController2 : list) {
            if (mediaController2.getSessionToken().equals(mediaController.getSessionToken())) {
                return true;
            }
        }
        return false;
    }
}
