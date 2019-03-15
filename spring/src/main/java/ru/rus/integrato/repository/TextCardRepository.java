package ru.rus.integrato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rus.integrato.domain.card.TextCard;

public interface TextCardRepository extends JpaRepository<TextCard, Integer> {
    TextCard getTextCardByBoardCardId(int id);
}
