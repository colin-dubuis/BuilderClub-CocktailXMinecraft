package com.hackathon.cocktailmod.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

/**
 * Draws the vertical alcohol meter HUD next to the hotbar.
 * - Safe zone (0-39g): Green
 * - Warning zone (40-59g): Yellow/Amber
 * - Critical zone (60-100g): Red (pulsing)
 */
public class AlcoholHudLayer implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) {
            return;
        }

        // Hide while in creative or spectator mode, or if completely sober
        if (mc.player.isCreative() || mc.player.isSpectator()) {
            return;
        }

        int level = ClientAlcoholData.getLevel();
        if (level <= 0) {
            return;
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int barWidth = 8;
        int barHeight = 44;
        // Positioned 10px to the right of the vanilla hotbar (hotbar width is 182, center is screenWidth / 2)
        int barX = (screenWidth / 2) + 91 + 10;
        int barY = screenHeight - 48;

        // Determine zone and color
        int fillColor;
        if (level < 40) {
            fillColor = 0xFF22C55E; // Green (Safe)
        } else if (level < 60) {
            fillColor = 0xFFF59E0B; // Amber / Yellow (Warning)
        } else {
            // Pulsing Red in critical zone
            boolean pulse = (System.currentTimeMillis() / 250) % 2 == 0;
            fillColor = pulse ? 0xFFEF4444 : 0xFF991B1B; // Red (Critical)
        }

        // 1. Dark outer border
        guiGraphics.fill(barX - 1, barY - 1, barX + barWidth + 1, barY + barHeight + 1, 0xFF18181B);

        // 2. Translucent dark glass background
        guiGraphics.fill(barX, barY, barX + barWidth, barY + barHeight, 0x8A000000);

        // 3. Fill bar from bottom to top
        int fillH = (int) Math.round((level / 100.0) * barHeight);
        fillH = Math.max(1, Math.min(barHeight, fillH));
        int fillTop = barY + barHeight - fillH;
        guiGraphics.fill(barX, fillTop, barX + barWidth, barY + barHeight, fillColor);

        // 4. Subtle threshold indicator lines:
        // Warning line at 40%
        int y40 = barY + barHeight - (int) Math.round(barHeight * 0.40);
        guiGraphics.fill(barX, y40, barX + barWidth, y40 + 1, 0x80F59E0B);

        // Critical line at 60%
        int y60 = barY + barHeight - (int) Math.round(barHeight * 0.60);
        guiGraphics.fill(barX, y60, barX + barWidth, y60 + 1, 0x80EF4444);

        // 5. Text reading on top (e.g. "45g")
        String text = level + "g";
        int textWidth = mc.font.width(text);
        int textX = barX + (barWidth - textWidth) / 2;
        int textY = barY - 10;
        guiGraphics.drawString(mc.font, text, textX, textY, fillColor, true);
    }
}
