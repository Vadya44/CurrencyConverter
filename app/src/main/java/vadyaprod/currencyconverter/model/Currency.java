package vadyaprod.currencyconverter.model;

/**
 * Created by Vadya on 06.09.17.
 */
import android.widget.Spinner;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.util.*;

@Root(name = "currency", strict = false)
public class Currency {
        @Element(name ="id")
        private String id;

        @Element(name = "numCode")
        private int numCode;

        @Element(name = "charCode")
        private String charCode;

        @Element(name = "nominal")
        private double nominal;

        @Element(name = "name")
        private String name;

        @Element(name = "value")
        private double value;


        public String getName() {
            return name;
        }

        public double getNominal() {
            return nominal;
        }

        public String getId() {
            return id;
        }

        public int getNumCode() {
            return numCode;
        }

        public String getCharCode() {
            return charCode;
        }

        public double getValue() {
            return value;
        }
}
