package com.mijiasdk.bleserver.protocol.bouncycastle.crypto.engines;

import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.CipherParameters;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.DataLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.OutputLengthException;
import com.mijiasdk.bleserver.protocol.bouncycastle.crypto.params.KeyParameter;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Arrays;
import com.mijiasdk.bleserver.protocol.bouncycastle.util.Pack;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;
import java.lang.reflect.Array;
import okio.Utf8;

/* loaded from: classes2.dex */
public class AESEngine implements BlockCipher {
    private static final byte[] a = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, Utf8.REPLACEMENT_BYTE, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, BinaryMemcacheOpcodes.GATK, -61, 24, -106, 5, -102, 7, 18, Byte.MIN_VALUE, -30, -21, 39, -78, 117, 9, -125, HttpConstants.COMMA, 26, Ascii.ESC, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, ByteSourceJsonBootstrapper.UTF8_BOM_1, -86, -5, 67, 77, 51, -123, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88, 81, -93, SignedBytes.MAX_POWER_OF_TWO, -113, -110, -99, 56, -11, -68, -74, -38, BinaryMemcacheOpcodes.SASL_AUTH, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, HttpConstants.EQUALS, 100, 93, 25, 115, 96, DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, BinaryMemcacheOpcodes.GATKQ, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, Ascii.US, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, ByteSourceJsonBootstrapper.UTF8_BOM_3, -26, 66, 104, 65, -103, 45, 15, -80, 84, ByteSourceJsonBootstrapper.UTF8_BOM_2, 22};
    private static final byte[] b = {82, 9, 106, -43, 48, 54, -91, 56, ByteSourceJsonBootstrapper.UTF8_BOM_3, SignedBytes.MAX_POWER_OF_TWO, -93, -98, DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, BinaryMemcacheOpcodes.GATK, HttpConstants.EQUALS, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, BinaryMemcacheOpcodes.GATKQ, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, HttpConstants.COMMA, 30, -113, -54, Utf8.REPLACEMENT_BYTE, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, Ascii.ESC, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, Ascii.US, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, Byte.MIN_VALUE, -20, 95, 96, 81, Byte.MAX_VALUE, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, ByteSourceJsonBootstrapper.UTF8_BOM_1, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, ByteSourceJsonBootstrapper.UTF8_BOM_2, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, BinaryMemcacheOpcodes.SASL_AUTH, 12, 125};
    private static final int[] c = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, Opcodes.IFNE, 47, 94, 188, 99, Opcodes.IFNULL, Opcodes.DCMPL, 53, 106, 212, 179, 125, 250, 239, 197, 145};
    private static final int[] d = {-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};
    private static final int[] e = {1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};
    private int f;
    private int[][] g = null;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private byte[] m;

    private static int a(int i) {
        return (((i & (-2139062144)) >>> 7) * 27) ^ ((2139062143 & i) << 1);
    }

    private static int a(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private static int b(int i) {
        int i2 = i & (-1061109568);
        int i3 = i2 ^ (i2 >>> 1);
        return (i3 >>> 5) ^ (((1061109567 & i) << 2) ^ (i3 >>> 2));
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "AES";
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void reset() {
    }

    private static int c(int i) {
        int a2 = a(i, 8) ^ i;
        int a3 = i ^ a(a2);
        int b2 = a2 ^ b(a3);
        return a3 ^ (b2 ^ a(b2, 16));
    }

    private static int d(int i) {
        byte[] bArr = a;
        return (bArr[(i >> 24) & 255] << 24) | (bArr[i & 255] & 255) | ((bArr[(i >> 8) & 255] & 255) << 8) | ((bArr[(i >> 16) & 255] & 255) << 16);
    }

    private int[][] a(byte[] bArr, boolean z) {
        int length = bArr.length;
        if (length < 16 || length > 32 || (length & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int i = length >>> 2;
        this.f = i + 6;
        int[][] iArr = (int[][]) Array.newInstance(int.class, this.f + 1, 4);
        if (i == 4) {
            int littleEndianToInt = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = littleEndianToInt;
            int littleEndianToInt2 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = littleEndianToInt2;
            int littleEndianToInt3 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = littleEndianToInt3;
            int littleEndianToInt4 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = littleEndianToInt4;
            int i2 = littleEndianToInt3;
            int i3 = littleEndianToInt;
            int i4 = littleEndianToInt4;
            for (int i5 = 1; i5 <= 10; i5++) {
                i3 ^= d(a(i4, 8)) ^ c[i5 - 1];
                iArr[i5][0] = i3;
                littleEndianToInt2 ^= i3;
                iArr[i5][1] = littleEndianToInt2;
                i2 ^= littleEndianToInt2;
                iArr[i5][2] = i2;
                i4 ^= i2;
                iArr[i5][3] = i4;
            }
        } else if (i == 6) {
            int littleEndianToInt5 = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = littleEndianToInt5;
            int littleEndianToInt6 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = littleEndianToInt6;
            int littleEndianToInt7 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = littleEndianToInt7;
            int littleEndianToInt8 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = littleEndianToInt8;
            int littleEndianToInt9 = Pack.littleEndianToInt(bArr, 16);
            iArr[1][0] = littleEndianToInt9;
            int littleEndianToInt10 = Pack.littleEndianToInt(bArr, 20);
            iArr[1][1] = littleEndianToInt10;
            int d2 = littleEndianToInt5 ^ (d(a(littleEndianToInt10, 8)) ^ 1);
            iArr[1][2] = d2;
            int i6 = littleEndianToInt6 ^ d2;
            iArr[1][3] = i6;
            int i7 = littleEndianToInt7 ^ i6;
            iArr[2][0] = i7;
            int i8 = littleEndianToInt8 ^ i7;
            iArr[2][1] = i8;
            int i9 = littleEndianToInt9 ^ i8;
            iArr[2][2] = i9;
            int i10 = littleEndianToInt10 ^ i9;
            iArr[2][3] = i10;
            int i11 = i9;
            int i12 = 2;
            int i13 = i8;
            int i14 = i7;
            int i15 = d2;
            int i16 = i10;
            for (int i17 = 3; i17 < 12; i17 += 3) {
                int d3 = d(a(i16, 8)) ^ i12;
                int i18 = i12 << 1;
                int i19 = i15 ^ d3;
                iArr[i17][0] = i19;
                int i20 = i6 ^ i19;
                iArr[i17][1] = i20;
                int i21 = i14 ^ i20;
                iArr[i17][2] = i21;
                int i22 = i13 ^ i21;
                iArr[i17][3] = i22;
                int i23 = i11 ^ i22;
                int i24 = i17 + 1;
                iArr[i24][0] = i23;
                int i25 = i16 ^ i23;
                iArr[i24][1] = i25;
                int d4 = d(a(i25, 8)) ^ i18;
                i12 = i18 << 1;
                i15 = i19 ^ d4;
                iArr[i24][2] = i15;
                i6 = i20 ^ i15;
                iArr[i24][3] = i6;
                i14 = i21 ^ i6;
                int i26 = i17 + 2;
                iArr[i26][0] = i14;
                i13 = i22 ^ i14;
                iArr[i26][1] = i13;
                i11 = i23 ^ i13;
                iArr[i26][2] = i11;
                i16 = i25 ^ i11;
                iArr[i26][3] = i16;
            }
            int d5 = (d(a(i16, 8)) ^ i12) ^ i15;
            iArr[12][0] = d5;
            int i27 = d5 ^ i6;
            iArr[12][1] = i27;
            int i28 = i27 ^ i14;
            iArr[12][2] = i28;
            iArr[12][3] = i28 ^ i13;
        } else if (i == 8) {
            int littleEndianToInt11 = Pack.littleEndianToInt(bArr, 0);
            iArr[0][0] = littleEndianToInt11;
            int littleEndianToInt12 = Pack.littleEndianToInt(bArr, 4);
            iArr[0][1] = littleEndianToInt12;
            int littleEndianToInt13 = Pack.littleEndianToInt(bArr, 8);
            iArr[0][2] = littleEndianToInt13;
            int littleEndianToInt14 = Pack.littleEndianToInt(bArr, 12);
            iArr[0][3] = littleEndianToInt14;
            int littleEndianToInt15 = Pack.littleEndianToInt(bArr, 16);
            iArr[1][0] = littleEndianToInt15;
            int littleEndianToInt16 = Pack.littleEndianToInt(bArr, 20);
            iArr[1][1] = littleEndianToInt16;
            int littleEndianToInt17 = Pack.littleEndianToInt(bArr, 24);
            iArr[1][2] = littleEndianToInt17;
            int littleEndianToInt18 = Pack.littleEndianToInt(bArr, 28);
            iArr[1][3] = littleEndianToInt18;
            int i29 = littleEndianToInt11;
            int i30 = littleEndianToInt17;
            int i31 = littleEndianToInt18;
            int i32 = littleEndianToInt16;
            int i33 = littleEndianToInt15;
            int i34 = 1;
            for (int i35 = 2; i35 < 14; i35 += 2) {
                int d6 = d(a(i31, 8)) ^ i34;
                i34 <<= 1;
                i29 ^= d6;
                iArr[i35][0] = i29;
                littleEndianToInt12 ^= i29;
                iArr[i35][1] = littleEndianToInt12;
                littleEndianToInt13 ^= littleEndianToInt12;
                iArr[i35][2] = littleEndianToInt13;
                littleEndianToInt14 ^= littleEndianToInt13;
                iArr[i35][3] = littleEndianToInt14;
                i33 ^= d(littleEndianToInt14);
                int i36 = i35 + 1;
                iArr[i36][0] = i33;
                i32 ^= i33;
                iArr[i36][1] = i32;
                i30 ^= i32;
                iArr[i36][2] = i30;
                i31 ^= i30;
                iArr[i36][3] = i31;
            }
            int d7 = (d(a(i31, 8)) ^ i34) ^ i29;
            iArr[14][0] = d7;
            int i37 = d7 ^ littleEndianToInt12;
            iArr[14][1] = i37;
            int i38 = i37 ^ littleEndianToInt13;
            iArr[14][2] = i38;
            iArr[14][3] = i38 ^ littleEndianToInt14;
        } else {
            throw new IllegalStateException("Should never get here");
        }
        if (!z) {
            for (int i39 = 1; i39 < this.f; i39++) {
                for (int i40 = 0; i40 < 4; i40++) {
                    iArr[i39][i40] = c(iArr[i39][i40]);
                }
            }
        }
        return iArr;
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.g = a(((KeyParameter) cipherParameters).getKey(), z);
            this.l = z;
            if (z) {
                this.m = Arrays.clone(a);
            } else {
                this.m = Arrays.clone(b);
            }
        } else {
            throw new IllegalArgumentException("invalid parameter passed to AES init - " + cipherParameters.getClass().getName());
        }
    }

    @Override // com.mijiasdk.bleserver.protocol.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.g == null) {
            throw new IllegalStateException("AES engine not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.l) {
            a(bArr, i);
            a(this.g);
            b(bArr2, i2);
            return 16;
        } else {
            a(bArr, i);
            b(this.g);
            b(bArr2, i2);
            return 16;
        }
    }

    private void a(byte[] bArr, int i) {
        int i2 = i + 1;
        this.h = bArr[i] & 255;
        int i3 = i2 + 1;
        this.h |= (bArr[i2] & 255) << 8;
        int i4 = i3 + 1;
        this.h |= (bArr[i3] & 255) << 16;
        int i5 = i4 + 1;
        this.h |= bArr[i4] << 24;
        int i6 = i5 + 1;
        this.i = bArr[i5] & 255;
        int i7 = i6 + 1;
        this.i = ((bArr[i6] & 255) << 8) | this.i;
        int i8 = i7 + 1;
        this.i |= (bArr[i7] & 255) << 16;
        int i9 = i8 + 1;
        this.i |= bArr[i8] << 24;
        int i10 = i9 + 1;
        this.j = bArr[i9] & 255;
        int i11 = i10 + 1;
        this.j = ((bArr[i10] & 255) << 8) | this.j;
        int i12 = i11 + 1;
        this.j |= (bArr[i11] & 255) << 16;
        int i13 = i12 + 1;
        this.j |= bArr[i12] << 24;
        int i14 = i13 + 1;
        this.k = bArr[i13] & 255;
        int i15 = i14 + 1;
        this.k = ((bArr[i14] & 255) << 8) | this.k;
        this.k |= (bArr[i15] & 255) << 16;
        this.k = (bArr[i15 + 1] << 24) | this.k;
    }

    private void b(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = this.h;
        bArr[i] = (byte) i3;
        int i4 = i2 + 1;
        bArr[i2] = (byte) (i3 >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i3 >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i3 >> 24);
        int i7 = i6 + 1;
        int i8 = this.i;
        bArr[i6] = (byte) i8;
        int i9 = i7 + 1;
        bArr[i7] = (byte) (i8 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 16);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i8 >> 24);
        int i12 = i11 + 1;
        int i13 = this.j;
        bArr[i11] = (byte) i13;
        int i14 = i12 + 1;
        bArr[i12] = (byte) (i13 >> 8);
        int i15 = i14 + 1;
        bArr[i14] = (byte) (i13 >> 16);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i13 >> 24);
        int i17 = i16 + 1;
        int i18 = this.k;
        bArr[i16] = (byte) i18;
        int i19 = i17 + 1;
        bArr[i17] = (byte) (i18 >> 8);
        bArr[i19] = (byte) (i18 >> 16);
        bArr[i19 + 1] = (byte) (i18 >> 24);
    }

    private void a(int[][] iArr) {
        char c2 = 0;
        int i = this.h ^ iArr[0][0];
        int i2 = 1;
        int i3 = this.i ^ iArr[0][1];
        int i4 = this.j ^ iArr[0][2];
        int i5 = this.k ^ iArr[0][3];
        int i6 = 1;
        while (i6 < this.f - i2) {
            int[] iArr2 = d;
            int a2 = (((a(iArr2[(i3 >> 8) & 255], 24) ^ iArr2[i & 255]) ^ a(d[(i4 >> 16) & 255], 16)) ^ a(d[(i5 >> 24) & 255], 8)) ^ iArr[i6][c2];
            int[] iArr3 = d;
            int a3 = (((a(iArr3[(i4 >> 8) & 255], 24) ^ iArr3[i3 & 255]) ^ a(d[(i5 >> 16) & 255], 16)) ^ a(d[(i >> 24) & 255], 8)) ^ iArr[i6][i2];
            int[] iArr4 = d;
            int a4 = (((a(iArr4[(i5 >> 8) & 255], 24) ^ iArr4[i4 & 255]) ^ a(d[(i >> 16) & 255], 16)) ^ a(d[(i3 >> 24) & 255], 8)) ^ iArr[i6][2];
            int[] iArr5 = d;
            int a5 = ((a(iArr5[(i >> 8) & 255], 24) ^ iArr5[i5 & 255]) ^ a(d[(i3 >> 16) & 255], 16)) ^ a(d[(i4 >> 24) & 255], 8);
            int i7 = i6 + 1;
            int i8 = iArr[i6][3] ^ a5;
            int[] iArr6 = d;
            i = (((a(iArr6[(a3 >> 8) & 255], 24) ^ iArr6[a2 & 255]) ^ a(d[(a4 >> 16) & 255], 16)) ^ a(d[(i8 >> 24) & 255], 8)) ^ iArr[i7][0];
            int[] iArr7 = d;
            i3 = (((a(iArr7[(a4 >> 8) & 255], 24) ^ iArr7[a3 & 255]) ^ a(d[(i8 >> 16) & 255], 16)) ^ a(d[(a2 >> 24) & 255], 8)) ^ iArr[i7][1];
            int[] iArr8 = d;
            i4 = (((a(iArr8[(i8 >> 8) & 255], 24) ^ iArr8[a4 & 255]) ^ a(d[(a2 >> 16) & 255], 16)) ^ a(d[(a3 >> 24) & 255], 8)) ^ iArr[i7][2];
            int[] iArr9 = d;
            int a6 = ((iArr9[i8 & 255] ^ a(iArr9[(a2 >> 8) & 255], 24)) ^ a(d[(a3 >> 16) & 255], 16)) ^ a(d[(a4 >> 24) & 255], 8);
            i6 = i7 + 1;
            i5 = a6 ^ iArr[i7][3];
            i2 = 1;
            c2 = 0;
        }
        int[] iArr10 = d;
        int a7 = (((a(iArr10[(i3 >> 8) & 255], 24) ^ iArr10[i & 255]) ^ a(d[(i4 >> 16) & 255], 16)) ^ a(d[(i5 >> 24) & 255], 8)) ^ iArr[i6][0];
        int[] iArr11 = d;
        int a8 = (((a(iArr11[(i4 >> 8) & 255], 24) ^ iArr11[i3 & 255]) ^ a(d[(i5 >> 16) & 255], 16)) ^ a(d[(i >> 24) & 255], 8)) ^ iArr[i6][1];
        int[] iArr12 = d;
        int a9 = (((a(iArr12[(i5 >> 8) & 255], 24) ^ iArr12[i4 & 255]) ^ a(d[(i >> 16) & 255], 16)) ^ a(d[(i3 >> 24) & 255], 8)) ^ iArr[i6][2];
        int[] iArr13 = d;
        int a10 = ((a(iArr13[(i >> 8) & 255], 24) ^ iArr13[i5 & 255]) ^ a(d[(i3 >> 16) & 255], 16)) ^ a(d[(i4 >> 24) & 255], 8);
        int i9 = i6 + 1;
        int i10 = iArr[i6][3] ^ a10;
        byte[] bArr = a;
        int i11 = (bArr[a7 & 255] & 255) ^ ((bArr[(a8 >> 8) & 255] & 255) << 8);
        byte[] bArr2 = this.m;
        this.h = ((i11 ^ ((bArr2[(a9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i10 >> 24) & 255] << 24)) ^ iArr[i9][0];
        this.i = ((((bArr2[a8 & 255] & 255) ^ ((bArr[(a9 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i10 >> 16) & 255] & 255) << 16)) ^ (bArr2[(a7 >> 24) & 255] << 24)) ^ iArr[i9][1];
        this.j = ((((bArr2[a9 & 255] & 255) ^ ((bArr[(i10 >> 8) & 255] & 255) << 8)) ^ ((bArr[(a7 >> 16) & 255] & 255) << 16)) ^ (bArr[(a8 >> 24) & 255] << 24)) ^ iArr[i9][2];
        this.k = ((((bArr2[i10 & 255] & 255) ^ ((bArr2[(a7 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(a8 >> 16) & 255] & 255) << 16)) ^ (bArr[(a9 >> 24) & 255] << 24)) ^ iArr[i9][3];
    }

    private void b(int[][] iArr) {
        int i = this.h;
        int i2 = this.f;
        char c2 = 0;
        int i3 = i ^ iArr[i2][0];
        int i4 = 1;
        int i5 = this.i ^ iArr[i2][1];
        int i6 = this.j ^ iArr[i2][2];
        int i7 = i2 - 1;
        int i8 = iArr[i2][3] ^ this.k;
        while (i7 > i4) {
            int[] iArr2 = e;
            int a2 = (((a(iArr2[(i8 >> 8) & 255], 24) ^ iArr2[i3 & 255]) ^ a(e[(i6 >> 16) & 255], 16)) ^ a(e[(i5 >> 24) & 255], 8)) ^ iArr[i7][c2];
            int[] iArr3 = e;
            int a3 = (((a(iArr3[(i3 >> 8) & 255], 24) ^ iArr3[i5 & 255]) ^ a(e[(i8 >> 16) & 255], 16)) ^ a(e[(i6 >> 24) & 255], 8)) ^ iArr[i7][i4];
            int[] iArr4 = e;
            int a4 = (((a(iArr4[(i5 >> 8) & 255], 24) ^ iArr4[i6 & 255]) ^ a(e[(i3 >> 16) & 255], 16)) ^ a(e[(i8 >> 24) & 255], 8)) ^ iArr[i7][2];
            int[] iArr5 = e;
            int a5 = a(e[(i3 >> 24) & 255], 8) ^ ((iArr5[i8 & 255] ^ a(iArr5[(i6 >> 8) & 255], 24)) ^ a(e[(i5 >> 16) & 255], 16));
            int i9 = i7 - 1;
            int i10 = a5 ^ iArr[i7][3];
            int[] iArr6 = e;
            i3 = (((a(iArr6[(i10 >> 8) & 255], 24) ^ iArr6[a2 & 255]) ^ a(e[(a4 >> 16) & 255], 16)) ^ a(e[(a3 >> 24) & 255], 8)) ^ iArr[i9][0];
            int[] iArr7 = e;
            i5 = (((a(iArr7[(a2 >> 8) & 255], 24) ^ iArr7[a3 & 255]) ^ a(e[(i10 >> 16) & 255], 16)) ^ a(e[(a4 >> 24) & 255], 8)) ^ iArr[i9][1];
            int[] iArr8 = e;
            i6 = (((a(iArr8[(a3 >> 8) & 255], 24) ^ iArr8[a4 & 255]) ^ a(e[(a2 >> 16) & 255], 16)) ^ a(e[(i10 >> 24) & 255], 8)) ^ iArr[i9][2];
            int[] iArr9 = e;
            i7 = i9 - 1;
            i8 = iArr[i9][3] ^ (((iArr9[i10 & 255] ^ a(iArr9[(a4 >> 8) & 255], 24)) ^ a(e[(a3 >> 16) & 255], 16)) ^ a(e[(a2 >> 24) & 255], 8));
            i4 = 1;
            c2 = 0;
        }
        int[] iArr10 = e;
        int a6 = (((a(iArr10[(i8 >> 8) & 255], 24) ^ iArr10[i3 & 255]) ^ a(e[(i6 >> 16) & 255], 16)) ^ a(e[(i5 >> 24) & 255], 8)) ^ iArr[i7][0];
        int[] iArr11 = e;
        int a7 = (((a(iArr11[(i3 >> 8) & 255], 24) ^ iArr11[i5 & 255]) ^ a(e[(i8 >> 16) & 255], 16)) ^ a(e[(i6 >> 24) & 255], 8)) ^ iArr[i7][1];
        int[] iArr12 = e;
        int a8 = (((a(iArr12[(i5 >> 8) & 255], 24) ^ iArr12[i6 & 255]) ^ a(e[(i3 >> 16) & 255], 16)) ^ a(e[(i8 >> 24) & 255], 8)) ^ iArr[i7][2];
        int[] iArr13 = e;
        int a9 = (a(e[(i3 >> 24) & 255], 8) ^ ((iArr13[i8 & 255] ^ a(iArr13[(i6 >> 8) & 255], 24)) ^ a(e[(i5 >> 16) & 255], 16))) ^ iArr[i7][3];
        byte[] bArr = b;
        byte[] bArr2 = this.m;
        this.h = ((((bArr[a6 & 255] & 255) ^ ((bArr2[(a9 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(a8 >> 16) & 255] & 255) << 16)) ^ (bArr[(a7 >> 24) & 255] << 24)) ^ iArr[0][0];
        this.i = ((((bArr2[a7 & 255] & 255) ^ ((bArr2[(a6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(a9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(a8 >> 24) & 255] << 24)) ^ iArr[0][1];
        this.j = ((((bArr2[a8 & 255] & 255) ^ ((bArr[(a7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(a6 >> 16) & 255] & 255) << 16)) ^ (bArr2[(a9 >> 24) & 255] << 24)) ^ iArr[0][2];
        this.k = ((((bArr[a9 & 255] & 255) ^ ((bArr2[(a8 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(a7 >> 16) & 255] & 255) << 16)) ^ (bArr2[(a6 >> 24) & 255] << 24)) ^ iArr[0][3];
    }
}
