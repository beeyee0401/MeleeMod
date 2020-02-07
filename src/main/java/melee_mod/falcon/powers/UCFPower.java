package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.ShieldDropTemp;

import static globals.Constants.Powers.UCF;

public class UCFPower extends AbstractPower {
    public static final String POWER_ID = UCF;
    public static final String NAME = "UCF";

    public UCFPower(AbstractCreature owner, int amount) {
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
        this.description = "Adds " + this.amount + " Shield " + (this.amount > 1 ? "Drops" : "Drop" ) + " with ethereal to your hand at the start of each turn";
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new MakeTempCardInHandAction(new ShieldDropTemp(), this.amount, false));
        }
    }
}
