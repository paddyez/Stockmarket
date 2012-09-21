/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paddy.stockmarket.util.image;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author paddy
 */
public class WindowIcons
{
    private static final int[] RESOLUTIONS = { 16, 24, 32, 48, 64, 96, 128 };
    private URL url;

    public WindowIcons(URL url)
    {
        this.url =url;
        List<Image> imageList = getWindowIcons(url);
    }
    /**
     *
     * @param imageResourceLocation
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Image> createScaledIcons(URL url)
    {
        ArrayList<Image> list = new ArrayList<>();
        try
        {
            Image icon = ImageIO.read(url);
            for (int res : RESOLUTIONS)
            {
                    list.add(createScaledImage(icon, res, res));
            }
        }
        catch(IOException ioe)
        {
            System.err.println(ioe);
        }
        return list;
    }
    /**
     *
     * @param image
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage createScaledImage(Image image, int width, int height)
    {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) scaledImage.createGraphics();
        //g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }
    /**
     * In GUI-Klasse kopieren und "imageIcons" des Frames mit Custom Code initialisieren:
     * @return
     */
    private List<Image> getWindowIcons(URL url)
    {
        System.out.println(url);
        List<Image> list = WindowIcons.createScaledIcons(url);
        return list;
    }
    /**
     *
     * @return
     */
    public static int getResolutionSize()
    {
        int size = RESOLUTIONS.length;
        return size;
    }
}