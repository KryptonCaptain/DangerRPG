package mixac1.dangerrpg.client.model;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.client.RPGRenderHelper.Color;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelBipedColor extends ModelBiped
{
    protected int color = 0xffffff;

    public ModelBipedColor()
    {
        super(0.0F);
    }

    public ModelBipedColor(float f)
    {
        super(f, 0.0F, 64, 32);
    }

    public ModelBipedColor(float f, float f1, int f2, int f3)
    {
        super(f, f1, f2, f3);
    }

    public void setColor(int color)
    {
        this.color = (int) Utils.alignment(color, 0, 0xffffff);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        if (color != 0xffffff) {
            GL11.glColor3f(Color.R.get(color), Color.G.get(color), Color.B.get(color));
            super.render(entity, f, f1, f2, f3, f4, f5);
            GL11.glColor3f(1f, 1f, 1f);
        }
        else {
            super.render(entity, f, f1, f2, f3, f4, f5);
        }
    }
}
