package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.account.sdk.ServiceToken;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.service.SpeakerService;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.skills.translation.bean.ExampleSentence;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SentenceItemView extends ConstraintLayout {
    TextView a;
    TextView b;
    TextView c;
    ImageView d;
    private String e;
    private String f;

    public SentenceItemView(@NonNull Context context) {
        this(context, null);
    }

    public SentenceItemView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SentenceItemView(@NonNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.sentence_item, this);
        this.a = (TextView) findViewById(R.id.index_tv);
        this.b = (TextView) findViewById(R.id.sentence_english);
        this.c = (TextView) findViewById(R.id.sentence_chinise);
        this.d = (ImageView) findViewById(R.id.sentence_play);
        RxViewHelp.debounceClicksWithOneSeconds(this.d).observeOn(MicoSchedulers.io()).flatMap($$Lambda$SentenceItemView$DpB5SXsl2K1guO9iGF8zXuVD3E.INSTANCE).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$SentenceItemView$oyU-ffdpVAll8KskP6Rrvci7vN4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = SentenceItemView.this.a(context, (String) obj);
                return a;
            }
        }).retry(1L).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.translation.view.-$$Lambda$SentenceItemView$G5dLdmZKgbewyor6Fsy8U5l-YIM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SentenceItemView.this.a((SpeakerService.SpeechTTS) obj);
            }
        }, $$Lambda$SentenceItemView$J88pDVnmCmezHTmtWDyyHnJFp7E.INSTANCE);
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        setPadding(0, UiUtils.getSize(context, R.dimen.phonetic_symbol_padding), 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ObservableSource a(Object obj) throws Exception {
        ServiceToken serviceInfo = TokenManager.getInstance().getServiceInfo(ApiConstants.SID_SPEAKER);
        if (serviceInfo != null && serviceInfo.isServiceTokenValid()) {
            return Observable.just(serviceInfo.getServiceToken());
        }
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(ApiConstants.SID_SPEAKER);
        if (blockGetServiceToken != null) {
            return Observable.just(blockGetServiceToken.serviceToken);
        }
        return Observable.just("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(@NonNull Context context, String str) throws Exception {
        if (!TextUtils.isEmpty(this.e)) {
            SpeakerService.SpeechTTS speechTTS = new SpeakerService.SpeechTTS();
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.e);
            speechTTS.ttsUrlList = arrayList;
            return Observable.just(speechTTS);
        }
        String str2 = MibrainConfig.getMibrainConfig(context).clientId;
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.f);
        return ApiManager.speechMiService.text2tts(str, str2, arrayList2, "zh-CN");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SpeakerService.SpeechTTS speechTTS) throws Exception {
        this.e = speechTTS.ttsUrlList.get(0);
        MicoMultiAudioPlayer.getInstance().play(this.e, AudioSource.AUDIO_SOURCE_UI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.skill.e("SentenceItemView request tts error", th);
    }

    public void setData(int i, String str, ExampleSentence exampleSentence) {
        TextView textView = this.a;
        textView.setText(i + ".");
        this.f = exampleSentence.getEnSentence();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.f);
        int indexOf = exampleSentence.getEnSentence().indexOf(str);
        if (indexOf < 0) {
            indexOf = 0;
        }
        spannableStringBuilder.setSpan(new CustomTypefaceSpan(str, Typeface.create(TypefaceUtil.FONT_WEIGHT_W600, 1)), indexOf, str.length() + indexOf, 33);
        this.b.setText(spannableStringBuilder);
        this.c.setText(exampleSentence.getCnSentence());
        this.e = exampleSentence.getTtsUrl();
    }
}
