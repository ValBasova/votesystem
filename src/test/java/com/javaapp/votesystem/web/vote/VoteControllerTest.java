package com.javaapp.votesystem.web.vote;

import com.javaapp.votesystem.TestUtil;
import com.javaapp.votesystem.model.Vote;
import com.javaapp.votesystem.service.VoteService;
import com.javaapp.votesystem.util.exception.NotFoundException;
import com.javaapp.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static com.javaapp.votesystem.TestUtil.userHttpBasic;
import static com.javaapp.votesystem.UserTestData.*;
import static com.javaapp.votesystem.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    static final LocalDateTime datetime1 = LocalDateTime.of(2020, Month.AUGUST, 20, 10, 59);
    static final LocalDateTime datetime2 = LocalDateTime.of(2020, Month.AUGUST, 21, 10, 59);
    static final LocalDateTime datetime3 = LocalDateTime.of(2020, Month.AUGUST, 22, 12, 0);

    @Test
    void voteCreated() throws Exception {
        voteService.setClock(datetime3);
        MvcResult mvcResult = perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=100004&date=2020-08-22")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        Vote vote = TestUtil.readFromJsonMvcResult(mvcResult, Vote.class);
        VOTE_MATCHER.assertMatch(voteService.get(vote.getId(), USER_ID1), vote);
    }

    @Test
    void voteChanged() throws Exception {
        voteService.setClock(datetime2);
        MvcResult mvcResult = perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=100005&date=2020-08-21")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        Vote vote = TestUtil.readFromJsonMvcResult(mvcResult, Vote.class);
        VOTE_MATCHER.assertMatch(voteService.get(vote.getId(), USER_ID1), vote);
    }

    @Test
    void delete() throws Exception {
        voteService.setClock(datetime1);
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID1 + "?date=2020-08-20")
                .with(userHttpBasic(USER_1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID1, USER_ID1));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID1)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE1));
    }

    @Test
    void getAllByDate() throws Exception {
        perform((MockMvcRequestBuilders.get(AdminVoteController.REST_URL + "?date=2020-08-21"))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE2, VOTE5));
    }
}