package vadyaprod.currencyconverter.model;

/**
 * Created by Vadya on 06.09.17.
 */
import android.widget.Spinner;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.util.*;

@Root(name = "Valute", strict = false)
public class Valute {
        @Element(name ="ID", required = false)
        private String ID;

        @Element(name = "NumCode")
        private int NumCode;

        @Element(name = "CharCode")
        private String CharCode;

        @Element(name = "Nominal")
        private double Nominal;

        @Element(name = "Name")
        private String Name;

        @Element(name = "Value")
        private double Value;


        public String getName() {
            return Name;
        }

        public double getNominal() {
            return Nominal;
        }

        public String getId() {
            return ID;
        }

        public int getNumCode() {
            return NumCode;
        }

        public String getCharCode() {
            return CharCode;
        }

        public double getValue() {
            return Value;
        }


    /**
     * Constructor is created for tests
     * @param name - charCode for valute
     * @param value - value for valute
     */
    public Valute(String name, double value){
            this.CharCode = name;
            this.Value = value;
        }
}
