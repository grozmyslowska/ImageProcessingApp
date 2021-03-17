package filtration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public abstract class Filtration {

    public static BufferedImage convolution(BufferedImage img, int[] mask) {
        BufferedImage copy = deepCopy(img);
        for(int w = 0; w < img.getWidth(); w++) {
            for(int h = 0; h < img.getHeight(); h++) {
                copy.setRGB(w, h, calculateNewPixelValue(img, w, h, mask));
            }
        }
        return copy;
    }

    public static int calculateNewPixelValue(BufferedImage img, int w, int h, int[]p) {
        double sumRed = 0, sumGreen = 0, sumBlue = 0;
        int ip = 0, sum = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                int ii = i, jj = j;
                if(w + i < 0 || w + i > img.getWidth() - 1) ii = 0;
                if(h + j < 0 || h + j > img.getHeight() - 1) jj = 0;
                Color c = new Color(img.getRGB(w + ii, h + jj));
                sum += p[ip];
                sumRed += c.getRed() * p[ip];
                sumGreen += c.getGreen() * p[ip];
                sumBlue += c.getBlue() * p[ip++];
            }
        }

        if(sum > 0){
            sumRed = sumRed / sum;
            sumBlue = sumBlue / sum;
            sumGreen = sumGreen / sum;
        }

        if(sumRed<0) sumRed=0;
        if(sumRed>255) sumRed=255;
        if(sumGreen<0) sumGreen=0;
        if(sumGreen>255) sumGreen=255;
        if(sumBlue<0) sumBlue=0;
        if(sumBlue>255) sumBlue=255;

        Color c = new Color((int) sumRed, (int) sumGreen, (int) sumBlue);
        return c.getRGB();
    }

    public static BufferedImage median (BufferedImage img, int r) {
        BufferedImage copy = deepCopy(img);
        for(int w = 0; w < img.getWidth(); w++) {
            for(int h = 0; h < img.getHeight(); h++) {

                int[] Red = new int[r*r];
                int[] Green = new int[r*r];
                int[] Blue = new int[r*r];
                int iter = 0;

                for(int i = -r/2; i <= r/2; i++) {
                    for(int j = -r/2; j <= r/2; j++) {
                        int ii = i, jj = j;
                        if(w + i < 0 || w + i > img.getWidth() - 1) ii = 0;
                        if(h + j < 0 || h + j > img.getHeight() - 1) jj = 0;
                        Color c = new Color(img.getRGB(w + ii, h + jj));
                        Red[iter] = c.getRed();
                        Green[iter] = c.getGreen();
                        Blue[iter++] = c.getBlue();
                    }
                }

                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);

                Color c = new Color(Red[r*r/2], Green[r*r/2], Blue[r*r/2]);

                copy.setRGB(w, h, c.getRGB());
            }
        }
        return copy;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage kuwahara (BufferedImage img){
        BufferedImage copy = deepCopy(img);
        for(int w = 0; w < img.getWidth(); w++) {
            for(int h = 0; h < img.getHeight(); h++) {

                int[][] maskR = new int[5][5];
                int[][] maskG = new int[5][5];
                int[][] maskB = new int[5][5];

                for(int i = -2; i <= 2; i++) {
                    for(int j = -2; j <= 2; j++) {
                        int ii = i, jj = j;
                        if(w + i < 0 || w + i > img.getWidth() - 1) ii = 0;
                        if(h + j < 0 || h + j > img.getHeight() - 1) jj = 0;
                        Color c = new Color(img.getRGB(w + ii, h + jj));
                        maskR[2+i][2+j] = c.getRed();
                        maskG[2+i][2+j] = c.getGreen();
                        maskB[2+i][2+j] = c.getBlue();
                    }
                }

                int avgR1 = 0;
                int avgG1 = 0;
                int avgB1 = 0;

                for(int n = 0; n < 3; n++) {
                    for(int m = 0; m < 3; m++) {
                        avgR1 += maskR[n][m];
                        avgG1 += maskG[n][m];
                        avgB1 += maskB[n][m];
                    }
                }

                avgR1 /= 9;
                avgG1 /= 9;
                avgB1 /= 9;

                int varR1 = 0;
                int varG1 = 0;
                int varB1 = 0;

                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        varR1 += (int)Math.pow(maskR[n][m] - avgR1, 2);
                        varG1 += (int)Math.pow(maskG[n][m] - avgG1, 2);
                        varB1 += (int)Math.pow(maskB[n][m] - avgB1, 2);
                    }
                }

                varR1 /= 9;
                varG1 /= 9;
                varB1 /= 9;

                int avgR2 = 0;
                int avgG2 = 0;
                int avgB2 = 0;

                for (int n = 2; n < 5; n++) {
                    for (int m = 0; m < 3; m++) {
                        avgR2 += maskR[n][m];
                        avgG2 += maskG[n][m];
                        avgB2 += maskB[n][m];
                    }
                }

                avgR2 /= 9;
                avgG2 /= 9;
                avgB2 /= 9;

                int varR2 = 0;
                int varG2 = 0;
                int varB2 = 0;

                for (int n = 2; n < 5; n++) {
                    for (int m = 0; m < 3; m++) {
                        varR2 += (int)Math.pow(maskR[n][m] - avgR2, 2);
                        varG2 += (int)Math.pow(maskG[n][m] - avgG2, 2);
                        varB2 += (int)Math.pow(maskB[n][m] - avgB2, 2);
                    }
                }

                varR2 /= 9;
                varG2 /= 9;
                varB2 /= 9;

                int avgR3 = 0;
                int avgG3 = 0;
                int avgB3 = 0;

                for (int n = 0; n < 3; n++) {
                    for (int m = 2; m < 5; m++) {
                        avgR3 += maskR[n][m];
                        avgG3 += maskG[n][m];
                        avgB3 += maskB[n][m];
                    }
                }
                avgR3 /= 9;
                avgG3 /= 9;
                avgB3 /= 9;

                int varR3 = 0;
                int varG3 = 0;
                int varB3 = 0;

                for (int n = 0; n < 3; n++) {
                    for (int m = 2; m < 5; m++) {
                        varR3 += (int)Math.pow(maskR[n][m] - avgR3, 2);
                        varG3 += (int)Math.pow(maskG[n][m] - avgG3, 2);
                        varB3 += (int)Math.pow(maskB[n][m] - avgB3, 2);
                    }
                }

                varR3 /= 9;
                varG3 /= 9;
                varB3 /= 9;


                int avgR4 = 0;
                int avgG4 = 0;
                int avgB4 = 0;

                for (int n = 2; n < 5; n++) {
                    for (int m = 2; m < 5; m++) {
                        avgR4 += maskR[n][m];
                        avgG4 += maskG[n][m];
                        avgB4 += maskB[n][m];
                    }
                }

                avgR4 /= 9;
                avgG4 /= 9;
                avgB4 /= 9;

                int varR4 = 0;
                int varG4 = 0;
                int varB4 = 0;

                for (int n = 2; n < 5; n++) {
                    for (int m = 2; m < 5; m++) {
                        varR4 += (int)Math.pow(maskR[n][m] - avgR4, 2);
                        varG4 += (int)Math.pow(maskG[n][m] - avgG4, 2);
                        varB4 += (int)Math.pow(maskB[n][m] - avgB4, 2);
                    }
                }

                varR4 /= 9;
                varG4 /= 9;
                varB4 /= 9;

                int newPixelR, newPixelG, newPixelB;

                int[] listR = {varR1, varR2, varR3, varR4};
                Arrays.sort(listR);

                if(listR[0] == varR1) { newPixelR = avgR1; }
                else if (listR[0] == varR2) { newPixelR = avgR2; }
                else if (listR[0] == varR3) { newPixelR = avgR3; }
                else { newPixelR = avgR4; }

                int[] listG = {varG1, varG2, varG3, varG4};
                Arrays.sort(listG);

                if (listG[0] == varG1) { newPixelG = avgG1; }
                else if (listG[0] == varG2) { newPixelG = avgG2; }
                else if (listG[0] == varG3) { newPixelG = avgG3; }
                else { newPixelG = avgG4; }

                int[] listB = {varB1, varB2, varB3, varB4};
                Arrays.sort(listB);

                if (listB[0] == varB1) { newPixelB = avgB1; }
                else if (listB[0] == varB2) { newPixelB = avgB2; }
                else if (listB[0] == varB3) { newPixelB = avgB3; }
                else { newPixelB = avgB4; }

                copy.setRGB(w, h, (new Color(newPixelR, newPixelG, newPixelB)).getRGB());
            }
        }
        return copy;
    }

}
