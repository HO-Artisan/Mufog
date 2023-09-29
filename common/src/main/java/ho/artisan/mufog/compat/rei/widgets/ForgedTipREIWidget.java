package ho.artisan.mufog.compat.rei.widgets;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ForgedTipREIWidget extends WidgetWithBounds {
    private final Rectangle bounds;

    public ForgedTipREIWidget(Rectangle bounds) {
        this.bounds = new Rectangle(Objects.requireNonNull(bounds));
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    }
}
