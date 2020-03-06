package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;


import static globals.Constants.Relics.EVIDENCE_ZIP;

public class EvidenceZip extends CustomRelic {
    private static final String ID = EVIDENCE_ZIP;

    public EvidenceZip() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EvidenceZip();
    }

    @Override
    public void atBattleStart() {
        boolean isBossCombat = false;
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS){
                isBossCombat = true;
                break;
            }
        }

        if (!AbstractDungeon.getCurrRoom().eliteTrigger && !isBossCombat) {
            this.flash();
            float MODIFIER_AMT = 0.2F;
            for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.currentHealth > (int)((float)m.maxHealth * (1.0F - MODIFIER_AMT))) {
                    m.currentHealth = (int)((float)m.maxHealth * (1.0F - MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
}
