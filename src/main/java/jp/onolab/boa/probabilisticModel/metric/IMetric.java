package jp.onolab.boa.probabilisticModel.metric;

import java.io.Serializable;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;


/**
 * ネットワークの良さを測る評価規準のインターフェース
 * @author shimazu
 */
public interface IMetric extends Serializable {
    /**
     * 入力ネットワークのスコアを計算
     * @param network ベイジアンネットワークの隣接行列
     * @return ベイジアンネットワークのスコア
     */
    double score(TCIntMatrix network);

    /**
     * 各ノードのスコアを計算
     * @param node ノード番号
     * @param parents 親ノード番号の集合
     * @return ノードとその親ノードの集合に対するスコア
     */
    double localScore(int node, List<Integer> parents);

    /**
     * 事前分布のスコアを返す．事前情報がなければ0でok．
     * @param network ベイジアンネットワークの隣接行列
     * @return 事前分布のスコア
     */
    double structurePrior(TCIntMatrix network);

    void setDataset(List<TIndividual> data);

    List<TIndividual> getDataset();

    TCIntMatrix getBase();

    IMetric clone();
}
