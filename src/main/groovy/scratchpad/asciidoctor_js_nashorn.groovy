package scratchpad

import javax.script.*
/**
 * TODO
 *
 * @since 7/16/13
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 */

def asciidocDocument = """
= asciidoctor.js, AsciiDoc in JavaScript
Doc Writer <docwriter@example.com>

Asciidoctor and Opal come together to bring
http://asciidoc.org[AsciiDoc] to the browser (and not only in browser)!.

== Technologies

* AsciiDoc
* Asciidoctor
* Opal
* Nashorn

NOTE: That's all she wrote!!!
"""

static void readAll(StringBuilder builder, BufferedReader reader) throws IOException {
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        builder.append(line).append("\n");
    }
}

static StringBuilder stream2Builder(InputStream is, StringBuilder builder){
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    readAll(builder, reader);
}

ScriptEngineManager manager = new ScriptEngineManager()
ScriptEngine engine = manager.getEngineByName("nashorn")

ClassLoader cl = new GroovyClassLoader();
ScriptContext opalScriptContext = new SimpleScriptContext()
Bindings bindings1 = engine.createBindings()
opalScriptContext.setBindings(bindings1, ScriptContext.ENGINE_SCOPE);
InputStream is1 = cl.getResourceAsStream("javascript/lib/opal.js");
InputStream is2 = cl.getResourceAsStream("javascript/lib/asciidoctor.js");

if (is1 == null || is2 ==null) {
    throw new FileNotFoundException("Cannot find opal.js or asciidoctor.js");
}

StringBuilder builder = new StringBuilder();
stream2Builder(is1, builder);
stream2Builder(is2, builder)

engine.eval(builder.toString(), opalScriptContext);
opalScriptContext.getBindings(ScriptContext.ENGINE_SCOPE).put("asciidocDocument", asciidocDocument);

Object res = engine.eval('Opal.Asciidoctor.$render(asciidocDocument)', opalScriptContext);
print res?.toString()
