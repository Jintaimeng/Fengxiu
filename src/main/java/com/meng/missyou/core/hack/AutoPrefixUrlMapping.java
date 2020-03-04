package com.meng.missyou.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {
    @Value("${missyou.api-package}")
    private String apiPackagePath;
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if(mappingInfo != null){
            String prefix = this.getPrefix(handlerType);
            RequestMappingInfo newMappingInfo = RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
            return newMappingInfo;
        }
        return mappingInfo;
    }
    private String getPrefix(Class<?> handlerType){
        String packageName = handlerType.getPackage().getName();
        String dotPaht = packageName.replaceAll(this.apiPackagePath,"");
        return dotPaht.replace(".","/");
    }
}
