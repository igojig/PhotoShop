package ru.igojig.photomag.components;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.igojig.photomag.exceptions.SaveToDropBoxException;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
@Data
@Builder
public class ImagesUploadInfo {

    private List<SaveToDropBoxException> exceptionList;

    @PostConstruct
    public void init(){
        exceptionList=new ArrayList<>();
        System.out.println("post construct");
    }

}
