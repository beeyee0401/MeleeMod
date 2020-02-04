package falcon_mod.falcon.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import falcon_mod.FalconCharacterMod;
import falcon_mod.falcon.powers.AirWobblingPower;

public class AirWobbling extends CustomRelic {
    public static final String ID = "AirWobbling";
    private static final int COMBO_TO_ADD = 1;

    public AirWobbling() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)),
                RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AirWobblingPower(AbstractDungeon.player, COMBO_TO_ADD)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AirWobbling();
    }
}
