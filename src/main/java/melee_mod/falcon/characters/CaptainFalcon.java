package melee_mod.falcon.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.Knee;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

import java.util.ArrayList;

public class CaptainFalcon extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;

    // Make the Orb textures a stock icon
    public static final String[] orbTextures = {
            FalconCharacterMod.makePath("char/orb/layer1.png"),
            FalconCharacterMod.makePath("char/orb/layer2.png"),
            FalconCharacterMod.makePath("char/orb/layer3.png"),
            FalconCharacterMod.makePath("char/orb/layer4.png"),
            FalconCharacterMod.makePath("char/orb/layer5.png"),
            FalconCharacterMod.makePath("char/orb/layer6.png"),
            FalconCharacterMod.makePath("char/orb/layer1d.png"),
            FalconCharacterMod.makePath("char/orb/layer2d.png"),
            FalconCharacterMod.makePath("char/orb/layer3d.png"),
            FalconCharacterMod.makePath("char/orb/layer4d.png"),
            FalconCharacterMod.makePath("char/orb/layer5d.png"),
    };

    public CaptainFalcon(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, FalconCharacterMod.makePath("char/orb/vfx.png"), new SpriterAnimation(FalconCharacterMod.makePath("char/cf_sprite.scml")));

        initializeClass(null, FalconCharacterMod.makePath(FalconCharacterMod.FALCON_SHOULDER_2),
                FalconCharacterMod.makePath(FalconCharacterMod.FALCON_SHOULDER_1),
                FalconCharacterMod.makePath(FalconCharacterMod.FALCON_CORPSE),
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public String getPortraitImageName() {
        return null;
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Constants.CardNames.JAB);
        retVal.add(Constants.CardNames.JAB);
        retVal.add(Constants.CardNames.JAB);
        retVal.add(Constants.CardNames.JAB);
        retVal.add(Constants.CardNames.SHIELD);
        retVal.add(Constants.CardNames.SHIELD);
        retVal.add(Constants.CardNames.SHIELD);
        retVal.add(Constants.CardNames.SHIELD);
        retVal.add(Constants.CardNames.NEUTRAL_AIR);
        retVal.add(Constants.CardNames.BACK_AIR);

        return retVal;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "Captain Falcon";
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Knee();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.FALCON_BLUE;
    }

    @Override
    public Color getCardTrailColor() {
        return FalconCharacterMod.BLUE.cpy();
    }

    @Override
    public String getSpireHeartText() {
        return "heart text here";
    }

    @Override
    public Color getSlashAttackColor() {
        return FalconCharacterMod.BLUE.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getVampireText() {
        return "Want Falcon to become a vampire?";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CaptainFalcon(name, chosenClass);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "Captain Falcon";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public Color getCardRenderColor() {
        return FalconCharacterMod.BLUE.cpy();
    }


    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Constants.Relics.TEAM_BEER_MANG0);
        UnlockTracker.markRelicAsSeen(Constants.Relics.TEAM_BEER_MANG0);
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("Captain Falcon", "Show him your moves.",
                70, 70, 0, 99, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

}
