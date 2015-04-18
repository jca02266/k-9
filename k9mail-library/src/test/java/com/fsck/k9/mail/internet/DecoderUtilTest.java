package com.fsck.k9.mail.internet;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 21)
public class DecoderUtilTest {

    @Test
    public void testDecodeEncodedWords() {
        String body, expect;
        String variant;

        body = "abc";
        expect = "abc";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?us-ascii?q?abc?=";
        expect = "abc";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?";
        expect = "=?";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=??";
        expect = "=??";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=???";
        expect = "=???";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=????";
        expect = "=????";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=????=";
        expect = "=????=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=??q??=";
        expect = "=??q??=";
        ;
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=??q?a?=";
        expect = "a";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=??=";
        expect = "=??=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x?=";
        expect = "=?x?=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x??=";
        expect = "=?x??=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x?q?=";
        expect = "=?x?q?=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x?q??=";
        expect = "=?x?q??=";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x?q?X?=";
        expect = "X";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        // invalid base64 string
        body = "=?us-ascii?b?abc?=";
        expect = "";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        // broken encoded header
        body = "=?us-ascii?q?abc?= =?";
        expect = "abc =?";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));

        body = "=?x?= =?";
        expect = "=?x?= =?";
        variant = null;
        assertEquals(expect, DecoderUtil.decodeEncodedWords(body, variant));
    }
}
