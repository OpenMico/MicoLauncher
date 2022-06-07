package com.xiaomi.micolauncher.module.homepage.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.homepage.event.LoadBlackListEvent;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class AudiobookCommonActivity extends BaseAudiobookActivity {
    TextView a;
    ConstraintLayout b;
    protected List<String> blackList;
    View c;
    ImageView d;
    protected String tag;

    protected abstract int getTitleResource();

    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    protected int layoutId() {
        return R.layout.audiobook_common_layout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadBlackListSuccess(LoadBlackListEvent loadBlackListEvent) {
        this.blackList = loadBlackListEvent.blackList;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void loadData() {
        this.b.setVisibility(0);
    }

    protected <T> void showOrHideEmptyLayout(List<T> list) {
        if (ContainerUtil.isEmpty(list) && this.c.getVisibility() == 8) {
            this.c.setVisibility(0);
        }
        if (ContainerUtil.hasData(list) && this.c.getVisibility() == 0) {
            this.c.setVisibility(8);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void initView() {
        super.initView();
        this.a = (TextView) findViewById(R.id.title_tv);
        this.b = (ConstraintLayout) findViewById(R.id.loading_layout);
        this.c = findViewById(R.id.empty_layout);
        this.d = (ImageView) findViewById(R.id.category_back_iv);
        if (TextUtils.isEmpty(this.tag)) {
            this.a.setText(getTitleResource());
        } else {
            this.a.setText(this.tag);
        }
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.activity.-$$Lambda$AudiobookCommonActivity$uEjYUcX-B5VHpPBtwG10urZCtE4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudiobookCommonActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(View view) {
        finish();
    }
}
