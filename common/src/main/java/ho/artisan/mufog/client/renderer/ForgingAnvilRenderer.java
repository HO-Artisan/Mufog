package ho.artisan.mufog.client.renderer;

import ho.artisan.mufog.common.blockentity.ForgingAnvilBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Quaternionf;

import java.util.Stack;

public class ForgingAnvilRenderer implements BlockEntityRenderer<ForgingAnvilBlockEntity> {

    public ForgingAnvilRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ForgingAnvilBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Stack<ItemStack> stack = entity.getItemStack();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        float y = 11.5f/16;

        if (!stack.isEmpty()) {
            for (int i = 0, stackSize = stack.size(); i < stackSize; i++) {
                boolean blockItem = itemRenderer.getModel(stack.elementAt(i), entity.getWorld(), null, (int) entity.getPos().asLong()).hasDepth();
                matrices.push();
                if (blockItem)
                    y += 7/24f;
                else
                    y += 1/16f;
                matrices.translate(0.5f, y, 0.5f);
                matrices.multiply(new Quaternionf().rotateX((float) Math.PI * 0.5f));
                itemRenderer.renderItem(stack.elementAt(i), ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, null, 0);
                matrices.pop();
                if (blockItem)
                    y += 5/24f;
            }
        }
    }
}
