package javaone.scripting;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptException;

/**
 * https://wiki.openjdk.java.net/display/Nashorn/Nashorn+jsr223+engine+notes
 *
 * @see http://cr.openjdk.java.net/~sundar/jdk.nashorn.api/8u40/javadoc/jdk/nashorn/api/scripting/NashornScriptEngineFactory.html
 */
public class ShellScriptingFromJava {
    public static void main(String[] args) throws ScriptException {
        String[] options = new String[] {
            "-scripting"
        };
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        NashornScriptEngine engine = (NashornScriptEngine) factory.getScriptEngine(options);
        engine.eval("$EXEC('ls -al'); print($OUT);");
    }
}
