package sortimo.formularmanager.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FormularManagerThanksController")
public class FormularManagerThanksController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FormularManagerThanksController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("pageTitle", "Danke");
		request.setAttribute("path", "formularmanager");
		request.setAttribute("view", "thanks");
		
		getServletContext().getRequestDispatcher("/layout.jsp").forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
