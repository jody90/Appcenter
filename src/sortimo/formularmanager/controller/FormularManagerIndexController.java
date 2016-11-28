package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.model.User;

@WebServlet("/FormularManagerIndexController")
public class FormularManagerIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public FormularManagerIndexController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("Fromularmanager Index Controller");
		
		User user = new User();

		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();
		
		// prueft ob User angemeldet ist
		String username = user.isLoggedIn(cookies);
		
		if (username != null) {
			
			Map<String, String> userInfo = user.getUserInfo();
			
			request.setAttribute("firstname", userInfo.get("firstname"));
			request.setAttribute("pageTitle", "Formular Manager");
			request.setAttribute("path", "formularmanager");
			request.setAttribute("view", "index");
			
			getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/sortimo/login");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
