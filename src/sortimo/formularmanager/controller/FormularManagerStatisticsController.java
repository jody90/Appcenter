package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;

import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.global.ConfigMaps;
import sortimo.formularmanager.storage.FromsStatisticsStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FormularManagerStatisticsController")
public class FormularManagerStatisticsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FormularManagerStatisticsController() {
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
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";

			request.setAttribute("user", user);
			request.setAttribute("formId", formId);

			FormStatistics stats = new FormStatistics();
			
			switch(action) {
				case "getStatistics" :
					FromsStatisticsStorage statistics = new FromsStatisticsStorage();
					
					try {
						statistics = stats.getStatistics(formId, country);
					} catch (Exception e) {
						System.err.println("Statistik konnte nicht aus DB gelesen werden");
						e.printStackTrace();
					}
					
					ConfigMaps config = new ConfigMaps();
					
					Map<String, String> meta = new HashMap<String, String>();
					meta.put("country", country);
					meta.put("formId", formId);
					meta.put("evaluationType", statistics.getEvaluationType());
					
					Gson gson = new Gson();
					String statisticsValueJson = gson.toJson(statistics.getStatisticsValue());
					String userJson = gson.toJson(user);
					String statesJson = gson.toJson(config.getStates());
					String metaJson = gson.toJson(meta);
					
					Map<String, String> statisticsData = new HashMap<String, String>();
					statisticsData.put("resultsJson", statisticsValueJson);
					statisticsData.put("formJson", statistics.getJsonForm());
					statisticsData.put("formHtml", statistics.getHtmlForm());
					statisticsData.put("formTitle", statistics.getFormTitle());
					statisticsData.put("user", userJson);
					statisticsData.put("states", statesJson);
					statisticsData.put("meta", metaJson);
					
					String json = gson.toJson(statisticsData);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
					return;
				case "saveProcessed" :
					try {
						stats.updateProcessState(request);
					} catch (Exception e) {
						System.err.println("Processed Status konnte nicht geändert werden");
						e.printStackTrace();
					}

					
				break;
				default :
					request.setAttribute("pageTitle", "Statistiken");
					request.setAttribute("path", "formularmanager");
					request.setAttribute("view", "statistics");
					getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
				break;
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
