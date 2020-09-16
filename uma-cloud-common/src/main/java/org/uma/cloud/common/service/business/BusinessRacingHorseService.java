package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.business.BusinessRacingHorse;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.repository.business.BusinessRacingHorseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessRacingHorseService {

    @Autowired
    private BusinessRacingHorseRepository repository;

    /**
     * @param raceId レースID
     * @return 最大 18個(出走可能競走馬数)
     */
    public List<BusinessRacingHorse> findAllByRaceId(String raceId) {
        return repository.findByRaceId(raceId);
    }

    /**
     * イベント通知 => Weight => 馬体重のみ更新
     *
     * @param weight 馬体重変更
     */
    public List<BusinessRacingHorse> updateAllWeight(Weight weight) {
        List<BusinessRacingHorse> updatingRacingHorse = weight.getHorseWeights()
                .stream()
                .flatMap(horseWeight -> this.findAllByRaceId(weight.getRaceId())
                        .stream()
                        // 同じhorseNoのものだけ更新。
                        .filter(racingHorse -> racingHorse.getHorseNo().equals(horseWeight.getHorseNo()))
                        .peek(racingHorse -> {
                            racingHorse.setHorseWeight(horseWeight.getHorseWeight());
                            // 馬体重増減
                            if ("+".equals(horseWeight.getChangeSign())) {
                                racingHorse.setHorseWeightGainOrLoss(horseWeight.getChangeAmount());
                            } else if ("-".equals(horseWeight.getChangeSign())) {
                                racingHorse.setHorseWeightGainOrLoss(-horseWeight.getChangeAmount());
                            } else if ("".equals(horseWeight.getChangeSign())) {
                                if (horseWeight.getChangeAmount() == null) {
                                    // nullなら、０を入れる。新馬戦とかの場合、nullになる。（前回出走していないから）
                                    racingHorse.setHorseWeightGainOrLoss(0);
                                } else {
                                    // 計測不可能フラグの９９９が入る or 前走と増減なしの０が入る。
                                    racingHorse.setHorseWeightGainOrLoss(horseWeight.getChangeAmount());
                                }
                            }
                        }))
                .collect(Collectors.toUnmodifiableList());

        return this.updateAll(updatingRacingHorse);
    }

    /**
     * イベント通知 => jockeyChange => 騎手のみ更新
     *
     * @param jockeyChange 騎手変更
     */
    public BusinessRacingHorse updateJockeyChange(JockeyChange jockeyChange) {
        BusinessRacingHorse.CompositeId id = createId(jockeyChange.getRaceId(), jockeyChange.getHorseNo());
        BusinessRacingHorse racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        racingHorse.setJockeyNameShort(jockeyChange.getJockeyNameAfter());
        racingHorse.setLoadWeight(jockeyChange.getLoadWeightAfter());
        racingHorse.setJockeyApprentice(jockeyChange.getJockeyApprenticeCdAfter());

        return this.update(racingHorse);
    }

    /**
     * イベント通知 => avoid => 出走取消 or 競走除外 のみ更新
     *
     * @param avoid 出走取消 or 競走除外
     */
    public BusinessRacingHorse updateAvoid(Avoid avoid) {
        BusinessRacingHorse.CompositeId id = createId(avoid.getRaceId(), avoid.getHorseNo());
        BusinessRacingHorse racingHorse = repository.findById(id).orElseThrow(); // ないのはおかしい。
        // ここセンスない。。
        racingHorse.setExclude(Integer.parseInt(avoid.getDataDiv()));
        racingHorse.setExcludeReason(avoid.getReason());

        return this.update(racingHorse);
    }


    @Transactional
    public BusinessRacingHorse update(BusinessRacingHorse businessRacingHorse) {
        return repository.save(businessRacingHorse);
    }

    @Transactional
    public List<BusinessRacingHorse> updateAll(List<BusinessRacingHorse> model) {
        return repository.saveAll(model);
    }


    private static BusinessRacingHorse.CompositeId createId(String raceId, String horseNo) {
        BusinessRacingHorse.CompositeId id = new BusinessRacingHorse.CompositeId();
        id.setRaceId(raceId);
        id.setHorseNo(horseNo);
        return id;
    }

}
