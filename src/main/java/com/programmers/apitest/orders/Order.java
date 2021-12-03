package com.programmers.apitest.orders;

import java.time.LocalDateTime;

public class Order {
    private Long seq;

    private Long userId;

    private Long productId;

    private Review review;

    private State state;

    private String requestMessage;

    private String rejectMessage;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public Order(Long seq,
                 Long userId,
                 Long productId,
                 Review review,
                 State state,
                 String requestMessage,
                 String rejectMessage,
                 LocalDateTime completedAt,
                 LocalDateTime rejectedAt,
                 LocalDateTime createAt) {
        this.seq=seq;
        this.userId=userId;
        this.productId=productId;
        this.review=review;
        this.state=state;
        this.requestMessage=requestMessage;
        this.rejectMessage=rejectMessage;
        this.completedAt=completedAt;
        this.rejectedAt=rejectedAt;
        this.createAt=createAt;
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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    static public class Builder {

        private Long seq;

        private Long userId;

        private Long productId;

        private Review review;

        private State state;

        private String requestMessage;

        private String rejectMessage;

        private LocalDateTime completedAt;

        private LocalDateTime rejectedAt;

        private LocalDateTime createAt;

        public Builder() {/*empty*/}

        public Builder(Order order) {
            this.seq = order.seq;
            this.userId = order.userId;
            this.productId = order.productId;
            this.review = order.review;
            this.state = order.state;;
            this.requestMessage = order.requestMessage;
            this.rejectMessage = order.rejectMessage;;
            this.completedAt = order.completedAt;
            this.rejectedAt = order.rejectedAt;;
            this.createAt = order.createAt;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder review(Review review) {
            this.review = review;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder requestMessage(String requestMessage) {
            this.requestMessage = requestMessage;
            return this;
        }

        public Builder rejectMessage(String rejectMessage) {
            this.rejectMessage = rejectMessage;
            return this;
        }


        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder rejectedAt(LocalDateTime rejectedAt) {
            this.rejectedAt = rejectedAt;
            return this;
        }

        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Order build() {
            if (review.invalidData())
                this.review = null;
            return new Order(
                    seq,
                    userId,
                    productId,
                    review,
                    state,
                    requestMessage,
                    rejectMessage,
                    completedAt,
                    rejectedAt,
                    createAt
            );
        }
    }
}
