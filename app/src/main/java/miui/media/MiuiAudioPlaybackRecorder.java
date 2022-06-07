package miui.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.MediaProjection;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class MiuiAudioPlaybackRecorder {
    private IMediaProjectionManager a;
    private IMediaProjection b;
    private MediaProjection c;
    private String d;
    private AudioManager e;
    private int f;
    private HashMap<AudioRecord, AudioPolicy> g = new HashMap<>();
    private HashMap<AudioRecord, AudioMix> h = new HashMap<>();
    private final String i = "MiuiAudioPlaybackRecorder";

    public MiuiAudioPlaybackRecorder(Context context) {
        this.d = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        this.e = (AudioManager) context.getSystemService("audio");
        try {
            this.f = packageManager.getApplicationInfo(this.d, 0).uid;
            this.a = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
            this.b = this.a.createProjection(this.f, this.d, 0, false);
            this.c = new MediaProjection(context, this.b);
        } catch (Exception e) {
            Log.e("MiuiAudioPlaybackRecorder", "fail to create projection: " + e);
        }
    }

    public AudioRecord createRecorder(int i, int i2, int i3, int[] iArr, int i4) {
        if (this.c == null) {
            Log.e("MiuiAudioPlaybackRecorder", "fail to createRecorder, projection is null");
            return null;
        }
        AudioFormat build = new AudioFormat.Builder().setEncoding(i3).setSampleRate(i).setChannelMask(i2).build();
        AudioMixingRule.Builder addRule = new AudioMixingRule.Builder().addRule(new AudioAttributes.Builder().setUsage(1).build(), 1);
        for (int i5 : iArr) {
            addRule.addMixRule(4, Integer.valueOf(i5));
        }
        AudioMix build2 = new AudioMix.Builder(addRule.build()).setFormat(build).setRouteFlags(i4).build();
        AudioPolicy build3 = new AudioPolicy.Builder((Context) null).setMediaProjection(this.c).addMix(build2).build();
        this.e.registerAudioPolicy(build3);
        AudioRecord createAudioRecordSink = build3.createAudioRecordSink(build2);
        if (createAudioRecordSink == null) {
            this.e.unregisterAudioPolicyAsync(build3);
            Log.d("MiuiAudioPlaybackRecorder", "createAudioRecordSink return null");
        } else {
            Log.d("MiuiAudioPlaybackRecorder", "registerAudioPolicy for record: " + createAudioRecordSink.hashCode() + " policy:" + build3.hashCode());
            this.g.put(createAudioRecordSink, build3);
        }
        this.h.put(createAudioRecordSink, build2);
        return createAudioRecordSink;
    }

    public AudioRecord createRecorder(int i, int i2, int i3, int i4, int i5) {
        return createRecorder(i, i2, i3, new int[]{i4}, i5);
    }

    public boolean releaseRecorder(AudioRecord audioRecord) {
        AudioPolicy audioPolicy = this.g.get(audioRecord);
        if (audioPolicy == null) {
            return false;
        }
        Log.d("MiuiAudioPlaybackRecorder", "unregisterAudioPolicyAsync for record: " + audioRecord.hashCode() + " policy:" + audioPolicy.hashCode());
        this.e.unregisterAudioPolicyAsync(audioPolicy);
        return true;
    }

    public void updateUid(int[] iArr, AudioRecord audioRecord) {
        AudioMixingRule.Builder addRule = new AudioMixingRule.Builder().addRule(new AudioAttributes.Builder().setUsage(1).build(), 1);
        for (int i : iArr) {
            addRule.addMixRule(4, Integer.valueOf(i));
        }
        AudioMix build = new AudioMix.Builder(addRule.build()).setFormat(this.h.get(audioRecord).getFormat()).setRouteFlags(this.h.get(audioRecord).getRouteFlags()).build();
        AudioPolicy audioPolicy = this.g.get(audioRecord);
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(build);
        audioPolicy.attachMixes(arrayList);
        arrayList.clear();
        arrayList.add(this.h.get(audioRecord));
        audioPolicy.detachMixes(arrayList);
    }

    protected void finalize() {
        for (AudioRecord audioRecord : this.g.keySet()) {
            AudioPolicy audioPolicy = this.g.get(audioRecord);
            Log.d("MiuiAudioPlaybackRecorder", "unregisterAudioPolicyAsync for record: " + audioRecord.hashCode() + " policy:" + audioPolicy.hashCode());
            this.e.unregisterAudioPolicyAsync(audioPolicy);
        }
    }
}
