package com.janita.idplugin.remote.rest;

import com.janita.idplugin.common.domain.CrQuestionSetting;
import com.janita.idplugin.common.enums.CrRestApiEnum;
import com.janita.idplugin.common.exception.PluginRuntimeException;
import com.janita.idplugin.remote.api.ApiResponse;
import com.janita.idplugin.remote.api.Head;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * HttpUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class HttpUtils {

    public static <T> T get(CrQuestionSetting setting, CrRestApiEnum apiEnum) {
        String restApiDomain = setting.getRestApiDomain();
        ResponseEntity<ApiResponse> responseEntity = RestTemplateFactory.getRestTemplate().getForEntity(apiEnum.getUrl(restApiDomain), ApiResponse.class);
        return (T) checkAndReturnEntity(responseEntity);
    }

    public static Object checkAndReturnEntity(ResponseEntity<ApiResponse> entity) {
        if (entity.getStatusCode() != HttpStatus.valueOf(200)) {
            throw new PluginRuntimeException("调用异常");
        }
        ApiResponse response = entity.getBody();
        boolean success = success(response);
        if (!success) {
            throw new PluginRuntimeException(response.getHead().getMsg());
        }
        return entity.getBody().getData();
    }

    public static <T> T post(CrQuestionSetting setting, CrRestApiEnum apiEnum, Object requestBody) {
        String restApiDomain = setting.getRestApiDomain();
        ResponseEntity<ApiResponse> postForEntity = RestTemplateFactory.getRestTemplate().postForEntity(apiEnum.getUrl(restApiDomain), requestBody, ApiResponse.class);
        return (T) checkAndReturnEntity(postForEntity);
    }

    public static boolean success(ApiResponse<?> response) {
        if (response == null) {
            return false;
        }
        Head head = response.getHead();
        return head.isSuccess();
    }
}
