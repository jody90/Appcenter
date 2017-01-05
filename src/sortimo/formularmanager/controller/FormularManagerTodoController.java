package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.global.ConfigMaps;
import sortimo.formularmanager.storage.FormsStatisticsStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

@WebServlet("/FormularManagerTodoController")
public class FormularManagerTodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FormularManagerTodoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HelperFunctions helper = new HelperFunctions();
		
		if (helper.checkCookie(request)) {
			HttpSession session = request.getSession();
			User userData = (User) session.getAttribute("userData");
			User user = new User();
			if (userData == null) {
				try {
					user.getUserAccount(helper.getUsername());
					session.setAttribute("userData", user);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				user = (User) session.getAttribute("userData");
			}
			
			String action = request.getParameter("action") != null ? request.getParameter("action") : "false";
			String formId = request.getParameter("form_id") != null ? request.getParameter("form_id") : "false";
			String responseId = request.getParameter("response_id") != null ? request.getParameter("response_id") : "false";
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";
			
			Todo todo = new Todo();
			Gson gson = new Gson();
			ConfigMaps config = new ConfigMaps();
			FormStatistics stats = new FormStatistics();
			
			String statesJson = gson.toJson(config.getStates());
			String stateIconsJson = gson.toJson(config.getStateIcons());
			
			switch (action) {
			case "getForms" : 
				Map<Integer, FormsStatisticsStorage> forms = new HashMap<>();
				
				try {
					forms = todo.getForms(user.getRoles());
				} catch (Exception e) {
					System.err.println("Formular für den aktuellen Boss können nicht abgefragt werden");
					e.printStackTrace();
				}
				
				String todoFormsJson = gson.toJson(forms);
				
				Map<String, String> todoData = new HashMap<String, String>();
				todoData.put("todoForms", todoFormsJson);
				todoData.put("states", statesJson);
				todoData.put("stateIcons", stateIconsJson);
				
				String json = gson.toJson(todoData);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				return;
			}
			
			
			request.setAttribute("user", user);
			request.setAttribute("pageTitle", "TODO");
			request.setAttribute("path", "formularmanager");
			request.setAttribute("view", "todo");
			
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
