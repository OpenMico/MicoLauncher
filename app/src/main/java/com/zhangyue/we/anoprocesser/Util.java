package com.zhangyue.we.anoprocesser;

import com.umeng.analytics.pro.ai;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes4.dex */
public class Util {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getDirName(File file) {
        return file.getParentFile().getName();
    }

    public static String getLayoutCategory(File file) {
        String name = file.getParentFile().getName();
        return ((name.hashCode() == -1109722326 && name.equals("layout")) ? (char) 0 : (char) 65535) != 0 ? name.substring(name.lastIndexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER) + 1) : "";
    }

    public static void sortLayout(ArrayList<File> arrayList) {
        if (arrayList != null && arrayList.size() > 1) {
            Collections.sort(arrayList, new Comparator<File>() { // from class: com.zhangyue.we.anoprocesser.Util.1
                /* renamed from: a */
                public int compare(File file, File file2) {
                    String layoutCategory = Util.getLayoutCategory(file);
                    String layoutCategory2 = Util.getLayoutCategory(file2);
                    if (layoutCategory == null || layoutCategory.equals("")) {
                        return 1;
                    }
                    if (layoutCategory2 == null || layoutCategory2.equals("") || layoutCategory.equals("land")) {
                        return -1;
                    }
                    if (layoutCategory2.equals("land")) {
                        return 1;
                    }
                    return Integer.parseInt(layoutCategory2.substring(layoutCategory2.indexOf(ai.aC) + 1)) - Integer.parseInt(layoutCategory.substring(layoutCategory.indexOf(ai.aC) + 1));
                }
            });
        }
    }
}
