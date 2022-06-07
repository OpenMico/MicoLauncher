package com.xiaomi.micolauncher.module.video.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.video.ui.base.CustomTabHost;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseGroupListActivity extends BaseActivity {
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_ORIGIN = "EXTRA_CATEGORY_ORIGIN";
    public static final String EXTRA_STYLE_KID = "EXTRA_STYLE_KID";
    protected long categoryId;
    protected List<CategoryInterface> dataList = new ArrayList();
    protected String origin;
    protected boolean styleBigScreen;
    protected CustomTabHost tabHost;

    /* loaded from: classes3.dex */
    public interface CategoryInterface {
        long getCategoryId();

        String getCategoryName();

        default String getCategoryOrigin() {
            return "";
        }

        String getCategoryType();
    }

    protected abstract void loadData();

    protected abstract void setupView();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_group_list);
        if (getIntent() != null) {
            this.categoryId = Long.parseLong(getIntent().getStringExtra("EXTRA_CATEGORY_ID"));
            this.origin = getIntent().getStringExtra("EXTRA_CATEGORY_ORIGIN");
            this.styleBigScreen = Boolean.parseBoolean(getIntent().getStringExtra(EXTRA_STYLE_KID)) && Hardware.isBigScreen();
            if (TextUtils.isEmpty(this.origin)) {
                this.origin = "iqiyi";
            }
        }
        a();
        loadData();
        setDefaultScheduleDuration();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            this.categoryId = intent.getLongExtra("EXTRA_CATEGORY_ID", 1L);
            setupView();
        }
    }

    private void a() {
        this.tabHost = (CustomTabHost) findViewById(R.id.tab_host);
        this.tabHost.setFragmentManager(getSupportFragmentManager());
        this.tabHost.getViewPager().setScrollable(false);
        this.tabHost.getTabWidget().setFocusable(false);
        this.tabHost.getTabWidget().setFocusableInTouchMode(false);
        if (this.styleBigScreen) {
            this.tabHost.setBackgroundColor(getColor(R.color.child_mode_bg_color));
        } else {
            this.tabHost.setBackgroundColor(getColor(R.color.color_000000));
        }
    }

    protected Fragment addTab(String str, Class<?> cls, Bundle bundle, View.OnClickListener onClickListener) {
        View inflate = LayoutInflater.from(this).inflate(b(), (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.text_view)).setText(str);
        return this.tabHost.addTab(inflate, cls, bundle, onClickListener);
    }

    private int b() {
        return this.styleBigScreen ? R.layout.view_left_tab_widget_child : R.layout.view_left_tab_widget;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.tabHost.clear();
        this.tabHost = null;
    }
}
