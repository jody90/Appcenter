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

import sortimo.formularmanager.databaseoperations.FormEdit;
import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.global.ConfigMaps;
import sortimo.formularmanager.storage.FormsStatisticsStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

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
			String responseId = request.getParameter("response_id") != null ? request.getParameter("response_id") : "false";
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";

			request.setAttribute("user", user);
			request.setAttribute("formId", formId);
			
			FormStatistics stats = new FormStatistics();
			ConfigMaps config = new ConfigMaps();
			Gson gson = new Gson();
			
			Map<Integer, FormsStatisticsStorage> statistics = null;
			Map<String, String> statisticsData = new HashMap<String, String>();
			Map<String, String> formData = null;
			FormEdit form = new FormEdit();
			FormsStatisticsStorage formStatistics = null;

			String formResponseStorageJson = null;
			String formDataJson = null;
			
			Map<String, String> meta = new HashMap<>();
			meta.put("country", country);
			meta.put("formId", formId);
			
			String statesJson = gson.toJson(config.getStates());
			String stateIconsJson = gson.toJson(config.getStateIcons());
			
			switch(action) {
				case "getFormStatistics" :
				
					try {
						formStatistics = stats.getFormStatistics(responseId);
						formData = form.getFormData(formId);
					} catch (Exception e) {
						System.err.println("Statistik konnte nicht aus DB gelesen werden");
						e.printStackTrace();
					}

					formResponseStorageJson = gson.toJson(formStatistics);
					formDataJson = gson.toJson(formData);
					String userJson = gson.toJson(user);
					
					statisticsData.put("responseData", formResponseStorageJson);
					statisticsData.put("formData", formDataJson);
					statisticsData.put("user", userJson);
					statisticsData.put("states", statesJson);
					
					String json = gson.toJson(statisticsData);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
					return;
				case "getChartStatistics" :
					Map<Integer, FormsStatisticsStorage> getChartStatistics = null;

					try {
						getChartStatistics = stats.getChartStatistics(formId);
						formData = form.getFormData(formId);
					} catch (Exception e) {
						System.err.println("Statistik konnte nicht aus DB gelesen werden");
						e.printStackTrace();
					}

					formResponseStorageJson = gson.toJson(getChartStatistics);
					formDataJson = gson.toJson(formData);
					String userInfoJson = gson.toJson(user);
					
					statisticsData.put("responseData", formResponseStorageJson);
					statisticsData.put("formData", formDataJson);
					statisticsData.put("user", userInfoJson);
					statisticsData.put("states", statesJson);
					
					String chartJson = gson.toJson(statisticsData);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(chartJson);
					return;
				case "getRespondedForms" :
					
					try {
						statistics = stats.getRespondedForms(formId, country);
					} catch (Exception e) {
						System.err.println("Responded Forms konnten nicht aus DB gelesen werden");
						e.printStackTrace();
					}

					formResponseStorageJson = gson.toJson(statistics);
					
					statisticsData.put("respondedForms", formResponseStorageJson);
					statisticsData.put("states", statesJson);
					statisticsData.put("stateIcons", stateIconsJson);
					
					String respondedFormsjson = gson.toJson(statisticsData);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(respondedFormsjson);
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
