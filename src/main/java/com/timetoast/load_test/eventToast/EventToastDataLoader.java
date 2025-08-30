package com.timetoast.load_test.eventToast;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Order(2)
@Component
public class EventToastDataLoader implements CommandLineRunner {
    private final JdbcTemplate jdbc;

    public EventToastDataLoader(final JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Long> iconIds = jdbc.queryForList("select icon_id from icon", Long.class);

        List<Long> memberIds = jdbc.queryForList("select member_id from member", Long.class);

        Random random = new Random();

        for(Long memberId: memberIds) {
            int randomInt = random.nextInt(20);
            for(int i=0; i<randomInt; i++){
                jdbc.update("INSERT INTO event_toast(member_id, icon_id, title, opened_date, " +
                        "is_opened, description,created_at, last_modified_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                        memberId, iconIds.get(random.nextInt(iconIds.size())), "title"+random.nextInt(),
                        LocalDate.now(), false, "description",LocalDate.now(), LocalDate.now());
            }
        }
    }
}
