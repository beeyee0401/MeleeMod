package melee_mod.falcon.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import melee_mod.falcon.cards.keyword_card_helpers.FinisherCardHelper;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.ComboPointPower;
import globals.Constants;

import static melee_mod.falcon.patches.CustomTags.AERIAL;

public class Knee extends CustomCard {
    public static final String ID = Constants.CardNames.KNEE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_DAMAGE = 16;
    private static final int UPGRADE_DAMAGE = 4;

    public Knee() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
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
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster monster, float tmp) {
        return FinisherCardHelper.getFinisherDamage(tmp, monster);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        FinisherCardHelper.dealDamage(player, monster, this.damage, this.damageTypeForTurn);
        if (!monster.hasPower(ComboPointPower.POWER_ID)) {
            ComboCardHelper.addComboPoint(monster);
            if (player.hasPower(Constants.Powers.AIR_WOBBLING)){
                ComboCardHelper.addComboPoint(monster);
            }
        } else {
            FinisherCardHelper.removeComboPoints(monster);
        }
    }
}
