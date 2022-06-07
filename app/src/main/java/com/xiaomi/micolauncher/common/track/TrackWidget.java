package com.xiaomi.micolauncher.common.track;

import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;

/* loaded from: classes3.dex */
public enum TrackWidget {
    VIDEO_PLAY_PAGE("video_play_page"),
    STATION_DISCOVER_PROMOTION_1("station_discover_promotion_1"),
    STATION_DISCOVER_PROMOTION_2("station_discover_promotion_2"),
    STATION_DISCOVER_RECOMMEND("station_discover_recommend"),
    STATION_DISCOVER_CATEGORY_RECOMMEND("station_discover_category_recommend"),
    STATION_DISCOVER_FUNCTION_MODULE("station_discover_function_module"),
    HOMEPAGE("homepage"),
    HOMEPAGE_V2("homepage_v2"),
    SEARCH_RESULT_PAGE("search_result_page"),
    LONGVIDEO_RECOMMEND_PAGE("longvideo_recommend_page"),
    SHORTVIDEO_RECOMMEND_PAGE("shortvideo_recommend_page"),
    KID_VIDEO_DISCOVERY("kid_video_discovery"),
    KID_VIDEO_RECOMMEND_PAGE("kid_video_recommend_page"),
    KID_VIDEO_TAB("kid_video_tab"),
    TTS(VoicePrintRegisterController.PARAMS_TTS),
    PLAY_LIST("playlist"),
    PLAY_PAGE("play_page"),
    PLAY_PAGE_MV("play_page_mv"),
    FAVORITE_GENERAL("favorite_general"),
    TRANSLATION_CARD("translation_card"),
    DEVICE_CONTROL_PAGE("device_control_page"),
    RADIO_PLAY_PAGE("radio_play_page"),
    TEACH_CLASS_DISCOVER_PAGE("teach_class_discover_page"),
    VIDEO_FLOATING_VIDEO("video_floating_video"),
    FUNC_GUIDE_WAKEUP("func_guide_wakeup"),
    FUNC_GUIDE_WAKEUP_RESOURCE("func_guide_wakeup_resource"),
    CHILD_VIDEO_VIP_POP("child_video_vip_pop"),
    CHILD_VIDEO_VIP_PAY_PAGE("child_video_vip_pay_page");
    
    private String id;

    TrackWidget(String str) {
        this.id = str;
    }

    public String getKey() {
        return this.id;
    }
}
