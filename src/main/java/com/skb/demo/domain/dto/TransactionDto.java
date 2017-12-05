package com.skb.demo.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 6561144049794706544L;
		
	private Long id;
    private Double amount;
    private String type;
    @JsonProperty("parent_id")
    private Long parentId;

    public TransactionDto(Long id, Double amount, String type, Long parentId) {
    	this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public TransactionDto() {

    }

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    // Only for test
    public String toJsonString() {

        return "{\"amount\":\"" + amount + "\"" +
                ",\"type\":" + type +
                ",\"parentId\":" + parentId +
                "}";
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionDto that = (TransactionDto) o;

        if (!amount.equals(that.amount)) return false;
        if (!type.equals(that.type)) return false;
        return parentId != null ? parentId.equals(that.parentId) : that.parentId == null;

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
