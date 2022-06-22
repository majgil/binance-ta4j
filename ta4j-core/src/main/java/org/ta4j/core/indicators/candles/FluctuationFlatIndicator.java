package org.ta4j.core.indicators.candles;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

/**
 * No fluctuacion
 */
public class FluctuationFlatIndicator extends CachedIndicator<Boolean> {

	public final static String MODE_EQUAL_OHLC = "OHLC";
	public final static String MODE_EQUAL_OC = "OC";
	
	public String mode;
	
    /**
     * Constructor.
     *
     * @param series a bar series
     */
    public FluctuationFlatIndicator(BarSeries series, String mode) {
        super(series);
        this.mode = mode;
    }

    @Override
    protected Boolean calculate(int index) {
    	
    	Boolean resultado = null;
    	
    	if (this.mode == null) {
    		this.mode = FluctuationFlatIndicator.MODE_EQUAL_OHLC;
    	}
    	
        if (index < 1) {
            return false;
        }
        Bar currBar = getBarSeries().getBar(index);
        
        final Num currHighPrice = currBar.getHighPrice();
        final Num currLowPrice = currBar.getLowPrice();
        final Num currOpenPrice = currBar.getOpenPrice();
        final Num currClosePrice = currBar.getClosePrice();
        
        if (FluctuationFlatIndicator.MODE_EQUAL_OHLC.equals(this.mode)) { // Debe ser igual OHLC
        	resultado = currOpenPrice.isEqual(currHighPrice) && currOpenPrice.isEqual(currLowPrice)
        			    && currClosePrice.isEqual(currOpenPrice) && currClosePrice.isEqual(currClosePrice);
        	
        } else if (FluctuationFlatIndicator.MODE_EQUAL_OC.equals(this.mode)) { // Sólo debe ser igual OC
        	resultado = currClosePrice.isEqual(currOpenPrice) && currClosePrice.isEqual(currClosePrice);
        	
        } else { // Cualquier otro valor se tomará modo estricto y debe ser igual OHLC
        	resultado = currOpenPrice.isEqual(currHighPrice) && currOpenPrice.isEqual(currLowPrice)
    			    && currClosePrice.isEqual(currOpenPrice) && currClosePrice.isEqual(currClosePrice);        	
        }
        
        return resultado;
        
        
    }
}
