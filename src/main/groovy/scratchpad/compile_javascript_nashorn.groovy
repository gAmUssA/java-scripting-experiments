package scratchpad

import jdk.nashorn.api.scripting.NashornScriptEngine
import jdk.nashorn.api.scripting.NashornScriptEngineFactory

import javax.script.CompiledScript
import javax.script.Invocable

/**
 * TODO
 *
 * @since 7/16/13
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 */
NashornScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine()

CompiledScript compiled = engine.compile("function hello(name){return 'hello '+name;}")
compiled.eval();

Invocable invocable = (Invocable) engine;
String result = (String) invocable.invokeFunction("hello", "Vik")
println result