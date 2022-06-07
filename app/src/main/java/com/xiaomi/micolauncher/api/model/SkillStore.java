package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonWriter;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SkillStore {

    /* loaded from: classes3.dex */
    public static class Category implements Serializable {
        private static final long serialVersionUID = -516989655075738930L;
        public String action;
        public int imageID;
        public String imageURL;
        public String name;
    }

    /* loaded from: classes3.dex */
    public static class Section implements Serializable {
        private static final long serialVersionUID = 7435277533142056835L;
        public String name;
        @SerializedName("skillItems")
        public List<Skill> skills;
        public String type;
    }

    /* loaded from: classes3.dex */
    public static class SectionV2 implements Serializable {
        public static final String TYPE_FUN_SKILLS = "fun_skills";
        public static final String TYPE_LATEST_SKILLS = "latest_skills";
        public static final String TYPE_SEARCH_RECOMMEND = "search_recommend";
        public static final String TYPE_SPECIAL_TOPIC = "special_topic";
        public static final String TYPE_WEEKLY_SELECTION = "weekly_selection";
        private static final long serialVersionUID = -3493281744182290817L;
        public String action;
        public List<SkillV2> skills;
        public String title;
        public String topicIconUrl;
        public String topicId;
        public String trackKey;
    }

    @JsonAdapter(SkillDetailJsonAdapter.class)
    /* loaded from: classes3.dex */
    public static class SkillDetail implements Serializable {
        private static final long serialVersionUID = -3971999535940172858L;
        public String icon;
        public String name;
        public List<SkillDetailSection> sections;
        public float userAvgRating;
        public long userRatingCount;
    }

    /* loaded from: classes3.dex */
    public static class SkillDetailSection implements Serializable {
        public static final String TYPE_INTRO = "intro";
        public static final String TYPE_RATING = "rating";
        public static final String TYPE_TIPS = "tips";
        private static final long serialVersionUID = 5160313151220702733L;
        public String name;
        public String type;
    }

    /* loaded from: classes3.dex */
    public static class SkillDetailSectionIntro extends SkillDetailSection implements Serializable {
        private static final long serialVersionUID = -1428463383534891258L;
        public String intro;
    }

    /* loaded from: classes3.dex */
    public static class SkillDetailSectionTips extends SkillDetailSection implements Serializable {
        private static final long serialVersionUID = 3820110736168049029L;
        public List<SkillTip> tipsList;
    }

    /* loaded from: classes3.dex */
    public static class SkillDetailSectionVideo extends SkillDetailSection {
        public String intro;
    }

    /* loaded from: classes3.dex */
    public static class Stock implements Serializable {
        private static final long serialVersionUID = -8155516769121493099L;
        @SerializedName("subscriptionCode")
        public String code;
        public String dataType;
        public String name;
        public String status;
        public String subscriptionId;
        public String type;
    }

    /* loaded from: classes3.dex */
    public static class Tip implements Serializable {
        private static final long serialVersionUID = -9217887709886408022L;
        public List<String> tips;

        public String say() {
            if (this.tips.size() >= 1) {
                return this.tips.get(0);
            }
            return null;
        }

        public String knowledge() {
            if (this.tips.size() >= 2) {
                return this.tips.get(1);
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static class Skill implements Serializable {
        private static final long serialVersionUID = -1653543707717983444L;
        public String action;
        public String author;
        public String description;
        public SkillDetail detail;
        public String icon;
        public boolean isLab;
        public boolean isPending;
        @SerializedName(alternate = {"skillName", "displayName"}, value = "name")
        public String name;
        public String providerID;
        public String skillID;
        public SkillPrivacy skillPrivacy;
        public List<String> tips;
        public String type;

        public boolean needAuth() {
            return !TextUtils.isEmpty(this.providerID);
        }
    }

    /* loaded from: classes3.dex */
    public class SubCategory {
        public String categoryID;
        public List<Skill> skillItems;

        public SubCategory() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class SkillDetailJsonAdapter extends TypeAdapter<SkillDetail> {
        public void write(JsonWriter jsonWriter, SkillDetail skillDetail) throws IOException {
        }

        public SkillDetailJsonAdapter() {
            SkillStore.this = r1;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:68:0x00c7 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x00d7 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x00e7 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0078 A[SYNTHETIC] */
        @Override // com.google.gson.TypeAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.xiaomi.micolauncher.api.model.SkillStore.SkillDetail read(com.google.gson.stream.JsonReader r11) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 330
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.api.model.SkillStore.SkillDetailJsonAdapter.read(com.google.gson.stream.JsonReader):com.xiaomi.micolauncher.api.model.SkillStore$SkillDetail");
        }
    }

    /* loaded from: classes3.dex */
    public class SkillTip implements Serializable {
        private static final long serialVersionUID = -5668294282700536291L;
        @SerializedName(alternate = {SkillDetailSection.TYPE_TIPS}, value = "contents")
        public List<String> tips;
        public String title;

        public SkillTip() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public static class SkillDetailSectionRating extends SkillDetailSection implements Serializable {
        private static final long serialVersionUID = 2090084581632960754L;
        public String skillId;
        public float userAvgRating;
        public long userRatingCount;

        public SkillDetailSectionRating() {
        }

        public SkillDetailSectionRating(String str, float f, long j) {
            this.type = SkillDetailSection.TYPE_RATING;
            this.skillId = str;
            this.userAvgRating = f;
            this.userRatingCount = j;
        }

        public float getUserAvgRating() {
            return this.userAvgRating;
        }

        public long getUserRatingCount() {
            return this.userRatingCount;
        }

        public String getSkillId() {
            return this.skillId;
        }
    }

    /* loaded from: classes3.dex */
    public class RssSkills {
        public List<RssSkill> disabledSkills;
        public List<RssSkill> enabledSkills;

        public RssSkills() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class RssSkill {
        public String developerName;
        public String displayName;
        public boolean enabled;
        public List<RssSkillIcon> icons;
        public String skillId;
        public String skillName;
        public String summary;

        public RssSkill() {
            SkillStore.this = r1;
        }

        public String getIcon() {
            List<RssSkillIcon> list = this.icons;
            if (list != null) {
                RssSkillIcon rssSkillIcon = null;
                for (RssSkillIcon rssSkillIcon2 : list) {
                    if ("App".equals(rssSkillIcon2.storeType)) {
                        return rssSkillIcon2.icon;
                    }
                    if (rssSkillIcon2.storeType == null) {
                        rssSkillIcon = rssSkillIcon2;
                    }
                }
                if (rssSkillIcon != null) {
                    return rssSkillIcon.icon;
                }
            }
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public class RssSkillIcon {
        public String icon;
        public String largetIcon;
        public String storeType;

        public RssSkillIcon() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class RankingType implements Serializable {
        private static final long serialVersionUID = 5927655823899409531L;
        public String id;
        public String name;

        public RankingType() {
            SkillStore.this = r1;
        }

        public boolean equals(Object obj) {
            RankingType rankingType = (RankingType) obj;
            return this.id.equals(rankingType.id) && this.name.equals(rankingType.name);
        }
    }

    /* loaded from: classes3.dex */
    public class Ranking extends RankingType implements Serializable {
        public List<SkillV2> skills;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Ranking() {
            super();
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class SkillV2 implements Serializable {
        public String action;
        public String developerName;
        public String displayName;
        public List<SkillIcon> icons;
        public List<SkillTip> instructions;
        public float rating;
        public int ratingCount;
        public SkillIcon skillIcon;
        public String skillId;
        public String skillStatus;
        public String slogan;
        public List<String> tips;

        public SkillV2() {
            SkillStore.this = r1;
        }

        public String getIcon() {
            List<SkillIcon> list = this.icons;
            if (list == null) {
                return null;
            }
            for (SkillIcon skillIcon : list) {
                if ("App".equals(skillIcon.storeType)) {
                    return skillIcon.icon;
                }
            }
            return null;
        }

        public String getLargeIcon() {
            List<SkillIcon> list = this.icons;
            if (list == null) {
                return null;
            }
            for (SkillIcon skillIcon : list) {
                if ("App".equals(skillIcon.storeType)) {
                    return skillIcon.largetIcon;
                }
            }
            return null;
        }

        public List<String> getContents() {
            List<SkillTip> list = this.instructions;
            if (list == null || list.size() <= 0) {
                return null;
            }
            return this.instructions.get(0).tips;
        }

        public List<String> getInstructionList() {
            ArrayList arrayList = new ArrayList();
            List<SkillTip> list = this.instructions;
            if (list != null && list.size() > 0) {
                for (int i = 1; i < this.instructions.size(); i++) {
                    arrayList.addAll(this.instructions.get(i).tips);
                }
            }
            return arrayList;
        }

        public String getFirstTip() {
            List<String> list;
            List<String> list2 = this.tips;
            if (list2 != null && list2.size() > 0) {
                return this.tips.get(0);
            }
            List<SkillTip> list3 = this.instructions;
            if (list3 == null || list3.size() <= 0 || (list = this.instructions.get(0).tips) == null || list.size() <= 0) {
                return null;
            }
            return list.get(0);
        }

        public String getSecondTip() {
            List<String> list;
            List<String> list2 = this.tips;
            if (list2 != null && list2.size() > 1) {
                return this.tips.get(1);
            }
            List<SkillTip> list3 = this.instructions;
            if (list3 == null || list3.size() <= 0 || (list = this.instructions.get(0).tips) == null || list.size() <= 1) {
                return null;
            }
            return list.get(1);
        }

        public boolean isLab() {
            String str = this.skillStatus;
            return str != null && "Beta".equalsIgnoreCase(str);
        }

        public boolean isPending() {
            String str = this.skillStatus;
            return str != null && "Created".equalsIgnoreCase(str);
        }
    }

    /* loaded from: classes3.dex */
    public class SkillDetailV2 extends SkillV2 implements Serializable {
        private static final long serialVersionUID = -3971999535940172858L;
        public SkillPrivacy privacy;
        @SerializedName("accountLinkId")
        public String providerID;
        public String skillType;
        public String summary;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SkillDetailV2() {
            super();
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class SkillIcon implements Serializable {
        public String icon;
        @SerializedName("large_icon")
        public String largetIcon;
        @SerializedName("store_type")
        public String storeType;

        public SkillIcon() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class Privacy {
        @SerializedName("privacy_enabled")
        public boolean privacyEnabled;
        @SerializedName("privacy_list")
        public List<PrivacyInfo> privacyList;

        public Privacy() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class PrivacyInfo {
        public String description;
        @SerializedName(ai.s)
        public String displayName;
        @SerializedName("enum_name")
        public String enumName;

        public PrivacyInfo() {
            SkillStore.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class SkillPrivacy implements Serializable {
        @SerializedName("skill_data_privacy_list")
        public List<String> privacyList;

        public SkillPrivacy() {
            SkillStore.this = r1;
        }
    }
}
