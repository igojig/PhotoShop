package ru.igojig.photomag.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.resizers.Resizers;
import net.coobird.thumbnailator.resizers.configurations.Antialiasing;
import net.coobird.thumbnailator.resizers.configurations.Dithering;
import net.coobird.thumbnailator.resizers.configurations.Rendering;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ImageUtilsImpl implements ImageUtils {

    ThreadLocal<ByteArrayOutputStream> threadLocal = ThreadLocal.withInitial(ByteArrayOutputStream::new);
    BufferedImage watermark;


    @Override
    public byte[] resize(MultipartFile file) {

        ByteArrayOutputStream baos = threadLocal.get();
        baos.reset();

//        log.info("Original size[{}]", file.getSize());
//
//        ImageIO.setUseCache(false);
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
//
//
//        if (!writers.hasNext()) {
//            throw new IllegalArgumentException("No writer for: " + "jpeg");
//        }
//
//        ImageWriter writer = writers.next();
//
//        try (ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream());
//             ImageOutputStream output = ImageIO.createImageOutputStream(baos)) {
//            // Get the reader
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
//
//            if (!readers.hasNext()) {
//                throw new IllegalArgumentException("No reader for: " + file);
//            }
//
//            ImageReader reader = readers.next();
//
//            try {
//                reader.setInput(input);
//
////                 Optionally, listen for read warnings, progress, etc.
//                reader.addIIOReadWarningListener((source, warning) -> log.info("warn [{}]", warning));
//                reader.addIIOReadProgressListener(new ProgressListenerBase() {
//                    @Override
//                    public void imageProgress(ImageReader source, float percentageDone) {
//                        log.info("progress:[{}]", percentageDone);
//                        super.imageProgress(source, percentageDone);
//                    }
//                });
//                float aspectRatio = reader.getAspectRatio(0);
//                IIOMetadata imageMetadata = reader.getImageMetadata(0);
//                ImageReadParam param = reader.getDefaultReadParam();
//
//
//                // Optionally, control read settings like sub sampling, source region or destination etc.
//
////                param.setSourceSubsampling(...);
////                param.setSourceRegion(...);
////                param.setDestination(...);
//                // ...
//
//                // Finally read the image, using settings from param
//                BufferedImage image = reader.read(0, param);
//
//
//                BufferedImageOp resampler = new ResampleOp(640, (int) (640 / aspectRatio), ResampleOp.FILTER_LANCZOS);
//                BufferedImage out = resampler.filter(image, null);
//
//
//                writer.setOutput(output);
//
//                // Optionally, listen to progress, warnings, etc.
////
//
//                ImageWriteParam paramWrite = writer.getDefaultWriteParam();
//
//
//                paramWrite.getCompressionTypes();
//                paramWrite.setCompressionMode(MODE_EXPLICIT);
//                paramWrite.setCompressionQuality(0.85f);
//                ImageTypeSpecifier fromRenderedImage = ImageTypeSpecifiers.createFromRenderedImage(out);
//                IIOMetadata defaultImageMetadata = writer.getDefaultImageMetadata(fromRenderedImage, paramWrite);
//                IIOMetadata defaultStreamMetadata = writer.getDefaultStreamMetadata(paramWrite);
//
//                writer.write(null, new IIOImage(out, null, defaultImageMetadata), paramWrite);
//
//                // Optionally, read thumbnails, meta data, etc...
////                int numThumbs = reader.getNumThumbnails(0);
//                // ...
//            } finally {
//                // Dispose reader in finally block to avoid memory leaks
//                reader.dispose();
//                writer.dispose();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        try (InputStream inputStream = file.getInputStream()) {
            Thumbnails.of(file.getInputStream())
                    .watermark(Positions.CENTER, watermark, 0.3f, 30)

                    .antialiasing(Antialiasing.ON)
                    .dithering(Dithering.ENABLE)
                    .outputQuality(0.85)
                    .rendering(Rendering.QUALITY)
                    .resizer(Resizers.PROGRESSIVE)
                    .size(640, 480)
                    .toOutputStream(baos);
        } catch (IOException e) {
            e.printStackTrace();
//            return e.getMessage();
        }


        log.info("Resized[{}]", baos.size());
        return baos.toByteArray();

    }

    @Override
    public LocalDateTime extractDateTime(MultipartFile file) {

        try(InputStream inputStream=file.getInputStream()){
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
//            Iterable<Directory> directories = metadata.getDirectories();

            ExifSubIFDDirectory firstDirectoryOfType = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            String date = firstDirectoryOfType.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            LocalDateTime parse = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
            return parse;

        }
        catch (IOException | ImageProcessingException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//        try (ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream())) {
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
//
//            if (!readers.hasNext()) {
//                throw new IllegalArgumentException("No reader for: " + file);
//            }
//
//            ImageReader reader = readers.next();
//
//            reader.setInput(input);
//
//            IIOMetadata imageMetadata = reader.getImageMetadata(0);
//            imageMetadata.getController();
//            Node asTree = imageMetadata.getAsTree("javax_imageio_jpeg_image_1.0");
//
//        } catch (IOException e) {
//             e.printStackTrace();
//        }



    }

    @PostConstruct
    private void watermark() {
        try {
            File file = ResourceUtils.getFile("classpath:static/assets/watermark.png");
            watermark = ImageIO.read(file);
            log.info("watermark file loaded");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
