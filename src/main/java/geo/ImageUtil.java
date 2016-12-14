package geo;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class ImageUtil {
    public static byte[] getBytes(String url) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new URL(url)), "jpg", baos);
        return baos.toByteArray();
    }
}
