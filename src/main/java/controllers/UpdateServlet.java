package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tasklist;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//		jspから送られたパラメータ_tokenを取得
		String _token = request.getParameter("_token");
		//		_tokenが存在する　かつセッションIdと同一値の時
		if (_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();

			//			セッションスコープからタスクのIdを取得して該当のタスクのデータ1件のみデータベースから取得
			Tasklist t = em.find(Tasklist.class, (Integer) (request.getSession().getAttribute("task_id")));

			//			フォームから送信されたタスクの内容contentを取得
			String content = request.getParameter("content");
			//			contentを上書き
			t.setContent(content);

			//			更新時の日時を取得
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			//			更新日時のみ上書き
			t.setUpdated_at(currentTime);

			//			更新処理
			//			トランザクションを取得して処理を開始
			em.getTransaction().begin();
			//			トランザクションをコミット
			em.getTransaction().commit();

			em.close();

			//			セッションスコープ上で不要になったタスクのId　task_idを削除
			request.getSession().removeAttribute("task_id");

			//			indexページにリダイレクト
			response.sendRedirect(request.getContextPath() + "/index");
		}
	}

}
