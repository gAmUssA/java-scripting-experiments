package javaone.servlet;

import javaone.scripting.AsciidoctorJSContext;

import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

@WebServlet(urlPatterns = "/asciidoc")
public class AsciidocServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AsciidoctorJSContext asciidoctor = (AsciidoctorJSContext) request.getServletContext().getAttribute("asciidoctor");
        String file = request.getParameter("file");
        String doc = "";
        URL url =this.getClass().getClassLoader().getResource("asciidoc/" + file);
        BufferedReader br = null;
        if (url != null) {
            br = new BufferedReader(new FileReader(url.getFile()));
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            doc = sb.toString();
        } finally {
            br.close();
        }
        String res = "null";
        try {
            long startTime = System.currentTimeMillis();
            res = asciidoctor.processFile(doc);
            long endTime = System.currentTimeMillis();
            System.out.println("rendering time = " + (endTime - startTime));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        String header = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Asciidoctor in JavaScript powered by Opal</title>\n" +
                "    <link rel=\"stylesheet\" href=\"css/colony.css\">\n" +
                "  </head>\n" +
                "  <body class=\"book\"><div id=\"content\">";
        writer.print(header);
        writer.print(res);
        writer.print("</div></body>\n</html>");
        writer.flush();
    }
}
