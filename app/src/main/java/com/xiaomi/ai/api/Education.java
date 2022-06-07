package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Education {

    /* loaded from: classes3.dex */
    public enum EduVideoVipType {
        UNKNOWN(0),
        PRIMARY(1),
        JUNIOR(2),
        HIGH(3);
        
        private int id;

        EduVideoVipType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SearchAccuracy {
        FAIL(-1),
        ACCURATE(0),
        FUZZY(1),
        FALLDOWN(2),
        RECOMMEND(3);
        
        private int id;

        SearchAccuracy(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "EduCurrentPageState", namespace = AIApiConstants.Education.NAME)
    /* loaded from: classes3.dex */
    public static class EduCurrentPageState implements ContextPayload {
        @Required
        private Video.PageType page_type;
        private Optional<List<EduSearchTag>> tags = Optional.empty();

        public EduCurrentPageState() {
        }

        public EduCurrentPageState(Video.PageType pageType) {
            this.page_type = pageType;
        }

        @Required
        public EduCurrentPageState setPageType(Video.PageType pageType) {
            this.page_type = pageType;
            return this;
        }

        @Required
        public Video.PageType getPageType() {
            return this.page_type;
        }

        public EduCurrentPageState setTags(List<EduSearchTag> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchTag>> getTags() {
            return this.tags;
        }
    }

    /* loaded from: classes3.dex */
    public static class EduSearchChildTag {
        @Required
        private boolean selected;
        private Optional<List<EduSearchSlot>> slots = Optional.empty();
        private Optional<List<EduSearchGrandTag>> tags = Optional.empty();
        @Required
        private String text;

        public EduSearchChildTag() {
        }

        public EduSearchChildTag(String str, boolean z) {
            this.text = str;
            this.selected = z;
        }

        @Required
        public EduSearchChildTag setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public EduSearchChildTag setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        @Required
        public boolean isSelected() {
            return this.selected;
        }

        public EduSearchChildTag setSlots(List<EduSearchSlot> list) {
            this.slots = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchSlot>> getSlots() {
            return this.slots;
        }

        public EduSearchChildTag setTags(List<EduSearchGrandTag> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchGrandTag>> getTags() {
            return this.tags;
        }
    }

    /* loaded from: classes3.dex */
    public static class EduSearchGrandTag {
        @Required
        private boolean selected;
        private Optional<List<EduSearchSlot>> slots = Optional.empty();
        @Required
        private String text;

        public EduSearchGrandTag() {
        }

        public EduSearchGrandTag(String str, boolean z) {
            this.text = str;
            this.selected = z;
        }

        @Required
        public EduSearchGrandTag setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public EduSearchGrandTag setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        @Required
        public boolean isSelected() {
            return this.selected;
        }

        public EduSearchGrandTag setSlots(List<EduSearchSlot> list) {
            this.slots = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchSlot>> getSlots() {
            return this.slots;
        }
    }

    /* loaded from: classes3.dex */
    public static class EduSearchSlot {
        @Required
        private String attribute;
        @Required
        private String value;

        public EduSearchSlot() {
        }

        public EduSearchSlot(String str, String str2) {
            this.attribute = str;
            this.value = str2;
        }

        @Required
        public EduSearchSlot setAttribute(String str) {
            this.attribute = str;
            return this;
        }

        @Required
        public String getAttribute() {
            return this.attribute;
        }

        @Required
        public EduSearchSlot setValue(String str) {
            this.value = str;
            return this;
        }

        @Required
        public String getValue() {
            return this.value;
        }
    }

    /* loaded from: classes3.dex */
    public static class EduSearchTag {
        @Required
        private boolean selected;
        private Optional<List<EduSearchSlot>> slots = Optional.empty();
        private Optional<List<EduSearchChildTag>> tags = Optional.empty();
        @Required
        private String text;

        public EduSearchTag() {
        }

        public EduSearchTag(String str, boolean z) {
            this.text = str;
            this.selected = z;
        }

        @Required
        public EduSearchTag setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public EduSearchTag setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        @Required
        public boolean isSelected() {
            return this.selected;
        }

        public EduSearchTag setSlots(List<EduSearchSlot> list) {
            this.slots = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchSlot>> getSlots() {
            return this.slots;
        }

        public EduSearchTag setTags(List<EduSearchChildTag> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchChildTag>> getTags() {
            return this.tags;
        }
    }

    @NamespaceName(name = "EduSearchTagsReset", namespace = AIApiConstants.Education.NAME)
    /* loaded from: classes3.dex */
    public static class EduSearchTagsReset implements EventPayload {
        @Required
        private List<EduSearchTag> tags;

        public EduSearchTagsReset() {
        }

        public EduSearchTagsReset(List<EduSearchTag> list) {
            this.tags = list;
        }

        @Required
        public EduSearchTagsReset setTags(List<EduSearchTag> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<EduSearchTag> getTags() {
            return this.tags;
        }
    }

    @NamespaceName(name = "EduShowSearchPage", namespace = AIApiConstants.Education.NAME)
    /* loaded from: classes3.dex */
    public static class EduShowSearchPage implements InstructionPayload {
        @Required
        private List<Video.VideoSearchItem> items;
        private Optional<List<EduSearchTag>> tags = Optional.empty();
        private Optional<Integer> total_size = Optional.empty();
        private Optional<SearchAccuracy> accuracy = Optional.empty();
        private Optional<Common.LoadmoreInfo> loadmore_info = Optional.empty();
        private Optional<Common.PageLoadType> load_type = Optional.empty();
        private Optional<List<EduVideoSearchItem>> items_more_info = Optional.empty();
        private Optional<List<Template.JumpItem>> jump_items = Optional.empty();
        private Optional<Template.Image> skill_icon = Optional.empty();

        public EduShowSearchPage() {
        }

        public EduShowSearchPage(List<Video.VideoSearchItem> list) {
            this.items = list;
        }

        @Required
        public EduShowSearchPage setItems(List<Video.VideoSearchItem> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<Video.VideoSearchItem> getItems() {
            return this.items;
        }

        public EduShowSearchPage setTags(List<EduSearchTag> list) {
            this.tags = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduSearchTag>> getTags() {
            return this.tags;
        }

        public EduShowSearchPage setTotalSize(int i) {
            this.total_size = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getTotalSize() {
            return this.total_size;
        }

        public EduShowSearchPage setAccuracy(SearchAccuracy searchAccuracy) {
            this.accuracy = Optional.ofNullable(searchAccuracy);
            return this;
        }

        public Optional<SearchAccuracy> getAccuracy() {
            return this.accuracy;
        }

        public EduShowSearchPage setLoadmoreInfo(Common.LoadmoreInfo loadmoreInfo) {
            this.loadmore_info = Optional.ofNullable(loadmoreInfo);
            return this;
        }

        public Optional<Common.LoadmoreInfo> getLoadmoreInfo() {
            return this.loadmore_info;
        }

        public EduShowSearchPage setLoadType(Common.PageLoadType pageLoadType) {
            this.load_type = Optional.ofNullable(pageLoadType);
            return this;
        }

        public Optional<Common.PageLoadType> getLoadType() {
            return this.load_type;
        }

        public EduShowSearchPage setItemsMoreInfo(List<EduVideoSearchItem> list) {
            this.items_more_info = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<EduVideoSearchItem>> getItemsMoreInfo() {
            return this.items_more_info;
        }

        public EduShowSearchPage setJumpItems(List<Template.JumpItem> list) {
            this.jump_items = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Template.JumpItem>> getJumpItems() {
            return this.jump_items;
        }

        public EduShowSearchPage setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    /* loaded from: classes3.dex */
    public static class EduVideoSearchItem {
        @Required
        private int index;
        private Optional<EduVideoVipType> vip_type = Optional.empty();

        public EduVideoSearchItem() {
        }

        public EduVideoSearchItem(int i) {
            this.index = i;
        }

        @Required
        public EduVideoSearchItem setIndex(int i) {
            this.index = i;
            return this;
        }

        @Required
        public int getIndex() {
            return this.index;
        }

        public EduVideoSearchItem setVipType(EduVideoVipType eduVideoVipType) {
            this.vip_type = Optional.ofNullable(eduVideoVipType);
            return this;
        }

        public Optional<EduVideoVipType> getVipType() {
            return this.vip_type;
        }
    }
}
