package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.BurnPower;
import globals.Constants;

public class FalconDive extends CustomCard {
    private static final String ID = Constants.CardNames.FALCON_DIVE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_BLOCK = 6;
    private static final int BASE_BURNS = 1;
    private static final int UPGRADE_BURN = 1;

    public FalconDive() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.block = this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber = BASE_BURNS;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FalconDive();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BURN);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        GainBlockAction action = new GainBlockAction(player, this.block);
        AbstractDungeon.actionManager.addToBottom(action);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new BurnPower(monster, this.magicNumber)));
        if (!this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, 1, false)));
        }
    }
}
