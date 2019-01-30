package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			// リクエストパラメータの文字コードを指定
			request.setCharacterEncoding("UTF-8");

			// リクエストパラメータの取得
			String id = request.getParameter("buyId");
			int  buyId = Integer.parseInt(id);
			/* ====購入完了ページ表示用==== */
			BuyDataBeans resultBDB = BuyDAO.getBuyDataBeansByBuyId(buyId);
			request.setAttribute("resultBDB", resultBDB);

			// 購入アイテム情報
			ArrayList<ItemDataBeans> buyIDBList = BuyDetailDAO.getItemDataBeansListByBuyId(buyId);
			request.setAttribute("buyIDBList", buyIDBList);


			// 購入完了ページ
			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}
}