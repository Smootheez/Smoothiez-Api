package io.github.smootheez.smoothiezapi.util;

import net.minecraft.util.*;

public final class ColorUtils {
    private ColorUtils() {}

    /**
     * Linearly interpolates between two RGB colors.
     *
     * @param from starting color (0xRRGGBB)
     * @param to   target color   (0xRRGGBB)
     * @param t    interpolation factor (0.0f–1.0f)
     * @return interpolated color as int (0xRRGGBB)
     */
    public static int lerpRGB(int from, int to, float t) {
        t = Mth.clamp(t, 0.0f, 1.0f);

        int r1 = (from >> 16) & 0xFF;
        int g1 = (from >> 8)  & 0xFF;
        int b1 =  from        & 0xFF;

        int r2 = (to >> 16) & 0xFF;
        int g2 = (to >> 8)  & 0xFF;
        int b2 =  to        & 0xFF;

        int r = (int) Mth.lerp(t, r1, r2);
        int g = (int) Mth.lerp(t, g1, g2);
        int b = (int) Mth.lerp(t, b1, b2);

        return (r << 16) | (g << 8) | b;
    }
}
