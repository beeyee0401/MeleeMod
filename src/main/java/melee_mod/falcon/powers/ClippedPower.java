package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.CLIPPED;

public class ClippedPower extends AbstractPower {
    private static final String POWER_ID = CLIPPED;
    private static final String NAME = "Clipped";
    public ClippedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.canGoNegative = false;
    }

    @Override
    public void updateDescription() {
        this.description = "Replay the cards played " + (this.amount > 1 ? ("for the next " + this.amount + " turns") : "this turn. All Attacks target one random monster.");
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (!c.cardID.equals(Constants.CardNames.SOMEBODY_CLIP_THAT)) {
                AbstractCard tmp = c.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                if (m != null && tmp.type == AbstractCard.CardType.ATTACK) {
                    tmp.calculateCardDamage(m);
                }
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, 0, true, true), true);
            }
        }
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }
}
