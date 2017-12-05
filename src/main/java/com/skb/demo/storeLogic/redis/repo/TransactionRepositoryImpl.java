package com.skb.demo.storeLogic.redis.repo;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.skb.demo.domain.dto.TransactionDto;


@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private RedisTemplate<String, TransactionDto> redisTemplate;

    @Autowired
    private RedisTemplate<String, Long> redisTemplateStringSet;

    @Autowired
    private RedisTemplate<Long, Long> redisTemplateSetLongSet;

    public static final String TRANSACTION_KEY = "com:skb:demo:transactionKey";


    @Override
    public TransactionDto getTransactionByTransactionId(Long transactionId) {

        return (TransactionDto) this.redisTemplate.opsForHash().get(TRANSACTION_KEY, transactionId);
    }


    @Override
    public void saveTransactionByTransactionId(Long transactionId, TransactionDto transactionDto) {

        redisTemplate.opsForHash().put(TRANSACTION_KEY, transactionId, transactionDto);
    }


    @Override
    public void addTransactionIdToTypeSet(String type, Long transactionId) {

        redisTemplateStringSet.opsForSet().add(type, transactionId);
    }

    @Override
    public void addTransactionIdToParentIdSet(Long ParentTransactionId, Long childTransactionId) {

        redisTemplateSetLongSet.opsForSet().add(ParentTransactionId, childTransactionId);
    }

    @Override
    public Set<Long> getChildsByParentIdSet(Long ParentTransactionId) {

        return redisTemplateSetLongSet.opsForSet().members(ParentTransactionId);
    }

    @Override
    public Set<Long> getKeysByType(String type) {

        return redisTemplateStringSet.opsForSet().members(type);
    }

}
