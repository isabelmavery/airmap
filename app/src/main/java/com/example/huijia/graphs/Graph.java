package com.example.huijia.graphs;

/**
 * Created by Huijia on 11/20/16.
 */
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        String temp = System.getProperty("user.dir");
        //temp);
//
        String csvFile = ("shit.csv");
        File f = new File(csvFile);
        try{
            System.out.println(f.getCanonicalPath());

        }
        catch(Exception e){

        }

//        URL path = getClass().getResource("/main/java/shit.csv");

//        File f = new File(path.getFile());

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int[] time = new int[10];
        int count = 0;



        try {
            count+=3;
            System.out.println(count);
            br = new BufferedReader(new FileReader(f));
            System.out.println(count+ "build/intermediates/exploded-aar/com.android.support/support-v4/25.0.1/res");

            count += 2;
            while ((line = br.readLine()) != null) {

                String[] current = line.split(cvsSplitBy);
                time[count] = Integer.parseInt(current[0]);
                time[count+1] = Integer.parseInt(current[1]);
                count += 2;



            }

        } catch (Exception e) {
            System.out.println(count+"excpetion");

            e.printStackTrace();
        }



        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                new DataPoint(0,count),
                new DataPoint(1,count),
                //new DataPoint(time[2],time[3]),
                //new DataPoint(time[4],time[5]),
                //new DataPoint(time[6],time[7]),
                //new DataPoint(8,9)

        });
        System.out.println("graph");
        graph.addSeries(series);




    }
}

