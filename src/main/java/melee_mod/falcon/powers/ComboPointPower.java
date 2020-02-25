package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import melee_mod.FalconCharacterMod;
import globals.Constants;
import melee_mod.falcon.cards.keyword_card_helpers.FinisherCardHelper;
import melee_mod.falcon.powers.helpers.CardCostHelper;

import java.util.ArrayList;

import static globals.Constants.Powers.*;
import static globals.Enums.CostAction.REDUCE;

public class ComboPointPower extends AbstractPower {
    private static final String POWER_ID = COMBO_POINTS;
    private static final String NAME = "Combo-Points";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();
    private boolean isStartedByComboAndFinisher;

    public ComboPointPower(AbstractCreature owner, int amount, boolean isStartedByComboAndFinisher) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.type = PowerType.DEBUFF;
        // this is super jank, but not sure how to make sure the points don't get consumed immediately since Knee is both a finisher and a combo
        this.isStartedByComboAndFinisher = isStartedByComboAndFinisher;
        setCardGroup();
    }

    @Override
    public void updateDescription() {
        this.description = "When using a Finisher on this target, multiply the damage by 25% for each combo-point consumed. Finisher costs are reduced by [E] for each active combo-point";
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card){
        boolean isFinisher = card.keywords.contains(Constants.Keywords.FINISHER) || card.keywords.contains(Constants.Keywords.FINISHER.toLowerCase());
        boolean isComboCard = card.keywords.contains(Constants.Keywords.COMBO) || card.keywords.contains(Constants.Keywords.COMBO.toLowerCase());
        if (isFinisher || (isComboCard && AbstractDungeon.player.hasPower(Constants.Powers.COMBO_FINISHER))) {
            damage += damage * (this.amount * 0.25);
        }
        return damage;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        reduceCardCosts(stackAmount);
    }

    @Override
    public void onInitialApplication() {
        reduceCardCosts(this.amount);
    }

    @Override
    public void atEndOfRound() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (action.target != null && action.target.hasPower(COMBO_POINTS) && card.type == AbstractCard.CardType.ATTACK &&
                (card.keywords.contains(Constants.Keywords.FINISHER) || card.keywords.contains(Constants.Keywords.FINISHER.toLowerCase())) &&
                !this.isStartedByComboAndFinisher){
            FinisherCardHelper.removeComboPoints(action.target);
        }
        this.isStartedByComboAndFinisher = false;
    }

    @Override
    public void onDeath() {
        onRemove();
        initializeComboPointCosts();
    }

    @Override
    public void onRemove() {
        CardCostHelper.resetCardCost(this.cardsToChange);
        CardCostHelper.initializeBuffCosts(this);
    }

    public static void initializeComboPointCosts(){
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && m.hasPower(COMBO_POINTS)){
                m.getPower(COMBO_POINTS).onInitialApplication();
            }
        }
    }

    private void setCardGroup(){
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.hand.group);
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : allCards) {
            if (c.type == AbstractCard.CardType.ATTACK && (c.keywords.contains(Constants.Keywords.FINISHER) || c.keywords.contains(Constants.Keywords.FINISHER.toLowerCase()))) {
                this.cardsToChange.add(c);
            }
        }
    }

    private void reduceCardCosts(int reduction){
        CardCostHelper.setCardCosts(this.cardsToChange, REDUCE, reduction);
    }
}
