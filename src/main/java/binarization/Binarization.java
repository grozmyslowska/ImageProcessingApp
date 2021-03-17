package binarization;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public abstract class Binarization {

    public static BufferedImage manualBinarization(BufferedImage img, int threshold) {

        int[] LUT = new int[256];

        for (int i=0; i<256; i++){
            LUT[i] = i <= threshold ? 0 : 255;
        }

        for(int w = 0; w < img.getWidth(); w++){
            for (int h = 0; h < img.getHeight(); h++){
                Color c = new Color(img.getRGB(w,h));
                int red = LUT[c.getRed()];
                img.setRGB(w,h,new Color(red,red,red).getRGB());
            }
        }

        return img;
    }

    public static BufferedImage binarizationOtsu (BufferedImage img){

        int[] histogram = new int[256];

        for(int w = 0; w < img.getWidth(); w++){
            for (int h = 0; h < img.getHeight(); h++){
                Color c = new Color(img.getRGB(w,h));
                histogram[c.getRed()]++;
            }
        }

        int N = img.getHeight()*img.getWidth();

        double[] p = new double[256];
        for(int i=0;i<256;i++){
            p[i]= (double) histogram[i] / N;
        }

        int threshold = 0;
        double Vw = Double.POSITIVE_INFINITY;

        for(int t=1; t<256; t++){

            double wb = 0;
            double ob = 0;
            double srArytb = 0;
            int Nb = 0;

            for(int i=0; i<t; i++){
                wb += p[i];
                srArytb += histogram[i]*i;
                Nb += histogram[i];
            }
            srArytb /= Nb;

            for(int i=0; i<t; i++){
                ob += Math.pow(i - srArytb, 2) / wb * p[i];
            }

            double wf = 0;
            double of = 0;
            double srArytf = 0;
            int Nf = 0;

            for(int i=t; i<256; i++){
                wf += p[i];
                srArytf += histogram[i]*i;
                Nf += histogram[i];
            }
            srArytf /= Nf;

            for(int i=t; i<256; i++){
                of += Math.pow(i - srArytf, 2) / wf * p[i];
            }

            double newVw = wf * of + wb * ob;

            if(newVw < Vw){
                threshold = t;
                Vw = newVw;
            }
        }

        int[] LUT = new int[256];

        for (int i=0; i<256; i++){
            LUT[i] = i <= threshold ? 0 : 255;
        }

        for(int w = 0; w < img.getWidth(); w++){
            for (int h = 0; h < img.getHeight(); h++){
                Color c = new Color(img.getRGB(w,h));
                int red = LUT[c.getRed()];
                img.setRGB(w,h,new Color(red,red,red).getRGB());
            }
        }
        return img;
    }
}
