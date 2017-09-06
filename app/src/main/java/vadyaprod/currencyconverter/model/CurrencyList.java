package vadyaprod.currencyconverter.model;

/**
 * Created by Vadya on 06.09.17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.*;

@Root(name = "root")
public class CurrencyList {

        @ElementList(name = "currencies", inline = true)
        private List<Currency> currencies;

        @Element(name = "message", required = false)
        private String message;

        public CurrencyList() {
        }

        public List<Currency> getCurrencies() {
            return currencies;
        }


}
