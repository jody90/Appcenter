package sortimo.formularmanager.controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.model.HelperFunctions;
import sortimo.model.User;

@WebServlet("/FormularManagerIndexController")
public class FormularManagerIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public FormularManagerIndexController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HelperFunctions helper = new HelperFunctions();
		
		if (helper.checkCookie(request)) {
			ServletContext application = getServletConfig().getServletContext();
			User userData = (User) application.getAttribute("userData");
			User user = new User();
			if (userData == null) {
				try {
					user.getUserAccount(helper.getUsername());
					application.setAttribute("userData", user);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				user = (User) application.getAttribute("userData");
			}
			
			request.setAttribute("firstname", user.getFirstname());
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
