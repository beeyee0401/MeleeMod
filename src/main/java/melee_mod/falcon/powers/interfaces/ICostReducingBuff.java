package melee_mod.falcon.powers.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public interface ICostReducingBuff {
    ArrayList<AbstractCard> cardsToChange = new ArrayList<>();
    int getReduction();
}
