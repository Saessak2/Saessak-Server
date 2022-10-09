package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
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

@Repository
@Component
public class MyPlantRepository implements IMyPlantRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyPlantRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void persist(MyPlant myPlant) {
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
    public Optional<MyPlant> findById(Long plantId) {
        MyPlant plant = jdbcTemplate.queryForObject(
                "SELECT * FROM mydb.myplant WHERE id = ?",
                myPlantRowMapper(), plantId);
        return Optional.ofNullable(plant);
    }

    @Override
    public List<MyPlant> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM myplant WHERE user_id = ?",
                myPlantRowMapper(),userId);
    }

    @Override
    public void merge(MyPlant prevPlant, MyPlant nextPlant){
        jdbcTemplate.update(
                "UPDATE myplant SET nickname = ? WHERE nickname = ?",
                nextPlant.getNickname(), prevPlant.getNickname());
    }  //어떻게 쪼갈라야할지 참 ...

    @Override
    public void delete(Long myPlantId) {

    }  //미구현

    private RowMapper<MyPlant> myPlantRowMapper(){
        return (rs, rowNum) -> {
            MyPlant myPlant = new MyPlant();
            myPlant.setId(rs.getLong("id"));
            myPlant.setUser_id(rs.getLong("user_id"));
            myPlant.setNickname(rs.getString("nickname"));
            myPlant.setSpecies(rs.getString("species"));
            myPlant.setSunCondition(rs.getInt("sun_condition"));
            myPlant.setWindCondition(rs.getInt("wind_condition"));
            myPlant.setWaterCondition(rs.getInt("water_condition"));
            myPlant.setLatestWaterDate(rs.getDate("latest_water_date"));
            myPlant.setWaterCycle(rs.getInt("water_cycle"));
            myPlant.setImgUrl(rs.getString("img_url"));
            myPlant.setDisable(rs.getBoolean("disable"));
            return myPlant;
        };
    }

}
