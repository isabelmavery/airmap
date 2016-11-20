package com.example.huijia.graphs;

/**
 * Created by Huijia on 11/20/16.
 */
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.net.URL;

public class Graph extends Activity {
    EditText eText;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        b1 = (Button) findViewById(R.id.button1);
        eText = (EditText) findViewById(R.id.editText2);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String zipcode = eText.getText().toString();
                makeGraph();
            }
        });


//        String temp = System.getProperty("user.dir");
//        //temp);
////
////        String csvFile = ("./shit.csv");
////        File f = new File(csvFile);
//        //File f = new File(getClass().getClassLoader().getResource("webservices.xml").toURI()););
//        URL path = getClass().getResource("shit.csv");
//
//        File f = new File(path.getFile());
//        try{
//            System.out.println(f.getCanonicalPath());
//
//        }
//        catch(Exception e){
//
//        }
//
//
//
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//        int[] time = new int[10];
//        int count = 0;
//
//
//
//        try {
//            count+=3;
//            System.out.println(count);
//            br = new BufferedReader(new FileReader(f));
//            System.out.println(count+ "build/intermediates/exploded-aar/com.android.support/support-v4/25.0.1/res");
//
//            count += 2;
//            while ((line = br.readLine()) != null) {
//
//                String[] current = line.split(cvsSplitBy);
//                time[count] = Integer.parseInt(current[0]);
//                time[count+1] = Integer.parseInt(current[1]);
//                count += 2;
//
//
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(count+"excpetion");
//
//            e.printStackTrace();
//        }
//







    }

    private int makeGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                new DataPoint(2007, 70),
                new DataPoint(2008, 76),
                new DataPoint(2009, 80),
                new DataPoint(2010, 82),
                new DataPoint(2011, 85),
                new DataPoint(2012, 90),
                new DataPoint(2013, 103),
                new DataPoint(2014, 119),
                new DataPoint(2015, 130),
                new DataPoint(2016, 147),
        });
        System.out.println("graph");
        graph.addSeries(series);
        return 0;
    }
}

