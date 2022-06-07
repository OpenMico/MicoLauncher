package com.xiaomi.micolauncher.module.child.util;

import com.xiaomi.ai.api.Education;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;

/* loaded from: classes3.dex */
public class ChildHelper {
    public static int getVipRes(ChildVideo.MiTvType miTvType) {
        return getVipRes(miTvType.getpCode());
    }

    public static int getVipRes(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1630308248) {
            if (str.equals(ChildVideoDataManager.PCODE_ERTONG)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode == -1558845957) {
            if (str.equals(ChildVideoDataManager.PCODE_JUNIOR)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 968776174) {
            if (hashCode == 2079548212 && str.equals(ChildVideoDataManager.PCODE_HIGH)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(ChildVideoDataManager.PCODE_PRIMARY)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return R.drawable.class_vip_primary;
            case 1:
                return R.drawable.class_vip_juniorhigh;
            case 2:
                return R.drawable.class_vip_seniorhigh;
            default:
                return R.drawable.kid_vip_video;
        }
    }

    public static String parseVipType(Education.EduVideoVipType eduVideoVipType) {
        switch (eduVideoVipType) {
            case JUNIOR:
                return ChildVideoDataManager.PCODE_JUNIOR;
            case HIGH:
                return ChildVideoDataManager.PCODE_HIGH;
            case PRIMARY:
                return ChildVideoDataManager.PCODE_PRIMARY;
            default:
                return "unknown";
        }
    }

    public static ChildVideo.MiTvType parsePCode(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1630308248) {
            if (str.equals(ChildVideoDataManager.PCODE_ERTONG)) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode == -1558845957) {
            if (str.equals(ChildVideoDataManager.PCODE_JUNIOR)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 968776174) {
            if (hashCode == 2079548212 && str.equals(ChildVideoDataManager.PCODE_HIGH)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(ChildVideoDataManager.PCODE_PRIMARY)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return ChildVideo.MiTvType.COURSE_HIGH;
            case 1:
                return ChildVideo.MiTvType.COURSE_JUNIOR;
            case 2:
                return ChildVideo.MiTvType.COURSE_PRIMARY;
            default:
                return ChildVideo.MiTvType.CHILD_VIDEO;
        }
    }
}
