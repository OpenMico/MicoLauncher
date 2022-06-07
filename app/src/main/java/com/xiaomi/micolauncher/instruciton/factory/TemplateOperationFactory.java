package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateAlarmOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateAncientPoemOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateDeviceListOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateGeneral2Operation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateGeneralOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateImageCardOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplatePlayInfoOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateRichPictureOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateStationsOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateToastOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateTranslationV2Operation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateVideosOperation;
import com.xiaomi.micolauncher.instruciton.impl.TemplateWeatherV2Operation;

/* loaded from: classes3.dex */
public class TemplateOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return AIApiConstants.Template.NAME;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        switch (str.hashCode()) {
            case -2079307189:
                if (str.equals(AIApiConstants.Template.AncientPoem)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1387314916:
                if (str.equals(AIApiConstants.Template.WeatherV2)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1171153257:
                if (str.equals(AIApiConstants.Template.ImageCard)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -643459826:
                if (str.equals(AIApiConstants.Template.RichPicture)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -554195084:
                if (str.equals(AIApiConstants.Template.General)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -492218189:
                if (str.equals(AIApiConstants.Template.Stations)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -178370:
                if (str.equals(AIApiConstants.Template.General2)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 67886326:
                if (str.equals(AIApiConstants.Template.PlayInfo)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 304115208:
                if (str.equals(AIApiConstants.Template.DeviceList)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 557206493:
                if (str.equals(AIApiConstants.Template.Alarm)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 557738813:
                if (str.equals(AIApiConstants.Template.Translation)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 572514862:
                if (str.equals(AIApiConstants.Template.Memo)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 574842803:
                if (str.equals(AIApiConstants.Template.Toast)) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 692050764:
                if (str.equals(AIApiConstants.Template.Videos)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new TemplateAlarmOperation(instruction);
            case 1:
                return new TemplateGeneralOperation(instruction);
            case 2:
                return new TemplateGeneral2Operation(instruction);
            case 3:
                return new TemplateImageCardOperation(instruction);
            case 4:
                return new TemplateWeatherV2Operation(instruction);
            case 5:
                return new TemplateAncientPoemOperation(instruction);
            case 6:
                if (a(instruction)) {
                    return new TemplatePlayInfoOperation(instruction);
                }
                return null;
            case 7:
                return new TemplateTranslationV2Operation(instruction);
            case '\b':
                return new TemplateVideosOperation(instruction);
            case '\t':
            default:
                return null;
            case '\n':
                return new TemplateRichPictureOperation(instruction);
            case 11:
                return new TemplateDeviceListOperation(instruction);
            case '\f':
                return new TemplateToastOperation(instruction);
            case '\r':
                return new TemplateStationsOperation(instruction);
        }
    }

    private boolean a(Instruction instruction) {
        Template.PlayInfo playInfo = (Template.PlayInfo) instruction.getPayload();
        return playInfo.getType().isPresent() && playInfo.getType().get() == Template.PlayInfoType.JOKE;
    }
}
