package javaone.servlet;

import javaone.scripting.DustJSContext;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/templateServlet")
public class TemplateServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String template = request.getParameter("template");
            if (template == null) {
                template = "404";
            }

            DustJSContext dustjs = (DustJSContext) request.getServletContext().getAttribute("dustjs");
            String res = dustjs.processFile(template);
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html");
            writer.write(res);
            writer.flush();

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
