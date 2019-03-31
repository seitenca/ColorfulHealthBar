package locusway.colorfulhealthbar.overlay;

import locusway.colorfulhealthbar.ColorfulHealthBar;
import locusway.colorfulhealthbar.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/*
    Class handles the drawing of the health bar
 */

public class HealthBarRenderer {
	private Minecraft mc;

	private int updateCounter = 0;
	private int playerHealth = 0;
	private int lastPlayerHealth = 0;
	private long healthUpdateCounter = 0;
	private long lastSystemTime = 0;
	private Random rand = new Random();
	private Icon[] healthIcons;
	private Icon[] absorbIcons;

	private static final ResourceLocation ICON_HEARTS = new ResourceLocation(ColorfulHealthBar.MODID, "textures/gui/health.png");
	private static final ResourceLocation ICON_ABSORPTION = new ResourceLocation(ColorfulHealthBar.MODID, "textures/gui/absorption.png");
	private static final ResourceLocation ICON_VANILLA = Gui.ICONS;

	private static final float PASS_ONE_ALPHA = 1.0F;
	private static final float PASS_TWO_ALPHA = 0.2647F;// 0.2645 - 0.2649 needs tweaking too much red, too little green/blue
	private static final float PASS_THREE_ALPHA = 0.769F;//exact
	private static final float PASS_FOUR_ALPHA = 0.63F;//< 0.66
	private static final float POTION_ALPHA = 0.85F;
	private static final float PASS_SIX_ALPHA = 0.20F;//< 0.66




