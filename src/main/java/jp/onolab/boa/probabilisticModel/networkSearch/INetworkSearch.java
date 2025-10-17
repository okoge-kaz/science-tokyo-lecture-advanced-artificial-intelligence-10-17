package jp.onolab.boa.probabilisticModel.networkSearch;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.probabilisticModel.metric.IMetric;


/**
 * ネットワーク構造の探索手法のインターフェース
 * @author shimazu
 */
public interface INetworkSearch extends Serializable {
    /**
     * ネットワーク構造の探索
     * @param network 隣接行列(結果が保存される)
     * @param parentsOfNodes 各ノードの親ノードの集合(結果が保存される)
     * @param metric 評価指標
     * @param minGain スコアの改善量の下限値
     * @return 構築したネットワーク構造のスコア
     */
    double search(TCIntMatrix network, List<List<Integer>> parentsOfNodes, IMetric metric, double minGain);

    /**
     * ネットワーク構造の探索
     * @param network 隣接行列(結果が保存される)
     * @param parentsOfNodes 各ノードの親ノードの集合(結果が保存される)
     * @param metric 評価指標
     * @return 構築したネットワーク構造のスコア
     */
    double search(TCIntMatrix network, List<List<Integer>> parentsOfNodes, IMetric metric);

    /**
     * 親ノードの上限数を設定．
     * @param k 親ノード数の上限
     */
    public void setMaximumNumberOfParents(int k);

    /**
     * 親ノードの上限数の取得．
     * @return 親ノード数の上限
     */
    public int getMaximumNumberOfParents();

    public INetworkSearch clone();
}
