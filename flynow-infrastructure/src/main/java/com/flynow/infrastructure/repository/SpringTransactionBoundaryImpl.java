package com.flynow.infrastructure.repository;

import com.flynow.service.repository.TransactionBoundary;
import jakarta.transaction.Transactional;

public class SpringTransactionBoundaryImpl implements TransactionBoundary {

    @Override
    @Transactional
    public void execute(Runnable operation) {
        operation.run();
    }
}
