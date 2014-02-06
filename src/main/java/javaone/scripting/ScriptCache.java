package javaone.scripting;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ScriptCache {
    // TODO
    public static final String ENGINE_NAME = "Nashorn";
    private Compilable scriptEngine;
    private LinkedHashMap<String, CachedScript> cacheMap;

    public ScriptCache(final int maxCachedScripts) {
        ScriptEngineManager manager = new ScriptEngineManager();
        scriptEngine = (Compilable) manager.getEngineByName(ENGINE_NAME);
        cacheMap = new LinkedHashMap<String, CachedScript>(
                maxCachedScripts, 1, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxCachedScripts;
            }
        };
    }

    public abstract File getScriptFile(String key);

    public synchronized CompiledScript getScript(String key)
            throws ScriptException, IOException {
        CachedScript script = cacheMap.get(key);
        if (script == null) {
            script = new CachedScript(scriptEngine, getScriptFile(key));
            cacheMap.put(key, script);
        }
        return script.getCompiledScript();
    }

    public ScriptEngine getEngine() {
        return (ScriptEngine) scriptEngine;
    }

    public void eval(String key, ScriptContext scriptContext) throws FileNotFoundException, ScriptException {
        getEngine().eval(new FileReader(getScriptFile(key)), scriptContext);
    }

    public void evalCompiled(String key, ScriptContext scriptContext) throws FileNotFoundException, ScriptException {
        Compilable compilable = (Compilable) getEngine();
        CompiledScript script = compilable.compile(new FileReader(getScriptFile(key)));
        script.eval(scriptContext);
    }


}

