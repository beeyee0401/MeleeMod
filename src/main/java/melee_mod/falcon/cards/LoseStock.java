package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class LoseStock extends CustomCard {
    private static final String ID = Constants.CardNames.LOSE_STOCK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int BASE_ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 1;

    public LoseStock() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = BASE_ENERGY_GAIN;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, 11, DamageInfo.DamageType.HP_LOSS);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, info));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, Constants.Powers.PERCENT));
    }

    public AbstractCard makeCopy() {
        return new LoseStock();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_ENERGY_GAIN);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
