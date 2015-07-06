package javaone.scripting;

import javax.script.*;

/**
 * Based SO question http://stackoverflow.com/questions/31236550/defining-a-default-global-java-object-to-nashorn-script-engine/
 *
 * @author Viktor Gamov on 7/6/15.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public class BindToGlobal {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        final SimpleScriptContext scriptContext = new SimpleScriptContext();

        // get JavaScript "global" object
        Object global = nashorn.eval("this");
        // get JS "Object" constructor object
        Object jsObject = nashorn.eval("Object");

        Invocable invocable = (Invocable) nashorn;

        invocable.invokeMethod(jsObject, "bindProperties", global, new BindToGlobal());
        nashorn.eval("someMethod('Hello')");
    }

    public void someMethod(String arg) {
        System.out.println(arg);
    }

}
