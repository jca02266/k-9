package com.fsck.k9.helper;

import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.internet.JisSupport;

public class EmojiConverter {

    /*
     * Lightweight method to check whether the message contains emoji or not.
     * Useful to avoid calling the heavyweight convertEmoji2Img method.
     * We don't use String.codePointAt here for performance reasons.
     */
    private static boolean hasEmoji(String html) {
        for (int i = 0; i < html.length(); ++i) {
            char c = html.charAt(i);
            if (c >= 0xDBB8 && c < 0xDBBC)
                return true;
        }
        return false;
    }

    public static String convertEmoji2Img(String html, Address[] fromAddrs) {
        if (!hasEmoji(html)) {
            return html;

        }

        int carrier = 0;
        if (fromAddrs != null && fromAddrs.length > 0) {
            String from = JisSupport.getJisVariantFromAddress(fromAddrs[0].getAddress());
            if ("docomo".equals(from)) {
                carrier = 0;
            }
            else if ("softbank".equals(from)) {
                carrier = 1;
            }
            else if ("kddi".equals(from)) {
                carrier = 2;
            }
            else {
                carrier = 3;
            }
        }

        StringBuilder buff = new StringBuilder(html.length() + 512);
        for (int i = 0; i < html.length(); ) {
            int codePoint, nextCodePoint = 0;
            int j;

            codePoint = html.codePointAt(i);
            i += Character.charCount(codePoint);

            if (i < html.length()) {
                nextCodePoint = html.codePointAt(i);
                if (nextCodePoint == 0xFE0F && i + 1 < html.length()) {
                    i += Character.charCount(nextCodePoint);

                    nextCodePoint = html.codePointAt(i);
                    i += Character.charCount(nextCodePoint);
                }
            }

            String emoji = null;
            switch (carrier) {
                case 0:
                    emoji = getEmojiForCodePointDocomo(codePoint, nextCodePoint);
                    break;
                case 1:
                    emoji = getEmojiForCodePointSoftBank(codePoint, nextCodePoint);
                    break;
                case 2:
                    emoji = getEmojiForCodePointKDDI(codePoint, nextCodePoint);
                    break;
                default:
                    emoji = getEmojiForCodePointDocomo(codePoint, nextCodePoint);
                    break;
            }
            if (emoji != null)
                buff.append("<img src=\"file:///android_asset/mobylet-emoticons/").append(emoji).append(".gif\" alt=\"").append(emoji).append("\" />");
            else
                buff.appendCodePoint(codePoint);

        }
        return buff.toString();
    }

    private static String getEmojiForCodePointDocomo(int codePoint, int nextCodePoint) {

        switch (codePoint) {
            case 0xFE000: return "E63E";
            case 0xFE001: return "E63F";
            case 0xFE002: return "E640";
            case 0xFE003: return "E641";
            case 0xFE004: return "E642";
            case 0xFE005: return "E643";
            case 0xFE006: return "E644";
            case 0xFE007: return "E645";
            case 0xFE008: return "E6B3";
            case 0xFE011: return "E69C";
            case 0xFE012: return "E69D";
            case 0xFE013: return "E69E";
            case 0xFE014: return "E69F";
            case 0xFE015: return "E6A0";
            case 0xFE018: return "E6B7";
            case 0xFE019: return "E6B8";
            case 0xFE01A: return "E6B9";
            case 0xFE01B: return "E71C";
            case 0xFE01D: return "E71F";
            case 0xFE02A: return "E6BA";
            case 0xFE02B: return "E646";
            case 0xFE02C: return "E647";
            case 0xFE02D: return "E648";
            case 0xFE02E: return "E649";
            case 0xFE02F: return "E64A";
            case 0xFE030: return "E64B";
            case 0xFE031: return "E64C";
            case 0xFE032: return "E64D";
            case 0xFE033: return "E64E";
            case 0xFE034: return "E64F";
            case 0xFE035: return "E650";
            case 0xFE036: return "E651";
            case 0xFE038: return "E73F";
            case 0xFE03C: return "E741";
            case 0xFE03D: return "E743";
            case 0xFE03E: return "E746";
            case 0xFE03F: return "E747";
            case 0xFE040: return "E748";
            case 0xFE04F: return "E742";
            case 0xFE050: return "E744";
            case 0xFE051: return "E745";
            case 0xFE190: return "E691";
            case 0xFE191: return "E692";
            case 0xFE195: return "E710";
            case 0xFE19A: return "E6B1";
            case 0xFE1B7: return "E6A1";
            case 0xFE1B8: return "E6A2";
            case 0xFE1B9: return "E74E";
            case 0xFE1BA: return "E74F";
            case 0xFE1BC: return "E750";
            case 0xFE1BD: return "E751";
            case 0xFE1BE: return "E754";
            case 0xFE1BF: return "E755";
            case 0xFE320: return "E6F1";
            case 0xFE323: return "E6F2";
            case 0xFE324: return "E6F4";
            case 0xFE326: return "E725";
            case 0xFE327: return "E726";
            case 0xFE329: return "E728";
            case 0xFE32B: return "E752";
            case 0xFE330: return "E6F0";
            case 0xFE331: return "E722";
            case 0xFE332: return "E72A";
            case 0xFE333: return "E753";
            case 0xFE339: return "E72E";
            case 0xFE33A: return "E72D";
            case 0xFE33C: return "E72B";
            case 0xFE33D: return "E724";
            case 0xFE33E: return "E721";
            case 0xFE33F: return "E6F3";
            case 0xFE340: return "E720";
            case 0xFE341: return "E757";
            case 0xFE343: return "E72C";
            case 0xFE344: return "E723";
            case 0xFE347: return "E729";
            case 0xFE4B0: return "E663";
            case 0xFE4B2: return "E664";
            case 0xFE4B3: return "E665";
            case 0xFE4B4: return "E666";
            case 0xFE4B5: return "E667";
            case 0xFE4B6: return "E668";
            case 0xFE4B7: return "E669";
            case 0xFE4B9: return "E66A";
            case 0xFE4BA: return "E73E";
            case 0xFE4C3: return "E740";
            case 0xFE4C9: return "E718";
            case 0xFE4CD: return "E699";
            case 0xFE4CE: return "E69A";
            case 0xFE4CF: return "E70E";
            case 0xFE4D0: return "E711";
            case 0xFE4D1: return "E71A";
            case 0xFE4D6: return "E674";
            case 0xFE4DC: return "E70F";
            case 0xFE4DD: return "E715";
            case 0xFE4E2: return "E6D6";
            case 0xFE4EF: return "E681";
            case 0xFE4F0: return "E682";
            case 0xFE4F1: return "E6AD";
            case 0xFE4F2: return "E713";
            case 0xFE4F3: return "E714";
            case 0xFE506: return "E66E";
            case 0xFE50F: return "E684";
            case 0xFE510: return "E685";
            case 0xFE511: return "E686";
            case 0xFE512: return "E6A4";
            case 0xFE522: return "E65A";
            case 0xFE523: return "E687";
            case 0xFE525: return "E688";
            case 0xFE526: return "E6CE";
            case 0xFE527: return "E689";
            case 0xFE528: return "E6D0";
            case 0xFE529: return "E6D3";
            case 0xFE52B: return "E6CF";
            case 0xFE536: return "E6AE";
            case 0xFE537: return "E6B2";
            case 0xFE538: return "E716";
            case 0xFE539: return "E719";
            case 0xFE53A: return "E730";
            case 0xFE53E: return "E675";
            case 0xFE546: return "E683";
            case 0xFE553: return "E698";
            case 0xFE7D0: return "E652";
            case 0xFE7D1: return "E653";
            case 0xFE7D2: return "E654";
            case 0xFE7D3: return "E655";
            case 0xFE7D4: return "E656";
            case 0xFE7D5: return "E657";
            case 0xFE7D6: return "E658";
            case 0xFE7D7: return "E659";
            case 0xFE7D8: return "E712";
            case 0xFE7D9: return "E733";
            case 0xFE7DF: return "E65B";
            case 0xFE7E1: return "E65C";
            case 0xFE7E2: return "E65D";
            case 0xFE7E4: return "E65E";
            case 0xFE7E5: return "E65F";
            case 0xFE7E6: return "E660";
            case 0xFE7E8: return "E661";
            case 0xFE7E9: return "E662";
            case 0xFE7EA: return "E6A3";
            case 0xFE7EB: return "E71D";
            case 0xFE7F5: return "E66B";
            case 0xFE7F6: return "E66C";
            case 0xFE7F7: return "E66D";
            case 0xFE7FA: return "E6F7";
            case 0xFE7FC: return "E679";
            case 0xFE800: return "E676";
            case 0xFE801: return "E677";
            case 0xFE803: return "E67A";
            case 0xFE804: return "E67B";
            case 0xFE805: return "E67C";
            case 0xFE806: return "E67D";
            case 0xFE807: return "E67E";
            case 0xFE808: return "E6AC";
            case 0xFE80A: return "E68B";
            case 0xFE813: return "E6F6";
            case 0xFE814: return "E6FF";
            case 0xFE81C: return "E68A";
            case 0xFE81D: return "E68C";
            case 0xFE823: return "E6F9";
            case 0xFE824: return "E717";
            case 0xFE825: return "E71B";
            case 0xFE82C: return "E6E0";
            case 0xFE82E: return "E6E2";
            case 0xFE82F: return "E6E3";
            case 0xFE830: return "E6E4";
            case 0xFE831: return "E6E5";
            case 0xFE832: return "E6E6";
            case 0xFE833: return "E6E7";
            case 0xFE834: return "E6E8";
            case 0xFE835: return "E6E9";
            case 0xFE836: return "E6EA";
            case 0xFE837: return "E6EB";
            case 0xFE960: return "E673";
            case 0xFE961: return "E749";
            case 0xFE962: return "E74A";
            case 0xFE963: return "E74C";
            case 0xFE964: return "E74D";
            case 0xFE980: return "E66F";
            case 0xFE981: return "E670";
            case 0xFE982: return "E671";
            case 0xFE983: return "E672";
            case 0xFE984: return "E71E";
            case 0xFE985: return "E74B";
            case 0xFE986: return "E756";
            case 0xFEAF0: return "E678";
            case 0xFEAF1: return "E696";
            case 0xFEAF2: return "E697";
            case 0xFEAF3: return "E6A5";
            case 0xFEAF4: return "E6F5";
            case 0xFEAF5: return "E700";
            case 0xFEAF6: return "E73C";
            case 0xFEAF7: return "E73D";
            case 0xFEB04: return "E702";
            case 0xFEB05: return "E703";
            case 0xFEB06: return "E704";
            case 0xFEB07: return "E709";
            case 0xFEB08: return "E70A";
            case 0xFEB0C: return "E6EC";
            case 0xFEB0D: return "E6ED";
            case 0xFEB0E: return "E6EE";
            case 0xFEB0F: return "E6EF";
            case 0xFEB1A: return "E68D";
            case 0xFEB1B: return "E68E";
            case 0xFEB1C: return "E68F";
            case 0xFEB1D: return "E690";
            case 0xFEB1E: return "E67F";
            case 0xFEB1F: return "E680";
            case 0xFEB20: return "E69B";
            case 0xFEB21: return "E6D7";
            case 0xFEB22: return "E6DE";
            case 0xFEB23: return "E737";
            case 0xFEB27: return "E70B";
            case 0xFEB28: return "E72F";
            case 0xFEB29: return "E731";
            case 0xFEB2A: return "E732";
            case 0xFEB2B: return "E734";
            case 0xFEB2C: return "E735";
            case 0xFEB2D: return "E736";
            case 0xFEB2E: return "E738";
            case 0xFEB2F: return "E739";
            case 0xFEB30: return "E73A";
            case 0xFEB31: return "E73B";
            case 0xFEB36: return "E6DD";
            case 0xFEB55: return "E6F8";
            case 0xFEB56: return "E6FB";
            case 0xFEB57: return "E6FC";
            case 0xFEB58: return "E6FE";
            case 0xFEB59: return "E701";
            case 0xFEB5A: return "E705";
            case 0xFEB5B: return "E706";
            case 0xFEB5C: return "E707";
            case 0xFEB5D: return "E708";
            case 0xFEB60: return "E6FA";
            case 0xFEB81: return "E6D8";
            case 0xFEB82: return "E6D9";
            case 0xFEB83: return "E6DA";
            case 0xFEB84: return "E6DB";
            case 0xFEB85: return "E6DC";
            case 0xFEB93: return "E693";
            case 0xFEB94: return "E694";
            case 0xFEB95: return "E695";
            case 0xFEB96: return "E6FD";
            case 0xFEB97: return "E727";

            // unicode 6
            case 0x0023: if (nextCodePoint == 0x20E3) return "E6E0"; else return null;
            case 0x0030: if (nextCodePoint == 0x20E3) return "E6EB"; else return null;
            case 0x0031: if (nextCodePoint == 0x20E3) return "E6E2"; else return null;
            case 0x0032: if (nextCodePoint == 0x20E3) return "E6E3"; else return null;
            case 0x0033: if (nextCodePoint == 0x20E3) return "E6E4"; else return null;
            case 0x0034: if (nextCodePoint == 0x20E3) return "E6E5"; else return null;
            case 0x0035: if (nextCodePoint == 0x20E3) return "E6E6"; else return null;
            case 0x0036: if (nextCodePoint == 0x20E3) return "E6E7"; else return null;
            case 0x0037: if (nextCodePoint == 0x20E3) return "E6E8"; else return null;
            case 0x0038: if (nextCodePoint == 0x20E3) return "E6E9"; else return null;
            case 0x0039: if (nextCodePoint == 0x20E3) return "E6EA"; else return null;
            case 0x00A9 : return "E731";
            case 0x00AE : return "E736";
            case 0x203C : return "E704";
            case 0x2049 : return "E703";
            case 0x2122 : return "E732";
            case 0x2194 : return "E73C";
            case 0x2195 : return "E73D";
            case 0x2196 : return "E697";
            case 0x2197 : return "E678";
            case 0x2198 : return "E696";
            case 0x2199 : return "E6A5";
            case 0x21A9 : return "E6DA";
            case 0x231A : return "E71F";
            case 0x23F0 : return "E6BA";
            case 0x23F3 : return "E71C";
            case 0x24C2 : return "E65C";
            case 0x2600 : return "E63E";
            case 0x2601 : return "E63F";
            case 0x260E : return "E687";
            case 0x2614 : return "E640";
            case 0x2615 : return "E670";
            case 0x2648 : return "E646";
            case 0x2649 : return "E647";
            case 0x264A : return "E648";
            case 0x264B : return "E649";
            case 0x264C : return "E64A";
            case 0x264D : return "E64B";
            case 0x264E : return "E64C";
            case 0x264F : return "E64D";
            case 0x2650 : return "E64E";
            case 0x2651 : return "E64F";
            case 0x2652 : return "E650";
            case 0x2653 : return "E651";
            case 0x2660 : return "E68E";
            case 0x2663 : return "E690";
            case 0x2665 : return "E68D";
            case 0x2666 : return "E68F";
            case 0x2668 : return "E6F7";
            case 0x267B : return "E735";
            case 0x267F : return "E69B";
            case 0x26A0 : return "E737";
            case 0x26A1 : return "E642";
            case 0x26BD : return "E656";
            case 0x26BE : return "E653";
            case 0x26C4 : return "E641";
            case 0x26F3 : return "E654";
            case 0x26F5 : return "E6A3";
            case 0x26FD : return "E66B";
            case 0x2702 : return "E675";
            case 0x2708 : return "E662";
            case 0x2709 : return "E6D3";
            case 0x270A : return "E693";
            case 0x270B : return "E695";
            case 0x270C : return "E694";
            case 0x270F : return "E719";
            case 0x2712 : return "E6AE";
            case 0x2728 : return "E6FA";
            case 0x2757 : return "E702";
            case 0x2764 : return "E6EC";
            case 0x27B0 : return "E70A";
            case 0x2934 : return "E6F5";
            case 0x2935 : return "E700";
            case 0x3030 : return "E709";
            case 0x3299 : return "E734";
            case 0x1F17F: return "E66C";
            case 0x1F191: return "E6DB";
            case 0x1F193: return "E6D7";
            case 0x1F194: return "E6D8";
            case 0x1F195: return "E6DD";
            case 0x1F196: return "E72F";
            case 0x1F197: return "E70B";
            case 0x1F232: return "E738";
            case 0x1F233: return "E739";
            case 0x1F234: return "E73A";
            case 0x1F235: return "E73B";
            case 0x1F300: return "E643";
            case 0x1F301: return "E644";
            case 0x1F302: return "E645";
            case 0x1F303: return "E6B3";
            case 0x1F30A: return "E73F";
            case 0x1F311: return "E69C";
            case 0x1F313: return "E69E";
            case 0x1F314: return "E69D";
            case 0x1F315: return "E6A0";
            case 0x1F319: return "E69F";
            case 0x1F331: return "E746";
            case 0x1F337: return "E743";
            case 0x1F338: return "E748";
            case 0x1F340: return "E741";
            case 0x1F341: return "E747";
            case 0x1F34C: return "E744";
            case 0x1F34E: return "E745";
            case 0x1F352: return "E742";
            case 0x1F354: return "E673";
            case 0x1F359: return "E749";
            case 0x1F35C: return "E74C";
            case 0x1F35E: return "E74D";
            case 0x1F370: return "E74A";
            case 0x1F374: return "E66F";
            case 0x1F375: return "E71E";
            case 0x1F376: return "E74B";
            case 0x1F377: return "E756";
            case 0x1F378: return "E671";
            case 0x1F37A: return "E672";
            case 0x1F380: return "E684";
            case 0x1F381: return "E685";
            case 0x1F382: return "E686";
            case 0x1F384: return "E6A4";
            case 0x1F3A0: return "E679";
            case 0x1F3A4: return "E676";
            case 0x1F3A5: return "E677";
            case 0x1F3A7: return "E67A";
            case 0x1F3A8: return "E67B";
            case 0x1F3A9: return "E67C";
            case 0x1F3AA: return "E67D";
            case 0x1F3AB: return "E67E";
            case 0x1F3AC: return "E6AC";
            case 0x1F3AE: return "E68B";
            case 0x1F3B5: return "E6F6";
            case 0x1F3B6: return "E6FF";
            case 0x1F3BD: return "E652";
            case 0x1F3BE: return "E655";
            case 0x1F3BF: return "E657";
            case 0x1F3C0: return "E658";
            case 0x1F3C1: return "E659";
            case 0x1F3C2: return "E712";
            case 0x1F3C3: return "E733";
            case 0x1F3E0: return "E663";
            case 0x1F3E2: return "E664";
            case 0x1F3E3: return "E665";
            case 0x1F3E5: return "E666";
            case 0x1F3E6: return "E667";
            case 0x1F3E7: return "E668";
            case 0x1F3E8: return "E669";
            case 0x1F3EA: return "E66A";
            case 0x1F3EB: return "E73E";
            case 0x1F40C: return "E74E";
            case 0x1F41F: return "E751";
            case 0x1F424: return "E74F";
            case 0x1F427: return "E750";
            case 0x1F431: return "E6A2";
            case 0x1F434: return "E754";
            case 0x1F436: return "E6A1";
            case 0x1F437: return "E755";
            case 0x1F440: return "E691";
            case 0x1F442: return "E692";
            case 0x1F44A: return "E6FD";
            case 0x1F44D: return "E727";
            case 0x1F451: return "E71A";
            case 0x1F453: return "E69A";
            case 0x1F455: return "E70E";
            case 0x1F456: return "E711";
            case 0x1F45B: return "E70F";
            case 0x1F45C: return "E682";
            case 0x1F45D: return "E6AD";
            case 0x1F45F: return "E699";
            case 0x1F460: return "E674";
            case 0x1F463: return "E698";
            case 0x1F464: return "E6B1";
            case 0x1F484: return "E710";
            case 0x1F48B: return "E6F9";
            case 0x1F48C: return "E717";
            case 0x1F48D: return "E71B";
            case 0x1F493: return "E6ED";
            case 0x1F494: return "E6EE";
            case 0x1F495: return "E6EF";
            case 0x1F4A0: return "E6F8";
            case 0x1F4A1: return "E6FB";
            case 0x1F4A2: return "E6FC";
            case 0x1F4A3: return "E6FE";
            case 0x1F4A4: return "E701";
            case 0x1F4A5: return "E705";
            case 0x1F4A6: return "E706";
            case 0x1F4A7: return "E707";
            case 0x1F4A8: return "E708";
            case 0x1F4B0: return "E715";
            case 0x1F4B4: return "E6D6";
            case 0x1F4BA: return "E6B2";
            case 0x1F4BB: return "E716";
            case 0x1F4BF: return "E68C";
            case 0x1F4CE: return "E730";
            case 0x1F4D6: return "E683";
            case 0x1F4DD: return "E689";
            case 0x1F4DF: return "E65A";
            case 0x1F4E0: return "E6D0";
            case 0x1F4E9: return "E6CF";
            case 0x1F4F1: return "E688";
            case 0x1F4F2: return "E6CE";
            case 0x1F4F7: return "E681";
            case 0x1F4FA: return "E68A";
            case 0x1F50D: return "E6DC";
            case 0x1F511: return "E6D9";
            case 0x1F514: return "E713";
            case 0x1F51A: return "E6B9";
            case 0x1F51B: return "E6B8";
            case 0x1F51C: return "E6B7";
            case 0x1F527: return "E718";
            case 0x1F5FB: return "E740";
            case 0x1F601: return "E753";
            case 0x1F603: return "E6F0";
            case 0x1F605: return "E722";
            case 0x1F606: return "E72A";
            case 0x1F609: return "E729";
            case 0x1F60B: return "E752";
            case 0x1F60C: return "E721";
            case 0x1F60D: return "E726";
            case 0x1F60F: return "E72C";
            case 0x1F612: return "E725";
            case 0x1F613: return "E723";
            case 0x1F614: return "E720";
            case 0x1F616: return "E6F3";
            case 0x1F61C: return "E728";
            case 0x1F61E: return "E6F2";
            case 0x1F620: return "E6F1";
            case 0x1F621: return "E724";
            case 0x1F622: return "E72E";
            case 0x1F623: return "E72B";
            case 0x1F62D: return "E72D";
            case 0x1F631: return "E757";
            case 0x1F635: return "E6F4";
            case 0x1F683: return "E65B";
            case 0x1F684: return "E65D";
            case 0x1F68C: return "E660";
            case 0x1F697: return "E65E";
            case 0x1F699: return "E65F";
            case 0x1F6A2: return "E661";
            case 0x1F6A5: return "E66D";
            case 0x1F6A9: return "E6DE";
            case 0x1F6AA: return "E714";
            case 0x1F6AC: return "E67F";
            case 0x1F6AD: return "E680";
            case 0x1F6B2: return "E71D";
            case 0x1F6BB: return "E66E";

            default: return null;
        }
    }

