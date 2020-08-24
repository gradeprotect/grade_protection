package com.das.thread;

import com.das.utils.SendEmail;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-24
 */
public class EmailThread implements Runnable{
    private String title;
    private String message;
    private List<String> receivers;

    public EmailThread(String title,String message,List<String> receivers){
        this.title = title;
        this.message = message;
        this.receivers = receivers;
    }

    @SneakyThrows
    @Override
    public void run() {
        for (String receiver:receivers){
            SendEmail.sendEmail(title,message,receiver);
        }
    }
}
