package com.xiaomi.micolauncher.module.homepage;

import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes3.dex */
public class AppHelpUtils {
    public static List<AppInfo> getAllAppsExcludeKeys(List<Long> list) {
        List<AppInfo> allAppInfos = SkillDataManager.getManager().getAllAppInfos();
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        if (ContainerUtil.hasData(list)) {
            for (Long l : list) {
                hashSet.add(SkillDataManager.getManager().getAppInfoByAppKey(l.longValue()));
            }
        }
        for (AppInfo appInfo : allAppInfos) {
            if (!hashSet.contains(appInfo)) {
                arrayList.add(appInfo);
            }
        }
        return arrayList;
    }
}
