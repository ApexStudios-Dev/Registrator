package xyz.apex.forge.testmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import xyz.apex.forge.testmod.container.DummyContainer;

@OnlyIn(Dist.CLIENT)
public class DummyContainerScreen extends ContainerScreen<DummyContainer>
{
	public DummyContainerScreen(DummyContainer container, PlayerInventory playerInventory, ITextComponent titleComponent)
	{
		super(container, playerInventory, titleComponent);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
	{

	}
}
