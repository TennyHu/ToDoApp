package com.app.todoapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;    // 1 = success, 0 = fail
    private String msg;  // error / success message
    private Object data;

    // create/delete/update success response
    public static Result success(){
        return new Result(1,"success",null);
    }
    // retrieve success response
    public static Result success(Object data){
        return new Result(1,"success",data);
    }
    // fail response
    public static Result error(String msg){
        return new Result(0,msg,null);
    }
}
