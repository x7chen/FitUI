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

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/16.
 */
public class BarChart {
    private Context context;

    public BarChart(Context context) {
        this.context = context;
    }

    public View execute(Context context) {
        this.context = context;
        return ChartFactory
                .getBarChartView(context, getBarDemoDataset(), getBarDemoRenderer(), Type.DEFAULT);
    }

    public View UpdateBarChar(int[] data) {
        return ChartFactory
                .getBarChartView(context, getBarDataset(data), getBarDemoRenderer(), Type.DEFAULT);
    }

    private XYMultipleSeriesDataset getBarDataset(int[] data) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        CategorySeries series = new CategorySeries("步数");
        for (int d : data) {
            series.add(d);
        }
        dataset.addSeries(series.toXYSeries());

        return dataset;
    }

    /**
     * XYMultipleSeriesDataset 类型的对象，用于提供图表需要表示的数据集，
     * 这里我们用 getBarDemoDataset 来得到它。
     */
    private XYMultipleSeriesDataset getBarDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 96;
        Random r = new Random();

        CategorySeries series = new CategorySeries("步数");
        for (int k = 0; k < nr; k++) {
            series.add(100 + r.nextInt() % 50);
        }
        dataset.addSeries(series.toXYSeries());

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
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.TRANSPARENT);
        renderer.setMarginsColor(1);
        int[] margins = {0, 100, 0, 0};
        renderer.setMargins(margins);
        renderer.setLegendHeight(1);
        renderer.setFitLegend(true);
        renderer.setZoomEnabled(false, false);
        renderer.setPanEnabled(true, false);
        renderer.setBarWidth(30F);
        setChartSettings(renderer);
        return renderer;
    }

    class TimeLabel extends NumberFormat {
        public TimeLabel() {

        }

        @Override
        public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) {
            StringBuffer stringBuffer = new StringBuffer();
            if (value > 0) {
                stringBuffer.append(String.format("%02d", ((int) (value - 1) / 4)));
                stringBuffer.append(":");
                stringBuffer.append(String.format("%02d", ((int) (value - 1) % 4) * 15));
            } else {
                value += 96;
                stringBuffer.append(String.format("%02d", ((int) (value - 1) / 4)));
                stringBuffer.append(":");
                stringBuffer.append(String.format("%02d", ((int) (value - 1) % 4) * 15));
            }
            return stringBuffer;
        }

        @Override
        public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.format("%02d", ((value - 1) / 4)));
            stringBuffer.append(":");
            stringBuffer.append(String.format("%02d", ((value - 1) % 4) * 15));
            return stringBuffer;
        }

        @Override
        public Number parse(String string, ParsePosition position) {
            return null;
        }
    }

    /**
     * setChartSettings 方法设置了下坐标轴样式。
     */
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int Xmax = hour * 4 + (minute / 15);
        int Xmin;
        if (Xmax > 24) {
            if (Xmax % 2 == 0) {
                Xmax = Xmax + 2;
                Xmin = Xmax - 24;
            } else {
                Xmax = Xmax + 3;
                Xmin = Xmax - 24;
            }
        } else {
            Xmin = 0;
            Xmax = 24;
        }
        renderer.setAxisTitleTextSize(50);
        renderer.setLegendTextSize(50);
        renderer.setChartTitleTextSize(50);
        renderer.setPointSize(50);
        renderer.setLabelsTextSize(30);
        renderer.setXLabelsColor(Color.GRAY);
        renderer.setYLabelsPadding(50);
        renderer.setXLabelsPadding(20);
        renderer.setXLabelsAngle(300);
        renderer.setXLabelFormat(new TimeLabel());
        renderer.setXAxisColor(Color.BLUE);
        renderer.setXAxisMin(Xmin);
        renderer.setXAxisMax(Xmax);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(200);
        renderer.setXLabels(26);
        double[] pan = {0, 97, 0, 200};
        renderer.setPanLimits(pan);
        renderer.setShowLegend(false);
    }
}
