package com.xiaomi.micolauncher.api.service;

import com.google.gson.JsonElement;
import com.xiaomi.micolauncher.api.MinaResponse;
import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.Admin;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.Miot;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.common.speech.utils.NearByWakeupHelper;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.personalize.model.TokenResult;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface MinaService {
    @FormUrlEncoded
    @POST("/aivs3/audio/collect")
    Observable<MinaResponse<Boolean>> addCollectV3(@Field("audioId") String str, @Field("listId") String str2);

    @ResponseHandler
    @FormUrlEncoded
    @POST("miai/todolist/add_item")
    Observable<Long> addMemo(@Field("value") String str);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/music/playlist/v2/add_songs")
    Observable<Boolean> addSongs(@Field("listId") long j, @Field("songs") String str);

    @ResponseHandler
    @GET("/aivs3/audio/history")
    Observable<List<Long>> audioHistory();

    @ResponseHandler
    @FormUrlEncoded
    @POST("/aivs3/audio/info/v2")
    Observable<List<Music.Song>> audioInfo(@Field("audioIdList") String str);

    @FormUrlEncoded
    @POST("/aivs3/audio/cancel_collect")
    Observable<MinaResponse<Boolean>> delCollectV3(@Field("audioId") String str, @Field("listId") String str2);

    @ResponseHandler
    @FormUrlEncoded
    @POST("miai/todolist/delete_item")
    Observable<String> deleteMemo(@Field("itemId") long j);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/music/playlist/v2/delete_songs")
    Observable<Boolean> deleteSongs(@Field("listId") long j, @Field("songs") String str);

    @ResponseHandler
    @GET("apphomepage/station/station_list/all")
    Observable<List<Station.Item>> getAudioBooksAll(@Query("tag") String str, @Query("stationCategoryType") int i, @Query("cpList") String str2, @Query("type") String str3, @Query("pageNumber") int i2, @Query("pageSize") int i3, @Query("locale") String str4);

    @FormUrlEncoded
    @POST("/aivs3/audio/info/v2")
    Observable<MinaResponse<List<Music.AudioInfo>>> getAudioInfoV3(@Field("audioIdList") String str);

    @ResponseHandler
    @GET("apphomepage/station/category_list")
    Observable<List<Station.CategoryItem>> getAudiobookCategoryList();

    @ResponseHandler
    @GET("/music/playlist/v2/playable_songs")
    Observable<Music.Channel> getChannelInfo(@Query("listId") long j, @Query("offset") int i, @Query("count") int i2);

    @ResponseHandler
    @GET("/music/playlist/v2/lists")
    Observable<List<Music.Channel>> getChannelList();

    @ResponseHandler
    @GET("admin/v2/device_list")
    Observable<List<Admin.Mico>> getDeviceList();

    @ResponseHandler
    @GET("admin/alias")
    Observable<String> getDeviceName(@Query("deviceId") String str);

    @ResponseHandler
    @GET("device_status/")
    Observable<String> getDeviceStatus(@Query("deviceId") String str, @Query("catagory") String str2);

    @ResponseHandler
    @GET("/music/playlist/v2/display/lists")
    Observable<List<PatchWall.FavoriteSongBook>> getFavoriteSongBookList();

    @ResponseHandler
    @FormUrlEncoded
    @POST("/music/song/is_favourite")
    Observable<List<Music.Favourite>> getFavouriteStatus(@Field("originSongIdList") String str);

    @ResponseHandler
    @GET("music/like/")
    Observable<Boolean> getLikeStatus(@Query("itemType") int i, @Query("itemId") long j);

    @ResponseHandler
    @GET("miai/todolist/list")
    Observable<List<Remote.Response.Memo>> getMemoList();

    @ResponseHandler
    @GET("miot/v2/device_list")
    Observable<List<Miot.Device>> getMiotDevicesV2(@Query("deviceId") String str, @Query("offset") int i);

    @ResponseHandler
    @GET("/apphomepage/patchwall/qqmusic/song_list")
    Observable<PatchWall.Category> getMusicCategoryList(@Query("categoryId") String str, @Query("categoryName") String str2, @Query("pageNum") int i, @Query("pageSize") int i2);

    @ResponseHandler
    @GET("/apphomepage/patchwall/qqmusic/category_list")
    Observable<List<PatchWall.Group>> getMusicGroupList(@Query("groupName") String str);

    @ResponseHandler
    @GET("/music/origin_map")
    Observable<Map<String, String>> getOriginNameMap();

    @ResponseHandler
    @GET("/music/origin_map_v2")
    Observable<Map<String, ThirdParty.CpInfo>> getOriginNameMapV2();

    @ResponseHandler
    @GET("/music/playlist/v2/collections")
    Observable<List<Music.Channel>> getQQCollections();

    @ResponseHandler
    @GET("music/qq/lyric")
    Observable<String> getQQMusicLyric(@Query("originSongId") String str);

    @GET("/apphomepage/patchwall/appv2/songbook")
    Observable<Response<MinaResponse<Music.SongBook>>> getQQMusicSongs(@Query("id") String str, @Query("type") String str2);

    @ResponseHandler
    @GET("/apphomepage/patchwall/appv2/unfiltered_songbook")
    Observable<Music.SongBook> getQQMusicSongs(@Query("id") String str, @Query("type") String str2, @Query("hardware") String str3);

    @ResponseHandler
    @GET("/music/recommendation")
    Observable<List<Music.Song>> getRecommendMusic(@Query("count") int i);

    @ResponseHandler
    @GET("music/song_info")
    Observable<Music.Song> getSongInfo(@Query("songId") long j);

    @ResponseHandler
    @FormUrlEncoded
    @POST("music/song_info")
    Observable<List<Music.Song>> getSongInfo(@Field("songIdList") String str);

    @ResponseHandler
    @GET("apphomepage/station/get_collected")
    Observable<List<Station.Item>> getStationCollectedList(@Query("pageType") String str);

    @ResponseHandler
    @GET("apphomepage/station/stationHistory")
    Observable<List<Station.Item>> getStationHistory(@Query("hardware") String str, @Query("num") int i, @Query("pageType") String str2);

    @ResponseHandler
    @GET("music/station/info")
    Observable<Music.Station> getStationInfo(@Query("stationId") String str, @Query("origin") String str2, @Query("stationType") int i);

    @ResponseHandler
    @GET("music/station/info")
    Single<Music.Station> getStationInfoSingle(@Query("stationId") String str, @Query("origin") String str2, @Query("stationType") int i);

    @ResponseHandler
    @GET("/music/playlist/v2/station/is_collected")
    Observable<Boolean> getStationLikeStatus(@Query("globalId") String str);

    @ResponseHandler
    @GET("music/station/sound_list")
    Observable<Music.StationSoundList> getStationSoundList(@Query("stationId") String str, @Query("origin") String str2, @Query("category") String str3, @Query("offset") int i, @Query("count") int i2, @Query("reverse") String str4);

    @ResponseHandler
    @GET("/device/aiservice/token/get_by_user_id")
    Observable<TokenResult.TokenData> getToken(@Query("deviceId") String str);

    @ResponseHandler
    @GET("/miai/weather/current_alerts")
    Observable<List<ThirdParty.WeatherAlert>> getWeatherAlert(@Query("locationKey") String str, @Query("isGlobal") boolean z);

    @ResponseHandler
    @GET("/miai/weather/current_forecast")
    Observable<ThirdParty.WeatherCurrentForecast> getWeatherCurrentForecast(@Query("locationKey") String str, @Query("isGlobal") boolean z);

    @ResponseHandler
    @GET("/miai/weather/daily_forecast")
    Observable<JsonElement> getWeatherDailyForecast(@Query("locationKey") String str, @Query("isGlobal") boolean z, @Query("days") int i);

    @ResponseHandler
    @GET("/miai/weather/location")
    Observable<List<ThirdParty.WeatherLocation>> getWeatherLocation(@Query("latitude") double d, @Query("longitude") double d2);

    @ResponseHandler
    @GET("/workday/config/auth_by_user")
    Call<JsonElement> getWorkDayConfigAuthByUser(@Query("version") String str);

    @ResponseHandler
    @GET("iot/dcm/get_user_lan_groups")
    Observable<List<NearByWakeupHelper.LanGroup>> lanAwakeGroup();

    @ResponseHandler
    @FormUrlEncoded
    @POST("music/like/")
    Observable<String> like(@Field("itemType") int i, @Field("itemId") long j);

    @ResponseHandler
    @GET("/common_config/")
    Observable<String> loadConfig(@Query("configName") String str);

    @GET("device/station/passportlogin/sound_list")
    Observable<Music.StationSoundList> loadStationSoundList(@Query("stationId") String str, @Query("origin") String str2, @Query("category") String str3, @Query("offset") int i, @Query("count") int i2);

    @ResponseHandler
    @GET("/babycourse/device/android/next/play")
    Observable<Music.BabyCourse> nextCourse(@Query("deviceId") String str, @Query("alarmId") String str2, @Query("cpId") String str3, @Query("cp") String str4);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/tencent/oauth2/status")
    Observable<MIBrain.CPBindStatus> qqMusicAuthStatus(@Field("placeholder") String str);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/music/action_log/")
    Observable<String> reportmusicActionLog(@Field("deviceId") String str, @Field("appId") String str2, @Field("eventType") String str3, @Field("itemId") String str4, @Field("itemType") String str5, @Field("extraInfo") String str6);

    @ResponseHandler
    @FormUrlEncoded
    @POST("device_status/")
    Observable<String> setDeviceStatus(@Field("deviceId") String str, @Field("catagory") String str2, @Field("status") String str3);

    @ResponseHandler
    @FormUrlEncoded
    @POST("device_status/v2")
    Observable<String> setDeviceStatusV2(@Field("deviceId") String str, @Field("catagory") String str2, @Field("status") String str3, @Field("extra") String str4);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/action_log/")
    Observable<String> statLog(@Field("jsonPayload") String str);

    @ResponseHandler
    @FormUrlEncoded
    @POST("music/playlist/v2/station/collect")
    Observable<String> stationLike(@Field("globalId") long j);

    @ResponseHandler
    @GET("/apphomepage/station/stationSoundHistory")
    Observable<Music.StationHistory> stationSoundHistory(@Query("hardware") String str, @Query("globalId") String str2);

    @ResponseHandler
    @FormUrlEncoded
    @POST("music/playlist/v2/station/remove_collected")
    Observable<String> stationUnlike(@Field("globalId") long j);

    @ResponseHandler
    @FormUrlEncoded
    @POST("music/like/unlike")
    Observable<String> unlike(@Field("itemType") int i, @Field("itemId") long j);

    @ResponseHandler
    @FormUrlEncoded
    @POST("iot/dcm/update_awake_status")
    Observable<Boolean> updateAwakeStatus(@Field("deviceId") String str, @Field("hardware") String str2, @Field("awakeStatus") int i, @Field("resource") String str3);

    @POST("/babycourse/device/android/play/update")
    Observable<ResponseBody> updateCoursePlayState(@Query("deviceId") String str, @Query("alarmId") String str2, @Query("playCPId") String str3, @Query("playCP") String str4, @Query("playIndex") int i, @Query("playPosition") long j);

    @ResponseHandler
    @FormUrlEncoded
    @POST("miai/todolist/update_item")
    Observable<String> updateMemo(@Field("itemId") long j, @Field("value") String str);

    @ResponseHandler
    @FormUrlEncoded
    @POST("/device/miot/token/uploadAuthByUser")
    Observable<String> uploadMiotAccessToken(@Field("deviceId") String str, @Field("token") String str2, @Field("sessionId") String str3);
}
