package melee_mod.falcon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class KeywordDrawPileToHandAction extends AbstractGameAction {
    private static final String[] TEXT;
    private AbstractPlayer player;
    private CardType typeToCheck;
    private String keywordToCheck;
    private String cardIdToExclude;
    private int numberOfCards;
    private boolean optional;

    public KeywordDrawPileToHandAction(int amount, CardType type, String keyword, String exclusion, int numberOfCards, boolean optional) {
        this.player = AbstractDungeon.player;
        this.setValues(this.player, this.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_MED;
        this.typeToCheck = type;
        this.keywordToCheck = keyword;
        this.cardIdToExclude = exclusion;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public KeywordDrawPileToHandAction(int amount, CardType type, String keyword, String exclusion) {
        this(amount, type, keyword, exclusion, 1, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                AbstractCard card;
                Iterator var6;
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    var6 = this.player.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        card = (AbstractCard)var6.next();
                        cardsToMove.add(card);
                    }

                    var6 = cardsToMove.iterator();

                    while(var6.hasNext()) {
                        card = (AbstractCard)var6.next();
                        if (this.player.hand.size() == 10) {
                            this.player.drawPile.moveToDiscardPile(card);
                            this.player.createHandIsFullDialog();
                        } else {
                            this.player.drawPile.moveToHand(card, this.player.drawPile);
                        }
                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroupType.UNSPECIFIED);
                    var6 = this.player.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        card = (AbstractCard)var6.next();
                        if (this.typeToCheck == card.type && !cardIdToExclude.equals(card.cardID) &&
                                (card.keywords.contains(this.keywordToCheck) || card.keywords.contains(this.keywordToCheck.toLowerCase()))) {
                            temp.addToRandomSpot(card);
                        }
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (this.player.hand.size() == 10) {
                        this.player.drawPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                    } else {
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
