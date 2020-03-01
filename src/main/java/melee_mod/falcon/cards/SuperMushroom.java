package melee_mod.falcon.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.AbstractCardEnum;
import melee_mod.falcon.powers.GainDexterityPower;

public class SuperMushroom extends CustomCard {
    private static final String ID = Constants.CardNames.SUPER_MUSHROOM;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BASE_STRENGTH = 3;
    private static final int BASE_DEX = -1;
    private static final int UPGRADE_STRENGTH = 2;

    public SuperMushroom() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.FALCON_BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BASE_STRENGTH;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperMushroom();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_STRENGTH);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractPower negativeDex = new DexterityPower(player, BASE_DEX);
        negativeDex.type = AbstractPower.PowerType.DEBUFF;
        this.addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, this.magicNumber)));
        this.addToBot(new ApplyPowerAction(player, player, new LoseStrengthPower(player, this.magicNumber)));
        this.addToBot(new ApplyPowerAction(player, player, negativeDex));
        this.addToBot(new ApplyPowerAction(player, player, new GainDexterityPower(player, -BASE_DEX)));
    }
}
