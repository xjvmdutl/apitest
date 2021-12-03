package com.programmers.apitest.orders;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ReviewDto {
    private Long seq;

    private Long userId;

    private Long productId;

    private String content;

    private LocalDateTime createAt;

    public ReviewDto(Review source) {
        copyProperties(source, this);
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "seq=" + seq +
                ", userId=" + userId +
                ", productId=" + productId +
                ", content='" + content + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
