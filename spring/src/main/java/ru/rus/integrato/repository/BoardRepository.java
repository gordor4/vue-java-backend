package ru.rus.integrato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rus.integrato.domain.board.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> getBoardsByOwnerId(int id);
}
