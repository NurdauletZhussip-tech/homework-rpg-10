package com.narxoz.rpg.guild;
public class Loremaster extends GuildMember {
    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }
    public void shareFinding(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }
    public void conductResearch(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }
    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[HistoryMaster " + getName() + "] topic=" + topic
                + " from=" + from.getName() + " -> " + payload);
    }
}