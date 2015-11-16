package com.x7chen.dev.fitui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;

/**
 * Created by Administrator on 2015/11/16.
 */
public class BarChart {
    private Context context;

    public View execute(Context context) {
        this.context = context;
        return ChartFactory
                .getBarChartView(context, getBarDemoDataset(), getBarDemoRenderer(), Type.DEFAULT);
    }

    /**
     * XYMultipleSeriesDataset 类型的对象，用于提供图表需要表示的数据集，
     * 这里我们用 getBarDemoDataset 来得到它。
     */
    private XYMultipleSeriesDataset getBarDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            CategorySeries series = new CategorySeries("球队 " + (i + 1));
            for (int k = 0; k < nr; k++) {
                series.add(100 + r.nextInt() % 100);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    /**
     * XYMultipleSeriesRenderer 类型的对象，用于提供图表展现时的一些样式，
     * 这里我们用 getBarDemoRenderer 方法来得到它。
     * getBarDemoRenderer 方法构建了一个 XYMultipleSeriesRenderer 用来设置2个系列各自的颜色
     */
    public XYMultipleSeriesRenderer getBarDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setColor(Color.GREEN);
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setColor(Color.RED);
        r.setDisplayChartValuesDistance(10);
        renderer.addSeriesRenderer(r);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.TRANSPARENT);
        renderer.setMarginsColor(Color.WHITE);
        renderer.setZoomEnabled(false, false);
        renderer.setPanEnabled(false, false);
        renderer.setLabelsTextSize(15);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setBarWidth(20F);
        setChartSettings(renderer);
        return renderer;
    }

    /**
     * setChartSettings 方法设置了下坐标轴样式。
     */
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setChartTitle("战绩分析");
        renderer.setXTitle("横坐标");
        renderer.setYTitle("纵坐标");
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(10.5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(500);
    }
}
