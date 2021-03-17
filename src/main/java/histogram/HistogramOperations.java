package histogram;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class HistogramOperations {

    public static List<int[]> calculateHistograms(BufferedImage image) {
        int[] histogramRed = new int[256];
        int[] histogramGreen = new int[256];
        int[] histogramBlue = new int[256];

        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                Color color = new Color(image.getRGB(w, h));
                histogramRed[color.getRed()]++;
                histogramGreen[color.getGreen()]++;
                histogramBlue[color.getBlue()]++;
            }
        }

        List<int[]> histograms = new ArrayList<>();
        histograms.add(histogramRed);
        histograms.add(histogramGreen);
        histograms.add(histogramBlue);

        return histograms;
    }

    public static void showHistogramRGB(BufferedImage image){
        HistogramRGB histogramRGB = new HistogramRGB(image);
    }
    public static void showHistogramRGB3(BufferedImage image){
        HistogramRGB3 histogramRGB3 = new HistogramRGB3(image);
    }
    public static void showHistogramR(BufferedImage image){
        HistogramR histogramR = new HistogramR(image);
    }
    public static void showHistogramG(BufferedImage image){
        HistogramG histogramG = new HistogramG(image);
    }
    public static void showHistogramB(BufferedImage image){
        HistogramB histogramB = new HistogramB(image);
    }

    public static BufferedImage rozciaganieHistograms(BufferedImage img){

        List<int[]> histograms = HistogramOperations.calculateHistograms(img);

        int[] histogramRed = histograms.get(0);
        int[] histogramGreen = histograms.get(1);
        int[] histogramBlue = histograms.get(2);

        int kMinRed = 255;
        int kMaxRed = 0;
        int kMinGreen = 255;
        int kMaxGreen = 0;
        int kMinBlue = 255;
        int kMaxBlue = 0;

        for(int i=0 ; i<256; i++)
            if (histogramRed[i] != 0) {
                kMinRed = i;
                break;
            }

        for(int i=255 ; i>=0; i--)
            if (histogramRed[i] != 0) {
                kMaxRed = i;
                break;
            }

        for(int i=0 ; i<256; i++)
            if (histogramGreen[i] != 0) {
                kMinGreen = i;
                break;
            }

        for(int i=255 ; i>=0; i--)
            if (histogramGreen[i] != 0) {
                kMaxGreen = i;
                break;
            }

        for(int i=0 ; i<256; i++)
            if (histogramBlue[i] != 0){
                kMinBlue = i;
                break;
            }

        for(int i=255 ; i>=0; i--)
            if (histogramBlue[i] != 0) {
                kMaxBlue = i;
                break;
            }

        int[] LUTRed = new int[256];
        int[] LUTGreen = new int[256];
        int[] LUTBlue = new int[256];

        for(int i=0 ; i<256; i++) {
            LUTRed[i] = (i - kMinRed) * 255 / (kMaxRed - kMinRed);
            LUTGreen[i] = (i - kMinGreen) * 255 / (kMaxGreen - kMinGreen);
            LUTBlue[i] = (i - kMinBlue) * 255 / (kMaxBlue - kMinBlue);
        }

        for(int i = 0; i< img.getWidth(); i ++){
            for (int j = 0; j< img.getHeight(); j++){
                Color c = new Color(img.getRGB(i,j));
                img.setRGB(i,j,new Color(LUTRed[c.getRed()], LUTGreen[c.getGreen()], LUTBlue[c.getBlue()]).getRGB());
            }
        }
        return img;
    }

    public static BufferedImage wyrownanieHistograms(BufferedImage img){

        List<int[]> histograms = HistogramOperations.calculateHistograms(img);

        int[] histogramRed = histograms.get(0);
        int[] histogramGreen = histograms.get(1);
        int[] histogramBlue = histograms.get(2);

        double[] DRed = new double[256];
        double[] DGreen = new double[256];
        double[] DBlue = new double[256];

        DRed[0]=histogramRed[0];
        DGreen[0]=histogramGreen[0];
        DBlue[0]=histogramBlue[0];

        for (int i=1;i<=255;i++){
            DRed[i]=DRed[i-1]+histogramRed[i];
            DGreen[i]=DGreen[i-1]+histogramGreen[i];
            DBlue[i]=DBlue[i-1]+histogramBlue[i];
        }

        double n = img.getHeight()*img.getWidth();

        int[] LUTRed = new int[256];
        int[] LUTGreen = new int[256];
        int[] LUTBlue = new int[256];

        for(int i=0 ; i<256; i++) {
            LUTRed[i] = (int) ((DRed[i]/n - DRed[0]/n) / (1 - DRed[0]/n) * 255);
            LUTGreen[i] = (int) ((DGreen[i]/n - DGreen[0]/n) / (1 - DGreen[0]/n) * 255);
            LUTBlue[i] = (int) ((DBlue[i]/n - DBlue[0]/n) / (1 - DBlue[0]/n) * 255);
        }

        for(int i = 0; i< img.getWidth(); i ++){
            for (int j = 0; j< img.getHeight(); j++){
                Color c = new Color(img.getRGB(i,j));
                img.setRGB(i,j,new Color(LUTRed[c.getRed()], LUTGreen[c.getGreen()], LUTBlue[c.getBlue()]).getRGB());
            }
        }

        return img;
    }
}
