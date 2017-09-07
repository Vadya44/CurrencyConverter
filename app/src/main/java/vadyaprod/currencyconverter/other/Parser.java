package vadyaprod.currencyconverter.other;

/**
 * Created by Vadya on 06.09.17.
 */


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import vadyaprod.currencyconverter.model.CurrencyList;
import android.util.Log;
import android.util.Log.*;

public class Parser {

    private CurrencyList currencyList;

    public Parser(){
        currencyList = null;
    }

    /**
     * Parsing file from parameters
     * @param file - xml file for parsing
     */
    private void Parse(File file) {
        try {
            Serializer ser = new Persister();
            currencyList = ser.read(CurrencyList.class, file);
        } catch (Exception e) {
            Log.v("parser", e.toString() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    /**
     * Getting final list
     * @param file - xml file for parsing
     * @return
     */
    public CurrencyList getCurrencyList(File file)
    {
        Parse(file);
        return  currencyList;
    }
}
