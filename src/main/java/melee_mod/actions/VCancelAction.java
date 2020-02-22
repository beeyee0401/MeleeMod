package melee_mod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VCancelAction  extends AbstractGameAction {
    private int extraCards;

    public VCancelAction(int extraCards) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.extraCards = extraCards;
    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.SKILL) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(extraCards));
                break;
            }
        }

        this.isDone = true;
    }
}

