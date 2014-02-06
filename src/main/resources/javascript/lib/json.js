var json = {};

//var MAPPER = new com.fasterxml.jackson.databind.ObjectMapper();
var MAPPER = new com.google.gson.Gson();

/**
 * Converts object to a json string.
 * @param object - the object to convert.
 * @return {String} the resultant json.
 */
json.toJson = function (object) {
    return MAPPER.toJson(object);
};

json.roundtripJson = function (object) {
    return JSON.parse(json.toJson(object));
};