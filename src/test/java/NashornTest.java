/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 9/17/13
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.script.*;
import java.io.*;
import java.net.URL;
import java.util.Random;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 9/3/13
 */
public class NashornTest {

    ScriptEngine engine;

    @Before
    public void setupScriptEngine() {
        final ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("javascript");
    }

    @Test
    public void useCase_1() throws ScriptException {
        System.out.println("// use case 1 - Hello World");
        // evaluate JavaScript code from String
        engine.eval("print('Hello, World')");
        Assert.assertTrue(true);
    }

    @Test
    public void useCase_2() throws ScriptException, FileNotFoundException {
        System.out.println("// use case 2 - Hello World from file test.js");
        // evaluate JavaScript code from given file - specified by first argument
        URL url = this.getClass().getClassLoader().getResource("javascript/test.js");
        FileReader fileReader = null;
        if (url != null) {
            fileReader = new FileReader(new File(url.getFile()));
        }
        engine.eval(fileReader);
    }

    @Test
    public void useCase_3() throws ScriptException {
        System.out.println("Passing java object to JavaScript");
        URL resource = this.getClass().getClassLoader().getResource("javascript/test.js");
        File f = null;
        if (resource != null) {
            f = new File(resource.getFile());
        }
        // expose File object as variable to script
        engine.put("file", f);

        // evaluate a script string. The script accesses "file"
        // variable and calls method on it
        engine.eval("print(file.getAbsolutePath())");
    }

    @Test
    public void contexts() throws ScriptException, FileNotFoundException {
        System.out.println("Passing JavaObject via ScriptContext to the engine");
        URL resource = this.getClass().getClassLoader().getResource("javascript/environment.js");
        File f = null;
        if (resource != null) {
            f = new File(resource.getFile());
        }

        ScriptContext scriptContext = new SimpleScriptContext();
        RequestMock requestMock = new RequestMock();
        if (f != null) {
            requestMock.setRequestURI(f.getAbsolutePath());
        }
        scriptContext.setAttribute("factory", engine.getFactory(), ScriptContext.ENGINE_SCOPE);
        scriptContext.setAttribute("request", requestMock, ScriptContext.ENGINE_SCOPE);

        engine.eval(new FileReader(f), scriptContext);
    }

    @Test
    public void contextWithCompiled() throws ScriptException, IOException {
        System.out.println("Passing JavaObject via ScriptContext to CompiledScript");
        URL resource = this.getClass().getClassLoader().getResource("javascript/environment.js");
        File f = null;
        if (resource != null) {
            f = new File(resource.getFile());
        }
        final Compilable compilable = (Compilable) engine;
        final CompiledScript compiledScript = compilable.compile(new FileReader(f));

        ScriptContext scriptContext = new SimpleScriptContext();
        RequestMock requestMock = new RequestMock();
        if (f != null) {
            requestMock.setRequestURI(f.getCanonicalPath());
        }
        scriptContext.setAttribute("factory", engine.getFactory(), ScriptContext.ENGINE_SCOPE);
        scriptContext.setAttribute("request", requestMock, ScriptContext.ENGINE_SCOPE);

        compiledScript.eval(scriptContext);
    }

    @Test
    public void templateWithDust() throws ScriptException, FileNotFoundException, NoSuchMethodException {

        URL resource = this.getClass().getClassLoader().getResource("javascript/lib/dust-full-2.0.0.js");
        File f = null;
        if (resource != null) {
            f = new File(resource.getFile());
        }
        engine.eval(new FileReader(f));
        Invocable inv = (Invocable) engine;
        Object dust = engine.get("dust");

        final String templateName = "intro";
        final String contextLiteral = "{name: 'Fred'}";
        final String templateBody = "Hello {name}!";

        Object compiledTemplate = inv.invokeMethod(dust, "compile", templateBody, templateName);

        inv.invokeMethod(dust, "loadSource", compiledTemplate);

        //wrapper to get proper context object
        engine.eval("var context = " + contextLiteral + ";");
        String templateCallback = "function(err, out){print(out);}";
        inv.invokeMethod(dust, "render", templateName, engine.get("context"), engine.eval(templateCallback));
    }

