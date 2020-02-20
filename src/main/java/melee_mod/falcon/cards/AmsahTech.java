package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;

import static globals.Constants.CardNames.AMSAH_TECH;

public class AmsahTech extends CustomCard {
    private static final String ID = AMSAH_TECH;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY = 2;
    private static final int DRAW = 2;

    public AmsahTech() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmsahTech();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            this.addToBot(new GainEnergyAction(UPGRADE_ENERGY));
        } else {
            this.addToBot(new GainEnergyAction(ENERGY_GAIN));
        }

        this.addToBot(new DrawCardAction(p, DRAW));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
