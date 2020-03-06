package melee_mod.falcon.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import melee_mod.FalconCharacterMod;

import static globals.Constants.Relics.YEARS_OF_RESEARCH;

public class YearsOfResearch extends CustomRelic implements ClickableRelic {
    private static final String ID = YEARS_OF_RESEARCH;
    private boolean hasShownMessages;

    public YearsOfResearch() {
        super(ID, new Texture(FalconCharacterMod.makeRelicImagePath(ID)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = 0;
        this.usedUp = false;
        this.grayscale = false;
        this.description = this.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onRightClick() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver && this.counter < 3) {
            this.flash();
            this.addToBot(new DrawCardAction(1));
            if (!hasShownMessages) {
                if (this.counter == 0) {
                    this.addToBot(new TalkAction(true, "Melee players only know melee and not other smash games.", 1.0F, 4.0F));
                } else if (counter == 1) {
                    this.addToBot(new TalkAction(true, "lol, keep being unable to play any other smash game but melee.", 1.0F, 4.0F));
                } else {
                    this.addToBot(new TalkAction(true, "Melee isn't part of the actual smash community", 1.0F, 4.0F));
                }
            }
            counter++;
            if (this.counter == 3) {
                this.usedUp();
                hasShownMessages = true;
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new YearsOfResearch();
    }
}
