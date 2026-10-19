package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.api.LanguagesProvider;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIIICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIVCategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_i.IntroEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii.IntroIIEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii.WorldEffectsEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.BloodstainedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.LifeInfusedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.bloodstained.*;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.life_infused.*;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iv.IntroIVEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iv.PurificationSpellEntry;
import io.github.anttluca.red_reign.init.*;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.function.Supplier;

public class RRLanguagesProvider extends LanguagesProvider {
    public static final String DEATH_ATTACK = "death.attack.";
    public static final String BASE_JEI_CATEGORY = "jei.red_reign.category.";

    private static final String BASE_BOOK = "book.%s.book.".formatted(RedReign.MODID);

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
        addItemLore(InitItems.CATALYST_OF_EVERYTHING, "Everything?! Sculk, Nether, The End, Overworld, Soul, Portal, Meat, Mind.", "Tudo?! Sculk, Nether, O Fim, Superfície, Alma, Portal, Carne, Espírito.");
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

        // JEI
        add(BASE_JEI_CATEGORY + "transmutation", "Transmutation", "Transmutação");
        add(BASE_JEI_CATEGORY + "transmutation.level", "Require: %s", "Requer: %s");
        add(BASE_JEI_CATEGORY + "purification", "Purification", "Purificação");

