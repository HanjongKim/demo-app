package com.skb.demo.storeLogic.redis.repo;


import java.util.Set;

import com.skb.demo.domain.dto.TransactionDto;


public interface TransactionRepository {

    public TransactionDto getTransactionByTransactionId(Long transactionId);

    public void saveTransactionByTransactionId(Long transactionId, TransactionDto transactionDto);

    public void addTransactionIdToTypeSet(String type, Long transactionId);

    public void addTransactionIdToParentIdSet(Long ParentTransactionId, Long childTransactionId);

    public Set<Long> getChildsByParentIdSet(Long ParentTransactionId);

    public Set<Long> getKeysByType(String type);

}
