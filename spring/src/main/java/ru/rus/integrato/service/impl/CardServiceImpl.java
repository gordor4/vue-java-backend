package ru.rus.integrato.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rus.integrato.domain.card.BoardCard;
import ru.rus.integrato.domain.card.TextCard;
import ru.rus.integrato.domain.front.BoardCardParams;
import ru.rus.integrato.repository.TextCardRepository;
import ru.rus.integrato.service.CardService;

@Component
public class CardServiceImpl implements CardService {

    @Autowired
    private TextCardRepository textCardRepository;

    public void generateContentForType(BoardCard boardCard) {
        switch (boardCard.getCardType()) {
            case text_card:
                TextCard textCard = new TextCard();
                textCard.setBoardCardId(boardCard.getId());

                textCardRepository.save(textCard);
                break;
        }
    }

    public Object getCardContent(BoardCardParams boardCardParams) {
        BoardCard.CardType cardType = BoardCard.CardType.valueOf(boardCardParams.getCardType());

        switch (cardType) {
            case text_card:
                return textCardRepository.getTextCardByBoardCardId(boardCardParams.getBoardCardId());
            default:
                return null;
        }
    }
}
