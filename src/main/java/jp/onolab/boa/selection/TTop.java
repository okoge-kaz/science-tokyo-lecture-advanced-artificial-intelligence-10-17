package jp.onolab.boa.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.onolab.boa.TIndividual;


/**
 * 上位選択
 * @author shimazu
 */
public class TTop extends TAbstractSelection {
    /** シリアライズ用のid */
    private static final long serialVersionUID = 1L;

    public TTop(double selectionRate) {
        super(selectionRate);
    }

    @Override
    public List<TIndividual> select(List<TIndividual> population) {
        Collections.sort(population);
        int numOfSelect = (int)(population.size() * fSelectionRate);
        List<TIndividual> selected = new ArrayList<>(numOfSelect);
        for(int i = 0; i < numOfSelect; ++i) {
            // どっちが良いか？
            selected.add(population.get(i));
            // selected.add(population.get(i).clone());
        }
        return selected;
    }
}
