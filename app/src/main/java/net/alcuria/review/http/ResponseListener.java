package net.alcuria.review.http;

public interface ResponseListener<T> {

    void invoke(T response);
}
