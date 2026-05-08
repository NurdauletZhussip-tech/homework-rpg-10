package com.narxoz.rpg.guild;
public class HistoryMaster extends GuildMember {
    public HistoryMaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }
    public void shareFinding(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }
    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[HistoryMaster " + getName() + "] topic=" + topic
                + " from=" + from.getName() + " -> " + payload);
    }
}