package org.ta4j.core.indicators.helpers;

import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

/**
 * Count the bars that meet the condition
 */
public class CounterIndicator extends CachedIndicator<Num> {

    private final AbstractIndicator<Boolean> indicator;

	/** Counter backwards to make the measurement  */
    private final int barCount;
    

    public CounterIndicator(AbstractIndicator<Boolean> indicator, int barCount) {
        super(indicator);
        this.indicator = indicator;        
        this.barCount = barCount;
    }

    @Override
    protected Num calculate(int index) {

    	Num resultado = indicator.numOf(0);
    	
        int end = Math.max(0, index - barCount + 1);
        
        for (int i = index - 1; i >= end; i--) {

        	if (indicator.getValue(i)) {
        		resultado = resultado.plus(resultado.one());
        	}
        }
        return resultado;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }
    
    @Override
    public int getUnstableBars() {
        return 0;
    }    
}
