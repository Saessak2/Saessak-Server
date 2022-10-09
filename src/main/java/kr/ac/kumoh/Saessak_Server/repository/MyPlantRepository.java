package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Component
public class MyPlantRepository implements IMyPlantRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyPlantRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(MyPlant myPlant) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("myplant").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", myPlant.getUser_id());
        params.put("nickname", myPlant.getNickname());
        params.put("species", myPlant.getSpecies());
        params.put("sun_condition", myPlant.getSunCondition());
        params.put("wind_condition", myPlant.getWindCondition());
        params.put("water_condition", myPlant.getWaterCondition());
        params.put("latest_water_date", myPlant.getLatestWaterDate());
        params.put("water_cycle", myPlant.getWaterCycle());
        params.put("img_url", myPlant.getImgUrl());
        params.put("disable", myPlant.getIsDisable());
        jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public void delete(Long myPlantId) {

    }

    @Override
    public List<MyPlant> findByUserId(Long userId) {
        List<MyPlant> tmpList = jdbcTemplate.query(
                "SELECT * FROM myplant WHERE user_id = ?",
                myPlantRowMapper("user_id"));
        return tmpList.stream()
                .filter(myPlant -> myPlant.getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MyPlant> findById(Long plantId) {
        List<MyPlant> tmpList = jdbcTemplate.query("SELECT * FROM myplant WHERE id = ?", myPlantRowMapper("id"));
        return tmpList.stream()
                .filter(myPlant -> myPlant.getId().equals(plantId))
                .findAny();
    }

    private RowMapper<MyPlant> myPlantRowMapper(String colName){
        return (rs, rowNum) -> {
            MyPlant myPlant = new MyPlant();
            myPlant.setId(rs.getLong(colName));
            return myPlant;
        };
    }

}
