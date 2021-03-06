package sortimo.formularmanager.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sortimo.formularmanager.databaseoperations.FormEdit;
import sortimo.model.HelperFunctions;
import sortimo.model.User;
import sortimo.settings.databaseoperations.ManageRolesDb;
import sortimo.storage.RolesStorage;

@WebServlet("/FormularManagerEditController")
public class FormularManagerEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FormularManagerEditController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HelperFunctions helper = new HelperFunctions();
		
		if (helper.checkCookie(request)) {
			HttpSession session = request.getSession();
			User userData = (User) session.getAttribute("userData");
			User user = new User();
			ManageRolesDb settingsRolesDb = new ManageRolesDb();
			
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
			
			FormEdit form = new FormEdit();
			
			String action = request.getParameter("action") != null ? request.getParameter("action") : "new";
			String formId = request.getParameter("form_id") != null ? request.getParameter("form_id") : "false";			
			String country = request.getParameter("country") != null ? request.getParameter("country") : "de";
			
			Enumeration<String> paramNames = request.getParameterNames();
			
			Map<String, String> globalData = new HashMap<String, String>();
			Map<String, String> metaData = new HashMap<String, String>();
			
			List<RolesStorage> roles = null;
			
			try {
				roles = settingsRolesDb.getRoles();
			} catch (Exception e1) {
				System.err.println("Es konnten keine Rollen aus DB gelesen werden");
				e1.printStackTrace();
			}
			
			request.setAttribute("user", user);
			request.setAttribute("roles", roles);
			request.setAttribute("pageTitle", "Formular bearbeiten");
			request.setAttribute("formId", formId);
			request.setAttribute("country", country);
			request.setAttribute("path", "formularmanager");
			request.setAttribute("view", "edit");
			
			globalData.put("formId", formId);
		      
			while (paramNames.hasMoreElements()) {
				
				// Parameter Keys in String Array schreiben
				String paramName = (String) paramNames.nextElement();
	
				if (paramName.startsWith("meta_")) {
					String newParamName = paramName.replace("meta_", "");
					metaData.put(newParamName, request.getParameter(paramName));					
				}
				else {
					globalData.put(paramName, request.getParameter(paramName));
				}
			}
			
			switch (action) {
				case "save" :
					try {
						boolean writeDatabaseResponse = false;
						if (formId.equals("false")) {
							writeDatabaseResponse = form.insertForm(globalData, metaData);
						}
						else {
							writeDatabaseResponse = form.updateForm(globalData, metaData);
						}
						
						
						if (writeDatabaseResponse) {
							response.sendRedirect("/sortimo/formularmanager/list");
							return;
						}
						else {					
							response.sendRedirect("/sortimo/error");
							return;
						}					
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				break;
				case "edit" :
					Map<String, String> formData = null;
					try {
						formData = form.getFormData(formId);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
					
					if (formData != null) {					
						request.setAttribute("country", country);
						request.setAttribute("formData", formData);
						getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);				
					}
					else {
						// Wenn formData leer ist Meldung ausgeben, dass Formular geloescht wurde 
						response.sendRedirect("/sortimo/formularmanager/list");
						return;
					}
				break;
				case "delete" :
					try {
						boolean writeDatabaseResponse = false;
						if (!formId.equals("false")) {
							writeDatabaseResponse = form.deleteForm(formId);
						}
						
						if (writeDatabaseResponse) {
							response.sendRedirect("/sortimo/formularmanager/list");
							return;
						}
						else {					
							response.sendRedirect("/sortimo/error");
							return;
						}					
					} 
					catch (Exception e) {
						e.printStackTrace();
					}			
				break;
				case "new" :
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
