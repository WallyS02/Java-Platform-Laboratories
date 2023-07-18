import org.apache.commons.lang3.tuple.Pair;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(1);
        processImage(pool);
        System.out.println("1 thread time:");
        System.out.println(System.currentTimeMillis() - time);
        pool.shutdown();

        time = System.currentTimeMillis();
        pool = new ForkJoinPool(10);
        processImage(pool);
        System.out.println("10 threads time:");
        System.out.println(System.currentTimeMillis() - time);
        pool.shutdown();

        time = System.currentTimeMillis();
        pool = new ForkJoinPool(100);
        processImage(pool);
        System.out.println("100 threads time:");
        System.out.println(System.currentTimeMillis() - time);
        pool.shutdown();
    }

    public static void processImage(ForkJoinPool pool) {
        List<Path> files;
        Path source = Path.of(System.getProperty("user.dir") + "\\img");
        try (Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
            pool.submit(() -> {
                Stream<Pair<String, BufferedImage>> pairs = files.stream().parallel().map(value -> {
                    BufferedImage image;
                    try {
                        String name = value.getFileName().toString();
                        image = ImageIO.read(value.toFile());
                        return Pair.of(name, image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });

                Stream<Pair<String, BufferedImage>> pairsChanged = pairs.parallel().map(value -> {
                    BufferedImage original = value.getValue();
                    String name = value.getKey();
                    BufferedImage outImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

                    for (int i = 0; i < original.getWidth(); i++) {
                        for (int j = 0; j < original.getHeight(); j++) {
                            int rgb = original.getRGB(i, j);
                            Color color = new Color(rgb);
                            int red = color.getRed();
                            int green = color.getGreen();
                            int blue = color.getBlue();
                            Color outColor = new Color(green, blue, red);
                            int outRgb = outColor.getRGB();
                            outImage.setRGB(i, j, outRgb);
                        }
                    }
                    return Pair.of(name, outImage);
                });

                String out = (System.getProperty("user.dir") + "\\new_img\\");

                pairsChanged.parallel().forEach(value -> {
                    File outputfile = new File(String.valueOf(Path.of(out + value.getKey())));
                    try {
                        ImageIO.write(value.getValue(), "jpg", outputfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }).get();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}