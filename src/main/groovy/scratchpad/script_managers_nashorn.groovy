package scratchpad

import javax.script.ScriptEngineManager
/**
 * TODO
 *
 * @since 7/16/13
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 */
new ScriptEngineManager()
        .getEngineByName("nashorn")
        .eval("""
             for each(var factory in new javax.script.ScriptEngineManager().getEngineFactories()) {
                 print("Engine name: " + factory.getEngineName() + ", Language: " + factory.getLanguageName() + ", Version: " + factory.getLanguageVersion() + "  Names: " + factory.getNames());
             }
             """)