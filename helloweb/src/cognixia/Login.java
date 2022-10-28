package com.cognixia;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("uname");
		String password = request.getParameter("password");
		
		//response.getWriter().print("<html><body><h1>Username: "+ username+"</h1> <br> <h1>password: "+ password+"</h1></body></html>");
				//doGet(request, response);
				
				response.getWriter().print("<!DOCTYPE html>\r\n"
						+ "<html>\r\n"
						+ "<head>\r\n"
						+ "<meta charset=\"ISO-8859-1\">\r\n"
						+ "<title>Table</title>\r\n"
						+ "<style>\r\n"
						+ "table, th, td {\r\n"
						+ "  border:1px solid black;\r\n"
						+ "}\r\n"
						+ "</style>\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "<table>\r\n"
						+ "  <tr>\r\n"
						+ "    <th>Username</th>\r\n"
						+ "    <th>Password</th>\r\n"
						+ "  </tr>\r\n"
						+ "  <tr>\r\n"
						+ "    <td>"+username+"</td>\r\n"
						+ "    <td>"+password+"</td>\r\n"
						+ "  </tr>\r\n"
						+ "</table>\r\n"
						+ "</body>\r\n"
						+ "</html>");

		
	}

}
