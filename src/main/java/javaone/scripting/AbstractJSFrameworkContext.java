package javaone.scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.URL;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 2/5/14
 */
public abstract class AbstractJSFrameworkContext implements JSFrameworkContext {
    ScriptEngine engine;
    ClassLoader cl;

    @Override
    public void init(ClassLoader classloader, String engineName) {
        this.cl = classloader;
        ScriptEngineManager factory = new ScriptEngineManager();
        this.engine = factory.getEngineByName(engineName);
        if (engine == null) {
            throw new RuntimeException(engineName + " engine not found");
        }
    }

    @Override
    public abstract String processFile(String fileName) throws IOException, ScriptException;

    void readAll(StringBuilder builder, BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
            line = reader.readLine();
        }
    }

    void stream2Builder(InputStream is, StringBuilder builder) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        readAll(builder, reader);
    }

    protected String textFromFile(String filename) throws IOException {
        URL url = cl.getResource(filename);
        String result = "";
        if (url != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(url.getFile()))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = reader.readLine();
                }
                result = sb.toString();
            }
        }
        return result;
    }
}
