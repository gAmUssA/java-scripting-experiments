package scratchpad

import javax.script.ScriptEngineFactory
/**
 * One-liner to detect available script engines in JDK
 *
 * @since 7/16/13
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 */
for (ScriptEngineFactory factory : new javax.script.ScriptEngineManager().getEngineFactories()) {
    println " Engine name: ${factory.getEngineName()}, Language: ${factory.getLanguageName()}, Version: ${factory.getLanguageVersion()}, Names: ${factory.getNames()}"
}