    private static String getEmojiForCodePointKDDI(int codePoint, int nextCodePoint) {

        switch (codePoint) {
            case 0xFE000: return "E488";
            case 0xFE001: return "E48D";
            case 0xFE002: return "E48C";
            case 0xFE003: return "E485";
            case 0xFE004: return "E487";
            case 0xFE005: return "E469";
            case 0xFE006: return "E598";
            case 0xFE007: return "EAE8";
            case 0xFE008: return "EAF1";
            case 0xFE00A: return "EAF4";
            case 0xFE00B: return "E5DA";
            case 0xFE00D: return "EAF2";
            case 0xFE00E: return "E48A";
            case 0xFE00F: return "E48E";
            case 0xFE010: return "E4BF";
            case 0xFE011: return "E5A8";
            case 0xFE012: return "E5A9";
            case 0xFE013: return "E5AA";
            case 0xFE014: return "E486";
            case 0xFE016: return "E489";
            case 0xFE017: return "EAEF";
            case 0xFE01B: return "E47C";
            case 0xFE01C: return "E57B";
            case 0xFE01D: return "E57A";
            case 0xFE02A: return "E594";
            case 0xFE02B: return "E48F";
            case 0xFE02C: return "E490";
            case 0xFE02D: return "E491";
            case 0xFE02E: return "E492";
            case 0xFE02F: return "E493";
            case 0xFE030: return "E494";
            case 0xFE031: return "E495";
            case 0xFE032: return "E496";
            case 0xFE033: return "E497";
            case 0xFE034: return "E498";
            case 0xFE035: return "E499";
            case 0xFE036: return "E49A";
            case 0xFE037: return "E49B";
            case 0xFE038: return "EB7C";
            case 0xFE039: return "E5B3";
            case 0xFE03A: return "EB53";
            case 0xFE03B: return "EB5F";
            case 0xFE03C: return "E513";
            case 0xFE03D: return "E4E4";
            case 0xFE03E: return "EB7D";
            case 0xFE03F: return "E4CE";
            case 0xFE040: return "E4CA";
            case 0xFE041: return "E5BA";
            case 0xFE042: return "E5CD";
            case 0xFE044: return "E480";
            case 0xFE045: return "EA94";
            case 0xFE046: return "E4E3";
            case 0xFE047: return "E4E2";
            case 0xFE048: return "EA96";
            case 0xFE04A: return "EB36";
            case 0xFE04B: return "EB37";
            case 0xFE04C: return "EB38";
            case 0xFE04D: return "EB49";
            case 0xFE04E: return "EB82";
            case 0xFE04F: return "E4D2";
            case 0xFE050: return "EB35";
            case 0xFE051: return "EAB9";
            case 0xFE052: return "EABA";
            case 0xFE053: return "E4D4";
            case 0xFE054: return "E4CD";
            case 0xFE055: return "EABB";
            case 0xFE056: return "EABC";
            case 0xFE057: return "EB32";
            case 0xFE058: return "EB33";
            case 0xFE059: return "EB34";
            case 0xFE05A: return "EB39";
            case 0xFE05B: return "EB5A";
            case 0xFE190: return "E5A4";
            case 0xFE191: return "E5A5";
            case 0xFE192: return "EAD0";
            case 0xFE193: return "EAD1";
            case 0xFE194: return "EB47";
            case 0xFE195: return "E509";
            case 0xFE196: return "EAA0";
            case 0xFE197: return "E50B";
            case 0xFE198: return "EAA1";
            case 0xFE199: return "EAA2";
            case 0xFE19B: return "E4FC"; // androidPUA should be 0xFE19D
            case 0xFE19C: return "E4FA"; // androidPUA should be 0xFE19E
            case 0xFE19F: return "E501";
            case 0xFE1A1: return "E5DD";
            case 0xFE1A2: return "EADB";
            case 0xFE1A3: return "EAE9";
            case 0xFE1A4: return "EB13";
            case 0xFE1A5: return "EB14";
            case 0xFE1A6: return "EB15";
            case 0xFE1A7: return "EB16";
            case 0xFE1A8: return "EB17";
            case 0xFE1A9: return "EB18";
            case 0xFE1AA: return "EB19";
            case 0xFE1AB: return "EB1A";
            case 0xFE1AC: return "EB44";
            case 0xFE1AD: return "EB45";
            case 0xFE1AE: return "E4CB";
            case 0xFE1AF: return "E5BF";
            case 0xFE1B0: return "E50E";
            case 0xFE1B1: return "E4EC";
            case 0xFE1B2: return "E4EF";
            case 0xFE1B3: return "E4F8";
            case 0xFE1B6: return "EB1C";
            case 0xFE1B7: return "E4E1";
            case 0xFE1B8: return "E4DB";
            case 0xFE1B9: return "EB7E";
            case 0xFE1BA: return "E4E0";
            case 0xFE1BB: return "EB76";
            case 0xFE1BC: return "E4DC";
            case 0xFE1BE: return "E4D8";
            case 0xFE1BF: return "E4DE";
            case 0xFE1C0: return "E5C0";
            case 0xFE1C1: return "E5C1";
            case 0xFE1C2: return "E5C2";
            case 0xFE1C3: return "E470";
            case 0xFE1C4: return "E4D9";
            case 0xFE1C5: return "E5C7";
            case 0xFE1C6: return "EAEC";
            case 0xFE1C7: return "EB1B";
            case 0xFE1C9: return "EB1D";
            case 0xFE1CB: return "EB1E";
            case 0xFE1CC: return "EB1F";
            case 0xFE1CD: return "EB20";
            case 0xFE1D1: return "EB21";
            case 0xFE1D2: return "E4D7";
            case 0xFE1D3: return "EB22";
            case 0xFE1D4: return "EB23";
            case 0xFE1D5: return "EB24";
            case 0xFE1D6: return "EB25";
            case 0xFE1D7: return "E4DA";
            case 0xFE1D8: return "E4DF";
            case 0xFE1D9: return "E4D3";
            case 0xFE1DA: return "E4DD";
            case 0xFE1DB: return "E4EE";
            case 0xFE1DC: return "E5D4";
            case 0xFE1DD: return "E5DB";
            case 0xFE1DE: return "EB3F";
            case 0xFE1DF: return "EB46";
            case 0xFE1E0: return "EB48";
            case 0xFE1E1: return "EB57";
            case 0xFE1E2: return "EB58";
            case 0xFE320: return "E472";
            case 0xFE321: return "EB67";
            case 0xFE322: return "EACA";
            case 0xFE324: return "E5AE";
            case 0xFE325: return "EACB";
            case 0xFE326: return "EAC9";
            case 0xFE327: return "E5C4";
            case 0xFE328: return "EAC1";
            case 0xFE329: return "E4E7";
            case 0xFE32C: return "EACF";
            case 0xFE32D: return "EACE";
            case 0xFE32E: return "EAC7";
            case 0xFE32F: return "EAC8";
            case 0xFE330: return "E471";
            case 0xFE333: return "EB80";
            case 0xFE334: return "EB64";
            case 0xFE335: return "EACD";
            case 0xFE336: return "E4FB";
            case 0xFE339: return "EB69";
            case 0xFE33A: return "E473";
            case 0xFE33B: return "EAC6";
            case 0xFE33C: return "EAC2";
            case 0xFE33D: return "EB5D";
            case 0xFE33E: return "EAC5";
            case 0xFE33F: return "EAC3";
            case 0xFE340: return "EAC0";
            case 0xFE341: return "E5C5";
            case 0xFE342: return "EAC4";
            case 0xFE343: return "EABF";
            case 0xFE344: return "E5C6";
            case 0xFE346: return "E474";
            case 0xFE347: return "E5C3";
            case 0xFE348: return "EB61";
            case 0xFE349: return "EB7F";
            case 0xFE34A: return "EB63";
            case 0xFE34B: return "EB60";
            case 0xFE34C: return "EB65";
            case 0xFE34D: return "EB68";
            case 0xFE34E: return "EB5E";
            case 0xFE34F: return "EB6A";
            case 0xFE350: return "EB66";
            case 0xFE351: return "EAD7";
            case 0xFE352: return "EAD8";
            case 0xFE353: return "EAD9";
            case 0xFE354: return "EB50";
            case 0xFE355: return "EB51";
            case 0xFE356: return "EB52";
            case 0xFE357: return "EB85";
            case 0xFE358: return "EB86";
            case 0xFE359: return "EB87";
            case 0xFE35A: return "EB88";
            case 0xFE35B: return "EAD2";
            case 0xFE4B0: return "E4AB";
            case 0xFE4B1: return "EB09";
            case 0xFE4B2: return "E4AD";
            case 0xFE4B3: return "E5DE";
            case 0xFE4B4: return "E5DF";
            case 0xFE4B5: return "E4AA";
            case 0xFE4B6: return "E4A3";
            case 0xFE4B7: return "EA81";
            case 0xFE4B8: return "EAF3";
            case 0xFE4B9: return "E4A4";
            case 0xFE4BA: return "EA80";
            case 0xFE4BB: return "E5BB";
            case 0xFE4BC: return "E5CF";
            case 0xFE4BD: return "EAF6";
            case 0xFE4BE: return "EAF7";
            case 0xFE4BF: return "EAF8";
            case 0xFE4C0: return "EAF9";
            case 0xFE4C1: return "E4A9";
            case 0xFE4C2: return "E4BD";
            case 0xFE4C3: return "E5BD";
            case 0xFE4C4: return "E4C0";
            case 0xFE4C7: return "E572";
            case 0xFE4C8: return "EB6C";
            case 0xFE4C9: return "E587";
            case 0xFE4CA: return "E5CB";
            case 0xFE4CB: return "E581";
            case 0xFE4CC: return "E5B7";
            case 0xFE4CD: return "EB2B";
            case 0xFE4CE: return "E4FE";
            case 0xFE4CF: return "E5B6";
            case 0xFE4D0: return "EB77";
            case 0xFE4D1: return "E5C9";
            case 0xFE4D3: return "EA93";
            case 0xFE4D4: return "EA9E";
            case 0xFE4D5: return "EB6B";
            case 0xFE4D6: return "E51A";
            case 0xFE4D8: return "EA9F";
            case 0xFE4D9: return "EAA3";
            case 0xFE4DA: return "EAA4";
            case 0xFE4DB: return "E50D";
            case 0xFE4DC: return "E504";
            case 0xFE4DD: return "E4C7";
            case 0xFE4DF: return "E5DC";
            case 0xFE4E0: return "E579";
            case 0xFE4E1: return "E57C";
            case 0xFE4E2: return "E57D";
            case 0xFE4E3: return "E585";
            case 0xFE4E4: return "EB5B";
            case 0xFE4E5: return "E4CC";
            case 0xFE4E6: return "E573";
            case 0xFE4E7: return "EAFA";
            case 0xFE4E8: return "EB0E";
            case 0xFE4E9: return "EB0F";
            case 0xFE4EA: return "EB10";
            case 0xFE4EB: return "E5D5";
            case 0xFE4EC: return "E5D6";
            case 0xFE4ED: return "EB11";
            case 0xFE4EE: return "EB12";
            case 0xFE4EF: return "E515";
            case 0xFE4F0: return "E49C";
            case 0xFE4F2: return "E512";
            case 0xFE4F4: return "E4F5";
            case 0xFE4F5: return "E50A";
            case 0xFE4F6: return "E47B";
            case 0xFE4F7: return "EA8F";
            case 0xFE4F9: return "E57E";
            case 0xFE4FA: return "E57F";
            case 0xFE4FB: return "E583";
            case 0xFE4FC: return "E584";
            case 0xFE4FD: return "E55F";
            case 0xFE4FE: return "E589";
            case 0xFE4FF: return "E565";
            case 0xFE500: return "E566";
            case 0xFE501: return "E567";
            case 0xFE502: return "E568";
            case 0xFE503: return "E56F";
            case 0xFE504: return "E51D";
            case 0xFE505: return "E5D8";
            case 0xFE506: return "E4A5";
            case 0xFE509: return "E510";
            case 0xFE50A: return "EA9A";
            case 0xFE50B: return "EB26";
            case 0xFE50C: return "EB27";
            case 0xFE50D: return "EB29";
            case 0xFE50E: return "EB28";
            case 0xFE50F: return "E59F";
            case 0xFE510: return "E4CF";
            case 0xFE511: return "E5A0";
            case 0xFE512: return "E4C9";
            case 0xFE513: return "EAF0";
            case 0xFE514: return "E5D9";
            case 0xFE515: return "E5CC";
            case 0xFE516: return "EA9B";
            case 0xFE517: return "EA9C";
            case 0xFE518: return "EAE3";
            case 0xFE519: return "EAE4";
            case 0xFE51A: return "EAE5";
            case 0xFE51B: return "EAE6";
            case 0xFE51C: return "EAE7";
            case 0xFE51D: return "EAEB";
            case 0xFE51E: return "EAED";
            case 0xFE51F: return "EAEE";
            case 0xFE520: return "E46F";
            case 0xFE521: return "EB3D";
            case 0xFE522: return "E59B";
            case 0xFE523: return "E596";
            case 0xFE524: return "E51E";
            case 0xFE525: return "E588";
            case 0xFE526: return "EB08";
            case 0xFE527: return "EA92";
            case 0xFE528: return "E520";
            case 0xFE529: return "E521";
            case 0xFE52A: return "E591";
            case 0xFE52B: return "EB62";
            case 0xFE52C: return "E51B";
            case 0xFE52D: return "EB0A";
            case 0xFE530: return "E511"; // androidPUA should be 0xFE821
            case 0xFE531: return "E4A8";
            case 0xFE532: return "E4FD";
            case 0xFE533: return "E592";
            case 0xFE534: return "E593";
            case 0xFE535: return "E51F";
            case 0xFE536: return "EB03";
            case 0xFE538: return "E5B8";
            case 0xFE539: return "E4A1";
            case 0xFE53A: return "E4A0";
            case 0xFE53B: return "E5CE";
            case 0xFE53C: return "E582";
            case 0xFE53D: return "E562";
            case 0xFE53E: return "E516";
            case 0xFE53F: return "E560";
            case 0xFE540: return "E561";
            case 0xFE541: return "E569";
            case 0xFE542: return "E563";
            case 0xFE543: return "E58F";
            case 0xFE544: return "E590";
            case 0xFE545: return "E56B";
            case 0xFE546: return "E49F";
            case 0xFE547: return "E49D";
            case 0xFE548: return "E564";
            case 0xFE549: return "E56A";
            case 0xFE54A: return "E574";
            case 0xFE54B: return "E575";
            case 0xFE54C: return "E576";
            case 0xFE54D: return "E56C";
            case 0xFE54E: return "E56D";
            case 0xFE54F: return "E56E";
            case 0xFE550: return "E570";
            case 0xFE551: return "E4A2";
            case 0xFE552: return "EB0B";
            case 0xFE553: return "EB2A";
            case 0xFE7D1: return "E4BA";
            case 0xFE7D2: return "E599";
            case 0xFE7D3: return "E4B7";
            case 0xFE7D4: return "E4B6";
            case 0xFE7D5: return "EAAC";
            case 0xFE7D6: return "E59A";
            case 0xFE7D7: return "E4B9";
            case 0xFE7D8: return "E4B8";
            case 0xFE7D9: return "E46B";
            case 0xFE7DA: return "EB41";
            case 0xFE7DB: return "E5D3";
            case 0xFE7DD: return "E4BB";
            case 0xFE7DE: return "EADE";
            case 0xFE7DF: return "E4B5";
            case 0xFE7E0: return "E5BC";
            case 0xFE7E3: return "E4B0";
            case 0xFE7E4: return "E4B1";
            case 0xFE7E6: return "E4AF";
            case 0xFE7E7: return "E4A7";
            case 0xFE7E8: return "EA82";
            case 0xFE7E9: return "E4B3";
            case 0xFE7EA: return "E4B4";
            case 0xFE7EB: return "E4AE";
            case 0xFE7EC: return "EB6D";
            case 0xFE7ED: return "E5C8";
            case 0xFE7F0: return "EB72";
            case 0xFE7F1: return "E4B2";
            case 0xFE7F2: return "EADF";
            case 0xFE7F3: return "EAE0";
            case 0xFE7F4: return "EAE1";
            case 0xFE7F5: return "E571";
            case 0xFE7F6: return "E4A6";
            case 0xFE7F7: return "E46A";
            case 0xFE7F8: return "E5D7";
            case 0xFE7F9: return "EB73";
            case 0xFE7FA: return "E4BC";
            case 0xFE7FB: return "E5D0";
            case 0xFE7FD: return "E46D";
            case 0xFE7FE: return "EAE2";
            case 0xFE7FF: return "EB42";
            case 0xFE800: return "E503";
            case 0xFE801: return "E517";
            case 0xFE803: return "E508";
            case 0xFE804: return "E59C";
            case 0xFE805: return "EAF5";
            case 0xFE806: return "E59E";
            case 0xFE807: return "E49E";
            case 0xFE808: return "E4BE";
            case 0xFE809: return "E59D";
            case 0xFE80A: return "E4C6";
            case 0xFE80B: return "E5D1";
            case 0xFE80C: return "E4C5";
            case 0xFE80D: return "E46E";
            case 0xFE80E: return "EADD";
            case 0xFE80F: return "E4C8";
            case 0xFE810: return "EB43";
            case 0xFE811: return "EB6E";
            case 0xFE812: return "EB6F";
            case 0xFE813: return "E5BE";
            case 0xFE814: return "E505";
            case 0xFE816: return "E506";
            case 0xFE817: return "EB40";
            case 0xFE818: return "EADC";
            case 0xFE819: return "E507";
            case 0xFE81A: return "EACC";
            case 0xFE81C: return "E502";
            case 0xFE81D: return "E50C";
            case 0xFE81F: return "E5B9";
            case 0xFE820: return "E580";
            case 0xFE822: return "E58B";
            case 0xFE823: return "E4EB";
            case 0xFE824: return "EB78";
            case 0xFE825: return "E514";
            case 0xFE827: return "E5CA";
            case 0xFE828: return "EA95";
            case 0xFE829: return "EADA";
            case 0xFE82C: return "EB84";
            case 0xFE82E: return "E522";
            case 0xFE82F: return "E523";
            case 0xFE830: return "E524";
            case 0xFE831: return "E525";
            case 0xFE832: return "E526";
            case 0xFE833: return "E527";
            case 0xFE834: return "E528";
            case 0xFE835: return "E529";
            case 0xFE836: return "E52A";
            case 0xFE837: return "E5AC";
            case 0xFE838: return "EA84";
            case 0xFE839: return "EA90";
            case 0xFE83A: return "EA91";
            case 0xFE83B: return "E52B";
            case 0xFE960: return "E4D6";
            case 0xFE961: return "E4D5";
            case 0xFE962: return "E4D0";
            case 0xFE963: return "E5B4";
            case 0xFE964: return "EAAF";
            case 0xFE965: return "E4D1";
            case 0xFE966: return "EAB0";
            case 0xFE967: return "EAB1";
            case 0xFE968: return "EAB2";
            case 0xFE969: return "EAB3";
            case 0xFE96A: return "EAB4";
            case 0xFE96B: return "EAB5";
            case 0xFE96C: return "EAB6";
            case 0xFE96D: return "EAB7";
            case 0xFE96E: return "EAB8";
            case 0xFE96F: return "EABD";
            case 0xFE970: return "EABE";
            case 0xFE971: return "EAEA";
            case 0xFE972: return "E4C4";
            case 0xFE973: return "E4ED";
            case 0xFE974: return "EB3A";
            case 0xFE975: return "EB3B";
            case 0xFE976: return "EB3C";
            case 0xFE977: return "EB4A";
            case 0xFE978: return "EB4B";
            case 0xFE979: return "EB4C";
            case 0xFE97A: return "EB4D";
            case 0xFE97B: return "EB4E";
            case 0xFE97C: return "EB4F";
            case 0xFE97D: return "EB56";
            case 0xFE97E: return "EB59";
            case 0xFE97F: return "EB70";
            case 0xFE980: return "E4AC";
            case 0xFE981: return "E597";
            case 0xFE982: return "E4C2";
            case 0xFE983: return "E4C3";
            case 0xFE984: return "EAAE";
            case 0xFE985: return "EA97";
            case 0xFE986: return "E4C1";
            case 0xFE987: return "EA98";
            case 0xFE988: return "EB3E";
            case 0xFEAF0: return "E555";
            case 0xFEAF1: return "E54D";
            case 0xFEAF2: return "E54C";
            case 0xFEAF3: return "E556";
            case 0xFEAF4: return "EB2D";
            case 0xFEAF5: return "EB2E";
            case 0xFEAF6: return "EB7A";
            case 0xFEAF7: return "EB7B";
            case 0xFEAF8: return "E53F";
            case 0xFEAF9: return "E540";
            case 0xFEAFA: return "E552";
            case 0xFEAFB: return "E553";
            case 0xFEAFC: return "E52E";
            case 0xFEAFD: return "E52D";
            case 0xFEAFE: return "E530";
            case 0xFEAFF: return "E52F";
            case 0xFEB00: return "E542";
            case 0xFEB01: return "E543";
            case 0xFEB02: return "E544";
            case 0xFEB03: return "E545";
            case 0xFEB04: return "E482";
            case 0xFEB05: return "EB2F";
            case 0xFEB06: return "EB30";
            case 0xFEB08: return "EB31";
            case 0xFEB09: return "E483";
            case 0xFEB0C: return "E595";
            case 0xFEB0D: return "EB75";
            case 0xFEB0E: return "E477";
            case 0xFEB0F: return "E478";
            case 0xFEB10: return "EAA6";
            case 0xFEB12: return "E4EA";
            case 0xFEB13: return "EAA7";
            case 0xFEB14: return "EAA8";
            case 0xFEB15: return "EAA9";
            case 0xFEB16: return "EAAA";
            case 0xFEB17: return "EB54";
            case 0xFEB18: return "E5AF";
            case 0xFEB1A: return "EAA5";
            case 0xFEB1B: return "E5A1";
            case 0xFEB1C: return "E5A2";
            case 0xFEB1D: return "E5A3";
            case 0xFEB1E: return "E47D";
            case 0xFEB1F: return "E47E";
            case 0xFEB20: return "E47F";
            case 0xFEB21: return "E578";
            case 0xFEB22: return "EB2C";
            case 0xFEB23: return "E481";
            case 0xFEB25: return "EA83";
            case 0xFEB26: return "E484";
            case 0xFEB27: return "E5AD";
            case 0xFEB29: return "E558";
            case 0xFEB2A: return "E54E";
            case 0xFEB2B: return "E4F1";
            case 0xFEB2C: return "EB79";
            case 0xFEB2D: return "E559";
            case 0xFEB2F: return "EA8A";
            case 0xFEB31: return "EA89";
            case 0xFEB32: return "E5D2";
            case 0xFEB36: return "E5B5";
            case 0xFEB37: return "E50F";
            case 0xFEB38: return "EA85";
            case 0xFEB3D: return "E4F7";
            case 0xFEB3E: return "EA86";
            case 0xFEB3F: return "EA87";
            case 0xFEB40: return "EA8B";
            case 0xFEB41: return "EA8C";
            case 0xFEB43: return "EA99";
            case 0xFEB44: return "EAAD";
            case 0xFEB45: return "E550";
            case 0xFEB46: return "E551";
            case 0xFEB47: return "E533";
            case 0xFEB48: return "E541";
            case 0xFEB49: return "E557";
            case 0xFEB4A: return "E55E";
            case 0xFEB4B: return "E58A";
            case 0xFEB4C: return "E58C";
            case 0xFEB4D: return "E58D";
            case 0xFEB4E: return "E58E";
            case 0xFEB4F: return "E4E8";
            case 0xFEB50: return "EB01";
            case 0xFEB51: return "E53C";
            case 0xFEB52: return "E53D";
            case 0xFEB53: return "E54F";
            case 0xFEB54: return "E554";
            case 0xFEB56: return "E476";
            case 0xFEB57: return "E4E5";
            case 0xFEB58: return "E47A";
            case 0xFEB59: return "E475";
            case 0xFEB5A: return "E5B0";
            case 0xFEB5B: return "E5B1";
            case 0xFEB5C: return "E4E6";
            case 0xFEB5D: return "E4F4";
            case 0xFEB5E: return "E4E9";
            case 0xFEB5F: return "EB5C";
            case 0xFEB60: return "EAAB";
            case 0xFEB61: return "E479";
            case 0xFEB62: return "E53E";
            case 0xFEB63: return "E54A";
            case 0xFEB64: return "E54B";
            case 0xFEB65: return "E53A";
            case 0xFEB66: return "E53B";
            case 0xFEB68: return "E48B";
            case 0xFEB6A: return "E468";
            case 0xFEB6B: return "E548";
            case 0xFEB6C: return "E549";
            case 0xFEB6D: return "E531";
            case 0xFEB6E: return "E532";
            case 0xFEB6F: return "E534";
            case 0xFEB70: return "E535";
            case 0xFEB71: return "E538";
            case 0xFEB72: return "E539";
            case 0xFEB73: return "E546";
            case 0xFEB74: return "E547";
            case 0xFEB75: return "E536";
            case 0xFEB76: return "E537";
            case 0xFEB77: return "E46C";
            case 0xFEB78: return "E55A";
            case 0xFEB79: return "E55B";
            case 0xFEB7A: return "E4F0";
            case 0xFEB7B: return "E4F2";
            case 0xFEB7C: return "EAFD";
            case 0xFEB7D: return "EAFE";
            case 0xFEB7E: return "EAFF";
            case 0xFEB7F: return "EB00";
            case 0xFEB80: return "EB55";
            case 0xFEB81: return "EA88";
            case 0xFEB82: return "E519";
            case 0xFEB83: return "E55D";
            case 0xFEB84: return "E5AB";
            case 0xFEB85: return "E518";
            case 0xFEB86: return "E51C";
            case 0xFEB88: return "E55C";
            case 0xFEB8A: return "EAFC";
            case 0xFEB8B: return "EB02";
            case 0xFEB8C: return "EB04";
            case 0xFEB8D: return "EB05";
            case 0xFEB8E: return "EB06";
            case 0xFEB8F: return "EB07";
            case 0xFEB90: return "EB0C";
            case 0xFEB91: return "EB0D";
            case 0xFEB92: return "EB71";
            case 0xFEB93: return "EB83";
            case 0xFEB94: return "E5A6";
            case 0xFEB95: return "E5A7";
            case 0xFEB96: return "E4F3";
            case 0xFEB97: return "E4F9";
            case 0xFEB98: return "E4F6";
            case 0xFEB99: return "EA8D";
            case 0xFEB9A: return "EA8E";
            case 0xFEB9B: return "E4FF";
            case 0xFEB9C: return "E500";
            case 0xFEB9D: return "EAD6";
            case 0xFEB9E: return "EAD3";
            case 0xFEB9F: return "EAD4";
            case 0xFEBA0: return "EAD5";

            /* unknown */
            case 0xFEE1C: return "E517";
            case 0xFEE33: return "E5BC";
            case 0xFEE40: return "E577";
            case 0xFEE41: return "E5B2";
            case 0xFEE42: return "E264";
            case 0xFEE43: return "E328";
            case 0xFEE44: return "E335";
            case 0xFEE45: return "E33D";
            case 0xFEE46: return "E33E";
            case 0xFEE47: return "E33F";
            case 0xFEE48: return "E340";
            case 0xFEE49: return "E341";
            case 0xFEE4A: return "E342";

            // unicode 6
            case 0x0023 : if (nextCodePoint == 0x20E3) return "EB84"; else return null;
            case 0x0030 : if (nextCodePoint == 0x20E3) return "E5AC"; else return null;
            case 0x0031 : if (nextCodePoint == 0x20E3) return "E522"; else return null;
            case 0x0032 : if (nextCodePoint == 0x20E3) return "E523"; else return null;
            case 0x0033 : if (nextCodePoint == 0x20E3) return "E524"; else return null;
            case 0x0034 : if (nextCodePoint == 0x20E3) return "E525"; else return null;
            case 0x0035 : if (nextCodePoint == 0x20E3) return "E526"; else return null;
            case 0x0036 : if (nextCodePoint == 0x20E3) return "E527"; else return null;
            case 0x0037 : if (nextCodePoint == 0x20E3) return "E528"; else return null;
            case 0x0038 : if (nextCodePoint == 0x20E3) return "E529"; else return null;
            case 0x0039 : if (nextCodePoint == 0x20E3) return "E52A"; else return null;
            case 0x00A9 : return "E558";
            case 0x00AE : return "E559";
            case 0x2002 : return "E58D";
            case 0x2003 : return "E58C";
            case 0x2005 : return "E58E";
            case 0x203C : return "EB30";
            case 0x2049 : return "EB2F";
            case 0x2122 : return "E54E";
            case 0x2139 : return "E533";
            case 0x2194 : return "EB7A";
            case 0x2195 : return "EB7B";
            case 0x2196 : return "E54C";
            case 0x2197 : return "E555";
            case 0x2198 : return "E54D";
            case 0x2199 : return "E556";
            case 0x21A9 : return "E55D";
            case 0x21AA : return "E55C";
            case 0x231A : return "E57A";
            case 0x231B : return "E57B";
            case 0x23E9 : return "E530";
            case 0x23EA : return "E52F";
            case 0x23EB : return "E545";
            case 0x23EC : return "E544";
            case 0x23F0 : return "E594";
            case 0x23F3 : return "E47C";
            case 0x25AA : return "E532";
            case 0x25AB : return "E531";
            case 0x25B6 : return "E52E";
            case 0x25C0 : return "E52D";
            case 0x25FB : return "E538";
            case 0x25FC : return "E539";
            case 0x25FD : return "E534";
            case 0x25FE : return "E535";
            case 0x2600 : return "E488";
            case 0x2601 : return "E48D";
            case 0x260E : return "E596";
            case 0x2611 : return "EB02";
            case 0x2614 : return "E48C";
            case 0x2615 : return "E597";
            case 0x261D : return "E4F6";
            case 0x263A : return "E4FB";
            case 0x2648 : return "E48F";
            case 0x2649 : return "E490";
            case 0x264A : return "E491";
            case 0x264B : return "E492";
            case 0x264C : return "E493";
            case 0x264D : return "E494";
            case 0x264E : return "E495";
            case 0x264F : return "E496";
            case 0x2650 : return "E497";
            case 0x2651 : return "E498";
            case 0x2652 : return "E499";
            case 0x2653 : return "E49A";
            case 0x2660 : return "E5A1";
            case 0x2663 : return "E5A3";
            case 0x2665 : return "EAA5";
            case 0x2666 : return "E5A2";
            case 0x2668 : return "E4BC";
            case 0x267B : return "EB79";
            case 0x267F : return "E47F";
            case 0x2693 : return "E4A9";
            case 0x26A0 : return "E481";
            case 0x26A1 : return "E487";
            case 0x26AA : return "E53A";
            case 0x26AB : return "E53B";
            case 0x26BD : return "E4B6";
            case 0x26BE : return "E4BA";
            case 0x26C4 : return "E485";
            case 0x26C5 : return "E48E";
            case 0x26CE : return "E49B";
            case 0x26D4 : return "E484";
            case 0x26EA : return "E5BB";
            case 0x26F2 : return "E5CF";
            case 0x26F3 : return "E599";
            case 0x26F5 : return "E4B4";
            case 0x26FA : return "E5D0";
            case 0x26FD : return "E571";
            case 0x2702 : return "E516";
            case 0x2705 : return "E55E";
            case 0x2708 : return "E4B3";
            case 0x2709 : return "E521";
            case 0x270A : return "EB83";
            case 0x270B : return "E5A7";
            case 0x270C : return "E5A6";
            case 0x270F : return "E4A1";
            case 0x2712 : return "EB03";
            case 0x2714 : return "E557";
            case 0x2716 : return "E54F";
            case 0x2728 : return "EAAB";
            case 0x2733 : return "E53E";
            case 0x2734 : return "E479";
            case 0x2744 : return "E48A";
            case 0x2747 : return "E46C";
            case 0x274C : return "E550";
            case 0x274E : return "E551";
            case 0x2753 : return "E483";
            case 0x2757 : return "E482";
            case 0x2764 : return "E595";
            case 0x2795 : return "E53C";
            case 0x2796 : return "E53D";
            case 0x2797 : return "E554";
            case 0x27A1 : return "E552";
            case 0x27B0 : return "EB31";
            case 0x2934 : return "EB2D";
            case 0x2935 : return "EB2E";
            case 0x2B05 : return "E553";
            case 0x2B06 : return "E53F";
            case 0x2B07 : return "E540";
            case 0x2B1B : return "E549";
            case 0x2B1C : return "E548";
            case 0x2B50 : return "E48B";
            case 0x2B55 : return "EAAD";
            case 0x3297 : return "EA99";
            case 0x3299 : return "E4F1";
            case 0x1F004: return "E5D1";
            case 0x1F0CF: return "EB6F";
            case 0x1F170: return "EB26";
            case 0x1F171: return "EB27";
            case 0x1F17E: return "EB28";
            case 0x1F17F: return "E4A6";
            case 0x1F18E: return "EB29";
            case 0x1F191: return "E5AB";
            case 0x1F192: return "EA85";
            case 0x1F193: return "E578";
            case 0x1F194: return "EA88";
            case 0x1F195: return "E5B5";
            case 0x1F197: return "E5AD";
            case 0x1F198: return "E4E8";
            case 0x1F199: return "E50F";
            case 0x1F19A: return "E5D2";
            case 0x1F1E8: if (nextCodePoint == 0x1F1F3) return "EB11"; else return null;
            case 0x1F1E9: if (nextCodePoint == 0x1F1EA) return "EB0E"; else return null;
            case 0x1F1EA: if (nextCodePoint == 0x1F1F8) return "E5D5"; else return null;
            case 0x1F1EB: if (nextCodePoint == 0x1F1F7) return "EAFA"; else return null;
            case 0x1F1EC: if (nextCodePoint == 0x1F1E7) return "EB10"; else return null;
            case 0x1F1EE: if (nextCodePoint == 0x1F1F9) return "EB0F"; else return null;
            case 0x1F1EF: if (nextCodePoint == 0x1F1F5) return "E4CC"; else return null;
            case 0x1F1F0: if (nextCodePoint == 0x1F1F7) return "EB12"; else return null;
            case 0x1F1F7: if (nextCodePoint == 0x1F1FA) return "E5D6"; else return null;
            case 0x1F1FA: if (nextCodePoint == 0x1F1F8) return "E573"; else return null;
            case 0x1F202: return "EA87";
            case 0x1F22F: return "EA8B";
            case 0x1F233: return "EA8A";
            case 0x1F235: return "EA89";
            case 0x1F239: return "EA86";
            case 0x1F23A: return "EA8C";
            case 0x1F250: return "E4F7";
            case 0x1F251: return "EB01";
            case 0x1F300: return "E469";
            case 0x1F301: return "E598";
            case 0x1F302: return "EAE8";
            case 0x1F303: return "EAF1";
            case 0x1F305: return "EAF4";
            case 0x1F306: return "E5DA";
            case 0x1F308: return "EAF2";
            case 0x1F309: return "E4BF";
            case 0x1F30A: return "EB7C";
            case 0x1F30B: return "EB53";
            case 0x1F30C: return "EB5F";
            case 0x1F30F: return "E5B3";
            case 0x1F311: return "E5A8";
            case 0x1F313: return "E5AA";
            case 0x1F314: return "E5A9";
            case 0x1F319: return "E486";
            case 0x1F31B: return "E489";
            case 0x1F320: return "E468";
            case 0x1F330: return "EB38";
            case 0x1F331: return "EB7D";
            case 0x1F334: return "E4E2";
            case 0x1F335: return "EA96";
            case 0x1F337: return "E4E4";
            case 0x1F338: return "E4CA";
            case 0x1F339: return "E5BA";
            case 0x1F33A: return "EA94";
            case 0x1F33B: return "E4E3";
            case 0x1F33C: return "EB49";
            case 0x1F33D: return "EB36";
            case 0x1F33F: return "EB82";
            case 0x1F340: return "E513";
            case 0x1F341: return "E4CE";
            case 0x1F342: return "E5CD";
            case 0x1F344: return "EB37";
            case 0x1F345: return "EABB";
            case 0x1F346: return "EABC";
            case 0x1F347: return "EB34";
            case 0x1F348: return "EB32";
            case 0x1F349: return "E4CD";
            case 0x1F34A: return "EABA";
            case 0x1F34C: return "EB35";
            case 0x1F34D: return "EB33";
            case 0x1F34E: return "EAB9";
            case 0x1F34F: return "EB5A";
            case 0x1F351: return "EB39";
            case 0x1F352: return "E4D2";
            case 0x1F353: return "E4D4";
            case 0x1F354: return "E4D6";
            case 0x1F355: return "EB3B";
            case 0x1F356: return "E4C4";
            case 0x1F357: return "EB3C";
            case 0x1F358: return "EAB3";
            case 0x1F359: return "E4D5";
            case 0x1F35A: return "EAB4";
            case 0x1F35B: return "EAB6";
            case 0x1F35C: return "E5B4";
            case 0x1F35D: return "EAB5";
            case 0x1F35E: return "EAAF";
            case 0x1F35F: return "EAB1";
            case 0x1F360: return "EB3A";
            case 0x1F361: return "EAB2";
            case 0x1F362: return "EAB7";
            case 0x1F363: return "EAB8";
            case 0x1F364: return "EB70";
            case 0x1F365: return "E4ED";
            case 0x1F366: return "EAB0";
            case 0x1F367: return "EAEA";
            case 0x1F368: return "EB4A";
            case 0x1F369: return "EB4B";
            case 0x1F36A: return "EB4C";
            case 0x1F36B: return "EB4D";
            case 0x1F36C: return "EB4E";
            case 0x1F36D: return "EB4F";
            case 0x1F36E: return "EB56";
            case 0x1F36F: return "EB59";
            case 0x1F370: return "E4D0";
            case 0x1F371: return "EABD";
            case 0x1F372: return "EABE";
            case 0x1F373: return "E4D1";
            case 0x1F374: return "E4AC";
            case 0x1F375: return "EAAE";
            case 0x1F376: return "EA97";
            case 0x1F377: return "E4C1";
            case 0x1F378: return "E4C2";
            case 0x1F379: return "EB3E";
            case 0x1F37A: return "E4C3";
            case 0x1F37B: return "EA98";
            case 0x1F380: return "E59F";
            case 0x1F381: return "E4CF";
            case 0x1F382: return "E5A0";
            case 0x1F383: return "EAEE";
            case 0x1F384: return "E4C9";
            case 0x1F385: return "EAF0";
            case 0x1F386: return "E5CC";
            case 0x1F387: return "EAEB";
            case 0x1F388: return "EA9B";
            case 0x1F389: return "EA9C";
            case 0x1F38A: return "E46F";
            case 0x1F38B: return "EB3D";
            case 0x1F38C: return "E5D9";
            case 0x1F38D: return "EAE3";
            case 0x1F38E: return "EAE4";
            case 0x1F38F: return "EAE7";
            case 0x1F390: return "EAED";
            case 0x1F391: return "EAEF";
            case 0x1F392: return "EAE6";
            case 0x1F393: return "EAE5";
            case 0x1F3A1: return "E46D";
            case 0x1F3A2: return "EAE2";
            case 0x1F3A3: return "EB42";
            case 0x1F3A4: return "E503";
            case 0x1F3A5: return "E517";
            case 0x1F3A7: return "E508";
            case 0x1F3A8: return "E59C";
            case 0x1F3A9: return "EAF5";
            case 0x1F3AA: return "E59E";
            case 0x1F3AB: return "E49E";
            case 0x1F3AC: return "E4BE";
            case 0x1F3AD: return "E59D";
            case 0x1F3AE: return "E4C6";
            case 0x1F3AF: return "E4C5";
            case 0x1F3B0: return "E46E";
            case 0x1F3B1: return "EADD";
            case 0x1F3B2: return "E4C8";
            case 0x1F3B3: return "EB43";
            case 0x1F3B4: return "EB6E";
            case 0x1F3B5: return "E5BE";
            case 0x1F3B6: return "E505";
            case 0x1F3B8: return "E506";
            case 0x1F3B9: return "EB40";
            case 0x1F3BA: return "EADC";
            case 0x1F3BB: return "E507";
            case 0x1F3BC: return "EACC";
            case 0x1F3BE: return "E4B7";
            case 0x1F3BF: return "EAAC";
            case 0x1F3C0: return "E59A";
            case 0x1F3C1: return "E4B9";
            case 0x1F3C2: return "E4B8";
            case 0x1F3C3: return "E46B";
            case 0x1F3C4: return "EB41";
            case 0x1F3C6: return "E5D3";
            case 0x1F3C8: return "E4BB";
            case 0x1F3CA: return "EADE";
            case 0x1F3E0: return "E4AB";
            case 0x1F3E1: return "EB09";
            case 0x1F3E2: return "E4AD";
            case 0x1F3E3: return "E5DE";
            case 0x1F3E5: return "E5DF";
            case 0x1F3E6: return "E4AA";
            case 0x1F3E7: return "E4A3";
            case 0x1F3E8: return "EA81";
            case 0x1F3E9: return "EAF3";
            case 0x1F3EA: return "E4A4";
            case 0x1F3EB: return "EA80";
            case 0x1F3EC: return "EAF6";
            case 0x1F3ED: return "EAF9";
            case 0x1F3EE: return "E4BD";
            case 0x1F3EF: return "EAF7";
            case 0x1F3F0: return "EAF8";
            case 0x1F40C: return "EB7E";
            case 0x1F40D: return "EB22";
            case 0x1F414: return "EB23";
            case 0x1F417: return "EB24";
            case 0x1F418: return "EB1F";
            case 0x1F419: return "E5C7";
            case 0x1F41A: return "EAEC";
            case 0x1F41B: return "EB1E";
            case 0x1F41C: return "E4DD";
            case 0x1F41D: return "EB57";
            case 0x1F41E: return "EB58";
            case 0x1F420: return "EB1D";
            case 0x1F421: return "E4D3";
            case 0x1F422: return "E5D4";
            case 0x1F423: return "E5DB";
            case 0x1F424: return "E4E0";
            case 0x1F425: return "EB76";
            case 0x1F427: return "E4DC";
            case 0x1F428: return "EB20";
            case 0x1F429: return "E4DF";
            case 0x1F42B: return "EB25";
            case 0x1F42C: return "EB1B";
            case 0x1F42D: return "E5C2";
            case 0x1F42E: return "EB21";
            case 0x1F42F: return "E5C0";
            case 0x1F430: return "E4D7";
            case 0x1F431: return "E4DB";
            case 0x1F432: return "EB3F";
            case 0x1F433: return "E470";
            case 0x1F434: return "E4D8";
            case 0x1F435: return "E4D9";
            case 0x1F436: return "E4E1";
            case 0x1F437: return "E4DE";
            case 0x1F438: return "E4DA";
            case 0x1F43B: return "E5C1";
            case 0x1F43C: return "EB46";
            case 0x1F43D: return "EB48";
            case 0x1F43E: return "E4EE";
            case 0x1F440: return "E5A4";
            case 0x1F442: return "E5A5";
            case 0x1F443: return "EAD0";
            case 0x1F444: return "EAD1";
            case 0x1F445: return "EB47";
            case 0x1F446: return "EA8D";
            case 0x1F447: return "EA8E";
            case 0x1F448: return "E4FF";
            case 0x1F449: return "E500";
            case 0x1F44A: return "E4F3";
            case 0x1F44B: return "EAD6";
            case 0x1F44C: return "EAD4";
            case 0x1F44D: return "E4F9";
            case 0x1F44E: return "EAD5";
            case 0x1F44F: return "EAD3";
            case 0x1F451: return "E5C9";
            case 0x1F452: return "EA9E";
            case 0x1F453: return "E4FE";
            case 0x1F454: return "EA93";
            case 0x1F455: return "E5B6";
            case 0x1F456: return "EB77";
            case 0x1F457: return "EB6B";
            case 0x1F458: return "EAA3";
            case 0x1F459: return "EAA4";
            case 0x1F45A: return "E50D";
            case 0x1F45B: return "E504";
            case 0x1F45C: return "E49C";
            case 0x1F45E: return "E5B7";
            case 0x1F45F: return "EB2B";
            case 0x1F460: return "E51A";
            case 0x1F462: return "EA9F";
            case 0x1F463: return "EB2A";
            case 0x1F468: return "E4FC";
            case 0x1F469: return "E4FA";
            case 0x1F46A: return "E501";
            case 0x1F46E: return "E5DD";
            case 0x1F46F: return "EADB";
            case 0x1F470: return "EAE9";
            case 0x1F471: return "EB13";
            case 0x1F472: return "EB14";
            case 0x1F473: return "EB15";
            case 0x1F474: return "EB16";
            case 0x1F475: return "EB17";
            case 0x1F476: return "EB18";
            case 0x1F477: return "EB19";
            case 0x1F478: return "EB1A";
            case 0x1F479: return "EB44";
            case 0x1F47A: return "EB45";
            case 0x1F47B: return "E4CB";
            case 0x1F47C: return "E5BF";
            case 0x1F47D: return "E50E";
            case 0x1F47E: return "E4EC";
            case 0x1F47F: return "E4EF";
            case 0x1F480: return "E4F8";
            case 0x1F483: return "EB1C";
            case 0x1F484: return "E509";
            case 0x1F485: return "EAA0";
            case 0x1F486: return "E50B";
            case 0x1F487: return "EAA1";
            case 0x1F488: return "EAA2";
            case 0x1F489: return "E510";
            case 0x1F48A: return "EA9A";
            case 0x1F48B: return "E4EB";
            case 0x1F48C: return "EB78";
            case 0x1F48D: return "E514";
            case 0x1F48F: return "E5CA";
            case 0x1F490: return "EA95";
            case 0x1F491: return "EADA";
            case 0x1F493: return "EB75";
            case 0x1F494: return "E477";
            case 0x1F495: return "E478";
            case 0x1F496: return "EAA6";
            case 0x1F498: return "E4EA";
            case 0x1F499: return "EAA7";
            case 0x1F49A: return "EAA8";
            case 0x1F49B: return "EAA9";
            case 0x1F49C: return "EAAA";
            case 0x1F49D: return "EB54";
            case 0x1F49E: return "E5AF";
            case 0x1F4A1: return "E476";
            case 0x1F4A2: return "E4E5";
            case 0x1F4A3: return "E47A";
            case 0x1F4A4: return "E475";
            case 0x1F4A5: return "E5B0";
            case 0x1F4A6: return "E5B1";
            case 0x1F4A7: return "E4E6";
            case 0x1F4A8: return "E4F4";
            case 0x1F4A9: return "E4F5";
            case 0x1F4AA: return "E4E9";
            case 0x1F4AB: return "EB5C";
            case 0x1F4AC: return "E4FD";
            case 0x1F4AE: return "E4F0";
            case 0x1F4AF: return "E4F2";
            case 0x1F4B0: return "E4C7";
            case 0x1F4B2: return "E579";
            case 0x1F4B3: return "E57C";
            case 0x1F4B4: return "E57D";
            case 0x1F4B5: return "E585";
            case 0x1F4B8: return "EB5B";
            case 0x1F4B9: return "E5DC";
            case 0x1F4BB: return "E5B8";
            case 0x1F4BC: return "E5CE";
            case 0x1F4BD: return "E582";
            case 0x1F4BE: return "E562";
            case 0x1F4BF: return "E50C";
            case 0x1F4C1: return "E58F";
            case 0x1F4C2: return "E590";
            case 0x1F4C3: return "E561";
            case 0x1F4C4: return "E569";
            case 0x1F4C5: return "E563";
            case 0x1F4C6: return "E56A";
            case 0x1F4C7: return "E56C";
            case 0x1F4C8: return "E575";
            case 0x1F4C9: return "E576";
            case 0x1F4CA: return "E574";
            case 0x1F4CB: return "E564";
            case 0x1F4CC: return "E56D";
            case 0x1F4CD: return "E560";
            case 0x1F4CE: return "E4A0";
            case 0x1F4CF: return "E570";
            case 0x1F4D0: return "E4A2";
            case 0x1F4D1: return "EB0B";
            case 0x1F4D2: return "E56E";
            case 0x1F4D3: return "E56B";
            case 0x1F4D4: return "E49D";
            case 0x1F4D5: return "E568";
            case 0x1F4D6: return "E49F";
            case 0x1F4D7: return "E565";
            case 0x1F4D8: return "E566";
            case 0x1F4D9: return "E567";
            case 0x1F4DA: return "E56F";
            case 0x1F4DB: return "E51D";
            case 0x1F4DC: return "E55F";
            case 0x1F4DD: return "EA92";
            case 0x1F4DE: return "E51E";
            case 0x1F4DF: return "E59B";
            case 0x1F4E0: return "E520";
            case 0x1F4E1: return "E4A8";
            case 0x1F4E4: return "E592";
            case 0x1F4E5: return "E593";
            case 0x1F4E6: return "E51F";
            case 0x1F4E7: return "EB71";
            case 0x1F4E8: return "E591";
            case 0x1F4E9: return "EB62";
            case 0x1F4EA: return "E51B";
            case 0x1F4EB: return "EB0A";
            case 0x1F4F0: return "E58B";
            case 0x1F4F1: return "E588";
            case 0x1F4F2: return "EB08";
            case 0x1F4F3: return "EA90";
            case 0x1F4F4: return "EA91";
            case 0x1F4F6: return "EA84";
            case 0x1F4F7: return "E515";
            case 0x1F4F9: return "E57E";
            case 0x1F4FA: return "E502";
            case 0x1F4FB: return "E5B9";
            case 0x1F4FC: return "E580";
            case 0x1F503: return "EB0D";
            case 0x1F50A: return "E511";
            case 0x1F50B: return "E584";
            case 0x1F50C: return "E589";
            case 0x1F50D: return "E518";
            case 0x1F50E: return "EB05";
            case 0x1F50F: return "EB0C";
            case 0x1F510: return "EAFC";
            case 0x1F511: return "E519";
            case 0x1F512: return "E51C";
            case 0x1F514: return "E512";
            case 0x1F516: return "EB07";
            case 0x1F517: return "E58A";
            case 0x1F518: return "EB04";
            case 0x1F519: return "EB06";
            case 0x1F51E: return "EA83";
            case 0x1F51F: return "E52B";
            case 0x1F520: return "EAFD";
            case 0x1F521: return "EAFE";
            case 0x1F522: return "EAFF";
            case 0x1F523: return "EB00";
            case 0x1F524: return "EB55";
            case 0x1F525: return "E47B";
            case 0x1F526: return "E583";
            case 0x1F527: return "E587";
            case 0x1F528: return "E5CB";
            case 0x1F529: return "E581";
            case 0x1F52A: return "E57F";
            case 0x1F52B: return "E50A";
            case 0x1F52E: return "EA8F";
            case 0x1F530: return "E480";
            case 0x1F534: return "E54A";
            case 0x1F535: return "E54B";
            case 0x1F536: return "E546";
            case 0x1F537: return "E547";
            case 0x1F538: return "E536";
            case 0x1F539: return "E537";
            case 0x1F53A: return "E55A";
            case 0x1F53B: return "E55B";
            case 0x1F53C: return "E543";
            case 0x1F53D: return "E542";
            case 0x1F5FB: return "E5BD";
            case 0x1F5FC: return "E4C0";
            case 0x1F5FE: return "E572";
            case 0x1F5FF: return "EB6C";
            case 0x1F601: return "EB80";
            case 0x1F602: return "EB64";
            case 0x1F603: return "E471";
            case 0x1F609: return "E5C3";
            case 0x1F60A: return "EACD";
            case 0x1F60C: return "EAC5";
            case 0x1F60D: return "E5C4";
            case 0x1F60F: return "EABF";
            case 0x1F612: return "EAC9";
            case 0x1F613: return "E5C6";
            case 0x1F614: return "EAC0";
            case 0x1F616: return "EAC3";
            case 0x1F618: return "EACF";
            case 0x1F61A: return "EACE";
            case 0x1F61C: return "E4E7";
            case 0x1F620: return "E472";
            case 0x1F621: return "EB5D";
            case 0x1F622: return "EB69";
            case 0x1F623: return "EAC2";
            case 0x1F624: return "EAC1";
            case 0x1F628: return "EAC6";
            case 0x1F629: return "EB67";
            case 0x1F62A: return "EAC4";
            case 0x1F62B: return "E474";
            case 0x1F62D: return "E473";
            case 0x1F630: return "EACB";
            case 0x1F631: return "E5C5";
            case 0x1F632: return "EACA";
            case 0x1F633: return "EAC8";
            case 0x1F635: return "E5AE";
            case 0x1F637: return "EAC7";
            case 0x1F638: return "EB7F";
            case 0x1F639: return "EB63";
            case 0x1F63A: return "EB61";
            case 0x1F63B: return "EB65";
            case 0x1F63C: return "EB6A";
            case 0x1F63D: return "EB60";
            case 0x1F63E: return "EB5E";
            case 0x1F63F: return "EB68";
            case 0x1F640: return "EB66";
            case 0x1F645: return "EAD7";
            case 0x1F646: return "EAD8";
            case 0x1F647: return "EAD9";
            case 0x1F648: return "EB50";
            case 0x1F649: return "EB52";
            case 0x1F64A: return "EB51";
            case 0x1F64B: return "EB85";
            case 0x1F64C: return "EB86";
            case 0x1F64D: return "EB87";
            case 0x1F64E: return "EB88";
            case 0x1F64F: return "EAD2";
            case 0x1F680: return "E5C8";
            case 0x1F683: return "E4B5";
            case 0x1F685: return "E4B0";
            case 0x1F687: return "E5BC";
            case 0x1F689: return "EB6D";
            case 0x1F68C: return "E4AF";
            case 0x1F68F: return "E4A7";
            case 0x1F691: return "EAE0";
            case 0x1F692: return "EADF";
            case 0x1F693: return "EAE1";
            case 0x1F697: return "E4B1";
            case 0x1F69A: return "E4B2";
            case 0x1F6A2: return "EA82";
            case 0x1F6A5: return "E46A";
            case 0x1F6A7: return "E5D7";
            case 0x1F6A8: return "EB73";
            case 0x1F6A9: return "EB2C";
            case 0x1F6AB: return "E541";
            case 0x1F6AC: return "E47D";
            case 0x1F6AD: return "E47E";
            case 0x1F6B2: return "E4AE";
            case 0x1F6B6: return "EB72";
            case 0x1F6BB: return "E4A5";
            case 0x1F6C0: return "E5D8";

            default: return null;
        }
    }

