package com.fsck.k9.mail.internet;


import com.fsck.k9.mail.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DecoderUtilTest {

    @Test
    public void testDecodeEncodedWords() {
        String body, expect;
        CharsetSupport charsetSupport = new CharsetSupport(null);

        body = "abc";
        expect = "abc";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?us-ascii?q?abc?=";
        expect = "abc";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?";
        expect = "=?";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=??";
        expect = "=??";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=???";
        expect = "=???";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=????";
        expect = "=????";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=????=";
        expect = "=????=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=??q??=";
        expect = "=??q??=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=??q?a?=";
        expect = "a";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=??=";
        expect = "=??=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x?=";
        expect = "=?x?=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x??=";
        expect = "=?x??=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x?q?=";
        expect = "=?x?q?=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x?q??=";
        expect = "=?x?q??=";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x?q?X?=";
        expect = "X";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        // invalid base64 string
        body = "=?us-ascii?b?abc?=";
        expect = "";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        // broken encoded header
        body = "=?us-ascii?q?abc?= =?";
        expect = "abc =?";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));

        body = "=?x?= =?";
        expect = "=?x?= =?";
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, charsetSupport));
    }
}
