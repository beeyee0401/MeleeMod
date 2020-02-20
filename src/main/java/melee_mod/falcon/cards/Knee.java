package melee_mod.falcon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

import static melee_mod.falcon.patches.CustomTags.AERIAL;

public class Knee extends CustomCard {
    private static final String ID = Constants.CardNames.KNEE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 4;

    public Knee() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.tags.add(AERIAL);
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Knee();
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
        if (!monster.hasPower(Constants.Powers.COMBO_POINTS)) {
            ComboCardHelper.addComboPoint(monster);
            if (player.hasPower(Constants.Powers.AIR_WOBBLING)){
                ComboCardHelper.addComboPoint(monster);
            }
        }
    }
}
