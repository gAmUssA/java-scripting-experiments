package javaone.scripting;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 2/5/14
 */
public class AsciidoctorJSContext extends AbstractJSFrameworkContext {
    ScriptContext asciidoctorScriptCompilerCtx;

    @Override
    public void init(ClassLoader classloader, String engineName) {
        this.cl = classloader;
        ScriptEngineManager factory = new ScriptEngineManager();
        this.engine = factory.getEngineByName(engineName);
        if (engine == null) {
            throw new RuntimeException(
                "Nashorn engine not found, probably you are not using Java 8 or later");
        }
    }

    private void createAsciidoctorScriptCompilerContext() throws IOException, ScriptException {
        asciidoctorScriptCompilerCtx = new SimpleScriptContext();
        Bindings bindings = engine.createBindings();
        asciidoctorScriptCompilerCtx.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        InputStream is1 = cl.getResourceAsStream("javascript/lib/opal.js");
        InputStream is2 = cl.getResourceAsStream("javascript/lib/asciidoctor.js");
        InputStream is3 = cl.getResourceAsStream("javascript/lib/asciidoctor_extensions.js");

        if (is1 == null || is2 == null || is3 == null) {
            throw new FileNotFoundException("Cannot find opal.js, asciidoctor.js or asciidoctor_extensions.js");
        }

        StringBuilder builder = new StringBuilder();
        stream2Builder(is1, builder);
        stream2Builder(is2, builder);
        stream2Builder(is3, builder);

        engine.eval(builder.toString(), asciidoctorScriptCompilerCtx);
    }

    @Override
    public synchronized String processFile(String asciidocFile)
        throws IOException, ScriptException {
        if (asciidoctorScriptCompilerCtx == null) {
            createAsciidoctorScriptCompilerContext();
        }
        try {
            asciidoctorScriptCompilerCtx.getBindings(ScriptContext.ENGINE_SCOPE)
                .put("asciidocDocument", asciidocFile);
            Object res = engine
                .eval("Opal.Asciidoctor.$render(asciidocDocument)", asciidoctorScriptCompilerCtx);
            return res == null ? null : res.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] processFiles(String files) throws IOException, ScriptException {
        throw new UnsupportedOperationException("method not implemented");
    }
}
