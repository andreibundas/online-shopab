package org.fasttrackit.onlineshopab.transfer.productReview;

import javax.validation.constraints.NotNull;

public class SaveProductReviewRequest {

    @NotNull
    private  String content;
    private long productId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "SaveProductReviewRequest{" +
                "content='" + content + '\'' +
                ", productId=" + productId +
                '}';
    }
}




