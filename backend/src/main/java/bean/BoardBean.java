package bean;

import domain.card.BoardCard;
import domain.card.TextCard;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BoardBean {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    public void generateContentForType(BoardCard boardCard) {
        switch(boardCard.getCardType()) {
            case text_card:
                TextCard textCard = new TextCard();
                textCard.setBoardCard(boardCard);

                entityManager.persist(textCard);
                break;
        }
    }

}
