package javaone.rest;

import javax.script.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;

@Path("script")
public class ScriptResource {

    public File getScriptFile(String file) {
        URL url = this.getClass().getClassLoader().getResource("javascript/" + file);
        if (url != null) {
            return new File(url.getFile());
        }
        return null;
    }

    @GET
    @Path("{fileName}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getRandomValue(@PathParam("fileName") String fileName, @Context HttpServletRequest request) {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ScriptContext scriptContext = new SimpleScriptContext();
        StringWriter stringWriter = new StringWriter();
        scriptContext.setWriter(stringWriter);
        scriptContext.setAttribute("request", request, ScriptContext.ENGINE_SCOPE);
        scriptContext.setAttribute("factory", engine.getFactory(), ScriptContext.ENGINE_SCOPE);
        try {
            engine.eval(new FileReader(getScriptFile(fileName)), scriptContext);
            return Response.ok().entity(stringWriter.toString()).build();
        } // region catch
        catch (ScriptException | FileNotFoundException e) {
            e.printStackTrace();
        }
        // endregion
        return Response.ok().build();
    }
}
