package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;

public class Taunt extends CustomCard {
    private static final String ID = Constants.CardNames.TAUNT;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int VULNERABLE = 3;
    private static final int UPGRADED_VULNERABLE = -2;

    public Taunt() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = VULNERABLE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 4)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, this.magicNumber, false)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -1)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Taunt();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_VULNERABLE);
        }
    }
}
