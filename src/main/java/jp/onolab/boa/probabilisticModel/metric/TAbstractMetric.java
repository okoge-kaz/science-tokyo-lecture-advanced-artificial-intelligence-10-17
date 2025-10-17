package jp.onolab.boa.probabilisticModel.metric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;


/**
 * 抽象情報量規準クラス
 * @author shimazu
 */
public abstract class TAbstractMetric implements IMetric {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /** 集団(データセット) **/
    List<TIndividual> fData;

    /** 集団サイズ **/
    int fDatasize;

    /** 各次元の基数 **/
    TCIntMatrix fBase;

    /**
     * コンストラクタ
     * @param data  集団(データセット)
     * @param base  各次元の基数
     */
    public TAbstractMetric(List<TIndividual> data, TCIntMatrix base) {
        fData = data;
        fBase = base;
        fDatasize = data.size();
    }

    public TAbstractMetric(TAbstractMetric other) {
        fData = new ArrayList<>(other.fData);
        fBase = other.fBase.clone();
        fDatasize = other.fDatasize;
    }

    public TAbstractMetric(TCIntMatrix base) {
        this(new ArrayList<>(), base);
    }

    @Override
    public double score(TCIntMatrix network) {
        assert(network.getColumnDimension() == network.getRowDimension());
        int dim = network.getColumnDimension();
        double networkScore = structurePrior(network);  // 事前分布のスコアを計算．
        for(int node = 0; node < dim; ++node) {
            List<Integer> parents = getParents(network, node);
            networkScore += localScore(node, parents);  // 各ノードのスコアを足し合わせていく．
        }
        return networkScore;
    }

    @Override
    public double structurePrior(TCIntMatrix network) {
        return 0.0;
    }

    @Override
    public void setDataset(List<TIndividual> data) {
        fData = data;
        fDatasize = data.size();
    }

    @Override
    public List<TIndividual> getDataset() {
        return fData;
    }

    @Override
    public TCIntMatrix getBase() {
        return fBase;
    }

    /**
     * 隣接行列から親ノードを取得．
     * @param network ベイジアンネットワークの隣接行列
     * @param node ノード番号
     * @return 入力ノードの親ノードの集合
     */
    List<Integer> getParents(TCIntMatrix network, int node) {
        int dim = network.getColumnDimension();
        List<Integer> parents = new ArrayList<>();
        for(int i = 0; i < dim; ++i) {
            if(network.getValue(node, i) != 0) {
                parents.add(i);
            }
        }
        return parents;
    }

    /**
     * データセット内の値の組み合わせごとの出現頻度を計測．
     * @param nodes 値の組み合わせの対象となるノード番号の集合．
     * @return 対応するノード組の各値に対するデータセット内の頻度情報
     */
    public HashMap<String, Integer> getFrequency(List<Integer> nodes) {
        HashMap<String, Integer> counts = new HashMap<>();
        for(TIndividual indiv : fData) {
            boolean isValid = true;
            String key = "";
            for(int node : nodes) {
                int val = indiv.getX().getValue(node);
                if(val < 0) {
                    isValid = false;
                    break;
                }
                key += String.valueOf(val) + "_";
            }
            if(isValid) {
                if(counts.containsKey(key)) {
                    counts.put(key, counts.get(key) + 1);
                }
                else {
                    counts.put(key, 1);
                }
            }
        }
        return counts;
    }

    /**
     * 条件付きエントロピーを推定
     * @param node ノード番号
     * @param parents 親ノード番号の集合
     * @param base 対数演算の底
     * @return 親ノードの集合のもとでのノードの条件付きエントロピー
     */
    public double getConditionalEntropy(int node, List<Integer> parents, double base) {
        double entropy = 0.0;
        /** 親ノードが存在しない場合 **/
        if(parents.size() == 0) {
            HashMap<String, Integer> uniqueFreq = getFrequency(new ArrayList<>(Arrays.asList(node)));
            int dataSize = 0;
            for(int num : uniqueFreq.values()) {
                dataSize += num;
            }
            for(int num : uniqueFreq.values()) {
                double prob = (double)num / dataSize;
                entropy += getEntropy(prob, base);
            }
        }
        /** 親ノードが存在する場合 **/
        else {
            HashMap<String, Integer> pCounts = new HashMap<>();
            HashMap<String, Integer> npCounts = new HashMap<>();
            for(TIndividual indiv : fData) {
                boolean isValid = true;
                String pKey = "p_";
                for(int parent : parents) {
                    int val = indiv.getX().getValue(parent);
                    if(val < 0) {
                        isValid = false;
                        break;
                    }
                    pKey += String.valueOf(val) + "_";
                }
                // 親ノードが全て値を持ち，かつ対象ノードも値を持つときのみカウント．
                int val = indiv.getX().getValue(node);
                if(isValid && val >= 0) {
                    if(pCounts.containsKey(pKey)) {
                        pCounts.put(pKey, pCounts.get(pKey) + 1);
                    }
                    else {
                        pCounts.put(pKey, 1);
                    }
                    String npKey = pKey + "c_" + String.valueOf(val);
                    if(npCounts.containsKey(npKey)) {
                        npCounts.put(npKey, npCounts.get(npKey) + 1);
                    }
                    else {
                        npCounts.put(npKey, 1);
                    }
                }
            }
            int dataSize = 0;
            for(int num : pCounts.values()) {
                dataSize += num;
            }
            double pEntropy = 0.0;
            for(int num : pCounts.values()) {
                double prob = (double)num / dataSize;
                pEntropy += getEntropy(prob, base);
            }
            double npEntropy = 0.0;
            for(int num : npCounts.values()) {
                double prob = (double)num / fDatasize;
                npEntropy += getEntropy(prob, base);
            }
            entropy = npEntropy - pEntropy;
        }
        return entropy;
    }

    /**
     * エントロピーの計算．
     * @param prob 確率
     * @param base 対数の底
     * @return エントロピー
     */
    public double getEntropy(double prob, double base) {
        return -prob * Math.log(prob + 1e-8) / Math.log(base);
    }

    @Override
    public IMetric clone() {
        return null;
    }
}
