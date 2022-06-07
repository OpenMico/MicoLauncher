package com.xiaomi.micolauncher.module.audiobooks;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.widget.NoScrollViewPager;
import com.xiaomi.micolauncher.databinding.ActivityAudioBookBinding;
import com.xiaomi.micolauncher.module.audiobooks.adapter.TabAdapter;
import com.xiaomi.micolauncher.module.audiobooks.fragment.TabFlowFragment;
import com.xiaomi.micolauncher.module.audiobooks.vm.AudioBookViewModel;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioBookActivity extends BaseActivity {
    private ActivityAudioBookBinding a;
    private AudioBookViewModel b;
    private List<Fragment> c;
    private TabAdapter e;
    private List<AudioDiscoveryPage.TabListBean.TabListItemBean> d = new ArrayList();
    private volatile int f = 3;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.f = bundle.getInt("key_pos");
        }
        this.a = (ActivityAudioBookBinding) DataBindingUtil.setContentView(this, R.layout.activity_audio_book);
        this.b = (AudioBookViewModel) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AudioBookViewModel.class);
        this.b.getTabList();
        addToDisposeBag(RxViewHelp.debounceClicksWithOneSeconds(this.a.loadingView.retryBtn).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$UtwNjtbnY-oIEPQIeG6jOsegwfU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudioBookActivity.this.a(obj);
            }
        }));
        a();
        scheduleToClose(DEFAULT_CLOSE_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        this.b.getTabList();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("key_pos", this.f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a = null;
    }

    private void a() {
        this.b.tablist.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$lD34cpXTx-BXGrtLRaK2h7POA5Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioBookActivity.this.a((List) obj);
            }
        });
        this.b.loading.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$YmfRg_Colh5nC5vdcroBcjkvM6k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioBookActivity.this.b((Boolean) obj);
            }
        });
        this.b.isErrorOccurred.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$viWJOLrVkz0z9QOwXAbRYclrFZ0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AudioBookActivity.this.a((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) {
        if (list != null) {
            this.d = list;
            b();
            a(false);
            return;
        }
        a(true);
        L.audiobook.d("api '/main_screen/station/tab_list' response data is null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Boolean bool) {
        if (bool == null) {
            return;
        }
        if (bool.booleanValue()) {
            c();
        } else {
            e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) {
        d();
    }

    private void b() {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        this.c.clear();
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= this.d.size()) {
                break;
            }
            List<Fragment> list = this.c;
            String name = this.d.get(i).getName();
            if (i != 3) {
                z = false;
            }
            list.add(TabFlowFragment.newInstance(name, z));
            i++;
        }
        this.a.viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) { // from class: com.xiaomi.micolauncher.module.audiobooks.AudioBookActivity.1
            @Override // androidx.fragment.app.FragmentStatePagerAdapter
            public Fragment getItem(int i2) {
                return (Fragment) AudioBookActivity.this.c.get(i2);
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return AudioBookActivity.this.c.size();
            }
        });
        if (this.e == null) {
            this.d.get(this.f).isSelected = true;
            this.e = new TabAdapter(this.d);
            this.e.setOnItemClickListener(new TabAdapter.OnItemClickListener() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$HyLd2unbr3vpTYYIoeYeoEYQZgs
                @Override // com.xiaomi.micolauncher.module.audiobooks.adapter.TabAdapter.OnItemClickListener
                public final void onItemClick(int i2) {
                    AudioBookActivity.this.a(i2);
                }
            });
        }
        this.a.tab.setAdapter(this.e);
        this.a.tab.scrollBy(0, (int) ((getResources().getDimensionPixelOffset(R.dimen.dp_64) * 3.5d) - (DisplayUtils.getDisplayRealHeight(this) / 2)));
        this.a.viewpager.setCurrentItem(this.f);
        this.c.get(this.f).setUserVisibleHint(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final int i) {
        if (this.f != i) {
            ThreadUtil.getComputationThreadPool().execute(new Runnable() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$U9r5aj0qAsmzjcgY0i7jD8zJ1IM
                @Override // java.lang.Runnable
                public final void run() {
                    AudioBookActivity.this.b(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(final int i) {
        int i2 = 0;
        while (i2 < this.d.size()) {
            this.d.get(i2).isSelected = i2 == i;
            i2++;
        }
        runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.audiobooks.-$$Lambda$AudioBookActivity$-Pmbt0okcbGyJ3UoyAfZZq5la0w
            @Override // java.lang.Runnable
            public final void run() {
                AudioBookActivity.this.c(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(int i) {
        this.f = i;
        this.e.notifyDataSetChanged();
        this.c.get(this.f).setUserVisibleHint(true);
        this.a.viewpager.setCurrentItem(this.f, true);
    }

    private void c() {
        this.a.loadingView.getRoot().setVisibility(0);
        this.a.loadingView.loadingTv.setText(R.string.common_loading_title);
        this.a.loadingView.retryBtn.setVisibility(8);
        this.a.loadingView.lottieView.setVisibility(0);
    }

    private void d() {
        this.a.loadingView.getRoot().setVisibility(0);
        this.a.loadingView.loadingTv.setText(R.string.common_loading_error_title);
        this.a.loadingView.retryBtn.setVisibility(0);
        this.a.loadingView.lottieView.setVisibility(8);
    }

    private void e() {
        this.a.loadingView.getRoot().setVisibility(8);
    }

    private void a(boolean z) {
        int i = 0;
        this.a.emptyView.setVisibility(z ? 0 : 8);
        this.a.tab.setVisibility(z ? 8 : 0);
        NoScrollViewPager noScrollViewPager = this.a.viewpager;
        if (z) {
            i = 8;
        }
        noScrollViewPager.setVisibility(i);
    }
}
