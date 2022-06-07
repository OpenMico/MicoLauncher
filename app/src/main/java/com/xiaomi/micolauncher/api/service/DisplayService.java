package com.xiaomi.micolauncher.api.service;

import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.api.model.Music;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.HomeVideoData;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.skills.video.model.RecommendCartoon;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/* loaded from: classes3.dex */
public interface DisplayService {
    public static final String BASE_URL = "https://display.api.mina.mi.com/";
    public static final String PREVIEW_URL = "https://preview-display.mina.mi.com/";

    @ResponseHandler
    @GET("/mainpage/get_ads")
    Observable<Picture.Advertise> getAds(@Query("hardware") String str, @Query("tagId") String str2, @Query("extra") String str3);

    @ResponseHandler
    @GET("/mainpage/get_advertiser_code")
    Observable<Long> getAdvertiserCode(@Query("sn") String str, @Query("hardware") String str2);

    @GET("/appstore/apk/list")
    Observable<List<AppInfo>> getAppStoreApkList();

    @ResponseHandler
    @GET("/musicHomePage/kids/block")
    Observable<ChildStory.BlocksBean> getChildStoryBlock(@Query("blockId") long j, @Query("pageNum") int i);

    @ResponseHandler
    @GET("/musicHomePage/kids/flow")
    Observable<ChildStory> getChildStoryFlow(@Query("pageNum") int i, @Query("userLevel") int i2);

    @ResponseHandler
    @GET("/videoHomePage/child/category_content")
    Observable<List<Video.Item>> getChildVideoCategoryContent(@Query("category") String str, @Query("pageNum") int i, @Query("pageSize") int i2);

    @ResponseHandler
    @GET("/videoHomePage/child/category_list")
    Observable<List<Video.Category>> getChildVideoCategoryList();

    @ResponseHandler
    @GET("/mainpage/get_weather_dial")
    Observable<List<Picture.Dial>> getDialList(@Query("hardware") String str);

    @ResponseHandler
    @GET("/main_screen/station/tab/block")
    Observable<AudioDiscoveryPage.Flow.BlocksBean> getDiscAudioTabBlock(@Query("tabName") String str, @Query("blockId") long j);

    @ResponseHandler
    @GET("/main_screen/station/tab/flow")
    Observable<AudioDiscoveryPage.Flow> getDiscAudioTabFlow(@Query("tabName") String str);

    @ResponseHandler
    @GET("/main_screen/station/tab_list")
    Observable<AudioDiscoveryPage.TabListBean> getDiscAudioTabList();

    @ResponseHandler
    @GET("/main_screen/discover/region1")
    Observable<OprationAudiobook> getFirstRegionData(@Query("hardware") String str, @Query("startItemId") String str2, @Query("exclude") String str3);

    @ResponseHandler
    @GET("/main_screen/tab_list/v2")
    Observable<VideoTabInfo> getHomeVideoTabs();

    @ResponseHandler
    @GET("/main_screen/tab/list")
    Observable<HomeVideoData> getHomeVideos(@Query("hardware") String str, @Query("tabKey") String str2, @Query("kidMode") boolean z, @Query("pageNum") int i, @Query("pageSize") int i2, @Query("traceId") String str3);

    @ResponseHandler
    @GET("/instruction/all")
    Observable<List<MainScreen.InstructionDetail>> getInstructions();

    @ResponseHandler
    @GET("/main_screen/kid_menu")
    Observable<List<MainPage.KidMenuItem>> getKidMenu();

    @ResponseHandler
    @GET("/musicHomePage/mimusic/flow")
    Observable<PatchWall> getMiPathWallFlow(@Query("pageNum") int i, @Query("userLevel") int i2);

    @ResponseHandler
    @GET("/musicHomePage/qqmusic/kid_category")
    Observable<List<Music.Category>> getMusicCategoryList();

    @ResponseHandler
    @GET("/main_screen/tab_list")
    @Deprecated
    Observable<List<VideoTabData>> getOldHomeVideoTabs();

    @ResponseHandler
    @FormUrlEncoded
    @POST("/mainpage/get_pictorial")
    Observable<List<Picture.Pictorial>> getPictorialList(@Field("sn") String str, @Field("hardware") String str2, @Field("romVersion") String str3, @Field("romChannel") String str4, @Field("locale") String str5, @Field("contryCode") String str6, @Field("themeId") long j);

    @ResponseHandler
    @GET("/musicHomePage/qqmusic/mv_recommand")
    Observable<PatchWall.Block> getQQPatchWallMv();

    @ResponseHandler
    @GET("/musicHomePage/qqmusic/flow")
    Observable<PatchWall> getQQPathWallFlow(@Query("pageNum") int i);

    @ResponseHandler
    @GET("/musicHomePage/qqmusic/flow")
    Observable<PatchWall> getQQPathWallFlow(@QueryMap Map<String, String> map);

    @ResponseHandler
    @GET("/recommend_page/child__pages")
    Observable<List<RecommendCartoon>> getRecommendCartoonsForChildMode();

