package com.xiaomi.micolauncher.common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ThirdAppInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ApkTool {
    public static List<ThirdAppInfo> scanLocalInstallAppList(PackageManager packageManager) {
        List<PackageInfo> list;
        ArrayList arrayList = new ArrayList();
        try {
            list = packageManager.getInstalledPackages(0);
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        if (!ContainerUtil.hasData(list)) {
            return arrayList;
        }
        for (int i = 0; i < list.size(); i++) {
            PackageInfo packageInfo = list.get(i);
            if ((packageInfo.applicationInfo.flags & 1) != 1) {
                ThirdAppInfo thirdAppInfo = new ThirdAppInfo();
                if (packageInfo.applicationInfo.loadIcon(packageManager) != null) {
                    thirdAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    thirdAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                    thirdAppInfo.setPackageName(packageInfo.packageName);
                    arrayList.add(thirdAppInfo);
                }
            }
        }
        return arrayList;
    }

    public static List<String> getLocalInstallAppPackages(PackageManager packageManager) {
        List<PackageInfo> list;
        ArrayList arrayList = new ArrayList();
        try {
            list = packageManager.getInstalledPackages(0);
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        if (!ContainerUtil.hasData(list)) {
            return arrayList;
        }
        for (int i = 0; i < list.size(); i++) {
            PackageInfo packageInfo = list.get(i);
            if ((packageInfo.applicationInfo.flags & 1) != 1 && !TextUtils.isEmpty(packageInfo.packageName)) {
                arrayList.add(packageInfo.packageName);
            }
        }
        return arrayList;
    }
}
