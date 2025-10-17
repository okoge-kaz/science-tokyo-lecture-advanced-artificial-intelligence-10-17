package jp.onolab.boa.probabilisticModel.networkSearch;


/**
 * 抽象ネットワーク構造探索クラス
 * @author shimazu
 */
public abstract class TAbstractNetworkSearch implements INetworkSearch {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /** 親ノード数の上限 **/
    protected int fConstraintK;

    /**
     * コンストラクタ
     * @param k 親ノード数の上限
     */
    public TAbstractNetworkSearch(int k) {
        fConstraintK = k;
    }

    public TAbstractNetworkSearch(TAbstractNetworkSearch other) {
        fConstraintK = other.fConstraintK;
    }

    @Override
    public void setMaximumNumberOfParents(int k) {
        fConstraintK = k;
    }

    @Override
    public int getMaximumNumberOfParents() {
        return fConstraintK;
    }

    @Override
    public INetworkSearch clone() {
        return null;
    }
}