    private static String getEmojiForCodePointSoftBank(int codePoint, int nextCodePoint) {

        switch (codePoint) {
// softbank
            case 0xFE82C: return "E210";
            case 0xFE837: return "E225";
            case 0xFE82E: return "E21C";
            case 0xFE82F: return "E21D";
            case 0xFE830: return "E21E";
            case 0xFE831: return "E21F";
            case 0xFE832: return "E220";
            case 0xFE833: return "E221";
            case 0xFE834: return "E222";
            case 0xFE835: return "E223";
            case 0xFE836: return "E224";
            case 0xFEB29: return "E24E";
            case 0xFEB2D: return "E24F";
            case 0xFEB2A: return "E537";
            case 0xFEAF2: return "E237";
            case 0xFEAF0: return "E236";
            case 0xFEAF1: return "E238";
            case 0xFEAF3: return "E239";
            case 0xFEAFE: return "E23C";
            case 0xFEAFF: return "E23D";
            case 0xFEAFC: return "E23A";
            case 0xFEAFD: return "E23B";
            case 0xFE000: return "E04A";
            case 0xFE001: return "E049";
            case 0xFE523: return "E009";
            case 0xFE002: return "E04B";
            case 0xFE981: return "E045";
            case 0xFEB98: return "E00F";
            case 0xFE337: return "E414"; // androidPUA should be 0xFE336
            case 0xFE02B: return "E23F";
            case 0xFE02C: return "E240";
            case 0xFE02D: return "E241";
            case 0xFE02E: return "E242";
            case 0xFE02F: return "E243";
            case 0xFE030: return "E244";
            case 0xFE031: return "E245";
            case 0xFE032: return "E246";
            case 0xFE033: return "E247";
            case 0xFE034: return "E248";
            case 0xFE035: return "E249";
            case 0xFE036: return "E24A";
            case 0xFEB1B: return "E20E";
            case 0xFEB1D: return "E20F";
            case 0xFEB1A: return "E20C";
            case 0xFEB1C: return "E20D";
            case 0xFE7FA: return "E123";
            case 0xFEB20: return "E20A";
            case 0xFEB23: return "E252";
            case 0xFE004: return "E13D";
            case 0xFE7D4: return "E018";
            case 0xFE7D1: return "E016";
            case 0xFE003: return "E048";
            case 0xFE037: return "E24B";
            case 0xFE4BB: return "E037";
            case 0xFE4BC: return "E121";
            case 0xFE7D2: return "E014";
            case 0xFE7EA: return "E01C";
            case 0xFE7FB: return "E122";
            case 0xFE7F5: return "E03A";
            case 0xFE53E: return "E313";
            case 0xFE7E9: return "E01D";
            case 0xFEB93: return "E010";
            case 0xFEB95: return "E012";
            case 0xFEB94: return "E011";
            case 0xFEB60: return "E32E";
            case 0xFEB62: return "E206";
            case 0xFEB61: return "E205";
            case 0xFEB45: return "E333";
            case 0xFEB09: return "E020";
            case 0xFEB0A: return "E336";
            case 0xFEB0B: return "E337";
            case 0xFEB04: return "E021";
            case 0xFEB0C: return "E022";
            case 0xFEAFA: return "E234";
            case 0xFEAFB: return "E235";
            case 0xFEAF8: return "E232";
            case 0xFEAF9: return "E233";
            case 0xFEB68: return "E32F";
            case 0xFEB44: return "E332";
            case 0xFE81B: return "E12C";
            case 0xFEB43: return "E30D";
            case 0xFEB2B: return "E315";
            case 0xFE80B: return "E12D";
            case 0xFE50B: return "E532";
            case 0xFE50C: return "E533";
            case 0xFE50E: return "E535";
            case 0xFE7F6: return "E14F";
            case 0xFE50D: return "E534";
            case 0xFEB38: return "E214";
            case 0xFEB81: return "E229";
            case 0xFEB36: return "E212";
            case 0xFEB27: return "E24D";
            case 0xFEB37: return "E213";
            case 0xFEB32: return "E12E";
            case 0xFE4ED: return "E513";
            case 0xFE4E8: return "E50E";
            case 0xFE4EB: return "E511";
            case 0xFE4E7: return "E50D";
            case 0xFE4EA: return "E510";
            case 0xFE4E9: return "E50F";
            case 0xFE4E5: return "E50B";
            case 0xFE4EE: return "E514";
            case 0xFE4EC: return "E512";
            case 0xFE4E6: return "E50C";
            case 0xFEB24: return "E203";
            case 0xFEB3F: return "E228";
            case 0xFEB3A: return "E216";
            case 0xFEB40: return "E22C";
            case 0xFEB2F: return "E22B";
            case 0xFEB31: return "E22A";
            case 0xFEB39: return "E215";
            case 0xFEB3B: return "E217";
            case 0xFEB3C: return "E218";
            case 0xFEB3E: return "E227";
            case 0xFEB41: return "E22D";
            case 0xFEB3D: return "E226";
            case 0xFE005: return "E443";
            case 0xFE007: return "E43C";
            case 0xFE008: return "E44B";
            case 0xFE009: return "E04D";
            case 0xFE00A: return "E449";
            case 0xFE00B: return "E146";
            case 0xFE00C: return "E44A";
            case 0xFE00D: return "E44C";
            case 0xFE038: return "E43E";
            case 0xFE014: return "E04C";
            case 0xFEB69: return "E335";
            case 0xFE047: return "E307";
            case 0xFE048: return "E308";
            case 0xFE03D: return "E304";
            case 0xFE040: return "E030";
            case 0xFE041: return "E032";
            case 0xFE045: return "E303";
            case 0xFE046: return "E305";
            case 0xFE049: return "E444";
            case 0xFE03C: return "E110";
            case 0xFE03F: return "E118";
            case 0xFE042: return "E119";
            case 0xFE043: return "E447";
            case 0xFE055: return "E349";
            case 0xFE056: return "E34A";
            case 0xFE054: return "E348";
            case 0xFE052: return "E346";
            case 0xFE051: return "E345";
            case 0xFE053: return "E347";
            case 0xFE960: return "E120";
            case 0xFE969: return "E33D";
            case 0xFE961: return "E342";
            case 0xFE96A: return "E33E";
            case 0xFE96C: return "E341";
            case 0xFE963: return "E340";
            case 0xFE96B: return "E33F";
            case 0xFE964: return "E339";
            case 0xFE967: return "E33B";
            case 0xFE968: return "E33C";
            case 0xFE96D: return "E343";
            case 0xFE96E: return "E344";
            case 0xFE966: return "E33A";
            case 0xFE971: return "E43F";
            case 0xFE962: return "E046";
            case 0xFE96F: return "E34C";
            case 0xFE970: return "E34D";
            case 0xFE965: return "E147";
            case 0xFE980: return "E043";
            case 0xFE984: return "E338";
            case 0xFE985: return "E30B";
            case 0xFE982: return "E044";
            case 0xFE983: return "E047";
            case 0xFE987: return "E30C";
            case 0xFE50F: return "E314";
            case 0xFE510: return "E112";
            case 0xFE511: return "E34B";
            case 0xFE51F: return "E445";
            case 0xFE512: return "E033";
            case 0xFE513: return "E448";
            case 0xFE515: return "E117";
            case 0xFE51D: return "E440";
            case 0xFE516: return "E310";
            case 0xFE517: return "E312";
            case 0xFE514: return "E143";
            case 0xFE518: return "E436";
            case 0xFE519: return "E438";
            case 0xFE51C: return "E43B";
            case 0xFE51E: return "E442";
            case 0xFE017: return "E446";
            case 0xFE51B: return "E43A";
            case 0xFE51A: return "E439";
            case 0xFE7FD: return "E124";
            case 0xFE7FE: return "E433";
            case 0xFE800: return "E03C";
            case 0xFE801: return "E03D";
            case 0xFE802: return "E507";
            case 0xFE803: return "E30A";
            case 0xFE804: return "E502";
            case 0xFE805: return "E503";
            case 0xFE807: return "E125";
            case 0xFE808: return "E324";
            case 0xFE80C: return "E130";
            case 0xFE80D: return "E133";
            case 0xFE80E: return "E42C";
            case 0xFE813: return "E03E";
            case 0xFE814: return "E326";
            case 0xFE815: return "E040";
            case 0xFE816: return "E041";
            case 0xFE818: return "E042";
            case 0xFE7D3: return "E015";
            case 0xFE7D5: return "E013";
            case 0xFE7D6: return "E42A";
            case 0xFE7D7: return "E132";
            case 0xFE7D9: return "E115";
            case 0xFE7DA: return "E017";
            case 0xFE7DB: return "E131";
            case 0xFE7DD: return "E42B";
            case 0xFE7DE: return "E42D";
            case 0xFE4B0: return "E036";
            case 0xFE4B2: return "E038";
            case 0xFE4B3: return "E153";
            case 0xFE4B4: return "E155";
            case 0xFE4B5: return "E14D";
            case 0xFE4B6: return "E154";
            case 0xFE4B7: return "E158";
            case 0xFE4B8: return "E501";
            case 0xFE4B9: return "E156";
            case 0xFE4BA: return "E157";
            case 0xFE4BD: return "E504";
            case 0xFE4C0: return "E508";
            case 0xFE4BE: return "E505";
            case 0xFE4BF: return "E506";
            case 0xFE1D3: return "E52D";
            case 0xFE7DC: return "E134";
            case 0xFE1CF: return "E529";
            case 0xFE1CE: return "E528";
            case 0xFE1D4: return "E52E";
            case 0xFE1D5: return "E52F";
            case 0xFE1CC: return "E526";
            case 0xFE1C5: return "E10A";
            case 0xFE1C6: return "E441";
            case 0xFE1CB: return "E525";
            case 0xFE1BD: return "E019";
            case 0xFE1C9: return "E522";
            case 0xFE1BA: return "E523";
            case 0xFE1C8: return "E521";
            case 0xFE1BC: return "E055";
            case 0xFE1CD: return "E527";
            case 0xFE1D6: return "E530";
            case 0xFE1C7: return "E520";
            case 0xFE1C2: return "E053";
            case 0xFE1D1: return "E52B";
            case 0xFE1C0: return "E050";
            case 0xFE1D2: return "E52C";
            case 0xFE1B8: return "E04F";
            case 0xFE1C3: return "E054";
            case 0xFE1BE: return "E01A";
            case 0xFE1C4: return "E109";
            case 0xFE1B7: return "E052";
            case 0xFE1BF: return "E10B";
            case 0xFE1D7: return "E531";
            case 0xFE1CA: return "E524";
            case 0xFE1D0: return "E52A";
            case 0xFE1C1: return "E051";
            case 0xFE190: return "E419";
            case 0xFE191: return "E41B";
            case 0xFE192: return "E41A";
            case 0xFE193: return "E41C";
            case 0xFEB99: return "E22E";
            case 0xFEB9A: return "E22F";
            case 0xFEB9B: return "E230";
            case 0xFEB9C: return "E231";
            case 0xFEB96: return "E00D";
            case 0xFEB9D: return "E41E";
            case 0xFEB9F: return "E420";
            case 0xFEB97: return "E00E";
            case 0xFEBA0: return "E421";
            case 0xFEB9E: return "E41F";
            case 0xFEBA1: return "E422";
            case 0xFE4D1: return "E10E";
            case 0xFE4D4: return "E318";
            case 0xFE4D3: return "E302";
            case 0xFE4CF: return "E006";
            case 0xFE4D5: return "E319";
            case 0xFE4D9: return "E321";
            case 0xFE4DA: return "E322";
            case 0xFE4F0: return "E323";
            case 0xFE4CC: return "E007"; // androidPUA should be 0xFE4CD
            case 0xFE4D6: return "E13E";
            case 0xFE4D7: return "E31A";
            case 0xFE4D8: return "E31B";
            case 0xFE553: return "E536";
            case 0xFE19B: return "E001";
            case 0xFE19C: return "E002";
            case 0xFE19D: return "E004";
            case 0xFE19E: return "E005";
            case 0xFE1A0: return "E428";
            case 0xFE1A1: return "E152";
            case 0xFE1A2: return "E429";
            case 0xFE1A4: return "E515";
            case 0xFE1A5: return "E516";
            case 0xFE1A6: return "E517";
            case 0xFE1A7: return "E518";
            case 0xFE1A8: return "E519";
            case 0xFE1A9: return "E51A";
            case 0xFE1AA: return "E51B";
            case 0xFE1AB: return "E51C";
            case 0xFE1AE: return "E11B";
            case 0xFE1AF: return "E04E";
            case 0xFE1B0: return "E10C";
            case 0xFE1B1: return "E12B";
            case 0xFE1B2: return "E11A";
            case 0xFE1B3: return "E11C";
            case 0xFE1B4: return "E253";
            case 0xFE1B5: return "E51E";
            case 0xFE1B6: return "E51F";
            case 0xFE195: return "E31C";
            case 0xFE196: return "E31D";
            case 0xFE197: return "E31E";
            case 0xFE198: return "E31F";
            case 0xFE199: return "E320";
            case 0xFE509: return "E13B";
            case 0xFE50A: return "E30F";
            case 0xFE823: return "E003";
            case 0xFE825: return "E034";
            case 0xFE826: return "E035";
            case 0xFE827: return "E111";
            case 0xFE828: return "E306";
            case 0xFE829: return "E425";
            case 0xFE82A: return "E43D";
            case 0xFEB0D: return "E327";
            case 0xFEB0E: return "E023";
            case 0xFEB11: return "E328";
            case 0xFEB12: return "E329";
            case 0xFEB13: return "E32A";
            case 0xFEB14: return "E32B";
            case 0xFEB15: return "E32C";
            case 0xFEB16: return "E32D";
            case 0xFEB17: return "E437";
            case 0xFEB19: return "E204";
            case 0xFEB56: return "E10F";
            case 0xFEB57: return "E334";
            case 0xFEB58: return "E311";
            case 0xFEB59: return "E13C";
            case 0xFEB5B: return "E331";
            case 0xFEB5D: return "E330";
            case 0xFE4F4: return "E05A";
            case 0xFEB5E: return "E14C";
            case 0xFE4DD: return "E12F";
            case 0xFE4DE: return "E149";
            case 0xFE4DF: return "E14A";
            case 0xFE537: return "E11F";
            case 0xFE538: return "E00C";
            case 0xFE53B: return "E11E";
            case 0xFE53C: return "E316";
            case 0xFE81D: return "E126";
            case 0xFE81E: return "E127";
            case 0xFE546: return "E148";
            case 0xFE527: return "E301";
            case 0xFE528: return "E00B";
            case 0xFE531: return "E14B";
            case 0xFE52F: return "E142";
            case 0xFE530: return "E317";
            case 0xFE52B: return "E103";
            case 0xFE52C: return "E101"; // androidPUA should be 0xFE52D
            case 0xFE52E: return "E102";
            case 0xFE525: return "E00A";
            case 0xFE526: return "E104";
            case 0xFE839: return "E250";
            case 0xFE83A: return "E251";
            case 0xFE838: return "E20B";
            case 0xFE4EF: return "E008";
            case 0xFE81C: return "E12A";
            case 0xFE81F: return "E128";
            case 0xFE820: return "E129";
            case 0xFE821: return "E141";
            case 0xFEB85: return "E114";
            case 0xFEB82: return "E03F";
            case 0xFEB86: return "E144";
            case 0xFEB87: return "E145";
            case 0xFE4F2: return "E325";
            case 0xFEB42: return "E24C";
            case 0xFEB25: return "E207";
            case 0xFE4F6: return "E11D";
            case 0xFE4CA: return "E116";
            case 0xFE4F5: return "E113";
            case 0xFE4F8: return "E23E";
            case 0xFE044: return "E209";
            case 0xFE4D2: return "E031";
            case 0xFEB64: return "E21A";
            case 0xFEB67: return "E21B";
            case 0xFEB63: return "E219";
            case 0xFE01E: return "E024";
            case 0xFE01F: return "E025";
            case 0xFE020: return "E026";
            case 0xFE021: return "E027";
            case 0xFE022: return "E028";
            case 0xFE023: return "E029";
            case 0xFE024: return "E02A";
            case 0xFE025: return "E02B";
            case 0xFE026: return "E02C";
            case 0xFE027: return "E02D";
            case 0xFE028: return "E02E";
            case 0xFE029: return "E02F";
            case 0xFE4C3: return "E03B";
            case 0xFE4C4: return "E509";
            case 0xFE4C6: return "E51D";
            case 0xFE328: return "E404"; // androidPUA should be 0xFE333
            case 0xFE334: return "E412";
            case 0xFE330: return "E057";
            case 0xFE338: return "E415";
            case 0xFE347: return "E405";
            case 0xFE335: return "E056";
            case 0xFE33E: return "E40A";
            case 0xFE327: return "E106";
            case 0xFE343: return "E402";
            case 0xFE326: return "E40E";
            case 0xFE344: return "E108";
            case 0xFE340: return "E403";
            case 0xFE33F: return "E407";
            case 0xFE32C: return "E418";
            case 0xFE32D: return "E417";
            case 0xFE329: return "E105";
            case 0xFE32A: return "E409";
            case 0xFE323: return "E058";
            case 0xFE320: return "E059";
            case 0xFE33D: return "E416";
            case 0xFE339: return "E413";
            case 0xFE33C: return "E406";
            case 0xFE345: return "E401";
            case 0xFE33B: return "E40B";
            case 0xFE342: return "E408";
            case 0xFE33A: return "E411";
            case 0xFE325: return "E40F";
            case 0xFE341: return "E107";
            case 0xFE322: return "E410";
            case 0xFE32F: return "E40D";
            case 0xFE32E: return "E40C";
            case 0xFE351: return "E423";
            case 0xFE352: return "E424";
            case 0xFE353: return "E426";
            case 0xFE358: return "E427";
            case 0xFE35B: return "E41D";
            case 0xFE7ED: return "E10D";
            case 0xFE7DF: return "E01E";
            case 0xFE7E2: return "E435";
            case 0xFE7E3: return "E01F";
            case 0xFE7E0: return "E434";
            case 0xFE7EC: return "E039";
            case 0xFE7E6: return "E159";
            case 0xFE7E7: return "E150";
            case 0xFE7F3: return "E431";
            case 0xFE7F2: return "E430";
            case 0xFE7F4: return "E432";
            case 0xFE7EF: return "E15A";
            case 0xFE7E4: return "E01B";
            case 0xFE7E5: return "E42E";
            case 0xFE7F1: return "E42F";
            case 0xFE7E8: return "E202";
            case 0xFE7EE: return "E135";
            case 0xFE7F7: return "E14E";
            case 0xFE7F8: return "E137";
            case 0xFEB1E: return "E30E";
            case 0xFEB1F: return "E208";
            case 0xFE7EB: return "E136";
            case 0xFE7F0: return "E201";
            case 0xFEB33: return "E138";
            case 0xFEB34: return "E139";
            case 0xFE506: return "E151";
            case 0xFEB35: return "E13A";
            case 0xFE507: return "E140";
            case 0xFE508: return "E309";
            case 0xFE505: return "E13F";

// unicode6
            case 0x0023 : if (nextCodePoint == 0x20E3) return "E210"; else return null;
            case 0x0030 : if (nextCodePoint == 0x20E3) return "E225"; else return null;
            case 0x0031 : if (nextCodePoint == 0x20E3) return "E21C"; else return null;
            case 0x0032 : if (nextCodePoint == 0x20E3) return "E21D"; else return null;
            case 0x0033 : if (nextCodePoint == 0x20E3) return "E21E"; else return null;
            case 0x0034 : if (nextCodePoint == 0x20E3) return "E21F"; else return null;
            case 0x0035 : if (nextCodePoint == 0x20E3) return "E220"; else return null;
            case 0x0036 : if (nextCodePoint == 0x20E3) return "E221"; else return null;
            case 0x0037 : if (nextCodePoint == 0x20E3) return "E222"; else return null;
            case 0x0038 : if (nextCodePoint == 0x20E3) return "E223"; else return null;
            case 0x0039 : if (nextCodePoint == 0x20E3) return "E224"; else return null;
            case 0x00A9 : return "E24E";
            case 0x00AE : return "E24F";
            case 0x2122 : return "E537";
            case 0x2196 : return "E237";
            case 0x2197 : return "E236";
            case 0x2198 : return "E238";
            case 0x2199 : return "E239";
            case 0x23E9 : return "E23C";
            case 0x23EA : return "E23D";
            case 0x25B6 : return "E23A";
            case 0x25C0 : return "E23B";
            case 0x2600 : return "E04A";
            case 0x2601 : return "E049";
            case 0x260E : return "E009";
            case 0x2614 : return "E04B";
            case 0x2615 : return "E045";
            case 0x261D : return "E00F";
            case 0x263A : return "E414";
            case 0x2648 : return "E23F";
            case 0x2649 : return "E240";
            case 0x264A : return "E241";
            case 0x264B : return "E242";
            case 0x264C : return "E243";
            case 0x264D : return "E244";
            case 0x264E : return "E245";
            case 0x264F : return "E246";
            case 0x2650 : return "E247";
            case 0x2651 : return "E248";
            case 0x2652 : return "E249";
            case 0x2653 : return "E24A";
            case 0x2660 : return "E20E";
            case 0x2663 : return "E20F";
            case 0x2665 : return "E20C";
            case 0x2666 : return "E20D";
            case 0x2668 : return "E123";
            case 0x267F : return "E20A";
            case 0x26A0 : return "E252";
            case 0x26A1 : return "E13D";
            case 0x26BD : return "E018";
            case 0x26BE : return "E016";
            case 0x26C4 : return "E048";
            case 0x26CE : return "E24B";
            case 0x26EA : return "E037";
            case 0x26F2 : return "E121";
            case 0x26F3 : return "E014";
            case 0x26F5 : return "E01C";
            case 0x26FA : return "E122";
            case 0x26FD : return "E03A";
            case 0x2702 : return "E313";
            case 0x2708 : return "E01D";
            case 0x270A : return "E010";
            case 0x270B : return "E012";
            case 0x270C : return "E011";
            case 0x2728 : return "E32E";
            case 0x2733 : return "E206";
            case 0x2734 : return "E205";
            case 0x274C : return "E333";
            case 0x2753 : return "E020";
            case 0x2754 : return "E336";
            case 0x2755 : return "E337";
            case 0x2757 : return "E021";
            case 0x2764 : return "E022";
            case 0x27A1 : return "E234";
            case 0x2B05 : return "E235";
            case 0x2B06 : return "E232";
            case 0x2B07 : return "E233";
            case 0x2B50 : return "E32F";
            case 0x2B55 : return "E332";
            case 0x303D : return "E12C";
            case 0x3297 : return "E30D";
            case 0x3299 : return "E315";
            case 0x1F004: return "E12D";
            case 0x1F170: return "E532";
            case 0x1F171: return "E533";
            case 0x1F17E: return "E535";
            case 0x1F17F: return "E14F";
            case 0x1F18E: return "E534";
            case 0x1F192: return "E214";
            case 0x1F194: return "E229";
            case 0x1F195: return "E212";
            case 0x1F197: return "E24D";
            case 0x1F199: return "E213";
            case 0x1F19A: return "E12E";
            case 0x1F1E8: if (nextCodePoint == 0x1F1F3) return "E513"; else return null;
            case 0x1F1E9: if (nextCodePoint == 0x1F1EA) return "E50E"; else return null;
            case 0x1F1EA: if (nextCodePoint == 0x1F1F8) return "E511"; else return null;
            case 0x1F1EB: if (nextCodePoint == 0x1F1F7) return "E50D"; else return null;
            case 0x1F1EC: if (nextCodePoint == 0x1F1E7) return "E510"; else return null;
            case 0x1F1EE: if (nextCodePoint == 0x1F1F9) return "E50F"; else return null;
            case 0x1F1EF: if (nextCodePoint == 0x1F1F5) return "E50B"; else return null;
            case 0x1F1F0: if (nextCodePoint == 0x1F1F7) return "E514"; else return null;
            case 0x1F1F7: if (nextCodePoint == 0x1F1FA) return "E512"; else return null;
            case 0x1F1FA: if (nextCodePoint == 0x1F1F8) return "E50C"; else return null;
            case 0x1F201: return "E203";
            case 0x1F202: return "E228";
            case 0x1F21A: return "E216";
            case 0x1F22F: return "E22C";
            case 0x1F233: return "E22B";
            case 0x1F235: return "E22A";
            case 0x1F236: return "E215";
            case 0x1F237: return "E217";
            case 0x1F238: return "E218";
            case 0x1F239: return "E227";
            case 0x1F23A: return "E22D";
            case 0x1F250: return "E226";
            case 0x1F300: return "E443";
            case 0x1F302: return "E43C";
            case 0x1F303: return "E44B";
            case 0x1F304: return "E04D";
            case 0x1F305: return "E449";
            case 0x1F306: return "E146";
            case 0x1F307: return "E44A";
            case 0x1F308: return "E44C";
            case 0x1F30A: return "E43E";
            case 0x1F319: return "E04C";
            case 0x1F31F: return "E335";
            case 0x1F334: return "E307";
            case 0x1F335: return "E308";
            case 0x1F337: return "E304";
            case 0x1F338: return "E030";
            case 0x1F339: return "E032";
            case 0x1F33A: return "E303";
            case 0x1F33B: return "E305";
            case 0x1F33E: return "E444";
            case 0x1F340: return "E110";
            case 0x1F341: return "E118";
            case 0x1F342: return "E119";
            case 0x1F343: return "E447";
            case 0x1F345: return "E349";
            case 0x1F346: return "E34A";
            case 0x1F349: return "E348";
            case 0x1F34A: return "E346";
            case 0x1F34E: return "E345";
            case 0x1F353: return "E347";
            case 0x1F354: return "E120";
            case 0x1F358: return "E33D";
            case 0x1F359: return "E342";
            case 0x1F35A: return "E33E";
            case 0x1F35B: return "E341";
            case 0x1F35C: return "E340";
            case 0x1F35D: return "E33F";
            case 0x1F35E: return "E339";
            case 0x1F35F: return "E33B";
            case 0x1F361: return "E33C";
            case 0x1F362: return "E343";
            case 0x1F363: return "E344";
            case 0x1F366: return "E33A";
            case 0x1F367: return "E43F";
            case 0x1F370: return "E046";
            case 0x1F371: return "E34C";
            case 0x1F372: return "E34D";
            case 0x1F373: return "E147";
            case 0x1F374: return "E043";
            case 0x1F375: return "E338";
            case 0x1F376: return "E30B";
            case 0x1F378: return "E044";
            case 0x1F37A: return "E047";
            case 0x1F37B: return "E30C";
            case 0x1F380: return "E314";
            case 0x1F381: return "E112";
            case 0x1F382: return "E34B";
            case 0x1F383: return "E445";
            case 0x1F384: return "E033";
            case 0x1F385: return "E448";
            case 0x1F386: return "E117";
            case 0x1F387: return "E440";
            case 0x1F388: return "E310";
            case 0x1F389: return "E312";
            case 0x1F38C: return "E143";
            case 0x1F38D: return "E436";
            case 0x1F38E: return "E438";
            case 0x1F38F: return "E43B";
            case 0x1F390: return "E442";
            case 0x1F391: return "E446";
            case 0x1F392: return "E43A";
            case 0x1F393: return "E439";
            case 0x1F3A1: return "E124";
            case 0x1F3A2: return "E433";
            case 0x1F3A4: return "E03C";
            case 0x1F3A5: return "E03D";
            case 0x1F3A6: return "E507";
            case 0x1F3A7: return "E30A";
            case 0x1F3A8: return "E502";
            case 0x1F3A9: return "E503";
            case 0x1F3AB: return "E125";
            case 0x1F3AC: return "E324";
            case 0x1F3AF: return "E130";
            case 0x1F3B0: return "E133";
            case 0x1F3B1: return "E42C";
            case 0x1F3B5: return "E03E";
            case 0x1F3B6: return "E326";
            case 0x1F3B7: return "E040";
            case 0x1F3B8: return "E041";
            case 0x1F3BA: return "E042";
            case 0x1F3BE: return "E015";
            case 0x1F3BF: return "E013";
            case 0x1F3C0: return "E42A";
            case 0x1F3C1: return "E132";
            case 0x1F3C3: return "E115";
            case 0x1F3C4: return "E017";
            case 0x1F3C6: return "E131";
            case 0x1F3C8: return "E42B";
            case 0x1F3CA: return "E42D";
            case 0x1F3E0: return "E036";
            case 0x1F3E2: return "E038";
            case 0x1F3E3: return "E153";
            case 0x1F3E5: return "E155";
            case 0x1F3E6: return "E14D";
            case 0x1F3E7: return "E154";
            case 0x1F3E8: return "E158";
            case 0x1F3E9: return "E501";
            case 0x1F3EA: return "E156";
            case 0x1F3EB: return "E157";
            case 0x1F3EC: return "E504";
            case 0x1F3ED: return "E508";
            case 0x1F3EF: return "E505";
            case 0x1F3F0: return "E506";
            case 0x1F40D: return "E52D";
            case 0x1F40E: return "E134";
            case 0x1F411: return "E529";
            case 0x1F412: return "E528";
            case 0x1F414: return "E52E";
            case 0x1F417: return "E52F";
            case 0x1F418: return "E526";
            case 0x1F419: return "E10A";
            case 0x1F41A: return "E441";
            case 0x1F41B: return "E525";
            case 0x1F41F: return "E019";
            case 0x1F420: return "E522";
            case 0x1F424: return "E523";
            case 0x1F426: return "E521";
            case 0x1F427: return "E055";
            case 0x1F428: return "E527";
            case 0x1F42B: return "E530";
            case 0x1F42C: return "E520";
            case 0x1F42D: return "E053";
            case 0x1F42E: return "E52B";
            case 0x1F42F: return "E050";
            case 0x1F430: return "E52C";
            case 0x1F431: return "E04F";
            case 0x1F433: return "E054";
            case 0x1F434: return "E01A";
            case 0x1F435: return "E109";
            case 0x1F436: return "E052";
            case 0x1F437: return "E10B";
            case 0x1F438: return "E531";
            case 0x1F439: return "E524";
            case 0x1F43A: return "E52A";
            case 0x1F43B: return "E051";
            case 0x1F440: return "E419";
            case 0x1F442: return "E41B";
            case 0x1F443: return "E41A";
            case 0x1F444: return "E41C";
            case 0x1F446: return "E22E";
            case 0x1F447: return "E22F";
            case 0x1F448: return "E230";
            case 0x1F449: return "E231";
            case 0x1F44A: return "E00D";
            case 0x1F44B: return "E41E";
            case 0x1F44C: return "E420";
            case 0x1F44D: return "E00E";
            case 0x1F44E: return "E421";
            case 0x1F44F: return "E41F";
            case 0x1F450: return "E422";
            case 0x1F451: return "E10E";
            case 0x1F452: return "E318";
            case 0x1F454: return "E302";
            case 0x1F455: return "E006";
            case 0x1F457: return "E319";
            case 0x1F458: return "E321";
            case 0x1F459: return "E322";
            case 0x1F45C: return "E323";
            case 0x1F45F: return "E007";
            case 0x1F460: return "E13E";
            case 0x1F461: return "E31A";
            case 0x1F462: return "E31B";
            case 0x1F463: return "E536";
            case 0x1F466: return "E001";
            case 0x1F467: return "E002";
            case 0x1F468: return "E004";
            case 0x1F469: return "E005";
            case 0x1F46B: return "E428";
            case 0x1F46E: return "E152";
            case 0x1F46F: return "E429";
            case 0x1F471: return "E515";
            case 0x1F472: return "E516";
            case 0x1F473: return "E517";
            case 0x1F474: return "E518";
            case 0x1F475: return "E519";
            case 0x1F476: return "E51A";
            case 0x1F477: return "E51B";
            case 0x1F478: return "E51C";
            case 0x1F47B: return "E11B";
            case 0x1F47C: return "E04E";
            case 0x1F47D: return "E10C";
            case 0x1F47E: return "E12B";
            case 0x1F47F: return "E11A";
            case 0x1F480: return "E11C";
            case 0x1F481: return "E253";
            case 0x1F482: return "E51E";
            case 0x1F483: return "E51F";
            case 0x1F484: return "E31C";
            case 0x1F485: return "E31D";
            case 0x1F486: return "E31E";
            case 0x1F487: return "E31F";
            case 0x1F488: return "E320";
            case 0x1F489: return "E13B";
            case 0x1F48A: return "E30F";
            case 0x1F48B: return "E003";
            case 0x1F48D: return "E034";
            case 0x1F48E: return "E035";
            case 0x1F48F: return "E111";
            case 0x1F490: return "E306";
            case 0x1F491: return "E425";
            case 0x1F492: return "E43D";
            case 0x1F493: return "E327";
            case 0x1F494: return "E023";
            case 0x1F497: return "E328";
            case 0x1F498: return "E329";
            case 0x1F499: return "E32A";
            case 0x1F49A: return "E32B";
            case 0x1F49B: return "E32C";
            case 0x1F49C: return "E32D";
            case 0x1F49D: return "E437";
            case 0x1F49F: return "E204";
            case 0x1F4A1: return "E10F";
            case 0x1F4A2: return "E334";
            case 0x1F4A3: return "E311";
            case 0x1F4A4: return "E13C";
            case 0x1F4A6: return "E331";
            case 0x1F4A8: return "E330";
            case 0x1F4A9: return "E05A";
            case 0x1F4AA: return "E14C";
            case 0x1F4B0: return "E12F";
            case 0x1F4B1: return "E149";
            case 0x1F4B9: return "E14A";
            case 0x1F4BA: return "E11F";
            case 0x1F4BB: return "E00C";
            case 0x1F4BC: return "E11E";
            case 0x1F4BD: return "E316";
            case 0x1F4BF: return "E126";
            case 0x1F4C0: return "E127";
            case 0x1F4D6: return "E148";
            case 0x1F4DD: return "E301";
            case 0x1F4E0: return "E00B";
            case 0x1F4E1: return "E14B";
            case 0x1F4E2: return "E142";
            case 0x1F4E3: return "E317";
            case 0x1F4E9: return "E103";
            case 0x1F4EB: return "E101";
            case 0x1F4EE: return "E102";
            case 0x1F4F1: return "E00A";
            case 0x1F4F2: return "E104";
            case 0x1F4F3: return "E250";
            case 0x1F4F4: return "E251";
            case 0x1F4F6: return "E20B";
            case 0x1F4F7: return "E008";
            case 0x1F4FA: return "E12A";
            case 0x1F4FB: return "E128";
            case 0x1F4FC: return "E129";
            case 0x1F50A: return "E141";
            case 0x1F50D: return "E114";
            case 0x1F511: return "E03F";
            case 0x1F512: return "E144";
            case 0x1F513: return "E145";
            case 0x1F514: return "E325";
            case 0x1F51D: return "E24C";
            case 0x1F51E: return "E207";
            case 0x1F525: return "E11D";
            case 0x1F528: return "E116";
            case 0x1F52B: return "E113";
            case 0x1F52F: return "E23E";
            case 0x1F530: return "E209";
            case 0x1F531: return "E031";
            case 0x1F532: return "E21A";
            case 0x1F533: return "E21B";
            case 0x1F534: return "E219";
            case 0x1F550: return "E024";
            case 0x1F551: return "E025";
            case 0x1F552: return "E026";
            case 0x1F553: return "E027";
            case 0x1F554: return "E028";
            case 0x1F555: return "E029";
            case 0x1F556: return "E02A";
            case 0x1F557: return "E02B";
            case 0x1F558: return "E02C";
            case 0x1F559: return "E02D";
            case 0x1F55A: return "E02E";
            case 0x1F55B: return "E02F";
            case 0x1F5FB: return "E03B";
            case 0x1F5FC: return "E509";
            case 0x1F5FD: return "E51D";
            case 0x1F601: return "E404";
            case 0x1F602: return "E412";
            case 0x1F603: return "E057";
            case 0x1F604: return "E415";
            case 0x1F609: return "E405";
            case 0x1F60A: return "E056";
            case 0x1F60C: return "E40A";
            case 0x1F60D: return "E106";
            case 0x1F60F: return "E402";
            case 0x1F612: return "E40E";
            case 0x1F613: return "E108";
            case 0x1F614: return "E403";
            case 0x1F616: return "E407";
            case 0x1F618: return "E418";
            case 0x1F61A: return "E417";
            case 0x1F61C: return "E105";
            case 0x1F61D: return "E409";
            case 0x1F61E: return "E058";
            case 0x1F620: return "E059";
            case 0x1F621: return "E416";
            case 0x1F622: return "E413";
            case 0x1F623: return "E406";
            case 0x1F625: return "E401";
            case 0x1F628: return "E40B";
            case 0x1F62A: return "E408";
            case 0x1F62D: return "E411";
            case 0x1F630: return "E40F";
            case 0x1F631: return "E107";
            case 0x1F632: return "E410";
            case 0x1F633: return "E40D";
            case 0x1F637: return "E40C";
            case 0x1F645: return "E423";
            case 0x1F646: return "E424";
            case 0x1F647: return "E426";
            case 0x1F64C: return "E427";
            case 0x1F64F: return "E41D";
            case 0x1F680: return "E10D";
            case 0x1F683: return "E01E";
            case 0x1F684: return "E435";
            case 0x1F685: return "E01F";
            case 0x1F687: return "E434";
            case 0x1F689: return "E039";
            case 0x1F68C: return "E159";
            case 0x1F68F: return "E150";
            case 0x1F691: return "E431";
            case 0x1F692: return "E430";
            case 0x1F693: return "E432";
            case 0x1F695: return "E15A";
            case 0x1F697: return "E01B";
            case 0x1F699: return "E42E";
            case 0x1F69A: return "E42F";
            case 0x1F6A2: return "E202";
            case 0x1F6A4: return "E135";
            case 0x1F6A5: return "E14E";
            case 0x1F6A7: return "E137";
            case 0x1F6AC: return "E30E";
            case 0x1F6AD: return "E208";
            case 0x1F6B2: return "E136";
            case 0x1F6B6: return "E201";
            case 0x1F6B9: return "E138";
            case 0x1F6BA: return "E139";
            case 0x1F6BB: return "E151";
            case 0x1F6BC: return "E13A";
            case 0x1F6BD: return "E140";
            case 0x1F6BE: return "E309";
            case 0x1F6C0: return "E13F";
            default: return null;
        }
    }

