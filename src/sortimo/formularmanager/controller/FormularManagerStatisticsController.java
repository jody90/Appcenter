package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;

import sortimo.formularmanager.databaseoperations.FormStatistics;
import sortimo.formularmanager.storage.FromsStatisticsStorage;
import sortimo.model.Login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		
		Login user = new Login();

		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();
		
		// prueft ob User angemeldet ist
		String username = user.isLoggedIn(cookies);
		
		if (username != null) {
			
			Map<String, String> userInfo = user.getUserInfo();
			
			String action = request.getParameter("action") != null ? request.getParameter("action") : "false";
			String formId = request.getParameter("form_id") != null ? request.getParameter("form_id") : "false";		
			String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";

			request.setAttribute("firstname", userInfo.get("firstname"));
			request.setAttribute("formId", formId);

			System.out.println(action);

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
				
				Gson gson = new Gson();
				String json = gson.toJson(statisticsData);
				
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				return;
			}
			else {
//				request.setAttribute("statistics", statistics);
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
