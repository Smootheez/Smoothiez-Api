package io.github.smootheez.smoothiezapi.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilsTest {
    @Test
    void testLerpRGB_tZero() {
        int from = 0x112233;
        int to   = 0xAABBCC;

        int result = ColorUtils.lerpRGB(from, to, 0.0f);

        assertEquals(from, result);
    }

    @Test
    void testLerpRGB_tOne() {
        int from = 0x112233;
        int to   = 0xAABBCC;

        int result = ColorUtils.lerpRGB(from, to, 1.0f);

        assertEquals(to, result);
    }

    @Test
    void testLerpRGB_tHalf() {
        int from = 0x000000;
        int to   = 0xFFFFFF;

        int result = ColorUtils.lerpRGB(from, to, 0.5f);

        assertEquals(0x7F7F7F, result);
    }

    @Test
    void testLerpRGB_tClamped() {
        int from = 0x112233;
        int to   = 0xAABBCC;

        int result1 = ColorUtils.lerpRGB(from, to, -1.0f);
        int result2 = ColorUtils.lerpRGB(from, to, 2.0f);

        assertEquals(from, result1);
        assertEquals(to, result2);
    }
}