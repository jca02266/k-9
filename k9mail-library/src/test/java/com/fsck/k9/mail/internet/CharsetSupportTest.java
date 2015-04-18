package com.fsck.k9.mail.internet;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 21)
public class CharsetSupportTest {

    @Test
    public void testFixupCharset() throws Exception {
        String charsetOnMail;
        String expect;

        charsetOnMail = "CP932";
        expect = "shift_jis";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, null));

//        charsetOnMail = "koi8-u";
//        expect = "koi8-r";
//        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, null));

        MimeMessage message;
        String variant = "";

        message = new MimeMessage();
        message.setHeader("From", "aaa@docomo.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@dwmail.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@pdx.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@willcom.com");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@emnet.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@emobile.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-docomo-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@softbank.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-softbank-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@vodafone.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-softbank-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@disney.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-softbank-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@vertuclub.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-softbank-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@ezweb.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-kddi-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));

        message = new MimeMessage();
        message.setHeader("From", "aaa@ido.ne.jp");
        variant = JisSupport.getJisVariantFromMessage(message);
        charsetOnMail = "shift_jis";
        expect = "x-kddi-shift_jis-2007";
        assertEquals(expect, CharsetSupport.fixupCharset(charsetOnMail, variant));
    }
}
