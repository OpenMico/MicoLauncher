package com.xiaomi.micolauncher.module.skill.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.common.view.base.FluidLayout;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.manager.SkillAnimationUtils;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class SkillDetailInfoFragment extends BaseFragment {
    private ImageView a;
    private TextView b;
    private FluidLayout c;
    private String d;
    private Skill.SkillInfo e;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (getArguments() != null) {
            this.d = getArguments().getString("EXTRA_SKILL_ID");
        }
        View inflate = layoutInflater.inflate(R.layout.fragment_skill_detail_info, viewGroup, false);
        a(inflate);
        a();
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.e = null;
        super.onDestroyView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        b();
    }

    private void a(View view) {
        this.a = (ImageView) view.findViewById(R.id.bg_iv);
        this.b = (TextView) view.findViewById(R.id.title_tv);
        this.c = (FluidLayout) view.findViewById(R.id.fluid_layout);
    }

    private void a() {
        SkillDataManager.getManager().loadSkillInfo(this.d).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.fragment.-$$Lambda$SkillDetailInfoFragment$XBhJQFkx53Q4cDRquP3tIS2oPp0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDetailInfoFragment.this.a((Skill.SkillInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Skill.SkillInfo skillInfo) throws Exception {
        this.e = skillInfo;
        b();
    }

    private void b() {
        if (this.e == null) {
            a();
            return;
        }
        GlideUtils.bindImageView(getContext(), this.e.bgImageUrl, this.a);
        this.b.setText(this.e.name);
        c();
    }

    private void c() {
        FluidLayout fluidLayout = this.c;
        if (!(fluidLayout == null || this.e == null)) {
            fluidLayout.removeAllViews();
            int dimensionPixelSize = this.c.getContext().getResources().getDimensionPixelSize(R.dimen.skill_detail_info_margin);
            for (final String str : this.e.getTips()) {
                final View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_skill_detail_info, (ViewGroup) null);
                final TextView textView = (TextView) inflate.findViewById(R.id.title_tv);
                textView.setText(str);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                marginLayoutParams.setMargins(0, 0, dimensionPixelSize, dimensionPixelSize);
                this.c.addView(inflate, marginLayoutParams);
                final ImageView imageView = (ImageView) inflate.findViewById(R.id.image_iv);
                inflate.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.skill.ui.fragment.SkillDetailInfoFragment.1
                    @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                    public void onAvoidFastDoubleClick(View view) {
                        if (CommonUtil.checkHasNetworkAndShowToast(SkillDetailInfoFragment.this.getContext())) {
                            SpeechManager.getInstance().nlpTtsRequest(str, true);
                            SkillAnimationUtils.startClickAnimation(imageView, textView.getMeasuredWidth() - (DisplayUtils.dip2px(SkillDetailInfoFragment.this.getContext(), 5.0f) * 2));
                            SkillDetailInfoFragment.this.c.startAnimationSet(inflate);
                        }
                    }
                });
            }
        }
    }

    public static SkillDetailInfoFragment newInstance(String str) {
        SkillDetailInfoFragment skillDetailInfoFragment = new SkillDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("EXTRA_SKILL_ID", str);
        skillDetailInfoFragment.setArguments(bundle);
        return skillDetailInfoFragment;
    }
}
