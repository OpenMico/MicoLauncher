package com.xiaomi.smarthome.library.common.network;

import android.text.TextUtils;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes4.dex */
public class HanziToPinyin {
    private static HanziToPinyin d;
    private final boolean e;
    private static final char[] a = {21526, 21710, 23433, 32942, 20985, 20843, 25520, 25203, 37030, 21241, 38466, 22868, 20283, 30336, 30765, 28780, 24971, 27715, 20907, 30326, 23788, 22163, 20594, 21442, 20179, 25761, 20874, 23934, 22092, 25184, 25286, 36799, 20261, 25220, 36710, 25275, 38455, 21507, 20805, 25277, 20986, 25611, 24027, 20997, 21561, 26110, 36372, 21618, 20174, 20945, 31895, 27718, 23828, 37032, 25619, 21649, 21574, 20025, 24403, 20992, 24692, 25189, 28783, 20222, 22002, 25921, 20993, 29241, 19969, 19999, 19996, 21562, 21090, 32785, 22422, 21544, 22810, 22968, 22848, 38821, 32780, 21457, 24070, 21274, 39134, 20998, 20016, 35205, 20175, 22458, 32017, 22827, 29476, 26094, 20357, 24178, 20872, 30347, 25096, 32473, 26681, 25583, 21948, 22000, 24037, 21246, 20272, 40505, 20054, 20851, 20809, 24402, 20008, 35636, 21593, 22926, 21645, 20292, 22831, 33568, 35779, 40658, 25323, 20136, 21503, 40769, 20046, 33457, 24576, 27426, 24031, 28784, 26127, 21529, 19980, 21152, 25099, 27743, 33405, 38454, 24062, 22357, 20866, 20009, 20965, 23010, 22104, 20891, 21652, 24320, 37774, 24572, 23611, 21308, 32910, 21157, 31354, 25248, 25181, 22840, 33967, 23485, 21281, 20111, 22372, 25193, 25289, 20358, 20848, 21879, 25438, 20162, 38647, 33079, 26865, 26974, 21774, 20457, 23294, 33391, 36477, 22483, 21432, 25294, 28316, 40857, 23044, 22108, 23402, 25249, 38961, 22920, 22475, 39071, 29284, 29483, 21573, 38376, 27667, 21674, 23424, 21941, 20060, 27665, 21517, 35884, 25720, 29279, 27597, 25295, 33097, 22241, 22228, 23404, 35767, 23070, 23273, 33021, 37552, 25288, 23064, 40479, 25423, 22236, 23425, 22942, 20892, 32698, 22900, 22907, 40641, 37069, 22114, 35764, 22929, 25293, 30469, 27768, 25243, 21624, 21943, 21257, 20086, 29255, 21117, 27669, 23000, 20050, 38027, 21078, 20166, 19971, 25488, 21315, 21595, 24708, 20999, 20146, 38737, 23430, 29911, 21306, 23761, 28820, 22795, 21605, 31331, 33627, 24825, 20154, 25172, 26085, 25102, 21433, 37018, 22567, 26741, 38384, 25404, 20200, 27618, 19977, 26706, 25531, 33394, 26862, 20711, 26432, 31579, 23665, 20260, 24368, 22882, 30003, 21319, 23608, 21454, 20070, 21047, 34928, 38377, 21452, 35841, 21550, 35828, 21430, 24554, 20929, 33487, 29435, 22794, 23385, 21766, 20182, 23393, 22349, 27748, 22834, 24529, 33199, 21076, 22825, 26091, 24599, 21381, 28853, 20599, 20984, 28237, 25512, 21534, 35756, 21176, 27498, 24367, 23587, 21361, 26167, 32705, 25373, 20044, 22805, 21623, 20185, 20065, 28785, 20123, 24515, 20852, 20982, 20241, 25100, 21509, 30134, 28709, 20011, 21693, 22830, 24186, 20539, 19968, 20058, 24212, 21815, 20323, 20248, 32417, 22246, 26352, 33920, 24064, 28797, 20802, 29250, 20654, 21867, 40033, 24590, 26365, 21522, 25434, 27838, 24352, 20299, 34567, 36126, 40686, 20043, 20013, 24030, 26417, 25235, 25341, 19987, 22918, 38585, 23442, 21331, 20180, 23447, 37049, 31199, 21143, 22010, 23562, 26152};
    private static final byte[][] b = {new byte[]{65, 0, 0, 0, 0, 0}, new byte[]{65, 73, 0, 0, 0, 0}, new byte[]{65, 78, 0, 0, 0, 0}, new byte[]{65, 78, 71, 0, 0, 0}, new byte[]{65, 79, 0, 0, 0, 0}, new byte[]{66, 65, 0, 0, 0, 0}, new byte[]{66, 65, 73, 0, 0, 0}, new byte[]{66, 65, 78, 0, 0, 0}, new byte[]{66, 65, 78, 71, 0, 0}, new byte[]{66, 65, 79, 0, 0, 0}, new byte[]{66, 69, 73, 0, 0, 0}, new byte[]{66, 69, 78, 0, 0, 0}, new byte[]{66, 69, 78, 71, 0, 0}, new byte[]{66, 73, 0, 0, 0, 0}, new byte[]{66, 73, 65, 78, 0, 0}, new byte[]{66, 73, 65, 79, 0, 0}, new byte[]{66, 73, 69, 0, 0, 0}, new byte[]{66, 73, 78, 0, 0, 0}, new byte[]{66, 73, 78, 71, 0, 0}, new byte[]{66, 79, 0, 0, 0, 0}, new byte[]{66, 85, 0, 0, 0, 0}, new byte[]{67, 65, 0, 0, 0, 0}, new byte[]{67, 65, 73, 0, 0, 0}, new byte[]{67, 65, 78, 0, 0, 0}, new byte[]{67, 65, 78, 71, 0, 0}, new byte[]{67, 65, 79, 0, 0, 0}, new byte[]{67, 69, 0, 0, 0, 0}, new byte[]{67, 69, 78, 0, 0, 0}, new byte[]{67, 69, 78, 71, 0, 0}, new byte[]{67, 72, 65, 0, 0, 0}, new byte[]{67, 72, 65, 73, 0, 0}, new byte[]{67, 72, 65, 78, 0, 0}, new byte[]{67, 72, 65, 78, 71, 0}, new byte[]{67, 72, 65, 79, 0, 0}, new byte[]{67, 72, 69, 0, 0, 0}, new byte[]{67, 72, 69, 78, 0, 0}, new byte[]{67, 72, 69, 78, 71, 0}, new byte[]{67, 72, 73, 0, 0, 0}, new byte[]{67, 72, 79, 78, 71, 0}, new byte[]{67, 72, 79, 85, 0, 0}, new byte[]{67, 72, 85, 0, 0, 0}, new byte[]{67, 72, 85, 65, 73, 0}, new byte[]{67, 72, 85, 65, 78, 0}, new byte[]{67, 72, 85, 65, 78, 71}, new byte[]{67, 72, 85, 73, 0, 0}, new byte[]{67, 72, 85, 78, 0, 0}, new byte[]{67, 72, 85, 79, 0, 0}, new byte[]{67, 73, 0, 0, 0, 0}, new byte[]{67, 79, 78, 71, 0, 0}, new byte[]{67, 79, 85, 0, 0, 0}, new byte[]{67, 85, 0, 0, 0, 0}, new byte[]{67, 85, 65, 78, 0, 0}, new byte[]{67, 85, 73, 0, 0, 0}, new byte[]{67, 85, 78, 0, 0, 0}, new byte[]{67, 85, 79, 0, 0, 0}, new byte[]{68, 65, 0, 0, 0, 0}, new byte[]{68, 65, 73, 0, 0, 0}, new byte[]{68, 65, 78, 0, 0, 0}, new byte[]{68, 65, 78, 71, 0, 0}, new byte[]{68, 65, 79, 0, 0, 0}, new byte[]{68, 69, 0, 0, 0, 0}, new byte[]{68, 69, 78, 0, 0, 0}, new byte[]{68, 69, 78, 71, 0, 0}, new byte[]{68, 73, 0, 0, 0, 0}, new byte[]{68, 73, 65, 0, 0, 0}, new byte[]{68, 73, 65, 78, 0, 0}, new byte[]{68, 73, 65, 79, 0, 0}, new byte[]{68, 73, 69, 0, 0, 0}, new byte[]{68, 73, 78, 71, 0, 0}, new byte[]{68, 73, 85, 0, 0, 0}, new byte[]{68, 79, 78, 71, 0, 0}, new byte[]{68, 79, 85, 0, 0, 0}, new byte[]{68, 85, 0, 0, 0, 0}, new byte[]{68, 85, 65, 78, 0, 0}, new byte[]{68, 85, 73, 0, 0, 0}, new byte[]{68, 85, 78, 0, 0, 0}, new byte[]{68, 85, 79, 0, 0, 0}, new byte[]{69, 0, 0, 0, 0, 0}, new byte[]{69, 78, 0, 0, 0, 0}, new byte[]{69, 78, 71, 0, 0, 0}, new byte[]{69, 82, 0, 0, 0, 0}, new byte[]{70, 65, 0, 0, 0, 0}, new byte[]{70, 65, 78, 0, 0, 0}, new byte[]{70, 65, 78, 71, 0, 0}, new byte[]{70, 69, 73, 0, 0, 0}, new byte[]{70, 69, 78, 0, 0, 0}, new byte[]{70, 69, 78, 71, 0, 0}, new byte[]{70, 73, 65, 79, 0, 0}, new byte[]{70, 79, 0, 0, 0, 0}, new byte[]{70, 85, 0, 0, 0, 0}, new byte[]{70, 79, 85, 0, 0, 0}, new byte[]{70, 85, 0, 0, 0, 0}, new byte[]{71, 85, 73, 0, 0, 0}, new byte[]{71, 65, 0, 0, 0, 0}, new byte[]{71, 65, 73, 0, 0, 0}, new byte[]{71, 65, 78, 0, 0, 0}, new byte[]{71, 65, 78, 71, 0, 0}, new byte[]{71, 65, 79, 0, 0, 0}, new byte[]{71, 69, 0, 0, 0, 0}, new byte[]{71, 69, 73, 0, 0, 0}, new byte[]{71, 69, 78, 0, 0, 0}, new byte[]{71, 69, 78, 71, 0, 0}, new byte[]{74, 73, 69, 0, 0, 0}, new byte[]{71, 69, 0, 0, 0, 0}, new byte[]{71, 79, 78, 71, 0, 0}, new byte[]{71, 79, 85, 0, 0, 0}, new byte[]{71, 85, 0, 0, 0, 0}, new byte[]{71, 85, 65, 0, 0, 0}, new byte[]{71, 85, 65, 73, 0, 0}, new byte[]{71, 85, 65, 78, 0, 0}, new byte[]{71, 85, 65, 78, 71, 0}, new byte[]{71, 85, 73, 0, 0, 0}, new byte[]{71, 85, 78, 0, 0, 0}, new byte[]{71, 85, 65, 78, 0, 0}, new byte[]{71, 85, 79, 0, 0, 0}, new byte[]{72, 65, 0, 0, 0, 0}, new byte[]{72, 65, 73, 0, 0, 0}, new byte[]{72, 65, 78, 0, 0, 0}, new byte[]{72, 65, 78, 71, 0, 0}, new byte[]{72, 65, 79, 0, 0, 0}, new byte[]{72, 69, 0, 0, 0, 0}, new byte[]{72, 69, 73, 0, 0, 0}, new byte[]{72, 69, 78, 0, 0, 0}, new byte[]{72, 69, 78, 71, 0, 0}, new byte[]{72, 79, 78, 71, 0, 0}, new byte[]{72, 79, 85, 0, 0, 0}, new byte[]{72, 85, 0, 0, 0, 0}, new byte[]{72, 85, 65, 0, 0, 0}, new byte[]{72, 85, 65, 73, 0, 0}, new byte[]{72, 85, 65, 78, 0, 0}, new byte[]{72, 85, 65, 78, 71, 0}, new byte[]{72, 85, 73, 0, 0, 0}, new byte[]{72, 85, 78, 0, 0, 0}, new byte[]{72, 85, 79, 0, 0, 0}, new byte[]{74, 73, 0, 0, 0, 0}, new byte[]{74, 73, 65, 0, 0, 0}, new byte[]{74, 73, 65, 78, 0, 0}, new byte[]{74, 73, 65, 78, 71, 0}, new byte[]{74, 73, 65, 79, 0, 0}, new byte[]{74, 73, 69, 0, 0, 0}, new byte[]{74, 73, 78, 0, 0, 0}, new byte[]{74, 73, 78, 71, 0, 0}, new byte[]{74, 73, 79, 78, 71, 0}, new byte[]{74, 73, 85, 0, 0, 0}, new byte[]{74, 85, 0, 0, 0, 0}, new byte[]{74, 85, 65, 78, 0, 0}, new byte[]{74, 85, 69, 0, 0, 0}, new byte[]{74, 85, 78, 0, 0, 0}, new byte[]{75, 65, 0, 0, 0, 0}, new byte[]{75, 65, 73, 0, 0, 0}, new byte[]{75, 65, 78, 0, 0, 0}, new byte[]{75, 65, 78, 71, 0, 0}, new byte[]{75, 65, 79, 0, 0, 0}, new byte[]{75, 69, 0, 0, 0, 0}, new byte[]{75, 69, 78, 0, 0, 0}, new byte[]{75, 69, 78, 71, 0, 0}, new byte[]{75, 79, 78, 71, 0, 0}, new byte[]{75, 79, 85, 0, 0, 0}, new byte[]{75, 85, 0, 0, 0, 0}, new byte[]{75, 85, 65, 0, 0, 0}, new byte[]{75, 85, 65, 73, 0, 0}, new byte[]{75, 85, 65, 78, 0, 0}, new byte[]{75, 85, 65, 78, 71, 0}, new byte[]{75, 85, 73, 0, 0, 0}, new byte[]{75, 85, 78, 0, 0, 0}, new byte[]{75, 85, 79, 0, 0, 0}, new byte[]{76, 65, 0, 0, 0, 0}, new byte[]{76, 65, 73, 0, 0, 0}, new byte[]{76, 65, 78, 0, 0, 0}, new byte[]{76, 65, 78, 71, 0, 0}, new byte[]{76, 65, 79, 0, 0, 0}, new byte[]{76, 69, 0, 0, 0, 0}, new byte[]{76, 69, 73, 0, 0, 0}, new byte[]{76, 73, 0, 0, 0, 0}, new byte[]{76, 73, 78, 71, 0, 0}, new byte[]{76, 69, 78, 71, 0, 0}, new byte[]{76, 73, 0, 0, 0, 0}, new byte[]{76, 73, 65, 0, 0, 0}, new byte[]{76, 73, 65, 78, 0, 0}, new byte[]{76, 73, 65, 78, 71, 0}, new byte[]{76, 73, 65, 79, 0, 0}, new byte[]{76, 73, 69, 0, 0, 0}, new byte[]{76, 73, 78, 0, 0, 0}, new byte[]{76, 73, 78, 71, 0, 0}, new byte[]{76, 73, 85, 0, 0, 0}, new byte[]{76, 79, 78, 71, 0, 0}, new byte[]{76, 79, 85, 0, 0, 0}, new byte[]{76, 85, 0, 0, 0, 0}, new byte[]{76, 85, 65, 78, 0, 0}, new byte[]{76, 85, 78, 0, 0, 0}, new byte[]{76, 85, 79, 0, 0, 0}, new byte[]{77, 65, 0, 0, 0, 0}, new byte[]{77, 65, 73, 0, 0, 0}, new byte[]{77, 65, 78, 0, 0, 0}, new byte[]{77, 65, 78, 71, 0, 0}, new byte[]{77, 65, 79, 0, 0, 0}, new byte[]{77, 69, 73, 0, 0, 0}, new byte[]{77, 69, 78, 0, 0, 0}, new byte[]{77, 69, 78, 71, 0, 0}, new byte[]{77, 73, 0, 0, 0, 0}, new byte[]{77, 73, 65, 78, 0, 0}, new byte[]{77, 73, 65, 79, 0, 0}, new byte[]{77, 73, 69, 0, 0, 0}, new byte[]{77, 73, 78, 0, 0, 0}, new byte[]{77, 73, 78, 71, 0, 0}, new byte[]{77, 73, 85, 0, 0, 0}, new byte[]{77, 79, 0, 0, 0, 0}, new byte[]{77, 79, 85, 0, 0, 0}, new byte[]{77, 85, 0, 0, 0, 0}, new byte[]{78, 65, 0, 0, 0, 0}, new byte[]{78, 65, 73, 0, 0, 0}, new byte[]{78, 65, 78, 0, 0, 0}, new byte[]{78, 65, 78, 71, 0, 0}, new byte[]{78, 65, 79, 0, 0, 0}, new byte[]{78, 69, 0, 0, 0, 0}, new byte[]{78, 69, 73, 0, 0, 0}, new byte[]{78, 69, 78, 0, 0, 0}, new byte[]{78, 69, 78, 71, 0, 0}, new byte[]{78, 73, 0, 0, 0, 0}, new byte[]{78, 73, 65, 78, 0, 0}, new byte[]{78, 73, 65, 78, 71, 0}, new byte[]{78, 73, 65, 79, 0, 0}, new byte[]{78, 73, 69, 0, 0, 0}, new byte[]{78, 73, 78, 0, 0, 0}, new byte[]{78, 73, 78, 71, 0, 0}, new byte[]{78, 73, 85, 0, 0, 0}, new byte[]{78, 79, 78, 71, 0, 0}, new byte[]{78, 79, 85, 0, 0, 0}, new byte[]{78, 85, 0, 0, 0, 0}, new byte[]{78, 85, 65, 78, 0, 0}, new byte[]{78, 85, 78, 0, 0, 0}, new byte[]{78, 85, 79, 0, 0, 0}, new byte[]{79, 0, 0, 0, 0, 0}, new byte[]{79, 85, 0, 0, 0, 0}, new byte[]{80, 65, 0, 0, 0, 0}, new byte[]{80, 65, 73, 0, 0, 0}, new byte[]{80, 65, 78, 0, 0, 0}, new byte[]{80, 65, 78, 71, 0, 0}, new byte[]{80, 65, 79, 0, 0, 0}, new byte[]{80, 69, 73, 0, 0, 0}, new byte[]{80, 69, 78, 0, 0, 0}, new byte[]{80, 69, 78, 71, 0, 0}, new byte[]{80, 73, 0, 0, 0, 0}, new byte[]{80, 73, 65, 78, 0, 0}, new byte[]{80, 73, 65, 79, 0, 0}, new byte[]{80, 73, 69, 0, 0, 0}, new byte[]{80, 73, 78, 0, 0, 0}, new byte[]{80, 73, 78, 71, 0, 0}, new byte[]{80, 79, 0, 0, 0, 0}, new byte[]{80, 79, 85, 0, 0, 0}, new byte[]{80, 85, 0, 0, 0, 0}, new byte[]{81, 73, 0, 0, 0, 0}, new byte[]{81, 73, 65, 0, 0, 0}, new byte[]{81, 73, 65, 78, 0, 0}, new byte[]{81, 73, 65, 78, 71, 0}, new byte[]{81, 73, 65, 79, 0, 0}, new byte[]{81, 73, 69, 0, 0, 0}, new byte[]{81, 73, 78, 0, 0, 0}, new byte[]{81, 73, 78, 71, 0, 0}, new byte[]{81, 73, 79, 78, 71, 0}, new byte[]{81, 73, 85, 0, 0, 0}, new byte[]{81, 85, 0, 0, 0, 0}, new byte[]{81, 85, 65, 78, 0, 0}, new byte[]{81, 85, 69, 0, 0, 0}, new byte[]{81, 85, 78, 0, 0, 0}, new byte[]{82, 65, 78, 0, 0, 0}, new byte[]{82, 65, 78, 71, 0, 0}, new byte[]{82, 65, 79, 0, 0, 0}, new byte[]{82, 69, 0, 0, 0, 0}, new byte[]{82, 69, 78, 0, 0, 0}, new byte[]{82, 69, 78, 71, 0, 0}, new byte[]{82, 73, 0, 0, 0, 0}, new byte[]{82, 79, 78, 71, 0, 0}, new byte[]{82, 79, 85, 0, 0, 0}, new byte[]{82, 85, 0, 0, 0, 0}, new byte[]{82, 85, 65, 78, 0, 0}, new byte[]{82, 85, 73, 0, 0, 0}, new byte[]{82, 85, 78, 0, 0, 0}, new byte[]{82, 85, 79, 0, 0, 0}, new byte[]{83, 65, 0, 0, 0, 0}, new byte[]{83, 65, 73, 0, 0, 0}, new byte[]{83, 65, 78, 0, 0, 0}, new byte[]{83, 65, 78, 71, 0, 0}, new byte[]{83, 65, 79, 0, 0, 0}, new byte[]{83, 69, 0, 0, 0, 0}, new byte[]{83, 69, 78, 0, 0, 0}, new byte[]{83, 69, 78, 71, 0, 0}, new byte[]{83, 72, 65, 0, 0, 0}, new byte[]{83, 72, 65, 73, 0, 0}, new byte[]{83, 72, 65, 78, 0, 0}, new byte[]{83, 72, 65, 78, 71, 0}, new byte[]{83, 72, 65, 79, 0, 0}, new byte[]{83, 72, 69, 0, 0, 0}, new byte[]{83, 72, 69, 78, 0, 0}, new byte[]{83, 72, 69, 78, 71, 0}, new byte[]{83, 72, 73, 0, 0, 0}, new byte[]{83, 72, 79, 85, 0, 0}, new byte[]{83, 72, 85, 0, 0, 0}, new byte[]{83, 72, 85, 65, 0, 0}, new byte[]{83, 72, 85, 65, 73, 0}, new byte[]{83, 72, 85, 65, 78, 0}, new byte[]{83, 72, 85, 65, 78, 71}, new byte[]{83, 72, 85, 73, 0, 0}, new byte[]{83, 72, 85, 78, 0, 0}, new byte[]{83, 72, 85, 79, 0, 0}, new byte[]{83, 73, 0, 0, 0, 0}, new byte[]{83, 79, 78, 71, 0, 0}, new byte[]{83, 79, 85, 0, 0, 0}, new byte[]{83, 85, 0, 0, 0, 0}, new byte[]{83, 85, 65, 78, 0, 0}, new byte[]{83, 85, 73, 0, 0, 0}, new byte[]{83, 85, 78, 0, 0, 0}, new byte[]{83, 85, 79, 0, 0, 0}, new byte[]{84, 65, 0, 0, 0, 0}, new byte[]{84, 65, 73, 0, 0, 0}, new byte[]{84, 65, 78, 0, 0, 0}, new byte[]{84, 65, 78, 71, 0, 0}, new byte[]{84, 65, 79, 0, 0, 0}, new byte[]{84, 69, 0, 0, 0, 0}, new byte[]{84, 69, 78, 71, 0, 0}, new byte[]{84, 73, 0, 0, 0, 0}, new byte[]{84, 73, 65, 78, 0, 0}, new byte[]{84, 73, 65, 79, 0, 0}, new byte[]{84, 73, 69, 0, 0, 0}, new byte[]{84, 73, 78, 71, 0, 0}, new byte[]{84, 79, 78, 71, 0, 0}, new byte[]{84, 79, 85, 0, 0, 0}, new byte[]{84, 85, 0, 0, 0, 0}, new byte[]{84, 85, 65, 78, 0, 0}, new byte[]{84, 85, 73, 0, 0, 0}, new byte[]{84, 85, 78, 0, 0, 0}, new byte[]{84, 85, 79, 0, 0, 0}, new byte[]{87, 65, 0, 0, 0, 0}, new byte[]{87, 65, 73, 0, 0, 0}, new byte[]{87, 65, 78, 0, 0, 0}, new byte[]{87, 65, 78, 71, 0, 0}, new byte[]{87, 69, 73, 0, 0, 0}, new byte[]{87, 69, 78, 0, 0, 0}, new byte[]{87, 69, 78, 71, 0, 0}, new byte[]{87, 79, 0, 0, 0, 0}, new byte[]{87, 85, 0, 0, 0, 0}, new byte[]{88, 73, 0, 0, 0, 0}, new byte[]{88, 73, 65, 0, 0, 0}, new byte[]{88, 73, 65, 78, 0, 0}, new byte[]{88, 73, 65, 78, 71, 0}, new byte[]{88, 73, 65, 79, 0, 0}, new byte[]{88, 73, 69, 0, 0, 0}, new byte[]{88, 73, 78, 0, 0, 0}, new byte[]{88, 73, 78, 71, 0, 0}, new byte[]{88, 73, 79, 78, 71, 0}, new byte[]{88, 73, 85, 0, 0, 0}, new byte[]{88, 85, 0, 0, 0, 0}, new byte[]{88, 85, 65, 78, 0, 0}, new byte[]{88, 85, 69, 0, 0, 0}, new byte[]{88, 85, 78, 0, 0, 0}, new byte[]{89, 65, 0, 0, 0, 0}, new byte[]{89, 65, 78, 0, 0, 0}, new byte[]{89, 65, 78, 71, 0, 0}, new byte[]{89, 65, 79, 0, 0, 0}, new byte[]{89, 69, 0, 0, 0, 0}, new byte[]{89, 73, 0, 0, 0, 0}, new byte[]{89, 73, 78, 0, 0, 0}, new byte[]{89, 73, 78, 71, 0, 0}, new byte[]{89, 79, 0, 0, 0, 0}, new byte[]{89, 79, 78, 71, 0, 0}, new byte[]{89, 79, 85, 0, 0, 0}, new byte[]{89, 85, 0, 0, 0, 0}, new byte[]{89, 85, 65, 78, 0, 0}, new byte[]{89, 85, 69, 0, 0, 0}, new byte[]{89, 85, 78, 0, 0, 0}, new byte[]{90, 65, 0, 0, 0, 0}, new byte[]{90, 65, 73, 0, 0, 0}, new byte[]{90, 65, 78, 0, 0, 0}, new byte[]{90, 65, 78, 71, 0, 0}, new byte[]{90, 65, 79, 0, 0, 0}, new byte[]{90, 69, 0, 0, 0, 0}, new byte[]{90, 69, 73, 0, 0, 0}, new byte[]{90, 69, 78, 0, 0, 0}, new byte[]{90, 69, 78, 71, 0, 0}, new byte[]{90, 72, 65, 0, 0, 0}, new byte[]{90, 72, 65, 73, 0, 0}, new byte[]{90, 72, 65, 78, 0, 0}, new byte[]{90, 72, 65, 78, 71, 0}, new byte[]{90, 72, 65, 79, 0, 0}, new byte[]{90, 72, 69, 0, 0, 0}, new byte[]{90, 72, 69, 78, 0, 0}, new byte[]{90, 72, 69, 78, 71, 0}, new byte[]{90, 72, 73, 0, 0, 0}, new byte[]{90, 72, 79, 78, 71, 0}, new byte[]{90, 72, 79, 85, 0, 0}, new byte[]{90, 72, 85, 0, 0, 0}, new byte[]{90, 72, 85, 65, 0, 0}, new byte[]{90, 72, 85, 65, 73, 0}, new byte[]{90, 72, 85, 65, 78, 0}, new byte[]{90, 72, 85, 65, 78, 71}, new byte[]{90, 72, 85, 73, 0, 0}, new byte[]{90, 72, 85, 78, 0, 0}, new byte[]{90, 72, 85, 79, 0, 0}, new byte[]{90, 73, 0, 0, 0, 0}, new byte[]{90, 79, 78, 71, 0, 0}, new byte[]{90, 79, 85, 0, 0, 0}, new byte[]{90, 85, 0, 0, 0, 0}, new byte[]{90, 85, 65, 78, 0, 0}, new byte[]{90, 85, 73, 0, 0, 0}, new byte[]{90, 85, 78, 0, 0, 0}, new byte[]{90, 85, 79, 0, 0, 0}};
    private static final Collator c = Collator.getInstance(Locale.CHINA);
    private static final char[] f = {'2', '2', '2', '3', '3', '3', '4', '4', '4', '5', '5', '5', '6', '6', '6', '7', '7', '7', '7', '8', '8', '8', '9', '9', '9', '9'};

