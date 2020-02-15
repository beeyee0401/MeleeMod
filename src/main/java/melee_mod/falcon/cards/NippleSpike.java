package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import melee_mod.falcon.cards.keyword_card_helpers.FinisherCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;

import static melee_mod.falcon.patches.CustomTags.AERIAL;

public class NippleSpike extends CustomCard {
    public static final String ID = Constants.CardNames.NIPPLE_SPIKE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_DAMAGE = 16;
    private static final int UPGRADE_DAMAGE = 4;

    public NippleSpike() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.tags.add(AERIAL);
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new NippleSpike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DamageInfo info = new DamageInfo(player, damage, damageType);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.SMASH);
        AbstractDungeon.actionManager.addToBottom(action);
        FinisherCardHelper.removeComboPoints(monster);
    }
}
