package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.PercentPower;

public class HomieStock extends CustomCard {
    private static final String ID = Constants.CardNames.HOMIE_STOCK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    public HomieStock() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HomieStock();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ApplyPowerAction(player, player, new PercentPower(player, 150)));
    }
}
