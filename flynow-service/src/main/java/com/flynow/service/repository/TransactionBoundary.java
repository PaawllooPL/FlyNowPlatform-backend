package com.flynow.service.repository;

public interface TransactionBoundary {
    void execute(Runnable operation);
}
