package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;

import java.util.List;

/**
 * Coordinates a guild council session using Iterator and Mediator patterns.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> team, QuestLog log, GuildMediator mediator) {

        if (!(mediator instanceof GuildHall hall)) {
            throw new IllegalArgumentException("GuildHall mediator is required for CouncilEngine");
        }

        Captain captain = locateMember(hall, Captain.class);
        Scout scout = locateMember(hall, Scout.class);
        Quartermaster quartermaster = locateMember(hall, Quartermaster.class);
        Healer healer = locateMember(hall, Healer.class);

        hall.resetMetrics();

        int processedQuests = 0;

        System.out.println("\n--- Iterator phase 1: normal quest order ---");

        QuestIterator normalIterator = log.ordered();

        while (normalIterator.hasNext()) {
            Quest quest = normalIterator.next();
            processedQuests++;

            System.out.println("  [QuestIterator] " + quest);

            displayPartyReview(team, quest);

            captain.issueOrder(
                    "logistics",
                    "Prepare supply distribution for quest: " + quest.getTitle()
            );

            scout.reportRoute(
                    "tactics",
                    "Scouting details prepared for: " + quest.getTitle()
            );

            quartermaster.requestSupplies(
                    "recon",
                    "Need reconnaissance support for: " + quest.getTitle()
            );

            healer.prepareAid(
                    "orders",
                    "Medical preparations started for: " + quest.getTitle()
            );

            if (quest.getPriority().ordinal() >= QuestPriority.HIGH.ordinal()) {
                captain.issueOrder(
                        "medical",
                        "Emergency response on standby for " + quest.getTitle()
                );
            }
        }

        System.out.println("\n--- Iterator phase 2: reverse quest order ---");

        QuestIterator reverseIterator = log.reverse();

        while (reverseIterator.hasNext()) {
            Quest quest = reverseIterator.next();
            processedQuests++;

            System.out.println("  [QuestIterator] " + quest);

            captain.issueOrder(
                    "medical",
                    "Review possible injuries during mission: " + quest.getTitle()
            );

            scout.reportRoute(
                    "casualties",
                    "Emergency retreat routes prepared for: " + quest.getTitle()
            );
        }

        int routedMessages = hall.getDispatchCalls();
        int notifiedMembers = hall.getMemberNotifications();

        return new CouncilRunResult(
                processedQuests,
                routedMessages,
                notifiedMembers
        );
    }

    private static <T extends GuildMember> T locateMember(
            GuildHall hall,
            Class<T> memberType
    ) {

        for (GuildMember member : hall.getRegisteredMembers()) {
            if (memberType.isInstance(member)) {
                return memberType.cast(member);
            }
        }

        throw new IllegalStateException(
                "No registered guild member found for role: "
                        + memberType.getSimpleName()
        );
    }

    private static void displayPartyReview(List<Hero> team, Quest quest) {

        if (team == null || team.isEmpty()) {
            return;
        }

        StringBuilder builder = new StringBuilder("  [Party] ");

        for (int i = 0; i < team.size(); i++) {

            if (i > 0) {
                builder.append(", ");
            }

            builder.append(team.get(i).getName());
        }

        builder.append(" discuss mission: ")
                .append(quest.getTitle());

        System.out.println(builder);
    }
}