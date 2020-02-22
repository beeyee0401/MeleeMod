package melee_mod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DamagePlusTwo extends DynamicVariable {
    public String key() {
        return "melee:D+2";
    }

    public boolean isModified(final AbstractCard card) {
        return card.isDamageModified;
    }

    public int value(final AbstractCard card) {
        return card.damage + 2;
    }

    public int baseValue(final AbstractCard card) {
        return card.baseDamage + 2;
    }

    public boolean upgraded(final AbstractCard card) {
        return card.upgradedDamage;
    }
}
