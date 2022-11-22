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
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewServlet() {
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
		//		データの書き換えに必要なEntityTransactionクラスのインスタンスを取得
		//		トランザクション処理を開始
		em.getTransaction().begin();
		//		CSRF対策でセッションIDをセットしておく
		//		CSRF：クロスサイトリクエストフォージェリ、本来拒否すべき他サイトからのリクエストを受信し処理してしまう
		request.setAttribute("_token", request.getSession().getId());

		//		フォーム作成時リクエストスコープにデータが入ってないとエラーが起きる。
		//		エラー回避のため文字数０のデータをフォームに渡す。
		request.setAttribute("tasklist", new Tasklist());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/new.jsp");
		rd.forward(request, response);
	}

}
