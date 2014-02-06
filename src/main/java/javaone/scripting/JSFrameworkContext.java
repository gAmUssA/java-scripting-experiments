package javaone.scripting;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 2/5/14
 */
public interface JSFrameworkContext {
    void init(ClassLoader classloader, String engineName);

    String processFile(String file) throws IOException, ScriptException;

    String[] processFiles(String files) throws IOException, ScriptException;
}
