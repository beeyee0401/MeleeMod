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
import melee_mod.falcon.powers.CrowdPleaserPower;

public class BabActivated extends CustomCard {
    private static final String ID = Constants.CardNames.BAB_ACTIVATED;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_CROWDPLEASER = 2;
    private static final int UPGRADE_CROWDPLEASER = 1;

    public BabActivated() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BASE_CROWDPLEASER;
    }

    @Override
    public AbstractCard makeCopy() {
        return new BabActivated();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_CROWDPLEASER);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ApplyPowerAction(player, player, new CrowdPleaserPower(player, this.magicNumber)));
    }
}
