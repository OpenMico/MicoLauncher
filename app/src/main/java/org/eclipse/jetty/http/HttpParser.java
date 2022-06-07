package org.eclipse.jetty.http;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.io.bio.StreamEndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HttpParser implements Parser {
    private static final Logger LOG = Log.getLogger(HttpParser.class);
    public static final int STATE_CHUNK = 6;
    public static final int STATE_CHUNKED_CONTENT = 3;
    public static final int STATE_CHUNK_PARAMS = 5;
    public static final int STATE_CHUNK_SIZE = 4;
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 0;
    public static final int STATE_END0 = -8;
    public static final int STATE_END1 = -7;
    public static final int STATE_EOF_CONTENT = 1;
    public static final int STATE_FIELD0 = -13;
    public static final int STATE_FIELD2 = -6;
    public static final int STATE_HEADER = -5;
    public static final int STATE_HEADER_IN_NAME = -3;
    public static final int STATE_HEADER_IN_VALUE = -1;
    public static final int STATE_HEADER_NAME = -4;
    public static final int STATE_HEADER_VALUE = -2;
    public static final int STATE_SEEKING_EOF = 7;
    public static final int STATE_SPACE1 = -12;
    public static final int STATE_SPACE2 = -9;
    public static final int STATE_START = -14;
    public static final int STATE_STATUS = -11;
    public static final int STATE_URI = -10;
    private Buffer _body;
    private Buffer _buffer;
    private final Buffers _buffers;
    private BufferCache.CachedBuffer _cached;
    protected int _chunkLength;
    protected int _chunkPosition;
    protected long _contentLength;
    protected long _contentPosition;
    protected final View _contentView;
    private final EndPoint _endp;
    protected byte _eol;
    private boolean _forceContentBuffer;
    private final EventHandler _handler;
    private boolean _headResponse;
    private Buffer _header;
    protected int _length;
    private String _multiLineValue;
    private boolean _persistent;
    private int _responseStatus;
    protected int _state;
    private final View.CaseInsensitive _tok0;
    private final View.CaseInsensitive _tok1;

    /* loaded from: classes5.dex */
    public static abstract class EventHandler {
        public abstract void content(Buffer buffer) throws IOException;

        public void earlyEOF() {
        }

        public void headerComplete() throws IOException {
        }

        public void messageComplete(long j) throws IOException {
        }

        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
        }

        public abstract void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException;

        public abstract void startResponse(Buffer buffer, int i, Buffer buffer2) throws IOException;
    }

    public HttpParser(Buffer buffer, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._endp = null;
        this._buffers = null;
        this._header = buffer;
        this._buffer = buffer;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive(this._header);
        this._tok1 = new View.CaseInsensitive(this._header);
    }

    public HttpParser(Buffers buffers, EndPoint endPoint, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._buffers = buffers;
        this._endp = endPoint;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive();
        this._tok1 = new View.CaseInsensitive();
    }

    public long getContentLength() {
        return this._contentLength;
    }

    public long getContentRead() {
        return this._contentPosition;
    }

    public void setHeadResponse(boolean z) {
        this._headResponse = z;
    }

    public int getState() {
        return this._state;
    }

    public boolean inContentState() {
        return this._state > 0;
    }

    public boolean inHeaderState() {
        return this._state < 0;
    }

    public boolean isChunking() {
        return this._contentLength == -2;
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isIdle() {
        return isState(-14);
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isComplete() {
        return isState(0);
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isMoreInBuffer() throws IOException {
        Buffer buffer;
        Buffer buffer2 = this._header;
        return (buffer2 != null && buffer2.hasContent()) || ((buffer = this._body) != null && buffer.hasContent());
    }

    public boolean isState(int i) {
        return this._state == i;
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean isPersistent() {
        return this._persistent;
    }

    @Override // org.eclipse.jetty.http.Parser
    public void setPersistent(boolean z) {
        this._persistent = z;
        if (!this._persistent) {
            int i = this._state;
            if (i == 0 || i == -14) {
                this._state = 7;
            }
        }
    }

    public void parse() throws IOException {
        if (this._state == 0) {
            reset();
        }
        if (this._state == -14) {
            while (this._state != 0 && parseNext() >= 0) {
            }
            return;
        }
        throw new IllegalStateException("!START");
    }

    @Override // org.eclipse.jetty.http.Parser
    public boolean parseAvailable() throws IOException {
        Buffer buffer;
        boolean z = parseNext() > 0;
        while (!isComplete() && (buffer = this._buffer) != null && buffer.length() > 0) {
            z |= parseNext() > 0;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0745 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0772 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0778 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:391:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x07af A[Catch: HttpException -> 0x0a23, TryCatch #1 {HttpException -> 0x0a23, blocks: (B:389:0x079c, B:390:0x07a0, B:392:0x07af, B:394:0x07b8), top: B:524:0x079c }] */
    /* JADX WARN: Removed duplicated region for block: B:404:0x07f2 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0830 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0890 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cc A[Catch: HttpException -> 0x0a2c, TryCatch #4 {HttpException -> 0x0a2c, blocks: (B:3:0x0004, B:6:0x0009, B:8:0x000d, B:9:0x0013, B:11:0x0019, B:13:0x0021, B:15:0x002b, B:18:0x0035, B:19:0x0039, B:25:0x005d, B:29:0x0072, B:31:0x0078, B:33:0x0080, B:35:0x0084, B:36:0x00a4, B:38:0x00a8, B:39:0x00ab, B:41:0x00b1, B:42:0x00b6, B:43:0x00be, B:44:0x00c8, B:46:0x00cc, B:48:0x00d2, B:51:0x00d9, B:52:0x00de, B:54:0x00e0, B:56:0x00e2, B:58:0x00ef, B:59:0x00f7, B:61:0x0103, B:63:0x0107, B:65:0x010b, B:66:0x0110, B:69:0x011c, B:70:0x0120), top: B:529:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0965 A[Catch: HttpException -> 0x0a28, TryCatch #8 {HttpException -> 0x0a28, blocks: (B:76:0x0132, B:77:0x0139, B:78:0x013d, B:80:0x0141, B:82:0x0149, B:83:0x015e, B:85:0x0162, B:86:0x016c, B:87:0x01a0, B:90:0x01aa, B:91:0x01ad, B:93:0x01b1, B:94:0x01b6, B:95:0x01c9, B:97:0x01cd, B:99:0x01d5, B:100:0x01ea, B:102:0x01ee, B:103:0x01f8, B:104:0x022c, B:109:0x023a, B:110:0x023d, B:111:0x0246, B:113:0x024a, B:115:0x024e, B:116:0x0262, B:117:0x0268, B:118:0x026d, B:120:0x0271, B:121:0x0285, B:126:0x0293, B:127:0x0296, B:129:0x029c, B:130:0x02a1, B:131:0x02b5, B:133:0x02b9, B:135:0x02bd, B:136:0x02d1, B:137:0x02d7, B:139:0x02db, B:140:0x02ef, B:146:0x02ff, B:148:0x0303, B:150:0x030b, B:152:0x0313, B:154:0x0317, B:156:0x031b, B:157:0x031e, B:158:0x0326, B:160:0x032c, B:161:0x032f, B:162:0x0336, B:170:0x0349, B:172:0x0351, B:174:0x0357, B:176:0x035d, B:178:0x0363, B:180:0x0367, B:181:0x036d, B:183:0x0375, B:185:0x037a, B:186:0x0387, B:187:0x0388, B:189:0x0397, B:190:0x039d, B:192:0x03ab, B:193:0x03b1, B:196:0x03ba, B:197:0x03c1, B:198:0x03c2, B:205:0x03d1, B:210:0x03dd, B:212:0x03eb, B:217:0x03fe, B:223:0x040f, B:224:0x042c, B:229:0x043b, B:231:0x0448, B:233:0x045c, B:236:0x0482, B:238:0x048a, B:240:0x048e, B:242:0x0494, B:244:0x049a, B:247:0x04a1, B:248:0x04a8, B:250:0x04af, B:252:0x04b9, B:254:0x04c1, B:256:0x04cb, B:257:0x04d3, B:260:0x04e0, B:261:0x04e3, B:262:0x04e6, B:263:0x04ea, B:265:0x04f3, B:267:0x04f9, B:272:0x0503, B:274:0x050e, B:275:0x0518, B:276:0x0522, B:278:0x0529, B:285:0x053c, B:287:0x0540, B:288:0x0556, B:289:0x0572, B:293:0x0581, B:298:0x05a7, B:300:0x05ab, B:301:0x05d9, B:305:0x05ec, B:308:0x05ff, B:310:0x060c, B:313:0x062a, B:317:0x0641, B:323:0x065a, B:328:0x067c, B:331:0x068d, B:332:0x06ba, B:338:0x06cc, B:339:0x06d3, B:340:0x06d4, B:342:0x06dd, B:343:0x06e6, B:345:0x06ed, B:349:0x070d, B:354:0x071f, B:355:0x0726, B:356:0x0727, B:359:0x0731, B:362:0x0741, B:364:0x0745, B:366:0x0749, B:368:0x074d, B:370:0x0753, B:375:0x075d, B:376:0x0766, B:377:0x076e, B:380:0x0774, B:382:0x0778, B:383:0x077c, B:385:0x0782, B:387:0x078c, B:395:0x07ba, B:396:0x07c0, B:398:0x07c8, B:402:0x07d7, B:403:0x07e2, B:404:0x07f2, B:406:0x07fa, B:409:0x080b, B:411:0x0830, B:417:0x084a, B:419:0x0850, B:421:0x0856, B:423:0x085e, B:425:0x0868, B:426:0x0870, B:430:0x0877, B:432:0x0882, B:433:0x0890, B:446:0x08b0, B:451:0x08ca, B:456:0x08e6, B:457:0x08fa, B:458:0x0910, B:459:0x0911, B:460:0x091f, B:462:0x0927, B:464:0x092d, B:466:0x0935, B:468:0x093f, B:469:0x0947, B:473:0x094e, B:475:0x0959, B:476:0x0965, B:482:0x0979, B:487:0x0989, B:489:0x0992, B:490:0x099f, B:492:0x09aa, B:496:0x09b1, B:498:0x09bc, B:500:0x09c1, B:501:0x09c2, B:503:0x09e4, B:507:0x09eb, B:509:0x09f8, B:511:0x0a1a), top: B:536:0x0132, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e0 A[Catch: HttpException -> 0x0a2c, TryCatch #4 {HttpException -> 0x0a2c, blocks: (B:3:0x0004, B:6:0x0009, B:8:0x000d, B:9:0x0013, B:11:0x0019, B:13:0x0021, B:15:0x002b, B:18:0x0035, B:19:0x0039, B:25:0x005d, B:29:0x0072, B:31:0x0078, B:33:0x0080, B:35:0x0084, B:36:0x00a4, B:38:0x00a8, B:39:0x00ab, B:41:0x00b1, B:42:0x00b6, B:43:0x00be, B:44:0x00c8, B:46:0x00cc, B:48:0x00d2, B:51:0x00d9, B:52:0x00de, B:54:0x00e0, B:56:0x00e2, B:58:0x00ef, B:59:0x00f7, B:61:0x0103, B:63:0x0107, B:65:0x010b, B:66:0x0110, B:69:0x011c, B:70:0x0120), top: B:529:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:580:0x099f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:581:0x09f8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0103 A[Catch: HttpException -> 0x0a2c, TryCatch #4 {HttpException -> 0x0a2c, blocks: (B:3:0x0004, B:6:0x0009, B:8:0x000d, B:9:0x0013, B:11:0x0019, B:13:0x0021, B:15:0x002b, B:18:0x0035, B:19:0x0039, B:25:0x005d, B:29:0x0072, B:31:0x0078, B:33:0x0080, B:35:0x0084, B:36:0x00a4, B:38:0x00a8, B:39:0x00ab, B:41:0x00b1, B:42:0x00b6, B:43:0x00be, B:44:0x00c8, B:46:0x00cc, B:48:0x00d2, B:51:0x00d9, B:52:0x00de, B:54:0x00e0, B:56:0x00e2, B:58:0x00ef, B:59:0x00f7, B:61:0x0103, B:63:0x0107, B:65:0x010b, B:66:0x0110, B:69:0x011c, B:70:0x0120), top: B:529:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int parseNext() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2712
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpParser.parseNext():int");
    }

    protected int fill() throws IOException {
        Buffer buffer;
        Buffer buffer2;
        if (this._buffer == null) {
            this._buffer = getHeaderBuffer();
        }
        if (this._state <= 0 || this._buffer != (buffer = this._header) || buffer == null || buffer.hasContent() || (buffer2 = this._body) == null || !buffer2.hasContent()) {
            Buffer buffer3 = this._buffer;
            Buffer buffer4 = this._header;
            if (buffer3 == buffer4 && this._state > 0 && buffer4.length() == 0 && ((this._forceContentBuffer || this._contentLength - this._contentPosition > this._header.capacity()) && !(this._body == null && this._buffers == null))) {
                if (this._body == null) {
                    this._body = this._buffers.getBuffer();
                }
                this._buffer = this._body;
            }
            if (this._endp == null) {
                return -1;
            }
            if (this._buffer == this._body || this._state > 0) {
                this._buffer.compact();
            }
            if (this._buffer.space() == 0) {
                LOG.warn("HttpParser Full for {} ", this._endp);
                this._buffer.clear();
                StringBuilder sb = new StringBuilder();
                sb.append("FULL ");
                sb.append(this._buffer == this._body ? "body" : "head");
                throw new HttpException(413, sb.toString());
            }
            try {
                return this._endp.fill(this._buffer);
            } catch (IOException e) {
                LOG.debug(e);
                if (e instanceof EofException) {
                    throw e;
                }
                throw new EofException(e);
            }
        } else {
            this._buffer = this._body;
            return this._buffer.length();
        }
    }

    @Override // org.eclipse.jetty.http.Parser
    public void reset() {
        Buffer buffer;
        View view = this._contentView;
        view.setGetIndex(view.putIndex());
        this._state = this._persistent ? -14 : this._endp.isInputShutdown() ? 0 : 7;
        this._contentLength = -3L;
        this._contentPosition = 0L;
        this._length = 0;
        this._responseStatus = 0;
        if (this._eol == 13 && (buffer = this._buffer) != null && buffer.hasContent() && this._buffer.peek() == 10) {
            this._eol = this._buffer.get();
        }
        Buffer buffer2 = this._body;
        if (buffer2 != null && buffer2.hasContent()) {
            Buffer buffer3 = this._header;
            if (buffer3 == null) {
                getHeaderBuffer();
            } else {
                buffer3.setMarkIndex(-1);
                this._header.compact();
            }
            int space = this._header.space();
            if (space > this._body.length()) {
                space = this._body.length();
            }
            Buffer buffer4 = this._body;
            buffer4.peek(buffer4.getIndex(), space);
            Buffer buffer5 = this._body;
            buffer5.skip(this._header.put(buffer5.peek(buffer5.getIndex(), space)));
        }
        Buffer buffer6 = this._header;
        if (buffer6 != null) {
            buffer6.setMarkIndex(-1);
            this._header.compact();
        }
        Buffer buffer7 = this._body;
        if (buffer7 != null) {
            buffer7.setMarkIndex(-1);
        }
        this._buffer = this._header;
        returnBuffers();
    }

    @Override // org.eclipse.jetty.http.Parser
    public void returnBuffers() {
        Buffer buffer = this._body;
        if (buffer != null && !buffer.hasContent() && this._body.markIndex() == -1 && this._buffers != null) {
            if (this._buffer == this._body) {
                this._buffer = this._header;
            }
            Buffers buffers = this._buffers;
            if (buffers != null) {
                buffers.returnBuffer(this._body);
            }
            this._body = null;
        }
        Buffer buffer2 = this._header;
        if (buffer2 != null && !buffer2.hasContent() && this._header.markIndex() == -1 && this._buffers != null) {
            if (this._buffer == this._header) {
                this._buffer = null;
            }
            this._buffers.returnBuffer(this._header);
            this._header = null;
        }
    }

    public void setState(int i) {
        this._state = i;
        this._contentLength = -3L;
    }

    public String toString(Buffer buffer) {
        return "state=" + this._state + " length=" + this._length + " buf=" + buffer.hashCode();
    }

    public String toString() {
        return String.format("%s{s=%d,l=%d,c=%d}", getClass().getSimpleName(), Integer.valueOf(this._state), Integer.valueOf(this._length), Long.valueOf(this._contentLength));
    }

    public Buffer getHeaderBuffer() {
        if (this._header == null) {
            this._header = this._buffers.getHeader();
            this._tok0.update(this._header);
            this._tok1.update(this._header);
        }
        return this._header;
    }

    public Buffer getBodyBuffer() {
        return this._body;
    }

    public void setForceContentBuffer(boolean z) {
        this._forceContentBuffer = z;
    }

    public Buffer blockForContent(long j) throws IOException {
        if (this._contentView.length() > 0) {
            return this._contentView;
        }
        if (getState() <= 0 || isState(7)) {
            return null;
        }
        try {
            parseNext();
            while (this._contentView.length() == 0 && !isState(0) && !isState(7) && this._endp != null && this._endp.isOpen()) {
                if (!this._endp.isBlocking()) {
                    if (parseNext() <= 0) {
                        if (!this._endp.blockReadable(j)) {
                            this._endp.close();
                            throw new EofException(RtspHeaders.Values.TIMEOUT);
                        }
                    }
                }
                parseNext();
            }
            if (this._contentView.length() > 0) {
                return this._contentView;
            }
            return null;
        } catch (IOException e) {
            this._endp.close();
            throw e;
        }
    }

    public int available() throws IOException {
        View view = this._contentView;
        if (view != null && view.length() > 0) {
            return this._contentView.length();
        }
        if (this._endp.isBlocking()) {
            if (this._state > 0) {
                EndPoint endPoint = this._endp;
                return (!(endPoint instanceof StreamEndPoint) || ((StreamEndPoint) endPoint).getInputStream().available() <= 0) ? 0 : 1;
            }
            return 0;
        }
        parseNext();
        View view2 = this._contentView;
        if (view2 == null) {
            return 0;
        }
        return view2.length();
    }
}
