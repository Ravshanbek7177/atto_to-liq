package service;

import container.ComponentContainer;
import dto.Card;
import dto.Profile;
import enums.CardStatus;

import repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CardService {

    public void createCard(String number, String exp_date) {
        Card card = ComponentContainer.cardRepository.getCard(number);
        //check
        if (card != null) {
            System.out.println("This card already exists");
            return;
        }
        //>>>>>>
        ComponentContainer.cardRepository.createCard(number, exp_date);
    }

    public void updateCardByNumber(String number, String exp_date) {
        Card card = ComponentContainer.cardRepository.getCard(number);
        if (card == null || !card.getExp_date().equals(exp_date)) {
            System.out.println("Card not found");
        } else {
            System.out.print("Enter new card number : ");
            String newCardNum = ComponentContainer.stringScanner.next();
            System.out.print("Enter new card exp_date : ");
            String new_exp_date = ComponentContainer.stringScanner.next();
            Card card1 = ComponentContainer.cardRepository.getCard(newCardNum);
            if (card1 == null || card1.getNumber().equals(number)) {
                ComponentContainer.cardRepository.updateCardByNumber(card, newCardNum, new_exp_date);
                System.out.println("Successfully");
            } else {
                System.out.println("This card already exists");
            }
        }
    }

    public void changeCardStatus(String numCard, String exp_date) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(exp_date)) {
            System.out.println("Card not found");
        } else {
            if (card.getStatus().equals(CardStatus.BLOCK)) {
                card.setStatus(CardStatus.ACTIVE);
            } else if (card.getStatus().equals(CardStatus.ACTIVE)) {
                card.setStatus(CardStatus.BLOCK);
            }
            ComponentContainer.cardRepository.updateCardStatus(card);

        }
    }

    public void deleteCard(String numCard, String expDate) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || (!card.getExp_date().equals(expDate))) {
            System.out.println("Card not found");
            return;
        }
        ComponentContainer.cardRepository.deleteCard(card);
    }

    public List<Card> cardListUser(Profile profile) {
        List<Card> cardList = ComponentContainer.cardRepository.cardListUser(profile);
        return cardList.stream().filter(card -> !card.getStatus().equals(CardStatus.NOT_VISIBLE_USER)).collect(Collectors.toList());
    }

    public void addCard(Profile profile, String numCard, String cardExpDate) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate)) {
            System.out.println("Card not found");
            return;
        } else if (card.getProfile_id() != profile.getId() && card.getProfile_id() != 0) {
            System.out.println("This card belong other profile");
            return;
        }
        ComponentContainer.cardRepository.addCardToUser(profile, card);
    }

    public void changeCardStatusUser(Profile profile, String numCard, String cardExpDate) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate) || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else {
            if (card.getStatus().equals(CardStatus.BLOCK)) {
                card.setStatus(CardStatus.ACTIVE);
            } else if (card.getStatus().equals(CardStatus.ACTIVE)) {
                card.setStatus(CardStatus.BLOCK);
            }
            ComponentContainer.cardRepository.updateCardStatus(card);

        }
    }

    public void deleteCardUser(Profile profile, String numCard, String cardExpDate) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate) || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else {
            card.setStatus(CardStatus.NOT_VISIBLE_USER);
            ComponentContainer.cardRepository.updateCardStatus(card);

        }
    }
    public void refill(Profile profile, String numCard, Double amount) {
        Card card = ComponentContainer.cardRepository.getCard(numCard);
        if (card == null || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else if (card.getStatus().equals(CardStatus.BLOCK)) {
            System.out.println("This card blocked ");
        } else {
            card.setAmount(card.getAmount() + amount);
            ComponentContainer.cardRepository.updateCardBalance(card);
            ComponentContainer.transactionRepository.refill(profile,card,amount);
        }
    }
}
