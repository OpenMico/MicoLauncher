package com.xiaomi.micolauncher.module.homepage.fragment;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.SkillPath;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillPatchWallFragment extends BaseSkillFragment {
    public static final String TAG = "SkillPatchWallFragment";

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseSkillFragment, com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(final Context context) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$SkillPatchWallFragment$YOwy9rnXHh-vPQ7M_oQGSAlBIH8
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                SkillPatchWallFragment.this.a(context, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, ObservableEmitter observableEmitter) throws Exception {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.third_app_data)));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append("\n");
            }
            List list = (List) Gsons.getGson().fromJson(sb.toString(), new TypeToken<List<SkillApp>>() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.SkillPatchWallFragment.1
            }.getType());
            if (ContainerUtil.isEmpty(list)) {
                bufferedReader.close();
                return;
            }
            ArrayList arrayList = new ArrayList();
            int ceil = (int) Math.ceil(list.size() / 6.0f);
            int i = 0;
            for (int i2 = 0; i2 < ceil; i2++) {
                ArrayList arrayList2 = new ArrayList();
                while (i < list.size()) {
                    SkillApp skillApp = (SkillApp) list.get(i);
                    skillApp.getResId(context.getResources());
                    arrayList2.add(skillApp);
                    i++;
                    if (i % 6.0f == 0.0f) {
                        break;
                    }
                }
                arrayList.add(arrayList2);
            }
            SkillDataManager.getManager().setThirdApps(arrayList);
            bufferedReader.close();
        } catch (IOException e) {
            L.homepage.e("load skill third app error : ", e);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseSkillFragment
    public List<Object> buildAppData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SkillApp(R.drawable.skill_mijia, SkillPath.MIOT, false, "mijia"));
        arrayList.add(new SkillApp(R.drawable.skill_phone, SkillPath.CONTACTS, true, "call"));
        arrayList.add(new SkillApp(R.drawable.skill_setting, SkillPath.SETUP, false, SkillStatHelper.SKILL_STAT_SETUP));
        arrayList.add(new SkillApp(R.drawable.skill_schedule, SkillPath.ALARM, false, "alarm"));
        arrayList.add(new SkillApp(R.drawable.skill_aiqiyi, SkillPath.VIDEO, true, "video", true));
        arrayList.add(new SkillApp(R.drawable.skill_tencent, SkillPath.TENCENT, true, "tencent", true));
        arrayList.add(new SkillApp(R.drawable.skill_youku, SkillPath.YOUKU, true, "youku", true));
        arrayList.add(new SkillApp(R.drawable.skill_more, "", false, SkillStatHelper.SKILL_STAT_MORE));
        return arrayList;
    }
}
