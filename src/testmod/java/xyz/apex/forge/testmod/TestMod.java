package xyz.apex.forge.testmod;

import org.apache.commons.lang3.Validate;

import net.minecraftforge.fml.common.Mod;

import xyz.apex.forge.testmod.init.TRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

@Mod(TestMod.ID)
public final class TestMod
{
	public static final String ID = "testmod";
	@Nullable private static TestMod instance = null;

	public TestMod()
	{
		Validate.isTrue(instance == null);
		instance = this;

		TRegistry.bootstrap();
	}

	public static TestMod getInstance()
	{
		return Objects.requireNonNull(instance);
	}
}
