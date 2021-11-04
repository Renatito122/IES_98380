package com.javacodegeeks.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
 
public class EmbeddedJettyExample {
 
    public static void main(String[] args) throws Exception {
         
        Server server = new Server(8680);       
         
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
                 
        servletHandler.addServletWithMapping(HelloServlet.class, "/hello");
         
        server.start();
        server.join();
 
    }
     
    public static class HelloServlet extends HttpServlet 
    {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
        	response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String username = request.getParameter("username");
            try {
                // Write some content
                out.println("<html>");
                out.println("<head>");
                out.println("<title>hello</title>");
                out.println("</head>");
                out.println("<body>");
                if(username != null){
                    out.println("<h2>Hello " + username +"!!!</h2>");
                }else{
                    out.println("<h2>Hello! if you want to see your name, specify it in the url as such:</h2>");
                    out.println("<h3>http://localhost:8680/hello?username=<NAME></h3>");
                }
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
               } 
        }
 }