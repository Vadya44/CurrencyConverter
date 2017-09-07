package vadyaprod.currencyconverter.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;

import android.net.ConnectivityManager;
import java.io.*;
import vadyaprod.currencyconverter.model.CurrencyList;
import vadyaprod.currencyconverter.model.Valute;
import vadyaprod.currencyconverter.other.Parser;
import vadyaprod.currencyconverter.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private String filePath;
    private Spinner spinnerFrom, spinnerTo;
    private List<Valute> valuteslist;

    /**
     * Downloading xml file (if we can)
     * If we can't - will be used file from cache
     */
    public void downloadFile() {
        filePath = "cacheXML.xml";
        if (!isInternetAvailable())
            downloadFailed();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
                        URLConnection connection = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openConnection();
                        connection.connect();
                        InputStream stream = connection.getInputStream();
                        if (stream == null) {
                            Log.e("mad", "Unable to create InputStream for mediaUrl: " + url);
                        }
                        FileOutputStream fos = openFileOutput(filePath, Context.MODE_PRIVATE);

                        byte buf[] = new byte[16 * 1024];
                        do {
                            int numread = stream.read(buf);
                            if (numread <= 0) {
                                break;
                            } else {
                                fos.write(numread);
                            }
                        } while (true);
                        fos.close();
                    } catch (Exception e) {
                        Log.v("parser", e.toString() + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            });


            thread.start();

    }

    public boolean isInternetAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /**
     * Getting last downloaded file
     * @return xml file with values of valutes
     */
    private File getFileFromDir() {
        return new File(MainActivity.this.getCacheDir(), filePath);
    }


    /**
     * Message about fail
     */
    private void downloadFailed() {
        Toast toast = Toast.makeText(getApplicationContext(), "Failed to upload data", Toast.LENGTH_LONG);
        toast.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateValutes();
        addItemsOnSpinnerFrom();
        addItemsOnSpinnerTo();
        addListenerOnButton();
        downloadFile();

        //Parser parser = new Parser();
        //CurrencyList list = parser.getCurrencyList(getFileFromDir());
        //Log.v("list", list.getCurrencies().toString());
    }

    /**
     * Spinner
     */
    public void addItemsOnSpinnerFrom() {
        spinnerFrom = findViewById(R.id.first_currency_name);
        List<String> list = new ArrayList<String>();
        for (Valute v : valuteslist)
        {
            list.add(v.getCharCode());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(dataAdapter);
    }


    /**
     * Spinner
     */
    public void addItemsOnSpinnerTo() {
        spinnerTo = findViewById(R.id.second_currency_name);
        List<String> list = new ArrayList<String>();
        for (Valute v : valuteslist)
        {
            list.add(v.getCharCode());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(dataAdapter);
    }


    /**
     * Clicking for convert button
     */
    public void addListenerOnButton() {
        Button btn = findViewById(R.id.button_convert);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                solveResult();
            }

        });
    }

    /**
     * Result showing method
     * @param stringRes - string for showing
     */
    public void showResult(String stringRes) {
        TextView textViewResult = findViewById(R.id.text_result);
        textViewResult.setText(stringRes);
    }

    /**
     * Counting result value in second valute
     */
    public void solveResult(){
        String result = "";
        double firstnum = getFirstNumber();
        double firstCurrency = getFirstValue();
        double secondCurrency = getSecondValue();
        Double dResult = firstnum * secondCurrency / firstCurrency;
        result = String.format("%.3f%n", dResult);
        showResult(result);
    }

    /**
     *
     * @return inputed value for convertation
     */
    public double getFirstNumber() {
        EditText editText = findViewById(R.id.first_currency_number);
        return Double.parseDouble(editText.getText().toString());
    }


    /**
     *
     * @return value of first choosed valute
     */
    public double getFirstValue() {
        String charCode = spinnerFrom.getSelectedItem().toString();
        for (Valute v : valuteslist)
        {
            if (v.getCharCode().equals(charCode))
                return v.getValue();
        }
        return 0.0;
    }

    /**
     *
     * @return value of second choosed valute
     */
    public double getSecondValue() {
        String charCode = spinnerTo.getSelectedItem().toString();
        for (Valute v : valuteslist)
        {
            if (v.getCharCode().equals(charCode))
                return v.getValue();
        }
        return 0.0;
    }

    /**
     * Generating Valutes for testing application
     */
    public void generateValutes(){
        valuteslist = new ArrayList<Valute>();
        Valute usd = new Valute("USD", 60.0);
        Valute euro = new Valute("EURO", 70.1);
        Valute gbp = new Valute("GBP", 74.7);
        valuteslist.add(usd);
        valuteslist.add(euro);
        valuteslist.add(gbp);
    }
}
