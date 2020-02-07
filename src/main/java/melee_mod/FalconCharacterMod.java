package melee_mod;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
//import fruitymod.seeker.actions.unique.ConvergenceAction;
//import fruitymod.seeker.cards.*;
//import fruitymod.seeker.characters.TheSeeker;
//import fruitymod.seeker.patches.AbstractCardEnum;
//import fruitymod.seeker.patches.TheSeekerEnum;
//import fruitymod.seeker.relics.*;
//import fruitymod.tranquil.characters.TheTranquil;
//import fruitymod.tranquil.patches.TheTranquilEnum;
import melee_mod.falcon.CharacterMod;
import melee_mod.falcon.cards.*;
import melee_mod.falcon.characters.CaptainFalcon;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.patches.FalconEnum;
import melee_mod.falcon.relics.AirWobbling;
import globals.Constants;
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
    }

    @Override
    public void receiveEditCards() {
        logger.info("add cards for " + FalconEnum.FALCON.toString());

        List<CustomCard> cards = new ArrayList<CustomCard>();

        // 28 attacks (15 atm)

        // AERIALS
        cards.add(new BackAir());
        cards.add(new DownAir());
        cards.add(new Knee());
        cards.add(new NeutralAir());

        // PERCENT
        cards.add(new DownTilt());
        cards.add(new ForwardTilt());
        cards.add(new GetUpAttack());

        // BURNS
        cards.add(new FalconKick());
        cards.add(new FalconPunch());
        cards.add(new ForwardSmash());
        cards.add(new RaptorBoost());

        // SKILLS (36 for Silent)
        cards.add(new DashDance());
        cards.add(new DIMixUp());
        cards.add(new EdgeCancel());
        cards.add(new FalconDive());
        cards.add(new LoseStock());
        cards.add(new Roll());
        cards.add(new Shield());
        cards.add(new ShieldDrop());
        cards.add(new NoDI());
        cards.add(new Pause());
        cards.add(new PhantomHit());
        cards.add(new TeamAttackOn());
        cards.add(new SweetSpot());

        // POWERS (11 for Silent)
        cards.add(new LCancel());
        cards.add(new Taunt());
        cards.add(new UCF());

        cards.add(new Jab());
        cards.add(new DownSmash());
        cards.add(new Gentleman());
        cards.add(new RapidJabs());
        cards.add(new ShieldPoke());

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
        BaseMod.addKeyword(new String[] { Constants.Keywords.BURN.toLowerCase(), Constants.Keywords.BURN }, "Take damage equal to two times the Burn stacks at the end of the turn");
        BaseMod.addKeyword(new String[] { Constants.Keywords.PERCENT.toLowerCase(), Constants.Keywords.PERCENT }, "Take X% additional damage");
    }

    //
    // Relic code
    // (yes we're doing the exact same things the devs did where relic code
    // isn't in the actual relics - oh well)
    //

    private boolean moreThanXStacks(AbstractPlayer player, String powerID, int stacksWanted) {
        if (player != null && player.hasPower(powerID) && player.getPower(powerID).amount >= stacksWanted) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {

    }

    @Override
    public void receivePostDungeonInitialize() {

    }

    @Override
    public void receivePostExhaust(AbstractCard c) {
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
    }

    //
    // Enigma hooks and functionality
    //

    @Override
    public void receiveCardUsed(AbstractCard c) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower("EnigmaPower") && c.cardID.equals("Dazed")) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, c.block));
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player,
                    c.multiDamage,
                    DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
            c.exhaustOnUseOnce = false;
        }
    }

    public static boolean hasRelicCustom(String relicID, AbstractCard card) {
        System.out.println("I was checked!");
        // if it's checking for relicID.equals("Medical Kit") then we know we're in the block where
        // we are saying if we can use a status card so also check if we have enigma and the card is Dazed
        if (relicID.equals("Medical Kit") && AbstractDungeon.player.hasPower("EnigmaPower") && card.cardID.equals("Dazed")) {
            return true;
        } else {
            // otherwise leave normal behavior intact
            return AbstractDungeon.player.hasRelic(relicID);
        }
    }

    public static void maybeUseDazed(Dazed dazed) {
        System.out.println("maybe use dazed");
        if (!AbstractDungeon.player.hasPower("EnigmaPower")) {
            System.out.println("do use dazed");
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.UseCardAction(dazed));
        } else {
            System.out.println("don't use dazed");
        }
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
//            result += ".jpg";
        }

        return result;
    }

    private static boolean hasExtension(String filename) {
        return filename.lastIndexOf('.') > 0;
    }

    @Override
    public void receivePowersModified() {

    }
}