	private boolean forceUpdateIcons = false;

	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		Minecraft.getInstance().ingameGUI.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}

	public HealthBarRenderer(Minecraft mc) {
		this.mc = mc;
	}

	public void forceUpdate() {
		forceUpdateIcons = true;
	}

	public void renderHealthBar(int screenWidth, int screenHeight) {
		//Push to avoid lasting changes
		GL11.glPushMatrix();
		GlStateManager.enableBlend();

		updateCounter = mc.ingameGUI.getTicks();//get update counter

		EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();
		int health = MathHelper.ceil(entityplayer.getHealth());
		boolean highlight = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

		if (health < playerHealth && entityplayer.hurtResistantTime > 0) {
			lastSystemTime = System.currentTimeMillis();
			healthUpdateCounter = (long) (updateCounter + 20);
		} else if (health > playerHealth && entityplayer.hurtResistantTime > 0) {
			lastSystemTime = System.currentTimeMillis();
			healthUpdateCounter = (long) (updateCounter + 10);
		}

		if (System.currentTimeMillis() - lastSystemTime > 1000L) {
			playerHealth = health;
			lastPlayerHealth = health;
			lastSystemTime = System.currentTimeMillis();
		}
		int absorb = MathHelper.ceil(entityplayer.getAbsorptionAmount());
		if (health != playerHealth || absorbIcons == null || healthIcons == null || forceUpdateIcons) {
			healthIcons = IconStateCalculator.calculateIcons(health, Configs.healthColorValues);
			absorbIcons = IconStateCalculator.calculateIcons(absorb, Configs.absorptionColorValues);
			forceUpdateIcons = false;
		}

		playerHealth = health;
		int j = lastPlayerHealth;
		rand.setSeed((long) (updateCounter * 312871));
		IAttributeInstance maxHealthAttribute = entityplayer.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
		int xStart = screenWidth / 2 - 91;
		int yStart = screenHeight - 39;
		float maxHealth = (float) maxHealthAttribute.getValue();
		int numberOfHealthBars = MathHelper.ceil((maxHealth + (float) absorb) / 20.0F);
		int i2 = Math.max(10 - (numberOfHealthBars - 2), 3);
		int regen = -1;

		if (entityplayer.isPotionActive(MobEffects.REGENERATION))
			regen = updateCounter % MathHelper.ceil(maxHealth + 5.0F);

		mc.profiler.startSection("health");

		for (int i = 9; i >= 0; --i) {
			healthIcons = IconStateCalculator.calculateIcons(health, Configs.healthColorValues
);
			Icon icon = healthIcons[i];
			IconColor firstHalfColor = icon.primaryIconColor;
			IconColor secondHalfColor = icon.secondaryIconColor;

			int k5 = 16;

			if (entityplayer.isPotionActive(MobEffects.POISON)) k5 += 36;
			else if (entityplayer.isPotionActive(MobEffects.WITHER)) k5 += 72;

			int i4 = (highlight) ? 1 : 0;

			int j4 = MathHelper.ceil((float) (i + 1) / 10.0F) - 1;
			int xPosition = xStart + i % 10 * 8;
			int yPosition = yStart - j4 * i2;

			if (health <= 4) yPosition += rand.nextInt(2);

			if (absorb <= 0 && i == regen) yPosition -= 2;

			int i5 = (entityplayer.world.getWorldInfo().isHardcore()) ? 5 : 0;

			//Heart background
			drawTexturedModalRect(xPosition, yPosition, 16 + i4 * 9, 9 * i5, 9, 9);

			if (highlight) {
				if (i * 2 + 1 < j) {
					//Draw full highlighted heart
					drawTexturedModalRect(xPosition, yPosition, k5 + 54, 9 * i5, 9, 9);
				}

				if (i * 2 + 1 == j) {
					//Draw half highlighted heart
					drawTexturedModalRect(xPosition, yPosition, k5 + 63, 9 * i5, 9, 9);
				}
			}

			//if (i * 2 + 1 < health)
			if (icon.iconType == Icon.Type.FULL) {
				//Draw full heart

				//Bind our custom texture
				mc.getTextureManager().bindTexture(ICON_HEARTS);

				//Draw tinted white heart
				GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
				drawTexturedModalRect(xPosition, yPosition, 0, 0, 9, 9);

				//Second pass dark highlights
				GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
				drawTexturedModalRect(xPosition, yPosition, 0, 9, 9, 9);

				if (i5==5){
					GlStateManager.color4f(1, 1, 1, PASS_FOUR_ALPHA);
					drawTexturedModalRect(xPosition, yPosition,0, 18, 9, 9);
				}
				else
				{GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_THREE_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 27, 0, 9, 9);}

				//Reset back to normal settings
				mc.getTextureManager().bindTexture(ICON_VANILLA);
				if (k5 != 16)potionEffects(xPosition,yPosition,k5,i, health);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			//if (i * 2 + 1 == health)
			if (icon.iconType == Icon.Type.HALF) {
				//Draw Half Heart

				if (health > 20) {
					//We have wrapped, Draw both parts of the heart seperately

					//Bind our custom texture
					mc.getTextureManager().bindTexture(ICON_HEARTS);

					//Draw first half of tinted white heart
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 9, 0, 9, 9);

					//Second pass dark highlights
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 9, 9, 9, 9);

					if (i5==5){
						GlStateManager.color4f(1, 1, 1, PASS_FOUR_ALPHA);
						drawTexturedModalRect(xPosition, yPosition,0, 18, 9, 9);
					}
					else
					{GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_THREE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition, 27, 0, 9, 9);}

					//Draw second half of tinted white heart
					GlStateManager.color4f(secondHalfColor.Red, secondHalfColor.Green, secondHalfColor.Blue, PASS_ONE_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 18, 0, 9, 9);

					//Second pass dark highlights
					GlStateManager.color4f(secondHalfColor.Red, secondHalfColor.Green, secondHalfColor.Blue, PASS_TWO_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 18, 9, 9, 9);

					if (i5==5){
						GlStateManager.color4f(1, 1, 1, PASS_FOUR_ALPHA);
						drawTexturedModalRect(xPosition, yPosition,0, 18, 9, 9);
					}
					else
					{GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_THREE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition, 27, 0, 9, 9);}
				} else {
					//Draw only first half of heart

					//Bind our custom texture
					mc.getTextureManager().bindTexture(ICON_HEARTS);

					//Draw tinted white heart
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 9, 0, 9, 9);

					//Second pass dark highlights
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
					drawTexturedModalRect(xPosition, yPosition, 9, 9, 9, 9);

					if (i5==5){
						GlStateManager.color4f(1, 1, 1, PASS_FOUR_ALPHA);
						drawTexturedModalRect(xPosition, yPosition,9, 18, 9, 9);
					}
					else
					{GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_THREE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition, 27, 0, 9, 9);}
				}

				//Reset back to normal settings
				mc.getTextureManager().bindTexture(ICON_VANILLA);
				if (k5 != 16)potionEffects(xPosition,yPosition,k5,i, health);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			}
		}
		if (absorb > 0) {
			for (int i = 9; i>=0; i--) {
				if ((absorb/2)<i)continue;
				int absorbCap = absorb % 20;
				int offset = 10;
				Icon icon2 = absorbIcons[i];
				absorbIcons = IconStateCalculator.calculateIcons(absorb, Configs.absorptionColorValues);
				IconColor firstHalfColor = icon2.primaryIconColor;
				IconColor secondHalfColor = icon2.secondaryIconColor;

				int k5 = 16;

				if (entityplayer.isPotionActive(MobEffects.POISON)) k5 += 36;
				else if (entityplayer.isPotionActive(MobEffects.WITHER)) k5 += 72;

				int i4 = (highlight) ? 1 : 0;

				int j4 = MathHelper.ceil((float) (i + 1) / 10.0F) - 1;
				int xPosition = xStart + i % 10 * 8;
				int yPosition = yStart - j4 * i2;

				//if (health <= 4) yPosition += .rand.nextInt(2);

				int i5 = (entityplayer.world.getWorldInfo().isHardcore()) ? 5 : 0;

				//Heart background
				//drawTexturedModalRect(xPosition, yPosition-offset, 16 + i4 * 9, 9 * i5, 9, 9);

				//if (i * 2 + 1 < absorb)
				if (i * 2 + 1 < absorb) {
					//Draw full heart

					//Bind our custom texture
					mc.getTextureManager().bindTexture(ICON_ABSORPTION);

					//Draw tinted white absorption heart
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
					drawTexturedModalRect(xPosition, yPosition-offset, 0, 0, 9, 9);

					//Second pass dark highlights
					GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
					drawTexturedModalRect(xPosition, yPosition-offset, 0, 9, 9, 9);

					//Third pass dot highlight
					GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_SIX_ALPHA);
					drawTexturedModalRect(xPosition, yPosition-offset, 27, 0, 9, 9);

					//Reset back to normal settings
					GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(ICON_VANILLA);
				}
				//if (i * 2 + 1 == absorb)
				if (i * 2 + 1 == absorbCap) {
					//Draw Half Heart
					if (absorb > 20) {
						//We have wrapped, Draw both parts of the heart separately
						//Bind our custom texture
						mc.getTextureManager().bindTexture(ICON_ABSORPTION);

						//Draw first half of tinted white heart
						GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition-offset, 9, 0, 9, 9);

						//Second pass dark highlights
						GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
						drawTexturedModalRect(xPosition, yPosition-offset, 9, 9, 9, 9);

						//Third pass dot highlight
						GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_SIX_ALPHA);
						drawTexturedModalRect(xPosition, yPosition-offset, 27, 0, 9, 9);

						//Draw second half of tinted white heart
						GlStateManager.color4f(secondHalfColor.Red, secondHalfColor.Green, secondHalfColor.Blue, PASS_ONE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition-offset, 18, 0, 9, 9);

						//Second pass dark highlights
						GlStateManager.color4f(secondHalfColor.Red, secondHalfColor.Green, secondHalfColor.Blue, PASS_TWO_ALPHA);
						drawTexturedModalRect(xPosition, yPosition-offset, 18, 9, 9, 9);
					} else {
						//Draw only first half of heart

						//Bind our custom texture
						mc.getTextureManager().bindTexture(ICON_ABSORPTION);

						//Draw tinted white heart
						GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_ONE_ALPHA);
						drawTexturedModalRect(xPosition, yPosition - offset, 9, 0, 9, 9);

						//Second pass dark highlights
						GlStateManager.color4f(firstHalfColor.Red, firstHalfColor.Green, firstHalfColor.Blue, PASS_TWO_ALPHA);
						drawTexturedModalRect(xPosition, yPosition - offset, 9, 9, 9, 9);

						//third pass dot highlight
						GlStateManager.color4f(1.0F, 1.0F, 1.0F, PASS_SIX_ALPHA);
						drawTexturedModalRect(xPosition, yPosition - offset, 27, 0, 9, 9);
					}

					//Reset back to normal settings
					GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(ICON_VANILLA);
				}
			}
		}
		GuiIngameForge.left_height += 10;
		if(absorb > 0) {
			GuiIngameForge.left_height += 10;
		}

			GlStateManager.disableBlend();

			//Revert our state back
			GL11.glPopMatrix();
			mc.profiler.endSection();
	}
	public void potionEffects(int x, int y, int k5, int i, int health){
		if (k5 == 52){
			if (i * 2 + 1 != health || health >=20){GlStateManager.color4f(1.0F, 1.0F, 1.0F, POTION_ALPHA);
			drawTexturedModalRect(x, y, 88, 0, 9, 9);}
			else {GlStateManager.color4f(1.0F, 1.0F, 1.0F, POTION_ALPHA);
				drawTexturedModalRect(x, y, 97, 0, 9, 9);}
		}
		if (k5 == 88){
			if (i * 2 + 1 != health || health >=20){GlStateManager.color4f(1.0F, 1.0F, 1.0F, POTION_ALPHA);
			drawTexturedModalRect(x, y, 124, 0, 9, 9);}
			else {GlStateManager.color4f(1.0F, 1.0F, 1.0F, POTION_ALPHA);
				drawTexturedModalRect(x, y, 133, 0, 9, 9);}
		}
	}
}