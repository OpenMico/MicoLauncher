package com.xiaomi.miplay.mylibrary.external;

import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.circulate.MetaData;
import com.xiaomi.miplay.mylibrary.circulate.MetaInfoUtil;
import com.xiaomi.miplay.mylibrary.circulate.MiplayMirrorService;
import com.xiaomi.miplay.mylibrary.circulate.VideoCirculatePool;
import com.xiaomi.miplay.mylibrary.external.api.IMediaControl;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/* loaded from: classes4.dex */
public class MediaControlImpl implements IMediaControl {
    private static String b;
    private static String d;
    private MiPlayAudioService a;
    private MiplayAccessExternalService c;

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    public int playState(String str, int i) {
        return 0;
    }

    public static void setDeviceId(String str) {
        b = str;
    }

    public void setAudioService(MiPlayAudioService miPlayAudioService) {
        this.a = miPlayAudioService;
    }

    public MediaControlImpl(MiplayAccessExternalService miplayAccessExternalService) {
        this.c = miplayAccessExternalService;
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int play(String str, MediaMetaData mediaMetaData) {
        Logger.i("MiplayMp_MediaControlImpl", "play. " + str, new Object[0]);
        a(str);
        try {
            if (this.a == null) {
                onCirculateFail(DataModel.CIRCULATEFAIL_NO_SUPPORT);
                return -1;
            } else if (this.a.getMiplayMirrorService() == null) {
                return -1;
            } else {
                OneTrackWorldUrl.getInstance().trackTarget(0, str);
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl play deviceMac=" + b, new Object[0]);
                if (!TextUtils.isEmpty(b)) {
                    int circulateStatus = this.a.getMiplayMirrorService().getCirculateStatus();
                    this.a.getMiplayMirrorService();
                    if (circulateStatus != MiplayMirrorService.CIRCULATE_STATUS_MIRROR) {
                        int circulateStatus2 = this.a.getMiplayMirrorService().getCirculateStatus();
                        this.a.getMiplayMirrorService();
                        if (circulateStatus2 != MiplayMirrorService.CIRCULATE_STATUS_CIRCULATE) {
                        }
                    }
                    this.a.getMiplayMirrorService().setResumeMirroring(false);
                    Logger.d("MiplayMp_MediaControlImpl", "cmdSession:" + this.a.cmdSessionControlMap.get(b), new Object[0]);
                    if (TextUtils.isEmpty(b) || !this.a.getMiplayMirrorService().IsMirrorting() || this.a.cmdSessionControlMap.get(b) != null) {
                        Logger.i("MiplayMp_MediaControlImpl", "url:" + mediaMetaData.getUrl(), new Object[0]);
                        Logger.i("MiplayMp_MediaControlImpl", "MediaMetaData:" + mediaMetaData.toString(), new Object[0]);
                        MiplayMirrorService miplayMirrorService = this.a.getMiplayMirrorService();
                        this.a.getMiplayMirrorService();
                        miplayMirrorService.mCirculateStatus = MiplayMirrorService.CIRCULATE_STATUS_CIRCULATE;
                        Logger.i("MiplayMp_MediaControlImpl", "mCirculateStatus:" + this.a.getMiplayMirrorService().mCirculateStatus, new Object[0]);
                        MetaData metaData = new MetaData();
                        metaData.setURL(mediaMetaData.getUrl());
                        metaData.setTitle(mediaMetaData.getTitle());
                        metaData.setDuration(mediaMetaData.getDuration());
                        metaData.setArt(mediaMetaData.getArt());
                        metaData.setPosition(mediaMetaData.getPosition());
                        metaData.setMux(mediaMetaData.getMux());
                        metaData.setCodec(mediaMetaData.getCodec());
                        metaData.setReverso(mediaMetaData.getReverso());
                        metaData.setPropertiesInfo(mediaMetaData.getPropertiesInfo());
                        this.a.cmdSessionControlMap.get(b).openAVDevice(MetaInfoUtil.getInstance().metaInfoToJson(metaData));
                        OneTrackWorldUrl.getInstance().startTimer(1);
                        this.a.getMiplayMirrorService().onBuffering();
                        return 0;
                    }
                    Logger.i("MiplayMp_MediaControlImpl", "request put in queue!", new Object[0]);
                    VideoCirculatePool.getInstance().clear();
                    this.a.getCurrentDeviceCache();
                    this.a.startDiscovery(2);
                    onCirculateFail(DataModel.CIRCULATEFAIL_NO_SUPPORT);
                    return -1;
                }
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl play on a null object or mode error.", new Object[0]);
                onCirculateFail(DataModel.CIRCULATEFAIL_NO_SUPPORT);
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int stop(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "stop.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (!TextUtils.equals(str, this.c.getmPlayPackageName())) {
                Logger.i("MiplayMp_MediaControlImpl", "stop error,Abandon this cmd", new Object[0]);
                return -1;
            } else if (this.a.getMiplayMirrorService() == null) {
                return -1;
            } else {
                if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                    Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                    return -1;
                }
                this.a.getMiplayMirrorService().onResumeMirror();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int pause(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "pause.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl pause on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).pauseDevice();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int resume(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "resume.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl resume on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).resumeDevice();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int seek(String str, long j) {
        Logger.i("MiplayMp_MediaControlImpl", "seek:" + j, new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl seek on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).seekMediaPlayer(j);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int setVolume(String str, double d2) {
        Logger.i("MiplayMp_MediaControlImpl", "setVolume:" + d2, new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl setVolume on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).setVolume((int) d2);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    public int onBufferState(String str, int i) {
        Logger.i("MiplayMp_MediaControlImpl", "onBufferState id:" + str + " ,state:" + i, new Object[0]);
        return 0;
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) {
        Logger.i("MiplayMp_MediaControlImpl", "sendPropertiesInfo.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl sendPropertiesInfo on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).sendCmdData(propertiesInfo.toJSONString().getBytes(StandardCharsets.UTF_8));
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int getPosition(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "getPosition.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl getPosition on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).getPosition();
                return (int) this.a.getPosition();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int getVolume(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "getVolume.", new Object[0]);
        try {
            if (this.a == null) {
                return -1;
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return -1;
            } else if (this.a.cmdSessionControlMap.get(b) == null) {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl getVolume on a null object", new Object[0]);
                return -1;
            } else {
                this.a.cmdSessionControlMap.get(b).getVolume();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public String getSourceName(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "getSourceName.", new Object[0]);
        try {
            if (this.a == null) {
                return "";
            }
            if (this.a.getMiplayMirrorService().getCirculateMode(b) != 3) {
                Logger.i("MiplayMp_MediaControlImpl", "current-non isvideocirculateting", new Object[0]);
                return "";
            } else if (this.a.cmdSessionControlMap.get(b) != null) {
                return this.a.getMiplayMirrorService().getSourceName(b);
            } else {
                Logger.i("MiplayMp_MediaControlImpl", "CmdSessionControl getPosition on a null object", new Object[0]);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int getCirculateMode() {
        Logger.i("MiplayMp_MediaControlImpl", "getCirculateMode.", new Object[0]);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.a == null || this.a.getMiplayMirrorService() == null || !this.a.getMiplayMirrorService().ismCirculateSwitch()) {
            return -1;
        }
        if (this.a.cmdSessionControlMap.get(b) == null) {
            Logger.i("MiplayMp_MediaControlImpl", "circulate no-discovery devices", new Object[0]);
            return -1;
        } else if (!this.a.getMiplayMirrorService().getSupportMPVAbility(b)) {
            return -1;
        } else {
            int circulateMode = this.a.getMiplayMirrorService().getCirculateMode(b);
            if (circulateMode == 3) {
                return 1;
            }
            if (circulateMode == MiplayMirrorService.CIRCULATE_STATUS_RESUME_MIRRORING) {
                return -2;
            }
            return this.a.getMiplayMirrorService().IsMirrorting() ? 0 : -1;
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.external.api.IMediaControl
    @RequiresApi(api = 23)
    public int cancelCirculate(String str, int i) {
        Logger.i("MiplayMp_MediaControlImpl", "cancelCirculate.", new Object[0]);
        try {
            if (this.a == null || this.a.getMiplayMirrorService() == null) {
                return -1;
            }
            this.a.getMiplayMirrorService().cancelCirculate(i);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int onCirculateFail(String str) {
        Logger.i("MiplayMp_MediaControlImpl", "onCirculateFail.", new Object[0]);
        try {
            if (this.c == null) {
                Logger.i("MiplayMp_MediaControlImpl", "mExternalService is null.", new Object[0]);
                return -1;
            } else if (this.c.getMiPlayExternalClientMap() == null) {
                Logger.i("MiplayMp_MediaControlImpl", "externalClientMap  is null.", new Object[0]);
                return -1;
            } else {
                try {
                    for (Map.Entry<String, MiPlayExternalClientInfo> entry : this.c.getMiPlayExternalClientMap().entrySet()) {
                        if (TextUtils.equals(entry.getValue().a, this.c.getmPlayPackageName())) {
                            Logger.i("MiplayMp_MediaControlImpl", "callback id:" + this.c.getmPlayPackageName(), new Object[0]);
                            entry.getValue().callback.onCirculateFail(str);
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public static String getAppId() {
        return d;
    }

    private static void a(String str) {
        d = str;
    }
}
