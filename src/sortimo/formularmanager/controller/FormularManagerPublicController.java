package sortimo.formularmanager.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sortimo.formularmanager.databaseoperations.FormEdit;
import sortimo.formularmanager.databaseoperations.FormResponse;

@WebServlet("/FormularManagerPublicController")
public class FormularManagerPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FormularManagerPublicController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action") != null ? request.getParameter("action") : "default";
		String formId = request.getParameter("form_id") != null ? request.getParameter("form_id") : "false";		
		String country = request.getParameter("country") != null ? request.getParameter("country") : "DE";
		
		switch (action) {
			case "save" : 
				FormResponse formResponse = new FormResponse();
				boolean insertFormResponse = false;
				Map<String, String> responseData = new HashMap<String, String>();
				Map<String, String> globalData = new HashMap<String, String>();
				
				Enumeration<String> paramNames = request.getParameterNames();
				
				globalData.put("country", country);
				globalData.put("formId", formId);
				globalData.put("userId", "0");
				
				while (paramNames.hasMoreElements()) {			
					String paramName = (String) paramNames.nextElement();
					responseData.put(paramName, request.getParameter(paramName));		
				}
				
				try {
					insertFormResponse = formResponse.insertFormResponse(responseData, globalData);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
				if (insertFormResponse) {
					response.sendRedirect("/sortimo/formularmanager/thanks");
					return;
				}
				
			break;
			default :
				FormEdit form = new FormEdit();
				
				Map<String, String> formData = null;
				
				try {
					formData = form.getFormData(formId);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
				String validFromString = formData.get("validFrom");
				String validToString = formData.get("validTo");

				boolean formIsActive = false;
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			    Date startDate;
			    Date endDate;
			    Date currentDate = new Date();
			    try {
			    	startDate = dateFormat.parse(validFromString);
			    	endDate = dateFormat.parse(validToString);
			    	
			    	if (endDate.after(currentDate) && startDate.before(currentDate)) {
			    		formIsActive = true;
			    	}
			    } catch (ParseException e) {
			        e.printStackTrace();
			    }
				
				if (!formData.isEmpty() && formIsActive) {					
					request.setAttribute("country", country);
					request.setAttribute("formId", formId);
					request.setAttribute("formData", formData);
					request.setAttribute("pageTitle", formData.get("formType").substring(0,1).toUpperCase() + formData.get("formType").substring(1));
					request.setAttribute("path", "formularmanager");
					request.setAttribute("view", "public");
					
					getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);				
				}
				else {
					// Wenn formData leer ist Meldung ausgeben, dass Formular geloescht wurde 
					response.sendRedirect("/sortimo/error");
					return;
				}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
