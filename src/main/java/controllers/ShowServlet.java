package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasklist;
import utils.DBUtil;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();
		//		リクエストスコープのidと合致するタスクリストのデータ1件のみをデータベースから取得
		Tasklist t = em.find(Tasklist.class, Integer.parseInt(request.getParameter("id")));
		em.close();
		//		タスクリストのデータをリクエストスコープに登録
		request.setAttribute("tasklist", t);
		//		show.jspの呼び出し
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/show.jsp");
		rd.forward(request, response);
	}

}
