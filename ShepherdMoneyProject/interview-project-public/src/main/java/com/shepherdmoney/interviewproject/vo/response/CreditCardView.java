package com.shepherdmoney.interviewproject.vo.response;

import com.shepherdmoney.interviewproject.model.CreditCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreditCardView {

    private int id;
    private String issuanceBank;

    private String number;

    public CreditCardView(CreditCard creditCard){
        this.id = creditCard.getId();
        this.issuanceBank = creditCard.getIssuanceBank();
        this.number = creditCard.getNumber();
    }
}
