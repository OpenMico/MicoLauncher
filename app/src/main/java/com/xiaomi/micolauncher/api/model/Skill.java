package com.xiaomi.micolauncher.api.model;

import com.xiaomi.mico.common.ContainerUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Skill {

    /* loaded from: classes3.dex */
    public static class SkillBean {
        public String action;
        public String id;
        public String imageUrl;
        public String name;
        public String[][] tipList;
    }

    /* loaded from: classes3.dex */
    public static class SkillType {
        public String action;
        public String id;
        public String imageUrl;
        public String name;
    }

    /* loaded from: classes3.dex */
    public static class SkillInfo {
        public String action;
        public String bgImageUrl;
        public String id;
        public String name;
        public String[][] tipList;

        public boolean isValid() {
            return ContainerUtil.hasData(this.id) && ContainerUtil.hasData(this.name);
        }

        public List<String> getTips() {
            ArrayList arrayList = new ArrayList();
            int length = this.tipList.length;
            boolean[][] zArr = new boolean[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int length2 = this.tipList[i2].length;
                zArr[i2] = new boolean[length2];
                if (length2 > i) {
                    i = length2;
                }
            }
            for (int i3 = 0; i3 < i; i3++) {
                for (int i4 = 0; i4 < length; i4++) {
                    int randomIndex = getRandomIndex(zArr[i4]);
                    if (randomIndex >= 0 && randomIndex < zArr[i4].length) {
                        zArr[i4][randomIndex] = true;
                        arrayList.add(this.tipList[i4][randomIndex]);
                    }
                }
            }
            return arrayList;
        }

        private int getRandomIndex(boolean[] zArr) {
            long currentTimeMillis = System.currentTimeMillis();
            int length = zArr.length;
            int i = (int) (currentTimeMillis % length);
            if (!zArr[i]) {
                return i;
            }
            for (int i2 = 0; i2 < length; i2++) {
                if (!zArr[i2]) {
                    return i2;
                }
            }
            return -1;
        }
    }

    /* loaded from: classes3.dex */
    public static class SkillPlaceHolder {
        public int imageResId;

        public SkillPlaceHolder(int i) {
            this.imageResId = i;
        }
    }
}
