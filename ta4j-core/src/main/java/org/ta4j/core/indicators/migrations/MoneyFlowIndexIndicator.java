/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2023 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.indicators.migrations;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.num.Num;

/**
 * The Money Flow Index (MFI) indicator.
 *
 * @see <a href=
 *      "https://school.stockcharts.com/doku.php?id=technical_indicators:money_flow_index_mfi</a>
 */
public class MoneyFlowIndexIndicator extends CachedIndicator<Num> {

    private final TypicalPriceIndicator typicalPriceIndicator;

    private final int barCount;

    public MoneyFlowIndexIndicator(BarSeries series,  int barCount) {
        super(series);
        this.barCount = barCount;
        this.typicalPriceIndicator = new TypicalPriceIndicator(series);

    }

    @Override
    protected Num calculate(int index) {
    	
    	Num sumRawMoneyFlowPositive = zero();
    	Num sumRawMoneyFlowNegative = zero();
    	Num ratioFlowIndex = zero();
    	Num moneyFlowIndex = zero();
    	Bar currBar = null;
    	
        if (index < this.barCount) {
            return zero();
        }

        int startIndex = Math.max(0, index - barCount + 1);

        for (int i = startIndex; i <= index; i++) {
        	
        	currBar = getBarSeries().getBar(i);
        	
        	// TODO - Zero volume and neither bullish nor bearish
        	//...
        	
        	if (currBar.isBullish()) {
        	  sumRawMoneyFlowPositive = sumRawMoneyFlowPositive.plus(typicalPriceIndicator.getValue(i).multipliedBy(getBarSeries().getBar(i).getVolume()));
        	} else {
        	  sumRawMoneyFlowNegative = sumRawMoneyFlowNegative.plus(typicalPriceIndicator.getValue(i).multipliedBy(getBarSeries().getBar(i).getVolume()));
        	}  
        }     
        
        if (sumRawMoneyFlowPositive != zero() && sumRawMoneyFlowNegative != zero()) {
            ratioFlowIndex = sumRawMoneyFlowPositive.dividedBy(sumRawMoneyFlowNegative);        	
        }
        
        moneyFlowIndex = numOf(100).minus(numOf(100).dividedBy(ratioFlowIndex.plus(numOf(1))));
        
        return moneyFlowIndex;
    }

    @Override
    public int getUnstableBars() {
        return 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }

}
