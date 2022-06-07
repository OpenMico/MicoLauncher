package com.xiaomi.micolauncher.common.schema.handler;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.parser.JSONLexer;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StartAiqiyiUiEvent;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.audiobooks.AudioBookActivity;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoGroupListActivity;
import com.xiaomi.micolauncher.module.childsong.ChildSongActivity;
import com.xiaomi.micolauncher.module.homepage.activity.SkillInfoActivity;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.intercom.IntercomActivity;
import com.xiaomi.micolauncher.module.localalbum.AlbumImagesManager;
import com.xiaomi.micolauncher.module.main.KidsActivity;
import com.xiaomi.micolauncher.module.music.MusicCategoryListActivity;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.micolauncher.module.music.MusicPatchWallActivity;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.module.skill.ui.SkillCategoryListActivity;
import com.xiaomi.micolauncher.module.skill.ui.SkillPatchWallActivity;
import com.xiaomi.micolauncher.module.station.StationPatchWallActivity;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.ui.VideoGroupListActivity;
import com.xiaomi.micolauncher.module.video.ui.VideoRecentListActivity;
import com.xiaomi.micolauncher.module.weather.WeatherDetailX08Activity;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.view.HourlyChimeActivity;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;

/* loaded from: classes3.dex */
public class HomepageSchemaHandler implements SchemaHandler {
    public static final String PATH_BLACKLIST = "mico://launcher/blacklist";
    public static final String PATH_INDIVIDUAL_RADIO = "mico://homepage/songbook?type=individual_radio&id=0";
    public static final String PATH_MAIN_PAGE = "mico://home/mainPage";
    private CustomDialog a;

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return SmartHomeStatHelper.PARAM_VALUE_HOME.equalsIgnoreCase(uri.getAuthority()) || "homepage".equalsIgnoreCase(uri.getAuthority()) || "launcher".equalsIgnoreCase(uri.getAuthority()) || "apphomepage".equalsIgnoreCase(uri.getAuthority());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        int i;
        String path = uri.getPath();
        L.base.d("[HomepageSchemaHandler]: process.path=%s", path);
        if (!TextUtils.isEmpty(path)) {
            char c = 65535;
            switch (path.hashCode()) {
                case -1810235731:
                    if (path.equals("/songbook")) {
                        c = 22;
                        break;
                    }
                    break;
                case -1593947235:
                    if (path.equals("/hourly_chime")) {
                        c = 15;
                        break;
                    }
                    break;
                case -1589428699:
                    if (path.equals("/station")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1497211961:
                    if (path.equals("/child_song")) {
                        c = 6;
                        break;
                    }
                    break;
                case -1466506495:
                    if (path.equals("/playlist")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1300345341:
                    if (path.equals("/audiobooks")) {
                        c = 30;
                        break;
                    }
                    break;
                case -1046090647:
                    if (path.equals("/station_page")) {
                        c = 5;
                        break;
                    }
                    break;
                case -886194034:
                    if (path.equals("/music/recently/")) {
                        c = 19;
                        break;
                    }
                    break;
                case -701637710:
                    if (path.equals("/patchwall/qqmusic/song_list")) {
                        c = 21;
                        break;
                    }
                    break;
                case -697749985:
                    if (path.equals("/video/categorylist")) {
                        c = '\n';
                        break;
                    }
                    break;
                case -535176835:
                    if (path.equals("/control/screen_off")) {
                        c = 16;
                        break;
                    }
                    break;
                case -362481015:
                    if (path.equals("/skill/skill_info")) {
                        c = '\f';
                        break;
                    }
                    break;
                case -362396039:
                    if (path.equals("/skill/skill_list")) {
                        c = '\r';
                        break;
                    }
                    break;
                case 46697244:
                    if (path.equals("/kids")) {
                        c = 29;
                        break;
                    }
                    break;
                case 46757168:
                    if (path.equals("/miot")) {
                        c = 3;
                        break;
                    }
                    break;
                case 261978217:
                    if (path.equals("/patchwall/qqmusic/category_list")) {
                        c = 20;
                        break;
                    }
                    break;
                case 514657843:
                    if (path.equals("/nlp_request")) {
                        c = 28;
                        break;
                    }
                    break;
                case 552971999:
                    if (path.equals("/ims/dial")) {
                        c = 25;
                        break;
                    }
                    break;
                case 588698155:
                    if (path.equals("/video/recently")) {
                        c = '\t';
                        break;
                    }
                    break;
                case 686812622:
                    if (path.equals("/blacklist")) {
                        c = 31;
                        break;
                    }
                    break;
                case 721859650:
                    if (path.equals("/ims/contacts")) {
                        c = 24;
                        break;
                    }
                    break;
                case 859062967:
                    if (path.equals("/received_images_album")) {
                        c = 17;
                        break;
                    }
                    break;
                case 940423447:
                    if (path.equals("/mainPage")) {
                        c = ' ';
                        break;
                    }
                    break;
                case 1192243189:
                    if (path.equals("/white_noise")) {
                        c = 27;
                        break;
                    }
                    break;
                case 1438465922:
                    if (path.equals("/alarm")) {
                        c = 14;
                        break;
                    }
                    break;
                case 1447781889:
                    if (path.equals("/video/category")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 1449833302:
                    if (path.equals("/music")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1454899118:
                    if (path.equals("/setup")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1455067010:
                    if (path.equals("/skill")) {
                        c = 11;
                        break;
                    }
                    break;
                case 1457772972:
                    if (path.equals("/video")) {
                        c = 7;
                        break;
                    }
                    break;
                case 1519386708:
                    if (path.equals("/intercom")) {
                        c = JSONLexer.EOI;
                        break;
                    }
                    break;
                case 1531147493:
                    if (path.equals("/weather")) {
                        c = 18;
                        break;
                    }
                    break;
                case 1661261509:
                    if (path.equals("/ims/history")) {
                        c = 23;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    String queryParameter = uri.getQueryParameter("id");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        PlayerApi.playSheet(context, queryParameter, LoopType.LIST_LOOP);
                        return;
                    }
                    return;
                case 1:
                    String queryParameter2 = uri.getQueryParameter("id");
                    String queryParameter3 = uri.getQueryParameter("origin");
                    String queryParameter4 = uri.getQueryParameter("type");
                    if (CommonUtils.isNumber(queryParameter4)) {
                        i = Integer.parseInt(queryParameter4);
                    } else {
                        i = AudiobookContent.convert2Type(queryParameter4);
                    }
                    PlayerApi.playStation(context, queryParameter2, queryParameter3, i);
                    return;
                case 2:
                    SettingOpenManager.openSetting(context);
                    return;
                case 3:
                    EventBusRegistry.getEventBus().post(new StartMiotUiEvent());
                    return;
                case 4:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, MusicPatchWallActivity.class));
                    return;
                case 5:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, StationPatchWallActivity.class));
                    return;
                case 6:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, ChildSongActivity.class));
                    return;
                case 7:
                    EventBusRegistry.getEventBus().post(new StartAiqiyiUiEvent());
                    return;
                case '\b':
                    if (!ChildModeManager.getManager().isChildMode() || !Hardware.isBigScreen()) {
                        VideoGroupListActivity.openVideoGroupListActivity(context, Long.parseLong(uri.getQueryParameter("id")));
                        return;
                    } else {
                        ChildVideoGroupListActivity.openChildVideoGroupListActivity(context, 1777L, true);
                        return;
                    }
                case '\t':
                    ActivityLifeCycleManager.startActivityQuietly(new Intent(context, VideoRecentListActivity.class));
                    return;
                case '\n':
                    ActivityLifeCycleManager.startActivityQuietly(new Intent(context, VideoGroupListActivity.class));
                    return;
                case 11:
                    ActivityLifeCycleManager.startActivityQuietly(new Intent(context, SkillPatchWallActivity.class));
                    return;
                case '\f':
                    SkillInfoActivity.startActivity(context, uri.getQueryParameter("id"));
                    return;
                case '\r':
                    SkillCategoryListActivity.openSkillCategoryListActivity(context, uri.getQueryParameter("id"));
                    return;
                case 14:
                    AlarmHelper.showAlarmList(context, null);
                    return;
                case 15:
                    HourlyChimeActivity.startActivity(context);
                    return;
                case 16:
                    ScreenUtil.turnScreenOff(context, true);
                    return;
                case 17:
                    AlbumImagesManager.showBlankAlbumPromptOrAlbum(context);
                    return;
                case 18:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, WeatherDetailX08Activity.class));
                    return;
                case 19:
                    PlayerApi.playRecently(context);
                    return;
                case 20:
                    MusicGroupListActivity.openMusicGroupListActivity(context, uri.getQueryParameter("groupName"));
                    return;
                case 21:
                    MusicCategoryListActivity.openMusicCategoryListActivity(context, uri.getQueryParameter("categoryId"), uri.getQueryParameter("categoryName"));
                    return;
                case 22:
                    try {
                        PlayerApi.playPatchwallBlock(context, uri.getQueryParameter("type"), Long.parseLong(uri.getQueryParameter("id")));
                        return;
                    } catch (Exception e) {
                        XLog.e(e);
                        return;
                    }
                case 23:
                    if (a()) {
                        b(context, 0);
                        return;
                    } else {
                        a(context, 0);
                        return;
                    }
                case 24:
                    if (a()) {
                        b(context, 1);
                        return;
                    } else {
                        a(context, 1);
                        return;
                    }
                case 25:
                    if (a()) {
                        b(context, 2);
                        return;
                    } else {
                        a(context, 2);
                        return;
                    }
                case 26:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, IntercomActivity.class));
                    return;
                case 27:
                    ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SpeechManager.getInstance().nlpTtsRequest("我要听白噪音");
                        }
                    });
                    return;
                case 28:
                    String queryParameter5 = uri.getQueryParameter("query");
                    if (!TextUtils.isEmpty(queryParameter5)) {
                        SpeechManager.getInstance().nlpRequest(queryParameter5);
                        return;
                    }
                    return;
                case 29:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, KidsActivity.class));
                    return;
                case 30:
                    ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, AudioBookActivity.class));
                    return;
                case 31:
                    this.a = new CustomDialog.Builder().setTitle(context.getString(R.string.black_filter)).setMessage(context.getString(R.string.remove_black)).setPositive(context.getString(R.string.known)).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$HomepageSchemaHandler$kuSiGio31Md_Kbm6uWYXj9UWYpE
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            HomepageSchemaHandler.this.a(view);
                        }
                    }).setNegative("").build();
                    this.a.show();
                    return;
                default:
                    ActivityLifeCycleManager.getInstance().gotoMainActivity(context);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        CustomDialog customDialog = this.a;
        if (customDialog != null && customDialog.isShowing()) {
            this.a.dismiss();
            this.a = null;
        }
    }

    private boolean a() {
        return SystemSetting.getHasPopUpAppUdateReminderDialogue();
    }

    private void a(final Context context, final int i) {
        new CustomDialog.Builder().setTitleResId(R.string.app_update_reminder_dialogue_title).setMessageResId(R.string.app_update_reminder_dialogue_content).setPositiveResId(R.string.app_update_reminder_dialogue_pos_button).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$HomepageSchemaHandler$Y2ra8Bxo7z0FM9XW7OhcgM4GXU0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomepageSchemaHandler.this.a(context, i, view);
            }
        }).setHasNegativeButton(false).build().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, int i, View view) {
        b(context, i);
        SystemSetting.setHasPopUpAppUdateReminderDialogue(true);
    }

    private void b(Context context, int i) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(Constants.MICO_VOIP_PKG, "com.xiaomi.micovoip.view.ImsContactsActivity"));
        intent.setAction("android.intent.action.START_MICO_VOIP_IMS_CONTACTS_ACTIVITY");
        intent.putExtra("select", i);
        ActivityLifeCycleManager.startActivityQuietly(context, intent);
    }
}
