package jp.onolab.boa.probabilisticModel;

import java.util.ArrayList;
import java.util.List;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.boa.TIndividual;
import jp.onolab.boa.probabilisticModel.estimator.IEstimator;
import jp.onolab.boa.probabilisticModel.metric.IMetric;
import jp.onolab.boa.probabilisticModel.networkSearch.INetworkSearch;
import jp.onolab.matrix2017.TCMatrix;
import jp.onolab.random2013.ICRandom;


/**
 * ベイジアンネットワークのクラス
 * @author shimazu
 */
public class TBayesianNetwork extends TAbstractProbabilisticModel {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /** K2アルゴリズム **/
    private INetworkSearch fNetworkSearch;

    /** K2アルゴリズムの評価指標 **/
    private IMetric fMetric;

    /** 確率分布の推定器 **/
    private IEstimator fEstimator;

    /** 隣接行列 **/
    private TCIntMatrix fNetwork;

    /** 各ノードの親ノードの集合
     * 隣接行列のみで表現可能だが，高速化のために利用
    */
    private List<List<Integer>> fParentsOfNodes;

    /** サンプリング順序 **/
    private List<Integer> fSamplingOrder;

    /** 各ノードの条件付き確率 **/
    private List<TCMatrix> fConditionalProb;

    /**
     * コンストラクタ
     * @param category 各次元の基数
     * @param populationSize 集団サイズ
     * @param metric ネットワークの評価指標
     * @param estimator 確率分布の推定器
     * @param networkSearch ネットワーク構造の探索手法
     * @param random 乱数生成器
     */
    public TBayesianNetwork(TCIntMatrix category, int populationSize, IMetric metric,
                            IEstimator estimator, INetworkSearch networkSearch, ICRandom random) {
        super(category, populationSize, random);
        fNetworkSearch = networkSearch;
        fMetric = metric;
        fEstimator = estimator;

        fNetwork = new TCIntMatrix(fDimension, fDimension);
        fParentsOfNodes = new ArrayList<>();
        for(int i = 0; i < fDimension; ++i) {
            fParentsOfNodes.add(new ArrayList<>());
        }
        fSamplingOrder = new ArrayList<>();
        fConditionalProb = new ArrayList<>();
    }

    public TBayesianNetwork(TBayesianNetwork other) {
        super(other);
        fNetworkSearch = other.fNetworkSearch.clone();
        fMetric = other.fMetric.clone();
        fEstimator = other.fEstimator.clone();
        fNetwork = other.fNetwork.clone();
        fParentsOfNodes = new ArrayList<>(other.fParentsOfNodes);
        fSamplingOrder = new ArrayList<>(other.fSamplingOrder);
        fConditionalProb = new ArrayList<>(other.fConditionalProb);
    }

    @Override
    public TBayesianNetwork clone() {
        return new TBayesianNetwork(this);
    }

    @Override
    public List<TIndividual> sampling(int lam) {
        List<TIndividual> population = new ArrayList<>(lam);
        for(int i = 0; i < lam; ++i) {
            TCIntMatrix vector = new TCIntMatrix(fDimension);
            for(int node : fSamplingOrder) {
                List<Integer> parents = fParentsOfNodes.get(node);
                int index = 0;
                for(int j = 0; j < parents.size(); ++j) {
                    int parent = parents.get(j);
                    index += vector.getValue(parent) * (int)Math.pow(fCMax, j + 1);
                }
                TCMatrix cpt = fConditionalProb.get(node);
                double th = fRandom.nextDouble(0, 1);
                double cumsum = 0.0;
                for(int j = 0; j < fCMax; ++j) {
                    cumsum += cpt.getValue(index + j);
                    if(th <= cumsum) {
                        vector.setValue(node, j);
                        break;
                    }
                }
            }
            TIndividual indiv = new TIndividual(fDimension);
            indiv.setX(vector);
            population.add(indiv);
        }
        return population;
    }

