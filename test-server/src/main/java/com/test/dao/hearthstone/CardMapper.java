package com.test.dao.hearthstone;

import com.test.dao.hearthstone.entity.Card;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CardMapper {

    @Insert("INSERT INTO cards(id,dbfId,name,cardClass,set,rarity,cost,attack,health,overload,spellDamage,armor,durability,race,type,faction,mechanics,referencedTags,targetingArrowText,playRequirements,entourage,elite,hideStats,classes,multiClassGroup,collectionText,collectible,howToEarn,howToEarnGolden,questReward,artist,flavor,text)" +
            "VALUES (#{card.id},#{card.dbfId},#{card.name},#{card.cardClass},#{card.set},#{card.rarity},#{card.cost},#{card.attack},#{card.health},#{card.overload},#{card.spellDamage},#{card.armor},#{card.durability},#{card.race},#{card.type},#{card.faction},#{card.mechanics},#{card.referencedTags},#{card.targetingArrowText},#{card.playRequirements},#{card.entourage},#{card.elite},#{card.hideStats},#{card.classes},#{card.multiClassGroup},#{card.collectionText},#{card.collectible},#{card.howToEarn},#{card.howToEarnGolden},#{card.questReward},#{card.artist},#{card.flavor},#{card.text})")
    void addCard(@Param("card") Card card);
}
