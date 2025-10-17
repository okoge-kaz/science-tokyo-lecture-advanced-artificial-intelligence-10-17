package jp.onolab.assignment02;

import java.io.IOException;

import jp.onolab.boa.TCIntMatrix;
import jp.onolab.cloneableArrayList2019.TCCloneableArrayList;
import jp.onolab.discreteFunctions.IDiscreteFunction;
import jp.onolab.discreteFunctions.TDeceptiveTrapFunction;
import jp.onolab.ga.TBitString;
import jp.onolab.ga.TIndividual;
import jp.onolab.ga.TUxMgg;
import jp.onolab.random2013.ICRandom;
import jp.onolab.random2013.TCJava48BitLcg;

/**
 * TUxMggDeceptiveTrap solves the 30-dimensional 3-deceptive function by UX+MGG.
 * @author isao
 *
 */
public class TUxMggThreeDeceptive {

	/**
	 * Converts TBitString to TCIntMatrix.
	 * @param str TBitString type
	 * @param m TCIntmatrix type
	 */
	private static void convertBitStringToIntMatrix(TBitString str, TCIntMatrix m) {
		for (int i = 0; i < m.getDimension(); ++i) {
			if (str.get(i)) {
				m.setValue(i, 1);
			} else {
				m.setValue(i, 0);
			}
		}
	}
	
	/**
	 * Evaluates the population
	 * @param objective the objective function
	 * @param pop the population
	 */
	private static void evaluate(IDiscreteFunction objective, TCCloneableArrayList<TIndividual> pop) {
		TCIntMatrix m = new TCIntMatrix(pop.get(0).getBitString().getLength());
		for (TIndividual ind: pop) {
			convertBitStringToIntMatrix(ind.getBitString(), m);			
			double eval = objective.evaluate(m);
			ind.setEvaluationValue(eval);
			ind.setFeasible(true);
		}
	}
	
	/**
	 * Initializes the population.
	 * @param objective the objective function
	 * @param pop the population
	 * @param rand the random generator
	 */
	private static void initializePopulation(IDiscreteFunction objective, TCCloneableArrayList<TIndividual> pop, ICRandom rand) {
		for (TIndividual ind: pop) {
			ind.getBitString().random(rand);
		}
		evaluate(objective, pop);
	}

	/**
	 * Performs one trial.
	 * @param trialNo the trial number
	 * @param objective the objective function
	 * @param optimum the optimum
	 * @param populationSize the population size
	 * @param samplingSize the sampling size
	 * @param maxNumOfEvals the maximum number of evaluations
	 * @param random a random number generator
	 */
	private static void doOneTrial(int trialNo, IDiscreteFunction objective, double optimum,
			                       int populationSize, int samplingSize, int maxNumOfEvals, ICRandom rand) {
		TUxMgg ga = new TUxMgg(objective.getDimension(), populationSize, samplingSize, rand);
		TCCloneableArrayList<TIndividual> initPop = ga.initialize();
		initializePopulation(objective, initPop, rand);
		int noOfEvaluations = populationSize;
        while (noOfEvaluations < maxNumOfEvals && ga.getBestEvaluationValue() > optimum) {
			TCCloneableArrayList<TIndividual> kids = ga.makeOffspring();
			evaluate(objective, kids);
			noOfEvaluations += samplingSize;
			ga.nextGeneration();
		}
        if (ga.getBestEvaluationValue() == optimum) {
            System.out.println("Trial No.: " + trialNo 
            		           + ", #Evaluations: " + noOfEvaluations
            		           + ", Best: " + ga.getBestEvaluationValue()
            		           + ", Sccuess: true");
        } else {
            System.out.println("Trial No.: " + trialNo 
 		           + ", #Evaluations: " + noOfEvaluations
 		           + ", Best: " + ga.getBestEvaluationValue()
 		           + ", Sccuess: false");        	
        }
	}
	
	/**
	 * Main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
        int populationSize = 1200; // Population size
        int samplingSize = 600; // Sampling size
        int maxNumOfEvals = 3000000; // The maximum number of evaluations
        int dim = 30; // Dimension
        int k = 3; // k
        double optimum = -10.0; // The optimum
        int trials = 3; // The number of trials
        long seed = 1; // Random Seed
        ICRandom random = new TCJava48BitLcg(seed); //Random number generator
        IDiscreteFunction objective = new TDeceptiveTrapFunction(dim, k); //3-deceptive function
        for(int trial = 0; trial < trials; ++trial) {
            doOneTrial(trial, objective, optimum, populationSize, samplingSize, maxNumOfEvals, random); //Performs one trial.
        }
    }
		
}
