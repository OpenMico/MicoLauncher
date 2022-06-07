package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.skills.translation.bean.DictionaryTranslation;
import com.xiaomi.micolauncher.skills.translation.bean.ExampleSentence;
import com.xiaomi.micolauncher.skills.translation.bean.MachineTranslation;
import com.xiaomi.micolauncher.skills.translation.bean.PartSimple;
import com.xiaomi.micolauncher.skills.translation.bean.Synonym;
import com.xiaomi.micolauncher.skills.translation.bean.SynonymCard;
import com.xiaomi.micolauncher.skills.translation.bean.WordDeformation;
import com.xiaomi.micolauncher.skills.translation.bean.WordDetail;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class TranslationHolder extends RecyclerView.ViewHolder implements AppBarLayout.OnOffsetChangedListener {
    private LinearLayout a;
    private Context b;
    private LinearLayout.LayoutParams c = new LinearLayout.LayoutParams(-1, -2);
    private TextView d;
    private AutoScaleTextView e;
    private ImageView f;
    private int g;
    private boolean h;

    public TranslationHolder(@NonNull View view) {
        super(view);
        this.b = view.getContext();
        this.c.setMargins(0, 0, 0, UiUtils.getSize(this.b, R.dimen.translation_title_margin_top));
        this.a = (LinearLayout) view.findViewById(R.id.card_container);
        this.d = (TextView) view.findViewById(R.id.translation_word);
        this.e = (AutoScaleTextView) view.findViewById(R.id.translation_dest);
        this.f = (ImageView) view.findViewById(R.id.translation_play_btn);
    }

    public void bindData(boolean z, DictionaryTranslation dictionaryTranslation) {
        if (dictionaryTranslation != null) {
            if (this.a.getChildCount() > 3) {
                LinearLayout linearLayout = this.a;
                linearLayout.removeViewsInLayout(3, linearLayout.getChildCount() - 3);
            }
            PartSimple partSimple = dictionaryTranslation.getPartSimple();
            if (!z && partSimple != null) {
                this.d.setText(partSimple.getWord());
                this.d.setVisibility(0);
            }
            a(partSimple);
            WordDetail wordDetail = dictionaryTranslation.getWordDetail();
            if (wordDetail == null) {
                SynonymCard synonymCard = dictionaryTranslation.getSynonymCard();
                int i = 8;
                if (synonymCard != null) {
                    List<MachineTranslation> translations = synonymCard.getTranslations();
                    if (ContainerUtil.hasData(translations)) {
                        final MachineTranslation machineTranslation = translations.get(0);
                        this.e.setText(machineTranslation.getTransText());
                        this.d.setVisibility(machineTranslation.getWordMeaning().isEmpty() ? 8 : 0);
                        this.d.setText(machineTranslation.getWordMeaning());
                        ImageView imageView = this.f;
                        if (!machineTranslation.getTransTextAudioUrl().isEmpty()) {
                            i = 0;
                        }
                        imageView.setVisibility(i);
                        this.f.setOnTouchListener(MicoAnimationTouchListener.getInstance());
                        RxViewHelp.debounceClicksWithOneSeconds(this.f).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationHolder$MH_htr6ooen9qS7ROOsakfTnsQ0
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                TranslationHolder.b(MachineTranslation.this, obj);
                            }
                        });
                        return;
                    }
                    return;
                }
                final MachineTranslation wordSimple = dictionaryTranslation.getWordSimple();
                if (wordSimple != null) {
                    this.e.setText(wordSimple.getTransText());
                    this.d.setVisibility(wordSimple.getWordMeaning().isEmpty() ? 8 : 0);
                    this.d.setText(wordSimple.getWordMeaning());
                    ImageView imageView2 = this.f;
                    if (!wordSimple.getTransTextAudioUrl().isEmpty()) {
                        i = 0;
                    }
                    imageView2.setVisibility(i);
                    this.f.setOnTouchListener(MicoAnimationTouchListener.getInstance());
                    RxViewHelp.debounceClicksWithOneSeconds(this.f).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$TranslationHolder$LXaKmYkihwmiEHzUwydf04MeLfU
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            TranslationHolder.a(MachineTranslation.this, obj);
                        }
                    });
                    return;
                }
                L.skill.e("TranslationHolder bindData error cuz both wordDetail and wordSimple are null");
                return;
            }
            if (partSimple == null) {
                partSimple = wordDetail.getPartSimple();
                a(partSimple);
            }
            List<WordDeformation> deformations = wordDetail.getDeformations();
            if (ContainerUtil.hasData(deformations)) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (WordDeformation wordDeformation : deformations) {
                    if (wordDeformation.isNoun()) {
                        arrayList.add(wordDeformation);
                    } else if (wordDeformation.isAdj()) {
                        arrayList3.add(wordDeformation);
                    } else {
                        arrayList2.add(wordDeformation);
                    }
                }
                LinkedHashMap<String, List<WordDeformation>> linkedHashMap = new LinkedHashMap<>();
                if (ContainerUtil.hasData(arrayList3)) {
                    linkedHashMap.put(this.a.getContext().getString(R.string.word_adj), arrayList3);
                }
                if (ContainerUtil.hasData(arrayList)) {
                    linkedHashMap.put(this.a.getContext().getString(R.string.word_noun), arrayList);
                }
                if (ContainerUtil.hasData(arrayList2)) {
                    linkedHashMap.put(this.a.getContext().getString(R.string.word_verb), arrayList2);
                }
                WordDeformationView wordDeformationView = new WordDeformationView(this.b);
                wordDeformationView.setData(linkedHashMap);
                this.a.addView(wordDeformationView, this.c);
            }
            List<ExampleSentence> sentences = wordDetail.getSentences();
            if (ContainerUtil.hasData(sentences)) {
                ExampleSentenceView exampleSentenceView = new ExampleSentenceView(this.b);
                String str = "";
                if (partSimple != null) {
                    str = partSimple.getWord();
                }
                exampleSentenceView.setData(str, sentences);
                this.a.addView(exampleSentenceView, this.c);
            }
            List<Synonym> synonyms = wordDetail.getSynonyms();
            if (ContainerUtil.hasData(synonyms)) {
                SynonymView synonymView = new SynonymView(this.b);
                synonymView.setData(R.string.below_synonym, R.string.synonym, synonyms);
                this.a.addView(synonymView, this.c);
            }
            List<Synonym> antonyms = wordDetail.getAntonyms();
            if (ContainerUtil.hasData(antonyms)) {
                SynonymView synonymView2 = new SynonymView(this.b);
                synonymView2.setData(R.string.below_antonym, R.string.antonym, antonyms);
                this.a.addView(synonymView2, this.c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(MachineTranslation machineTranslation, Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(machineTranslation.getTransTextAudioUrl(), AudioSource.AUDIO_SOURCE_UI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(MachineTranslation machineTranslation, Object obj) throws Exception {
        MicoMultiAudioPlayer.getInstance().play(machineTranslation.getTransTextAudioUrl(), AudioSource.AUDIO_SOURCE_UI);
    }

    private void a(PartSimple partSimple) {
        if (partSimple != null) {
            if (this.d.getVisibility() == 0) {
                String meaning = partSimple.getMeanings().get(0).getMeaning();
                int indexOf = meaning.indexOf(65292);
                if (indexOf > 0) {
                    meaning = meaning.substring(0, indexOf);
                }
                this.e.setText(meaning);
            } else {
                this.e.setText(partSimple.getWord());
            }
            PartSimpleView partSimpleView = new PartSimpleView(this.b);
            partSimpleView.setData(partSimple);
            this.a.addView(partSimpleView, this.c);
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (this.g == 0) {
            this.g = appBarLayout.getTotalScrollRange();
        }
        float abs = ((Math.abs(i) * 100.0f) / this.g) / 100.0f;
        if (this.h) {
            float f = 1.0f - abs;
            this.e.setScaleX(f);
            this.e.setScaleY(f);
            this.e.setAlpha(f);
        }
        this.h = true;
    }
}
