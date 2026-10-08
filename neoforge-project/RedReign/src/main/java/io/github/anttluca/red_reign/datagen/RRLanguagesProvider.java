package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.api.LanguagesProvider;
import io.github.anttluca.red_reign.init.*;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class RRLanguagesProvider extends LanguagesProvider {
    public static final String DEATH_ATTACK = "death.attack.";
    public static final String BASE_JEI_CATEGORY = "jei.red_reign.category.";

    public RRLanguagesProvider(PackOutput output) {
        super(output, RedReign.MODID, List.of("en_us", "pt_br"));
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + RedReign.MODID, "Red Reign", "Renado Vermelho");

        addStory("white_queen_was_devoured", "White Queen was devoured", "Rainha Branca foi devorada");
        addStory("red_reign_take_world", "A red reign is taking over the world", "Um reinado vermelho toma o mundo");

        addAdvancement("activate_altar_of_red_lady",
            new String[]{"", ""},
            new String[]{"What did you do? Steve, She is free! It was really inevitable.", "O que você fez? Steve, Ela está livre! Isso era mesmo inevitável."}
        );
        addAdvancement("life_cost_crafting",
            new String[]{"Crimson Table", "Mesa Escarlate"},
            new String[]{"Are your hands still clean? Tools infused with vitality.", "Mãos ainda limpas? Ferramentas infundidas com vitalidade."}
        );
        addAdvancement("get_bloodstained_relic",
            new String[]{"Bloodstained", "Manchado de Sangue"},
            new String[]{"Now, you're tainted.", "Agora, você está manchado."}
        );
        addAdvancement("get_red_signet",
            new String[]{"The Red Signet", "O Sinete Vermelho"},
            new String[]{"Are you a knight of the Red Reign?", "Você é um cavaleiro do Reinado Vermelho?"}
        );
        addAdvancement("get_blade_of_the_bloodstained_lament",
            new String[]{"The Blade of the Lament", "A Lâmina do Lamento"},
            new String[]{"With that much power, you can take on anything.", "Com tamanho poder, é possível desafiar qualquer coisa."}
        );
        addAdvancement("challenge_red_queens_avatar",
            new String[]{"Revolution", "Revolução"},
            new String[]{"Challenged the Red Queen and wins.", "Desafiou a Rainha Vermelha e venceu."}
        );

        addDataComponent("adoptable", "Belongs to", "Pertence a");
        addDataComponent("adoptable.unknown", "Unknown", "Desconhecido");
        addDataComponent("stolen_life", "Life stolen so far", "Vida roubada até agora");

        addEnchantment("transmutation",
            new String[]{"Transmutation", "Transmutação"},
            new String[]{"Placeholder for craft through enchantment.", "Espaço reservado para criação por encantamento."}
        );

        add("item.%s.common.abilities".formatted(RedReign.MODID), "Abilities", "Habilidades");
        add("item.%s.common.hold_shift".formatted(RedReign.MODID), "Hold down the §6Shift§r to view the item's abilities.", "Mantenha o §6Shift§r pressionado para ver as habilidades do item.");
        addItem(InitItems.HONEYCOMB_BUCKET, "Honeycomb Bucket", "Balde de Cera de Abelha");
        addItem(InitItems.MELTED_BEESWAX_BUCKET, "Melted Beeswax Bucket", "Balde de Cera de Abelha Derretida");
        addItem(InitItems.REDSTONE_CRYSTAL, "Redstone Crystal", "Cristal de Redstone");
        addItem(InitItems.INTRINSIC_MECHANISM, "Intrinsic Mechanism", "Mecanismo Intrínseco");
        addItemLore(InitItems.INTRINSIC_MECHANISM, "We, the metalworking villagers, are increasingly setting aside our swords, tools and armor and embracing advanced mechanical technology.", "Nós, aldeões metalúrgicos, estamos cada vez mais abandonando as espadas, ferramentas e armaduras e adotando a tecnologia mecânica avançada.");
        addItem(InitItems.CATALYST_OF_EVERYTHING, "Catalyst of Everything", "Catalisador do Tudo");
        addItem(InitItems.CATALYST_OF_EVERYTHING, "Everything?! Sculk, Nether, The End, Overworld, Soul, Portal, Meat, Mind.", "Tudo?! Sculk, Nether, O Fim, Superfície, Alma, Portal, Carne, Espírito.");
        addItem(InitItems.ALLAY_CAGE, "Allay’s Cage", "Gaiola de Allay");
        addItemLore(InitItems.ALLAY_CAGE, "Interact with an Allay and it will let you catch it easily! I'll admit, the amethyst and its apparent love of being trapped really help with the capture.", "Interaja com um Allay e você o prenderá facilmente! Confesso, a ametista e o aparente amor por estarem presos ajudam muito na captura.");
        addItem(InitItems.CHALICE_OF_THE_BLOODBLADE, "Chalice of the Bloodblade", "Cálice da Lâmina de Sangue");
        addItemLore(InitItems.CHALICE_OF_THE_BLOODBLADE, "What's in this chalice? Who knows?! At least it tastes good and can be refilled.", "O que esse cálice carrega? Quem sabe?! Pelo menos é saboroso e pode ser enchido.");
        addItemAbilities(InitItems.CHALICE_OF_THE_BLOODBLADE, new String[]{"When attacking enemies, your Life Steal does not restore health to the wearer, but instead stores vitality in the chalice itself.", "Ao atacar inimigos, seu Roubo de Vida não restaura vida para o portador, mas estoca vitalidade no próprio cálice."});
        addItem(InitItems.TOTEM_OF_THE_RED_QUEEN, "Totem of the Red Queen", "Totem da Rainha Vermelha");
        addItemLore(InitItems.TOTEM_OF_THE_RED_QUEEN, "A mysterious totem covered in a rare and extremely durable material. It reacts strangely when it witnesses life slipping away.", "Totem misterioso coberto por um material raro e extremamente resistente. Reage estranhamente ao presenciar a vida se esvaindo.");
        addItemAbilities(InitItems.TOTEM_OF_THE_RED_QUEEN, new String[]{"Grants the Undying effect, but restores (Life Steal)% of your Maximum Health and then disappears.", "Concede o efeito de Imortalidade, mas restaura (Roubo de Vida)% da sua Vida Máxima e se consome."});
        addItem(InitItems.PURIFICATION_SPELL, "Purification Spell", "Feitiço de Purificação");
        addItemLore(InitItems.PURIFICATION_SPELL, "This ancient spell promises to purify red things... Like a poppy!", "Esse feitiço antigo promete purificar coisas vermelhas... Como uma papoula!");
        addItemAbilities(InitItems.PURIFICATION_SPELL, new String[]{"Use it in your Main Hand, paired with a Poppy in Off-Hand, and complete the purification.", "Use na Mão Principal, unido a uma Papoula na Mão Secundária e conclua a purificação."});
        add(InitItems.PURIFICATION_SPELL.get().getDescriptionId() + ".unpurified", "No memories to purify.", "Sem lembranças para purificar.");
        addItem(InitItems.PINK_EMBRYO, "Pink Embryo", "Embrião Rosa");
        addItemLore(InitItems.PINK_EMBRYO, "Originating from a disjointed attack. Created by a white memory. Born from a red womb.", "Originada de um ataque desparelho. Criada por uma lembrança branca. Nascida de um ventre vermelho.");
        addItem(InitItems.CRYSTALLIZED_TEAR, "Crystallized Tear", "Lágrima Cristalizada");
        addItemLore(InitItems.CRYSTALLIZED_TEAR, "This crystallized tear is the embodiment of memories belonging to a former white monarch.", "Essa lágrima cristalizada é a materialização de lembranças de uma antiga monarca branca.");
        addItem(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT, "Blade of the Bloodstained Lament", "Lâmina do Lamento Manchado de Sangue");
        addItemLore(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT, "Only blood can wound blood and only the spirit can soothe it.", "Somente o sangue para ferir o sangue e somente o espírito para o acalmar.");
        addItemAbilities(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT, new String[]{"Hold down the §6Right Button§r to challenge the Red Queen.", "Segure o §6Clique Direito§r para desafiar a Rainha Vermelha."});
        addItem(InitItems.VAMPIRE_ROSE, "Vampire Rose", "Rosa Vampira");
        addItemLore(InitItems.VAMPIRE_ROSE, "During the day, it may dry out a little, but its bloodthirsty power remains.", "De dia, pode ressecar um pouco, mas o poder sanguinário permanece.");
        addItem(InitItems.FINAL_BLESSING, "Final Blessing", "Última Benção");
        addItemLore(InitItems.FINAL_BLESSING, "A keychain containing the petrified tongue of a clergyman.", "Chaveiro com a língua petrificada de um clérigo.");
        addItem(InitItems.ETHEREAL_PROTECTION, "Ethereal Protection", "Proteção Etérea");
        addItemLore(InitItems.ETHEREAL_PROTECTION, "A censer that emits the essence of Allay.", "Turíbulo que exala essência de Allay.");
        addItem(InitItems.EARTHLY_ICHOR, "Earthly Ichor", "Ichor Terreno");
        addItemLore(InitItems.EARTHLY_ICHOR, "A substance similar to the ichor of the gods, but produced on Earth. Mortals can consume it to gain more vitality.", "Substância parecida com o ichor dos deuses, mas produzida na Terra. Mortais podem consumir para obter mais vitalidade.");
        addItem(InitItems.HEALING_BULB, "Healing Bulb", "Bulbo Curador");
        addItemLore(InitItems.HEALING_BULB, "This suction pear can be attached to the back with two small punctures and keep the wearer's internal organs in place for longer.", "Esta pera de sucção é capaz de se fixar nas costas com duas pequenas perfurações e manter as entranhas do portador no lugar por mais tempo.");
        addItem(InitItems.LAZULI_PROVIDENCE, "Lazuli Providence", "Providência Lazuli");
        addItemLore(InitItems.LAZULI_PROVIDENCE, "A pure and simple start. Stylish earrings that call for a little extra charm and rumor has it that lapis lazuli brings good luck.", "Um início puro e singelo. Brincos estilosos que pedem por mais um charme e dizem por aí que lapis lazuli traz sorte.");
        addItem(InitItems.DAISY_SILVER_METEOR, "Silver Daisy Meteor", "Meteoro da Margarida de Prata");
        addItemLore(InitItems.DAISY_SILVER_METEOR, "The beauty of a daisy. The weight of a meteor. The shine of silver. The magic of...", "A beleza de uma margarida. O peso de um meteoro. O brilho da prata. A magia do...");
        addItemAbilities(InitItems.DAISY_SILVER_METEOR,
            new String[]{"The XP costs of the anvils used by the wearer are reduced by 50%.", "Os custos de XP das bigornas usadas pelo portador é reduzido em 50%."},
            new String[]{"When the wearer uses an anvil, the chance of it being damaged is reduced by 50%.", "Quando o portador usa uma bigorna, a chance dela ser danificada é reduzido em 50%."});
        addItem(InitItems.CORAL_GAUNTLET, "Coral Gauntlet", "Manopla Coral");
        addItemLore(InitItems.CORAL_GAUNTLET, "I can feel this gauntlet pulsing... Is it called \"coral\" because of its color or its material?", "Eu sinto essa manopla pulsar... É \"coral\" por causa da cor ou do material?");
        addItemAbilities(InitItems.CORAL_GAUNTLET, new String[]{"When the wearer's target has 25% or less of its maximum health, the wearer's attack deals 35% increased damage and restores health equal to 10% of the damage dealt.", "Quando o alvo do portador tem 25% ou menos da vida máxima, o ataque do portador tem dano aumentado em 35% e restaura vida igual a 10% do dano causado."});
        addItem(InitItems.ROSE_ANCHOR, "Rose Anchor", "Âncora Rosa");
        addItemLore(InitItems.ROSE_ANCHOR, "With this belt, the wearer becomes steady, hard as sto... Rose quartz?", "Com esse cinto, o portador se torna firme, duro como ped... Quartzo rosa?");
        addItemAbilities(InitItems.ROSE_ANCHOR,
            new String[]{"Knockback immunity.", "Imunidade a Empurrão."},
            new String[]{"Fall Damage immunity.", "Imunidade a Dano de Queda."});
        addItem(InitItems.VORTEX_PEARL, "Vortex Pearl", "Pérola Vórtice");
        addItemLore(InitItems.VORTEX_PEARL, "A pearl necklace that swirls through the wearer's armor like a vortex.", "Um colar de pérola que mexe com a armadura do portador como um vórtice.");
        addItemAbilities(InitItems.VORTEX_PEARL,
            new String[]{"Grants (2.5 * Armor)% Breath.", "Concede (2.5 * Armadura)% de Respiração."},
            new String[]{"Grants (1 * Armor)% Magic Resistance.", "Concede (1 * Armadura)% de Resistência Mágica."});
        addItem(InitItems.AMETHYST_RESONATOR, "Amethyst Resonator", "Ressonador de Ametista");
        addItemLore(InitItems.AMETHYST_RESONATOR, "It reproduces sound and electrical waves. Caution! Risk of hearing loss and electric shock.", "Reproduz ondas sonoras e elétricas. Cuidado! Risco de surdez e eletrocussão.");
        addItem(InitItems.RED_IDENTITY, "Red Identity", "Identidade Vermelha");
        addItemLore(InitItems.RED_IDENTITY, "When wearing this mask of pure red, the wearer loses all sense of their own identity, hides behind a power devoid of guilt and frees themselves from ethical constraints.", "Ao equipar essa máscara de vermelho puro, o portador perde a noção de sua própria identidade, se esconde atrás de um poder sem culpa e se livra das amarras éticas.");
        addItemAbilities(InitItems.RED_IDENTITY, new String[]{"Increases damage dealt by 0–20% based on the amount of health lost (100–1%).", "Aumenta o dano causado em 0-20% de acordo com a vida perdida (100-1%)."});
        addItem(InitItems.RED_SIGNET, "Red Signet", "Sinete Vermelho");
        addItemLore(InitItems.RED_SIGNET, "This ring is the ultimate sign of Her approval. The red power flows more intensely.", "Esse anel é o sinal máximo da aprovação dEla. O poder vermelho flui mais intensamente.");
        addItemAbilities(InitItems.RED_SIGNET,
            new String[]{"The wearer gains 1.4x the benefit from Life Steal.", "O portador se beneficia 1.4x do Roubo de Vida."},
            new String[]{"Allows the wearer to use the Purification Spell.", "Permite ao portador usar o Feitiço de Purificação."});
        add(InitItems.RED_SIGNET.get().getDescriptionId() + ".unworthy", "Unworthy! You can't cast that spell without the Red Signet.", "Indigno! Você não pode usar esse feitiço sem o Sinete Vermelho.");

        addBlock(InitBlocks.BOUQUET_OF_POPPIES, "Bouquet of Poppies", "Buquê de Papoulas");
        addBlock(InitBlocks.ALTAR_OF_RED_LADY, "Altar of Red Lady", "Altar da Dama Vermelha");
        addBlock(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN, "Crafting Table of Red Queen", "Mesa de Trabalho da Rainha Vermelha");
        add(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get().getDescriptionId() + ".hp_cost", "HP Cost", "Custo em PV");
        addBlock(InitBlocks.ROSE_QUARTZ_BLOCK, "Block of Rose Quartz", "Bloco de Quartzo Rosa");

        addAttribute(InitAttributes.LOOTING, "Looting Level", "Nível de Saque");
        addAttribute(InitAttributes.FIRE_DAMAGE, "Fire Damage Taken", "Dano de Fogo Sofrido");
        addAttribute(InitAttributes.POISON_DAMAGE, "Poison Damage Taken", "Dano de Veneno Sofrido");
        addAttribute(InitAttributes.PHYSICAL_DAMAGE, "Physical Damage Taken", "Dano Físico Sofrido");

        addMobEffect(InitMobEffects.SENSITIVE_SKIN, "Sensitive Skin", "Pele Sensível");
        addMobEffect(InitMobEffects.ARMOR_CORROSION, "Armor Corrosion", "Corrosão de Armadura");
        addMobEffect(InitMobEffects.BLEEDING, "Bleeding", "Sangramento");

        add(DEATH_ATTACK + "bleeding", "%1$s bled away", "%1$s vazou até a morte");
        add(DEATH_ATTACK + "bleeding.player", "%%1$s bled away while fighting %2$s", "%1$s vazou até a morte enquanto lutava com %2$s");
        add(DEATH_ATTACK + "red_queen_power", "%1$s was turned into carrion by the power of %2$s", "%1$s virou carniça pelo poder de %2$s");
        add(DEATH_ATTACK + "red_queen_power.item", "%1$s was turned into carrion by the power of %2$s using %3$s", "%1$s virou carniça pelo poder de %2$s usando %3$s");

        addFluid(InitFluids.MELTED_BEESWAX_TYPE, "Melted Beeswax", "Cera de Abelha Derretida");

        addEntity(InitEntityTypes.RED_QUEENS_AVATAR, "Red Queen’s Avatar", "Avatar da Rainha Vermelha");
        addEntity(InitEntityTypes.RED_QUEEN_POWER, "Red Queen’s Power", "Poder da Rainha Vermelha");

        add(BASE_JEI_CATEGORY + "transmutation", "Transmutation", "Transmutação");
        add(BASE_JEI_CATEGORY + "transmutation.level", "Require: %s", "Requer: %s");
        add(BASE_JEI_CATEGORY + "purification", "Purification", "Purificação");
    }

    protected void addItemLore(Supplier<? extends Item> key, String... lores) {
        addItemLore(key.get(), lores);
    }

    protected void addItemLore(Item key, String... lores) {
        add(key.getDescriptionId() + ".lore", lores);
    }

    protected void addItemAbilities(Supplier<? extends Item> key, String[]... abilities) {
        addItemAbilities(key.get(), abilities);
    }

    protected void addItemAbilities(Item key, String[]... abilities) {
        String baseKey = key.getDescriptionId() + ".abilitiy";

        for (int a = 0; a < abilities.length; a++) {
            String[] ability = abilities[a];

            add(baseKey + a, ability);
        }
    }

    // Hi, antthLuca from the future!
    // You could create a Util class with methods that generate translation keys.
    // That way, datagen and Component.translatable can use the same key without any issues!
}
