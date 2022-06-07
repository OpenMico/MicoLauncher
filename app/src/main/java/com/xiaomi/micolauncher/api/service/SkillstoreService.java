package com.xiaomi.micolauncher.api.service;

import com.xiaomi.micolauncher.api.MinaResponse;
import com.xiaomi.micolauncher.api.converter.handler.ResponseHandler;
import com.xiaomi.micolauncher.api.model.SkillStore;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes3.dex */
public interface SkillstoreService {
    public static final String BASE_URL = "https://skillstore.mina.mi.com/";
    public static final String PREVIEW_URL = "http://preview.skillstore.mina.mi.com/";
    public static final String TW_BASE_URL = "https://tw.skillstore.mina.mi.com/";
    public static final String TW_PREVIEW_URL = "http://preview.tw.skillstore.mina.mi.com/";

    @ResponseHandler
    @GET("/front_page/category_skills")
    Observable<List<SkillStore.SkillV2>> getCategorySkills(@Query("clientType") String str, @Query("page") int i, @Query("limit") int i2, @Query("categoryId") String str2);

    @ResponseHandler
    @GET("/skill/detail")
    Observable<SkillStore.SkillDetailV2> getSkillDetail(@Query("clientType") String str, @Query("skillId") String str2);

    @ResponseHandler
    @GET("/front_page/category_list")
    Observable<List<SkillStore.RankingType>> getSkillTypeList(@Query("clientType") String str);

    @GET("/skill/detail_by_skillname")
    Observable<Response<MinaResponse<SkillStore.SkillDetailV2>>> skillDetailBySkillName(@Query("skillName") String str);
}
