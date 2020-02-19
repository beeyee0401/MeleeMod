package melee_mod;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import melee_mod.falcon.CharacterMod;
import melee_mod.falcon.cards.*;
import melee_mod.falcon.characters.CaptainFalcon;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.patches.FalconEnum;
import melee_mod.falcon.relics.AirWobbling;
import globals.Constants;
import melee_mod.falcon.relics.AutoLCanceling;
import melee_mod.falcon.relics.LosersMang0;
import melee_mod.falcon.relics.PowerClipper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FalconCharacterMod implements CharacterMod {
    public static final Logger logger = LogManager.getLogger(FalconCharacterMod.class.getName());

    public static final Color BLUE = CardHelper.getColor(139.0f, 0.0f, 139.0f);

    private static final String ASSETS_FOLDER = "img";

    // card backgrounds
    private static final String ATTACK_BLUE = "512/bg_attack_blue.png";
    private static final String SKILL_BLUE = "512/bg_skill_blue.png";
    private static final String POWER_BLUE = "512/bg_power_blue.png";
    private static final String ENERGY_ORB_BLUE = "512/card_blue_orb.png";
    private static final String CARD_ENERGY_ORB_BLUE = "512/card_blue_small_orb.png";

    private static final String ATTACK_BLUE_PORTRAIT = "1024/bg_attack_blue.png";
    private static final String SKILL_BLUE_PORTRAIT = "1024/bg_skill_blue.png";
    private static final String POWER_BLUE_PORTRAIT = "1024/bg_power_blue.png";
    private static final String ENERGY_ORB_BLUE_PORTRAIT = "1024/card_blue_orb.png";

    // falcon assets
    private static final String FALCON_BUTTON = "charSelect/falcon_select_button.png";
    private static final String FALCON_PORTRAIT = "charSelect/falconPortraitBG.png";
    public static final String FALCON_SHOULDER_1 = "char/shoulder.png";
    public static final String FALCON_SHOULDER_2 = "char/shoulder2.png";
    public static final String FALCON_CORPSE = "char/corpse.png";

    public FalconCharacterMod() {
        logger.info("creating the color " + AbstractCardEnum.FALCON_BLUE.toString());
        BaseMod.addColor(AbstractCardEnum.FALCON_BLUE,
                BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
                makePath(ATTACK_BLUE), makePath(SKILL_BLUE),
                makePath(POWER_BLUE), makePath(ENERGY_ORB_BLUE),
                makePath(ATTACK_BLUE_PORTRAIT), makePath(SKILL_BLUE_PORTRAIT),
                makePath(POWER_BLUE_PORTRAIT), makePath(ENERGY_ORB_BLUE_PORTRAIT), makePath(CARD_ENERGY_ORB_BLUE));

    }

    @Override
    public void receiveEditCharacters() {
        logger.info("add " + FalconEnum.FALCON.toString());
        BaseMod.addCharacter(new CaptainFalcon("Captain Falcon", FalconEnum.FALCON), makePath(FALCON_BUTTON), makePath(FALCON_PORTRAIT), FalconEnum.FALCON);
    }

    @Override
    public void receiveEditRelics() {
        // Add relics
        BaseMod.addRelicToCustomPool(new AirWobbling(), AbstractCardEnum.FALCON_BLUE);
        BaseMod.addRelicToCustomPool(new AutoLCanceling(), AbstractCardEnum.FALCON_BLUE);
        BaseMod.addRelicToCustomPool(new LosersMang0(), AbstractCardEnum.FALCON_BLUE);
        BaseMod.addRelicToCustomPool(new PowerClipper(), AbstractCardEnum.FALCON_BLUE);
    }

    @Override
    public void receiveEditCards() {
        logger.info("add cards for " + FalconEnum.FALCON.toString());

        List<CustomCard> cards = new ArrayList<CustomCard>();

        // total 75 cards (20 common, 16 rare, 3 starter, 36 uncommon)
        // my rarity dist (16 common, 10 rare, 4 starter, 13 uncommon)
        // 28 attacks (20 atm)

        // AERIALS
        cards.add(new BackAir());
        cards.add(new DownAir());
        cards.add(new Knee());
        cards.add(new NeutralAir());
        cards.add(new NippleSpike());
        cards.add(new SoftKnee());
        cards.add(new UpAir());

        // PERCENT
        cards.add(new DownTilt());
        cards.add(new ForwardTilt());
        cards.add(new GetUpAttack());
        cards.add(new BlueFalcon());
        cards.add(new UpTilt());

        // BURNS
        cards.add(new FalconKick());
        cards.add(new FalconPunch());
        cards.add(new ForwardSmash());
        cards.add(new RaptorBoost());

        // SKILLS (36 for Silent)(16)
        cards.add(new AirDodge());
        cards.add(new AmsahTech());
        cards.add(new DashDance());
        cards.add(new DIMixUp());
        cards.add(new EdgeCancel());
        cards.add(new FalconDive());
        cards.add(new HaxDash());
        cards.add(new LoseStock());
        cards.add(new NoDI());
        cards.add(new Pause());
        cards.add(new PhantomHit());
        cards.add(new Roll());
        cards.add(new Shield());
        cards.add(new ShieldDrop());
        cards.add(new SomebodyClipThat());
        cards.add(new SweetSpot());
        cards.add(new TeamAttackOn());

        // POWERS (11 for Silent)
        cards.add(new IKilledMufasa());
        cards.add(new LCancel());
        cards.add(new PowerShield());
        cards.add(new Taunt());
        cards.add(new UCF());

        cards.add(new Jab());
        cards.add(new DownSmash());
        cards.add(new Gentleman());
        cards.add(new RapidJabs());
        cards.add(new ShieldPoke());
        cards.add(new Grab());
        cards.add(new UpSmash());

        for(CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }
    }

    @Override
    public void receiveEditStrings() {
        // RelicStrings
        String relicStrings = Gdx.files.internal("localization/Falcon-RelicStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal("localization/Falcon-CardStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

        logger.info("done editting strings");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] { Constants.Keywords.FINISHER.toLowerCase(), Constants.Keywords.FINISHER }, "Add 25% additional damage for each combo-point consumed");
        BaseMod.addKeyword(new String[] { Constants.Keywords.COMBO.toLowerCase(), Constants.Keywords.COMBO }, "Add a combo-point to the target");
        BaseMod.addKeyword(new String[] { Constants.Keywords.BURN.toLowerCase(), Constants.Keywords.BURN }, "Take damage equal to two times the Burn stacks at the end of the turn. Burn decreases by 1 each turn.");
        BaseMod.addKeyword(new String[] { Constants.Keywords.PERCENT.toLowerCase(), Constants.Keywords.PERCENT }, "Take X% additional damage. At 100%, at the end of the turn, gain 1 intangible and remove all %");
    }

    public static String makeCardImagePath(String cardName) {
        try{
            return makePath("cards/" + cardName);
        }
        catch (Exception e) {
            return makePath("cards/placeholder.png");
        }
    }

    public static String makeRelicImagePath(String power) {
        return makePath("relics/" + power);
    }

    public static String makePowerImagePath(String power) {
        return makePath("powers/" + power);
    }

    /**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static String makePath(String resource) {
        String result = ASSETS_FOLDER + "/falcon/" + resource;

        if (! hasExtension(resource)) {
            result += ".png";
        }

        return result;
    }

    private static boolean hasExtension(String filename) {
        return filename.lastIndexOf('.') > 0;
    }
}