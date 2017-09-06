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

    private void Parse(File file) {
        try {
            Serializer ser = new Persister();
            currencyList = ser.read(CurrencyList.class, file);
        } catch (Exception e) {
            Log.v("parser", e.toString() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    public CurrencyList getCurrencyList(File file)
    {
        Parse(file);
        return  currencyList;
    }
}
