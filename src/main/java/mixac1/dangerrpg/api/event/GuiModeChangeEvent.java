package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.init.RPGOther.GuiMode.GuiModeType;

public class GuiModeChangeEvent extends Event
{
    public GuiModeType type;

    public GuiModeChangeEvent(GuiModeType type)
    {
        this.type = type;
    }
}
