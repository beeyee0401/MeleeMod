package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.BurnPower;

public class SuperSpicyCurry extends CustomCard {
    private static final String ID = Constants.CardNames.SUPER_SPICY_CURRY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_BURN = 1;
    private static final int UPGRADE_BURN = 1;

    public SuperSpicyCurry() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = BASE_BURN;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperSpicyCurry();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BURN);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int burn = this.magicNumber;
        if (player.hasPower(StrengthPower.POWER_ID)){
            burn += player.getPower(StrengthPower.POWER_ID).amount;
        }
        if (player.hasPower(DexterityPower.POWER_ID)){
            burn += player.getPower(DexterityPower.POWER_ID).amount;
        }
        this.addToBot(new ApplyPowerAction(monster, player, new BurnPower(monster, burn)));
    }
}
