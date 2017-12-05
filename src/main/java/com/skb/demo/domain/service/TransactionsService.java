package com.skb.demo.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skb.demo.domain.dto.TransactionDto;
import com.skb.demo.storeLogic.redis.repo.TransactionRepository;


@Service
public class TransactionsService  {

    @Autowired
    private TransactionRepository transactionRepository;


    public boolean saveTransaction(Long transactionId, TransactionDto transactionDto) {

        if (transactionId != null && transactionDto != null && transactionDto.getType() != null) {

        	transactionRepository.saveTransactionByTransactionId(transactionId, transactionDto);
        	transactionRepository.addTransactionIdToTypeSet(transactionDto.getType(), transactionId);
            if (transactionDto.getParentId() != null) {
            	transactionRepository.addTransactionIdToParentIdSet(
                		transactionDto.getParentId(), transactionId);
            }
            return true;
        }
        return false;
    }


    public TransactionDto getTransaction(Long transactionId) {

        if (transactionId != null) {

            return transactionRepository.getTransactionByTransactionId(transactionId);
        }
        return null;
    }


    public Set<Long> getAllTransactionsKeysByType(String type) {

        if (type != null) {
            return transactionRepository.getKeysByType(type);
        }
        return null;
    }


    public Double getChildsAmountSumByTransactionId(Long transactionId) {

        return incrementTransaction(transactionId);
    }

    private Double incrementTransaction(Long transactionId) {

        TransactionDto transactionDto =
        		transactionRepository.getTransactionByTransactionId(transactionId);

        Double sum = new Double(0);
        if (transactionDto.getAmount() > 0) {
            sum += transactionDto.getAmount();
        }
        Set<Long> childs = transactionRepository.getChildsByParentIdSet(transactionId);

        if (!childs.isEmpty()) {
            sum += childs.stream().mapToDouble(i -> incrementTransaction(i)).sum();
        }
        return sum;
    }

}
