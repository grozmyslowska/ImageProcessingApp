package brightness;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Brightness {

    public static BufferedImage przyciemnianiePotega(BufferedImage img){
        double C = 0.001;
        double N = 2;

        int[] LUT = new int[256];

        for(int i=0 ; i<256; i++) {
            LUT[i] = (int) (C * Math.pow(i,N));
            if(LUT[i]<0) LUT[i]=0;
            if(LUT[i]>255) LUT[i]=255;
        }

        for(int i = 0; i< img.getWidth(); i ++){
            for (int j = 0; j< img.getHeight(); j++){
                Color c = new Color(img.getRGB(i,j));
                img.setRGB(i,j,new Color(LUT[c.getRed()], LUT[c.getGreen()], LUT[c.getBlue()]).getRGB());
            }
        }
        return img;
    }
    public static BufferedImage rozjasnienieLogarytm(BufferedImage img){
        double C = 50;

        int[] LUT = new int[256];

        for(int i=0 ; i<256; i++) {
            LUT[i] = (int) (C * Math.log(i+1));
            if(LUT[i]<0) LUT[i]=0;
            if(LUT[i]>255) LUT[i]=255;
        }

        for(int i = 0; i< img.getWidth(); i ++){
            for (int j = 0; j< img.getHeight(); j++){
                Color c = new Color(img.getRGB(i,j));
                img.setRGB(i,j,new Color(LUT[c.getRed()], LUT[c.getGreen()], LUT[c.getBlue()]).getRGB());
            }
        }
        return img;
    }
}
