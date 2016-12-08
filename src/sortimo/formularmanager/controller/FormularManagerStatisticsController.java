package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;

import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.storage.FromsStatisticsStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FormularManagerStatisticsController")
public class FormularManagerStatisticsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FormularManagerStatisticsController() {
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
			
			String action = request.getParameter("action") != null ? request.getParameter("action") : "false";
			String formId = request.getParameter("form_id") != null ? request.getParameter("form_id") : "false";		
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";

			request.setAttribute("user", user);
			request.setAttribute("formId", formId);

			if (action.equals("getStatistics")) {
				FormStatistics stats = new FormStatistics();
				FromsStatisticsStorage statistics = new FromsStatisticsStorage();
				
				try {
					statistics = stats.getStatistics(formId, country);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Gson gson1 = new Gson(); 
				String statisticsValueJson = gson1.toJson(statistics.getStatisticsValue());
				
				Map<String, String> statisticsData = new HashMap<String, String>();
				statisticsData.put("resultsJson", statisticsValueJson);
				statisticsData.put("formJson", statistics.getJsonForm());
				statisticsData.put("formHtml", statistics.getHtmlForm());
				statisticsData.put("formTitle", statistics.getFormTitle());
				
				Gson gson = new Gson();
				String json = gson.toJson(statisticsData);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				return;
			}
			else {
				request.setAttribute("pageTitle", "Statistiken");
				request.setAttribute("path", "formularmanager");
				request.setAttribute("view", "statistics");
				getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
			}
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
