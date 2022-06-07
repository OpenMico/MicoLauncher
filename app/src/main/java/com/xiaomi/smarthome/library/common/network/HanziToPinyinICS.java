package com.xiaomi.smarthome.library.common.network;

import android.text.TextUtils;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.smarthome.library.common.network.HanziToPinyin;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes4.dex */
public class HanziToPinyinICS {
    private static HanziToPinyinICS e;
    private final boolean f;
    public static final char[] UNIHANS = {21621, 21710, 23433, 32942, 20985, 20843, 25344, 25203, 37030, 21253, 21329, 22868, 20283, 23620, 36793, 26631, 24971, 37024, 27103, 30326, 23788, 22163, 23111, 39137, 20179, 25805, 20874, 23934, 22092, 21449, 38039, 36799, 20261, 25220, 36710, 25275, 26621, 21507, 20805, 25277, 20986, 27451, 25571, 24029, 30126, 21561, 26486, 36916, 30133, 21254, 20945, 31895, 27718, 23828, 37032, 25619, 21649, 22823, 30136, 24403, 20992, 28098, 24471, 25189, 28783, 27664, 22002, 30008, 20993, 29241, 20163, 19999, 19996, 21783, 22047, 20595, 22534, 37907, 22810, 23104, 35830, 22848, 38821, 32780, 21457, 24070, 26041, 39134, 20998, 20016, 35205, 20175, 32017, 20245, 26094, 35813, 29976, 20872, 30347, 25096, 32102, 26681, 24218, 24037, 21246, 20272, 29916, 32619, 20851, 20809, 24402, 34926, 21593, 21704, 21683, 39032, 33472, 33983, 35779, 40658, 25323, 20136, 22135, 21565, 40769, 21282, 33457, 24576, 29375, 24031, 28784, 26127, 21529, 19980, 21152, 25099, 27743, 33405, 38454, 24062, 21156, 20866, 21244, 21258, 23071, 22104, 20891, 21652, 24320, 21002, 38390, 23611, 21308, 21067, 32943, 38444, 31354, 25248, 21043, 22840, 33967, 23485, 21281, 20111, 22372, 25193, 22403, 26469, 20848, 21879, 25438, 20162, 21202, 22596, 21013, 20486, 22849, 33391, 25769, 21015, 25294, 12295, 28316, 40857, 30620, 22108, 23048, 30055, 25249, 32599, 21603, 22920, 38718, 23258, 37017, 29483, 40636, 27794, 38376, 30015, 21674, 30496, 21941, 21673, 27665, 21517, 35884, 25720, 21726, 27626, 25295, 23419, 22241, 22218, 23404, 35767, 39297, 24641, 33021, 22958, 25288, 23330, 40479, 25423, 24744, 23425, 22942, 20892, 32698, 22900, 22907, 34384, 25386, 21908, 35764, 36276, 25293, 30469, 20051, 25243, 21624, 21943, 21257, 19989, 20559, 21117, 27669, 23000, 20050, 38027, 21078, 20166, 19971, 25488, 21315, 21595, 24708, 30335, 20405, 38737, 37019, 19992, 26354, 24366, 32570, 22795, 21605, 31331, 23046, 24825, 20154, 25172, 26085, 33592, 21433, 22914, 22567, 26741, 38384, 33509, 20200, 27618, 19977, 26706, 25531, 33394, 26862, 20711, 26432, 31579, 23665, 20260, 24368, 22882, 30003, 21319, 23608, 21454, 20070, 21047, 25684, 38377, 21452, 35841, 21550, 22913, 21430, 24554, 25436, 33487, 29435, 22794, 23385, 21766, 20182, 33492, 22349, 38132, 22834, 24529, 29093, 21076, 22825, 20347, 24086, 21381, 22258, 20599, 37568, 28237, 25512, 21534, 25176, 25366, 27498, 24367, 23594, 21361, 22637, 32705, 25373, 20800, 22805, 34430, 20186, 20065, 28785, 20123, 24515, 26143, 20982, 20241, 26100, 36713, 30134, 21195, 20011, 24697, 22830, 24186, 32822, 19968, 27437, 24212, 21727, 20323, 20248, 25180, 40482, 26352, 26197, 21277, 28797, 31948, 29250, 20654, 21017, 36156, 24590, 22679, 21522, 25434, 27838, 24352, 38026, 34567, 36126, 20105, 20043, 20013, 24030, 26417, 25235, 36329, 19987, 22918, 38585, 23442, 21331, 23388, 23447, 37049, 31199, 38075, 21404, 23562, 26152};
    public static final byte[][] PINYINS = {new byte[]{65, 0, 0, 0, 0, 0}, new byte[]{65, 73, 0, 0, 0, 0}, new byte[]{65, 78, 0, 0, 0, 0}, new byte[]{65, 78, 71, 0, 0, 0}, new byte[]{65, 79, 0, 0, 0, 0}, new byte[]{66, 65, 0, 0, 0, 0}, new byte[]{66, 65, 73, 0, 0, 0}, new byte[]{66, 65, 78, 0, 0, 0}, new byte[]{66, 65, 78, 71, 0, 0}, new byte[]{66, 65, 79, 0, 0, 0}, new byte[]{66, 69, 73, 0, 0, 0}, new byte[]{66, 69, 78, 0, 0, 0}, new byte[]{66, 69, 78, 71, 0, 0}, new byte[]{66, 73, 0, 0, 0, 0}, new byte[]{66, 73, 65, 78, 0, 0}, new byte[]{66, 73, 65, 79, 0, 0}, new byte[]{66, 73, 69, 0, 0, 0}, new byte[]{66, 73, 78, 0, 0, 0}, new byte[]{66, 73, 78, 71, 0, 0}, new byte[]{66, 79, 0, 0, 0, 0}, new byte[]{66, 85, 0, 0, 0, 0}, new byte[]{67, 65, 0, 0, 0, 0}, new byte[]{67, 65, 73, 0, 0, 0}, new byte[]{67, 65, 78, 0, 0, 0}, new byte[]{67, 65, 78, 71, 0, 0}, new byte[]{67, 65, 79, 0, 0, 0}, new byte[]{67, 69, 0, 0, 0, 0}, new byte[]{67, 69, 78, 0, 0, 0}, new byte[]{67, 69, 78, 71, 0, 0}, new byte[]{67, 72, 65, 0, 0, 0}, new byte[]{67, 72, 65, 73, 0, 0}, new byte[]{67, 72, 65, 78, 0, 0}, new byte[]{67, 72, 65, 78, 71, 0}, new byte[]{67, 72, 65, 79, 0, 0}, new byte[]{67, 72, 69, 0, 0, 0}, new byte[]{67, 72, 69, 78, 0, 0}, new byte[]{67, 72, 69, 78, 71, 0}, new byte[]{67, 72, 73, 0, 0, 0}, new byte[]{67, 72, 79, 78, 71, 0}, new byte[]{67, 72, 79, 85, 0, 0}, new byte[]{67, 72, 85, 0, 0, 0}, new byte[]{67, 72, 85, 65, 0, 0}, new byte[]{67, 72, 85, 65, 73, 0}, new byte[]{67, 72, 85, 65, 78, 0}, new byte[]{67, 72, 85, 65, 78, 71}, new byte[]{67, 72, 85, 73, 0, 0}, new byte[]{67, 72, 85, 78, 0, 0}, new byte[]{67, 72, 85, 79, 0, 0}, new byte[]{67, 73, 0, 0, 0, 0}, new byte[]{67, 79, 78, 71, 0, 0}, new byte[]{67, 79, 85, 0, 0, 0}, new byte[]{67, 85, 0, 0, 0, 0}, new byte[]{67, 85, 65, 78, 0, 0}, new byte[]{67, 85, 73, 0, 0, 0}, new byte[]{67, 85, 78, 0, 0, 0}, new byte[]{67, 85, 79, 0, 0, 0}, new byte[]{68, 65, 0, 0, 0, 0}, new byte[]{68, 65, 73, 0, 0, 0}, new byte[]{68, 65, 78, 0, 0, 0}, new byte[]{68, 65, 78, 71, 0, 0}, new byte[]{68, 65, 79, 0, 0, 0}, new byte[]{68, 69, 0, 0, 0, 0}, new byte[]{68, 69, 73, 0, 0, 0}, new byte[]{68, 69, 78, 0, 0, 0}, new byte[]{68, 69, 78, 71, 0, 0}, new byte[]{68, 73, 0, 0, 0, 0}, new byte[]{68, 73, 65, 0, 0, 0}, new byte[]{68, 73, 65, 78, 0, 0}, new byte[]{68, 73, 65, 79, 0, 0}, new byte[]{68, 73, 69, 0, 0, 0}, new byte[]{68, 73, 78, 71, 0, 0}, new byte[]{68, 73, 85, 0, 0, 0}, new byte[]{68, 79, 78, 71, 0, 0}, new byte[]{68, 79, 85, 0, 0, 0}, new byte[]{68, 85, 0, 0, 0, 0}, new byte[]{68, 85, 65, 78, 0, 0}, new byte[]{68, 85, 73, 0, 0, 0}, new byte[]{68, 85, 78, 0, 0, 0}, new byte[]{68, 85, 79, 0, 0, 0}, new byte[]{69, 0, 0, 0, 0, 0}, new byte[]{69, 73, 0, 0, 0, 0}, new byte[]{69, 78, 0, 0, 0, 0}, new byte[]{69, 78, 71, 0, 0, 0}, new byte[]{69, 82, 0, 0, 0, 0}, new byte[]{70, 65, 0, 0, 0, 0}, new byte[]{70, 65, 78, 0, 0, 0}, new byte[]{70, 65, 78, 71, 0, 0}, new byte[]{70, 69, 73, 0, 0, 0}, new byte[]{70, 69, 78, 0, 0, 0}, new byte[]{70, 69, 78, 71, 0, 0}, new byte[]{70, 73, 65, 79, 0, 0}, new byte[]{70, 79, 0, 0, 0, 0}, new byte[]{70, 79, 85, 0, 0, 0}, new byte[]{70, 85, 0, 0, 0, 0}, new byte[]{71, 65, 0, 0, 0, 0}, new byte[]{71, 65, 73, 0, 0, 0}, new byte[]{71, 65, 78, 0, 0, 0}, new byte[]{71, 65, 78, 71, 0, 0}, new byte[]{71, 65, 79, 0, 0, 0}, new byte[]{71, 69, 0, 0, 0, 0}, new byte[]{71, 69, 73, 0, 0, 0}, new byte[]{71, 69, 78, 0, 0, 0}, new byte[]{71, 69, 78, 71, 0, 0}, new byte[]{71, 79, 78, 71, 0, 0}, new byte[]{71, 79, 85, 0, 0, 0}, new byte[]{71, 85, 0, 0, 0, 0}, new byte[]{71, 85, 65, 0, 0, 0}, new byte[]{71, 85, 65, 73, 0, 0}, new byte[]{71, 85, 65, 78, 0, 0}, new byte[]{71, 85, 65, 78, 71, 0}, new byte[]{71, 85, 73, 0, 0, 0}, new byte[]{71, 85, 78, 0, 0, 0}, new byte[]{71, 85, 79, 0, 0, 0}, new byte[]{72, 65, 0, 0, 0, 0}, new byte[]{72, 65, 73, 0, 0, 0}, new byte[]{72, 65, 78, 0, 0, 0}, new byte[]{72, 65, 78, 71, 0, 0}, new byte[]{72, 65, 79, 0, 0, 0}, new byte[]{72, 69, 0, 0, 0, 0}, new byte[]{72, 69, 73, 0, 0, 0}, new byte[]{72, 69, 78, 0, 0, 0}, new byte[]{72, 69, 78, 71, 0, 0}, new byte[]{72, 77, 0, 0, 0, 0}, new byte[]{72, 79, 78, 71, 0, 0}, new byte[]{72, 79, 85, 0, 0, 0}, new byte[]{72, 85, 0, 0, 0, 0}, new byte[]{72, 85, 65, 0, 0, 0}, new byte[]{72, 85, 65, 73, 0, 0}, new byte[]{72, 85, 65, 78, 0, 0}, new byte[]{72, 85, 65, 78, 71, 0}, new byte[]{72, 85, 73, 0, 0, 0}, new byte[]{72, 85, 78, 0, 0, 0}, new byte[]{72, 85, 79, 0, 0, 0}, new byte[]{74, 73, 0, 0, 0, 0}, new byte[]{74, 73, 65, 0, 0, 0}, new byte[]{74, 73, 65, 78, 0, 0}, new byte[]{74, 73, 65, 78, 71, 0}, new byte[]{74, 73, 65, 79, 0, 0}, new byte[]{74, 73, 69, 0, 0, 0}, new byte[]{74, 73, 78, 0, 0, 0}, new byte[]{74, 73, 78, 71, 0, 0}, new byte[]{74, 73, 79, 78, 71, 0}, new byte[]{74, 73, 85, 0, 0, 0}, new byte[]{74, 85, 0, 0, 0, 0}, new byte[]{74, 85, 65, 78, 0, 0}, new byte[]{74, 85, 69, 0, 0, 0}, new byte[]{74, 85, 78, 0, 0, 0}, new byte[]{75, 65, 0, 0, 0, 0}, new byte[]{75, 65, 73, 0, 0, 0}, new byte[]{75, 65, 78, 0, 0, 0}, new byte[]{75, 65, 78, 71, 0, 0}, new byte[]{75, 65, 79, 0, 0, 0}, new byte[]{75, 69, 0, 0, 0, 0}, new byte[]{75, 69, 73, 0, 0, 0}, new byte[]{75, 69, 78, 0, 0, 0}, new byte[]{75, 69, 78, 71, 0, 0}, new byte[]{75, 79, 78, 71, 0, 0}, new byte[]{75, 79, 85, 0, 0, 0}, new byte[]{75, 85, 0, 0, 0, 0}, new byte[]{75, 85, 65, 0, 0, 0}, new byte[]{75, 85, 65, 73, 0, 0}, new byte[]{75, 85, 65, 78, 0, 0}, new byte[]{75, 85, 65, 78, 71, 0}, new byte[]{75, 85, 73, 0, 0, 0}, new byte[]{75, 85, 78, 0, 0, 0}, new byte[]{75, 85, 79, 0, 0, 0}, new byte[]{76, 65, 0, 0, 0, 0}, new byte[]{76, 65, 73, 0, 0, 0}, new byte[]{76, 65, 78, 0, 0, 0}, new byte[]{76, 65, 78, 71, 0, 0}, new byte[]{76, 65, 79, 0, 0, 0}, new byte[]{76, 69, 0, 0, 0, 0}, new byte[]{76, 69, 73, 0, 0, 0}, new byte[]{76, 69, 78, 71, 0, 0}, new byte[]{76, 73, 0, 0, 0, 0}, new byte[]{76, 73, 65, 0, 0, 0}, new byte[]{76, 73, 65, 78, 0, 0}, new byte[]{76, 73, 65, 78, 71, 0}, new byte[]{76, 73, 65, 79, 0, 0}, new byte[]{76, 73, 69, 0, 0, 0}, new byte[]{76, 73, 78, 0, 0, 0}, new byte[]{76, 73, 78, 71, 0, 0}, new byte[]{76, 73, 85, 0, 0, 0}, new byte[]{76, 79, 78, 71, 0, 0}, new byte[]{76, 79, 85, 0, 0, 0}, new byte[]{76, 85, 0, 0, 0, 0}, new byte[]{76, 85, 65, 78, 0, 0}, new byte[]{76, 85, 69, 0, 0, 0}, new byte[]{76, 85, 78, 0, 0, 0}, new byte[]{76, 85, 79, 0, 0, 0}, new byte[]{77, 0, 0, 0, 0, 0}, new byte[]{77, 65, 0, 0, 0, 0}, new byte[]{77, 65, 73, 0, 0, 0}, new byte[]{77, 65, 78, 0, 0, 0}, new byte[]{77, 65, 78, 71, 0, 0}, new byte[]{77, 65, 79, 0, 0, 0}, new byte[]{77, 69, 0, 0, 0, 0}, new byte[]{77, 69, 73, 0, 0, 0}, new byte[]{77, 69, 78, 0, 0, 0}, new byte[]{77, 69, 78, 71, 0, 0}, new byte[]{77, 73, 0, 0, 0, 0}, new byte[]{77, 73, 65, 78, 0, 0}, new byte[]{77, 73, 65, 79, 0, 0}, new byte[]{77, 73, 69, 0, 0, 0}, new byte[]{77, 73, 78, 0, 0, 0}, new byte[]{77, 73, 78, 71, 0, 0}, new byte[]{77, 73, 85, 0, 0, 0}, new byte[]{77, 79, 0, 0, 0, 0}, new byte[]{77, 79, 85, 0, 0, 0}, new byte[]{77, 85, 0, 0, 0, 0}, new byte[]{78, 65, 0, 0, 0, 0}, new byte[]{78, 65, 73, 0, 0, 0}, new byte[]{78, 65, 78, 0, 0, 0}, new byte[]{78, 65, 78, 71, 0, 0}, new byte[]{78, 65, 79, 0, 0, 0}, new byte[]{78, 69, 0, 0, 0, 0}, new byte[]{78, 69, 73, 0, 0, 0}, new byte[]{78, 69, 78, 0, 0, 0}, new byte[]{78, 69, 78, 71, 0, 0}, new byte[]{78, 73, 0, 0, 0, 0}, new byte[]{78, 73, 65, 78, 0, 0}, new byte[]{78, 73, 65, 78, 71, 0}, new byte[]{78, 73, 65, 79, 0, 0}, new byte[]{78, 73, 69, 0, 0, 0}, new byte[]{78, 73, 78, 0, 0, 0}, new byte[]{78, 73, 78, 71, 0, 0}, new byte[]{78, 73, 85, 0, 0, 0}, new byte[]{78, 79, 78, 71, 0, 0}, new byte[]{78, 79, 85, 0, 0, 0}, new byte[]{78, 85, 0, 0, 0, 0}, new byte[]{78, 85, 65, 78, 0, 0}, new byte[]{78, 85, 69, 0, 0, 0}, new byte[]{78, 85, 79, 0, 0, 0}, new byte[]{79, 0, 0, 0, 0, 0}, new byte[]{79, 85, 0, 0, 0, 0}, new byte[]{80, 65, 0, 0, 0, 0}, new byte[]{80, 65, 73, 0, 0, 0}, new byte[]{80, 65, 78, 0, 0, 0}, new byte[]{80, 65, 78, 71, 0, 0}, new byte[]{80, 65, 79, 0, 0, 0}, new byte[]{80, 69, 73, 0, 0, 0}, new byte[]{80, 69, 78, 0, 0, 0}, new byte[]{80, 69, 78, 71, 0, 0}, new byte[]{80, 73, 0, 0, 0, 0}, new byte[]{80, 73, 65, 78, 0, 0}, new byte[]{80, 73, 65, 79, 0, 0}, new byte[]{80, 73, 69, 0, 0, 0}, new byte[]{80, 73, 78, 0, 0, 0}, new byte[]{80, 73, 78, 71, 0, 0}, new byte[]{80, 79, 0, 0, 0, 0}, new byte[]{80, 79, 85, 0, 0, 0}, new byte[]{80, 85, 0, 0, 0, 0}, new byte[]{81, 73, 0, 0, 0, 0}, new byte[]{81, 73, 65, 0, 0, 0}, new byte[]{81, 73, 65, 78, 0, 0}, new byte[]{81, 73, 65, 78, 71, 0}, new byte[]{81, 73, 65, 79, 0, 0}, new byte[]{81, 73, 69, 0, 0, 0}, new byte[]{81, 73, 78, 0, 0, 0}, new byte[]{81, 73, 78, 71, 0, 0}, new byte[]{81, 73, 79, 78, 71, 0}, new byte[]{81, 73, 85, 0, 0, 0}, new byte[]{81, 85, 0, 0, 0, 0}, new byte[]{81, 85, 65, 78, 0, 0}, new byte[]{81, 85, 69, 0, 0, 0}, new byte[]{81, 85, 78, 0, 0, 0}, new byte[]{82, 65, 78, 0, 0, 0}, new byte[]{82, 65, 78, 71, 0, 0}, new byte[]{82, 65, 79, 0, 0, 0}, new byte[]{82, 69, 0, 0, 0, 0}, new byte[]{82, 69, 78, 0, 0, 0}, new byte[]{82, 69, 78, 71, 0, 0}, new byte[]{82, 73, 0, 0, 0, 0}, new byte[]{82, 79, 78, 71, 0, 0}, new byte[]{82, 79, 85, 0, 0, 0}, new byte[]{82, 85, 0, 0, 0, 0}, new byte[]{82, 85, 65, 78, 0, 0}, new byte[]{82, 85, 73, 0, 0, 0}, new byte[]{82, 85, 78, 0, 0, 0}, new byte[]{82, 85, 79, 0, 0, 0}, new byte[]{83, 65, 0, 0, 0, 0}, new byte[]{83, 65, 73, 0, 0, 0}, new byte[]{83, 65, 78, 0, 0, 0}, new byte[]{83, 65, 78, 71, 0, 0}, new byte[]{83, 65, 79, 0, 0, 0}, new byte[]{83, 69, 0, 0, 0, 0}, new byte[]{83, 69, 78, 0, 0, 0}, new byte[]{83, 69, 78, 71, 0, 0}, new byte[]{83, 72, 65, 0, 0, 0}, new byte[]{83, 72, 65, 73, 0, 0}, new byte[]{83, 72, 65, 78, 0, 0}, new byte[]{83, 72, 65, 78, 71, 0}, new byte[]{83, 72, 65, 79, 0, 0}, new byte[]{83, 72, 69, 0, 0, 0}, new byte[]{83, 72, 69, 78, 0, 0}, new byte[]{83, 72, 69, 78, 71, 0}, new byte[]{83, 72, 73, 0, 0, 0}, new byte[]{83, 72, 79, 85, 0, 0}, new byte[]{83, 72, 85, 0, 0, 0}, new byte[]{83, 72, 85, 65, 0, 0}, new byte[]{83, 72, 85, 65, 73, 0}, new byte[]{83, 72, 85, 65, 78, 0}, new byte[]{83, 72, 85, 65, 78, 71}, new byte[]{83, 72, 85, 73, 0, 0}, new byte[]{83, 72, 85, 78, 0, 0}, new byte[]{83, 72, 85, 79, 0, 0}, new byte[]{83, 73, 0, 0, 0, 0}, new byte[]{83, 79, 78, 71, 0, 0}, new byte[]{83, 79, 85, 0, 0, 0}, new byte[]{83, 85, 0, 0, 0, 0}, new byte[]{83, 85, 65, 78, 0, 0}, new byte[]{83, 85, 73, 0, 0, 0}, new byte[]{83, 85, 78, 0, 0, 0}, new byte[]{83, 85, 79, 0, 0, 0}, new byte[]{84, 65, 0, 0, 0, 0}, new byte[]{84, 65, 73, 0, 0, 0}, new byte[]{84, 65, 78, 0, 0, 0}, new byte[]{84, 65, 78, 71, 0, 0}, new byte[]{84, 65, 79, 0, 0, 0}, new byte[]{84, 69, 0, 0, 0, 0}, new byte[]{84, 69, 78, 71, 0, 0}, new byte[]{84, 73, 0, 0, 0, 0}, new byte[]{84, 73, 65, 78, 0, 0}, new byte[]{84, 73, 65, 79, 0, 0}, new byte[]{84, 73, 69, 0, 0, 0}, new byte[]{84, 73, 78, 71, 0, 0}, new byte[]{84, 79, 78, 71, 0, 0}, new byte[]{84, 79, 85, 0, 0, 0}, new byte[]{84, 85, 0, 0, 0, 0}, new byte[]{84, 85, 65, 78, 0, 0}, new byte[]{84, 85, 73, 0, 0, 0}, new byte[]{84, 85, 78, 0, 0, 0}, new byte[]{84, 85, 79, 0, 0, 0}, new byte[]{87, 65, 0, 0, 0, 0}, new byte[]{87, 65, 73, 0, 0, 0}, new byte[]{87, 65, 78, 0, 0, 0}, new byte[]{87, 65, 78, 71, 0, 0}, new byte[]{87, 69, 73, 0, 0, 0}, new byte[]{87, 69, 78, 0, 0, 0}, new byte[]{87, 69, 78, 71, 0, 0}, new byte[]{87, 79, 0, 0, 0, 0}, new byte[]{87, 85, 0, 0, 0, 0}, new byte[]{88, 73, 0, 0, 0, 0}, new byte[]{88, 73, 65, 0, 0, 0}, new byte[]{88, 73, 65, 78, 0, 0}, new byte[]{88, 73, 65, 78, 71, 0}, new byte[]{88, 73, 65, 79, 0, 0}, new byte[]{88, 73, 69, 0, 0, 0}, new byte[]{88, 73, 78, 0, 0, 0}, new byte[]{88, 73, 78, 71, 0, 0}, new byte[]{88, 73, 79, 78, 71, 0}, new byte[]{88, 73, 85, 0, 0, 0}, new byte[]{88, 85, 0, 0, 0, 0}, new byte[]{88, 85, 65, 78, 0, 0}, new byte[]{88, 85, 69, 0, 0, 0}, new byte[]{88, 85, 78, 0, 0, 0}, new byte[]{89, 65, 0, 0, 0, 0}, new byte[]{89, 65, 78, 0, 0, 0}, new byte[]{89, 65, 78, 71, 0, 0}, new byte[]{89, 65, 79, 0, 0, 0}, new byte[]{89, 69, 0, 0, 0, 0}, new byte[]{89, 73, 0, 0, 0, 0}, new byte[]{89, 73, 78, 0, 0, 0}, new byte[]{89, 73, 78, 71, 0, 0}, new byte[]{89, 79, 0, 0, 0, 0}, new byte[]{89, 79, 78, 71, 0, 0}, new byte[]{89, 79, 85, 0, 0, 0}, new byte[]{89, 85, 0, 0, 0, 0}, new byte[]{89, 85, 65, 78, 0, 0}, new byte[]{89, 85, 69, 0, 0, 0}, new byte[]{89, 85, 78, 0, 0, 0}, new byte[]{90, 65, 0, 0, 0, 0}, new byte[]{90, 65, 73, 0, 0, 0}, new byte[]{90, 65, 78, 0, 0, 0}, new byte[]{90, 65, 78, 71, 0, 0}, new byte[]{90, 65, 79, 0, 0, 0}, new byte[]{90, 69, 0, 0, 0, 0}, new byte[]{90, 69, 73, 0, 0, 0}, new byte[]{90, 69, 78, 0, 0, 0}, new byte[]{90, 69, 78, 71, 0, 0}, new byte[]{90, 72, 65, 0, 0, 0}, new byte[]{90, 72, 65, 73, 0, 0}, new byte[]{90, 72, 65, 78, 0, 0}, new byte[]{90, 72, 65, 78, 71, 0}, new byte[]{90, 72, 65, 79, 0, 0}, new byte[]{90, 72, 69, 0, 0, 0}, new byte[]{90, 72, 69, 78, 0, 0}, new byte[]{90, 72, 69, 78, 71, 0}, new byte[]{90, 72, 73, 0, 0, 0}, new byte[]{90, 72, 79, 78, 71, 0}, new byte[]{90, 72, 79, 85, 0, 0}, new byte[]{90, 72, 85, 0, 0, 0}, new byte[]{90, 72, 85, 65, 0, 0}, new byte[]{90, 72, 85, 65, 73, 0}, new byte[]{90, 72, 85, 65, 78, 0}, new byte[]{90, 72, 85, 65, 78, 71}, new byte[]{90, 72, 85, 73, 0, 0}, new byte[]{90, 72, 85, 78, 0, 0}, new byte[]{90, 72, 85, 79, 0, 0}, new byte[]{90, 73, 0, 0, 0, 0}, new byte[]{90, 79, 78, 71, 0, 0}, new byte[]{90, 79, 85, 0, 0, 0}, new byte[]{90, 85, 0, 0, 0, 0}, new byte[]{90, 85, 65, 78, 0, 0}, new byte[]{90, 85, 73, 0, 0, 0}, new byte[]{90, 85, 78, 0, 0, 0}, new byte[]{90, 85, 79, 0, 0, 0}};
    private static HashMap<Character, String[]> a = new HashMap<>();
    private static HashMap<String, String[]> b = new HashMap<>();
    private static HashMap<Character, String> c = new HashMap<>();
    private static final Collator d = Collator.getInstance(Locale.CHINA);
    private static final char[] g = {'2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '7', '7', '7', '7', '8', '8', '8', '9', '9', '9', '9'};

    static {
        a.put((char) 38463, new String[]{"A", "E"});
        a.put((char) 33100, new String[]{"YAN", "A"});
        a.put((char) 25303, new String[]{"AO", "O", "NIU"});
        a.put((char) 25170, new String[]{"PA", "BA"});
        a.put((char) 34444, new String[]{"BANG", "BENG"});
        a.put((char) 34180, new String[]{"BAO", "BO"});
        a.put((char) 22561, new String[]{"BAO", "BU", "PU"});
        a.put((char) 26292, new String[]{"BAO", "PU"});
        a.put((char) 36146, new String[]{"BEN", "FEI", "BI"});
        a.put((char) 36153, new String[]{"FEI", "BI"});
        a.put((char) 33218, new String[]{"BI", "BEI"});
        a.put((char) 36767, new String[]{"PI", "BI"});
        a.put((char) 33536, new String[]{"FU", "BI"});
        a.put((char) 25153, new String[]{"BIAN", "PIAN"});
        a.put((char) 20415, new String[]{"BIAN", "PIAN"});
        a.put((char) 33152, new String[]{"PANG", "BANG"});
        a.put((char) 30917, new String[]{"PANG", "BANG"});
        a.put((char) 39584, new String[]{"BIAO", "PIAO"});
        a.put((char) 30058, new String[]{"FAN", "PAN", "BO"});
        a.put((char) 23387, new String[]{"BEI", "BO"});
        a.put((char) 24223, new String[]{"FEI", "BO"});
        a.put((char) 21093, new String[]{"BO", "BAO", "XUE"});
        a.put((char) 27850, new String[]{"BO", "PO", "BAN"});
        a.put((char) 20271, new String[]{"BO", "BAI"});
        a.put((char) 21340, new String[]{"BO", "BU"});
        a.put((char) 20263, new String[]{"CANG", "CHEN"});
        a.put((char) 34255, new String[]{"CANG", "ZANG"});
        a.put((char) 21442, new String[]{"CAN", "SHEN", "CEN"});
        a.put((char) 26366, new String[]{"CENG", "ZENG"});
        a.put((char) 22092, new String[]{"CENG", "CHENG"});
        a.put((char) 24046, new String[]{"CHA", "CHAI"});
        a.put((char) 26597, new String[]{"CHA", "ZHA"});
        a.put((char) 31109, new String[]{"CHAN", "SHAN"});
        a.put((char) 39076, new String[]{"CHAN", "ZHAN"});
        a.put((char) 23409, new String[]{"CHAN", "CAN"});
        a.put((char) 35059, new String[]{"SHANG", "CHANG"});
        a.put((char) 22330, new String[]{"CHANG", "CHANG"});
        a.put((char) 26216, new String[]{"CHEN", "CHANG", "ZE"});
        a.put((char) 38271, new String[]{"CHANG", "ZHANG"});
        a.put((char) 21378, new String[]{"CHANG", "AN", "HAN"});
        a.put((char) 22066, new String[]{"CHAO", "ZHAO", "ZHA"});
        a.put((char) 36710, new String[]{"CHE", "JU"});
        a.put((char) 31216, new String[]{"CHENG", "CHEN"});
        a.put((char) 28548, new String[]{"CHENG", "DENG"});
        a.put((char) 38107, new String[]{"DANG", "CHENG"});
        a.put((char) 20056, new String[]{"CHENG", "SHENG"});
        a.put((char) 26397, new String[]{"CHAO", "ZHAO"});
        a.put((char) 38241, new String[]{"XIN", "CHAN", "TAN"});
        a.put((char) 21273, new String[]{"SHI", "CHI"});
        a.put((char) 37079, new String[]{"XI", "CHI"});
        a.put((char) 27835, new String[]{"ZHI", "CHI"});
        a.put((char) 30259, new String[]{"CHOU", "LU"});
        a.put((char) 19985, new String[]{"CHOU", "NIU"});
        a.put((char) 33261, new String[]{"CHOU", "XIU"});
        a.put((char) 37325, new String[]{"ZHONG", "CHONG"});
        a.put((char) 31181, new String[]{"ZHONG", "CHONG"});
        a.put((char) 30044, new String[]{"CHU", "XU"});
        a.put((char) 38500, new String[]{"CHU", "XU"});
        a.put((char) 20256, new String[]{"CHUAN", "ZHUAN"});
        a.put((char) 21852, new String[]{"CHUO", "CHUAI"});
        a.put((char) 32496, new String[]{"CHUO", "CHAO"});
        a.put((char) 35098, new String[]{"ZHU", "CHU", "ZHE"});
        a.put((char) 26894, new String[]{"ZHUI", "CHUI"});
        a.put((char) 27425, new String[]{"CI", "CHI", "QI"});
        a.put((char) 20282, new String[]{"CI", "SI"});
        a.put((char) 20857, new String[]{"ZI", "CI"});
        a.put((char) 26526, new String[]{"CONG", "ZONG"});
        a.put((char) 25874, new String[]{"CUAN", "ZAN"});
        a.put((char) 21330, new String[]{"ZU", "CU"});
        a.put((char) 34928, new String[]{"SHUAI", "CUI"});
        a.put((char) 25774, new String[]{"CUO", "ZUO"});
        a.put((char) 22823, new String[]{"DA", "DAI"});
        a.put((char) 27795, new String[]{"TA", "DA"});
        a.put((char) 21333, new String[]{"DAN", "CHAN", "SHAN"});
        a.put((char) 21480, new String[]{"DAO", "TAO"});
        a.put((char) 25552, new String[]{"TI", "DI"});
        a.put((char) 36934, new String[]{"DI", "TI"});
        a.put((char) 32735, new String[]{"DI", "ZHAI"});
        a.put((char) 24471, new String[]{"DE", "DEI"});
        a.put((char) 38079, new String[]{"DIAN", "TIAN"});
        a.put((char) 20291, new String[]{"DIAN", "TIAN"});
        a.put((char) 20992, new String[]{"DAO", "DIAO"});
        a.put((char) 35843, new String[]{"DIAO", "TIAO"});
        a.put((char) 37117, new String[]{"DOU", "DU"});
        a.put((char) 24230, new String[]{"DU", "DUO"});
        a.put((char) 22244, new String[]{"TUN", "DUN"});
        a.put((char) 21542, new String[]{"FOU", "PI"});
        a.put((char) 33071, new String[]{"PU", "FU"});
        a.put((char) 36711, new String[]{"YA", "ZHA", "GA"});
        a.put((char) 25179, new String[]{"KANG", "GANG"});
        a.put((char) 30422, new String[]{"GAI", "GE"});
        a.put((char) 21679, new String[]{"GE", "KA", "LO"});
        a.put((char) 38761, new String[]{"GE", "JI"});
        a.put((char) 21512, new String[]{"HE", "GE"});
        a.put((char) 32473, new String[]{"GEI", "JI"});
        a.put((char) 39048, new String[]{"JING", "GENG"});
        a.put((char) 32418, new String[]{"HONG", "GONG"});
        a.put((char) 26552, new String[]{"GOU", "JU"});
        a.put((char) 21617, new String[]{"GUA", "GU"});
        a.put((char) 35895, new String[]{"GU", "YU"});
        a.put((char) 34411, new String[]{"CHONG", "GU"});
        a.put((char) 40516, new String[]{"HU", "GU"});
        a.put((char) 25324, new String[]{"KUO", "GUA"});
        a.put((char) 33694, new String[]{"GUAN", "WAN"});
        a.put((char) 32438, new String[]{"LUN", "GUAN"});
        a.put((char) 28805, new String[]{"JIONG", "GUI"});
        a.put((char) 26727, new String[]{"GUI", "HUI"});
        a.put((char) 28820, new String[]{"QUE", "GUI"});
        a.put((char) 26123, new String[]{"GUI", "JIONG"});
        a.put((char) 20250, new String[]{"HUI", "KUAI", "GUI"});
        a.put((char) 33445, new String[]{"JIE", "GAI"});
        a.put((char) 34430, new String[]{"XIA", "HA"});
        a.put((char) 36713, new String[]{"XUAN", "HAN"});
        a.put((char) 25750, new String[]{"KAN", "HAN"});
        a.put((char) 21683, new String[]{"KE", "HAI"});
        a.put((char) 24055, new String[]{"XIANG", "HANG"});
        a.put((char) 21549, new String[]{"KENG", "HANG"});
        a.put((char) 40664, new String[]{"MO", "HE", "MEI"});
        a.put((char) 21644, new String[]{"HE", "HU", "HUO"});
        a.put((char) 35977, new String[]{"HE", "HAO"});
        a.put((char) 40657, new String[]{"HEI", "HE"});
        a.put((char) 34425, new String[]{"HONG", "JIANG"});
        a.put((char) 37063, new String[]{"XUN", "HUAN"});
        a.put((char) 23536, new String[]{"HUAN", "XIAN"});
        a.put((char) 22855, new String[]{"QI", "JI"});
        a.put((char) 32521, new String[]{"JI", "QI"});
        a.put((char) 20552, new String[]{"JIE", "JI"});
        a.put((char) 31995, new String[]{"XI", "JI"});
        a.put((char) 31293, new String[]{"JI", "QI"});
        a.put((char) 20127, new String[]{"JI", "QI"});
        a.put((char) 35800, new String[]{"JIE", "JI"});
        a.put((char) 35760, new String[]{"JI", "JIE"});
        a.put((char) 21095, new String[]{"JU", "JI"});
        a.put((char) 31085, new String[]{"JI", "ZHA", "ZHAI"});
        a.put((char) 33540, new String[]{"QIE", "JIA"});
        a.put((char) 22204, new String[]{"JIAO", "JUE"});
        a.put((char) 20389, new String[]{"JIAO", "YAO"});
        a.put((char) 35282, new String[]{"JIAO", "JUE"});
        a.put((char) 33050, new String[]{"JIAO", "JUE"});
        a.put((char) 21119, new String[]{"JIAO", "CHAO"});
        a.put((char) 26657, new String[]{"XIAO", "JIAO"});
        a.put((char) 32564, new String[]{"JIAO", "ZHUO"});
        a.put((char) 35265, new String[]{"JIAN", "XIAN"});
        a.put((char) 38477, new String[]{"XIANG", "LONG", "JIANG"});
        a.put((char) 35299, new String[]{"JIE", "XIE"});
        a.put((char) 34249, new String[]{"JIE", "JI"});
        a.put((char) 30684, new String[]{"JIN", "QIN"});
        a.put((char) 21170, new String[]{"JIN", "JING"});
        a.put((char) 40863, new String[]{"GUI", "QIU", "CI", "JUN"});
        a.put((char) 21632, new String[]{"JU", "ZUI"});
        a.put((char) 29722, new String[]{"JU", "QU"});
        a.put((char) 33740, new String[]{"JUN", "XUN"});
        a.put((char) 38589, new String[]{"JUN", "JUAN"});
        a.put((char) 21345, new String[]{"KA", "QIA"});
        a.put((char) 30475, new String[]{"KAN", "KAN"});
        a.put((char) 25000, new String[]{"HAN", "KAN"});
        a.put((char) 22391, new String[]{"KE", "KE"});
        a.put((char) 22771, new String[]{"KE", "QIA"});
        a.put((char) 20811, new String[]{"KE", "KEI"});
        a.put((char) 38752, new String[]{"KAO", "KU"});
        a.put((char) 38551, new String[]{"WEI", "KUI"});
        a.put((char) 39740, new String[]{"GUI", "WEI", "KUI"});
        a.put((char) 33554, new String[]{"KUANG", "GUAN", "YUAN"});
        a.put((char) 21895, new String[]{"LA", "YAO"});
        a.put((char) 34013, new String[]{"LAN", "PIE"});
        a.put((char) 28889, new String[]{"LAO", "LUO", "PAO"});
        a.put((char) 38610, new String[]{"LUO", "LAO"});
        a.put((char) 32907, new String[]{"LE", "LEI"});
        a.put((char) 20048, new String[]{"LE", "YUE"});
        a.put((char) 20102, new String[]{"LE", "LIAO"});
        a.put((char) 20457, new String[]{"LIANG", "LIA"});
        a.put((char) 28518, new String[]{"LIAO", "LAO"});
        a.put((char) 30860, new String[]{"LU", "ZHOU", "LIU"});
        a.put((char) 20603, new String[]{"LOU", "LU"});
        a.put((char) 38706, new String[]{"LU", "LOU"});
        a.put((char) 25419, new String[]{"LU", "LUO"});
        a.put((char) 32511, new String[]{"LV", "LU"});
        a.put((char) 20845, new String[]{"LIU", "LU"});
        a.put((char) 32476, new String[]{"LUO", "LAO"});
        a.put((char) 33853, new String[]{"LUO", "LAO", "LA"});
        a.put((char) 25273, new String[]{"MA", "MO"});
        a.put((char) 33033, new String[]{"MAI", "MO"});
        a.put((char) 22475, new String[]{"MAI", "MAN"});
        a.put((char) 34067, new String[]{"MAN", "WAN"});
        a.put((char) 27667, new String[]{"MANG", "MENG"});
        a.put((char) 27809, new String[]{"MEI", "MO"});
        a.put((char) 31192, new String[]{HomePageUtils.MUSIC_SOURCE_MI, "BI"});
        a.put((char) 27852, new String[]{HomePageUtils.MUSIC_SOURCE_MI, "BI"});
        a.put((char) 20340, new String[]{HomePageUtils.MUSIC_SOURCE_MI, "NAI", "NI"});
        a.put((char) 35884, new String[]{"MIAO", "MIU"});
        a.put((char) 27169, new String[]{"MO", "MU"});
        a.put((char) 25705, new String[]{"MO", "MA", "SA"});
        a.put((char) 27597, new String[]{"MU", "WU"});
        a.put((char) 32554, new String[]{"MIU", "MIAO", "MOU"});
        a.put((char) 24324, new String[]{"NONG", "LONG"});
        a.put((char) 38590, new String[]{"NAN", "NING"});
        a.put((char) 30111, new String[]{"NUE", "YAO"});
        a.put((char) 20060, new String[]{"MIE", "NIE"});
        a.put((char) 23068, new String[]{"NA", "NUO"});
        a.put((char) 21306, new String[]{"QU", "OU"});
        a.put((char) 32321, new String[]{"FAN", "PO"});
        a.put((char) 36843, new String[]{"PO", "PAI"});
        a.put((char) 32982, new String[]{"PANG", "PAN"});
        a.put((char) 21032, new String[]{"PAO", "BAO"});
        a.put((char) 28846, new String[]{"PAO", "BAO"});
        a.put((char) 36898, new String[]{"FENG", "PANG"});
        a.put((char) 34028, new String[]{"PENG", "PANG"});
        a.put((char) 26420, new String[]{"PU", "PO", "PIAO"});
        a.put((char) 28689, new String[]{"PU", "BAO"});
        a.put((char) 26333, new String[]{"BAO", "PU"});
        a.put((char) 26646, new String[]{"XI", "QI"});
        a.put((char) 36426, new String[]{"XI", "QI"});
        a.put((char) 31293, new String[]{"JI", "QI"});
        a.put((char) 33640, new String[]{"XUN", "QIAN"});
        a.put((char) 31140, new String[]{"QIAN", "XUAN"});
        a.put((char) 24378, new String[]{"QIANG", "JIANG"});
        a.put((char) 36228, new String[]{"QIE", "JU"});
        a.put((char) 20146, new String[]{"QIN", "QING"});
        a.put((char) 38592, new String[]{"QUE", "QIAO"});
        a.put((char) 20167, new String[]{"CHOU", "QIU"});
        a.put((char) 22280, new String[]{"QUAN", "JUAN"});
        a.put((char) 33394, new String[]{"SE", "SHAI"});
        a.put((char) 22622, new String[]{"SAI", "SE"});
        a.put((char) 21414, new String[]{"XIA", "SHA"});
        a.put((char) 21484, new String[]{"ZHAO", "SHAO"});
        a.put((char) 26441, new String[]{"SHAN", "SHA"});
        a.put((char) 27748, new String[]{"TANG", "SHANG"});
        a.put((char) 25342, new String[]{"SHI", "SHE"});
        a.put((char) 25240, new String[]{"ZHE", "SHE"});
        a.put((char) 20160, new String[]{"SHEN", "SHI"});
        a.put((char) 33882, new String[]{"SHEN", "REN"});
        a.put((char) 35782, new String[]{"SHI", "ZHI"});
        a.put((char) 20284, new String[]{"SI", "SHI"});
        a.put((char) 23646, new String[]{"SHU", "ZHU"});
        a.put((char) 29087, new String[]{"SHU", "SHOU"});
        a.put((char) 35828, new String[]{"SHUO", "SHUI"});
        a.put((char) 25968, new String[]{"SHU", "SHUO"});
        a.put((char) 24554, new String[]{"SONG", "ZHONG"});
        a.put((char) 23487, new String[]{"SU", "XIU"});
        a.put((char) 30509, new String[]{"GUI", "XU", "SUI"});
        a.put((char) 28601, new String[]{"DAN", "TAN"});
        a.put((char) 27795, new String[]{"TA", "DA"});
        a.put((char) 35203, new String[]{"TAN", "QIN"});
        a.put((char) 35843, new String[]{"DIAO", "TIAO"});
        a.put((char) 35114, new String[]{"TUI", "TUN"});
        a.put((char) 25299, new String[]{"TUO", "TA"});
        a.put((char) 22313, new String[]{"WEI", "XU"});
        a.put((char) 22996, new String[]{"WEI", "QU"});
        a.put((char) 23614, new String[]{"WEI", "YI"});
        a.put((char) 23561, new String[]{"WEI", "YU"});
        a.put((char) 36951, new String[]{"YI", "WEI"});
        a.put((char) 20044, new String[]{"WU", "LA"});
        a.put((char) 21523, new String[]{"XIA", "HE"});
        a.put((char) 32420, new String[]{"XIAN", "QIAN"});
        a.put((char) 34892, new String[]{"XING", "HANG", "HENG"});
        a.put((char) 30465, new String[]{"SHENG", "XING"});
        a.put((char) 21066, new String[]{"XIAO", "XUE"});
        a.put((char) 34880, new String[]{"XUE", "XIE"});
        a.put((char) 27575, new String[]{"YIN", "YAN"});
        a.put((char) 21693, new String[]{"YAN", "YE"});
        a.put((char) 32422, new String[]{"YUE", "YAO"});
        a.put((char) 38053, new String[]{"YAO", "YUE"});
        a.put((char) 21494, new String[]{"YE", "XIE"});
        a.put((char) 33406, new String[]{"AI", "YI"});
        a.put((char) 29096, new String[]{"YUN", "YU"});
        a.put((char) 21505, new String[]{"YU", "XU"});
        a.put((char) 21592, new String[]{"YUAN", "YUN"});
        a.put((char) 36128, new String[]{"YUAN", "YUN"});
        a.put((char) 21643, new String[]{"ZA", "ZE", "ZHA"});
        a.put((char) 25321, new String[]{"ZE", "ZHAI"});
        a.put((char) 25166, new String[]{"ZHA", "ZA"});
        a.put((char) 36711, new String[]{"YA", "ZHA"});
        a.put((char) 31896, new String[]{"NIAN", "ZHAN"});
        a.put((char) 29226, new String[]{"ZHUA", "ZHAO"});
        a.put((char) 30528, new String[]{"ZHAO", "ZHUO"});
        a.put((char) 27542, new String[]{"ZHI", "SHI"});
        a.put((char) 33879, new String[]{"ZHU", "ZHE", "ZHUO"});
        a.put((char) 24162, new String[]{"ZHUANG", "CHUANG"});
        a.put((char) 32508, new String[]{"ZONG", "ZENG"});
        a.put((char) 26590, new String[]{"ZUO", "ZHA"});
        a.put((char) 20180, new String[]{"ZI", "ZAI"});
        a.put((char) 20869, new String[]{"NEI"});
        a.put((char) 30655, new String[]{"QU"});
        a.put((char) 26469, new String[]{"LAI"});
        a.put((char) 21449, new String[]{"CHA"});
        a.put((char) 22905, new String[]{"TA"});
        a.put((char) 20799, new String[]{"ER"});
        a.put((char) 27784, new String[]{"SHEN"});
        a.put((char) 36158, new String[]{"JIA"});
        b.put("单于", new String[]{"CHAN", "YU"});
        b.put("长孙", new String[]{"ZHANG", "SUN"});
        b.put("子车", new String[]{"ZI", "JU"});
        b.put("万俟", new String[]{"MO", "QI"});
        b.put("澹台", new String[]{"TAN", "TAI"});
        b.put("尉迟", new String[]{"YU", "CHI"});
        c.put((char) 36158, "JIA");
        c.put((char) 27784, "SHEN");
        c.put((char) 21340, "BU");
        c.put((char) 34180, "BO");
        c.put((char) 23387, "BO");
        c.put((char) 36146, "BEN");
        c.put((char) 36153, "FEI");
        c.put((char) 27850, "BAN");
        c.put((char) 33536, "BI");
        c.put((char) 24223, "BO");
        c.put((char) 30058, "BO");
        c.put((char) 35098, "CHU");
        c.put((char) 37325, "CHONG");
        c.put((char) 21378, "HAN");
        c.put((char) 20256, "CHUAN");
        c.put((char) 21442, "CAN");
        c.put((char) 31181, "CHONG");
        c.put((char) 37079, "CHI");
        c.put((char) 38241, "CHAN");
        c.put((char) 26397, "CHAO");
        c.put((char) 27835, "CHI");
        c.put((char) 21852, "CHUAI");
        c.put((char) 34928, "CUI");
        c.put((char) 26216, "CHANG");
        c.put((char) 19985, "CHOU");
        c.put((char) 30259, "CHOU");
        c.put((char) 38271, "CHANG");
        c.put((char) 27425, "QI");
        c.put((char) 36710, "CHE");
        c.put((char) 32735, "ZHAI");
        c.put((char) 20291, "DIAN");
        c.put((char) 20992, "DIAO");
        c.put((char) 35843, "DIAO");
        c.put((char) 36934, "DI");
        c.put((char) 30422, "GE");
        c.put((char) 28805, "GUI");
        c.put((char) 34411, "GU");
        c.put((char) 28820, "GUI");
        c.put((char) 26123, "GUI");
        c.put((char) 20250, "GUI");
        c.put((char) 33445, "GAI");
        c.put((char) 33554, "KUANG");
        c.put((char) 37063, "HUAN");
        c.put((char) 24055, "XIANG");
        c.put((char) 40657, "HE");
        c.put((char) 36713, "HAN");
        c.put((char) 25750, "HAN");
        c.put((char) 40664, "HE");
        c.put((char) 35265, "JIAN");
        c.put((char) 38477, "JIANG");
        c.put((char) 35282, "JIAO");
        c.put((char) 32564, "JIAO");
        c.put((char) 35760, "JI");
        c.put((char) 29722, "JU");
        c.put((char) 21095, "JI");
        c.put((char) 38589, "JUAN");
        c.put((char) 38551, "KUI");
        c.put((char) 39740, "KUI");
        c.put((char) 25000, "KAN");
        c.put((char) 38752, "KU");
        c.put((char) 20048, "YUE");
        c.put((char) 20845, "LU");
        c.put((char) 21895, "LA");
        c.put((char) 38610, "LUO");
        c.put((char) 20102, "LIAO");
        c.put((char) 32554, "MIAO");
        c.put((char) 20340, HomePageUtils.MUSIC_SOURCE_MI);
        c.put((char) 35884, "MIAO");
        c.put((char) 20060, "NIE");
        c.put((char) 38590, "NING");
        c.put((char) 21306, "OU");
        c.put((char) 36898, "PANG");
        c.put((char) 34028, "PENG");
        c.put((char) 26420, "PIAO");
        c.put((char) 32321, "PO");
        c.put((char) 20415, "PIAN");
        c.put((char) 20167, "QIU");
        c.put((char) 21345, "QIA");
        c.put((char) 35203, "TAN");
        c.put((char) 31140, "QIAN");
        c.put((char) 21484, "SHAO");
        c.put((char) 20160, "SHI");
        c.put((char) 25240, "SHE");
        c.put((char) 30509, "SUI");
        c.put((char) 35299, "XIE");
        c.put((char) 31995, "XI");
        c.put((char) 24055, "XIANG");
        c.put((char) 38500, "XU");
        c.put((char) 23536, "XIAN");
        c.put((char) 21592, "YUAN");
        c.put((char) 36128, "YUAN");
        c.put((char) 26366, "ZENG");
        c.put((char) 26597, "ZHA");
        c.put((char) 20256, "CHUAN");
        c.put((char) 21484, "SHAO");
        c.put((char) 31085, "ZHAI");
    }

    protected HanziToPinyinICS(boolean z) {
        this.f = z;
    }

    public static HanziToPinyinICS getInstance() {
        synchronized (HanziToPinyinICS.class) {
            if (e != null) {
                return e;
            }
            e = new HanziToPinyinICS(true);
            return e;
        }
    }

    private HanziToPinyin.Token a(char c2) {
        int i;
        int i2;
        HanziToPinyin.Token token = new HanziToPinyin.Token();
        String ch = Character.toString(c2);
        token.source = ch;
        if (c2 < 256) {
            token.type = 1;
            token.target = ch;
            return token;
        } else if (c2 < 13312) {
            token.type = 3;
            token.target = ch;
            return token;
        } else {
            String[] strArr = a.get(Character.valueOf(c2));
            int i3 = 0;
            if (strArr != null) {
                token.type = 2;
                token.polyPhones = strArr;
                token.target = token.polyPhones[0];
                return token;
            }
            int compare = d.compare(ch, "阿");
            if (compare < 0) {
                token.type = 3;
                token.target = ch;
                return token;
            }
            if (compare == 0) {
                token.type = 2;
                i = 0;
            } else {
                compare = d.compare(ch, "蓙");
                if (compare > 0) {
                    token.type = 3;
                    token.target = ch;
                    return token;
                } else if (compare == 0) {
                    token.type = 2;
                    i = UNIHANS.length - 1;
                } else {
                    i = -1;
                }
            }
            token.type = 2;
            if (i < 0) {
                int length = UNIHANS.length - 1;
                i2 = compare;
                int i4 = 0;
                while (i4 <= length) {
                    i = (i4 + length) / 2;
                    i2 = d.compare(ch, Character.toString(UNIHANS[i]));
                    if (i2 == 0) {
                        break;
                    } else if (i2 > 0) {
                        i4 = i + 1;
                    } else {
                        length = i - 1;
                    }
                }
            } else {
                i2 = compare;
            }
            if (i2 < 0) {
                i--;
            }
            StringBuilder sb = new StringBuilder();
            while (true) {
                byte[][] bArr = PINYINS;
                if (i3 >= bArr[i].length || bArr[i][i3] == 0) {
                    break;
                }
                sb.append((char) bArr[i][i3]);
                i3++;
            }
            token.target = sb.toString();
            return token;
        }
    }

    private ArrayList<HanziToPinyin.Token> a(String str) {
        String substring;
        String[] strArr;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<HanziToPinyin.Token> arrayList = new ArrayList<>();
        if (str.length() < 2 || (strArr = b.get((substring = str.substring(0, 2)))) == null) {
            Character valueOf = Character.valueOf(str.charAt(0));
            String str2 = c.get(valueOf);
            if (str2 == null) {
                return null;
            }
            HanziToPinyin.Token token = new HanziToPinyin.Token();
            token.type = 2;
            token.source = valueOf.toString();
            token.target = str2;
            arrayList.add(token);
            return arrayList;
        }
        for (int i = 0; i < strArr.length; i++) {
            HanziToPinyin.Token token2 = new HanziToPinyin.Token();
            token2.type = 2;
            token2.source = String.valueOf(substring.charAt(i));
            token2.target = strArr[i];
            arrayList.add(token2);
        }
        return arrayList;
    }

    public ArrayList<HanziToPinyin.Token> get(String str) {
        return get(str, true, true);
    }

    public ArrayList<HanziToPinyin.Token> get(String str, boolean z, boolean z2) {
        ArrayList<HanziToPinyin.Token> a2;
        ArrayList<HanziToPinyin.Token> arrayList = new ArrayList<>();
        if (!this.f || TextUtils.isEmpty(str)) {
            return arrayList;
        }
        int i = 0;
        if (!z2 && (a2 = a(str)) != null && a2.size() > 0) {
            arrayList.addAll(a2);
            i = a2.size();
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt == ' ') {
                if (sb.length() > 0) {
                    a(sb, arrayList, i2);
                }
                if (!z) {
                    String valueOf = String.valueOf(' ');
                    arrayList.add(new HanziToPinyin.Token(3, valueOf, valueOf));
                }
                i2 = 3;
            } else if (charAt < 256) {
                if (i2 != 1 && sb.length() > 0) {
                    a(sb, arrayList, i2);
                }
                sb.append(charAt);
                i2 = 1;
            } else if (charAt < 13312) {
                if (i2 != 3 && sb.length() > 0) {
                    a(sb, arrayList, i2);
                }
                sb.append(charAt);
                i2 = 3;
            } else {
                HanziToPinyin.Token a3 = a(charAt);
                if (a3.type == 2) {
                    if (sb.length() > 0) {
                        a(sb, arrayList, i2);
                    }
                    arrayList.add(a3);
                    i2 = 2;
                } else {
                    if (i2 != a3.type && sb.length() > 0) {
                        a(sb, arrayList, i2);
                    }
                    i2 = a3.type;
                    sb.append(charAt);
                }
            }
            i++;
        }
        if (sb.length() > 0) {
            a(sb, arrayList, i2);
        }
        return arrayList;
    }

    private void a(StringBuilder sb, ArrayList<HanziToPinyin.Token> arrayList, int i) {
        String sb2 = sb.toString();
        arrayList.add(new HanziToPinyin.Token(i, sb2, sb2));
        sb.setLength(0);
    }

    public static String formatCharToT9(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= 'A' && charAt <= 'Z') {
                sb.append(g[charAt - 'A']);
            } else if (charAt >= 'a' && charAt <= 'z') {
                sb.append(g[charAt - 'a']);
            } else if (charAt >= '0' && charAt <= '9') {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static char formatCharToT9(char c2) {
        if (c2 >= 'A' && c2 <= 'Z') {
            return g[c2 - 'A'];
        }
        if (c2 >= 'a' && c2 <= 'z') {
            return g[c2 - 'a'];
        }
        if (c2 < '0' || c2 > '9') {
            return (char) 0;
        }
        return c2;
    }
}
