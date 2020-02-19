package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;

public class CommentatorsCurse extends CustomCard {
    private static final String ID = Constants.CardNames.COMMENTATORS_CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public CommentatorsCurse() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.CURSE, AbstractCardEnum.FALCON_BLUE,
                CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CommentatorsCurse();
    }

    @Override
    public void upgrade(){
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        monster.rollMove();
        monster.createIntent();
    }
}
