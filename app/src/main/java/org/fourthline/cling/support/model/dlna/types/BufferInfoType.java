package org.fourthline.cling.support.model.dlna.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.support.model.dlna.types.CodedDataBuffer;

/* loaded from: classes5.dex */
public class BufferInfoType {
    static final Pattern pattern = Pattern.compile("^dejitter=(\\d{1,10})(;CDB=(\\d{1,10});BTM=(0|1|2))?(;TD=(\\d{1,10}))?(;BFR=(0|1))?$", 2);
    private CodedDataBuffer cdb;
    private Long dejitterSize;
    private Boolean fullnessReports;
    private Long targetDuration;

    public BufferInfoType(Long l) {
        this.dejitterSize = l;
    }

    public BufferInfoType(Long l, CodedDataBuffer codedDataBuffer, Long l2, Boolean bool) {
        this.dejitterSize = l;
        this.cdb = codedDataBuffer;
        this.targetDuration = l2;
        this.fullnessReports = bool;
    }

    public static BufferInfoType valueOf(String str) throws InvalidValueException {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            try {
                Long valueOf = Long.valueOf(Long.parseLong(matcher.group(1)));
                Boolean bool = null;
                CodedDataBuffer codedDataBuffer = matcher.group(2) != null ? new CodedDataBuffer(Long.valueOf(Long.parseLong(matcher.group(3))), CodedDataBuffer.TransferMechanism.values()[Integer.parseInt(matcher.group(4))]) : null;
                Long valueOf2 = matcher.group(5) != null ? Long.valueOf(Long.parseLong(matcher.group(6))) : null;
                if (matcher.group(7) != null) {
                    bool = Boolean.valueOf(matcher.group(8).equals("1"));
                }
                return new BufferInfoType(valueOf, codedDataBuffer, valueOf2, bool);
            } catch (NumberFormatException unused) {
            }
        }
        throw new InvalidValueException("Can't parse BufferInfoType: " + str);
    }

    public String getString() {
        String str = "dejitter=" + this.dejitterSize.toString();
        if (this.cdb != null) {
            str = str + ";CDB=" + this.cdb.getSize().toString() + ";BTM=" + this.cdb.getTranfer().ordinal();
        }
        if (this.targetDuration != null) {
            str = str + ";TD=" + this.targetDuration.toString();
        }
        if (this.fullnessReports == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(";BFR=");
        sb.append(this.fullnessReports.booleanValue() ? "1" : "0");
        return sb.toString();
    }

    public Long getDejitterSize() {
        return this.dejitterSize;
    }

    public CodedDataBuffer getCdb() {
        return this.cdb;
    }

    public Long getTargetDuration() {
        return this.targetDuration;
    }

    public Boolean isFullnessReports() {
        return this.fullnessReports;
    }
}
