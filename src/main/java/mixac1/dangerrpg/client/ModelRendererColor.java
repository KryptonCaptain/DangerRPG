package mixac1.dangerrpg.client;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.client.RPGRenderHelper.Color;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRendererColor extends ModelRenderer
{
    protected int color;

    public ModelRendererColor(ModelBase model, String str)
    {
        super(model, str);
    }

    public ModelRendererColor(ModelBase model)
    {
        super(model);
    }

    public ModelRendererColor(ModelBase model, int par1, int par2)
    {
        super(model, par1, par2);
    }

    public void setColor(int color)
    {
        this.color = (int) RPGCommonHelper.fixValue(color, 0, 0xffffff);
    }

    @Override
    public void render(float par)
    {
        GL11.glColor3f(Color.R.get(color), Color.G.get(color), Color.B.get(color));
        super.render(par);
        GL11.glColor3f(1f, 1f, 1f);
    }
}
