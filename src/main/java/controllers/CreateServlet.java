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
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//		リクエストスコープに登録されているパラメータ_tokenを取得
		String _token = request.getParameter("_token");
		//		_tokenが存在している かつセッションIdと同一値の場合
		if (_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();
			//			トランザクション処理の開始
			em.getTransaction().begin();
			//			エンティティクラスのインスタンス生成
			Tasklist t = new Tasklist();
			//			フォームに入力されたタスクの内容contentを取得
			String content = request.getParameter("content");
			//			取得したcontentの値をtaskテーブルのcontentカラムにセット
			t.setContent(content);
			//			現在日時の取得
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			//			作成日時
			t.setCreated_at(currentTime);
			//			更新日時
			t.setUpdated_at(currentTime);
			//			データベースに保存
			em.persist(t);
			em.getTransaction().commit();

			em.close();
			//			indexページにリダイレクト
			response.sendRedirect(request.getContextPath() + "/index");
		}
	}

}
