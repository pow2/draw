package bg.tusofia.draw.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnlimitedImageWorks {
	
	private static Logger logger = LoggerFactory.getLogger(UnlimitedImageWorks.class);
			
	public static byte[] createThumbnail( byte[] orig, int maxDim) {
		  try {
		    ImageIcon imageIcon = new ImageIcon(orig);
		    Image inImage = imageIcon.getImage();
		    double scale = (double) maxDim / (double) inImage.getWidth(null);

		    int scaledW = (int) (scale * inImage.getWidth(null));
		    int scaledH = (int) (scale * inImage.getHeight(null));

		    BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
		    AffineTransform tx = new AffineTransform();

		    if (scale < 1.0d) {
		      tx.scale(scale, scale);
		    }

		    Graphics2D g2d = outImage.createGraphics();
		    g2d.drawImage(inImage, tx, null);
		    g2d.dispose();  

		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(outImage, "JPG", baos);
		    byte[] bytesOut = baos.toByteArray();

		    return bytesOut;
		  } catch (IOException e) {
			  logger.error(GF.getError(e));
		  }
		  return null;
		}
}
