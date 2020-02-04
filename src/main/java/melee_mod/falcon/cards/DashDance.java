package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class DashDance extends CustomCard {
    private static final String ID = Constants.CardNames.DASH_DANCE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DRAW = 1;
    private static final int BASE_BLOCK = 6;
    private static final int UPGRADE_BLOCK = 2;
    private static final int UPGRADE_DRAW = 1;

    public DashDance() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.baseBlock = this.block = BASE_BLOCK;
        this.baseMagicNumber = this.magicNumber = BASE_DRAW;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DashDance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_DRAW);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster abstractMonster) {
        GainBlockAction action = new GainBlockAction(player, player, this.block);
        AbstractDungeon.actionManager.addToBottom(action);
        DrawCardAction drawCardAction = new DrawCardAction(player, this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(drawCardAction);
    }
}
