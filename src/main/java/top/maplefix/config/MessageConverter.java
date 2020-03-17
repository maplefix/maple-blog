package top.maplefix.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Maple
 * @description: 接收参数类型兼容text/plain和text/xml
 * @date : 2020/1/16 9:56
 */
@Component
public class MessageConverter extends MappingJackson2HttpMessageConverter {

    public MessageConverter(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_XML);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        setSupportedMediaTypes(mediaTypes);
    }

}
