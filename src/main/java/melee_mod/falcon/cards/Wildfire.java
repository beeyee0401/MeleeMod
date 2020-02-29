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
import melee_mod.falcon.powers.BurnPower;

public class Wildfire extends CustomCard {
    private static final String ID = Constants.CardNames.WILDFIRE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_BURNS = -2;
    private static final int UPGRADE_BURN = 1;

    public Wildfire() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = BASE_BURNS;
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wildfire();
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
        if (monster.hasPower(Constants.Powers.BURN)){
            int resultingBurn = monster.getPower(Constants.Powers.BURN).amount + this.magicNumber;
            this.addToBot(new ApplyPowerAction(monster, player, new BurnPower(monster, this.magicNumber)));
            for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != monster){
                    this.addToBot(new ApplyPowerAction(m, player, new BurnPower(m, resultingBurn)));
                }
            }
        }
    }
}
