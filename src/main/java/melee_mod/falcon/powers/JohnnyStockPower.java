package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import melee_mod.falcon.powers.helpers.CardCostHelper;
import melee_mod.falcon.powers.interfaces.ICostReducingBuff;

import java.util.ArrayList;

import static globals.Enums.CostAction.REDUCE;

public class JohnnyStockPower extends AbstractPower implements ICostReducingBuff, NonStackablePower {
    private static final String POWER_ID = Constants.Powers.JOHNNY_STOCK;
    private static final String NAME = "Johnny Stock";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();

    public JohnnyStockPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.setCardGroup();
    }

    @Override
    public void updateDescription() {
        this.description = "While you have 20% or less of your max health, ALL attack cards add an additional combo-point to enemies and cost " + this.amount + " less [E]";
    }

    @Override
    public void onInitialApplication() {
        CardCostHelper.setCardCosts(this.cardsToChange, REDUCE, this.amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK ){
            if (card.target == AbstractCard.CardTarget.ENEMY && action.target != null){
                ComboCardHelper.addComboPoint(action.target);
                this.flash();
            } else if (card.target == AbstractCard.CardTarget.ALL || card.target == AbstractCard.CardTarget.ALL_ENEMY){
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    ComboCardHelper.addComboPoint(m);
                }
                this.flash();
            }
        }
    }

    @Override
    public void onRemove() {
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        CardCostHelper.initializeBuffCosts(this);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (c.type == AbstractCard.CardType.ATTACK && !c.isCostModifiedForTurn) {
            CardCostHelper.initializeBuffCostsForCard(c);
        }
    }

    @Override
    public boolean shouldAddToCardGroup(AbstractCard c){
        return c.type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public int getReduction(){
        return this.amount;
    }

    @Override
    public ArrayList<AbstractCard> getCardsToChange() {
        return this.cardsToChange;
    }
}
