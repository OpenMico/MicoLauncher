package com.xiaomi.micolauncher.module.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.module.recommend.holder.DefaultPageVH;
import com.xiaomi.micolauncher.module.recommend.holder.MusicPageVH;
import com.xiaomi.micolauncher.module.recommend.holder.SkillPageVH;

/* loaded from: classes3.dex */
public class PageFactory {

    /* loaded from: classes3.dex */
    public interface PageVH {
        void setMainColor(int i);
    }

    public static View getPage(Context context, ViewGroup viewGroup, MainScreen.RecommendPage recommendPage) {
        char c;
        String str = recommendPage.type;
        int hashCode = str.hashCode();
        if (hashCode != -1900983756) {
            if (hashCode == -299945888 && str.equals(MainScreen.RecommendPage.SKILL_PAGE)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(MainScreen.RecommendPage.MUSIC_PAGE)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                View inflate = LayoutInflater.from(context).inflate(R.layout.recommend_page_music, viewGroup, false);
                inflate.setTag(new MusicPageVH(inflate, (MainScreen.MusicPage) recommendPage));
                return inflate;
            case 1:
                View inflate2 = LayoutInflater.from(context).inflate(R.layout.recommend_page_skill, viewGroup, false);
                inflate2.setTag(new SkillPageVH(inflate2, (MainScreen.SkillPage) recommendPage));
                return inflate2;
            default:
                View inflate3 = LayoutInflater.from(context).inflate(R.layout.recommend_page_default, viewGroup, false);
                inflate3.setTag(new DefaultPageVH(inflate3, recommendPage));
                return inflate3;
        }
    }
}
