package falcon_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.AbstractCardEnum;
import falcon_mod.falcon.powers.EdgeCancellingPower;
import globals.Constants;

public class EdgeCancel extends CustomCard {
    private static final String ID = Constants.CardNames.EDGE_CANCEL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public EdgeCancel() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EdgeCancel();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = "Your Aerials cost !M! less [E] this turn. Draw a card. Gain !M! [E]. Exhaust.";
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new EdgeCancellingPower(player, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.magicNumber));
        if (this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
        }
    }
}
