package vadyaprod.currencyconverter.model;

/**
 * Created by Vadya on 06.09.17.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.*;

@Root(name = "ValCurs")
public class CurrencyList {


        @Element(name ="Date", required = false)
        private String date;


        @Element(name ="name", required = false)
        private String name;


        @ElementList(required = true, inline = true)
        private List<Valute> currencies;


        public CurrencyList() {
        }

        public List<Valute> getCurrencies() {
            return currencies;
        }


}
