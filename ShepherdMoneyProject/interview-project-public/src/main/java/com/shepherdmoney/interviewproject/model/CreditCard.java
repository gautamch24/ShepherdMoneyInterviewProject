package com.shepherdmoney.interviewproject.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;



import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String issuanceBank;

    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    @OneToMany(mappedBy = "credit_card_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceHistory> balanceHistory = new ArrayList<>();


    public void addBalanceHistory(LocalDate date, double balance) {
        BalanceHistory history = new BalanceHistory(date, balance);
        history.setCreditCard(this);
        balanceHistory.add(history);
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
