package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    public Long persist(MyPlant myPlant) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("myplant").usingGeneratedKeyColumns("id");

        Map<String, Object> params = myPlant.getMyPlantMap();
        return jdbcInsert.executeAndReturnKey(params).longValue();
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
    public int merge(MyPlant myPlant, boolean forDisable){
        int ret;
        if(!forDisable) {
            ret = jdbcTemplate.update(
                    "UPDATE myplant SET id = ?, user_id = ?, nickname = ?, species = ?, " +
                            "sun_condition = ?, wind_condition = ?, water_condition = ?, " +
                            "latest_water_date = ?, water_cycle = ?, img_url = ?, disable = ? WHERE id = ?",
                        myPlant.getId(), myPlant.getUser_id(), myPlant.getNickname(), myPlant.getSpecies(),
                        myPlant.getSunCondition(), myPlant.getWindCondition(), myPlant.getWaterCondition(),
                        myPlant.getLatestWaterDate(), myPlant.getWaterCycle(), myPlant.getImgUrl(),
                        myPlant.getIsDisable(), myPlant.getId());
        }
        else{
            ret = jdbcTemplate.update(
                    "UPDATE myplant SET disable = ? WHERE id = ?",
                         myPlant.getIsDisable(), myPlant.getId());
        }
        return ret;
    }

    @Override
    public int delete(Long myPlantId) {
        return jdbcTemplate.update(
                "DELETE FROM myplant WHERE id = ?",
                myPlantId);
    }

    private RowMapper<MyPlant> myPlantRowMapper(){
        return (rs, rowNum) -> new MyPlant(
                rs.getLong("id"), rs.getLong("user_id"),
                rs.getString("nickname"), rs.getString("species"),
                rs.getInt("sun_condition"), rs.getInt("wind_condition"),
                rs.getInt("water_condition"), rs.getInt("water_cycle"),
                rs.getString("img_url"), rs.getBoolean("disable"),
                rs.getDate("latest_water_date").toString());
    }

}
