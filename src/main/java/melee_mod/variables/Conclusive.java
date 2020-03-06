package melee_mod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class Conclusive extends DynamicVariable {
    public String key() {
        return "melee:C";
    }

    public boolean isModified(final AbstractCard card) {
        return false;
    }

    public int value(final AbstractCard card) {
        return card.misc;
    }

    public int baseValue(final AbstractCard card) {
        return card.misc;
    }

    public boolean upgraded(final AbstractCard card) {
        return false;
    }
}
