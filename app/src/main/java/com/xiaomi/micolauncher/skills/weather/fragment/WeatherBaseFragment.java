package com.xiaomi.micolauncher.skills.weather.fragment;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.skills.weather.WeatherModel;
import com.xiaomi.micolauncher.skills.weather.model.EventWeatherOut;
import com.xiaomi.micolauncher.skills.weather.model.MiWeather;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class WeatherBaseFragment extends BaseFragment {
    MiWeather a;
    boolean b;
    String c;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.c = getArguments().getString(BaseEventActivity.KEY_DIALOG_ID);
        }
    }

    void a(long j) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).scheduleToClose(j);
        }
    }

    void a() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).removeCloseScheduler();
        }
    }

    public void onTtsPlayEnd() {
        L.base.d("WeatherSkill onTtsPlayEnd");
        this.b = true;
    }

    public void finish() {
        EventBusRegistry.getEventBus().post(new EventWeatherOut());
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void initWeatherView() {
        this.a = WeatherModel.getInstance().getMiWeather();
        if (this.c == null) {
            this.c = WeatherModel.getInstance().getDialogId();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayFinishEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        onTtsPlayEnd();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayErrorEvent(TtsPlayErrorEvent ttsPlayErrorEvent) {
        onTtsPlayEnd();
    }

    public void onBackPressed() {
        if (!this.b) {
            SpeechManager.getInstance().stopTts();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        WeatherModel.getInstance().release(this.c);
    }
}
