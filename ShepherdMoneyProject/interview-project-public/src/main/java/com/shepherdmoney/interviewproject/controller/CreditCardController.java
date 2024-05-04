package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.lang.String;
import java.util.stream.Collectors;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class CreditCardController {

    private final CreditCardRepository CreditCardRepository;
    private final UserRepository userRepository;

    public CreditCardController(CreditCardRepository creditCardRepository, UserRepository userRepository) {
        this.CreditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        // TODO: Create a credit card entity, and then associate that credit card with user with given userId
        //       Return 200 OK with the credit card id if the user exists and credit card is successfully associated with the user
        //       Return other appropriate response code for other exception cases
        //       Do not worry about validating the card number, assume card number could be any arbitrary format and length

        int userId = payload.getUserId();
        String cardIssuanceBank = payload.getCardIssuanceBank();
        String cardNumber = payload.getCardNumber();
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        CreditCard creditCard = new CreditCard();
        creditCard.setIssuanceBank(cardIssuanceBank);
        creditCard.setNumber(cardNumber);
        creditCard.setUser(user);
        
        CreditCard savedCreditCard = CreditCardRepository.save(creditCard);
        return ResponseEntity.ok(savedCreditCard.getId());
    }

    @GetMapping("/credit-card:all")
    public ResponseEntity<List<CreditCardView>> getAllCardOfUser(@RequestParam int userId) {
        List<CreditCard> creditCards = CreditCardRepository.findByUserId(userId);
        if (creditCards.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }

        List<CreditCardView> creditCardViews = creditCards.stream()
                .map(creditCard -> new CreditCardView(creditCard.getId(), creditCard.getIssuanceBank(), creditCard.getNumber()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(creditCardViews);
    }

    @GetMapping("/credit-card:user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        // TODO: Given a credit card number, efficiently find whether there is a user associated with the credit card
        //       If so, return the user id in a 200 OK response. If no such user exists, return 400 Bad Request

        Optional <CreditCard> optionalCreditCard = CreditCardRepository.findByNumber(creditCardNumber);

        if(optionalCreditCard.isPresent()){
            CreditCard creditCard = optionalCreditCard.get();
            User user = creditCard.getUser();

            if(user!= null){
                return ResponseEntity.ok().body(user.getId());
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

}
