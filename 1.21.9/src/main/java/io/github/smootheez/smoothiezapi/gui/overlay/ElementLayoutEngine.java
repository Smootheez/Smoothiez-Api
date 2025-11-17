package io.github.smootheez.smoothiezapi.gui.overlay;

import java.util.ArrayList;
import java.util.List;

public record ElementLayoutEngine(List<OverlayElement> elements, ElementAnchor anchor, ElementDirection direction,
                                  int spacing, int margin) {

    /**
     * Calculates coordinates for all elements based on the anchor point.
     */
    public List<PositionedElement> layout(int anchorX, int anchorY) {
        List<PositionedElement> result = new ArrayList<>();

        // Compute total layout size
        int totalWidth = 0;
        int totalHeight = 0;

        if (direction == ElementDirection.HORIZONTAL) {
            for (OverlayElement e : elements) {
                totalWidth += e.width();
            }
            totalWidth += (elements.size() - 1) * spacing;
            totalHeight = elements.stream().mapToInt(OverlayElement::height).max().orElse(0);
        } else {
            for (OverlayElement e : elements) {
                totalHeight += e.height();
            }
            totalHeight += (elements.size() - 1) * spacing;
            totalWidth = elements.stream().mapToInt(OverlayElement::width).max().orElse(0);
        }

        // Adjust anchor position based on START / MIDDLE / END
        if (direction == ElementDirection.HORIZONTAL) {
            switch (anchor) {
                case START -> anchorX += margin;
                case MIDDLE -> anchorX -= (totalWidth / 2);
                case END -> anchorX -= (totalWidth + margin);
            }
        } else {
            switch (anchor) {
                case START -> anchorY += margin;
                case MIDDLE -> anchorY -= (totalHeight / 2);
                case END -> anchorY -= (totalHeight + margin);
            }
        }

        // Place elements
        int currentX = anchorX;
        int currentY = anchorY;

        for (OverlayElement element : elements) {
            result.add(new PositionedElement(currentX, currentY, element));

            if (direction == ElementDirection.HORIZONTAL) {
                currentX += element.width() + spacing;
            } else {
                currentY += element.height() + spacing;
            }
        }

        return result;
    }
}


