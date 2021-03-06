package sortimo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sortimo.model.User;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		user.setRequest(request);
		user.setResponse(response);
		
		request.setAttribute("pageTitle", "Formular Manager");
		request.setAttribute("path", "");
		request.setAttribute("view", "login");
		
		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();

		String action = request.getParameter("action") != null ? request.getParameter("action") : "false";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		switch (action) {
			case "login" :
						
				if (!username.isEmpty() && !password.isEmpty()) {					
					if (user.login(password, username)) {
						response.sendRedirect("/sortimo/index");
						return;
					}
				}
				response.sendRedirect("/sortimo/login");
				return;
			case "logout" :
				
				HttpSession session = request.getSession();
				session.invalidate();

				boolean logoutResponse = user.logout(cookies);
				
				if (logoutResponse) {
					response.sendRedirect("/sortimo/login");
					return;
				}
				
			break;
			default : 
				getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
