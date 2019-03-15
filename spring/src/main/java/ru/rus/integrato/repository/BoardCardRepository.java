package ru.rus.integrato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rus.integrato.domain.card.BoardCard;

import java.util.List;

public interface BoardCardRepository extends JpaRepository<BoardCard, Integer> {
    List<BoardCard> getBoardCardByBoardId(int boardId);
}
