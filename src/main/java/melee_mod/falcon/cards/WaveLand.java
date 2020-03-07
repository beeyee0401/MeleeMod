package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;

public class WaveLand extends CustomCard {
    private static final String ID = Constants.CardNames.WAVE_LAND;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BASE_WEAK = 1;
    private static final int UPGRADE_WEAK = 1;

    public WaveLand() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.FALCON_BLUE, CardRarity.COMMON, CardTarget.ENEMY);

        this.block = this.baseBlock = BLOCK_AMT;
        this.magicNumber = this.baseMagicNumber = BASE_WEAK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false)));
    }

    public AbstractCard makeCopy() {
        return new WaveLand();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_WEAK);
            this.upgradeBlock(UPGRADE_BLOCK);
        }
    }
}
