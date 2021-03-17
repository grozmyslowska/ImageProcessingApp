package viewer;

import binarization.Binarization;
import brightness.Brightness;
import filtration.Filtration;
import histogram.HistogramOperations;
import shared.ImageSharedOperations;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

public class Viewer extends JFrame {

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu files = new JMenu("File");
    private final JMenuItem loadImage = new JMenuItem("Load image");
    private final JMenuItem saveImageJPG = new JMenuItem("Save image as jpg");
    private final JMenuItem saveImagePNG = new JMenuItem("Save image as png");
    private final JMenuItem saveImageTIFF = new JMenuItem("Save image as tiff");
    private final JMenuItem saveImageBMP = new JMenuItem("Save image as bmp");

    private final JMenu histogram = new JMenu("Histograms");
    private final JMenuItem showHistogramRGB = new JMenuItem("Show histogram RGB");
    private final JMenuItem showHistogramRGB3 = new JMenuItem("Show histogram R+G+B/3");
    private final JMenuItem showHistogramR = new JMenuItem("Show histogram R");
    private final JMenuItem showHistogramG = new JMenuItem("Show histogram G");
    private final JMenuItem showHistogramB = new JMenuItem("Show histogram B");
    private final JMenuItem rozciaganieHistograms = new JMenuItem("Stretch histograms");
    private final JMenuItem wyrownanieHistograms = new JMenuItem("Equalize histograms");

    private final JMenu brightness = new JMenu("Brightness");
    private final JMenuItem brighten = new JMenuItem("Brighten image");
    private final JMenuItem darken = new JMenuItem("Darken image");

    private final JMenu binarization = new JMenu("Binarization");
    private final JMenuItem binarizationManula = new JMenuItem("Manual");
    private final JMenuItem binarizationOtsu = new JMenuItem("Otsu's method");
//    private final JMenuItem binarizationNiblack = new JMenuItem("Niblack's method");

    private final JMenu filter = new JMenu("Filter");
    private final JMenuItem filterImage = new JMenuItem("Convolution filter (mask 3x3)");
    private final JMenuItem filterKuwahara = new JMenuItem("Kuwahara filter");
    private final JMenuItem filterMedianowy3 = new JMenuItem("Median filter (mask 3x3)");
    private final JMenuItem filterMedianowy5 = new JMenuItem("Median filter (mask 5x5)");

    private MyImagePanel jPanel = new MyImagePanel();

