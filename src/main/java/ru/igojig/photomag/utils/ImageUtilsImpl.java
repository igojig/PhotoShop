package ru.igojig.photomag.utils;

import com.twelvemonkeys.image.ResampleOp;
import com.twelvemonkeys.imageio.util.ImageTypeSpecifiers;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static javax.imageio.ImageWriteParam.MODE_EXPLICIT;

@Slf4j
@Component
public class ImageUtilsImpl implements ImageUtils {

    ThreadLocal<ByteArrayOutputStream> threadLocal=ThreadLocal.withInitial(ByteArrayOutputStream::new);
    BufferedImage watermark;

    @Override
    public byte[] resize(MultipartFile file) {

ByteArrayOutputStream baos=threadLocal.get();
baos.reset();

        log.info("Original size[{}]", file.getSize());

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageIO.setUseCache(false);


        if (!writers.hasNext()) {
            throw new IllegalArgumentException("No writer for: " + "jpeg");
        }

        ImageWriter writer = writers.next();

        try (ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream());
             ImageOutputStream output = ImageIO.createImageOutputStream(baos)) {
            // Get the reader
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

            if (!readers.hasNext()) {
                throw new IllegalArgumentException("No reader for: " + file);
            }

            ImageReader reader = readers.next();

            try {
                reader.setInput(input);

                // Optionally, listen for read warnings, progress, etc.
//                reader.addIIOReadWarningListener(...);
//                reader.addIIOReadProgressListener(...);
                float aspectRatio = reader.getAspectRatio(0);
                IIOMetadata imageMetadata = reader.getImageMetadata(0);
                ImageReadParam param = reader.getDefaultReadParam();


                // Optionally, control read settings like sub sampling, source region or destination etc.

//                param.setSourceSubsampling(...);
//                param.setSourceRegion(...);
//                param.setDestination(...);
                // ...

                // Finally read the image, using settings from param
                BufferedImage image = reader.read(0, param);


                BufferedImageOp resampler = new ResampleOp(640, (int) (640/aspectRatio), ResampleOp.FILTER_LANCZOS);
                BufferedImage out = resampler.filter(image, null);


                writer.setOutput(output);

                // Optionally, listen to progress, warnings, etc.
//

                ImageWriteParam paramWrite = writer.getDefaultWriteParam();


                paramWrite.getCompressionTypes();
                paramWrite.setCompressionMode(MODE_EXPLICIT);
                paramWrite.setCompressionQuality(0.85f);
                ImageTypeSpecifier fromRenderedImage = ImageTypeSpecifiers.createFromRenderedImage(out);
                IIOMetadata defaultImageMetadata = writer.getDefaultImageMetadata(fromRenderedImage, paramWrite);
                IIOMetadata defaultStreamMetadata = writer.getDefaultStreamMetadata(paramWrite);

                writer.write(null, new IIOImage(out, null, null), paramWrite);

                // Optionally, read thumbnails, meta data, etc...
//                int numThumbs = reader.getNumThumbnails(0);
                // ...
            } finally {
                // Dispose reader in finally block to avoid memory leaks
                reader.dispose();
                writer.dispose();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//
//        try (InputStream inputStream = file.getInputStream()) {
//            Thumbnails.of(file.getInputStream())
////                    .watermark(watermark)
//                    .antialiasing(Antialiasing.ON)
//                    .dithering(Dithering.ENABLE)
//                    .outputQuality(1)
//                    .rendering(Rendering.QUALITY)
//                    .resizer(Resizers.PROGRESSIVE)
//                    .size(640, 480)
//                    .toOutputStream(baos);
//        } catch (IOException e) {
//            e.printStackTrace();
////            return e.getMessage();
//        }


        log.info("Resized[{}]", baos.size());
        return baos.toByteArray();

    }

    @PostConstruct
    private void watermark(){
        try {
            File file = ResourceUtils.getFile("classpath:static/assets/watermark.png");
           watermark=ImageIO.read(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
