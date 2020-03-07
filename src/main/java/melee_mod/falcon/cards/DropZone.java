package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import melee_mod.falcon.cards.keyword_card_helpers.ConclusiveCard;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.CrowdPleaserPower;

public class DropZone extends ConclusiveCard {
    private static final String ID = Constants.CardNames.DROP_ZONE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 9;
    private static final int CROWD_PLEASER = 2;
    private static final int UPGRADE_CROWD_PLEASER = 1;
    private static final int REQUIRED_COMBO_POINTS = 1;

    public DropZone() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE, CardRarity.RARE, CardTarget.ENEMY, REQUIRED_COMBO_POINTS);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.magicNumber = this.baseMagicNumber = CROWD_PLEASER;
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DropZone();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_CROWD_PLEASER);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DamageInfo info = new DamageInfo(player, this.damage, this.damageTypeForTurn);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractDungeon.actionManager.addToBottom(action);
        this.addToBot(new ApplyPowerAction(player, player, new CrowdPleaserPower(player, this.magicNumber)));
    }
}
