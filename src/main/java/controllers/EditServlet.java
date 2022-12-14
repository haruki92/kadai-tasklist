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
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditServlet() {
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
		//		該当のIDのタスクのデータ1件のみデータベースから取得
		Tasklist t = em.find(Tasklist.class, Integer.parseInt(request.getParameter("id")));
		//		取得したタスクのデータをリクエストスコープに登録
		request.setAttribute("tasklist", t);
		//		セッションIdをリクエストスコープに登録
		request.setAttribute("_token", request.getSession().getId());
		//		タスクのデータがある時
		if (t != null) {
			//			セッションスコープにタスクのデータのIdを登録
			request.getSession().setAttribute("task_id", t.getId());
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
		rd.forward(request, response);
	}

}
