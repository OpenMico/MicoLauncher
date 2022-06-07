package com.xiaomi.micolauncher.module.homepage.fragment;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.SkillAdapter;
import com.xiaomi.micolauncher.module.homepage.event.LoadExploreDataSuccessEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadSkillDataSuccessEvent;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseSkillFragment extends BaseHomeFragment {
    public static final String TAG = "BaseSkillFragment";
    private boolean a;
    protected List<List<Object>> dataList = new ArrayList();

    public abstract List<Object> buildAppData();

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 3;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.fragment_skill_center;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadData() {
        List<Object> buildAppData = buildAppData();
        if (!ContainerUtil.isEmpty(buildAppData)) {
            this.dataList.add(buildAppData);
        }
        this.dataList.add(new ArrayList());
        b();
        if (ApiManager.isInited()) {
            loadDataFromSever();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        a();
        c();
    }

    private void a() {
        SkillDataManager.getManager().loadExploreData();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        return new SkillAdapter(this.dataList);
    }

    private void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_loading));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_loading));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_loading));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_loading));
        arrayList.add(new Skill.SkillPlaceHolder(R.drawable.skill_loading));
        this.dataList.add(arrayList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadSkillDataSuccess(LoadSkillDataSuccessEvent loadSkillDataSuccessEvent) {
        this.a = true;
        this.dataList.set(2, new ArrayList(loadSkillDataSuccessEvent.skillBeans));
        getAdapter().notifyItemChanged(2, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadExploreDataSuccess(LoadExploreDataSuccessEvent loadExploreDataSuccessEvent) {
        this.a = true;
        this.dataList.set(1, new ArrayList(loadExploreDataSuccessEvent.explore.getExplores()));
        getAdapter().notifyItemChanged(1, 0);
    }

    private void c() {
        SkillDataManager.getManager().loadSkillData();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (!isHidden()) {
            d();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!ContainerUtil.hasData(this.dataList)) {
            c();
        } else if (z) {
            d();
        }
    }

    @SuppressLint({"CheckResult"})
    private void d() {
        Observable.timer(50L, TimeUnit.MILLISECONDS).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$BaseSkillFragment$JEMsTW88BGN3Klvs2nLNFPYrWnQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseSkillFragment.this.a((Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Long l) throws Exception {
        L.base.d("BaseSkillFragmentfreshItem");
        getAdapter().notifyItemChanged(1, 0);
        getAdapter().notifyItemChanged(2, 0);
    }
}
