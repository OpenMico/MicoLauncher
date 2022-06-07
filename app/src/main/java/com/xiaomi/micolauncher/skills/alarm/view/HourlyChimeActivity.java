package com.xiaomi.micolauncher.skills.alarm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.HourlyChimeEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.widget.itemdecoration.ItemDecorationHelper;
import com.xiaomi.micolauncher.skills.alarm.adapter.HourlyChimeAdapter;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeRealmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HourlyChimeActivity extends BaseEventActivity {
    private RecyclerView a;
    private ImageView b;
    private HourlyChimeAdapter c;
    private HourlyChimeRealmHelper d;
    private boolean e;
    public int selectCount = 0;

    public static void startActivity(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, HourlyChimeActivity.class));
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_hourly_chime);
        EventBusRegistry.getEventBus().register(this);
        this.a = (RecyclerView) findViewById(R.id.recyclerView_hours);
        this.b = (ImageView) findViewById(R.id.hourly_chime_switcher);
        RxViewHelp.debounceClicks(this.b, 100L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$HourlyChimeActivity$7zxvrC0GHla9SqvMLnHalri_ZwA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HourlyChimeActivity.this.a(obj);
            }
        }, $$Lambda$HourlyChimeActivity$UqC0MhsRIxSaBB_yXdUIm2FoY.INSTANCE);
        this.a.addItemDecoration(ItemDecorationHelper.listPadding(DisplayUtils.dip2px((Activity) this, getResources().getDimension(R.dimen.hourly_chime_item_margin)), DisplayUtils.dip2px((Activity) this, getResources().getDimension(R.dimen.hourly_chime_item_margin))));
        this.a.setLayoutManager(new GridLayoutManager((Context) this, 6, 1, false));
        this.e = SystemSetting.getHourlyChimeEnable();
        if (this.e) {
            this.b.setSelected(true);
        } else {
            this.b.setSelected(false);
        }
        this.d = new HourlyChimeRealmHelper();
        this.c = new HourlyChimeAdapter(this, this.d.queryAllHours(), this.d, this.e);
        this.a.setAdapter(this.c);
        List<HourlyChimeObject> querySetHourlyChime = this.d.querySetHourlyChime(true);
        if (ContainerUtil.hasData(querySetHourlyChime)) {
            this.selectCount = querySetHourlyChime.size();
        }
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        onHourlyChimeSwitcherClick();
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.hourlychime.e("debounceClicks", th);
    }

    public void onHourlyChimeSwitcherClick() {
        this.e = !this.b.isSelected();
        this.b.setSelected(this.e);
        SystemSetting.setHourlyChimeEnable(this.e);
        this.c.updateStatus(this.e);
        Logger logger = L.hourlychime;
        logger.d("onClick, enable:" + this.e);
        EventBusRegistry.getEventBus().post(new HourlyChimeEvent());
    }

    public int getSelectCount() {
        return this.selectCount;
    }

    public void setSelectCount(int i) {
        this.selectCount = i;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.d.close();
        EventBusRegistry.getEventBus().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHourlyChimeEvent(HourlyChimeEvent hourlyChimeEvent) {
        L.hourlychime.d("HourlyChimeActivity onHourlyChimeEvent");
        this.e = SystemSetting.getHourlyChimeEnable();
        if (this.e) {
            this.b.setSelected(true);
        } else {
            this.b.setSelected(false);
        }
        this.c.updateStatus(this.e);
    }
}
