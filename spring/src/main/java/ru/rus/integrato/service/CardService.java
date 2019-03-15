package ru.rus.integrato.service;

import ru.rus.integrato.domain.card.BoardCard;
import ru.rus.integrato.domain.front.BoardCardParams;

public interface CardService {
    void generateContentForType(BoardCard boardCard);
    Object getCardContent(BoardCardParams boardCardParams);
}
