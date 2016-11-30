package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.formularmanager.databaseoperations.ListForms;
import sortimo.formularmanager.storage.FormsListStorage;
import sortimo.model.Login;

@WebServlet("/FormularManagerListController")
public class FormularManagerListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FormularManagerListController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Login user = new Login();

		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();
		
		// prueft ob User angemeldet ist
		String username = user.isLoggedIn(cookies);
		
		if (username != null) {
			
			Map<String, String> userInfo = user.getUserInfo();
			
			request.setAttribute("firstname", userInfo.get("firstname"));
			
			ListForms Forms = new ListForms();
			
			String filter = request.getParameter("filter") != null ? request.getParameter("filter") : "false";
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";
			
			try {
				ArrayList<FormsListStorage> formsList = Forms.getFormsList(country);		
				request.setAttribute("formsList", formsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String pageTitle = filter != "false" ? "Übersicht Aktiv" : "Übersicht";
			
			request.setAttribute("pageTitle", pageTitle);
			request.setAttribute("country", country);
			request.setAttribute("filter", filter);
			request.setAttribute("path", "formularmanager");
			request.setAttribute("view", "list");
			
			getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("/sortimo/login");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
