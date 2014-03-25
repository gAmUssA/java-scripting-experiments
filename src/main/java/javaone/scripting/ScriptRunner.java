package javaone.scripting;

import javax.script.*;

public class ScriptRunner {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        String scriptName = args[0];
        Bindings bindings = nashorn.createBindings();
        bindings.put("scriptFileName", scriptName);
        nashorn.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        nashorn.eval("load('src/main/resources/javascript/' + scriptFileName)");
    }
}
