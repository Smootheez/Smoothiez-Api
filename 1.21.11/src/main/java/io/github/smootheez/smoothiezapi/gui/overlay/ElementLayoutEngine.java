package io.github.smootheez.smoothiezapi.gui.overlay;

import net.minecraft.client.gui.*;

import java.util.*;

public record ElementLayoutEngine(List<OverlayElement> elements, ElementAnchor anchor, ElementDirection direction,
                                  int spacing, int margin) {

    /**
     * Calculates coordinates for all elements based on the anchor point.
     */
    public List<PositionedElement> layout(int anchorX, int anchorY) {
        var size = computeTotalSize();
        var adjusted = applyAnchorAdjustment(anchorX, anchorY, size.totalWidth(), size.totalHeight());
        return placeElements(adjusted.x(), adjusted.y());
    }

    public void renderLayout(GuiGraphics guiGraphics, List<PositionedElement> elements) {
        for (PositionedElement p : elements) p.element().renderer().render(guiGraphics, p.x(), p.y());
    }

    private record Size(int totalWidth, int totalHeight) {}
    private record Point(int x, int y) {}

    private Size computeTotalSize() {
        int totalWidth = 0;
        int totalHeight = 0;

        if (direction == ElementDirection.HORIZONTAL) {
            for (OverlayElement e : elements) totalWidth += e.width();
            totalWidth += (elements.size() - 1) * spacing;
            totalHeight = elements.stream().mapToInt(OverlayElement::height).max().orElse(0);
        } else {
            for (OverlayElement e : elements) totalHeight += e.height();
            totalHeight += (elements.size() - 1) * spacing;
            totalWidth = elements.stream().mapToInt(OverlayElement::width).max().orElse(0);
        }

        return new Size(totalWidth, totalHeight);
    }

    private Point applyAnchorAdjustment(int anchorX, int anchorY, int totalWidth, int totalHeight) {
        // Horizontal alignment (X)
        anchorX = switch (anchor) {
            case START -> anchorX + margin;
            case MIDDLE -> anchorX - (totalWidth / 2);
            case END -> anchorX - (totalWidth + margin);
        };

        // Vertical alignment (Y)
        anchorY = switch (anchor) {
            case START -> anchorY + margin;
            case MIDDLE -> anchorY - (totalHeight / 2);
            case END -> anchorY - (totalHeight + margin);
        };

        return new Point(anchorX, anchorY);
    }

    private List<PositionedElement> placeElements(int startX, int startY) {
        List<PositionedElement> result = new ArrayList<>();
        int currentX = startX;
        int currentY = startY;

        for (OverlayElement element : elements) {
            result.add(new PositionedElement(currentX, currentY, element));

            if (direction == ElementDirection.HORIZONTAL) currentX += element.width() + spacing;
            else currentY += element.height() + spacing;
        }

        return result;
    }
}


