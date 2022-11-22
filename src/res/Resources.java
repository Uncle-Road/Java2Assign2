package res;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static BufferedImage[] letters = null;
    static {
        letters = new BufferedImage[2];
        letters[0] = loadImage("D:\\code\\java\\MultiPlayerTicTacToe\\src\\res\\X.png");
        letters[1] = loadImage("D:\\code\\java\\MultiPlayerTicTacToe\\src\\res\\o.png");
    }
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
