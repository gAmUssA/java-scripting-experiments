package javaone.scripting;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.IOException;
import java.io.StringWriter;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 2/5/14
 */
public class DustJSContext extends AbstractJSFrameworkContext {
    ScriptContext dustjsScriptContext;

    @Override
    public String processFile(String fileName) throws IOException, ScriptException {
        if (dustjsScriptContext == null) {
            createDustJSScriptContext();
        }
        final Bindings bindings = dustjsScriptContext.getBindings(ScriptContext.ENGINE_SCOPE);
        String template = getText("javascript/templates/" + fileName + ".dust");
        String model = getText("javascript/templates/" + fileName + ".json");

        bindings.put("myTemplate", template);
        bindings.put("myModel", model);
        bindings.put("templateName", "servlet" + System.currentTimeMillis());
        StringWriter output = new StringWriter();
        dustjsScriptContext.setWriter(output);
        engine.eval(
            "var compiled = dust.compile(myTemplate, templateName);" +
                "dust.loadSource(compiled);" +
                "dust.render(templateName,JSON.parse(myModel), function(err, out){print(out);})",
            dustjsScriptContext
        );

        return output.toString();
    }

    private void createDustJSScriptContext() throws ScriptException {
        dustjsScriptContext = new SimpleScriptContext();
        Bindings bindings = engine.createBindings();
        dustjsScriptContext.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        try {
            engine.eval(getText("javascript/lib/dust-full-2.0.0.js"), dustjsScriptContext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] processFiles(String files) throws IOException, ScriptException {
        throw new UnsupportedOperationException("method not implemented");
    }
}
