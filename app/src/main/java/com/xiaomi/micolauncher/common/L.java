package com.xiaomi.micolauncher.common;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.router.proxy.Proxies;

/* loaded from: classes3.dex */
public class L {
    public static Logger statusModel = a("MICO.statusmodel");
    public static Logger startuplauncher = a("MICO.startuplauncher");
    public static Logger base = a("MICO.base");
    public static Logger activity = a("MICO.activity");
    public static Logger dmesg = a("MICO.dmesg");
    public static Logger util = a("MICO.util");
    public static Logger eventbus = a("MICO.eventbus");
    public static Logger threadutil = a("MICO.threadutil");
    public static Logger login = a("MICO.login");
    public static Logger api = a("MICO.api");
    public static Logger speech = a("MICO.speech");
    public static Logger miot = a("MICO.miot");
    public static Logger ot = a("MICO.ot");
    public static Logger voip = a("MICO.voip");
    public static Logger sleep_mode = a("MICO.sleep_mode");
    public static Logger micoVoip = a("MICO.micoVoip");
    public static Logger alarm = a("MICO.alarm");
    public static Logger countdown = a("MICO.countdown");
    public static Logger video = a("MICO.video");
    public static Logger update = a("MICO.update");
    public static Logger contentprovider = a("MICO.contentprovider");
    public static Logger memo = a("MICO.memo");
    public static Logger player = a("MICO.player");
    public static Logger init = a("MICO.init");
    public static Logger push = a("MICO.push");
    public static Logger sdklog = a("MICO.brainlog");
    public static Logger dlna = a("MICO.dlna");
    public static Logger bluetooth = a("MICO.bluetooth");
    public static Logger debug = a("MICO.debug");
    public static Logger battery = a("MICO.battery");
    public static Logger leak = a("MICO.leak");
    public static Logger exception = a("MICO.exception");
    public static Logger launcher = a("MICO.launcher");
    public static Logger launcherVideo = a("MICO.launcher.video");
    public static Logger miotSdk = a("MICO.OT-SDK");
    public static Logger localalbum = a("MICO.localalbum");
    public static Logger lockscreen = a("MICO.lockscreen");
    public static Logger skillpage = a("MICO.skillpage");
    public static Logger homepage = a("MICO.homepage");
    public static Logger profile = a("MICO.profile");
    public static Logger startUpProfile = a("MICO.startUpProfile");
    public static Logger openplatform = a("MICO.openplatform");
    public static Logger mesh = a("MICO.mesh");
    public static Logger childmode = a("MICO.childmode");
    public static Logger ubus = a("MICO.ubus");
    public static Logger mitv = a("MICO.mitv");
    public static Logger weather = a("MICO.weather");
    public static Logger policy = a("MICO.policy");
    public static Logger skill = a("MICO.skill");
    public static Logger latency = a("MICO.latency");
    public static Logger capability = a("MICO.cap");
    public static Logger notification = a("MICO.notification");
    public static Logger ancientPoem = a("MICO.ancientPoem");
    public static Logger wlan = a("MICO.wlan");
    public static Logger wakeup = a("MICO.wakeupView");
    public static Logger childContent = a("MICO.childContent");
    public static Logger camera2 = a("MICO.camera2");
    public static Logger lock = a("MICO.lock");
    public static Logger statistics = a("MICO.statistics");
    public static Logger ope = a("MICO.ope");
    public static Logger quickapp = a("MICO.quickapp");
    public static Logger pay = a("MICO.pay");
    public static Logger storage = a("MICO.storage");
    public static Logger wakeguide = a("MICO.wakeguide");
    public static Logger course = a("MICO.course");
    public static Logger database = a("MICO.database");
    public static Logger audiobook = a("MICO.audiobook");
    public static Logger monitor = a("MICO.monitor");
    public static Logger encryption = a("MICO.encryption");
    public static Logger mic = a("MICO.microphone");
    public static Logger accessibility = a("MICO.accessibility");
    public static Logger multicp = a("MICO.multicp");
    public static Logger hourlychime = a("MICO.hourlychime");
    public static Logger smarthome = a("MICO.smarthome");
    public static Logger smarthomesetting = a("MICO.smarthomesetting");
    public static Logger lifecycle = a("MICO.lifecycle");
    public static Logger relay = a("MICO.relay");
    public static Logger ir = a("MICO.IR");
    public static Logger videoMonitor = a("MICO.videomonitor");
    public static Logger launcheroverlay = a("MICO.launcheroverlay");

    private static Logger a(String str) {
        Logger build = XLog.tag(str).build();
        Proxies.Instance.getApp();
        return build;
    }
}