    /* loaded from: classes4.dex */
    public static class Token {
        public static final int LATIN = 1;
        public static final int PINYIN = 2;
        public static final char SEPARATOR = ' ';
        public static final int UNKNOWN = 3;
        public String[] polyPhones;
        public String source;
        public String target;
        public int type;

        public Token() {
        }

        public Token(int i, String str, String str2) {
            this.type = i;
            this.source = str;
            this.target = str2;
        }
    }

    protected HanziToPinyin(boolean z) {
        this.e = z;
    }

    public static HanziToPinyin getInstance() {
        synchronized (HanziToPinyin.class) {
            if (d != null) {
                return d;
            }
            d = new HanziToPinyin(true);
            return d;
        }
    }

    private Token a(char c2) {
        int i;
        int i2;
        Token token = new Token();
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
            int compare = c.compare(ch, "???");
            if (compare < 0) {
                token.type = 3;
                token.target = ch;
                return token;
            }
            int i3 = 0;
            if (compare == 0) {
                token.type = 2;
                i = 0;
            } else {
                compare = c.compare(ch, "???");
                if (compare > 0) {
                    token.type = 3;
                    token.target = ch;
                    return token;
                } else if (compare == 0) {
                    token.type = 2;
                    i = a.length - 1;
                } else {
                    i = -1;
                }
            }
            token.type = 2;
            if (i < 0) {
                int length = a.length - 1;
                i2 = compare;
                int i4 = 0;
                while (i4 <= length) {
                    i = (i4 + length) / 2;
                    i2 = c.compare(ch, Character.toString(a[i]));
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
                byte[][] bArr = b;
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

    public ArrayList<Token> get(String str) {
        return get(str, true);
    }

    public ArrayList<Token> get(String str, boolean z) {
        ArrayList<Token> arrayList = new ArrayList<>();
        if (!this.e || TextUtils.isEmpty(str)) {
            return arrayList;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == ' ') {
                if (sb.length() > 0) {
                    a(sb, arrayList, i);
                }
                if (!z) {
                    String valueOf = String.valueOf(' ');
                    arrayList.add(new Token(3, valueOf, valueOf));
                }
            } else if (charAt < 256) {
                if (i != 1 && sb.length() > 0) {
                    a(sb, arrayList, i);
                }
                sb.append(charAt);
                i = 1;
            } else if (charAt < 13312) {
                if (i != 3 && sb.length() > 0) {
                    a(sb, arrayList, i);
                }
                sb.append(charAt);
                i = 3;
            } else {
                Token a2 = a(charAt);
                if (a2.type == 2) {
                    if (sb.length() > 0) {
                        a(sb, arrayList, i);
                    }
                    arrayList.add(a2);
                    i = 2;
                } else {
                    if (i != a2.type && sb.length() > 0) {
                        a(sb, arrayList, i);
                    }
                    i = a2.type;
                    sb.append(charAt);
                }
            }
        }
        if (sb.length() > 0) {
            a(sb, arrayList, i);
        }
        return arrayList;
    }

    private void a(StringBuilder sb, ArrayList<Token> arrayList, int i) {
        String sb2 = sb.toString();
        arrayList.add(new Token(i, sb2, sb2));
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
                sb.append(f[charAt - 'A']);
            } else if (charAt >= 'a' && charAt <= 'z') {
                sb.append(f[charAt - 'a']);
            } else if (charAt >= '0' && charAt <= '9') {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
