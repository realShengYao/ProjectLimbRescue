package com.example.projectlimbrescue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String LEFT_LIMB_X = "left_limb_x";
    public static final String LEFT_LIMB_Y = "left_limb_y";

    public static final String RIGHT_LIMB_X = "right_limb_x";
    public static final String RIGHT_LIMB_Y = "right_limb_y";

    private static final double NANO_TO_SECONDS = 1000000000.0;

    // x values are float since we convert the time in nanoseconds to time since first reading
    private List<Double> leftLimbX;
    private List<Double> leftLimbY;

    private List<Double> rightLimbX;
    private List<Double> rightLimbY;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rightLimbX = new ArrayList<>();
            rightLimbY = new ArrayList<>();
            leftLimbX = new ArrayList<>();
            leftLimbY = new ArrayList<>();

            long[] rightX = getArguments().getLongArray(RIGHT_LIMB_X);
            double[] rightY = getArguments().getDoubleArray(RIGHT_LIMB_Y);

            long[] leftX = getArguments().getLongArray(LEFT_LIMB_X);
            double[] leftY = getArguments().getDoubleArray(LEFT_LIMB_Y);

            // convert arrays to ArrayList (I can't find a better way of doing this)
            // also have x start from 0
            long rightStart = rightX[0];
            for (int i = 0; i < rightX.length; i++) {
                rightLimbX.add((rightX[i] - rightStart) / NANO_TO_SECONDS);
                rightLimbY.add(rightY[i]);
            }

            long leftStart = leftX[0];
            for (int i = 0; i < leftX.length; i++) {
                leftLimbX.add((leftX[i] - leftStart) / NANO_TO_SECONDS);
                leftLimbY.add(leftY[i]);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_graph, container, false);

        XYPlot plot = v.findViewById(R.id.plot);

        // initialize the plot

        PanZoom.attach(plot);

        // turn the above arrays into XYSeries
        XYSeries rightLimb = new SimpleXYSeries(rightLimbX, rightLimbY, "Right Limb PPG");
        XYSeries leftLimb = new SimpleXYSeries(leftLimbX, leftLimbY, "Left Limb PPG");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter leftLimbFormat =
                new LineAndPointFormatter(getContext(), R.xml.line_point_formatter);

        LineAndPointFormatter rightLimbFormat = new LineAndPointFormatter(getContext(),
                R.xml.right_limb_point_formatter);

        // add a new series' to the xyplot:
        plot.addSeries(rightLimb, rightLimbFormat);
        plot.addSeries(leftLimb, leftLimbFormat);

        // display more decimals on range
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT);


        return v;
    }
}