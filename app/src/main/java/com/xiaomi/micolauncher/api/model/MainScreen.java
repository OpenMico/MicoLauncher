package com.xiaomi.micolauncher.api.model;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class MainScreen {

    /* loaded from: classes3.dex */
    public static class InstructionDetail implements Serializable {
        private static final long serialVersionUID = -7997992123389944101L;
        public String feature;
        public List<InstructionPage> pages;
    }

    /* loaded from: classes3.dex */
    public class InstructionPage implements Serializable {
        private static final long serialVersionUID = -7315248150468365919L;
        public String pageType;
        @SerializedName("pageStatus")
        public List<InstructionPageStatus> status;

        public InstructionPage() {
            MainScreen.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class InstructionPageStatus implements Serializable {
        private static final long serialVersionUID = 5588216825600591282L;
        public List<InstructionItem> details;
        public String status;

        public InstructionPageStatus() {
            MainScreen.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class InstructionItem implements Serializable {
        private static final long serialVersionUID = -5181667191121799667L;
        public long endTime;
        public String instructionDetailId;
        public int priority;
        public long startTime;
        public String text;
        public String trackKey;

        public InstructionItem() {
            MainScreen.this = r1;
        }
    }

    @JsonAdapter(RecommendPageJsonAdapter.class)
    /* loaded from: classes3.dex */
    public class RecommendPage {
        public static final String CATEGORY_MEDIA = "Media";
        public static final String CATEGORY_SKILL = "Skill";
        public static final String MUSIC_PAGE = "MusicPage";
        public static final String SKILL_PAGE = "SkillPage";
        public static final String VIDEO_PAGE = "VideoPage";
        public String action;
        public BackgroundImage backgroundImage;
        public int interval;
        public String pageId;
        public String subTitle1;
        public String subTitle2;
        public String tag;
        public List<String> tips;
        public String title;
        public String trackKey;
        public String type;

        public RecommendPage() {
            MainScreen.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class BackgroundImage {
        public String large;
        public String middle;
        public String small;

        public BackgroundImage() {
            MainScreen.this = r1;
        }
    }

    /* loaded from: classes3.dex */
    public class MusicPage extends RecommendPage {
        public Extend extend;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MusicPage() {
            super();
            MainScreen.this = r1;
        }

        /* loaded from: classes3.dex */
        class Extend {
            public String subType;

            Extend() {
                MusicPage.this = r1;
            }
        }
    }

    /* loaded from: classes3.dex */
    public class VideoPage extends RecommendPage {
        public Extend extend;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public VideoPage() {
            super();
            MainScreen.this = r1;
        }

        /* loaded from: classes3.dex */
        class Extend {
            public String videoUrl;

            Extend() {
                VideoPage.this = r1;
            }
        }
    }

    /* loaded from: classes3.dex */
    public class SkillPage extends RecommendPage {
        public Extend extend;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SkillPage() {
            super();
            MainScreen.this = r1;
        }

        /* loaded from: classes3.dex */
        public class Extend {
            public String skillIcon;

            public Extend() {
                SkillPage.this = r1;
            }
        }
    }

    /* loaded from: classes3.dex */
    public class RecommendPageJsonAdapter extends TypeAdapter<RecommendPage> {
        public void write(JsonWriter jsonWriter, RecommendPage recommendPage) throws IOException {
        }

        public RecommendPageJsonAdapter() {
            MainScreen.this = r1;
        }

        @Override // com.google.gson.TypeAdapter
        public RecommendPage read(JsonReader jsonReader) throws IOException {
            char c;
            JsonElement parse = Streams.parse(jsonReader);
            String asString = parse.getAsJsonObject().get("type").getAsString();
            int hashCode = asString.hashCode();
            if (hashCode == -1900983756) {
                if (asString.equals(RecommendPage.MUSIC_PAGE)) {
                    c = 0;
                }
                c = 65535;
            } else if (hashCode != -958179958) {
                if (hashCode == -299945888 && asString.equals(RecommendPage.SKILL_PAGE)) {
                    c = 2;
                }
                c = 65535;
            } else {
                if (asString.equals(RecommendPage.VIDEO_PAGE)) {
                    c = 1;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    return (RecommendPage) Gsons.getGson().fromJson(parse, (Class<Object>) MusicPage.class);
                case 1:
                    return (RecommendPage) Gsons.getGson().fromJson(parse, (Class<Object>) VideoPage.class);
                case 2:
                    return (RecommendPage) Gsons.getGson().fromJson(parse, (Class<Object>) SkillPage.class);
                default:
                    L.api.e("RecommentPage unknown type %s", asString);
                    return null;
            }
        }
    }
}
