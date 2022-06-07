package com.xiaomi.micolauncher.module.audiobooks.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.databinding.FragmentTabFlowBinding;
import com.xiaomi.micolauncher.module.audiobooks.adapter.FlowAdapter;
import com.xiaomi.micolauncher.module.audiobooks.vm.AudioBookViewModel;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TabFlowFragment extends BaseFragment {
    private FragmentTabFlowBinding a;
    private AudioBookViewModel b;
    private Activity c;
    private String d;
    private FlowAdapter e;
    private List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> f = new ArrayList();
    private volatile boolean g = false;
    private volatile boolean h = true;
    private volatile boolean i;
    private View j;

    public static TabFlowFragment newInstance(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("tabName", str);
        bundle.putBoolean("default", z);
        TabFlowFragment tabFlowFragment = new TabFlowFragment();
        tabFlowFragment.setArguments(bundle);
        return tabFlowFragment;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = (AudioBookViewModel) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.c.getApplication())).get(AudioBookViewModel.class);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.c = (Activity) context;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (bundle != null) {
            this.d = bundle.getString("tabName");
            this.i = bundle.getBoolean("default");
        } else if (getArguments() != null) {
            this.d = getArguments().getString("tabName");
            this.i = getArguments().getBoolean("default");
        }
        this.a = FragmentTabFlowBinding.inflate(layoutInflater, viewGroup, false);
        a();
        b();
        if (this.i) {
            this.b.getDiscAudioTabFlow(this.d);
            if (this.b.isImmutableTabFlow(this.d)) {
                this.g = true;
            }
        }
        return this.a.getRoot();
    }

    private void a() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this.c, 2, 0, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.TabFlowFragment.1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                return ((AudioDiscoveryPage.Flow.BlocksBean.ItemsBean) TabFlowFragment.this.f.get(i)).isBlockTitle() ? 2 : 1;
            }
        });
        this.e = new FlowAdapter(this.f);
        this.a.rvCardList.setLayoutManager(gridLayoutManager);
        this.a.rvCardList.setAdapter(this.e);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("tabName", this.d);
        bundle.putBoolean("default", this.i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        AudioBookViewModel audioBookViewModel;
        super.onActivate();
        if (!this.g && (audioBookViewModel = this.b) != null) {
            audioBookViewModel.getDiscAudioTabFlow(this.d);
            if (this.b.isImmutableTabFlow(this.d)) {
                this.g = true;
            }
        }
    }

    private void b() {
        this.b.items.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.-$$Lambda$TabFlowFragment$TKMxvVjVs9FE0KAdqdi6PcHkbQA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TabFlowFragment.this.a((List) obj);
            }
        });
        this.b.loading.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.-$$Lambda$TabFlowFragment$hvPllPJWWrCKhTbFynW0JIjz3WU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TabFlowFragment.this.b((Boolean) obj);
            }
        });
        this.b.isErrorOccurred.observe(this, new Observer() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.-$$Lambda$TabFlowFragment$Zf_-7gBCsUrphcieQNTVbFDDmic
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TabFlowFragment.this.a((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final List list) {
        if (!ContainerUtil.hasData(list)) {
            this.f.clear();
            this.e.notifyDataSetChanged();
            a(2);
            return;
        }
        a(1);
        addToDisposeBag(this.b.calcDiffResult(this.f, list).subscribeOn(MicoSchedulers.computation()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.-$$Lambda$TabFlowFragment$tOSoMN4D4yxbXXU1mC1rM5wje-o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TabFlowFragment.this.a(list, (DiffUtil.DiffResult) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, DiffUtil.DiffResult diffResult) throws Exception {
        diffResult.dispatchUpdatesTo(this.e);
        this.f.clear();
        this.f.addAll(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Boolean bool) {
        if (bool != null && bool.booleanValue() && this.h) {
            this.h = false;
            a(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) {
        this.g = false;
        a(4);
    }

    private void a(int i) {
        switch (i) {
            case 1:
                this.a.emptyView.setVisibility(8);
                this.a.rvCardList.setVisibility(0);
                this.a.loading.setVisibility(8);
                View view = this.j;
                if (view != null) {
                    view.setVisibility(8);
                    return;
                }
                return;
            case 2:
                this.a.emptyView.setVisibility(0);
                this.a.rvCardList.setVisibility(8);
                this.a.loading.setVisibility(8);
                View view2 = this.j;
                if (view2 != null) {
                    view2.setVisibility(8);
                    return;
                }
                return;
            case 3:
                this.a.emptyView.setVisibility(8);
                this.a.rvCardList.setVisibility(8);
                this.a.loading.setVisibility(0);
                View view3 = this.j;
                if (view3 != null) {
                    view3.setVisibility(8);
                    return;
                }
                return;
            case 4:
                this.h = true;
                if (!this.a.errorView.isInflated() && this.a.errorView.getViewStub() != null) {
                    this.j = this.a.errorView.getViewStub().inflate();
                    ImageView imageView = (ImageView) this.j.findViewById(R.id.retry_btn);
                    imageView.setVisibility(0);
                    addToDisposeBag(RxViewHelp.debounceClicksWithOneSeconds(imageView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.audiobooks.fragment.-$$Lambda$TabFlowFragment$gG8oUddqb8IEIwAX2meTI1kTJdg
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            TabFlowFragment.this.a(obj);
                        }
                    }));
                    ((TextView) this.j.findViewById(R.id.loading_tv)).setText(R.string.common_loading_error_title);
                    this.j.findViewById(R.id.lottie_view).setVisibility(8);
                }
                View view4 = this.j;
                if (view4 != null) {
                    view4.setVisibility(0);
                }
                this.a.emptyView.setVisibility(8);
                this.a.rvCardList.setVisibility(8);
                this.a.loading.setVisibility(8);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        this.b.getDiscAudioTabFlow(this.d);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.a = null;
    }
}
