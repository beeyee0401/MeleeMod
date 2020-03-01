package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import globals.Constants;

public class BurnPower extends AbstractPower implements HealthBarRenderPower {
    private static final String POWER_ID = Constants.Powers.BURN;
    private static final String NAME = "Burn";
    private static final String DESCRIPTION = "Take damage equal to two times the Burn stacks at the end of the turn. Reduce Burn by 1 each turn.";
    private final int multiplier;

    public BurnPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.multiplier = AbstractDungeon.player.hasRelic(Constants.Relics.B_MOVE_USER) ? 3 : 2;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION;
    }

    @Override
    public void atStartOfTurn() {
        DamageInfo info = new DamageInfo(this.owner, this.amount * this.multiplier, DamageInfo.DamageType.HP_LOSS);
        this.addToBot(new DamageAction(this.owner, info, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(Constants.Powers.B_MOVE_SPECIALIST)){
            for (int i = 0; i < p.getPower(Constants.Powers.B_MOVE_SPECIALIST).amount; i++) {
                DamageInfo info = new DamageInfo(this.owner, this.amount * this.multiplier, DamageInfo.DamageType.HP_LOSS);
                this.addToBot(new DamageAction(this.owner, info, AbstractGameAction.AttackEffect.FIRE));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount * this.multiplier;
    }

    @Override
    public Color getColor() {
        return Color.GOLD;
    }
}