    @ResponseHandler
    @GET("/main_screen/discover/recommendation")
    Observable<List<AudiobookContent>> getRecommendData(@Query("userId") String str, @Query("category") String str2, @Query("count") int i);

    @ResponseHandler
    @GET("/main_screen/station/recommendation")
    Observable<List<Station.HomeStation>> getRecommendHomeStation();

    @ResponseHandler
    @GET("/main_screen/long_banner/recommendation")
    Observable<String> getRecommendLongBanner(@Query("hardware") String str);

    @ResponseHandler
    @GET("/main_screen/video/recommendation")
    Observable<List<MainPage.LongVideo>> getRecommendLongVideo();

    @ResponseHandler
    @GET("/main_screen/music")
    Observable<List<MainPage.Music>> getRecommendMusicList(@Query("hardware") String str);

    @ResponseHandler
    @GET("/main_screen/news")
    Observable<List<MainPage.News>> getRecommendNewsList(@Query("hardware") String str, @Query("isNeedVideo") boolean z);

    @ResponseHandler
    @GET("/main_screen/short_banner/recommendation")
    Observable<String> getRecommendShortBanner(@Query("hardware") String str);

    @ResponseHandler
    @GET("/main_screen/app/recommendation")
    Observable<List<MainPage.SkillApp>> getRecommendSkillApp();

    @ResponseHandler
    @GET("/main_screen/station")
    Observable<List<MainPage.Station>> getRecommendStationList();

    @ResponseHandler
    @GET("/main_screen/video")
    Observable<List<MainPage.Video>> getRecommendVideoList();

    @ResponseHandler
    @GET("/sound_box_image/get_screen_saver")
    Observable<List<Picture.ScreenSaver>> getScreenSaverList();

    @ResponseHandler
    @FormUrlEncoded
    @POST("/mainpage/get_pictorial_v2")
    Observable<List<Picture.Pictorial>> getScreenSaverList(@Field("hardware") String str, @Field("sn") String str2, @Field("themeId") long j, @Field("extra") String str3);

    @ResponseHandler
    @GET("/main_screen/discover/region2")
    Observable<OprationAudiobook> getSecondRegionData(@Query("hardware") String str, @Query("startItemId") String str2, @Query("exclude") String str3);

    @ResponseHandler
    @GET("/skill_recommend/skill_info")
    Observable<List<Skill.SkillInfo>> getSkillInfo(@Query("idList") String str);

    @ResponseHandler
    @GET("/skill_recommend/skill_list")
    Observable<List<Skill.SkillBean>> getSkillList();

    @ResponseHandler
    @GET("/skill_recommend/skill_category")
    Observable<Skill.SkillType> getSkillTypeById(@Query("categoryId") String str);

    @ResponseHandler
    @GET("/skill_recommend/skill_category_list")
    Observable<List<Skill.SkillType>> getSkillTypeList();

    @ResponseHandler
    @GET("/station_home_page/kid_category_detail")
    Observable<List<Station.Item>> getStationCategoryContent(@Query("categoryId") long j, @Query("pageNum") int i, @Query("pageSize") int i2);

    @ResponseHandler
    @GET("/station_home_page/kid_category")
    Observable<List<Station.Category>> getStationCategoryList();

    @ResponseHandler
    @GET("/station_home_page/recommend")
    Observable<List<Station.Item>> getStationRecommend();

    @ResponseHandler
    @GET("/videoHomePage/category_content")
    Observable<List<Video.Item>> getVideoCategoryContent(@Query("categoryId") long j, @Query("pageNum") int i, @Query("pageSize") int i2, @Query("hardware") String str);

    @ResponseHandler
    @GET("/videoHomePage/category_list")
    Observable<List<Video.Category>> getVideoCategoryList(@Query("hardware") String str, @Query("origin") String str2);

    @ResponseHandler
    @GET("/ai/file/url")
    Observable<String> getVideoFileUrl(@Query("fileID") String str);

    @GET("/kid/vip/video/duetime")
    Observable<ChildVideo.DueTime> getVipDueTime(@Query("sdkVersion") int i, @Query("biz") int i2, @Query("pcode") String str, @Query("platform") String str2);

    @ResponseHandler
    @GET("main_screen/apps")
    Observable<String> loadAppsData();

    @ResponseHandler
    @GET("/main_screen/short_video")
    Observable<List<Video.ShortVideo>> recommendShortVideo();

    @FormUrlEncoded
    @POST("/mainpage/report_device_info")
    Observable<Picture.ReportResponse> reportDeviceInfo(@Field("sn") String str, @Field("hardware") String str2, @Field("romVersion") String str3, @Field("romChannel") String str4, @Field("locale") String str5, @Field("contryCode") String str6, @Field("advertiserCode") long j, @Field("operateType") String str7);

    @ResponseHandler
    @GET("/videoHomePage/short_video/category_content")
    Observable<List<Video.ShortVideo>> shortVideoCategoryContent(@Query("category") String str, @Query("pageNum") int i, @Query("pageSize") int i2);
}
