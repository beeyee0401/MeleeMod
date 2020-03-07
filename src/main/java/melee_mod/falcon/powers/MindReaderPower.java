package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Powers.MIND_READER;

public class MindReaderPower extends AbstractPower implements OnLoseBlockPower {
    private static final String POWER_ID = MIND_READER;
    private static final String NAME = "Mind Reader";
    private boolean lostBlockThisTurn = false;
    private boolean damagedThisTurn = false;

    public MindReaderPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "When you block all incoming damage, gain " + this.amount + " Dexterity.";
    }

    @Override
    public void atEndOfRound() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.lostBlockThisTurn && !this.damagedThisTurn){
            this.flash();
            this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.amount)));
        }
        this.lostBlockThisTurn = false;
        this.damagedThisTurn = false;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        damagedThisTurn = true;
        return damageAmount;
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        this.lostBlockThisTurn = true;
        return i;
    }
}
