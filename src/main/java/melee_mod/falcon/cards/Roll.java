package falcon_mod.falcon.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.patches.AbstractCardEnum;
import falcon_mod.falcon.powers.RemoveArtifactsPower;
import globals.Constants;

public class Roll extends CustomCard {
    private static final String ID = Constants.CardNames.ROLL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_BLOCK = 8;
    private static final int DEBUFFS_AVOIDED = 1;
    private static final int UPGRADE_DEBUFFS = 1;

    public Roll() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber = DEBUFFS_AVOIDED;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Roll();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DEBUFFS);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster abstractMonster) {
        GainBlockAction action = new GainBlockAction(player, player, this.block);
        AbstractDungeon.actionManager.addToBottom(action);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ArtifactPower(player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RemoveArtifactsPower(player, this.magicNumber), this.magicNumber));
    }
}
