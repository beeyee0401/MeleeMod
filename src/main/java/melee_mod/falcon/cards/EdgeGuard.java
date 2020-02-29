package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;

public class EdgeGuard extends CustomCard {
    private static final String ID = Constants.CardNames.EDGE_GUARD;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;

    public EdgeGuard() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE,
                CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EdgeGuard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && canMonsterBeKilled(m)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (canMonsterBeKilled(monster)){
            DamageInfo info = new DamageInfo(monster, monster.currentHealth, DamageInfo.DamageType.HP_LOSS);
            this.addToBot(new DamageAction(monster, info));
        }
    }

    private boolean canMonsterBeKilled(AbstractMonster m){
        boolean canBeKilled = false;
        double monsterHealth = (double) m.currentHealth / m.maxHealth;
        if (m.type == AbstractMonster.EnemyType.NORMAL) {
            if (monsterHealth <= 0.5){
                canBeKilled = true;

            }
        } else if (this.upgraded && m.type == AbstractMonster.EnemyType.ELITE) {
            if (monsterHealth <= 0.35){
                canBeKilled = true;
            }
        }
        return canBeKilled;
    }
}
