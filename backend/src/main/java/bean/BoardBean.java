package bean;

import domain.card.BoardCard;
import domain.card.TextCard;
import domain.front.BoardCardParams;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class BoardBean {

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    public void generateContentForType(BoardCard boardCard) {
        switch (boardCard.getCardType()) {
            case text_card:
                TextCard textCard = new TextCard();
                textCard.setBoardCard(boardCard);

                entityManager.persist(textCard);
                break;
        }
    }

    public Object getCardContent(BoardCardParams boardCardParams) {
        BoardCard.CardType cardType = BoardCard.CardType.valueOf(boardCardParams.getCardType());

        switch (cardType) {
            case text_card:
                TypedQuery<TextCard> textCardContent = entityManager.createNamedQuery(TextCard.FIND_TEXT_CARD_CONTENT, TextCard.class);
                textCardContent.setParameter("id", boardCardParams.getBoardCardId());

                return textCardContent.getSingleResult();
            default:
                return null;
        }
    }

}
