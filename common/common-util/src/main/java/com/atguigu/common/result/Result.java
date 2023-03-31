package com.atguigu.common.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Result(){
    }

    public static<T> Result<T> build(T data,ResultCodeEnum resultCodeEnum){
        Result<T> result = new Result<>();
        result.setCode(resultCodeEnum.getCode());
        result.setData(data);
        result.setMessage(result.getMessage());
        return result;
    }


    public static<T> Result<T> ok(T data){
        return build(data,ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> fail(T data){
        return build(data,ResultCodeEnum.FAIL);
    }
    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
