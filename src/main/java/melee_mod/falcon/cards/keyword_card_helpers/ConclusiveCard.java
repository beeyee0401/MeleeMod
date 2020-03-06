package melee_mod.falcon.cards.keyword_card_helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import globals.Constants;

public abstract class ConclusiveCard extends CustomCard {

    public ConclusiveCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, int requiredComboPoints) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.misc = requiredComboPoints;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster monster) {
        if (monster != null) {
            if (monster.hasPower(Constants.Powers.COMBO_POINTS) && monster.getPower(Constants.Powers.COMBO_POINTS).amount >= this.misc){
                return this.cardPlayable(monster) && this.hasEnoughEnergy();
            }
        } else {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.hasPower(Constants.Powers.COMBO_POINTS) && m.getPower(Constants.Powers.COMBO_POINTS).amount >= this.misc){
                    return this.hasEnoughEnergy();
                }
            }
        }
        return false;
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster m, float tmp) {
        if (m != null) {
            if (canUse(player, m)) {
                this.beginGlowing();
            } else {
                this.stopGlowing();
            }
        }
        return tmp;
    }
}
