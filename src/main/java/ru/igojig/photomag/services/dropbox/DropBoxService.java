package ru.igojig.photomag.services.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.exceptions.SaveToDropBoxException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DropBoxService {

    private final DbxClientV2 client;


    public boolean isFolderExists(String path) {

        boolean result = false;

        try {
            Metadata metadata = client.files().getMetadata(path);
            result = true;
        } catch (GetMetadataErrorException e) {
            log.warn("folder doesn't exists {}", path, e);
        } catch (DbxException e) {
            log.error("Drop box exception", e);
        }

        return result;
    }

    public Optional<CreateFolderResult> createFolder(String path) {

        Optional<CreateFolderResult> createFolderResult = Optional.empty();

        try {
            CreateFolderResult folderV2 = client.files().createFolderV2(path);
            createFolderResult = Optional.of(folderV2);
        } catch (CreateFolderErrorException e) {
            log.error("Error folder creation {}", path, e);
        } catch (DbxException e) {
            log.error("Drop box exception", e);
        }

        return createFolderResult;
    }


    public FileMetadata save(MultipartFile file, Image image) {

        String path = image.getFilePath();
        String name = image.getFileName();

        if (!isFolderExists(path)) {
            createFolder(path);
        }
        try (InputStream in=file.getInputStream()){

            FileMetadata fileMetadata = client.files().uploadBuilder(path + "/" + name).uploadAndFinish(in);
            log.info("file uploaded to dropbox {}", fileMetadata.toStringMultiline());
            return fileMetadata;
        } catch (DbxException e) {
            log.error("Drop box exception", e);
            throw SaveToDropBoxException.builder()
                    .message(e.getMessage())
                    .imageId(image.getId())
                    .fileName(image.getFileName())
                    .build();
        } catch (IOException e) {
            log.error("IOException", e);
            throw SaveToDropBoxException.builder()
                    .message(e.getMessage())
                    .imageId(image.getId())
                    .fileName(image.getFileName())
                    .build();
        }
    }
}
