package mixac1.dangerrpg.hook;

import gloomyfolken.hooklib.asm.AsmHook;
import gloomyfolken.hooklib.asm.HookClassTransformer;

/**
 * Used for crash game if some hooks wansn't injecting. <br>
 * Using here {@link HookClassTransformer#transform(String, byte[])}
 */
public class HookException extends RuntimeException
{
    public AsmHook hook;

    public HookException(String msg)
    {
        super(msg);
    }

    public HookException(String msg, AsmHook hook)
    {
        super(msg.concat(": ").concat(hook.toString()));
        this.hook = hook;
    }
}
