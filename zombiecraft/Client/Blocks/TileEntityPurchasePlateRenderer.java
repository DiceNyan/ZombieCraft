package zombiecraft.Client.Blocks;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import zombiecraft.Core.Buyables;
import zombiecraft.Core.Blocks.TileEntityPurchasePlate;
import zombiecraft.Core.GameLogic.ZCGame;
import zombiecraft.Forge.ZCClientTicks;

public class TileEntityPurchasePlateRenderer extends TileEntitySpecialRenderer
{
    /**
     * Hash map of the entities that the mob spawner has rendered/rendering spinning inside of them
     */
    private Map entityHashMap = new HashMap();
    
    public long lifeTick = 0;
    public long lastWorldTick = 0;
    
    
    public TileEntityPurchasePlateRenderer() {
    	
    	lifeTick = 0;//rand.nextInt(20);
    }

    public void renderTileEntityMobSpawner(TileEntityPurchasePlate par1TileEntityMobSpawner, double par2, double par4, double par6, float par8)
    {
    	
    	if (ZCGame.instance() != null && ZCGame.instance().mapMan != null && ZCGame.instance().mapMan.editMode && ZCGame.instance().mapMan.infoOverlay) {
    		if (par1TileEntityMobSpawner.cycleItems) renderLivingLabel(("Cycling"), par2+0.5F, par4 - 0.5F, par6+0.5F, 0);
    	}
    	
    	if (lastWorldTick != ZCClientTicks.mc.theWorld.getWorldTime()) {
    		lastWorldTick = ZCClientTicks.mc.theWorld.getWorldTime();
    		lifeTick++;
    		/*if (lifeTick >= 20) {
    			lifeTick = 0;
    		}*/
    		par1TileEntityMobSpawner.angle += 1F;
    		if (par1TileEntityMobSpawner.angle >= 360F) par1TileEntityMobSpawner.angle -= 360F;
    		
    	}
    	
    	renderEntityItem2(par1TileEntityMobSpawner, par2, par4, par6, par8);
    	//GL11.glPushMatrix();
        //GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
        /*Entity var9 = (Entity)this.entityHashMap.get(par1TileEntityMobSpawner.getMobID());

        if (var9 == null)
        {
            var9 = EntityList.createEntityInWorld(par1TileEntityMobSpawner.getMobID(), (World)null);
            this.entityHashMap.put(par1TileEntityMobSpawner.getMobID(), var9);
        }

        if (var9 != null)
        {
            var9.setWorld(par1TileEntityMobSpawner.worldObj);
            float var10 = 0.4375F;
            GL11.glTranslatef(0.0F, 0.4F, 0.0F);
            GL11.glRotatef((float)(par1TileEntityMobSpawner.yaw2 + (par1TileEntityMobSpawner.yaw - par1TileEntityMobSpawner.yaw2) * (double)par8) * 10.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(var10, var10, var10);
            var9.setLocationAndAngles(par2, par4, par6, 0.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(var9, 0.0D, 0.0D, 0.0D, 0.0F, par8);
        }*/

        //GL11.glPopMatrix();
    }
    
    public void renderEntityItem2(TileEntityPurchasePlate par1TileEntityMobSpawner, double par2, double par4, double par6, float par8) {
    	
    	GL11.glPushMatrix();
    	
    	float var10 = 0.6666667F;
    	

    	float yOffset = MathHelper.sin(((float)lifeTick + par8) / 10.0F + par1TileEntityMobSpawner.startOffset) * 0.1F + 0.1F;
    	
    	GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + yOffset + 0.75F * var10, (float)par6 + 0.5F);
    	
    	float var12 = (((float)lifeTick + par8) / 20.0F + par1TileEntityMobSpawner.startOffset) * (180F / (float)Math.PI);
    	
    	float var11 = var12;//par1TileEntityMobSpawner.angle;//(float)(1F * 360) / 16.0F;
        GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
    	
        GL11.glScalef(var10, var10, var10);
        
    	RenderManager.instance.itemRenderer.renderItem(ZCClientTicks.mc.thePlayer, Buyables.getBuyItem(par1TileEntityMobSpawner.itemIndex), 0);
    	
    	GL11.glPopMatrix();
    }
    
    public void renderEntityItem() {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        
        float scale = 0.0078125F;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        ZCClientTicks.mc.renderEngine.bindTexture("/misc/mapbg.png");
        Tessellator tessellator = Tessellator.instance;
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        tessellator.startDrawingQuads();
        byte margin = 7;
        tessellator.addVertexWithUV((double)(0 - margin), (double)(128 + margin), 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)(128 + margin), (double)(128 + margin), 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)(128 + margin), (double)(0 - margin), 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV((double)(0 - margin), (double)(0 - margin), 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        //MapData data = Item.map.getMapData(entity.item, ModLoader.getMinecraftInstance().theWorld);
        //this.mapRenderer.renderMap(mod_ZombieCraft.mc.thePlayer, mod_ZombieCraft.mc.renderEngine, data);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityMobSpawner((TileEntityPurchasePlate)par1TileEntity, par2, par4, par6, par8);
    }
    

    
    protected void renderLivingLabel(String par2Str, double par3, double par5, double par7, int par9)
    {
        //float var10 = par1EntityLiving.getDistanceToEntity(this.renderManager.livingPlayer);

    	
    	
        //if (var10 <= (float)par9)
        //{
            FontRenderer var11 = RenderManager.instance.getFontRenderer();
            float var12 = 1.6F;
            float var13 = 0.016666668F * var12;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + 2.3F, (float)par7);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-var13, -var13, var13);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator var14 = Tessellator.instance;
            byte var15 = 0;

            if (par2Str.equals("deadmau5"))
            {
                var15 = -10;
            }

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            var14.startDrawingQuads();
            int var16 = var11.getStringWidth(par2Str) / 2;
            var14.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            var14.addVertex((double)(-var16 - 1), (double)(-1 + var15), 0.0D);
            var14.addVertex((double)(-var16 - 1), (double)(8 + var15), 0.0D);
            var14.addVertex((double)(var16 + 1), (double)(8 + var15), 0.0D);
            var14.addVertex((double)(var16 + 1), (double)(-1 + var15), 0.0D);
            var14.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            var11.drawString(par2Str, -var11.getStringWidth(par2Str) / 2, var15, 553648127);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            var11.drawString(par2Str, -var11.getStringWidth(par2Str) / 2, var15, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        //}
    }
}
