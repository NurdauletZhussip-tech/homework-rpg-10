
package com.narxoz.rpg;

import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");
        System.out.println("=== The Adventurers' Guild Simulation ===\n");

        // 1. Create at least 2 heroes.
        System.out.println("Creating heroes...");
        Hero hero1 = new Hero("Aragorn", 100, 20, 10);
        Hero hero2 = new Hero("Legolas", 80, 25, 8);

        System.out.println("Hero 1: " + hero1);
        System.out.println("Hero 2: " + hero2);

        // Demonstrate hero functionality
        hero1.takeDamage(30);
        System.out.println("\nAfter battle: " + hero1.getName() + " HP: " + hero1.getHp());
        hero2.heal(10);
        System.out.println("After healing: " + hero2.getName() + " HP: " + hero2.getHp());
        hero1.addGold(50);
        System.out.println("\n" + hero1.getName() + " gold: " + hero1.getGold());

        // 2. Build a QuestLog with at least 5 quests of mixed priority.
        System.out.println("\nCreating quest log...");
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

        System.out.println("Quest Log contains " + questLog.size() + " quests:");
        System.out.println("- URGENT: " + q1);
        System.out.println("- HIGH: " + q2);
        System.out.println("- NORMAL: " + q3);
        System.out.println("- LOW: " + q4);
        System.out.println("- HIGH: " + q5);

        // 3. Register at least 4 GuildMembers on the GuildHall.
        System.out.println("\nInitializing Guild Hall...");
        GuildHall guildHall = new GuildHall();

        System.out.println("Registering guild members...");
        Captain captain = new Captain("Boromir", guildHall);
        Scout scout = new Scout("Strider", guildHall);
        Healer healer = new Healer("Elrond", guildHall);
        Quartermaster quartermaster = new Quartermaster("Gandalf", guildHall);
        Loremaster loremaster = new Loremaster("Faramir", guildHall);

        System.out.println("Registered members:");
        System.out.println("  Captain: " + captain.getName());
        System.out.println("  Scout: " + scout.getName());
        System.out.println("  Healer: " + healer.getName());
        System.out.println("  Quartermaster: " + quartermaster.getName());
        System.out.println("  Loremaster: " + loremaster.getName());

        // 4. Iterate the quest log with at least 2 different QuestIterator implementations.
        System.out.println("\n=== Quest Processing Phase ===");
        System.out.println("\n--- Iterating quests in normal order ---");
        QuestIterator normalIterator = questLog.ordered();
        while (normalIterator.hasNext()) {
            Quest quest = normalIterator.next();
            System.out.println("  [QuestIterator] " + quest);
        }

        System.out.println("\n--- Iterating quests in reverse order ---");
        QuestIterator reverseIterator = questLog.reverse();
        while (reverseIterator.hasNext()) {
            Quest quest = reverseIterator.next();
            System.out.println("  [QuestIterator] " + quest);
        }

        System.out.println("\n--- Filtering quests by priority (HIGH or higher) ---");
        QuestIterator highPriorityIterator = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highPriorityIterator.hasNext()) {
            Quest quest = highPriorityIterator.next();
            System.out.println("  [PriorityFilter] " + quest);
        }

        System.out.println("\n--- Sorting quests by reward ---");
        QuestIterator rewardIterator = questLog.byReward();
        while (rewardIterator.hasNext()) {
            Quest quest = rewardIterator.next();
            System.out.println("  [RewardSort] " + quest);
        }

        // 5. Dispatch coordinating messages through the mediator during quest planning.
        System.out.println("\n=== Messaging System Demonstration ===");

        System.out.println("\n--- Dispatching coordinating messages ---");
        captain.issueOrder("logistics", "Prepare supply distribution for quest: " + q1.getTitle());
        scout.reportRoute("tactics", "Scouting details prepared for: " + q2.getTitle());
        quartermaster.requestSupplies("recon", "Need reconnaissance support for: " + q3.getTitle());
        healer.prepareAid("orders", "Medical preparations started for: " + q4.getTitle());

        if (q5.getPriority().ordinal() >= QuestPriority.HIGH.ordinal()) {
            captain.issueOrder("medical", "Emergency response on standby for " + q5.getTitle());
        }

        System.out.println("\n--- Conducting lore research ---");
        loremaster.conductResearch("lore", "Ancient Relic history and curse analysis");
        loremaster.conductResearch("history", "Moria Mines historical records");

        // 6. Run the CouncilEngine and print a final CouncilRunResult.
        System.out.println("\n=== Council Session ===");
        System.out.println("\n--- Running Council Engine ---");

        CouncilEngine councilEngine = new CouncilEngine();
        List<Hero> team = new ArrayList<>();
        team.add(hero1);
        team.add(hero2);

        System.out.println("\n--- Council Engine Output ---");
        councilEngine.runCouncil(team, questLog, guildHall);

        System.out.println("\n=== Simulation Complete ===");
    }
}