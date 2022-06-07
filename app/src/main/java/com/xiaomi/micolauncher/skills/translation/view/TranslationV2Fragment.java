package com.xiaomi.micolauncher.skills.translation.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.ai.api.Application;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.skills.translation.bean.DictionaryTranslation;
import com.xiaomi.micolauncher.skills.translation.bean.MachineTranslation;
import com.xiaomi.micolauncher.skills.translation.bean.PartSimple;
import com.xiaomi.micolauncher.skills.translation.bean.SynonymCard;
import com.xiaomi.micolauncher.skills.translation.bean.Translation;
import com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TranslationV2Fragment extends BaseFragment {
    public static final String KEY_TRANSLATION_DATA = "translation_data";
    public static final String KEY_TRANSLATION_SENTENCE_URL = "translation_sentence";
    TextView a;
    RecyclerView b;
    FrameLayout c;
    ImageView d;
    ImageView e;
    View f;
    TextView g;
    TextView h;
    ImageView i;
    private Translation j;
    private List<MachineTranslation> k = new ArrayList();
    private List<DictionaryTranslation> l = new ArrayList();
    private int m;
    private boolean n;
    private boolean o;
    private Activity p;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.translation_layout, viewGroup, false);
        this.a = (TextView) inflate.findViewById(R.id.translation_src_text);
        this.b = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        this.c = (FrameLayout) inflate.findViewById(R.id.translation_root);
        this.d = (ImageView) inflate.findViewById(R.id.img_top);
        this.e = (ImageView) inflate.findViewById(R.id.img_bottom);
        this.f = inflate.findViewById(R.id.machine_translation_view);
        this.g = (TextView) inflate.findViewById(R.id.machine_translation_dest);
        this.h = (TextView) inflate.findViewById(R.id.machine_translation_src);
        this.i = (ImageView) inflate.findViewById(R.id.play_icon);
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.p = (Activity) context;
    }

    @Override // androidx.fragment.app.Fragment
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.j = (Translation) arguments.getSerializable(KEY_TRANSLATION_DATA);
            Translation translation = this.j;
            if (translation != null) {
                DictionaryTranslation dictionary = translation.getDictionary();
                if (dictionary == null) {
                    this.c.setBackgroundResource(R.drawable.translation_yellow);
                    this.d.setImageResource(R.drawable.bg_yellow_arc);
                    this.e.setImageResource(R.drawable.bg_yellow_circle);
                    this.b.setVisibility(8);
                    this.f.setVisibility(0);
                    this.g.setText(this.j.getDestText());
                    this.h.setText(this.j.getSrcText());
                    this.a.setText(!TextUtils.isEmpty(this.j.getSrcText()) ? this.j.getSrcText() : PreferenceUtils.getSettingString(getContext(), KEY_TRANSLATION_DATA, this.j.getSrcText()));
                    final String settingString = PreferenceUtils.getSettingString(getContext(), KEY_TRANSLATION_SENTENCE_URL, "");
                    if (!TextUtils.isEmpty(settingString)) {
                        this.i.setVisibility(0);
                        RxViewHelp.debounceClicksWithOneSeconds(this.i).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationV2Fragment$P-q6CdSefYfNlu6ysInhnmZDWMQ
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                TranslationV2Fragment.a(settingString, obj);
                            }
                        });
                        this.i.setOnTouchListener(MicoAnimationTouchListener.getInstance());
                        return;
                    }
                    this.i.setVisibility(8);
                    return;
                }
                this.a.setText(this.j.getSrcText());
                SynonymCard synonymCard = dictionary.getSynonymCard();
                int i = 1;
                if (synonymCard != null) {
                    this.k = synonymCard.getTranslations();
                    this.o = true;
                }
                if (ContainerUtil.hasData(this.k)) {
                    this.l = new ArrayList(this.k.size());
                }
                if (synonymCard != null) {
                    this.c.setBackgroundResource(R.drawable.translation_yellow);
                    this.d.setImageResource(R.drawable.bg_yellow_arc);
                    this.e.setImageResource(R.drawable.bg_yellow_circle);
                } else {
                    this.c.setBackgroundResource(R.drawable.translation_green);
                    this.d.setImageResource(R.drawable.bg_green_arc);
                    this.e.setImageResource(R.drawable.bg_green_circle);
                }
                this.l.add(a(dictionary));
                L.skill.i("TranslationV2Fragment dictionaryTranslations size=%d, machineTranslations size=%d", Integer.valueOf(this.l.size()), Integer.valueOf(this.k.size()));
                final a aVar = new a();
                RecyclerView recyclerView = this.b;
                int size = UiUtils.getSize(recyclerView.getContext(), R.dimen.card_margin_horizontal);
                if (ContainerUtil.hasData(this.k)) {
                    i = this.k.size();
                }
                recyclerView.addItemDecoration(new SpaceItemDecoration(size, i));
                this.b.setAdapter(aVar);
                final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(this.b);
                this.b.setNestedScrollingEnabled(false);
                this.b.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment.1
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int i2) {
                        super.onScrollStateChanged(recyclerView2, i2);
                        RecyclerView.LayoutManager layoutManager = recyclerView2.getLayoutManager();
                        View findSnapView = pagerSnapHelper.findSnapView(layoutManager);
                        if (findSnapView != null) {
                            TranslationV2Fragment.this.m = layoutManager.getPosition(findSnapView);
                        }
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(@NonNull RecyclerView recyclerView2, int i2, int i3) {
                        super.onScrolled(recyclerView2, i2, i3);
                        int i4 = TranslationV2Fragment.this.m + 1;
                        if (i2 > 0 && !TranslationV2Fragment.this.n && i4 >= TranslationV2Fragment.this.l.size() && i4 < TranslationV2Fragment.this.k.size()) {
                            TranslationV2Fragment.this.n = true;
                            MachineTranslation machineTranslation = (MachineTranslation) TranslationV2Fragment.this.k.get(i4);
                            L.skill.i("TranslationV2Fragment onScrolled position trans text : %s", machineTranslation.getTransText());
                            Application.Translation translation2 = new Application.Translation();
                            translation2.setText(machineTranslation.getTransText());
                            translation2.setSrcLang(TranslationV2Fragment.this.j.getDestLanguage());
                            translation2.setTargetLang(TranslationV2Fragment.this.j.getSrcLanguage());
                            ArrayList arrayList = new ArrayList();
                            arrayList.add("en");
                            arrayList.add("zh");
                            translation2.setSupportLang(arrayList);
                            translation2.setBackup(TranslationV2Fragment.this.j.getSrcText());
                            translation2.setType(Application.TranslationType.SWITCH_WORD);
                            SpeechEventUtil.getInstance().eventRequest(APIUtils.buildEvent(translation2), new C01701(machineTranslation, i4));
                        }
                    }

                    /* renamed from: com.xiaomi.micolauncher.skills.translation.view.TranslationV2Fragment$1$1  reason: invalid class name and collision with other inner class name */
                    /* loaded from: classes3.dex */
                    class C01701 implements SpeechEventUtil.EventListener {
                        final /* synthetic */ MachineTranslation a;
                        final /* synthetic */ int b;

                        C01701(MachineTranslation machineTranslation, int i) {
                            this.a = machineTranslation;
                            this.b = i;
                        }

                        @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                        public void onEventResult(List<Instruction> list, Event event) {
                            TranslationV2Fragment.this.n = false;
                            if (!ContainerUtil.isEmpty(list)) {
                                boolean z = false;
                                for (Instruction instruction : list) {
                                    if ("Translation".equals(instruction.getName())) {
                                        TranslationV2Fragment.this.l.add(new DictionaryTranslation(((Template.Translation) instruction.getPayload()).getDictionary().get()));
                                        z = true;
                                    }
                                }
                                if (!z) {
                                    DictionaryTranslation dictionaryTranslation = new DictionaryTranslation();
                                    if (TextUtils.isEmpty(this.a.getWordMeaning())) {
                                        this.a.setWordMeaning(TranslationV2Fragment.this.j.getSrcText());
                                    }
                                    dictionaryTranslation.setWordSimple(this.a);
                                    TranslationV2Fragment.this.l.add(dictionaryTranslation);
                                }
                                L.skill.i("onEventResult current thread : %s", Thread.currentThread().getName());
                                Activity activity = TranslationV2Fragment.this.p;
                                final a aVar = aVar;
                                final int i = this.b;
                                activity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationV2Fragment$1$1$aQXeLr_2CQLZHPdiuFSKvNAbgC8
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TranslationV2Fragment.a.this.notifyItemChanged(i);
                                    }
                                });
                            }
                        }

                        @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
                        public void onEventFail(Event event, boolean z) {
                            TranslationV2Fragment.this.n = false;
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(str, AudioSource.AUDIO_SOURCE_UI);
    }

    private DictionaryTranslation a(DictionaryTranslation dictionaryTranslation) {
        PartSimple partSimple = dictionaryTranslation.getPartSimple();
        String srcText = this.j.getSrcText();
        if (partSimple != null && TextUtils.isEmpty(partSimple.getWord())) {
            partSimple.setWord(srcText);
        }
        if (dictionaryTranslation.getWordDetail() == null) {
            SynonymCard synonymCard = dictionaryTranslation.getSynonymCard();
            if (synonymCard != null) {
                List<MachineTranslation> translations = synonymCard.getTranslations();
                if (ContainerUtil.hasData(translations)) {
                    MachineTranslation machineTranslation = translations.get(0);
                    if (TextUtils.isEmpty(machineTranslation.getWordMeaning())) {
                        machineTranslation.setWordMeaning(srcText);
                    }
                }
            }
            MachineTranslation wordSimple = dictionaryTranslation.getWordSimple();
            if (wordSimple != null && TextUtils.isEmpty(wordSimple.getWordMeaning())) {
                wordSimple.setWordMeaning(srcText);
            }
        }
        return dictionaryTranslation;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        MicoMultiAudioPlayer.getInstance().tryStopPlayer();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.Adapter<TranslationHolder> {
        a() {
        }

        @NonNull
        /* renamed from: a */
        public TranslationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new TranslationHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.translation_card, viewGroup, false));
        }

        /* renamed from: a */
        public void onBindViewHolder(@NonNull TranslationHolder translationHolder, int i) {
            if (i < TranslationV2Fragment.this.l.size()) {
                translationHolder.bindData(TranslationV2Fragment.this.o, (DictionaryTranslation) TranslationV2Fragment.this.l.get(i));
            } else {
                translationHolder.bindData(TranslationV2Fragment.this.o, null);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (ContainerUtil.isEmpty(TranslationV2Fragment.this.k)) {
                return 1;
            }
            return TranslationV2Fragment.this.k.size();
        }
    }
}
