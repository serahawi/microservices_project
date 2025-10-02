package com.demo.cards.Service;


import com.demo.cards.dto.CardsDto;

public interface iCardsService {

    void createCards(String mobileNumber);
    CardsDto fetchCards(String mobileNumber);
    boolean updateCards(CardsDto cardsDto);
    boolean deleteCards(String mobileNumber);
}
