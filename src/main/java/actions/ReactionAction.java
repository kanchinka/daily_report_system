package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ReactionView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import models.Reaction;
import services.ReactionService;
import services.ReportService;

/**
 * リアクションに関わる処理を行うActionクラス
 *
 */
public class ReactionAction extends ActionBase {

    private ReactionService service;
    private ReportService rservice;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReactionService();
        rservice = new ReportService();

        //メソッドを実行
        invoke();

        service.close();
        rservice.close();
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        System.out.println("create");

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //idを条件に日報データを取得する
            ReportView rv = rservice.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));
            //パラメータの値を元に従業員情報のインスタンスを作成する
            ReactionView rev = new ReactionView(
                    null,
                    ev,
                    rv,
                    null);
            //create
            service.create(rev);

            //セッションに完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_GOOD.getMessage());

          //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
            }
        }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

      //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //idを条件に日報データを取得する
            ReportView rv = rservice.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //指定した従業員IDと日報IDを持つリアクションデータを取得する
            Reaction rea =service.getByIds(rv, ev);

            //idを条件にいいねデータを論理削除する
            service.destroy(rea.getId());

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED_GOOD.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
        }
    }

}
