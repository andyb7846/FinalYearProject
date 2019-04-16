package com.example.andy.prototype2.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.prototype2.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsActivity extends RootActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Comp1", "Comp2", "Comp3", "Comp4", "Comp5"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 20),
                new DataPoint(1, 50),
                new DataPoint(2, 30),
                new DataPoint(3, 20),
                new DataPoint(4, 60)
        });
        graph.addSeries(series);
        series.setSpacing(50);
        /*
// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });



// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        */
    }
}
