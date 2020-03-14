package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

public class PhantomHit extends CustomCard {
    private static final String ID = Constants.CardNames.PHANTOM_HIT;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int INTANGIBILITY = 1;
    private static final int UPGRADE_INTANGIBILITY = 1;

    public PhantomHit() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.RARE, CardTarget.ALL);
        this.magicNumber = this.baseMagicNumber = INTANGIBILITY;
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new PhantomHit();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_INTANGIBILITY);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, this.magicNumber)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, player, new IntangiblePlayerPower(m, 1)));
        }
    }
}
