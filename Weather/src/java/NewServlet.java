/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.json.simple.parser.JSONParser.*;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
/**
 *
 * @author Bhushan
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            // Recovering the location string entered by the user in the html form.
            String location = request.getParameter("user");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Weather Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
           
            java.lang.String str=(java.lang.String)NewClass.getWeatherInfo(location);
            JSONParser jp = new JSONParser();
            
            // I have inserted a backslash so that the quotes ( " ) are considered a part of JSON string.
            String str1 = str.replace('"', '\"');
            JSONObject j = (JSONObject)jp.parse(str1);
            
            // we need to parse the json string obtained from the openweathermap api as it contains many key value pairs.
            // so defining a JSONParser object to extract the value for key= "temp"
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(str1);
            String output = "";
            JSONObject ob = (JSONObject)json.get("main");
            Object val1 = ob.get("temp"); // The Object val1 now contains the value of the temperature at the specified location.
            // we can extract this temperature by using the toString() method.
            
            /* The following lines of code call the index.jsp file so that we can view the final result in a JSP page 
             * But before we call the jsp page, we need to pass the location and temperature parameters to it.
             */
            request.setAttribute("temp", val1.toString());
            request.setAttribute("location", location);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
           // out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            
            
        }
        catch(ParseException e)
        {
            out.println(" Exception caught!");
        }
        finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