    private static String getEmojiForCodePointForEmoticon(int codePoint) {
        // Derived from http://code.google.com/p/emoji4unicode/source/browse/trunk/data/emoji4unicode.xml
        // XXX: This doesn't cover all the characters.  More emoticons are wanted.
        switch (codePoint) {
            case 0xFE000:
                return "sun";
            case 0xFE001:
                return "cloud";
            case 0xFE002:
                return "rain";
            case 0xFE003:
                return "snow";
            case 0xFE004:
                return "thunder";
            case 0xFE005:
                return "typhoon";
            case 0xFE006:
                return "mist";
            case 0xFE007:
                return "sprinkle";
            case 0xFE008:
                return "night";
            case 0xFE009:
                return "sun";
            case 0xFE00A:
                return "sun";
            case 0xFE00C:
                return "sun";
            case 0xFE010:
                return "night";
            case 0xFE011:
                return "newmoon";
            case 0xFE012:
                return "moon1";
            case 0xFE013:
                return "moon2";
            case 0xFE014:
                return "moon3";
            case 0xFE015:
                return "fullmoon";
            case 0xFE016:
                return "moon2";
            case 0xFE018:
                return "soon";
            case 0xFE019:
                return "on";
            case 0xFE01A:
                return "end";
            case 0xFE01B:
                return "sandclock";
            case 0xFE01C:
                return "sandclock";
            case 0xFE01D:
                return "watch";
            case 0xFE01E:
                return "clock";
            case 0xFE01F:
                return "clock";
            case 0xFE020:
                return "clock";
            case 0xFE021:
                return "clock";
            case 0xFE022:
                return "clock";
            case 0xFE023:
                return "clock";
            case 0xFE024:
                return "clock";
            case 0xFE025:
                return "clock";
            case 0xFE026:
                return "clock";
            case 0xFE027:
                return "clock";
            case 0xFE028:
                return "clock";
            case 0xFE029:
                return "clock";
            case 0xFE02A:
                return "clock";
            case 0xFE02B:
                return "aries";
            case 0xFE02C:
                return "taurus";
            case 0xFE02D:
                return "gemini";
            case 0xFE02E:
                return "cancer";
            case 0xFE02F:
                return "leo";
            case 0xFE030:
                return "virgo";
            case 0xFE031:
                return "libra";
            case 0xFE032:
                return "scorpius";
            case 0xFE033:
                return "sagittarius";
            case 0xFE034:
                return "capricornus";
            case 0xFE035:
                return "aquarius";
            case 0xFE036:
                return "pisces";
            case 0xFE038:
                return "wave";
            case 0xFE03B:
                return "night";
            case 0xFE03C:
                return "clover";
            case 0xFE03D:
                return "tulip";
            case 0xFE03E:
                return "bud";
            case 0xFE03F:
                return "maple";
            case 0xFE040:
                return "cherryblossom";
            case 0xFE042:
                return "maple";
            case 0xFE04E:
                return "clover";
            case 0xFE04F:
                return "cherry";
            case 0xFE050:
                return "banana";
            case 0xFE051:
                return "apple";
            case 0xFE05B:
                return "apple";
            case 0xFE190:
                return "eye";
            case 0xFE191:
                return "ear";
            case 0xFE193:
                return "kissmark";
            case 0xFE194:
                return "bleah";
            case 0xFE195:
                return "rouge";
            case 0xFE198:
                return "hairsalon";
            case 0xFE19A:
                return "shadow";
            case 0xFE19B:
                return "happy01";
            case 0xFE19C:
                return "happy01";
            case 0xFE19D:
                return "happy01";
            case 0xFE19E:
                return "happy01";
            case 0xFE1B7:
                return "dog";
            case 0xFE1B8:
                return "cat";
            case 0xFE1B9:
                return "snail";
            case 0xFE1BA:
                return "chick";
            case 0xFE1BB:
                return "chick";
            case 0xFE1BC:
                return "penguin";
            case 0xFE1BD:
                return "fish";
            case 0xFE1BE:
                return "horse";
            case 0xFE1BF:
                return "pig";
            case 0xFE1C8:
                return "chick";
            case 0xFE1C9:
                return "fish";
            case 0xFE1CF:
                return "aries";
            case 0xFE1D0:
                return "dog";
            case 0xFE1D8:
                return "dog";
            case 0xFE1D9:
                return "fish";
            case 0xFE1DB:
                return "foot";
            case 0xFE1DD:
                return "chick";
            case 0xFE1E0:
                return "pig";
            case 0xFE1E3:
                return "cancer";
            case 0xFE320:
                return "angry";
            case 0xFE321:
                return "sad";
            case 0xFE322:
                return "wobbly";
            case 0xFE323:
                return "despair";
            case 0xFE324:
                return "wobbly";
            case 0xFE325:
                return "coldsweats02";
            case 0xFE326:
                return "gawk";
            case 0xFE327:
                return "lovely";
            case 0xFE328:
                return "smile";
            case 0xFE329:
                return "bleah";
            case 0xFE32A:
                return "bleah";
            case 0xFE32B:
                return "delicious";
            case 0xFE32C:
                return "lovely";
            case 0xFE32D:
                return "lovely";
            case 0xFE32F:
                return "happy02";
            case 0xFE330:
                return "happy01";
            case 0xFE331:
                return "coldsweats01";
            case 0xFE332:
                return "happy02";
            case 0xFE333:
                return "smile";
            case 0xFE334:
                return "happy02";
            case 0xFE335:
                return "delicious";
            case 0xFE336:
                return "happy01";
            case 0xFE337:
                return "happy01";
            case 0xFE338:
                return "coldsweats01";
            case 0xFE339:
                return "weep";
            case 0xFE33A:
                return "crying";
            case 0xFE33B:
                return "shock";
            case 0xFE33C:
                return "bearing";
            case 0xFE33D:
                return "pout";
            case 0xFE33E:
                return "confident";
            case 0xFE33F:
                return "sad";
            case 0xFE340:
                return "think";
            case 0xFE341:
                return "shock";
            case 0xFE342:
                return "sleepy";
            case 0xFE343:
                return "catface";
            case 0xFE344:
                return "coldsweats02";
            case 0xFE345:
                return "coldsweats02";
            case 0xFE346:
                return "bearing";
            case 0xFE347:
                return "wink";
            case 0xFE348:
                return "happy01";
            case 0xFE349:
                return "smile";
            case 0xFE34A:
                return "happy02";
            case 0xFE34B:
                return "lovely";
            case 0xFE34C:
                return "lovely";
            case 0xFE34D:
                return "weep";
            case 0xFE34E:
                return "pout";
            case 0xFE34F:
                return "smile";
            case 0xFE350:
                return "sad";
            case 0xFE351:
                return "ng";
            case 0xFE352:
                return "ok";
            case 0xFE357:
                return "paper";
            case 0xFE359:
                return "sad";
            case 0xFE35A:
                return "angry";
            case 0xFE4B0:
                return "house";
            case 0xFE4B1:
                return "house";
            case 0xFE4B2:
                return "building";
            case 0xFE4B3:
                return "postoffice";
            case 0xFE4B4:
                return "hospital";
            case 0xFE4B5:
                return "bank";
            case 0xFE4B6:
                return "atm";
            case 0xFE4B7:
                return "hotel";
            case 0xFE4B9:
                return "24hours";
            case 0xFE4BA:
                return "school";
            case 0xFE4C1:
                return "ship";
            case 0xFE4C2:
                return "bottle";
            case 0xFE4C3:
                return "fuji";
            case 0xFE4C9:
                return "wrench";
            case 0xFE4CC:
                return "shoe";
            case 0xFE4CD:
                return "shoe";
            case 0xFE4CE:
                return "eyeglass";
            case 0xFE4CF:
                return "t-shirt";
            case 0xFE4D0:
                return "denim";
            case 0xFE4D1:
                return "crown";
            case 0xFE4D2:
                return "crown";
            case 0xFE4D6:
                return "boutique";
            case 0xFE4D7:
                return "boutique";
            case 0xFE4DB:
                return "t-shirt";
            case 0xFE4DC:
                return "moneybag";
            case 0xFE4DD:
                return "dollar";
            case 0xFE4E0:
                return "dollar";
            case 0xFE4E2:
                return "yen";
            case 0xFE4E3:
                return "dollar";
            case 0xFE4EF:
                return "camera";
            case 0xFE4F0:
                return "bag";
            case 0xFE4F1:
                return "pouch";
            case 0xFE4F2:
                return "bell";
            case 0xFE4F3:
                return "door";
            case 0xFE4F9:
                return "movie";
            case 0xFE4FB:
                return "flair";
            case 0xFE4FD:
                return "sign05";
            case 0xFE4FF:
                return "book";
            case 0xFE500:
                return "book";
            case 0xFE501:
                return "book";
            case 0xFE502:
                return "book";
            case 0xFE503:
                return "book";
            case 0xFE505:
                return "spa";
            case 0xFE506:
                return "toilet";
            case 0xFE507:
                return "toilet";
            case 0xFE508:
                return "toilet";
            case 0xFE50F:
                return "ribbon";
            case 0xFE510:
                return "present";
            case 0xFE511:
                return "birthday";
            case 0xFE512:
                return "xmas";
            case 0xFE522:
                return "pocketbell";
            case 0xFE523:
                return "telephone";
            case 0xFE524:
                return "telephone";
            case 0xFE525:
                return "mobilephone";
            case 0xFE526:
                return "phoneto";
            case 0xFE527:
                return "memo";
            case 0xFE528:
                return "faxto";
            case 0xFE529:
                return "mail";
            case 0xFE52A:
                return "mailto";
            case 0xFE52B:
                return "mailto";
            case 0xFE52C:
                return "postoffice";
            case 0xFE52D:
                return "postoffice";
            case 0xFE52E:
                return "postoffice";
            case 0xFE535:
                return "present";
            case 0xFE536:
                return "pen";
            case 0xFE537:
                return "chair";
            case 0xFE538:
                return "pc";
            case 0xFE539:
                return "pencil";
            case 0xFE53A:
                return "clip";
            case 0xFE53B:
                return "bag";
            case 0xFE53E:
                return "hairsalon";
            case 0xFE540:
                return "memo";
            case 0xFE541:
                return "memo";
            case 0xFE545:
                return "book";
            case 0xFE546:
                return "book";
            case 0xFE547:
                return "book";
            case 0xFE548:
                return "memo";
            case 0xFE54D:
                return "book";
            case 0xFE54F:
                return "book";
            case 0xFE552:
                return "memo";
            case 0xFE553:
                return "foot";
            case 0xFE7D0:
                return "sports";
            case 0xFE7D1:
                return "baseball";
            case 0xFE7D2:
                return "golf";
            case 0xFE7D3:
                return "tennis";
            case 0xFE7D4:
                return "soccer";
            case 0xFE7D5:
                return "ski";
            case 0xFE7D6:
                return "basketball";
            case 0xFE7D7:
                return "motorsports";
            case 0xFE7D8:
                return "snowboard";
            case 0xFE7D9:
                return "run";
            case 0xFE7DA:
                return "snowboard";
            case 0xFE7DC:
                return "horse";
            case 0xFE7DF:
                return "train";
            case 0xFE7E0:
                return "subway";
            case 0xFE7E1:
                return "subway";
            case 0xFE7E2:
                return "bullettrain";
            case 0xFE7E3:
                return "bullettrain";
            case 0xFE7E4:
                return "car";
            case 0xFE7E5:
                return "rvcar";
            case 0xFE7E6:
                return "bus";
            case 0xFE7E8:
                return "ship";
            case 0xFE7E9:
                return "airplane";
            case 0xFE7EA:
                return "yacht";
            case 0xFE7EB:
                return "bicycle";
            case 0xFE7EE:
                return "yacht";
            case 0xFE7EF:
                return "car";
            case 0xFE7F0:
                return "run";
            case 0xFE7F5:
                return "gasstation";
            case 0xFE7F6:
                return "parking";
            case 0xFE7F7:
                return "signaler";
            case 0xFE7FA:
                return "spa";
            case 0xFE7FC:
                return "carouselpony";
            case 0xFE7FF:
                return "fish";
            case 0xFE800:
                return "karaoke";
            case 0xFE801:
                return "movie";
            case 0xFE802:
                return "movie";
            case 0xFE803:
                return "music";
            case 0xFE804:
                return "art";
            case 0xFE805:
                return "drama";
            case 0xFE806:
                return "event";
            case 0xFE807:
                return "ticket";
            case 0xFE808:
                return "slate";
            case 0xFE809:
                return "drama";
            case 0xFE80A:
                return "game";
            case 0xFE813:
                return "note";
            case 0xFE814:
                return "notes";
            case 0xFE81A:
                return "notes";
            case 0xFE81C:
                return "tv";
            case 0xFE81D:
                return "cd";
            case 0xFE81E:
                return "cd";
            case 0xFE823:
                return "kissmark";
            case 0xFE824:
                return "loveletter";
            case 0xFE825:
                return "ring";
            case 0xFE826:
                return "ring";
            case 0xFE827:
                return "kissmark";
            case 0xFE829:
                return "heart02";
            case 0xFE82B:
                return "freedial";
            case 0xFE82C:
                return "sharp";
            case 0xFE82D:
                return "mobaq";
            case 0xFE82E:
                return "one";
            case 0xFE82F:
                return "two";
            case 0xFE830:
                return "three";
            case 0xFE831:
                return "four";
            case 0xFE832:
                return "five";
            case 0xFE833:
                return "six";
            case 0xFE834:
                return "seven";
            case 0xFE835:
                return "eight";
            case 0xFE836:
                return "nine";
            case 0xFE837:
                return "zero";
            case 0xFE960:
                return "fastfood";
            case 0xFE961:
                return "riceball";
            case 0xFE962:
                return "cake";
            case 0xFE963:
                return "noodle";
            case 0xFE964:
                return "bread";
            case 0xFE96A:
                return "noodle";
            case 0xFE973:
                return "typhoon";
            case 0xFE980:
                return "restaurant";
            case 0xFE981:
                return "cafe";
            case 0xFE982:
                return "bar";
            case 0xFE983:
                return "beer";
            case 0xFE984:
                return "japanesetea";
            case 0xFE985:
                return "bottle";
            case 0xFE986:
                return "wine";
            case 0xFE987:
                return "beer";
            case 0xFE988:
                return "bar";
            case 0xFEAF0:
                return "upwardright";
            case 0xFEAF1:
                return "downwardright";
            case 0xFEAF2:
                return "upwardleft";
            case 0xFEAF3:
                return "downwardleft";
            case 0xFEAF4:
                return "up";
            case 0xFEAF5:
                return "down";
            case 0xFEAF6:
                return "leftright";
            case 0xFEAF7:
                return "updown";
            case 0xFEB04:
                return "sign01";
            case 0xFEB05:
                return "sign02";
            case 0xFEB06:
                return "sign03";
            case 0xFEB07:
                return "sign04";
            case 0xFEB08:
                return "sign05";
            case 0xFEB0B:
                return "sign01";
            case 0xFEB0C:
                return "heart01";
            case 0xFEB0D:
                return "heart02";
            case 0xFEB0E:
                return "heart03";
            case 0xFEB0F:
                return "heart04";
            case 0xFEB10:
                return "heart01";
            case 0xFEB11:
                return "heart02";
            case 0xFEB12:
                return "heart01";
            case 0xFEB13:
                return "heart01";
            case 0xFEB14:
                return "heart01";
            case 0xFEB15:
                return "heart01";
            case 0xFEB16:
                return "heart01";
            case 0xFEB17:
                return "heart01";
            case 0xFEB18:
                return "heart02";
            case 0xFEB19:
                return "cute";
            case 0xFEB1A:
                return "heart";
            case 0xFEB1B:
                return "spade";
            case 0xFEB1C:
                return "diamond";
            case 0xFEB1D:
                return "club";
            case 0xFEB1E:
                return "smoking";
            case 0xFEB1F:
                return "nosmoking";
            case 0xFEB20:
                return "wheelchair";
            case 0xFEB21:
                return "free";
            case 0xFEB22:
                return "flag";
            case 0xFEB23:
                return "danger";
            case 0xFEB26:
                return "ng";
            case 0xFEB27:
                return "ok";
            case 0xFEB28:
                return "ng";
            case 0xFEB29:
                return "copyright";
            case 0xFEB2A:
                return "tm";
            case 0xFEB2B:
                return "secret";
            case 0xFEB2C:
                return "recycle";
            case 0xFEB2D:
                return "r-mark";
            case 0xFEB2E:
                return "ban";
            case 0xFEB2F:
                return "empty";
            case 0xFEB30:
                return "pass";
            case 0xFEB31:
                return "full";
            case 0xFEB36:
                return "new";
            case 0xFEB44:
                return "fullmoon";
            case 0xFEB48:
                return "ban";
            case 0xFEB55:
                return "cute";
            case 0xFEB56:
                return "flair";
            case 0xFEB57:
                return "annoy";
            case 0xFEB58:
                return "bomb";
            case 0xFEB59:
                return "sleepy";
            case 0xFEB5A:
                return "impact";
            case 0xFEB5B:
                return "sweat01";
            case 0xFEB5C:
                return "sweat02";
            case 0xFEB5D:
                return "dash";
            case 0xFEB5F:
                return "sad";
            case 0xFEB60:
                return "shine";
            case 0xFEB61:
                return "cute";
            case 0xFEB62:
                return "cute";
            case 0xFEB63:
                return "newmoon";
            case 0xFEB64:
                return "newmoon";
            case 0xFEB65:
                return "newmoon";
            case 0xFEB66:
                return "newmoon";
            case 0xFEB67:
                return "newmoon";
            case 0xFEB77:
                return "shine";
            case 0xFEB81:
                return "id";
            case 0xFEB82:
                return "key";
            case 0xFEB83:
                return "enter";
            case 0xFEB84:
                return "clear";
            case 0xFEB85:
                return "search";
            case 0xFEB86:
                return "key";
            case 0xFEB87:
                return "key";
            case 0xFEB8A:
                return "key";
            case 0xFEB8D:
                return "search";
            case 0xFEB90:
                return "key";
            case 0xFEB91:
                return "recycle";
            case 0xFEB92:
                return "mail";
            case 0xFEB93:
                return "rock";
            case 0xFEB94:
                return "scissors";
            case 0xFEB95:
                return "paper";
            case 0xFEB96:
                return "punch";
            case 0xFEB97:
                return "good";
            case 0xFEB9D:
                return "paper";
            case 0xFEB9F:
                return "ok";
            case 0xFEBA0:
                return "down";
            case 0xFEBA1:
                return "paper";
            case 0xFEE10:
                return "info01";
            case 0xFEE11:
                return "info02";
            case 0xFEE12:
                return "by-d";
            case 0xFEE13:
                return "d-point";
            case 0xFEE14:
                return "appli01";
            case 0xFEE15:
                return "appli02";
            case 0xFEE1C:
                return "movie";
            default:
                return null;
        }
    }
}