    public Viewer() {
        this.setLayout(new BorderLayout());
        this.setTitle("Podstawy Biometrii");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.menuBar.add(this.files);
        this.files.add(this.loadImage);
        this.files.add(this.saveImageJPG);
        this.files.add(this.saveImagePNG);
        this.files.add(this.saveImageTIFF);
        this.files.add(this.saveImageBMP);

        this.menuBar.add(this.histogram);
        this.histogram.add(this.showHistogramRGB);
        this.histogram.add(this.showHistogramRGB3);
        this.histogram.add(this.showHistogramR);
        this.histogram.add(this.showHistogramG);
        this.histogram.add(this.showHistogramB);
        this.histogram.add(this.rozciaganieHistograms);
        this.histogram.add(this.wyrownanieHistograms);

        this.menuBar.add(this.brightness);
        this.brightness.add(this.brighten);
        this.brightness.add(this.darken);

        this.menuBar.add(this.binarization);
        this.binarization.add(this.binarizationManula);
        this.binarization.add(this.binarizationOtsu);

        this.menuBar.add(this.filter);
        this.filter.add(this.filterImage);
        this.filter.add(this.filterKuwahara);
        this.filter.add(this.filterMedianowy3);
        this.filter.add(this.filterMedianowy5);

        this.add(this.menuBar, BorderLayout.NORTH);
        this.add(this.jPanel, BorderLayout.CENTER);

        this.loadImage.addActionListener((ActionEvent e) -> {
            JFileChooser imageOpener = new JFileChooser();
            imageOpener.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    String fileName = f.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
                            || fileName.endsWith(".tiff") || fileName.endsWith(".bmp")) {
                        return true;
                    } else return false;
                }

                @Override
                public String getDescription() {
                    return "Image files (.jpg, .png, .tiff, .bmp)";
                }
            });

            int returnValue = imageOpener.showDialog(null, "Select image");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                BufferedImage img = ImageSharedOperations.loadImage(imageOpener.getSelectedFile().getPath());
                jPanel.setImage(img);
            }
        });

        this.saveImageJPG.addActionListener((ActionEvent e) -> {
            String path = "./image.jpg";
            BufferedImage img = jPanel.getImage();
            ImageSharedOperations.saveImage(img, path, "jpg");
        });
        this.saveImagePNG.addActionListener((ActionEvent e) -> {
            String path = "./image.png";
            BufferedImage img = jPanel.getImage();
            ImageSharedOperations.saveImage(img, path, "png");
        });
        this.saveImageTIFF.addActionListener((ActionEvent e) -> {
            String path = "./image.tiff";
            BufferedImage img = jPanel.getImage();
            ImageSharedOperations.saveImage(img, path, "tiff");
        });
        this.saveImageBMP.addActionListener((ActionEvent e) -> {
            String path = "./image.bmp";
            BufferedImage img = jPanel.getImage();
            ImageSharedOperations.saveImage(img, path, "bmp");
        });

        this.rozciaganieHistograms.addActionListener((ActionEvent e) -> {
            BufferedImage img = jPanel.getImage();
            jPanel.setImage(HistogramOperations.rozciaganieHistograms(img));
        });
        this.wyrownanieHistograms.addActionListener((ActionEvent e) -> {
            BufferedImage img = jPanel.getImage();
            jPanel.setImage(HistogramOperations.wyrownanieHistograms(img));
        });

        this.showHistogramRGB.addActionListener((ActionEvent e) -> {
            HistogramOperations.showHistogramRGB(jPanel.getImage());
        });
        this.showHistogramRGB3.addActionListener((ActionEvent e) -> {
            HistogramOperations.showHistogramRGB3(jPanel.getImage());
        });
        this.showHistogramR.addActionListener((ActionEvent e) -> {
            HistogramOperations.showHistogramR(jPanel.getImage());
        });
        this.showHistogramG.addActionListener((ActionEvent e) -> {
            HistogramOperations.showHistogramG(jPanel.getImage());
        });
        this.showHistogramB.addActionListener((ActionEvent e) -> {
            HistogramOperations.showHistogramB(jPanel.getImage());
        });

        this.brighten.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Brightness.rozjasnienieLogarytm(jPanel.getImage()));
        });
        this.darken.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Brightness.przyciemnianiePotega(jPanel.getImage()));
        });

        this.binarizationManula.addActionListener((ActionEvent e) -> {
            String test1= JOptionPane.showInputDialog("Please input threshold: ");
            int int1 = Integer.parseInt(test1);
            jPanel.setImage(Binarization.manualBinarization(jPanel.getImage(),int1));
        });
        this.binarizationOtsu.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Binarization.binarizationOtsu(jPanel.getImage()));
        });

        this.filterImage.addActionListener((ActionEvent e) -> {
            int [] p = getMaskFromUser();
            jPanel.setImage(Filtration.convolution(jPanel.getImage(),p));
        });
        this.filterKuwahara.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Filtration.kuwahara(jPanel.getImage()));
        });
        this.filterMedianowy3.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Filtration.median(jPanel.getImage(),3));
        });
        this.filterMedianowy5.addActionListener((ActionEvent e) -> {
            jPanel.setImage(Filtration.median(jPanel.getImage(),5));
        });
    }

    public static int[] getMaskFromUser() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 3, 10, 5));

        int col = 2;
        JTextField p11 = new JTextField(col);
        JTextField p12 = new JTextField(col);
        JTextField p13 = new JTextField(col);
        JTextField p21 = new JTextField(col);
        JTextField p22 = new JTextField(col);
        JTextField p23 = new JTextField(col);
        JTextField p31 = new JTextField(col);
        JTextField p32 = new JTextField(col);
        JTextField p33 = new JTextField(col);

        pane.add(p11);
        pane.add(p12);
        pane.add(p13);
        pane.add(p21);
        pane.add(p22);
        pane.add(p23);
        pane.add(p31);
        pane.add(p32);
        pane.add(p33);

        JFrame frame = new JFrame();

        int option = JOptionPane.showConfirmDialog(frame, pane, "Fill with numbers all the fields of mask", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {

            int[] p = new int[9];

            try {
                p[0] = Integer.parseInt(p11.getText());
                p[1] = Integer.parseInt(p12.getText());
                p[2] = Integer.parseInt(p13.getText());
                p[3] = Integer.parseInt(p21.getText());
                p[4] = Integer.parseInt(p22.getText());
                p[5] = Integer.parseInt(p23.getText());
                p[6] = Integer.parseInt(p31.getText());
                p[7] = Integer.parseInt(p32.getText());
                p[8] = Integer.parseInt(p33.getText());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            return p;
        }
        return null;
    }
}
