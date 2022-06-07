package com.xiaomi.micolauncher.module.setting;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.base.utils.VersionUtils;
import com.xiaomi.mico.mqtt.service.iface.IAiServiceTokenCallback;
import com.xiaomi.mico.mqtt.service.iface.IMQTTService;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.visual.algorithm.FaceDetectDistanceDialog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.bluetooth.BluetoothInitManager;
import com.xiaomi.micolauncher.common.crash.LogUploader;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.MqttMonitor;
import com.xiaomi.micolauncher.module.applist.MiTvCodeverEditActivity;
import com.xiaomi.micolauncher.module.applist.ThirdAppListActivity;
import com.xiaomi.micolauncher.module.initialize.BootActivity;
import com.xiaomi.micolauncher.module.initialize.IntroMovieActivity;
import com.xiaomi.micolauncher.module.localalbum.AlbumImagesManager;
import com.xiaomi.micolauncher.skills.mitv.MiTvControlTest;
import com.xiaomi.micolauncher.skills.mitv.MiTvOpenTest;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DevFragment extends BaseFragment {
    private Button a;
    private IMQTTService b;
    private MultiAudioPlayer d;
    private ServiceConnection c = new ServiceConnection() { // from class: com.xiaomi.micolauncher.module.setting.DevFragment.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.init.i("mqtt onServiceConnected %s", componentName);
            DevFragment.this.b = IMQTTService.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.init.i("mqtt onServiceDisconnected %s", componentName);
            DevFragment.this.b = null;
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            L.init.i("mqtt onBindingDied %s", componentName);
            DevFragment.this.b = null;
        }
    };
    private AdvertiseCallback e = new AdvertiseCallback() { // from class: com.xiaomi.micolauncher.module.setting.DevFragment.3
        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartFailure(int i) {
            super.onStartFailure(i);
            L.init.i("startAdvertising onStartFailure");
        }

        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartSuccess(AdvertiseSettings advertiseSettings) {
            super.onStartSuccess(advertiseSettings);
            L.init.i("startAdvertising onStartSuccess");
        }
    };

    private void d() {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dev, (ViewGroup) null, false);
        this.a = (Button) inflate.findViewById(R.id.buttonUpdateProgress);
        NotificationManager.from(getContext()).createNotificationChannel(new NotificationChannel("com.xiaomi.micolauncher", "mico", 4));
        inflate.setTag(this);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MqttMonitor.MQTT_PKG, MqttMonitor.MQTT_SERVICE));
        getContext().bindService(intent, this.c, 1);
        ((Button) inflate.findViewById(R.id.buttonVideoCall)).getText().toString();
        inflate.findViewById(R.id.buttonVoip).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonMemo).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonCreateMemo).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonOpenThirdSkill).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonCloseThirdSkill).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonBaikeLDH).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonUploadMiotToken).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonWatchVideo).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonWatchVideoBySystemMediaPlayer).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonShowBlockCanary).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonVideoCall).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonPlayVideo2).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.open_gallary).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.reboot_test).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.launcher_upgrade).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.init_intro_movie).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.music).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonTts).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.init_animation).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonDing).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.mitv_open).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.mitv_control).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonXiaoXunWatch).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonXiaoXunWatchLocal).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.send_notification).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.open_boot_activity).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonTestWeather).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonTestIotScan).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonBigSetting).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.audio_video_play).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.face_detect).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.mijia_adv).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonTestOpenThird).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.buttonMiTvVersion).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        inflate.findViewById(R.id.server_environment_change).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$RdYGAsXWzwwy6t4bDIoOJObFmco
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DevFragment.this.onViewClicked(view);
            }
        });
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        getContext().unbindService(this.c);
    }

    private void a() {
        this.d = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_MUSIC);
        this.d.playTts("该吃饭了");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonCreateMemo /* 2131362092 */:
                d();
                return;
            case R.id.buttonDing /* 2131362093 */:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("iqiyi://com.qiyi.video.speaker/app?from=xiaoai&command=play_from_id&param=1069930300")));
                return;
            case R.id.buttonMemo /* 2131362094 */:
                d();
                return;
            case R.id.buttonMiTvVersion /* 2131362095 */:
                startActivity(new Intent(getContext(), MiTvCodeverEditActivity.class));
                return;
            case R.id.buttonOpenThirdSkill /* 2131362096 */:
                break;
            default:
                switch (id) {
                    case R.id.buttonPlayVideo2 /* 2131362099 */:
                    case R.id.buttonTestWeather /* 2131362103 */:
                        return;
                    case R.id.buttonShowBlockCanary /* 2131362100 */:
                        a("am start com.example.blockcanary");
                        return;
                    case R.id.buttonTestIotScan /* 2131362101 */:
                        Log.i(DomainConfig.Mesh.DOMAIN_NAME, "send scan action");
                        return;
                    case R.id.buttonTestOpenThird /* 2131362102 */:
                        startActivity(new Intent(getContext(), ThirdAppListActivity.class));
                        return;
                    case R.id.buttonTts /* 2131362104 */:
                        SpeechManager.getInstance().nlpRequest("现在是几点");
                        return;
                    default:
                        switch (id) {
                            case R.id.buttonUploadMiotToken /* 2131362106 */:
                                c();
                                return;
                            case R.id.buttonVideoCall /* 2131362107 */:
                            case R.id.buttonVoip /* 2131362108 */:
                            case R.id.buttonWatchVideo /* 2131362109 */:
                            case R.id.buttonWatchVideoBySystemMediaPlayer /* 2131362110 */:
                            case R.id.buttonXiaoXunWatch /* 2131362111 */:
                            case R.id.buttonXiaoXunWatchLocal /* 2131362112 */:
                                return;
                            default:
                                switch (id) {
                                    case R.id.init_animation /* 2131362647 */:
                                        return;
                                    case R.id.init_intro_movie /* 2131362648 */:
                                        Intent intent = new Intent(getContext(), IntroMovieActivity.class);
                                        intent.putExtra(IntroMovieActivity.KEY_SOURCE, IntroMovieActivity.Source.Initial.ordinal());
                                        startActivity(intent);
                                        return;
                                    default:
                                        switch (id) {
                                            case R.id.mitv_control /* 2131362924 */:
                                                MiTvControlTest.getInstance().start(getContext());
                                                return;
                                            case R.id.mitv_open /* 2131362925 */:
                                                MiTvOpenTest.getInstance().start(getContext());
                                                return;
                                            default:
                                                switch (id) {
                                                    case R.id.open_boot_activity /* 2131363038 */:
                                                        startActivity(new Intent(getContext(), BootActivity.class));
                                                        return;
                                                    case R.id.open_gallary /* 2131363039 */:
                                                        AlbumImagesManager.showBlankAlbumPromptOrAlbum(getActivity());
                                                        return;
                                                    default:
                                                        switch (id) {
                                                            case R.id.audio_video_play /* 2131361984 */:
                                                                a();
                                                                return;
                                                            case R.id.buttonBaikeLDH /* 2131362086 */:
                                                            case R.id.buttonCloseThirdSkill /* 2131362090 */:
                                                                break;
                                                            case R.id.crash_upload /* 2131362269 */:
                                                                new LogUploader().upload(getContext());
                                                                return;
                                                            case R.id.face_detect /* 2131362483 */:
                                                                FaceDetectDistanceDialog faceDetectDistanceDialog = new FaceDetectDistanceDialog(MicoApplication.getApp());
                                                                if (!faceDetectDistanceDialog.isShown()) {
                                                                    faceDetectDistanceDialog.show();
                                                                    return;
                                                                }
                                                                return;
                                                            case R.id.launcher_upgrade /* 2131362771 */:
                                                                String formatVersionCode = VersionUtils.getFormatVersionCode(getContext());
                                                                ToastUtil.showToast("当前版本：" + formatVersionCode);
                                                                return;
                                                            case R.id.mijia_adv /* 2131362904 */:
                                                                L.bluetooth.i("米家广播测试!");
                                                                BluetoothInitManager.getInstance().startAdvertising(this.e);
                                                                return;
                                                            case R.id.music /* 2131362980 */:
                                                                SpeechManager.getInstance().nlpRequest("播放嘻哈歌曲");
                                                                return;
                                                            case R.id.reboot_test /* 2131363170 */:
                                                                startActivity(new Intent(getActivity(), BootActivity.class));
                                                                return;
                                                            case R.id.send_notification /* 2131363308 */:
                                                                NotificationManager notificationManager = (NotificationManager) MicoApplication.getGlobalContext().getSystemService(NotificationManager.class);
                                                                if (notificationManager.getNotificationChannel("mychannel") == null) {
                                                                    notificationManager.createNotificationChannel(new NotificationChannel("mychannel", "mychannel", 4));
                                                                }
                                                                notificationManager.notify(45676457, new Notification.Builder(MicoApplication.getGlobalContext(), "mychannel").setVisibility(1).setSmallIcon(R.drawable.notification_album).setContentTitle("title").setContentText("this is my notification").setTimeoutAfter(TimeUnit.SECONDS.toMillis(10L)).setAutoCancel(true).build());
                                                                return;
                                                            case R.id.server_environment_change /* 2131363318 */:
                                                                b();
                                                                return;
                                                            default:
                                                                return;
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
        SpeechManager.getInstance().nlpRequest(((Button) view).getText().toString());
    }

    @SuppressLint({"StringFormatInvalid"})
    private void b() {
        String str;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int intGlobal = MicoSettings.getIntGlobal(getActivity().getApplicationContext(), "mico_server_env", -1);
        CharSequence[] textArray = getResources().getTextArray(R.array.dev_update_server_environment_array);
        if (intGlobal == -1) {
            str = getString(R.string.dev_update_server_environment_title, textArray[2]);
        } else {
            int i = intGlobal - 1;
            str = i < textArray.length ? getString(R.string.dev_update_server_environment_title, textArray[i]) : getString(R.string.dev_update_server_environment_title);
        }
        builder.setTitle(str).setItems(textArray, new DialogInterface.OnClickListener() { // from class: com.xiaomi.micolauncher.module.setting.DevFragment.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                MicoSettings.putIntGlobal(DevFragment.this.getActivity().getApplicationContext(), "mico_server_env", i2 + 1);
            }
        });
        builder.create().show();
    }

    private void c() {
        if (this.b == null) {
            ToastUtil.showToast("mqttService is null");
            return;
        }
        try {
            this.b.uploadMiotToken(Long.valueOf(LoginManager.get().getUserId()).longValue(), "s1", "s2", new IAiServiceTokenCallback() { // from class: com.xiaomi.micolauncher.module.setting.DevFragment.4
                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return null;
                }

                @Override // com.xiaomi.mico.mqtt.service.iface.IAiServiceTokenCallback
                public void call(boolean z, String str) throws RemoteException {
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void a(final String str) {
        if (!StringUtil.isNullOrEmpty(str)) {
            ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$DevFragment$bKwoJt5xCKcjORMFaR4hh8IMfzQ
                @Override // java.lang.Runnable
                public final void run() {
                    DevFragment.b(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str) {
        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            L.base.w("inputKeyevent", e);
        }
    }
}
