package com.demo.cards.Service.Impl;

import com.demo.cards.Constants.CardsConstants;
import com.demo.cards.Entity.Cards;
import com.demo.cards.Exceptions.AlreadyExistsException;
import com.demo.cards.Exceptions.ResourceNotFoundException;
import com.demo.cards.Mapper.CardsMapper;
import com.demo.cards.Repository.CardsRepository;
import com.demo.cards.Service.iCardsService;
import com.demo.cards.dto.CardsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements iCardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCards(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()) {
            throw new AlreadyExistsException("Cards already exists");
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCards(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber)
        );
        return CardsMapper.mapToCardsDto(new CardsDto(), cards);
    }

    @Override
    public boolean updateCards(CardsDto cardsDto) {
        boolean isUpdated = false;
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards","mobileNumber",cardsDto.getMobileNumber())
        );
        CardsMapper.mapToCards(cards, cardsDto);
        cardsRepository.save(cards);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    public boolean deleteCards(String mobileNumber) {
        boolean isDeleted = false;
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber)
        );
        cardsRepository.delete(cards);
        isDeleted = true;
        return isDeleted;
    }
}
