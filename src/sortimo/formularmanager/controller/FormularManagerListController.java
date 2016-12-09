package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sortimo.formularmanager.databaseoperations.ListForms;
import sortimo.formularmanager.storage.FormsListStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

@WebServlet("/FormularManagerListController")
public class FormularManagerListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FormularManagerListController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HelperFunctions helper = new HelperFunctions();
		helper.setRequest(request);
		helper.setResponse(response);
		
		User user = new User();
		user.getUserAccount();
		
		if (!helper.checkCookie(request)) {
			response.sendRedirect("/sortimo/login");
			return;
		}
		
		if (!helper.isAuthorized("form_overview")) {
			response.sendRedirect("/sortimo/index");
			return;
		}

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
		
		request.setAttribute("user", user);
		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("country", country);
		request.setAttribute("filter", filter);
		request.setAttribute("path", "formularmanager");
		request.setAttribute("view", "list");
		
		getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
