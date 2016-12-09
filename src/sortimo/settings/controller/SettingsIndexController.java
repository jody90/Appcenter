package sortimo.settings.controller;

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

import com.google.gson.Gson;

import sortimo.model.HelperFunctions;
import sortimo.model.User;
import sortimo.settings.databaseoperations.ManageRightsDb;
import sortimo.settings.databaseoperations.ManageRolesDb;
import sortimo.settings.databaseoperations.ManageUserDb;
import sortimo.storage.RightsStorage;
import sortimo.storage.RolesStorage;

@WebServlet("/SettingsIndexController")
public class SettingsIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SettingsIndexController() {
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
			String editUser = request.getParameter("editUser") != null ? request.getParameter("editUser") : "";
			request.setAttribute("user", user);
			request.setAttribute("username", user.getUsername());
			request.setAttribute("path", "settings");
			ManageUserDb settingsUsersDb = new ManageUserDb();
			ManageRolesDb settingsRolesDb = new ManageRolesDb();
			ManageRightsDb settingsRightsDb = new ManageRightsDb();
			
			switch (action) {
				case "manageUsers" :
					try {
						List<Map<String, String>> usersToEdit = settingsUsersDb.getUsers(editUser);
						request.setAttribute("users", usersToEdit);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("view", "manageUsers");
					request.setAttribute("pageTitle", "Benutzer verwalten");
				break;
				case "getEditUserData" :
					try {

						Map<String, String> userToEdit = settingsUsersDb.getUser(editUser);
						List<RolesStorage> roles = settingsRolesDb.getRoles();
						List<RightsStorage> rights = settingsRightsDb.getRights();

						Gson gsonUser = new Gson();
						String userJson = gsonUser.toJson(userToEdit);
						
						Gson gsonRoles = new Gson();
						String rolesJson = gsonRoles.toJson(roles);
						
						Gson gsonRights = new Gson();
						String rightsJson = gsonRights.toJson(rights);
						
						String EditUserData = "["+userJson+","+rolesJson+","+rightsJson+"]";
						
						response.setContentType("text/plain");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(EditUserData);
					    return;

					} catch (Exception e) {
						e.printStackTrace();
					}
				break;
				case "saveEditUser" :					
					Map<String, String> updateData = new HashMap<String, String>();
					
					Enumeration<String> paramNames = request.getParameterNames();
					
					while (paramNames.hasMoreElements()) {
						String paramName = (String) paramNames.nextElement();
						String paramValue = request.getParameter(paramName).isEmpty() ? null : request.getParameter(paramName);
						if (!paramName.equals("action")) {
							updateData.put(paramName, paramValue);
						}
					}
					
					try {
						settingsUsersDb.addAccount(updateData);
						response.sendRedirect("/sortimo/settings/index?action=manageUsers");
						return;
					} catch (Exception e) {
//						e.printStackTrace();
						response.sendRedirect("/sortimo/error");
						return;
					}
				case "deleteUser" :
					String deleteUser = request.getParameter("username");					
					
					try {
						settingsUsersDb.deleteAccount(deleteUser);
						response.sendRedirect("/sortimo/settings/index?action=manageUsers");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;					
				case "manageRoles" :
					try {
						List<RolesStorage> roles = settingsRolesDb.getRoles();
						request.setAttribute("roles", roles);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("view", "manageRoles");
					request.setAttribute("pageTitle", "Rollen verwalten");				
				break;
				case "getEditRoleData" :
					String roleId = request.getParameter("roleId") != null ? request.getParameter("roleId") : "false";					

					try {

						Map<String, String> role = settingsRolesDb.getRole(roleId);
						List<RightsStorage> rights = settingsRightsDb.getRights();

						Gson gsonRoles = new Gson();
						String rolesJson = gsonRoles.toJson(role);
						
						Gson gsonRights = new Gson();
						String rightsJson = gsonRights.toJson(rights);
						
						String EditUserData = "["+rolesJson+","+rightsJson+"]";
						
						response.setContentType("text/plain");
					    response.setCharacterEncoding("UTF-8");
					    response.getWriter().write(EditUserData);
					    return;

					} catch (Exception e) {
						e.printStackTrace();
					}
				break;
				case "saveEditRole" :
					Enumeration<String> paramNamesRole = request.getParameterNames();
					Map<String, String> roleData = new HashMap<String, String>();
					
					while (paramNamesRole.hasMoreElements()) {
						String paramName = (String) paramNamesRole.nextElement();
						String paramValue = request.getParameter(paramName).isEmpty() ? null : request.getParameter(paramName);
						if (!paramName.equals("action")) {
							roleData.put(paramName, paramValue);
						}
					}
					
					try {
						settingsRolesDb.addRole(roleData);
						response.sendRedirect("/sortimo/settings/index?action=manageRoles");
						return;
					} catch (Exception e) {
//						e.printStackTrace();
						response.sendRedirect("/sortimo/error");
						return;
					}
				case "deleteRole" :
					String deleteRoleId = request.getParameter("roleId");					
					
					try {
						settingsRolesDb.deleteRole(deleteRoleId);
						response.sendRedirect("/sortimo/settings/index?action=manageRoles");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;	
				case "manageRights" :
					try {
						List<RightsStorage> rights = settingsRightsDb.getRights();
						request.setAttribute("rights", rights);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("view", "manageRights");
					request.setAttribute("pageTitle", "Rechte verwalten");
				break;
				case "saveEditRight" :
					Enumeration<String> paramNamesRight = request.getParameterNames();
					Map<String, String> rightData = new HashMap<String, String>();
					
					while (paramNamesRight.hasMoreElements()) {
						String paramName = (String) paramNamesRight.nextElement();
						String paramValue = request.getParameter(paramName).isEmpty() ? null : request.getParameter(paramName);
						if (!paramName.equals("action")) {
							rightData.put(paramName, paramValue);
						}
					}
					
					try {
						settingsRightsDb.addRight(rightData);
						response.sendRedirect("/sortimo/settings/index?action=manageRights");
						return;
					} catch (Exception e) {
//						e.printStackTrace();
						response.sendRedirect("/sortimo/error");
						return;
					}
				case "deleteRight" :
					String deleteRightId = request.getParameter("rightId");		
					
					try {
						settingsRightsDb.deleteRight(deleteRightId);
						response.sendRedirect("/sortimo/settings/index?action=manageRights");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;
				case "changePassword" :
					
					String password = request.getParameter("password");
					
					try {
						settingsUsersDb.updatePassword(editUser, password);
						response.sendRedirect("/sortimo/settings/index");
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;
				default :					
					request.setAttribute("view", "index");
					request.setAttribute("pageTitle", "Verwaltung");
			}
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
