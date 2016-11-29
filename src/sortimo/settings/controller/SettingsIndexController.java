package sortimo.settings.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import sortimo.databaseoperations.UserDb;
import sortimo.model.User;
import sortimo.settings.databaseoperations.SettingsDb;
import sortimo.settings.storage.RightsStorage;
import sortimo.settings.storage.RolesStorage;

@WebServlet("/SettingsIndexController")
public class SettingsIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SettingsIndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();

		// liest alle Cookies in cookies ein
		Cookie[] cookies = null;
		cookies = request.getCookies();
		
		// prueft ob User angemeldet ist
		String username = user.isLoggedIn(cookies);
		
		if (username != null) {
			Map<String, String> userInfo = user.getUserInfo();
			
			String action = request.getParameter("action") != null ? request.getParameter("action") : "false";
			String editUser = request.getParameter("editUser");
			request.setAttribute("firstname", userInfo.get("firstname"));
			request.setAttribute("path", "settings");
			SettingsDb settingsDb = new SettingsDb();
			
			switch (action) {
				case "manageUsers" :
					try {
						List<Map<String, String>> usersToEdit = settingsDb.getUsers(editUser);
						request.setAttribute("users", usersToEdit);
					} catch (Exception e) {
						e.printStackTrace();
					}
					request.setAttribute("view", "manageUsers");
					request.setAttribute("pageTitle", "Benutzer verwalten");
				break;
				case "getEditUserData" :
					try {

						Map<String, String> userToEdit = settingsDb.getUser(editUser);
						List<RolesStorage> roles = settingsDb.getRoles();
						List<RightsStorage> rights = settingsDb.getRights();

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
					UserDb userDb = new UserDb();
					
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
						userDb.addAccount(updateData);
						response.sendRedirect("/sortimo/settings/index?action=manageUsers");
						return;
					} catch (Exception e) {
//						e.printStackTrace();
						response.sendRedirect("/sortimo/error");
						return;
					}
				case "deleteUser" :
					UserDb userDbConnection = new UserDb();
					String deleteUser = request.getParameter("username");					
					
					try {
						userDbConnection.deleteAccount(deleteUser);
						response.sendRedirect("/sortimo/settings/index?action=manageUsers");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;					
				case "manageRoles" :
					try {
						List<RolesStorage> roles = settingsDb.getRoles();
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

						Map<String, String> role = settingsDb.getRole(roleId);
						List<RightsStorage> rights = settingsDb.getRights();

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
						settingsDb.addRole(roleData);
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
						settingsDb.deleteRole(deleteRoleId);
						response.sendRedirect("/sortimo/settings/index?action=manageRoles");
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;	
				case "manageRights" :
					try {
						List<RightsStorage> rights = settingsDb.getRights();
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
						settingsDb.addRight(rightData);
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
						settingsDb.deleteRight(deleteRightId);
						response.sendRedirect("/sortimo/settings/index?action=manageRights");
						return;
					} catch (Exception e) {
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
