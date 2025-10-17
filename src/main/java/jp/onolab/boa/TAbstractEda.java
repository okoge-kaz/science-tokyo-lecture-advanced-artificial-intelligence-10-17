package jp.onolab.boa;

import java.util.ArrayList;
import java.util.List;

import jp.onolab.boa.probabilisticModel.IProbabilisticModel;
import jp.onolab.boa.replacement.IReplacement;
import jp.onolab.boa.selection.ISelection;


/**
 * 抽象分布推定アルゴリズムのクラス．
 * @author shimazu
 */
public abstract class TAbstractEda implements IEda {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    /** 確率モデル **/
    protected IProbabilisticModel fModel;

    /** 選択方法 **/
    protected ISelection fSelection;

    /** 置換方法 **/
    protected IReplacement fReplacement;

    /** 集団 **/
    protected List<TIndividual> fPopulation;

    /** 最良個体 **/
    protected TIndividual fBestIndiv;

    /** 評価回数 **/
    protected int fNumOfEval;

    /**
     * コンストラクタ
     * @param model 確率モデル
     * @param selection 選択方法
     * @param replacement 置換方法
     */
    public TAbstractEda(IProbabilisticModel model, ISelection selection, IReplacement replacement) {
        fModel = model;
        fSelection = selection;
        fReplacement = replacement;
        fPopulation = new ArrayList<>();

        fBestIndiv = new TIndividual();
        fNumOfEval = 0;
    }

    @Override
    public List<TIndividual> initialize() {
        return fModel.initialize();
    }

    @Override
    public List<TIndividual> samplePopulation(int lam) {
        return fModel.sampling(lam);
    }

    @Override
    public boolean isConvergence() {
        return fModel.isConvergence();
    }

    @Override
    public double getConvergence() {
        return fModel.getConvergence();
    }

    @Override
    public TIndividual getBestIndividual() {
        return fBestIndiv;
    }

    @Override
    public int getNumOfEvaluations() {
        return fNumOfEval;
    }

    @Override
    public List<TIndividual> getPopulation() {
        return new ArrayList<>(fPopulation);
    }

    @Override
    public IProbabilisticModel getProbabilisticModel() {
        return fModel.clone();
    }
}
