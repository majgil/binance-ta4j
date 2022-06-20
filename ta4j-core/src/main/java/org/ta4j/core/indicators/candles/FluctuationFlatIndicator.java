package org.ta4j.core.indicators.candles;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

/**
 * No fluctuacion
 */
public class FluctuationFlatIndicator extends CachedIndicator<Boolean> {

    /**
     * Constructor.
     *
     * @param series a bar series
     */
    public FluctuationFlatIndicator(BarSeries series) {
        super(series);
    }

    @Override
    protected Boolean calculate(int index) {
        if (index < 1) {
            return false;
        }
        Bar currBar = getBarSeries().getBar(index);
        
        final Num currHighPrice = currBar.getHighPrice();
        final Num currLowPrice = currBar.getLowPrice();
        final Num currOpenPrice = currBar.getOpenPrice();
        final Num currClosePrice = currBar.getClosePrice();
        
        return currOpenPrice.isEqual(currHighPrice) && currOpenPrice.isEqual(currLowPrice)
               && currClosePrice.isEqual(currOpenPrice) && currClosePrice.isEqual(currClosePrice);
        
        
    }
}
