package org.fourthline.cling.support.model.dlna.message.header;

import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.model.types.BytesRange;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.support.model.dlna.types.AvailableSeekRangeType;
import org.fourthline.cling.support.model.dlna.types.NormalPlayTimeRange;

/* loaded from: classes5.dex */
public class AvailableSeekRangeHeader extends DLNAHeader<AvailableSeekRangeType> {
    public AvailableSeekRangeHeader() {
    }

    public AvailableSeekRangeHeader(AvailableSeekRangeType availableSeekRangeType) {
        setValue(availableSeekRangeType);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        NormalPlayTimeRange normalPlayTimeRange;
        if (str.length() != 0) {
            String[] split = str.split(StringUtils.SPACE);
            boolean z = true;
            try {
                if (split.length > 1) {
                    try {
                        AvailableSeekRangeType.Mode valueOf = AvailableSeekRangeType.Mode.valueOf("MODE_" + split[0]);
                        BytesRange bytesRange = null;
                        try {
                            try {
                                normalPlayTimeRange = NormalPlayTimeRange.valueOf(split[1], true);
                            } catch (InvalidValueException unused) {
                                throw new InvalidValueException("Invalid AvailableSeekRange Range");
                            }
                        } catch (InvalidValueException unused2) {
                            bytesRange = BytesRange.valueOf(split[1]);
                            z = false;
                            normalPlayTimeRange = null;
                        }
                        if (!z) {
                            setValue(new AvailableSeekRangeType(valueOf, bytesRange));
                            return;
                        } else if (split.length > 2) {
                            setValue(new AvailableSeekRangeType(valueOf, normalPlayTimeRange, BytesRange.valueOf(split[2])));
                            return;
                        } else {
                            setValue(new AvailableSeekRangeType(valueOf, normalPlayTimeRange));
                            return;
                        }
                    } catch (IllegalArgumentException unused3) {
                        throw new InvalidValueException("Invalid AvailableSeekRange Mode");
                    }
                }
            } catch (InvalidValueException e) {
                throw new InvalidHeaderException("Invalid AvailableSeekRange header value: " + str + "; " + e.getMessage());
            }
        }
        throw new InvalidHeaderException("Invalid AvailableSeekRange header value: " + str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        AvailableSeekRangeType value = getValue();
        String num = Integer.toString(value.getModeFlag().ordinal());
        if (value.getNormalPlayTimeRange() != null) {
            num = num + StringUtils.SPACE + value.getNormalPlayTimeRange().getString(false);
        }
        if (value.getBytesRange() == null) {
            return num;
        }
        return num + StringUtils.SPACE + value.getBytesRange().getString(false);
    }
}
