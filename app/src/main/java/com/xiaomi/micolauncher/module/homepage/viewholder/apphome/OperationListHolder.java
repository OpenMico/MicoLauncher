package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.bean.AppItem;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.homepage.view.AppOperateView;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class OperationListHolder extends BaseAppHolder {
    AppOperateView[] a = new AppOperateView[4];
    TextView b;
    TextView c;
    private List<AppItem> d;
    private final int e;

    public OperationListHolder(View view) {
        super(view);
        this.a[0] = (AppOperateView) view.findViewById(R.id.app_list_first);
        this.a[1] = (AppOperateView) view.findViewById(R.id.app_list_second);
        this.a[2] = (AppOperateView) view.findViewById(R.id.app_list_third);
        this.a[3] = (AppOperateView) view.findViewById(R.id.app_list_fourth);
        if (this.isChildMode || Hardware.isX6A()) {
            this.e = this.a.length - 1;
        } else {
            this.e = this.a.length;
        }
        this.b = (TextView) view.findViewById(R.id.special_title);
        this.c = (TextView) view.findViewById(R.id.description_tv);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    protected void initViewInMain() {
        for (final int i = 0; i < this.e; i++) {
            AnimLifecycleManager.repeatOnAttach(this.a[i], MicoAnimConfigurator4EntertainmentAndApps.get());
            RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$OperationListHolder$MKXUBN2OGN4Wewa6H-9CPZOv7i4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    OperationListHolder.this.a(i, obj);
                }
            });
        }
    }

    public /* synthetic */ void a(int i, Object obj) throws Exception {
        AppInfo appInfoByAppKey = SkillDataManager.getManager().getAppInfoByAppKey(this.d.get(i).getAppKey());
        executeAction(appInfoByAppKey);
        AppRecordHelper.recordAppClickTrackKey(this.recommendCard.getTrackKey());
        AppRecordHelper.recordAppClickByMi(this.tabName, appInfoByAppKey.getAppName());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindData(RecommendCard recommendCard, String str) {
        super.bindData(recommendCard, str);
        this.b.setText(recommendCard.getTitle());
        this.c.setText(recommendCard.getDescription());
        this.d = recommendCard.getItemList();
        int size = this.d.size();
        for (int i = 0; i < this.e; i++) {
            if (size > i) {
                this.a[i].bindData(this.d.get(i));
                this.a[i].setVisibility(0);
            } else {
                this.a[i].setVisibility(4);
            }
        }
    }
}
