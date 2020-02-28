package melee_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class RetainKeywordCardAction extends AbstractGameAction {
    private String keyword;

    public RetainKeywordCardAction(AbstractCreature source, int amount, String keyword) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.keyword = keyword;
    }

    public void update() {
        ArrayList<AbstractCard> cardsInHand = AbstractDungeon.player.hand.group;
        CardGroup keywordCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c: cardsInHand) {
            if (c.type == AbstractCard.CardType.ATTACK && (c.keywords.contains(keyword) || c.keywords.contains(keyword.toLowerCase()))){
                c.stopGlowing();
                keywordCards.addToBottom(c);
            }
        }
        int numKeywords = keywordCards.size();
        if (numKeywords > 0) {
            if (this.duration == 0.5F) {
                String pluralize = this.amount > 1 ? " cards to Retain." : " card to Retain.";
                AbstractDungeon.gridSelectScreen.open(keywordCards, this.amount, true, "Choose up to " + this.amount + " " + this.keyword + pluralize);
            } else {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                    while(var1.hasNext()) {
                        AbstractCard c = (AbstractCard)var1.next();
                        c.retain = true;
                    }

                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
            }
            this.tickDuration();
        } else {
            this.isDone = true;
        }
    }
}