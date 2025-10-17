package jp.onolab.ga;

import java.io.Serializable;
import java.util.Collections;

import jp.onolab.cloneableArrayList2019.TCCloneableArrayList;
import jp.onolab.random2013.ICRandom;

/**
 * Binary/UX+MGG
 * @author isao
 */
public class TUxMgg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** ビット数 */
	private int fNoOfBits;
	
	/** The population */
	private TCCloneableArrayList<TIndividual> fPopulation;
	
	/** The population size */
	private int fPopulationSize;
	
	/** The parent set */
	private TCCloneableArrayList<TIndividual> fParents;
	
	/** The offspring set */
	private TCCloneableArrayList<TIndividual> fKids;
	
	/** The number of offspring */
	private int fNoOfKids;

	/** The mating selection operator */
	private TRandomSelectionWithoutReplacement fReproductionSelection;

	/** UX */
	private TUniformCrossover fUx;
	
	/** The survival selection operator */
	private TBestAndRankBasedRouletteSelectionFromFamily fSurvivalSelection;
	
	/** The random number generator */
	private ICRandom fRandom;
	
	/** The comparator of individuals */
	private TEvaluationValueComparator fComparator;
	
	/** The solution template */
	private TIndividual fSolutionTemplate;
	
	/**
	 * コンストラクタ
	 * @param noOfBits ビット数
	 * @param populationSize 集団サイズ
	 * @param noOfKids 生成子個体数
	 * @param random 乱数発生器
	 */
	public TUxMgg(int noOfBits, int populationSize, int noOfKids, ICRandom random) {
		fNoOfBits = noOfBits;
		fPopulationSize = populationSize;
		fRandom = random;
		fNoOfKids = noOfKids;
		fSolutionTemplate = new TIndividual();
		fSolutionTemplate.getBitString().setLength(fNoOfBits);
		fComparator = new TEvaluationValueComparator();
	}

	/**
	 * 初期化する．
	 * @return 初期集団
	 */
	public TCCloneableArrayList<TIndividual> initialize() {
		fParents = new TCCloneableArrayList<TIndividual>(fSolutionTemplate);
		fKids = new TCCloneableArrayList<TIndividual>(fSolutionTemplate);
		fKids.resize(fNoOfKids);
		fReproductionSelection = new TRandomSelectionWithoutReplacement(fRandom);
		fUx = new TUniformCrossover(fRandom);
		fSurvivalSelection = new TBestAndRankBasedRouletteSelectionFromFamily(fRandom);
		fPopulation = new TCCloneableArrayList<TIndividual>(fSolutionTemplate);
		fPopulation.resize(fPopulationSize);
		return fPopulation;
	}
	
	/**
	 * 集団を返す．
	 * @return 集団
	 */
	public TCCloneableArrayList<TIndividual> getPopulation() {
		return fPopulation;
	}
	
	/**
	 * 子個体集合を生成して返す．
	 * @return 子個体集合
	 */
	public TCCloneableArrayList<TIndividual> makeOffspring() {
		fReproductionSelection.doIt(fPopulation, fUx.getNoOfParents(), fParents); //生存選択を行う．
		fUx.sampleOffspring(fParents, fKids); //AREXにより子個体集合を生成する．
		return fKids;
	}
	
	/**
	 * 世代を進める．
	 */
	public void nextGeneration() {
		fSurvivalSelection.doIt(fPopulation, fParents, fKids); //生存選択を行う．
	}
	
	/**
	 * 集団中の最良個体を返す．
	 * @return 集団中の最良個体
	 */
	public TIndividual getBestIndividual() {
		Collections.sort(fPopulation, fComparator);
		return fPopulation.get(0);
	}
	
	/**
	 * 集団中の最良個体の評価値を返す．
	 * @return 集団中の最良個体の評価値
	 */
	public double getBestEvaluationValue() {
		return getBestIndividual().getEvaluationValue();
	}
		
}
