package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.BurnPower;
import globals.Constants;

public class FalconKick extends CustomCard {
    private static final String ID = Constants.CardNames.FALCON_KICK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_BLOCK = 5;
    private static final int BASE_DAMAGE = 5;
    private static final int UPGRADE_DAMAGE_BLOCK = 2;
    private static final int BASE_BURNS = 1;
    private static final int UPGRADE_BURN = 1;

    public FalconKick() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.block = this.baseBlock = BASE_BLOCK;
        this.magicNumber = this.baseMagicNumber = BASE_BURNS;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FalconKick();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_BLOCK);
            this.upgradeBlock(UPGRADE_DAMAGE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_BURN);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, this.block));
        DamageInfo info = new DamageInfo(player, damage, damageTypeForTurn);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.FIRE);
        AbstractDungeon.actionManager.addToBottom(action);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new BurnPower(monster, this.magicNumber)));
    }
}
