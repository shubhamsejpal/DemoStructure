package com.bazmashop.api;


import com.bazmashop.BaseApp;
import com.bazmashop.api.error.ErrorModel;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class ErrorUtils {

    public static ErrorModel parseError(Response<?> response) {
        Converter<ResponseBody, ErrorModel> converter = BaseApp.Companion.getRetrofitInstance().responseBodyConverter(ErrorModel.class, new Annotation[0]);

        ErrorModel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new ErrorModel();
        }

        return error;
    }
}