        // Modonomicon
        add(BASE_BOOK + "name", "Crimson Grimoire", "Grimório Escarlate");
        addItemLore(InitItems.CRIMSON_GRIMOIRE, "This very old and somewhat worn book fell out of a bouquet of poppies. It contains comments from a fanatic. It’s all so convenient...", "Esse livro muito antigo e um pouco deteriorado caiu de um buquê de papoulas. Contém comentários de um fanático. Tudo isso é tão conveniente...");
        // Research
        addModonomiconResearch(RRResearchSubProvider.ID_CRYSTALLIZED_TEAR, "Crystallized Tear Acquisition", "Obteve Lágrima Cristalizada");
        addModonomiconResearch(RRResearchSubProvider.ID_VAMPIRE_ROSE, "Vampire Rose Acquisition", "Obteve Rosa Vampira");
        addModonomiconResearch(RRResearchSubProvider.ID_RED_SIGNET, "Red Signet Acquisition", "Obteve Sinete Vermelho");
        addModonomiconResearch(RRResearchSubProvider.ID_EARTHLY_ICHOR, "Earthly Ichor Acquisition", "Obteve Ichor Terreno");
        addModonomiconResearch(RRResearchSubProvider.ID_ETHEREAL_PROTECTION, "Ethereal Protection Acquisition", "Obteve Proteção Etérea");
        addModonomiconResearch(RRResearchSubProvider.ID_FINAL_BLESSING, "Final Blessing Acquisition", "Obteve Última Benção");
        addModonomiconResearch(RRResearchSubProvider.ID_HEALING_BULB, "Healing Bulb Acquisition", "Obteve Bulbo Curador");
        addModonomiconResearch(RRResearchSubProvider.ID_AMETHYST_RESONATOR, "Amethyst Resonator Acquisition", "Obteve Ressonador de Ametista");
        addModonomiconResearch(RRResearchSubProvider.ID_CORAL_GAUNTLET, "Coral Gauntlet Acquisition", "Obteve Manopla Coral");
        addModonomiconResearch(RRResearchSubProvider.ID_DAISY_SILVER_METEOR, "Daisy Silver Meteor Acquisition", "Obteve Meteoro da Margarida de Prata");
        addModonomiconResearch(RRResearchSubProvider.ID_LAZULI_PROVIDENCE, "Lazuli Providence Acquisition", "Obteve Providência Lazuli");
        addModonomiconResearch(RRResearchSubProvider.ID_RED_IDENTITY, "Red Identity Acquisition", "Obteve Identidade Vermelha");
        addModonomiconResearch(RRResearchSubProvider.ID_ROSE_ANCHOR, "Rose Anchor Acquisition", "Obteve Âncora Rosa");
        addModonomiconResearch(RRResearchSubProvider.ID_VORTEX_PEARL, "Vortex Pearl Acquisition", "Obteve Pérola Vórtice");
        addModonomiconLocked(InitItems.VAMPIRE_ROSE, "Vampire Rose is required", "Rosa Vampira é necessária");
        // Chapter I
        addModonomiconCategory(ChapterICategory.ID, "§kRemind", "§kRelembre");
        addModonomiconEntry(ChapterICategory.ID, IntroEntry.ID,
            new String[]{"Intro", "Intro"},
            new String[]{"Introduction about the world.", "Introdução sobre o mundo."});
        addModonomiconPage(ChapterICategory.ID, IntroEntry.ID, IntroEntry.PG_MAIN,
            new String[]{"Seals", "Selos"},
            new String[]{"[#](00AAAA)Thousands of petrified faces cry, their mouths mumbling words on the verge of extinction — a seal that keeps a red entity captive.[#]()\\\n[#](AA0000)The more we weaken these structures, the closer our Lady comes to becoming Queen.", "[#](00AAAA)Milhares de rostos petrificados choram, suas bocas balbuciam palavras quase extintas, um selo que mantém uma entidade vermelha cativa.[#]()\\\n[#](AA0000)Quanto mais enfraquercemos essas estruturas, mais perto a nossa Dama está perto de se tornar Rainha."});
        // Chapter II
        addModonomiconCategory(ChapterIICategory.ID, "Chapter II", "Capítulo II");
        addModonomiconEntry(ChapterIICategory.ID, IntroIIEntry.ID,
            new String[]{"Intro", "Intro"},
            new String[]{"Introduction about The Queen.", "Introdução sobre A Rainha."});
        addModonomiconPage(ChapterIICategory.ID, IntroIIEntry.ID, IntroIIEntry.PG_MAIN,
            new String[]{"Powers and Costs", "Poderes e Preços"},
            new String[]{"When the Red Queen extends her influence over the world, she will separate the lazy from the hardworking. Those who belong to her will be rewarded with great powers, but at an equal cost.", "Quando a Rainha Vermelha trazer sua influência sobre o mundo, ela separá os preguiçosos dos trabalhadores. A recompensa virá para aqueles que são dela, grandes poderes, mas preços equivalentes."});
        // > World Effects
        addModonomiconEntry(ChapterIICategory.ID, WorldEffectsEntry.ID,
            new String[]{"Begin the Red Reign", "Início do Reino Vermelho"},
            new String[]{"About the effects of the red influence.", "Sobre os efeitos da influência vermelha."});
        addModonomiconPage(ChapterIICategory.ID, WorldEffectsEntry.ID, WorldEffectsEntry.PG_EFFECTS,
            new String[]{"Effects on the World", "Efeitos no Mundo"},
            new String[]{"The Red Queen’s influence has certain effects on the world. They are as follows:\n- The white blessing has been removed from the world, the Player's regeneration is ineffective;\n- The Queen demands her tribute, all regeneration is reduced by 40%%;\n- There is a plague in the world, all meat dropped by animals has a 50%% chance of rotting;\n- The Player can draw health from meat, eating meat restores health;\n- You can access the Red Queen’s power at a special workbench.", "A influência da Rainha Vermelha traz alguns efeitos ao mundo. São eles:\n- A benção branca se retira do mundo, a regeneração do Jogador é ineficaz;\n- A Rainha cobra o seu tributo, toda regeneração é reduzida em 40%%;\n- Há uma praga no mundo, todo drop de carne vindo de animais tem 50%% de chance de apodrecer;\n- O Jogador pode extrair saúde da carne, restaura vida ao comer carne;\n- Pode-se acessar os poderes da Rainha Vermelha numa mesa de trabalho especial."});
        addModonomiconPage(ChapterIICategory.ID, WorldEffectsEntry.ID, WorldEffectsEntry.PG_CRAFT,
            new String[]{"Relics", "Relíquias"},
            new String[]{"Gain power with relics, such as the Vampire Rose. Start by creating something that will allow you to tap into the Red Queen’s powers, using golden objects, something soft and white, red crystals, and stones as black as night.\\\nThen, place a **Red Rose** inside it.", "Se torne poderoso com relíquias, como a Rosa Vampira. Comece criando algo para acessar os poderes da Rainha Vermelha, usando coisas douradas, algo macio e branco, cristais vermelhos e pedras tão negras quanto a noite.\\\nDepois, deposite uma **Rosa Vermelha** nele."});
        // Chapter III
        addModonomiconCategory(ChapterIIICategory.ID, "Chapter III", "Capítulo III");
        // > Bloodstained Relics
        addModonomiconEntry(ChapterIIICategory.ID, BloodstainedEntry.ID,
            new String[]{"Bloodstained Relics", "Relíquias Manchadas de Sangue"},
            new String[]{"Pay the cost.", "Pague o preço."});
        addModonomiconPage(ChapterIIICategory.ID, BloodstainedEntry.ID, BloodstainedEntry.PG_MAIN,
            new String[]{"Buffs and Nerfs", "Melhorias e Enfraquecimentos"},
            new String[]{"Relics that inflict weaknesses on their bearer but increase their vitality. Unravel the mystery and obtain the relic.\\\n[#](AA0000)A delightful power, even if some of them demand something in return.", "Relíquias que concedem fraquezas ao seu portador, mas aumentam sua vitalidade. Desvende o mistério e obtenha a relíquia.\\\n[#](AA0000)Poder prazeroso. Mesmo que algumas delas cobrem algo em troca."});
        // > > Earthly Ichor
        addModonomiconEntry(ChapterIIICategory.ID, EarthlyIchorEntry.ID,
            new String[]{"Vital Liquid", "Líquido Vital"},
            new String[]{"Is it safe to drink?", "Será que dá para beber?"});
        addModonomiconPage(ChapterIIICategory.ID, EarthlyIchorEntry.ID, EarthlyIchorEntry.PG_MAIN,
            new String[]{"Vital Liquid", "Líquido Vital"},
            new String[]{"Treasure found in ancient chests in an underground city.", "Tesouro encontrado em baús antigos de uma cidade subterrânea."});
        addModonomiconEntry(ChapterIIICategory.ID, EarthlyIchorUnlockedEntry.ID,
            new String[]{"Earthly Ichor", "Ichor Terreno"},
            new String[]{"Mortals can consume it to gain more vitality.", "Mortais podem consumir para obter mais vitalidade."});
        addModonomiconPage(ChapterIIICategory.ID, EarthlyIchorUnlockedEntry.ID, EarthlyIchorUnlockedEntry.PG_MAIN,
            new String[]{"Earthly Ichor", "Ichor Terreno"},
            new String[]{"A substance similar to the ichor of the gods, but produced on Earth. Mortals can consume it to gain more vitality.", "Substância parecida com o ichor dos deuses, mas produzida na Terra. Mortais podem consumir para obter mais vitalidade."});
        // > > Ethereal Protection
        addModonomiconEntry(ChapterIIICategory.ID, EtherealProtectionEntry.ID,
            new String[]{"Shiny Cage", "Gaiola Brilhante"},
            new String[]{"Can I touch it? Will it burn me?", "Pode tocar? Será que queima?"});
        addModonomiconPage(ChapterIIICategory.ID, EtherealProtectionEntry.ID, EtherealProtectionEntry.PG_MAIN,
            new String[]{"Shiny Cage", "Gaiola Brilhante"},
            new String[]{"Imagine a lantern, but with an amethyst in the middle.", "Imagine um lampião, mas com uma ametista no meio."});
        addModonomiconEntry(ChapterIIICategory.ID, EtherealProtectionUnlockedEntry.ID,
            new String[]{"Ethereal Protection", "Proteção Etérea"},
            new String[]{"Carry it with you to exist between the ethereal and the physical.", "Carregue com você para estar entre etéreo e o físico."});
        addModonomiconPage(ChapterIIICategory.ID, EtherealProtectionUnlockedEntry.ID, EtherealProtectionUnlockedEntry.PG_MAIN,
            new String[]{"Ethereal Protection", "Proteção Etérea"},
            new String[]{"A censer that emits the essence of Allay.", "Turíbulo que exala essência de Allay."});
        // > > Final Blessing
        addModonomiconEntry(ChapterIIICategory.ID, FinalBlessingEntry.ID,
            new String[]{"Rotten Keychain", "Chaveiro Podre"},
            new String[]{"That stinks!", "Isso fede!"});
        addModonomiconPage(ChapterIIICategory.ID, FinalBlessingEntry.ID, FinalBlessingEntry.PG_MAIN,
            new String[]{"Rotten Keychain", "Chaveiro Podre"},
            new String[]{"It originates from the final blessing given before the death of the religious who gather in villages.", "Nasce da última benção antes da morte dos religiosos que se reúnem em vilas."});
        addModonomiconEntry(ChapterIIICategory.ID, FinalBlessingUnlockedEntry.ID,
            new String[]{"Final Blessing", "Último Benção"},
            new String[]{"It reduces healing, but blesses with vitality.", "Diminui a cura, mas abençoa com vitalidade."});
        addModonomiconPage(ChapterIIICategory.ID, FinalBlessingUnlockedEntry.ID, FinalBlessingUnlockedEntry.PG_MAIN,
            new String[]{"Final Blessing", "Último Benção"},
            new String[]{"A keychain containing the petrified tongue of a clergyman.", "Chaveiro com a língua petrificada de um clérigo."});
        // > > Healing Bulb
        addModonomiconEntry(ChapterIIICategory.ID, HealingBulbEntry.ID,
            new String[]{"Glass Container", "Recipiente de Vidro"},
            new String[]{"What is the capacity of this?", "Qual a capacidade disso?"});
        addModonomiconPage(ChapterIIICategory.ID, HealingBulbEntry.ID, HealingBulbEntry.PG_MAIN,
            new String[]{"Glass Container", "Recipiente de Vidro"},
            new String[]{"Gold, iron, glass, and a technology developed in villages by metalworkers.", "Ouro, ferro, vidro e uma tecnogia criado em vilas por metalúrgicos."});
        addModonomiconEntry(ChapterIIICategory.ID, HealingBulbUnlockedEntry.ID,
            new String[]{"Healing Bulb", "Bulbo Curador"},
            new String[]{"It increases internal pressure, slowing the carrier down, but keeps everything in place.", "Aumenta a pressão interna, diminuindo a velocidade do portador, mas mantém as coisas no lugar."});
        addModonomiconPage(ChapterIIICategory.ID, HealingBulbUnlockedEntry.ID, HealingBulbUnlockedEntry.PG_MAIN,
            new String[]{"Healing Bulb", "Bulbo Curador"},
            new String[]{"This suction pear can be attached to the back with two small punctures and keep the wearer's internal organs in place for longer.", "Esta pera de sucção é capaz de se fixar nas costas com duas pequenas perfurações e manter as entranhas do portador no lugar por mais tempo."});
        // > Life Infused Relics
        addModonomiconEntry(ChapterIIICategory.ID, LifeInfusedEntry.ID,
            new String[]{"Life Infused Relics", "Relíquias Infundidas com Vitalidade"},
            new String[]{"You are the payment.", "Você é o pagamento."});
        addModonomiconPage(ChapterIIICategory.ID, LifeInfusedEntry.ID, LifeInfusedEntry.PG_MAIN,
            new String[]{"Just Power", "Apenas Poder"},
            new String[]{"Relics that grant bonuses, you just need to equip them. Unravel the mystery and obtain the relic.\\\n[#](AA0000)This is the pinnacle of power. I wanted a Red Signet so badly.", "Relíquias que concedem melhorias, basta estarem equipadas. Desvende o mistério e obtenha a relíquia.\\\n[#](AA0000)Esse é o ápice do poder. Eu queria tanto um Red Signet."});
        // > > Amethyst Resonator
        addModonomiconEntry(ChapterIIICategory.ID, AmethystResonatorEntry.ID,
            new String[]{"A crystal... And is purple!", "Um cristal... E roxo!"},
            new String[]{"A crystal!? What else can they invent?", "Um cristal!? O que mais podem inventar?"});
        addModonomiconPage(ChapterIIICategory.ID, AmethystResonatorEntry.ID, AmethystResonatorEntry.PG_MAIN,
            new String[]{"A crystal... And is purple!", "Um cristal... E roxo!"},
            new String[]{"An iron mechanism, a purple crystal, and that mold that can hear sounds.", "Um mecanismo de ferro, um cristal roxo e aquele mofo que consegue escutar."});
        addModonomiconEntry(ChapterIIICategory.ID, AmethystResonatorUnlockedEntry.ID,
            new String[]{"Amethyst Resonator", "Ressonador de Ametista"},
            new String[]{"Sounds and electricity?! What a combination...", "Sons e eletricidade?! Que combinação..."});
        addModonomiconPage(ChapterIIICategory.ID, AmethystResonatorUnlockedEntry.ID, AmethystResonatorUnlockedEntry.PG_MAIN,
            new String[]{"Amethyst Resonator", "Ressonador de Ametista"},
            new String[]{"It reproduces sound and electrical waves. Caution! Risk of hearing loss and electric shock.", "Reproduz ondas sonoras e elétricas. Cuidado! Risco de surdez e eletrocussão."});
        // > > Coral Gauntlet
        addModonomiconEntry(ChapterIIICategory.ID, CoralGauntletEntry.ID,
            new String[]{"Brainstone", "Pedra-Cérebro"},
            new String[]{"This seems to be alive!", "Isso parece estar vivo!"});
        addModonomiconPage(ChapterIIICategory.ID, CoralGauntletEntry.ID, CoralGauntletEntry.PG_MAIN,
            new String[]{"Brainstone", "Pedra-Cérebro"},
            new String[]{"Something that comes from the sea and pulses, surrounded by pink and purple corals, that is how you will obtain your relic.", "Algo que vem do mar e pulsa, circulado de corais rosa e roxo, assim obterá sua relíquia."});
        addModonomiconEntry(ChapterIIICategory.ID, CoralGauntletUnlockedEntry.ID,
            new String[]{"Coral Gauntlet", "Manopla Coral"},
            new String[]{"A wearable brain!?", "Um cérebro vestível!?"});
        addModonomiconPage(ChapterIIICategory.ID, CoralGauntletUnlockedEntry.ID, CoralGauntletUnlockedEntry.PG_MAIN,
            new String[]{"Coral Gauntlet", "Manopla Coral"},
            new String[]{"I can feel this gauntlet pulsing... Is it called \"coral\" because of its color or its material?", "Eu sinto essa manopla pulsar... É \"coral\" por causa da cor ou do material?"});
        // > > Daisy Silver Meteor
        addModonomiconEntry(ChapterIIICategory.ID, DaisySilverMeteorEntry.ID,
            new String[]{"Meteor", "Meteoro"},
            new String[]{"Wait a minute, do we have a meteor?", "Espera aí, nós temos um meteoro?"});
        addModonomiconPage(ChapterIIICategory.ID, DaisySilverMeteorEntry.ID, DaisySilverMeteorEntry.PG_MAIN,
            new String[]{"Meteor", "Meteoro"},
            new String[]{"We can make something similar to iron chains, a little iron and some compacted lapis lazuli.", "Podemos reproduzir algo parecido com correntes de ferro, um pouco de ferro e lapis lazuli compactada."});
        addModonomiconEntry(ChapterIIICategory.ID, DaisySilverMeteorUnlockedEntry.ID,
            new String[]{"Silver Daisy Meteor", "Meteoro da Margarida de Prata"},
            new String[]{"Oh, ok... A metaphorical meteor.", "Ah, ok... Um meteoro metafórico."});
        addModonomiconPage(ChapterIIICategory.ID, DaisySilverMeteorUnlockedEntry.ID, DaisySilverMeteorUnlockedEntry.PG_MAIN,
            new String[]{"Silver Daisy Meteor", "Meteoro da Margarida de Prata"},
            new String[]{"The beauty of a daisy. The weight of a meteor. The shine of silver. The magic of...", "A beleza de uma margarida. O peso de um meteoro. O brilho da prata. A magia do..."});
        // Chapter IV
        addModonomiconCategory(ChapterIVCategory.ID, "Chapter IV", "Capítulo IV");
        addModonomiconEntry(ChapterIVCategory.ID, IntroIVEntry.ID,
            new String[]{"Intro", "Intro"},
            new String[]{"Introduction about spells.", "Introdução sobre feitiços."});
        addModonomiconPage(ChapterIVCategory.ID, IntroIVEntry.ID, IntroIVEntry.PG_MAIN,
            new String[]{"Spells", "Feitiços"},
            new String[]{"[#](AA0000)A little mouse stopped by here LOL. I tore out a few pages, mostly the spells from that white thing, but kept only what might come in handy someday.", "Um ratinho passou por aqui haha. Rasguei algumas páginas, principalmente dos feitiços daquela coisa branca, deixei apenas aquilo que pode ser útil algum dia."});
        // > Purification Spell
        addModonomiconEntry(ChapterIVCategory.ID, PurificationSpellEntry.ID,
            new String[]{"Purification Spell", "Feitiço de Purificação"},
            new String[]{"This spell purify red things.", "Esse feitiço purifica coisas vermelhas."});
        addModonomiconPage(ChapterIVCategory.ID, PurificationSpellEntry.ID, PurificationSpellEntry.PG_MAIN,
            new String[]{"Purification Spell", "Feitiço de Purificação"},
            new String[]{"[#](00AAAA)Combine Sculk, Nether, The End, Overworld, Soul, Portal, Meat and Mind into a single item, and then enchant it!", "[#](00AAAA)Reúna Sculk, Nether, O Fim, Superfície, Alma, Portal, Carne e Espírito numa só fabricação e então, encante-a!"});
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
        String baseKey = key.getDescriptionId() + ".ability";

