package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.BMoveSpecialistPower;
import melee_mod.falcon.powers.BurnPower;

public class BMoveSpecialist extends CustomCard {
    private static final String ID = Constants.CardNames.B_MOVE_SPECIALIST;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public BMoveSpecialist() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.FALCON_BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BMoveSpecialist();
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
        this.addToBot(new ApplyPowerAction(player, player, new BMoveSpecialistPower(player, 1)));
    }
}
