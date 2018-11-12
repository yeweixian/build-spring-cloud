package com.test.dao.hearthstone.entity;

import lombok.Data;

@Data
public class Card {

    private String id;
    private String dbfId;
    private String name;
    private String cardClass;
    private String set;
    private String rarity;
    private int cost;
    private String attack;
    private String health;
    private String overload;
    private String spellDamage;
    private String armor;
    private String durability;
    private String race;
    private String type;
    private String faction;
    private String mechanics;
    private String referencedTags;
    private String targetingArrowText;
    private String playRequirements;
    private String entourage;
    private String elite;
    private String hideStats;
    private String classes;
    private String multiClassGroup;
    private String collectionText;
    private String collectible;
    private String howToEarn;
    private String howToEarnGolden;
    private String questReward;
    private String artist;
    private String flavor;
    private String text;
}