    @Override
    public void update(List<TIndividual> pop) {
        // K2アルゴリズムの実行
        fMetric.setDataset(pop);
        fNetworkSearch.search(fNetwork, fParentsOfNodes, fMetric);
        // 各ノードの条件付き確率の推定
        fConditionalProb.clear();
        for(int node = 0; node < fDimension; ++node) {
            List<Integer> parents = fParentsOfNodes.get(node);
            fConditionalProb.add(fEstimator.estimate(node, parents, pop));
        }
        // ネットワーク構造に従って値のサンプリング順序を決定
        decideSamplingOrder();
    }

    /**
     * ネットワーク構造を参照して，個体生成時のサンプリング順序を決定．
     */
    private void decideSamplingOrder() {
        fSamplingOrder.clear();
        List<Integer> rootNodes = new ArrayList<>();
        for(int i = 0; i < fDimension; ++i) {
            if(fParentsOfNodes.get(i).size() == 0) {
                rootNodes.add(i);
            }
        }
        TCIntMatrix tmp = fNetwork.clone();
        while(!rootNodes.isEmpty()) {
            int node = rootNodes.remove(0);
            fSamplingOrder.add(node);
            for(int i = 0; i < fDimension; ++i) {
                if(tmp.getValue(i, node) == 1) {
                    tmp.setValue(i, node, 0);
                    int sum = 0;
                    for(int j = 0; j < fDimension; ++j) {
                        sum += tmp.getValue(i, j);
                    }
                    if(sum == 0) {
                        rootNodes.add(i);
                    }
                }
            }
        }
        // 全ての要素が0になっていればグラフは非巡回であると保証される
        if(tmp.max(new ArrayList<Integer>()) != 0) {
            System.out.println("エラー発生．ネットワークが巡回しています．");
            System.exit(-1);
        }
    }

    @Override
    public boolean isConvergence() {
        double convergence = getConvergence();
        return 1 - convergence < 1e-8;
    }

    @Override
    public double getConvergence() {
        double convergence = 0.0;
        if(fConditionalProb.size() == 0) {
            for(int i = 0; i < fDimension; ++i) {
                convergence += 1 / fCategory.getValue(i);
            }
            convergence /= fDimension;
        }
        else {
            TCIntMatrix vector = new TCIntMatrix(fDimension);
            for(int node : fSamplingOrder) {
                List<Integer> parents = fParentsOfNodes.get(node);
                int index = 0;
                for(int j = 0; j < parents.size(); ++j) {
                    int parent = parents.get(j);
                    index += vector.getValue(parent) * (int)Math.pow(fCMax, j + 1);
                }
                TCMatrix cpt = fConditionalProb.get(node);
                double maxProb = 0.0;
                int maxIdx = -1;
                for(int j = 0; j < fCategory.getValue(node); ++j) {
                    if(maxProb < cpt.getValue(index + j)) {
                        maxProb = cpt.getValue(index + j);
                        maxIdx = j;
                    }
                }
                convergence += maxProb;
                vector.setValue(node, maxIdx);
            }
            convergence /= fDimension;
        }
        return convergence;
    }

    /**
     * 隣接行列を返す．
     * 外部で変更されたくないため，cloneして返す．
     * @return 隣接行列
     */
    public TCIntMatrix getAdjacencyMatrix() {
        return fNetwork.clone();
    }

    /**
     * 親ノードの集合を返す．
     * 外部で変更されたくないため，ディープコピーして返す．
     * @param node ノード番号
     * @return 親ノードの集合
     */
    public List<Integer> getParents(int node) {
        return new ArrayList<>(fParentsOfNodes.get(node));
    }

    /**
     * サンプリング順序を返す．
     * 外部で変更されたくないため，ディープコピーして返す．
     * @return サンプリング順序
     */
    public List<Integer> getSamplingOrder() {
        return new ArrayList<>(fSamplingOrder);
    }

    /**
     * 条件付き確率を返す．
     * 外部で変更されたくないため，cloneして返す．
     * @param node ノード番号
     * @return 条件付き確率
     */
    public TCMatrix getConditionalProbability(int node) {
        return fConditionalProb.get(node);
    }

    /**
     * 評価指標を返す．
     * 外部で変更されたくないため，cloneして返す．
     * @return 評価指標のインスタンス
     */
    public IMetric getMetric() {
        return fMetric.clone();
    }
}
