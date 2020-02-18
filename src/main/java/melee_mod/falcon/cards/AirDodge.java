package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.LoseBufferPower;
import melee_mod.falcon.powers.PercentPower;

public class AirDodge extends CustomCard {
    private static final String ID = Constants.CardNames.AIR_DODGE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BUFFER = 1;
    private static final int PERCENT = 20;
    private static final int UPGRADE_PERCENT = -10;

    public AirDodge() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.FALCON_BLUE, CardRarity.UNCOMMON, CardTarget.NONE);

        this.baseMagicNumber = this.magicNumber = PERCENT;
    }

    @Override
    public AbstractCard makeCopy() {
        return new AirDodge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PERCENT);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, BUFFER)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseBufferPower(p, BUFFER)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PercentPower(p, this.magicNumber)));
    }
}
