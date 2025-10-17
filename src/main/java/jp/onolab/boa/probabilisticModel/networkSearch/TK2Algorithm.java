package jp.onolab.boa.probabilisticModel.networkSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.probabilisticModel.metric.IMetric;
import jp.onolab.matrix2017.TCMatrix;


/**
 * K2アルゴリズムのクラス
 * オリジナルのK2アルゴリズムでは順序関係を事前に与えることでベイジアンネットワークが巡回しないようにしているが，
 * このK2アルゴリズムではinheritanceという変数を用いて先祖・子孫関係を表現して巡回しないようにしている．
 * 参考コード：https://github.com/wohnjayne/eda-suite
 * @author shimazu
 */
public class TK2Algorithm extends TAbstractNetworkSearch {
    /** シリアライズ用のid **/
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param k 親ノード数の上限
     */
    public TK2Algorithm(int k) {
        super(k);
    }

    public TK2Algorithm(TK2Algorithm other) {
        super(other);
    }

    public TK2Algorithm clone() {
        return new TK2Algorithm(this);
    }

    @Override
    public double search(TCIntMatrix network, List<List<Integer>> parentsOfNodes, IMetric metric) {
        return search(network, parentsOfNodes, metric, 0.0);
    }

    @Override
    public double search(TCIntMatrix network, List<List<Integer>> parentsOfNodes, IMetric metric, double minGain) {
        int dim = network.getColumnDimension();
        // 各変数の初期化
        network.fill(0);
        for(int i = 0; i < dim; ++i) {
            parentsOfNodes.get(i).clear();
        }
        TCIntMatrix inheritance = new TCIntMatrix(dim, dim);  // 先祖，子孫の関係を表現する行列
        TCMatrix nodeScore = new TCMatrix(dim);  // 各ノードの現状のスコアを保存する変数
        ArrayList<Integer> indices = new ArrayList<>(); // スコアが最も改善するインデックスを保存する変数
        // 各ノードが独立な場合のスコア計算
        List<Integer> dummyParents = new ArrayList<>();
        for(int node = 0; node < dim; ++node) {
            nodeScore.setValue(node, metric.localScore(node, dummyParents));
        }
        // 各ノード間に依存関係があると仮定した場合のスコアの改善量を計算
        TCMatrix candidateGain = new TCMatrix(dim, dim).add(minGain);
        for(int i = 0; i < dim; ++i) {
            for(int j = 0; j < dim; ++j) {
                // 自分自身との場合は飛ばす
                if(i == j) {
                    continue;
                }
                List<Integer> parents = new ArrayList<>(Arrays.asList(j));
                double newScore = metric.localScore(i, parents);
                candidateGain.setValue(i, j, newScore - nodeScore.getValue(i));
            }
        }
        // 貪欲法によってエッジを追加．fConstraintKが0以下とは，親ノードを持たない(=各ノードが独立)ということ．
        while(fConstraintK > 0) {
            double maxScore = candidateGain.max(indices);
            int child = indices.get(0) / dim;
            int parent = indices.get(0) % dim;
            if(maxScore - minGain < 1e-10) {
                break;
            }
            network.setValue(child, parent, 1);
            parentsOfNodes.get(child).add(parent);
            nodeScore.setValue(child, nodeScore.getValue(child) + maxScore);

            candidateGain.setValue(child, parent, minGain);
            candidateGain.setValue(parent, child, minGain);
            inheritance.setValue(child, parent, 1);

            List<Integer> ancestors = new ArrayList<>(Arrays.asList(parent));
            for(int i = 0; i < dim; ++i) {
                if(inheritance.getValue(parent, i) == 1 && inheritance.getValue(child, i) == 0) {
                    ancestors.add(i);
                    inheritance.setValue(child, i, 1);
                    candidateGain.setValue(i, child, minGain);
                }
            }

            List<Integer> descendents = new ArrayList<>();
            for(int i = 0; i < dim; ++i) {
                if(inheritance.getValue(i, child) == 1) {
                    descendents.add(i);
                }
            }

            while(!descendents.isEmpty()) {
                int node = descendents.remove(0);

                boolean nodeUpdated = false;
                for(Integer ancestor : ancestors) {
                    if(inheritance.getValue(node, ancestor) == 0) {
                        inheritance.setValue(node, ancestor, 1);
                        candidateGain.setValue(ancestor, node, minGain);
                        nodeUpdated = true;
                    }
                }
                if(nodeUpdated) {
                    for(int i = 0; i < dim; ++i) {
                        if(inheritance.getValue(i, node) == 1 && !descendents.contains(i)) {
                            descendents.add(i);
                        }
                    }
                }
            }
            for(int i = 0; i < dim; ++i) {
                if(network.getValue(child, i) == 0 && inheritance.getValue(i, child) == 0 && child != i) {
                    List<Integer> parents = new ArrayList<>();
                    for(int j = 0; j < dim; ++j) {
                        if(network.getValue(child, j) == 1) {
                            parents.add(j);
                        }
                    }
                    parents.add(i);
                    double newScore = metric.localScore(child, parents);
                    candidateGain.setValue(child, i, newScore - nodeScore.getValue(child));
                }
            }
            if(parentsOfNodes.get(child).size() == fConstraintK) {
                for(int i = 0; i < dim; ++i) {
                    candidateGain.setValue(child, i, minGain);
                }
            }
        }
        double score = 0.0;
        for(int i = 0; i < dim; ++i) {
            score += nodeScore.getValue(i);
        }
        return score;
    }
}
