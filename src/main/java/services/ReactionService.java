package services;

import java.time.LocalDateTime;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReactionConverter;
import actions.views.ReactionView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Reaction;

/**
 * リアクションテーブルの操作に関わる処理を行うクラス
 */
public class ReactionService extends ServiceBase {


    /**
     * 指定した日報データのいいねの件数を取得し、返却する
     * @param report
     * @return 日報データのいいねの件数
     */
    public long countAllMine(ReportView report) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REA_GOOD_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return count;
    }

    /**
     * idを条件に取得したデータをReactionViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReactionView findOne(int id) {
        return ReactionConverter.toView(findOneInternal(id));
    }

    /**
     * 日報と従業員の登録内容を元にデータを1件作成し、いいねテーブルに登録する
     * @param rev いいねの登録内容
     */
    public void create(ReactionView rev) {
            LocalDateTime ldt = LocalDateTime.now();
            rev.setCreatedAt(ldt);
            createInternal(rev);
        }

    /**
     * いいねデータを削除する
     */
    public void destroy(Integer id) {
        em.getTransaction().begin();
        Reaction rea = findOneInternal(id);
        em.remove(rea);
        em.getTransaction().commit();
        }

    /**
     * 日報データのいいねの件数
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public long countGood(ReportView rv) {
        long count = (long) em.createNamedQuery(JpaConst.Q_REA_GOOD_COUNT_ALL_MINE, Long.class)
        .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
        .getSingleResult();
        return count;
    }

    /**
     * 指定した従業員IDと日報IDを持つリアクションデータを取得する
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public Reaction getByIds(ReportView rv, EmployeeView e) {
        Reaction rea = null;
        try {
            rea = em.createNamedQuery(JpaConst.Q_REA_GET_MINE, Reaction.class)
            .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
            .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(e))
            .getSingleResult();
        } catch(Exception ex) {
        }

        return rea;

    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Reaction findOneInternal(int id) {
        return em.find(Reaction.class, id);
    }

    /**
     * いいねデータを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(ReactionView rev) {

        em.getTransaction().begin();
        em.persist(ReactionConverter.toModel(rev));
        em.getTransaction().commit();

    }

}