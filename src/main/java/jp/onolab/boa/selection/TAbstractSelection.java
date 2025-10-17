package jp.onolab.boa.selection;


/**
 * 抽象選択クラス
 * @author shimazu
 */
public abstract class TAbstractSelection implements ISelection {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /** 選択率 **/
    double fSelectionRate;

    /**
     * コンストラクタ
     * @param selectionRate 選択率
     */
    public TAbstractSelection(double selectionRate) {
        assert(0 < selectionRate && selectionRate <= 1.0);
        fSelectionRate = selectionRate;
    }
}
