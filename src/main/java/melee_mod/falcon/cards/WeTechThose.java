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
import melee_mod.falcon.powers.WeTechThosePower;

public class WeTechThose extends CustomCard {
    private static final String ID = Constants.CardNames.WE_TECH_THOSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int MULTIPLIER = 2;
    private static final int UPGRADE_MULTIPLIER = 1;

    public WeTechThose() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = MULTIPLIER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeTechThosePower(p, this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new WeTechThose();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTIPLIER);
            this.rawDescription = "If you take 5 or more damage, gain Dexterity equal to 30% of the damage that expires at the end of the next turn.";
            this.initializeDescription();
        }
    }
}
