package mixac1.dangerrpg.util;

import net.minecraft.util.StatCollector;

public class Translator
{
    public static String trans(String s)
    {
        return StatCollector.translateToLocal(s);
    }
}
