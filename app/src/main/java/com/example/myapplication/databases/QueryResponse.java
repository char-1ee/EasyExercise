package com.example.myapplication.databases;

public interface QueryResponse<T> {
    void onSuccess(T data);
    void onFailure(String message);
}