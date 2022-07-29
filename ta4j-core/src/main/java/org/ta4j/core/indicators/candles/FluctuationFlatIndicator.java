package org.ta4j.core.indicators.candles;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

/**
 * No fluctuacion
 */
public class FluctuationFlatIndicator extends CachedIndicator<Boolean> {

	public static final String MODE_EQUAL_OHLC = "O=H=L=C";
	public static final String MODE_EQUAL_OHLC_PREV = "O=H=L=C_PREV";	
	public static final String MODE_EQUAL_OC = "O=C";
	public static final String MODE_EQUAL_OC_PREV = "O=C_PREV";
	public static final String MODE_EQUAL_OHC_GT_L = "O=H=C>L";
	public static final String MODE_EQUAL_OLC_LT_H = "O=L=C<H";
	
	private String mode;
	
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
    	
        if (index < 2) {
            return false;
        }
        Bar currBar = getBarSeries().getBar(index);
        Bar prevBar = getBarSeries().getBar(index - 1);
        
        final Num currHighPrice = currBar.getHighPrice();
        final Num currLowPrice = currBar.getLowPrice();
        final Num currOpenPrice = currBar.getOpenPrice();
        final Num currClosePrice = currBar.getClosePrice();
        final Num prevHighPrice = prevBar.getHighPrice();
        final Num prevLowPrice = prevBar.getLowPrice();
        final Num prevOpenPrice = prevBar.getOpenPrice();
        final Num prevClosePrice = prevBar.getClosePrice();        
        
        if (FluctuationFlatIndicator.MODE_EQUAL_OHLC.equals(this.mode)) { // Debe ser igual OHLC
        	resultado = currOpenPrice.isEqual(currHighPrice) && currOpenPrice.isEqual(currLowPrice)
    			    && currOpenPrice.isEqual(currClosePrice);
        	
        } else if (FluctuationFlatIndicator.MODE_EQUAL_OHLC_PREV.equals(this.mode)) { // Debe ser igual O=H=L=C_PREV
        	resultado = (currOpenPrice.isEqual(currHighPrice) && currHighPrice.isEqual(currLowPrice)
    			         && currLowPrice.isEqual(currClosePrice))
    			     || (currOpenPrice.isEqual(prevOpenPrice) && currClosePrice.isEqual(prevClosePrice)
         			     && currHighPrice.isEqual(prevHighPrice) && currLowPrice.isEqual(prevLowPrice));        	
    	
        } else if (FluctuationFlatIndicator.MODE_EQUAL_OC.equals(this.mode)) { // Sólo debe ser igual OC
        	resultado = currOpenPrice.isEqual(currClosePrice);
        	
        } else if (FluctuationFlatIndicator.MODE_EQUAL_OC_PREV.equals(this.mode)) { // Sólo debe ser igual OC ó O=Oprev y C=Cprev
        	resultado = (currOpenPrice.isEqual(currClosePrice)) 
        			 || (currOpenPrice.isEqual(prevOpenPrice) && currClosePrice.isEqual(prevClosePrice));
        	
        } else { // Cualquier otro valor se tomará modo estricto y debe ser igual OHLC
        	resultado = currOpenPrice.isEqual(currHighPrice) && currOpenPrice.isEqual(currLowPrice)
    			    && currOpenPrice.isEqual(currClosePrice);      	
        }
        
        return resultado;
        
        
    }
}
