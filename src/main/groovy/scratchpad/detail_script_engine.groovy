package scratchpad

import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager;

//System.properties.each { k, v -> println("$k = $v") }
println System.properties['java.runtime.version']
println "---------------------------------"
ScriptEngineManager manager = new ScriptEngineManager()
ScriptEngine nashorn = manager.getEngineByName("nashorn")
if (nashorn != null) {
    println "Nashorn is present."
    println "---------------------------------"
    printEngineInfo nashorn.getFactory()
} else {
    println "Nashorn is not present."
}
for (ScriptEngineFactory f : manager.getEngineFactories()) {
    printEngineInfo(f)
}

def printEngineInfo(ScriptEngineFactory factory) {
    println "engine name=" + factory.engineName
    println "engine version=" + factory.engineVersion
    println "language name=" + factory.languageName
    println "extensions=" + factory.extensions
    println "language version=" + factory.languageVersion
    println "names=" + factory.names
    println "mime types=" + factory.mimeTypes
    println "---------------------------------"
}