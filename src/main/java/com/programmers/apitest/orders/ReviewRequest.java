package com.programmers.apitest.orders;

import javax.validation.constraints.Size;

public class ReviewRequest {

    @Size(min = 1, max = 1000)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReviewRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}
