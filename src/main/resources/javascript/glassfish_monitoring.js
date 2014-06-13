#! /usr/local/bin/jjs -scripting

print("Total requests hit GlassFish: ${numberOfRollbacks()} requests ");

function numberOfRollbacks() {
    var command = "curl http://localhost:4848/monitoring/domain/server/server/web/request.json";
    $EXEC(command);
    var result = $OUT;
    var elements = JSON.parse(result);
    return elements.extraProperties.entity.requestcount.count;
}
