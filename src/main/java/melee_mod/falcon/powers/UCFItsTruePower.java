package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.PhantomHit;

import static globals.Constants.Powers.UCF_ITS_TRUE;

public class UCFItsTruePower extends AbstractPower {
    public static final String POWER_ID = UCF_ITS_TRUE;
    public static final String NAME = "UCF: It's True";

    public UCFItsTruePower(AbstractCreature owner, int amount) {
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
        this.description = "When an enemy dies, add " + this.amount + " Phantom " + (this.amount > 1 ? "Hits" : "Hit") + " to your Draw Pile";
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && m.isDying){
                    this.addToBot(new MakeTempCardInDrawPileAction(new PhantomHit(), 1, true, true));
                }
            }
        }
    }
}
