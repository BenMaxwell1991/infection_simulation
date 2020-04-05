package com.maxwell.charts;


import com.maxwell.data.Results;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

import javax.swing.*;
import java.awt.*;

public class MyGraphDrawer {

    Double[] S;
    Double[] I;
    Double[] R;

    XYSeriesCollection dataset = new XYSeriesCollection();

    public MyGraphDrawer(Results chartData) {

        S = chartData.getS();
        I = chartData.getI();
        R = chartData.getR();

        initUI();
    }

    private void initUI() {

//        XYDataset dataset_S = createDataSet(S, "Susceptible");
//        XYDataset dataset_I = createDataSet(I, "Infected");
//        XYDataset dataset_R = createDataSet(R, "Recovered");
        dataset.addSeries(createDataSet(S, "Susceptible"));
        dataset.addSeries(createDataSet(I, "Infected"));
        dataset.addSeries(createDataSet(R, "Recovered"));
//        XYDataset[] dataArray = {dataset_S, dataset_I, dataset_R};

        JFreeChart chart = createChart(null);
        JFrame frame = new JFrame();

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        frame.add(chartPanel);

        frame.pack();
        frame.setTitle("Line chart");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    public XYSeries createDataSet(Double[] xyData, String key) {

        XYSeries series = new XYSeries(key);
        for (int i = 0; i < xyData.length/2; i = i + 2) {
            series.add(xyData[i], xyData[i+1]);
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return series;
    }

    public void drawDataSet(XYDataset someData) {

    }


    private JFreeChart createChart(XYDataset[] filler) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Viral Spread Within a Population",
                "Time",
                "Percentage of Population (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        plot.getRenderer().setSeriesStroke(1, new BasicStroke(2.0f));
        plot.getRenderer().setSeriesStroke(2, new BasicStroke(2.0f));

//        renderData(plot, dataset[0], Color.red);
//        renderData(plot, dataset[1], Color.green);
//        renderData(plot, dataset[2], Color.blue);

        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Viral Spread Within a Population",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }

    private void renderData(XYPlot p, XYDataset xy, Color c) {
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, c);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        p.setDataset(xy);
        p.setRenderer(renderer);
    }
}