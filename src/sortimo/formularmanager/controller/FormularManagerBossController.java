package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import sortimo.formularmanager.databaseoperations.Boss;
import sortimo.formularmanager.databaseoperations.FormEdit;
import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.global.ConfigMaps;
import sortimo.formularmanager.storage.FormsStatisticsStorage;
import sortimo.model.HelperFunctions;
import sortimo.model.User;

@WebServlet("/FormularManagerBossController")
public class FormularManagerBossController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FormularManagerBossController() {
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
			
			Boss boss = new Boss();
			Gson gson = new Gson();
			FormStatistics stats = new FormStatistics();
			
			switch (action) {
				case "getForms" : 
					Map<Integer, FormsStatisticsStorage> forms = new HashMap<>();
					
					try {
						forms = boss.getForms(user.getUsername());
					} catch (Exception e) {
						System.err.println("Formular für den aktuellen Boss können nicht abgefragt werden");
						e.printStackTrace();
					}
					
					String bossFormsJson = gson.toJson(forms);
					
					Map<String, String> bossData = new HashMap<String, String>();
					bossData.put("bossForms", bossFormsJson);
					
					String json = gson.toJson(bossData);
					
					System.out.println("JSON: " + json);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
					return;
				case "getStatistics" :
					FormsStatisticsStorage statistics = null;
					Map<String, String> formData = null;
					FormEdit form = new FormEdit();
					
					try {
						statistics = stats.getFormStatistics(responseId);
						formData = form.getFormData(formId);
					} catch (Exception e) {
						System.err.println("Statistik konnte nicht aus DB gelesen werden");
						e.printStackTrace();
					}
					
					ConfigMaps config = new ConfigMaps();
					
					Map<String, String> meta = new HashMap<String, String>();
					meta.put("country", country);
					meta.put("formId", formId);
					
					String formResponseStorageJson = gson.toJson(statistics);
					String formDataJson = gson.toJson(formData);
					String userJson = gson.toJson(user);
					String statesJson = gson.toJson(config.getStates());
					String stateIconsJson = gson.toJson(config.getStateIcons());
					
					Map<String, String> statisticsData = new HashMap<String, String>();
					statisticsData.put("respondedForms", formResponseStorageJson);
					statisticsData.put("formData", formDataJson);
					statisticsData.put("user", userJson);
					statisticsData.put("states", statesJson);
					statisticsData.put("stateIcons", stateIconsJson);
					
					String json2 = gson.toJson(statisticsData);

					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json2);
					return;
				case "saveProcessed" :
					try {
						stats.updateProcessState(request);
					} catch (Exception e) {
						System.err.println("Processed Status konnte nicht geändert werden");
						e.printStackTrace();
					}
				break;
			}
			
			request.setAttribute("user", user);
			request.setAttribute("pageTitle", "Boss View");
			request.setAttribute("path", "formularmanager");
			request.setAttribute("view", "boss");
			
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
