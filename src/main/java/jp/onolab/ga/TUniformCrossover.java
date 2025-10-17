package jp.onolab.ga;

import jp.onolab.cloneableArrayList2019.TCCloneableArrayList;
import jp.onolab.random2013.ICRandom;
import jp.onolab.random2013.TCJava48BitLcg;

public class TUniformCrossover {
	
	private ICRandom fRandom;
	
	public TUniformCrossover(ICRandom rand) {
		fRandom = rand;
	}
	
	public int getNoOfParents() {
		return 2;
	}
	
	/**
	 * 子個体集合offspring中の個体のサンプリングを行う．
	 * @param parents 親子体集合
	 * @param offspring 子個体集合
	 */
	public void sampleOffspring(TCCloneableArrayList<TIndividual> parents, TCCloneableArrayList<TIndividual> offspring) {
		if (parents.size() != 2) {
			throw new RuntimeException("Error: The size of offspring must be 2.");
		}
		if (offspring.size() % 2 != 0) {
			throw new RuntimeException("Error: The size of offspring must be an even number.");
		}
		TBitString parent1 = parents.get(0).getBitString();
		TBitString parent2 = parents.get(1).getBitString();
		for (int i = 0; i < offspring.size() / 2; ++i) {
			TBitString kid1 = offspring.get(2 * i).getBitString();
			TBitString kid2 = offspring.get(2 * i + 1).getBitString();
			crossover(parent1, parent2, kid1, kid2);
		}
	}
	
	private void crossover(TBitString parent1, TBitString parent2, TBitString kid1, TBitString kid2) {
		kid1.copyFrom(parent1);
		kid2.copyFrom(parent2);
		for (int i = 0; i < parent1.getLength(); ++i) {
			if (fRandom.nextBoolean()) {
				boolean tmp = kid1.get(i);
				kid1.set(i, kid2.get(i));
				kid2.set(i, tmp);
			}
		}		
	}

	public static void main(String[] args) {
		ICRandom rand = new TCJava48BitLcg();
		TCCloneableArrayList<TIndividual> parents = new TCCloneableArrayList<TIndividual>(new TIndividual()).resize(2);
		parents.get(0).getBitString().fromString("11111111");
		parents.get(1).getBitString().fromString("00000000");
		TCCloneableArrayList<TIndividual> offspring = new TCCloneableArrayList<TIndividual>(new TIndividual()).resize(4);
		TUniformCrossover crossover = new TUniformCrossover(rand);
		crossover.sampleOffspring(parents, offspring);
		System.out.println(offspring);
	}

}
