package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.MOONWALKING;

public class MoonwalkingPower extends AbstractPower {
    private static final String POWER_ID = MOONWALKING;
    private static final String NAME = "Moonwalk";

    public MoonwalkingPower(AbstractCreature owner, int amount) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("At the start of your turn, gain ");

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        sb.append(".");
        this.description = sb.toString();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new GainEnergyAction(this.amount));
    }
}
