package vadyaprod.currencyconverter.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.telecom.Connection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;
import vadyaprod.currencyconverter.model.CurrencyList;
import vadyaprod.currencyconverter.other.Parser;
import vadyaprod.currencyconverter.R;

public class MainActivity extends AppCompatActivity {

    private String filePath = getApplicationContext().getFilesDir().getPath().toString() + "/cacheXML.xml";
    private Spinner spinnerFrom, spinnerTo;
    private Button btn;

    public void downloadFile() {
        final Context context = this.getApplicationContext();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            File file = new File(context.getCacheDir(),filePath);
                URLConnection connection = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                if (stream == null) {
                    Log.e("mad", "Unable to create InputStream for mediaUrl: "+url);
                }
                FileOutputStream fos = new FileOutputStream(file);

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

    private File getFileFromDir() {
        File file = new File(this.getApplicationContext().getFilesDir(), filePath);
        return file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsOnSpinnerFrom();
        addItemsOnSpinnerTo();
        addListenerOnButton();
        downloadFile();
        Parser parser = new Parser();
        CurrencyList list = parser.getCurrencyList(getFileFromDir());
        Log.v("list", list.getCurrencies().toString());
    }

    public void addItemsOnSpinnerFrom() {

        spinnerFrom = findViewById(R.id.first_currency_name);
        List<String> list = new ArrayList<String>();
        list.add("list 1list2list3");
        list.add("list 2list3");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(dataAdapter);
    }


    public void addItemsOnSpinnerTo() {

        spinnerTo = findViewById(R.id.second_currency_name);
        List<String> list = new ArrayList<String>();
        list.add("list 1list2list3");
        list.add("list 2list3");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(dataAdapter);
    }

    public void addListenerOnButton() {

        btn = findViewById(R.id.button_convert);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinnerFrom.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinnerTo.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

}
