package sortimo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ErrorController")
public class ErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ErrorController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setAttribute("pageTitle", "Fehler");
		request.setAttribute("path", "");
		request.setAttribute("view", "error");
		getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);
	}

}
