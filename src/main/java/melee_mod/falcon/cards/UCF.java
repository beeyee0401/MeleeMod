package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.UCFItsTruePower;
import melee_mod.falcon.powers.UCFPower;

public class UCF extends CustomCard {
    private static final String ID = Constants.CardNames.UCF;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    public UCF() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.POWER,
                AbstractCardEnum.FALCON_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
//        if (this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UCFItsTruePower(p, 1)));
//        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UCFPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UCF();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgraded = true;
            this.name = "UCF: It's True";
            this.initializeTitle();
            this.rawDescription = DESCRIPTION + "NL When an enemy dies, add a Phantom Hit to your Draw Pile";
            this.initializeDescription();
        }
    }
}
