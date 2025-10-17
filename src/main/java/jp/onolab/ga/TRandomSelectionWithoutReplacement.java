package jp.onolab.ga;

import jp.onolab.cloneableArrayList2019.TCCloneableArrayList;
import jp.onolab.random2013.ICRandom;

/**
 * 集団からランダムに親個体を非復元抽出する．
 * @author isao
 */
public class TRandomSelectionWithoutReplacement {

	/** 乱数発生機器 */
	private ICRandom fRandom;

	/**
	 * コンストラクタ
	 * @param random 乱数発生機
	 */
	public TRandomSelectionWithoutReplacement(ICRandom random) {
		fRandom = random;
	}
	
	/**
	 * 集団popからランダムに非復元抽出したnoOfParents個の個体を親子体集合parentsに入れて返す．
	 * @param pop 集団
	 * @param noOfParents 親個体数
	 * @param parents 親子体集合．空である必要がある．
	 */
	public void doIt(TCCloneableArrayList<TIndividual> pop, int noOfParents, TCCloneableArrayList<TIndividual> parents) {
		assert parents.size() == 0;
		//親個体を集団からランダムにnoOfParents個体数だけ非復元抽出する．
		for(int i = 0; i < noOfParents; i++) {
			int index = fRandom.nextInt(0, pop.size()-1);
			parents.add(pop.remove(index));
		}
	}

}
