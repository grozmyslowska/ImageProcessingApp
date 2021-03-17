package histogram;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HistogramG extends JFrame {

    public HistogramG(BufferedImage image) {

        int[] histogramGreen = new int[256];

        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                histogramGreen[color.getGreen()]++;
            }
        }

        var seriesG = new XYSeries("G");

        for(int i=0;i<=255;i++){
            seriesG.add(i,histogramGreen[i]);
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(seriesG);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);

        renderer.setSeriesPaint(0, Color.GREEN);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Histogram Green",
                "Intensywność",
                "Ilość pikseli",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);

        add(chartPanel);
        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setVisible(true);
    }
}