        for (int a = 1; a <= abilities.length; a++) {
            String[] ability = abilities[a - 1];

            add(baseKey + a, ability);
        }
    }

    protected void addModonomiconResearch(String research, String... titles) {
        add(RRResearchSubProvider.toastTitle(research), titles);
    }

    protected void addModonomiconCategory(String category, String... names) {
        add("%s%s.name".formatted(BASE_BOOK, category), names);
    }

    protected void addModonomiconCategory(String category, String[] names, String[] descriptions) {
        addModonomiconCategory(category, names);
        add("%s%s.description".formatted(BASE_BOOK, category), descriptions);
    }

    protected void addModonomiconEntry(String category, String entry, String[] names, String[] descriptions) {
        String key = "%s%s.%s.".formatted(BASE_BOOK, category, entry);
        add(key + "name", names);
        add(key + "description", descriptions);
    }

    protected void addModonomiconLocked(DeferredItem<Item> entry, String... descriptions) {
        add("research_node.%s.%s.%s".formatted(
            RedReign.MODID, RRResearchSubProvider.RESEARCH, entry.getId().getPath()), descriptions);
    }

    protected void addModonomiconPage(String category, String entry, String page, String[] titles, String[] texts) {
        String key = "%s%s.%s.%s.".formatted(BASE_BOOK, category, entry, page);
        add(key + "title", titles);
        add(key + "text", texts);
    }

    // Hi, antthLuca from the future!
    // You could create a Util class with methods that generate translation keys.
    // That way, datagen and Component.translatable can use the same key without any issues!
}
