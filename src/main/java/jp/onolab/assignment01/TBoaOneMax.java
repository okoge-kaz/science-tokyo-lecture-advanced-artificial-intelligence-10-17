package jp.onolab.assignment01;

import java.io.IOException;
import java.util.List;

import jp.onolab.boa.TBoa;
import jp.onolab.boa.TIndividual;
import jp.onolab.discreteFunctions.IDiscreteFunction;
import jp.onolab.discreteFunctions.TOneMaxFunction;
import jp.onolab.random2013.ICRandom;
import jp.onolab.random2013.TCJava48BitLcg;

/**
 * TBoaOneMax solves the 30-dimensional one-max function by BOA.
 * @author isao
 *
 */
public class TBoaOneMax {

	/**
	 * Evaluates the population
	 * @param population the population
	 * @param objective the objective function
	 */
	private static void evaluate(List<TIndividual> population, IDiscreteFunction objective) {
        for(TIndividual indiv : population) {
            double eval = objective.evaluate(indiv.getX());
            indiv.setEvaluationValue(eval);
        }
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
			                       int populationSize, int samplingSize, int maxNumOfEvals, ICRandom random) {
        TBoa boa = new TBoa(objective.getCategory(), populationSize, random); //Creates a BOA object.
        List<TIndividual> population = boa.initialize(); // Generates an initial population.
        evaluate(population, objective); // Evaluates the initial population.
        while (boa.getNumOfEvaluations() < maxNumOfEvals && boa.getBestIndividual().getEvaluationValue() > optimum) {
            boa.update(population); // Updates the probability model.
            population = boa.samplePopulation(samplingSize); // Generates a population according to the probability model.
            evaluate(population, objective); // Evaluates the population.
        }
        if (boa.getBestIndividual().getEvaluationValue() == optimum) {
            System.out.println("Trial No.: " + trialNo 
            		           + ", #Evaluations: " + boa.getNumOfEvaluations() 
            		           + ", Best: " + boa.getBestIndividual().getEvaluationValue()
            		           + ", Sccuess: true");
        } else {
            System.out.println("Trial No.: " + trialNo 
 		           + ", #Evaluations: " + boa.getNumOfEvaluations() 
 		           + ", Best: " + boa.getBestIndividual().getEvaluationValue()
 		           + ", Sccuess: false");        	
        }
    }

	/**
	 * Main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
        int populationSize = 160; // Population size
        int candidatePopulationSize = 80; // Sampling size
        int maxNumOfEvals = 10000; // The maximum number of evaluatinos
        int dim = 30; // Dimension
        double optimum = -30.0; // The optimum
        int trials = 3; // The number of trials
        long seed = 1; // Random Seed
        ICRandom random = new TCJava48BitLcg(seed); //Random number generator
        IDiscreteFunction objective = new TOneMaxFunction(dim); //One-max function
        for(int trial = 0; trial < trials; ++trial) {
            doOneTrial(trial, objective, optimum, populationSize, candidatePopulationSize, maxNumOfEvals, random); //Performs one trial.
        }
    }
}
