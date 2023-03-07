package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Reaction;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReactionConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReactionViewのインスタンス
     * @return Reactionのインスタンス
     */
    public static Reaction toModel(ReactionView rev) {
        return new Reaction(
                rev.getId(),
                EmployeeConverter.toModel(rev.getEmployee()),
                ReportConverter.toModel(rev.getReport()),
                rev.getCreatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param rea Reactionのインスタンス
     * @return ReactionViewのインスタンス
     */
    public static ReactionView toView(Reaction rea) {

        if (rea == null) {
            return null;
        }

        return new ReactionView(
                rea.getId(),
                EmployeeConverter.toView(rea.getEmployee()),
                ReportConverter.toView(rea.getReport()),
                rea.getCreatedAt());

    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReactionView> toViewList(List<Reaction> list) {
        List<ReactionView> evs = new ArrayList<>();

        for (Reaction rea : list) {
            evs.add(toView(rea));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Reaction rea, ReactionView rev) {
        rea.setId(rev.getId());
        rea.setEmployee(EmployeeConverter.toModel(rev.getEmployee()));
        rea.setReport(ReportConverter.toModel(rev.getReport()));
        rea.setCreatedAt(rev.getCreatedAt());
    }

}