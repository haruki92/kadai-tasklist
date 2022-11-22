package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasklist;
import utils.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
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

		//		createNamedQuery()の引数に指定したJPQL'getAllTasklists'を実行して
		//		getResultList()を使って、実行結果を型のないリストとして取得
		List<Tasklist> tasklists = em.createNamedQuery("getAllTasklists", Tasklist.class).getResultList();
		response.getWriter().append(Integer.valueOf(tasklists.size()).toString());

		em.close();

		//		取得したリストtasklistsをリクエストスコープにセット
		//		request.setAttribute("tasklists", tasklists);
		//
		//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/index.jsp");
		//		rd.forward(request, response);
	}

}