    @Test
    public void useCase_4() throws ScriptException, NoSuchMethodException {
        // JavaScript code in a String
        String script = "function hello(name) { print('Hello, ' + name); }";
        // evaluate script
        engine.eval(script);

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;

        // invoke the global function named "hello"
        inv.invokeFunction("hello", "Scripting!!");
    }

    @Test
    public void useCase_5() throws ScriptException, NoSuchMethodException {
        // JavaScript code in a String. This code defines a script object 'obj'
        // with one method called 'hello'.
        String script = "var obj = new Object(); obj.hello = function(name) { print('Hello, ' + name); }";
        // evaluate script
        engine.eval(script);

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;

        // get script object on which we want to call the method
        Object obj = engine.get("obj");

        // invoke the method named "hello" on the script object "obj"
        inv.invokeMethod(obj, "hello", "Script Method !!");
    }

    @Test
    public void useCase_6() throws ScriptException, NoSuchMethodException {
        // JavaScript code in a String
        String script = "function add(a, b) { return a + b }";
        // evaluate script
        engine.eval(script);

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;

        Assert.assertEquals("invoke the global function named add for numbers ", ((Integer) inv.invokeFunction("add", 2, 2)).intValue(), 4);
        Assert.assertEquals("invoke the global function named add for strings", inv.invokeFunction("add", "a", "b").toString(), "ab");
    }

    @Test
    public void useCase_7() throws ScriptException, NoSuchMethodException {
        engine.eval("x = {a: {b: {c: function(a, b) { return a + b } } } }");
        Invocable inv = (Invocable) engine;
        Assert.assertEquals("function reference chain", inv.invokeMethod(engine.eval("x.a.b"), "c", 2, 3), 5);
    }


    @Test
    public void useCase_8() throws ScriptException, NoSuchMethodException {
        engine.eval("x = {c: function(a, b) { return a + b } } ");
        Invocable inv = (Invocable) engine;
        Object x = engine.get("x");
        Assert.assertEquals("function reference chain", inv.invokeMethod(x, "c", 2, 3), 5);
    }

    @Test
    public void convertToJson() throws ScriptException, NoSuchMethodException, FileNotFoundException {
        Person person = new Person();
        person.setFirstName("Vik");
        person.setLastName("Gamov");
        person.setSsn("111-11-11");
        // evaluate JavaScript code from given file - specified by first argument
        URL url = this.getClass().getClassLoader().getResource("javascript/lib/json.js");
        FileReader fileReader = null;
        if (url != null) {
            fileReader = new FileReader(new File(url.getFile()));
        }
        engine.eval(fileReader);

        Invocable invocable = (Invocable) engine;

        //Object x = invocable.invokeMethod(engine.eval("JSON"), "stringify", person);
        System.out.println(invocable.invokeMethod(engine.eval("json"), "toJson", person));
        System.out.println(invocable.invokeMethod(engine.eval("json"), "roundtripJson", new Object[]{person}));
    }

    @Test
    public void concurrentRunsShouldNotEffectOneAnother() throws ScriptException, InterruptedException {
        engine.eval("function add(a, b) { return a + b }");
        final Invocable inv = (Invocable) engine;
        final Random random = new Random();
        TestUtils.runConcurrent(10, new Runnable() {
            @Override
            public void run() {
                double a = random.nextDouble();
                Integer b = random.nextInt(100);
                try {
                    Assert.assertEquals(a + b, inv.invokeFunction("add", a, b));
                } catch (ScriptException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class RequestMock {
        public String getRequestURI() {
            return requestURI;
        }

        public void setRequestURI(String requestURI) {
            this.requestURI = requestURI;
        }

        private String requestURI;
    }

    private class Person {
        private String firstName;
        private String ssn;
        private String lastName;

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setSsn(String ssn) {
            this.ssn = ssn;
        }

        public String getSsn() {
            return ssn;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }
    }
}
