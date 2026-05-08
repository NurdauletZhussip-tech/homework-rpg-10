
package com.narxoz.rpg;

import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        // 1. Create at least 2 heroes.
        Hero hero1 = new Hero("Aragorn", 100, 20, 10);
        Hero hero2 = new Hero("Legolas", 80, 25, 8);

        System.out.println("Created heroes:");
        System.out.println("  " + hero1);
        System.out.println("  " + hero2);

        // 2. Build a QuestLog with at least 5 quests of mixed priority.
        QuestLog questLog = new QuestLog();

        Quest q1 = new Quest("Destroy the One Ring", QuestPriority.URGENT, 1000, true);
        Quest q2 = new Quest("Rescue Gandalf", QuestPriority.HIGH, 500, true);
        Quest q3 = new Quest("Explore Mines of Moria", QuestPriority.NORMAL, 300, false);
        Quest q4 = new Quest("Train with Elves", QuestPriority.LOW, 100, false);
        Quest q5 = new Quest("Recover Ancient Relic", QuestPriority.HIGH, 700, false);

        questLog.add(q1);
        questLog.add(q2);
        questLog.add(q3);
        questLog.add(q4);
        questLog.add(q5);

        System.out.println("\nQuest Log contains " + questLog.size() + " quests:");
        QuestIterator normalIterator = questLog.ordered();
        while (normalIterator.hasNext()) {
            System.out.println("  " + normalIterator.next());
        }

        // 3. Register at least 4 GuildMembers (Quartermaster, Scout, Healer, Captain) on the GuildHall.
        GuildHall guildHall = new GuildHall();

        Captain captain = new Captain("Boromir", guildHall);
        Scout scout = new Scout("Strider", guildHall);
        Healer healer = new Healer("Elrond", guildHall);
        Quartermaster quartermaster = new Quartermaster("Gandalf", guildHall);

        System.out.println("\nRegistered Guild Members:");
        System.out.println("  Captain: " + captain.getName());
        System.out.println("  Scout: " + scout.getName());
        System.out.println("  Healer: " + healer.getName());
        System.out.println("  Quartermaster: " + quartermaster.getName());

        // 4. Iterate the quest log with at least 2 different QuestIterator implementations.
        System.out.println("\n--- Iterator phase 1: normal quest order ---");
        normalIterator = questLog.ordered();
        while (normalIterator.hasNext()) {
            Quest quest = normalIterator.next();
            System.out.println("  [QuestIterator] " + quest);
        }

        System.out.println("\n--- Iterator phase 2: reverse quest order ---");
        QuestIterator reverseIterator = questLog.reverse();
        while (reverseIterator.hasNext()) {
            Quest quest = reverseIterator.next();
            System.out.println("  [QuestIterator] " + quest);
        }

        // 5. Dispatch coordinating messages through the mediator during quest planning.
        System.out.println("\n--- Dispatching coordinating messages ---");

        captain.issueOrder("logistics", "Prepare supply distribution for quest: " + q1.getTitle());
        scout.reportRoute("tactics", "Scouting details prepared for: " + q2.getTitle());
        quartermaster.requestSupplies("recon", "Need reconnaissance support for: " + q3.getTitle());
        healer.prepareAid("orders", "Medical preparations started for: " + q4.getTitle());

        if (q5.getPriority().ordinal() >= QuestPriority.HIGH.ordinal()) {
            captain.issueOrder("medical", "Emergency response on standby for " + q5.getTitle());
        }

        // 6. Run the CouncilEngine and print a final CouncilRunResult.
        System.out.println("\n--- Running Council Engine ---");

        CouncilEngine councilEngine = new CouncilEngine();
        List<Hero> team = new ArrayList<>();
        team.add(hero1);
        team.add(hero2);

        System.out.println("\n--- Council Engine Output ---");
        councilEngine.runCouncil(team, questLog, guildHall);
    }
}
